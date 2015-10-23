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
		$data['user_class_name'] = $user_data['class_name'];

		$exam_type = $this->input->post('exam_type');
		if($exam_type != '')
		{
			$where1 = array('where' => array('e.classroom_id' => $user_classroom,'e.is_delete' => 0,'e.exam_category !='=>'tutorial','e.exam_category'=>$exam_type));
			$where2 = 'and exam_category ="'.$exam_type.'" and classroom_id='.$user_classroom;
			$data['exam_type'] = $exam_type;
		}
		else{
			$where1 = array('where' => array('e.classroom_id' => $user_classroom,'e.is_delete' => 0,'e.exam_category !='=>'tutorial'));
			$where2 = 'and 1=1 and classroom_id='.$user_classroom;
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
		$data['my_subject'] = select(TBL_COURSE_SUBJECT.' cs','(select count(*) from '.TBL_EXAMS.' where is_delete = 0 and subject_id = s.id '.$where2.')tot_exam,s.subject_name,s.id AS subject_id,s.subject_image',$where,$option);
		
		//	get exam list with percentage (if attampted)
		$option = array('join' =>
				array(
					array(
						'table' => TBL_STUDENT_EXAM_SCORE.' sc',
						'condition' => 'sc.exam_id = e.id and sc.exam_status = "finished"'
					)
				)
			);
		$data['my_exam']  = select(TBL_EXAMS.' e','e.exam_name,e.id as exam_id,TRUNCATE((sc.correct_answers * 100 / (select count(*) from '.TBL_EXAM_QUESTION.' where exam_id = sc.exam_id)),2) as per,e.subject_id,sc.id',$where1,$option);
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

		$data['exam_status'] = select(
				TBL_STUDENT_EXAM_SCORE.' sc',
				'sc.exam_id,if(TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute) < 0,0,TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute)) as remaining_time',
				'sc.user_id = '.$data['user_id'].' and sc.exam_status = "started"',
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

		if($data['exam_status']['remaining_time'] <= 0){
			$this->session->set_flashdata('error','Selected exam is finished!');
			redirect('/student/my_classroom_exam');
		}
		
		if(isset($data['exam_status']['exam_id']) && !empty($data['exam_status']['exam_id'])){
			
			$question_info = select(
				TBL_STUDENT_EXAM_SCORE. ' ss',
				'GROUP_CONCAT(eq.question_id) as question_id, count(eq.question_id) as total_question, (SELECT GROUP_CONCAT(`srq`.`question_id`) FROM '.TBL_STUDENT_EXAM_RESPONSE.' `srq` 
					WHERE `srq`.`exam_id` = `ss`.`exam_id` AND `srq`.`user_id` = `ss`.`user_id`) as attemped_question',
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
		//p($question_info,true);
		$this->template->load('student/default','student/class_exam_question_answer',$data);

	}
}
