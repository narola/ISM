<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Notice extends ADMIN_Controller {

	// Create Public Blank Variable Use in all function

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		$this->data['page_title'] = 'Notice';
		$this->data['cur_url'] = $this->session->userdata('cur_url');
		$this->data['prev_url'] = $this->session->userdata('prev_url');
	}

	//  List All noticeboard view,delete,add,and change into archive notice
	public function index()
	{

		$order = 'created_date DESC';
		
		if($_POST){
			
			$bulk_notices = $this->input->post('notices_bulk');
			$bulk_action = $this->input->post('bulk_action');

			if(!empty($bulk_notices)){

				foreach($bulk_notices as $b_notice){
					update(TBL_NOTICEBOARD,$b_notice,array('is_delete'=>TRUE));	
				}	
				$this->session->set_flashdata('success', 'Notices are successfully deleted.');
				redirect('admin/notice');
			}else{
				$this->session->set_flashdata('error', 'No Notices Selected for Bulkaction.');
				redirect('admin/notice');
			}

		}

		if( !empty($_GET['role']) || !empty($_GET['status']) || !empty($_GET['classroom']) || !empty($_GET['order']) ){

			if( !empty($_GET['role']) ) { $role = $this->input->get('role'); }	
			if( !empty($_GET['status']) ) { $status = $this->input->get('status'); }
			if( !empty($_GET['classroom']) ) { $classroom = $this->input->get('classroom'); }	
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		
			
			$str = '';
			
			$where['noticeboard.is_delete'] = FALSE;

			if(!empty($role)){ $where['noticeboard_viewer.role_id'] = $role ; $str .= '&role='.$role; }	
			if(!empty($status)){ 
						if($status != 'template') { $where['noticeboard.status'] = $status;   $str .= '&status='.$status;}
						else{ $where['noticeboard.is_template'] = TRUE;   $str .= '&status='.$status; } 
					}
			if(!empty($classroom)){  $where['noticeboard_viewer.classroom_id'] = $classroom ; $str .= '&classroom='.$classroom; }		
			
			if($order == 'name_asc'){ $order = "noticeboard.notice asc"; $str.='&order=name_asc';  }
			if($order == 'name_desc'){ $order = "noticeboard.notice desc"; $str.='&order=name_desc'; }
			if($order == 'latest'){ $order = "noticeboard.created_date desc"; $str.='&order=latest'; }
			if($order == 'older'){ $order = "noticeboard.created_date asc"; $str.='&order=older'; }	


			$str =  trim($str,'&');

			$config['base_url'] = base_url().'admin/notice/index?'.$str;
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'

		}else{
			$where = array('noticeboard.is_delete'=>FALSE);
			$config['base_url'] = base_url().'admin/notice/index';	
			$offset = $this->uri->segment(4);
		}

		$config['num_links'] = 5;
		$config['total_rows'] = select(TBL_NOTICEBOARD,FALSE,array('where'=>$where),array('count'=>TRUE,'join'=>array(array('table'=>'noticeboard_viewer','condition'=>'noticeboard.id=noticeboard_viewer.notice_id'))));
		$config['per_page'] = 11;

		$config['full_tag_open'] = '<ul class="pagination pagination_admin">';
 		$config['full_tag_close'] = '</ul>';

 		$config['num_tag_open'] = '<li>';
 		$config['num_tag_close'] = '</li>';

 		$config['first_link'] = 'First';
	 	$config['first_tag_open'] = '<li>';
	 	$config['first_tag_close'] = '</li>';

	 	$config['cur_tag_open'] = '<li style="display:none"></li><li class="active"><a>';
	 	$config['cur_tag_close'] = '</a></li><li style="display:none"></li>';

	 	$config['prev_link'] = '&laquo;';
	 	$config['prev_tag_open'] = '<li>';
	 	$config['prev_tag_close'] = '</li>';

	 	$config['next_link'] = '&raquo;';
	 	$config['next_tag_open'] = '<li>';
	 	$config['next_tag_close'] = '</li>';

	 	$config['last_link'] = 'Last';
	 	$config['last_tag_open'] = '<li>';
	 	$config['last_tag_close'] = '</li>';

	 	//fetch data from noticeboard table and join with noticeboard viewerd table
		$this->data['notices'] = select(TBL_NOTICEBOARD,
										"noticeboard.id,noticeboard.status,noticeboard.notice,noticeboard.created_date,noticeboard.notice_title,noticeboard_viewer.role_id",
										array('where'=>$where),
										array(
											'order_by'=>$order,
											'limit'=>$config['per_page'],
											'offset'=>$offset,
											'join'=>array(
													array(
														'table'=>'noticeboard_viewer'	,
														'condition'=>'noticeboard.id=noticeboard_viewer.notice_id'
													)
												)
											)
										);
		
		//if(empty($this->data['notices'])){ $this->session->set_flashdata('error', 'No Notices Found.'); }

		$this->data['schools'] = select(TBL_SCHOOLS,FALSE,array('where'=>array('is_delete'=>FALSE)),array('limit'=>10));
		$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>FALSE)),array('limit'=>10));
		$this->data['roles'] = select(TBL_ROLES,FALSE,array('where_not_in'=>array('role_name'=>array('admin'))),array('limit'=>10));
		$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>FALSE)),array('limit'=>10));

		//pass pagination configuration set in $config variable and initialize into pagination class
		$this->pagination->initialize($config);
		
		$this->template->load('admin/default','admin/notice/view_notice',$this->data);	
	}

	// Notice Add Form
	public function add(){

		$this->data['roles'] = select(TBL_ROLES);
		$this->data['templates'] = select(TBL_NOTICEBOARD,false,array('where'=>array('is_template'=>TRUE,'is_delete'=>FALSE)));
		$this->data['classrooms'] = select(TBL_CLASSROOMS);

		$this->form_validation->set_rules('notice_title', 'Notice Title', 'trim|required|alpha_numeric_spaces');
		$this->form_validation->set_rules('notice', 'Notice Description', 'trim|required');
		$this->form_validation->set_rules('role_id', 'Role', 'trim|required');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim');

		if($this->form_validation->run() == FALSE){
			$this->template->load('admin/default','admin/notice/add_notice',$this->data);	
		}else{

			$db_template = select(TBL_NOTICEBOARD,FALSE,array('where'=>array('is_template'=>'1','is_delete'=>'0')));
			$notice_title = $this->input->post('notice_title');

			$cnt = 0;
			if(isset($_POST['is_template'])){
				foreach($db_template as $db_temp){
					if($db_temp['notice_title'] == $notice_title){
						$cnt++;
					}
				}
			}
			
			if($cnt != 0){
				$this->session->set_flashdata('msgerror', 'Notice template should be Unique.');
				redirect('admin/notice/add');
			}

			$posted_by = $this->session->userdata('id'); 
			
			$data = array(
					'notice_title'=>$notice_title,
					'notice'=>$this->input->post('notice'),
					'posted_by'=>$posted_by,
					'status'=>$this->input->post('status'),
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
			redirect($this->data['prev_url']);
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

		$this->form_validation->set_rules('notice_title', 'Notice Title', 'trim|required|alpha_numeric_spaces');
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

			if(!empty($this->input->post('classroom_id'))){ $classroom_id = $this->input->post('classroom_id');}else{ $classroom_id = null; }
			 
			$notice_viewer_data = array(
									'role_id'=>$this->input->post('role_id'),
									'classroom_id'=>$classroom_id,
									'modified_date'=>date('Y-m-d H:i:s',time()),
								);
			update(TBL_NOTICEBOARD_VIEWER,$notice_viewer_id,$notice_viewer_data);
			$this->session->set_flashdata('success', 'Data is Successfully Updated.');
			redirect($this->data['prev_url']);
		}
	}

	// Notice Delete on function call with id as parameter ( Update is_delete to 1 )
	public function delete($id){

		update(TBL_NOTICEBOARD,$id,array('is_delete'=>TRUE));
		$this->session->set_flashdata('success', 'Data is Successfully Deleted.');
		redirect($this->data['prev_url']);	
	}

	//Admin can cange status of Notice Active to Archive
	public function archive($id,$active=''){

		if(!empty($active)){
			update(TBL_NOTICEBOARD,$id,array('status'=>'active'));	
			$this->session->set_flashdata('success', "Data's Status has been added to Active.");
		}else{
			update(TBL_NOTICEBOARD,$id,array('status'=>'archive'));	
			$this->session->set_flashdata('success', "Data's Status has been added to Archive.");
		}
		redirect($this->data['prev_url']);	
	}
	
}

/* End of file Notice.php */
/* Location: ./application/controllers/Admin/Notice.php */