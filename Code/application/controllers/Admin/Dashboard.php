<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Dashboard extends ISM_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
			
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

		$loggedin = is_loggedin();

		if($loggedin == TRUE){
			redirect('admin/dashboard');
		}

		$this->form_validation->set_rules('username', 'Email / User Name', 'trim|required');
		$this->form_validation->set_rules('password', 'Password', 'trim|required');	

		if($this->form_validation->run() == FALSE){

			$this->load->view('admin/login');

		}else{

			$username = $this->input->post('username');
			$password = $this->input->post('password');

			if(filter_var($username, FILTER_VALIDATE_EMAIL)) {

		        $fetch_data = $this->common_model->sql_select('users',FALSE,array('email_id'=>$username),array('single'=>TRUE));
		    }
		    else {
				$fetch_data = $this->common_model->sql_select('users',FALSE,array('username'=>$username),array('single'=>TRUE));
		    }

			if(!empty($fetch_data)){

				$db_pass = $this->encrypt->decode($fetch_data['password']);
				/* Password match And Where role_id = 5 which is for admin and is_delete = False or 0 */

			    if($db_pass === $password && $fetch_data['is_delete']==0 ){


			    	$role_data = $this->common_model->sql_select('roles',FALSE,array('id'=>$fetch_data['role_id']),array('single'=>TRUE));	

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
						'loggedin' =>TRUE
					);
					
					$this->session->set_userdata( $array );

					redirect('admin/dashboard');

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
	 *  function view_school(), add_school() ,update_school(), delete_school() 
	 *	Table name - schools 
	 * @return Array or Integer
	 * @author Virendra patel Sparks ID - VPA
	 **/


	 
	/**
	 * function ajax_get_states and ajax_get_cities used to fetch  
	 * states and city dynamically as per AJAX request	
	 *
	 * @return String
	 * @author Virendra patel ( Sparks Id-VPA )
	 **/
		
	
	public function logout(){

		$this->session->sess_destroy();
		delete_cookie('Remember_me');
		redirect('admin');
	}

}

/* End of file Admin.php */
/* Location: ./application/controllers/Admin.php */