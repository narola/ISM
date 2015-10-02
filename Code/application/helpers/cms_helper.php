<?php 

/**
*	Print array/string.
*	@param - data  = data that you want to print
*	@param -is_die = if true. Excecution will stop after print. 
* @author = Virendra Patel - VPA
*	@modified = Sandip Gopani (SAG)
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
*	@author = Sandip Gopani (SAG)
*/
function file_name($name = null){
	if($name != null){
		$name = str_replace('.'.pathinfo($name, PATHINFO_EXTENSION), '', $name.'_'.time()).'.'.$ext;
	}
	return $name;
}



/**
   * Generate CSV from a query result object
   *
   * @param object  $query    Query result object
   * @param string  $delim    Delimiter (default: ,)
   * @param string  $newline  Newline character (default: \n)
   * @param string  $enclosure  Enclosure (default: ")
   * @return  string
   * @author Virendra Patel - Sparks ID-VPA  ( Reference - Codeigniter From dbutil Class system\database\DB_utility.php:)
   */

function csv_from_results($query, $delim = ',', $newline = "\n", $enclosure = '')
{
    $CI =& get_instance();

    if ( ! is_object($query) OR ! method_exists($query, 'list_fields')){
      show_error('You must submit a valid result object');
    }

    $out = '';
    
    // First generate the headings from the table column names
    $out .= $enclosure.str_replace($enclosure, $enclosure.$enclosure, "Number").$enclosure.$delim;

    foreach ($query->list_fields() as $name){
      $out .= $enclosure.str_replace($enclosure, $enclosure.$enclosure, $name).$enclosure.$delim;
    }

    $out = substr($out, 0, -strlen($delim)).$newline;

    // Next blast through the result array and build out the rows
    $cnt = 1;
    while ($row = $query->unbuffered_row('array'))
    {
      $out .= $enclosure.str_replace($enclosure, $enclosure.$enclosure, $cnt).$enclosure.$delim;
      $out .= $enclosure.str_replace($enclosure, $enclosure.$enclosure, $row['username']).$enclosure.$delim;
      $out .= $enclosure.str_replace($enclosure, $enclosure.$enclosure, $CI->encrypt->decode($row['password'])).$enclosure.$delim;
      $out = substr($out, 0, -strlen($delim)).$newline;
      $cnt++;
    }
    
    return $out;
}

/**
 * function will return alert box with alert alert-danger class and mostly used for indivisual error in form validation
 * @return String
 * @author Virendra Patel - Sparks ID-VPA
 **/

function myform_error($field){
  
  $error = form_error($field);
  $str = '';
  if(!empty($error)){
    $str .= '<div class="alert alert-danger">'.strip_tags($error,'').'</div>';
  }
  return $str;
}


/**
*	This function will display notification msg.
*	@author = Sandip Gopani (SAG)
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
* Return difference between two times in seconds.
* @time1 = Basically End Time
* @time2 = Basically Current Time
*/

function dateDiff($time1, $time2) {
    // If not numeric then convert texts to unix timestamps
    if (!is_int($time1)) {
      $time1 = strtotime($time1);
    }
    if (!is_int($time2)) {
      $time2 = strtotime($time2);
    }
 
    // If time1 is bigger than time2
    // Then swap time1 and time2
    if ($time1 > $time2) {
      $ttime = $time1;
      $time1 = $time2;
      $time2 = $ttime;
    }
 
    // Set up intervals and diffs arrays
    $intervals = array('year','month','day','hour','minute','second');
    $diffs = array();
 
    // Loop thru all intervals
    foreach ($intervals as $interval) {
      // Create temp time from time1 and interval
      $ttime = strtotime('+1 ' . $interval, $time1);
      // Set initial values
      $add = 1;
      $looped = 0;
      // Loop until temp time is smaller than time2
      while ($time2 >= $ttime) {
        // Create new temp time from time1 and interval
        $add++;
        $ttime = strtotime("+" . $add . " " . $interval, $time1);
        $looped++;
      }
 
      $time1 = strtotime("+" . $looped . " " . $interval, $time1);
      $diffs[$interval] = $looped;
    }
    
    $count = 0;
    $times = array();
    // Loop thru all diffs
    foreach ($diffs as $interval => $value) {
      // Break if we have needed precission
      if ($count >= 6) {
		break;
      }
      // Add value and interval 
      if ($value > 0) {
			// Add value and interval to times array
			$times[$interval] =  $value;
			$count++;
      }
    }
    $check = array('day' => 86400, 'hour' => 3600, 'minute' => 60, 'second' => 1);
 	$seconds = 0;
 	foreach($times as $key => $value ){
 		foreach ($check as $k => $v) {
 			if($k == $key){
 				$seconds += $value*$check[$key];
 			}
 		}
 		
 	}
    
    return $seconds;
  }



