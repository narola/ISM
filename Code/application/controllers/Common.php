<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Common extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function ajax_get_states() {

        $country_id = $this->input->post('country_id');
        $all_states = select(TBL_STATES, FALSE, array('where' => array('country_id' => $country_id)), array('order_by' => 'state_name'));
        $new_str = '';

        $new_str .= '<option selected disabled >Select State</option>';
        if (!empty($all_states)) {
            foreach ($all_states as $state) {
                $new_str.='<option value="' . $state['id'] . '">' . $state['state_name'] . '</option>';
            }
        }
        echo $new_str;
    }

    public function ajax_get_cities() {

        $state_id = $this->input->post('state_id');
        $all_cities = select(TBL_CITIES, FALSE, array('where' => array('state_id' => $state_id)), array('order_by' => 'city_name'));
        $new_str = '';

        $new_str .= '<option selected disabled >Select City</option>';
        if (!empty($all_cities)) {
            foreach ($all_cities as $city) {
                $new_str.='<option value="' . $city['id'] . '">' . $city['city_name'] . '</option>';
            }
        }
        echo $new_str;
    }

    public function ajax_get_districts() {

        $city_id = $this->input->post('city_id');
        $all_districts = select(TBL_DISTRICTS, FALSE, array('where' => array('city_id' => $city_id)), array('order_by' => 'district_name'));
        $new_str = '';

        $new_str .= '<option selected disabled > Select District </option>';
        if (!empty($all_districts)) {
            foreach ($all_districts as $district) {
                $new_str.='<option value="' . $district['id'] . '">' . $district['district_name'] . '</option>';
            }
        }
        echo $new_str;
    }

    public function ajax_get_classrooms() {

        $course_id = $this->input->post('course_id');
        $all_classrooms = select(TBL_CLASSROOMS, FALSE, array('where' => array('course_id' => $course_id)), array('order_by' => 'class_name'));
        $new_str = '';

        $new_str .= '<option selected disabled > Select Classroom </option>';
        if (!empty($all_classrooms)) {
            foreach ($all_classrooms as $class) {
                $new_str.='<option value="' . $class['id'] . '">' . $class['class_name'] . '</option>';
            }
        }
        echo $new_str;
    }

    public function template_notice() {

        $notice_id = $this->input->post('notice_id');
        $data = select(TBL_NOTICEBOARD, FALSE, array('where' => array('id' => $notice_id)), array('single' => TRUE));
        $new_str = '';
        $new_str .=$data['notice'] . '###' . $data['notice_title'];
        echo $new_str;
    }

    public function template_message() {

        $msg_id = $this->input->post('msg_id');
        $data = select(TBL_MESSAGES, FALSE, array('where' => array('id' => $msg_id)), array('single' => TRUE));
        $new_str = '';
        $new_str .=$data['message_title'] . '###' . $data['message_text'];
        echo $new_str;
    }

    /* Check weather Message Template is unique or not ?  */
    public function check_template_unique() {
        $msg_title = $this->input->post('msg_title');
        $data = select(TBL_MESSAGES, FALSE, array('where' => array('message_title' => $msg_title, 'is_template' => '1', 'is_delete' => '0')));
        if (!empty($data)) {
            echo "1"; // Find Template in database Return 1
        } else {
            echo "0";  // Can't Find template in database and Return 0
        }
    }

    /* Check weather Notice Template is unique or not ?  */
    public function check_template_notice_unique() {

        $notice_title = $this->input->post('notice_title');

        $data = select(TBL_NOTICEBOARD, FALSE, array('where' => array('notice_title' => $notice_title, 'is_template' => '1', 'is_delete' => '0')));
        if (!empty($data)) {
            echo "1"; // Find Template in database Return 1
        } else {
            echo "0";  // Can't Find template in database and Return 0
        }
    }

    /* Fetch Classroom Based on Course ID */
    public function fetch_classroom(){

        $course_id = $this->input->post('course_id');
        $all_classrooms = select(TBL_CLASSROOMS, FALSE, array('where' => array('course_id' => $course_id)), array('order_by' => 'class_name'));
        $new_str = '';

        $new_str .= '<option selected disabled > Select Classroom </option>';
        if (!empty($all_classrooms)) {
            foreach ($all_classrooms as $classroom) {
                $new_str.='<option value="' . $classroom['id'] . '">' . $classroom['class_name'] . '</option>';
            }
        }
        echo $new_str;
    }

    /* Fetch Subject based on Class ID */
    public function fetch_subject(){

        $class_id = $this->input->post('class_id');
       
        $all_subjects = select(
                               TBL_SUBJECTS.' as subjects', 
                               "subjects.id,subjects.subject_name", 
                               array('where' => array('c_sub.classroom_id' => $class_id)), 
                               array(
                                    'join'=>array(
                                                array(
                                                    'table'=>TBL_CLASSROOM_SUBJECT.' c_sub',
                                                    'condition'=> 'subjects.id=c_sub.subject_id'
                                                    )
                                    )
                                )
                           );
        $new_str = '';

        $new_str .= '<option selected disabled > Select Subject </option>';
        if (!empty($all_subjects)) {
            foreach ($all_subjects as $subject) {
                $new_str.='<option value="' . $subject['id'] . '">' . $subject['subject_name'] . '</option>';
            }
        }
        echo $new_str;
    }

    /* Fetch Classroom Based on Course ID */
    public function fetch_users(){

        $role_id = $this->input->post('role_id');
        
        if($role_id != 0){
            $all_users = select(TBL_USERS, FALSE, array('where' => array('role_id' => $role_id)), array('order_by' => 'username'));
        }else{
            $all_users = select(TBL_USERS, FALSE, FALSE, array('order_by' => 'username'));    
        }
        
        $new_str = '';
        $new_str .= '<option selected disabled > Select User </option>';

        if (!empty($all_users)) {
            foreach ($all_users as $user) {
                $new_str.='<option value="' . $user['id'] . '">' . ucfirst($user['username']) . '</option>';
            }
        }

        echo $new_str;
    }

    /* Fetch All Schools based on Grade System */
    public function fetch_school_from_grade(){
        
        $school_grade = $this->input->post('school_grade');   
        if($school_grade == '1'){
            $schools = select(TBL_SCHOOLS,FALSE,array('where'=>array('is_delete'=>FALSE)),array('order_by'=>'school_name'));
        }else{
            $schools = select(TBL_SCHOOLS,FALSE,array('where'=>array('is_delete'=>FALSE,'school_grade'=>$school_grade)),array('order_by'=>'school_name'));
        }

        $new_str = '<option value="" selected disabled > Select School </option>';
        
        if(!empty($schools)){
            foreach ($schools as $school) {
                $new_str .= '<option '.$selected.' value="'.$school['id'].'">'.
                                $school["school_name"].
                            '</option>';
            }
        }else{
            $new_str .= '<option value=""> No School Found </option>';
        }

        echo $new_str;
    }

}

/* End of file common.php */
/* Location: ./application/controllers/common.php */