<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 26/10/15
 * Time: 11:19 AM
 */
 
error_reporting(0); 
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
                return $this->postForClasswall($postData);//done
            }
                break;
            case "GetAllClasswallPost":
            {
                return $this->getAllClasswallPost($postData);//done
            }
                break;

            case "GetMyStudents":
            {
                return $this->getMyStudents($postData);//done
            }
                break;

            case "GetAllSubjectsByClass":
            {
                return $this->getAllSubjectsByClass($postData);//done
            }
                break;

            case "GetAllNotes":
            {
                return $this->getAllNotes($postData);//done
            }
                break;

            case "SubmitNotes":
            {
                return $this->submitNotes($postData);//done
            }
                break;
            case "UploadMediaNotes":
            {
                return $this->uploadMediaNotes($postData);//done
            }
                break;
            case "CreateAssignment":
            {
                return $this->createAssignment($postData);//done
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $insertFields = "`wall_post`, `post_by`, `classroom_id`, `modified_date`";
            $insertValues = "'" . $wall_post . "'," . $post_by . ", " . $classroom_id . ",'" . getDefaultDate() . "'";
            $query = "INSERT INTO " . TABLE_CLASSWALL . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
            $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
            if ($result) {
                $status = "success";
                $message = "Post successfully submitted";
            } else {
                $status = "failed";
                $message = "";
            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['classwall']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($role == 2) {
                //student
                $table = TABLE_STUDENT_PROFILE;
            } else if ($role == 3) {
                //teacher
                $table = TABLE_TEACHER_SUBJECT_INFO;
            }
            $query = "SELECT `classroom_id`FROM " . $table . " WHERE `user_id`=" . $user_id." AND is_delete=0";
            $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $classroom_id = $row['classroom_id'];
                    // echo $classroom_id."\n";
                    $getFields = "classwall.id,classwall.created_date,users.profile_pic,classwall.wall_post,classwall.post_by,users.full_name";
                    $queryGetPost = "SELECT ".$getFields." FROM ".TABLE_CLASSWALL." classwall INNER JOIN ".TABLE_USERS." users on classwall.post_by=users.id WHERE `classroom_id`=" .$classroom_id." AND classwall.is_delete=0 AND users.is_delete=0";
                    $resultGetPost = mysqli_query($GLOBALS['con'],$queryGetPost) or $message = mysqli_error($GLOBALS['con']);
                    //echo $queryGetPost;
                    if (mysqli_num_rows($resultGetPost)) {
                        while ($val = mysqli_fetch_assoc($resultGetPost)) {
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
                    } else {
                        $status = "success";
                        $message = DEFAULT_NO_RECORDS;
                    }
                }
            } else {
                $status = "success";
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }

        $response['classwall']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {


            $query = "SELECT `student_id`,is_online FROM ".TABLE_STUDENT_TEACHER." WHERE `teacher_id`=".$user_id." AND is_delete=0";
            $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
            //echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $post = array();
                    $student_id = $row['student_id'];
                    // echo "\n".$student_id."\n";
                    // $query="SELECT * FROM ".TABLE_USERS." WHERE `id`=".$student_id;
                    // $result=mysql_query($query) or $message=mysqli_error($GLOBALS['con']);
                    $queryInnerJoin = TABLE_USERS . " users INNER JOIN " . TABLE_STUDENT_PROFILE . " studentAcademicInfo INNER JOIN " . TABLE_SCHOOLS . " schools";
                    $queryOn = "users.id=studentAcademicInfo.user_id or schools.id=studentAcademicInfo.school_id";

                    $getField = "users.id,schools.school_name,users.full_name,users.profile_pic,users.id";
                    $queryStudent = "SELECT ".$getField." from ".$queryInnerJoin." on ".$queryOn."  where users.id=".$student_id." AND studentAcademicInfo.is_delete=0 AND schools.is_delete=0 AND users.is_delete=0";
                    $resultStudent = mysqli_query($GLOBALS['con'],$queryStudent) or $message = mysqli_error($GLOBALS['con']);
                    // echo $queryStudent."\n";
                    if (mysqli_num_rows($resultStudent)) {
                        $val = mysqli_fetch_assoc($resultStudent);

                        $post['user_id'] = $val['id'];
                        $post['full_name'] = $val['full_name'];
                        $post['is_online'] = $row['is_online'];
                        $post['profile_pic'] = $val['profile_pic'];
                        $post['school_name'] = $val['school_name'];
                        $data[] = $post;
                        // echo $post."\n";

                    }


                }
                $status = "success";
                // $message="";
            } else {
                $status = "success";
                //  $message=DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['students']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            if ($role == 2) {
                //student
                $table = TABLE_STUDENT_PROFILE;
            } else if ($role == 3) {
                //teacher
                $table = TABLE_TEACHER_SUBJECT_INFO;
            }

            $query = "SELECT `classroom_id`FROM ".$table." WHERE `user_id`=" . $user_id." AND is_delete=0";
            $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $classroom_id = $row['classroom_id'];
                    // echo $classroom_id."\n";
                    $getFields = "classroom_subject.classroom_id,classroom_subject.subject_id,subjects.subject_name,subjects.subject_image";
                    $queryGetPost = "SELECT ".$getFields." FROM ".TABLE_CLASSROOM_SUBJECT." classroom_subject INNER JOIN ".TABLE_SUBJECTS." subjects on classroom_subject.subject_id=subjects.id  WHERE classroom_subject.`classroom_id`=" . $classroom_id." AND classroom_subject.is_delete=0 AND subjects.is_delete=0";
                    $resultGetPost = mysqli_query($GLOBALS['con'],$queryGetPost) or $message = mysqli_error($GLOBALS['con']);
                    // echo $queryGetPost;

                    if (mysqli_num_rows($resultGetPost)) {
                        while ($val = mysqli_fetch_assoc($resultGetPost)) {
                            $data[] = $val;
                        }
                        $message = "";
                        $status = "success";
                    }
                }
            } else {
                $message = DEFAULT_NO_RECORDS;
            }

        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['class_subjects']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($role == 2) {
                //student
                $table = TABLE_STUDENT_PROFILE;
            } else if ($role == 3) {
                //teacher
                $table = TABLE_TEACHER_SUBJECT_INFO;
            }

            $query = "SELECT `classroom_id`FROM ".$table." WHERE `user_id`=" . $user_id." AND is_delete=0";
            $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $classroom_id = $row['classroom_id'];
                    // echo $classroom_id."\n";
                    $getFields = "notes.topic_id,topics.topic_name,notes.id,notes.user_id,users.full_name,users.profile_pic,notes.created_date,notes.note_title,notes.note,notes.video_link,notes.audio_link,notes.video_thumbnail,notes.image_link";
                    $queryGetPost = "SELECT ".$getFields." FROM ".TABLE_NOTES." notes INNER JOIN ".TABLE_TOPICS." topics INNER JOIN ".TABLE_USERS." users on topics.id=notes.topic_id and users.id=notes.user_id WHERE `classroom_id`=" .$classroom_id ." AND notes.is_delete=0 AND topics.is_delete=0 AND users.is_delete=0";
                    $resultGetPost = mysqli_query($GLOBALS['con'],$queryGetPost) or $message = mysqli_error($GLOBALS['con']);
                    // echo $queryGetPost;

                    if (mysqli_num_rows($resultGetPost)) {
                        while ($val = mysqli_fetch_assoc($resultGetPost)) {
                            $post = array();
                            $post['topic_id'] = $val['topic_id'];
                            $post['topic_name'] = $val['topic_name'];
                            $post['note_id'] = $val['id'];
                            $post['note_by_id'] = $val['user_id'];
                            $post['note_by_user'] = $val['full_name'];
                            $post['user_profile_pic'] = $val['profile_pic'];
                            $post['note_title'] = $val['note_title'];
                            $post['note_text'] = $val['note'];
                            $post['video_link'] = $val['video_link'];
                            $post['video_thumbnail'] = $val['video_thumbnail'];
                            $post['image_link'] = $val['image_link'];
                            $post['audio_link'] = $val['audio_link'];
                            $post['created_date'] = $val['created_date'];
                            $data[] = $post;
                        }
                        $message = "Record Found";
                    }
                }
            } else {
                $message = DEFAULT_NO_RECORDS;
            }
            $status = "success";
        }
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['notes']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if (!is_dir(NOTES_MEDIA)) {
                mkdir(NOTES_MEDIA, 0777, true);
            }
            //Image Saving
            $image_link = "user_" . $user_id . "/";

            $image_dir = NOTES_MEDIA . $image_link;

            if (!is_dir($image_dir)) {
                mkdir($image_dir, 0777, true);
            }
            $image_url = "";
            if ($images != null) {
                $i = 0;
                $image_name = "IMG-" . date("Ymd-his") . $i++ . "_test.png";
                $image_path = $image_dir . $image_name;
                $image_url = $image_link . $image_name;
                file_put_contents($image_path, base64_decode($images));
            }
            $thumbnail_url = "";
            if ($video_thumbnail != null) {

                $image_name = "THUMBNAIL-" . date("Ymd-his") . "_test.png";
                $image_path = $image_dir . $image_name;
                $thumbnail_url = $image_link . $image_name;
                file_put_contents($image_path, base64_decode($video_thumbnail));
            }
            $insertFields = "`note_title`, `note`, `video_link`, `video_thumbnail`,`audio_link`, `image_link`, `topic_id`, `classroom_id`, `user_id`";
            $insertValues = "'" . $note_title . "','" . $note_text . "','" . $video_content . "','" . $thumbnail_url . "','" . $audio_content . "','" . $image_url . "','" . $topic_id . "','" . $classroom_id . "'," . $user_id;
            $query = "INSERT INTO " . TABLE_NOTES . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
            $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
            if ($result) {
                $note_id = mysqli_insert_id($GLOBALS['con']);
                $status = "success";
                $message = SUCCESSFULLY_ADDED;
            } else {
                $status = "failed";
                $message = "";
            }
        }
        else
            {
                $status="failed";
                $message = MALICIOUS_SOURCE;
            }
        $response['status'] = $status;
        $response['message'] =$message;
        $response['notes']=$data;
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

        $secret_key = $_POST['secret_key'];
        $access_key = $_POST['access_key'];

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            if (!is_dir(NOTES_MEDIA)) {
                mkdir(NOTES_MEDIA, 0777, true);
            }
            $media_dir = "user_" . $user_id . "/";
            $dir = NOTES_MEDIA . $media_dir;
            if (!is_dir($dir)) {
                mkdir($dir, 0777);
            }
            if ("video" == $mediaType) {
                if ($_FILES["mediaFile"]["error"] > 0) {
                    $message = $_FILES["mediaFile"]["error"];

                } else {
                    // Image 5 = Video 6 = Audio 7

                    $mediaName = "VIDEO-" . $created_date . "_test.mp4";
                    $uploadDir = $dir;
                    $uploadFile = NOTES_MEDIA . $media_dir . $mediaName;
                    if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                        //store image data.
                        $link = $media_dir . $mediaName;
                        $queryUpdate = "Update " . TABLE_NOTES . " set video_link= '" . $link . "' where id=" . $note_id." and is_delete=0";
                        $resultUpdate = mysqli_query($GLOBALS['con'],$queryUpdate) or $message = mysqli_error($GLOBALS['con']);
                        //echo $queryUpdate;
                        $status = "success";
                        $message = "Successfully uploaded!.";
                    } else {
                        $status = "failed";
                        $message = FAILED_TO_UPLOAD_MEDIA;
                    }
                }
            } else if ("audio" == $mediaType) {
                if ($_FILES["mediaFile"]["error"] > 0) {
                    $message = $_FILES["mediaFile"]["error"];
                    $status = 2;
                } else {
                    $mediaName = "AUDIO-" . $created_date . "_test.mp3";

                    $uploadDir = $dir;
                    $uploadFile = NOTES_MEDIA . $media_dir . $mediaName;
                    if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                        //store image data.

                        $link = $media_dir . $mediaName;
                        $queryUpdate = "Update " . TABLE_NOTES . " set audio_link= '" . $link . "' where id=" . $note_id." and is_delete=0";
                        $resultUpdate = mysqli_query($GLOBALS['con'],$queryUpdate) or $errorMsg = mysqli_error($GLOBALS['con']);
                        $status = "success";
                        $message = "Successfully uploaded!.";
                    } else {
                        $status = "failed";
                        $message = FAILED_TO_UPLOAD_MEDIA;
                    }
                }

            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
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
        $post=array();
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

//        $submission_date = validateObject ($postData , 'submission_date', "");
//        $submission_date = addslashes($submission_date);

//        $assignment_type = validateObject ($postData , 'assignment_type', "");
//        $assignment_type = addslashes($assignment_type);

        $topic_id = validateObject ($postData , 'topic_id', "");
        $topic_id = addslashes($topic_id);

        $assignment_text = validateObject ($postData , 'assignment_text', "");
        $assignment_text = addslashes($assignment_text);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {


            $insertFields = "`assignment_by`, `description`, `classroom_id`, `subject_id`, `topic_id`";
            $insertValues = $user_id . ",'" . $assignment_text . "'," . $classroom_id . "," . $subject_id . "," . $topic_id;
            $query = "INSERT INTO " . TABLE_ASSIGNMENTS . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
            $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
            //echo $query;
            if ($result) {
                $post['assignment_id'] = mysqli_insert_id($GLOBALS['con']);
                $status = "success";
                $message = "Assignment created";
            } else {
                $post['assignment_id'] = "";
                $status = "failed";
                $message = "";
            }

            $data[] = $post;
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['message']=$message;
        $response['status']=$status;
        $response['assignment']=$data;

        return $response;
    }

}