<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class My_scoreboard extends ISM_Controller {

	/*	Student scoreboard.
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
	}

	public function index()
	{
		$data['title'] = 'ISM - MY Scoreboard';

		$user_data = $this->session->userdata('user');
		$userid = $user_data['id'];

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
		// qry();
		// p($data['my_scoreboard'],true);
		$this->template->load('student/default','student/My_scoreboard',$data);
	}
}
