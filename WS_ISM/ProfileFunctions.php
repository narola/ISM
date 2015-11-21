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
<<<<<<< HEAD
        }
    }

    /*
    * uploadUserProfilePic
    */

    public function uploadUserProfilePic ($postData)
    {
        $data = array();
        $response = array();
        if (!is_dir(USER_PROFILE_PICTURE))
            mkdir(USER_PROFILE_PICTURE, 0777, true);

        $user_id = $_POST['user_id'];
      // $filedetails = explode('_',$_FILES['mediaFile']['name']);
        //Image Saving
      //  $user_id=$filedetails[1];
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
            if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                //store image data.
                $link=$profile_user_link . $mediaName;

                $queryProfileImage="INSERT INTO ".TABLE_USER_PROFILE_PICTURE."(`user_id`, `profile_link`) VALUES (".$user_id.",'".$link."')";
                $resultProfileImage=mysql_query($queryProfileImage) or $message=mysql_error();
                if($resultProfileImage){
                    $query="Select full_name,profile_pic from ".TABLE_USERS." where id=".$user_id;
                    $result=mysql_query($query) or $message=mysql_error();
                    if(mysql_num_rows($result)){
                        $val=mysql_fetch_assoc($result);
                        $data['user_id']=$user_id;
                        $data['full_name']=$val['full_name'];
                        $data['profile_pic']=$val['profile_pic'];
                        $status = "success";
                        $message = "Successfully uploaded!.";

                    }
                    else{
                        $status = "failed";
                        //$message = "";
                    }

                }
                else{
                    $status = "failed";
                    //  $message = "Failed to upload media on server.";
                }

            } else {
                $status = "failed";
                //$message = "Failed to upload media on server.";
=======
                
            case "GenerateVoucher":
            {
                return $this->generateVoucher($postData);
>>>>>>> 95d0158d17d7bc405bb57119f7db1db76fc38b82
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
               
        }
<<<<<<< HEAD

        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message; //$response['message']=$message."--".$user_id.$_FILES["mediaFile"]["name"];
        return $response;

=======
>>>>>>> 95d0158d17d7bc405bb57119f7db1db76fc38b82
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
        $response['data']=$data;
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
        $response['data']=$data;

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
        $response['data']=$data;
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
        $response['data']=$post;
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
        $response['data']=$post;
        return $response;

    }

    public function authenticateUser($postData)
    {

        $data=array();
        $data['data']=array();
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
                {$post=array();
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

        array_push($data['data'],$post);
        $data['message'] = $message;
        $data['status'] = $status;

        return $data;
    }

    public function registerUser($postData)
    {

        $data=array();
        $response=array();
        $response['data']=array();


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

<<<<<<< HEAD

        //code for base64
=======
>>>>>>> 95d0158d17d7bc405bb57119f7db1db76fc38b82
        $profile_image = validateObject ($postData , 'profile_image', "");
        $profile_image_name = validateObject ($postData , 'profile_image_name', "");
        $profile_image_name = addslashes($profile_image_name);
        $profile_image_name_array=explode(".",$profile_image_name);
        if (!is_dir(USER_PROFILE_PICTURE)) {
            mkdir(USER_PROFILE_PICTURE, 0777, true);
        }
<<<<<<< HEAD
//        if (!mkdir(USER_PROFILE_PICTURE, 0777, true)) {
//            die('Failed to create folders...'.USER_PROFILE_PICTURE);
//        }
        // echo $profile_image_name_array[0]."_test.".$profile_image_name_array[1];
=======
       // if (!mkdir(USER_PROFILE_PICTURE, 0777, true)) {
           // die('Failed to create folders...'.USER_PROFILE_PICTURE);
       // }
        // echo $profile_image_name_array[0]."_test.".$profile_image_name_array[1];
        
>>>>>>> 95d0158d17d7bc405bb57119f7db1db76fc38b82
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

                $insertAcademicField="`user_id`, `school_id`, `classroom_id`, `academic_year`, `course_id`";
                $insertAcademicValue="'".$user_id."', '".$school_id."', '".$classroom_id."', '".$academic_year."', '".$course_id."'";


<<<<<<< HEAD
//                //Image Saving
=======
                //Image Saving
>>>>>>> 95d0158d17d7bc405bb57119f7db1db76fc38b82
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
<<<<<<< HEAD


=======
>>>>>>> 95d0158d17d7bc405bb57119f7db1db76fc38b82
                $queryAcademic="INSERT INTO ".TABLE_STUDENT_ACADEMIC_INFO."(".$insertAcademicField.") values (".$insertAcademicValue.")";
                $resultAcademic=mysql_query($queryAcademic) or $message=mysql_error();
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

                }

            }
        }
        else
        {
            $status="failed";

        }
        $response['data']=$data;
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


		$response['data']=$data;
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
        
        $percent_value=$balance -(($balance*$voucher_config_value)/100);
       
       
       //Generate random String for Voucher Code
       
    	$chars ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";//length:36
    	$final_rand='';
    	for($i=0;$i<6; $i++)
    	{
       		 $final_rand .= $chars[ rand(0,strlen($chars)-1)];
   		}
       
       //Check, if Voucher_amont is greater than Count value or not
       		if($voucher_amount > $percent_value)
      	 	{
       	  		$insertFields="`user_id`,`voucher_code`,`voucher_amount`";
          		$insertValues="".$user_id.",'".$final_rand."',".$voucher_amount."";
           
          		$queryInsert="INSERT INTO ".TABLE_USER_VOUCHER."(".$insertFields.") values(".$insertValues.")";
          		//echo $queryInsert; 
         		$resultQuery=mysql_query($queryInsert) or $message=mysql_error();
      	  		if($resultQuery)
          		{
            			$data['voucher_code']=$final_rand;
            			$data['voucher_amount']=$voucher_amount;
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
        $response['data']=$data;
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
        
        $selectQuery="SELECT j.school_name,j.class_name,student_profile.*,users.id,users.full_name,users.profile_pic,users.contact_number,users.birthdate,users.about_me 
        FROM ".TABLE_USERS." users INNER JOIN ".TABLE_STUDENT_PROFILE." student_profile ON users.id=student_profile.user_id
        INNER JOIN ( SELECT sa.user_id,s.school_name,c.class_name FROM ".TABLE_STUDENT_ACADEMIC_INFO." sa 
        JOIN ".TABLE_SCHOOLS." s ON s.id=sa.school_id 
        JOIN ".TABLE_CLASSROOMS." c ON c.id=sa.classroom_id WHERE sa.user_id=1 )j ON j.user_id=users.id
        WHERE student_profile.user_id=".$user_id;
        
        //SELECT s.school_name,c.class_name FROM `student_academic_info` sa JOIN schools s ON s.id=sa.school_id JOIN classrooms c ON c.id=sa.classroom_id WHERE sa.user_id=1
        $resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        
        if(mysql_num_rows($resultQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultQuery))
                {
                	$post=array();
                	$post['user_id']=$val['id'];
                	$post['username']=$val['full_name'];
                	$post['user_profile_pic']=$val['profile_pic'];
                	$post['school_name']=$val['school_name'];
                	$post['course_name']=$val['class_name'];
                	$post['contact_number']=$val['contact_number'];
                	$post['birthdate']=$val['birthdate'];
                	$post['aboutMeText']=$val['about_me'];
                	$post['ambitionInLife']=$val['ambitionInLife'];
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
        $response['data']=$data;
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
        
        $user_profile_pic = validateObject($postData, 'user_profile_pic', "");
        $user_profile_pic = addslashes($user_profile_pic);
        
        $contact_number = validateObject($postData, 'contact_number', "");
        $contact_number = addslashes($contact_number);
        
        $birthdate = validateObject($postData, 'birthdate', "");
        //$birthdate = addslashes($user_id);
        
        $aboutMeText = validateObject($postData, 'aboutMeText', "");
        //$aboutMeText = addslashes($user_id);
    	
    	$ambitionInLife = validateObject($postData, 'ambitionInLife', "");
        $ambitionInLife = addslashes($ambitionInLife);
    	
    	$updateFeild="full_name='".$username."', profile_pic='".$user_profile_pic."', contact_number='".$contact_number."', birthdate='".$birthdate."', about_me='".$aboutMeText."'";
    	
    		$queryUpdate="UPDATE ".TABLE_USERS." SET ".$updateFeild ." WHERE id=".$user_id;
    		$resultQuery=mysql_query($queryUpdate) or $message=mysql_error();
    		
    	 if($resultQuery)
    	 {
    		if($ambitionInLife != NULL)
    		{
    			$queryToUpdateAmbition="UPDATE ".TABLE_STUDENT_PROFILE." SET ambitionInLife='".$ambitionInLife."' WHERE user_id=".$user_id;
    			$resultToUpdateAmbition==mysql_query($queryToUpdateAmbition) or $message=mysql_error();
    		
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
        $response['data']=$data;
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
        
         $selectQuery="select book.*,joinQuery.full_name,joinQuery.profile_pic from ".TABLE_BOOKS ." book 
         INNER JOIN(SELECT autotBook.user_id,autotBook.book_id,users.full_name,users.profile_pic from ".TABLE_AUTHOR_BOOK." autotBook INNER JOIN ".TABLE_USERS.
         " users ON users.id=autotBook.user_id) joinQuery ON joinQuery.book_id=book.id LIMIT 30";
       //$selectQuery="select * from ".TABLE_BOOKS ."order by id desc limit(". $limit1.",". $limit2 .")";
       // echo $selectQuery;
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
                    $data['suggested'][]=$suggested;
                } 
                
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['suggested']=array();
            	//$status="failed";
            }
            
         $selectFavoriteQuery="select book.*,joinQuery.full_name,joinQuery.profile_pic from ".TABLE_USER_FAVORITE_BOOK . " userFavoriteBook 
         JOIN ".TABLE_BOOKS ." book ON book.id=userFavoriteBook.book_id 
         INNER JOIN(SELECT authorBook.user_id,users.full_name,users.profile_pic from ".TABLE_USER_FAVORITE_AUTHOR." authorBook INNER JOIN ".TABLE_USERS.
         " users ON users.id=authorBook.user_id) joinQuery ON joinQuery.user_id=userFavoriteBook.user_id
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
                    $data['favorite'][]=$favorite;
                } 
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['favorite']=array();
            	//$status="failed";
            }
       
       
    	$response['data']=$data;
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
        
    	$response['data']=$data;
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
        
        
        if($user_id == NULL)
        {
        	$selectQuery="SELECT id FROM ".TABLE_USERS." users WHERE email_id=".$email_id;
   			$resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        	$getUserId=mysql_fetch_row($resultRequest);
        	$userID= $requestCount[0];
        }
        else
        {
        	$userID=$user_id;
        }
        if(mysql_num_rows($resultQuery)>0)
            {
                $updateQuery="UPDATE ".TABLE_STUDYMATES_REQUEST." SET status='blocked' WHERE request_from_mate_id=".$block_user." and request_to_mate_id=".$userID;
                $message="";
                $status="success";
            }
            else
            {
            	$status="failed";
            }
        
        }
         
        
        $response['data']=$data;
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
        
        
         $selectQuery="select id as 'pastime_id',pastime_name,pastime_image from ".TABLE_PASTIMES." LIMIT 30";
         $resultQuery=mysql_query($selectQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultQuery))
                {
                    $data['suggested'][]=$val;
                } 
                
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['suggested']=array();
            	//$status="failed";
            }
            
         $selectFavoriteQuery="select user_favorite_pastime.pastime_id,users.full_name as 'pastime_name',users.profile_pic as 'pastime_image' from " .TABLE_USER_FAVORITE_PASTIME." user_favorite_pastime 
         INNER JOIN ".TABLE_PASTIMES." pastime ON user_favorite_pastime.pastime_id=pastime.id 
         INNER JOIN ".TABLE_USERS." users ON users.id=user_favorite_pastime.user_id
         WHERE user_favorite_pastime.user_id=".$user_id;
        // echo  $selectFavoriteQuery;
    	 $resultFavoriteQuery=mysql_query($selectFavoriteQuery) or $message=mysql_error();
        
         if(mysql_num_rows($resultFavoriteQuery)>0)
            {
                while($val=mysql_fetch_assoc($resultFavoriteQuery))
                {
                    $data['favorite'][]=$val;
                } 
                $message="Request accepted";
                $status="success";
            }
            else
            {
            	 $data['favorite']=array();
            	//$status="failed";
            }
       
       
    	$response['data']=$data;
        $response['message'] = $message;
        $response['status'] = $status;
        
	   return $response;
    }
}
?>