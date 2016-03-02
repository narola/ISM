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
								'table' => TBL_COURSES.' c',
								'condition' => 'si.course_id = c.id'
							),
							array(
								'table' => TBL_USER_PROFILE_PICTURE.' p',
								'condition' => 'p.user_id = u.id'
							)
						)
					);
		$where = array('where'=>array('s.request_to_mate_id'=>$user_id,'s.is_delete'=> 0),'where_in'=>array('s.status'=>array(0,2)),'where_not_in'=>array('u.id'=>$my_studymates));
		$data['studymate_request'] = select(TBL_STUDYMATES_REQUEST.' s','c.course_name,sl.school_name,u.full_name,p.profile_link,u.id',$where,$options);
		
		/*----get recommended studymate list---*/
		$data['recommended_studymates'] = get_recommended($user_id,$user_group_id);

		$this->template->load('student/default','student/studymates_request',$data);
	}
}
