<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
* kap - kamlesh pokiya 
*
*/

class Notice_board extends ISM_Controller {

	public function __construct()
	{
	    parent::__construct();
	    $this->data['title'] = 'ISM - Notice Board';
	}

	public function index()
	{	
		// p($_POST,TRUE);
		$sort_by 	= $this->input->get_post('sort_by',TRUE);
		$sort_type 	= $this->input->get_post('sort_type',TRUE);
		$txt_search = $this->input->get_post('txt_search',TRUE);
		$str = '';
		if($sort_type == 'ascending'){
			$order_by = 'ASC';
			$str .= '&sort_type=ascending';
		}
		elseif($sort_type == 'descending'){
			$order_by = 'DESC';
			$str .= '&sort_type=descending';
		}	
		else{
			$order_by = 'ASC';
		}
		if($sort_by == 'date'){
			$order_column = 'n.created_date';
			$str .= '&sort_by=date';
		}
		elseif($sort_by == 'title'){
			$order_column = 'n.notice_title';
			$str .= '&sort_type=title';
		}
		else
			$order_column = 'n.id';
		if($txt_search!='')
			$str .= '&txt_search='.$txt_search;

		if($str != '')
			$config['base_url'] 	= base_url().'/student/notice_board/index?'.$str;
		else
			$config['base_url'] 	= base_url().'/student/notice_board/index';
		$config['page_query_string'] = TRUE;
		$config['per_page'] 	= 5;
		$config['num_links'] 	= 5;
		$config["uri_segment"] 	= 4;
		$page = $this->input->get_post('per_page');
		// echo $page;
		// exit;
		$classroom_id 	=	$this->session->userdata('user')['classroom_id'];
		// p($id,TRUE);
		if($txt_search != '')
			$where 	= 	"(nv.classroom_id is null or nv.classroom_id =".$classroom_id.") and n.is_delete = 0 and (n.notice_title like '%".$txt_search."%' or n.notice like '%".$txt_search."%')";
		else
			$where 	= 	"(nv.classroom_id is null or nv.classroom_id =".$classroom_id.") and n.is_delete = 0";
		$option	=	array('join' => 
							array(
								array(
									'table' 	=> 	TBL_NOTICEBOARD.' n',
									'condition' =>	'nv.notice_id = n.id'
								)
							),
							'count'=>TRUE
						);	

		$config['total_rows'] 	= select(TBL_NOTICEBOARD_VIEWER.' nv','n.notice_title,n.notice,n.created_date',$where,$option);
							
		$option	=	array('join' => 
						array(
							array(
								'table' 	=> 	TBL_NOTICEBOARD.' n',
								'condition' =>	'nv.notice_id = n.id'
							)
						),
						'order_by' => $order_column.' '.$order_by,
						'limit' => $config['per_page'],
						'offset'=> $page
					);	
		$this->data['notice_list']	= 	select(TBL_NOTICEBOARD_VIEWER.' nv','n.notice_title,n.notice,n.created_date',$where,$option);
		$this->data['sort_type']	=	$sort_type;	
		$this->data['sort_by']		=	$sort_by;
		$this->data['txt_search']	=	$txt_search;
		$config['full_tag_open'] = '<ul class="pagination pagination_admin">';
	  	$config['full_tag_close'] = '</ul>';

	  	$config['num_tag_open'] = '<li>';
	  	$config['num_tag_close'] = '</li>';

	  	$config['first_link'] = 'First';
	  	$config['first_tag_open'] = '<li>';
	  	$config['first_tag_close'] = '</li>';

	  	$config['cur_tag_open'] = '<li style="display:none"></li><li><a style="background-color:#323941;">';
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


		$this->pagination->initialize($config);
		$this->data['pagination']=$this->pagination->create_links();

		// qry(true);
		// p($this->data['notice_list'],TRUE);
			
		$this->template->load('student/default','student/notice_board',$this->data);
		// exit();

	}
}
