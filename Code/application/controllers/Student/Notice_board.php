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
	    $this->load->library('upload','form_validation');
	}

	public function index()
	{	
		$id 	=	$this->session->userdata('user')['id'];
		$where 	= 	array('or_where' => array('nv.classroom_id'=> null,'si.user_id' => $id),'where'=>array('role_id'=>2));
		$option	=	array('join' => 
						array(
							array(
								'table' 	=> 	TBL_NOTICEBOARD_VIEWER.' nv',
								'condition' =>	'nv.notice_id = n.id'
							),
							array(
								'table'		=>	TBL_STUDENT_ACADEMIC_INFO.' si',
								'condition' =>  'si.classroom_id = nv.classroom_id'
							)
						)
					);	
		$this->data['notice_list']	= select(TBL_NOTICEBOARD.' n','n.notice_title,n.notice,n.created_date',$where,$option);
		// qry();
		p($this->data['notice_list'],TRUE);
			
		$this->template->load('student/default','student/notice_board',$this->data);
		// exit();

	}
}
