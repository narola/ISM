<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class ISM_Controller extends CI_Controller {

	public function __construct(){
		
		parent::__construct();
		
		$this->load->model(array('common_model'));	
		
		$exceptional_url = array('student/logout','student/forgot_password','student/reset_password',
								'student/group_allocation');
		
		if(in_array(uri_string(), $exceptional_url) == FALSE && is_loggedin() == FALSE){
				
				if(is_loggedin_admin() == TRUE){
					redirect('admin/user');
				}

			  redirect('login');

		} else{

			$role = $this->session->userdata('role');

			if($role != 'admin'){
				if(in_array(uri_string(), $exceptional_url) == FALSE){
					$group_id   =   $this->session->userdata('user')['group_id']; 
	            	$count_member = select(TBL_TUTORIAL_GROUP_MEMBER,null,
	            		array('where'=>array('group_id'=>$group_id,'joining_status'=>'1')),array('count'=>TRUE));
	            	if($count_member != 5)
	                	redirect('login/welcome');
        		}
			}
			
		}

		 // $this->output->enable_profiler(TRUE);
	}

	


}

/* End of file ISM_Controller.php */
/* Location: ./application/core/ISM_Controller.php */