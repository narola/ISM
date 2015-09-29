<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Common extends CI_Controller {

	public function __construct()
	{
		parent::__construct();
		$this->load->model('common_model');	
	}
	
	public function ajax_get_states(){
		
		$country_id = $this->input->post('country_id');
		$all_states = $this->common_model->sql_select('states',FALSE,array('where'=>array('country_id'=>$country_id)));
		$new_str = '';
		
		$new_str .= '<option selected disabled >Select State</option>';
		if(!empty($all_states)){
			foreach($all_states as $state){
				$new_str.='<option value="'.$state['id'].'">'.$state['state_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	public function ajax_get_cities(){

		$state_id = $this->input->post('state_id');
		$all_cities = $this->common_model->sql_select('cities',FALSE,array('where'=>array('state_id'=>$state_id)));
		
		$new_str = '';

		$new_str .= '<option selected disabled >Select City</option>';
		if(!empty($all_cities)){
			foreach($all_cities as $city){
				$new_str.='<option value="'.$city['id'].'">'.$city['city_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	public function ajax_get_districts(){

		$city_id = $this->input->post('city_id');
		$all_districts = $this->common_model->sql_select('districts',FALSE,array('where'=>array('city_id'=>$city_id)));
		$new_str = '';

		$new_str .= '<option selected disabled > Select District </option>';
		if(!empty($all_districts)){
			foreach($all_districts as $district){
				$new_str.='<option value="'.$district['id'].'">'.$district['district_name'].'</option>';
			}	
		}
		echo $new_str;
	}

	public function template_notice(){
		
		$notice_id = $this->input->post('notice_id');

		$result = $this->common_model->sql_select(TBL_NOTICEBOARD,FALSE,array('where'=>array('id'=>$notice_id)),array('single'=>TRUE));

		$new_str = '';
		p($result);
		//echo $new_str;		
	}

	public function template_message(){

		$msg_id = $this->input->post('msg_id');
		$data = $this->common_model->sql_select('messages',FALSE,array('where'=>array('id'=>$msg_id)),array('single'=>TRUE)); 
		$new_str = '';
		$new_str .=$data['message_title'].'###'.$data['message_text'];
		echo $new_str;
	}

}

/* End of file common.php */
/* Location: ./application/controllers/common.php */