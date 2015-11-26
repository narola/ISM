<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 09/03/15
 * Time: 12:20 PM
 */
include_once 'Encrypt.php';
include_once 'ConstantValues.php';
include_once 'SendEmail.php';
include_once 'TutorialGroup.php';

error_reporting(0);
class ProfileFunctions
{

    function __construct()
    {

    }

    public function call_service($service, $postData)
    {
        switch($service)
        {
            case "AuthenticateUser":
            {
                return $this->authenticateUser($postData);//done
            }
                break;
            case "RegisterUser":
            {
                return $this->registerUser($postData);//done
            }
                break;

            case "RequestForCredentials":
            {
                return $this->requestForCredentials($postData);//done
            }
                break;

            case "CheckUsernameAvailability":
            {
                return $this->checkUsernameAvailability($postData);//done
            }
                break;

            case "ForgotPassword":
            {
                return $this->forgotPassword($postData);//done
            }
                break;

            case "GetStudentAcademicInfo":
            {
                return $this->getStudentAcademicInfo($postData);//done
            }
                break;
            case "RequestForSchoolInfoUpdation":
            {
                return $this->requestForSchoolInfoUpdation($postData);//done
            }
                break;
                
            case "GetWalletSummary":
            {
                return $this->getWalletSummary($postData);//done
            }
                break;
                
            case "GenerateVoucher":
            {
                return $this->generateVoucher($postData);
            }
                break;
                
            case "GetAboutMe":
            {
                return $this->getAboutMe($postData);
            }
                break;
                
             case "EditAboutMe":
             {
                return $this->editAboutMe($postData);
             }
                break;
                
             case "GetBooksForUser":
            {
                return $this->getBooksForUser($postData);
            }
                break;
                
            case "GetMyActivity":
            {
                return $this->getMyActivity($postData);
            }
                break;
                
            case "BlockUser":
            {
                return $this->blockUser($postData);
            }
                break;
                
            case "GetPastimeForUser":
            {
                return $this->getPastimeForUser($postData);
            }
                break;
             
            case "GetRoleModelForUser":
            {
                return $this->getRoleModelForUser($postData);
            }
                break;
                
            case "GetMoviesForUser":
            {
                return $this->getMoviesForUser($postData);
            }
                break;
                
            case "ManageFavorite":
            {
                return $this->addResourcesToFavorite($postData);
            }
                break;
                
            case "FollowUser":
            {
                return $this->followUser($postData);
            }
                break;
            
            case "GetStudentProfile":
            {
                return $this->getStudentProfile($postData);
            }
                break;

            case "ManageBookLibrary":
            {
                return $this->manageBookLibrary($postData);
            }
                break;
        }
    }
    /*
    * getStudentAcademicInfo
    */

    public function getStudentAcademicInfo ($postData)
    {
        $data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $queryFrom=TABLE_STUDENT_ACADEMIC_INFO . " academicInfo INNER JOIN ".TABLE_CLASSROOMS." classroom INNER JOIN ".TABLE_SCHOOLS." schools INNER JOIN ".TABLE_COURSES."  courses INNER JOIN ".TABLE_SCHOOL_CLASSROOM."  school_classroom ";
        $queryGetFields="school_classroom.classroom_name,academicInfo.school_id,academicInfo.classroom_id,academicInfo.academic_year,academicInfo.course_id,schools.school_name,classroom.class_name,courses.course_name";
        $queryOn="academicInfo.school_id=school_classroom.school_id and academicInfo.classroom_id=school_classroom.classroom_id and academicInfo.school_id=schools.id and academicInfo.classroom_id=classroom.id and academicInfo.course_id=courses.id";
        $query = "SELECT ".$queryGetFields. " FROM ".$queryFrom ." on ".$queryOn." where user_id =" . $user_id;
        $result = mysql_query($query) or $message = mysql_error();
       //  echo $query;
        //echo $message;
        if (mysql_num_rows($result)) {
            $val = mysql_fetch_assoc($result);
            $data['school_id'] = $val['school_id'];
            $data['school_name'] = $val['school_name'];
            $data['course_id'] = $val['course_id'];
            $data['course_name'] = $val['course_name'];
            $data['academic_year'] = $val['academic_year'];
            $data['classroom_id'] = $val['classroom_id'];
            $data['classroom_name'] = $val['class_name'];
            $data['class_division'] = $val['classroom_name'];
            $status="success";
            //$message="";
        }
        else{
            $status="failed";
            //$message=DEFAULT_NO_RECORDS;

        }
        $response['student_info']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;
    }

    public function forgotPassword ($postData)
    {
        $data=array();
        $response=array();
        $email_id = validateObject ($postData , 'email_id', "");
        $email_id = addslashes($email_id);

        $sendEmail = new SendEmail();
        $randomString=gen_random_string();

        $status=0;
        $queryCheckEmail="SELECT * FROM ".TABLE_USERS." WHERE `email_id`='".$email_id."'";
        $resultCheckEmail=mysql_query($queryCheckEmail) or $errorMsg=mysql_error();
       // echo $queryCheckEmail;
        if(mysql_num_rows($resultCheckEmail)) {
            while ($val = mysql_fetch_assoc($resultCheckEmail)) {
                $status = 1;
            }
        }
        else{

                $status=0;

        }
        //$message="Hello ISM,\nI am very much interested to be part of ISM system.Please check my details below and let me know how can I become the part of this system.
        if($status==1) {
            $message = "Successfully sent";
            $status="success";
            $response = $sendEmail->sendemail("ism.educare@gmail.com", $randomString, "Forgot Password", $email_id);
        }
        else
        {
            $message = "Email id is not valid!";
            $status="failed";
        }
        // return "Request sent successfully'";

        //$response['data']=$data;
        // $response['status']=$status;
        $response['message']=$message;
        $response['status']=$status;
        $response['user']=$data;

        return $response;
    }

