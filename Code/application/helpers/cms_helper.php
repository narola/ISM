<?php 

/**
*	Print array/string.
*	@data  = data that you want to print
*	@is_die = if true. Excecution will stop after print. 
* 	Author = VPA
*	Modified = Sandip Gopani (SAG)
*/
function p($data, $is_die = false){

	if(is_array($data)){
		echo "<pre>";
		print_r($data);
		echo "</pre>";
	}else{
		echo $data;
	}

	if($is_die)
	die;
	
}

/**
* 	Append current timesptamp to file name
*	@name = 
*	return  = timestamp appended unique file name.
*	Author = Sandip Gopani (SAG)
*/
function file_name($name = null){
	if($name != null){
		$name = str_replace('.'.pathinfo($name, PATHINFO_EXTENSION), '', $name.'_'.time()).'.'.$ext;
	}
	return $name;
}


/**
*	This function will display notification msg.
*	@Author = Sandip Gopani (SAG)
*/
function flashMessage($success = '', $error = '') {
	$CI =& get_instance();
    if ($CI->session->flashdata('success') != "") {
        echo '<div class="alert alert-success alert-dismissible col-sm-6 col-sm-offset-3" role="alert" style="position: absolute; top: 20%; left: 0; z-index: 999999;"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' . $CI->session->flashdata('success') . '</div>';
    }else if ($CI->session->flashdata('error') != "") {
        echo '<div class="alert alert-danger alert-dismissible col-sm-6 col-sm-offset-3" style="position: absolute; top: 20%; left: 0; z-index: 999999;"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' . $CI->session->flashdata('error') . '</div>';
    }
}

/**
*	This function will return true if called within active hours.
*	return  true/false or null 
*	@Author = Sandip Gopani (SAG)
*/
function is_active_hours(){
	$CI =& get_instance();
		// Get active hours time from DB
		$active = $CI->common_model->sql_select(
			'admin_config ac',
		    'ac.config_value, ac.config_key',
			"ac.config_key = 'activeHoursStartTime' OR ac.config_key = 'activeHoursEndTime'"
			);
		
		$starttime = $endtime = $output = false;
		$currenttime = getdate(); // Get an array of current time

		// Store current hours and minutes
		$currenttime = (string)$currenttime['hours'].':'.$currenttime['minutes'];

		foreach($active as $key => $value){
			if($value['config_key'] == 'activeHoursStartTime'){
				// Asign time and remove seconds from value incase added by admin ( e.g  11:30:54 will become 11:30 ). Same with else part
				$starttime = explode(':',$value['config_value'])[0].':'.explode(':',$value['config_value'])[1]; 
			}else{
				$endtime = explode(':',$value['config_value'])[0].':'.explode(':',$value['config_value'])[1]; 
			}
		}
		if($starttime !== null && $endtime !== null){

			// Convert to date time
			$cur = DateTime::createFromFormat('H:i', $currenttime);
			$start = DateTime::createFromFormat('H:i', $starttime);
			$end = DateTime::createFromFormat('H:i', $endtime);

			// Check current time is between $starttime and $endtime
			if ($cur > $start && $cur < $end){ 
			   $output = true;
			}
		}
		return $output;
	}


/** 
* This function simply return output of sql_select function of common_model.  
* This is just to simplify function call.
* @Author = Sandip Gopani (SAG)
*/
function select($table, $select = null, $where = null, $options = null){
	$CI =& get_instance();
	return $CI->common_model->sql_select($table, $select , $where , $options );
}	

/** 
* This function simply return output of update function of common_model.  
* This is just to simplify function call.
* @Author = Sandip Gopani (SAG)
*/
function update($table, $id = null, $data){
	$CI =& get_instance();
	return $CI->common_model->update($table, $id, $data);
}

/** 
* This function simply return output of insert function of common_model.  
* This is just to simplify function call.
* @Author = Sandip Gopani (SAG)
*/
function insert($table,$data){	
	$CI =& get_instance();
	return $CI->common_model->insert($table,$data);
}

/** 
* This function simply return output of delete function of common_model.  
* This is just to simplify function call.
* @Author = Sandip Gopani (SAG)
*/
function delete($table,$id){
	$CI =& get_instance();
	return $CI->common_model->delete($table,$id);
}


/**
* This function simply print last executed query
* @bool = boolean execution stopped if true 
* @Author = Sandip Gopani (SAG)
*/
function qry($bool = false){
	$CI =& get_instance();
	echo $CI->db->last_query();
	if($bool)
	die;
}

/**
* This function simply check user is loggedin or not 
* @Author = VPA
*/
function is_loggedin(){
	$CI =& get_instance();
	return $CI->session->userdata('loggedin');
}

