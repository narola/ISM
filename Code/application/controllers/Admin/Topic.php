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
		$this->load->library('pagination');
		$this->data['cur_url'] = $this->session->userdata('cur_url');
		$this->data['prev_url'] = $this->session->userdata('prev_url');
	}

	/**
	* This function will used to list all the topics allocated for tutorial groups.
	*/
	public function lists(){

		$role = $this->input->get('role');
		$subject  = $this->input->get('subject');
		$q  = $this->input->get('q');
		$where['where']['tut_topic.is_delete']=0;

		if( !empty($role) || !empty($subject) || !empty($q) || !empty($_GET['order']) ){

			$str = '';

			if(!empty($role)){ $where['where']['user.role_id'] = $role ; $str .= '&role='.$role; }	
			if(!empty($subject)){ $where['where']['tut_topic.subject_id'] = $subject; $str .='&subject='.$subject; }
			if(!empty($q)){ 
					$where['like']['tut_topic.topic_name'] = $q; 
					// $where['or_like']['tut_topic.topic_description'] = $q;
					// $where['or_like']['tut_topic.evaluation_keywords'] = $q;  
					$str .='&q='.$q; 
			}
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		

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
											'tut_topic.id,tut_topic.topic_name,tut_topic.is_archived,tut_topic.status,tut_topic.topic_description,
											tut_topic.allocation_count,tut_topic.classroom_id,tut_topic.subject_id, 
											tut_topic.created_by,sub.subject_name,class.class_name,user.first_name,user.last_name,user.role_id,
											count(quest.question_id) as questions_count,tte.exam_id',
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
																),
											    			array(
											    				'table' => TBL_TUTORIAL_TOPIC_EXAM.' tte',
											    				'condition' => 'tte.tutorial_topic_id = tut_topic.id',
																)
												    		),
												'group_by'=>'tut_topic.id'
												)
											);
		
		// p($this->data['all_topics'],true);
		// qry(true);

		$topic_exams = select(TBL_TUTORIAL_TOPIC_EXAM);

		$not_in = array();
		foreach($topic_exams as $te){
			array_push($not_in,$te['tutorial_topic_id']);
		}
		
		$this->data['not_in'] = $not_in;

		$this->pagination->initialize($config);

		$this->data['roles'] = select(TBL_ROLES,FALSE,array('where'=>array('is_delete'=>'0')),null);
		$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('is_delete'=>'0')),null);

		$this->data['page_title'] = 'Topics';
		$this->template->load('admin/default','admin/topic/list', $this->data);
	}

	/**
	* ajax function to allocate topic to groups 
	*/
	public function allocate($unallocated = null){

		// get the current week of the current year
		$year = date('Y', time());
		$date = new DateTime($year);
		$week = $date->format("W");
		// save the allocated topic in db
		if($_POST){

			$gid = $this->input->post('group_id');
			$tid = $this->input->post('topic_id');
			$tutorial_data = array('group_id'=>$gid,'interface_type'=>'','date_day'=>'','week_no'=>$week,'status'=>'',
								   'topic_id'=>$tid,'group_score'=>0,'created_date'=>date('Y-m-d H:i:s'),
								   'modified_date'=>'0000-00-00 00:00:00','is_delete'=>'0','is_testdata'=>'yes');
			insert(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION,$tutorial_data);
			
			$members = select(TBL_TUTORIAL_GROUP_MEMBER,TBL_TUTORIAL_GROUP_MEMBER.'.id',
				array('where'=>array(TBL_TUTORIAL_GROUP_MEMBER.'.group_id'=>59))
				);
			$member_ids = array_column($members,'id');
			foreach ($member_ids as $member) {
				$member_data = array('topic_id'=>$tid,
					'score'=>0,
					'member_id'=>$member
					);
				insert(TBL_TUTORIAL_GROUP_MEMBER_SCORE,$member_data);
			}

			$this->session->set_flashdata('success', 'Topic has beed allocated to group.');
			redirect('admin/topic/allocate');

		}

		
		$where['where']['tut_topic.week_no'] = $week;
		$where['where']['YEAR(tut_topic.created_date)'] = $year;

		$allocated_groups = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' tut_topic',
			'tut_topic.group_id',
			$where
			);

		$allocated_group_ids = array_column($allocated_groups, 'group_id');
		
       	$where  = array('where'=> array(TBL_TUTORIAL_GROUPS.'.is_completed'=>1,
					TBL_TUTORIAL_GROUPS.'.group_type'=>'tutorial group',
				)) ;

		if(!empty($allocated_group_ids)){
			$where['where_not_in'] = array(TBL_TUTORIAL_GROUPS.'.id' => $allocated_group_ids);
		}

		$str = '';

		if($_GET){
			if(!empty($_GET['course_id']) || !empty($_GET['classroom_id']) || !empty($_GET['group_id']) ){
				
				if(!empty($_GET['course_id'])){ 
						$course_id = $_GET['course_id']; 
						$where['where'][TBL_CLASSROOMS.'.course_id']=$course_id;
						$str .= '&course_id='.$course_id; 
					}

				if(!empty($_GET['classroom_id'])){ 
						$classroom_id = $_GET['classroom_id']; 
						$where['where'][TBL_CLASSROOMS.'.id']=$classroom_id; 
						$str .= '&classroom_id='.$classroom_id; 
				}

				if(!empty($_GET['group_id'])){
						$group_id = $_GET['group_id']; 
						$where['where'][TBL_TUTORIAL_GROUPS.'.id']=$group_id;
						$str .= '&group_id='.$group_id;  
				}	

				$str = trim($str,'&');
			}
		}

		$config['base_url'] = base_url() . 'admin/topic/allocate?'.$str;
		$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
        $config['uri_segment'] = 4;
        $config['num_links'] = 5;
        $config['total_rows'] = sizeof(select(TBL_TUTORIAL_GROUPS, TBL_TUTORIAL_GROUPS . '.id,' . TBL_TUTORIAL_GROUPS . '.group_name,' . TBL_TUTORIAL_GROUPS . '.group_type,' .
                TBL_TUTORIAL_GROUPS . '.group_status,'. TBL_CLASSROOMS . '.class_name,' . TBL_TUTORIAL_GROUPS . '.is_completed,'. TBL_TUTORIAL_GROUPS . '.is_delete,' . TBL_COURSES . '.course_name,' .
                TBL_COURSES . '.id as course_id, sum(' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_score) as score, count(' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_id) as exams', $where, array(
	            'group_by' => array(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_id'),
	            'join' => array(
				                array(
				                    'table' => TBL_CLASSROOMS,
				                    'condition' => TBL_CLASSROOMS . '.id = ' . TBL_TUTORIAL_GROUPS . '.classroom_id',
				                ),
				                array(
				                    'table' => TBL_COURSES,
				                    'condition' => TBL_COURSES . '.id = ' . TBL_CLASSROOMS . '.course_id',
				                ),
				                array(
				                    'table' => TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION,
				                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_id'
				                )
	            			)
             		)
        		));

        $config['per_page'] = 10;
		$offset = $this->input->get('per_page');

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
		
		// ------------------------------------------------------------------------

		$unallocated_groups = select(TBL_TUTORIAL_GROUPS, TBL_TUTORIAL_GROUPS . '.id,' . TBL_TUTORIAL_GROUPS . '.group_name,' . TBL_TUTORIAL_GROUPS . '.group_type,' .
                TBL_TUTORIAL_GROUPS . '.group_status,'. TBL_CLASSROOMS . '.class_name,' . TBL_TUTORIAL_GROUPS . '.is_completed,'. TBL_TUTORIAL_GROUPS . '.is_delete,' . TBL_COURSES . '.course_name,' .
                TBL_COURSES . '.id as course_id, sum(' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_score) as score, count(' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_id) as exams', $where, array(
	            'limit' => $config['per_page'],
	            'offset' => $offset,
	            'group_by' => array(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_id'),
	            'join' => array(
	                array(
	                    'table' => TBL_CLASSROOMS,
	                    'condition' => TBL_CLASSROOMS . '.id = ' . TBL_TUTORIAL_GROUPS . '.classroom_id',
	                ),
	                array(
	                    'table' => TBL_COURSES,
	                    'condition' => TBL_COURSES . '.id = ' . TBL_CLASSROOMS . '.course_id',
	                ),
	                array(
	                    'table' => TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION,
	                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_id'
	                )
	            )
              )
        );	
		
		// p($unallocated_groups,true);
		
		$this->data['groups'] = $unallocated_groups;

		// ------------------------------------------------------------------------

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
											    				'condition'=>TBL_USER_PROFILE_PICTURE.'.user_id='.TBL_TUTORIAL_GROUP_MEMBER.'.user_id'
											    				)
											    			),
											)
											);

		$this->pagination->initialize($config);


		$unallocated_group_ids = array_column($unallocated_groups, 'id');
	
		if($unallocated == null){
			$unallocated = current($unallocated_group_ids);
		}
		
		$this->data['unallocated_group'] = $unallocated;

		$last_week = $week-1;
		$where  = array('where' => array('tut_topic.week_no' => $last_week,
			'YEAR(tut_topic.created_date)' => $year
			));
		if(!empty($allocated_group_ids)){
		$where['where_in'] = array('tut_topic.group_id'=> $allocated_group_ids);
		}
		
		$last_week_groups = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' tut_topic',
			'tut_topic.group_id,tut_topic.topic_id',
			$where
			);
		
			if(in_array($unallocated, array_column($last_week_groups, 'group_id'))){

				$key = array_search($unallocated, array_column($last_week_groups, 'group_id'));
				$last_week_topic = $last_week_groups[$key]['topic_id'];

				$where = array('where' => array('tut_topic.id'=>$last_week_topic));
				$subject = select(TBL_TUTORIAL_TOPIC.' tut_topic',
				'tut_topic.subject_id,tut_topic.classroom_id',
					$where, array('single'=>true)
				);
				

				$where = array('where'=>array('tut_course.classroom_id'=>$subject['classroom_id']));

				$options = array('order_by'=>'RAND()','single'=>true,'limit'=>1,
					'join' =>  array(
					    			array(
					    				'table' => TBL_TUTORIAL_TOPIC.' tut_topic',
					    				'condition' => 'tut_topic.subject_id = tut_course.subject_id',
										)
								)
							);
				$random_subject = select(TBL_CLASSROOM_SUBJECT.' tut_course',
				'tut_course.subject_id',
					$where, $options
				);
				

				$random_subject_id = $random_subject['subject_id'];

			}else{
				
				$where = array('where'=>array('tut_grp.id'=>$unallocated));

				$options = array('limit'=>1,'single'=>true);
				$group_classroom = select(TBL_TUTORIAL_GROUPS.' tut_grp',
				'tut_grp.classroom_id',
					$where, $options
				);
				
				$where = array('where'=>array('tut_course.classroom_id'=>$group_classroom['classroom_id']
					));

				$options = array('order_by'=>'RAND()','single'=>true,'limit'=>1,
					'join' =>  array(
					    			array(
					    				'table' => TBL_TUTORIAL_TOPIC.' tut_topic',
					    				'condition' => 'tut_topic.subject_id = tut_course.subject_id',
										)
								)
							);

				$random_subject_info = select(TBL_CLASSROOM_SUBJECT.' tut_course',
				'tut_course.subject_id',
					$where, $options
				);
				
		
				$random_subject_id = $random_subject_info['subject_id'];

			}
			$where = array('where'=>array('tut_topic.subject_id'=>$random_subject_id));

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
												)
											);

		$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>0)));

		if(!empty($_GET['course_id']) || !empty($_GET['classroom_id']) || !empty($_GET['group_id']) ){
			$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>0,TBL_CLASSROOMS.'.course_id'=>$_GET['course_id'])));
			$this->data['all_groups'] = select(TBL_TUTORIAL_GROUPS,TBL_TUTORIAL_GROUPS.'.id,'.TBL_TUTORIAL_GROUPS.'.group_name',
												array('where'=>array('classroom_id'=>$_GET['classroom_id']))
				
			);
		}else{
			$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>0)));

		}
		
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
		
		// if($_POST){
		// 	p($_POST);
		// 	die();
		// }		

		//Validation Set For Add Topic Following fields are required and topic name allow some character only
		 // $this->form_validation->set_rules('topic_name', 'Topic Name', 'trim|required|regex_match[/[a-zA-Z& ]$/]', 
		 // 	array('regex_match' => 'The {field} should have only characters,numbers and & special character only.'));
		
		$this->form_validation->set_rules('topic_name', 'Topic Name', 'trim|required|regex_match[/[a-zA-Z& ]$/]', 
		array('regex_match' => 'The {field} should have only characters,numbers and & special character only.'));
		$this->form_validation->set_rules('keywords', 'Keywords', 'trim|required');
		$this->form_validation->set_rules('subjects', 'Subject', 'trim|required');
		$this->form_validation->set_rules('course_id', 'Course', 'trim|required');
		$this->form_validation->set_rules('classrooms', 'Classroom', 'trim|required');
		$this->form_validation->set_rules('topic_id', 'Topic', 'trim|required');
		$this->form_validation->set_rules('topic_name', 'Topic Name', 'trim|required');
		
		if($_POST){

			$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>'0')),null); // Fetch All Courses From Database
			
			if(!empty($_POST['course_id'])){
				$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>'0',
											  'course_id'=>$_POST['course_id'])),null);
			}else{
				$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>'0')));
			}
			
			if(!empty($_POST['classrooms'])){

				$this->data['subjects'] = select(TBL_CLASSROOM_SUBJECT,
												 TBL_CLASSROOM_SUBJECT.'.subject_id as id,sub.subject_name ',
												 array('where'=>array(TBL_CLASSROOM_SUBJECT.'.classroom_id'=>$_POST['classrooms'],
												 					  'sub.is_delete'=>'0')),
													array(
														'join'=>array(
																	array(
														    				'table' => TBL_SUBJECTS.' sub',
														    				'condition' => 'sub.id = '.TBL_CLASSROOM_SUBJECT.'.subject_id',
																		)
																	)
														)
											 	);
			}else{
				$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('is_delete'=>'0')));
			}

			if(!empty($_POST['subjects'])){
				$this->data['topics'] = select(TBL_TOPICS,FALSE,array('where'=>array('is_delete'=>'0',
										  'subject_id'=>$_POST['subjects'])));
			}else{
				$this->data['topics'] = select(TBL_TOPICS,FALSE,array('where'=>array('is_delete'=>'0')));
			}	

		}else{

			$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>'0'))); // Fetch All Courses From Database
			$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>'0')));
			$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('is_delete'=>'0')));
			$this->data['topics'] = select(TBL_TOPICS,FALSE,array('where'=>array('is_delete'=>'0')));
		}

		$this->data['page_title'] = 'Add New Topic'; // Set Page Title

		if($this->form_validation->run() == FALSE){
			$this->template->load('admin/default','admin/topic/add', $this->data);
		}else{

			$data=array(
				 "topic_name"=>$this->input->post("topic_name"),
				 "parent_id"=>0,
				 "topic_description"=>htmlentities($this->input->post("topic_desc")),
				 "subject_id"=>$this->input->post("subjects"),
				 "topic_id"=>$this->input->post('topic_id'),
				 "evaluation_keywords"=>$this->input->post("keywords"),
				 "created_by"=>$this->session->userdata('id'),
				 "classroom_id"=>$this->input->post('classrooms'),
				 "allocation_count"=>0,
				 "status"=>"",
				 "topic_day"=>"Mon",
				 "is_archived"=>0
				);

			insert(TBL_TUTORIAL_TOPIC,$data);

			$this->session->set_flashdata('success','Topic has been created.');
			
			if(isset($_POST['save'])){
				redirect('admin/topic/lists');
			}else{
				redirect('admin/topic/add');
			}

		}		
	}

	/**
	* function to Update Selected topic
	*/
	
	public function update($id){
		
		$this->data['page_title'] = 'Update Topic'; // Set Page Title
		
		//Validation Set For Add Topic Following fields are required and topic name allow some character only
		$this->form_validation->set_rules('topic_name', 'Topic Name', 'trim|required|regex_match[/[a-zA-Z& ]$/]', 
		array('regex_match' => 'The {field} should have only characters,numbers and & special character only.'));
		$this->form_validation->set_rules('keywords', 'Keywords', 'trim|required');
		$this->form_validation->set_rules('subjects', 'Subject', 'trim|required');
		$this->form_validation->set_rules('course_id', 'Course', 'trim|required');
		$this->form_validation->set_rules('classrooms', 'Classroom', 'trim|required');
		$this->form_validation->set_rules('topic_id', 'Topic', 'trim|required');
		$this->form_validation->set_rules('topic_name', 'Topic Name', 'trim|required');

		$this->data['tutorial_topic'] = select(
												TBL_TUTORIAL_TOPIC.' tutorial_topic',
												'tutorial_topic.id,tutorial_topic.topic_name,tutorial_topic.topic_description,
												tutorial_topic.classroom_id,tutorial_topic.subject_id,tutorial_topic.topic_id,
												tutorial_topic.evaluation_keywords,classroom.course_id',
												array('where'=>array('tutorial_topic.is_delete'=>'0','tutorial_topic.id'=>$id)),
												array(
													'single'=>TRUE,
													'join'=>array(
																array(
																	'table'=>TBL_CLASSROOMS.' as classroom',
																	'condition'=>'tutorial_topic.classroom_id=classroom.id'
																)	
														)
												)
											);
		
		if($_POST){

			$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>'0')),null); // Fetch All Courses From Database
			
			if(!empty($_POST['course_id'])){
				$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>'0',
											  'course_id'=>$_POST['course_id'])),null);
			}else{
				$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>'0')));
			}
			
			if(!empty($_POST['classrooms'])){

				$this->data['subjects'] = select(TBL_CLASSROOM_SUBJECT,
												 TBL_CLASSROOM_SUBJECT.'.subject_id as id,sub.subject_name ',
												 array('where'=>array(TBL_CLASSROOM_SUBJECT.'.classroom_id'=>$_POST['classrooms'],
												 					  'sub.is_delete'=>'0')),
													array(
														'join'=>array(
																	array(
														    				'table' => TBL_SUBJECTS.' sub',
														    				'condition' => 'sub.id = '.TBL_CLASSROOM_SUBJECT.'.subject_id',
																		)
																	)
														)
											 	);
			}else{
				$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('is_delete'=>'0')));
			}

			if(!empty($_POST['subjects'])){
				$this->data['topics'] = select(TBL_TOPICS,FALSE,array('where'=>array('is_delete'=>'0',
										  'subject_id'=>$_POST['subjects'])));
			}else{
				$this->data['topics'] = select(TBL_TOPICS,FALSE,array('where'=>array('is_delete'=>'0')));
			}	

		}else{

			$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>'0')),null); // Fetch All Courses From Database
			
			$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>'0',
											  'course_id'=>$this->data['tutorial_topic']['course_id'])),null);

			$this->data['subjects'] = select(TBL_CLASSROOM_SUBJECT,
												 TBL_CLASSROOM_SUBJECT.'.subject_id as id,sub.subject_name ',
												 array('where'=>array(TBL_CLASSROOM_SUBJECT.'.classroom_id'=>$this->data['tutorial_topic']['classroom_id'],
												 					  'sub.is_delete'=>'0')),
													array(
														'join'=>array(
																	array(
														    				'table' => TBL_SUBJECTS.' sub',
														    				'condition' => 'sub.id = '.TBL_CLASSROOM_SUBJECT.'.subject_id',
																		)
																	)
														)
											 	);

			$this->data['topics'] = select(TBL_TOPICS,FALSE,array('where'=>array('is_delete'=>'0',
										  'subject_id'=>$this->data['tutorial_topic']['subject_id'])),null);
		}
		
		if($this->form_validation->run() == FALSE){
			
			$this->template->load('admin/default','admin/topic/edit', $this->data);

		}else{
			
			$data=array(
				 "topic_name"=>$this->input->post("topic_name"),
				 "parent_id"=>0,
				 "topic_description"=>htmlentities($this->input->post("topic_desc")),
				 "subject_id"=>$this->input->post("subjects"),
				 "topic_id"=>$this->input->post('topic_id'),
				 "evaluation_keywords"=>$this->input->post("keywords"),
				 "created_by"=>$this->session->userdata('id'),
				 "classroom_id"=>$this->input->post('classrooms'),
				 "modified_date"=>date('Y-m-d H:i:s')
				);

			update(TBL_TUTORIAL_TOPIC,$id,$data);

			$this->session->set_flashdata('success','Topic has been Updated.');
			
			if(isset($_POST['save'])){
				redirect('admin/topic/lists');
			}else{
				redirect('admin/topic/add');
			}

		}		
	}

	/**
	* function to Delete Selected topic
	*/
	
	public function delete($id){
		update(TBL_TUTORIAL_TOPIC,$id,array('is_delete'=>TRUE));
		$this->session->set_flashdata('success', 'Topic has been deleted Successfully.');
		redirect('admin/topic/lists');
	}

	// ----------------------  AJAX FUNCTIONS --------------------------------------------------
	
	/*
	* function to get the classrooms through the course.
	*/
	public function ajax_get_classrooms(){
		$course_id = $this->input->post('course_id');
		
		$classrooms = select(TBL_CLASSROOMS,TBL_CLASSROOMS.'.id,'.TBL_CLASSROOMS.'.class_name ',
			array('where'=>array(TBL_CLASSROOMS.'.course_id'=>$course_id))
			);

		
		$new_str = '';
		
		$new_str .= '<option selected value="" disabled >Select Classroom</option>';
		if(!empty($classrooms)){
			foreach($classrooms as $classroom){
				$new_str.='<option value="'.$classroom['id'].'">'.$classroom['class_name'].'</option>';
			}	
		}
		echo $new_str;
	}
	/**
	* ajax function to fetch Subjects from Classroom 
	*/
	public function ajax_get_subjects(){
		$course_id = $this->input->post('course_id');
		
		$subjects = select(TBL_CLASSROOM_SUBJECT,TBL_CLASSROOM_SUBJECT.'.subject_id,sub.subject_name ',
			array('where'=>array('course_id'=>$course_id)),
				array(
					'join'=>array(
								array(
					    				'table' => TBL_SUBJECTS.' sub',
					    				'condition' => 'sub.id = '.TBL_CLASSROOM_SUBJECT.'.subject_id',
									)
								)
					)
			);

		
		$new_str = '';
		
		$new_str .= '<option selected disabled >Select Subject</option>';
		if(!empty($subjects)){
			foreach($subjects as $subject){
				$new_str.='<option value="'.$subject['subject_id'].'">'.$subject['subject_name'].'</option>';
			}	
		}
		echo $new_str;
	}
	/**
	* ajax function to fetch groups from Classroom 
	*/
	public function ajax_get_groups(){
		$classroom_id = $this->input->post('classroom_id');
		
		$groups = select(TBL_TUTORIAL_GROUPS,TBL_TUTORIAL_GROUPS.'.id,'.TBL_TUTORIAL_GROUPS.'.group_name',
			array('where'=>array('classroom_id'=>$classroom_id))
				
			);

		
		$new_str = '';
		
		$new_str .= '<option selected disabled >Select Group</option>';
		if(!empty($groups)){
			foreach($groups as $group){
				$new_str.='<option value="'.$group['id'].'">'.$group['group_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	/**
	* ajax function to fetch Topics from TOPIC Table not from tutorial topic
	*/
	public function ajax_get_topics(){

		$subject_id = $this->input->post('subject_id');
		$topics = select(TBL_TOPICS,FALSE,array('where'=>array('subject_id'=>$subject_id)));

		$new_str = '';		
		$new_str .= '<option selected disabled >Select Topic</option>';

		if(!empty($topics)){
			foreach($topics as $topic){
				$new_str.='<option value="'.$topic['id'].'">'.$topic['topic_name'].'</option>';
			}	
		}
		echo $new_str;
	}

    /**
	* ajax function to delete the topic 
	*/
	public function delete_topic(){
		$topic_id = $this->input->post('topic_id');
        $is_delete = 1;
		$data=array(
				 "is_delete"=> $is_delete
				 );
		update('tutorial_topic',$topic_id,$data);	// Update data  using common_model.php and cms_helper.php
		
		$response = array('is_delete'=>$is_delete,'id'=>'delete_'.$topic_id);
		echo json_encode($response);
		exit; 
	}

	public function next_phase(){
		$this->data['page_title'] = 'Coming Soon';
		$this->template->load('admin/default','admin/next_phase', $this->data);
	}

}