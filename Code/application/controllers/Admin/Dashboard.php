<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * class Dashboard extends ADMIN_Controller which is in application/libraries Folder
 * ADMIN_Controller is extend in config.php using __autoload megic method		 
 *
 * @author Virendra Patel - Spark ID- VPA
 **/

class Dashboard extends ADMIN_Controller {

	public $data = array();

	public function __construct()
	{
		parent::__construct();
		
		$this->load->helper(array('csv','file','download'));	
	}

	public function index()
	{
		$remember_me = get_cookie('Remember_me');  

		/* 	If Remember_key Cookie exists in browser then it wil fetch data using it's value and 
			set sessin data and force login student  */

		if(isset($remember_me)){

			$remember_me_decode = $this->encrypt->decode($remember_me);

			$rem_data = select(TBL_USERS,FALSE,array('where'=>array('id'=>$remember_me)),array('single'=>TRUE));	

			$array = array(
				'id'=>$rem_data['id'],
				'loggedin_admin'=>TRUE
			);
			
			$this->session->set_userdata( $array );
		}

		$loggedin = is_loggedin();
		$loggedin_admin = is_loggedin_admin();  /* is_logginin() in cms_helper.php It will Check Admin is loggen or not. */

		if($loggedin == FALSE && $loggedin_admin == TRUE){
			redirect('admin/user');
		}elseif($loggedin == TRUE){
			redirect('login');
		}	

		$this->form_validation->set_rules('username', 'Email / User Name', 'trim|required');
		$this->form_validation->set_rules('password', 'Password', 'trim|required');	

		if($this->form_validation->run() == FALSE){

			$this->load->view('admin/login');

		}else{

			$username = $this->input->post('username');
			$password = $this->input->post('password');

			if(filter_var($username, FILTER_VALIDATE_EMAIL)) {
				$fetch_data = select(TBL_USERS,FALSE,array('where'=>array('email_id'=>$username)),array('single'=>TRUE));

		    }else {
				$fetch_data = select(TBL_USERS,FALSE,array('where'=>array('username'=>$username)),array('single'=>TRUE));
		    }

			if(!empty($fetch_data)){

				$db_pass = $this->encrypt->decode($fetch_data['password']);

			    if($db_pass == $password && $fetch_data['is_delete']==0 && $fetch_data['role_id'] == 1 ){

					$role_data = select(TBL_ROLES,FALSE,array('where'=>array('id'=>$fetch_data['role_id']))
						,array('single'=>TRUE));	

			    	/* If remember Me Checkbox is clicked */
					/* Set Cookie IF Start */
					if(isset($_POST['remember'])){

						$cookie = array(
						    'name'   =>'Remember_me' ,
						    'value'  => $this->encrypt->encode($fetch_data['id']),
						    'expire' => '86500'
						);

						$this->input->set_cookie($cookie);
					}   /* Set Cookie IF END */

					$array = array(
						'id' => $fetch_data['id'],
						'role' => $role_data['role_name'],
						'username'=>$fetch_data['username'],
						'email_id'=>$fetch_data['email_id'],
						'loggedin_admin' =>TRUE
					);

					$this->session->set_userdata( $array ); // Set Session for Admin
					redirect('admin/user');  
				}else{
			    	$this->session->set_flashdata('error', 'Invalid Username or Password.');		
					redirect('admin');	
			    }

			}else{
				$this->session->set_flashdata('error', 'Invalid Username or Password.');		
				redirect('admin');	
			}	
		}	
	}

	public function dashboard(){
		echo "Admin Dashboard";
	}

	/**
	 * function auto_generated_credentials() will generate credentials for school.
	 *
	 * @author Virendra patel Sparks ID - VPA
	 **/

