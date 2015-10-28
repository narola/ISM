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

            case "SubmitNotes":
            {
                return $this->submitNotes($postData);
            }
                break;
            case "UploadMediaNotes":
            {
                return $this->uploadMediaNotes($postData);
            }
                break;
            case "CreateAssignment":
            {
                return $this->createAssignment($postData);
            }
                break;
            case "CreateExam":
            {
                return $this->createExam($postData);
            }
                break;


        }
    }
    /*
    * PostForClasswall
     * used for teacher to post messages for class wall.
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
      * used to fetch all the class wall post.
      */

    public function getAllClasswallPost ($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $role = validateObject ($postData , 'role', "");
        $role = addslashes($role);

        if($role==2){
            //student
            $table=TABLE_STUDENT_ACADEMIC_INFO;
        }
        else if($role==3) {
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
                $queryGetPost = "SELECT ".$getFields." FROM ".TABLE_CLASSWALL." classwall INNER JOIN ".TABLE_USERS." users on classwall.post_by=users.id WHERE `classroom_id`=" .                $classroom_id;
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
     *  used to fetch students that belongs to the specific teacher.
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
     * used to fetch the subjects that are specific to a classroom.
    */

    public function getAllSubjectsByClass ($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $role = validateObject ($postData , 'role', "");
        $role = addslashes($role);

        if($role==2){
            //student
            $table=TABLE_STUDENT_ACADEMIC_INFO;
        }
        else if($role==3) {
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
     *  used to fetch all the notes that belongs to the user.
     *
     */
    public function getAllNotes ($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $role = validateObject ($postData , 'role', "");
        $role = addslashes($role);

        if($role==2){
            //student
            $table=TABLE_STUDENT_ACADEMIC_INFO;
        }
        else if($role==3) {
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
                $getFields="notes.topic_id,topics.topic_name,notes.id,notes.user_id,users.full_name,users.profile_pic,notes.created_date,notes.note_title,notes.note,notes.video_link,notes.audio_link,notes.video_thumbnail,notes.image_link";
                $queryGetPost = "SELECT ".$getFields." FROM ".TABLE_NOTES." notes INNER JOIN ".TABLE_TOPICS." topics INNER JOIN ".TABLE_USERS." users on topics.id=notes.topic_id and users.id=notes.user_id WHERE `classroom_id`=" .$classroom_id;
                $resultGetPost = mysql_query($queryGetPost) or $message = mysql_error();
                // echo $queryGetPost;

                if (mysql_num_rows($resultGetPost)) {
                    while ($val = mysql_fetch_assoc($resultGetPost)){
                        $post=array();
                        $post['topic_id']=$val['topic_id'];
                        $post['topic_name']=$val['topic_name'];
                        $post['note_id']=$val['id'];
                        $post['note_by_id']=$val['user_id'];
                        $post['note_by_user']=$val['full_name'];
                        $post['user_profile_pic']=$val['profile_pic'];
                        $post['note_title']=$val['note_title'];
                        $post['note_text']=$val['note'];
                        $post['video_link']=$val['video_link'];
                        $post['video_thumbnail']=$val['video_thumbnail'];
                        $post['image_link']=$val['image_link'];
                        $post['audio_link']=$val['audio_link'];
                        $post['created_date']=$val['created_date'];
                        $data[] = $post;
                    }
                    $message = "Record Found";
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
     * submitNotes
     *  used to submit notes in the system by either students or teachers.
     */
    public function submitNotes($postData)
    {
        $data=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $note_title = validateObject ($postData , 'note_title', "");
        $note_title = addslashes($note_title);

        $note_text = validateObject ($postData , 'note_text', "");
        $note_text = addslashes($note_text);

        $video_content = validateObject ($postData , 'video_content', "");
        $video_content = addslashes($video_content);

        $video_thumbnail = validateObject ($postData , 'video_thumbnail', "");
        $video_thumbnail = addslashes($video_thumbnail);

        $audio_content = validateObject ($postData , 'audio_content', "");
        $audio_content = addslashes($audio_content);

        $images = validateObject ($postData , 'images', "");

        $topic_id = validateObject ($postData , 'topic_id', "");
        $topic_id = addslashes($topic_id);

        $classroom_id = validateObject ($postData , 'classroom_id', "");
        $classroom_id = addslashes($classroom_id);

        if (!is_dir(NOTES_MEDIA)) {
            mkdir(NOTES_MEDIA, 0777, true);
        }
        //Image Saving
        $image_link="user_".$user_id."/";

        $image_dir=NOTES_MEDIA.$image_link;

        if (!is_dir($image_dir)) {
            mkdir($image_dir, 0777, true);
        }
        $image_url="";
        if($images!=null){
            $i=0;
            $image_name = "IMG-" . date("Ymd-his").$i++."_test.png";
            $image_path = $image_dir . $image_name;
            $image_url = $image_link.$image_name;
            file_put_contents($image_path, base64_decode($images));
        }
        $thumbnail_url="";
        if($video_thumbnail!=null){

            $image_name = "THUMBNAIL-" . date("Ymd-his")."_test.png";
            $image_path = $image_dir . $image_name;
            $thumbnail_url = $image_link.$image_name;
            file_put_contents($image_path, base64_decode($video_thumbnail));
        }
        $insertFields="`note_title`, `note`, `video_link`, `video_thumbnail`,`audio_link`, `image_link`, `topic_id`, `classroom_id`, `user_id`";
        $insertValues="'".$note_title."','".$note_text."','".$video_content."','".$thumbnail_url."','".$audio_content."','".$image_url."','".$topic_id."','".$classroom_id."',".$user_id;
        $query="INSERT INTO ".TABLE_NOTES."(".$insertFields.") VALUES (".$insertValues.")";
        $result = mysql_query($query) or $message = mysql_error();
        if($result){
            $note_id=mysql_insert_id();
            $response['status']="success";
            $response['message']="Notes added successfully";
        }
        else {
            $response['status'] = "failed";
            $response['message'] = "";
        }

        $response['data']=$data;
        return $response;

    }

    /*
     *  used for the upload media(audio/video ) in submits notes
     */
    public function uploadMediaNotes($postData)
    {
        $dir = '';
        $mediaName = '';
        $created_date = date("Ymd-His");

        $user_id=$_POST['user_id'];

        $note_id=$_POST['note_id'];
        $mediaType=$_POST['mediaType'];

        if (!is_dir(NOTES_MEDIA)) {
            mkdir(NOTES_MEDIA, 0777, true);
        }
        $media_dir = "user_" . $user_id . "/";
        $dir = NOTES_MEDIA . $media_dir;
        if (!is_dir($dir)) {
            mkdir($dir, 0777);
        }
        if("video"==$mediaType)
        {
            if ($_FILES["mediaFile"]["error"] > 0) {
                $message = $_FILES["mediaFile"]["error"];

            } else {
                // Image 5 = Video 6 = Audio 7

                $mediaName = "VIDEO-".$created_date."_test.mp4";
                $uploadDir = $dir;
                $uploadFile = NOTES_MEDIA.$media_dir . $mediaName;
                if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                    //store image data.
                    $link=$media_dir . $mediaName;
                    $queryUpdate = "Update ".TABLE_NOTES." set video_link= '".$link."' where id=".$note_id;
                    $resultUpdate= mysql_query($queryUpdate) or $errorMsg = mysql_error();
                    //echo $queryUpdate;
                    $status = "success";
                    $message = "Successfully uploaded!.";
                } else {
                    $status = "failed";
                    $message = "Failed to upload media on server.";
                }
            }
        }
        else if("audio"==$mediaType)
        {
            if ($_FILES["mediaFile"]["error"] > 0) {
                $message = $_FILES["mediaFile"]["error"];
                $status=2;
            } else {
                $mediaName = "AUDIO-".$created_date."_test.mp3";

                $uploadDir = $dir;
                $uploadFile = NOTES_MEDIA .$media_dir. $mediaName;
                if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                    //store image data.

                    $link=$media_dir . $mediaName;
                    $queryUpdate = "Update ".TABLE_NOTES." set audio_link= '".$link."' where id=".$note_id;
                    $resultUpdate= mysql_query($queryUpdate) or $errorMsg = mysql_error();
                    $status = "success";
                    $message = "Successfully uploaded!.";
                } else {
                    $status = "failed";
                    $message = "Failed to upload media on server.";
                }
            }

        }

        $data['status']=$status;
        //$data['link']=$link;
        $data['message']=$message;
        return $data;

    }

    /*
     * createAssignment
     */
    public function createAssignment ($postData)
    {
        $data=array();
        $response=array();
        $message='';
        $status='';

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

//        $course_id = validateObject ($postData , 'course_id', "");
//        $course_id = addslashes($course_id);

        $classroom_id = validateObject ($postData , 'classroom_id', "");
        $classroom_id = addslashes($classroom_id);

        $subject_id = validateObject ($postData , 'subject_id', "");
        $subject_id = addslashes($subject_id);

        $submission_date = validateObject ($postData , 'submission_date', "");
        $submission_date = addslashes($submission_date);

//        $assignment_type = validateObject ($postData , 'assignment_type', "");
//        $assignment_type = addslashes($assignment_type);

        $topic_id = validateObject ($postData , 'topic_id', "");
        $topic_id = addslashes($topic_id);

        $assignment_text = validateObject ($postData , 'assignment_text', "");
        $assignment_text = addslashes($assignment_text);

        $response['message']=$message;
        $response['status']=$status;
        $response['data']=$data;

        return $response;
    }

    /*
     * create exam
     */
    public function createExam ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $response=array();

//“passing_percent”:””
//“subject_id”: “”,
//“exam_mode”:””
//“exam_type”:””
//“exam_category”:””
//“exam_duration”:””
//“submission_date”:””,
// “exam_instruction”:””
//“declare_results”:
//“negative_marking”:
//“random_question”:””

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $exam_name = validateObject ($postData , 'exam_name', "");
        $exam_name = addslashes($exam_name);

        $course_id = validateObject ($postData , 'course_id', "");
        $course_id = addslashes($course_id);

        $classroom_id = validateObject ($postData , 'classroom_id', "");
        $classroom_id = addslashes($classroom_id);

        $passing_percent = validateObject ($postData , 'passing_percent', "");
        $passing_percent = addslashes($passing_percent);

        $subject_id = validateObject ($postData , 'subject_id', "");
        $subject_id = addslashes($subject_id);

        $exam_mode = validateObject ($postData , 'exam_mode', "");
        $exam_mode = addslashes($exam_mode);

        $exam_type = validateObject ($postData , 'exam_type', "");
        $exam_type = addslashes($exam_type);

        $exam_category = validateObject ($postData , 'exam_category', "");
        $exam_category = addslashes($exam_category);

        $exam_duration = validateObject ($postData , 'exam_duration', "");
        $exam_duration= addslashes($exam_duration);

        $submission_date = validateObject ($postData , 'submission_date', "");
        $submission_date = addslashes($submission_date);

        $exam_instruction = validateObject ($postData , 'exam_instruction', "");
        $exam_instruction = addslashes($exam_instruction);

        $declare_results = validateObject ($postData , 'declare_results', "");
        $declare_results = addslashes($declare_results);

        $negative_marking = validateObject ($postData , 'negative_marking', "");
        $negative_marking = addslashes($negative_marking);

        $random_question = validateObject ($postData , 'random_question', "");
        $random_question = addslashes($random_question);

        $insertField="";
        $insertValues="";
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