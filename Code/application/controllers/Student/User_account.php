<?php
defined('BASEPATH') OR exit('No direct script access allowed');
/*-kap-22-09-2015-------------------*/
class User_account extends CI_Controller {

	public function __construct()
	{
	    parent::__construct();
	    $this->data['title'] = 'ISM User Account';
	    $this->load->model('common_model');
	    $this->load->model('student/student_account_model');
	    $this->load->library('upload','form_validation');
	    $this->data[] = array();
	    if(!empty($this->session->userdata('user')))
	    	redirect('student/group_allocation');
	}

	public function index()
	{	
		/*------fill combo's------*/
		$this->data['countries'] 	= 	$this->common_model->get_all('countries');
		$this->data['states'] 		= 	$this->common_model->get_all('states');
		$this->data['cities'] 		= 	$this->common_model->get_all('cities');
		$this->data['schools'] 		= 	$this->common_model->get_all('schools');
		$this->data['class'] 		= 	$this->common_model->get_all('classrooms');
		// $this->data['years'] 		= 	$this->common_model->get_all('years');
		$this->data['program'] 		= 	$this->common_model->get_all('courses');
		$this->data['districts'] 		= 	$this->common_model->get_all('districts');
		// $this->data['school_information'] = $this->common_model->getAll(
		// 	$this->session->userdata('credential_user'));
		$this->data['school_information'] = select(TBL_AUTO_GENERATED_CREDENTIAL.' a',
	    		's.id as school_id,a.classroom_id as class_id,a.course_id as program,s.district_id',	
	    		array('a.username' => $this->session->userdata('credential_user')),
		    		array( 'join' => array(
		    			array(
		    				'table' => TBL_SCHOOLS.' s',
		    				'condition' => 's.id = a.school_id'
		    			)),
		    		'single' => 1
		    		));
		
		$this->form_validation->set_rules('username', 'Username', 'trim|required|callback_check_user');
		$this->form_validation->set_rules('cur_password', 'Current Password', 'trim|required|callback_check_current_password');
		$this->form_validation->set_rules('new_password', 'New Password', 'trim|required|exact_length[8]');
		$this->form_validation->set_rules('con_password', 'Confirm Password', 'trim|required|matches[new_password]');
		$this->form_validation->set_rules('email', 'Email', 'trim|valid_email|is_unique[users.email_id]|callbach_check_email');
		$this->form_validation->set_rules('reg[birthdate]', 'Date of birth', 'regex_match[(0[1-9]|1[0-9]|2[0-9]|3(0|1))-(0[1-9]|1[0-2])-\d{4}]');
		$todo 	= 	$this->input->post('todo',TRUE);
		if($todo == 'enabled'){
			$this->form_validation->set_rules('school_id', 'School', 'trim|required');
			$this->form_validation->set_rules('class_id', 'Class', 'trim|required');
			$this->form_validation->set_rules('year_id', 'Year', 'trim|required');
			$this->form_validation->set_rules('district_id', 'District', 'trim|required');
			$this->form_validation->set_rules('program_id', 'Program/Course', 'trim|required');
		}
		if($this->form_validation->run() == FALSE){
			$this->load->view('student/user_account_update_view',$this->data);
		}
		else{
			$old_username = $this->session->userdata('credential_user');
			$data = array('username'=>$old_username);
			$old_result = select(TBL_AUTO_GENERATED_CREDENTIAL,null,array('where'=>$data),1);
			$password = $this->encrypt->decode($old_result['password']);
			$id = $old_result['id'];

			// if($password == $this->input->post('cur_password')){
				
				/*-----------user detail-----------*/
				// if($error_count == 0 ){
					$data_student = array(
					 "full_name"		=>	$this->input->post("fullname"),
					 "email_id"			=>	$this->input->post("email_id"),
					 "gender"			=>	$this->input->post("gender"),
					 "contact_number"	=>	$this->input->post("contact"),
					 "home_address"		=>	$this->input->post("home_address"),
					 "country_id"		=>	$this->input->post("country_id"),
					 "state_id"			=>	$this->input->post("state_id"),
					 "city_id"			=>	$this->input->post("city_id"),
					 "username"			=>	$this->input->post("username"),
					 "role_id"			=>	$old_result['role_id'],
					 "password"			=>	$this->encrypt->encode($this->input->post("new_password")),
					 "created_date"		=>	date('Y-m-d H:i:s',time()),
					 "modified_date"	=>	date('Y-m-d H:i:s',time())
					);
					$insertid =  insert(TBL_USERS,$data_student);
					$update_data = array('status' => 1 );
					// update('auto_generated_credential',$id,$update_data);
					
					/*-----------user academic detail--------------*/
					if(!empty($this->input->post('todo'))){
						$data_academic = array(
							'user_id' => $insertid,
							'school_id'=>$this->input->post('school_id'),
							'classroom_id'=>$this->input->post('class_id'),
							'academic_year'=>$this->input->post('year_id'),
							'course_id'=>$this->input->post('program_id')
						);
						$course_id	= 	$this->input->post('program_id');
						$school_id	=	$this->input->post('school_id');
						insert(TBL_STUDENT_ACADEMIC_INFO,$data_academic);
					}
					else{
						$query_result		= $this->data['school_information']; 
						$data_academic 	= array(
							'user_id' => $insertid,
							'school_id'=>$query_result['school_id'],
							'classroom_id'=>$query_result['class_id'],
							'academic_year'=>'2015-2016',
							'course_id'=>$query_result['program']
						);
						$course_id	= 	$query_result['program'];
						$school_id	=	$query_result['school_id'];
						insert(TBL_STUDENT_ACADEMIC_INFO,$data_academic);
					}

					/*----------upload image----------*/
					$path = "uploads/user_".$insertid;

				    if(!is_dir($path)) //create the folder if it's not already exists
				    {
				      mkdir($path,0755,TRUE);
				    } 
					$ext = pathinfo($_FILES['profile_image_1']['name'], PATHINFO_EXTENSION);
					$name = str_replace('.'.$ext, '', $_FILES['profile_image_1']['name'].'_'.time()).'.'.$ext;
					$config['upload_path'] = $path;
					$config['allowed_types'] = 'gif|jpg|png';
					$config['max_size']  = '1000000000';	
					$config['file_name'] = $name;
					$error_count = 0;
					$this->upload->initialize($config);
					if(!empty($_FILES['profile_image_1']['name'])){
						if (!$this->upload->do_upload('profile_image_1')){
							$file_upload_error = strip_tags($this->upload->display_errors(),'');
							$file_required_error = "You did not select a file to upload.";
							if($file_upload_error !== $file_required_error){
								$error_count++;
								$this->session->set_flashdata('error_profile', $file_upload_error);
							}
							else{
								$student_profile = '';
							}
						}
						else{
							$data = array('upload_data' => $this->upload->data());
							$student_profile = "user_".$insertid.'/'.$data['upload_data']['file_name'];
						}
					}

					/*-----------user profile pic detail--------------*/
					if(!empty($student_profile)){
						$data_profile_pic = array(
							'user_id' => $insertid,
							'profile_link'=>$student_profile,
							"created_date"=>date('Y-m-d H:i:s',time()),
						 	"modified_date"=>date('Y-m-d H:i:s',time())
						);
						insert(TBL_USER_PROFILE_PICTURE,$data_profile_pic);
					}

					/*---create login session----*/
                    $this->set_session($insertid);
                    $get_grade = select(TBL_SCHOOLS,'school_grade',array('where'=>array('id'=>$school_id)),1);
                    $school_grade = $get_grade['school_grade'];
                   /*------------group allocation------------------------*/
                   $where 	=	array('where'=>array('i.school_id !='=>$school_id,'i.course_id'=>$course_id,'tg.is_completed'=>'1'));
                   $options	=	array('join'=>
	       							array(
	       								array(
	       									'table'=>TBL_TUTORIAL_GROUP_MEMBER.' tm',
	       									'condition'=>'tm.user_id = i.user_id'
	       								),
	       								array(
	       									'table'=>TBL_TUTORIAL_GROUPS.' tg',
	       									'condition'=>'tm.group_id = tg.id'
	       								),
	       								array(
	       									'table'=>TBL_SCHOOLS.' s',
	       									'condition'=>'i.school_id = s.id'
	       								)	
	       							),
	       							'group_by'=>'tm.group_id'
	       						);
                   $exist_members 	= 	select(TBL_STUDENT_ACADEMIC_INFO.' i','i.user_id,tm.group_id,group_concat(s.school_grade) as grade,i.course_id',$where,$options);
                   if(sizeof($exist_members)>0){
                   foreach ($exist_members as $key => $value) {
                   		$grade_array	=	explode(',',$value['grade']);
                   		if(in_array($school_grade, $grade_array)){
               			 	$group_data =	array(
               					'group_name'		=>	'tutorial_group1',
               					'group_type'		=>	'tutorial group',
               					'group_status'		=>	0
               				);
               				$groupid 	=	insert(TBL_TUTORIAL_GROUPS,$group_data);
               				$group_member_data	=	array(
               					'group_id'			=> 	$groupid,
               					'user_id'			=>	$insertid,
               					'joining_status'	=>	0
               				);
               				insert(TBL_TUTORIAL_GROUP_MEMBER,$group_member_data);
							redirect('student/group_allocation');
                   		}
                   		else{

               			  	$group_member_data	=	array(
		                   		'group_id'			=> 	$value['group_id'],
		                   		'user_id'			=>	$insertid,
		                   		'joining_status'	=>	0,
		                   	);
		                   	insert(TBL_TUTORIAL_GROUP_MEMBER,$group_member_data);
							$member_count	=	count($grade_array)+1;
							if($member_count == 5){
								$is_completed	=  array('is_completed' => 1);
								$where	=	array('group_id' => $value['group_id']);
								update(TBL_TUTORIAL_GROUPS,$where,$is_completed);
							}
							redirect('student/group_allocation');
                   		}
                   	}
                   }else{
                   			$group_data =	array(
               					'group_name'		=>	'tutorial_group1',
               					'group_type'		=>	'tutorial group',
               					'group_status'		=>	0
               				);
               				$groupid 	=	insert(TBL_TUTORIAL_GROUPS,$group_data);
               				$group_member_data	=	array(
               					'group_id'			=> 	$groupid,
               					'user_id'			=>	$insertid,
               					'joining_status'	=>	0
               				);
               				insert(TBL_TUTORIAL_GROUP_MEMBER,$group_member_data);
							redirect('student/group_allocation');
                   }
           			// p($null,TRUE);
           			// exit;
					redirect('student/home');
				// }
				// else{
				// 	redirect('student/register_student');
				// }
			// }
			// else{
			// 	$this->session->set_flashdata('error_cur_pass', 'Please enter correct Current Password.');
			// 	redirect('student/user_account');
			// }
		}
	}

