<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Notice extends ISM_Controller {

/**
 * function add(),update(),delete() 
 *	
 *
 * @author Virendra Patel Sparks ID-VPA
 **/

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		$this->load->library(array('form_validation','encrypt'));
		$this->load->model(array('common_model'));	
	}

	public function index()
	{

		$this->data['notices'] = select('noticeboard');

		$this->template->load('admin/default','admin/notice/view_notice',$this->data);	
	}

	public function add(){

		$this->data['roles'] = $this->common_model->sql_select('roles');
		$this->data['templates'] = $this->common_model->sql_select('noticeboard',false,array('where'=>array('is_template'=>TRUE)));
		$this->data['classrooms'] = $this->common_model->sql_select('classrooms');

		$this->form_validation->set_rules('notice_title', 'Notice Title', 'trim|required');
		$this->form_validation->set_rules('notice', 'Notice Description', 'trim|required');
		$this->form_validation->set_rules('role_id', 'Role', 'trim|required');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim');

		if($this->form_validation->run() == FALSE){
			$this->template->load('admin/default','admin/notice/add_notice',$this->data);	
		}else{

			$posted_by = $this->session->userdata('id'); 
			
			$data = array(
					'notice_title'=>$this->input->post('notice_title'),
					'notice'=>$this->input->post('notice'),
					'posted_by'=>$posted_by,
					'status'=>TRUE,
					'created_date'=>date('Y-m-d H:i:s',time()),
					'modified_date'=>'0000-00-00 00:00:00',
					'is_template'=>$this->input->post('is_template'),
					'is_delete'=>FALSE,
					'is_testdata'=>'yes'
				);

			$notice_id = $this->common_model->insert('noticeboard',$data);

			$noticeboard_viewer = array(
					'notice_id'=>$notice_id,
					'role_id'=>$this->input->post('role_id'),
					'classroom_id'=>$this->input->post('classroom_id'),
					'created_date'=>date('Y-m-d H:i:s',time()),
					'modified_date'=>'0000-00-00 00:00:00',
					'is_delete'=>FALSE,
					'is_testdata'=>'yes'
				);

			$this->common_model->insert('noticeboard_viewer',$noticeboard_viewer);
			
			redirect('admin/notice');

		}
		
	}

	public function update(){

	}

	public function delete(){

	}

}

/* End of file Notice.php */
/* Location: ./application/controllers/Admin/Notice.php */