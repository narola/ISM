<?php 
if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Exam extends CI_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		//Load Dependencies

	}

	// List all your items
	public function index(){

		$this->data['page_title'] = 'Exams';

		if(!empty($_GET['exam_type']) || !empty($_GET['subject']) || !empty($_GET['q']) || !empty($_GET['order']) ){

			$order = '';

			if( !empty($_GET['exam_type']) ) { $exam_type = $this->input->get('exam_type'); }
			if( !empty($_GET['subject']) ) { $subject = $this->input->get('subject'); }		
			if( !empty($_GET['topic']) ) { $topic = $this->input->get('topic'); }		
			if( !empty($_GET['q']) ) { $q = $this->input->get('q'); }
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		

			$str = '';  $where['where']=array();

			if(!empty($exam_type)){ $where['where']['exam.exam_type']=$exam_type; $str.='&exam_type='.$exam_type; }
			if(!empty($subject)){ $where['where']['exam.subject_id']=$subject; $str.='&subject='.$subject; }
			if(!empty($q)){ 
					$where['like']['exam.exam_name']=$q;  $str.='&q='.$q; 
				}

			if($order == 'name_asc'){ $order = "exam.exam_name asc"; $str.='&order='.$order;  }
			if($order == 'name_desc'){ $order = "exam.exam_name desc"; $str.='&order='.$order; }
			
			$str =  trim($str,'&');

			$config['base_url']	 = base_url().'admin/exam/index?'.$str;
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'
		}else{
			$order = '';
			$where=null;
			$where['where']['exam.is_delete']=FALSE;
			
			$config['base_url']	 = base_url().'admin/exam/index';
			$offset = $this->uri->segment(4);
		}



		//$config['base_url']	 = base_url().'admin/exam/index';
		// $offset = $this->uri->segment(4);

		$config['num_links'] = 5;
		$config['total_rows'] = select(TBL_EXAMS." exam",
	  									 "exam.id,exam.exam_name,exam.classroom_id,exam.subject_id,exam.exam_type,exam.exam_category,exam.exam_mode,
	  									 exam.attempt_count,sub.subject_name",
	  									 $where,
	  									 array(	
	  									 		'count'=>TRUE,
	  									 		'join'=>array(
	  									 					array(
	  									 						'table'=>TBL_SUBJECTS." sub",
	  									 						'condition'=>'sub.id=exam.subject_id'
	  									 					)
	  									 				)
	  									 	)
	  									 );
		$config['per_page'] = 15;

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

	  	$this->pagination->initialize($config);

	  	$this->data['all_exams'] = select(
	  									 TBL_EXAMS." exam",
	  									 "exam.id,exam.exam_name,exam.classroom_id,exam.subject_id,exam.exam_type,exam.exam_category,exam.exam_mode,
	  									 exam.attempt_count,sub.subject_name",
	  									 $where,
	  									 array(
	  									 		'order_by'=>$order,
	  									 		'limit'=>$config['per_page'],
	  									 		'offset'=>$offset,
	  									 		'join'=>array(
	  									 					array(
	  									 						'table'=>TBL_SUBJECTS." sub",
	  									 						'condition'=>'sub.id=exam.subject_id'
	  									 					)
	  									 				)
	  									 	)
	  									 );
	  	
	  	$this->data['all_subjects'] = select(TBL_SUBJECTS); // Fetch All Subjects 
	  	$this->data['all_topics'] = select(TBL_TUTORIAL_TOPIC); //Fetch All Topics

		$this->template->load('admin/default','admin/exam/view_exam',$this->data);
	}

	// Add a new item
	public function add(){

		$this->data['all_subjects'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['all_courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['all_classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>FALSE)));		

		$this->form_validation->set_rules('exam_name', 'Exam Name', 'trim|required');
		$this->form_validation->set_rules('course_id', 'Course Name', 'trim|required');
		$this->form_validation->set_rules('subject_id', 'Subject Name', 'trim|required');
		$this->form_validation->set_rules('pass_percentage', 'Passing Percentage', 'trim|required');

		if($this->form_validation->run() == FALSE){
			$this->template->load('admin/default','admin/exam/add_exam',$this->data);
		}else{

		}

		
	}

	//Update one item
	public function update( $id = NULL )
	{

	}

	//Delete one item
	public function delete( $id = NULL )
	{

	}
}

/* End of file Exam.php */
/* Location: ./application/controllers/Admin/Exam.php */


 