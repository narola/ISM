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
            case "UploadUserProfilePic":
            {
                return $this->uploadUserProfilePic($postData);//done
            }
                break;
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
            }
        }

        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message; //$response['message']=$message."--".$user_id.$_FILES["mediaFile"]["name"];
        return $response;

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


        //code for base64
        $profile_image = validateObject ($postData , 'profile_image', "");
        $profile_image_name = validateObject ($postData , 'profile_image_name', "");
        $profile_image_name = addslashes($profile_image_name);
        $profile_image_name_array=explode(".",$profile_image_name);
        if (!is_dir(USER_PROFILE_PICTURE)) {
            mkdir(USER_PROFILE_PICTURE, 0777, true);
        }
//        if (!mkdir(USER_PROFILE_PICTURE, 0777, true)) {
//            die('Failed to create folders...'.USER_PROFILE_PICTURE);
//        }
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

                $insertAcademicField="`user_id`, `school_id`, `classroom_id`, `academic_year`, `course_id`";
                $insertAcademicValue="'".$user_id."', '".$school_id."', '".$classroom_id."', '".$academic_year."', '".$course_id."'";


//                //Image Saving
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


                $queryAcademic="INSERT INTO ".TABLE_STUDENT_ACADEMIC_INFO."(".$insertAcademicField.") values (".$insertAcademicValue.")";
                $resultAcademic=mysql_query($queryAcademic) or $message=mysql_error();
                if($resultAcademic)
                {
//                    $updateStatus="UPDATE `auto_generated_credential` SET `status`='1' WHERE `id`=".$credential_id;
//                    $resultAcademic=mysql_query($updateStatus) or $message=mysql_error();
                   // echo $updateStatus;
                    $post['user_id']=$user_id;
                    //$post['full_name']=$firstname." ".$lastname;
                   // $post['profile_pic']=$profile_image_link;
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
}
?>