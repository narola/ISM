<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Student Year upgradation 
 *
 * @param  -
 * @return - 
 * @author - Pankaj(pv)
 */

class Upgrade extends CI_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();

		$this->load->helper(array('csv','file'));		
		$this->load->library(array('zip'));
		$this->data['cur_url'] = $this->session->userdata('cur_url');
		$this->data['prev_url'] = $this->session->userdata('prev_url');
	}

	// ---------------------------- User Module Start --------------------------------------------
	
	/**
	  * function index() have all users listing using codeigniter pagination limit of 15 users per page
	  *
	  **/
	 
	public function index() {
		

		/* Update user curent year to next year */
		
		if(!empty($_POST['user_ids']))
		{
			$i = 0;
			foreach ($_POST['user_ids'] as  $value) {
				$ids = array(
					'user_id'=>$value);

				$new_ac_y = array();
				$new_ac_y = explode("-",$_POST['ac_years'][$i]);
				$temp_array = array();
				foreach ($new_ac_y as  $new_ac_y_value) {
					//$new_ac_y_value = $new_ac_y_value + 1;
					$temp_array[] = $new_ac_y_value + 1;
				}

				$final_academic_year = implode("-", $temp_array);
				$data = array(
					'academic_year' => $final_academic_year);

				update(TBL_STUDENT_ACADEMIC_INFO,$ids,$data);
				update(TBL_STUDENT_ACADEMIC_DET,$ids,$data);
			}
			
			
		}


		$this->data['page_title'] = 'Year upgradation';
		$order = '';
		$where['where'][TBL_USERS.'.is_delete']=FALSE;

		if(!empty($_GET['role']) || !empty($_GET['course']) || !empty($_GET['school']) || 
			!empty($_GET['classroom']) || !empty($_GET['q']) || !empty($_GET['order']) ){
			
			if( !empty($_GET['role']) ) { $role = $this->input->get('role'); }	
			if( !empty($_GET['acadamic_year']) ) { $academic_year = $this->input->get('acadamic_year'); }	
			if( !empty($_GET['course'])){ $course  = $this->input->get('course'); }
			if( !empty($_GET['school'])){ $school = $this->input->get('school'); }
			if( !empty($_GET['year']) ) { $year = $this->input->get('year'); }
			if( !empty($_GET['classroom']) ){  $classroom = $this->input->get('classroom'); }
			if( !empty($_GET['q']) ){  $q = replace_invalid_chars($this->input->get('q')); }
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		


			$str = '';

			if(!empty($role)){ $where['where']['role_id'] = $role ; $str .= '&role='.$role; }
			if(!empty($academic_year)){ $where['where'][TBL_STUDENT_ACADEMIC_INFO.'.academic_year'] = $academic_year ; $str .= '&acadamic_year='.$academic_year; }	
			if(!empty($course)){  $where['where'][TBL_STUDENT_ACADEMIC_INFO.'.course_id'] = $course; $str .='&course='.$course; }
			if(!empty($school)){  $where['where'][TBL_STUDENT_ACADEMIC_INFO.'.school_id'] = $school; $str .='&school='.$school; }
			if(!empty($classroom)){ $where['where'][TBL_STUDENT_ACADEMIC_INFO.'.classroom_id'] = $classroom; $str .= '&classroom='.$classroom;  }
			if(!empty($year)){ 
								$next_year=$year+1; $academic_year = "$year-$next_year";    // find next year and create string like 2015-2016
								$where['where'][TBL_STUDENT_ACADEMIC_INFO.'.academic_year'] = $academic_year; $str .='&year='.$year;  
							}
			if(!empty($q)){ $where['like'][TBL_USERS.'.username'] = $q; $str.='&q='.$q; }							

			if($order == 'name_asc'){ $order = TBL_USERS.".username asc"; $str.='&order=name_asc';  }
			if($order == 'name_desc'){ $order = TBL_USERS.".username desc"; $str.='&order=name_desc'; }
			if($order == 'latest'){ $order = TBL_USERS.".created_date desc"; $str.='&order=latest'; }
			if($order == 'older'){ $order = TBL_USERS.".created_date asc"; $str.='&order=older'; }

			$str =  trim($str,'&');

			if(!empty($str)) { $config['base_url']	 = base_url().'admin/year_upgradation?'.$str; }else{ $config['base_url'] = base_url().'admin/user/index';  }
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
	
		}else{
			
			//$where['where'][TBL_USERS.'.is_delete']=FALSE;
			$where = array('where'=>array(TBL_USERS.'.is_delete'=> FALSE,TBL_USERS.'.role_id' => 6));
			
			$config['base_url']	 = base_url().'admin/year_upgradation';
			$offset = $this->uri->segment(4);
		}

		$config['uri_segment'] = 4;
		$config['num_links'] = 2;
		$config['total_rows'] = select(TBL_USERS,FALSE,$where,array('count'=>TRUE,'join'=>array(array('table' => TBL_STUDENT_ACADEMIC_INFO,'condition' => TBL_USERS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.user_id'))));
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

		//fetch all data of users joins with roles,cities,countries,states 
		$this->data['all_users'] =   select(TBL_USERS,
											TBL_USERS.'.id,'.TBL_USERS.'.user_status,'.TBL_USERS.'.username,'.TBL_CITIES.'.city_name,'.TBL_STATES.'.state_name,
											'.TBL_USERS.'.role_id,'.TBL_ROLES.'.role_name,'.TBL_STUDENT_ACADEMIC_INFO.'.course_id,'.TBL_COURSES.'.course_name,
											'.TBL_CLASSROOMS.'.class_name,'.TBL_USER_PROFILE_PICTURE.'.profile_link,,'.TBL_STUDENT_ACADEMIC_INFO.'.academic_year,'.TBL_USERS.'.profile_pic',
											$where,
											array(
												'order_by'=>$order,
												'group_by'=>TBL_USERS.'.id',
												'join' =>  array(
											    			array(
											    				'table' => TBL_ROLES,
											    				'condition' => TBL_ROLES.'.id = '.TBL_USERS.'.role_id'
																),
											    			array(
											    				'table' => TBL_COUNTRIES,
											    				'condition' => TBL_COUNTRIES.'.id = '.TBL_USERS.'.country_id'
																),
											    			array(
											    				'table' => TBL_STATES,
											    				'condition' => TBL_STATES.'.id = '.TBL_USERS.'.state_id'
																),
											    			array(
											    				'table' => TBL_CITIES,
											    				'condition' => TBL_CITIES.'.id = '.TBL_USERS.'.city_id'
																),
											    			array(
											    				'table' => TBL_STUDENT_ACADEMIC_INFO,
											    				'condition' => TBL_USERS.'.id = '.TBL_STUDENT_ACADEMIC_INFO.'.user_id'
																),
											    			array(
											    				'table'=>TBL_COURSES,
											    				'condition'=>TBL_STUDENT_ACADEMIC_INFO.'.course_id='.TBL_COURSES.'.id'	
											    				),
											    			array(
											    				'table'=>TBL_STUDENT_ACADEMIC_DET,
											    				'condition'=>TBL_STUDENT_ACADEMIC_INFO.'.user_id='.TBL_STUDENT_ACADEMIC_DET.'.user_id'	
											    				),
											    			array(
											    				'table'=>TBL_CLASSROOMS,
											    				'condition'=>TBL_STUDENT_ACADEMIC_INFO.'.classroom_id='.TBL_CLASSROOMS.'.id'	
											    				),
											    			array(
											    				'table'=>TBL_USER_PROFILE_PICTURE,
											    				'condition'=>TBL_USER_PROFILE_PICTURE.'.user_id='.TBL_USERS.'.id'	
											    				)			
												    		)
												)
											);
		// qry();
		// p($this->data['all_users'],true);
		$this->pagination->initialize($config);
		
		$this->data['schools'] = select(TBL_SCHOOLS,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['courses'] = select(TBL_COURSES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['roles'] = select(TBL_ROLES,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$this->data['classrooms'] = select(TBL_CLASSROOMS,FALSE,array('where'=>array('is_delete'=>FALSE)));
		$select = TBL_STUDENT_ACADEMIC_DET.".academic_year";
		$option = array(
			'group_by' => TBL_STUDENT_ACADEMIC_DET.'.academic_year');
		$this->data['academic_years'] = select(TBL_STUDENT_ACADEMIC_DET,$select,NULL,$option);

		$this->template->load('Admin/default','admin/upgradation/year_upgradation',$this->data);
	}

}

/* End of file upgrade.php */
/* Location: ./application/controllers/Admin/upgrade.php */
