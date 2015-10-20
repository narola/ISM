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
		$data['my_subject'] = select(TBL_COURSE_SUBJECT.' cs','s.subject_name,s.id AS subject_id,s.subject_image',$where,$option);
		
		//	get exam list with percentage (if attampted)
		$where = array('where' => array('e.classroom_id' => $user_classroom,'e.is_delete' => 0));
		$option = array('join' =>
				array(
					array(
						'table' => TBL_STUDENT_EXAM_SCORE.' sc',
						'condition' => 'sc.exam_id = e.id and sc.exam_status = "finished"'
					)
				)
			);
		$data['my_exam']  = select(TBL_EXAMS.' e','e.exam_name,e.id as exam_id,TRUNCATE((sc.correct_answers * 100 / (select count(*) from '.TBL_EXAM_QUESTION.' where exam_id = sc.exam_id)),2) as per,e.subject_id,sc.id',$where,$option);
		
		$this->template->load('student/default','student/my_classroom_exam',$data);
	}

	//	Load class exam instruction  
	public function exam_instruction()
	{
		$data['title'] = 'ISM - My Classroom Exam';
		$user_id = $this->session->userdata('user')['id'];
		if(is_numeric($this->uri->segment(3))){
			$this->session->set_userdata('class_examid',$this->uri->segment(3));
			$data['exam_id'] = $this->session->userdata('class_examid');
		}
		else{
			$data['exam_id'] = $this->session->userdata('class_examid');	
		}

		// echo select(TBL_STUDENT_EXAM_SCORE,null,array('where'=>array('user_id'=>$user_id,'exam_id'=>$data['exam_id'],'exam_status'=>'started')),1);
		// qry();
		// exit;
		if(select(TBL_STUDENT_EXAM_SCORE,null,array('where'=>array('user_id'=>$user_id,'exam_id'=>$data['exam_id'],'exam_status'=>'started')),1) > 0)
			redirect('student/class_exam');
		$this->template->load('student/default','student/class_exam_instruction',$data);	
	}

	public function exam_start()
	{
		$data['user_id'] = $user_id = $this->session->userdata('user')['id'];
		$data['hide_right_bar'] = true;	
		$data['left_menu'] = false;

		// if($data['exam_status'] != 1 || $count == 0){
		// 	redirect('student/exam-instruction');
		// }
		$exam_id = $this->session->userdata('class_examid');
		$data['exam_id'] = $exam_id;
		if(isset($exam_id)){
			
			$question_info = select(
				TBL_STUDENT_EXAM_SCORE. ' ss',
				'GROUP_CONCAT(eq.question_id) as question_id, count(eq.question_id) as total_question, (SELECT GROUP_CONCAT(`srq`.`question_id`) FROM '.TBL_STUDENT_EXAM_RESPONSE.' `srq` 
					WHERE `srq`.`exam_id` = `ss`.`exam_id` AND `srq`.`user_id` = `ss`.`user_id`) as attemped_question',
				array('where' => array('ss.exam_id' => $exam_id,'ss.user_id' =>$data['user_id'] )),
				array('join' => array(
						array(
						'table' => TBL_EXAM_QUESTION. ' eq',
						'condition' => 'eq.exam_id = ss.exam_id'
						)),
				'single' => true
					)
			);

			$data['attempted_question'] = select(TBL_STUDENT_EXAM_RESPONSE.' ser',
				'ser.id,ser.question_id,ser.answer_status',
				array('where' => array(
					'ser.exam_id' => $exam_id,
					'ser.user_id' => $data['user_id'] ))
				);

			$new = explode(",",$question_info['question_id']);
			//$data['answered_question'] = explode(",",$question_info['attemped_question']);
			
			$data['current_no'] = count($data['attempted_question']) + 1;
			
			// Randomely stored question ids.
			shuffle($new);
			if(!$this->session->userdata('exam_question')){

				if(count($data['attemped_question']) > 0 && $data['attemped_question'] != ''){
					foreach($new as $key => $value){
						if(in_array($value,$data['attemped_question'])){
								unset($new[$key]);
						}
					}
					
					$new = array_merge($data['attemped_question'],$new);
				}
				// p($data['answered_question']);
			 	$this->session->set_userdata('exam_question',$new);
			}


			$data['question_id'] = $this->session->userdata('exam_question');

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
		// p($data,true);
		$this->template->load('student/default','student/class_exam_question_answer',$data);

	}
}
