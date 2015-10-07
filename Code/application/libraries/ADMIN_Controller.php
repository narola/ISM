<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class ADMIN_Controller extends CI_Controller {

	public function __construct(){
		
		parent::__construct();
		
		$exceptional_url = array('admin','admin/logout');
		
		
			$cur_url = $this->session->userdata('cur_url'); echo "<br/>";
		 	$prev_url = $this->session->userdata('prev_url'); echo "<br/>";
		 	echo $test_url = $cur_url;	

		$test_url = $cur_url;

		if(empty($cur_url) && empty($prev_url)){
			
			$this->session->set_userdata( array('cur_url'=>$this->input->server('REQUEST_URI')) );
			$this->session->set_userdata( array('prev_url'=>'test') );
		}

		p($_SESSION);
		
		if(!empty($cur_url)){

			if( $this->input->server('REQUEST_URI') != $cur_url ){
				
				$this->session->set_userdata( array('cur_url'=>$this->input->server('REQUEST_URI')) );
				$this->session->set_userdata( array('prev_url'=>$cur_url) );	

			}
				
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