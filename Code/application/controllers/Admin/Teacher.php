<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Teacher extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
	}

	// function to list down the teachers 
	public function index(){
		$this->data['page_title'] = 'Teachers';

		$teachers = select(TBL_USERS,TBL_USERS.'.id,'.TBL_USERS.'.full_name,'.TBL_USERS.'.profile_pic,'.TBL_USERS.'.user_status,'.TBL_TEACHER_PROFILE.'.education,'.TBL_TEACHER_PROFILE.'.specialization',
										array('where'=>array(
											TBL_ROLES.'.role_name'=>'teacher',
											TBL_USERS.'.is_delete'=>0
											)),
										array(
											'join'=> array(
													array(
								    				'table' => TBL_ROLES,
								    				'condition' => TBL_ROLES.".id = ".TBL_USERS.".role_id",
								    				),
								    				array(
								    				'table' => TBL_TEACHER_PROFILE,
								    				'condition' => TBL_TEACHER_PROFILE.".user_id = ".TBL_USERS.".id",
								    				)
												)
											)
								);
		p($teachers, true);
		
	}
}