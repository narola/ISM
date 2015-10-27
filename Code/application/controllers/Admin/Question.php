<?php 
if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Question extends ADMIN_Controller {
	public $data = array();

	public function __construct()
	{
		parent::__construct();
		//Load Dependencies

	}

	/*
	* function to set the questions for the tutorial topic
	*/
	public function set(){

		$where = array();

		if( !empty($_GET['course_id']) && !empty($_GET['classroom_id']) || !empty($_GET['subject_id']) || 
			!empty($_GET['topic_id'])  || !empty($_GET['exam_id']) ){
			
			$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>0)));	

			$where = array();
			$str = '';

			if( !empty($_GET['course_id']) ) { 
					$course_id = $this->input->get('course_id'); 
					$str .= '&course_id='.$course_id; 
					$this->data['classrooms'] =select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>0,'course_id'=>$course_id ))); 
			}else{
				$this->data['classrooms'] =select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>0)));
			}	
			
			if( !empty($_GET['classroom_id']) ) { 
				
				$classroom_id = $this->input->get('classroom_id'); 
				$this->data['subjects'] = select(TBL_CLASSROOM_SUBJECT,
													 TBL_CLASSROOM_SUBJECT.'.subject_id as id,sub.subject_name ',
													 array('where'=>array(TBL_CLASSROOM_SUBJECT.'.classroom_id'=>$classroom_id,'sub.is_delete'=>'0')),
														array(
															'join'=>array(
																		array(
															    				'table' => TBL_SUBJECTS.' sub',
															    				'condition' => 'sub.id = '.TBL_CLASSROOM_SUBJECT.'.subject_id',
																			)
																		)
															)
												 	);
				$where[TBL_QUESTIONS.'.classroom_id'] = $classroom_id;  
				$str .= '&classroom_id='.$classroom_id;

			}else{
				$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('is_delete'=>0)));
			}

			if( !empty($_GET['subject_id']) ) { 
			
					$subject_id = $this->input->get('subject_id'); 
			
					$this->data['topics'] = select(TBL_TUTORIAL_TOPIC, TBL_TUTORIAL_TOPIC.'.id,'.TBL_TUTORIAL_TOPIC.'.topic_name, ',
							array('where'=>array('subject_id'=>$subject_id,'is_delete'=>0)),
							null
					);
					
					$where[TBL_QUESTIONS.'.subject_id'] = $subject_id; 
					$str .= '&subject_id='.$subject_id;
			}else{
				$this->data['topics'] = select(TBL_TUTORIAL_TOPIC,FALSE,array('where'=>array('is_delete'=>0)));
			}	

			if( !empty($_GET['topic_id']) ) { 
				
				$topic_id = $this->input->get('topic_id'); 
				$where[TBL_TUTORIAL_GROUP_QUESTION.'.tutorial_topic_id'] = $topic_id; 
				$str .= '&topic_id='.$topic_id; 
			}

			// echo $classroom_id;
			// echo "<br/>";
			// echo $tutorial_topic_id;
			// echo "<br/>";
			// echo $subject_id;
		 
		}else{
			
			$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>0)));
			$this->data['classrooms'] =select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>0)));
			$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('is_delete'=>0)));
			$this->data['topics'] = select(TBL_TUTORIAL_TOPIC,FALSE,array('where'=>array('is_delete'=>0)));

		}


		$config['num_links'] = 3;
		$config['total_rows'] = $questions = select(TBL_QUESTIONS,
													TBL_QUESTIONS.'.id,'.TBL_QUESTIONS.'.question_text,'.TBL_SUBJECTS.'.subject_name,'.
													TBL_USERS.'.full_name',
													array('where'=>$where),
													array(
														'group_by'=>TBL_QUESTIONS.'.id',
														'count'=>TRUE,
														'join'=>array(
															array(
											    				'table' => TBL_TUTORIAL_GROUP_QUESTION,
											    				'condition' => TBL_QUESTIONS.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.question_id',
																),
															array(
											    				'table' => TBL_TUTORIAL_TOPIC,
											    				'condition' => TBL_TUTORIAL_TOPIC.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.tutorial_topic_id'
											    				),
															array(
											    				'table' => TBL_SUBJECTS,
											    				'condition' => TBL_SUBJECTS.'.id = '.TBL_TUTORIAL_TOPIC.'.subject_id',
																),
															array(
											    				'table' => TBL_USERS,
											    				'condition' => TBL_USERS.'.id = '.TBL_QUESTIONS.'.question_creator_id',
																),
															),
														)
													);


		$this->data['page_number'] =  $this->uri->segment(4);
		$config['per_page'] = 1;
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

		
		$questions = select(TBL_QUESTIONS,
							TBL_QUESTIONS.'.id,'.TBL_QUESTIONS.'.question_text,'.TBL_SUBJECTS.'.subject_name,'.TBL_USERS.'.full_name',
							array('where'=>$where),
							array(
								'group_by'=>TBL_QUESTIONS.'.id',
								'join'=>array(
									array(
					    				'table' => TBL_TUTORIAL_GROUP_QUESTION,
					    				'condition' => TBL_QUESTIONS.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.question_id',
										),
									array(
					    				'table' => TBL_TUTORIAL_TOPIC,
					    				'condition' => TBL_TUTORIAL_TOPIC.'.id = '.TBL_TUTORIAL_GROUP_QUESTION.'.tutorial_topic_id'
					    				),
									array(
					    				'table' => TBL_SUBJECTS,
					    				'condition' => TBL_SUBJECTS.'.id = '.TBL_TUTORIAL_TOPIC.'.subject_id',
										),
									array(
					    				'table' => TBL_USERS,
					    				'condition' => TBL_USERS.'.id = '.TBL_QUESTIONS.'.question_creator_id',
										),
									),
								)
							);

		// p($questions);
		// qry(true);

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
		
		// p($questions,true);
		$this->data['questions'] = $questions;
		
		// ------------------------------------------------------------------------

		$this->data['page_title'] = 'Set Question';
		
		$this->data['exams'] = select(TBL_EXAMS,FALSE,$where);	

		$this->template->load('admin/default','admin/question/set',$this->data);
	}

	// ------------------------------------------------------------------------

	/*
	* function to Add Question into Database
	*/		
	public function add(){
		
		$this->data['page_tite'] = 'Add Question';
		$this->data['tags'] = select(TBL_TAGS,FALSE,array('where'=>array('is_delete'=>'0')));

		$where = array('where'=>array('is_delete'=>0));
		
		$this->data['courses'] = select(TBL_COURSES,FALSE,$where,null);
		$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,$where,null);
		$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,$where,null);
		$this->data['topics'] = select(TBL_TUTORIAL_TOPIC,FALSE,$where,null);

		$this->form_validation->set_rules('question_text', 'Question Text', 'trim|required');
		$this->form_validation->set_rules('course_id', 'Course', 'trim|required');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim|required');
		$this->form_validation->set_rules('subject_id', 'Subject', 'trim|required');
		$this->form_validation->set_rules('topic_id', 'Topic', 'trim|required');
		$this->form_validation->set_rules('correct_ans', 'Correct Answer', 'trim|required');
		//$this->form_validation->set_rules('q_tags', 'Question Tags', 'trim|required');

		$error_count = 0;

		if($_POST){

			$choices = $this->input->post('choices');
			foreach($choices as $choice){
				if($choice == ''){
					$this->form_validation->set_rules('choice', 'Choice', 'trim|required');
				}
			}

		}

		if($this->form_validation->run() == FALSE){

			$this->template->load('admin/default','admin/question/add',$this->data);
		
		}else{

			$session_id = $this->session->userdata('id');
			$choices = $this->input->post('choices');
			$tags  = $this->input->post('q_tags');

			$data_question = array(
				 "question_text"=>$this->input->post("question_text"),
				 "question_hint"=>'  ',
				 "question_creator_id"=>$session_id,
				 "assets_link"=>' ',
				 "question_image_link"=>' ',
				 "evaluation_notes"=>$this->input->post("evaluation_notes"),
				 "solution"=>$this->input->post("solution"),
				 "topic_id"=>$this->input->post("topic_id"),
				 "subject_id"=>$this->input->post("subject_id"),
				 "classroom_id"=>$this->input->post("classroom_id"),
			);

			$question_id = insert(TBL_QUESTIONS,$data_question);

			if(!empty($tags)){
				foreach($tags as $tag){
					$data_question_tags =array('question_id'=>$question_id,'tag_id' => $tag );	
					insert(TBL_TAGS_QUESTION,$data_question_tags);					
				}
			}

			

			$correct_choice =  $this->input->post('correct_choice')-1;
			$cnt = 0;

			foreach($choices as $choice){
				
				$cnt==$correct_choice ? $is_right = TRUE : $is_right= FALSE; // Set Correct Answer for Choices

				$data_choice = array(
							'question_id'=>$question_id,
							'choice_text'=>$choice,
							'is_right'=>$is_right,
							'image_link'=>'',
							'audio_link'=>'',
							'video_link'=>''
						);

				insert(TBL_ANSWER_CHOICES,$data_choice);					

				$cnt++;
			}


			
		}
	}

	// ------------------------------------------------------------------------

	/*
	* function to Update Question into Database
	*/		
	public function update($qid){

		$this->data['page_tite'] = 'Add Question';
		
		$this->data['tags'] = select(TBL_TAGS,FALSE,array('where'=>array('is_delete'=>'0')));
		
		$this->data['question'] = select(
											TBL_QUESTIONS,
											TBL_QUESTIONS.'.id,'.TBL_QUESTIONS.'.question_text,'.TBL_QUESTIONS.'.topic_id,'.
											TBL_QUESTIONS.'.subject_id,'.TBL_QUESTIONS.'.classroom_id,'.TBL_CLASSROOMS.'.course_id,'.
											TBL_QUESTIONS.'.evaluation_notes,'.TBL_QUESTIONS.'.solution',
											array('where'=>array(TBL_QUESTIONS.'.id'=>$qid)),
											array(
												'single'=>TRUE,
												'join'=>array(
														array(
															'table'=>TBL_CLASSROOMS,
															'condition'=>TBL_QUESTIONS.'.classroom_id='.TBL_CLASSROOMS.'.id'	
														)
													)
												)
										);

		//p($this->data['question'],true);

		$where = array('where'=>array( 'is_delete'=>0 ));

		$this->data['question_tags'] = select(TBL_TAGS_QUESTION,FALSE,array('where'=>array('question_id'=>$qid)));
		$this->data['choices'] = select(TBL_ANSWER_CHOICES,FALSE,array('where'=>array('question_id'=>$qid))); 
		$this->data['choice_count'] = count($this->data['choices']);
		$correct_choice_count = 1;
		foreach($this->data['choices'] as $q_choice){
			if($q_choice['is_right'] == TRUE){
				$this->data['correct_choice_que'] = $correct_choice_count;
			}
			$correct_choice_count++;
		}
		
		//p($this->data['question_tags']);
		//p($this->data['choices']);
		//die();

		$this->data['courses'] = select(TBL_COURSES,FALSE,$where,null);
		$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>0,'course_id'=>$this->data['question']['course_id'])),null);
		$this->data['subjects'] = select(TBL_SUBJECTS,FALSE,$where,null);
		$this->data['topics'] = select(TBL_TUTORIAL_TOPIC,FALSE,$where,null); 

		$this->form_validation->set_rules('question_text', 'Question Text', 'trim|required');
		$this->form_validation->set_rules('course_id', 'Course', 'trim|required');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim|required');
		$this->form_validation->set_rules('subject_id', 'Subject', 'trim|required');
		$this->form_validation->set_rules('topic_id', 'Topic', 'trim|required');
		$this->form_validation->set_rules('correct_ans', 'Correct Answer', 'trim|required');
		//$this->form_validation->set_rules('q_tags', 'Question Tags', 'trim|required');

		$error_count = 0;

		if($_POST){
			$choices = $this->input->post('choices');
			foreach($choices as $choice){
				if($choice == ''){
					$this->form_validation->set_rules('choice', 'Choice', 'trim|required');
				}
			}
		}

		if($this->form_validation->run() == FALSE){

			$this->template->load('admin/default','admin/question/update',$this->data);
		
		}else{

			$session_id = $this->session->userdata('id');
			$choices = $this->input->post('choices');
			$tags  = $this->input->post('q_tags');

			$data_question = array(
				 "question_text"=>$this->input->post("question_text"),
				 "question_hint"=>'  ',
				 "question_creator_id"=>$session_id,
				 "assets_link"=>' ',
				 "question_image_link"=>' ',
				 "evaluation_notes"=>$this->input->post("evaluation_notes"),
				 "solution"=>$this->input->post("solution"),
				 "topic_id"=>$this->input->post("topic_id"),
				 "subject_id"=>$this->input->post("subject_id"),
				 "classroom_id"=>$this->input->post("classroom_id"),
			);

			$question_id = insert(TBL_QUESTIONS,$data_question);

			if(!empty($tags)){
				foreach($tags as $tag){
					$data_question_tags =array('question_id'=>$question_id,'tag_id' => $tag );	
					insert(TBL_TAGS_QUESTION,$data_question_tags);					
				}
			}

			$correct_choice =  $this->input->post('correct_choice')-1;
			$cnt = 0;

			foreach($choices as $choice){
				
				$cnt==$correct_choice ? $is_right = TRUE : $is_right= FALSE; // Condition for Set Correct Answer for Choices

				$data_choice = array(
							'question_id'=>$question_id,
							'choice_text'=>$choice,
							'is_right'=>$is_right,
							'image_link'=>'',
							'audio_link'=>'',
							'video_link'=>''
						);

				insert(TBL_ANSWER_CHOICES,$data_choice);					

				$cnt++;
			
			}

		}
	}

	// ------------------------------------------------------------------------

	/*
	* function to get the classrooms through the course.
	*/
	public function ajax_get_classrooms(){
		$course_id = $this->input->post('course_id');
		
		$classrooms = select(TBL_CLASSROOMS,TBL_CLASSROOMS.'.id,'.TBL_CLASSROOMS.'.class_name ',
			array('where'=>array(TBL_CLASSROOMS.'.course_id'=>$course_id))
			);

		
		$new_str = '';
		
		$new_str .= '<option selected value="" disabled >Classroom</option>';
		if(!empty($classrooms)){
			foreach($classrooms as $classroom){
				$new_str.='<option value="'.$classroom['id'].'">'.$classroom['class_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	// ------------------------------------------------------------------------

	/*
	* function to get the subjects through the classroom.
	*/
	public function ajax_get_subjects(){
		$classroom_id = $this->input->post('classroom_id');
		$course_id = $this->input->post('course_id');
		
		$subjects = select(TBL_CLASSROOM_SUBJECT,TBL_CLASSROOM_SUBJECT.'.subject_id,sub.subject_name ',
			array('where'=>array(TBL_CLASSROOM_SUBJECT.'.classroom_id'=>$classroom_id)),
				array(
					'join'=>array(
								array(
					    				'table' => TBL_SUBJECTS.' sub',
					    				'condition' => 'sub.id = '.TBL_CLASSROOM_SUBJECT.'.subject_id',
									)
								)
					)
			);

		
		$new_str = '';
		
		$new_str .= '<option selected disabled value="">Subject</option>';
		if(!empty($subjects)){
			foreach($subjects as $subject){
				$new_str.='<option value="'.$subject['subject_id'].'">'.$subject['subject_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	// ------------------------------------------------------------------------

	/*
	* function to get the tutorial topics through the subjects.
	*/
	public function ajax_get_topics(){
		$subject_id = $this->input->post('subject_id');
		
		$topics = select(TBL_TUTORIAL_TOPIC, TBL_TUTORIAL_TOPIC.'.id,'.TBL_TUTORIAL_TOPIC.'.topic_name, ',
							array('where'=>array('subject_id'=>$subject_id)),
							null
					);

		
		$new_str = '';
		
		$new_str .= '<option selected value="" disabled >Topic</option>';
		if(!empty($topics)){
			foreach($topics as $topic){
				$new_str.='<option value="'.$topic['id'].'">'.$topic['topic_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	// ------------------------------------------------------------------------

	public function set_question(){

		$qid = $this->input->post('qid');
		$eid = $this->input->post('eid');

		$fetch_data = select(TBL_EXAM_QUESTION,FALSE,array('where'=>array('exam_id'=>$eid,'question_id'=>$qid)));

		if(empty($fetch_data)){
			
			$data = array(
				'exam_id'=>$eid,
				'question_id'=>$qid
			);

			insert(TBL_EXAM_QUESTION,$data);

			$count = count(select(TBL_EXAM_QUESTION,FALSE,array('where'=>array('exam_id'=>$eid))));
			
			$where = array(TBL_QUESTIONS.'.id' => $qid,TBL_EXAM_QUESTION.'.exam_id'=>$eid,TBL_EXAM_QUESTION.'.question_id'=>$qid,);

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

			 $new_str = '';
			 foreach( $questions as $question) {

						$new_str.='<div class="question_wrapper" id="que_div_'.$count.'">
		                    <div class="question_left">
		                        <h5 class="txt_red">Question <span id="exam_quest_'.$count.'">'.$count.'</span></h5>                                        
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
		                        <a href="'.base_url().'admin/question/delete_question/'.$question['exam_ques_id'].'" onclick="delete_question(this.href,event,'.$count.')" 
		                        class="icon icon_delete_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Delete"></a>
		                    </div>
		                    <div class="clearfix"></div>
		                </div>';
	            	}

			echo json_encode(array('res'=>1,'new_str'=>$new_str,'count'=>$count)); 
		}else{
			echo json_encode(array('res'=>0)); 
		}
		

		//$data
	}

	// ------------------------------------------------------------------------

	public function delete_question($id){
		//$id=$this->input->post('id');
		delete(TBL_EXAM_QUESTION,$id);
		echo '1';
	}

}