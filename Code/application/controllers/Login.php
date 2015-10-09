<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/* --kap 21-09-2015-------------------- */

class Login extends CI_Controller {

    public function __construct() {
        parent::__construct();
        $this->load->model(array('common_model'));
    }

    /* --For Login */

    public function index() {
        $remember_me = get_cookie('Remember_me');
        /* 	If Remember_key Cookie exists in browser then it wil fetch data using it's value and 
          set sessin data and force login student */

        if (isset($remember_me)) {
            $rem_data = select(TBL_USERS,null, array('where'=>array('id'=>$this->encrypt->decode($remember_me))));
            // $array = array(
            //     'id' => $rem_data['id'],
            //     'loggedin' => TRUE
            // );
            // $this->session->set_userdata($array);
            $this->set_session($remember_me);
        }

        $loggedin = is_loggedin();
        $loggedin_admin = is_loggedin_admin();

        if ($loggedin == TRUE &&  $loggedin_admin == FALSE) {

        	$group_id   =   $this->session->userdata('user')['group_id']; 
            $count_member = select(TBL_TUTORIAL_GROUP_MEMBER,null,array('where'=>array('group_id'=>$group_id,'joining_status'=>'1')),array('count'=>TRUE));
            
            if($count_member == 5)
                redirect('student/home');
            else
                redirect('login/welcome');
        }
        elseif($loggedin_admin == TRUE){
            redirect('admin/user');
        }

        $this->form_validation->set_rules('username', 'User Name', 'trim|required');
        $this->form_validation->set_rules('password', 'Password', 'trim|required');

        if ($this->form_validation->run() == FALSE) {
            $this->load->view('login/login');
        } 
        else{
            $username = $this->input->post('username');
            $password = $this->input->post('password');

            if (filter_var($username, FILTER_VALIDATE_EMAIL)) {
                $fetch_data = select(TBL_USERS,null, array('where'=>array('email_id' => $username)),1);
            } 
            else{
                $fetch_data = select(TBL_USERS,null, array('where'=>array('username' => $username)),1);
            }
         
            if (!empty($fetch_data)) {
                $db_pass = $this->encrypt->decode($fetch_data['password']);
                if ($db_pass === $password && $fetch_data['role_id'] != 1 && $fetch_data['is_delete'] == 0 && $fetch_data['user_status'] == 'active') {
                    
                    /* If remember Me Checkbox is clicked */
                    /* Set Cookie IF Start */
                    if (isset($_POST['remember'])) {
                        $cookie = array(
							'name' => 'Remember_me',
                            'value' => $this->encrypt->encode($fetch_data['id']),
                            'expire' => '86500'
                        );
                        $this->input->set_cookie($cookie);
                    } /* Set Cookie IF END */

                    $this->set_session($fetch_data['id']);
                    $role = $fetch_data['role_id'];

                    switch ($role) {
                        case '2':
                            $group_id   =   $this->session->userdata('user')['group_id']; 
                            if($this->session->userdata('user')['group_id'] != ''){
                                $count_member = select(TBL_TUTORIAL_GROUP_MEMBER,null,array('where'=>array('group_id'=>$group_id)),array('count'=>TRUE));
                                
                                if($count_member == 5){
                                    redirect('student/home');
                                }
                                else
                                    redirect('login/welcome');
                            }
                            break;
                        default:
                            break;
                    }
                } 
                else{
                    if($fetch_data['user_status'] == 'blocked')
                        $this->session->set_flashdata('error', 'You are blocked by admin.');
                    else
                        $this->session->set_flashdata('error', 'Invalid Username or Password.');
                    redirect('login');
                }
            } else {

                $auto_generated_data = select(TBL_AUTO_GENERATED_CREDENTIAL,null, array('where' => array('username' => $username,'status'=>1)),1);

                if (!empty($auto_generated_data)) {
                    $user_pass = $auto_generated_data['password']; /* Data from database */
                    $user_pass = $this->encrypt->decode($auto_generated_data['password']); 
                    // $user_status = $auto_generated_data['status'];
                    if ($user_pass == $password) {
                        if ($user_status == 0) {
                            $this->session->set_userdata('credential_user',$username);
                            redirect('/student/user_account');
                        } else {
                            redirect('student/home');
                        }
                    } else {
                        $this->session->set_flashdata('error', 'Invalid Username or Password.');
                        redirect('login');
                    }
                } else {
                    $this->session->set_flashdata('error', 'Invalid Username or Password.');
                    redirect('login');
                }
            }
        }
    }