	public  function auto_generated_credentials(){
		
		$this->data['page_title'] = 'Auto Generated Credentials';

		$school_grade = $this->input->post('school_grade');

		if(empty($school_grade) || !$_POST){
			$this->data['schools'] = select(TBL_SCHOOLS,false,array('where'=>array('is_delete'=>0)),array('order_by'=>TBL_SCHOOLS.'.school_name'));
		}else{
			$this->data['schools'] = select(TBL_SCHOOLS,false,array('where'=>array('is_delete'=>0,'school_grade'=>$school_grade)),array('order_by'=>TBL_SCHOOLS.'.school_name'));
		}

		$this->data['roles'] = select(TBL_ROLES,false,array('where'=>array('is_delete'=>0)));
		$this->data['courses'] = select(TBL_COURSES,false,array('where'=>array('is_delete'=>0)));

		$course_id = $this->input->post('course_id');

		if(!$_POST && empty($course_id)){
			$this->data['classrooms'] = select(TBL_CLASSROOMS,false,array('where'=>array('is_delete'=>0)),array('order_by'=>TBL_CLASSROOMS.'.class_name'));
		}else{
			$this->data['classrooms'] = select(TBL_CLASSROOMS,false,array('where'=>array('is_delete'=>0,'course_id'=>$course_id)),array('order_by'=>TBL_CLASSROOMS.'.class_name'));
		}

		$this->data['cur_year'] = date('Y');
		$this->data['next_year'] = date('Y')+1;
		
		//set Codeigniter Form Validation using form_validation Class
		$this->form_validation->set_rules('school_id', 'School Name', 'trim|required|integer');
		$this->form_validation->set_rules('role_id', 'Role', 'trim|required|integer');
		$this->form_validation->set_rules('course_id', 'Course', 'trim|required|integer');
		$this->form_validation->set_rules('no_of_credentials', 'No of credentials', 'trim|required|integer|greater_than[0]');
		$this->form_validation->set_rules('classroom_id', 'Classroom', 'trim|required|integer');

		// if from_validation set rules are false or if data not posted then it will set to FALSE
		if($this->form_validation->run() == FALSE){
			$this->template->load('admin/default','admin/generated_credentials',$this->data);
		}else{

			//ELSE if data posted by user and all set validation are TRUE
			$school_id = $this->input->post('school_id');
			$role_id = $this->input->post('role_id');
			$course_id = $this->input->post('course_id');
			$classroom_id = $this->input->post('classroom_id');
			$year_id = $this->input->post('year_id');
			$next_year = $year_id+1;
			$year = "$year_id-$next_year";
			$no_of_credentials	=	$this->input->post('no_of_credentials',TRUE);

			// Fetch role name,course name,classroom name,school name from diffetent table using cms_helper.php and common_model.php
			$role_name = select(TBL_ROLES,'role_name',array('where'=>array('id'=>$role_id)),array('single'=>TRUE));
			$course_name = select(TBL_COURSES,'course_name',array('where'=>array('id'=>$course_id)),array('single'=>TRUE));
			$class_name = select(TBL_CLASSROOMS,'class_name',array('where'=>array('id'=>$classroom_id)),array('single'=>TRUE));
			$school_name = select(TBL_SCHOOLS,'school_name',array('where'=>array('id'=>$school_id)),array('single'=>TRUE));

			/*Setting for PHPExcel Set Heading for spreadsheet*/

			//load our new PHPExcel library
			$this->load->library('excel');
			ob_clean();
			//activate worksheet number 1
			$this->excel->setActiveSheetIndex(0);
			//name the worksheet
			$this->excel->getActiveSheet()->setTitle('test worksheet');
			//set cell A1 content with some text
			$this->excel->getActiveSheet()->setCellValue('A1', 'Number');
			//set cell A1 content with some text
			$this->excel->getActiveSheet()->setCellValue('B1', 'Username');
			//set cell A1 content with some text
			$this->excel->getActiveSheet()->setCellValue('C1', 'Passowrd');

			$this->excel->getActiveSheet()->getStyle('A1')->getFont()->setBold(true);
			$this->excel->getActiveSheet()->getStyle('B1')->getFont()->setBold(true);
			$this->excel->getActiveSheet()->getStyle('C1')->getFont()->setBold(true);

			$this->excel->getActiveSheet()->getStyle("A1:C1")->getFont()->setSize(14);
			
			$this->excel->getActiveSheet()->getColumnDimension('A')->setAutoSize(true);
			$this->excel->getActiveSheet()->getColumnDimension('B')->setAutoSize(true);
			$this->excel->getActiveSheet()->getColumnDimension('C')->setAutoSize(true);

			$this->excel->getActiveSheet()->freezePane('A2'); //Freeze panel Above of A2 row

			$this->excel->getActiveSheet()->getStyle('A1:C1')->getAlignment()->setHorizontal(PHPExcel_Style_Alignment::HORIZONTAL_CENTER);

			$this->excel->getActiveSheet()->getRowDimension('1')->setRowHeight(20);
			$this->excel->getActiveSheet()->getColumnDimension('A')->setWidth(100);
			
			$cnt = 2;

			//No of Credentials loop will run if that username does not exist in users.username table.field
			for ($i=0; $i < $no_of_credentials; $i++) { 

				$usrename_str 	= 	'ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
				$username 		=	substr(str_shuffle($usrename_str),0,10);
				$password_str 	=	'abcdefghijklmnopqrstuvwxyz1234567890';
				$pass_generated = 	 substr(str_shuffle($password_str),0,8);
				$password 		=	$this->encrypt->encode($pass_generated);
				
				$data = array('username'=>$username);
				
				$find_credentials = select(TBL_AUTO_GENERATED_CREDENTIAL,FALSE,array('where'=>$data));

				$find_user = select(TBL_USERS,FALSE,array('where'=>$data));

				if(sizeof($find_credentials)>0 || sizeof($find_user) > 0){
					//if data found in users or auto_generated_credentials table then it will decrease $i value and continue 
					$i--;
					$cnt--;
					continue;
					
				}else{

					$data = array(
							'username'=>$username,
							'password'=>$password,
							'school_id'=>$school_id,
							'role_id'=>$role_id,
							'status'=>'1',
							'classroom_id'=> $classroom_id,
							'course_id'=> $course_id,
							'academic_year'=>$year
						);

					insert(TBL_AUTO_GENERATED_CREDENTIAL,$data); // insert data into database using common_model.php and cms_helper.php

					$this->excel->getActiveSheet()->setCellValue('A'.$cnt, $cnt-1);
					$this->excel->getActiveSheet()->setCellValue('B'.$cnt, $username);
					$this->excel->getActiveSheet()->setCellValue('C'.$cnt, $pass_generated);
					$this->excel->getActiveSheet()->getStyle('A'.$cnt.':C'.$cnt)->getAlignment()->setHorizontal(PHPExcel_Style_Alignment::HORIZONTAL_CENTER);
					$this->excel->getActiveSheet()->getRowDimension($cnt)->setRowHeight(17); //set Height After first Row
					$cnt++;

 				}
			} // End Of For Loop	

			$filename=$school_name['school_name'].'-'.$class_name['class_name'].'.xls'; //save our workbook as this file name
			
			header('Content-Type: application/vnd.ms-excel'); //mime type
            header('Content-Disposition: attachment;filename="' . $filename . '"'); //tell browser what's the file name
            header('Cache-Control: max-age=0'); //no cache
            $objWriter = PHPExcel_IOFactory::createWriter($this->excel, 'Excel5');
            $objWriter->save('php://output'); 
		    

		} // End else consdition
	}
	