	/*--get state country wise-----------------*/
	public function ajax_get_states(){
		
		$country_id = $this->input->post('country_id');
		$all_states = select(TBL_STATES,null,array('where'=>array('country_id'=>$country_id)));
		$new_str = '';
		
		$new_str .= '<option selected disabled >Select State</option>';
		if(!empty($all_states)){
			foreach($all_states as $state){
				$new_str.='<option value="'.$state['id'].'">'.$state['state_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	/*--get city state wise-----------------*/
	public function ajax_get_cities(){

		$state_id = $this->input->post('state_id');
		$all_cities = select(TBL_CITIES,array('where'=>array('state_id'=>$state_id)));
		$new_str = '';

		$new_str .= '<option selected disabled >Select City</option>';
		if(!empty($all_cities)){
			foreach($all_cities as $city){
				$new_str.='<option value="'.$city['id'].'">'.$city['city_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	/*--check user exist or not------------------*/
	public function check_user()
	{
		$found	=	select(TBL_USERS,null,array('where'=>array('username' => $this->input->post('username',TRUE))));
		if(sizeof($found) > 0){
			$this->form_validation->set_message('check_user', 'Username Already Exist');
			return FALSE;
		}
		else{
			return TRUE;
		}
	}

	/*--check email exist or not------------------*/
	public function check_email()
	{
		$found	=	select(TBL_USERS,array('where'=>array('email_id' => $this->input->post('email_id',TRUE))));
		if(sizeof($found) > 0){
			$this->form_validation->set_message('check_email', 'Email Already Exist');
			return FALSE;
		}
		else{
			return TRUE;
		}
	}

	/*--check current password is valid or not------*/
	public function check_current_password()
	{
		$old_username	=	$this->session->userdata('credential_user');
		$data 			= 	array('username'=>$old_username);
		$old_result 	= 	select(TBL_AUTO_GENERATED_CREDENTIAL,null,array('where'=>$data),1);
		$password 		= 	$this->encrypt->decode($old_result['password']);
		if($this->input->post('cur_password',TRUE) == $password){
			return TRUE;
		}
		else{
			$this->form_validation->set_message('check_current_password', 'Current Password Not Match');
			return FALSE;
		}
	}

	 public function set_session($userid){
        $users = select(TBL_USERS.' u',
                'u.*,s.school_name, s.address as school_address, ct.city_name as city_name, cut.country_name as country_name, st.state_name as state_name,up.profile_link as profile_pic',   
                array('where'   =>  array('u.id' => $userid)),
                array('join'    =>    
                    array(
                        array(
                            'table' => TBL_USER_PROFILE_PICTURE.' up',
                            'condition' => 'up.user_id = u.id'
                            ),
                        array(
                            'table' => TBL_TUTORIAL_GROUP_MEMBER.' tm',
                            'condition' => 'tm.user_id = u.id'
                            ),
                        
                        array(
                            'table' => TBL_TUTORIAL_GROUPS.' gu',
                            'condition' => 'gu.id = tm.group_id'
                            ),
                        array(
                            'table' => TBL_STUDENT_ACADEMIC_INFO.' si',
                            'condition' => 'u.id = si.user_id'
                            ),
                        array(
                            'table' => TBL_SCHOOLS.' s',
                            'condition' => 's.id = si.school_id'
                            ),
                        array(
                            'table' => TBL_CITIES.' ct',
                            'condition' => 'ct.id = u.city_id'
                            ), 
                        array(
                            'table' => TBL_COUNTRIES.' cut',
                            'condition' => 'cut.id = u.country_id'
                            ), 
                        array(
                            'table' => TBL_STATES.' st',
                            'condition' => 'st.id = u.state_id'
                            )
                        )
                    )    
                );
        $session_data = array(
            'loggedin' => TRUE,
            'user'=>$users[0]
        );
        $this->session->set_userdata($session_data);
        return;
    }
}
