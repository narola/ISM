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
		// p($this->session->userdata('user'),true);

		/*---get student course name---*/
		$data['my_course_name'] = $this->session->userdata('user')['course_name'];

		/*---get my exam list---*/

		$classroom_id = $this->session->userdata('user')['classroom_id'];
		$where = array('where'=>array('e.classroom_id' => $classroom_id));
		$option =  array( 'join' =>
						array(
							array(
								'table' => TBL_SUBJECTS.' s',
								'condition' => 'e.subject_id = s.id'
							)
						)
					);
		$select = 'e.exam_name,s.id,s.subject_name';
		$data['get_all_subject'] = select(TBL_EXAMS.' e',$select,$where,$option);
				

		$where = array('where'=>array('e.classroom_id' => $classroom_id));
		$option =  array( 'join' =>
						array(
							array(
								'table' => TBL_SUBJECTS.' s',
								'condition' => 'e.subject_id = s.id'
							),
						),
						'group_by' => 's.id'
					);
		$select = 'e.exam_name,s.id,s.subject_name';
		$data['my_exam'] = select(TBL_EXAMS.' e',$select,$where,$option);
		// qry();
		// p($data['my_exam'],true);
		$this->template->load('student/default','student/my_exam',$data);
	}
}