	/**
	 * function logout will clear All Session Data and Delete Remember_me Coookie.
	 * @author Virendra patel Sparks ID- VPA
	 **/

	public function logout(){

		$this->session->sess_destroy();
		delete_cookie('Remember_me');
		redirect('admin');
	}

	/*
	*	@Auther KAMLESH POKIYA (KAP).
	*	Forgot password - administrator
	*/
	
	public function forgot_password()
    {
        $this->form_validation->set_rules('emailid', 'Email', 'trim|required|valid_email|callback_check_email');
        if($this->form_validation->run() == FALSE){
            $this->load->view('admin/forgot_password');
        }
        else
        {
            $this->session->set_flashdata('success', 'Please click on verfication link in your email');
            redirect('admin');
        }
    }

    /*
	*	@Auther KAMLESH POKIYA (KAP).
	*	check administrator email is valid or not,
	* 	if email is valid then send varification link for reset password
	*/
    public function check_email()
    {
        $emailid    =   $this->input->post('emailid',TRUE);
        $data       =   array('where'   =>  array('email_id' => $emailid,'is_delete'=>0,'role_id' => 1));
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
                redirect('admin');  
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
                                            <a href="http://ism/admin/change?id='.$encoded_mail.'" style="color:#fff; text-decoration:none; font-weight:bold; text-transform:uppercase;">Reset Your Password</a>
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
            $this->email->subject('ISM - Reset Password Admin');
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
	*	@Auther KAMLESH POKIYA (KAP).
	*	Once link send then get encoded email id as token,
	* 	token is expired within 30 minutes,
	*	within 30 minute admin can apply to reset password.
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
                redirect('admin');
            }

            //-- check token is expired or not.
            $inserted_date = date($token_result['created_date']);
            $currentDate = strtotime($inserted_date);
            $futureDate = $currentDate+(60*30);
            $formatDate = date("Y-m-d H:i:s", $futureDate);
            if(strtotime(date('Y-m-d H:i:s')) > strtotime($formatDate))
            {   
                $update_array = array('complete_date'=>date('Y-m-d H:i:s'));
                update(TBL_USER_FORGOT_PASSWORD,array('id'=>$token_result['id']),$update_array);
                $this->session->set_flashdata('error', 'Your request is expired please try again');
                redirect('admin/forgot_password');
            }
            else{
                $data['token'] = $token;
                $this->load->view('admin/reset_forgot_password',$data);
            }
        }
    } 

    /*
	*	@Auther KAMLESH POKIYA (KAP).
	*	after verifiction of email and token accept request for change password.
	*/
    public function reset_password(){

    	//-- check if token is empty or not if token is expired then redirect to admin login
        $token = $this->input->post('token',TRUE);
        if(empty($token))
            redirect('admin');

        $this->form_validation->set_rules('new_password', 'New Password', 'trim|exact_length[8]|required');
        $this->form_validation->set_rules('con_password', 'Confirm Password', 'trim|required|matches[new_password]');
        if($this->form_validation->run() == FALSE){
            $data['token'] = $token;
            $this->load->view('admin/reset_forgot_password');
        }
        else{
        	
        	//-- if token is valid then change admin password
            $get_record =   select(TBL_USER_FORGOT_PASSWORD,null,array('where'=>array('token'=>$token)),1);
            if(sizeof($get_record)>0){
                $user_id = $get_record['user_id'];
                $password_data = array('password' => $this->encrypt->encode($this->input->post('new_password',TRUE)));
                update(TBL_USERS,$user_id,$password_data);
                update(TBL_USER_FORGOT_PASSWORD,array('token'=>$token),array('complete_date'=>date('Y-m-d H:i:s')));
                $this->session->set_flashdata('success', 'Your password successfully changed');
                redirect('admin');
            }else{

            	//-- if token is not empty but invalid
                $this->session->set_flashdata('error', 'Invalid request try again');
                redirect('admin/forgot_password');
            }
        }
    }

}

/* End of file Admin.php */
/* Location: ./application/controllers/Admin.php */