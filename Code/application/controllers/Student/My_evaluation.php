<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class My_evaluation extends ISM_Controller {

	/*	Student Exam Evaluation.
	*	@Auther : Kamlesh Pokiya (KAP). 
	*/

	public function __construct()
	{
	    parent::__construct();
	    if(is_numeric($this->uri->segment(4))){
	    	$this->session->set_userdata('examid',$this->uri->segment(4));
	    	$this->examid = $this->session->userdata('examid');
	    }
	    else{
	    	$this->examid = $this->session->userdata('examid');
	    }

	    //	if session not available
	    if($this->examid == '')
	    	redirect('student/my_exam');

	}

	public function index()
	{
		//	page title
		$data['title'] =  'My - Evaluation';

		//	get user detail
		$user_data = $this->session->userdata('user');
		$userid = $user_data['id'];
		
		// 	get attempt exam detail with result
		$where 	= array('where' => array('e.id' => $this->examid,'sc.user_id' => $userid));
		$option = array('join' => 
					array(
						array(
							'table' => TBL_STUDENT_EXAM_SCORE.' sc',
							'condition' => 'sc.exam_id = e.id'
						),
						array(
							'table' => '(select count(*) as cnt,exam_id from exam_question where exam_id = '.$this->examid.') eq',
							'condition' => 'eq.exam_id = e.id'
						)
					),
					'single' => true
				);
		$data['my_scoreboard']	= select(TBL_EXAMS.' e','e.id,eq.cnt,TRUNCATE(sc.total_time_spent / 60,2)as totmin,sc.attempt_count,(eq.cnt - sc.attempt_count) as unattampt,e.exam_category,e.exam_name,sc.incorrect_answers,sc.correct_answers,TRUNCATE((sc.correct_answers * 100 / cnt ),2)as percentage',$where,$option);

		//	get exam evaluation
		$where 	= array('where' => array('eq.exam_id'=>$this->examid));
		$option = array('join' => 
					array(
						array(//	for get exam question
							'table' => TBL_QUESTIONS.' q',
							'condition' => 'q.id = eq.question_id'
						),
						array(
							'table' => TBL_ANSWER_CHOICES.' ac',
							'condition' => 'q.id = ac.question_id and ac.is_right = 1'
						),
						array(
							'table' => TBL_STUDENT_EXAM_RESPONSE.' sr',
							'condition' => 'sr.question_id = q.id'
						),
						array(
							'table' => TBL_ANSWER_CHOICES.' ac2',
							'condition' => 'ac2.id = sr.choice_id'
						)
					),
					'group_by' => 'eq.question_id'
				);
		$data['my_evaluation'] = select(TBL_EXAM_QUESTION.' eq','sr.choice_id,
			eq.question_id,q.question_text,ac.choice_text as correct_ans,ac2.choice_text as your_ans',$where,$option);
		// p($data['my_evaluation'],true);
		$this->template->load('student/default','student/my_evaluation',$data);

	}
}
