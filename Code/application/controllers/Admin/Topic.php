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
		$this->data['all_topics'] = $this->common_model->sql_select('topics',
																	'topics.id,topics.topic_name,topics.topic_description,topics.allocation_count,topics.course_id,topics.subject_id, subjects.subject_name,courses.course_name',
																	null,
																	array(
																		'join' =>  array(
																	    			array(
																	    				'table' => 'subjects',
																	    				'condition' => 'subjects.id = topics.subject_id',
																						),
																	    			array(
																	    				'table' => 'courses',
																	    				'condition' => 'courses.id = topics.course_id',
																						),
																	    			
																		    		)
																		)
																	);
		
		$this->template->load('admin/default','admin/topic/list', $this->data);
	}


}