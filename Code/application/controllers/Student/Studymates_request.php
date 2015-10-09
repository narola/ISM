<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Studymates_request extends ISM_Controller {
	
	/*	Student Studymate Request.
	*	@Auther : Kamlesh Pokiya (KAP). 
	*/

	public function __construct()
	{
	    parent::__construct();
	}

	public function index()
	{
		$data['title'] = 'ISM - MY Studymate Requests';
		$user_group_id = $this->session->userdata('user')['group_id'];
		$user_id = $this->session->userdata('user')['id'];
		
		/*----get studymate list-----*/
		$my_studymates = studymates($user_id,false);
		if(!sizeof($my_studymates) > 0)
			$my_studymates = array('');

		/*----get studymate request list----*/
		$options = array('join' => array(
							array(
								'table' => TBL_USERS.' u',
								'condition' => 'u.id = s.request_from_mate_id'
							),
							array(
								'table' => TBL_STUDENT_ACADEMIC_INFO.' si',
								'condition' => 'si.user_id = u.id'	
							),
							array(
								'table' => TBL_SCHOOLS.' sl',
								'condition' => 'sl.id = si.school_id'
							),
							array(
								'table' => TBL_USER_PROFILE_PICTURE.' p',
								'condition' => 'p.user_id = u.id'
							)
						)
					);
		$where = array('where'=>array('s.request_to_mate_id'=>$user_id),'where_in'=>array('s.status'=>array(0,2)),'where_not_in'=>array('u.id'=>$my_studymates));
		$data['studymate_request'] = select(TBL_STUDYMATES_REQUEST.' s','sl.school_name,u.full_name,p.profile_link,u.id',$where,$options);
		
		

		/*----get recommended studymate list---*/
		$where = array('where' => array('m.group_id'=>$user_group_id,'in1.user_id !=' => $user_id),'where_not_in'=>array('in1.user_id' => $my_studymates));
		$options = array('join' => array(
					array(
						'table' => TBL_STUDENT_ACADEMIC_INFO.' in',
						'condition' => 'in.user_id = m.user_id',
						'join'=>'join'
					),
					array(
						'table' => TBL_STUDENT_ACADEMIC_INFO.' in1',
						'condition' => 'in.classroom_id = in1.classroom_id and in.course_id = in1.course_id and in.academic_year = in1.academic_year and in.school_id = in1.school_id',
						'join'=>'join'
					),
					array(
						'table' => TBL_USERS.' u',
						'condition' => 'in1.user_id = u.id'
					),
					array(
						'table' => TBL_SCHOOLS.' s',
						'condition' => 's.id = in.school_id'
					),
					array(
						'table' => TBL_COURSES.' c',
						'condition' => 'c.id = in1.course_id'
					),
					array(
						'table' => TBL_USER_PROFILE_PICTURE.' p',
						'condition' => 'u.id = p.user_id'
					),
					array(
						'table' => TBL_STUDYMATES_REQUEST.' sr',
						'condition' => 'sr.request_from_mate_id='.$user_id.' and sr.request_to_mate_id = in1.user_id'
					),
					
				),
		'group_by' => 'in1.user_id'
			);
		$data['recommended_studymates'] = select(TBL_TUTORIAL_GROUP_MEMBER.' m','in1.user_id,u.full_name,s.school_name,c.course_name,p.profile_link,sr.id as srid',$where,$options);

		$this->template->load('student/default','student/studymates_request',$data);
	}
}
