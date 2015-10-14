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
			$data['timing'] = active_hours();
			$data['hide_right_bar'] = true;	
			$data['weekday'] = array('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday');
			$data['user_id'] = $user_id = $this->session->userdata('user')['id'];
			$is_active = false;
			$data['title'] = 'ISM - Group Tutorial';
			$data['active_comment'] = 0;
			$data['menu'] = 'week';

			// Store remaining reconds of active time or 0 if inactive hours.
			$data['time'] = $data['timing']['time_to_left'];
			if($data['time'] > 0){
				$is_active = true;
			}

			// Get Current week no.
			$data['current_weekday'] = getdate()['wday'];
			if($data['current_weekday'] >= 4){
				//redirect('student/exam');
				exit;
			}
			$date = new DateTime(date("Y-m-d H:i:s"));
			$c_week = $date->format("W");
			$year = date("Y");

			// Get current topic data from DB
			$data['topic']  = select(
				TBL_TUTORIAL_TOPIC.' t',
				't.id,
				t.topic_name,
				t.topic_description,
				t.created_date,
				ts.score as my_score,
				(SELECT SUM(group_score) FROM '.TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' WHERE group_id = tm.group_id) as group_score,
				up.profile_link,
				u.full_name',
				array('where' => array('ta.group_id' => $this->session->userdata('user')['group_id'],'ta.week_no' => $c_week,'tm.user_id' => $user_id ,'YEAR(`ta`.`created_date`)' => $year )),
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
			 //p($data,true);
			$this->template->load('student/default','student/tutorial_group',$data);
	}
		
	/**
	*	Attempt exam.
	*   @autor Sandip Gopani
	*/
	public function exam(){
		$data['menu'] = 'week';
		$data['current_weekday'] = getdate()['wday'];
		$data['user_id'] = $user_id = $this->session->userdata('user')['id'];
		$data['weekday'] = array('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday');
		$data['user_id'] = $user_id = $this->session->userdata('user')['id'];
	    $data['title'] = 'ISM - Exam';
	    $data['exam_status'] = 0;    // 0 = Not statred, 1 = started, 2 = finished
	    $data['current_weekday'] = getdate()['wday'];
	    $data['error'] = null;
		$date = new DateTime(date("Y-m-d H:i:s"));
		$c_week = $date->format("W");
		$year = date("Y");

		// Check week day is thursday, friday or saturday
		if($data['current_weekday'] <= 3){
			//redirect('student/tutorial');
		}

		// Check topic and exam allocated for current week and get exam info.
	    $data['exam']  = select(
				TBL_TUTORIAL_TOPIC.' t',
				'ta.topic_id,
				t.topic_name,
				t.topic_description,
				t.created_date,
				te.exam_id,
				ss.created_date',
				array('where' => array('ta.group_id' => $this->session->userdata('user')['group_id'],'ta.week_no' => $c_week,'tm.user_id' => $user_id,'YEAR(`ta`.`created_date`)' => $year  )),
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
						'table' => TBL_TUTORIAL_TOPIC_EXAM.' te',
						'condition' => 'te.tutorial_topic_id = ta.topic_id'
						),
					array(
						'table' => TBL_STUDENT_EXAM_SCORE. ' ss',
						'condition' => 'ss.exam_id = te.exam_id AND ss.user_id = '.$user_id
						)
					),
				'single' => true
				)
			); 
	     $data['exam']['created_date'] = '2015-10-14'; 
	    $current_date = DateTime::createFromFormat('Y-m-d', date('Y-m-d'));
	    $current_time = DateTime::createFromFormat('Y-m-d', date('Y-m-d H:i:s'));
	    if(isset($data['exam']) && !empty($data['exam'])){
	    	if(isset($data['exam']['created_date'])){
	    		if($data['exam']['created_date'] != '' && $data['exam']['created_date'] != null){
	    			$exam_start_date = DateTime::createFromFormat('Y-m-d', date('Y-m-d',  strtotime($data['exam']['created_date'])));
	    			$exam_start_time = DateTime::createFromFormat('Y-m-d', date('Y-m-d',  strtotime($data['exam']['created_date'])));
	    			
	    			if($exam_start_date < $current_date){
	    				$data['exam_status'] = 2;
	    			}else if($exam_start_date == $current_date ){
	    				if($this->active_h['exam_st'] == 'finished'){
	    					$data['exam_status'] = 2;
	    				}else if($this->active_h['exam_st'] == 'started'){
	    					$data['exam_status'] = 1;
	    				}
	    			}
	    			
	    		}
	    	}
	    }else{
	    	$data['error'] = 'Exam or Topic is not allocated for current week!';
	    }

	    if($data['exam_status'] == 1){
	    	// Get exam status if exam is already started.

	    }

	//	p($data,true);
		$this->template->load('student/default','student/exam_instruction',$data);
	}


}
