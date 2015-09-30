<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class User extends ISM_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		
		$this->load->helper(array('csv','file'));		
		$this->load->library(array('zip'));

	}

	// ---------------------------- User Module Start --------------------------------------------
	
	/**
	  * function index() have all users listing using codeigniter pagination limit of 15 users per page
	  *
	  **/
	 
	public function index() {
		
		$this->data['page_title'] = 'Users';

		$this->load->library('pagination');
		
		if($_GET){

			if( !empty($_GET['role']) ) { $role = $this->input->get('role'); }	
			if( !empty($_GET['course'])){ $course  = $this->input->get('course'); }
			if( !empty($_GET['school'])){ $school = $this->input->get('school'); }
			if( !empty($_GET['year']) ) { $year = $this->input->get('year'); }
			if( !empty($_GET['classroom']) ){  $classroom = $this->input->get('classroom'); }

			if( !empty($role) || !empty($course) || !empty($school) || !empty($year) || !empty($classroom) ){

				$str = '';

				if(!empty($role)){ $where['where']['role_id'] = $role ; $str .= '&role='.$role; }	
				if(!empty($course)){  $where['where']['student_academic_info.course_id'] = $course; $str .='&course='.$course; }
				if(!empty($school)){  $where['where']['student_academic_info.school_id'] = $school; $str .='&school='.$school; }
				if(!empty($classroom)){ $where['where']['student_academic_info.classroom_id'] = $classroom; $str .= '&$classroom='.$classroom;  }
				if(!empty($year)){ 
									$next_year=$year+1; $academic_year = "$year-$next_year";    // find next year and create string like 2015-2016
									$where['where']['student_academic_info.academic_year'] = $academic_year; $str .='year='.$year;  
								}

				$str =  trim($str,'&');

				$config['base_url']	 = base_url().'admin/user/index?'.$str;
				$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
				$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'
				
			}

		}else{
			$where=null;
			$config['base_url']	 = base_url().'admin/user/index';
			$offset = $this->uri->segment(4);
		}

		$config['uri_segment'] = 4;
		$config['num_links'] = 5;
		$config['total_rows'] = select('users',FALSE,$where,array('count'=>TRUE,'join'=>array(array('table' => 'student_academic_info','condition' => 'users.id = student_academic_info.user_id'))));
		$config['per_page'] = 15;

		$config['full_tag_open'] = '<ul class="pagination pagination_admin">';
	  	$config['full_tag_close'] = '</ul>';

	  	$config['num_tag_open'] = '<li>';
	  	$config['num_tag_close'] = '</li>';

	  	$config['first_link'] = 'First';
	  	$config['first_tag_open'] = '<li>';
	  	$config['first_tag_close'] = '</li>';

	  	$config['cur_tag_open'] = '<li style="display:none"></li><li class="active"><a>';
	  	$config['cur_tag_close'] = '</a></li>';

	  	$config['prev_link'] = '&laquo;';
	  	$config['prev_tag_open'] = '<li>';
	  	$config['prev_tag_close'] = '</li>';

		$config['next_link'] = '&raquo;';
	  	$config['next_tag_open'] = '<li>';
	  	$config['next_tag_close'] = '</li>';

	  	$config['last_link'] = 'Last';
	  	$config['last_tag_open'] = '<li>';
	  	$config['last_tag_close'] = '</li>';

		//fetch all data of users joins with roles,cities,countries,states 
		$this->data['all_users'] =   select('users',
											'users.id,users.username,cities.city_name,states.state_name,
											users.role_id,roles.role_name,student_academic_info.course_id,courses.course_name,
											classrooms.class_name',
											$where,
											array(
												'limit'=>$config['per_page'],
												'offset'=>$offset,
												'join' =>  array(
											    			array(
											    				'table' => 'roles',
											    				'condition' => 'roles.id = users.role_id'
																),
											    			array(
											    				'table' => 'countries',
											    				'condition' => 'countries.id = users.country_id'
																),
											    			array(
											    				'table' => 'states',
											    				'condition' => 'states.id = users.state_id'
																),
											    			array(
											    				'table' => 'cities',
											    				'condition' => 'cities.id = users.city_id'
																),
											    			array(
											    				'table' => 'student_academic_info',
											    				'condition' => 'users.id = student_academic_info.user_id'
																),
											    			array(
											    				'table'=>'courses',
											    				'condition'=>'student_academic_info.course_id=courses.id'	
											    				),
											    			array(
											    				'table'=>'classrooms',
											    				'condition'=>'student_academic_info.classroom_id=classrooms.id'	
											    				),		
												    		)
												)
											);
		
		$this->pagination->initialize($config);
		
		$this->data['schools'] = select(TBL_SCHOOLS,FALSE,FALSE,array('limit'=>10));
		$this->data['courses'] = select(TBL_COURSES,FALSE,FALSE,array('limit'=>10));
		$this->data['roles'] = select(TBL_ROLES,FALSE,FALSE,array('limit'=>10));
		$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,FALSE,array('limit'=>10));

		$this->template->load('admin/default','admin/user/view_user',$this->data);
	}

	/**
	  * function add() have User Registration form to Add user by Admin using codeigniter validation
	  *
	  **/

	public function add(){

		$this->data['page_title'] = 'Users Add';

		$this->data['countries']  =select('countries');
		$this->data['states'] =select('states');
		$this->data['cities'] =select('cities');
		$this->data['roles'] =select('roles');
		$this->data['packages'] =select('membership_package');

		$this->form_validation->set_rules('username', 'User Name', 'trim|required|is_unique[users.username]');
		$this->form_validation->set_rules('password', 'Password', 'trim|required|min_length[8]');	
		$this->form_validation->set_rules('re_password', 'Retype Password', 'trim|required|matches[password]|min_length[8]');
		$this->form_validation->set_rules('email_id', 'Email', 'trim|valid_email|is_unique[users.email_id]');	

		if($this->form_validation->run() == FALSE){

			$this->template->load('admin/default','admin/user/add_user',$this->data);
			
		}else{

			$encrypted_password = $this->encrypt->encode($this->input->post("password"));

			$bdate = $this->input->post("birthdate");
			if(!empty($bdate)){ $bdate = $this->input->post("birthdate"); }else{ $bdate = "0000-00-00"; }

			$data=array(
				 "username"=>$this->input->post("username"),
				 "password"=>$encrypted_password,
				 "first_name"=>$this->input->post("first_name"),
				 "last_name"=>$this->input->post("last_name"),
				 "full_name"=>$this->input->post("full_name"),
				 "email_id"=>$this->input->post("email_id"),
				 "contact_number"=>$this->input->post("contact_number"),
				 "home_address"=>$this->input->post("home_address"),
				 "city_id"=>$this->input->post("city"),
				 "state_id"=>$this->input->post("state"),
				 "country_id"=>$this->input->post("country"),
				 "birthdate"=>$bdate,
				 "gender"=>$this->input->post("gender"),
				 "device_type"=>$this->input->post("device_type"),
				 "device_token"=>$this->input->post("device_token"),
				 "role_id"=>$this->input->post("roles"),
				 "user_status"=>$this->input->post("user_status"),
				 "about_me"=>$this->input->post("about_me"),
				 "user_current_api_version"=>$this->input->post("user_current_api_version"),
				 "membership_validity_date"=>$this->input->post("membership_validity_date"),
				 "package_id"=>$this->input->post("package"),
				 "created_date"=>date('Y-m-d H:i:s'),
				 "modified_date"=>date('Y-m-d H:i:s'),
				 "is_delete"=>0,
				 "is_testdata"=>'yes',
				 "websocket_id"=>''
			);
			
			insert('users',$data);	 // insert data into database using common_model.php and cms_helper.php

			$this->session->set_flashdata('success', 'Record is Successfully created.');
			redirect('admin/user');
		}

	}

	public function test(){

		$this->load->view('admin/user/select_test');
	}

	/**
	  * function update() have Form of user rgisteration with fill up data with userdata of given ID
	  *	@param User ID
	  * @author Virendra Patel - Spark ID- VPA
	  **/

	public function update($id){

		$this->data['page_title'] = 'Users Update';

		if(empty($id) && !	is_numeric($id)){
			redirect('admin/dashboard');
		 }

		$this->data['user'] = select('users',FALSE,array('where'=>array('id'=>$id)),array('single'=>TRUE));	
		$this->data['countries'] = select('countries');
		$this->data['states'] = select('states',FALSE,array('where'=>array('country_id'=>$this->data['user']['country_id'])));
	    $this->data['cities'] = select('cities',FALSE,array('where'=>array('state_id'=>$this->data['user']['state_id'])));
		$this->data['roles'] = select('roles');
		$this->data['packages'] = select('membership_package');
		
		if($_POST){
			
			$username = $this->input->post('username');
			$email = $this->input->post('email_id');

			if($this->data['user']['username'] !== $username){
				$user_rule = 'trim|required|is_unique[users.username]';
			}else{
				$user_rule = 'trim|required';
			}

			if($this->data['user']['email_id'] !== $email){
				$email_rule = 'trim|required|is_unique[users.email_id]';
			}else{
				$email_rule = 'trim';
			}

		}else{
			$user_rule = 'trim|required';
			$email_rule = 'trim|required';
		}

		$this->form_validation->set_rules('username', 'User Name', $user_rule);
		$this->form_validation->set_rules('email_id', 'Email', $email_rule);	

		if($this->form_validation->run() == FALSE){

			$this->template->load('admin/default','admin/user/update_user',$this->data);

		}else{
			
			$bdate = $this->input->post("birthdate");
			if(!empty($bdate)){ $bdate = $this->input->post("birthdate"); }else{ $bdate = "0000-00-00"; }

			$data=array(
				 "username"=>$this->input->post("username"),
				 "first_name"=>$this->input->post("first_name"),
				 "last_name"=>$this->input->post("last_name"),
				 "full_name"=>$this->input->post("full_name"),
				 "email_id"=>$this->input->post("email_id"),
				 "contact_number"=>$this->input->post("contact_number"),
				 "home_address"=>$this->input->post("home_address"),
				 "city_id"=>$this->input->post("city"),
				 "state_id"=>$this->input->post("state"),
				 "country_id"=>$this->input->post("country"),
				 "birthdate"=>$bdate,
				 "gender"=>$this->input->post("gender"),
				 "modified_date"=>date('Y-m-d H:i:s'),
				 "role_id"=>$this->input->post("roles"),
				 "user_status"=>$this->input->post("user_status"),
				 "about_me"=>$this->input->post("about_me"),
				 "package_id"=>$this->input->post("package")
			);
	
			update('users',$id,$data);	// Update data  using common_model.php and cms_helper.php

			$this->session->set_flashdata('success', 'Record is Successfully updated.');
			redirect('admin/user');

		}

	}

	/**
	 * function Blcked will block user for temporary set database field 'status' of `users` table set to 'blocked' and redirect to user listing page
	 *
	 **/
	
	public function blocked($id){
		update('users',$id,array('user_status'=>'blocked'));
		$this->session->set_flashdata('success', 'User is Successfully Blocked.');
		redirect('admin/user');
	}

	/**
	 * function send_message will used to send message from admin to other user tables-(messages,messages_receiver) 
	 *	admin can send messages to one or more than one users at a time
	 **/
	public function send_message($id){
		
		$this->data['page_title'] = 'User Send Message';

		$this->data['u'] =select(TBL_USERS,FALSE,array('where'=>array('id'=>$id)),array('single'=>true));
		$this->data['templates'] =select(TBL_MESSAGES,FALSE,array('where'=>array('is_template'=>'1')));
		$this->data['users'] =select(TBL_USERS,
									TBL_USERS.'.username,'.TBL_USERS.'.id,'.TBL_ROLES.'.role_name',
									 array('where_not_in'=>array(TBL_USERS.'.role_id'=>array('1'))),
									 array(
										'order_by'=>TBL_USERS.'.username',
										'join'=>array(
													array(
														'table'=>'roles',
														'condition'=>TBL_USERS.'.role_id='.TBL_ROLES.'.id'
													)
												)
											)
									);	

		$this->form_validation->set_rules('all_users[]', 'Users', 'trim|required');	
		$this->form_validation->set_rules('message_title', 'Message Title', 'trim|required');	
		$this->form_validation->set_rules('message_desc', 'Message', 'trim|required');	

		if($this->form_validation->run() == FALSE){
			
			$this->template->load('admin/default','admin/user/send_message',$this->data);
		}else{


			$all_users = $this->input->post('all_users');

			if(!empty($all_users)){			

				foreach($all_users as $user){

						$data = array(
								'message_text'=>$this->input->post('message_desc'),
								'sender_id'=>$this->session->userdata('id'),
								'message_title'=>$this->input->post('message_title'),
								'status'=>'1',
								'reply_for'=>'0',
								'created_date'=>date('Y-m-d H:i:s',time()),
								'modified_date'=>'0000-00-00 00:00:00',
								'is_template'=>$this->input->post('save_template'),
								'is_delete'=>'0',
								'is_testdata'=>'yes'
							);

						//insert data into messages table
						$message_id = insert(TBL_MESSAGES,$data);

						$data_message_receiver = array(
								'message_id'=>$message_id,
								'receiver_id'=>$user,
								'created_date'=>date('Y-m-d H:i:s',time()),
								'modified_date'=>'0000-00-00 00:00:00',	
								'is_delete'=>'0',
								'is_testdata'=>'yes'
							);

						// insert data into messages_receiver table using message id from message table
						insert(TBL_MESSAGE_RECEIVER,$data_message_receiver);

					}
				}

				$this->session->set_flashdata('success', 'Message has been Successfully sent.');
				redirect('admin/user');

				die();

				$this->email->from('email@email.com', 'Name');
				$this->email->to('someone@example.com');
				
				$this->email->subject('subject');
				$this->email->message('message');
				
				$this->email->send();
				
				echo $this->email->print_debugger();



		}

	}

	public function send_messages(){
		
		$this->data['page_title'] = 'Users Send Messages';

		if($_POST){
			
			if(isset($_POST['all_users'])){
				$this->data['post_users'] = $this->input->post('all_users[]');
				$this->data['my_cnt'] = 1;
			}elseif(isset($_POST['message_title'])){
				$this->data['my_cnt'] = 1;
			}else{
				$this->data['post_users'] = $this->input->post('users');
				$this->data['my_cnt'] = 0;	
			}

		}else{
			$this->data['post_users'] = array();
		} 

		$this->data['templates'] =select(TBL_MESSAGES,FALSE,array('where'=>array('is_template'=>'1')));
		$this->data['users'] =select(TBL_USERS,
									TBL_USERS.'.username,'.TBL_USERS.'.id,'.TBL_ROLES.'.role_name',
									 array('where_not_in'=>array(TBL_USERS.'.role_id'=>array('1'))),
									 array(
										'order_by'=>TBL_USERS.'.username',
										'join'=>array(
													array(
														'table'=>'roles',
														'condition'=>TBL_USERS.'.role_id='.TBL_ROLES.'.id'
													)
												)
											)
									);

		$this->form_validation->set_rules('all_users[]', 'Users', 'trim|required');	
		$this->form_validation->set_rules('message_title', 'Message Title', 'trim|required');	
		$this->form_validation->set_rules('message_desc', 'Message', 'trim|required');	

		if($this->form_validation->run() == FALSE){

			$this->template->load('admin/default','admin/user/send_messages',$this->data);	

		}else{
			
			$all_users = $this->input->post('all_users');

			if(!empty($all_users)){			

				foreach($all_users as $user){

						$data = array(
								'message_text'=>$this->input->post('message_desc'),
								'sender_id'=>$this->session->userdata('id'),
								'message_title'=>$this->input->post('message_title'),
								'status'=>'1',
								'reply_for'=>'0',
								'created_date'=>date('Y-m-d H:i:s',time()),
								'modified_date'=>'0000-00-00 00:00:00',
								'is_template'=>$this->input->post('save_template'),
								'is_delete'=>'0',
								'is_testdata'=>'yes'
							);

						//insert data into messages table
						$message_id = insert(TBL_MESSAGES,$data);

						$data_message_receiver = array(
								'message_id'=>$message_id,
								'receiver_id'=>$user,
								'created_date'=>date('Y-m-d H:i:s',time()),
								'modified_date'=>'0000-00-00 00:00:00',	
								'is_delete'=>'0',
								'is_testdata'=>'yes'
							);

						// insert data into messages_receiver table using message id from message table
						insert(TBL_MESSAGE_RECEIVER,$data_message_receiver);

					}

					$this->session->set_flashdata('success', 'Messages has been Successfully sent.');
					redirect('admin/user');
				}		
			}
		}

	// ---------------------------- User Module END --------------------------------------------

}

/* End of file User.php */
/* Location: ./application/controllers/Admin/User.php */