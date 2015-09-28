<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Dashboard extends ISM_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		
		$this->load->helper('csv');	
		$this->load->library(array('form_validation','encrypt'));
		$this->load->model(array('common_model'));
	}


	public function index()
	{
		$remember_me = get_cookie('Remember_me');  

		/* 	If Remember_key Cookie exists in browser then it wil fetch data using it's value and 
			set sessin data and force login student  */

		if(isset($remember_me)){

			$remember_me_decode = $this->encrypt->decode($remember_me);

			$rem_data = $this->common_model->sql_select('users',FALSE,array('where'=>array('id'=>$remember_me)),array('single'=>TRUE));	

			$array = array(
				'id'=>$rem_data['id'],
				'loggedin'=>TRUE
			);
			
			$this->session->set_userdata( $array );
		}

		$loggedin = is_loggedin();  /* is_logginin() in cms_helper.php It will Check Admin is loggen or not. */

		if($loggedin == TRUE){
			redirect('admin/user');
		}

		$this->form_validation->set_rules('username', 'Email / User Name', 'trim|required');
		$this->form_validation->set_rules('password', 'Password', 'trim|required');	

		if($this->form_validation->run() == FALSE){

			$this->load->view('admin/login');

		}else{

			$username = $this->input->post('username');
			$password = $this->input->post('password');

			if(filter_var($username, FILTER_VALIDATE_EMAIL)) {
				$fetch_data = $this->common_model->sql_select('users',FALSE,array('where'=>array('email_id'=>$username)),array('single'=>TRUE));

		    }else {
				$fetch_data = $this->common_model->sql_select('users',FALSE,array('where'=>array('username'=>$username)),array('single'=>TRUE));
		    }

			if(!empty($fetch_data)){

				$db_pass = $this->encrypt->decode($fetch_data['password']);

			    if($db_pass == $password && $fetch_data['is_delete']==0 && $fetch_data['role_id'] == 1 ){

					$role_data = $this->common_model->sql_select('roles',FALSE,array('where'=>array('id'=>$fetch_data['role_id']))
						,array('single'=>TRUE));	

			    	/* If remember Me Checkbox is clicked */
					/* Set Cookie IF Start */
					if(isset($_POST['remember'])){

						$cookie = array(
						    'name'   =>'Remember_me' ,
						    'value'  => $this->encrypt->encode($fetch_data['id']),
						    'expire' => '86500'
						);

						$this->input->set_cookie($cookie);
					}   /* Set Cookie IF END */

					$array = array(
						'id' => $fetch_data['id'],
						'role' => $role_data['role_name'],
						'username'=>$fetch_data['username'],
						'email_id'=>$fetch_data['email_id'],
						'loggedin' =>TRUE
					);

					$this->session->set_userdata( $array ); // Set Session for Admin

					redirect('admin/user');  

			    }else{
			    	$this->session->set_flashdata('error', 'Invalid Username or Password.');		
					redirect('admin');	
			    }

			}else{
				$this->session->set_flashdata('error', 'Invalid Username or Password.');		
				redirect('admin');	
			}	
		}	
	}

	public function dashboard(){

		$this->load->view('admin/dashboard');

	}

	/**
	 * function auto_generated_credentials() will generate auto credentials for school.
	 *
	 * @author Virendra patel Sparks ID - VPA
	 **/

	public  function auto_generated_credentials(){

		$this->data['schools']	=	$this->common_model->sql_select(TBL_SCHOOLS);
		$this->data['roles'] = $this->common_model->sql_select(TBL_ROLES);
		$this->data['courses'] = $this->common_model->sql_select(TBL_COURSES);
		$this->data['classrooms'] = $this->common_model->sql_select(TBL_CLASSROOMS);

		$this->form_validation->set_rules('school_id', 'School Name', 'trim|required');
		$this->form_validation->set_rules('role_id', 'Role', 'trim|required');
		$this->form_validation->set_rules('course_id', 'Course', 'trim|required');
		$this->form_validation->set_rules('no_of_credentials', 'No of credentials', 'trim|required|integer|greater_than[0]');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim|required');

		if($this->form_validation->run() == FALSE){
			//$this->load->view('admin/student_credentials_dashboard',$data);
			$this->template->load('admin/default','admin/generated_credentials',$this->data);
		}else{

			$school_id = $this->input->post('school_id');
			$role_id = $this->input->post('role_id');
			$course_id = $this->input->post('course_id');
			$classroom_id = $this->input->post('classroom_id');
			$no_of_credentials	=	$this->input->post('no_of_credentials',TRUE);

			for ($i=0; $i < $no_of_credentials; $i++) { 

				$usrename_str 	= 	'ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
				$username 		=	substr(str_shuffle($usrename_str),0,10);
				$password_str 	=	'abcdefghijklmnopqrstuvwxyz1234567890';	 
				$password 		=	$this->encrypt->encode(substr(str_shuffle($password_str),0,8));
				
				$data = array('username'=>$username);
				
				$find_credentials = $this->common_model->sql_select(TBL_AUTO_GENERATED_CREDENTIAL,FALSE,array('where'=>$data));

				$find_user = $this->common_model->sql_select(TBL_USERS,FALSE,array('where'=>$data));

				if(sizeof($find_credentials)>0 || sizeof($find_user) > 0){

					$i--;
					continue;

				}else{

					$data = array(
							'username'=>$username,
							'password'=>$password,
							'school_id'=>$school_id,
							'role_id'=>$role_id,
							'status'=>'1',
							'created_date'=>date('Y-m-d H:i:s',time()),
							'modified_date'=>'0000-00-00 00:00:00',
							'classroom_id'=> $classroom_id,
							'course_id'=> $course_id,
							'academic_year'=>'',
							'is_delete'=>FALSE,
							'is_testdata'=>'yes'
						);

					$this->common_model->insert(TBL_AUTO_GENERATED_CREDENTIAL,$data);	
 					
 				}
			}

			
		}

	}
	
	/**
	 * function logout will clear All Session Data and Delete Remember_me Coookie.
	 * @author Virendra patel Sparks ID- VPA
	 **/

	public function logout(){

		$this->session->sess_destroy();
		delete_cookie('Remember_me');
		redirect('admin');
	}

}

/* End of file Admin.php */
/* Location: ./application/controllers/Admin.php */