/**
*	This function will return true if called within active hours.
*	return  true/false or null 
*	@author = Sandip Gopani (SAG)
*/
function active_hours(){
	$CI =& get_instance();
		// Get active hours time from DB
		$active = $CI->common_model->sql_select(
			'admin_config ac',
		    'ac.config_value, ac.config_key',
			"ac.config_key = 'activeHoursStartTime' OR ac.config_key = 'activeHoursEndTime'"
			);
		
		$starttime = $endtime = null;
		$output = 0;
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
			   $output = dateDiff($endtime.':00', $currenttime.':'.getdate()['seconds']);
			}
		}
		return $output;
	}

/** 
* This function simply return output of sql_select function of common_model.  
* This is just to simplify function call.
* @author = Sandip Gopani (SAG)
*/
function select($table, $select = null, $where = null, $options = null){
	$CI =& get_instance();
	return $CI->common_model->sql_select($table, $select , $where , $options );
}	

/** 
* This function simply return output of update function of common_model.  
* This is just to simplify function call.
* @author = Sandip Gopani (SAG)
*/
function update($table, $id = null, $data){
	$CI =& get_instance();
	return $CI->common_model->update($table, $id, $data);
}

/** 
* This function simply return output of insert function of common_model.  
* This is just to simplify function call.
* @author = Sandip Gopani (SAG)
*/
function insert($table,$data){	
	$CI =& get_instance();
	return $CI->common_model->insert($table,$data);
}

/** 
* This function simply return output of delete function of common_model.  
* This is just to simplify function call.
* @author = Sandip Gopani (SAG)
*/
function delete($table,$id){
	$CI =& get_instance();
	return $CI->common_model->delete($table,$id);
}


/**
* This function simply print last executed query
* @bool = boolean execution stopped if true 
* @author = Sandip Gopani (SAG)
*/
function qry($bool = false){
	$CI =& get_instance();
	echo $CI->db->last_query();
	if($bool)
	die;
}

/**
* This function simply check user is loggedin or not 
* @author = VPA
*/
function is_loggedin(){
	$CI =& get_instance();
	return $CI->session->userdata('loggedin');
}

/**
* This function simply check Admin is loggedin or not 
* @author = VPA
*/
function is_loggedin_admin(){
  $CI =& get_instance();
  return $CI->session->userdata('loggedin_admin');
}


/**
     * Crop Uploaded image in $width & $height and move cropped images to destination
     * @param type $src
     * @param type $destination
     * @param type $width
     * @param type $height
     * @param type $type
     * @author Sandip Gopani (SAG)
     */

function crop($src, $width, $height) {

    $destination = $src;
    $type = strtolower(pathinfo($src, PATHINFO_EXTENSION));
    $allowed_type = array('png', 'jpeg', 'gif', 'jpg');
    $return = 0;
    if (in_array($type, $allowed_type)) {
        list($w, $h) = getimagesize($src);

        $sourceRatio = $w / $h;
        $targetRatio = $width / $height;

        if ($sourceRatio < $targetRatio) {
            $scale = $w / $width;
        } else {
            $scale = $h / $height;
        }
        $cropWidth = $width * $scale;
        $cropHeight = $height * $scale;

        $widthPadding = ($w - $cropWidth) / 2;
        $heightPadding = ($h - $cropHeight) / 2;

        if ($type == 'jpg' || $type == 'jpeg') {
            $img_r = imagecreatefromjpeg($src);
            $function = 'imagejpeg';
        } else if ($type == 'png') {
            $img_r = imagecreatefrompng($src);
            $function = 'imagepng';
        } else if ($type == 'gif') {
            $img_r = imagecreatefromgif($src);
            $function = 'imagejgif';
        }
        $dst_r = ImageCreateTrueColor($width, $height);
        imagecopyresampled($dst_r, $img_r, 0, 0, $widthPadding, $heightPadding, $width, $height, $cropWidth, $cropHeight);

        if ($function($dst_r, $destination)) {
            $return = 1;
        }
    }
    return $return;
}

/**
* This function simply print studymate list
* @userid = user id for want its studymates 
* @author = Kamlesh Pokiya (KAP)
*/
function studymates($userid,$append = true){
  $CI =& get_instance();
  return $CI->common_model->class_mate_list($userid,$append);
}

/**
*  Return an array of id of current online user.
*/
function online(){ 
      $all_online = rtrim(get_cookie('status'),"-");
      $all_online = ltrim($all_online,"-");
      return explode('-', $all_online);
}


