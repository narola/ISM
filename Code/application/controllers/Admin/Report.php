<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Report extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
    }

    public function index(){

    	$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$top_groups = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION,"sum(".TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".group_score) as score,".TBL_TUTORIAL_GROUPS.".group_name",
			FALSE,
			array('join'=>array(
					array(
							'table'=>TBL_TUTORIAL_GROUPS,
							'condition'=>TBL_TUTORIAL_GROUPS.'.id='.TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.'.group_id'
						)
				),
			'group_by'=>TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".group_id",
			'order_by'=>'score desc',
			'limit'=>5
			)
			);
		$this->data['top_groups'] = $top_groups;

    	$this->data['page_title'] = 'Reports';
    	$this->template->load('admin/default','admin/reports/reports',$this->data);
    }

    public function get_question_stats(){
    	$course_id = $this->input->post('course_id');
    	$date_range = $this->input->post('date_range');
    	$date_range_split = explode(" - ", $date_range);
    	$sdate = date_create(reset($date_range_split));
    	$edate = date_create(end($date_range_split));
    	$start_date=date_format($sdate,"Y-m-d H:i:s");
		$end_date=date_format($edate,"Y-m-d H:i:s");
		
    	
    	$classrooms = select(TBL_CLASSROOMS,TBL_CLASSROOMS.".id", array('where'=>array(TBL_CLASSROOMS.".course_id"=>$course_id)),FALSE );
    	$classroom_ids = array_column($classrooms,'id');
    	
    	$quest_stats = select(TBL_QUESTIONS,"count(".TBL_QUESTIONS.".id) as y,".TBL_CLASSROOMS.".class_name as name", 
    		array('where_in'=>array(TBL_QUESTIONS.".classroom_id"=>$classroom_ids),
    				'where'=>array(TBL_QUESTIONS.".created_date > " => $start_date,
    									TBL_QUESTIONS.".created_date < " => $end_date)
    									
    			),
    		array(
    			'join'=>array(
								array(
									'table'=>TBL_CLASSROOMS,
									'condition'=>TBL_CLASSROOMS.'.id='.TBL_QUESTIONS.'.classroom_id'
								)
							),
    			'group_by'=>TBL_QUESTIONS.".classroom_id"));
    	echo json_encode($quest_stats,JSON_NUMERIC_CHECK);
    }

    public function get_group_stats(){
    	$classroom_id = $this->input->post('classroom_id');
    	// $classroom_id = 2;
    	$date_range = $this->input->post('date_range');
    	$date_range_split = explode(" - ", $date_range);
    	$sdate = date_create(reset($date_range_split));
    	$edate = date_create(end($date_range_split));
    	$start_date=date_format($sdate,"Y-m-d H:i:s");
		$end_date=date_format($edate,"Y-m-d H:i:s");

    	$members = select(TBL_STUDENT_ACADEMIC_INFO,TBL_TUTORIAL_GROUPS.".group_name as name,".TBL_TUTORIAL_GROUP_MEMBER.".group_id,sum(".TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".group_score) as y",
    		array('where'=>array(TBL_STUDENT_ACADEMIC_INFO.".classroom_id"=>$classroom_id,
    			TBL_TUTORIAL_GROUPS.".is_completed"=>1,
    			TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".created_date > " => $start_date,
    									TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".created_date < " => $end_date
    					)),
    		array('join'=>array(
    				array(
							'table'=>TBL_TUTORIAL_GROUP_MEMBER,
							'condition'=>TBL_TUTORIAL_GROUP_MEMBER.'.user_id='.TBL_STUDENT_ACADEMIC_INFO.'.user_id'
						),
    			array(
							'table'=>TBL_TUTORIAL_GROUPS,
							'condition'=>TBL_TUTORIAL_GROUPS.'.id='.TBL_TUTORIAL_GROUP_MEMBER.'.group_id'
						),
    				array(
							'table'=>TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION,
							'condition'=>TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.'.group_id='.TBL_TUTORIAL_GROUP_MEMBER.'.group_id'
						)
    			),
    		'group_by'=>TBL_TUTORIAL_GROUP_MEMBER.".group_id"
    		)
    		);
    	
    	echo json_encode($members,JSON_NUMERIC_CHECK);
    }
}
