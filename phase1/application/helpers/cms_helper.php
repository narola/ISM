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
        echo '<div class="alert alert-success alert_notification alert-dismissible" role="alert" style="z-index: 999999;"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' . $CI->session->flashdata('success') . '</div>';
    }else if ($CI->session->flashdata('error') != "") {
        echo '<div class="alert alert-danger error_notification alert-dismissible " style="z-index: 999999;"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' . $CI->session->flashdata('error') . '</div>';
    }
}


/**
* Return difference between two times in seconds.
* @time1 = Basically End Time
* @time2 = Basically Current Time
* @Author = Sandip Gopani (SAG)
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
  function active_hours() {
       $CI =& get_instance();
        $starttime = $endtime = null;
        $output = array(
            'time_to_start' => 0,
            'total_active_time' => 0,
            'total_deactive_time' => 0,
            'time_to_left' => 0,
            'system_time' => 0,
            'exam_time_to_start' => 0,
            'exam_total_active_time' => 0,
            'exam_total_deactive_time' => 0,
            'exam_time_to_left' => 0
        );
        $currenttime = getdate(); // Get an array of current time

        if ($currenttime['hours'] < 10) {
            $currenttime['hours'] = '0' . $currenttime['hours'];
        }
        if ($currenttime['minutes'] < 10) {
            $currenttime['minutes'] = '0' . $currenttime['minutes'];
        }

        if ($currenttime['seconds'] < 10) {
            $currenttime['seconds'] = '0' . $currenttime['seconds'];
        }

        // Store current hours and minutes
        $output['system_time'] = $c_full_time = $currenttime['hours'] . ':' . $currenttime['minutes'] . ':' . $currenttime['seconds'];
        $active = $CI->common_model->sql_select(
      'admin_config ac',
        'ac.config_value, ac.config_key',
      array("where_in" => array('`ac`.`config_key` ' => array('activeHoursStartTime','activeHoursEndTime','questionHrExamStartTime','questionHrExamEndTime')))
      );
        
        if (count($active) == 4) {
            foreach($active as $key => $rows) {
                $time_part = explode(':', $rows['config_value']);
                if (!isset($time_part[2])) {
                    $time_part[2] = '00';
                } else if ($time_part[2] < 10 && strlen($time_part[2]) == 1) {
                    $time_part[2] = '0' . $time_part[2];
                }

                if ($time_part[0] < 10 && strlen($time_part[0]) == 1) {
                    $time_part[0] = '0' . $time_part[0];
                }

                if ($time_part[1] < 10 && strlen($time_part[1]) == 1) {
                    $time_part[1] = '0' . $time_part[1];
                }

                if ($rows['config_key'] == 'activeHoursStartTime') {
                    // Asign time and remove seconds from value incase added by admin ( e.g  11:30:54 will become 11:30 ). Same with else part
                    $starttime = implode(':', $time_part);
                } else if ($rows['config_key'] == 'activeHoursEndTime') {
                    $endtime = implode(':', $time_part);
                }

                if ($rows['config_key'] == 'questionHrExamStartTime') {
                    $examStart = implode(':', $time_part);
                } else if ($rows['config_key'] == 'questionHrExamEndTime') {
                    $examEnd = implode(':', $time_part);
                }
            }
            $cur = DateTime::createFromFormat('H:i:s', $c_full_time);
            if ($starttime !== null && $endtime !== null) {
                // Convert to date time
                $start = DateTime::createFromFormat('H:i:s', $starttime);
                $end = DateTime::createFromFormat('H:i:s', $endtime);


                if ($cur < $start) {
                    $output['time_to_start'] = dateDiff($c_full_time, $starttime);
                } else if ($cur > $end) {
                    $output['time_to_start'] = dateDiff('00:00:00', $starttime) + dateDiff($c_full_time, '24:00:00');
                }

                $output['total_active_time'] = dateDiff($starttime, $endtime);
                $output['total_deactive_time'] = dateDiff('00:00:00', $starttime) + dateDiff($endtime, '24:00:00');


                // Check current time is between $starttime and $endtime
                if ($cur > $start && $cur < $end) {
                    $output['time_to_left'] = dateDiff($endtime, $c_full_time);
                }
                // }
            }

            if ($examStart != null && $examEnd != null) {

                $Estart = DateTime::createFromFormat('H:i:s', $examStart);
                $Eend = DateTime::createFromFormat('H:i:s', $examEnd);

                if ($cur < $Estart) {
                    $output['exam_st'] = 'pending';
                    $output['exam_time_to_start'] = dateDiff($c_full_time, $examStart);
                } else if ($cur > $Eend) {
                    $output['exam_st'] = 'finished';
                    $output['exam_time_to_start'] = dateDiff('00:00:00', $examStart) + dateDiff($c_full_time, '24:00:00');
                }

                $output['exam_total_active_time'] = dateDiff($examStart, $examEnd);
                $output['exam_total_deactive_time'] = dateDiff('00:00:00', $examStart) + dateDiff($examEnd, '24:00:00');

                // Check current time is between $starttime and $endtime
                if ($cur > $Estart && $cur < $Eend) {
                    $output['exam_st'] = 'started';
                    $output['exam_time_to_left'] = dateDiff($examEnd, $c_full_time);
                }
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

function count_studymate_request($userid){
  $CI =& get_instance();
  return $CI->common_model->count_studymate_request($userid);
}

function notification_list($userid){
  $CI =& get_instance();
  return $CI->common_model->get_notification_list($userid); 
}

function count_notification_list($userid){
  $CI =& get_instance();
  return $CI->common_model->count_notification_list($userid); 
}

function studymates_info(){
  $CI =& get_instance();
  $user_id = $CI->session->userdata('user')['id'];
 return $CI->common_model->studymates_info($user_id);
}

function active_chat(){
  $CI =& get_instance();
   $user_id = $CI->session->userdata('user')['id'];
  return $CI->common_model->active_chat($user_id);
}

/**
* Return time based on creted date and current date.
* @param date $t
* @author Sandip Gopani (SAG)
*/
function  get_time_format($t){
  $CI =& get_instance();
$timeFirst  = strtotime($t);
$time = select('users','NOW() as ctime',null,array('limit' => 1,'single' => 1));
$timeSecond = strtotime($time['ctime']);
$output = null;
$diff = $timeSecond - $timeFirst;
  if($diff < 60){
    $output = 'Just Now';
  }else if($diff < 3600){
    $output = floor($diff/60). ' min ago';
  }else if ($diff < 86400){
      $output = floor($diff/3600);
    if($output < 2 ){
      $output .= ' hour ago';
    }else{
      $output .= ' hours ago';
    }
  }else if($diff < 86400*2){
    $output = 'yesterday'; 
  }else{
    $output = date_format( date_create($t), 'M d, Y g:i a');
  }
return $output; 
}

