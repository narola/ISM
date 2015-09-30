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
	}

	public function index()
	{	
		// p($_POST,TRUE);
		$sort_by 	= $this->input->post('sort_by',TRUE);
		$sort_type 	= $this->input->post('sort_type',TRUE);
		$txt_search = $this->input->post('txt_search',TRUE);
		if($sort_type == 'ascending')
			$order_by = 'ASC';
		elseif($sort_type == 'descending')	
			$order_by = 'DESC';
		else
			$order_by = 'ASC';
		if($sort_by == 'date'){
			$order_column = 'n.created_date';
		}
		elseif($sort_by == 'title'){
			$order_column = 'n.notice_title';
		}
		else
			$order_column = 'n.id';

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
						'order_by' => $order_column.' '.$order_by
					);	
		$this->data['notice_list']	= 	select(TBL_NOTICEBOARD_VIEWER.' nv','n.notice_title,n.notice,n.created_date',$where,$option);
		$this->data['sort_type']	=	$sort_type;	
		$this->data['sort_by']		=	$sort_by;
		$this->data['txt_search']	=	$txt_search;
		// qry(true);
		// p($this->data['notice_list'],TRUE);
			
		$this->template->load('student/default','student/notice_board',$this->data);
		// exit();

	}
}
