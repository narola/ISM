<?php 
if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Exam extends ADMIN_Controller {

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
			if($order == 'latest'){ $order = "exam.created_date asc"; $str.='&order='.$order; }
			if($order == 'older'){ $order = "exam.created_date desc"; $str.='&order='.$order; }
			
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
	  									 exam.attempt_count,sub.subject_name,sub.subject_image",
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

	  // /	p($this->data['all_exams'],true);

	  	$this->data['all_subjects'] = select(TBL_SUBJECTS); // Fetch All Subjects 
	  	$this->data['all_topics'] = select(TBL_TUTORIAL_TOPIC); //Fetch All Topics

		$this->template->load('admin/default','admin/exam/view_exam',$this->data);
	}

	// Add a new item
	public function add(){

		$this->data['all_subjects'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['all_courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['all_classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>FALSE)));		

		$this->form_validation->set_rules('exam_name', 'Exam Name', 'trim|required|is_unique['.TBL_EXAMS.'.exam_name]');
		$this->form_validation->set_rules('course_id', 'Course Name', 'trim|required');
		$this->form_validation->set_rules('subject_id', 'Subject Name', 'trim|required');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim|required');
		$this->form_validation->set_rules('pass_percentage', 'Passing Percentage', 'trim|required');
		$this->form_validation->set_rules('exam_category', 'Exam Category', 'trim|required');
		$this->form_validation->set_rules('duration', 'Exam duration', 'trim|required');
		$this->form_validation->set_rules('attempt_count', 'Attempt Count', 'trim|required');
		$this->form_validation->set_rules('start_date', 'Start Exam Date', 'trim|required|callback_valid_date');


		if($this->form_validation->run() == FALSE){
			$this->template->load('admin/default','admin/exam/add_exam',$this->data);
		}else{
			
			if(isset($_POST['exam_type'])){
				$exam_type = 'subject';
			}else{	
				$exam_type = 'topic';
			}
			p($_POST,true);
			$exam_data=array(
					'exam_name'=>$this->input->post('exam_name'),
					'classroom_id'=>$this->input->post('classroom_id'),
					'subject_id'=>$this->input->post('subject_id'),
					'exam_type'=>$exam_type,
					'exam_category'=>$this->input->post('exam_category'),
					'pass_percentage'=>$this->input->post('pass_percentage'),
					'duration'=>$this->input->post('duration'),
					'attempt_count'=>$this->input->post('attempt_count'),
					'instructions' => htmlspecialchars($this->input->post('instructions')),
					'negative_marking'=>$this->input->post('negative_marking'),
					'random_question'=>$this->input->post('random_question'),
					'declare_results'=>$this->input->post('declare_results')
				);

			$exam_id = insert(TBL_EXAMS,$exam_data); // Insert Data into database and return Inserted ID using common_model.php and cms_helper.php

			$exam_schedule = array(
					'exam_id'=>$exam_id,
					'start_date'=>$this->input->post('start_date'),
					'start_time'=>$this->input->post('start_time'),
					'school_classroom_id'=>'1'
				);

			insert(TBL_EXAM_SCHEDULE,$exam_schedule);

			$this->session->set_flashdata('success', 'Exam has been Successfully Created');

			redirect('admin/exam');

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

	public function valid_date($date){

		if (preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$date))
	    {
	        return true;
	    }else{
	    	$this->form_validation->set_message('valid_date','The Start Exam date is not valid.');
	        return false;
	    }

	}

}

/* End of file Exam.php */
/* Location: ./application/controllers/Admin/Exam.php */


 