    public function checkUsernameAvailability ($postData)
    {
        $message='';
        $data=array();
        $response=array();
        $username = validateObject ($postData , 'username', "");
        $username = addslashes($username);

        if($username!=null)
        {
            $queryUserName="SELECT `username` FROM ".TABLE_USERS." WHERE `username`='".$username."'";
            $resultUserName=mysql_query($queryUserName) or $errorMsg=mysql_error();
            if(mysql_num_rows($resultUserName))
            {
                while ($val = mysql_fetch_assoc($resultUserName))
                {
                    regenerate:
                    {
                        $username=$val['username'];
                        $randomNumber=rand ( 0 , 999 );
                        $username.=$randomNumber;
                        //echo "\n".$username."i=";
                        $queryGenUserName="SELECT `username` FROM ".TABLE_USERS." WHERE `username`='".$username."'";
                        $resultGenUserName=mysql_query($queryGenUserName) or $errorMsg=mysql_error();
                        if(mysql_num_rows($resultGenUserName))
                        {
                            goto regenerate;
                        }
                        else
                        {
                            //	echo "\n".$username."i=else=";}
                            $status="success";
                            $message="username is not available.";
                            $data['username']=$username;
                        }
                    }
                }
            }
            else
            {
                $status="success";
                $message="username is available.";
            }
        }
        else
        {
            $status="failed";
            $message="Invalid data.";
        }
        $response['user']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    public function requestForCredentials ($postData)
    {
        $message ='';
        $post=array();
        $response=array();
        $firstname = validateObject ($postData , 'firstname', "");
        $firstname = addslashes($firstname);

        $lastname = validateObject ($postData , 'lastname', "");
        $lastname = addslashes($lastname);

        $home_address = validateObject ($postData , 'home_address', "");
        $home_address = addslashes($home_address);

        $contact_number = validateObject ($postData , 'contact_number', "");
        $contact_number = addslashes($contact_number);

        $email_address = validateObject ($postData , 'email_address', "");
        $email_address = addslashes($email_address);

        $school_name = validateObject ($postData , 'school_name', "");
        $school_name = addslashes($school_name);

        $city_id = validateObject ($postData , 'city_id', "");
        $city_id = addslashes($city_id);

        $state_id = validateObject ($postData , 'state_id', "");
        $state_id = addslashes($state_id);

        $country_id = validateObject ($postData , 'country_id', "");
        $country_id = addslashes($country_id);

        $queryState="SELECT `state_name` FROM `states` WHERE `id`=".$state_id;
        $resultState=mysql_query($queryState) or $errorMsg=mysql_error();
        if(mysql_num_rows($resultState))
        {
            while ($val = mysql_fetch_assoc($resultState))
            {
                $state_name=$val['state_name'];
            }
        }
        else
        {
            $state_name=DEFAULT_NO_RECORDS;
        }

        $queryCountry="SELECT `country_name` FROM `countries` WHERE `id`=".$country_id;
        $resultCountry=mysql_query($queryCountry) or $errorMsg=mysql_error();
        if(mysql_num_rows($resultCountry))
        {
            while ($val = mysql_fetch_assoc($resultCountry))
            {
                $country_name=$val['country_name'];
            }
        }
        else
        {
            $country_name=DEFAULT_NO_RECORDS;
        }

        $queryCity="SELECT `city_name` FROM `cities` WHERE `id`=".$city_id;
        $resultCity=mysql_query($queryCity) or $errorMsg=mysql_error();
        if(mysql_num_rows($resultCity))
        {
            while ($val = mysql_fetch_assoc($resultCity))
            {
                $city_name=$val['city_name'];
            }
        }
        else
        {
            $country_name=DEFAULT_NO_RECORDS;
        }
        $data['firstname']=$firstname;
        $data['lastname']=$lastname;
        $data['home_address']=$home_address;
        $data['contact_number']=$contact_number;
        $data['email_address']=$email_address;
        $data['school_name']=$school_name;
        $data['city_id']=$city_id;
        $data['state_id']=$state_id;
        $data['country_id']=$country_id;

        $sendEmail = new SendEmail();
        $message="Hello ISM,\nI am very much interested to be part of ISM system.Please check my details below and let me know how can I become the part of this system.
     	\nFullname: ".$firstname." ".$lastname.
            "\nHome Address: ".$home_address.
            "\nContact number: ". $contact_number.
            "\nEmail address: ".$email_address.
            "\nSchool name: ".$school_name.
            "\nCity: ".$city_name.
            "\nState: ".$state_name.
            "\nCountry: ".$country_name.
            "\n\nI am waiting for your call.
		\nThanks.";
        $response['status'] ="success";
        $sendEmail -> sendemail($email_address, $message,"Request For Credentials","ism.educare@gmail.com");
        //  $response['status'] =$status;
        $response['message'] ="Sent successfully";
        $response['request_credentials']=$post;
        return $response;

    }

    public function RequestForSchoolInfoUpdation ($postData)
    {
        $message ='';
        $post=array();
        $response=array();
        $email_address = validateObject ($postData , 'email_address', "");
        $email_address= addslashes($email_address);

        $message= validateObject ($postData , 'message', "");
        $message = addslashes($message);

        $name = validateObject ($postData , 'name', "");
        $name = addslashes($name);

        $sendEmail = new SendEmail();
        $email_message="Hello, \n".$message."\n\n Thank You \n ".$name;
        $response['status'] ="success";
        $sendEmail -> sendemail($email_address, $email_message,"Request For Wrong School Details ","ism.educare@gmail.com");
        //  $response['status'] =$status;
        $response['message'] ="Sent successfully";
        $response['request_updation']=$post;
        return $response;

    }

    public function authenticateUser($postData)
    {

        $data=array();
        $data['user']=array();
        $obj = new CI_Encrypt();

        $username = validateObject ($postData , 'username', "");
        $username = addslashes($username);

        $password = validateObject ($postData , 'password', "");
        $password = addslashes($password);

        $queryUser="SELECT id,username,password,profile_pic,full_name from ".TABLE_USERS." where username='".$username."'";
        //echo $encrypted_passwd = $obj->encode($password);
        //$decrypted_password = $obj->decode("vxbhjXDuOZ8uncgNP7ykB2UvgLr5Q9SU31K6z+JGMYfREqZTYyr1f5E20k7jMTxNILaWMK0ImrNVS1GGn6gshA==");
        //echo "---------".$obj->decode("v8R/H5JqnMdmkqVWyYLr7a/z46844fI8otkn17Ba+Afd5eOTjH9uJRg0X5nHW6EAcAQP4QNhvbNWmfgqlzLXew==");
        $resultUser=mysql_query($queryUser) or $message=mysql_error();
        if(mysql_num_rows($resultUser))
        {
            $encryptedPassword='';
            while ($val = mysql_fetch_assoc($resultUser))
            {
                //echo $obj->encode($password);
                $encryptedPassword=$val['password'];
                //echo $encryptedPassword;
                $decrypted_password = $obj->decode($encryptedPassword);
                //echo $decrypted_password;
                if($decrypted_password==$password)
                {
                	$post=array();
                    $message=CREDENTIALS_EXITST;
                    $post['user_id']=$val['id'];
                    $post['full_name']=$val['full_name'];
                    $post['profile_pic']=$val['profile_pic'];
                    $status="success";

	                $tutorialGroupClass = new TutorialGroup();
	                $tutorialGroup = $tutorialGroupClass -> call_service("GetTutorialGroupOfUser", $post['user_id']);
					
	                if ($tutorialGroup['tutorial_group_found']) {
		                $groupData = $tutorialGroup['tutorial_group'];
		                $post['tutorial_group_id'] = $groupData['tutorial_group_id'];
		                $post['tutorial_group_joining_status'] = $groupData['tutorial_group_joining_status'];
		                $post['tutorial_group_complete'] = $groupData['tutorial_group_complete'];
		                $post['tutorial_group_name'] = $groupData['tutorial_group_name'];
		                $post['tutorial_group_members'] = $groupData['tutorial_group_members'];
	                }

               }
                else
                {
                    $status="failed";
                    $message=CREDENTIALS_DO_NOT_EXIST_IN_OUR_SYSTEM;
                }
            }
        }
        else
        {
            //$encryptedPassword=encryptPassword($passowrd);
            $queryOn="autoGenerateCredential.school_id=schools.id and autoGenerateCredential.course_id=courses.id";
            $queryData="SELECT * FROM ".TABLE_AUTO_GENERATED_CREDENTIAL." autoGenerateCredential where username='".$username."'";
            //$queryAuthUser="select * from ".TABLE_AUTO_GENERATED_CREDENTIAL." where username='".$userName."'";
            $resultAuthUser=mysql_query($queryData) or $message=mysql_error();
            if(mysql_num_rows($resultAuthUser))
            {
                $encryptedPassword='';
                while ($val = mysql_fetch_assoc($resultAuthUser))
                {
                    $username=$val['username'];
                    $encryptedPassword=$val['password'];
                    $status=$val['status'];
                    $id=$val['id'];
                    $decrypted_password = $obj->decode($encryptedPassword);

                    if($decrypted_password==$password)
                    {

                        if($status==1)
                        {
                            //$status=$status;
                            $message = CREDENTIALS_ARE_DEACTIVATED_OR_ALREADY_TAKEN;
                        }
                        else if($status==0)
                        {
                            $post=array();
                            //$queryData="SELECT * FROM `auto_generated_credential` t1 INNER JOIN `schools`t2 INNER JOIN `courses`t3 ON t1.school_id=t2.id and t1.course_id=t3.id where `username`='WJL91RU473'";
                            $post['credential_id']=$val['id'];
                            $post['school_id']=$val['school_id'];
                            $post['role_id']=$val['role_id'];
                            $post['class_id']=$val['classroom_id'];
                            $post['course_id']=$val['course_id'];

                            $post['academic_year']=$val['academic_year'];
                            $querySchool="select school.school_name,school.school_type,course.course_name,district. district_name, class.class_name from courses course INNER JOIN classrooms class INNER JOIN districts district INNER JOIN schools school on school.district_id=district.id where course.id=".$val['course_id']." and school.id=".$val['school_id']." and class.id=".$val['classroom_id']." limit 1";
                            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
                            if(mysql_num_rows($resultSchool)){
                                $valSchool=mysql_fetch_assoc($resultSchool);
                                $post['school_name']=$valSchool['school_name'];
                                $post['course_name']=$valSchool['course_name'];
                                $post['district_name']=$valSchool['district_name'];
                                $post['class_name']=$valSchool['class_name'];
                                $post['school_type']=$valSchool['school_type'];
                            }
                            $status="success";
                            $message=CREDENTIALS_EXITST;

                        }
                    }
                    else
                    {
                       // $post="";
                        $status="failed";
                        $message=CREDENTIALS_DO_NOT_EXIST_IN_OUR_SYSTEM;
                    }

                }
            }
            else
            {
               // $post="";
                $status="failed";
               $message = CREDENTIALS_DO_NOT_EXIST_IN_OUR_SYSTEM;
            }

        }
        // AND password='".$encryptedPassword."'

        array_push($data['user'],$post);
        $data['message'] = $message;
        $data['status'] = $status;

        return $data;
    }



    public function registerUser($postData)
    {
		
        $data=array();
        $response=array();
        //$response['data']=array();

        $credential_id = validateObject ($postData , 'credential_id', "");
        $credential_id = addslashes($credential_id);

        $firstname = validateObject ($postData , 'firstname', "");
        $firstname = addslashes($firstname);

        $lastname = validateObject ($postData , 'lastname', "");
        $lastname = addslashes($lastname);

        $home_address = validateObject ($postData , 'home_address', "");
        $home_address = addslashes($home_address);

        $contact_number = validateObject ($postData , 'contact_number', "");
        $contact_number = addslashes($contact_number);

        $email_address = validateObject ($postData , 'email_address', "");
        $email_address = addslashes($email_address);

        $gender = validateObject ($postData , 'gender', "");
        $gender = addslashes($gender);

        $birthdate = validateObject ($postData , 'birthdate', "");
        // $birthdate = addslashes($birthdate);

        $city_id = validateObject ($postData , 'city_id', "");
        $city_id = addslashes($city_id);

        $state_id = validateObject ($postData , 'state_id', "");
        $state_id = addslashes($state_id);

        $country_id = validateObject ($postData , 'country_id', "");
        $country_id = addslashes($country_id);

        $device_token = validateObject ($postData , 'device_token', "");
        $device_token = addslashes($device_token);

        $role_id = validateObject ($postData , 'role_id', "");
        $role_id = addslashes($role_id);

        $device_type = validateObject ($postData , 'device_type', "");
        $device_type = addslashes($device_type);

        $username = validateObject ($postData , 'username', "");
        $username = addslashes($username);

        $password = validateObject ($postData , 'password', "");
        $password = addslashes($password);

        $profile_image = validateObject ($postData , 'profile_image', "");
        $profile_image_name = validateObject ($postData , 'profile_image_name', "");
        $profile_image_name = addslashes($profile_image_name);
        $profile_image_name_array=explode(".",$profile_image_name);
        if (!is_dir(USER_PROFILE_PICTURE)) {
            mkdir(USER_PROFILE_PICTURE, 0777, true);
        }
       // if (!mkdir(USER_PROFILE_PICTURE, 0777, true)) {
           // die('Failed to create folders...'.USER_PROFILE_PICTURE);
       // }
        // echo $profile_image_name_array[0]."_test.".$profile_image_name_array[1];
        
        $profile_image_name=$profile_image_name_array[0]."_test.".$profile_image_name_array[1];
        $obj = new CI_Encrypt();

        $insertFields="`username`, `password`,`device_type`, `first_name`, `last_name`, `full_name`,`email_id`, `contact_number`, `home_address`, `city_id`, `state_id`, `country_id`, `birthdate`, `gender`, `device_token`, `role_id`";
        $insertValues="'".$username."','".$obj->encode($password)."','".$device_type."','".$firstname."','".$lastname."','".$firstname." ".$lastname."','".$email_address."','".$contact_number."','".$home_address."','".$city_id."','".$state_id."','".$country_id."','".$birthdate."','".$gender."','".$device_token."','".$role_id."'";

        $queryInsert="INSERT INTO ".TABLE_USERS."(".$insertFields.") values(".$insertValues.")";
        $resultQuery=mysql_query($queryInsert) or $message=mysql_error();
        if($resultQuery)
        {
            $user_id = mysql_insert_id();
            if($role_id==2)
            {
                $course_id = validateObject ($postData , 'course_id', "");
                $course_id = addslashes($course_id);

                $academic_year = validateObject ($postData , 'academic_year', "");
                $academic_year = addslashes($academic_year);

                $classroom_id = validateObject ($postData , 'classroom_id', "");
                $classroom_id = addslashes($classroom_id);

                $school_id = validateObject ($postData , 'school_id', "");
                $school_id = addslashes($school_id);
                
                
                $insertAcademicField="`user_id`, `school_id`, `classroom_id`, `academic_year`, `joining_year`, `joining_class`, `course_id`";
                $insertAcademicValue="'".$user_id."', '".$school_id."','".$classroom_id."','".$academic_year."','". date('M,Y')."','".$classroom_id."','".$course_id."'";
				
	
                //Image Saving
                $profile_user_link="user_".$user_id."/";

                $profile_image_dir=USER_PROFILE_PICTURE.$profile_user_link;

                if (!is_dir(USER_PROFILE_PICTURE.$profile_user_link)) {
                    mkdir(USER_PROFILE_PICTURE.$profile_user_link, 0777, true);
                }

                $profile_image_dir = $profile_image_dir . $profile_image_name;
                $profile_image_link = $profile_user_link.$profile_image_name;
                file_put_contents($profile_image_dir, base64_decode($profile_image));

                $queryProfileImage="INSERT INTO ".TABLE_USER_PROFILE_PICTURE."(`user_id`, `profile_link`) VALUES (".$user_id.",'".$profile_image_link."')";
                $resultProfileImage=mysql_query($queryProfileImage) or $message=mysql_error();
                
                $queryAcademic="INSERT INTO ".TABLE_STUDENT_PROFILE."(".$insertAcademicField.") values (".$insertAcademicValue.")";
                $resultAcademic=mysql_query($queryAcademic) or $message=mysql_error();
                
                //Generate Token
                $security=new SecurityFunctions();
    			$generateToken=$security->generateToken(8);
               
                
                $insertTokenField="`user_id`, `token`";
                $insertTokenValue="".$user_id.",'".$generateToken."'";
				
				$queryAddToken="INSERT INTO ".TABLE_TOKENS."(".$insertTokenField.") values (".$insertTokenValue.")";
                $resultAddToken=mysql_query($queryAddToken) or $message=mysql_error();
                
                //**************************For Encryption***************************
                //===================================================================
                //=============Use username as Key For Encrypt=======================
              	$aes256Key = hash("SHA256", $username, true);

				// for good entropy (for MCRYPT_RAND)
				
				srand((double) microtime() * 1000000);
				// generate random iv
		
				$iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_CBC), MCRYPT_RAND);
				
				
				//===================================================================
                //===================Call AES Encrypt Function=======================
                
				$secrect_key = $security->functionToEncrypt($generateToken, $aes256Key);
		
				//**************************End Encryption***************************
		
                if($resultAddToken)
                {
                	$post['token']=$secrect_key;
                	$status="success";
                    $message="Token key generated.";
                }
                else
                {
                	$status="failed";
                }
                
                
                if($resultAcademic)
                {
//                    $updateStatus="UPDATE `auto_generated_credential` SET `status`='1' WHERE `id`=".$credential_id;
//                    $resultAcademic=mysql_query($updateStatus) or $message=mysql_error();
                   // echo $updateStatus;
                    $post['user_id']=$user_id;
                    $post['full_name']=$firstname." ".$lastname;
                    $post['profile_pic']=$profile_image_link;
                    $data[]=$post;
                    $status="success";
                    $message="Registration completed successfully";

                }
                ELSE{
					$status="failed";
                }
            }
        }
        else
        {
            $status="failed";

        }
        $response['user']=$data;
        $response['status'] = $status;
        $response['message'] = $message;


