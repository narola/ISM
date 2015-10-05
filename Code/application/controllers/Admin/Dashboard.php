<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * class Dashboard extends ADMIN_Controller which is in application/libraries Folder
 * ADMIN_Controller is extend in config.php using __autoload megic method		 
 *
 * @author Virendra Patel - Spark ID- VPA
 **/

class Dashboard extends ADMIN_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		$this->load->helper(array('csv','file','download'));	
	}

	public function index()
	{
		$remember_me = get_cookie('Remember_me');  

		/* 	If Remember_key Cookie exists in browser then it wil fetch data using it's value and 
			set sessin data and force login student  */

		if(isset($remember_me)){

			$remember_me_decode = $this->encrypt->decode($remember_me);

			$rem_data = select(TBL_USERS,FALSE,array('where'=>array('id'=>$remember_me)),array('single'=>TRUE));	

			$array = array(
				'id'=>$rem_data['id'],
				'loggedin_admin'=>TRUE
			);
			
			$this->session->set_userdata( $array );
		}

		$loggedin = is_loggedin();
		$loggedin_admin = is_loggedin_admin();  /* is_logginin() in cms_helper.php It will Check Admin is loggen or not. */

		if($loggedin == FALSE && $loggedin_admin == TRUE){
			redirect('admin/user');
		}elseif($loggedin == TRUE){
			redirect('login');
		}	

		$this->form_validation->set_rules('username', 'Email / User Name', 'trim|required');
		$this->form_validation->set_rules('password', 'Password', 'trim|required');	

		if($this->form_validation->run() == FALSE){

			$this->load->view('admin/login');

		}else{

			$username = $this->input->post('username');
			$password = $this->input->post('password');

			if(filter_var($username, FILTER_VALIDATE_EMAIL)) {
				$fetch_data = select(TBL_USERS,FALSE,array('where'=>array('email_id'=>$username)),array('single'=>TRUE));

		    }else {
				$fetch_data = select(TBL_USERS,FALSE,array('where'=>array('username'=>$username)),array('single'=>TRUE));
		    }

			if(!empty($fetch_data)){

				$db_pass = $this->encrypt->decode($fetch_data['password']);

			    if($db_pass == $password && $fetch_data['is_delete']==0 && $fetch_data['role_id'] == 1 ){

					$role_data = select(TBL_ROLES,FALSE,array('where'=>array('id'=>$fetch_data['role_id']))
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
						'loggedin_admin' =>TRUE
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
		echo "Admin Dashboard";
	}

	/**
	 * function auto_generated_credentials() will generate credentials for school.
	 *
	 * @author Virendra patel Sparks ID - VPA
	 **/

	public  function auto_generated_credentials(){
		
		$this->data['page_title'] = 'Auto Generated Credentials';

		$this->data['schools'] = select(TBL_SCHOOLS);
		$this->data['roles'] = select(TBL_ROLES);
		$this->data['courses'] = select(TBL_COURSES);
		$this->data['classrooms'] = select(TBL_CLASSROOMS);

		$this->data['cur_year'] = date('Y');
		$this->data['next_year'] = date('Y')+1;
		
		//set Codeigniter Form Validation using form_validation Class
		$this->form_validation->set_rules('school_id', 'School Name', 'trim|required|integer');
		$this->form_validation->set_rules('role_id', 'Role', 'trim|required|integer');
		$this->form_validation->set_rules('course_id', 'Course', 'trim|required|integer');
		$this->form_validation->set_rules('no_of_credentials', 'No of credentials', 'trim|required|integer|greater_than[0]');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim|required|integer');

		// if from_validation set rules are false or if data not posted then it will set to FALSE
		if($this->form_validation->run() == FALSE){
			$this->template->load('admin/default','admin/generated_credentials',$this->data);
		}else{

			//ELSE if data posted by user and all set validation are TRUE
			$school_id = $this->input->post('school_id');
			$role_id = $this->input->post('role_id');
			$course_id = $this->input->post('course_id');
			$classroom_id = $this->input->post('classroom_id');
			$no_of_credentials	=	$this->input->post('no_of_credentials',TRUE);

			// Fetch role name,course name,classroom name,school name from diffetent table using cms_helper.php and common_model.php
			$role_name = select(TBL_ROLES,'role_name',array('where'=>array('id'=>$role_id)),array('single'=>TRUE));
			$course_name = select(TBL_COURSES,'course_name',array('where'=>array('id'=>$course_id)),array('single'=>TRUE));
			$class_name = select(TBL_CLASSROOMS,'class_name',array('where'=>array('id'=>$classroom_id)),array('single'=>TRUE));
			$school_name = select(TBL_SCHOOLS,'school_name',array('where'=>array('id'=>$school_id)),array('single'=>TRUE));

			//No of Credentials loop will run if that username does not exist in users.username table.field
			for ($i=0; $i < $no_of_credentials; $i++) { 

				$usrename_str 	= 	'ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
				$username 		=	substr(str_shuffle($usrename_str),0,10);
				$password_str 	=	'abcdefghijklmnopqrstuvwxyz1234567890';	 
				$password 		=	$this->encrypt->encode(substr(str_shuffle($password_str),0,8));
				
				$data = array('username'=>$username);
				
				$find_credentials = select(TBL_AUTO_GENERATED_CREDENTIAL,FALSE,array('where'=>$data));

				$find_user = select(TBL_USERS,FALSE,array('where'=>$data));

				if(sizeof($find_credentials)>0 || sizeof($find_user) > 0){
					//if data found in users or auto_generated_credentials table then it will decrease $i value and continue 
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
							'is_my_school'=>FALSE,
							'is_testdata'=>'yes'
						);

					insert(TBL_AUTO_GENERATED_CREDENTIAL,$data); // insert data into database using common_model.php and cms_helper.php
 				}
			} // End Of For Loop	

			// find no of entries whose school,role,course and classroom are same 
			$count_query= select(TBL_AUTO_GENERATED_CREDENTIAL,'username,password',array('where'=>array('school_id'=>$school_id,
																'role_id'=>$role_id,'classroom_id'=> $classroom_id,'course_id'=> $course_id)),array('count'=>TRUE));

			//If data exists for same school,course,classroom,and role if not exits then IF Cond.. otherwise ELSE Cond..
			if($count_query == $no_of_credentials){
				$csv_query = $this->db->query("SELECT username,password FROM ".TBL_AUTO_GENERATED_CREDENTIAL." WHERE school_id='$school_id' AND 
										role_id='$role_id' AND classroom_id='$classroom_id' AND course_id='$course_id' ");
			}else{
				
				$offset = $count_query - $no_of_credentials; 
				$limit =  $no_of_credentials; 

				$csv_query = $this->db->query("SELECT username,password FROM ".TBL_AUTO_GENERATED_CREDENTIAL." WHERE school_id='$school_id' AND 
										role_id='$role_id' AND classroom_id='$classroom_id' AND course_id='$course_id' LIMIT $offset,$limit ");
			}

			$data =  csv_from_results($csv_query);  // function in cms_helper.php used to generate only text accordingly comma saperated value
			$path = $_SERVER['DOCUMENT_ROOT'].'/csv/myfile.csv';

			if ( !write_file($path, $data) ){
			  	show_404();
			}

			$data = file_get_contents($path);
			force_download($school_name['school_name'].'.csv',$data);
			
			redirect('admin/auto_generated_credentials');

		} // End else consdition
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