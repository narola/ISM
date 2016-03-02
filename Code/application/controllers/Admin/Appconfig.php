<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Appconfig extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
	}

	/**
	 * [index description]
	 * function to load different background images for app login screens
	 * @return [type] [description]
	 */
	public function index(){

		$this->data['page_title'] = 'App Config';
		$this->data['tab'] = 'author';

		$app_array = array('author','student','teacher');
		$where = array();

		foreach ($app_array as $app_name) {
			if(isset($_POST[$app_name.'_status'])){
				if($this->input->post($app_name.'_status') != '')
					$where['where']['status'] = $this->input->post($app_name.'_status'); 
				$this->data[$app_name.'_status'] = $this->input->post($app_name.'_status');
				$this->data['tab'] = $app_name;
			}

			$where['where']['is_delete'] = 0;
			$where['where']['app_name'] = $app_name;
			$this->data[$app_name.'_images'] = select(TBL_APP_IMAGES,'id,image_url, status', $where);
		}

		if( isset($_POST['author_btn']) || isset($_POST['student_btn']) || isset($_POST['teacher_btn']) ){

			$tab='author';
			$selected_images = array();
			if(isset($_POST['author_btn'])){
				$tab = 'author';
				$selected_images = $this->input->post('author');
			}elseif(isset($_POST['student_btn'])){
				$tab = 'student';
				$selected_images = $this->input->post('student');
			}elseif(isset($_POST['teacher_btn'])){
				$tab = 'teacher';
				$selected_images = $this->input->post('teacher');
			}

			$this->data['tab'] = $tab;
			$data = array(
						'status'=>'archive'
						);
			
			update(TBL_APP_IMAGES, array('app_name'=>$tab), $data);
			
			if(!empty($selected_images)){
				foreach ($selected_images as $image) {
					$data = array(
						'status'=>'active'
						);
					update(TBL_APP_IMAGES, $image, replace_invalid_chars($data));
				}
			}

		}

		$this->template->load('admin/default','admin/appconfig/index',$this->data);
	}

	public function upload_images(){
		$app_user = strtolower($this->input->post('app_user'));

		$path = "uploads/images/app_images/".$app_user;

            // retrieve the number of images uploaded;
    $number_of_files = sizeof($_FILES['app_images']['tmp_name']);
    // considering that do_upload() accepts single files, we will have to do a small hack so that we can upload multiple files. For this we will have to keep the data of uploaded files in a variable, and redo the $_FILE.
    $files = $_FILES['app_images'];
    $errors = array();

    // first make sure that there is no error in uploading the files
    for($i=0;$i<$number_of_files;$i++)
    {
      if($files['error'][$i] != 0) $errors[$i][] = 'Couldn\'t upload file '.$files['name'][$i];
    }
    if(sizeof($errors)==0)
    {
      // now, taking into account that there can be more than one file, for each file we will have to do the upload
      // we first load the upload library
      $this->load->library('upload');
      // next we pass the upload path for the images
      $config['upload_path'] = $path;
      // also, we make sure we allow only certain type of images
      $config['allowed_types'] = 'gif|jpg|png';
      $time = time();
      for ($i = 0; $i < $number_of_files; $i++) {
      	$time++;
        $_FILES['app_image']['name'] = $files['name'][$i];
        $_FILES['app_image']['type'] = $files['type'][$i];
        $_FILES['app_image']['tmp_name'] = $files['tmp_name'][$i];
        $_FILES['app_image']['error'] = $files['error'][$i];
        $_FILES['app_image']['size'] = $files['size'][$i];

        $ext = pathinfo($_FILES['app_image']['name'], PATHINFO_EXTENSION);
        $name = str_replace('.'.$ext, '', '_dev_'.$app_user.'_'.$time).'.'.$ext;
       	$config['file_name'] = $name;
		
        //now we initialize the upload library
        $this->upload->initialize($config);

        // we retrieve the number of files that were uploaded
        if ($this->upload->do_upload('app_image'))
        {
          $data_app_image = array(
				'app_name' => $this->input->post('app_user'),
				'image_url'=>'app_images/'.$app_user.'/'.$name,
				'status'=>'archive',
				'is_testdata'=>'dev',
				'created_date'=>date('Y-m-d H:i:s',time()),
			 	'modified_date'=>date('Y-m-d H:i:s',time())
			);
			insert(TBL_APP_IMAGES,replace_invalid_chars($data_app_image));
        }
        else
        {
          $data['upload_errors'][$i] = $this->upload->display_errors();
        }

      }
         $this->session->set_flashdata('msg', 'Images Successfully Uploaded.');
   
    }
    else
    {
     	$this->session->set_flashdata('err', 'Something went wrong.');
    }
   	
    redirect('admin/appconfig');
	}

	public function delete_all(){

	}
}