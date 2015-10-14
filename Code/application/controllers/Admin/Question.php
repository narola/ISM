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
		$this->template->load('admin/default','admin/question/set',$this->data);
	}
}