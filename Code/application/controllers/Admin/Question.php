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

		if($_POST){
			
			$course_id = $this->input->post('course_id');
			$classroom_id = $this->input->post('classroom_id');
			$subject_id = $this->input->post('subject_id');
			$tutorial_topic_id = $this->input->post('topic_id');
			
			$where = array();



			if($classroom_id !='' && $subject_id=='' && $tutorial_topic_id == ''){
				$where = array(TBL_QUESTIONS.'.classroom_id'=>$classroom_id);
			}else if($classroom_id !='' && $subject_id != '' && $tutorial_topic_id == ''){
				$where = array(TBL_QUESTIONS.'.subject_id'=>$subject_id);
			}else if($classroom_id !='' && $subject_id != '' && $tutorial_topic_id != ''){
				$where = array(TBL_TUTORIAL_GROUP_QUESTION.'.tutorial_topic_id'=>$tutorial_topic_id);
			}

			$questions = select(TBL_QUESTIONS,
								TBL_QUESTIONS.'.question_text,'.
								TBL_SUBJECTS.'.subject_name,'.
								TBL_USERS.'.full_name',
			array('where'=>$where),
				array(
					'join'=>array(
						array(
		    				'table' => TBL_TUTORIAL_GROUP_QUESTION,
		    				'condition' => TBL_QUESTIONS.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.question_id',
							),
						array(
		    				'table' => TBL_SUBJECTS,
		    				'condition' => TBL_SUBJECTS.'.id = '.TBL_QUESTIONS.'.subject_id',
							),
						array(
		    				'table' => TBL_USERS,
		    				'condition' => TBL_USERS.'.id = '.TBL_QUESTIONS.'.question_creator_id',
							),
						),
					)
				);
			p($questions,true);
			$this->data['questions'] = $questions;



		}

		$this->data['page_title'] = 'Set Question';
		$where = array('where'=>array('is_delete'=>0));
		$this->data['courses'] = select(TBL_COURSES,FALSE,$where,null);
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
}