function set_session($userid){
  $CI =& get_instance();
         $users = select(TBL_USERS.' u',
                'u.*,s.district_id,s.school_name,gu.group_status, s.address as school_address, ct.city_name as city_name, cut.country_name as country_name, st.state_name as state_name,up.profile_link as profile_pic,tm.group_id,co.course_name,course_nickname,si.academic_year,si.course_id,si.classroom_id,si.school_id,(select count(*) cnt from tutorial_group_member where group_id = gu.id) as membercount,cl.class_name,s.district_id,d.district_name',   
                array('where'   =>  array('u.id' => $userid)),
                array('join'    =>    
                   array(
                        array(
                            'table' => TBL_USER_PROFILE_PICTURE.' up',
                            'condition' => 'up.user_id = u.id'
                            ),
                        array(
                            'table' => TBL_TUTORIAL_GROUP_MEMBER.' tm',
                            'condition' => 'tm.user_id = u.id'
                            ),
                        
                        array(
                            'table' => TBL_TUTORIAL_GROUPS.' gu',
                            'condition' => 'gu.id = tm.group_id'
                            ),
                        array(
                            'table' => TBL_STUDENT_ACADEMIC_INFO.' si',
                            'condition' => 'u.id = si.user_id'
                            ),
                        array(
                            'table' => TBL_SCHOOLS.' s',
                            'condition' => 's.id = si.school_id'
                            ),
                        array(
                            'table' => TBL_CITIES.' ct',
                            'condition' => 'ct.id = u.city_id'
                            ), 
                        array(
                            'table' => TBL_COUNTRIES.' cut',
                            'condition' => 'cut.id = u.country_id'
                            ), 
                        array(
                            'table' => TBL_STATES.' st',
                            'condition' => 'st.id = u.state_id'
                            ),
                        array(
                            'table' => TBL_COURSES.' co',
                            'condition' => 'si.course_id = co.id'
                            ),
                        array(
                            'table' => TBL_CLASSROOMS.' cl',
                            'condition' => 'cl.id = si.classroom_id'
                            ),
                        array(
                          'table' => TBL_DISTRICTS.' d',
                          'condition' => 'd.id = s.district_id'
                          )
                        ),
    'limit' => 1,
    'single' => true
                    )
                );
        $session_data = array(
            'loggedin' => TRUE,
            'user'=>$users
        );
        $CI->session->set_userdata($session_data);
        return;
    }


