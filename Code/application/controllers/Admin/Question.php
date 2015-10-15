<?php 
if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Question extends CI_Controller {
	public $data = array();

	public function __construct()
	{
		parent::__construct();
		//Load Dependencies

	}

	public function set(){
		$this->data['page_title'] = 'Set Question';
		$where = array('where'=>array('is_delete'=>0));
		$this->data['courses'] = select(TBL_COURSES,FALSE,$where,null);
		$this->template->load('admin/default','admin/question/set',$this->data);
	}

	public function ajax_get_classrooms(){
		$course_id = $this->input->post('course_id');
		
		$classrooms = select(TBL_CLASSROOMS,TBL_CLASSROOMS.'.id,'.TBL_CLASSROOMS.'.class_name ',
			array('where'=>array('course_id'=>$course_id))
			);

		
		$new_str = '';
		
		$new_str .= '<option selected disabled >Select Subject</option>';
		if(!empty($classrooms)){
			foreach($classrooms as $classroom){
				$new_str.='<option value="'.$classroom['id'].'">'.$classroom['class_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	public function ajax_get_subjects(){
		$classroom_id = $this->input->post('classroom_id');
		$course_id = $this->input->post('course_id');
		
		$subjects = select(TBL_COURSE_SUBJECT,TBL_COURSE_SUBJECT.'.subject_id,sub.subject_name ',
			array('where'=>array('course_id'=>$course_id,'classroom_id'=>$classroom_id)),
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
		
		$new_str .= '<option selected disabled >Select Subject</option>';
		if(!empty($subjects)){
			foreach($subjects as $subject){
				$new_str.='<option value="'.$subject['subject_id'].'">'.$subject['subject_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	/*public function ajax_get_topics(){
		$classroom_id = $this->input->post('classroom_id');
		$course_id = $this->input->post('course_id');
		
		$subjects = select(TBL_TUTORIAL_TOPIC.'.subject_id,sub.subject_name ',
			array('where'=>array('course_id'=>$course_id,'classroom_id'=>$classroom_id)),
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
		
		$new_str .= '<option selected disabled >Select Subject</option>';
		if(!empty($subjects)){
			foreach($subjects as $subject){
				$new_str.='<option value="'.$subject['subject_id'].'">'.$subject['subject_name'].'</option>';
			}	
		}
		echo $new_str;
	}*/
}