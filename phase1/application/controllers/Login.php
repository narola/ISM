<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/*
*   KAMLESH POKIYA (KAP)
*/

class Login extends CI_Controller {

    public function __construct() {
        parent::__construct();
        $this->load->model(array('common_model'));
    }

    public function index() {

        /*
        *   Send request for credentials
        */

        $request_change = $this->input->post('send_request');
        if($request_change == 'change'){
            $this->request_for_credentials();
        }


        $remember_me = get_cookie('Remember_me');
        
        /* 	
        *   If Remember_key Cookie exists in browser then it wil fetch data using it's value and 
          set sessin data and force login student 
        */

        if (isset($remember_me)) {
            $rem_data = select(TBL_USERS,null, array('where'=>array('id'=>$this->encrypt->decode($remember_me))));
            set_session($remember_me);
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
                    
                    /*
                    *   If remember Me Checkbox is clicked
                    *   Set Cookie 
                    */

                    if (isset($_POST['remember'])) {
                        $cookie = array(
							'name' => 'Remember_me',
                            'value' => $this->encrypt->encode($fetch_data['id']),
                            'expire' => '86500'
                        );
                        $this->input->set_cookie($cookie);
                    }

                    set_session($fetch_data['id']);
                    $role = $fetch_data['role_id'];

                    /*
                    *   Redirect after login role wise
                    */
                    switch ($role) {
                        case '2':
                            $group_id   =   $this->session->userdata('user')['group_id']; 
                            if($this->session->userdata('user')['group_id'] != ''){
                                $count_member = select(TBL_TUTORIAL_GROUP_MEMBER,null,array('where'=>array('group_id'=>$group_id,'joining_status'=>'1')),array('count'=>TRUE));
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

    /*
    *   Sign out
    */
    public function logout() {
        $this->session->sess_destroy();
        delete_cookie('Remember_me');
        redirect('login');
    }

    /*
    *   Forgot password - student
    */
    public function forgot_password()
    {
        $this->form_validation->set_rules('emailid', 'Email', 'trim|required|valid_email|callback_check_email');
        if($this->form_validation->run() == FALSE){
            $this->load->view('login/forgot_password');
        }
        else
        {
            $this->session->set_flashdata('success', 'Please click on verfication link in your email');
            redirect('login');
        }
    }

    /*
    *   check student email is valid or not,
    *   if email is valid then send varification link for reset password
    */
    public function check_email()
    {
        $emailid    =   $this->input->post('emailid',TRUE);
        $data       =   array('where'   =>  array('email_id' => $emailid,'is_delete' => 0,'role_id' => 2));
        $get_data   =   select(TBL_USERS,null,$data,1);
        if(sizeof($get_data)>0){
            
            //-- check already request send or not
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
            $chkdata = select(TBL_USERS.' u','f.token,f.complete_date,f.created_date',$where,$options);
            
            //-- if request already send
            if(empty($chkdata['complete_date']) && $chkdata['token'] != '' && date('Y-m-d',strtotime($chkdata['created_date'])) == date('Y-m-d')){
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
            $msg .='<body style="background-color:#f5f5f5; background: repeating-linear-gradient(90deg, #eee, #fff 8px); color:#333; font-family:Tahoma, Geneva, sans-serif;">
                <table align="center" style="width: 600px;">
                    <tr>
                        <td style="text-align:center; padding: 35px 0;"><img alt="ISM" height="70px" src="../images/logo.png"></td>
                    </tr>
                    <tr>
                        <td>
                            <table style="padding: 15px; width:100%;background-color: #fff;border: 1px solid rgba(0,0,0,0.1);">
                                <tr>
                                    <td style="text-align: center;border-bottom: 1px solid rgba(0,0,0,0.1);">
                                        <h2 style="color: #1bc4a3; margin:10px 0;">Reset Password</h2>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <p>Please click on blow link within 30 minute for reset your password.</p>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="width:160px;"></td>
                                                <td><p style="margin: 0 0 10px;">Username : <strong>'.$get_data['username'].'</strong></p></td>
                                                <td></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="border-bottom: 1px solid rgba(0,0,0,0.1);">
                                        <table width="100%">
                                            <tr>
                                                <td style="width:160px;"></td>
                                                <td><p style="margin: 0 0 20px;">Email : <strong>'.$get_data['email_id'].'</strong></p></td>
                                                <td></td>
                                            </tr>
                                        </table>                            
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div style="background-color:#1bc4a3; text-align:center; padding: 12px; margin:15px 0; border-radius: 5px;">
                                            <a href="'.base_url().'login/change?id='.$encoded_mail.'" style="color:#fff; text-decoration:none; font-weight:bold; text-transform:uppercase;">Reset Your Password</a>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                     <tr>
                        <td>
                            <span style="font-size:x-small;">©2015 ISM. All Rights Reserved.</span>   
                        </td>
                    </tr>
                </table>
            </body>';
            $msg .='</html>';
            
            $this->email->subject('ISM - Reset Password');
            $this->email->message($msg);
            $this->email->send();
            $this->email->print_debugger();
            
        }
        else{
            $this->form_validation->set_message('check_email', 'Invalid email address');
            return FALSE;
        }
    }

    /*
    *   Once link send then get encoded email id as token,
    *   token is expired within 30 minutes,
    *   within 30 minute admin can apply to reset password.
    */
    public function change()
    {
        $token          =   $this->input->get_post('id',TRUE);
        $token_result   =   select(TBL_USER_FORGOT_PASSWORD,null,array('where'=>array('token'=>$token)),1);
        if(sizeof($token_result)>0){

            //-- check password is already changed or not
            $complete_date = $token_result['complete_date'];
            if(!empty($complete_date))
            {
                $this->session->set_flashdata('error', 'Your password already changed please login');
                redirect('login');
            }   

            //-- check token is expired or not.
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

    /*
    *   after verifiction of email and token accept request for change password.
    */ 
    public function reset_password(){

        //-- check if token is empty or not if token is expired then redirect to student login
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

            //-- if token is valid then change admin password
            $get_record =   select(TBL_USER_FORGOT_PASSWORD,null,array('where'=>array('token'=>$token)),1);
            if(sizeof($get_record)>0){
                $user_id = $get_record['user_id'];
                $password_data = array('password' => $this->encrypt->encode($this->input->post('new_password',TRUE)));
                update(TBL_USERS,$user_id,$password_data);
                update(TBL_USER_FORGOT_PASSWORD,array('token'=>$token),array('complete_date'=>date('Y-m-d H:i:s')));
                $this->session->set_flashdata('error', 'Your password successfully changed');
                redirect('login');
            }else{

                //-- if token is not empty but invalid
                $this->session->set_flashdata('error', 'Invalid request try again');
                redirect('login/forgot_password');
            }
        }
    }  
    
    /*-----Do not have credentials----*/
    public function request_for_credentials(){
        $message = $this->input->post('message');
        $email_id = $this->input->post('request_email');
        $name = $this->input->post('request_name');
        $configs = mail_config();
        $this->load->library('email', $configs);
        $this->email->initialize($configs);
        $this->email->from($email_id, $name);
        $this->email->to('nav.narola@narolainfotech.com');
        // $encoded_mail = urlencode($token);
        $msg = '';
        $msg .='<html>';
        $msg .='<head><title></title></head>';
        $msg .='<body style="background-color:#f5f5f5; background: repeating-linear-gradient(90deg, #eee, #fff 8px); color:#333; font-family:Tahoma, Geneva, sans-serif;">
            <table align="center" style="width: 600px;">
                <tr>
                    <td style="text-align:center; padding: 35px 0;"><img alt="ISM" height="70px" src="../images/logo.png"></td>
                </tr>
                <tr>
                    <td>
                        <table style="padding: 15px; width:100%;background-color: #fff;border: 1px solid rgba(0,0,0,0.1);">
                    <tr>
                        <td style="text-align: center;border-bottom: 1px solid rgba(0,0,0,0.1);">
                            <h4 style="color: #1bc4a3; margin:10px 0;">Request for credentials</h4>
                        </td>
                    </tr>                   
                    <tr>
                        <td style="border-bottom: 1px solid rgba(0,0,0,0.1); padding: 20px 0;">
                            <table width="100%">
                                <tr>
                                    <td style="width:160px;">
                                        Hello,<br><br>
                                        <b style="font-size:x-small;">Email : '.$email_id.'</b><br>
                                        <pre>'.$message.'</pre>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>                    
                    <tr>
                        <td>
                            <span style="font-size:x-small;">©2015 ISM. All Rights Reserved.</span>   
                        </td>
                    </tr>
                </table>
                    </td>
                </tr>
            </table>
        </body>';
        $msg .='</html>';
        $this->email->subject('ISM - Request For Credentials');
        $this->email->message($msg);
        $this->email->send();
        $this->email->print_debugger();
        $this->session->set_flashdata('success', 'Your request submitted successfully.');
        redirect('login');
    }

    /*----Load welcome page---*/
    public function welcome()
    {
        $data = array();
        $data['username'] = $this->session->userdata('user')['full_name'];
        $this->load->view('student/welcome', $data);
    }
}