/*  Get SUGGESTED + RECOMMENDED studymates
*   @Auther - KAMLESH POKIYA (KAP).

    Algorithm of find suggested studymate
    1) get my group id and find all members connected to me in tutorial group
    2) get the school ids and classroom_id of the member got in step 1
    3) get the student studying in classroom_id + school_id got in step 2
    4) all student got in step 3 can be my recommendations.
*/
function get_recommended($user_id,$user_group_id){
    
    //--get my studymate
    $my_studymates = studymates($user_id,false);
    if(!sizeof($my_studymates) > 0)
            $my_studymates = array('');

    //--get studymate for already requested me.    
    $request_of = select(TBL_STUDYMATES_REQUEST,null,array('where' => array('request_to_mate_id' => $user_id,'is_delete'=>0)));
    $already_request = array();
    foreach ($request_of as $key => $value) {
        $already_request[] = $value['request_from_mate_id'];
    }

    //--merge studymate + requested studymate
    if(is_array($already_request))
        $my_studymates = array_merge($my_studymates,$already_request);

    $where = array('where' => array('m.group_id'=>$user_group_id,'in1.user_id !=' => $user_id),'where_not_in'=>array('in1.user_id' => $my_studymates));
    $options = array('join' => array(
                array(
                    'table' => TBL_STUDENT_ACADEMIC_INFO.' in',
                    'condition' => 'in.user_id = m.user_id',
                    'join'=>'join'
                ),
                array(
                    'table' => TBL_STUDENT_ACADEMIC_INFO.' in1',
                    'condition' => 'in.classroom_id = in1.classroom_id and in.course_id = in1.course_id and in.academic_year = in1.academic_year and in.school_id = in1.school_id',
                    'join'=>'join'
                ),
                array(
                    'table' => TBL_USERS.' u',
                    'condition' => 'in1.user_id = u.id'
                ),
                array(
                    'table' => TBL_SCHOOLS.' s',
                    'condition' => 's.id = in.school_id'
                ),
                array(
                    'table' => TBL_COURSES.' c',
                    'condition' => 'c.id = in1.course_id'
                ),
                array(
                    'table' => TBL_USER_PROFILE_PICTURE.' p',
                    'condition' => 'u.id = p.user_id'
                ),
                array(
                    'table' => TBL_STUDYMATES_REQUEST.' sr',
                    'condition' => 'sr.request_from_mate_id='.$user_id.' and sr.request_to_mate_id = in1.user_id and sr.is_delete = 0'
                ),
                
            ),
    'group_by' => 'in1.user_id'
        );
    return select(TBL_TUTORIAL_GROUP_MEMBER.' m','in1.user_id,u.full_name,s.school_name,c.course_name,p.profile_link,sr.id as srid,sr.is_delete',$where,$options);
}

