<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Appconfig extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
	}

	/**
	 * [index description]
	 * function to load different background images for app login screens
	 * @return [type] [description]
	 */
	public function index(){
		$this->data['page_title'] = 'App Config';
		$this->data['tab'] = 'author';
		$author_where = $student_where = $teacher_where = array();
		if(isset($_POST['author_status'])){
			if($this->input->post('author_status') != '')
				$author_where['where']['status'] = $this->input->post('author_status'); 
			$this->data['author_status'] = $this->input->post('author_status');
			$this->data['tab'] = 'author';
		}
		if(isset($_POST['student_status'])){
			if($this->input->post('student_status') != '')
				$student_where['where']['status'] = $this->input->post('student_status'); 
			$this->data['student_status'] = $this->input->post('student_status');
			$this->data['tab'] = 'student';
		}
		if(isset($_POST['teacher_status'])){
			if($this->input->post('teacher_status') != '')
				$teacher_where['where']['status'] = $this->input->post('teacher_status'); 
			$this->data['teacher_status'] = $this->input->post('teacher_status');
			$this->data['tab'] = 'teacher';
		}
		if(isset($_POST['author_btn'])){
			$author_selected_images = $this->input->post('author');
			$data = array(
						'status'=>'archive'
						);
			update(TBL_APP_IMAGES, array('app_name'=>'Author'), $data);
			
			if(!empty($author_selected_images)){
				foreach ($author_selected_images as $image) {
					$data = array(
						'status'=>'active'
						);
					update(TBL_APP_IMAGES, $image, replace_invalid_chars($data));
				}
			}
		}
		if(isset($_POST['student_btn'])){
			$student_selected_images = $this->input->post('student');
			$data = array(
						'status'=>'archive'
						);
			update(TBL_APP_IMAGES, array('app_name'=>'Student'), $data);
			
			if(!empty($student_selected_images)){
				foreach ($student_selected_images as $image) {
					$data = array(
						'status'=>'active'
						);
					update(TBL_APP_IMAGES, $image, replace_invalid_chars($data));
				}
			}
		}
		if(isset($_POST['teacher_btn'])){
			$teacher_selected_images = $this->input->post('teacher');
			$data = array(
						'status'=>'archive'
						);
			update(TBL_APP_IMAGES, array('app_name'=>'Teacher'), $data);
			
			if(!empty($teacher_selected_images)){
				foreach ($teacher_selected_images as $image) {
					$data = array(
						'status'=>'active'
						);
					update(TBL_APP_IMAGES, $image, replace_invalid_chars($data));
				}
			}
		}

		$author_where['where']['is_delete'] = 0;
		$author_where['where']['app_name'] = 'Author';
		$author_images = select(TBL_APP_IMAGES,'id,image_url, status', $author_where);

		$student_where['where']['is_delete'] = 0;
		$student_where['where']['app_name'] = 'Student';
		$student_images = select(TBL_APP_IMAGES,'id,image_url, status', $student_where);

		$teacher_where['where']['is_delete'] = 0;
		$teacher_where['where']['app_name'] = 'Student';
		$teacher_images = select(TBL_APP_IMAGES,'id,image_url, status', $teacher_where);

		$this->data['author_images'] = $author_images;
		$this->data['student_images'] = $student_images;
		$this->data['teacher_images'] = $teacher_images;
		$this->template->load('admin/default','admin/appconfig/index',$this->data);
	}
}