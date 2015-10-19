<?php 
if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Question extends ADMIN_Controller {
	public $data = array();

	public function __construct()
	{
		parent::__construct();
		//Load Dependencies

	}

	/*
	* function to set the questions for the tutorial topic
	*/
	public function set(){

		$where = array();

		if($_POST){
			
			$course_id = $this->input->post('course_id');
			$classroom_id = $this->input->post('classroom_id');
			$subject_id = $this->input->post('subject_id');
			$tutorial_topic_id = $this->input->post('topic_id');

			if($classroom_id !='' && $subject_id=='' && $tutorial_topic_id == ''){
				$where = array(TBL_QUESTIONS.'.classroom_id'=>$classroom_id);
			}else if($classroom_id !='' && $subject_id != '' && $tutorial_topic_id == ''){
				$where = array(TBL_QUESTIONS.'.subject_id'=>$subject_id);
			}else if($classroom_id !='' && $subject_id != '' && $tutorial_topic_id != ''){
				$where = array(TBL_TUTORIAL_GROUP_QUESTION.'.tutorial_topic_id'=>$tutorial_topic_id);
			}

		}
		
			$questions = select(TBL_QUESTIONS,
								TBL_QUESTIONS.'.id,'.
								TBL_QUESTIONS.'.question_text,'.
								TBL_SUBJECTS.'.subject_name,'.
								TBL_USERS.'.full_name',
			array('where'=>$where),
				array(
					'group_by'=>TBL_QUESTIONS.'.id,',
					'join'=>array(
						array(
		    				'table' => TBL_TUTORIAL_GROUP_QUESTION,
		    				'condition' => TBL_QUESTIONS.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.question_id',
							),
						array(
		    				'table' => TBL_TUTORIAL_TOPIC,
		    				'condition' => TBL_TUTORIAL_TOPIC.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.tutorial_topic_id',
							),
						array(
		    				'table' => TBL_SUBJECTS,
		    				'condition' => TBL_SUBJECTS.'.id = '.TBL_TUTORIAL_TOPIC.'.subject_id',
							),
						array(
		    				'table' => TBL_USERS,
		    				'condition' => TBL_USERS.'.id = '.TBL_QUESTIONS.'.question_creator_id',
							),
						),
					)
				);

			foreach ($questions as $key=>$question) {
				$choices = select(TBL_ANSWER_CHOICES,
								TBL_ANSWER_CHOICES.'.id,'.
								TBL_ANSWER_CHOICES.'.choice_text,',
								// TBL_ANSWER_CHOICES.'.question_id',
								array('where'=>array(TBL_ANSWER_CHOICES.'.question_id'=>$question['id'])),
								null
								);

				$questions[$key]['choices']=array_column($choices,'choice_text');

											}
			// p($questions,true);
			$this->data['questions'] = $questions;



		$this->data['page_title'] = 'Set Question';
		$where = array('where'=>array('is_delete'=>0));

		$this->data['courses'] = select(TBL_COURSES,FALSE,$where,null);
		$this->data['exams'] = select(TBL_EXAMS,FALSE,$where);	
		$this->template->load('admin/default','admin/question/set',$this->data);
	}

	/*
	* function to get the classrooms through the course.
	*/
	public function ajax_get_classrooms(){
		$course_id = $this->input->post('course_id');
		
		$classrooms = select(TBL_CLASSROOMS,TBL_CLASSROOMS.'.id,'.TBL_CLASSROOMS.'.class_name ',
			array('where'=>array('course_id'=>$course_id))
			);

		
		$new_str = '';
		
		$new_str .= '<option selected value="">Classroom</option>';
		if(!empty($classrooms)){
			foreach($classrooms as $classroom){
				$new_str.='<option value="'.$classroom['id'].'">'.$classroom['class_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	/*
	* function to get the subjects through the classroom.
	*/
	public function ajax_get_subjects(){
		$classroom_id = $this->input->post('classroom_id');
		$course_id = $this->input->post('course_id');
		
		$subjects = select(TBL_COURSE_SUBJECT,TBL_COURSE_SUBJECT.'.subject_id,sub.subject_name ',
			array('where'=>array('classroom_id'=>$classroom_id)),
				array(
					'join'=>array(
								array(
					    				'table' => TBL_SUBJECTS.' sub',
					    				'condition' => 'sub.id = '.TBL_COURSE_SUBJECT.'.subject_id',
									)
								)
					)
			);

		
		$new_str = '';
		
		$new_str .= '<option selected value="">Subject</option>';
		if(!empty($subjects)){
			foreach($subjects as $subject){
				$new_str.='<option value="'.$subject['subject_id'].'">'.$subject['subject_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	/*
	* function to get the tutorial topics through the subjects.
	*/
	public function ajax_get_topics(){
		$subject_id = $this->input->post('subject_id');
		
		$topics = select(TBL_TUTORIAL_TOPIC, TBL_TUTORIAL_TOPIC.'.id,'.TBL_TUTORIAL_TOPIC.'.topic_name, ',
							array('where'=>array('subject_id'=>$subject_id)),
							null
					);

		
		$new_str = '';
		
		$new_str .= '<option selected value="">Topic</option>';
		if(!empty($topics)){
			foreach($topics as $topic){
				$new_str.='<option value="'.$topic['id'].'">'.$topic['topic_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	public function set_question(){

		$qid = $this->input->post('qid');
		$eid = $this->input->post('eid');

		$fetch_data = select(TBL_EXAM_QUESTION,FALSE,array('where'=>array('exam_id'=>$eid,'question_id'=>$qid)));

		if(empty($fetch_data)){
			
			$data = array(
				'exam_id'=>$eid,
				'question_id'=>$qid
			);

			insert(TBL_EXAM_QUESTION,$data);

			$count = count(select(TBL_EXAM_QUESTION,FALSE,array('where'=>array('exam_id'=>$eid))));
			
			$where = array(TBL_QUESTIONS.'.id' => $qid);

			$questions = select(TBL_QUESTIONS,
								TBL_QUESTIONS.'.id,'.
								TBL_QUESTIONS.'.question_text,'.
								TBL_SUBJECTS.'.subject_name,'.
								TBL_USERS.'.full_name',
								array('where'=>$where),
								array(
									'group_by'=>TBL_QUESTIONS.'.id,',
									'join'=>array(
										array(
						    				'table' => TBL_TUTORIAL_GROUP_QUESTION,
						    				'condition' => TBL_QUESTIONS.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.question_id',
											),
										array(
						    				'table' => TBL_TUTORIAL_TOPIC,
						    				'condition' => TBL_TUTORIAL_TOPIC.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.tutorial_topic_id',
											),
										array(
						    				'table' => TBL_SUBJECTS,
						    				'condition' => TBL_SUBJECTS.'.id = '.TBL_TUTORIAL_TOPIC.'.subject_id',
											),
										array(
						    				'table' => TBL_USERS,
						    				'condition' => TBL_USERS.'.id = '.TBL_QUESTIONS.'.question_creator_id',
											),
										),
									)
						);


			 foreach ($questions as $key=>$question) {
				
				$choices = select(TBL_ANSWER_CHOICES,
								TBL_ANSWER_CHOICES.'.id,'.
								TBL_ANSWER_CHOICES.'.choice_text,',
								// TBL_ANSWER_CHOICES.'.question_id',
								array('where'=>array(TBL_ANSWER_CHOICES.'.question_id'=>$question['id'])),
								null
								);

			 	$questions[$key]['choices']=array_column($choices,'choice_text');

			 }

			 $new_str = '';
			 foreach( $questions as $question) {

						$new_str.='<div class="question_wrapper">
		                    <div class="question_left">
		                        <h5 class="txt_red">Question <span>'.$count.'</span></h5>                                        
		                        <p class="ques">'.$question["question_text"].'</p>
		                        <div class="answer_options_div">
		                            <ol>';
		                            	foreach($question['choices'] as $choice) {
		                                	$new_str .='<li>'.$choice.'</li>';
		                                }	

		                     $new_str .='</ol>
		                        </div>
		                    </div>
		                    <div class="notice_action">                                            
		                        <a href="#" class="icon icon_hand" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Move"></a>
		                        <a href="#" class="icon icon_edit_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Edit"></a>
		                        <a href="#" class="icon icon_copy_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Copy"></a>
		                        <a href="#" class="icon icon_delete_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Delete"></a>
		                        
		                    </div>
		                    <div class="clearfix"></div>
		                </div>';
	            	}

			echo json_encode(array('res'=>1,'new_str'=>$new_str,'count'=>$count)); 
		}else{
			echo json_encode(array('res'=>0)); 
		}
		

		//$data
	}
}