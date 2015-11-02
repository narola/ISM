<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class My_classroom_exam extends ISM_Controller {

	/*	Student classroom exams.
	*	@Auther : Kamlesh Pokiya (KAP). 
	*/

	public function __construct()
	{
	    parent::__construct();
	}

	public function index()
	{
		//	page title
		$data['title'] = 'ISM - MY Classroom Exam';

		//	get user detail
		$user_data = $this->session->userdata('user');
		$user_classroom = $user_data['classroom_id'];
		$user_id = $user_data['id'];
		$data['user_class_name'] = $user_data['class_name'];

		$exam_type = $this->input->post('exam_type');
	
		if($exam_type != '0' )
		{
			$where1 = array('where' => array('e.classroom_id' => $user_classroom,'e.is_delete' => 0,'e.exam_category !='=>'tutorial','e.exam_category'=>$exam_type));
			$where2 = 'AND exam_category ="'.$exam_type.'" AND classroom_id='.$user_classroom;
			$data['exam_type'] = $exam_type;
		}
		else{
			$where1 = array('where' => array('e.classroom_id' => $user_classroom,'e.is_delete' => 0,'e.exam_category !='=>'tutorial'));
			$where2 = 'AND 1=1 AND classroom_id='.$user_classroom;
			$data['exam_type'] = '';

		}
		//	get classroom subject		
		$option = array('join' =>
				array(
					array(
						'table' => TBL_SUBJECTS.' s',
						'condition' => 'cs.subject_id = s.id' 
					)
				)
			);
		$where = array('where' => array('cs.classroom_id' => $user_classroom));
		$data['my_subject'] = select(TBL_CLASSROOM_SUBJECT.' cs','(SELECT COUNT(*) FROM '.TBL_EXAMS.' WHERE is_delete = 0 AND subject_id = s.id '.$where2.')tot_exam,s.subject_name,s.id AS subject_id,s.subject_image',$where,$option);
		
		//	get exam list with percentage (if attampted)
		$option = array('join' =>
				array(
					array(
						'table' => TBL_STUDENT_EXAM_SCORE.' sc',
						'condition' => 'sc.exam_id = e.id and sc.user_id='.$user_id
					)
				)
		);

		$data['my_exam']  = select(TBL_EXAMS.' e','IF(TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute) < 0,0,TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute)) AS remaining_time,sc.exam_status,e.exam_name,e.id AS exam_id,TRUNCATE((sc.correct_answers * 100 / (SELECT COUNT(*) FROM '.TBL_EXAM_QUESTION.' WHERE exam_id = sc.exam_id)),2) AS per,e.subject_id,sc.id',$where1,$option);
		
		/* Mark exam as finished whose time is over. */
		foreach ($data['my_exam'] as $k => $v) {
			if($v['exam_status'] ==  'started' && $v['remaining_time'] == 0){
				$data['my_exam'][$k]['exam_status'] = 'finished';
				update(TBL_STUDENT_EXAM_SCORE,$v['id'],array('exam_status' => 'finished'));
			}
		}

		//p($data['my_exam'],true);
		$this->template->load('student/default','student/my_classroom_exam',$data);
	}

	public function is_exam_given($user_id,$exam_id){
		$is_finished = select(
						TBL_STUDENT_EXAM_SCORE. ' s',
						"if(count(*)>0,1,0) AS is_given",
						"s.exam_status = 'finished' AND s.user_id = $user_id AND e.exam_category != 'Tutorial' AND s.is_delete = 0 AND e.is_delete = 0 AND s.exam_id = $exam_id",
						array('join' => array(
							array(
								'table' => TBL_EXAMS.' e',
								'condition' => 'e.id = s.exam_id'
								)
							),
						'single' => true
						)
						);

				if($is_finished['is_given'] == 1){
					$this->session->set_flashdata('error','Selected exam was already given.');
					redirect(base_url().'student/my_scoreboard/index/'.$exam_id);
				}
	}

	//	Load class exam instruction  
	public function exam_instruction()
	{	

		$data['title'] = 'ISM - My Classroom Exam';
		$user_id = $this->session->userdata('user')['id'];
		$classroom_id = $this->session->userdata('user')['classroom_id'];
		$exam_id = $data['exam_id']= $this->uri->segment(3);
		$data['show_button'] = false;





		/* Verify Exam  before start.*/
		$data['verify'] = select(
				TBL_EXAMS.' e',
				"COUNT(*) AS `is_valid`,(SELECT IF(COUNT(*)>0,1,0) FROM student_exam_score s JOIN exams e on e.id = s.exam_id WHERE s.exam_status = 'started' AND s.user_id = $user_id AND e.exam_category != 'Tutorial' AND s.is_delete = 0 AND e.is_delete = 0) is_started, (SELECT IF(COUNT(`id`)>0,1,0) FROM `exam_question` WHERE `exam_id` = $exam_id AND `is_delete` = 0) is_que_added ",
				"`e`.`classroom_id` = $classroom_id AND `e`.`exam_category` != 'Tutorial' AND `e`.`id` = $exam_id AND e.is_delete = 0",
				1
			);

		
		if($data['verify']['is_started'] == 1){
			$data['exam_status'] = select(
					TBL_STUDENT_EXAM_SCORE.' sc',
					'sc.id, sc.exam_id,sc.created_date,IF(TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute) < 0,0,TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute)) AS remaining_time',
					'sc.user_id = '.$user_id.' AND sc.exam_status = "started" AND e.classroom_id` = '. $classroom_id,
					array('join' => array(
							array(
								'table' => TBL_EXAMS.' e',
								'condition' => 'e.id = sc.exam_id'
								)
							),
						'limit' => 1,
						'order_by' => 'sc.id DESC',
						'single' => true
						)
				);
			// Check current exam time is finished or not.
			if($data['exam_status']['remaining_time'] <= 0){
					update(
						TBL_STUDENT_EXAM_SCORE,
						array('id' => $data['exam_status']['id']),
						array('exam_status' => 'finished')
						);

					$this->is_exam_given($user_id,$exam_id);
					$data['show_button'] = true;
			}else{
				if($exam_id != $data['exam_status']['exam_id'] ){
					$this->session->set_flashdata('error','Please finish current running exam first!!');
				    redirect(base_url().'student/my_classroom_exam');
				}else{
					redirect(base_url().'student/class_exam');
				}
			}
		}else{
			if($data['verify']['is_valid'] == 1){

				$this->is_exam_given($user_id,$exam_id);

				if($data['verify']['is_que_added'] == 0){
					$this->session->set_flashdata('error','No questions found in selected exam!');
					redirect(base_url().'student/my_classroom_exam');
				}else{

					$data['show_button'] = true;
				}
			}else{
				$this->session->set_flashdata('error','Selected Exam Invalid! Please don\'t modify data manually.');
				redirect(base_url().'/student/my_classroom_exam');
			}

		}
					
		$this->template->load('student/default','student/class_exam_instruction',$data);	
	}

	public function exam_start()
	{
		$user_id = $data['user_id'] = $user_id = $this->session->userdata('user')['id'];
		$data['hide_right_bar'] = true;	
		$data['left_menu'] = false;
		$classroom_id = $this->session->userdata('user')['classroom_id'];
		$data['exam_status'] = select(
					TBL_STUDENT_EXAM_SCORE.' sc',
					'e.exam_name,IF(sc.id > 0, sc.id, 0 ) AS id, sc.exam_id,sc.created_date,if(TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute) < 0,0,TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute)) AS remaining_time',
					'sc.user_id = '.$user_id.' AND sc.exam_status = "started" AND e.classroom_id` = '. $classroom_id,
					array('join' => array(
							array(
								'table' => TBL_EXAMS.' e',
								'condition' => 'e.id = sc.exam_id'
								)
							),
						'limit' => 1,
						'order_by' => 'sc.id desc',
						'single' => true
						)
				);
		if($data['exam_status']['id']  ==  0 ){
			$this->session->set_flashdata('error','There is problem! Please come later!!');
			redirect('/student/my_classroom_exam');
		}

		if($data['exam_status']['remaining_time'] <= 0){
			$this->session->set_flashdata('error','Selected exam is finished!');
			redirect('/student/my_classroom_exam');
		}
		
		if(isset($data['exam_status']['exam_id']) && !empty($data['exam_status']['exam_id'])){
			
			$question_info = select(
				TBL_STUDENT_EXAM_SCORE. ' ss',
				'GROUP_CONCAT(eq.question_id) as question_id, COUNT(eq.question_id) AS total_question, (SELECT GROUP_CONCAT(`srq`.`question_id`) FROM '.TBL_STUDENT_EXAM_RESPONSE.' `srq` 
					WHERE `srq`.`exam_id` = `ss`.`exam_id` AND `srq`.`user_id` = `ss`.`user_id`) AS attemped_question',
				array('where' => array('ss.exam_id' => $data['exam_status']['exam_id'],'ss.user_id' =>$data['user_id'] )),
				array('join' => array(
						array(
						'table' => TBL_EXAM_QUESTION. ' eq',
						'condition' => 'eq.exam_id = ss.exam_id'
						)),
				'single' => true
					)
			);

			// Get list of allready attempted questions
			$data['attempted_question'] = select(TBL_STUDENT_EXAM_RESPONSE.' ser',
				'ser.id,ser.question_id,ser.answer_status',
				array('where' => array(
					'ser.exam_id' => $data['exam_status']['exam_id'],
					'ser.user_id' => $data['user_id'] ))
				);

			$new = explode(",",$question_info['question_id']);
			//$data['answered_question'] = explode(",",$question_info['attemped_question']);
			
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

			//  Removing empty element if any
			foreach($data['question_id'] as $key => $value){
				if($value == ''){
					unset($data['question_id'][$key]);
				}
			}

			// check any question in exam or not.
			if(count($data['question_id']) == 0){
				$this->session->set_flashdata('error','No questions are added in selected exam!');
				redirect('/student/my_classroom_exam');
			}
			
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
		//p($data,true);
		$this->template->load('student/default','student/class_exam_question_answer',$data);

	}
}
