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
include_once 'ApiCrypter.php';

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
                return $this->manageFavorite($postData);
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

            case "GetBlockedUser":
            {
                return $this->getBlockedUser($postData);
            }
                break;

            case "GetBooksByAuthors":
            {
                return $this->getBooksByAuthors($postData);
            }
                break;

            case "RefreshToken":
            {
                return $this->refreshToken($postData);
            }
                break;

            case "GetAdminConfig":
            {
                return $this->getAdminConfig($postData);
            }
                break;

            case "UnBlockUser":
            {
                return $this->unBlockUser($postData);
            }
                break;

            case "UploadProfileImages":
            {
                return $this->uploadProfileImages($postData);
            }
                break;

            case "GetMyFollowers":{
                return $this->getMyFollowers($postData);
            }
                break;

            case "GetRecommendedAuthors":
            {
                return $this->getRecommendedAuthors($postData);
            }
            break;

            case "GetReportData":
            {
                return $this->getReportData($postData);
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $queryFrom = TABLE_STUDENT_PROFILE . " academicInfo INNER JOIN " . TABLE_CLASSROOMS . " classroom INNER JOIN " . TABLE_SCHOOLS . " schools INNER JOIN " . TABLE_COURSES . "  courses INNER JOIN " . TABLE_SCHOOL_CLASSROOM . "  school_classroom ";
            $queryGetFields = "school_classroom.classroom_name,academicInfo.school_id,academicInfo.classroom_id,academicInfo.academic_year,academicInfo.course_id,schools.school_name,classroom.class_name,courses.course_name";
            $queryOn = "academicInfo.school_id=school_classroom.school_id and academicInfo.classroom_id=school_classroom.classroom_id and academicInfo.school_id=schools.id and academicInfo.classroom_id=classroom.id and academicInfo.course_id=courses.id";
            $query = "SELECT ".$queryGetFields. " FROM ".$queryFrom ." on ".$queryOn." where user_id =" . $user_id ." and academicInfo.is_delete=0 and schools.is_delete=0 and courses.is_delete=0 and school_classroom.is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            //  echo $query;
            //echo $message;
            if (mysqli_num_rows($result)) {
                $val = mysqli_fetch_assoc($result);
                $data['school_id'] = $val['school_id'];
                $data['school_name'] = $val['school_name'];
                $data['course_id'] = $val['course_id'];
                $data['course_name'] = $val['course_name'];
                $data['academic_year'] = $val['academic_year'];
                $data['classroom_id'] = $val['classroom_id'];
                $data['classroom_name'] = $val['class_name'];
                $data['class_division'] = $val['classroom_name'];
                $status = SUCCESS;
                //$message="";
            } else {
                $status = SUCCESS;
                $message=DEFAULT_NO_RECORDS;

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $sendEmail = new SendEmail();
            $randomString = gen_random_string();

            $status = 0;
            $queryCheckEmail = "SELECT * FROM ".TABLE_USERS." WHERE `email_id`='".$email_id."' and is_delete=0";
            $resultCheckEmail = mysqli_query($GLOBALS['con'], $queryCheckEmail) or $message = mysqli_error($GLOBALS['con']);
            // echo $queryCheckEmail;
            if (mysqli_num_rows($resultCheckEmail)) {
                while ($val = mysqli_fetch_assoc($resultCheckEmail)) {
                    $status = 1;
                }
            } else {

                $status = 0;

            }
            //$message="Hello ISM,\nI am very much interested to be part of ISM system.Please check my details below and let me know how can I become the part of this system.
            if ($status == 1) {
                $message = "Successfully sent";
                $status = SUCCESS;
                $response = $sendEmail->sendemail("ism.educare@gmail.com", $randomString, "Forgot Password", $email_id);
            } else {
                $message = "Email id is not valid!";
                $status = FAILED;
            }
            // return "Request sent successfully'";

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure != no) {

            if ($username != null) {
                $queryUserName = "SELECT `username` FROM ".TABLE_USERS." WHERE `username`='".$username."'  and is_delete=0";
                $resultUserName = mysqli_query($GLOBALS['con'], $queryUserName) or $message = mysqli_error($GLOBALS['con']);
                if (mysqli_num_rows($resultUserName)) {
                    while ($val = mysqli_fetch_assoc($resultUserName)) {
                        regenerate:
                        {
                            $username = $val['username'];
                            $randomNumber = rand(0, 999);
                            $username .= $randomNumber;
                            //echo "\n".$username."i=";
                            $queryGenUserName = "SELECT `username` FROM " . TABLE_USERS . " WHERE `username`='" . $username . "'";
                            $resultGenUserName = mysqli_query($GLOBALS['con'], $queryGenUserName) or $message = mysqli_error($GLOBALS['con']);
                            if (mysqli_num_rows($resultGenUserName)) {
                                goto regenerate;
                            } else {
                                //	echo "\n".$username."i=else=";}
                                $status = SUCCESS;
                                $message = "username is not available.";
                                $data['username'] = $username;
                            }
                        }
                    }
                } else {
                    $status = SUCCESS;
                    $message = "username is available.";
                }
            } else {
                $status = FAILED;
                $message = "Invalid data.";
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $queryState = "SELECT `state_name` FROM `states` WHERE `id`=" . $state_id ." and is_delete=0";
            $resultState = mysqli_query($GLOBALS['con'], $queryState) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultState)) {
                while ($val = mysqli_fetch_assoc($resultState)) {
                    $state_name = $val['state_name'];
                }
            } else {
                $state_name = DEFAULT_NO_RECORDS;
            }

            $queryCountry = "SELECT `country_name` FROM `countries` WHERE `id`=".$country_id ." and is_delete=0";
            $resultCountry = mysqli_query($GLOBALS['con'], $queryCountry) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultCountry)) {
                while ($val = mysqli_fetch_assoc($resultCountry)) {
                    $country_name = $val['country_name'];
                }
            } else {
                $country_name = DEFAULT_NO_RECORDS;
            }

            $queryCity = "SELECT `city_name` FROM `cities` WHERE `id`=".$city_id." and is_delete=0";
            $resultCity = mysqli_query($GLOBALS['con'], $queryCity) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultCity)) {
                while ($val = mysqli_fetch_assoc($resultCity)) {
                    $city_name = $val['city_name'];
                }
            } else {
                $country_name = DEFAULT_NO_RECORDS;
            }
            $data['firstname'] = $firstname;
            $data['lastname'] = $lastname;
            $data['home_address'] = $home_address;
            $data['contact_number'] = $contact_number;
            $data['email_address'] = $email_address;
            $data['school_name'] = $school_name;
            $data['city_id'] = $city_id;
            $data['state_id'] = $state_id;
            $data['country_id'] = $country_id;

            $sendEmail = new SendEmail();
            $message = "Hello ISM,\nI am very much interested to be part of ISM system.Please check my details below and let me know how can I become the part of this system.
     	\nFullname: " . $firstname . " " . $lastname .
                "\nHome Address: " . $home_address .
                "\nContact number: " . $contact_number .
                "\nEmail address: " . $email_address .
                "\nSchool name: " . $school_name .
                "\nCity: " . $city_name .
                "\nState: " . $state_name .
                "\nCountry: " . $country_name .
                "\n\nI am waiting for your call.
		\nThanks.";
            $status = SUCCESS;
            $sendEmail->sendemail($email_address, $message, "Request For Credentials", "ism.educare@gmail.com");
            //  $response['status'] =$status;
        $message="Sent successfully";

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['status'] =$status;
        $response['message'] =$message;
        $response['request_credentials']=$post;
        return $response;

    }

    public function RequestForSchoolInfoUpdation ($postData)
    {
        $message = '';
        $post = array();
        $response = array();
        $email_address = validateObject($postData, 'email_address', "");
        $email_address = addslashes($email_address);

        $message = validateObject($postData, 'message', "");
        $message = addslashes($message);

        $name = validateObject($postData, 'name', "");
        $name = addslashes($name);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security = new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key, $secret_key);

        if ($isSecure == yes) {

        $sendEmail = new SendEmail();
        $email_message = "Hello, \n" . $message . "\n\n Thank You \n " . $name;
            $status= SUCCESS;
        $sendEmail->sendemail($email_address, $email_message, "Request For Wrong School Details ", "ism.educare@gmail.com");
        //  $response['status'] =$status;
        $message="Sent successfully";;

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['status'] = $status;
        $response['message'] =$message;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure != no) {

           $queryUser = "SELECT id,username,password,profile_pic,full_name from ".TABLE_USERS." where username='".$username."' and is_delete=0";
            // $encrypted_passwd = $obj->encode($password);
            //$decrypted_password = $obj->decode("vxbhjXDuOZ8uncgNP7ykB2UvgLr5Q9SU31K6z+JGMYfREqZTYyr1f5E20k7jMTxNILaWMK0ImrNVS1GGn6gshA==");
            //echo "---------".$obj->decode("v8R/H5JqnMdmkqVWyYLr7a/z46844fI8otkn17Ba+Afd5eOTjH9uJRg0X5nHW6EAcAQP4QNhvbNWmfgqlzLXew==");
            $resultUser = mysqli_query($GLOBALS['con'], $queryUser) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultUser)) {
                $encryptedPassword = '';
                while ($val = mysqli_fetch_assoc($resultUser)) {
                    //echo $obj->encode($password);
                    $encryptedPassword = $val['password'];
                   // echo $encryptedPassword;
                    $decrypted_password = $obj->decode($encryptedPassword);
                    //echo $decrypted_password;
                    if ($decrypted_password == $password) {

                        $post = array();
                        $message = CREDENTIALS_EXITST;
                        $post['user_id'] = $val['id'];
                        $post['full_name'] = $val['full_name'];
                        $post['profile_pic'] = $val['profile_pic'];
                        $status = SUCCESS;

                        $tutorialGroupClass = new TutorialGroup();
                        $tutorialGroup = $tutorialGroupClass->call_service("GetTutorialGroupOfUser", $post['user_id']);

                        if ($tutorialGroup['tutorial_group_found']) {
                            $groupData = $tutorialGroup['tutorial_group'];
                            $post['tutorial_group_id'] = $groupData['tutorial_group_id'];
                            $post['tutorial_group_joining_status'] = $groupData['tutorial_group_joining_status'];
                            $post['tutorial_group_complete'] = $groupData['tutorial_group_complete'];
                            $post['tutorial_group_name'] = $groupData['tutorial_group_name'];
                            $post['tutorial_group_members'] = $groupData['tutorial_group_members'];
                        }

                    } else {
                        $status = FAILED;
                        $message = CREDENTIALS_DO_NOT_EXIST_IN_OUR_SYSTEM;
                    }
                }
            } else {
                //$encryptedPassword=encryptPassword($passowrd);
                $queryOn = "autoGenerateCredential.school_id=schools.id and autoGenerateCredential.course_id=courses.id";
                $queryData = "SELECT * FROM ".TABLE_AUTO_GENERATED_CREDENTIAL." autoGenerateCredential where username='".$username."' and is_delete=0";
                //$queryAuthUser="select * from ".TABLE_AUTO_GENERATED_CREDENTIAL." where username='".$userName."'";
                $resultAuthUser = mysqli_query($GLOBALS['con'], $queryData) or $message = mysqli_error($GLOBALS['con']);
                if (mysqli_num_rows($resultAuthUser)) {
                    $encryptedPassword = '';
                    while ($val = mysqli_fetch_assoc($resultAuthUser)) {
                        $username = $val['username'];
                        $encryptedPassword = $val['password'];
                        $status = $val['status'];
                        $id = $val['id'];
                        $decrypted_password = $obj->decode($encryptedPassword);

                        if ($decrypted_password == $password) {

                            if ($status == 1) {
                                //$status=$status;
                                $message = CREDENTIALS_ARE_DEACTIVATED_OR_ALREADY_TAKEN;
                            } else if ($status == 0) {
                                $post = array();
                                //$queryData="SELECT * FROM `auto_generated_credential` t1 INNER JOIN `schools`t2 INNER JOIN `courses`t3 ON t1.school_id=t2.id and t1.course_id=t3.id where `username`='WJL91RU473'";
                                 $post['credential_id'] = $val['id'];
                                 $post['role_id'] = $val['role_id'];
                                 $post['academic_year'] = $val['academic_year'];

                                $geSchool="SELECT school.school_name,school.school_type,district.district_name FROM ".TABLE_SCHOOLS." school JOIN ".TABLE_DISTRICTS." district ON school.district_id=district.id WHERE school.id=".$val['school_id'];
                                $resultToGetSchool=mysqli_query($GLOBALS['con'], $geSchool) or $message = mysqli_error($GLOBALS['con']);
                                $rowToGetSchoolName=mysqli_fetch_row($resultToGetSchool);

                                $post['school_id'] = $val['school_id'];
                                $post['school_name'] = $rowToGetSchoolName[0];
                                $post['school_type'] = $rowToGetSchoolName[1];
                                $post['district_name'] = $rowToGetSchoolName[2];

                                $geCourse="SELECT course_name FROM ".TABLE_COURSES." WHERE id=".$val['course_id'];
                                $resultToGetCourse=mysqli_query($GLOBALS['con'], $geCourse) or $message = mysqli_error($GLOBALS['con']);
                                $rowToGetCourseName=mysqli_fetch_row($resultToGetCourse);
                                $post['course_id'] = $val['course_id'];
                                $post['course_name'] = $rowToGetCourseName[0];

                                $geClassroom="SELECT class_name FROM ".TABLE_CLASSROOMS." WHERE id=".$val['course_id'];
                                $resultToGetClassRoom=mysqli_query($GLOBALS['con'], $geClassroom) or $message = mysqli_error($GLOBALS['con']);
                                $rowToGetClassroomName=mysqli_fetch_row($resultToGetClassRoom);
                                $post['class_id'] = $val['classroom_id'];
                                $post['class_name'] = $rowToGetClassroomName[0];


                                $status = SUCCESS;
                                $message = CREDENTIALS_EXITST;

                            }
                        } else {
                            // $post="";
                            $status = FAILED;
                            $message = CREDENTIALS_DO_NOT_EXIST_IN_OUR_SYSTEM;
                        }

                    }
                } else {
                    // $post="";
                    $status = FAILED;
                    $message = CREDENTIALS_DO_NOT_EXIST_IN_OUR_SYSTEM;
                }

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        //$isSecure = $security->checkForSecurity($access_key,$secret_key);
        $isSecure = $security->checkForSecurityTest($access_key,$secret_key);
        //echo $isSecure; exit;
        if ($isSecure != no) {

            $obj = new CI_Encrypt();

            $firstName=ucfirst(strtolower($firstname));
            $lastName=ucfirst(strtolower($lastname));

            $insertFields = "`username`, `password`,`device_type`, `first_name`, `last_name`, `full_name`,`email_id`, `contact_number`, `home_address`, `city_id`, `state_id`, `country_id`, `birthdate`, `gender`, `device_token`, `role_id`";
            $insertValues = "'" . $username . "','" . $obj->encode($password) . "','" . $device_type . "','" . $firstName . "','" . $lastName . "','" . $firstName . " " . $lastName . "','" . $email_address . "','" . $contact_number . "','" . $home_address . "','" . $city_id . "','" . $state_id . "','" . $country_id . "','" . $birthdate . "','" . $gender . "','" . $device_token . "','" . $role_id . "'";

            $queryInsert = "INSERT INTO " . TABLE_USERS . "(" . $insertFields . ") values(" . $insertValues . ")";
            $resultQuery = mysqli_query($GLOBALS['con'], $queryInsert) or $message = mysqli_error($GLOBALS['con']);
            if($resultQuery) {
                $user_id = mysqli_insert_id($GLOBALS['con']);
                if ($role_id == 2) {
                    $course_id = validateObject($postData, 'course_id', "");
                    $course_id = addslashes($course_id);

                    $academic_year = validateObject($postData, 'academic_year', "");
                    $academic_year = addslashes($academic_year);

                    $classroom_id = validateObject($postData, 'classroom_id', "");
                    $classroom_id = addslashes($classroom_id);

                    $school_id = validateObject($postData, 'school_id', "");
                    $school_id = addslashes($school_id);

                    $school_classroom_id = validateObject($postData, 'school_classroom_id', "");
                    $school_classroom_id = addslashes($school_classroom_id);


                    $insertAcademicField = "`user_id`, `school_id`, `classroom_id`, `school_classroom_id`, `academic_year`, `joining_year`, `joining_class`, `course_id`";
                    $insertAcademicValue = "'" . $user_id . "', '" . $school_id . "','" . $classroom_id . "','" . $school_classroom_id . "','" . $academic_year . "','" . date('M,Y') . "','" . $classroom_id . "','" . $course_id . "'";

                    echo $queryAcademic = "INSERT INTO " . TABLE_STUDENT_PROFILE . "(" . $insertAcademicField . ") values (" . $insertAcademicValue . ")";
                    $resultAcademic = mysqli_query($GLOBALS['con'], $queryAcademic) or $message = mysqli_error($GLOBALS['con']);

                } else if ($role_id == 3) {
                    $specialization = validateObject($postData, 'specialization', "");
                    $specialization = addslashes($specialization);

                    $education = validateObject($postData, 'education', "");
                    $education = addslashes($education);

                    $school_id = validateObject($postData, 'school_id', "");
                    $school_id = addslashes($school_id);

                    $insertTeacherField = "`user_id`, `specialization`, `education`";
                    $insertTeacherValue = "'" . $user_id . "','" . $specialization . "','" . $education . "'";

                    $queryTeacher = "INSERT INTO " . TABLE_TEACHER_PROFILE . "(" . $insertTeacherField . ") values (" . $insertTeacherValue . ")";
                    $resultTeacher = mysqli_query($GLOBALS['con'], $queryTeacher) or $message = mysqli_error($GLOBALS['con']);


                } else if ($role_id == 4) {

                    $education = validateObject($postData, 'education', "");
                    $education = addslashes($education);

                    $insertAuthorField = "`user_id`, `education`";
                    $insertAuthorValue = "'" . $user_id . "', '" . $education . "'";

                    $queryAuthor = "INSERT INTO " . TABLE_AUTHOR_PROFILE . "(" . $insertAuthorField . ") values (" . $insertAuthorValue . ")";
                    $resultAuthor = mysqli_query($GLOBALS['con'], $queryAuthor) or $message = mysqli_error($GLOBALS['con']);

                }


                $crypterSecurity = new Security();
                $queryToCheckRecordExist = "SELECT * FROM " . TABLE_TOKENS . " WHERE user_id=" . $user_id . " AND is_delete=0";
                $resultToCheckRecordExist = mysqli_query($GLOBALS['con'], $queryToCheckRecordExist) or $message = mysqli_error($GLOBALS['con']);
                //echo $queryToCheckRecordExist;
                if (mysqli_num_rows($resultToCheckRecordExist) > 0) {

                    $rowRecord = mysqli_fetch_row($resultToCheckRecordExist);
                    $tokenName = $rowRecord[2];
                    $tokenName = $crypterSecurity->encrypt($tokenName, $username);
                    $post['token_name'] = $tokenName;

                    $message="Token already exist";
                    $status=SUCCESS;

                } else {

                    //Generate Token
                    $security = new SecurityFunctions();
                    $generateToken = $security->generateToken(8);


                    $insertTokenField = "`user_id`, `token`";
                    $insertTokenValue = "" . $user_id . ",'" . $generateToken . "'";

                    $queryAddToken = "INSERT INTO " . TABLE_TOKENS . "(" . $insertTokenField . ") values (" . $insertTokenValue . ")";
                    $resultAddToken = mysqli_query($GLOBALS['con'], $queryAddToken) or $message = mysqli_error($GLOBALS['con']);

                    //**************************For Encryption***************************

                    $secret_key = $crypterSecurity->encrypt($generateToken, $username);
                    //**************************End Encryption***************************

                    if ($resultAddToken) {
                        $post['token_name'] = $secret_key;
                        $status = SUCCESS;
                        $message = "Token key generated.";
                    } else {
                        $status = FAILED;
                    }
                }

                if($resultAcademic || $resultAuthor || $resultTeacher)
                {
//                    $updateStatus="UPDATE `auto_generated_credential` SET `status`='1' WHERE `id`=".$credential_id;
//                    $resultAcademic=mysqli_query($GLOBALS['con'], $updateStatus) or $message=mysqli_error($GLOBALS['con']);
                    // echo $updateStatus;
                    $post['user_id']=$user_id;
                    $post['full_name']=$firstname." ".$lastname;
                    //$post['profile_pic']=$profile_image_link;
                    $data[]=$post;
                    $status=SUCCESS;
                    $message="Registration completed successfully";

                }
                ELSE{
                    $status=FAILED;
                }
                //}
            }
            else {
                $status = FAILED;

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            $queryGetSummary = "SELECT user_id,wallet_balance FROM " . TABLE_STUDENT_PROFILE ." WHERE user_id =".$user_id ." and is_delete=0";
            $resultGetSummary = mysqli_query($GLOBALS['con'], $queryGetSummary) or $message = mysqli_error($GLOBALS['con']);
            $summary_count = mysqli_num_rows($resultGetSummary);

            if ($summary_count > 0) {
                while ($val = mysqli_fetch_assoc($resultGetSummary)) {

                    $post = array();

                    $post['user_id'] = $val['user_id'];
                    $post['wallet_balance'] = $val['wallet_balance'];

                    //Get Voucher Details
                    $queryGetDetails = "SELECT id as 'voucher_id',voucher_code,created_date,voucher_amount as 'voucher_amount' FROM ".TABLE_USER_VOUCHER." WHERE user_id=".$user_id ." and is_delete=0";
                    $resultGetDetails = mysqli_query($GLOBALS['con'], $queryGetDetails) or $message = mysqli_error($GLOBALS['con']);
                    $allDetails = array();

                    if (mysqli_num_rows($resultGetDetails)) {
                        while ($details = mysqli_fetch_assoc($resultGetDetails)) {
                            $allDetails[] = $details;
                        }
                    }
                    $post['voucher_details'] = $allDetails;

                    $data[] = $post;
                }
                $status = SUCCESS;
                $message = "";
            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
                $data = "";
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            $queryGetWalletBal = "select wallet_balance from ".TABLE_STUDENT_PROFILE." where user_id=".$user_id ." and is_delete=0";
            $resultGetWalletBal = mysqli_query($GLOBALS['con'], $queryGetWalletBal) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultGetWalletBal) > 0) {
                $walletBalance = mysqli_fetch_row($resultGetWalletBal);
                $balance = $walletBalance[0];

                $queryGetConfigValue = "select config_value from ".TABLE_ADMIN_CONFIG." where config_key='maxVoucherAmount' and is_delete=0";
                $resultGetConfigValue = mysqli_query($GLOBALS['con'], $queryGetConfigValue) or $message = mysqli_error($GLOBALS['con']);

                $configValue = mysqli_fetch_row($resultGetConfigValue);
                $voucher_config_value = $configValue[0];

                $percent_value = (($balance*$voucher_config_value)/100);


                //Generate random String for Voucher Code

                $security=new SecurityFunctions();
                $final_rand=$security->generateToken(6);


                //Check, if Voucher_amont is greater than Count value or not
                if ($voucher_amount < $percent_value) {
                    $insertFields = "`user_id`,`voucher_code`,`voucher_amount`";
                    $insertValues = "" . $user_id . ",'" . $final_rand . "'," . $voucher_amount . "";

                    $queryInsert = "INSERT INTO " . TABLE_USER_VOUCHER . "(" . $insertFields . ") values(" . $insertValues . ")";
                    //echo $queryInsert;
                    $resultQuery = mysqli_query($GLOBALS['con'], $queryInsert) or $message = mysqli_error($GLOBALS['con']);

                    $voucher_id=mysqli_insert_id($GLOBALS['con']);

                    //Get Crete date
                    $querySelect="SELECT created_date FROM ".TABLE_USER_VOUCHER." WHERE id = ".$voucher_id." and is_delete=0";
                    $resultSelect=mysqli_query($GLOBALS['con'],$querySelect) or $message = mysqli_error($GLOBALS['con']);
                    $create_date=mysqli_fetch_row($resultSelect);


                    //For getting remaining balance
                    $remaining_wallet_bal = $balance - $voucher_amount;
                    $queryUpdateWalletBalance="UPDATE ".TABLE_STUDENT_PROFILE." SET wallet_balance = ".$remaining_wallet_bal." WHERE user_id=".$user_id ." and is_delete=0";
                    $resultUpdateWalletBalance=mysqli_query($GLOBALS['con'], $queryUpdateWalletBalance) or $message = mysqli_error($GLOBALS['con']);


                    if ($resultQuery || $resultUpdateWalletBalance) {
                        $data['voucher_code'] = $final_rand;
                        $data['voucher_amount'] = $voucher_amount;
                        //$post['created_date']=date('Y-m-d h:i:s');
                        $post['created_date']=$create_date[0];
                        $data[]=$post;
                        $data1['wallet_balance']=$remaining_wallet_bal;
                        $data1['voucher_details']=$data;
                        $status=SUCCESS;
                    } else {
                        $status = FAILED;
                    }
                } else {
                    $message = "You cannot create coupon greater then " . $percent_value;
                    $status = FAILED;

                }
            } else {
                $status = SUCCESS;
                $message=DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['wallet_summary'][]=$data1;
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

        $role_id = validateObject($postData, 'role_id', "");
        $role_id = addslashes($role_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            if($role_id==2)
            {
                $selectQuery="SELECT school.school_name,classroom.class_name,student_profile.*,users.id,users.full_name,users.profile_pic,users.contact_number,users.birthdate,users.about_me
        FROM ".TABLE_USERS." users INNER JOIN ".TABLE_STUDENT_PROFILE." student_profile ON users.id=student_profile.user_id
        INNER JOIN ".TABLE_SCHOOLS." school ON school.id=student_profile.school_id
        INNER JOIN ".TABLE_CLASSROOMS." classroom ON classroom.id=student_profile.classroom_id
        WHERE student_profile.user_id=".$user_id." and student_profile.is_delete=0 and users.is_delete=0 and school.is_delete=0 and classroom.is_delete=0";
            }
            elseif($role_id==4)
            {
                $selectQuery="SELECT author_profile.*,users.id,users.full_name,users.profile_pic,users.contact_number,users.birthdate,users.about_me
        FROM ".TABLE_USERS." users INNER JOIN ".TABLE_AUTHOR_PROFILE." author_profile ON users.id=author_profile.user_id
        WHERE author_profile.user_id=".$user_id." and author_profile.is_delete=0 and users.is_delete=0 ";
            }

            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    $post = array();
                    $post['user_id'] = $val['id'];
                    $post['username'] = $val['full_name'];
                    $post['profile_pic'] = $val['profile_pic'];

                    $post['contact_number'] = $val['contact_number'];
                    $post['birthdate'] = $val['birthdate'];

                    if($role_id==2) {
                        $post['school_name'] = $val['school_name'];
                        $post['course_name'] = $val['class_name'];
                        $post['aboutMeText'] = $val['about_me'];
                        $post['ambitionInLife']=$val['ambition_in_life'];
                        $post['total_post'] = $val['total_posts'];
                        $post['total_studymates'] = $val['total_studymates'];
                        $post['total_authors_followed'] = $val['total_authors_followed'];
                        $post['ism_score'] = $val['total_score'];
                        $post['ism_rank'] = $val['rank'];
                        $post['total_exams'] = $val['total_exams'];
                        $post['total_assignment'] = $val['total_assignment'];
                        $post['total_question_asked'] = $val['total_questions_asked'];
                        $post['total_favorite_questions'] = $val['total_favorite_questions'];
                        $post['total_badges_earned'] = $val['total_badges'];
                    }
                    if($role_id==4)
                    {
                        $post['education'] = $val['education'];
                        $post['about_author'] = $val['about_author'];
                        $post['total_questions_answered'] = $val['total_questions_answered'];
                        $post['total_assignment'] = $val['total_assignment'];
                        $post['total_exam'] = $val['total_exam'];
                        $post['total_posts'] = $val['total_posts'];
                        $post['total_following'] = $val['total_following'];
                        $post['total_followers'] = $val['total_followers'];
                        $post['total_favorite_question'] = $val['total_favorite_question'];
                        $post['total_books'] = $val['total_books'];
                        $post['total_badges'] = $val['total_badges'];

                    }
                    $data[] = $post;
                }
                $message = REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $status = SUCCESS;
                $message=DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

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

            $queryToChk="select * from ".TABLE_USERS." where id=".$user_id ." and is_delete=0";
            $resultToChk=mysqli_query($GLOBALS['con'], $queryToChk) or $message=mysqli_error($GLOBALS['con']);

            if(mysqli_num_rows($resultToChk)>0) {

                $updateField = "full_name='" . $username . "', profile_pic='" . $profile_image . "', contact_number='" . $contact_number . "', birthdate='" . $birthdate . "', about_me='" . $aboutMeText . "'";

                $queryUpdate = "UPDATE " . TABLE_USERS . " SET " . $updateField . " WHERE id=" . $user_id;
                $resultQuery = mysqli_query($GLOBALS['con'], $queryUpdate) or $message = mysqli_error($GLOBALS['con']);

                if ($resultQuery) {
                    $status = SUCCESS;
                    $message = SUCCESSFULLY_UPDATED;

                    if ($ambitionInLife != NULL) {
                        $queryToUpdateAmbition = "UPDATE " . TABLE_STUDENT_PROFILE . " SET ambition_in_life='" . $ambitionInLife . "' WHERE user_id=" . $user_id;
                        $resultToUpdateAmbition = mysqli_query($GLOBALS['con'], $queryToUpdateAmbition) or $message = mysqli_error($GLOBALS['con']);

                        if ($resultToUpdateAmbition) {
                            $status = SUCCESS;
                            $message = SUCCESSFULLY_UPDATED;
                        }
                    }

                } else {
                    $status = FAILED;
                }
            }
            else
            {
                $status = SUCCESS;
                $message=DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['user']=$data;
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
        $post=array();
        $post1=array();
        $post2=array();
        $post3=array();
        $post4=array();
        $post5=array();
        $post6=array();
        $post7=array();


    	$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            $selectQuery="SELECT user_activity.user_id,users.full_name,users.profile_pic,user_activity.display_content,user_activity.resource_id,user_activity.activity_type,user_activity.created_date FROM ".TABLE_USER_ACTIVITY. " user_activity
        INNER JOIN ".TABLE_USERS." users ON user_activity.user_id=users.id WHERE user_activity.user_id=".$user_id." AND user_activity.is_delete=0 and users.is_delete=0 order by user_activity.activity_type";
          //echo $selectQuery; exit;
            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);


            if (mysqli_num_rows($resultQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                   // $post['user_id'] = $val['user_id'];
                  //  $post['full_name'] = $val['full_name'];
                   // $post['profile_pic'] = $val['profile_pic'];
                    //$post['display_content'] = $val['display_content'];
                    //$post['resource_id'] = $val['resource_id'];

//print_r($val);
                    if($val['activity_type'] == 'topic')
                    {
                        $post1['activity_type'] = $val['activity_type'];
                        $post1['activity_time'] = $val['created_date'];
                        /*$getFields="tutorial_group_member_score.topic_id,tutorial_group_member_score.score,tutorial_group_member_score.total_comments,tutorial_groups.group_name";

                        $queryToGetTopic="SELECT DISTINCT ".$getFields." FROM ". TABLE_TUTORIAL_GROUP_MEMBER_SCORE." tutorial_group_member_score
                        INNER JOIN(SELECT tutorial_group_topic_allocation.topic_id,tutorial_group_topic_allocation.group_id,tutorial_group_member.user_id,tutorial_group_member.id  FROM ". TABLE_TUTORIAL_GROUP_MEMBER ." tutorial_group_member
                        INNER JOIN  ". TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION ." tutorial_group_topic_allocation ON tutorial_group_member.group_id=tutorial_group_topic_allocation.group_id WHERE tutorial_group_member.user_id=".$user_id.")s1
                        ON s1.topic_id=tutorial_group_member_score.topic_id and tutorial_group_member_score.member_id =s1.id JOIN ". TABLE_TUTORIAL_GROUPS." tutorial_groups ON tutorial_groups.id=s1.group_id WHERE tutorial_group_member_score.topic_id=".$val['resource_id'];
                       // echo $queryToGetTopic; exit;*/



                        $getFields="tutorial_group_member_score.topic_id,tutorial_group_member_score.score,tutorial_group_member_score.total_comments,,tutorial_topics.topic_name";

                        $queryToGetTopic="SELECT DISTINCT ".$getFields." FROM ". TABLE_TUTORIAL_GROUP_MEMBER_SCORE." tutorial_group_member_score
                        INNER JOIN(SELECT tutorial_group_topic_allocation.topic_id,tutorial_group_topic_allocation.group_id,tutorial_group_member.user_id,tutorial_group_member.id  FROM ". TABLE_TUTORIAL_GROUP_MEMBER ." tutorial_group_member
                        INNER JOIN  ". TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION ." tutorial_group_topic_allocation ON tutorial_group_member.group_id=tutorial_group_topic_allocation.group_id WHERE tutorial_group_member.user_id=".$user_id.")s1
                        ON s1.topic_id=tutorial_group_member_score.topic_id and tutorial_group_member_score.member_id =s1.id JOIN ". TABLE_TUTORIAL_TOPIC." tutorial_topics ON tutorial_topics.id=s1.topic_id WHERE tutorial_group_member_score.topic_id".$val['resource_id'];
                        // echo $queryToGetTopic; exit;
                        $resultToGetTopic=mysqli_query($GLOBALS['con'], $queryToGetTopic);

                        $topic_array=array();
                        if(mysqli_num_rows($resultToGetTopic)) {
                            while ($topicRow = mysqli_fetch_assoc($resultToGetTopic)) {
                                //$topic_array[]=$topicRow;

                                $topic_array['topic_id']=$topicRow['topic_id'];
                                $topic_array['topic_name']=$topicRow['topic_name'];
                                $topic_array['score']=$topicRow['score'];
                                $topic_array['discussion_comment']=$topicRow['total_comments'];
                               // $post['topic_allocated']=$topic_array;

                            }

                            $post1['topic_allocated']=$topic_array;
                            $data[]=$post1;
                        }

                        /*else
                        {
                            $post['topic_allocated']=array();
                        }*/
                    }

                   elseif($val['activity_type'] == 'assignment')
                    {
                        $post2['activity_type'] = $val['activity_type'];
                        $post2['activity_time'] = $val['created_date'];

                        $selectAssignment="SELECT assignment_submission.assignment_id,assignment.assignment_name,assignment.submission_date,subjects.subject_name from ".TABLE_ASSIGNMENT_SUBMISSION." assignment_submission
                        INNER JOIN ".TABLE_ASSIGNMENTS." assignment ON assignment_submission.assignment_id=assignment.id
                        INNER JOIN ". TABLE_SUBJECTS." subjects ON subjects.id=assignment.subject_id
                        WHERE assignment_submission.assignment_id=".$val['resource_id']." AND assignment_submission.user_id=".$user_id." assignment.is_delete=0";
                        $resultAssignment=mysqli_query($GLOBALS['con'], $selectAssignment);

                        $assignment=array();
                        if(mysqli_num_rows($resultAssignment))
                        {
                            while($assignmentRow=mysqli_fetch_assoc($resultAssignment))
                            {
                                //$assignment[]=$assignmentRow;
                                $assignment['assignment_id']=$assignmentRow['assignment_id'];
                                $assignment['assignment_name']=$assignmentRow['assignment_name'];
                                $assignment['submission_date']=$assignmentRow['submission_date'];
                                $assignment['subject_name']=$assignmentRow['subject_name'];

                            }
                            $post2['assignmentSubmitted']=$assignment;
                            $data[]=$post2;
                            //$post['assignmentSubmitted'][]=$assignment;
                        }
                       /* else{
                            $post['assignmentSubmitted']=array();
                        }*/
                    }
                    elseif($val['activity_type'] == 'exam')
                    {

                        $post3['activity_type'] = $val['activity_type'];
                        $post3['activity_time'] = $val['created_date'];

                        $selectExam="SELECT exam.id as 'exam_id',exam.exam_name,student_exam_score.marks_obtained as 'exam_score',subjects.subject_name from ".TABLE_STUDENT_EXAM_SCORE." student_exam_score
                        INNER JOIN ". TABLE_EXAMS ." exams ON exam.id=student_exam_score.exam_id
                        INNER JOIN ". TABLE_SUBJECTS." subjects ON subjects.id=exam.subject_id WHERE student_exam_score.exam_id=".$val['resource_id']." AND student_exam_score.user_id=".$user_id." AND student_exam_score.is_delete=0";
                        $resultExam=mysqli_query($GLOBALS['con'], $selectExam);

                        $exams=array();
                        if(mysqli_num_rows($resultExam))
                        {
                            while($examRow=mysqli_fetch_assoc($resultExam))
                            {
                                $exams[]=$examRow;
                                //$post['exam_attempted']=$exams;
                            }
                            $post3['exam_attempted']=$exams;
                            $data[]=$post3;
                        }
                       /* else{
                            $post['exam_attempted']=array();
                        }*/
                    }
                    elseif($val['activity_type'] == 'liked')
                    {


                        $feedsLiked=array();
                        $post4['activity_type'] = $val['activity_type'];
                        $post4['activity_time'] = $val['created_date'];

                        $queryFeedLike="select feed.*,user.full_name,user.profile_pic  from ".TABLE_FEED_LIKE." feed_like
                         INNER JOIN ". TABLE_FEEDS ." feed ON feed.id=feed_like.feed_id
                        INNER JOIN ".TABLE_USERS." user ON feed.feed_by=user.id
                         where feed_like.like_by=".$user_id." and feed_like.feed_id= ". $val['resource_id']." and feed_like.is_delete=0 and feed.is_delete=0 and user.is_delete=0";
                        $resultFeedLike=mysqli_query($GLOBALS['con'], $queryFeedLike) or $message= mysqli_error($GLOBALS['con']);

//echo $queryFeedLike; exit;

                        if(mysqli_num_rows($resultFeedLike))
                        {
                            while($feed=mysqli_fetch_assoc($resultFeedLike))
                            {
                                $feedsLiked['feed_id']=$feed['id'];
                                $feedsLiked['feed_text']=$feed['feed_text'];
                                $feedsLiked['feed_posted_on']=$feed['posted_on'];
                                $feedsLiked['feed_user_id']=$feed['feed_by'];
                                $feedsLiked['full_name']=$feed['full_name'];
                                $feedsLiked['feed_user_pic']=$feed['profile_pic'];
                                $feedsLiked['feed_total_like']=$feed['total_like'];
                                $feedsLiked['feed_total_comment']=$feed['total_comment'];
                                //Get Comments
                               /* $queryGetAllComments = "SELECT f.id,f.comment ,f.comment_by,f.created_date,u.full_name,p.profile_link as 'profile_pic' FROM ".TABLE_FEED_COMMENT." f INNER JOIN ".TABLE_USERS." u
            ON f.comment_by=u.id INNER JOIN ".TABLE_USER_PROFILE_PICTURE." p ON p.user_id=u.id WHERE f.feed_id=".$feed['id'] ." AND f.is_delete=0 AND u.is_delete=0 AND p.is_delete=0 LIMIT 2";
                                //echo $queryGetAllComments;
                                $resultGetAlComments = mysqli_query($GLOBALS['con'], $queryGetAllComments) or $message =  mysqli_error($GLOBALS['con']);
                                $allcomment=array();

                                if(mysqli_num_rows($resultGetAlComments))
                                {
                                    while($comments=mysqli_fetch_assoc($resultGetAlComments))
                                    {
                                        $allcomment[]=$comments;
                                    }
                                }
                                $feeds['comment_list']=$allcomment;*/

                            }
                            $post4['feed_liked']=$feedsLiked;
                            $data[]=$post4;
                            //$post['feedLiked'][]=$feeds;
                        }
                        /*else{
                            $post['feedLiked']=array();
                        }*/

                    }
                   elseif($val['activity_type'] == 'studymate')
                    {
                        $studymate=array();
                        $post5['activity_type'] = $val['activity_type'];
                        $post5['activity_time'] = $val['created_date'];

                         $queryGetStudyMate="SELECT studymates.mate_id,users.full_name,users.profile_pic,school.school_name,student_profile.total_authors_followed from studymates studymates
                        INNER JOIN users users on studymates.mate_id=users.id
                        LEFT JOIN ".TABLE_STUDENT_PROFILE ." student_profile ON student_profile.user_id=studymates.mate_id
                        LEFT JOIN ". TABLE_SCHOOLS." school ON  school.id=student_profile.school_id
                        where studymates.is_delete=0 and studymates.mate_of=".$user_id." and studymates.mate_id=".$val['resource_id'];
                        //$queryGetStudyMate="SELECT studymates.mate_id,users.full_name,users.profile_pic from ".TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users on studymates.mate_id=users.id where is_delete=0 and id=".$val['resource_id']." AND mate_id=".$user_id;
                        $resultGetStudyMate=mysqli_query($GLOBALS['con'], $queryGetStudyMate) or $message= mysqli_error($GLOBALS['con']);



                        if(mysqli_num_rows($resultGetStudyMate))
                        {
                            while($studymateRow=mysqli_fetch_assoc($resultGetStudyMate))
                            {
                                //$studymate[]=$studymateRow;

                                $studymate['display_content'] = $val['display_content'];
                                $studymate['studymate_id']=$studymateRow['mate_id'];
                                $studymate['studymate_name']=$studymateRow['full_name'];
                                $studymate['studymate_profile_pic']=$studymateRow['profile_pic'];
                                $studymate['studymate_school_name']=$studymateRow['school_name'];
                                $studymate['total_authors_followed']=$studymateRow['total_authors_followed'];


                            }

                            $post5['studymates']=$studymate;
                            $data[]=$post5;
                            //$post1[]=$studymate;

                        }

                        /*else
                        {
                            $post['studymates']=array();
                        }*/


                    }


                    elseif($val['activity_type'] == 'commented') {

                        $post6['activity_type'] = $val['activity_type'];
                        $post6['activity_time'] = $val['created_date'];

                        $queryGetComment = "SELECT feed.*,f.feed_id,f.comment,f.comment_by,f.created_date,u.full_name,p.profile_link as 'profile_pic' FROM " . TABLE_FEED_COMMENT . " f
                        INNER JOIN " . TABLE_USERS . " u ON f.comment_by=u.id
                        INNER JOIN " . TABLE_USER_PROFILE_PICTURE . " p ON p.user_id=u.id
                        INNER JOIN ". TABLE_FEEDS ." feed ON feed.id=f.feed_id
                        WHERE f.feed_id=" . $val['resource_id'] . " AND comment_by=" . $user_id . " AND f.is_delete=0
                        AND u.is_delete=0 AND p.is_delete=0 ORDER BY f.id DESC LIMIT 2";
                        //echo $queryGetAllComments;
                        $resultGetComment = mysqli_query($GLOBALS['con'], $queryGetComment) or $message =  mysqli_error($GLOBALS['con']);

                        $comment = array();
                        if (mysqli_num_rows($resultGetComment)) {
                            while ($commentRow = mysqli_fetch_assoc($resultGetComment)) {
                                $comment['feed_id'] = $commentRow['feed_id'];
                                $comment['comment_text'] = $commentRow['comment'];
                                $comment['comment_by'] = $commentRow['comment_by'];
                                $comment['feed_user_name'] = $commentRow['full_name'];
                                $comment['feed_user_pic'] = $commentRow['profile_pic'];
                                $comment['feed_total_like']=$commentRow['total_like'];
                                $comment['feed_comment_posted'] = $commentRow['created_date'];

                            }

                            $post6['comment_added'] = $comment;
                            $data[]=$post6;
                        }

                      /*  else
                        {
                            $post['comment_added']=array();
                        }*/

                    }
                   if($val['activity_type'] == 'post')
                    {

                        $post7['activity_type'] = $val['activity_type'];
                        $post7['activity_time'] = $val['created_date'];

                        $queryFeedLike="select feed.*,user.id as 'UserId',user.full_name,user.profile_pic as 'Profile_pic' from ".TABLE_FEEDS." feed
                        INNER JOIN ".TABLE_USERS." user ON feed.feed_by=user.id
                         where feed.feed_by=".$user_id." and feed.id= ". $val['resource_id']." and feed.is_delete=0 and user.is_delete=0";
                        $resultFeedLike=mysqli_query($GLOBALS['con'], $queryFeedLike) or $message= mysqli_error($GLOBALS['con']);

//echo $queryFeedLike; exit;
                        $feedPosted=array();
                        if(mysqli_num_rows($resultFeedLike))
                        {
                            while($feed=mysqli_fetch_assoc($resultFeedLike))
                            {
                                $feedPosted['feed_id']=$feed['id'];
                                $feedPosted['feed_text']=$feed['feed_text'];
                                $feedPosted['feed_user_id']=$feed['feed_by'];
                                $feedPosted['feed_user_name']=$feed['full_name'];
                                $feedPosted['feed_user_pic']=$feed['Profile_pic'];
                                $feedPosted['feed_posted_on']=$feed['posted_on'];

                                //Get Comments
                               /* $queryGetAllComments = "SELECT f.id,f.comment ,f.comment_by,f.created_date,u.full_name,p.profile_link as 'profile_pic' FROM ".TABLE_FEED_COMMENT." f INNER JOIN ".TABLE_USERS." u
            ON f.comment_by=u.id INNER JOIN ".TABLE_USER_PROFILE_PICTURE." p ON p.user_id=u.id WHERE f.feed_id=".$feed['id'] ." AND f.is_delete=0 AND u.is_delete=0 AND p.is_delete=0 ORDER BY f.id DESC LIMIT 2";
                                //echo $queryGetAllComments;
                                $resultGetAlComments = mysqli_query($GLOBALS['con'], $queryGetAllComments) or $message =  mysqli_error($GLOBALS['con']);
                                $allcomments=array();

                                if(mysqli_num_rows($resultGetAlComments))
                                {
                                    while($comments=mysqli_fetch_assoc($resultGetAlComments))
                                    {
                                        $allcomments[]=$comments;
                                    }
                                }
                                $feeds['comment_list']=$allcomments;*/

                            }
                            $post7['feed_posted']=$feedPosted;
                            $data[]=$post7;
                        }

                       /* else
                        {
                            $post['feedPosted']=array();
                        }*/

                    }



                    //$data['resource']=$post;



                }

                //$data[]=$post;
                $message = "";
                $status = SUCCESS;
            } else {
                $status = SUCCESS;
                $message=DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
    	$response['user_activities']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);


        if ($isSecure == yes) {

            //Check If UserID is not Present, then Find id from EmailID.
            if ($block_user == NULL || $block_user == 0) {
                $selectQuery = "SELECT id FROM ".TABLE_USERS." WHERE email_id='".$email_id."' AND is_delete=0";
                $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);
                $getUserId = mysqli_fetch_row($resultQuery);
                $block_user_id = $getUserId[0];

            }
            else
            {
                $block_user_id=$block_user;
            }

            if ($user_id != NULL || $block_user != NULL || $email_id != NULL) {

                //Find UserId and Block User from StudyMate table
                $queryFindStudyMate = "SELECT * FROM ".TABLE_STUDYMATES." WHERE mate_id=".$block_user_id." AND mate_of=".$user_id." AND (status='request' OR status='friend') AND is_delete=0";
                //echo $queryFindStudyMate;
                $resultFindStudyMate = mysqli_query($GLOBALS['con'], $queryFindStudyMate) or $message = mysqli_error($GLOBALS['con']);

                if (mysqli_num_rows($resultFindStudyMate) > 0) {
                    $updateQuery = "UPDATE " . TABLE_STUDYMATES . " SET status='block' WHERE mate_id=" . $block_user_id . " and mate_of=" . $user_id;
                    $updateResult = mysqli_query($GLOBALS['con'], $updateQuery) or $message = mysqli_error($GLOBALS['con']);
                    $message = "user blocked";
                    $status = SUCCESS;
                }
                else{
                    $status = SUCCESS;
                    $message = DEFAULT_NO_RECORDS;
                }

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['block_user']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            $selectQuery = "select book.*,authorBookJoin.full_name,authorBookJoin.profile_pic from ".TABLE_BOOKS ." book
         LEFT JOIN(SELECT autotBook.user_id,autotBook.book_id,users.full_name,users.profile_pic from ".TABLE_AUTHOR_BOOK." autotBook LEFT JOIN ".TABLE_USERS.
                " users ON users.id=autotBook.user_id) authorBookJoin ON authorBookJoin.book_id=book.id WHERE book.is_delete=0 AND book.id NOT IN(SELECT book_id from ".TABLE_USER_FAVORITE_BOOK."  where user_id=".$user_id. " AND is_delete=0)  ORDER BY book.id DESC LIMIT 30";

            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    $suggested['book_id'] = $val['id'];
                    $suggested['book_name'] = $val['book_name'];
                    $suggested['ebook_link'] = $val['ebook_link'];
                    $suggested['front_cover_image'] = $val['front_cover_image'];
                    $suggested['back_cover_image'] = $val['back_cover_image'];
                    $suggested['pdf_link'] = $val['pdf_link'];
                    $suggested['publisher_name'] = $val['publisher_name'];
                    $suggested['description'] = $val['book_description'];
                    $suggested['author_name'] = $val['full_name'];
                    $suggested['author_image'] = $val['profile_pic'];
                    $suggested['price'] = $val['price'];


                    $queryIsInLibrary="SELECT *  FROM ".TABLE_USER_LIBRARY." where book_id=".$val['id']." and is_delete=0 ";
                    $resultIsInLibrary=mysqli_query($GLOBALS['con'], $queryIsInLibrary) or  $message= mysqli_error($GLOBALS['con']);

                    if(mysqli_num_rows($resultIsInLibrary) > 0)
                    {
                        $suggested['is_in_library'] = '1';
                    }
                    else{
                        $suggested['is_in_library'] = '0';
                    }

                    $tags=array();
                    $tagQuery="SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_BOOK." tag_books JOIN ".TABLE_TAGS." tags ON tags.id=tag_books.tag_id WHERE tag_books.book_id=".$val['id']." AND tags.is_delete=0 and tag_books.is_delete=0";
                    $tagResult=mysqli_query($GLOBALS['con'], $tagQuery) or  $message= mysqli_error($GLOBALS['con']);
                    if(mysqli_num_rows($tagResult))
                    {

                        while($rowGetTags=mysqli_fetch_assoc($tagResult)) {
                            $tags[]=$rowGetTags;

                        }
                        $suggested['tags']=$tags;
                    }
                    else{
                        $suggested['tags']=$tags;
                    }


                    $data['suggested_book'][] = $suggested;
                }

                $message=REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data['suggested_book'] = array();
                $status=SUCCESS;

            }


            $selectFavoriteQuery="select DISTINCT book.*,authorBookJoin.full_name,authorBookJoin.profile_pic from ".TABLE_USER_FAVORITE_BOOK . " userFavoriteBook
         INNER JOIN ".TABLE_BOOKS ." book ON book.id=userFavoriteBook.book_id
         LEFT JOIN(SELECT authorBook.user_id,authorBook.book_id,users.full_name,users.profile_pic from ".TABLE_AUTHOR_BOOK." authorBook INNER JOIN ".TABLE_USERS.
                " users ON users.id=authorBook.user_id) authorBookJoin ON authorBookJoin.book_id=book.id
         WHERE userFavoriteBook.user_id=".$user_id ." AND userFavoriteBook.is_delete = 0";
            // echo  $selectFavoriteQuery;
            $resultFavoriteQuery = mysqli_query($GLOBALS['con'], $selectFavoriteQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultFavoriteQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultFavoriteQuery)) {
                    $favorite['book_id'] = $val['id'];
                    $favorite['book_name'] = $val['book_name'];
                    $favorite['ebook_link'] = $val['ebook_link'];
                    $favorite['front_cover_image'] = $val['front_cover_image'];
                    $favorite['back_cover_image'] = $val['back_cover_image'];
                    $favorite['pdf_link'] = $val['pdf_link'];
                    $favorite['publisher_name'] = $val['publisher_name'];
                    $favorite['description'] = $val['book_description'];
                    $favorite['author_name'] = $val['full_name'];
                    $favorite['author_image'] = $val['profile_pic'];
                    $favorite['price'] = $val['price'];


                    //$queryIsInLibrary="SELECT *  FROM ". TABLE_USER_FAVORITE_BOOK." uf JOIN ".TABLE_USER_LIBRARY." ul ON uf.book_id=ul.book_id where ul.book_id=".$val['id']." and ul.user_id=".$user_id." and ul.is_delete=0 and uf.is_delete=0";
                    $queryIsInLibrary="SELECT *  FROM ".TABLE_USER_LIBRARY." where book_id=".$val['id']." and user_id=".$user_id." and is_delete=0 ";
                    $resultIsInLibrary=mysqli_query($GLOBALS['con'], $queryIsInLibrary) or  $message= mysqli_error($GLOBALS['con']);

                    if(mysqli_num_rows($resultIsInLibrary) > 0)
                    {
                        $favorite['is_in_library'] = '1';
                    }
                    else{
                        $favorite['is_in_library'] = '0';
                    }

                    $tags=array();
                    $tagQuery="SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_BOOK." tag_books JOIN ".TABLE_TAGS." tags ON tags.id=tag_books.tag_id WHERE tag_books.book_id=".$val['id']." AND tags.is_delete=0 and tag_books.is_delete=0";
                    $tagResult=mysqli_query($GLOBALS['con'], $tagQuery) or  $message= mysqli_error($GLOBALS['con']);
                    if(mysqli_num_rows($tagResult))
                    {

                        while($rowGetTags=mysqli_fetch_assoc($tagResult)) {
                            $tags[]=$rowGetTags;

                        }
                        $favorite['tags']=$tags;
                    }
                    else{
                        $favorite['tags']=$tags;
                    }

                    $data['favorite_book'][] = $favorite;
                }
                $message = REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data['favorite_book'] = array();
                $status=SUCCESS;

            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['books'][]=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            $selectQuery = "select id as 'pastime_id',pastime_name,pastime_image from ".TABLE_PASTIMES." WHERE  is_delete=0 AND id NOT IN(SELECT pastime_id from ".TABLE_USER_FAVORITE_PASTIME." WHERE user_id=".$user_id. " AND is_delete=0)  ORDER BY id DESC LIMIT 30";
            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    $data['suggested_pastime'][] = $val;
                }

                $message = REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data['suggested_pastime'] = array();
                //$status=FAILED;
            }

            /* $selectFavoriteQuery="select user_favorite_pastime.pastime_id,users.full_name as 'pastime_name',users.profile_pic as 'pastime_image' from " .TABLE_USER_FAVORITE_PASTIME." user_favorite_pastime
             INNER JOIN ".TABLE_PASTIMES." pastime ON user_favorite_pastime.pastime_id=pastime.id
             INNER JOIN ".TABLE_USERS." users ON users.id=user_favorite_pastime.user_id
             WHERE user_favorite_pastime.user_id=".$user_id;
             echo  $selectFavoriteQuery;*/
            $selectFavoriteQuery =  $selectFavoriteQuery="select user_favorite_pastime.pastime_id,pastime.pastime_name,pastime.pastime_image from " .TABLE_USER_FAVORITE_PASTIME." user_favorite_pastime
         INNER JOIN ".TABLE_PASTIMES." pastime ON user_favorite_pastime.pastime_id=pastime.id
         WHERE user_favorite_pastime.user_id=".$user_id ." AND user_favorite_pastime.is_delete = 0";
            $resultFavoriteQuery = mysqli_query($GLOBALS['con'], $selectFavoriteQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultFavoriteQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultFavoriteQuery)) {
                    $data['favorite_pastime'][] = $val;
                }
                $message = REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data['favorite_pastime'] = array();
                //$status=FAILED;
            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            $selectQuery = "SELECT id as 'rolemodel_id',model_name,model_image,birthdate,description,organization,quotes,achievements,activities,education
         from ".TABLE_ROLE_MODELS." WHERE  is_delete=0 AND id NOT IN(SELECT role_model_id from ".TABLE_USER_ROLE_MODEL." where user_id=".$user_id. " AND is_delete=0)  ORDER BY id DESC LIMIT 30";
            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    $data['suggested_rolemodel'][] = $val;
                }

                $message = REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data['suggested_rolemodel'] = array();
                //$status=FAILED;
            }

            $selectFavoriteQuery = "select user_rolemodel.role_model_id,roleModel.model_name,roleModel.model_image,roleModel.birthdate,roleModel.description,
         roleModel.organization,roleModel.quotes,roleModel.achievements,roleModel.activities,roleModel.education from " .TABLE_USER_ROLE_MODEL." user_rolemodel
         INNER JOIN ".TABLE_ROLE_MODELS." roleModel ON user_rolemodel.role_model_id=roleModel.id
         WHERE user_rolemodel.user_id=".$user_id." AND user_rolemodel.is_delete = 0";
            // echo  $selectFavoriteQuery;
            $resultFavoriteQuery = mysqli_query($GLOBALS['con'], $selectFavoriteQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultFavoriteQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultFavoriteQuery)) {
                    $data['favorite_rolemodel'][] = $val;
                }
                $message = REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data['favorite_rolemodel'] = array();
                //$status=FAILED;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            $selectQuery = "SELECT id as 'movie_id',movie_name,movie_image,genres as 'movie_genre',description,screenplay from ".TABLE_MOVIES." WHERE  is_delete=0 AND id NOT IN (SELECT movie_id from ".TABLE_USER_FAVORITE_MOVIE." where user_id=".$user_id. " AND is_delete=0)  ORDER BY id DESC LIMIT 30";
            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    $data['suggested_movies'][] = $val;
                }

                $message = REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data['suggested_movies'] = array();
                //$status=SUCCESS;
            }

            $selectFavoriteQuery = "select user_favorite_movie.movie_id,movie.movie_name,movie.movie_image,movie.genres as 'movie_genre',movie.description,movie.screenplay
         from " .TABLE_USER_FAVORITE_MOVIE." user_favorite_movie
         INNER JOIN ".TABLE_MOVIES." movie ON user_favorite_movie.movie_id=movie.id
         WHERE user_favorite_movie.user_id=".$user_id." AND user_favorite_movie.is_delete = 0";
            // echo  $selectFavoriteQuery;
            $resultFavoriteQuery = mysqli_query($GLOBALS['con'], $selectFavoriteQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultFavoriteQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultFavoriteQuery)) {
                    $data['favorite_movies'][] = $val;
                }
                $message = REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data['favorite_movies'] = array();
                //$status=FAILED;
            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
    	$response['movies'][]=$data;
        $response['message'] = $message;
        $response['status'] = $status;
        
	   return $response;
    }
    
    /*
    * addResourcesToFavorite
    */
    public function manageFavorite($postData)
    {
    	$data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $resource_name = validateObject($postData, 'resource_name', "");
        $resource_name = addslashes($resource_name);

        $fav_resource_id = validateObject($postData, 'fav_resource_id', "");

        $unfavorite_resource_id = validateObject($postData, 'unfavorite_resource_id', "");

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            if ($resource_name == "rolemodel") {
                $table = TABLE_USER_ROLE_MODEL;
                $resource_name_id = "role_model_id";
            } else if ($resource_name == "books") {
                $table = TABLE_USER_FAVORITE_BOOK;
                $resource_name_id = "book_id";
            } else if ($resource_name == "movies") {
                $table = TABLE_USER_FAVORITE_MOVIE;
                $resource_name_id = "movie_id";
            } else if ($resource_name == "pastimes") {
                $table = TABLE_USER_FAVORITE_PASTIME;
                $resource_name_id = "pastime_id";
            }

            //To add resources in favorite

            if($fav_resource_id!=null)
            {
                foreach($fav_resource_id as $fav_id) {
                    $selQuery = "SELECT * FROM " . $table . " WHERE user_id=" . $user_id . " AND " . $resource_name_id . "=" . $fav_id ." AND is_delete=0";;
                    $selResult = mysqli_query($GLOBALS['con'],$selQuery) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($selResult) == 0) {
                        $insertFields = "`user_id`,`" . $resource_name_id . "`,`is_delete`";
                        $insertValues = $user_id . "," . $fav_id.",'0'";

                        $query = "INSERT INTO " . $table . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                        $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);

                        if($result)
                        {
                            $status = SUCCESS;
                            $message = "favorite synced";
                        }
                        else{
                            $status = FAILED;
                            $message = "";
                        }
                    }
                    else
                    {
                        $queryUpdate="UPDATE " .$table ." SET is_delete = 0 WHERE user_id=".$user_id." AND ".$resource_name_id."  =" . $fav_id;
                        $resultUpdate = mysqli_query($GLOBALS['con'],$queryUpdate) or $message = mysqli_error($GLOBALS['con']);

                        if($resultUpdate)
                        {

                            $status = SUCCESS;
                            $message = "favorite synced";
                        }
                        else{
                            $status = FAILED;
                            $message = "";
                        }
                    }
                }
            }


            //To make resource ids as unfavorite
            if($unfavorite_resource_id!=null)
            {
                foreach($unfavorite_resource_id as $unfav_id) {

                    $queryCheckFeed = "SELECT id FROM " . $table . " WHERE user_id=".$user_id." AND ".$resource_name_id." =" . $unfav_id ." AND is_delete=0";
                    $resultCheckFeed = mysqli_query($GLOBALS['con'],$queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultCheckFeed) > 0) {

                        $queryUpdate="UPDATE " .$table ." SET is_delete = 1 WHERE user_id=".$user_id." AND ".$resource_name_id."  =" . $unfav_id;
                        $resultUpdate = mysqli_query($GLOBALS['con'],$queryUpdate) or $message = mysqli_error($GLOBALS['con']);

                        // $queryToDelete="DELETE * FROM " .$table ."  WHERE user_id=".$user_id." AND ".$resource_name_id."  =" . $unfav_id;
                        //$resultToDelete = mysqli_query($GLOBALS['con'],$queryToDelete) or $message = mysqli_error($GLOBALS['con']);

                        if($resultUpdate)
                        {

                            $status = SUCCESS;
                            $message = "favorite synced";
                        }
                        else{
                            $status = FAILED;
                            $message = "";
                        }

                    }
                    else
                    {
                        $status = SUCCESS;
                        $message = DEFAULT_NO_RECORDS;
                    }

                }

            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            if ($follower_id != NULL && $follow_to != NULL) {
                $selectQuery = "SELECT * FROM ".TABLE_FOLLOWERS." WHERE follower_id=". $follower_id ." AND follow_to=".$follow_to ." AND is_delete=0";
                $selResult = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

                if (mysqli_num_rows($selResult) > 0) {
                    while ($row = mysqli_fetch_assoc($selResult)) {
                        $follow_status = $row['follow_status'];
                    }
                    if ($follow_status == "followed")
                        $follow_status = "unfollowed";
                    else
                        $follow_status = "followed";

                    //Check for following user
                    $queryUpdateStatus = "SELECT status FROM ".TABLE_FOLLOWERS." WHERE follower_id=". $follow_to ." AND follow_to=".$follower_id." AND is_delete=0";
                    $resultUpdateStatus = mysqli_query($GLOBALS['con'], $queryUpdateStatus) or $message = mysqli_error($GLOBALS['con']);

                    if ($resultUpdateStatus) {
                        $queryUpdate = "UPDATE " . TABLE_FOLLOWERS . " SET follow_status='following' WHERE follower_id=" . $follow_to . " AND follow_to=" . $follower_id;
                        $resultUpdate = mysqli_query($GLOBALS['con'], $queryUpdate) or $message = mysqli_error($GLOBALS['con']);
                    } else {
                        $queryUpdate = "UPDATE " . TABLE_FOLLOWERS . " SET follow_status='" . $follow_status . "' WHERE follower_id=" . $follower_id . " AND follow_to=" . $follow_to;
                        $resultUpdate = mysqli_query($GLOBALS['con'], $queryUpdate) or $message = mysqli_error($GLOBALS['con']);
                    }
                } else {
                    $insertFields = "`follower_id`,`follow_to`";
                    $insertValues = $follower_id . "," . $follow_to;

                    $queryInsert = "INSERT INTO " . TABLE_FOLLOWERS . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                    $resultInsert = mysqli_query($GLOBALS['con'], $queryInsert) or $message = mysqli_error($GLOBALS['con']);
                }
                $status = SUCCESS;
                $message = "Followship updated";
            } else {
                $status = FAILED;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            //Check Privacy Setting for Contact and Birthdate Info in User account Prefernce.
            $queryToChkDOBInPreference = "SELECT preference_value FROM ".TABLE_USER_ACCOUNT_PREFERENCE." WHERE preference_id=6 AND user_id=".$user_id." AND is_delete=0";
            $resultToChkDOBInPreference = mysqli_query($GLOBALS['con'], $queryToChkDOBInPreference) or $message = mysqli_error($GLOBALS['con']);
            $isShowDOB = mysqli_fetch_row($resultToChkDOBInPreference);

            $queryToChkContactInPreference = "SELECT preference_value FROM ".TABLE_USER_ACCOUNT_PREFERENCE." WHERE preference_id=7 AND user_id=".$user_id." AND is_delete=0";
            $resultToChkContactInPreference = mysqli_query($GLOBALS['con'], $queryToChkContactInPreference) or $message = mysqli_error($GLOBALS['con']);
            $isShowContact = mysqli_fetch_row($resultToChkContactInPreference);


            if ((strcasecmp($isShowDOB[0], 'yes') == 0) && (strcasecmp($isShowContact[0], 'yes') == 0)) {
                $getUserInfo = "users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.birthdate,users.contact_number,users.email_id,users.about_me,";
            } else if ((strcasecmp($isShowDOB[0], 'no') == 0) && (strcasecmp($isShowContact[0], 'yes') == 0)) {
                $getUserInfo = "users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.contact_number,users.email_id,users.about_me,";
            } else if ((strcasecmp($isShowDOB[0], 'yes') == 0) && (strcasecmp($isShowContact[0], 'no') == 0)) {
                $getUserInfo = "users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.birthdate,users.email_id,users.about_me,";
            } else if ((strcasecmp($isShowDOB[0], 'no') == 0) && (strcasecmp($isShowContact[0], 'no') == 0)) {
                $getUserInfo = "users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.email_id,users.about_me,";
            }
            else{
                $getUserInfo = "users.id as 'user_id',users.full_name as 'username',users.profile_pic,users.birthdate,users.contact_number,users.email_id,users.about_me,";
            }


            $getStudentProfileInfo = "studentProfile.total_score,studentProfile.total_exams,studentProfile.total_badges,studentProfile.total_studymate,studentProfile.total_authors_followed,studentProfile.total_assignment,studentProfile.total_favorite_question,studentProfile.total_question_asked,studentProfile.total_post,studentProfile.rank as 'ism_rank',studentProfile.ambition_in_life";
            $queryGetInfo = "SELECT ".$getUserInfo." school.school_name,ifnull(tutorial_group.group_name,'') as 'group_name',course.course_name,classroom.class_name as 'classroom_name',".$getStudentProfileInfo." FROM ".TABLE_STUDENT_PROFILE." studentProfile
    	INNER JOIN ".TABLE_USERS." users ON users.id=studentProfile.user_id
    	LEFT JOIN ".TABLE_SCHOOLS." school ON school.id=studentProfile.school_id
    	LEFT JOIN ".TABLE_CLASSROOMS." classroom ON classroom.id=studentProfile.classroom_id
    	LEFT JOIN ".TABLE_COURSES." course ON course.id=studentProfile.course_id
    	LEFT JOIN ".TABLE_TUTORIAL_GROUPS." tutorial_group ON tutorial_group.classroom_id=studentProfile.classroom_id
    	WHERE studentProfile.user_id=".$user_id." AND studentProfile.is_delete=0 AND users.is_delete=0 ";//AND school.is_delete=0 AND classroom.is_delete=0 AND course.is_delete=0 AND tutorial_group.is_delete=0 ";
            //echo $queryGetInfo;
            $resultGetInfo = mysqli_query($GLOBALS['con'], $queryGetInfo) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultGetInfo) > 0) {
                while ($row = mysqli_fetch_assoc($resultGetInfo)) {
                    $post[] = $row;
                    $post = $post[0];
                }

                //Get Favorite Authors
                $queryGetFavoriteAuthors = "SELECT fav_author.id as 'author_id',users.full_name as 'author_name',users.profile_pic as 'author_image' FROM " .TABLE_USER_FAVORITE_AUTHOR." fav_author LEFT JOIN ".TABLE_USERS." users ON fav_author.author_id=users.id WHERE fav_author.author_id=".$user_id." AND fav_author.is_delete=0 AND users.is_delete=0 ";
                $resultGetFavoriteAuthors = mysqli_query($GLOBALS['con'], $queryGetFavoriteAuthors) or $message = mysqli_error($GLOBALS['con']);
                $fav_author_array = array();
                if (mysqli_num_rows($resultGetFavoriteAuthors) > 0) {

                    while ($authors = mysqli_fetch_assoc($resultGetFavoriteAuthors)) {
                        $fav_author_array[] = $authors;
                    }
                }
                $post['favorite_authors'] = $fav_author_array;

                //Get User Movies

                $queryGetFavoriteMovies = "SELECT fav_movie.movie_id,movie.movie_name,movie.movie_image FROM " .TABLE_USER_FAVORITE_MOVIE." fav_movie INNER JOIN ".TABLE_MOVIES." movie ON fav_movie.movie_id=movie.id WHERE fav_movie.user_id=".$user_id ." AND fav_movie.is_delete=0 AND movie.is_delete=0 ";
                $resultGetFavoriteMovies = mysqli_query($GLOBALS['con'], $queryGetFavoriteMovies) or $message = mysqli_error($GLOBALS['con']);
                $fav_movies_array = array();
                if (mysqli_num_rows($resultGetFavoriteMovies) > 0) {

                    while ($authors = mysqli_fetch_assoc($resultGetFavoriteMovies)) {
                        $fav_movies_array[] = $authors;
                    }
                }
                $post['user_movies'] = $fav_movies_array;

                //Get favorite pastime

                $queryGetFavoritepastime = "SELECT fav_pastime.pastime_id,pastimes.pastime_name,pastimes.pastime_image FROM " .TABLE_USER_FAVORITE_PASTIME." fav_pastime INNER JOIN ".TABLE_PASTIMES." pastimes ON fav_pastime.pastime_id=pastimes.id WHERE fav_pastime.user_id=".$user_id." AND fav_pastime.is_delete=0 AND pastimes.is_delete=0 ";
                $resultGetFavoritepastime = mysqli_query($GLOBALS['con'], $queryGetFavoritepastime) or $message = mysqli_error($GLOBALS['con']);
                $fav_pastime_array = array();
                if (mysqli_num_rows($resultGetFavoritepastime) > 0) {

                    while ($authors = mysqli_fetch_assoc($resultGetFavoritepastime)) {
                        $fav_pastime_array[] = $authors;
                    }
                }
                $post['favorite_pastime'] = $fav_pastime_array;

                //Get favorite role_model

                $queryGetUserRole_model = "SELECT fav_role_model.role_model_id as 'model_id',role_model.model_name,role_model.model_image FROM " .TABLE_USER_ROLE_MODEL." fav_role_model INNER JOIN ".TABLE_ROLE_MODELS." role_model ON fav_role_model.role_model_id=role_model.id WHERE fav_role_model.user_id=".$user_id." AND fav_role_model.is_delete=0 AND role_model.is_delete=0 ";
                $resultGetFavoriteUserRole_model = mysqli_query($GLOBALS['con'], $queryGetUserRole_model) or $message = mysqli_error($GLOBALS['con']);
                $user_role_model_array = array();
                if (mysqli_num_rows($resultGetFavoriteUserRole_model) > 0) {

                    while ($authors = mysqli_fetch_assoc($resultGetFavoriteUserRole_model)) {
                        $user_role_model_array[] = $authors;
                    }
                }
                $post['role_model'] = $user_role_model_array;

                $data[] = $post;
                $status = SUCCESS;
                $message = REQUEST_ACCEPTED;
            } else {
                $status = SUCCESS;
                $message=DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
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

        $add_book_id = validateObject($postData, 'add_book_id', "");

        $remove_book_id = validateObject($postData, 'remove_book_id', "");

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {
            if ($add_book_id != null) {
                foreach ($add_book_id as $book_id) {

                    $queryCheckFeed = "SELECT * FROM " . TABLE_USER_LIBRARY . " WHERE user_id=" . $user_id . " AND book_id=" . $book_id . " AND is_delete=0";
                    //echo $queryCheckFeed."\n";
                    $resultCheckFeed = mysqli_query($GLOBALS['con'], $queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultCheckFeed) == 0) {

                        $insertFields = "`user_id`,`book_id`,`is_delete`";
                        $insertValues = $user_id . "," . $book_id . ", 0";

                        $query = "INSERT INTO " . TABLE_USER_LIBRARY . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                        if ($result) {
                            $status = SUCCESS;
                            $message = "library synced";
                        } else {
                            $status = FAILED;
                            $message = "";
                        }
                    } else {
                        $status = SUCCESS;
                        $message = RECORD_ALREADY_EXIST;
                    }
                }

            }


            if ($remove_book_id != null) {
                foreach ($remove_book_id as $book_id) {

                    $queryCheckFeed = "SELECT * FROM " . TABLE_USER_LIBRARY . " WHERE user_id=" . $user_id . " AND book_id=" . $book_id . " AND is_delete=0";
                    //echo $queryCheckFeed."\n";
                    $resultCheckFeed = mysqli_query($GLOBALS['con'], $queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultCheckFeed) > 0) {

                        $queryUpdate = "UPDATE " . TABLE_USER_LIBRARY . " SET is_delete = 1 WHERE user_id=" . $user_id . " AND book_id=" . $book_id . " AND is_delete=0";
                        $resultUpdate = mysqli_query($GLOBALS['con'], $queryUpdate) or $message = mysqli_error($GLOBALS['con']);
                        // echo $queryUpdate;

                        if ($resultUpdate) {
                            $status = SUCCESS;
                            $message = "library synced";
                        } else {
                            $status = FAILED;
                            $message = "";
                        }
                    } else {
                        $status = SUCCESS;
                        $message = RECORD_ALREADY_EXIST;
                    }
                }

            }


        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $response['student_profile']=$data;
        $response['status']=$status;
        $response['message']=$message;

        return $response;
    }

    public function getBlockedUser($postData)
    {
        $data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {
            //studymate_request.request_to_mate_id,studymates.mate_of,
            $getField = "studymates.mate_id,users.full_name,users.profile_pic,users.email_id";

            $queryGetBlockedUser = "SELECT " . $getField . " FROM " . TABLE_STUDYMATES . " studymates
        JOIN " . TABLE_USERS . " users ON users.id=studymates.mate_id
        WHERE  studymates.mate_of=" . $user_id . " AND studymates.status='block' AND studymates.is_delete=0";
            $resultGetBlockedUser = mysqli_query($GLOBALS['con'], $queryGetBlockedUser) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultGetBlockedUser) > 0) {
                while ($row = mysqli_fetch_assoc($resultGetBlockedUser)) {
                    $data[] = $row;

                }
                $status = SUCCESS;
                $message = REQUEST_ACCEPTED;
            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        $response['blocked_users']=$data;
        $response['status']=$status;
        $response['message']=$message;

        return $response;
    }

    /*
   *getBooksByAuthors
   */
    public function getBooksByAuthors($postData)
    {
        $message ='';
        $status='';
        $data=array();
        $suggested=array();
        $favorite=array();
        $response=array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            if($user_id==0)
            {
               // $selectQuery = "SELECT `id` as 'book_id' , `book_name`, `book_description`,`ebook_link`,`front_cover_image`,`back_cover_image` FROM ".TABLE_BOOKS ." WHERE is_delete=0";
            }
            else{
                $selectQuery = "select book.*,autorBook.book_id as 'book_id',users.full_name,users.profile_pic from ".TABLE_BOOKS ." book INNER JOIN ".TABLE_AUTHOR_BOOK." autorBook ON autorBook.book_id=book.id LEFT JOIN ".TABLE_USERS.
                    " users ON users.id=autorBook.user_id  WHERE autorBook.is_delete=0 AND autorBook.user_id=".$user_id." ORDER BY book.id DESC";
            }


            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    $suggested['book_id'] = $val['book_id'];
                    $suggested['book_name'] = $val['book_name'];
                    $suggested['book_description'] = $val['book_description'];
                    $suggested['front_cover_image'] = $val['front_cover_image'];
                    $suggested['back_cover_image'] = $val['back_cover_image'];
                    $suggested['ebook_link'] = $val['ebook_link'];
                    $suggested['publisher_name'] = $val['publisher_name'];
                    $suggested['description'] = $val['book_description'];
                    $suggested['author_name'] = $val['full_name'];
                    $suggested['author_image'] = $val['profile_pic'];
                    $suggested['price'] = $val['price'];

                    $tags=array();
                    $tagQuery="SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_BOOK." tag_books JOIN ".TABLE_TAGS." tags ON tags.id=tag_books.tag_id WHERE tag_books.book_id=".$val['id']." AND tags.is_delete=0 and tag_books.is_delete=0";
                    $tagResult=mysqli_query($GLOBALS['con'], $tagQuery) or  $message= mysqli_error($GLOBALS['con']);
                    if(mysqli_num_rows($tagResult))
                    {

                        while($rowGetTags=mysqli_fetch_assoc($tagResult)) {
                            $tags[]=$rowGetTags;

                        }
                        $suggested['tags']=$tags;
                    }
                    else{
                        $suggested['tags']=$tags;
                    }

                    $data[] = $suggested;

                }


                $message=REQUEST_ACCEPTED;
                $status = SUCCESS;
            } else {
                $data = array();
                $status=SUCCESS;
                $message=DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['author_book']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }



    /*
    *getMyActivity
    */
    public function oldGetMyActivity($postData)
    {
        $message ='';
        $status='';
        $data=array();
        $response=array();
        $post=array();


        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            $selectQuery="SELECT user_activity.user_id,users.full_name,users.profile_pic,user_activity.display_content,user_activity.resource_id,user_activity.activity_type FROM ".TABLE_USER_ACTIVITY. " user_activity
        INNER JOIN ".TABLE_USERS." users ON user_activity.user_id=users.id WHERE user_activity.user_id=".$user_id." AND user_activity.is_delete=0 and users.is_delete=0 order by user_activity.activity_type";
            // echo $selectQuery;
            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);


            if (mysqli_num_rows($resultQuery) > 0) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    // $post['user_id'] = $val['user_id'];
                    //  $post['full_name'] = $val['full_name'];
                    // $post['profile_pic'] = $val['profile_pic'];
                    $post['display_content'] = $val['display_content'];
                    $post['resource_id'] = $val['resource_id'];
                    $post['activity_type'] = $val['activity_type'];

                    if($val['activity_type'] = 'topicAllocated')
                    {

                        $getFields="tutorial_group_member_score.topic_id,tutorial_group_member_score.score,tutorial_group_member_score.total_comments,tutorial_groups.group_name";

                        $queryToGetTopic="SELECT DISTINCT ".$getFields." FROM ". TABLE_TUTORIAL_GROUP_MEMBER_SCORE." tutorial_group_member_score
                        INNER JOIN(SELECT tutorial_group_topic_allocation.topic_id,tutorial_group_topic_allocation.group_id,tutorial_group_member.user_id,tutorial_group_member.id  FROM ". TABLE_TUTORIAL_GROUP_MEMBER ." tutorial_group_member
                        INNER JOIN  ". TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION ." tutorial_group_topic_allocation ON tutorial_group_member.group_id=tutorial_group_topic_allocation.group_id WHERE tutorial_group_member.user_id=".$user_id.")s1
                        ON s1.topic_id=tutorial_group_member_score.topic_id and tutorial_group_member_score.member_id =s1.id JOIN ". TABLE_TUTORIAL_GROUPS." tutorial_groups ON tutorial_groups.id=s1.group_id WHERE tutorial_group_member_score.topic_id=".$val['resource_id'];
                        // echo $queryToGetTopic; exit;
                        $resultToGetTopic=mysqli_query($GLOBALS['con'], $queryToGetTopic);

                        $topic_array=array();
                        if(mysqli_num_rows($resultToGetTopic)) {
                            while ($topicRow = mysqli_fetch_assoc($resultToGetTopic)) {
                                //$topic_array[]=$topicRow;

                                $post['topic_id']=$topicRow['topic_id'];
                                $post['group_name']=$topicRow['group_name'];
                                $post['score']=$topicRow['score'];
                                $post['total_comments']=$topicRow['total_comments'];


                            }
                            //$post['topic_allocated']=$topic_array;
                        }
                        /*else
                        {
                            $post['topic_allocated']=array();
                        }*/
                    }

                    if($val['activity_type'] = 'assignmentSubmitted')
                    {
                        $selectAssignment="SELECT assignment_submission.assignment_id,assignment.assignment_name,assignment.submission_date,subjects.subject_name from ".TABLE_ASSIGNMENT_SUBMISSION." assignment_submission
                        INNER JOIN ".TABLE_ASSIGNMENTS." assignment ON assignment_submission.assignment_id=assignment.id
                        INNER JOIN ". TABLE_SUBJECTS." subjects ON subjects.id=assignment.subject_id
                        WHERE assignment_submission.assignment_id=".$val['resource_id']." AND assignment_submission.user_id=".$user_id." assignment.is_delete=0";
                        $resultAssignment=mysqli_query($GLOBALS['con'], $selectAssignment);

                        $assignment=array();
                        if(mysqli_num_rows($resultAssignment))
                        {
                            while($assignmentRow=mysqli_fetch_assoc($resultAssignment))
                            {
                                //$assignment[]=$assignmentRow;
                                $post['assignment_id']=$assignmentRow['assignment_id'];
                                $post['assignment_name']=$assignmentRow['assignment_name'];
                                $post['submission_date']=$assignmentRow['submission_date'];
                                $post['subject_name']=$assignmentRow['subject_name'];

                            }
                            // $post['assignmentSubmitted']=$assignment;
                        }
                        /* else{
                             $post['assignmentSubmitted']=array();
                         }*/
                    }
                    if($val['activity_type'] = 'exam_attempted')
                    {
                        $selectExam="SELECT exam.id,exam.exam_name,student_exam_score.marks_obtained,subjects.subject_name from ".TABLE_STUDENT_EXAM_SCORE." student_exam_score
                        INNER JOIN ". TABLE_EXAMS ." exams ON exam.id=student_exam_score.exam_id
                        INNER JOIN ". TABLE_SUBJECTS." subjects ON subjects.id=exam.subject_id WHERE student_exam_score.exam_id=".$val['resource_id']." AND student_exam_score.user_id=".$user_id." AND student_exam_score.is_delete=0";
                        $resultExam=mysqli_query($GLOBALS['con'], $selectExam);

                        $exams=array();
                        if(mysqli_num_rows($resultExam))
                        {
                            while($examRow=mysqli_fetch_assoc($resultExam))
                            {
                                $exams[]=$examRow;
                            }
                            $post['exam_attempted']=$exams;
                        }
                        /* else{
                             $post['exam_attempted']=array();
                         }*/
                    }
                    if($val['activity_type'] = 'liked')
                    {

                        $queryFeedLike="select feed.*,user.id as 'UserId',user.full_name,user.profile_pic as 'Profile_pic' from ".TABLE_FEED_LIKE." feed_like
                         inner join ". TABLE_FEEDS ." feed
                        INNER JOIN ".TABLE_USERS." user ON feed.feed_by=user.id
                         where feed_like.like_by=".$user_id." and feed_like.feed_id= ". $val['resource_id']." and feed_like.is_delete=0 and feed.is_delete=0 and user.is_delete=0";
                        $resultFeedLike=mysqli_query($GLOBALS['con'], $queryFeedLike) or $message= mysqli_error($GLOBALS['con']);


                        $feeds=array();
                        if(mysqli_num_rows($resultFeedLike))
                        {
                            while($feed=mysqli_fetch_assoc($resultFeedLike))
                            {
                                $feeds['feed_id']=$feed['id'];
                                $feeds['feed_text']=$feed['feed_text'];
                                $feeds['video_link']=$feed['video_link'];
                                $feeds['user_id']=$feed['UserId'];
                                $feeds['full_name']=$feed['full_name'];
                                $feeds['profile_pic']=$feed['Profile_pic'];
                                $feeds['total_like']=$feed['total_like'];
                                $feeds['total_comment']=$feed['total_comment'];
                                //Get Comments
                                $queryGetAllComments = "SELECT f.id,f.comment ,f.comment_by,f.created_date,u.full_name,p.profile_link as 'profile_pic' FROM ".TABLE_FEED_COMMENT." f INNER JOIN ".TABLE_USERS." u
            ON f.comment_by=u.id INNER JOIN ".TABLE_USER_PROFILE_PICTURE." p ON p.user_id=u.id WHERE f.feed_id=".$feed['id'] ." AND f.is_delete=0 AND u.is_delete=0 AND p.is_delete=0 LIMIT 2";
                                //echo $queryGetAllComments;
                                $resultGetAlComments = mysqli_query($GLOBALS['con'], $queryGetAllComments) or $message =  mysqli_error($GLOBALS['con']);
                                $allcomment=array();

                                if(mysqli_num_rows($resultGetAlComments))
                                {
                                    while($comments=mysqli_fetch_assoc($resultGetAlComments))
                                    {
                                        $allcomment[]=$comments;
                                    }
                                }
                                $feeds['comment_list']=$allcomment;

                            }
                            $post['feed_liked']=$feeds;
                        }
                        /*else{
                            $post['feedLiked']=array();
                        }*/

                    }
                    if($val['activity_type'] = 'studymate')
                    {
                       $queryGetStudyMate="SELECT studymates.mate_id,users.full_name,users.profile_pic,school.school_name,student_profile.total_authors_followed from studymates studymates
                        INNER JOIN users users on studymates.mate_id=users.id
                        INNER JOIN ".TABLE_STUDENT_PROFILE ." student_profile ON student_profile.user_id=studymates.mate_id
                        INNER JOIN ". TABLE_SCHOOLS." school ON  school.id=student_profile.school_id
                        where studymates.is_delete=0 and studymates.mate_of=".$user_id." and studymates.mate_id=".$val['resource_id'];
                        //$queryGetStudyMate="SELECT studymates.mate_id,users.full_name,users.profile_pic from ".TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users on studymates.mate_id=users.id where is_delete=0 and id=".$val['resource_id']." AND mate_id=".$user_id;
                        $resultGetStudyMate=mysqli_query($GLOBALS['con'], $queryGetStudyMate) or $message= mysqli_error($GLOBALS['con']);


                        $studymate=array();
                        if(mysqli_num_rows($resultGetStudyMate))
                        {
                            while($studymateRow=mysqli_fetch_assoc($resultGetStudyMate))
                            {
                                //$studymate[]=$studymateRow;

                                $post['studymate_id']=$studymateRow['mate_id'];
                                $post['studymate_name']=$studymateRow['full_name'];
                                $post['studymate_profile_pic']=$studymateRow['profile_pic'];
                                $post['studymate_school_name']=$studymateRow['school_name'];
                                $post['total_authors_followed']=$studymateRow['total_authors_followed'];

                            }
                            //$post['studymates']=$studymate;
                        }
                        /*else
                        {
                            $post['studymates']=array();
                        }*/


                    }

                    if($val['activity_type'] = 'commented') {
                        $queryGetComment = "SELECT f.id,f.comment ,f.comment_by,f.created_date,u.full_name,p.profile_link as 'profile_pic' FROM " . TABLE_FEED_COMMENT . " f INNER JOIN " . TABLE_USERS . " u
            ON f.comment_by=u.id INNER JOIN " . TABLE_USER_PROFILE_PICTURE . " p ON p.user_id=u.id WHERE f.id=" . $val['resource_id'] . " AND comment_by=" . $user_id . " AND f.is_delete=0
            AND u.is_delete=0 AND p.is_delete=0 ORDER BY f.id DESC LIMIT 2";
                        //echo $queryGetAllComments;
                        $resultGetComment = mysqli_query($GLOBALS['con'], $queryGetComment) or $message =  mysqli_error($GLOBALS['con']);

                        $comment = array();
                        if (mysqli_num_rows($resultGetComment)) {
                            while ($commentRow = mysqli_fetch_assoc($resultGetComment)) {
                                $comment[] = $commentRow;
                            }
                            $post['comment_added'] = $comment;
                        }
                        /*  else
                          {
                              $post['comment_added']=array();
                          }*/

                    }
                    if($val['activity_type'] = 'post')
                    {

                        $queryFeedLike="select feed.*,user.id as 'UserId',user.full_name,user.profile_pic as 'Profile_pic' from ".TABLE_FEEDS." feed
                        INNER JOIN ".TABLE_USERS." user ON feed.feed_by=user.id
                         where feed.feed_by=".$user_id." and feed.id= ". $val['resource_id']." and feed.is_delete=0 and user.is_delete=0";
                        $resultFeedLike=mysqli_query($GLOBALS['con'], $queryFeedLike) or $message= mysqli_error($GLOBALS['con']);


                        $feeds=array();
                        if(mysqli_num_rows($resultFeedLike))
                        {
                            while($feed=mysqli_fetch_assoc($resultFeedLike))
                            {
                                $feeds['feed_id']=$feed['id'];
                                $feeds['feed_text']=$feed['feed_text'];
                                $feeds['video_link']=$feed['video_link'];
                                $feeds['user_id']=$feed['UserId'];
                                $feeds['full_name']=$feed['full_name'];
                                $feeds['profile_pic']=$feed['Profile_pic'];
                                $feeds['total_like']=$feed['total_like'];
                                $feeds['total_comment']=$feed['total_comment'];
                                //Get Comments
                                $queryGetAllComments = "SELECT f.id,f.comment ,f.comment_by,f.created_date,u.full_name,p.profile_link as 'profile_pic' FROM ".TABLE_FEED_COMMENT." f INNER JOIN ".TABLE_USERS." u
            ON f.comment_by=u.id INNER JOIN ".TABLE_USER_PROFILE_PICTURE." p ON p.user_id=u.id WHERE f.feed_id=".$feed['id'] ." AND f.is_delete=0 AND u.is_delete=0 AND p.is_delete=0 ORDER BY f.id DESC LIMIT 2";
                                //echo $queryGetAllComments;
                                $resultGetAlComments = mysqli_query($GLOBALS['con'], $queryGetAllComments) or $message =  mysqli_error($GLOBALS['con']);
                                $allcomments=array();

                                if(mysqli_num_rows($resultGetAlComments))
                                {
                                    while($comments=mysqli_fetch_assoc($resultGetAlComments))
                                    {
                                        $allcomments[]=$comments;
                                    }
                                }
                                $feeds['comment_list']=$allcomments;

                            }
                            $post['feedPosted']=$feeds;
                        }
                        /* else
                         {
                             $post['feedPosted']=array();
                         }*/

                    }


                    $data[]=$post;
                    //$data['resource']=$post;

                }
                $message = "";
                $status = SUCCESS;
            } else {
                $status = FAILED;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['user_activities'][]=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }


    public function refreshToken($postData)
    {
        $message ='';
        $data=array();
        $response=array();


        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);


