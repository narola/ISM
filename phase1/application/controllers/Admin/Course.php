<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Course extends ADMIN_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
        $this->data['prev_url'] = $this->session->userdata('prev_url');
    }
	 
	/**
      * Function for list all Courses
     */

	public function lists() {      

		$this->data['page_title'] = 'Courses';
        
		$where['where'][TBL_COURSES.'.is_delete']=0;
        $category  = $this->input->get('category');
		$q  = replace_invalid_chars($this->input->get('q'));
		$order = '';
		
		$str = '';
        $where['where'][TBL_COURSES.'.is_delete']=0;

		if(!empty($_GET['category']) || !empty($_GET['q']) || !empty($_GET['order']) ){

			if( !empty($_GET['category']) ) { $role = $this->input->get('category'); }			
			if( !empty($_GET['q']) ){  $q = $this->input->get('q'); }
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		


			if(!empty($category)){ $where['where']['course_category_id'] = $category ; $str .= '&category='.$category; }			
			if(!empty($q)){ 
					$where['like'][TBL_COURSES.'.course_name'] = $q; 
					$str .='&q='.$q; 
			}
            
            if($order == 'name_asc'){ $order = TBL_COURSES.".course_name asc"; $str.='&order=name_asc';  }
			if($order == 'name_desc'){ $order = TBL_COURSES.".course_name desc"; $str.='&order=name_desc'; }
			if($order == 'latest'){ $order = TBL_COURSES.".created_date desc"; $str.='&order=latest'; }
			if($order == 'older'){ $order = TBL_COURSES.".created_date asc"; $str.='&order=older'; }

            $str =  trim($str,'&');

			$config['base_url']	 = base_url().'admin/course/lists?'.$str; 
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
	
		}else{
			
			$config['base_url']	 = base_url().'admin/course/lists/index';
			$offset = $this->uri->segment(5); // Set URI Segment for 5 Because it is not in INDEX function
		}

		
		$config['num_links'] = 5;
		$config['total_rows'] = select(TBL_COURSES,FALSE,$where,array('count'=>TRUE,'join'=>array(array('table' => TBL_COURSE_CATEGORY,'condition' => TBL_COURSES.'.course_category_id = '.TBL_COURSE_CATEGORY.'.id'))));
		$config['per_page'] = 10;

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

		//fetch all data of course
		$this->data['all_courses'] =   select(TBL_COURSES,
											TBL_COURSES.'.id,'.TBL_COURSES.'.course_name,'.TBL_COURSES.'.course_nickname,'.TBL_COURSES.'.course_details,'.TBL_COURSES.'.course_type,
											'.TBL_COURSES.'.course_duration,'.TBL_COURSES.'.course_degree,'.TBL_COURSE_CATEGORY.'.id AS course_category_id,'.TBL_COURSE_CATEGORY.'.course_category_name',											
											$where,
											array(
												'order_by'=>$order,
												'limit'=>$config['per_page'],
												'offset'=>$offset,
												'join' =>  array(
											    			array(
											    				'table' => TBL_COURSE_CATEGORY,
											    				'condition' => TBL_COURSE_CATEGORY.'.id = '.TBL_COURSES.'.course_category_id'
																)
											    			)
												)
											);      
		
		$this->pagination->initialize($config);

		$this->data['course_category'] = select(TBL_COURSE_CATEGORY,FALSE,array('where'=>array('is_delete'=>0)),array('limit'=>10));
		$this->template->load('Admin/default','admin/course/list',$this->data);
	}
    
    /**
	* ajax function to delete the Course 
	*/
	public function delete_course($course_id){
		//$course_id = $this->input->post('course_id');
        $is_delete = 1;
		$data=array(
				 "is_delete"=> $is_delete
				 );
		update('courses',$course_id,$data);	// Update data  using common_model.php and cms_helper.php
		$this->session->set_flashdata('success', 'Course has been Successfully deleted.');
		redirect('admin/course/lists');
	}

    /**
     * Function for add course
     */
    public function add_course()
    {
        $this->data['page_title'] = 'Course Add';
        $this->data['course_category'] = select(TBL_COURSE_CATEGORY,FALSE,array('where'=>array('is_delete'=>0)),array('limit'=>10));
        $this->data['course_types'] = array('University','Primary','Secondary','Higher Secondary');
        
        $this->form_validation->set_rules('course_name', 'Course Name', 'trim|required');
		$this->form_validation->set_rules('course_nickname', 'Course Nickname', 'trim|required');	
		$this->form_validation->set_rules('course_type', 'Course Type', 'trim|required');
		$this->form_validation->set_rules('course_duration', 'Course Duration', 'trim|required|integer');
		$this->form_validation->set_rules('course_degree', 'Course Degree', 'trim|required');
		$this->form_validation->set_rules('course_category_id', 'Course Degree', 'required');
		

		if($this->form_validation->run() == FALSE){
            $this->template->load('Admin/default','admin/course/add_course',$this->data);			
		}else{			
			$data=array(
				 "course_name"=>$this->input->post("course_name"),				
				 "course_nickname"=>$this->input->post("course_nickname"),
				 "course_details"=>$this->input->post("course_details"),
				 "course_type"=>$this->input->post("course_type"),
				 "course_duration"=>$this->input->post("course_duration"),
				 "course_degree"=>$this->input->post("course_degree"),
				 "course_category_id"=>$this->input->post("course_category_id"),
				 "is_semester"=>$this->input->post("is_semester"),
				 "created_date"=>date('Y-m-d H:i:s'),
				 "modified_date"=>date('Y-m-d H:i:s'),
				 "is_delete"=>0				 				
			);
			
			insert(TBL_COURSES,replace_invalid_chars($data));	 // insert data into database using common_model.php and cms_helper.php

			$this->session->set_flashdata('success', 'Record is Successfully created.');
			redirect('admin/course/lists');
		}
    }
    
    /**
     * Function for update course
     */
    public function update_course($id){

		$this->data['page_title'] = 'Course Update';

		if(empty($id) && !is_numeric($id)){
			redirect('admin');
		}
      
        $this->data['course'] = select(TBL_COURSES,FALSE,array('where'=>array('id'=>$id)),array('single'=>TRUE));
		$this->data['course_category'] = select(TBL_COURSE_CATEGORY,FALSE,array('where'=>array('is_delete'=>0)),array('limit'=>10));
        $this->data['course_types'] = array('University','Primary','Secondary','Higher Secondary');
		
		$this->form_validation->set_rules('course_name', 'Course Name', 'trim|required');
		$this->form_validation->set_rules('course_nickname', 'Course Nickname', 'trim|required');	
		$this->form_validation->set_rules('course_type', 'Course Type', 'trim|required');	
		$this->form_validation->set_rules('course_duration', 'Course Duration', 'trim|required|integer');
		$this->form_validation->set_rules('course_degree', 'Course Degree', 'trim|required');
		$this->form_validation->set_rules('course_category_id', 'Course Degree', 'required|greater_than[0]');
		
		if($this->form_validation->run() == FALSE){
            $this->template->load('Admin/default','admin/course/update_course',$this->data);			
		}else{            
            
            $data=array(
				 "course_name"=>$this->input->post("course_name"),				
				 "course_nickname"=>$this->input->post("course_nickname"),
				 "course_details"=>$this->input->post("course_details"),
				 "course_type"=>$this->input->post("course_type"),
				 "course_duration"=>$this->input->post("course_duration"),
				 "course_degree"=>$this->input->post("course_degree"),
				 "course_category_id"=>$this->input->post("course_category_id"),
				 "is_semester"=>$this->input->post("is_semester"),				 
				 "modified_date"=>date('Y-m-d H:i:s'),
				 "is_delete"=>0				 				
			);	
			
			update(TBL_COURSES,$id,replace_invalid_chars($data));	// Update data using common_model.php and cms_helper.php
			$this->session->set_flashdata('success', 'Record is Successfully updated.');
			redirect('admin/course/lists');
        }
	}
}
/* End of file Course.php */
/* Location: ./application/controllers/Admin/Course.php */