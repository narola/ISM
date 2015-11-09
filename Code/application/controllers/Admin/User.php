<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class User extends ADMIN_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();

		$this->load->helper(array('csv','file'));		
		$this->load->library(array('zip'));
		$this->data['cur_url'] = $this->session->userdata('cur_url');
		$this->data['prev_url'] = $this->session->userdata('prev_url');
	}

	// ---------------------------- User Module Start --------------------------------------------
	
	/**
	  * function index() have all users listing using codeigniter pagination limit of 15 users per page
	  *
	  **/
	 
	public function index() {
		
		if($this->input->post('email',TRUE) != ''){
			$this->send_invitation();
		}

		$this->data['page_title'] = 'Users';
		$order = '';
		$where['where'][TBL_USERS.'.is_delete']=FALSE;

		if(!empty($_GET['role']) || !empty($_GET['course']) || !empty($_GET['school']) || 
			!empty($_GET['classroom']) || !empty($_GET['q']) || !empty($_GET['order']) ){

			if( !empty($_GET['role']) ) { $role = $this->input->get('role'); }	
			if( !empty($_GET['course'])){ $course  = $this->input->get('course'); }
			if( !empty($_GET['school'])){ $school = $this->input->get('school'); }
			if( !empty($_GET['year']) ) { $year = $this->input->get('year'); }
			if( !empty($_GET['classroom']) ){  $classroom = $this->input->get('classroom'); }
			if( !empty($_GET['q']) ){  $q = $this->input->get('q'); }
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		


			$str = '';

			if(!empty($role)){ $where['where']['role_id'] = $role ; $str .= '&role='.$role; }	
			if(!empty($course)){  $where['where'][TBL_STUDENT_ACADEMIC_INFO.'.course_id'] = $course; $str .='&course='.$course; }
			if(!empty($school)){  $where['where'][TBL_STUDENT_ACADEMIC_INFO.'.school_id'] = $school; $str .='&school='.$school; }
			if(!empty($classroom)){ $where['where'][TBL_STUDENT_ACADEMIC_INFO.'.classroom_id'] = $classroom; $str .= '&classroom='.$classroom;  }
			if(!empty($year)){ 
								$next_year=$year+1; $academic_year = "$year-$next_year";    // find next year and create string like 2015-2016
								$where['where'][TBL_STUDENT_ACADEMIC_INFO.'.academic_year'] = $academic_year; $str .='&year='.$year;  
							}
			if(!empty($q)){ $where['like'][TBL_USERS.'.username'] = $q; $str.='&q='.$q; }							

			if($order == 'name_asc'){ $order = TBL_USERS.".username asc"; $str.='&order=name_asc';  }
			if($order == 'name_desc'){ $order = TBL_USERS.".username desc"; $str.='&order=name_desc'; }
			if($order == 'latest'){ $order = TBL_USERS.".created_date desc"; $str.='&order=latest'; }
			if($order == 'older'){ $order = TBL_USERS.".created_date asc"; $str.='&order=older'; }

			$str =  trim($str,'&');

			if(!empty($str)) { $config['base_url']	 = base_url().'admin/user/index?'.$str; }else{ $config['base_url'] = base_url().'admin/user/index';  }
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
	
		}else{
			
			$where['where'][TBL_USERS.'.is_delete']=FALSE;
			
			$config['base_url']	 = base_url().'admin/user/index';
			$offset = $this->uri->segment(4);
		}

		$config['uri_segment'] = 4;
		$config['num_links'] = 2;
		$config['total_rows'] = select(TBL_USERS,FALSE,$where,array('count'=>TRUE,'join'=>array(array('table' => TBL_STUDENT_ACADEMIC_INFO,'condition' => TBL_USERS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.user_id'))));
		$config['per_page'] = 15;

		$config['full_tag_open'] = '<ul class="pagination pagination_admin">';
	  	$config['full_tag_close'] = '</ul>';

	  	$config['num_tag_open'] = '<li>';
	  	$config['num_tag_close'] = '</li>';

	  	$config['first_link'] = 'First';
	  	$config['first_tag_open'] = '<li>';
	  	$config['first_tag_close'] = '</li>';

	  	$config['cur_tag_open'] = '<li style="display:none"></li><li class="active"><a>';
	  	$config['cur_tag_close'] = '</a></li><li style="display:none"></li>';

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
		$this->data['all_users'] =   select(TBL_USERS,
											TBL_USERS.'.id,'.TBL_USERS.'.user_status,'.TBL_USERS.'.username,'.TBL_CITIES.'.city_name,'.TBL_STATES.'.state_name,
											'.TBL_USERS.'.role_id,'.TBL_ROLES.'.role_name,'.TBL_STUDENT_ACADEMIC_INFO.'.course_id,'.TBL_COURSES.'.course_name,
											'.TBL_CLASSROOMS.'.class_name,'.TBL_USER_PROFILE_PICTURE.'.profile_link,'.TBL_USERS.'.profile_pic',
											$where,
											array(
												'order_by'=>$order,
												'limit'=>$config['per_page'],
												'offset'=>$offset,
												'join' =>  array(
											    			array(
											    				'table' => TBL_ROLES,
											    				'condition' => TBL_ROLES.'.id = '.TBL_USERS.'.role_id'
																),
											    			array(
											    				'table' => TBL_COUNTRIES,
											    				'condition' => TBL_COUNTRIES.'.id = '.TBL_USERS.'.country_id'
																),
											    			array(
											    				'table' => TBL_STATES,
											    				'condition' => TBL_STATES.'.id = '.TBL_USERS.'.state_id'
																),
											    			array(
											    				'table' => TBL_CITIES,
											    				'condition' => TBL_CITIES.'.id = '.TBL_USERS.'.city_id'
																),
											    			array(
											    				'table' => TBL_STUDENT_ACADEMIC_INFO,
											    				'condition' => TBL_USERS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.user_id'
																),
											    			array(
											    				'table'=>TBL_COURSES,
											    				'condition'=>TBL_STUDENT_ACADEMIC_INFO.'.course_id='.TBL_COURSES.'.id'	
											    				),
											    			array(
											    				'table'=>TBL_CLASSROOMS,
											    				'condition'=>TBL_STUDENT_ACADEMIC_INFO.'.classroom_id='.TBL_CLASSROOMS.'.id'	
											    				),
											    			array(
											    				'table'=>TBL_USER_PROFILE_PICTURE,
											    				'condition'=>TBL_USER_PROFILE_PICTURE.'.user_id='.TBL_USERS.'.id'	
											    				)			
												    		)
												)
											);
		
		// p($this->data['all_users'],true);
		$this->pagination->initialize($config);
		
		$this->data['schools'] = select(TBL_SCHOOLS,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['roles'] = select(TBL_ROLES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>FALSE)));

		$this->template->load('admin/default','admin/user/view_user',$this->data);
	}

	/**
	  * function add() have User Registration form to Add user by Admin using codeigniter validation
	  *
	  **/

	public function add(){

		$this->data['page_title'] = 'Users Add';

		$this->data['countries']  =select(TBL_COUNTRIES);
		$this->data['states'] =select(TBL_STATES);
		$this->data['cities'] =select(TBL_CITIES);
		$this->data['roles'] =select(TBL_ROLES);
		$this->data['packages'] =select(TBL_MEMBERSHIP_PACKAGE);

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
			
			insert(TBL_USERS,$data);	 // insert data into database using common_model.php and cms_helper.php

			$this->session->set_flashdata('success', 'Record is Successfully created.');
			redirect('admin/user');
		}
	}

	/**
	  * function update() have Form of user rgisteration with fill up data with userdata of given ID
	  *	@param User ID
	  * @author Virendra Patel - Spark ID- VPA
	  **/

	public function update($id){

		$this->data['page_title'] = 'Users Update';

		if(empty($id) && !is_numeric($id)){
			redirect('admin');
		 }

		$this->data['user'] = select(TBL_USERS,FALSE,array('where'=>array('id'=>$id)),array('single'=>TRUE));	
		$this->data['countries'] = select(TBL_COUNTRIES);
		$this->data['states'] = select(TBL_STATES,FALSE,array('where'=>array('country_id'=>$this->data['user']['country_id'])));
	    $this->data['cities'] = select(TBL_CITIES,FALSE,array('where'=>array('state_id'=>$this->data['user']['state_id'])));
		$this->data['roles'] = select(TBL_ROLES,FALSE,array('where'=>array('is_delete'=>'0')));
		$this->data['packages'] = select(TBL_MEMBERSHIP_PACKAGE);
		
		if($_POST){
			
			$username = $this->input->post('username');
			$email = $this->input->post('email_id');

			if($this->data['user']['username'] !== $username){
				$user_rule = 'trim|required|is_unique[users.username]|alpha_numeric|max_length[8]';
			}else{
				$user_rule = 'trim|required|alpha_numeric|max_length[8]';
			}

			if($this->data['user']['email_id'] !== $email){
				$email_rule = 'trim|is_unique[users.email_id]|valid_email';
			}else{
				$email_rule = 'trim|valid_email';
			}

		}else{
			$user_rule = 'trim|required|alpha_numeric|max_length[8]';
			$email_rule = 'trim|valid_email';
		}

		$this->form_validation->set_rules('username', 'User Name', $user_rule);
		$this->form_validation->set_rules('email_id', 'Email', $email_rule);
		$this->form_validation->set_rules('first_name', 'First Name', 'trim|alpha_numeric_spaces');
		$this->form_validation->set_rules('last_name', 'Last Name', 'trim|alpha_numeric_spaces');	
		$this->form_validation->set_rules('full_name', 'Full Name', 'trim|alpha_numeric_spaces');
		$this->form_validation->set_rules('contact_number', 'Contact Number', 'trim|numeric');	
		$this->form_validation->set_rules('birthdate', 'Birthdate', 'required|trim|callback_valid_date');
		$this->form_validation->set_rules('city', 'City', 'required|trim');
		$this->form_validation->set_rules('state', 'State', 'required|trim');
		$this->form_validation->set_rules('country', 'Country', 'required|trim');

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
				 "package_id"=>$this->input->post("package"),
				 'modified_date'=>date('Y-m-d H:i:s',time())
			);
	
			update(TBL_USERS,$id,$data);	// Update data using common_model.php and cms_helper.php
			$this->session->set_flashdata('success', 'Record is Successfully updated.');
			redirect($this->data['prev_url']); // Redirect to previous page set in ADMIN_Controller.php

		}
	}

	/**
	 *
	 * @return Bool
	 * @author  Virendra patel SparkID-VPA
	 **/
	
	public function valid_date($date){

		if (preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$date))
	    {
	        return true;
	    }else{
	    	$this->form_validation->set_message('valid_date','The date is not valid.');
	        return false;
	    }

	}


	/**
	 * function Blocked will block user for temporary set database field 'user_status' of `users` table set to 
	 * 'blocked' and redirect to user listing page
	 *
	 **/
	
	public function blocked($id){
		update(TBL_USERS,$id,array('user_status'=>'blocked','modified_date'=>date('Y-m-d H:i:s',time())));
		$this->session->set_flashdata('success', 'User is Successfully Blocked.');
		redirect($this->data['prev_url']);
	}

	/**
	 * function active will activate user for temporary set database field 'user_status' of `users` table set to 
	 * 'active' and redirect to user listing page
	 *
	 **/
	
	public function active($id){
		update(TBL_USERS,$id,array('user_status'=>'active','modified_date'=>date('Y-m-d H:i:s',time())));
		$this->session->set_flashdata('success', 'User is Successfully Activated.');
		redirect($this->data['prev_url']);
	}

	/**
	 * function send_message will used to send message from admin to other user tables-(messages,messages_receiver) 
	 *	admin can send messages to one or more than one users at a time
	 **/
	public function send_message($id){
		
		$this->data['page_title'] = 'User Send Message';

		if(empty($id) && !is_numeric($id)){
			redirect('admin');
		 }

		$this->data['templates'] =select(TBL_MESSAGES,FALSE,array('where'=>array('is_template'=>'1','is_delete'=>0)));
		
		$role_id = $this->input->post('role_id');

		if(!$_POST || $role_id==0){

			$this->data['u'] =select(TBL_USERS,FALSE,array('where'=>array('id'=>$id,'is_delete'=>0)),array('single'=>true));
			$this->data['users'] =select(TBL_USERS,
										TBL_USERS.'.username,'.TBL_USERS.'.id,'.TBL_ROLES.'.role_name,'.TBL_ROLES.'.id as rid',
										 array(
										 		'where'=>array(TBL_USERS.'.is_delete'=>FALSE),
										 		'where_not_in'=>array(TBL_USERS.'.user_status'=>array('blocked'))
										 	  ),
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

		}else{

			$this->data['users'] =select(TBL_USERS,
										TBL_USERS.'.username,'.TBL_USERS.'.id,'.TBL_ROLES.'.role_name,'.TBL_ROLES.'.id as rid',
										 array(
										 		'where'=>array(
										 						TBL_USERS.'.is_delete'=>FALSE,
										 						TBL_USERS.'.role_id'=>$role_id
										 					  ),
										 		'where_not_in'=>array(TBL_USERS.'.user_status'=>array('blocked'))
										 	  ),
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
			$this->data['u'] =select(TBL_USERS,FALSE,array('where'=>array('id'=>$id,'role_id'=>$role_id,'is_delete'=>0)),array('single'=>true));
		}

		if(count($this->input->post('all_users')) == 0){
			$this->form_validation->set_rules('all_users', 'Users', 'trim|required');		
		}										

		$this->data['roles'] = select(TBL_ROLES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		

		$this->form_validation->set_rules('message_title', 'Message Title', 'trim|required|alpha_numeric_spaces');	
		$this->form_validation->set_rules('message_desc', 'Message', 'trim|required');	

		if($this->form_validation->run() == FALSE){
			
			$this->template->load('admin/default','admin/user/send_message',$this->data);
		}else{

			$all_users = $this->input->post('all_users');

			$msg_title = $this->input->post('message_title');
			$msg_text = $this->input->post('message_desc');

			$db_template = select(TBL_MESSAGES,FALSE,array('where'=>array('is_template'=>'1')));

			$cnt = 0;
			if(isset($_POST['save_template'])){
				foreach($db_template as $db_temp){
					
					if($db_temp['message_title'] === $msg_title){
						$cnt++;
					}
				}
			}

			if($cnt != 0){
				$this->session->set_flashdata('msgerror', 'Message template should be Unique.');
				redirect('admin/user/send_message/'.$id);
			}
			
			$template_counter = 0;

			if(!empty($all_users)){			

				foreach($all_users as $user){

						if(isset($_POST['save_template']) && $template_counter != 1 ){
							$template_counter = 1;
							$template = '1';
						}else{
							$template = '0';
						}

						$data = array(
								'message_text'=>$msg_text,
								'sender_id'=>$this->session->userdata('id'),
								'message_title'=>$msg_title,
								'status'=>'1',
								'reply_for'=>'0',
								'is_template'=>$template
							);

						//insert data into messages table
						$message_id = insert(TBL_MESSAGES,$data);

						$data_message_receiver = array(
								'message_id'=>$message_id,
								'receiver_id'=>$user
							);

						// insert data into messages_receiver table using message id from message table
						insert(TBL_MESSAGE_RECEIVER,$data_message_receiver);

						$user_mail = select(TBL_USERS,'email_id',array('where'=>array('id'=>$user)),array('single'=>TRUE));

						if(!empty($user_mail['email_id'])){
							
							$config = mail_config(); // set configuration for email from email_helper.php
							
							$this->email->initialize($config);
							$this->load->library('email', $config);	
							$this->email->from('admin@admin.com', 'Admin');
							$this->email->to($user_mail['email_id']);
							
							$this->email->subject($msg_title);
							$this->email->message($msg_text);
							
							$this->email->send();
						}
					}
				}

				$this->session->set_flashdata('success', 'Message has been Successfully sent.');
				redirect($this->data['prev_url']);
		}

	}

	public function send_messages(){
		
		$this->data['page_title'] = 'Users Send Messages';

		if($_POST){
			
			if(isset($_POST['all_users'])){
				$this->data['post_users'] = $this->input->post('all_users[]');
				$this->data['my_cnt'] = 1;
				$this->form_validation->set_rules('all_users[]', 'Users', 'trim|required');	
			}elseif(isset($_POST['message_title'])){
				$this->data['my_cnt'] = 1;
				$this->form_validation->set_rules('all_users[]', 'Users', 'trim|required');	
			}else{
				$this->data['post_users'] = array();
				$this->data['post_users'] = $this->input->post('users');
				$this->data['my_cnt'] = 0;	
				$this->form_validation->set_rules('all_users[]', 'Users', 'trim');		
			}

		}else{
			$this->data['post_users'] = array();
		} 

		$this->data['templates'] =select(TBL_MESSAGES,FALSE,array('where'=>array('is_template'=>'1')));
		$this->data['users'] =select(TBL_USERS,
									 TBL_USERS.'.username,'.TBL_USERS.'.id,'.TBL_ROLES.'.role_name,'.TBL_ROLES.'.id as rid',
									 array(
									 		'where'=>array(TBL_USERS.'.is_delete'=>FALSE),
									 		'where_not_in'=>array(TBL_USERS.'.user_status'=>array('blocked'))
									 	  ),
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

		$this->data['roles'] = select(TBL_ROLES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['schools'] = select(TBL_SCHOOLS,'id, school_name',array('where'=>array('is_delete'=>FALSE)));
		

		$this->form_validation->set_rules('message_title', 'Message Title', 'trim|required|alpha_numeric_spaces');	
		$this->form_validation->set_rules('message_desc', 'Message', 'trim|required');	

		if($this->form_validation->run() == FALSE){

			$this->template->load('admin/default','admin/user/send_messages',$this->data);	
			
		}else{
			
			$all_users = $this->input->post('all_users');

			$msg_title = $this->input->post('message_title');
			$msg_text = $this->input->post('message_desc');

			$db_template = select(TBL_MESSAGES,FALSE,array('where'=>array('is_template'=>'1')));

			 
			$cnt = 0;
			if(isset($_POST['save_template'])){
				foreach($db_template as $db_temp){
					echo $db_temp['message_title'].'<br/>';
					if($db_temp['message_title'] === $msg_title){
						$cnt++;
					}
				}
			}

			if($cnt != 0){
				$this->session->set_flashdata('msgerror', 'Message template should be Unique.');
				redirect('admin/user/send_messages');
			}
			
			$template_counter = 0;	

			if(!empty($all_users)){			

				foreach($all_users as $user){

						if(isset($_POST['save_template']) && $template_counter != 1 ){
							$template_counter = 1;
							$template = '1';
						}else{
							$template = '0';
						}

						$data = array(
								'message_text'=>$msg_text,
								'sender_id'=>$this->session->userdata('id'),
								'message_title'=>$msg_title,
								'status'=>'1',
								'reply_for'=>'0',
								'created_date'=>date('Y-m-d H:i:s',time()),
								'modified_date'=>'0000-00-00 00:00:00',
								'is_template'=>$template,
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

						$user_mail = select(TBL_USERS,'email_id',array('where'=>array('id'=>$user)),array('single'=>TRUE));

						if(!empty($user_mail['email_id'])){
							
							$config = mail_config();
							
							$this->email->initialize($config);
							$this->load->library('email', $config);	
							$this->email->from('admin@admin.com', 'Admin');
							$this->email->to($user_mail['email_id']);
							
							$this->email->subject($msg_title);
							$this->email->message($msg_text);
							
							$this->email->send();
						}
					}

					$this->session->set_flashdata('success', 'Messages has been Successfully sent.');
					redirect($this->data['prev_url']);
				}		
			}
		}


	public function activity($id)
	{
		$data['page_title'] = 'ISM - MY Activities';
		$user_data = select(TBL_USERS,FALSE,array('where'=>array('id'=>$id)),array('single'=>TRUE));
		$user_group_data = select(TBL_TUTORIAL_GROUP_MEMBER,FALSE,array('where'=>array('user_id'=>$id)),array('single'=>TRUE));
		
		$user_group_id = $user_group_data['group_id'];
		$user_id = $id;
		$created_date = $user_data['created_date'];

		/*------ Get current month to user registration month-------*/
		$begin = new DateTime($created_date);
		$end = new DateTime(date('Y-m-d H:i:s'));
		$date_array = array();
		while ($begin <= $end) {
			$date_array[] = $begin->format('Y-m');
			$begin->modify('first day of next month');
		}
		$month = array();

		/*----find current month and if request to view more append one month in descending form---*/
		$month[] = date('m',strtotime(date('Y-m-d')));
		$load_more = $this->input->post('load_more');
		if($load_more != '')
			$month[] = date('m',strtotime($load_more));
		
		/*---------Get topic allocated------*/
		$where = array('where' => array('ga.group_id' => $user_group_id),'where_in' => array('date_format(ga.created_date,"%m")' => $month));
		$option= array('join' =>
					array(
						array(
							'table' => TBL_TOPICS.' t',
							'condition' => 't.id = ga.topic_id'
						)
					)
				);
		$select = 't.topic_name,ga.created_date';
		$data['my_activities']['topic_allcated'] = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' ga',$select,$where,$option);

		/*--------Get my studymates---------*/
		$studymate = studymates($user_id,false);
		$where = array('where_in'=>array('u.id'=>$studymate),'where_in' => array('date_format(sm.created_date,"%m")' => $month));
		$option = array('join'=>
					array(
						array(
							'table' => TBL_STUDYMATES.' sm',
							'condition' => 'u.id = sm.mate_of and sm.mate_id ='.$user_id
						),
						array(
							'table' => TBL_STUDYMATES.' sm2',
							'condition' => 'u.id = sm2.mate_id and sm2.mate_of ='.$user_id
						),
						array(
							'table' => TBL_STUDENT_ACADEMIC_INFO.' in',
							'condition' => 'u.id = in.user_id'
						),
						array(
							'table' => TBL_SCHOOLS.' s',
							'condition' => 's.id = in.school_id'
						),
						array(
							'table' => TBL_USER_PROFILE_PICTURE.' p',
							'condition' => 'u.id = p.user_id'
						)
					),
				);
		$select='u.full_name,sm.mate_of ,sm2.mate_id,if(sm.created_date is null,sm2.created_date,sm.created_date) as created_date,s.school_name,p.profile_link';
		$data['my_activities']['studymates'] = select(TBL_USERS.' u',$select,$where,$option);

		/*--------Get my like feed----------*/
		$where = array('where' => array('like.like_by'=>$user_id),'where_in' => array('date_format(like.created_date,"%m")' => $month));
		$options = array('join' =>
						array(
							array(
								'table' => TBL_FEEDS.' like_feed',
								'condition' => 'like_feed.id = like.feed_id'
							),
							array(
								'table' => TBL_USERS.' upost',
								'condition' => 'upost.id = like_feed.feed_by'
							)
							
						),
						'order_by' => 'like.created_date DESC'
					);
		$select = 'upost.full_name as post_username,like_feed.feed_text,like.created_date,(select count(*) from feed_like where feed_id = like_feed.id) as totlike,(select count(*) from feed_comment where feed_id = like_feed.id) as totcomment';
		$data['my_activities']['like'] = select(TBL_FEED_LIKE.' like',$select,$where,$options);		

		/*-------Get my comment----------*/
		$where = array('where' => array('comment.comment_by'=>$user_id),'where_in' => array('date_format(comment.created_date,"%m")' => $month));
		$options = array('join' =>
						array(
							array(
								'table' => TBL_FEEDS.' comment_feed',
								'condition' => 'comment_feed.id = comment.feed_id'
							),
							array(
								'table' => TBL_USERS.' u',
								'condition' => 'u.id = comment_feed.feed_by'
							),
							array(
								'table' => TBL_USER_PROFILE_PICTURE.' p',
								'condition' => 'p.user_id = u.id'
							)
							
						),
						'order_by' => 'comment.created_date DESC',
						'group_by' => 'comment_feed.id'
					);
		$select = 'u.full_name,u.id,comment_feed.feed_text,p.profile_link,comment.comment,comment.created_date,(select count(*) from feed_like where feed_id = comment_feed.id) as totlike,(select count(*) from feed_comment where feed_id = comment_feed.id) as totcomment,comment_feed.id';
		$data['my_activities']['comment'] = select(TBL_FEED_COMMENT.' comment',$select,$where,$options);

		/*-------Get my post--------*/
		$where = array('where' => array('post.feed_by'=>$user_id),'where_in' => array('date_format(post.created_date,"%m")' => $month));
		$options = array('order_by' => 'post.created_date DESC');
		$select = 'post.feed_text,(select count(*) from feed_like where feed_id = post.id) as totlike,(select count(*) from feed_comment where feed_id = post.id) as totcomment,post.created_date';
		$data['my_activities']['post'] = select(TBL_FEEDS.' post',$select,$where,$options);
		
		$data['my_month'] = $date_array;
		$this->template->load('admin/default','admin/user/my_activities',$data);
	}	
	
	/*
	*	@auther KAMLESH POKIYA
	*	Send invitation 
	*/
	public function send_invitation(){
		$email_id = $this->input->post('email');
		$configs = mail_config();
        $this->load->library('email', $configs);
        $this->email->initialize($configs);
        $this->email->from('','ISM Admin');
        $this->email->to($email_id);
        // $encoded_mail = urlencode($token);
        $msg = '';
        $msg .='<html>';
        $msg .='<head><title></title></head>';
        $msg .='<body style="background-color:#f5f5f5; background: repeating-linear-gradient(90deg, #eee, #fff 8px); color:#333; font-family:Tahoma, Geneva, sans-serif;">
			<table align="center" style="width: 600px;">
		    	<tr>
		        	<td style="text-align:center; padding: 35px 0;"><img alt="ISM" height="70px" src="../images/logo_login_admin.png"></td>
		        </tr>
		        <tr>
		        	<td>
		            	<table style="padding: 15px; width:100%;background-color: #fff;border: 1px solid rgba(0,0,0,0.1);">
		                	<tr>
		                    	<td style="text-align: center;border-bottom: 1px solid rgba(0,0,0,0.1);">
		                        	<h2 style="color: #ff6b6b; margin:10px 0;">Invitation</h2>
		                        </td>
		                    </tr>
		                    <tr>
		                    	<td>
		                        	<p>Hi, <br><br>
		                             &nbsp;&nbsp;If you are looking for an easier  way to manage education for your students then here we are with the best tools that can help you.<br><br>
		                             &nbsp;&nbsp;We are very pleased to introduce ISM which is designed to make learning a great experience. ISM keeps all authors, teachers and student stay connected.</p>
		                        </td>
		                    </tr>
		                    <tr>
		                    	<td>
		                        	<div style="background-color:#ff6b6b; text-align:center; padding: 12px; margin:15px 0; border-radius: 5px;">
		                            	<a href="http://ism/about_us" style="color:#fff; text-decoration:none; font-weight:bold; text-transform:uppercase;">Click Here to Explore ISM</a>
		                            </div>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>
		                            <div>
		                                Thanks, <br>
		                                <span style="color:#ff6b6b;">ISM Admin</span>
		                            </div>
		                        </td>
		                    </tr>
		                </table>
		            </td>
		        </tr>
		    </table>
		</body>';
        $msg .='</html>';
        $this->email->subject('ISM - Invitation');
        $this->email->message($msg);
        $this->email->send();
        $this->email->print_debugger();
        $this->session->set_flashdata('success', 'Your request submitted successfully.');
        redirect('admin/user');
	}
	// ---------------------------- User Module END --------------------------------------------

}

/* End of file User.php */
/* Location: ./application/controllers/Admin/User.php */