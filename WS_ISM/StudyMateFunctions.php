<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 23/10/15
 * Time: 9:56 AM
 */
include_once 'ConstantValues.php';
class StudyMateFunctions
{

    function __construct()
    {

    }

    public function call_service($service, $postData)
    {
        switch($service)
        {
            case "GetStudymates":
            {
                return $this->getStudymates($postData);
            }
                break;
            case "GetStudymatesWithDetails":
            {
                return $this->getStudymatesWithDetails($postData);
            }
                break;

            case "GetSuggestedStudymates":
            {
                return $this->getSuggestedStudymates($postData);
            }
                break;

            case "SendRequestToStudymate":
            {
                return $this->sendRequestToStudymate($postData);
            }
                break;

            case "AcceptRequestFromStudymate":
            {
                return $this->acceptRequestFromStudymate($postData);
            }
                break;

        }
    }
    /*
    * getStudentAcademicInfo
    */

    public function getStudentAcademicInfo ($postData)
    {
//        $data = array();
//        $response = array();
//
//        $user_id = validateObject($postData, 'user_id', "");
//        $user_id = addslashes($user_id);
//
//        $queryOn="";
//        $queryCheckFeed = "SELECT * FROM ".TABLE_USERS." users INNER JOIN " . TABLE_STUDENT_ACADEMIC_INFO . "academicInfo INNER JOIN ".TABLE_SCHOOLS." schools INNER JOIN ".TABLE_COURSES."  courses on ".$queryOn." where user_id =" . $like_by . " and feed_id=" . $feed_id;
//        $resultCheckFeed = mysql_query($queryCheckFeed) or $message = mysql_error();
//        // echo $queryCheckFeed;
//        if (mysql_num_rows($resultCheckFeed)) {
//            $val = mysql_fetch_assoc($resultCheckFeed);
//            $data['school_id'] = $val['school_id'];
//            $data['school_name'] = $val['school_name'];
//            $data['course_id'] = $val['course_id'];
//            $data['course_name'] = $val['course_name'];
//            $data['academic_year'] = $val['academic_year'];
//            $data['classroom_id'] = $val['classroom_id'];
//
//
//        }
    }

    /*
      * LikeFeed
      */

    public function likeFeed ($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $liked_id = validateObject ($postData , 'liked_id', "");
        $unliked_id = validateObject ($postData , 'unliked_id', "");

        foreach($unliked_id as $feed_id) {
            $insertFields = "`like_by`, `feed_id`";
            $insertValues = "" . $user_id . ", " . $feed_id;
            $queryCheckFeed = "SELECT * FROM " . TABLE_FEED_LIKE . " where like_by =" . $user_id . " and feed_id=" . $feed_id;
            $resultCheckFeed = mysql_query($queryCheckFeed) or $message = mysql_error();
            echo $queryCheckFeed . "\n";
            if (mysql_num_rows($resultCheckFeed)) {
                $val = mysql_fetch_assoc($resultCheckFeed);
                $feed_liked_id = $val['id'];
                $queryLikedFeedDelete = "DELETE FROM " . TABLE_FEED_LIKE . " where id =" . $feed_liked_id;
                $resultLikedFeedDelete = mysql_query($queryLikedFeedDelete) or $message = mysql_error();

            }
        }
        foreach($liked_id as $feed_id) {
            $insertFields = "`like_by`, `feed_id`";
            $insertValues = "" . $user_id . ", " . $feed_id;
            $query="INSERT INTO " .TABLE_FEED_LIKE." (".$insertFields.") VALUES (".$insertValues.")";
            $result=mysql_query($query) or $message=mysql_error();
        }

        $status = "success";
        $message = "Successfully";
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


    public function acceptRequestFromStudymate ($postData)
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

    public function sendRequestToStudymate ($postData)
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

    public function getSuggestedStudymates ($postData)
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

    public function getStudymates($postData)
    {
        $response=array();
        $data=array();
        $response['data']=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $queryGetStudyMate="SELECT * from ".TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users on studymates.mate_id=users.id where mate_of=".$user_id;
        $resultGetStudyMate=mysql_query($queryGetStudyMate) or $message=mysql_error();
        if(mysql_num_rows($resultGetStudyMate))
        {

            while ($val = mysql_fetch_assoc($resultGetStudyMate))
            {
                $post=array();
                $post['user_id']=$val['mate_id'];
                $post['full_name']=$val['username'];
                $post['profile_pic']=$val['profile_pic'];
                array_push($data,$post);
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

        array_push($response['data'],$data);
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

    public function getStudymatesWithDetails($postData)
    {

        $response=array();
        $data=array();
        $response['data']=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $queryInnerJoin=TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users INNER JOIN ".TABLE_STUDENT_ACADEMIC_INFO." studentAcademicInfo INNER JOIN ".TABLE_SCHOOLS." schools";
        $queryOn="studymates.mate_id=users.id ";

        $queryGetStudyMateAllDetail="SELECT * from ".$queryInnerJoin." on ".$queryOn."  where mate_of=".$user_id;
        echo $queryGetStudyMateAllDetail;
        $resultGetStudyMateAllDetail=mysql_query($queryGetStudyMateAllDetail) or $message=mysql_error();
        if(mysql_num_rows($resultGetStudyMateAllDetail))
        {

            while ($val = mysql_fetch_assoc($resultGetStudyMateAllDetail))
            {
                $post=array();
                $post['user_id']=$val['mate_id'];
                $post['full_name']=$val['username'];
                $post['profile_pic']=$val['profile_pic'];
                $post['is_online']=$val['is_online'];
                $post['school_name']=$val['school_name'];

                array_push($data,$post);
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

        array_push($response['data'],$data);
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
}
?>