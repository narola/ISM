<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Book extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
	}

	public function index(){
		if( !empty($_GET['author']) || !empty($_GET['tags']) || !empty($_GET['order']) ){

			if( !empty($_GET['author']) ) { $role = $this->input->get('author'); }	
			if( !empty($_GET['tags']) ) { $status = $this->input->get('tags'); }
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		
			
			$str = '';
			
			$where[TBL_BOOKS.'.is_delete'] = FALSE;

			/*if(!empty($role)){ $where['noticeboard_viewer.role_id'] = $role ; $str .= '&role='.$role; }	
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
*/
			$config['base_url'] = base_url().'admin/book/index?'.$str;
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'

		}else{
			$where = array(TBL_BOOKS.'.is_delete'=>FALSE);
			$config['base_url'] = base_url().'admin/book/index';	
			$offset = $this->uri->segment(4);
		}
		$config['num_links'] = 3;
		$config['total_rows'] = select(TBL_BOOKS,FALSE,array('where'=>$where),array('count'=>TRUE));
		
		// START To check weather page is on 1st page or not ? if it is on first page do not show add notice field
		$this->data['page_number'] =  $this->uri->segment(4);
		$config['per_page'] = 11;
		//END
		
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
		$this->data['books'] = select(TBL_BOOKS, 'id, book_name, image_link',
										array('where'=>$where),
										array(
											'limit'=>$config['per_page'],
											'offset'=>$offset
											
											)
										);
		// p($this->data['books']);
		$this->pagination->initialize($config);
		
		$this->template->load('admin/default','admin/book/view_all',$this->data);
	}
}