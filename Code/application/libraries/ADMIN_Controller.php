<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class ADMIN_Controller extends CI_Controller {

	public function __construct(){
		
		parent::__construct();
		
		$cur_url = trim($this->session->userdata('cur_url'),'/'); 
	 	$prev_url = trim($this->session->userdata('prev_url'),'/'); 
		
		if(empty($cur_url) && empty($prev_url)){
			$this->session->set_userdata( array('cur_url'=>$this->input->server('REQUEST_URI')) );
			$this->session->set_userdata( array('prev_url'=>'test') );
		}

		// If Ajax Call then Current and Previous Urls will not be Set.
		$session_exceptional_urls = array('admin/question/set_question','admin/question/ajax_get_topics_tutorials','ajax_get_states','template_notice',
										  'template_message','check_template_unique','check_template_notice_unique');

		if(!empty($cur_url) && trim($this->input->server('REQUEST_URI'),'/') != $cur_url 
			&& in_array(trim($this->input->server('REQUEST_URI'),'/'),$session_exceptional_urls) == FALSE ){
			
			$this->session->set_userdata( array('cur_url'=> trim($this->input->server('REQUEST_URI'),'/') ) );
			$this->session->set_userdata( array('prev_url'=>$cur_url) );	
		}

		$exceptional_url = array('admin','admin/logout','admin/forgot_password','admin/change','admin/reset_password');

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