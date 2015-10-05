<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Group extends ADMIN_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		$this->load->helper(array('file'));		
		$this->load->library(array('zip'));
	}

	// ---------------------------- Group Module Start --------------------------------------------
	
	/**
	  * function index() have all Group listing using codeigniter pagination library limit of 10 groups per page
	  *
	  **/
	 
	public function index() {
		
		$this->data['page_title'] = 'Groups';
		
		if(!empty($_GET['course']) ||  !empty($_GET['year']) ){

			if( !empty($_GET['course'])){ $course  = $this->input->get('course'); }
			if( !empty($_GET['year']) ) { $year = $this->input->get('year'); }
			
			$str = '';

			if(!empty($course)){ $where['where']['course_id'] = $course; $str .='&course='.$course; }
			if(!empty($year)){ 
								$next_year=$year+1; $academic_year = "$year-$next_year";    // find next year and create string like 2015-2016
								$where['where']['student_academic_info.academic_year'] = $academic_year; $str .='year='.$year;  
							}

			$str =  trim($str,'&');

			$config['base_url']	 = base_url().'admin/group/index?'.$str;
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
	
		}else{
			$where=null;
			$config['base_url']	 = base_url().'admin/group/index';
			$offset = $this->uri->segment(4);
		}

		$config['uri_segment'] = 4;
		$config['num_links'] = 5;
		$config['total_rows'] = select(TBL_TUTORIAL_GROUPS,FALSE,$where,
								array('count'=>TRUE,'group_by'=>TBL_TUTORIAL_GROUP_MEMBER.'.group_id','join'=>array(array(
									  'table' => TBL_TUTORIAL_GROUP_MEMBER,
									  'condition' => TBL_TUTORIAL_GROUPS.'.id = '.TBL_TUTORIAL_GROUP_MEMBER.'.group_id',
									  'join'=>'left'
									 ),array(
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
						    				)
								)));
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

	  	$this->data['all_groups'] =   select(TBL_TUTORIAL_GROUPS,
											TBL_TUTORIAL_GROUPS.'.id,'.TBL_TUTORIAL_GROUPS.'.group_name,'.TBL_TUTORIAL_GROUPS.'.group_type,'.
											TBL_TUTORIAL_GROUPS.'.group_status,'.TBL_TUTORIAL_GROUPS.'.is_completed,'.TBL_COURSES.'.course_name,'.
											TBL_COURSES.'.id as course_id',
											$where,
											array(
												'limit'=>$config['per_page'],
												'offset'=>$offset,
												'group_by'=>array(TBL_TUTORIAL_GROUP_MEMBER.'.group_id'),
												'join' =>  array(
											    			array(
											    				'table' => TBL_TUTORIAL_GROUP_MEMBER,
											    				'condition' => TBL_TUTORIAL_GROUPS.'.id = '.TBL_TUTORIAL_GROUP_MEMBER.'.group_id'
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
											    				)
											    			)
												)
											);
	  	//fetch all data of group right joins with tutorial group members
		
		$this->data['all_groups_members'] =   select(TBL_TUTORIAL_GROUPS,
											TBL_TUTORIAL_GROUP_MEMBER.'.id,'.TBL_TUTORIAL_GROUPS.'.group_name,'.TBL_TUTORIAL_GROUPS.'.id as gid,'.
											TBL_USERS.'.username,'.TBL_SCHOOLS.'.school_name,'.TBL_CLASSROOMS.'.class_name,'.TBL_USER_PROFILE_PICTURE.'.profile_link,'.TBL_TUTORIAL_GROUP_MEMBER.'.user_id',
											FALSE,
											array(
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
		
		// /p($this->data['all_groups_members']);
		// echo "------------------------------------------------------------------------";
		//p($config,true);
		// p($this->data['all_groups'],true);

		$this->pagination->initialize($config);
		
		$this->data['schools'] = select(TBL_SCHOOLS,FALSE,array('where'=>array('is_delete'=>FALSE)),array('limit'=>10));
		$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>FALSE)),array('limit'=>10));
		$this->data['roles'] = select(TBL_ROLES,FALSE,array('where'=>array('is_delete'=>FALSE)),array('limit'=>10));
		$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>FALSE)),array('limit'=>10));

		$this->template->load('admin/default','admin/group/view_group',$this->data);
	}

	/**
	 * function Blocked will block user for temporary set database field 'user_status' of `users` table set to 
	 * 'blocked' and redirect to user listing page
	 *
	 **/
	
	public function blocked($id){
		update(TBL_USERS,$id,array('user_status'=>'blocked','modified_date'=>date('Y-m-d H:i:s',time())));
		$this->session->set_flashdata('success', 'User is Successfully Blocked.');
		redirect('admin/user');
	}

	/**
	 * function active will activate user for temporary set database field 'user_status' of `users` table set to 
	 * 'active' and redirect to user listing page
	 *
	 **/
	
	public function active($id){
		update(TBL_USERS,$id,array('user_status'=>'active','modified_date'=>date('Y-m-d H:i:s',time())));
		$this->session->set_flashdata('success', 'User is Successfully Activated.');
		redirect('admin/user');
	}

	// ---------------------------- Group Module END --------------------------------------------

}

/* End of file Group.php */
/* Location: ./application/controllers/Admin/Group.php */