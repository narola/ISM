<?php 
if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Exam extends ADMIN_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		$this->data['cur_url'] = $this->session->userdata('cur_url');
		$this->data['prev_url'] = $this->session->userdata('prev_url');
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
			
			$button_type = $this->input->post('button_type');

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

			$id = insert(TBL_EXAM_SCHEDULE,$exam_schedule);

			if($button_type == 'set'){
				redirect('admin/question/set?exam='.$id);	
			}else{
				$this->session->set_flashdata('success', 'Exam has been Successfully Created');
				redirect($this->data['prev_url']);	
			}

		}
	}

	//Update one item
	public function update( $id = NULL ){

		$copy = $this->uri->segment(3);

		$this->data['exam']	= select(
									 TBL_EXAMS.' exam',
									 'exam.id,exam.exam_name,exam.exam_type,exam.exam_category,exam.classroom_id,exam.subject_id,classroom.course_id,exam.pass_percentage,exam.duration,
									  exam.attempt_count,exam.instructions,exam.negative_marking,exam.random_question,exam.declare_results,
									  '.TBL_EXAM_SCHEDULE.'.start_date,'.TBL_EXAM_SCHEDULE.'.start_time,'.TBL_EXAM_SCHEDULE.'.id as schedule_id',
									 array('where'=>array('exam.id'=>$id)),
									 array(
									 		'single'=>TRUE,
									 		'join'=>array(
										 				array(
										 						'table'=>TBL_EXAM_SCHEDULE,
										 						'condition'=>TBL_EXAM_SCHEDULE.'.exam_id=exam.id'
										 					),
										 				array(
										 						'table'=>TBL_CLASSROOMS.' as classroom',
										 						'condition'=>'exam.classroom_id=classroom.id'
										 					)
									 			       )
									 	  	)
									);

		if(empty($this->data['exam'])){
			redirect('admin/exam');
		}

		$this->data['exam_schedule'] = select(TBL_EXAM_SCHEDULE,FALSE,array('where'=>array('exam_id'=>$id)),array('single'=>TRUE));
		
		$this->data['all_courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		
		$this->data['all_subjects'] = select(
												TBL_SUBJECTS,
												TBL_SUBJECTS.'.id,'.TBL_SUBJECTS.'.subject_name,'.TBL_COURSE_SUBJECT.'.classroom_id',
												array('where'=>array(
																		TBL_SUBJECTS.'.is_delete'=>FALSE,
																		TBL_COURSE_SUBJECT.'.classroom_id'=>$this->data['exam']['classroom_id'])
																	),
												array(
													//'group_by'=>TBL_SUBJECTS.'.id',
													'join'=>array(
															array(
																	'table'=>TBL_COURSE_SUBJECT,
																	'condition'=>TBL_SUBJECTS.'.id='.TBL_COURSE_SUBJECT.'.subject_id'
																)
														)
												)
											);

		$this->data['all_classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>FALSE,'course_id'=>$this->data['exam']['course_id'])));		

		if($_POST){
			
			$exam_name = $this->input->post('exam_name');

			if($copy == 'copy'){
				$exam_validation = 'trim|required|is_unique['.TBL_EXAMS.'.exam_name]';
			}else{
				$exam_name == $this->data['exam']['exam_name']?$exam_validation = 'trim|required':$exam_validation = 'trim|required|is_unique['.TBL_EXAMS.'.exam_name]';
			}
			
			
		}else{
			$exam_validation = 'trim|required';
		}

		$this->form_validation->set_rules('exam_name', 'Exam Name', $exam_validation );
		$this->form_validation->set_rules('course_id', 'Course Name', 'trim|required');
		$this->form_validation->set_rules('subject_id', 'Subject Name', 'trim|required');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim|required');
		$this->form_validation->set_rules('pass_percentage', 'Passing Percentage', 'trim|required');
		$this->form_validation->set_rules('exam_category', 'Exam Category', 'trim|required');
		$this->form_validation->set_rules('duration', 'Exam duration', 'trim|required');
		$this->form_validation->set_rules('attempt_count', 'Attempt Count', 'trim|required');
		$this->form_validation->set_rules('start_date', 'Start Exam Date', 'trim|required|callback_valid_date');

		if($this->form_validation->run() == FALSE){
			$this->template->load('admin/default','admin/exam/edit_exam',$this->data);
		}else{
			
			if(isset($_POST['exam_type'])){
				$exam_type = 'subject';
			}else{	
				$exam_type = 'topic';
			}
			
			// $button_type = $this->input->post('button_type');

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

			if($copy == 'copy'){
				
				$exam_id = insert(TBL_EXAMS,$exam_data); 

				$exam_schedule = array(
					'exam_id'=>$exam_id,
					'start_date'=>$this->input->post('start_date'),
					'start_time'=>$this->input->post('start_time'),
					'school_classroom_id'=>'1'
				);

				$id = insert(TBL_EXAM_SCHEDULE,$exam_schedule);

			}else{
				
				$exam_id = update(TBL_EXAMS,$id,$exam_data); 
				
				$exam_schedule = array(
					'exam_id'=>$this->data['exam']['id'],
					'start_date'=>$this->input->post('start_date'),
					'start_time'=>$this->input->post('start_time'),
					'school_classroom_id'=>'1'	
				);
				
				if(!empty($this->data['exam']['schedule_id'])){
					$id = update(TBL_EXAM_SCHEDULE,$this->data['exam']['schedule_id'],$exam_schedule);	
				}else{
					$id = insert(TBL_EXAM_SCHEDULE,$exam_schedule);	
				}
			}

			if($button_type == 'set'){
				redirect('admin/question/set?exam='.$id);	
			}else{
				$this->session->set_flashdata('success', 'Exam has been Successfully Updated.');
				redirect($this->data['prev_url']);	
			}
		}

	}

	//Delete one item
	public function delete( $id = NULL )
	{

	}

	public function fetch_question(){
	 
		$eid = $this->input->post('eid');
		
		if(!empty($eid)) {

			$where = array(TBL_EXAM_QUESTION.'.exam_id'=>$eid,TBL_EXAM_QUESTION.'.exam_id'=>$eid);	
			$questions = select(TBL_QUESTIONS,
									TBL_QUESTIONS.'.id,'.
									TBL_QUESTIONS.'.question_text,'.
									TBL_SUBJECTS.'.subject_name,'.
									TBL_EXAM_QUESTION.'.id as exam_ques_id,'.
									TBL_USERS.'.full_name',
					array('where'=>$where),
					array(
						'group_by'=>TBL_QUESTIONS.'.id,',
						'join'=>array(
							array(
			    				'table' => TBL_TUTORIAL_GROUP_QUESTION,
			    				'condition' => TBL_QUESTIONS.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.question_id',
								),
							array(
			    				'table' => TBL_TUTORIAL_TOPIC,
			    				'condition' => TBL_TUTORIAL_TOPIC.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.tutorial_topic_id',
								),
							array(
			    				'table' => TBL_SUBJECTS,
			    				'condition' => TBL_SUBJECTS.'.id = '.TBL_TUTORIAL_TOPIC.'.subject_id',
								),
							array(
			    				'table' => TBL_USERS,
			    				'condition' => TBL_USERS.'.id = '.TBL_QUESTIONS.'.question_creator_id',
								),
							array(
			    				'table' => TBL_EXAM_QUESTION,
			    				'condition' => TBL_EXAM_QUESTION.'.question_id = '.TBL_QUESTIONS.'.id',
								)
							),
						)
					);

				foreach ($questions as $key=>$question) {
					$choices = select(TBL_ANSWER_CHOICES,
									TBL_ANSWER_CHOICES.'.id,'.
									TBL_ANSWER_CHOICES.'.choice_text,',
									// TBL_ANSWER_CHOICES.'.question_id',
									array('where'=>array(TBL_ANSWER_CHOICES.'.question_id'=>$question['id'])),
									null
									);

					$questions[$key]['choices']=array_column($choices,'choice_text');

					}

					$this->data['questions'] = $questions;

					$new_str = '';
					$cnt = 1;
					foreach( $questions as $question) {

						$new_str.='<div class="question_wrapper" id="que_div_'.$cnt.'">
		                    <div class="question_left">
		                        <h5 class="txt_red">Question <span id="exam_quest_'.$cnt.'">'.$cnt.'</span></h5>                                        
		                        <p class="ques">'.$question["question_text"].'</p>
		                        <div class="answer_options_div">
		                            <ol>';
		                            	foreach($question['choices'] as $choice) {
		                                	$new_str .='<li>'.$choice.'</li>';
		                                }	

		                     $new_str .='</ol>
		                        </div>
		                    </div>
		                    <div class="notice_action">                                            
		                        <a href="#" class="icon icon_hand" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Move"></a>
		                        <a href="#" class="icon icon_edit_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Edit"></a>
		                        <a href="#" class="icon icon_copy_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Copy"></a>
		                        <a href="'.base_url().'admin/question/delete_question/'.$question['exam_ques_id'].'" onclick="delete_question(this.href,event,'.$cnt.')" 
		                        class="icon icon_delete_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Delete"></a>
		                    </div>
		                    <div class="clearfix"></div>
		                </div>';
		                $cnt++;
	            	}
	            	
	            if($new_str == ''){
	            	echo '<div class="question_wrapper">
		                    <div class="question_left">
		                        <h5 class="txt_red"><span></span></h5>                                        
		                        <p class="ques"> No Question Set For This Exm.</p>
		                        <div class="answer_options_div">
		                        </div>
		                    </div>
		                    <div class="clearfix"></div>
		                </div>';
	            }else{
	            	echo json_encode(array('new_str'=>$new_str,'count'=>$cnt)); 
	            }
            }else{
            	echo 'NO_EXAM_ID'; 
           }       
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


 