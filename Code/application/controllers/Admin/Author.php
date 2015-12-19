<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Author extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
	}

	// function to list down the authors 
	public function index(){
		$order = '';
		$this->data['page_title'] = 'Authors';
		$where['where'][TBL_USERS.'.is_delete']=FALSE;
		$where['where'][TBL_ROLES.'.role_name']='author';

		if(!empty($_GET['q']) || !empty($_GET['author']) || !empty($_GET['order']) ){

			if (!empty($_GET['q'])) {$q = replace_invalid_chars($this->input->get('q'));}
			if( !empty($_GET['author']) ) { $author = $this->input->get('author'); }	
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		

			$str = '';

			if(!empty($author)){ $where['like'][TBL_USERS.'.id'] = $author; $str.='&author='.$author; }
			if (!empty($q)) {
                $where['like'][TBL_USERS . '.full_name'] = $q;
                // $where['like'][TBL_BOOKS . '.book_name'] = $q;
                $str.='&q=' . $q;
            }

			if($order == 'name_asc'){ $order = TBL_USERS.".full_name asc"; $str.='&order=name_asc';  }
			if($order == 'name_desc'){ $order = TBL_USERS.".full_name desc"; $str.='&order=name_desc'; }
			if($order == 'latest'){ $order = TBL_USERS.".created_date desc"; $str.='&order=latest'; }
			if($order == 'older'){ $order = TBL_USERS.".created_date asc"; $str.='&order=older'; }

			$str =  trim($str,'&');

			if(!empty($str)) { $config['base_url']	 = base_url().'admin/author/index?'.$str; }else{ $config['base_url'] = base_url().'admin/author/index';  }
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
	
		}else{
			
			$config['base_url']	 = base_url().'admin/author/index';
			$offset = $this->uri->segment(4);
		}

		$config['uri_segment'] = 4;
		$config['num_links'] = 2;
		$config['total_rows'] = select(TBL_USERS,TBL_USERS.'.id',$where,array('count'=>TRUE,
									'join'=>array(
										array(
											'table' => TBL_ROLES,
						    				'condition' => TBL_ROLES.".id = ".TBL_USERS.".role_id"
						    				),
						    				array(
											'table' => TBL_AUTHOR_BOOK,
						    				'condition' => TBL_AUTHOR_BOOK.".user_id = ".TBL_USERS.".id"
						    				),
						    				array(
											'table' => TBL_BOOKS,
						    				'condition' => TBL_BOOKS.".id = ".TBL_AUTHOR_BOOK.".book_id"
						    				),	
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

		// get the authors 
		$authors = select(TBL_USERS,TBL_USERS.'.id,'.TBL_USERS.'.full_name',
								$where,
								array(
									'order_by'=>$order,
    								'limit' => $config['per_page'],
    								'offset' => $offset,
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
		$this->pagination->initialize($config);
		// p($author_books, true);
		$this->data['author_books'] = $author_books;
		$this->data['authors_list'] = $authors;
		$this->template->load('admin/default','admin/author/list',$this->data);
	}
}