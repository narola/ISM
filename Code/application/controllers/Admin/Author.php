<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Author extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
	}

	// function to list down the authors 
	public function index(){
		$this->data['page_title'] = 'Authors';

		if(!empty($_GET['role']) || !empty($_GET['course']) || !empty($_GET['school']) || 
			!empty($_GET['classroom']) || !empty($_GET['q']) || !empty($_GET['order']) ){

			if( !empty($_GET['role']) ) { $role = $this->input->get('role'); }	
			if( !empty($_GET['course'])){ $course  = $this->input->get('course'); }
			if( !empty($_GET['school'])){ $school = $this->input->get('school'); }
			if( !empty($_GET['year']) ) { $year = $this->input->get('year'); }
			if( !empty($_GET['classroom']) ){  $classroom = $this->input->get('classroom'); }
			if( !empty($_GET['q']) ){  $q = replace_invalid_chars($this->input->get('q')); }
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		


			$str = '';

			if(!empty($role)){ $where['where']['role_id'] = $role ; $str .= '&role='.$role; }	
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

			if(!empty($str)) { $config['base_url']	 = base_url().'admin/user/index?'.$str; }else{ $config['base_url'] = base_url().'admin/user/index';  }
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
	
		}else{
			
			$where['where'][TBL_USERS.'.is_delete']=FALSE;
			
			$config['base_url']	 = base_url().'admin/user/index';
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



		// get the authors 
		$authors = select(TBL_USERS,TBL_USERS.'.id,'.TBL_USERS.'.full_name',
										array('where'=>array(
											TBL_ROLES.'.role_name'=>'author',
											TBL_USERS.'.is_delete'=>0
											)),
										array(
											'join'=> array(
													array(
								    				'table' => TBL_ROLES,
								    				'condition' => TBL_ROLES.".id = ".TBL_USERS.".role_id",
								    				),
												)
											)
								);
		
		$authors_ids = array_column($authors, 'id');
		
		// get the books under each author
		$author_books = array();
		foreach ($authors_ids as $author_id) {
			$author = select(TBL_USERS, TBL_USERS.'.id,'.TBL_USERS.'.full_name,'.TBL_USERS.'.profile_pic,'.TBL_USERS.'.user_status,'.TBL_AUTHOR_PROFILE.'.education',
										array('where'=>array(TBL_USERS.'.id'=> $author_id)),
										array(
											'single'=>true,
											'join'=>array(
													array(
									    				'table' => TBL_AUTHOR_PROFILE,
									    				'condition' => TBL_AUTHOR_PROFILE.".user_id = ".TBL_USERS.".id",
								    				)
												)
											)
										);
			$array['author'] = $author;
			$books = select(TBL_AUTHOR_BOOK,TBL_BOOKS.'.id,'.TBL_BOOKS.'.book_name,'.TBL_BOOKS.'.front_cover_image',
										array('where'=>array(
											TBL_AUTHOR_BOOK.'.user_id'=> $author_id,
											)
										),
										array('join'=> array(
												array(
								    				'table' => TBL_BOOKS,
								    				'condition' => TBL_BOOKS.".id = ".TBL_AUTHOR_BOOK.".book_id",
							    				)
							    			),
										'limit'=>4
										)
									);
			$array['books'] = $books;
			$author_books[] = $array;
		}
		// p($author_books, true);
		$this->data['author_books'] = $author_books;
		$this->data['authors_list'] = $authors;
		$this->template->load('admin/default','admin/author/list',$this->data);
	}
}