
<?php
defined('BASEPATH') OR exit('No direct script access allowed');


/**
*	Handle group discussion
*   @Author = Sandip Gopani (SAG)
*/
class Tutorial extends ISM_Controller {
	
	public function __construct()
	{
	    parent::__construct();
	}
	

	public function index(){
			
			$data = array();
			$data['weekday'] = array('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday');
			$data['user_id'] = $user_id = $this->session->userdata('user')['id'];
			$is_active = false;
			//$data['title'] = 'ISM - Group Tutorial';
			$data['active_comment'] = 0;
			$data['disable_group_chatting'] = false;

			// Store remaining reconds of active time or 0 if inactive hours.
			$data['time'] = active_hours();
			if($data['time'] > 0){
				$is_active = true;
			}

			// Get Current week no.
			$c_week = ceil(getdate()['yday']/7);
			if($c_week > 3){
				$data['disable_group_chatting'] = true;
			}

			// Get current topic data from DB
			$data['topic']  = select(
				TBL_TUTORIAL_TOPIC.' t',
				't.id,
				t.topic_name,
				t.topic_description,
				t.created_date,
				ts.score as my_score,
				ta.group_score,
				up.profile_link,
				u.full_name',
				array('where' => array('ta.group_id' => $this->session->userdata('user')['group_id'],'ta.week_no' => $c_week,'tm.user_id' => $user_id  )),
				array('join' => array(
					array(
						'table' => TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' ta',
						'condition' => 'ta.topic_id = t.id'
						),
					array(
						'table' => TBL_TUTORIAL_GROUP_MEMBER.' tm',
						'condition' => 'tm.group_id = ta.group_id'
						),
					array(
						'table' => TBL_TUTORIAL_GROUP_MEMBER_SCORE.' ts',
						'condition' => 'ts.member_id = tm.id'
						),
					array(
						'table' => TBL_USERS.' u',
						'condition' => 'u.id = t.created_by'
						),
					array(
						'table' => TBL_USER_PROFILE_PICTURE.' up',
						'condition' => 'up.user_id = u.id'
						)
					),
				'single' => true
				)
			);
			// Check topic found or not.
			if(isset($data['topic']) && !empty($data['topic'])){
				// Get all discussion messages.
				$data['discussion'] = select(
						TBL_TUTORIAL_GROUP_DISCUSSION.' td',
						'u.full_name,
						td.id,
						td.message,
						td.message_type,
						td.message_status,
						td.media_link,
						td.media_type,
						up.profile_link,
						td.sender_id,
						td.created_date,
						td.in_active_hours',
						array('where' => array('td.topic_id' => $data['topic']['id'])),
						array('join' => array(
							array(
								'table' => TBL_USERS.' u',
								'condition' => 'td.sender_id = u.id'
								),
							array(
								'table' => TBL_USER_PROFILE_PICTURE. ' up',
								'condition' => 'up.user_id = u.id'
								)
							)
						)
					);
				
				// Set WeekDay
				if(isset($data['discussion']) && !empty($data['discussion'])){
					foreach($data['discussion'] as $key => $value){

						// Count active comments.
						if($value['in_active_hours'] == 1){
							$data['active_comment']++;
						}

						$data['discussion'][$key]['week_day'] = date("w", strtotime($value['created_date']));
					}
				}

			}
			$data['online'] = online();
			// Get information of all group members
			$data['member'] = select(
					TBL_TUTORIAL_GROUP_MEMBER.' tm',
					'u.id, u.full_name,sc.school_name,up.profile_link',
					array('where' => array('tm.group_id' => $this->session->userdata('user')['group_id'],'u.id !=' => $this->session->userdata('user')['id'] )),
					array('join' => array(
							array(
							'table' => TBL_USERS. ' u',
							'condition' => 'u.id = tm.user_id'
							),
							array(
							'table' => TBL_STUDENT_ACADEMIC_INFO. ' tai',
							'condition' => 'u.id = tai.user_id'
							),
							array(
							'table' => TBL_SCHOOLS. ' sc',
							'condition' => 'sc.id = tai.school_id'
							),
							array(
							'table' => TBL_USER_PROFILE_PICTURE. ' up',
							'condition' => 'up.user_id = u.id'
							)
						)
					)
				);
		//	p($data,true);
			$this->template->load('student/default','student/tutorial_group',$data);
	}	


}
