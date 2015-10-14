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

		/*---get student course name---*/
		$user_data = $this->session->userdata('user');
		// p($user_data,'true');
		$data['my_course_name'] = $user_data['course_name'];
		$course_id = $user_data['course_id'];
		$classroom_id = $user_data['classroom_id'];
		$user_id = $user_data['id'];

		$where	=	array('where' => array('c.course_id' => $course_id,'c.classroom_id' => $classroom_id));
		$option	= 	array('join' => 
						array(
							array(
								'table' => TBL_SUBJECTS.' s',
								'condition'	=>  's.id = c.subject_id'
							),
							array(
								'table' => '(select e.subject_id,count(*) as cnt from exams e left join student_exam_score sc on e.id = sc.exam_id where sc.user_id = '.$user_id.' and e.is_delete = 0) st',
								'condition' => 'st.subject_id = s.id'
							)
						)
					);
		$data['subject_list'] = select(TBL_COURSE_SUBJECT.' c','s.id,s.subject_name,st.cnt',$where,$option);
		// qry();		

		// p($data['subject_list'],true);
		/*---get my exam list---*/

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
		// qry();		
		// p($data['my_exam'],true);
		$this->template->load('student/default','student/my_exam',$data);
	}
}
