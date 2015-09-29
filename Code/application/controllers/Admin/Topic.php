<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * class Topic 
 * 	
 * @package default
 * @author Namrata Varma ( Sparks ID- NV )
 **/

class Topic extends ISM_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
	}

	/**
	* This function will used to list all the topics allocated for tutorial groups.
	*/
	public function lists(){
		$this->data['all_topics'] = select('tutorial_topic',
											'tutorial_topic.id,tutorial_topic.topic_name,tutorial_topic.topic_description,tutorial_topic.allocation_count,tutorial_topic.classroom_id,tutorial_topic.subject_id, tutorial_topic.created_by,subjects.subject_name,classrooms.class_name,users.first_name,users.last_name',
											null,
											array(
												'join' =>  array(
											    			array(
											    				'table' => 'subjects',
											    				'condition' => 'subjects.id = tutorial_topic.subject_id',
																),
											    			array(
											    				'table' => 'classrooms',
											    				'condition' => 'classrooms.id = tutorial_topic.classroom_id',
																),
											    			array(
											    				'table' => 'users',
											    				'condition' => 'users.id = tutorial_topic.created_by',
																),
											    			array(
											    				'table' => 'questions',
											    				'condition' => 'questions.topic_id = tutorial_topic.id',
																),
												    		)
												)
											);
// p($this->data);

		
		$this->template->load('admin/default','admin/topic/list', $this->data);
	}


}