//        $secret_key = validateObject($postData, 'secret_key', "");
//        $secret_key = addslashes($secret_key);

        $secret_key=NULL;


        $security=new SecurityFunctions();

        $isSecure = $security->checkForSecurityTest($access_key,$secret_key);



        /*if($isSecure==yes) {

            $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='globalPassword' AND is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            $masterKey = mysqli_fetch_row($result);

            $crypterSecurity = new Security();
            $decrypted_username = $crypterSecurity->decrypt($access_key, $masterKey[0]);


            $queryToGetUserId = "SELECT id FROM " . TABLE_USERS . " WHERE username='" . $decrypted_username . "' AND is_delete=0";
            $resultToGetUserId = mysqli_query($GLOBALS['con'], $queryToGetUserId) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultToGetUserId) > 0) {
                $user_id = mysqli_fetch_row($resultToGetUserId);

                //Generate Token

                $generateToken = $security->generateToken(8);


                $insertTokenField = "`user_id`, `token`";
                $insertTokenValue = "" . $user_id[0] . ",'" . $generateToken . "'";

                $queryToCheckRecordExist = "SELECT * FROM " . TABLE_TOKENS . " WHERE user_id=" . $user_id[0] . " AND is_delete=0";
                $resultToCheckRecordExist = mysqli_query($GLOBALS['con'], $queryToCheckRecordExist) or $message = mysqli_error($GLOBALS['con']);
                $rowRecord = mysqli_fetch_row($resultToCheckRecordExist);
                $tokenName = $rowRecord[2];

                if (mysqli_num_rows($resultToCheckRecordExist) == 0) {
                    $queryAddToken = "INSERT INTO " . TABLE_TOKENS . "(" . $insertTokenField . ") values (" . $insertTokenValue . ")";
                    $resultAddToken = mysqli_query($GLOBALS['con'], $queryAddToken) or $message = mysqli_error($GLOBALS['con']);

                    //===================Call AES Encrypt Function=======================

                    $secret_key = $crypterSecurity->encrypt($generateToken, $decrypted_username);
                    //**************************End Encryption***************************

                    if ($resultAddToken) {
                        $data['token_name'] = $secret_key;
                        $data['username'] = $access_key;
                        $status = SUCCESS;
                        $message = "Token key generated.";
                    } else {
                        $status = FAILED;
                        $message = "Failed to generate token.";
                    }
                } else {
                    $status = SUCCESS;
                    $message = RECORD_ALREADY_EXIST;
                    $tokenName = $crypterSecurity->encrypt($tokenName, $decrypted_username);
                    $data['token_name'] = $tokenName;
                }
            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;

            }
        }*/


        if($isSecure != no) {

            if ($isSecure == allowaccesstoapp) {

                $post[]['token_name'] = $isSecure;
                $response['token'] = $post;
                $response['status'] = SUCCESS;
                $response['message'] = "Temp token generated";

            } else {

                $response = $isSecure;
            }
        }
        else
        {
            $post[]['token_name'] = array();
            $response['token']=$post;
            $response['status']=FAILED;
            $response['message'] = MALICIOUS_SOURCE;

        }

        return $response;
