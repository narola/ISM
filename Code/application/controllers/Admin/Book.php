<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Book extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
        $this->load->library('upload','form_validation');
	}

	// function to list down the books author wise.
	public function index(){

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
		// p($authors_ids);

		// get the books under each author
		$author_books = array();
		foreach ($authors_ids as $author_id) {
			$author = select(TBL_USERS, TBL_USERS.'.id,'.TBL_USERS.'.full_name',
										array('where'=>array(TBL_USERS.'.id'=> $author_id)),
										array('single'=>true)
										);
			$array['author'] = $author;
			$books = select(TBL_AUTHOR_BOOK,TBL_BOOKS.'.*',
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
										)
									);
			$array['books'] = $books;
			$author_books[] = $array;
		}
		// p($author_books, true);
		$this->data['author_books'] = $author_books;
		$this->data['page_title'] = 'Books';
		$this->template->load('admin/default','admin/book/list',$this->data);
	}

	// function to view all books of a particular author
	// $id - id of the author
	public function view_all($id){

		// set condition for particular author
		$where[TBL_AUTHOR_BOOK.'.user_id'] = $id;

		if( !empty($_GET['author']) || !empty($_GET['tags']) || !empty($_GET['order']) ){

			if( !empty($_GET['author']) ) { $role = $this->input->get('author'); }	
			if( !empty($_GET['tags']) ) { $status = $this->input->get('tags'); }
			if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }		
			
			$str = '';
			

			/*if(!empty($role)){ $where['noticeboard_viewer.role_id'] = $role ; $str .= '&role='.$role; }	
			if(!empty($status)){ 
						if($status != 'template') { $where['noticeboard.status'] = $status;   $str .= '&status='.$status;}
						else{ $where['noticeboard.is_template'] = TRUE;   $str .= '&status='.$status; } 
					}
			if(!empty($classroom)){  $where['noticeboard_viewer.classroom_id'] = $classroom ; $str .= '&classroom='.$classroom; }		
			
			if($order == 'name_asc'){ $order = "noticeboard.notice asc"; $str.='&order=name_asc';  }
			if($order == 'name_desc'){ $order = "noticeboard.notice desc"; $str.='&order=name_desc'; }
			if($order == 'latest'){ $order = "noticeboard.created_date desc"; $str.='&order=latest'; }
			if($order == 'older'){ $order = "noticeboard.created_date asc"; $str.='&order=older'; }	


			$str =  trim($str,'&');
*/
			// pagination configuration when searched something
			$config['base_url'] = base_url().'admin/book/view_all/'.$id.'?'.$str;
			$config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
			$offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'

		}else{
			// pagination configuration normally when nothing searched
			$config['base_url'] = base_url().'admin/book/view_all/'.$id;	
			$offset = $this->uri->segment(5);
		}

		// other pagination settings
		$config['num_links'] = 3;
		$config['total_rows'] = select(TBL_AUTHOR_BOOK,FALSE,array('where'=>$where),array('count'=>TRUE));
		
		
		$this->data['page_number'] =  $this->uri->segment(5);
		$config['per_page'] = 4;
		$config['uri_segment'] = 5;
		
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

	 	// paginate the books as per the conditions and configurations
	 	$this->data['books'] = select(TBL_AUTHOR_BOOK,TBL_BOOKS.'.id,'. TBL_BOOKS.'.book_name,'.TBL_BOOKS.'.front_cover_image',
										array('where'=>$where),
										array(
											'limit'=>$config['per_page'],
											'offset'=>$offset,
											'join'=>array(
												array(
							    				'table' => TBL_BOOKS,
							    				'condition' => TBL_BOOKS.".id = ".TBL_AUTHOR_BOOK.".book_id",
							    				)
												)
											)
										);
		// qry();
		// p($this->data['books'], true);
		$this->pagination->initialize($config);
		$this->data['author_name'] = select(TBL_USERS,'full_name',array('where'=>array('id'=>$id)),array('single'=>true));
		$this->data['page_title'] = 'View All Books';
		
		$this->template->load('admin/default','admin/book/view_all',$this->data);
	}

	// function to show the details of a book
	// $id - id of the book
	public function book_detail($id){
		
		// get the book's details
		$book = select(TBL_BOOKS, FALSE,
										array('where'=>array(TBL_BOOKS.'.id'=> $id)),
										array('single'=>true)
										);

		// get the authors' ids of the book
		$book_author = select(TBL_AUTHOR_BOOK,TBL_AUTHOR_BOOK.'.user_id' ,
										array('where'=>array(TBL_AUTHOR_BOOK.'.book_id'=> $id))
										);

		$book_detail = array();
		$book_detail = $book;

		// get the profiles of each author
		foreach ($book_author as $author) {

			$author_profile = select(TBL_AUTHOR_PROFILE,TBL_USERS.'.full_name,'.TBL_AUTHOR_PROFILE.'.about_author',
										array('where'=>array(TBL_AUTHOR_PROFILE.'.user_id'=> $author['user_id'])),
										array('join'=> array(
												array(
							    				'table' => TBL_USERS,
							    				'condition' => TBL_USERS.".id = ".TBL_AUTHOR_PROFILE.".user_id",
							    				)
							    			),
										'single'=>true
										)
									);
			$book_detail['authors'][] = $author_profile;
		}

		$this->data['book_detail'] = $book_detail;
		// p($book_detail, true);
		$this->data['page_title'] = 'Book Details';
		$this->template->load('admin/default','admin/book/book_detail',$this->data);
	}

	// function to add new book
	public function add(){

		if(isset($_POST['btn_save'])){

			
			$data = array(
					'book_name'=>$this->input->post('book_name'),
					'book_description'=>$this->input->post('book_desc'),
					'back_cover_image'=>$this->input->post('status'),
					'publication_date'=>$this->input->post('publication_date'),
					'price'=>$this->input->post('price'),
					'publisher_name'=>$this->input->post('publisher_name'),
					'isbn_no'=>$this->input->post('isbn_no')
				);
			


			$book_id = insert(TBL_BOOKS,replace_invalid_chars($data));
			$this->set_links($book_id);
			$authors = $this->input->post('authors');
			foreach ($authors as $author) {
				$author_book = array(
					'book_id'=>$book_id,
					'user_id'=>$author
				);

				insert(TBL_AUTHOR_BOOK,replace_invalid_chars($author_book));
			}
			
			p($_FILES);
			p($_POST, true);
		}
		$this->data['authors'] = select(TBL_USERS,TBL_USERS.'.id,'.TBL_USERS.'.full_name',
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
		$this->data['page_title'] = 'Add New Book';
		$this->template->load('admin/default','admin/book/add',$this->data);
	}

	 public function set_links($bid){
		$path = "uploads/book_".$bid;

		if(!empty($_FILES)){
		    if(!is_dir($path)){
		      	mkdir($path,0755,TRUE);
		    }
		// upload front cover
		$ext 	= pathinfo($_FILES['front_cover']['name'], PATHINFO_EXTENSION);
		$name 	= str_replace('.'.$ext, '', $_FILES['front_cover']['name'].'_'.time()).'.'.$ext;
		$config['upload_path']	 	= $path;
		$config['allowed_types'] 	= 'gif|jpg|png|jpeg';
		$config['max_size']  		= '1000000000';	
		$config['file_name'] 		= $name;
		$error_count = 0;
		$this->upload->initialize($config);
		
			if (!$this->upload->do_upload('front_cover')){
				$file_upload_error = strip_tags($this->upload->display_errors(),'');
				$file_required_error = "You did not select a file to upload.";
				if($file_upload_error !== $file_required_error){
					$error_count++;
					$this->session->set_flashdata('error_front_cover', $file_upload_error);
				}
				else{
					$front_cover_link = '';
				}
			}
			else{
				$data = array('upload_data' => $this->upload->data());
				$front_cover_link = "book_".$bid.'/'.$data['upload_data']['file_name'];
			}

			if(!empty($front_cover_link)){
			
				$data_front_cover = array(
					'front_cover_image'=>$front_cover_link,
					"created_date"=>date('Y-m-d H:i:s',time()),
				 	"modified_date"=>date('Y-m-d H:i:s',time())
				);
				update(TBL_BOOKS,array('id'=>$bid),$data_front_cover);
			
			}

		// upload back cover
		$ext 	= pathinfo($_FILES['back_cover']['name'], PATHINFO_EXTENSION);
		$name 	= str_replace('.'.$ext, '', $_FILES['back_cover']['name'].'_'.time()).'.'.$ext;
		$config['upload_path']	 	= $path;
		$config['allowed_types'] 	= 'gif|jpg|png|jpeg';
		$config['max_size']  		= '1000000000';	
		$config['file_name'] 		= $name;
		$error_count = 0;
		$this->upload->initialize($config);
		
			if (!$this->upload->do_upload('back_cover')){
				$file_upload_error = strip_tags($this->upload->display_errors(),'');
				$file_required_error = "You did not select a file to upload.";
				if($file_upload_error !== $file_required_error){
					$error_count++;
					$this->session->set_flashdata('error_back_cover', $file_upload_error);
				}
				else{
					$back_cover_link = '';
				}
			}
			else{
				$data = array('upload_data' => $this->upload->data());
				$back_cover_link = "book_".$bid.'/'.$data['upload_data']['file_name'];
			}

			if(!empty($back_cover_link)){
			
				$data_back_cover = array(
					'back_cover_image'=>$back_cover_link,
					"created_date"=>date('Y-m-d H:i:s',time()),
				 	"modified_date"=>date('Y-m-d H:i:s',time())
				);
				update(TBL_BOOKS,array('id'=>$bid),$data_back_cover);
			
			}

			// upload pdf file
			$ext 	= pathinfo($_FILES['book_pdf']['name'], PATHINFO_EXTENSION);
			$name 	= str_replace('.'.$ext, '', $_FILES['book_pdf']['name'].'_'.time()).'.'.$ext;
			$config['upload_path']	 	= $path;
			$config['allowed_types'] 	= 'pdf';
			$config['max_size']  		= '1000000000';	
			$config['file_name'] 		= $name;
			$error_count = 0;
			$this->upload->initialize($config);
			
			if (!$this->upload->do_upload('book_pdf')){
				$file_upload_error = strip_tags($this->upload->display_errors(),'');
				$file_required_error = "You did not select a file to upload.";
				if($file_upload_error !== $file_required_error){
					$error_count++;
					$this->session->set_flashdata('error_book_pdf', $file_upload_error);
				}
				else{
					$book_pdf_link = '';
				}
			}
			else{
				$data = array('upload_data' => $this->upload->data());
				$book_pdf_link = "book_".$bid.'/'.$data['upload_data']['file_name'];
			}

			if(!empty($book_pdf_link)){
			
				$data_pdf_link = array(
					'pdf_link'=>$book_pdf_link,
					"created_date"=>date('Y-m-d H:i:s',time()),
				 	"modified_date"=>date('Y-m-d H:i:s',time())
				);
				update(TBL_BOOKS,array('id'=>$bid),$data_pdf_link);
			
			}

			// upload epub file
			$ext 	= pathinfo($_FILES['book_epub']['name'], PATHINFO_EXTENSION);
			$name 	= str_replace('.'.$ext, '', $_FILES['book_epub']['name'].'_'.time()).'.'.$ext;
			$config['upload_path']	 	= $path;
			$config['allowed_types'] 	= $_FILES['book_epub']['type'];
			$config['max_size']  		= '1000000000';	
			$config['file_name'] 		= $name;
			$error_count = 0;
			$this->upload->initialize($config);
			
				if (!$this->upload->do_upload('book_epub')){
					$file_upload_error = strip_tags($this->upload->display_errors(),'');
					// var_dump($file_upload_error);
					// exit;
					$file_required_error = "You did not select a file to upload.";
					if($file_upload_error !== $file_required_error){
						$error_count++;
						$this->session->set_flashdata('error_book_epub', $file_upload_error);
					}
					else{
						$book_epub_link = '';
					}
				}
				else{
					$data = array('upload_data' => $this->upload->data());
					$book_epub_link = "book_".$bid.'/'.$data['upload_data']['file_name'];
				}

				if(!empty($book_epub_link)){
				
					$data_book_epub = array(
						'ebook_link'=>$book_epub_link,
						"created_date"=>date('Y-m-d H:i:s',time()),
					 	"modified_date"=>date('Y-m-d H:i:s',time())
					);
					update(TBL_BOOKS,array('id'=>$bid),$data_book_epub);
				
				}
			}
		// crop(UPLOAD_URL.'/'.$student_profile,200,200);


		

		
    }
}
