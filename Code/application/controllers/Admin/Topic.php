<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * class Topic 
 * 	
 * @package default
 * @author Namrata Varma ( Sparks ID- NV )
 **/

class Topic extends ISM_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
			
		$this->load->library(array('form_validation','encrypt'));
		$this->load->model(array('common_model'));
	}

	
}