<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 26/10/15
 * Time: 11:19 AM
 */

class TeacherFunctions
{

    function __construct()
    {

    }

    public function call_service($service, $postData)
    {
        switch($service)
        {
            case "PostForClasswall":
            {
                return $this->postForClasswall($postData);
            }
                break;
            case "GetAllClasswallPost":
            {
                return $this->getAllClasswallPost($postData);
            }
                break;

            case "GetMyStudents":
            {
                return $this->getMyStudents($postData);
            }
                break;

            case "GetAllSubjectsByClass":
            {
                return $this->getAllSubjectsByClass($postData);
            }
                break;

            case "GetAllNotes":
            {
                return $this->getAllNotes($postData);
            }
                break;

        }
    }
    /*
    * PostForClasswall
     * This service will be used for teacher to post messages for class wall.
    */

    public function PostForClasswall ($postData)
    {
        $data = array();
        $response = array();

        $post_by = validateObject($postData, 'post_by', "");
        $post_by = addslashes($post_by);

        $wall_post = validateObject($postData, 'wall_post', "");
        $wall_post = addslashes($wall_post);

        $classroom_id = validateObject($postData, 'classroom_id', "");
        $classroom_id = addslashes($classroom_id);

        $insertFields="`wall_post`, `post_by`, `classroom_id`, `modified_date`";
        $insertValues="'".$wall_post."',".$post_by.", " .$classroom_id.",'".getDefaultDate()."'";
        $query = "INSERT INTO ".TABLE_CLASSWALL."(".$insertFields.") VALUES (".$insertValues.")";
        $result = mysql_query($query) or $message = mysql_error();
        if ($result) {
            $status = "success";
            $message = "Post successfully submitted";
        }
        else{
            $status = "failed";
            $message = "";
        }
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;
    }

    /*
      * getAllClasswallPost
      * This service will be used to fetch all the class wall post.
      */

    public function getAllClasswallPost ($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $role_id = validateObject ($postData , 'role_id', "");
        $role_id = addslashes($role_id);

        if($role_id==2){
            //student
            $table=TABLE_STUDENT_ACADEMIC_INFO;
        }
        else if($role_id==3) {
            //teacher
            $table=TABLE_TEACHER_SUBJECT_INFO;
        }
        $query = "SELECT `classroom_id`FROM ".$table." WHERE `user_id`=" . $user_id;
        $result = mysql_query($query) or $message = mysql_error();
        if (mysql_num_rows($result))
        {
            while ($row = mysql_fetch_assoc($result))
            {
                $classroom_id = $row['classroom_id'];
               // echo $classroom_id."\n";
                $getFields="classwall.id,classwall.created_date,users.profile_pic,classwall.wall_post,classwall.post_by,users.full_name";
                $queryGetPost = "SELECT ".$getFields." FROM ".TABLE_CLASSWALL." classwall INNER JOIN ".TABLE_USERS." users on classwall.post_by=users.id WHERE `classroom_id`=" . $classroom_id;
                $resultGetPost = mysql_query($queryGetPost) or $message = mysql_error();
                //echo $queryGetPost;
                if (mysql_num_rows($resultGetPost))
                {
                    while ($val = mysql_fetch_assoc($resultGetPost))
                    {
                        $post = array();
                        $post['classwall_id'] = $val['id'];
                        $post['wall_post'] = $val['wall_post'];
                        $post['post_by_id'] = $val['post_by'];
                        $post['post_by_user'] = $val['full_name'];
                        $post['post_user_pic'] = $val['profile_pic'];
                        $post['posted_on'] = $val['created_date'];
                        $post['classroom_id'] = $classroom_id;

                        $data[] = $post;
                    }
                    $status = "success";
                    $message = "Successfully";
                }
                else{
                    $status = "success";
                    $message = DEFAULT_NO_RECORDS;
                }
            }
        }
        else{
            $status = "success";
            $message = DEFAULT_NO_RECORDS;
        }

        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }

    /*
     * GetMyStudents
     * This service will be used to fetch students that belongs to the specific teacher.
     */

    public function getMyStudents ($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);


