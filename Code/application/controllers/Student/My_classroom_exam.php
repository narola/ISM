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
}
