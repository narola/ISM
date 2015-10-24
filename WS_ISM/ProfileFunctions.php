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
                return $this->authenticateUser($postData);
            }
                break;
            case "RegisterUser":
            {
                return $this->registerUser($postData);
            }
                break;

            case "RequestForCredentials":
            {
                return $this->requestForCredentials($postData);
            }
                break;

            case "CheckUsernameAvailability":
            {
                return $this->checkUsernameAvailability($postData);
            }
                break;

            case "ForgotPassword":
            {
                return $this->forgotPassword($postData);
            }
                break;

            case "PostFeed":
            {
                return $this->postFeed($postData);
            }
                break;
            case "UploadMedia":
            {
               return $this->uploadMedia($postData);
            }
                break;
            case "TagFriendInFeed":
            {
                return $this->tagFriendInFeed($postData);
            }
                break;
            case "AddComment":
            {
                return $this->addComment($postData);
            }
                break;
            case "LikeFeed":
            {
                return $this->likeFeed($postData);
            }
                break;

        }
    }

    /*
      * LikeFeed
      */

    public function likeFeed ($postData)
    {
        $data=array();
        $response=array();

        $feed_id = validateObject ($postData , 'feed_id', "");
        $feed_id = addslashes($feed_id);

        $like_by = validateObject ($postData , 'like_by', "");
        $like_by = addslashes($like_by);

//        $comment = validateObject ($postData , 'comment', "");
//        $comment = addslashes($comment);


        $insertFields="`like_by`, `feed_id`";
        $insertValues="".$like_by.", ".$feed_id;

        $queryCheckFeed="SELECT * FROM " .TABLE_FEED_LIKE." where like_by =".$like_by." and feed_id=".$feed_id;
        $resultCheckFeed=mysql_query($queryCheckFeed) or $message=mysql_error();
       // echo $queryCheckFeed;
        if(mysql_num_rows($resultCheckFeed))
        {
            $val=mysql_fetch_assoc($resultCheckFeed);
            $feed_liked_id=$val['id'];
            $queryLikedFeedDelete="DELETE FROM " .TABLE_FEED_LIKE." where id =".$feed_liked_id;
            $resultLikedFeedDelete=mysql_query($queryLikedFeedDelete) or $message=mysql_error();
            $status="success";
            $message="Unliked successfully";
        }
        else{
            $query="INSERT INTO " .TABLE_FEED_LIKE." (".$insertFields.") VALUES (".$insertValues.")";
            $result=mysql_query($query) or $message=mysql_error();

            $status="success";
            $message="Liked successfully";
        }

        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }

    /*
       * AddComment
       */

    public function addComment ($postData)
    {
        $data=array();
        $response=array();

        $feed_id = validateObject ($postData , 'feed_id', "");
        $feed_id = addslashes($feed_id);

        $comment_by = validateObject ($postData , 'comment_by', "");
        $comment_by = addslashes($comment_by);

        $comment = validateObject ($postData , 'comment', "");
        $comment = addslashes($comment);

        $insertFields="`comment`, `comment_by`, `feed_id`";
        $insertValues="'".$comment."', ".$comment_by.", ".$feed_id."";


        $query="INSERT INTO " .TABLE_FEED_COMMENT." (".$insertFields.") VALUES (".$insertValues.")";
        $result=mysql_query($query) or $message=mysql_error();
        //echo $query;
        if($result)
        {
            $status="success";
            $message="Comment added successfully";
        }
        else
        {
            $status="failed";
            $message="";
        }
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }
    /*
    * TagFriendInFeed
    */

    public function tagFriendInFeed ($postData)
    {
        $data=array();
        $response=array();

        $feed_id = validateObject ($postData , 'feed_id', "");
        $feed_id = addslashes($feed_id);

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $tagged_by = validateObject ($postData , 'tagged_by', "");
        $tagged_by = addslashes($tagged_by);

        $insertFields="`user_id`, `feed_id`, `tagged_by`";
        $insertValues=$user_id.", ".$feed_id.", ".$tagged_by;


        $query="INSERT INTO " .TABLE_FEEDS_TAGGED_USER." (".$insertFields.") VALUES (".$insertValues.")";
        $result=mysql_query($query) or $message=mysql_error();
       // echo $query;
        if($result)
        {
            $status="success";
            $message="Tagged successfully";
        }
        else
        {
            $status="failed";
            $message="";
        }
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }

   /*
    * postFeed
    *
    */
    public function postFeed ($postData)
    {
        $data=array();
        $response=array();
        $feed_by = validateObject ($postData , 'feed_by', "");
        $feed_by = addslashes($feed_by);

        $feed_text = validateObject ($postData , 'feed_text', "");
        $feed_text = addslashes($feed_text);

        $video_link = validateObject ($postData , 'video_link', "");
        $video_link = addslashes($video_link);

        $audio_link = validateObject ($postData , 'audio_link', "");
        $audio_link = addslashes($audio_link);

        $images = validateObject ($postData , 'images', "");
        // $images = addslashes($images);

        $posted_on = validateObject ($postData , 'posted_on', "");
        $posted_on = addslashes($posted_on);

        if (!is_dir(FEEDS_MEDIA)) {
            mkdir(FEEDS_MEDIA, 0777, true);
        }
        $feed_media_dir = "user_" . $feed_by . "/";
        $dir = FEEDS_MEDIA . $feed_media_dir;
        if (!is_dir($dir)) {
            mkdir($dir, 0777);
        }
        $insertFields="`feed_by`, `feed_text`, `video_link`, `audio_link`, `posted_on`";
        $insertValues=$feed_by.",'".$feed_text."','".$video_link."','".$audio_link."','".$posted_on."'";
        $queryPostFeed="INSERT INTO ".TABLE_FEEDS."(".$insertFields.") VALUES (".$insertValues.")";

        $result=mysql_query($queryPostFeed) or $message=mysql_error();
        if($result)
        {
            $feed_id=mysql_insert_id();
            if($video_link!=null)
            {
                //$this->uploadMedia($feed_id,"video",$feed_media_dir);
            }
            $i=0;
            if($images!=null)
            {
                foreach($images as $feed_image)
                {
                    if($feed_image!=null)
                    {

                        $feed_image_name = "IMG-" . date("Ymd-his").$i++.".png";
                        $feed_image_link = $feed_media_dir.$feed_image_name;
                        file_put_contents(FEEDS_MEDIA.$feed_image_link, base64_decode($feed_image));
                        $queryInsertImage="INSERT INTO `feed_image`(`feed_id`, `image_link`) VALUES (".$feed_id.",'".$feed_image_link."')";
                        $resultImageUploading=mysql_query($queryInsertImage) or $message=mysql_error();
                    }

                }
            }
            $message="“Post successfully submitted";
            $status="success";
        }
        else
        {
            $status="failed";
        }

        $response['status']=$status;
        $response['message']=$message;
        $response['data']=$data;
        return $response;
    }

    public function uploadMedia($postData)
    {


        $dir = '';
        $mediaName = '';
        $created_date = date("Y-m-d H:i:s");
        //create Random String.
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        //generate random string with minimum 5 and maximum of 10 characters
        $str = substr(str_shuffle($chars), 0, 8);
        //add extension to file
        $name = $str;
        $feed_id=$_POST['feed_id'];

        $feed_by=$_POST['feed_by'];
        $mediaType=$_POST['mediaType'];

        if (!is_dir(FEEDS_MEDIA)) {
            mkdir(FEEDS_MEDIA, 0777, true);
        }
        $feed_media_dir = "user_" . $feed_by . "/";
        $dir = FEEDS_MEDIA . $feed_media_dir;
        if (!is_dir($dir)) {
            mkdir($dir, 0777);
        }
        if("video"==$mediaType)
        {
            if ($_FILES["video_link"]["error"] > 0) {
                $message = $_FILES["video_link"]["error"];

            } else {
                // Image 5 = Video 6 = Audio 7

                $mediaName = $name . '.mp4';
                $uploadDir = $dir;
                $uploadFile = FEEDS_MEDIA.$feed_media_dir . $mediaName;
                if (move_uploaded_file($_FILES['video_link']['tmp_name'], $uploadFile)) {
                    //store image data.
                    $link=$feed_media_dir . $mediaName;
                    $procedure_insert_set = "CALL UPDATE_VIDEO_LINK ('".$link."','".$feed_id."' )";
                    $result_procedure = mysql_query($procedure_insert_set) or $errorMsg = mysql_error();
                    $status = "1";
                    $message = "Successfully uploaded!.";
                } else {
                    $status = 2;
                    $message = "Failed to upload media on server.";
                }
            }
        }
        else if("audio"==$mediaType)
        {
            if ($_FILES["audio_link"]["error"] > 0) {
                $message = $_FILES["audio_link"]["error"];
                $status=2;
            } else {
                $mediaName = $name . '.mp3';

                $uploadDir = $dir;
                $uploadFile = FEEDS_MEDIA .$feed_media_dir. $mediaName;
                if (move_uploaded_file($_FILES['audio_link']['tmp_name'], $uploadFile)) {
                    //store image data.

                    $link=$feed_media_dir . $mediaName;
                    $procedure_insert_set = "CALL UPDATE_AUDIO_LINK ('".$link."','".$feed_id."' )";
                    $result_procedure = mysql_query($procedure_insert_set) or $errorMsg = mysql_error();
                    $status = "1";
                    $message = "Successfully uploaded!.";
                } else {
                    $status = 2;
                    $message = "Failed to upload media on server.";

                }
            }

        }


        $data['status']=$status;
        $data['link']=$link;
        $data['message']=$message;
        return $data;

    }


    /** Get Extension
     * @param $str
     * @return string
     */
    private function getExtension($str)
    {
        $i = strrpos($str,".");
        if (!$i) { return ""; }

        $l = strlen($str) - $i;
        $ext = substr($str,$i+1,$l);
        return $ext;
    }


    public function forgotPassword ($postData)
    {
        $data=array();
        $response=array();
        $email_address = validateObject ($postData , 'email_address', "");
        $email_address = addslashes($email_address);

        $sendEmail = new SendEmail();
        $randomString=gen_random_string();
        //$message="Hello ISM,\nI am very much interested to be part of ISM system.Please check my details below and let me know how can I become the part of this system.
        $message="Successfully sent";
        $response = $sendEmail -> sendemail("ism.educare@gmail.com", $randomString,"Forgot Password",$email_address);
        // return "Request sent successfully'";

        //$response['data']=$data;
        // $response['status']=$status;
        $response['message']=$message;
        return $response;
    }

    public function checkUsernameAvailability ($postData)
    {
        $message='';
        $data=array();
        $response=array();
        $username = validateObject ($postData , 'username', "");
        //echo "\n".$username;
        $username = addslashes($username);
        //echo "\n".$username;
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
        $response = $sendEmail -> sendemail($email_address, $message,"Request For Credentials","ism.educare@gmail.com");
        return "Request sent successfully'";

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

        $queryUser="SELECT id,username,password from ".TABLE_USERS." where username='".$username."'";
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
                    $post['username']=$val['username'];
                    $status="success";

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
            $queryData="SELECT * FROM ".TABLE_AUTO_GENERATED_CREDENTIAL." autoGenerateCredential INNER JOIN ".TABLE_SCHOOLS." schools INNER JOIN ".TABLE_COURSES." courses ON ".$queryOn." where username='".$username."'";
            //$queryAuthUser="select * from ".TABLE_AUTO_GENERATED_CREDENTIAL." where username='".$userName."'";
            $resultAuthUser=mysql_query($queryData) or $errorMsg=mysql_error();
            if(mysql_num_rows($resultAuthUser))
            {
                $encryptedPassword='';
                while ($val = mysql_fetch_assoc($resultAuthUser))
                {
                    $username=$val['username'];
                    $encryptedPassword=$val['password'];
                    $status=$val['status'];
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
                            $post['school_id']=$val['school_id'];
                            $post['school_name']=$val['school_name'];
                            $post['course_id']=$val['course_id'];
                            $post['course_name']=$val['course_name'];
                            $post['academic_year']=$val['academic_year'];
                            $post['role_id']=$val['role_id'];
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

//        if (!mkdir(USER_PROFILE_PICTURE, 0777, true)) {
//            die('Failed to create folders...');
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

                //Image Saving
                $profile_user_link="user_".$user_id."/";
                $profile_image_dir=USER_PROFILE_PICTURE.$profile_user_link;
                if (!mkdir($profile_image_dir, 0777, true)) {
                    die('Failed to create folders...');
                }

                $profile_image_dir = $profile_image_dir . $profile_image_name;
                $profile_image_link = $profile_user_link.$profile_image_name;
                file_put_contents($profile_image_dir, base64_decode($profile_image));

                $queryProfileImage="INSERT INTO ".TABLE_USER_PROFILE_PICTURE."(`user_id`, `profile_link`) VALUES (".$user_id.",'".$profile_image_link."')";
                $resultProfileImage=mysql_query($queryProfileImage) or $message=mysql_error();

//                if($resultProfileImage)
//                {
//                    $message="done";
//                }
//                else
//                {
//                    $message="not done";
//                }
                $queryAcademic="INSERT INTO ".TABLE_STUDENT_ACADEMIC_INFO."(".$insertAcademicField.") values (".$insertAcademicValue.")";
                $resultAcademic=mysql_query($queryAcademic) or $message=mysql_error();
//				if($resultAcademic)
//				{
                $data['user_id']=$user_id;
                $data['username']=$username;
                $data['profile_pic']=$profile_image_link;
                $status="success";
                $message="Registration completed successfully";
//				}
//				else
//				{
//					$status="failed";
//					//$messsage="1Registration failed";
//				}

            }
        }
        else
        {
            $status="failed";
        }

        $response['status'] = $status;
        $response['message'] = $message;
        $response['data'] = $data;

        return $response;
    }
}
?>