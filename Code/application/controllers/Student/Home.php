

<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Home extends ISM_Controller {

	

	public function __construct()
	{
	    parent::__construct();
	}

	public function index()
	{

		$user_id = $this->session->userdata('user')['id'];
		$data['title'] = 'ISM - Home';		
		
		// Get Post feed with comment 
		$options =	array(
						'join'	=>	array(
							array(
								'table' => TBL_USERS.' u',
								'condition' => 'u.id = f.feed_by'
							),
							array(
								'table'=>TBL_USER_PROFILE_PICTURE.' p',
								'condition' => 'u.id = p.user_id'	
							)
						),
						'limit'=>4,
						'offset'=>0,
						'order_by' => 'f.id DESC'

					);  

		$where = array('where'=>array('f.is_delete'=> 0),'where_in'=>array('f.feed_by'=>studymates($user_id)));
		$result_feed = select(TBL_FEEDS.' f','f.id as fid,f.feed_by,f.feed_text,f.posted_on,u.full_name,(select count(*) from feed_comment where feed_id = f.id and is_delete = 0) as tot_comment,(select count(*) from feed_like where feed_id = f.id and is_delete = 0) as tot_like,p.profile_link',$where,$options);
		
		//---find feeds
		$feed_ids = array();
		foreach ($result_feed as $key => $value) {
			$feed_ids[] = $value['fid'];
			$data_array[$key] = $value;
		}	
		
		//---find feeds commentss
		$options = array(
				'join' => array(
					array(
						'table' => TBL_USERS.' u', 
						'condition'=>'u.id = fc.comment_by'
					),
					array(
							'table'=>TBL_USER_PROFILE_PICTURE.' p',
							'condition' => 'u.id = p.user_id'	
					)
				)
			);	
		
		$where 	= array('where'=>array('fc.is_delete'=> 0),'where_in'=> array('feed_id'=>$feed_ids));
		$comment = select(TBL_FEED_COMMENT.' fc','feed_id,comment,u.full_name,p.profile_link',$where,$options);
		
		//----merge feeds and comment in single array			
		$final_feed = array();
		foreach ($data_array as $key => $value) {
			$final_feed[$key] = $value;
			$found_comment = array();
			foreach ($comment as $key1 => $value1) {
				if($value1['feed_id'] == $value['fid'])
                {
                    $found_comment[] = $value1;
                } 
			}
			$final_feed[$key]['comment'] = $found_comment;
		}
		
		$data['feed'] = $final_feed;

 		// Get Classmates details
		$where = array('where_in' => array('u.id' =>  studymates($user_id,false)));
		$options = array('join' => array(
				array(
					'table' => TBL_USER_PROFILE_PICTURE.' upp',
					'condition' => 'upp.user_id = u.id'
				)
			),
		);
		$data['classmates'] = select(TBL_USERS.' u', 'u.id,u.full_name,upp.profile_link,  (SELECT count(*) FROM `user_chat` `uc` WHERE `uc`.`sender_id` = `u`.`id` AND `uc`.`receiver_id` = '.$user_id.' AND `uc`.`received_status` = 0) as `unread_msg`',$where,$options);

		/* Get all online users */
		$all_online = rtrim(get_cookie('status'),"-");
        $all_online = ltrim($all_online,"-");
        $data['online'] = (Array)explode('-', $all_online);

		/* Get user id of active chat window */
		$active_chat_id = get_cookie('active');
		if(!empty($active_chat_id)){
			$options = array('join' => array(
				array(
					'table' => TBL_USER_PROFILE_PICTURE.' upp',
					'condition' => 'upp.user_id = u.id'
					)
				),
			'single' => true
			);
			$data['active_chat']['user'] = select(
				TBL_USERS.' u',
				'u.id, u.full_name,upp.profile_link',
				array('where' => array('u.id' => $active_chat_id)),
				$options
				);
			$data['active_chat']['comment'] = array_reverse (select(
				TBL_USER_CHAT. ' uc',
				'uc.id,uc.sender_id,uc.receiver_id,uc.message',
				"(uc.sender_id = $active_chat_id AND uc.receiver_id = $user_id) OR (uc.sender_id = $user_id AND uc.receiver_id = $active_chat_id) ",
				array('limit' => 10,'order_by' => 'uc.id DESC')));
		}
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