    public function set_session($userid){

        $users = select(TBL_USERS.' u',
                'u.*,s.district_id,s.school_name, s.address as school_address, ct.city_name as city_name, cut.country_name as country_name, st.state_name as state_name,up.profile_link as profile_pic,tm.group_id,co.course_name,si.academic_year,si.course_id,si.classroom_id,si.school_id,(select count(*) cnt from tutorial_group_member where group_id = gu.id) as membercount,cl.class_name,s.district_id,d.district_name',   
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
                        )
                    )
                );
        $session_data = array(
            'loggedin' => TRUE,
            'user'=>$users[0]
        );
        $this->session->set_userdata($session_data);
        return;
    }

    //----------sigun out
    public function logout() {
        $this->session->sess_destroy();
        delete_cookie('Remember_me');
        redirect('login');
    }

    //--------request for forgot password
    public function forgot_password()
    {
        $this->form_validation->set_rules('emailid', 'Email', 'trim|required|valid_email|callback_check_email');
        if($this->form_validation->run() == FALSE){
            $this->load->view('login/forgot_password');
        }
        else
        {
            $this->session->set_flashdata('error', 'Please click on verfication link in your email');
            redirect('login');
        }
    }

    //--check email is valid or not
    public function check_email()
    {
        $emailid    =   $this->input->post('emailid',TRUE);
        $data       =   array('where'   =>  array('email_id' => $emailid));
        $get_data   =   select(TBL_USERS,null,$data,1);
        if(sizeof($get_data)>0){
            $options = array(
                            'join' =>
                                array(
                                    array(
                                        'table'      =>  'user_forgot_password f',
                                        'condition'=>  'f.user_id = u.id'
                                    )
                                ),
                            'limit' => 1,
                            'order_by' => 'f.id DESC',
                            'single'=>1
                        );
            $where = array('where'  =>  array('u.email_id' => $emailid));
            $chkdata = select(TBL_USERS.' u','f.token,f.complete_date',$where,$options);
            if(empty($chkdata['complete_date']) && $chkdata['token'] != ''){
                $this->session->set_flashdata('error', 'Request alredy sended please check it');
                redirect('login');  
            }    

            $string =   'ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstu1234567890'.time();
            $token  =   str_shuffle($string);
            $check_token    =  select('user_forgot_password',null,array('where'=>array('token' =>  $token)));
            for ($i=0; $i < 1; $i++) { 
                if(sizeof($check_token)>0){
                    $i--;
                    continue;
                }  
                else{
                    $token_data = array(
                        'user_id' => $get_data['id'],
                        'token'=>$token,
                        'status'=>1
                    );
                    insert('user_forgot_password',$token_data);
                }
            }
            $configs = mail_config();
            $this->load->library('email', $configs);
            $this->email->initialize($configs);
            $this->email->from('kap.narola@narolainfotech.com', 'Kamlesh Pokiya');
            $this->email->to($emailid);
            $encoded_mail = urlencode($token);
            $msg = '';
            $msg .='<html>';
            $msg .='<head><title></title></head>';
            $msg .= '<a href="http://ism/login/change?id='.$encoded_mail.'">Change Password</a>';
            $msg .='</html>';
            $this->email->subject('Reset Password');
            $this->email->message($msg);
            $this->email->send();
            $this->email->print_debugger();
            
        }
        else{
            $this->form_validation->set_message('check_email', 'Invalid email address');
            return FALSE;
        }
    }

    //---encoded email id get and reset password
    public function change()
    {
        $token          =   $this->input->get_post('id',TRUE);
        $token_result   =   select(TBL_USER_FORGOT_PASSWORD,null,array('where'=>array('token'=>$token)),1);
        if(sizeof($token_result)>0){
            $complete_date = $token_result['complete_date'];
            if(!empty($complete_date))
            {
                $this->session->set_flashdata('error', 'Your password already changed please login');
                redirect('login');
            }   
            $inserted_date = date($token_result['created_date'],strtotime("+30 minutes"));
            $currentDate = strtotime($inserted_date);
            $futureDate = $currentDate+(60*30);
            $formatDate = date("Y-m-d H:i:s", $futureDate);
            if(strtotime(date('Y-m-d H:i:s')) > strtotime($formatDate))
            {   
                $update_array = array('complete_date'=>date('Y-m-d',time()));
                update(TBL_USER_FORGOT_PASSWORD,array('id'=>$token_result['id']),$update_array);
                $this->session->set_flashdata('error', 'Your request is expired please try again');
                redirect('login/forgot_password');
            }
            else{
                $data['token'] = $token;
                $this->load->view('login/reset_forgot_password',$data);
            }
        }
    } 

    //---reset password 
    public function reset_password(){
        $token = $this->input->post('token',TRUE);
        if(empty($token))
            redirect('login');
        $this->form_validation->set_rules('new_password', 'New Password', 'trim|exact_length[8]|required');
        $this->form_validation->set_rules('con_password', 'Confirm Password', 'trim|required|matches[new_password]');
        if($this->form_validation->run() == FALSE){
            $data['token'] = $token;
            $this->load->view('login/reset_forgot_password');
        }
        else{
            $get_record =   select(TBL_USER_FORGOT_PASSWORD,null,array('where'=>array('token'=>$token)),1);
            if(sizeof($get_record)>0){
                $user_id = $get_record['user_id'];
                $password_data = array('password' => $this->encrypt->encode($this->input->post('new_password',TRUE)));
                update(TBL_USERS,$user_id,$password_data);
                update(TBL_USER_FORGOT_PASSWORD,array('token'=>$token),array('complete_date'=>date('Y-m-d H:i:s')));
                $this->session->set_flashdata('error', 'Your password successfully changed');
                redirect('login');
            }else{
                $this->session->set_flashdata('error', 'Invalid request try again');
                redirect('login/forgot_password');
            }
        }
    }  

    /*----Load welcome page---*/
    public function welcome()
    {
        $this->load->view('student/welcome');
    }
}