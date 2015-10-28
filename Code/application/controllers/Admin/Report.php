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
    	
    	// $course_id = $this->input->post('course_id');
         $date_range = '09/27/2015 - 10/11/2015';
        //$date_range = $this->input->post('date_range');
    	$date_range_split = explode(" - ", $date_range);
    	$sdate = date_create(reset($date_range_split));
    	$edate = date_create(end($date_range_split));
    	$start_date=date_format($sdate,"Y-m-d H:i:s");
		$end_date=date_format($edate,"Y-m-d H:i:s");
		
    	$courses = select(TBL_COURSES, TBL_COURSES.".id,".TBL_COURSES.".course_nickname",array('where'=>array('is_delete'=>FALSE)));
        
        $stats = array();

        foreach ($courses as $course) {
            $classrooms = select(
                                 TBL_CLASSROOMS,
                                 TBL_CLASSROOMS.".id",
                                 array('where'=>array(TBL_CLASSROOMS.".course_id"=>$course['id']))
                                 );
        
            $classroom_ids = array_column($classrooms,'id');
            
            if(!empty($classroom_ids)){
                    $quest_stats = select(TBL_QUESTIONS,"count(".TBL_QUESTIONS.".id) as y", 
                    array(
                          'where_in'=>array(TBL_QUESTIONS.".classroom_id"=>$classroom_ids),
                          'where'=>array(
                                            TBL_QUESTIONS.".created_date > " => $start_date,
                                            TBL_QUESTIONS.".created_date < " => $end_date
                                        )
                                                
                        ),
                    array('single'=>true)
                    );

                    $quest_stats['name'] = $course['course_nickname'];
                    $stats[]=$quest_stats;
            }

        }

    	echo json_encode($stats,JSON_NUMERIC_CHECK);
    }



    public function get_group_stats(){

    	// $classroom_id = $this->input->post('classroom_id');
    	$classroom_id = 2;

    	// $date_range = $this->input->post('date_range');
    	$date_range = '08/02/2015 - 11/10/2015';

        $date_range_split = explode(" - ", $date_range);
    	$sdate = date_create(reset($date_range_split));
    	$edate = date_create(end($date_range_split));
    	$start_date=date_format($sdate,"Y-m-d H:i:s");
		$end_date=date_format($edate,"Y-m-d H:i:s");  

    	$members = select(
                            TBL_STUDENT_ACADEMIC_INFO,
                            TBL_TUTORIAL_GROUPS.".group_name as name,".TBL_TUTORIAL_GROUP_MEMBER.
                            ".group_id,".TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".group_score as y",
    		               array(
                            'where'=>array(
                                    TBL_TUTORIAL_GROUPS.".classroom_id"=>$classroom_id,
    			                    TBL_TUTORIAL_GROUPS.".is_completed"=>'1',
    			                    TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".created_date > " => $start_date,
    								TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".created_date < " => $end_date
    					           )
                            ),
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
        
        $new_str = array();            
        if(!empty($members)){
            foreach($members as $member){
                array_push($new_str, $member['group_id']);
            }
        }else{
            array_push($new_str, '0');
        }

        if(!empty($new_str)){
            $group_by = "group_topic.group_id";
        }else{
            $group_by= '';
        }

        $response['group_data'] = select(
                              TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' group_topic',
                              'group_topic.group_id,group.group_name as name,sum(group_topic.group_score) as y',
                              array('where_in'=>array('group_topic.group_id'=>$new_str)),
                              array(
                                    'group_by'=>$group_by,
                                    'join'=>array(
                                                array(
                                                        'table'=>TBL_TUTORIAL_GROUPS.' as group',
                                                        'condition'=>'group.id=group_topic.group_id'
                                                    )

                                                )
                                    )
                            );
        
        $where = array('where'=>array(TBL_EXAMS.'.classroom_id'=>$classroom_id,
                                        TBL_EXAMS.'.exam_category'=>'ISM_Mock',
                                        TBL_EXAMS.'.is_delete'=>0
                                    ));
        $exams = select(TBL_EXAMS,TBL_EXAMS.'.id',$where,FALSE);
        $exam_ids = array_column($exams, 'id');
        p($exam_ids);

        $exam_score = select(TBL_STUDENT_EXAM_SCORE.','.TBL_ADMIN_CONFIG,
            TBL_STUDENT_EXAM_SCORE.'.user_id,sum('.
            TBL_STUDENT_EXAM_SCORE.'.correct_answers * '.TBL_ADMIN_CONFIG.'.config_value) as score,count('.
            TBL_EXAM_QUESTION.'.question_id),'.TBL_ADMIN_CONFIG.'.config_value, count('.
            TBL_EXAM_QUESTION.'.question_id) * '.TBL_ADMIN_CONFIG.'.config_value as total',
            array(
                'where'=>array(
                    TBL_STUDENT_EXAM_SCORE.".created_date > " => $start_date,
                    TBL_STUDENT_EXAM_SCORE.".created_date < " => $end_date,
                    TBL_STUDENT_EXAM_SCORE.".exam_status" => 'finished',
                    TBL_ADMIN_CONFIG.'.config_key'=>'correctAnswerScore'
                    ),
                'where_in'=>array(TBL_STUDENT_EXAM_SCORE.'.exam_id'=>$exam_ids)
                ),
            array(
                'join'=>array(
                    array(
                        'table'=>TBL_EXAM_QUESTION,
                        'condition'=>TBL_EXAM_QUESTION.'.exam_id='.TBL_STUDENT_EXAM_SCORE.'.exam_id'
                    )

                    ),
                'group_by'=>TBL_STUDENT_EXAM_SCORE.'.user_id'
                )
            );
qry();
        /*foreach (range(0, 100, 10) as $number) {
            echo $number;
        }*/
        p($exam_score, true);
    	echo json_encode($response,JSON_NUMERIC_CHECK);
    }


}
