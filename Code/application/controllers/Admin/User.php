<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * class User 
 * 	
 * @package default
 * @author Virendra patel ( Sparks ID- VPA )
 **/

class User extends ISM_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		
		$this->load->helper(array('csv','file'));		
		$this->load->library(array('zip'));
	}

	// ---------------------------- User Module Start --------------------------------------------
	 	
 	public function index(){

		$this->load->library('pagination');
		
		$role = $this->input->get('role');

		if(!empty($role)){
			$where = array('where'=>array('role_id'=>$role));	
		}else{
			$where=null;
		}	
		
		$config['base_url'] = base_url().'admin/user/index' ;
		$config['uri_segment'] = 4;
		$config['num_links'] = 5;
		$config['total_rows'] = $this->common_model->sql_select('users',FALSE,$where,array('count'=>TRUE));
		$config['per_page'] = 15;

		//$config['page_query_string'] = TRUE;

		$config['full_tag_open'] = '<ul class="pagination pagination_admin">';
 		$config['full_tag_close'] = '</ul>';

 		$config['num_tag_open'] = '<li>';
 		$config['num_tag_close'] = '</li>';

 		$config['first_link'] = 'First';
	 	$config['first_tag_open'] = '<li>';
	 	$config['first_tag_close'] = '</li>';

	 	$config['cur_tag_open'] = '<li><a>';
	 	$config['cur_tag_close'] = '</a></li>';

	 	$config['prev_link'] = '&laquo;';
	 	$config['prev_tag_open'] = '<li>';
	 	$config['prev_tag_close'] = '</li>';

	 	$config['next_link'] = '&raquo;';
	 	$config['next_tag_open'] = '<li>';
	 	$config['next_tag_close'] = '</li>';

	 	$config['last_link'] = 'Last';
	 	$config['last_tag_open'] = '<li>';
	 	$config['last_tag_close'] = '</li>';
	 	
		$offset = $this->uri->segment(4);

		$this->data['all_users'] = $this->common_model->sql_select('users',
																	'users.id,users.username,cities.city_name,states.state_name,
																	users.role_id,roles.role_name',
																	$where,
																	array(
																		'limit'=>$config['per_page'],
																		'offset'=>$offset,
																		'join' =>  array(
																	    			array(
																	    				'table' => 'roles',
																	    				'condition' => 'roles.id = users.role_id',
																						),
																	    			array(
																	    				'table' => 'countries',
																	    				'condition' => 'countries.id = users.country_id',
																						),
																	    			array(
																	    				'table' => 'states',
																	    				'condition' => 'states.id = users.state_id',
																						),
																	    			array(
																	    				'table' => 'cities',
																	    				'condition' => 'cities.id = users.city_id',
																						)
																		    		)
																		)
																	);
		$this->pagination->initialize($config);
		
		$this->data['schools'] = $this->common_model->sql_select(TBL_SCHOOLS,FALSE,FALSE,array('limit'=>10));
		$this->data['courses'] = $this->common_model->sql_select(TBL_COURSES,FALSE,FALSE,array('limit'=>10));
		$this->data['roles'] = $this->common_model->sql_select(TBL_ROLES,FALSE,FALSE,array('limit'=>10));
		
		$this->template->load('admin/default','admin/user/view_user',$this->data);
	}

	public function add(){

		$this->data['countries']  = $this->common_model->sql_select('countries');
		$this->data['states'] = $this->common_model->sql_select('states');
		$this->data['cities'] = $this->common_model->sql_select('cities');
		$this->data['roles'] = $this->common_model->sql_select('roles');
		$this->data['packages'] = $this->common_model->sql_select('membership_package');

		$this->form_validation->set_rules('username', 'User Name', 'trim|required|is_unique[users.username]');
		$this->form_validation->set_rules('password', 'Password', 'trim|required|min_length[8]');	
		$this->form_validation->set_rules('re_password', 'Retype Password', 'trim|required|matches[password]|min_length[8]');
		$this->form_validation->set_rules('email_id', 'Email', 'trim|valid_email|is_unique[users.email_id]');	

		if($this->form_validation->run() == FALSE){

			$this->template->load('admin/default','admin/user/add_user',$this->data);
			
		}else{

			$encrypted_password = $this->encrypt->encode($this->input->post("password"));

			$data=array(
				 "username"=>$this->input->post("username"),
				 "password"=>$encrypted_password,
				 "first_name"=>$this->input->post("first_name"),
				 "last_name"=>$this->input->post("last_name"),
				 "full_name"=>$this->input->post("full_name"),
				 "email_id"=>$this->input->post("email_id"),
				 "contact_number"=>$this->input->post("contact_number"),
				 "home_address"=>$this->input->post("home_address"),
				 "city_id"=>$this->input->post("city"),
				 "state_id"=>$this->input->post("state"),
				 "country_id"=>$this->input->post("country"),
				 "birthdate"=>$this->input->post("birthdate"),
				 "gender"=>$this->input->post("gender"),
				 "device_type"=>$this->input->post("device_type"),
				 "device_token"=>$this->input->post("device_token"),
				 "role_id"=>$this->input->post("roles"),
				 "user_status"=>$this->input->post("user_status"),
				 "about_me"=>$this->input->post("about_me"),
				 "user_current_api_version"=>$this->input->post("user_current_api_version"),
				 "membership_validity_date"=>$this->input->post("membership_validity_date"),
				 "package_id"=>$this->input->post("package"),
				 "created_date"=>date('Y-m-d H:i:s'),
				 "modified_date"=>date('Y-m-d H:i:s'),
				 "is_delete"=>0,
				 "is_testdata"=>'yes'
			);
			
			$this->common_model->insert('users',$data);	

			$this->session->set_flashdata('success', 'Data is Successfully created.');
			redirect('admin/user');
		}

	}

	public function test(){

		 //$this->template->load('admin/default','admin/user/select_test');
		$this->load->view('admin/user/select_test');
	}

	public function update($id){

		if(empty($id) && !	is_numeric($id)){
			redirect('admin/dashboard');
		 }

		$this->data['user'] = $this->common_model->sql_select('users',FALSE,array('where'=>array('id'=>$id)),array('single'=>TRUE));	
		$this->data['countries']  = $this->common_model->sql_select('countries');
		$this->data['states'] = $this->common_model->sql_select('states',FALSE,array('where'=>array('country_id'=>$this->data['user']['country_id'])));
	     $this->data['cities'] = $this->common_model->sql_select('cities',FALSE,array('where'=>array('state_id'=>$this->data['user']['state_id'])));
		$this->data['roles'] = $this->common_model->sql_select('roles');
		$this->data['packages'] = $this->common_model->sql_select('membership_package');
		


		if($_POST){
			
			$username = $this->input->post('username');
			$email = $this->input->post('email_id');

			if($this->data['user']['username'] !== $username){
				$user_rule = 'trim|required|is_unique[users.username]';
			}else{
				$user_rule = 'trim|required';
			}

			if($this->data['user']['email_id'] !== $email){
				$email_rule = 'trim|required|is_unique[users.email_id]';
			}else{
				$email_rule = 'trim|required';
			}

		}else{
			$user_rule = 'trim|required';
			$email_rule = 'trim|required';
		}

		$this->form_validation->set_rules('username', 'User Name', $user_rule);
		$this->form_validation->set_rules('email_id', 'Email', $email_rule);	

		if($this->form_validation->run() == FALSE){

			//$this->load->view('admin/user/update_user',$data);
			$this->template->load('admin/default','admin/user/update_user',$this->data);

		}else{
			
			$data=array(
				 "username"=>$this->input->post("username"),
				 "first_name"=>$this->input->post("first_name"),
				 "last_name"=>$this->input->post("last_name"),
				 "full_name"=>$this->input->post("full_name"),
				 "email_id"=>$this->input->post("email_id"),
				 "contact_number"=>$this->input->post("contact_number"),
				 "home_address"=>$this->input->post("home_address"),
				 "city_id"=>$this->input->post("city"),
				 "state_id"=>$this->input->post("state"),
				 "country_id"=>$this->input->post("country"),
				 "birthdate"=>$this->input->post("birthdate"),
				 "gender"=>$this->input->post("gender"),
				 "modified_date"=>date('Y-m-d H:i:s'),
				 "role_id"=>$this->input->post("roles"),
				 "user_status"=>$this->input->post("user_status"),
				 "about_me"=>$this->input->post("about_me"),
				 "package_id"=>$this->input->post("package")
			);
	
			$this->common_model->update('users',$id,$data);	

			$this->session->set_flashdata('success', 'Data is Successfully updated.');
			redirect('admin/user');

		}

	}

	public function blocked($id){
		$this->common_model->update('users',$id,array('user_status'=>'blocked'));
		$this->session->set_flashdata('success', 'User is Successfully Blocked.');
		redirect('admin/view_user');
	}

	public function send_message($id){
		
		$this->data['user'] = $this->common_model->sql_select('users',FALSE,array('where'=>array('id'=>$id)),array('single'=>true));
		$this->data['templates'] = $this->common_model->sql_select('messages',FALSE,array('where'=>array('is_template'=>'1')));
		
		$this->form_validation->set_rules('message_to', 'Recipient', 'trim|required');	
		$this->form_validation->set_rules('message_title', 'Message Title', 'trim|required');	
		$this->form_validation->set_rules('message_desc', 'Message', 'trim|required');	

		if($this->form_validation->run() == FALSE){
			
			$this->template->load('admin/default','admin/user/send_message',$this->data);
		}else{

			$username = $this->input->post('message_to');

			$user_data = $this->common_model->sql_select('users',FALSE,array('where'=>array('username'=>$username)),array('single'=>TRUE));

			if(!empty($user_data)){

				$data = array(
						'message_text'=>$this->input->post('message_desc'),
						'sender_id'=>$this->session->userdata('id'),
						'message_title'=>$this->input->post('message_title'),
						'status'=>'1',
						'reply_for'=>'0',
						'created_date'=>date('Y-m-d H:i:s',time()),
						'modified_date'=>'0000-00-00 00:00:00',
						'is_template'=>$this->input->post('save_template'),
						'is_delete'=>'0',
						'is_testdata'=>'yes'
					);

				$message_id = $this->common_model->insert('messages',$data);

				$data_message_receiver = array(
						'message_id'=>$message_id,
						'receiver_id'=>$user_data['id'],
						'created_date'=>date('Y-m-d H:i:s',time()),
						'modified_date'=>'0000-00-00 00:00:00',	
						'is_delete'=>'0',
						'is_testdata'=>'yes'
					);

				$this->common_model->insert('message_receiver',$data_message_receiver);

				die();

				$this->email->from('email@email.com', 'Name');
				$this->email->to('someone@example.com');
				
				$this->email->subject('subject');
				$this->email->message('message');
				
				$this->email->send();
				
				echo $this->email->print_debugger();

				p($data);

			}else{
				$this->session->set_flashdata('error', 'Username you entered doen not exists.');
				redirect('admin/user/send_message/'.$id);
			}
			p($user_data);

		}

	}

	// ---------------------------- User Module END --------------------------------------------



}

/* End of file User.php */
/* Location: ./application/controllers/Admin/User.php */