        return $response;
    }
    
    /*
    *getWalletSummary
    */
    public function getWalletSummary($postData)
    {
    	$message ='';
        $status='';
        $post=array();
        $data=array();
        $response=array();
        
		$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $queryGetSummary = "SELECT user_id,wallet_balance FROM " . TABLE_STUDENT_PROFILE ." WHERE user_id =".$user_id;
        $resultGetSummary = mysql_query($queryGetSummary) or $message = mysql_error();
        $summary_count = mysql_num_rows($resultGetSummary);

        if ($summary_count > 0) {
            while ($val = mysql_fetch_assoc($resultGetSummary)) {

			$post=array();
			
			$post['user_id']=$val['user_id'];
			$post['wallet_balance']=$val['wallet_balance'];
			
			//Get Voucher Details
            $queryGetDetails = "SELECT id as 'voucher_id',voucher_code,created_date,voucher_amount as 'Amount' FROM ".TABLE_USER_VOUCHER." WHERE user_id=".$user_id;
            $resultGetDetails = mysql_query($queryGetDetails) or $message = mysql_error();
            $allDetails=array();
            
            if(mysql_num_rows($resultGetDetails))
            {
                while($details=mysql_fetch_assoc($resultGetDetails))
                {
                    $allDetails[]=$details;
                } 
            }
			$post['voucher_details']=$allDetails;
      
            $data[]=$post;
            }
             $status="success";
       		 $message="";
        }
        else
        {
            $status="failed";
            $message = DEFAULT_NO_RECORDS;
            $data="";
        }


		$response['wallet_summary']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
    
    /*
    *generateVoucher
    */
    public function generateVoucher($postData)
    {
   		$message ='';
        $status='';
        $data=array();
        $response=array();
        
    
    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $voucher_amount = validateObject($postData, voucher_amount, "");
        $voucher_amount = addslashes($voucher_amount);
        
        $queryGetWalletBal="select wallet_balance from ".TABLE_STUDENT_PROFILE." where user_id=".$user_id;
        $resultGetWalletBal=mysql_query($queryGetWalletBal) or $message=mysql_error();
        
        if(mysql_num_rows($resultGetWalletBal)>0)
        {
       		$walletBalance=mysql_fetch_row($resultGetWalletBal);
    	    $balance= $walletBalance[0];
        
        $queryGetConfigValue="select config_value from ".TABLE_ADMIN_CONFIG." where config_key='maxVoucherAmount'";
        $resultGetConfigValue=mysql_query($queryGetConfigValue) or $message=mysql_error();
        
        $configValue=mysql_fetch_row($resultGetConfigValue);
        $voucher_config_value=$configValue[0];
        
        $percent_value=(($balance*$voucher_config_value)/100);
       
       
       //Generate random String for Voucher Code

            $security=new SecurityFunctions();
            $final_rand=$security->generateToken(6);

       
       //Check, if Voucher_amont is greater than Count value or not
           // if($percent_value > $voucher_amount)
       		if($voucher_amount < $percent_value)
      	 	{
       	  		$insertFields="`user_id`,`voucher_code`,`voucher_amount`";
          		$insertValues="".$user_id.",'".$final_rand."',".$voucher_amount."";
           
          		$queryInsert="INSERT INTO ".TABLE_USER_VOUCHER."(".$insertFields.") values(".$insertValues.")";
          		//echo $queryInsert; 
         		$resultQuery=mysql_query($queryInsert) or $message=mysql_error();


                $remaining_wallet_bal = $balance - $voucher_amount;
                $queryUpdateWalletBalance="UPDATE ".TABLE_STUDENT_PROFILE." SET wallet_balance = ".$remaining_wallet_bal." WHERE user_id=".$user_id;
                $resultUpdateWalletBalance=mysql_query( $queryUpdateWalletBalance) or $message=mysql_error();

      	  		if($resultQuery)
          		{
            			$data['voucher_code']=$final_rand;
            			$data['voucher_amount']=$voucher_amount;
                        $data['total_available_balance']=$percent_value;
            			$status="success";
      	  		}
      	  		else
      	 		{
						$status="failed";
      	  		}
      		}	  
      		else
      		{
       	       	$message="You cannot create coupon greater then ".$percent_value;
       	       	$status="failed";
       	       	
      		}
       } 
       else
       {
       		$status="failed";
       }
        $response['voucher']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
    
    /*
    *getAboutMe
    */
    public function getAboutMe($postData)
    {
    	$message ='';
        $status='';
        $data=array();
        $response=array();
        
    
    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $selectQuery="SELECT school.school_name,classroom.class_name,student_profile.*,users.id,users.full_name,users.profile_pic,users.contact_number,users.birthdate,users.about_me 
        FROM ".TABLE_USERS." users INNER JOIN ".TABLE_STUDENT_PROFILE." student_profile ON users.id=student_profile.user_id
        INNER JOIN ".TABLE_SCHOOLS." school ON school.id=student_profile.school_id 
        INNER JOIN ".TABLE_CLASSROOMS." classroom ON classroom.id=student_profile.classroom_id
        WHERE student_profile.user_id=".$user_id;
        
        $resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        
        if(mysql_num_rows($resultQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultQuery))
                {
                	$post=array();
                	$post['user_id']=$val['id'];
                	$post['username']=$val['full_name'];
                	$post['profile_pic']=$val['profile_pic'];
                	$post['school_name']=$val['school_name'];
                	$post['course_name']=$val['class_name'];
                	$post['contact_number']=$val['contact_number'];
                	$post['birthdate']=$val['birthdate'];
                	$post['aboutMeText']=$val['about_me'];
                	$post['ambitionInLife']=$val['ambition_in_life'];
                	$post['total_post']=$val['total_posts'];
                	$post['total_studymates']=$val['total_studymates'];
                	$post['total_authors_followed']=$val['otal_authors_followed'];
                	$post['ism_score']=$val['total_score'];
                	$post['ism_rank']=$val['rank'];
                	$post['total_exams']=$val['total_exams'];
                	$post['total_assignment']=$val['total_assignment'];
                	$post['total_question_asked']=$val['total_questions_asked'];
                	$post['total_Favorite_questions']=$val['total_favorite_questions'];
                	$post['total_badges_earned']=$val['total_badges'];
                    $data[]=$post;
                } 
                $message="Request accepted";
                $status="success";
            }
       
        else
        {
       		$status="failed";
        }
        $response['user']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
    
    /*
    *editAboutMe
    */
    public function editAboutMe($postData)
    {
    	$message ='';
        $status='';
        $data=array();
        $response=array();
        
    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
    	$username = validateObject($postData, 'username', "");
        $username = addslashes($username);
        
        $profile_image = validateObject($postData, 'profile_image', "");
        $profile_image = addslashes($profile_image);
        
        $contact_number = validateObject($postData, 'contact_number', "");
        $contact_number = addslashes($contact_number);
        
        $birthdate = validateObject($postData, 'birthdate', "");
        //$birthdate = addslashes($user_id);
        
        $aboutMeText = validateObject($postData, 'aboutMeText', "");
        //$aboutMeText = addslashes($user_id);
    	
    	$ambitionInLife = validateObject($postData, 'ambitionInLife', "");
        $ambitionInLife = addslashes($ambitionInLife);
    	
    	
    	//============For Profile Picture========================
    	/*if (!is_dir(USER_PROFILE_PICTURE))
            mkdir(USER_PROFILE_PICTURE, 0777, true);

        //Image Saving
        $profile_user_link="user_".$user_id."/";
		
        $profile_image_dir=USER_PROFILE_PICTURE.$profile_user_link;
		
        if (!is_dir(USER_PROFILE_PICTURE.$profile_user_link)) {
            mkdir(USER_PROFILE_PICTURE.$profile_user_link, 0777, true);
        }
        $created_date = date("Ymd-His");

        if ($_FILES["mediaFile"]["error"] > 0) {
            $message = $_FILES["mediaFile"]["error"];
        }
        else {
            $mediaName = "IMG".$created_date."_test.png";

            $uploadFile = $profile_image_dir . $mediaName;
            if (move_uploaded_file($_FILES['profile_image']['tmp_name'], $uploadFile)) {
                //store image data.
                $link=$profile_user_link . $mediaName;
			}
		}*/
    	//=========================================================
    	
    	
    	
    	$updateFeild="full_name='".$username."', profile_pic='".$profile_image."', contact_number='".$contact_number."', birthdate='".$birthdate."', about_me='".$aboutMeText."'";
    	
    		$queryUpdate="UPDATE ".TABLE_USERS." SET ".$updateFeild ." WHERE id=".$user_id;
    		$resultQuery=mysql_query($queryUpdate) or $message=mysql_error();
    		
    	 if($resultQuery)
    	 {
    		if($ambitionInLife != NULL)
    		{
    			$queryToUpdateAmbition="UPDATE ".TABLE_STUDENT_PROFILE." SET ambition_in_life='".$ambitionInLife."' WHERE user_id=".$user_id;
    			$resultToUpdateAmbition=mysql_query($queryToUpdateAmbition) or $message=mysql_error();
    		
    			if($resultToUpdateAmbition)
    			{
    				$status="success";
    			}    			
    			else
    			{
    				$status="failed";
    			}
    		}
    		$status="success";
    		$message= "successfully updated";
    	}
    	else
        {
       		$status="failed";
        }
        $response['user']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
    
    
    /*
    *getBooksForUser
    */
    public function getBooksForUser($postData)
    {
    	$message ='';
        $status='';
        $data=array();
        $suggested=array();
        $favorite=array();
        $response=array();
        
    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        /*$limit1 = validateObject($postData, 'limit1', "");
        $limit1 = addslashes($limit1);
        
        $limit2 = validateObject($postData, 'limit2', "");
        $limit2 = addslashes($limit2);*/
        
        $selectQuery="select book.*,authorBookJoin.full_name,authorBookJoin.profile_pic from ".TABLE_BOOKS ." book
         LEFT JOIN(SELECT autotBook.user_id,autotBook.book_id,users.full_name,users.profile_pic from ".TABLE_AUTHOR_BOOK." autotBook LEFT JOIN ".TABLE_USERS.
         " users ON users.id=autotBook.user_id) authorBookJoin ON authorBookJoin.book_id=book.id WHERE id NOT IN(SELECT book_id from ".TABLE_USER_FAVORITE_BOOK."  where user_id=".$user_id. ") ORDER BY book.id DESC LIMIT 30";

        $resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultQuery))
                {
                	$suggested['book_id']=$val['id'];
                	$suggested['book_name']=$val['book_name'];
                	$suggested['book_image']=$val['image_link'];
                	$suggested['ebook_link']=$val['ebook_link'];
                	$suggested['publisher_name']=$val['publisher_name'];
                	$suggested['description']=$val['book_description'];
                	$suggested['author_name']=$val['full_name'];
                	$suggested['author_image']=$val['profile_pic'];
                	$suggested['price']=$val['price'];

                    $tags=array();
                    $tagQuery="SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_BOOK." tag_books JOIN ".TABLE_TAGS." tags ON tags.id=tag_books.tag_id WHERE tag_books.book_id=".$val['id'];
                    $tagResult=mysql_query($tagQuery) or  $message=mysql_error();
                    if(mysql_num_rows($tagResult))
                    {

                        while($rowGetTags=mysql_fetch_assoc($tagResult)) {
                            $tags[]=$rowGetTags;

                        }
                        $suggested['tags']=$tags;
                    }
                    else{
                        $suggested['tags']=$tags;
                    }

                    $data['suggested_book'][]=$suggested;
                } 
                
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['suggested_book']=array();
            	//$status="failed";
            }
            
         $selectFavoriteQuery="select DISTINCT book.*,authorBookJoin.full_name,authorBookJoin.profile_pic from ".TABLE_USER_FAVORITE_BOOK . " userFavoriteBook
         INNER JOIN ".TABLE_BOOKS ." book ON book.id=userFavoriteBook.book_id
         LEFT JOIN(SELECT authorBook.user_id,authorBook.book_id,users.full_name,users.profile_pic from ".TABLE_AUTHOR_BOOK." authorBook INNER JOIN ".TABLE_USERS.
         " users ON users.id=authorBook.user_id) authorBookJoin ON authorBookJoin.book_id=book.id
         WHERE userFavoriteBook.user_id=".$user_id;
        // echo  $selectFavoriteQuery;
    	 $resultFavoriteQuery=mysql_query($selectFavoriteQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultFavoriteQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultFavoriteQuery))
                {
                    $favorite['book_id']=$val['id'];
                	$favorite['book_name']=$val['book_name'];
                	$favorite['book_image']=$val['image_link'];
                	$favorite['ebook_link']=$val['ebook_link'];
                	$favorite['publisher_name']=$val['publisher_name'];
                	$favorite['description']=$val['book_description'];
                	$favorite['author_name']=$val['full_name'];
                	$favorite['author_image']=$val['profile_pic'];
                	$favorite['price']=$val['price'];

                    $tags=array();
                    $tagQuery="SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_BOOK." tag_books JOIN ".TABLE_TAGS." tags ON tags.id=tag_books.tag_id WHERE tag_books.book_id=".$val['id'];
                    $tagResult=mysql_query($tagQuery) or  $message=mysql_error();
                    if(mysql_num_rows($tagResult))
                    {

                        while($rowGetTags=mysql_fetch_assoc($tagResult)) {
                            $tags[]=$rowGetTags;

                        }
                        $favorite['tags']=$tags;
                    }
                    else{
                        $favorite['tags']=$tags;
                    }

                    $data['favorite_book'][]=$favorite;
                } 
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['favorite_book']=array();
            	//$status="failed";
            }
       
       
    	$response['books'][]=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
    
    /*
    *getMyActivity
    */
    public function getMyActivity($postData)
    {
    	$message ='';
        $status='';
        $data=array();
        $response=array();
        
    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $selectQuery="SELECT user_activity.user_id,users.full_name,users.profile_pic,user_activity.display_content,user_activity.resource_id FROM ".TABLE_USER_ACTIVITY. " user_activity 
        INNER JOIN ".TABLE_USERS." users ON user_activity.user_id=users.id WHERE user_activity.user_id=".$user_id;
       // echo $selectQuery;
        $resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        
        if(mysql_num_rows($resultQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultQuery))
                {
                    $data[]=$val;
                } 
                $message="";
                $status="success";
            }
            else
            {
            	$status="failed";
            }
        
    	$response['activity']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
    
    /*
     * blockUser
     */
    public function blockUser($postData)
    {
    	$message ='';
        $status='';
        $data=array();
        $response=array();
        
        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $email_id = validateObject($postData, 'email_id', "");
        $email_id = addslashes($email_id);
        
        $block_user = validateObject($postData, 'block_user', "");
        $block_user = addslashes($block_user);
        
        //Check If UserID is not Present, then Find id from EmailID.
        if($user_id == NULL || $user_id == 0)
        {
        	$selectQuery="SELECT id FROM ".TABLE_USERS." WHERE email_id='".$email_id."'";
   			$resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        	$getUserId=mysql_fetch_row($resultQuery);
        	$user_id= $getUserId[0];
        }
        
         if($user_id != NULL || $block_user != NULL){
         	
         	//Find UserId and Block User from StudyMate table
        	 $queryFindStudyMate="SELECT * FROM ".TABLE_STUDYMATES." WHERE mate_id=".$block_user." and mate_of=".$user_id;
        	 //echo $queryFindStudyMate;
         	 $resultFindStudyMate=mysql_query($queryFindStudyMate) or $message=mysql_error();
         
        	if(mysql_num_rows($resultFindStudyMate) > 0)
        	{
                $updateQuery="UPDATE ".TABLE_STUDYMATES." SET status='block' WHERE mate_id=".$block_user." and mate_of=".$user_id;
                $updateResult=mysql_query($updateQuery) or $message=mysql_error();
                $message="user blocked";
                $status="success";
        	}
        	else
        	{
        		//Find UserId and Block User from StudyMateRequest table
        		
        		$queryFindUserFromRequest="SELECT * FROM ".TABLE_STUDYMATES_REQUEST." WHERE request_from_mate_id=".$block_user." and request_to_mate_id=".$user_id;
        		$resultFindUserFromRequest=mysql_query($queryFindUserFromRequest) or $message=mysql_error();
        		if(mysql_num_rows($resultFindUserFromRequest) > 0)
        		{
        			$updateQuery="UPDATE ".TABLE_STUDYMATES_REQUEST." SET status=2 WHERE request_from_mate_id=".$block_user." and request_to_mate_id=".$user_id;
        			$updateResult=mysql_query($updateQuery) or $message=mysql_error();
               		 $message="user blocked";
                	 $status="success";
            	}
            	else
            	{
            		 $message= DEFAULT_NO_RECORDS;
            		 $status="failed";
            	}
        	}
        }
        else
        {
        	$status="failed";
            $message="";
        }
       
        $response['block_user']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
    
     /*
     * getPastimeForUser
     */
    public function getPastimeForUser($postData)
    {
    	$message ='';
        $status='';
        $data=array();
        $suggested=array();
        $favorite=array();
        $response=array();
        
    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        
         $selectQuery="select id as 'pastime_id',pastime_name,pastime_image from ".TABLE_PASTIMES." WHERE id NOT IN(SELECT pastime_id from ".TABLE_USER_FAVORITE_PASTIME.") ORDER BY id DESC LIMIT 30";
         $resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultQuery))
                {
                    $data['suggested_pastime'][]=$val;
                } 
                
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['suggested_pastime']=array();
            	//$status="failed";
            }
            
        /* $selectFavoriteQuery="select user_favorite_pastime.pastime_id,users.full_name as 'pastime_name',users.profile_pic as 'pastime_image' from " .TABLE_USER_FAVORITE_PASTIME." user_favorite_pastime 
         INNER JOIN ".TABLE_PASTIMES." pastime ON user_favorite_pastime.pastime_id=pastime.id 
         INNER JOIN ".TABLE_USERS." users ON users.id=user_favorite_pastime.user_id
         WHERE user_favorite_pastime.user_id=".$user_id;
         echo  $selectFavoriteQuery;*/
          $selectFavoriteQuery="select user_favorite_pastime.pastime_id,pastime.pastime_name,pastime.pastime_image from " .TABLE_USER_FAVORITE_PASTIME." user_favorite_pastime 
         INNER JOIN ".TABLE_PASTIMES." pastime ON user_favorite_pastime.pastime_id=pastime.id 
         WHERE user_favorite_pastime.user_id=".$user_id;
    	 $resultFavoriteQuery=mysql_query($selectFavoriteQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultFavoriteQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultFavoriteQuery))
                {
                    $data['favorite_pastime'][]=$val;
                } 
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['favorite_pastime']=array();
            	//$status="failed";
            }
       
       
    	$response['pastime'][]=$data;
        $response['message'] = $message;
        $response['status'] = $status;
        
	   return $response;
    }
    
    /*
    * getRoleModelForUser
    */
    public function getRoleModelForUser($postData)
    {
    	$message ='';
        $status='';
        $data=array();
        $suggested=array();
        $favorite=array();
        $response=array();
        
    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        
         $selectQuery="SELECT id as 'rolemodel_id',model_name,model_image,birthdate,description,organization,quotes,achievements,activities,education 
         from ".TABLE_ROLE_MODELS." WHERE id NOT IN(SELECT role_model_id from ".TABLE_USER_ROLE_MODEL.") ORDER BY id DESC LIMIT 30";
         $resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultQuery))
                {
                    $data['suggested_rolemodel'][]=$val;
                } 
                
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['suggested_rolemodel']=array();
            	//$status="failed";
            }
            
         $selectFavoriteQuery="select user_rolemodel.role_model_id,roleModel.model_name,roleModel.model_image,roleModel.birthdate,roleModel.description,
         roleModel.organization,roleModel.quotes,roleModel.achievements,roleModel.activities,roleModel.education from " .TABLE_USER_ROLE_MODEL." user_rolemodel 
         INNER JOIN ".TABLE_ROLE_MODELS." roleModel ON user_rolemodel.role_model_id=roleModel.id 
         WHERE user_rolemodel.user_id=".$user_id;
        // echo  $selectFavoriteQuery;
    	 $resultFavoriteQuery=mysql_query($selectFavoriteQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultFavoriteQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultFavoriteQuery))
                {
                    $data['favorite_rolemodel'][]=$val;
                } 
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['favorite_rolemodel']=array();
            	//$status="failed";
            }
       
       
    	$response['role_model'][]=$data;
        $response['message'] = $message;
        $response['status'] = $status;
        
	   return $response;
    }
    
    
    /*
    * getMoviesForUser
    */
    public function getMoviesForUser($postData)
    {
    	$message ='';
        $status='';
        $data=array();
        $suggested=array();
        $favorite=array();
        $response=array();
        
    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        
         $selectQuery="SELECT id as 'movie_id',movie_name,movie_image,genres as 'movie_genre',description,screenplay from ".TABLE_MOVIES." WHERE id NOT IN (SELECT movie_id from ".TABLE_USER_FAVORITE_MOVIE.") ORDER BY id DESC LIMIT 30";
         $resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultQuery))
                {
                    $data['suggested_movies'][]=$val;
                } 
                
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['suggested_movies']=array();
            	//$status="failed";
            }
            
         $selectFavoriteQuery="select user_favorite_movie.movie_id,movie.movie_name,movie.movie_image,movie.genres as 'movie_genre',movie.description,movie.screenplay 
         from " .TABLE_USER_FAVORITE_MOVIE." user_favorite_movie 
         INNER JOIN ".TABLE_MOVIES." movie ON user_favorite_movie.movie_id=movie.id 
         WHERE user_favorite_movie.user_id=".$user_id;
        // echo  $selectFavoriteQuery;
    	 $resultFavoriteQuery=mysql_query($selectFavoriteQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultFavoriteQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultFavoriteQuery))
                {
                    $data['favorite_movies'][]=$val;
                } 
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['favorite_movies']=array();
            	//$status="failed";
            }
       
       
    	$response['movies'][]=$data;
        $response['message'] = $message;
        $response['status'] = $status;
        
	   return $response;
    }
    
    /*
    * addResourcesToFavorite
    */
    public function addResourcesToFavorite($postData)
    {
    	$data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $resource_name = validateObject($postData, 'resource_name', "");
        $resource_name = addslashes($resource_name); 
        
        $fav_resource_id = validateObject($postData, 'fav_resource_id', "");

        $unfavorite_resource_id = validateObject($postData, 'unfavorite_resource_id', "");

        if($resource_name == "rolemodel"){
        	$table= TABLE_USER_ROLE_MODEL;
            $main_table=TABLE_ROLE_MODELS;
        	$resource_name_id="role_model_id";
        }
        else if($resource_name == "books"){
        	$table= TABLE_USER_FAVORITE_BOOK;
            $main_table=TABLE_BOOKS;
        	$resource_name_id="book_id";
       }
        else if($resource_name == "movies"){
        	$table= TABLE_USER_FAVORITE_MOVIE;
            $main_table=TABLE_MOVIES;
        	$resource_name_id="movie_id";
        }
       else if($resource_name == "pastimes"){
        	$table= TABLE_USER_FAVORITE_PASTIME;
            $main_table=TABLE_PASTIMES;
        	$resource_name_id="pastime_id";
        }


        //To add resources in favorite

        if($fav_resource_id!=null)
        {
            foreach($fav_resource_id as $fav_id) {
                $selQuery = "SELECT * FROM " . $table . " WHERE user_id=" . $user_id . " AND " . $resource_name_id . "=" . $fav_id;
                $selResult = mysql_query($selQuery) or $message = mysql_error();

                if (mysql_num_rows($selResult) == 0) {
                    $insertFields = "`user_id`,`" . $resource_name_id . "`,`is_delete`";
                    $insertValues = $user_id . "," . $fav_id.",'1'";

                    $query = "INSERT INTO " . $table . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                    $result = mysql_query($query) or $message = mysql_error();


                }
                else
                {
                    $queryUpdate="UPDATE " .$table ." SET is_delete = 1 WHERE user_id=".$user_id." AND ".$resource_name_id."  =" . $fav_id;
                    $resultUpdate = mysql_query($queryUpdate) or $errorMsg = mysql_error();
                }
            }
            $status = "success";
            $message = "favorite synced";
        }
    else
    {
        $status = "failed";
        $message = DEFAULT_NO_RECORDS;
    }


        //To make resource ids as unfavorite
        if($unfavorite_resource_id!=null)
        {
            foreach($unfavorite_resource_id as $unfav_id) {

                $queryCheckFeed = "SELECT id FROM " . $table . " WHERE user_id=".$user_id." AND ".$resource_name_id." =" . $unfav_id;
                //$queryCheckFeed = "SELECT id FROM " . $table . " WHERE user_id=".$user_id." AND id = " . $unfav_id;
                //echo $queryCheckFeed."\n";
                $resultCheckFeed = mysql_query($queryCheckFeed) or $message = mysql_error();

                if (mysql_num_rows($resultCheckFeed) > 0) {

                    $queryUpdate="UPDATE " .$table ." SET is_delete = 0 WHERE user_id=".$user_id." AND ".$resource_name_id."  =" . $unfav_id;
                    $resultUpdate = mysql_query($queryUpdate) or $errorMsg = mysql_error();
                   // echo $queryUpdate;

                    $status = "success";
                    $message = "favorite synced";
                }

            }

        }
        else
        {
            $status = "failed";
            $message = DEFAULT_NO_RECORDS;
        }



        $response['favorite_resource']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
    }
    
    /*
    * followUser
    */
    public function followUser($postData)
    {
    	$message ='';
        $status='';
    	$data = array();
        $response = array();

     	$follower_id = validateObject($postData, 'follower_id', "");
        $follower_id = addslashes($follower_id);
        
        $follow_to = validateObject($postData, 'follow_to', "");
        $follow_to = addslashes($follow_to); 
        
        if($follower_id != NULL &&  $follow_to != NULL)
        {
        	$selectQuery="SELECT * FROM ".TABLE_FOLLOWERS." WHERE follower_id=". $follower_id ." AND follow_to=".$follow_to;
        	$selResult=mysql_query($selectQuery) or  $message=mysql_error();
        
        	if(mysql_num_rows($selResult) > 0)
        	{
				while($row=mysql_fetch_assoc($selResult)){
                    $follow_status=$row['follow_status'];
                }
                if($follow_status=="followed")
                	$follow_status="unfollowed";
                else
                	$follow_status="followed";
                
                //Check for following user
                $queryUpdateStatus="SELECT status FROM ".TABLE_FOLLOWERS." WHERE follower_id=". $follow_to ." AND follow_to=".$follower_id;
                $resultUpdateStatus=mysql_query($queryUpdateStatus) or  $message=mysql_error();
                
                if($resultUpdateStatus)
                {
                	$queryUpdate="UPDATE ".TABLE_FOLLOWERS." SET follow_status='following' WHERE follower_id=". $follow_to ." AND follow_to=".$follower_id;
        			$resultUpdate=mysql_query($queryUpdate) or  $message=mysql_error(); 
                }
                else
                {
        			$queryUpdate="UPDATE ".TABLE_FOLLOWERS." SET follow_status='".$follow_status."' WHERE follower_id=". $follower_id ." AND follow_to=".$follow_to;
        			$resultUpdate=mysql_query($queryUpdate) or  $message=mysql_error();  
        		}
        	}
        	else
        	{
        	 	$insertFields="`follower_id`,`follow_to`";
        	 	$insertValues=$follower_id .",". $follow_to;

       		 	$queryInsert="INSERT INTO ".TABLE_FOLLOWERS."(".$insertFields.") VALUES (".$insertValues.")";
        	 	$resultInsert=mysql_query($queryInsert) or  $message=mysql_error();
        	}
		    	$status = "success";
       	    	$message = "Followship updated";
        }
        else
        {
        		$status = "failed";
        }
        
        $response['user']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
    }
    
    /*
    * getStudentProfile
    */
    public function getStudentProfile($postData)
    {
    	$message ='';
        $status='';
    	$data = array();
    	$post = array();
        $response = array();

     	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        //Check Privacy Setting for Contact and Birthdate Info in User account Prefernce.
        $queryToChkDOBInPreference="SELECT preference_value FROM ".TABLE_USER_ACCOUNT_PREFERENCE." WHERE preference_id=6 AND user_id=".$user_id;
        $resultToChkDOBInPreference=mysql_query($queryToChkDOBInPreference) or  $message=mysql_error();
      	$isShowDOB=mysql_fetch_row($resultToChkDOBInPreference);
       
        $queryToChkContactInPreference="SELECT preference_value FROM ".TABLE_USER_ACCOUNT_PREFERENCE." WHERE preference_id=7 AND user_id=".$user_id;
        $resultToChkContactInPreference=mysql_query($queryToChkContactInPreference) or  $message=mysql_error();
      	$isShowContact=mysql_fetch_row($resultToChkContactInPreference);
       
       
        if((strcasecmp($isShowDOB[0], 'yes') ==0) && (strcasecmp($isShowContact[0], 'yes') == 0))
		{
        	$getUserInfo="users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.birthdate,users.contact_number,users.email_id,users.about_me";
        }
        else if((strcasecmp($isShowDOB[0], 'no')==0) && (strcasecmp($isShowContact[0], 'yes') == 0))
		{
        	$getUserInfo="users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.contact_number,users.email_id,users.about_me";
        }
        else if((strcasecmp($isShowDOB[0], 'yes') ==0) && (strcasecmp($isShowContact[0], 'no') == 0))
		{
        	$getUserInfo="users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.birthdate,users.email_id,users.about_me";
        }
        else if((strcasecmp($isShowDOB[0], 'no') == 0) && (strcasecmp($isShowContact[0], 'no') == 0))
		{
        	$getUserInfo="users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.email_id,users.about_me";
        }
        
        $getStudentProfileInfo="studentProfile.total_score,studentProfile.total_exams,studentProfile.total_badges,studentProfile.total_studymate,studentProfile.total_authors_followed,studentProfile.total_assignment,studentProfile.total_favorite_question,studentProfile.total_question_asked,studentProfile.total_post,studentProfile.rank as 'ism_rank',studentProfile.ambition_in_life";
    	$queryGetInfo="SELECT ".$getUserInfo.",school.school_name,tutorial_group.group_name,course.course_name,classroom.class_name as 'classroom_name',".$getStudentProfileInfo." FROM ".TABLE_STUDENT_PROFILE." studentProfile 
    	INNER JOIN ".TABLE_USERS." users ON users.id=studentProfile.user_id 
    	LEFT JOIN ".TABLE_SCHOOLS." school ON school.id=studentProfile.school_id
    	LEFT JOIN ".TABLE_CLASSROOMS." classroom ON classroom.id=studentProfile.classroom_id
    	LEFT JOIN ".TABLE_COURSES." course ON course.id=studentProfile.course_id
    	LEFT JOIN ".TABLE_TUTORIAL_GROUPS." tutorial_group ON tutorial_group.classroom_id=studentProfile.classroom_id
    	WHERE studentProfile.user_id=".$user_id;
    	//echo $queryGetInfo;
    	$resultGetInfo=mysql_query($queryGetInfo) or  $message=mysql_error();
    	
    	if(mysql_num_rows($resultGetInfo) > 0){
			while($row=mysql_fetch_assoc($resultGetInfo)){
                   $post[]=$row;
                   $post=$post[0];
            } 
            
         //Get Favorite Authors
      	 	$queryGetFavoriteAuthors="SELECT fav_author.id as 'author_id',users.full_name as 'author_name',users.profile_pic as 'author_image' FROM " .TABLE_USER_FAVORITE_AUTHOR." fav_author LEFT JOIN ".TABLE_USERS." users ON fav_author.author_id=users.id WHERE fav_author.author_id=".$user_id;
            $resultGetFavoriteAuthors=mysql_query($queryGetFavoriteAuthors) or  $message=mysql_error();
            $fav_author_array=array();
        	if(mysql_num_rows($resultGetFavoriteAuthors)>0){
            	
				while($authors=mysql_fetch_assoc($resultGetFavoriteAuthors)){
                   $fav_author_array[]=$authors; 
            	}
            }
            $post['favorite_authors']=$fav_author_array;
            
            //Get User Movies
            
            $queryGetFavoriteMovies="SELECT fav_movie.movie_id,movie.movie_name,movie.movie_image FROM " .TABLE_USER_FAVORITE_MOVIE." fav_movie INNER JOIN ".TABLE_MOVIES." movie ON fav_movie.movie_id=movie.id WHERE fav_movie.user_id=".$user_id;
            $resultGetFavoriteMovies=mysql_query($queryGetFavoriteMovies) or  $message=mysql_error();
            $fav_movies_array=array();
        	if(mysql_num_rows($resultGetFavoriteMovies)>0){
            	
				while($authors=mysql_fetch_assoc($resultGetFavoriteMovies)){
                   $fav_movies_array[]=$authors; 
            	}
            }
            $post['user_movies']=$fav_movies_array;
            
             //Get favorite pastime
            
            $queryGetFavoritepastime="SELECT fav_pastime.pastime_id,pastimes.pastime_name,pastimes.pastime_image FROM " .TABLE_USER_FAVORITE_PASTIME." fav_pastime INNER JOIN ".TABLE_PASTIMES." pastimes ON fav_pastime.pastime_id=pastimes.id WHERE fav_pastime.user_id=".$user_id;
            $resultGetFavoritepastime=mysql_query($queryGetFavoritepastime) or  $message=mysql_error();
            $fav_pastime_array=array();
        	if(mysql_num_rows($resultGetFavoritepastime)>0){
            	
				while($authors=mysql_fetch_assoc($resultGetFavoritepastime)){
                   $fav_pastime_array[]=$authors; 
            	}
            }
            $post['favorite_pastime']=$fav_pastime_array;
            
            //Get favorite role_model
            
            $queryGetUserRole_model="SELECT fav_role_model.role_model_id as 'model_id',role_model.model_name,role_model.model_image FROM " .TABLE_USER_ROLE_MODEL." fav_role_model INNER JOIN ".TABLE_ROLE_MODELS." role_model ON fav_role_model.role_model_id=role_model.id WHERE fav_role_model.author_id=".$user_id;
            $resultGetFavoriteUserRole_model=mysql_query($queryGetUserRole_model) or  $message=mysql_error();
            $user_role_model_array=array();
        	if(mysql_num_rows($resultGetFavoriteUserRole_model)>0){
            	
				while($authors=mysql_fetch_assoc($resultGetFavoriteUserRole_model)){
                   $user_role_model_array[]=$authors; 
            	}
            }
            $post['role_model']=$user_role_model_array;
            
       	 	$data[]=$post;
        	$status="success";
        	$message="Request accepted";
    	}
    	 
        else
        {
        	$status = "failed";
        }
        
    	$response['student_profile']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
    }

      public function manageBookLibrary($postData)
      {
          $data = array();
          $response = array();

          $user_id = validateObject($postData, 'user_id', "");
          $user_id = addslashes($user_id);

          $add_book_id = validateObject($postData, '$add_book_id', "");

          $remove_book_id = validateObject($postData, '$remove_book_id', "");

          if($add_book_id!=null)
          {
              foreach($add_book_id as $book_id) {

                  $queryCheckFeed = "SELECT * FROM " . TABLE_USER_LIBRARY . " WHERE user_id=" . $user_id . " AND book_id="  . $book_id;
                  //echo $queryCheckFeed."\n";
                  $resultCheckFeed = mysql_query($queryCheckFeed) or $message = mysql_error();

                  if (mysql_num_rows($resultCheckFeed) == 0) {

                      $insertFields = "`user_id`,`book_id`";
                      $insertValues = $user_id . "," . $book_id;

                      $query = "INSERT INTO " . TABLE_USER_LIBRARY . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                      $result = mysql_query($query) or $message = mysql_error();

                      $status = "success";
                      $message = "library synced";
                  }
                  else
                  {
                      $status="success";
                      $message=DEFAULT_NO_RECORDS;
                  }
              }

          }
          else
          {
              $status="failed";
              $message=DEFAULT_NO_RECORDS;
          }


          if($remove_book_id!=null)
          {
              foreach($remove_book_id as $book_id) {

                  $queryCheckFeed = "SELECT * FROM " . TABLE_USER_LIBRARY . " WHERE user_id=" . $user_id . " AND book_id="  . $book_id;
                  //echo $queryCheckFeed."\n";
                  $resultCheckFeed = mysql_query($queryCheckFeed) or $message = mysql_error();

                  if (mysql_num_rows($resultCheckFeed) > 0) {

                      $queryUpdate="UPDATE " .TABLE_USER_LIBRARY ." SET is_delete = 1 WHERE user_id=".$user_id." AND book_id="  . $book_id;
                      $resultUpdate = mysql_query($queryUpdate) or $errorMsg = mysql_error();
                      // echo $queryUpdate;

                      $status = "success";
                      $message = "library synced";
                  }
                  else
                  {
                      $status="success";
                      $message=DEFAULT_NO_RECORDS;
                  }
              }

          }
          else
          {
              $status="failed";
              $message=DEFAULT_NO_RECORDS;
          }



          $response['student_profile']=$data;
          $response['status']=$status;
          $response['message']=$message;

          return $response;
      }
}
?>