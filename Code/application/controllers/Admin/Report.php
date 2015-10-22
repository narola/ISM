<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Report extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
    }

    public function index(){
    	$this->data['page_title'] = 'Reports';
    	$this->template->load('admin/default','admin/reports/reports',$this->data);
    }
}
