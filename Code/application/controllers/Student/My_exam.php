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
							),
							array(
								'table' => '(select e.subject_id,count(*) as cnt,TRUNCATE((sc.correct_answers * 100) / (select count(*) as totquestion from exam_question where exam_id = e.id),2) as percentage from '.TBL_EXAMS.' e left join '.TBL_STUDENT_EXAM_SCORE.' sc on e.id = sc.exam_id where sc.user_id = '.$user_id.' and e.is_delete = 0 and sc.exam_status="finished") st',
								'condition' => 'st.subject_id = s.id'
							)
						)
					);
		$data['subject_list'] = select(TBL_COURSE_SUBJECT.' c','s.id,s.subject_name,st.cnt,st.percentage',$where,$option);

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
							)
						)
					);
		$select = 'e.exam_name,s.id,s.subject_name';
		$data['my_exam'] = select(TBL_STUDENT_EXAM_SCORE.' sc',$select,$where,$option);
		$this->template->load('student/default','student/my_exam',$data);
	}
}
