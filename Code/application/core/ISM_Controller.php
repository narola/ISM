<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class ISM_Controller extends CI_Controller {

	public function __construct(){
		
		parent::__construct();
		$this->load->model(array('common_model'));	
		$exceptional_url = array('admin','admin/logout','student/logout','student/forgot_password','student/reset_password');
		

		if(in_array(uri_string(), $exceptional_url) == FALSE && is_loggedin() == FALSE){
				redirect('login');
		}

		 // $this->output->enable_profiler(TRUE);
	}

	


}

/* End of file ISM_Controller.php */
/* Location: ./application/core/ISM_Controller.php */