/*
*   @auther KAMLESH POKIYA (KAP)
*   Get high score
*   display high sscore with user detail in ISM_mock test.
*/
    
function get_highscore($classroom_id = NULL){
   $cls = "AND `e`.`classroom_id` = 2";
    if($classroom_id == NULL){
      $cls = "";
    }else{
      $cls = "AND `e`.`classroom_id` = ".$classroom_id;
    }
    
    $sel = "`u`.`id`,`u`.`full_name`,`sh`.`school_name`,`p`.`profile_link` ,`ses`.`exam_id`,`e`.`exam_name`,`e`.`subject_id`,`s`.`subject_name`,SUM((`ses`.`correct_answers` * (SELECT `config_value` FROM `admin_config` WHERE `config_key` = 'correctAnswerScore' LIMIT 1))) AS `total_marks` ";
    $options = array( 'join' => 
                        array(
                            array(
                                'table' => TBL_EXAMS.' e',
                                'condition' => "`e`.`id` = `ses`.`exam_id` ".$cls." AND `e`.`exam_category` = 'ISM_Mock'",
                                'join' => 'JOIN'
                            ),
                            array(
                                'table' => TBL_SUBJECTS.' s',
                                'condition' => '`s`.`id` = `e`.`subject_id`',
                                'join' => 'JOIN'
                            ),
                            array(
                                'table' => TBL_USERS.' u',
                                'condition' => '`u`.`id` = `ses`.`user_id`',
                                'join' => 'JOIN'
                            ),
                            array(
                                'table' => TBL_STUDENT_ACADEMIC_INFO.' in',
                                'condition' => 'in.user_id = u.id',
                            ),
                            array(
                                'table' => TBL_SCHOOLS.' sh',
                                'condition' => 'sh.id = in.school_id'
                            ),
                            array(
                                'table' => TBL_USER_PROFILE_PICTURE.' p',
                                'condition' => 'u.id = p.user_id'
                            )

                        ),
                        'group_by' => '`u`.`id`,`s`.`subject_name`',
                        'order_by' => '`s`.`subject_name` DESC,`total_marks` DESC'
                );
$high = select(TBL_STUDENT_EXAM_SCORE.' ses',$sel,null,$options);
    $new = array();
    $sub_id = 0;
    foreach ($high as $k => $v) {
      if(!isset($new[$v['subject_name']])){
        $new[$v['subject_name']] = array();
      }
      if(count($new[$v['subject_name']]) < 1){
        $new[$v['subject_name']][] = $v;
      }
    }

   return  $new;
}

function group_high_score(){


$options = array(
  'join' => array(
      array(
          'table' => TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' tga',
          'condition' => "`tga`.`group_id` = `tg`.`id`",
          'join' => 'JOIN'
      ),
       array(
          'table' => TBL_CLASSROOMS.' c',
          'condition' => "`c`.`id` = `tg`.`classroom_id`",
          'join' => 'JOIN'
      )
    ),
  'group_by' => '`tga`.`group_id`',
  'order_by' => '`total_score` DESC',
  'limit' => 5
  );

return select(TBL_TUTORIAL_GROUPS.' tg','`tg`.`group_profile_pic` ,`c`.`class_name`,`tg`.`group_name`,SUM(`tga`.`group_score`) AS `total_score`',null,$options);

}
    
/*  Filter the data to store in database
*   @Author - SANDIP GOPANI [SAG].
*/
function replace_invalid_chars($data = null) {
    if (is_array($data) && $data != null) {
        foreach ($data as $key => $value) {
            if (is_array($value)) {
                $data[$key] = replace_invalid_chars($value);
            } else {
                $data[$key] = htmlentities($value, ENT_QUOTES);
            }
        }
    }else{
        $data = htmlentities($data, ENT_QUOTES);
    }
    return $data;
}

