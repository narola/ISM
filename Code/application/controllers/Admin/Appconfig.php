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

		if(isset($_POST['author_btn'])){
			
		}
		if(isset($_POST['author_btn'])){
			
		}
		if(isset($_POST['author_btn'])){
			
		}

		$author_images = select(TBL_APP_IMAGES,'id,image_url, status', array('where'=>array(
											'is_delete'=>0,
											'app_name'=>'Author'
			)));
		$student_images = select(TBL_APP_IMAGES,'id,image_url, status', array('where'=>array(
											'is_delete'=>0,
											'app_name'=>'Student'
			)));
		$teacher_images = select(TBL_APP_IMAGES,'id,image_url, status', array('where'=>array(
											'is_delete'=>0,
											'app_name'=>'Teacher'
			)));
		$this->data['author_images'] = $author_images;
		$this->data['student_images'] = $student_images;
		$this->data['teacher_images'] = $teacher_images;
		$this->template->load('admin/default','admin/appconfig/index',$this->data);
	}
}