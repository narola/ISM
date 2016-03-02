<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Studymates extends ISM_Controller {
	/*	Student Studymate.
	*	@Auther : Kamlesh Pokiya (KAP). 
	*/

	public function __construct()
	{
	    parent::__construct();
	}

	public function index()
	{
		$data['tab'] = 'my_studymates';
		if($this->input->post('find_studymates')){
			$data['tab'] = 'find_studymates';	
		}
		$data['title'] = 'ISM - MY Studymates';
		$user_group_id = $this->session->userdata('user')['group_id'];
		$user_id = $this->session->userdata('user')['id'];
		$course_id = $this->session->userdata('user')['course_id'];
		
		/*----get My studymate list-----*/
		$my_studymates = studymates($user_id,false);
		
		if(sizeof($my_studymates)>0){
			$where = array('where_in' => array('u.id' => $my_studymates));
			$options = array('join' =>
						array(
							array(
								'table' => TBL_STUDENT_ACADEMIC_INFO.' i',
								'condition' => 'i.user_id = u.id'
							),
							array(
								'table' => TBL_SCHOOLS.' s',
								'condition' => 's.id = i.school_id'
							),
							array(
								'table' => TBL_COURSES.' c',
								'condition' => 'c.id = i.course_id'
							),
							array(
								'table' => TBL_USER_PROFILE_PICTURE.' p',
								'condition' => 'p.user_id = u.id'
							)
						)
					);
			$data['my_studymates'] = select(TBL_USERS.' u','u.id as user_id,u.full_name,s.school_name,c.course_name,p.profile_link',$where,$options);
		}
		else{
			$my_studymates = array('');
		}
		
		/*----get recommended studymate list---*/
		
		$data['recommended_studymates'] = get_recommended($user_id,$user_group_id);
	
		/*----find studymates-----*/
		$my_studymates[] = $user_id;
		$where = array('where' => array('u.is_delete'=>0),'where_not_in'=>array('u.id' => $my_studymates));
		$options = array('join' => array(
					array(
						'table' => TBL_STUDENT_ACADEMIC_INFO.' in',
						'condition' => 'in.user_id = u.id',
						'join'=>'join'
					),
					array(
						'table' => TBL_SCHOOLS.' s',
						'condition' => 's.id = in.school_id'
					),
					array(
						'table' => TBL_COURSES.' c',
						'condition' => 'c.id = in.course_id'
					),
					array(
						'table' => TBL_USER_PROFILE_PICTURE.' p',
						'condition' => 'u.id = p.user_id'
					),
					array(
						'table' => TBL_STUDYMATES_REQUEST.' sr',
						'condition' => 'sr.request_from_mate_id='.$user_id.' and sr.request_to_mate_id = u.id and sr.is_delete = 0'
					)
				),
				'limit'=>'4,0'
			);
		$data['find_studymates'] = select(TBL_USERS.' u','u.id as user_id,u.full_name,s.school_name,c.course_name,p.profile_link,sr.id as srid,sr.is_delete',$where,$options);

		$this->template->load('student/default','student/studymates',$data);
	}
}
