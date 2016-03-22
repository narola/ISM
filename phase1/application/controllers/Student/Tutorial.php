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
			// $data['current_weekday']=2;
			if($data['current_weekday'] >= 4){
				redirect('student/exam-instruction');
				exit;
			}
			$date = new DateTime(date("Y-m-d H:i:s"));
			$c_week = $date->format("W");
			$last_week = $c_week - 1;
			
			$year = date("Y");
			$if_allocated = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION, 'id', array('where'=>array('group_id'=>$this->session->userdata('user')['group_id'], 'week_no'=>$c_week,'YEAR(`created_date`)' => $year )), array('count'=>true));
			$data['if_allocated'] = 1;
			if($if_allocated == 0){
				$c_week = $last_week;	
				$data['if_allocated'] = 0;
			}
			$cnt = 0; $arr = array();
			for($i=$c_week;$i>0;$i--){
				if($cnt<5){
					array_push($arr, $i);
				}
				else{
					break;
				}
				$cnt++;
			}

			// Get current topic data from DB
			$data['topic']  = select(
				TBL_TUTORIAL_TOPIC.' t',
				't.id,
				t.topic_name,
				t.topic_description,
				t.created_date,
				tg.group_status,
				ts.score as my_score,
				(SELECT SUM(group_score) FROM '.TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' WHERE group_id = tm.group_id) as group_score,
				up.profile_link,
				u.full_name',
				array('where' => array('ta.group_id' => $this->session->userdata('user')['group_id'],'tm.user_id' => $user_id ,'YEAR(`ta`.`created_date`)' => $year),
					'where_in'=> array('ta.week_no' => $arr )
					),
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
						'condition' => 'ts.member_id = tm.id and ts.topic_id = ta.topic_id'
						),
					array(
						'table' => TBL_USERS.' u',
						'condition' => 'u.id = t.created_by'
						),
					array(
						'table' => TBL_USER_PROFILE_PICTURE.' up',
						'condition' => 'up.user_id = u.id'
						),
					array(
						'table' => TBL_TUTORIAL_GROUPS.' tg',
						'condition' => 'tg.id = ta.group_id'
						)
					),
				'group_by'=> 'ta.topic_id'
				)
			);
