<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class My_exam extends ISM_Controller {

	/*	Student exams.
	*	@Auther : Kamlesh Pokiya (KAP). 
	*/

	public function __construct()
	{
	    parent::__construct();
	}

	public function index()
	{
		$data['title'] = 'ISM - MY Exam';

		//	get student detail
		$user_data = $this->session->userdata('user');
		$data['my_course_name'] = $user_data['course_name'];
		$course_id = $user_data['course_id'];
		$classroom_id = $user_data['classroom_id'];
		$user_id = $user_data['id'];


		//	get attampted exam list with result
		$where	=	array('where' => array('c.classroom_id' => $classroom_id));
		$option	= 	array('join' => 
						array(
							array(
								'table' => TBL_SUBJECTS.' s',
								'condition'	=>  's.id = c.subject_id'
							)
						)
					);
		$data['subject_list'] = select(TBL_CLASSROOM_SUBJECT.' c','s.id,s.subject_name,(select count(*)  from '.TBL_STUDENT_EXAM_SCORE.' sc LEFT JOIN '.TBL_EXAMS.' e ON e.id = sc.exam_id where sc.user_id = '.$user_id.' and sc.exam_status = \'finished\' and e.subject_id = s.id) as cnt',$where,$option);
		
		//	get student classroom subject detail
		$where = array('where'=>array('e.classroom_id' => $classroom_id,'sc.exam_status'=>'finished','sc.user_id'=>$user_id,'sc.is_delete' => 0,'e.is_delete' => 0));
		$option =  array( 'join' =>
						array(
							array(
								'table' => TBL_EXAMS.' e',
								'condition' => 'e.id = sc.exam_id'
							),
							array(
								'table' => TBL_SUBJECTS.' s',
								'condition' => 'e.subject_id = s.id'
							),
							array(
								'table' => '(select count(*) as cnt,exam_id	FROM exam_question group by exam_id) ed',
								'condition' => 'ed.exam_id = e.id'
							)
						)
					);
		$select = 'ed.cnt,e.exam_name,s.id,s.subject_name,e.id as exam_id,TRUNCATE((sc.correct_answers * 100) / ed.cnt,2) as percentage';
		$data['my_exam'] = select(TBL_STUDENT_EXAM_SCORE.' sc',$select,$where,$option);
		
		$this->template->load('student/default','student/my_exam',$data);
	}
}
