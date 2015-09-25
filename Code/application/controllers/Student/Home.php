
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

		// Get latest info of logged in USER.  Becasue logged in user may chaged related data after login.
		$user_group_info = select(
			'tutorial_group_member',
			null,
			array(
				'where' => array(
					'user_id' => $this->session->userdata('user')['id'])
				),
			1
			);

		// No. of users who accept group join request.
		$total_joined = select(
			'tutorial_group_member',
			null,
			array(
				'where' => array(
					'group_id' => $user_group_info['group_id'],
					'joining_status' => 1)),
			array('count' => true)
			);
	
		//  Handle post request to join group
		if($this->input->server('REQUEST_METHOD') == 'POST'){
			update('tutorial_group_member',$this->session->userdata('user')['id'], array('joining_status' => 1));
			
			// If loggedin user accept group and all other members also accepted group then rerirect to "student/home" 
			if(select('tutorial_group_member',null,$where,array('count' => true)) == 5){
					$this->session->set_flashdata('success','Thanks for acceptting!!');
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
		$data['users'] = select('users',
	    		'users.id,users.full_name,schools.school_name, schools.address as school_address, cities.city_name as city_name, countries.country_name as country_name, states.state_name as state_name,user_profile_picture.profile_link as profile_pic',	
			    	array(
					'where' => array(
						'tutorial_group_member.group_id' => $user_group_info['group_id'],
						'users.id !=' => $this->session->userdata('user')['id']
						)
					),
		    		array( 'join' =>  array(
				    			array(
				    				'table' => 'user_profile_picture',
				    				'condition' => 'user_profile_picture.user_id = users.id',
				    				),
				    			array(
				    				'table' => 'tutorial_group_member',
				    				'condition' => 'tutorial_group_member.user_id = users.id',
				    				),
					    		array(
				    				'table' => 'student_academic_info',
				    				'condition' => 'student_academic_info.user_id = tutorial_group_member.user_id',
				    				),
				    			array(
				    				'table' => 'schools',
				    				'condition' => 'student_academic_info.school_id = schools.id',
				    				),
				    			array(
				    				'table' => 'countries',
				    				'condition' => 'countries.id = schools.country_id',
				    				),
					    		array(
				    				'table' => 'cities',
				    				'condition' => 'cities.id = schools.city_id',
				    				),
				    			array(
				    				'table' => 'states',
				    				'condition' => 'states.id = schools.state_id',
				    				)
					    		)
		    				)

		    		);
		$this->load->view('student/group_allocation',$data);
	}
}
