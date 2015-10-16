<?php
defined('BASEPATH') OR exit('No direct script access allowed');
/**
* kap - kamlesh pokiya 
*
*/
class User_account extends CI_Controller {

	public function __construct()
	{
	    parent::__construct();
	    $this->data['title'] = 'ISM - Manage Account';
	    $this->load->library('upload','form_validation');
	    $this->data[] = array();
	    if(!empty($this->session->userdata('user')) && $this->session->userdata('user')['membercount'] != 5)
	    	redirect('student/group_allocation');
	    if(empty($this->session->userdata('user')) && empty($this->session->userdata('credential_user')))
	    	redirect('login/logout');

	}

	public function index()
	{	

		/*--get record for update--*/

		if(isset($this->session->userdata('user')['id'])){
			
			$this->set_session($this->session->userdata('user')['id']);
			$user_session	= 	$this->session->userdata('user');

			$this->data['full_name']	= $user_session['full_name'];
			$this->data['email_id']		= $user_session['email_id'];
			$this->data['gender']		= $user_session['gender'];
			$this->data['birthdate']	= $user_session['birthdate'];
			$this->data['contact_number']	= $user_session['contact_number'];
			$this->data['home_address']	= $user_session['home_address'];
			$this->data['country_id']	= $user_session['country_id'];
			$this->data['state_id']		= $user_session['state_id'];
			$this->data['city_id']		= $user_session['city_id'];
			$this->data['school_name']	= $user_session['school_name'];
			$this->data['academic_year']= $user_session['academic_year'];
			$this->data['course_name']	= $user_session['course_name'];
			$this->data['classroom_id']	= $user_session['classroom_id'];
			$this->data['class_name']	= $user_session['class_name'];
			$this->data['district_id']	= $user_session['district_id'];
			$this->data['district_name']	= $user_session['district_name'];
			$this->data['username']		= $user_session['username'];
			$this->data['password']		= $this->encrypt->decode($user_session['password']);
			$this->data['profile_pic']	= $user_session['profile_pic'];
			$this->data['disabled']		=	'';
			$this->data['display']		=	'none';
		}
		
		/*------fill combo's------*/
		$this->data['countries'] 	= 	select(TBL_COUNTRIES);
		$this->data['states'] 		= 	select(TBL_STATES);
		$this->data['cities'] 		= 	select(TBL_CITIES);
		$this->data['schools'] 		= 	select(TBL_SCHOOLS);
		$this->data['class'] 		= 	select(TBL_CLASSROOMS);
		// $this->data['years'] 		= 	select('years');
		$this->data['program'] 		= 	select(TBL_COURSES);
		$this->data['districts'] 		= 	select(TBL_DISTRICTS);
		$this->data['school_information'] = select(TBL_AUTO_GENERATED_CREDENTIAL.' a',
	    		's.id as school_id,a.classroom_id as class_id,a.course_id as program,s.district_id,a.is_my_school,s.school_name,c.course_name,cl.class_name,d.district_name,a.academic_year',	
	    		array('where' => array('a.username' => $this->session->userdata('credential_user'))),
		    		array( 'join' => array(
		    			array(
		    				'table' => TBL_SCHOOLS.' s',
		    				'condition' => 's.id = a.school_id'
		    			),
		    			array(
		    				'table' => TBL_COURSES.' c',
		    				'condition' => 'c.id = a.course_id'
		    			),
		    			array(
		    				'table' => TBL_CLASSROOMS.' cl',
		    				'condition' => 'cl.id = a.classroom_id'
		    			),
		    			array(
		    				'table' => TBL_DISTRICTS.' d',
		    				'condition' => 's.district_id = d.id'
		    			)

		    			),
		    		'single' => 1
		    		));
		// qry(true);
		$this->form_validation->set_rules('username', 'Username', 'trim|required|callback_check_user');
		$this->form_validation->set_rules('contact_number', 'Contact Number', 'trim|regex_match[/^[0-9().-]+$/]');
		
		if(isset($this->session->userdata('user')['id'])){
			$this->form_validation->set_rules('new_password', 'New Password', 'trim|exact_length[8]');
			$this->form_validation->set_rules('con_password', 'Confirm Password', 'trim|matches[new_password]');
			// $this->form_validation->set_rules('class_id', 'Class', 'trim|required');
			// $this->form_validation->set_rules('district_id', 'District', 'trim|required');
			$this->form_validation->set_rules('cur_password', 'Current Password', 'trim|required');

		}
		else{
			$this->form_validation->set_rules('new_password', 'New Password', 'trim|required|exact_length[8]');
			$this->form_validation->set_rules('con_password', 'Confirm Password', 'trim|required|matches[new_password]');
			$this->form_validation->set_rules('cur_password', 'Current Password', 'trim|required|callback_check_current_password');
			$todo 	= 	$this->input->post('todo',TRUE);
			// if($todo == 'enabled'){
			// 	$this->form_validation->set_rules('school_id', 'School', 'trim|required');
			// 	$this->form_validation->set_rules('class_id', 'Class', 'trim|required');
			// 	$this->form_validation->set_rules('year_id', 'Year', 'trim|required');
			// 	$this->form_validation->set_rules('district_id', 'District', 'trim|required');
			// 	$this->form_validation->set_rules('program_id', 'Program/Course', 'trim|required');
			// }
		}
		
		/*---send request for change school detail ------*/
		$request_change = $this->input->post('send_request');
		if($request_change == 'change')
		{
			$this->send_mail($this->session->userdata('credential_user'));
		}
		// p($_POST,true);
		$this->form_validation->set_rules('email_id', 'Email', 'trim|valid_email|required|callback_check_email');
		$this->form_validation->set_rules('full_name', 'Full name', 'trim|required|callback_check_full_name');
		$this->form_validation->set_rules('birthdate', 'Date of birth', 'trim|callback_check_birth_date');
					
		if($this->form_validation->run() == FALSE){
			$this->load->view('student/user_account_update',$this->data);
		}
		else{
			if($this->input->post("birthdate") == '')
				$birthdate = null;
			else
				$birthdate = $this->input->post("birthdate");
			if($this->input->post("country_id") == '')
				$country = null;
			else
				$country = $this->input->post("country_id");
			if($this->input->post("state_id") == '')
				$state_id = null;
			else
				$state_id = $this->input->post("state_id");
			if($this->input->post("city_id") == '')
				$city_id = null;
			else
				$city_id = $this->input->post("city_id");
			$data_student = array(
				"full_name"			=>	$this->input->post("full_name"),
				"email_id"			=>	$this->input->post("email_id"),
				"gender"			=>	$this->input->post("gender"),
				"contact_number"	=>	$this->input->post("contact_number"),
				"home_address"		=>	$this->input->post("home_address"),
				"country_id"		=>	$country,
				"state_id"			=>	$state_id,
				"city_id"			=>	$city_id,
				"username"			=>	$this->input->post("username"),
				"birthdate"			=>	$birthdate,
				"password"			=>	$this->encrypt->encode($this->input->post("new_password")),
				"user_status"		=>	'active'
			);
			if(isset($this->session->userdata('user')['id'])){
				$uid = 	$this->session->userdata('user')['id'];
				
				/*---update user detail --*/
				
				if($this->input->post('new_password') == ''){
					unset($data_student['password']);
					$data_student['password']	=	$this->encrypt->encode($this->input->post('cur_password'));
				}
				$data_student["modified_date"] = date('Y-m-d H:i:s',time());
				update(TBL_USERS,array('id'=>$uid),$data_student);
				
				/*---update acedemic detail--*/
				// $data_academic	=	array(
				// 		'classroom_id'=>$this->input->post('class_id')
				// 	);
				// update(TBL_STUDENT_ACADEMIC_INFO,array('user_id'=>$uid),$data_academic);
				
				/*--upadate profile picture*/
				
				$path = "uploads/user_".$uid;

				if(!empty($_FILES['profile_image_1']['name'])){
				    if(!is_dir($path)){
				      	mkdir($path,0755,TRUE);
				    } 
				$ext 	= pathinfo($_FILES['profile_image_1']['name'], PATHINFO_EXTENSION);
				$name 	= str_replace('.'.$ext, '', $_FILES['profile_image_1']['name'].'_'.time()).'.'.$ext;
				$config['upload_path']	 	= $path;
				$config['allowed_types'] 	= 'gif|jpg|png';
				$config['max_size']  		= '1000000000';	
				$config['file_name'] 		= $name;
				$error_count = 0;
				$this->upload->initialize($config);
				
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
						$student_profile = "user_".$uid.'/'.$data['upload_data']['file_name'];
					}
				}
				crop(UPLOAD_URL.'/'.$student_profile,150,150);

				/*-----------user profile pic detail--------------*/

				if(!empty($student_profile)){
					$data_profile_pic = array(
						'profile_link'=>$student_profile,
						"created_date"=>date('Y-m-d H:i:s',time()),
					 	"modified_date"=>date('Y-m-d H:i:s',time())
					);
					update(TBL_USER_PROFILE_PICTURE,array('user_id'=>$uid),$data_profile_pic);
				}
				$this->session->set_flashdata('success','Record Updated');
				redirect('student/user_account');

			}
			else{
				$old_username = $this->session->userdata('credential_user');
				$data = array('username'=>$old_username);
				$old_result = select(TBL_AUTO_GENERATED_CREDENTIAL,null,array('where'=>$data),1);
				$password = $this->encrypt->decode($old_result['password']);
				$id = $old_result['id'];
				$data_student["role_id"] = $old_result['role_id'];
				$data_student["created_date"] = date('Y-m-d H:i:s',time());
				/*-----------user detail-----------*/
				
				
				$insertid =  insert(TBL_USERS,$data_student);
				
				/*------update auto credential when once user can filup form----*/
				
				$update_data = array('status' => 0 );
				update(TBL_AUTO_GENERATED_CREDENTIAL,$id,$update_data);
				
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
					$class_id	=	$this->input->post('class_id');
					$academic_year	=	$this->input->post('year_id');
					insert(TBL_STUDENT_ACADEMIC_INFO,$data_academic);
				}
				else{
					$query_result		= $this->data['school_information']; 
					$data_academic 	= array(
						'user_id' => $insertid,
						'school_id'=>$query_result['school_id'],
						'classroom_id'=>$query_result['class_id'],
						'academic_year'=>$query_result['academic_year'],
						'course_id'=>$query_result['program']
					);
					$course_id	= 	$query_result['program'];
					$school_id	=	$query_result['school_id'];
					$class_id	=	$query_result['class_id'];
					$academic_year	=	$query_result['academic_year'];
					insert(TBL_STUDENT_ACADEMIC_INFO,$data_academic);
				}

				/*----------upload image----------*/

				$path = "uploads/user_".$insertid;

				/*----create the folder if it's not already exists--*/

				if(!empty($_FILES['profile_image_1']['name'])){
			    if(!is_dir($path)){
			      	mkdir($path,0755,TRUE);
			    } 
				$ext 	= pathinfo($_FILES['profile_image_1']['name'], PATHINFO_EXTENSION);
				$name 	= str_replace('.'.$ext, '', $_FILES['profile_image_1']['name'].'_'.time()).'.'.$ext;
				$config['upload_path']	 	= $path;
				$config['allowed_types'] 	= 'gif|jpg|png';
				$config['max_size']  		= '1000000000';	
				$config['file_name'] 		= $name;
				$error_count = 0;
				$this->upload->initialize($config);
				
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
					crop(UPLOAD_URL.'/'.$student_profile,200,200);
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
	           	
	           	/*------------group allocation--------------------*/
	           	
	           	$where 	=	array('where'=>array('i.school_id !='=>$school_id,'i.course_id'=>$course_id,'tg.is_completed'=>'0','i.classroom_id'=>$class_id,'i.academic_year'=>$academic_year));
	           	$options	=	array('join'=>
	   							array(
	   								array(
	   									'table'=>TBL_TUTORIAL_GROUP_MEMBER.' tm',
	   									'condition'=>'tm.user_id = i.user_id'
	   								),
	   								array(
	   									'table' => TBL_TUTORIAL_GROUPS.' tg',
	   									'condition' => 'tg.id = tm.group_id'
	   								)
	   							),
	   							'group_by' => 'tm.group_id'
	   						);
	           	$exist_members 	= 	select(TBL_STUDENT_ACADEMIC_INFO.' i','tm.group_id as group_id',$where,$options);

	           	//--find all group are match with my crieteria

	           	$my_group_id = array();
	           	foreach ($exist_members as $key => $value) {
	           		$my_group_id[] = $value['group_id'];
	           	}

	           	if(sizeof($my_group_id) > 0){
	           	
		           	//--find all schools grade that are joined with founded groups 
		           	
		           	$options = array('join' => array(
		           						array('table' => TBL_STUDENT_ACADEMIC_INFO.' i','condition' => 'i.user_id = tm.user_id'),
		           						array('table' => TBL_SCHOOLS.' s','condition' => 'i.school_id = s.id')
		           					),
		           				'group_by' => 'tm.group_id'
		           				);
		           	$where = array('where_in' => array('tm.group_id' => $my_group_id));
		           	$get_school_info = select(TBL_TUTORIAL_GROUP_MEMBER.' tm','tm.group_id, group_concat(s.school_grade) as grade',$where,$options);

		           	/*----Auto generated group name------*/	

		           	$options			=	array('order_by'=>'RAND()','limit'=>1,'single'=>1); 	
		           	$found_group_name 	=	select(TBL_GROUP_NAMES,'group_name',null,$options);
		           	$course_name		=	select(TBL_COURSES,'course_name',array('where'=>array('id'=>$course_id)),1);
		           	$group_name 		=	$found_group_name['group_name'].'-'.$course_name['course_name'];
		        }
	           	if(sizeof($get_school_info)>0){

	               //--find group where grade is instead of my grade 

	           		$found_grade = '';

	               	foreach ($get_school_info as $key => $value) {
	               		$grade_array	=	explode(',',$value['grade']);
	               		if(in_array($school_grade, $grade_array)){
	               			$found_grade = true;//--found
	               		}
	               		else{
	               			$found_grade = false;//--not found	
	               			$found_group_id = $value['group_id']; 
	               		}
	               	}

	               	if($found_grade == true){//--when found then create new group
           			 	$group_data =	array(
           					'group_name'		=>	$group_name,
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
                        redirect('login/welcome');
	               	}
	               	else if($found_grade == false){//--when not found then use exist group
           			  	$group_member_data	=	array(
	                   		'group_id'			=> 	$found_group_id,
	                   		'user_id'			=>	$insertid,
	                   		'joining_status'	=>	0,
	                   	);
	                   	insert(TBL_TUTORIAL_GROUP_MEMBER,$group_member_data);
						$member_count	=	count($grade_array)+1;
						if($member_count == 5){
							$is_completed	=  array('is_completed' => 1);
							$where	=	array('id' => $value['group_id']);
							update(TBL_TUTORIAL_GROUPS,$where,$is_completed);
						}
						redirect('login/welcome');
	               	}
	           	}
	           	else{//--if no any records with our crieteria then creat new one group
	       			$group_data =	array(
	   					'group_name'		=>	$group_name,
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
	   				redirect('login/welcome');
	           }
			}
		}

	}

	/*--get state country wise-----------------*/
	public function ajax_get_states(){
		
		$country_id = $this->input->post('country_id');
		$all_states = select(TBL_STATES,null,array('where'=>array('country_id'=>$country_id)));
		$new_str = '';
		
		$new_str .= '<option selected>Select State</option>';
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
		$all_cities = select(TBL_CITIES,null,array('where'=>array('state_id'=>$state_id)));
		$new_str = '';

		$new_str .= '<option selected>Select City</option>';
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
		$uid = $this->session->userdata('user')['id'];
		if(!empty($uid))
			$found	=	select(TBL_USERS,null,array('where'=>array('username' => $this->input->post('username',TRUE),'id !='=>$uid)));
		else
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
		if($this->session->userdata('user')['id'] != '')
			$where = array('where'=>array('email_id' => $this->input->post('email_id',TRUE),'id !='=>$this->session->userdata('user')['id']));
		else
			$where = array('where'=>array('email_id' => $this->input->post('email_id',TRUE)));
		$found	=	select(TBL_USERS,null,$where);
		if(sizeof($found) > 0){
			$this->form_validation->set_message('check_email', 'Email Already Exist');
			return FALSE;
		}
		else{
			return TRUE;
		}
	}

	/*---check full name is valid or not------*/
	public function check_full_name(){
		$full_name = $this->input->post('full_name',TRUE);
		if(preg_match('/^[a-z0-9 .\-]+$/i', $full_name))
		{
			return TRUE;	
		}
		else
		{
			$this->form_validation->set_message('check_full_name', 'Invalid Full Name');
			return FALSE;	
		}
	}

	public function check_birth_date(){
		$birthdate = $this->input->post('birthdate',TRUE);
		if($birthdate != ''){
			if (preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$birthdate)){
				if($birthdate == '0000-00-00')
					$this->form_validation->set_message('check_birth_date', 'Invalid Date');
				else
					return TRUE;
			}
			else{
				$this->form_validation->set_message('check_birth_date', 'Invalid Date');
				return FALSE;
			}
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
                'u.*,s.district_id,s.school_name, s.address as school_address, ct.city_name as city_name, cut.country_name as country_name, st.state_name as state_name,up.profile_link as profile_pic,tm.group_id,co.course_name,si.academic_year,si.course_id,si.classroom_id,si.school_id,(select count(*) cnt from tutorial_group_member where group_id = gu.id) as membercount,cl.class_name,s.district_id,d.district_name',   
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
                            ),
                        array(
                            'table' => TBL_COURSES.' co',
                            'condition' => 'si.course_id = co.id'
                            ),
                        array(
                            'table' => TBL_CLASSROOMS.' cl',
                            'condition' => 'cl.id = si.classroom_id'
                            ),
                        array(
                        	'table' => TBL_DISTRICTS.' d',
                        	'condition' => 'd.id = s.district_id'
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


    public function send_mail($username){
    	
    	update(TBL_AUTO_GENERATED_CREDENTIAL,array('username'=>$username),array('is_my_school'=>1));
		$message = $this->input->post('message');
		$email_id = $this->input->post('request_email');
		$name = $this->input->post('request_name');
		$configs = mail_config();
        $this->load->library('email', $configs);
        $this->email->initialize($configs);
        $this->email->from($email_id,$name );
        $this->email->to('kap.narola@narolainfotech.com');
        // $encoded_mail = urlencode($token);
        $msg = '';
        $msg .='<html>';
        $msg .='<head><title></title></head>';
        $msg .='<body style="background-color:#f5f5f5; background: repeating-linear-gradient(90deg, #eee, #fff 8px); color:#333; font-family:Tahoma, Geneva, sans-serif;">
            <table align="center" style="width: 600px;">
                <tr>
                    <td style="text-align:center; padding: 35px 0;"><img alt="ISM" height="70px" src="../images/logo.png"></td>
                </tr>
                <tr>
                    <td>
                        <table style="padding: 15px; width:100%;background-color: #fff;border: 1px solid rgba(0,0,0,0.1);">
                	<tr>
                    	<td style="text-align: center;border-bottom: 1px solid rgba(0,0,0,0.1);">
                        	<h4 style="color: #1bc4a3; margin:10px 0;">Change School Detail Request</h4>
                        </td>
                    </tr>                   
                    <tr>
                    	<td style="border-bottom: 1px solid rgba(0,0,0,0.1); padding: 20px 0;">
                        	<table width="100%">
                            	<tr>
                                	<td style="width:160px;">
                                    	Hello,<br><br>
                                        <b style="font-size:x-small;">Email : '.$email_id.'</b><br>
                                        <b style="font-size:x-small;">Username : '.$username.'</b><br>
                                        <pre>'.$message.'</pre>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>                    
                    <tr>
                    	<td>
                        	<span style="font-size:x-small;">©2015 ISM. All Rights Reserved.</span>   
                        </td>
                    </tr>
                </table>
                    </td>
                </tr>
            </table>
        </body>';
        $msg .='</html>';
        $this->email->subject('ISM - Change School Detail');
        $this->email->message($msg);
        $this->email->send();
        $this->email->print_debugger();
        $this->session->set_flashdata('success', 'Your request submitted successfully.');
        redirect('student/user_account');
    }
}
