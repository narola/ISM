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
		$this->template->load('admin/default','admin/appconfig/index',$this->data);
	}
}