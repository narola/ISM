
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
	
	/*  */
	public function index(){

			$data = array();
			$is_active = false;
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
				TBL_TOPICS.' t',
				't.id,
				t.topic_name,
				t.topic_description,
				t.created_date,
				ts.score,
				ta.group_score',
				array('where' => array('ta.group_id' => $this->session->userdata('user')['group_id'],'ta.week_no' => $c_week,'tm.user_id' => $this->session->userdata('user')['id']  )),
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
						td.message,
						td.message_type,
						td.message_status,
						td.media_link,
						td.media_type,
						td.created_date,
						td.in_active_hours',
						array('where' => array('td.topic_id' => $data['topic']['id'])),
						array('join' => array(
							array(
								'table' => TBL_USERS.' u',
								'condition' => 'td.sender_id = u.id'
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

			// Get information of all group members
			$data['member'] = select(
					TBL_TUTORIAL_GROUP_MEMBER.' tm',
					'u.full_name,sc.school_name,up.profile_link',
					array('where' => array('tm.group_id' => $this->session->userdata('user')['group_id'])),
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
			p($data,true);
	}	


}
