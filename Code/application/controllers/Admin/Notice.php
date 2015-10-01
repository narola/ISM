<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Notice extends ADMIN_Controller {

	// Create Public Blank Variable Use in all function

	public $data = array();

	public function __construct()
	{
		parent::__construct();
	}

	//  List All noticeboard view,delete,add,and change into archive notice
	public function index()
	{
		$this->data['page_title'] = 'Notice';

		$this->load->library('pagination');
		
		$config['base_url'] = base_url().'admin/notice/index';
		$config['uri_segment'] = 4;
		$config['num_links'] = 5;
		$config['total_rows'] = select('noticeboard',FALSE,array('where'=>array('noticeboard_viewer.is_delete'=>FALSE)),array('count'=>TRUE));
		$config['per_page'] = 10;

		$config['full_tag_open'] = '<ul class="pagination pagination_admin">';
 		$config['full_tag_close'] = '</ul>';

 		$config['num_tag_open'] = '<li>';
 		$config['num_tag_close'] = '</li>';

 		$config['first_link'] = 'First';
	 	$config['first_tag_open'] = '<li>';
	 	$config['first_tag_close'] = '</li>';

	 	$config['cur_tag_open'] = '<li style="display:none"></li><li class="active"><a>';
	 	$config['cur_tag_close'] = '</a></li>';

	 	$config['prev_link'] = '&laquo;';
	 	$config['prev_tag_open'] = '<li>';
	 	$config['prev_tag_close'] = '</li>';

	 	$config['next_link'] = '&raquo;';
	 	$config['next_tag_open'] = '<li>';
	 	$config['next_tag_close'] = '</li>';

	 	$config['last_link'] = 'Last';
	 	$config['last_tag_open'] = '<li>';
	 	$config['last_tag_close'] = '</li>';
	 	
		$offset = $this->uri->segment(4);

		$this->data['notices'] = select(TBL_NOTICEBOARD,
										FALSE,
										array('where'=>array('noticeboard.is_delete'=>FALSE)),
										array(
											'limit'=>$config['per_page'],
											'offset'=>$offset,
											// 'join'=>array(
											// 			array(
											// 				'table'=>'noticeboard_viewer',
											// 				'condition'=>'noticeboard.id=noticeboard_viewer.notice_id'
											// 			),
											// 			array(
											// 				'table'=>'roles',
											// 				'condition'=>'noticeboard_viewer.role_id=roles.id'
											// 			)
											// 		)
												)
										);

		

		$this->data['schools'] = select(TBL_SCHOOLS,FALSE,FALSE,array('limit'=>10));
		$this->data['courses'] = select(TBL_COURSES,FALSE,FALSE,array('limit'=>10));
		$this->data['roles'] = select(TBL_ROLES,FALSE,FALSE,array('limit'=>10));

		$this->pagination->initialize($config);
		
		$this->template->load('admin/default','admin/notice/view_notice',$this->data);	
	}

	// Notice Add Form
	public function add(){

		$this->data['roles'] = select(TBL_ROLES);
		$this->data['templates'] = select(TBL_NOTICEBOARD,false,array('where'=>array('is_template'=>TRUE)));
		$this->data['classrooms'] = select(TBL_CLASSROOMS);

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

			$notice_id = insert(TBL_NOTICEBOARD,$data);

			$noticeboard_viewer = array(
					'notice_id'=>$notice_id,
					'role_id'=>$this->input->post('role_id'),
					'classroom_id'=>$this->input->post('classroom_id'),
					'created_date'=>date('Y-m-d H:i:s',time()),
					'modified_date'=>'0000-00-00 00:00:00',
					'is_delete'=>FALSE,
					'is_testdata'=>'yes'
				);

			insert(TBL_NOTICEBOARD_VIEWER,$noticeboard_viewer);
			$this->session->set_flashdata('success', 'Data is Successfully created.');
			redirect('admin/notice');
		}
	}

	//Notice update Form
	public function update($id){

		$this->data['roles'] = select(TBL_ROLES);
		$this->data['templates'] = select(TBL_NOTICEBOARD,false,array('where'=>array('is_template'=>TRUE)));
		$this->data['classrooms'] = select(TBL_CLASSROOMS);
		
		$this->data['notice'] = select(TBL_NOTICEBOARD,
							TBL_NOTICEBOARD.'.notice_title,'.TBL_NOTICEBOARD.'.notice,'.TBL_NOTICEBOARD.
							'.is_template,'.TBL_NOTICEBOARD_VIEWER.'.classroom_id,'.TBL_NOTICEBOARD_VIEWER.'.role_id,'.
							TBL_NOTICEBOARD_VIEWER.'.id as notice_viewer_id,'.TBL_NOTICEBOARD.'.status',
							array('where'=>array(TBL_NOTICEBOARD.'.id'=>$id)),
						  	array(
						  		'single'=>TRUE,
						  		'join'	=>array(
						  					array(
							    				'table' => TBL_NOTICEBOARD_VIEWER,
							    				'condition' => TBL_NOTICEBOARD_VIEWER.".notice_id = ".TBL_NOTICEBOARD.".id",
							    				)
						  				)
						  			));		

		$this->form_validation->set_rules('notice_title', 'Notice Title', 'trim|required');
		$this->form_validation->set_rules('notice', 'Notice Description', 'trim|required');
		$this->form_validation->set_rules('role_id', 'Role', 'trim|required');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim');

		if($this->form_validation->run() == FALSE ){
			$this->template->load('admin/default','admin/notice/update_notice',$this->data);	
		}else{

			$notice_id = $id;
			$notice_viewer_id = $this->data['notice']['notice_viewer_id'];

			$notice_data = array(
					'notice_title'=>$this->input->post('notice_title'),
					'notice'=>$this->input->post('notice'),
					'status'=>$this->input->post('status'),
					'modified_date'=>date('Y-m-d H:i:s',time()),
					'is_template'=>$this->input->post('is_template'),
				);

			update(TBL_NOTICEBOARD,$notice_id,$notice_data);

			$notice_viewer_data = array(
									'role_id'=>$this->input->post('role_id'),
									'classroom_id'=>$this->input->post('classroom_id'),
									'modified_date'=>date('Y-m-d H:i:s',time()),
								);
			update(TBL_NOTICEBOARD_VIEWER,$notice_viewer_id,$notice_viewer_data);
			$this->session->set_flashdata('success', 'Data is Successfully Updated.');
			redirect('admin/notice');
			//redirect('','refresh');
		}
	}

	// Notice Delete on function call with id as parameter ( Update is_delete to 1 )
	public function delete($id){

		update(TBL_NOTICEBOARD,$id,array('is_delete'=>TRUE));
		$this->session->set_flashdata('success', 'Data is Successfully Deleted.');
		redirect('admin/notice');	
	}

	//Admin can cange status of Notice Active to Archive
	public function archive($id){
		update(TBL_NOTICEBOARD,$id,array('status'=>'archive'));
		$this->session->set_flashdata('success', "Data's Status has been added to Archive.");
		redirect('admin/notice');	
	}
	
}

/* End of file Notice.php */
/* Location: ./application/controllers/Admin/Notice.php */