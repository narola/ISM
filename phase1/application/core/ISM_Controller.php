<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class ISM_Controller extends CI_Controller {

	var $active_h = array();
	var $notification_list = array();
	var $noti_cnt;

	public function __construct(){
		
		parent::__construct();
		
		$this->load->model(array('Common_model'));	
		$this->active_h = active_hours();
		$this->notification_list = notification_list($this->session->userdata('user')['id']);
		//p($this->notification_list, true);
		$this->noti_cnt = count_notification_list($this->session->userdata('user')['id']);
		$exceptional_url = array('student/logout','student/forgot_password','student/reset_password',
								'student/group_allocation');
		$tutorial_exceptional_url = array('student/tutorial','student/exam-instruction');
		
		if(in_array(uri_string(), $exceptional_url) == FALSE && is_loggedin() == FALSE){
				
				if(is_loggedin_admin() == TRUE){
					redirect('admin/user');
				}

			  redirect('login');

		} else{
			$data['error'] = 'Chat and topic exam are disabled! Because your group is blocked by admin!';
                    $data['redirect'] = '/student/home';
			$role = $this->session->userdata('role');
			$group_id   =   $this->session->userdata('user')['group_id']; 
					$is_completed = select(TBL_TUTORIAL_GROUPS,'is_completed',
	            		array('where'=>array('id'=>$group_id)),array('single'=>TRUE));
					$this->session->set_userdata('is_completed',$is_completed['is_completed']);
					
			if($role != 'admin'){
				if(in_array(uri_string(), $tutorial_exceptional_url) == true){
					// /$group_id   =   $this->session->userdata('user')['group_id']; 
	            	$count_member = select(TBL_TUTORIAL_GROUP_MEMBER,null,
	            		array('where'=>array('group_id'=>$group_id,'joining_status'=>'1')),array('count'=>TRUE));
	            	
	            	if($count_member != 5){
	            		$this->session->set_flashdata('error','All group member must accept group access tutorial!!');
	            		redirect('student/home');
	                }
        		}
			}
		}

		/* set user session again */
		if($this->session->userdata('user')){
	     set_session($this->session->userdata('user')['id']);
		}

		// get_highscore($this->session->userdata('user')['id'],$this->session->userdata('user')['classroom_id']);	
	}

	


}

/* End of file ISM_Controller.php */
/* Location: ./application/core/ISM_Controller.php */