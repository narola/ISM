<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class My_evaluation extends ISM_Controller {

	/*	Student Exam Evaluation.
	*	@Auther : Kamlesh Pokiya (KAP). 
	*/

	public function __construct()
	{
	    parent::__construct();
	    if(is_numeric($this->uri->segment(4))){
	    	$this->session->set_userdata('examid',$this->uri->segment(4));
	    	$this->examid = $this->session->userdata('examid');
	    }
	    else{
	    	$this->examid = $this->session->userdata('examid');
	    }
	}

	public function index()
	{
		$data = array();
		$this->template->load('student/default','student/my_evaluation',$data);
	}
}
