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

		// get the authors 
		$authors = select(TBL_USERS,TBL_USERS.'.id',
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
			$books = select(TBL_AUTHOR_BOOK,TBL_BOOKS.'.id,'.TBL_BOOKS.'.book_name,'.TBL_BOOKS.'.image_link',
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
		$this->template->load('admin/default','admin/author/list',$this->data);
	}
}