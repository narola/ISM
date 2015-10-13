<?php 
if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Exam extends CI_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		//Load Dependencies

	}

	// List all your items
	public function index( $offset = 0 ){

		$this->template->load('admin/default','admin/exam/view_exam',$this->data);
	}

	// Add a new item
	public function add(){

		$this->template->load('admin/default','admin/exam/add_exam',$this->data);
	}

	//Update one item
	public function update( $id = NULL )
	{

	}

	//Delete one item
	public function delete( $id = NULL )
	{

	}
}

/* End of file Exam.php */
/* Location: ./application/controllers/Admin/Exam.php */


 