// p($data['topic'], true);
$ids = array_column($data['topic'], 'id');
if(empty($data['topic'])){
	$this->session->set_flashdata('error','Topic is not allocated yet.');
	redirect('/student/home');
}
		if($this->session->userdata('user')['group_status'] != 'active'){
			redirect('/student/home');
		}
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
						td.in_active_hours,
						td.is_active',
						array('where_in' => array('td.topic_id' => $ids)),
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
			$this->template->load('student/default','student/tutorial_group',$data);
	}
		
	function exam_status(){
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
		// $data['current_weekday'] = 2;
		// Check week day is thursday, friday or saturday
		if($data['current_weekday'] <= 3){
			redirect('student/tutorial');
		}

		// Check topic and exam allocated for current week and get exam info.
	    $data['exam']  = select(
				TBL_TUTORIAL_TOPIC.' t',
				'ta.topic_id,
				t.topic_name,
				t.topic_description,
				tg.group_status,
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
						),
					array(
						'table' => TBL_TUTORIAL_GROUPS.' tg',
						'condition' => 'tg.id = ta.group_id'
						)
					),
				'single' => true,
				'limit' => 1
				)
			);

	   if($data['exam']['group_status'] != 'active'){
	   		//redirect('student/home');

	   }
	    $current_date = DateTime::createFromFormat('Y-m-d', date('Y-m-d'));
	    //$current_time = DateTime::createFromFormat('Y-m-d', date('Y-m-d H:i:s'));
	    if(isset($data['exam']) && !empty($data['exam'])){
	    	if(isset($data['exam']['created_date'])){
	    		if($data['exam']['created_date'] != '' && $data['exam']['created_date'] != null){
	    			$exam_start_date = DateTime::createFromFormat('Y-m-d', date('Y-m-d',  strtotime($data['exam']['created_date'])));
	    			//$exam_start_time = DateTime::createFromFormat('Y-m-d', date('Y-m-d',  strtotime($data['exam']['created_date'])));
	    			
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

	    return $data;
	}

	/**
	*	Attempt exam (Exam instruction).
	*   @autor Sandip Gopani
	*/
	public function exam(){
		$data = $this->exam_status();
		$this->template->load('student/default','student/exam_instruction',$data);
	}



	function exam_start(){
			
		$data = $this->exam_status();
		$data['user_id'] = $user_id = $this->session->userdata('user')['id'];
		$data['hide_right_bar'] = true;	
		$data['left_menu'] = false;

		// Check score entry in DB
		$count = select(
			TBL_STUDENT_EXAM_SCORE. ' ss',
			'ss.id',
			"ss.user_id = '".$data['user_id']."' AND ss.exam_id = '".$data['exam']['exam_id']."' AND ss.exam_status != 'finished' ",
			array('count' => true)
			);
		
		if($data['exam_status'] != 1 || $count == 0){
			redirect('student/exam-instruction');
		}

		if(isset($data['exam']['topic_id']) && isset($data['exam']['exam_id'])){
			
			$question_info = select(
				TBL_STUDENT_EXAM_SCORE. ' ss',
				'e.exam_name, GROUP_CONCAT(eq.question_id) as question_id, count(eq.question_id) as total_question, (SELECT GROUP_CONCAT(`srq`.`question_id`) FROM '.TBL_STUDENT_EXAM_RESPONSE.' `srq` 
					WHERE `srq`.`exam_id` = `ss`.`exam_id` AND `srq`.`user_id` = `ss`.`user_id`) as attemped_question',
				array('where' => array('ss.exam_id' => $data['exam']['exam_id'],'ss.user_id' =>$data['user_id'] )),
				array('join' => array(
						array(
						'table' => TBL_EXAM_QUESTION. ' eq',
						'condition' => 'eq.exam_id = ss.exam_id'
						),
						array(
						'table' => TBL_EXAMS. ' e',
						'condition' => 'e.id = ss.exam_id'
						),
					),
					'single' => true
				)
			);

			$data['exam_name'] = $question_info['exam_name'];
			$data['attempted_question'] = select(TBL_STUDENT_EXAM_RESPONSE.' ser',
				'ser.id,ser.question_id,ser.answer_status',
				array('where' => array(
					'ser.exam_id' => $data['exam']['exam_id'],
					'ser.user_id' => $data['user_id'] ))
				);
			$new = explode(",",$question_info['question_id']);
			$data['current_no'] = count($data['attempted_question']) + 1;
			
			// Randomely stored question ids.
			shuffle($new);
				if(count($data['attempted_question']) > 0){
					$final_attemped = array();
					foreach ($data['attempted_question'] as $key => $value) {
						$final_attemped[] = $value['question_id'];
					}
					foreach($new as $key => $value){
						if(in_array($value,$final_attemped)){
								unset($new[$key]);
						}
					}
					$new = array_merge($final_attemped,$new);
				}

			$data['question_id'] = $new;
			if(count($data['attempted_question']) == count($new)){
				$data['current_no'] = 1;
			}
			/* Get random question */
			$data['current_question'] = select(TBL_QUESTIONS. ' q',
					'q.question_text,q.id',
					'q.id = '.$data['question_id'][$data['current_no'] - 1].' ',
					array('limit' => 1,
					'single' => 1
					)
				);

			/* Get Choises of current question */
			$data['question_choices'] = array();
			if(isset($data['current_question']) && !empty($data['current_question'])){
			$data['question_choices'] = select(
				TBL_ANSWER_CHOICES. ' ac',
				'ac.id,ac.question_id,ac.choice_text',
				'ac.question_id = '.$data['current_question']['id'],
				array('order_by' => 'RAND()')
				);
			}
		}else{
			$data['error'] = 'Topic or Exam is not allocated for this week!';
		}
		$this->template->load('student/default','student/exam_question_answer',$data);
	}

}
