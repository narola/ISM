<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * class Topic 
 * @package default
 * @author Namrata Varma ( Sparks ID- NV )
 **/

class Topic extends ADMIN_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
	}

	/**
	* This function will used to list all the topics allocated for tutorial groups.
	*/
	public function lists(){

		$this->load->library('pagination');
		$role = $this->input->get('role');
		$subject  = $this->input->get('subject');
		$where['where']['tut_topic.is_delete']=0;
		if( !empty($role) || !empty($subject)){

			$str = '';

			if(!empty($role)){ $where['where']['user.role_id'] = $role ; $str .= '&role='.$role; }	
			
			if(!empty($subject)){ $where['where']['tut_topic.subject_id'] = $subject; $str .='&subject='.$subject; }

			$str =  trim($str,'&');

			$config['base_url']	 = base_url().'admin/topic/lists?'.$str;
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'

		}else{
			$config['base_url']	 = base_url().'admin/topic/lists';
			$offset = $this->uri->segment(4);
		}
		
		$config['uri_segment'] = 4;
		$config['num_links'] = 5;
		$config['total_rows'] = select(TBL_TUTORIAL_TOPIC.' tut_topic',FALSE,$where,array('count'=>TRUE,'join'=>array(array('table' => TBL_USERS.' user','condition' => 'tut_topic.created_by = user.id'))));
		$config['per_page'] = 15;

		$config['full_tag_open'] = '<ul class="pagination pagination_admin">';
	  	$config['full_tag_close'] = '</ul>';

	  	$config['num_tag_open'] = '<li>';
	  	$config['num_tag_close'] = '</li>';

	  	$config['first_link'] = 'First';
	  	$config['first_tag_open'] = '<li>';
	  	$config['first_tag_close'] = '</li>';

	  	$config['cur_tag_open'] = '<li style="display:none"></li><li class="active"><a>';
	  	$config['cur_tag_close'] = '</a></li><li style="display:none"></li>';

	  	$config['prev_link'] = '&laquo;';
	  	$config['prev_tag_open'] = '<li>';
	  	$config['prev_tag_close'] = '</li>';

		$config['next_link'] = '&raquo;';
	  	$config['next_tag_open'] = '<li>';
	  	$config['next_tag_close'] = '</li>';

	  	$config['last_link'] = 'Last';
	  	$config['last_tag_open'] = '<li>';
	  	$config['last_tag_close'] = '</li>';

		$this->data['all_topics'] = select(TBL_TUTORIAL_TOPIC.' tut_topic',
											'tut_topic.id,tut_topic.topic_name,tut_topic.is_archived,tut_topic.status,tut_topic.topic_description,tut_topic.allocation_count,tut_topic.classroom_id,tut_topic.subject_id, tut_topic.created_by,sub.subject_name,class.class_name,user.first_name,user.last_name,user.role_id,count(quest.question_id) as questions_count',
											$where,
											array(
												'limit'=>$config['per_page'],
												'offset'=>$offset,
												'join' =>  array(
											    			array(
											    				'table' => TBL_SUBJECTS.' sub',
											    				'condition' => 'sub.id = tut_topic.subject_id',
																),
											    			array(
											    				'table' => TBL_CLASSROOMS.' class',
											    				'condition' => 'class.id = tut_topic.classroom_id',
																),
											    			array(
											    				'table' => TBL_USERS.' user',
											    				'condition' => 'user.id = tut_topic.created_by',
																),
											    			array(
											    				'table' => TBL_TUTORIAL_GROUP_QUESTION.' quest',
											    				'condition' => 'quest.tutorial_topic_id = tut_topic.id',
																)
												    		),
												'group_by'=>'tut_topic.id'
												)
											);
		$this->pagination->initialize($config);

		$this->data['roles'] = select(TBL_ROLES,FALSE,FALSE,null);
		$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,FALSE,null);

		$this->data['page_title'] = 'Topics';
		$this->template->load('admin/default','admin/topic/list', $this->data);
	}

	/**
	* ajax function to allocate topic to groups 
	*/
	public function allocate($unallocated = null){

		$ddate = date('Y', time());
		
		$date = new DateTime($ddate);
		$week = $date->format("W");
		
		$date1 = new DateTime('2015-09-24 10:18:40');
		$year = $date1->format('Y');

		$where['where']['tut_topic.week_no'] = $week;
		$where['where']['YEAR(tut_topic.created_date)'] = $year;

		$allocated_groups = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' tut_topic',
			'tut_topic.group_id',
			$where
			);

		$allocated_group_ids = array_column($allocated_groups, 'group_id');
		
		// echo 'assigned for the current week<br/>';
		// p($allocated_group_ids);

		$where  = array('where_not_in' => array(TBL_TUTORIAL_GROUPS.'.id' => $allocated_group_ids),
			'where'=> array(TBL_TUTORIAL_GROUPS.'.is_completed'=>1,
				TBL_TUTORIAL_GROUPS.'.group_type'=>'tutorial group'
				)) ;

		/*$unallocated_groups = select(TBL_TUTORIAL_GROUPS.' grp',
			'grp.id',
			$where
			);*/

		$unallocated_groups = select(TBL_TUTORIAL_GROUPS,
											TBL_TUTORIAL_GROUPS.'.id,'.TBL_TUTORIAL_GROUPS.'.group_name,'.TBL_TUTORIAL_GROUPS.'.group_type,'.
											TBL_TUTORIAL_GROUPS.'.group_status,'.TBL_TUTORIAL_GROUPS.'.is_completed,'.TBL_COURSES.'.course_name,'.
											TBL_COURSES.'.id as course_id,'.TBL_USERS.'.username,'.TBL_SCHOOLS.'.school_name',
											$where,
											array(
												//'limit'=>$config['per_page'],
												//'offset'=>$offset,
												'group_by'=>array(TBL_TUTORIAL_GROUP_MEMBER.'.group_id'),
												'join' =>  array(
											    			array(
											    				'table' => TBL_TUTORIAL_GROUP_MEMBER,
											    				'condition' => TBL_TUTORIAL_GROUPS.'.id = '.TBL_TUTORIAL_GROUP_MEMBER.'.group_id',
											    				'join'=>'right'
											    				),
											    			array(
											    				'table' => TBL_USERS,
											    				'condition' => TBL_USERS.'.id = '.TBL_TUTORIAL_GROUP_MEMBER.'.user_id',
											    				),
											    			array(
											    				'table' => TBL_STUDENT_ACADEMIC_INFO,
											    				'condition' => TBL_USERS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.user_id',
											    				),
											    			array(
											    				'table' => TBL_COURSES,
											    				'condition' => TBL_COURSES.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.course_id',
											    				),
											    			array(
											    				'table' => TBL_SCHOOLS,
											    				'condition' => TBL_SCHOOLS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.school_id',
											    				),
											    			)
												)
											);
	
	// qry();	
	// p($unallocated_groups,true);

	$this->data['groups'] = $unallocated_groups;


		//fetch all data of group right joins with tutorial group members
		$this->data['all_groups_members'] =   select(TBL_TUTORIAL_GROUPS,
											TBL_TUTORIAL_GROUP_MEMBER.'.id,'.TBL_TUTORIAL_GROUPS.'.group_name,'.TBL_TUTORIAL_GROUPS.'.id as gid,'.
											TBL_USERS.'.username,'.TBL_SCHOOLS.'.school_name,'.TBL_CLASSROOMS.'.class_name,'.
											TBL_USER_PROFILE_PICTURE.'.profile_link,'.TBL_TUTORIAL_GROUP_MEMBER.'.user_id',
											FALSE,
											array(
												'join' =>  array(
											    			array(
											    				'table' => TBL_TUTORIAL_GROUP_MEMBER,
											    				'condition' => TBL_TUTORIAL_GROUPS.'.id = '.TBL_TUTORIAL_GROUP_MEMBER.'.group_id',
											    				),
											    			array(
											    				'table' => TBL_USERS,
											    				'condition' => TBL_USERS.'.id = '.TBL_TUTORIAL_GROUP_MEMBER.'.user_id',
											    				),
											    			array(
											    				'table' => TBL_STUDENT_ACADEMIC_INFO,
											    				'condition' => TBL_USERS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.user_id',
											    				),
											    			array(
											    				'table' => TBL_SCHOOLS,
											    				'condition' => TBL_SCHOOLS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.school_id',
											    				),
											    			array(
											    				'table' => TBL_CLASSROOMS,
											    				'condition' => TBL_CLASSROOMS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.classroom_id',
											    				),
											    			array(
											    				'table'=>TBL_USER_PROFILE_PICTURE,
											    				'condition'=>TBL_USER_PROFILE_PICTURE.'.id='.TBL_TUTORIAL_GROUP_MEMBER.'.user_id'
											    				)
											    			)
												)
											);

		$unallocated_group_ids = array_column($unallocated_groups, 'id');


		// echo 'other than assigned<br/>';
		// p($unallocated_group_ids);

		if($unallocated == null){
			$unallocated = current($unallocated_group_ids);
		}
		echo $unallocated;

		$last_week = $week-1;
		$where  = array('where' => array('tut_topic.week_no' => $last_week,
			'YEAR(tut_topic.created_date)' => $year
			),
		'where_in'=>array('tut_topic.group_id'=> $unallocated_group_ids)
		);
		
		$last_week_groups = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' tut_topic',
			'tut_topic.group_id,tut_topic.topic_id',
			$where
			);
		
		// echo 'assigned last week<br/>';
		// p($last_week_groups);

		//foreach ($unallocated_group_ids as $unallocated) {
			// echo 'out '.$key." ".$unallocated;

			if(in_array($unallocated, array_column($last_week_groups, 'group_id'))){

				$key = array_search($unallocated, array_column($last_week_groups, 'group_id'));
				$last_week_topic = $last_week_groups[$key]['topic_id'];
				$where = array('where' => array('tut_topic.id'=>$last_week_topic));
				$subject = select(TBL_TUTORIAL_TOPIC.' tut_topic',
				'tut_topic.subject_id',
					$where, array('single'=>true)
				);
				// echo 'subject ids<br/>';
				// p($subject);
				$where = array('where'=>array('tut_course.subject_id'=>$subject['subject_id'],
					));
				$course = select(TBL_COURSE_SUBJECT.' tut_course',
				'tut_course.course_id',
					$where, array('single'=>true)
				);
				// p($course);

				$where = array('where'=>array('tut_course.course_id'=>$course['course_id']
												// 'tut_course.subject_id !='=>$subject['subject_id']
					));

				$options = array('order_by'=>'RAND()','single'=>true,'limit'=>1,
					'join' =>  array(
					    			array(
					    				'table' => TBL_TUTORIAL_TOPIC.' tut_topic',
					    				'condition' => 'tut_topic.subject_id = tut_course.subject_id',
										)
								)
							);

				$random_subject = select(TBL_COURSE_SUBJECT.' tut_course',
				'tut_course.subject_id',
					$where, $options
				);

				// echo 'subject';
				// p($random_subject);
				$random_subject_id = $random_subject['subject_id'];
				

			}else{
				
				$where = array('where'=>array('tut_grp_member.group_id'=>$unallocated));

				$options = array('limit'=>1,'single'=>true);
				$group_member = select(TBL_TUTORIAL_GROUP_MEMBER.' tut_grp_member',
				'tut_grp_member.user_id',
					$where, $options
				);
				
				$where = array('where'=>array('tut_stud_info.user_id'=>$group_member['user_id']));

				$options = array('single'=>true);
				$course_info = select(TBL_STUDENT_ACADEMIC_INFO.' tut_stud_info',
				'tut_stud_info.course_id',
					$where, $options
				);
				// p($course_info);
				$where = array('where'=>array('tut_course.course_id'=>$course_info['course_id']
					));

				$options = array('order_by'=>'RAND()','single'=>true,'limit'=>1,
					'join' =>  array(
					    			array(
					    				'table' => TBL_TUTORIAL_TOPIC.' tut_topic',
					    				'condition' => 'tut_topic.subject_id = tut_course.subject_id',
										)
								)
							);

				$random_subject_info = select(TBL_COURSE_SUBJECT.' tut_course',
				'tut_course.subject_id',
					$where, $options
				);
				
				// p($random_subject_info);
				$random_subject_id = $random_subject_info['subject_id'];

			}
			$where = array('where'=>array('tut_topic.subject_id'=>$random_subject_id));

				// $options = array('order_by'=>'RAND()','limit'=>3);
				/*$random_topics = select(TBL_TUTORIAL_TOPIC.' tut_topic',
				'tut_topic.id',
					$where, $options
				);*/
				$this->data['recommended_topics'] = select(TBL_TUTORIAL_TOPIC.' tut_topic',
											'tut_topic.id,tut_topic.topic_name,tut_topic.is_archived,tut_topic.status,tut_topic.topic_description,tut_topic.allocation_count,tut_topic.classroom_id,tut_topic.subject_id, tut_topic.created_by,sub.subject_name,class.class_name',
											$where,
											array(
												'limit'=>3,
												'order_by'=>'RAND()',
												'join' =>  array(
											    			array(
											    				'table' => TBL_SUBJECTS.' sub',
											    				'condition' => 'sub.id = tut_topic.subject_id',
																),
											    			array(
											    				'table' => TBL_CLASSROOMS.' class',
											    				'condition' => 'class.id = tut_topic.classroom_id',
																),
											    			
											    			
												    		),
												// 'group_by'=>'tut_topic.id'
												)
											);

				//$random_topic_ids = array_column($random_topics,'id');
				// echo 'unallocated: '.$unallocated.'<br/>';
				//p($random_topic_ids);
				// p($this->data);
		//}
		// exit;

		$this->data['page_title'] = 'Allocate Topic';
		$this->template->load('admin/default','admin/topic/allocate', $this->data);
	}

	/**
	* ajax function to save the status of the topic 
	*/
	public function set_topic_status(){
		$status = $this->input->post('status');
		$topic_id = $this->input->post('topic_id');
		$data=array(
				 "status"=>$status
				 );
		update('tutorial_topic',$topic_id,$data);	// Update data  using common_model.php and cms_helper.php
		
		$response = array('topic_status'=>$status,
			'html'=>'set_status_'.$topic_id
			);
		echo json_encode($response);
		exit; 

	}

	/**
	* ajax function to archive the topic 
	*/
	public function archive_topic(){
		$curr = $this->input->post('is_archive');
		$new = ($curr == 0) ? 1 : 0;

		$topic_id = $this->input->post('topic_id');
		$data=array(
				 "is_archived"=>$new
				 );
		update('tutorial_topic',$topic_id,$data);	// Update data  using common_model.php and cms_helper.php
		
		$response = array('status'=>$new,
			'id'=>'archive_'.$topic_id
			);
		echo json_encode($response);
		exit;
	}

	/**
	* function to add new topic
	*/
	public function add(){
		$this->data['page_title'] = 'Add New Topic';
		$this->template->load('admin/default','admin/topic/add', $this->data);
	}


}