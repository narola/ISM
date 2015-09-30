<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * class Topic 
 * 	
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
		
		if( !empty($role) || !empty($subject)){

			$str = '';

			if(!empty($role)){ $where['where']['user.role_id'] = $role ; $str .= '&role='.$role; }	
			
			if(!empty($subject)){ $where['where']['tut_topic.subject_id'] = $subject; $str .='&subject='.$subject; }

			$str =  trim($str,'&');

			$config['base_url']	 = base_url().'admin/topic/lists?'.$str;
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'

		}else{
			$where=null;
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

	  	$config['cur_tag_open'] = ' <li style="display:none"></li><li class="active"><a>';
	  	$config['cur_tag_close'] = '</a></li>';

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
											'tut_topic.id,tut_topic.topic_name,tut_topic.topic_description,tut_topic.allocation_count,tut_topic.classroom_id,tut_topic.subject_id, tut_topic.created_by,sub.subject_name,class.class_name,user.first_name,user.last_name,user.role_id,count(quest.question_id) as questions_count',
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
// p($this->data);
// qry(true);
		$this->pagination->initialize($config);

		$this->data['roles'] = select(TBL_ROLES,FALSE,FALSE,null);
		$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,FALSE,null);

		$this->data['page_title'] = 'Topics';
		$this->template->load('admin/default','admin/topic/list', $this->data);
	}


}