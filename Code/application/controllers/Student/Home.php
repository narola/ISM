
<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Home extends ISM_Controller {

	

	public function __construct()
	{
	    parent::__construct();
	}

	public function index()
	{
		$data['title'] = 'ISM Login';
		$this->template->load('student/default','student/home_view',$data);
	}


	/**
	* This function will used to load group_allocation page and confirm join group.
	* Author - Sandip Gopani (SAG)
	*/
	public function group_allocation(){
		$data = array(); 
		// p($this->session->userdata('user'));
		// Get latest info of logged in USER.  Becasue logged in user may chaged related data after login.
		$user_group_info = select(
			TBL_TUTORIAL_GROUP_MEMBER,
			null,
			array(
				'where' => array(
					'user_id' => $this->session->userdata('user')['id'])
				),
			1
			);

		// No. of users who accept group join request.
		$total_joined = select(
			TBL_TUTORIAL_GROUP_MEMBER,
			null,
			array(
				'where' => array(
					'group_id' => $user_group_info['group_id'],
					'joining_status' => 1)),
			array('count' => true)
			);
	
		//  Handle post request to join group
		if($this->input->server('REQUEST_METHOD') == 'POST'){
			update(TBL_TUTORIAL_GROUP_MEMBER,array('user_id'=>$this->session->userdata('user')['id']), array('joining_status' => 1));
			// If loggedin user accept group and all other members also accepted group then rerirect to "student/home" 
			if(select(TBL_TUTORIAL_GROUP_MEMBER,null,$where,array('count' => true)) == 5){
					$this->session->set_flashdata('success','Thanks for acceptting!!');
					$websocket_id = str_shuffle('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz').time();
					update(TBL_USRS,array('id'=>$this->session->userdata('user')['id']), array('websocket_id' => $websocket_id));
                    //generate websocket cookie
					redirect('student/home');
			}else{
				$this->session->set_flashdata('error','All group member must accept group to go ahead!!');
				redirect('student/group_allocation');
			}
			
		}

		//  Redirect User to /home. if all members of group acceptted join request.
		if($user_group_info['joining_status'] == 1  &&  $total_joined == 5){
			redirect('student/home');
		
		}else if($user_group_info['joining_status'] != 1){   // if loggedin user not accepted group
			$this->session->set_flashdata('error','You must accept group to go ahead!!');
		}else if($total_joined <= 4 && $user_group_info['joining_status'] == 1){  // if loggedin user accepted group but not all other member.
			$this->session->set_flashdata('error','All group member must accept group to go ahead!!');
		
		}

		// Disable accept group button if current user acceptted join group request but not all other members.
		if($user_group_info['joining_status'] == 1){
			$data['disable_accept_button'] = true;	
		}

		// Get information of all group members excluding loggedin USER
		$data['users'] = select(TBL_USERS.' u',
	    		'u.id,u.full_name,s.school_name, s.address as school_address, ct.city_name as city_name, cu.country_name as country_name, st.state_name as state_name,up.profile_link as profile_pic',	
			    	array(
					'where' => array(
						'tm.group_id' => $user_group_info['group_id'],
						'u.id !=' => $this->session->userdata('user')['id']
						)
					),
		    		array( 'join' =>  array(
				    			array(
				    				'table' => TBL_USER_PROFILE_PICTURE.' up',
				    				'condition' => 'up.user_id = u.id',
				    				),
				    			array(
				    				'table' => TBL_TUTORIAL_GROUP_MEMBER.' tm',
				    				'condition' => 'tm.user_id = u.id',
				    				),
					    		array(
				    				'table' => TBL_STUDENT_ACADEMIC_INFO.' si',
				    				'condition' => 'si.user_id = tm.user_id',
				    				),
				    			array(
				    				'table' => TBL_SCHOOLS.' s',
				    				'condition' => 'si.school_id = s.id',
				    				),
				    			array(
				    				'table' => TBL_COUNTRIES.' cu',
				    				'condition' => 'cu.id = s.country_id',
				    				),
					    		array(
				    				'table' => TBL_CITIES.' ct',
				    				'condition' => 'ct.id = s.city_id',
				    				),
				    			array(
				    				'table' => TBL_STATES.' st',
				    				'condition' => 'st.id = s.state_id',
				    				)
					    		)
		    				)

		    		);
		$this->load->view('student/group_allocation',$data);
	}
}