//        $response['message'] = $message;
//        $response['status'] = $status;

    }


    /*
  * getConfigData
   */
    public function getAdminConfig($postData)
    {
        $status='';
        $message='';
        $data=array();
        $response=array();

        $last_sync_date = validateObject($postData, 'last_sync_date', "");
        $last_sync_date = addslashes($last_sync_date);

        $role = validateObject($postData, 'role', "");
        $role = addslashes($role);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurityTest($access_key,$secret_key);

//        if($isSecure != no)
//        {
//            if($isSecure != yes)
//            {
//
//            }
//        }

//        if($role=='Student')
//        {
//            $scope='Student';
//        }
//        elseif($role=='Author')
//        {
//            $scope='Author';
//        }
//        elseif($role=='Admin')
//        {
//            $scope='Admin';
//        }
//        elseif($role=='Teacher')
//        {
//            $scope='Teacher';
//        }
//        elseif($role=='All')
//        {
//            $scope='All';
//        }
        //else
        if($role == NULL)
        {
            $role='All';
        }

        if($isSecure != no) {

            //  if ($last_sync_date < date("Y-m-d")) {
            // $last_sync_date=date("Y-m-d");
            //$condition=" and date(created_date,'Y-M-d') < '".$last_sync_date."'";
            //$condition=" and DATE_FORMAT(`modified_date`, 'Y-m-d') > '".$last_sync_date."'";

            if($last_sync_date!=NULL)
             {
                $condition = " and `modified_date` > '" . $last_sync_date . "'";
            }
            $query = "SELECT config_key,config_value,value_unit FROM " . TABLE_ADMIN_CONFIG . " WHERE  scope='" . $role . "' and is_delete=0". $condition;
            //echo $query;
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $data[]=$row;
                }

                $status = SUCCESS;
            }
            else{
                $status=SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
            // }
        }
        else{
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
            $data="";
        }


        // $response['last_sync_date'] = $last_sync_date;
        $response['admin_config']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

    public function unBlockUser($postData)
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);


        if ($isSecure == yes) {

            //Check If UserID is not Present, then Find id from EmailID.
            if ($block_user == NULL || $block_user == 0) {
                $selectQuery = "SELECT id FROM ".TABLE_USERS." WHERE email_id='".$email_id."' AND is_delete=0";
                $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);
                $getUserId = mysqli_fetch_row($resultQuery);
                $block_user_id = $getUserId[0];

            }
            else
            {
                $block_user_id=$block_user;
            }

            if ($user_id != NULL || $block_user != NULL || $email_id != NULL) {

                //Find UserId and Block User from StudyMate table
                $queryFindStudyMate = "SELECT * FROM ".TABLE_STUDYMATES." WHERE mate_id=".$block_user_id." AND mate_of=".$user_id." AND (status='block') AND is_delete=0";
                //echo $queryFindStudyMate;
                $resultFindStudyMate = mysqli_query($GLOBALS['con'], $queryFindStudyMate) or $message = mysqli_error($GLOBALS['con']);

                if (mysqli_num_rows($resultFindStudyMate) > 0) {
                    $updateQuery = "UPDATE " . TABLE_STUDYMATES . " SET status='friend' WHERE mate_id=" . $block_user_id . " and mate_of=" . $user_id;
                    $updateResult = mysqli_query($GLOBALS['con'], $updateQuery) or $message = mysqli_error($GLOBALS['con']);
                    $message = "user is unblocked";
                    $status = SUCCESS;
                }
                else{
                    $status = SUCCESS;
                    $message = DEFAULT_NO_RECORDS;
                }

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['unblock_user']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }


    public function uploadProfileImages($postData)
    {

        $response=array();
        $status = '';
        $message = '';
        $created_date = date("Ymd-His");

        $user_id = $_POST['user_id'];

        $secret_key = $_POST['secret_key'];
        $access_key = $_POST['access_key'];

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $selectQuery="SELECT id FROM ".TABLE_USERS." WHERE id= ".$user_id;
            $result=mysqli_query($GLOBALS['con'], $selectQuery) or $message =mysqli_error($GLOBALS['con']);


            for ($i = 0; $i < count($_FILES["media_images"]["name"]); $i++) {
                if ($_FILES["media_images"]["name"][$i] != '') { // don't insert if file name empty

                    $file_ext=explode('.',$_FILES['media_images']['name'][$i])	;
                    $file_ext=end($file_ext);
                    $file_ext=strtolower(end(explode('.',$_FILES['media_images']['name'][$i])));

                    $mediaName = "_test_".$i."_IMAGE_" . $created_date . ".";
                    $fileName =  $mediaName .$file_ext ;



                    $user_media_dir = "user_" . $user_id . "/";

                   $target_path1 =  USER_PROFILE_PICTURE . $user_media_dir;

                    if(is_dir($target_path1)==false){
                        $target_path1 =  USER_PROFILE_PICTURE . $user_media_dir;
                        mkdir($target_path1, 0777);		// Create directory if it does not exist
                         $target_path =  USER_PROFILE_PICTURE . $user_media_dir . $fileName;
                    }
                    else
                    {
                        if (file_exists($target_path1)) {
                         //  echo "dir=".$user_media_dir;

                            //$target_path1 =  USER_PROFILE_PICTURE . $user_media_dir;
                            //chmod($target_path1,0777);

                            $user_media_dir = "user_" . $user_id."/" ;
                            $target_path =  USER_PROFILE_PICTURE . $user_media_dir . $fileName;
                            (@fopen($target_path,"r")==true);

                        }

                    }

//                    $target_path =  USER_PROFILE_PICTURE . $user_media_dir . $fileName;
                    if (move_uploaded_file($_FILES["media_images"]["tmp_name"][$i], $target_path)) {    // The file is in the images/gallery folder.

                        // Insert record into database by executing the following query:
                        $queryProfileImage = "INSERT INTO " . TABLE_USER_PROFILE_PICTURE . "(`user_id`, `profile_link`) VALUES (" . $user_id . ",'" . $fileName . "')";
                        $result=mysqli_query($GLOBALS['con'], $queryProfileImage) or $message =mysqli_error($GLOBALS['con']);

                        if($result)
                        {
                            mysqli_free_result($result);
                            $images[]=$fileName;
                        }
                        else{
                            $status = FAILED;
                            $message = FAILED_TO_UPLOAD_MEDIA;
                        }
//                        if($result = mysqli_query($GLOBALS['con'], $queryProfileImage)){
//                                $images[]=$fileName;
//                            mysqli_free_result($result);
//
//                        } else {
//                            $status = FAILED;
//                            $message = FAILED_TO_UPLOAD_MEDIA;
//                            //die(mysqli_error($GLOBALS['con']));
//                        }

                        $data['profile_images']=$images;
                        $status = SUCCESS;
                        $message = "successfully uploaded";

                    } else {
                        $message= "There was an error uploading the file " . $_FILES['media_images']['name'][$i] . ", please try again!";
                    }

                }
            }
            $procedure_to_get_profile_pic= mysqli_query($GLOBALS['con'],"CALL GET_USER_PROFILE_PIC($user_id);") or $message=mysqli_error($GLOBALS['con']);

            if($procedure_to_get_profile_pic)
                mysqli_free_result($procedure_to_get_profile_pic);
            $currentUserPic=mysqli_fetch_row($procedure_to_get_profile_pic);


            $queryUpdate = "UPDATE " . TABLE_USERS . " SET profile_pic='" . $currentUserPic[0] . "' WHERE id=" . $user_id;
            $resultQuery = mysqli_query($GLOBALS['con'], $queryUpdate);// or $message = mysqli_error($GLOBALS['con']);

            if($resultQuery)
            {
                mysqli_free_result($resultQuery);
            }
//            if($currentUserPic=mysqli_fetch_row($procedure_to_get_profile_pic)) {
//
//
//                $queryUpdate = "UPDATE " . TABLE_USERS . " SET profile_pic='" . $currentUserPic[0] . "' WHERE id=" . $user_id;
//                mysqli_free_result($currentUserPic);
//            }
//
//            if($resultQuery = mysqli_query($GLOBALS['con'], $queryUpdate)) {
//                mysqli_free_result($resultQuery);
//            }
//            else{
//                $message="hie yes ".mysqli_error($GLOBALS['con']);
//            }
        }

        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }


        $response['user_images']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }


    /*
    * getMyFollowers
    */
    public function getMyFollowers($postData)
    {
        $message ='';
        $status='';
        $data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            //$selectQuery = "SELECT followers.follower_id as 'follower_id',users.full_name,users.profile_pic FROM ".TABLE_FOLLOWERS." followers INNER JOIN ".TABLE_USERS." users ON followers.follower_id=users.id WHERE followers.follow_to=". $user_id ." AND followers.follow_status='followed' AND followers.is_delete=0";
                 $selectQuery = "SELECT followers.follower_id as 'follower_id',users.full_name,users.profile_pic,country.country_name,school.school_name FROM ".TABLE_FOLLOWERS." followers INNER JOIN ".TABLE_USERS." users ON followers.follower_id=users.id LEFT JOIN ".TABLE_COUNTRIES." country ON users.country_id=country.id LEFT JOIN ". TABLE_STUDENT_PROFILE." student_proile ON followers.follower_id=student_proile.user_id LEFT JOIN ".TABLE_SCHOOLS." school ON student_proile.school_id=school.id WHERE followers.follow_to=". $user_id ." AND followers.follow_status='followed' AND followers.is_delete=0";
                $selResult = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

                if (mysqli_num_rows($selResult) > 0) {
                    while ($row = mysqli_fetch_assoc($selResult)) {
                        $post['follower_id']=$row['follower_id'];
                        $post['follower_name']=$row['full_name'];
                        $post['follower_profile_pic']=$row['profile_pic'];
                        $post['follower_country_name']=$row['country_name'];
                        $post['follower_school']=$row['school_name'];

                         $queryGetAuthorFollower="SELECT follow_to FROM ".TABLE_FOLLOWERS." WHERE `follower_id`=".$row['follower_id']." AND follow_status='followed' AND is_delete=0";
                        $resultGetAuthorFollower= mysqli_query($GLOBALS['con'], $queryGetAuthorFollower) or $message = mysqli_error($GLOBALS['con']);


                        $count=0;
                        if(mysqli_num_rows($resultGetAuthorFollower))
                        {
                            while ($row1 = mysqli_fetch_assoc($resultGetAuthorFollower))
                            {
                                //echo $getCountQuery="SELECT followers.count(*) FROM  ".TABLE_FOLLOWERS." followers LEFT JOIN ".TABLE_AUTHOR_PROFILE." author_profile ON followers.follow_to=author_profile.id AND followers.follow_to=".$row1['follow_to']." AND followers.follow_status='followed'";
                                $getCountQuery="SELECT user_id FROM author_profile WHERE user_id IN ( SELECT follow_to FROM followers WHERE follow_to=".$row1['follow_to'].")" ;
                                $getCountResult = mysqli_query($GLOBALS['con'], $getCountQuery) or $message = mysqli_error($GLOBALS['con']);
                                $count+=mysqli_num_rows($getCountResult);
                            }
                            $post['number_of_author_followed']=$count;
                        }
                        else
                        {
                            $post['number_of_author_followed']=0;
                        }

                        $data[]=$post;
                    }

                    $message = "Followers listed";
                }
                else {
                    $message = DEFAULT_NO_RECORDS;
                }
                $status = SUCCESS;
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['followers']=$data;
        $response['status']=$status;
        $response['message']=$message;

        return $response;
    }

        /*
       * getRecommendedAuthors
       */
    public function getRecommendedAuthors($postData)
    {
        $message ='';
        $status='';
        $data = array();
        $response = array();

        $author_id = validateObject($postData, 'author_id', "");
        $author_id = addslashes($author_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {


            $selectQuery = "SELECT users.full_name,users.profile_pic,author_profile.* FROM ".TABLE_USERS." users INNER JOIN ".TABLE_AUTHOR_PROFILE." author_profile ON author_profile.user_id=users.id WHERE users.role_id=4 AND author_profile.is_delete=0 AND users.is_delete=0";
            $selectResult = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($selectResult) > 0) {
                while ($row = mysqli_fetch_assoc($selectResult)) {
                    $post['author_id']=$row['user_id'];
                    $post['author_name']=$row['full_name'];
                    $post['author_pic']=$row['profile_pic'];
                    $post['total_books']=$row['total_books'];
                    $post['about_author']=$row['about_author'];
                    $post['education']=$row['education'];
                    $post['total_followers']=$row['total_followers'];
                    $post['terms_link']=$row['terms_link'];
                    $data[]=$post;
                }

                $message = "Authors listed";
            }
            else {
                $message = DEFAULT_NO_RECORDS;
            }
            $status = SUCCESS;
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['author']=$data;
        $response['status']=$status;
        $response['message']=$message;

        return $response;
    }


    /*
     * getReportData
     */
    public function getReportData($postData)
    {
        $message ='';
        $status='';
        $data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $role_id = validateObject($postData, 'role_id', "");
        $role_id = addslashes($role_id);

        $last_sync_date = validateObject($postData, 'last_sync_date', "");
        $last_sync_date = addslashes($last_sync_date);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if ($isSecure == yes) {

            if ($last_sync_date != NULL) {
                $condition = " and `modified_date` > '" . $last_sync_date . "'";
            }

            if ($role_id == 3) {
                $queryToGetSchoolClassroomId = "SELECT id FROM " . TABLE_SCHOOL_CLASSROOM . " WHERE class_incharge=" . $user_id . " AND is_delete=0";
            }
            elseif($role_id==2)
            {
                  $queryToGetSchoolClassroomId = "SELECT school_classroom_id as 'id' FROM " . TABLE_STUDENT_PROFILE . " WHERE user_id=" . $user_id . " AND is_delete=0";
            }

                $resultToGetSchoolClassroomId = mysqli_query($GLOBALS['con'], $queryToGetSchoolClassroomId) or $message = mysqli_error($GLOBALS['con']);

                if (mysqli_num_rows($resultToGetSchoolClassroomId)>0) {
                    while ($rowId = mysqli_fetch_assoc($resultToGetSchoolClassroomId)) {
                        $queryToExamId = "SELECT * FROM " . TABLE_EXAM_PROFILE . " WHERE school_classroom_id=" . $rowId['id'] . " AND NOT academic_exam_subject_id =0 AND is_delete=0";
                        $resultToExamId = mysqli_query($GLOBALS['con'], $queryToExamId) or $message = mysqli_error($GLOBALS['con']);

                        if (mysqli_num_rows($resultToExamId)>0) {
                            while ($rowExamProfile = mysqli_fetch_assoc($resultToExamId)) {

                                $queryToGetAcademicExam = "SELECT * FROM " . TABLE_ACADEMIC_EXAM_SUBJECT . " WHERE id=" . $rowExamProfile['academic_exam_subject_id'] . " AND is_delete=0";
                                $resultToGetAcademicExam = mysqli_query($GLOBALS['con'], $queryToGetAcademicExam) or $message = mysqli_error($GLOBALS['con']);

                                if (mysqli_num_rows($resultToGetAcademicExam)) {
                                    while ($rowExamDetails = mysqli_fetch_assoc($resultToGetAcademicExam)) {

                                         $queryToGetAcademicExamDetails = "SELECT * FROM " . TABLE_ACADEMIC_EXAM . " WHERE id=" . $rowExamDetails['academic_exam_id'] . " AND is_delete=0";

                                        $resultToGetAcademicExamDetails = mysqli_query($GLOBALS['con'], $queryToGetAcademicExamDetails) or $message = mysqli_error($GLOBALS['con']);
                                        $exams = array();
                                        if (mysqli_num_rows($resultToGetAcademicExamDetails)) {

                                            while ($rowAcademicExam = mysqli_fetch_assoc($resultToGetAcademicExamDetails)) {

                                                $exams['exam_name'] = $rowAcademicExam['exam_name'];

                                                 //  $queryToGetAverageResult="SELECT ";

                                                $exams['exam_score'] = $rowAcademicExam['exam_score'];
                                                $exams['internal_marks'] = $rowAcademicExam['internal_exam_score'];


                                               // $queryToGetAllData = "SELECT student_result.* FROM " . TABLE_STUDENT_RESULT . " student_result INNER JOIN " . TABLE_USERS . " users  ON student_result.user_id=users.id WHERE student_result.academic_exam_id=" . $rowAcademicExam['id'];//$rowExamProfile['exam_id'] . "";

                                                  $queryToGetAllData = "SELECT student_exam_score.user_id,users.full_name,users.profile_pic,student_result.percentage as 'per',student_result.* FROM " . TABLE_STUDENT_EXAM_SCORE . " student_exam_score INNER JOIN " . TABLE_USERS . " users ON student_exam_score.user_id=users.id  JOIN " . TABLE_STUDENT_RESULT . " student_result ON student_exam_score.user_id=student_result.user_id WHERE student_exam_score.exam_id=" . $rowExamProfile['exam_id'] ;
                                                $resultToGetAllData = mysqli_query($GLOBALS['con'], $queryToGetAllData) or $message = mysqli_error($GLOBALS['con']);

                                                $students=array();

                                                if (mysqli_num_rows($resultToGetAllData)) {
                                                    while ($val = mysqli_fetch_assoc($resultToGetAllData)) {
                                                        $student = array();
                                                        $student['student_id'] = $val['user_id'];
                                                        $student['student_name'] = $val['full_name'];
                                                        $student['student_pic'] = $val['profile_pic'];
                                                        $student['student_score'] = $val['total_score_obtained'];
                                                        $student['percentage'] = $val['per'];
                                                        $student['rank'] = $val['class_rank'];
                                                        $student['grade'] = $val['grade'];
                                                        $student['head_mistress_comment'] = $val['head_mistress_remark'];
                                                        $student['class_mistress_comment'] = $val['class_mistress_remark'];


                                                        //$queryToGetSubjects="SELECT academic_subject.* FROM ".TABLE_ACADEMIC_EXAM_SUBJECT." academic_subject JOIN ".TABLE_STUDENT_RESULT." student_result ON academic_subject.academic_exam_id=student_result.academic_exam_id ";//WHERE student_result.user_id=";
                                                         $queryToGetSubjects="SELECT student_exam_score.*,exams.subject_id,subjects.subject_name FROM ".TABLE_STUDENT_EXAM_SCORE." student_exam_score JOIN ".TABLE_EXAMS." exams ON student_exam_score.exam_id=exams.id LEFT JOIN ".TABLE_SUBJECTS." subjects ON exams.subject_id=subjects.id WHERE student_exam_score.user_id=".$student['student_id'];// ." and student_exam_score.exam_id=" . $rowExamProfile['exam_id'] ;
                                                        $resultToGetSubjects= mysqli_query($GLOBALS['con'], $queryToGetSubjects) or $message = mysqli_error($GLOBALS['con']);
                                                        $subjects=array();
                                                        if(mysqli_num_rows($resultToGetSubjects)>0)
                                                        {
                                                            while($sub=mysqli_fetch_assoc($resultToGetSubjects))
                                                            {
                                                                $subject['subject_id']=$sub['subject_id'];
                                                                $subject['subject_name']=$sub['subject_name'];
                                                                $subject['marks_obtained']=$sub['marks_obtained'];
                                                                $subject['percentage']=$sub['percentage'];
                                                                $subject['subject_grade']=$sub['grade_obtained'];
                                                                $subject['remarks']=$sub['remarks'];
                                                                $subject['subject_rank']=$sub['subject_rank'];
                                                                $subject['internal_score']=$sub['internal_score'];

                                                                $subjects[]=$subject;
                                                            }
                                                            $student['subject_wise_score']=$subjects;
                                                        }
                                                        else
                                                        {
                                                            $status=SUCCESS;
                                                            $message = "subjects not found";
                                                            $subjects=array();
                                                        }

                                                        $students[]=$student;
                                                    }
                                                    $exams['students_score'] = $students;
                                                }
                                                else
                                                {
                                                    $status=SUCCESS;
                                                    $message = "students not found";
                                                    $students=array();
                                                }
                                            }
                                            $post[] = $exams;

                                        }
                                        else
                                        {
                                            $status=SUCCESS;
                                            $message = "academic exams not found";
                                        }
                                    }
                                }
                                else
                                {
                                    $status=SUCCESS;
                                    $message = "academic subjects not found";
                                }
                            }
                        }
                        else
                        {
                            $status=SUCCESS;
                            $message = "exams not found";
                        }
                    }
                    $data[]=$post;
                    $status=SUCCESS;
                    $message = "report generated";
                }
            else{
                $status=SUCCESS;
                $message = DEFAULT_NO_RECORDS;

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['class_performance']=$data;
        $response['status']=$status;
        $response['message']=$message;

        return $response;
    }

}
?>