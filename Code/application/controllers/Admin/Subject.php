<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Subject extends ADMIN_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
        $this->load->library('upload');
        $this->data['prev_url'] = $this->session->userdata('prev_url');
    }
	 /**
      * Function for list all Courses
      */
	public function lists() {       
		$this->data['page_title'] = 'Subject';
        $q  = $this->input->get('q');
		$str = '';
        $where['where'][TBL_SUBJECTS.'.is_delete']=0;
		if(!empty($_GET['q']) ){

			if( !empty($_GET['q']) ){  $q = $this->input->get('q'); }
			if(!empty($q)){ $where['like'][TBL_SUBJECTS.'.subject_name'] = $q;$str .='&q='.$q; }
            
            $str =  trim($str,'&');

			if(!empty($str)) { $config['base_url']	 = base_url().'admin/subject/lists?'.$str; }else{ $config['base_url'] = base_url().'admin/subject/lists';  }
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
	
		}else{
			$where=null;
			$where['where'][TBL_SUBJECTS.'.is_delete']=FALSE;
			
			$config['base_url']	 = base_url().'admin/subject/lists?'.$str;
			$offset = $this->uri->segment(4);
		}

		$config['uri_segment'] = 4;
		$config['num_links'] = 5;
		$config['total_rows'] = select(TBL_SUBJECTS,FALSE,$where,array('count'=>TRUE));       
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

		//fetch all data of subjects
		$this->data['all_subjects'] =   select(TBL_SUBJECTS,
											TBL_SUBJECTS.'.id,'.TBL_SUBJECTS.'.subject_name,'.TBL_SUBJECTS.'.subject_image,',
                                            $where,
											array(
												'limit'=>$config['per_page'],
												'offset'=>$offset));

		$this->pagination->initialize($config);
		$this->template->load('admin/default','admin/subject/list',$this->data);
	}
    /**
	* ajax function to delete the Course 
	*/
	public function delete_subject(){
		$subject_id = $this->input->post('subject_id');
        $is_delete = 1;
		$data=array(
				 "is_delete"=> $is_delete
				 );
		update('subjects',$subject_id,$data);	// Update data  using common_model.php and cms_helper.php
		
		$response = array('is_delete'=>$is_delete,'id'=>'delete_'.$subject_id);
		echo json_encode($response);
		exit; 
	}

    /**
     * Function for add subject
     */
    public function add_subject()
    {
        $this->data['page_title'] = 'Subject Add';
        
        $this->form_validation->set_rules('subject_name', 'Subject Name', 'trim|required');
		if($this->form_validation->run() == FALSE){
            $this->template->load('admin/default','admin/subject/add_subject',$this->data);			
		}else{			
			$data=array(
				 "subject_name"=>$this->input->post("subject_name"),				
				 "created_date"=>date('Y-m-d H:i:s'),
				 "modified_date"=>date('Y-m-d H:i:s'),
				 "is_delete"=>0				 				
			);
			
			$insertid = insert(TBL_SUBJECTS,$data);	 // insert data into database using common_model.php and cms_helper.php
            $path = "uploads/subjects";
          
            if(!empty($_FILES['subject_image']['name'])){
                $name = '_dev_'.$insertid.$_FILES['subject_image']['name'];                
				$config['upload_path']	 	= $path;
				$config['allowed_types'] 	= 'gif|jpg|png';
				$config['max_size']  		= '1000000000';	
				$config['file_name'] 		= $name;
				$error_count = 0;
				$this->upload->initialize($config);				
					if (!$this->upload->do_upload('subject_image')){
						$file_upload_error = strip_tags($this->upload->display_errors(),'');
						$file_required_error = "You did not select a file to upload.";
						if($file_upload_error !== $file_required_error){
							$error_count++;
							$this->session->set_flashdata('error_profile', $file_upload_error);
						}						
					}
					else{                       
                            $data_subject_image = array('subject_image'=>'subjects/'.$name);
                            update(TBL_SUBJECTS,$insertid,$data_subject_image);                        
					}					
				}   
                exit;
			$this->session->set_flashdata('success', 'Record is Successfully created.');
			redirect('admin/subject/lists');
		}
    }
    
    /**
     * Function for update course
     */
    public function update_subject($id){

		$this->data['page_title'] = 'Subject Update';

		if(empty($id) && !is_numeric($id)){
			redirect('admin');
		}
      
        $this->data['subject'] = select(TBL_SUBJECTS,FALSE,array('where'=>array('id'=>$id)),array('single'=>TRUE));
		
		$this->form_validation->set_rules('subject_name', 'Subject Name', 'trim|required');
		
		if($this->form_validation->run() == FALSE){
            $this->template->load('admin/default','admin/subject/update_subject',$this->data);			
		}else{            
            $data=array(
				 "subject_name"=>$this->input->post("subject_name"),				
				 "modified_date"=>date('Y-m-d H:i:s')				 			 				
			);
			update(TBL_SUBJECTS,$id,$data);	// Update data using common_model.php and cms_helper.php
            
            $path = "uploads/subjects/";           
            if(!empty($_FILES['subject_image']['name'])){
                $img_path = $path.$this->data['subject']['subject_image'];
                if (file_exists($img_path)){
                    unlink($img_path);
                }
                $ext 	= pathinfo($_FILES['subject_image']['name'], PATHINFO_EXTENSION);
                $name 	= str_replace('.'.$ext, '', '_dev_'.$id.date('ymdhis')).'.'.$ext;
				$config['upload_path']	 	= $path;
				$config['allowed_types'] 	= 'gif|jpg|png';
				$config['max_size']  		= '1000000000';	
				$config['file_name'] 		= $name;
				$error_count = 0;
				$this->upload->initialize($config);				
					if (!$this->upload->do_upload('subject_image')){
						$file_upload_error = strip_tags($this->upload->display_errors(),'');
						$file_required_error = "You did not select a file to upload.";
						if($file_upload_error !== $file_required_error){
							$error_count++;
							$this->session->set_flashdata('error_profile', $file_upload_error);
						}						
					}
					else{             
                            $data_subject_image = array('subject_image'=>'subjects/'.$name);
                            update(TBL_SUBJECTS,$id,$data_subject_image);                        
					}
		    }           
			$this->session->set_flashdata('success', 'Record is Successfully updated.');
			redirect('admin/subject/lists');
        }       
    }
}
/* End of file Subject.php */
/* Location: ./application/controllers/Admin/Subject.php */