        $query="SELECT `student_id`,is_online FROM ".TABLE_STUDENT_TEACHER." WHERE `teacher_id`=".$user_id;
        $result=mysql_query($query) or $message=mysql_error();
        //echo $query;
        if(mysql_num_rows($result))
        {
            while($row=mysql_fetch_assoc($result))
            {
                $post=array();
                $student_id=$row['student_id'];
               // echo "\n".$student_id."\n";
               // $query="SELECT * FROM ".TABLE_USERS." WHERE `id`=".$student_id;
               // $result=mysql_query($query) or $message=mysql_error();
                $queryInnerJoin=TABLE_USERS." users INNER JOIN ".TABLE_STUDENT_ACADEMIC_INFO." studentAcademicInfo INNER JOIN ".TABLE_SCHOOLS." schools";
                $queryOn="users.id=studentAcademicInfo.user_id or schools.id=studentAcademicInfo.school_id";

                $getField="users.id,schools.school_name,users.full_name,users.profile_pic,users.id";
                $queryStudent="SELECT ".$getField." from ".$queryInnerJoin." on ".$queryOn."  where users.id=".$student_id;
                $resultStudent=mysql_query($queryStudent) or $message=mysql_error();
               // echo $queryStudent."\n";
                if(mysql_num_rows($resultStudent)) {
                   $val = mysql_fetch_assoc($resultStudent);

                        $post['user_id'] = $val['id'];
                        $post['full_name'] = $val['full_name'];
                        $post['is_online'] = $row['is_online'];
                        $post['profile_pic'] = $val['profile_pic'];
                        $post['school_name'] = $val['school_name'];
                        $data[]=$post;
                       // echo $post."\n";

                }


            }
            $status="success";
           // $message="";
        }
        else
        {
            $status="success";
          //  $message=DEFAULT_NO_RECORDS;
        }
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }

    /*
    * getAllSubjectsByClass
     *This service will be used to fetch the subjects that are specific to a classroom.
    */

    public function getAllSubjectsByClass ($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $role_id = validateObject ($postData , 'role_id', "");
        $role_id = addslashes($role_id);

        if($role_id==2){
            //student
            $table=TABLE_STUDENT_ACADEMIC_INFO;
        }
        else if($role_id==3) {
            //teacher
            $table=TABLE_TEACHER_SUBJECT_INFO;
        }

        $query = "SELECT `classroom_id`FROM ".$table." WHERE `user_id`=" . $user_id;
        $result = mysql_query($query) or $message = mysql_error();
        if (mysql_num_rows($result))
        {
            while ($row = mysql_fetch_assoc($result))
            {
                $classroom_id = $row['classroom_id'];
                // echo $classroom_id."\n";
                $getFields="classroom_subject.classroom_id,classroom_subject.subject_id,subjects.subject_name,subjects.subject_image";
                $queryGetPost = "SELECT ".$getFields." FROM ".TABLE_CLASSROOM_SUBJECT." classroom_subject INNER JOIN ".TABLE_SUBJECTS." subjects on classroom_subject.subject_id=subjects.id  WHERE classroom_subject.`classroom_id`=" . $classroom_id;
                $resultGetPost = mysql_query($queryGetPost) or $message = mysql_error();
               // echo $queryGetPost;

                if (mysql_num_rows($resultGetPost))
                {
                    while ($val = mysql_fetch_assoc($resultGetPost))
                    {
                        $data[] = $val;
                    }
                    $message = "";
                }
            }
        }
        else{
            $message = DEFAULT_NO_RECORDS;
        }
        $status = "success";
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }

    /*
     * getAllNotes
     * This service will be used to fetch all the notes that belongs to the user.
     *
     */
    public function getAllNotes ($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $role_id = validateObject ($postData , 'role_id', "");
        $role_id = addslashes($role_id);

        if($role_id==2){
            //student
            $table=TABLE_STUDENT_ACADEMIC_INFO;
        }
        else if($role_id==3) {
            //teacher
            $table=TABLE_TEACHER_SUBJECT_INFO;
        }

        $query = "SELECT `classroom_id`FROM ".$table." WHERE `user_id`=" . $user_id;
        $result = mysql_query($query) or $message = mysql_error();
       // echo $query;
        if (mysql_num_rows($result))
        {
            while ($row = mysql_fetch_assoc($result))
            {
                $classroom_id = $row['classroom_id'];
                // echo $classroom_id."\n";
                $getFields="notes.id,notes.note_title,notes.note,notes.topic_id,topics.topic_name";
                $queryGetPost = "SELECT ".$getFields." FROM ".TABLE_NOTES." notes INNER JOIN ".TABLE_TOPICS." topics on topics.id=notes.topic_id WHERE `classroom_id`=" . $classroom_id;
                $resultGetPost = mysql_query($queryGetPost) or $message = mysql_error();
                // echo $queryGetPost;

                if (mysql_num_rows($resultGetPost)) {
                    while ($val = mysql_fetch_assoc($resultGetPost)){
                        $data[] = $val;
                    }
                    $message = "";
                }
            }
        }
        else{
            $message = DEFAULT_NO_RECORDS;
        }
        $status = "success";
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
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