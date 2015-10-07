<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class ADMIN_Controller extends CI_Controller {

	public function __construct(){
		
		parent::__construct();
		
		$this->load->model(array('common_model'));	
		
		$exceptional_url = array('admin','admin/logout');
		
		
		$cur_url = $this->session->userdata('cur_url');
		$prev_url = $this->session->userdata('prev_url');

		if(empty($cur_url) && empty($prev_url)){
			
			$this->session->set_userdata( array('cur_url'=>$this->input->server('REQUEST_URI')) );
			$this->session->set_userdata( array('prev_url'=>'test') );
		}
		
		if(!empty($cur_url)){

			$this->session->set_userdata( array('cur_url'=>$this->input->server('REQUEST_URI')) );
			$this->session->set_userdata( array('prev_url'=>$cur_url) );	
		}

		if(in_array(uri_string(), $exceptional_url) == FALSE && is_loggedin_admin() == FALSE){

			if(is_loggedin() == TRUE){
				redirect('login');
			}
		
			redirect('admin');
		} 

	} // END of Construct
}

/* End of file ADMIN_Controller.php */
/* Location: ./application/libraries/ADMIN_Controller.php */