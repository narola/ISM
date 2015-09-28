<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
* kap - kamlesh pokiya 
*
*/

class Notice_board extends CI_Controller {

	public function __construct()
	{
	    parent::__construct();
	    $this->data['title'] = 'ISM - Notice Board';
	    $this->load->model('common_model');
	    $this->load->model('student/student_account_model');
	    $this->load->library('upload','form_validation');
	}

	public function index()
	{	
		exit();
	}
}
