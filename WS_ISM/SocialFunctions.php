<?php
include_once 'ApiCrypter.php';


error_reporting(0);
class SocialFunctions
{


    function __construct()
    {

    }

    public function call_service($service, $postData)
    {
        switch ($service) {

            case "GetAllFeeds": {
                return $this->getAllFeedsFunction($postData);
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

            case "GetAllComments":
            {
                return $this->getAllComments($postData);
            }
                break;

            case "GetMyFeeds":
            {
                return $this->getMyFeeds($postData);
            }
                break;

            case "AddQuestionToFavorite":
            {
                return $this->addQuestionToFavorite($postData);
            }
                break;
            case "TempGetMyFeeds":
            {
                return $this->tempGetMyFeeds($postData);
            }
                break;

            case "GetSecurirty":
            {
                return $this->getSecurirty($postData);
            }
                break;

            case "EncryptionData":
            {
                return $this->encryptionData($postData);
            }
                break;

            case "DecryptionData":
            {
                return $this->decryptionData($postData);
            }
                break;

            case "Hashtag":
            {
                return $this->hashTag($postData);
            }
                break;

            case "GetAllHashtag":
            {
                return $this->getAllHashtag($postData);
            }
                break;

            case "GetUserFavoriteQuestion";
            {
                return $this->getUserFavoriteQuestion($postData);
            }
                break;
        }
    }

    /*
       * getAllComments
       */

    public function getAllComments ($postData)
    {
        $data=array();
        $response=array();

        $feed_id = validateObject ($postData , 'feed_id', "");
        $feed_id = addslashes($feed_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $getFields = "f.id, f.comment,f.created_date, f.comment_by,u.full_name,p.profile_link as 'profile_pic'";
            $query = "SELECT ".$getFields."  FROM feed_comment f INNER JOIN users u ON f.comment_by=u.id
LEFT JOIN user_profile_picture p ON p.user_id=u.id  WHERE f.feed_id=".$feed_id ." AND f.is_delete=0 AND u.is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            //echo $query;
            if (mysqli_num_rows($result) >0) {
                while ($comments = mysqli_fetch_assoc($result)) {
                    $data[] = $comments;
                }
                $message = "";
                $status=SUCCESS;
            } else {
                $message = DEFAULT_NO_RECORDS;
                $status=SUCCESS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $response['comments']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }


    public function likeFeed ($postData)
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

            $liked_id = validateObject($postData, 'liked_id', "");
            $unliked_id = validateObject($postData, 'unliked_id', "");
            if ($unliked_id != null) {
                foreach ($unliked_id as $feed_id) {
                    $queryCheckFeed = "SELECT * FROM " . TABLE_FEED_LIKE . " where like_by =" . $user_id . " and feed_id=" . $feed_id;
                    // echo $queryCheckFeed."\n_unliked";
                    $resultCheckFeed = mysqli_query($GLOBALS['con'], $queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);
                    // echo $queryCheckFeed . "\n";
                    if (mysqli_num_rows($resultCheckFeed)) {
                        $val = mysqli_fetch_assoc($resultCheckFeed);
                        $feed_like_id = $val['id'];
                        $queryUpdate = "UPDATE `feed_like` SET `is_delete`=0 WHERE `feed_id`=" . $feed_like_id;
                        // echo $procedure;
                        $result = mysqli_query($GLOBALS['con'], $queryUpdate) or $errorMsg = mysqli_error($GLOBALS['con']);
                    }
                }
            }

            if ($liked_id != null) {
                foreach ($liked_id as $feed_id) {
                    $queryCheckFeed = "SELECT * FROM " . TABLE_FEED_LIKE . " where like_by =" . $user_id . " and feed_id=" . $feed_id;
                    //echo $queryCheckFeed."\n_liked";
                    $resultCheckFeed = mysqli_query($GLOBALS['con'], $queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);
                    if (mysqli_num_rows($resultCheckFeed)) {
                        $val = mysqli_fetch_assoc($resultCheckFeed);
                        $feed_like_id = $val['id'];
                        $queryUpdate = "UPDATE `feed_like` SET `is_delete`=1 WHERE `id`=" . $feed_like_id;
                        // echo $procedure;
                        $result = mysqli_query($GLOBALS['con'], $queryUpdate) or $errorMsg = mysqli_error($GLOBALS['con']);

                    } else {
                        $insertFields = "`like_by`, `feed_id`,`is_delete`";
                        $insertValues = "" . $user_id . ", " . $feed_id . ",'1'";
                        $query = "INSERT INTO " . TABLE_FEED_LIKE . " (" . $insertFields . ") VALUES (" . $insertValues . ")";
                        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                    }
                }
            }
            $status = "success";
            $message = "Successfully";
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['like_feed']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }

    /*
      * LikeFeed

    */
    public function likeFeed1 ($postData)
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

            $liked_id = validateObject($postData, 'liked_id', "");
            $unliked_id = validateObject($postData, 'unliked_id', "");
            if ($unliked_id != null) {
                foreach ($unliked_id as $feed_id) {
                    $queryCheckFeed = "SELECT * FROM " . TABLE_FEED_LIKE . " where like_by =" . $user_id . " and feed_id=" . $feed_id ." and is_delete=0";
                    //echo $queryCheckFeed."\n_unliked";exit;
                    $resultCheckFeed = mysqli_query($GLOBALS['con'], $queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);
                    // echo $queryCheckFeed . "\n";
                    if (mysqli_num_rows($resultCheckFeed)>0) {
                        $val = mysqli_fetch_assoc($resultCheckFeed);
                        $feed_like_id = $val['id'];
                        $queryUpdate = "UPDATE ".TABLE_FEED_LIKE." SET `is_delete`=1 WHERE `feed_id`=" . $feed_id ." AND like_by=".$user_id;
                        // echo $procedure;
                        $result = mysqli_query($GLOBALS['con'], $queryUpdate) or $errorMsg = mysqli_error($GLOBALS['con']);
                    }
                }
            }

            if ($liked_id != null) {
                foreach ($liked_id as $feed_id) {
                    $queryCheckFeed = "SELECT * FROM " . TABLE_FEED_LIKE . " where like_by =" . $user_id . " and feed_id=" . $feed_id ;
                    //echo $queryCheckFeed."\n_liked";
                    $resultCheckFeed = mysqli_query($GLOBALS['con'], $queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);
                    if (mysqli_num_rows($resultCheckFeed)>0) {

                        $queryUpdate = "UPDATE `feed_like` SET `is_delete`=0 WHERE `feed_id`=" . $feed_id." AND like_by=".$user_id;
                        // echo $procedure;
                        $result = mysqli_query($GLOBALS['con'], $queryUpdate) or $errorMsg = mysqli_error($GLOBALS['con']);

                    } else {
                        $insertFields = "`like_by`, `feed_id`,`is_delete`";
                        $insertValues = "" . $user_id . ", " . $feed_id . ",'0'";
                        $query = "INSERT INTO " . TABLE_FEED_LIKE . " (" . $insertFields . ") VALUES (" . $insertValues . ")";
                        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                    }
                }
            }
            $status = SUCCESS;
            $message = "Successfully";
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['like_feed']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            $insertFields = "`comment`, `comment_by`, `feed_id`";
            $insertValues = "'" . $comment . "', " . $comment_by . ", " . $feed_id . "";


            $query = "INSERT INTO " . TABLE_FEED_COMMENT . " (" . $insertFields . ") VALUES (" . $insertValues . ")";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            //echo $query;
            if ($result) {
                $data['comment_id']=mysqli_insert_id($GLOBALS['con']);
                $status = SUCCESS;
                $message = SUCCESSFULLY_ADDED;
            } else {
                $status = FAILED;
                $message = "";
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['comment'][]=$data;
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

        $user_id = validateObject ($postData , 'tagged_user_id', "");
        //$user_id = addslashes($user_id);

        $tagged_by = validateObject ($postData , 'tagged_by', "");
        $tagged_by = addslashes($tagged_by);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {


            foreach ($user_id as $id) {
                $insertFields = "`user_id`, `feed_id`, `tagged_by`";
                $insertValues = $id . ", " . $feed_id . ", " . $tagged_by;
                $query = "INSERT INTO " . TABLE_FEEDS_TAGGED_USER . " (" . $insertFields . ") VALUES (" . $insertValues . ")";
                $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                // echo $query;
                if ($result) {
                    $status = SUCCESS;
                    $message = "Tagged successfully";
                }
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['tag_friend']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

    }

    /*
     * postFeed
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

        $video_thumbnail = validateObject ($postData , 'video_thumbnail', "");
        $video_thumbnail = addslashes($video_thumbnail);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if (!is_dir(FEEDS_MEDIA)) {
                mkdir(FEEDS_MEDIA, 0777, true);
            }
            $feed_media_dir = "user_" . $feed_by . "/";
            $dir = FEEDS_MEDIA . $feed_media_dir;
            if (!is_dir($dir)) {
                mkdir($dir, 0777);
            }
            $feed_thumb_link = null;
            if ($video_thumbnail != null) {
                $feed_thumb = "Thumb-" . date("Ymd-his") . ".png";
                $feed_thumb_link = $feed_media_dir . $feed_thumb;
                if (!file_put_contents(FEEDS_MEDIA . $feed_thumb_link, base64_decode($video_thumbnail))) {
                    $feed_thumb_link = FAILED;
                }

            }
            $insertFields = "`feed_by`, `feed_text`, `video_link`, `audio_link`, `posted_on`,`video_thumbnail`";
            $insertValues = $feed_by . ",'" . $feed_text . "','" . $video_link . "','" . $audio_link . "','" . $posted_on . "','" . $feed_thumb_link . "'";
            $queryPostFeed = "INSERT INTO " . TABLE_FEEDS . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
            $result = mysqli_query($GLOBALS['con'], $queryPostFeed) or $message = mysqli_error($GLOBALS['con']);
            if ($result) {
                $feed_id = mysqli_insert_id($GLOBALS['con']);
                $post['feed_id'] = $feed_id;
                if ($video_link != null) {
                    //$this->uploadMedia($feed_id,"video",$feed_media_dir);
                }
                $i = 0;
                if ($images != null) {
                    foreach ($images as $feed_image) {
                        if ($feed_image != null) {

                            $feed_image_name = "IMG-" . date("Ymd-his") . $i++ . ".png";
                            $feed_image_link = $feed_media_dir . $feed_image_name;
                            file_put_contents(FEEDS_MEDIA . $feed_image_link, base64_decode($feed_image));
                            $queryInsertImage = "INSERT INTO `feed_image`(`feed_id`, `image_link`) VALUES (" . $feed_id . ",'" . $feed_image_link . "')";
                            $resultImageUploading = mysqli_query($GLOBALS['con'], $queryInsertImage) or $message = mysqli_error($GLOBALS['con']);
                        }

                    }
                }
                $data[] = $post;
                $message="";//"Post successfully submitted";
                $status = SUCCESS;
            } else {
                $status = FAILED;
            }
        }

        else{
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['status']=$status;
        $response['message']=$message;
        $response['feed']=$data;
        return $response;
    }

    public function uploadMedia($postData)
    {

        $response=array();
        $dir = '';
        $mediaName = '';
        $created_date = date("Ymd-His");
        //create Random String.
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        //generate random string with minimum 5 and maximum of 10 characters
        $str = substr(str_shuffle($chars), 0, 8);
        //add extension to file
        //$name = $str."_test";
        $feed_id = $_POST['feed_id'];
        $feed_by = $_POST['feed_by'];
        $mediaType = $_POST['mediaType'];
        $secret_key = $_POST['secret_key'];
        $access_key = $_POST['access_key'];

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {



            $post['feed_id'] = $feed_id;
            $post['mediaType'] = $mediaType;
            $post['feed_by'] = $feed_by;

            $feed_media_dir = "user_" . $feed_by . "/";


            if (!is_dir(FEEDS_MEDIA . $feed_media_dir)) {
                mkdir(FEEDS_MEDIA . $feed_media_dir, 0777);
            }




            if ("video" == $mediaType) {
                if ($_FILES["mediaFile"]["error"] > 0) {
                    $message = $_FILES["mediaFile"]["error"];

                } else {
                    // Image 5 = Video 6 = Audio 7

                    $mediaName = "_test_VIDEO_" . $created_date . ".mp4";

                    $uploadFile = FEEDS_MEDIA . $feed_media_dir . $mediaName;
                    if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                        //store image data.
                        $link = $feed_media_dir . $mediaName;
                        $procedure_insert_set = "CALL UPDATE_VIDEO_LINK ('" . $link . "','" . $feed_id . "' )";
                        $result_procedure = mysqli_query($GLOBALS['con'], $procedure_insert_set) or $message = mysqli_error($GLOBALS['con']);

                        $post['link'] = $link;
                        $status = SUCCESS;
                        $message = "Successfully uploaded!.";
                    } else {
                        $status = FAILED;
                        $message = FAILED_TO_UPLOAD_MEDIA;
                    }
                }
            } else if ("audio" == $mediaType) {
                if ($_FILES["mediaFile"]["error"] > 0) {
                    $message = $_FILES["audio_link"]["error"];
                    $status = 2;
                } else {
                    $mediaName = "_test_AUDIO_" . $created_date . ".mp3";


                    $uploadFile = FEEDS_MEDIA . $feed_media_dir . $mediaName;
                    if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                        //store image data.

                        $link = $feed_media_dir . $mediaName;
                        $procedure_insert_set = "CALL UPDATE_AUDIO_LINK ('" . $link . "','" . $feed_id . "' )";
                        $result_procedure = mysqli_query($GLOBALS['con'], $procedure_insert_set) or $message = mysqli_error($GLOBALS['con']);

                        $post['link'] = $link;
                        $status = SUCCESS;
                        $message = "Successfully uploaded!.";
                    } else {
                        $status = FAILED;
                        $message = FAILED_TO_UPLOAD_MEDIA;
                    }
                }

            }
            else if ("image" == $mediaType) {
                if ($_FILES["mediaFile"]["error"] > 0) {
                    $message = $_FILES["mediaFile"]["error"];
                    $status = 2;
                } else {
                    $mediaName = "_test_IMAGE_" . $created_date . ".jpg";

                    $uploadFile = FEEDS_MEDIA . $feed_media_dir . $mediaName;
                    if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                        //store image data.

                        $link = $feed_media_dir . $mediaName;

                        $insertFields="`feed_id`,`image_link`";
                        $insertValues="'" . $feed_id . "','" . $link . "'";

                        $queryInsertImage = "INSERT INTO " . TABLE_FEED_IMAGE . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                        $resultInsertImage = mysqli_query($GLOBALS['con'], $queryInsertImage) or $message = mysqli_error($GLOBALS['con']);

                        $post['link'] = $link;
                        $status = SUCCESS;
                        $message = "Successfully uploaded!.";
                    } else {
                        $status = FAILED;
                        $message = FAILED_TO_UPLOAD_MEDIA;
                    }
                }

            }
            $data[]=$post;
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $response['status']=$status;
        $response['feed_images']=$data;
        $response['message']=$message;
        return $response;

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

    /*
     *
     * GetAllFeeds
     */
    public function getAllFeedsFunction($postData)
    {
        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            $final_followers = $this->getAllFollowtoFromFollowers($user_id);
            $final_mates = $this->getAllMatesFromStudyMates($user_id);
            $final_teachers = $this->getAllTeachersFromStudentTeacher($user_id);
            $final_groups = $this->getAllGroupsFromTutorialGroupMember($user_id);
            $final_users = $this->getAllUsersFromUsers($user_id);
            $final_feeds_tagged = $this->getAllFeedIdFromFeedsTag($user_id);

            $final_feeds = $this->getAllFeedsFromFeeds($user_id, $final_followers, $final_mates, $final_teachers, $final_groups, $final_users, $final_feeds_tagged);
        }
        else
        {
            $final_feeds['status']=FAILED;
            $final_feeds['message'] = MALICIOUS_SOURCE;
        }
        $response['feeds']=$final_feeds['data'];
        $response['status']=$final_feeds['status'];
        $response['message']=$final_feeds['message'];




        //$data['data']=$final_feeds;
        return $response;
    }

    public function getAllFollowtoFromFollowers($user_id)
    {


        $query = "select follow_to from " . TABLE_FOLLOWERS . " where follower_id='" . $user_id . "' and is_delete=0";

        $result = mysqli_query($GLOBALS['con'],$query) or
        $errorMsg = mysqli_error($GLOBALS['con']);
        $my_row_count = mysqli_num_rows($result);
        //echo "followers:" . $my_row_count;
        $followers_array = array();
        if ($my_row_count > 0) {
            while ($followers = mysqli_fetch_assoc($result)) {
                $followers_array[] = $followers['follow_to'];
            }
        }
        return $followers_array;

    }

    public function getAllMatesFromStudyMates($user_id)
    {
        $query_mates = "select mate_id from " . TABLE_STUDYMATES . " where mate_of='" . $user_id . "' and is_delete=0";
        $result_mates = mysqli_query($GLOBALS['con'],$query_mates) or $errorMsg = mysqli_error($GLOBALS['con']);

        $mates_row_count = mysqli_num_rows($result_mates);
        $mates_array = array();
        //echo "mates:" . $mates_row_count;
        if ($mates_row_count > 0) {
            while ($mates = mysqli_fetch_assoc($result_mates)) {
                $mates_array[] = $mates['mate_id'];
            }
        }
        // return $data['mates'] = $mates_array;
        return $mates_array;

    }

    public function getAllTeachersFromStudentTeacher($user_id)
    {
        $query_teachers = "select teacher_id from " . TABLE_STUDENT_TEACHER . " where student_id='" . $user_id . "' and is_delete=0";
        $result_teachers = mysqli_query($GLOBALS['con'],$query_teachers) or $errorMsg = mysqli_error($GLOBALS['con']);

        $teachers_row_count = mysqli_num_rows($result_teachers);
        $teachers_array = array();
        //echo "teachers:" . $teachers_row_count;
        if ($teachers_row_count > 0) {
            while ($teachers = mysqli_fetch_assoc($result_teachers)) {
                $teachers_array[] = $teachers['teacher_id'];

            }
        }
//        return $data['teachers'] = $teachers_array;
        return $teachers_array;

    }

    public function getAllGroupsFromTutorialGroupMember($user_id)
    {
        $query_group_id = "select group_id from " . TABLE_TUTORIAL_GROUP_MEMBER . " where user_id='" . $user_id . "' and is_delete=0";

        $result_group_ids = mysqli_query($GLOBALS['con'],$query_group_id) or $errorMsg = mysqli_error($GLOBALS['con']);

        $group_ids_count = mysqli_num_rows($result_group_ids);
        //echo "groups:" . $group_ids_count;
        $group_id_array = array();

        if ($group_ids_count > 0) {
            while ($group_ids = mysqli_fetch_assoc($result_group_ids)) {
                $group_id_array[] = $group_ids['group_id'];

            }
        }
//        return $data['groups'] = $group_id_array;
        return $group_id_array;

    }

    public function getAllUsersFromUsers($user_id)
    {
        $query_id_from_user_table = "select id from " . TABLE_USERS . " where role_id='1' and is_delete=0";

        $result_id_from_user_table = mysqli_query($GLOBALS['con'],$query_id_from_user_table) or $errorMsg = mysqli_error($GLOBALS['con']);
        $users_count = mysqli_num_rows($result_id_from_user_table);
        $users_array = array();
        //echo "all_users:" . $users_count;
        if ($users_count > 0) {
            while ($users = mysqli_fetch_assoc($result_id_from_user_table)) {
                $users_array[] = $users['id'];

            }
        }
//        return $data['all_users'] = $users_array;
        return $users_array;

    }

    public function getAllFeedIdFromFeedsTag($user_id)
    {
        $query_feed_id = "select feed_id from " . TABLE_FEEDS_TAGGED_USER . " where user_id='" . $user_id . "' and is_delete=0";
        $result_feed_id = mysqli_query($GLOBALS['con'],$query_feed_id) or $errorMsg = mysqli_error($GLOBALS['con']);
        $feed_id_counts = mysqli_num_rows($result_feed_id);
        $feeds_id_array = array();

        //echo "allfeeds_tagged:" . $feed_id_counts;

        if ($feed_id_counts > 0) {
            while ($feeds_ids = mysqli_fetch_assoc($result_feed_id)) {
                $feeds_id_array[] = $feeds_ids['feed_id'];

            }
        }

        //return $data['allfeeds_tagged'] = $feeds_id_array;
        return $feeds_id_array;
    }


    public function getAllFeedsFromFeeds($user_id,$final_followers, $final_mates, $final_teachers, $final_groups, $final_users, $final_feeds_tagged)
    {

        $string_of_all_ids = '';

        foreach( $final_followers as $value){
            $string_of_all_ids .= "'".$value."',";
        }
        foreach( $final_mates as $value){
            $string_of_all_ids .= "'".$value."',";
        }
        foreach( $final_teachers as $value){
            $string_of_all_ids .= "'".$value."',";
        }
        foreach( $final_groups as $value){
            $string_of_all_ids .= "'".$value."',";
        }
        foreach( $final_users as $value){
            $string_of_all_ids .= "'".$value."',";
        }
        foreach( $final_feeds_tagged as $value){
            $string_of_all_ids .= "'".$value."',";
        }

        // to trim the last comma after finishing concating all the ids
        $final_string=rtrim($string_of_all_ids,",");

        // //echo "test_latest:".$final_string;


        //SELECT f.`id`, f.`comment`, f.`comment_by`,u.`username`,p.`profile_link` FROM `feed_comment` f INNER JOIN `users` u INNER JOIN user_profile_picture p ON f.`comment_by`=u.id and p.user_id=u.id WHERE feed_id=13


        //$queryGetAllFeeds = "select * from " . TABLE_FEEDS . " where feed_by IN ('138','140')";
        $queryGetAllFeeds = "select * from " . TABLE_FEEDS ." where feed_by IN (".$final_string.") and is_delete=0 ORDER BY id DESC Limit 20";
        $resultGetAllFeeds = mysqli_query($GLOBALS['con'],$queryGetAllFeeds) or $errorMsg = mysqli_error($GLOBALS['con']);

        //for counting the number of rows for query result
        $feeds_count = mysqli_num_rows($resultGetAllFeeds);
        $feeds_array = array();
        $allfeeds=array();
        $feedId[]=array();
        if ($feeds_count > 0) {
            while ($feeds = mysqli_fetch_assoc($resultGetAllFeeds)) {
//                $feeds_array['user_id'] = $user_id;
//                $feeds_array['feed_id'] = $feeds['id'];
//                $feeds_array['feed_by'] = $feeds['feed_by'];
//                $feeds_array['feed_text'] = $feeds['feed_text'];
//                $feeds_array['video_link'] = $feeds['video_link'];
//                $feeds_array['audio_link'] = $feeds['audio_link'];
//                $feeds_array['posted_on'] = $feeds['posted_on'];
//                $feeds_array['total_like'] = $feeds['total_like'];
//                $feeds_array['total_comment'] = $feeds['total_comment'];
//                $feeds_array['created_date'] = $feeds['created_date'];
//                $feeds_array['modified_date'] = $feeds['modified_date'];
//
//                $queryUser="select * from ".TABLE_USERS." where id=". $feeds['feed_by'];
//                $resultUser=mysqli_query($GLOBALS['con'],$queryUser) or $errorMsg=mysqli_error($GLOBALS['con']);
//
//                if(mysqli_num_rows($resultUser)){
//                    $val = mysqli_fetch_assoc($resultUser);
//                    $feeds_array['full_name'] = $val['full_name'];
//                    $feeds_array['profile_pic'] = $val['profile_pic'];
//
//                }
//                $queryLike="select * from ".TABLE_FEED_LIKE." where like_by=".$user_id." and feed_id= ". $feeds['id'];
//                $resultLike=mysqli_query($GLOBALS['con'],$queryLike) or $errorMsg=mysqli_error($GLOBALS['con']);
//                $feeds_array['like'] ="0";
//                if(mysqli_num_rows($resultLike)){
//                    $valLike = mysqli_fetch_assoc($resultLike);
//                    $feeds_array['like'] = $valLike['is_delete'];
//                }
//                //$feeds_array['user_id'] = $feeds['user_id'];
//                $feeds_array['comment_list']=array();
//
//                if(sizeof($feeds_array)>0)
//                {
//                    $queryGetAllComments = "SELECT f.id, f.comment, f.comment_by,u.full_name,p.profile_link FROM feed_comment f INNER JOIN users u INNER JOIN user_profile_picture p ON f.comment_by=u.id and p.user_id=u.id WHERE f.feed_id=".$feeds['id']." Limit 2";
//                    $resultGetAlComments = mysqli_query($GLOBALS['con'],$queryGetAllComments) or $errorMsg = mysqli_error($GLOBALS['con']);
//                        $allcomment=array();
//                    //echo "\n".$queryGetAllComments;
//                    //for counting the number of rows for query result
//                    if(mysqli_num_rows($resultGetAlComments))
//                    {
//                        while($comments=mysqli_fetch_assoc($resultGetAlComments))
//                        {
//                            $allcomment[]=$comments;
//                        }
//
//                        $feeds_array['comment_list']=$allcomment;
//                    }
//
//                    //$data['comments']=$comments_array;
//
//                }
                $feedId=$feeds['id'];
                $allfeeds[]=$this->getFeedInArray($feeds,$user_id);
            }
        } else {

        }
        $queryGetAllFeeds = "select * from " . TABLE_FEEDS ." where feed_by =".$user_id."  and is_delete=0 ORDER BY id DESC Limit 20";
        $resultGetAllFeeds = mysqli_query($GLOBALS['con'],$queryGetAllFeeds) or $errorMsg = mysqli_error($GLOBALS['con']);
        // echo $queryGetAllFeeds;
        $feeds_count = mysqli_num_rows($resultGetAllFeeds);

        if ($feeds_count > 0) {
            while ($feeds = mysqli_fetch_assoc($resultGetAllFeeds)) {
                $feedID=$feeds['id'];
                // echo $feedID."\n";
//                    if(in_array($feedID,$feedId)){
//                        echo $feedID."***\n";
                $allfeeds[]=$this->getFeedInArray($feeds,$user_id);
//                    }
            }
        }
        $status = SUCCESS;

        $errorMsg="";
        $data['message'] = $errorMsg;
        $data['status'] = $status;
        $data['data'] = $allfeeds;


        return $data;
        // return $feeds_array;
    }
    public  function  getFeedInArray($feeds,$user_id){
        $feeds_array['user_id'] = $user_id;
        $feeds_array['feed_id'] = $feeds['id'];
        $feeds_array['feed_by'] = $feeds['feed_by'];
        $feeds_array['feed_text'] = $feeds['feed_text'];
        $feeds_array['video_link'] = $feeds['video_link'];
        $feeds_array['audio_link'] = $feeds['audio_link'];
        $feeds_array['video_thumbnail'] = $feeds['video_thumbnail'];
        $feeds_array['posted_on'] = $feeds['posted_on'];
        $feeds_array['total_like'] = $feeds['total_like'];
        $feeds_array['total_comment'] = $feeds['total_comment'];
        $feeds_array['created_date'] = $feeds['created_date'];
        $feeds_array['modified_date'] = $feeds['modified_date'];


        $queryUser="select * from ".TABLE_USERS." where id=". $feeds['feed_by']." and is_delete=0";
        $resultUser=mysqli_query($GLOBALS['con'],$queryUser) or $errorMsg=mysqli_error($GLOBALS['con']);

        if(mysqli_num_rows($resultUser)){
            $val = mysqli_fetch_assoc($resultUser);
            $feeds_array['full_name'] = $val['full_name'];
            $feeds_array['profile_pic'] = $val['profile_pic'];

        }
        $queryLike="select * from ".TABLE_FEED_LIKE." where like_by=".$user_id." and feed_id= ". $feeds['id']." and is_delete=0";
        $resultLike=mysqli_query($GLOBALS['con'],$queryLike) or $errorMsg=mysqli_error($GLOBALS['con']);
        // $feeds_array['like'] ="0";
        if(mysqli_num_rows($resultLike)){
            $valLike = mysqli_fetch_assoc($resultLike);
            $feeds_array['self_like'] = 1;
        }
        else
        {
            $feeds_array['self_like'] = 0;
        }
        //$feeds_array['user_id'] = $feeds['user_id'];
        $feeds_array['comment_list']=array();

        if(sizeof($feeds_array)>0)
        {
             $queryGetAllComments = "SELECT f.id, f.comment, f.comment_by,f.created_date,u.full_name,p.profile_link as 'profile_pic'
            FROM feed_comment f
            INNER JOIN users u ON f.comment_by=u.id
            LEFT JOIN user_profile_picture p ON p.user_id=u.id
            WHERE f.feed_id=".$feeds['id']." AND f.is_delete=0 AND u.is_delete=0 ORDER BY f.id DESC Limit 2";
            $resultGetAlComments = mysqli_query($GLOBALS['con'],$queryGetAllComments) or $errorMsg = mysqli_error($GLOBALS['con']);
            $allcomment=array();
            //echo "\n".$queryGetAllComments;
            //for counting the number of rows for query result
            if(mysqli_num_rows($resultGetAlComments))
            {
                while($comments=mysqli_fetch_assoc($resultGetAlComments))
                {
                    $allcomment[]=$comments;
                }

                $feeds_array['comment_list']=$allcomment;
            }

            //$data['comments']=$comments_array;

        }
        $queryGetImages="SELECT `id`,`image_link` FROM `feed_image` WHERE `feed_id`=".$feeds['id']." and is_delete=0";
        $resultGetImages=mysqli_query($GLOBALS['con'],$queryGetImages) or $errorMsg=mysqli_error($GLOBALS['con']);
        $allImages=array();
        //echo "\n".$queryGetAllComments;
        //for counting the number of rows for query result
        if(mysqli_num_rows($resultGetImages))
        {
            while($images=mysqli_fetch_assoc($resultGetImages))
            {
                $allImages[]=$images;
            }


        }
        $feeds_array['feed_images']=$allImages;
        return $feeds_array;
    }


    /*
        * Get My Feeds
    */
    public function getMyFeeds($postData)
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

        if($isSecure==yes) {

            $queryGetAllFeeds = "select feed.*,user.id as 'UserId',user.full_name,user.profile_pic as 'Profile_pic' from " . TABLE_FEEDS ." feed
        INNER JOIN ".TABLE_USERS." user ON feed.feed_by=user.id where feed.feed_by =".$user_id ." and feed.is_delete=0 and user.is_delete=0";
            $resultGetAllFeeds = mysqli_query($GLOBALS['con'],$queryGetAllFeeds) or $errorMsg = mysqli_error($GLOBALS['con']);
            $feeds_count = mysqli_num_rows($resultGetAllFeeds);

            if ($feeds_count > 0) {
                while ($feed = mysqli_fetch_assoc($resultGetAllFeeds)) {

                    $feeds['feed_id']=$feed['id'];
                    $feeds['feed_text']=$feed['feed_text'];
                    $feeds['video_link']=$feed['video_link'];
                    $feeds['user_id']=$feed['UserId'];
                    $feeds['full_name']=$feed['full_name'];
                    $feeds['profile_pic']=$feed['Profile_pic'];
                    $feeds['total_like']=$feed['total_like'];
                    $feeds['total_comment']=$feed['total_comment'];


                    //For Like
                    $queryLike="select * from ".TABLE_FEED_LIKE." where like_by=".$user_id." and feed_id= ". $feed['id']." and is_delete=0";
                    $resultLike=mysqli_query($GLOBALS['con'],$queryLike) or $errorMsg=mysqli_error($GLOBALS['con']);
                    //$feeds_array['like'] ="0";
                    if(mysqli_num_rows($resultLike)){
                        // $valLike = mysqli_fetch_assoc($resultLike);
                        $feeds['self_like'] = 1;
                    }
                    else
                    {
                        $feeds['self_like'] = 0;
                    }

                    //Get Comments
                    $queryGetAllComments = "SELECT f.id,f.comment ,f.comment_by,f.created_date,u.full_name,p.profile_link as 'profile_pic' FROM ".TABLE_FEED_COMMENT." f INNER JOIN ".TABLE_USERS." u
            ON f.comment_by=u.id LEFT JOIN ".TABLE_USER_PROFILE_PICTURE." p ON p.user_id=u.id WHERE f.feed_id=".$feed['id'] ." AND f.is_delete=0 AND u.is_delete=0 ORDER BY f.id DESC LIMIT 2";
                    //echo $queryGetAllComments;
                    $resultGetAlComments = mysqli_query($GLOBALS['con'], $queryGetAllComments) or $errorMsg = mysqli_error($GLOBALS['con']);
                    $allcomment = array();

                    if (mysqli_num_rows($resultGetAlComments)) {
                        while ($comments = mysqli_fetch_assoc($resultGetAlComments)) {
                            $allcomment[] = $comments;
                        }
                    }
                    $feeds['comment_list'] = $allcomment;


                    //Get Images
                    $queryGetImages = "SELECT `id`,`image_link` FROM `feed_image` WHERE `feed_id`=".$feed['id']." AND is_delete=0";
                    $resultGetImages = mysqli_query($GLOBALS['con'], $queryGetImages) or $errorMsg = mysqli_error($GLOBALS['con']);
                    $allImages = array();
                    //for counting the number of rows for query result
                    if (mysqli_num_rows($resultGetImages)) {
                        while ($images = mysqli_fetch_assoc($resultGetImages)) {
                            $allImages[] = $images;
                        }

                    }
                    $feeds['feed_images'] = $allImages;

                    $data[] = $feeds;
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
        $response['feeds']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

    /*
    * AddQuestionToFavorite
     */
    public function addQuestionToFavorite($postData)
    {
        $data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $question_id = validateObject($postData, 'question_id', "");

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($question_id != null) {
                foreach ($question_id as $record_id) {

                    $queryCheckInQuestion="SELECT * FROM ". TABLE_QUESTIONS." WHERE id=".$record_id;
                    $resultCheckInQuestion=mysqli_query($GLOBALS['con'], $queryCheckInQuestion) or $message = mysqli_error($GLOBALS['con']);

                    if(mysqli_num_rows($resultCheckInQuestion) > 0) {

                        $queryCheckFeed = "SELECT * FROM " . TABLE_USER_FAVORITE_QUESTION . " WHERE user_id=" . $user_id . " AND question_id =" . $record_id . " AND is_delete=0";
                        //echo $queryCheckFeed."\n"; exit;
                        $resultCheckFeed = mysqli_query($GLOBALS['con'], $queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);

                        if (mysqli_num_rows($resultCheckFeed) == 0) {
                            $insertFields = "`user_id`,`question_id`";
                            $insertValues = $user_id . "," . $record_id;

                            $query = "INSERT INTO " . TABLE_USER_FAVORITE_QUESTION . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                            //echo $query;
                            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                            if ($result) {
                                $status = SUCCESS;
                                $message = "favorite synced";
                            } else {
                                $status = FAILED;
                                $message = "";
                            }
                        } else {
                            $status = SUCCESS;
                            $message = RECORD_ALREADY_EXIST;
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
        else {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $response['favorite_question']=$data;
        $response['status']=$status;
        $response['message']=$message;

        return $response;
    }


    public function tempGetMyFeeds($postData)
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
        $isSecure = $security->checkForSecurityTest($access_key,$secret_key);


        //$isSecure = $security->checkForSecurityTest($access_key,$secret_key);
        if($isSecure==yes)
        {

            $queryGetAllFeeds = "select feed.*,user.id as 'UserId',user.full_name as 'Username',user.profile_pic as 'Profile_pic' from " . TABLE_FEEDS ." feed INNER JOIN ".TABLE_USERS." user ON feed.feed_by=user.id where feed.feed_by =".$user_id;
            $resultGetAllFeeds = mysqli_query( $GLOBALS['con'], $queryGetAllFeeds) or $errorMsg = mysqli_error($GLOBALS['con']);
            $feeds_count = mysqli_num_rows($resultGetAllFeeds);

            if ($feeds_count > 0) {
                while ($feed = mysqli_fetch_assoc($resultGetAllFeeds)) {

                    $feeds['postId']=$feed['id'];
                    $feeds['postText']=$feed['feed_text'];
                    $feeds['postVideo']=$feed['video_link'];
                    $feeds['UserId']=$feed['UserId'];
                    $feeds['Username']=$feed['Username'];
                    $feeds['Profile_pic']=$feed['Profile_pic'];
                    $feeds['totalLikes']=$feed['total_like'];
                    $feeds['totalComments']=$feed['total_comment'];

                    //Get Comments
                    $queryGetAllComments = "SELECT f.comment as 'comment_text', f.comment_by,u.username,u.profile_pic FROM ".TABLE_FEED_COMMENT." f INNER JOIN ".TABLE_USERS." u ON f.comment_by=u.id WHERE f.feed_id=".$feed['id'];
                    //echo $queryGetAllComments;
                    $resultGetAlComments = mysqli_query($GLOBALS['con'],$queryGetAllComments) or $errorMsg = mysqli_error($GLOBALS['con']);
                    $allcomment=array();

                    if(mysqli_num_rows($resultGetAlComments))
                    {
                        while($comments=mysqli_fetch_assoc($resultGetAlComments))
                        {
                            $allcomment[]=$comments;
                        }
                    }
                    $feeds['Comments']=$allcomment;


                    //Get Images
                    $queryGetImages="SELECT `id`,`image_link` FROM `feed_image` WHERE `feed_id`=".$feed['id'];
                    $resultGetImages=mysqli_query($GLOBALS['con'],$queryGetImages) or $errorMsg=mysqli_error($GLOBALS['con']);
                    $allImages=array();
                    //for counting the number of rows for query result
                    if(mysqli_num_rows($resultGetImages))
                    {
                        while($images=mysqli_fetch_assoc($resultGetImages))
                        {
                            $allImages[]=$images;
                        }

                    }
                    $feeds['postImage']=$allImages;

                    $data[]=$feeds;
                }
                $status=SUCCESS;
                $message="";
            }
            else
            {
                $status=FAILED;
                $message = DEFAULT_NO_RECORDS;
                $data="";
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
            $data="";
        }

        $response['feed']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }


    public function getSecurirty($postData)
    {
        $message ='';
        $status='';
        $post=array();
        $data=array();
        $response=array();


        /* foreach (getallheaders() as $name => $value) {

              echo "$name :$value\n";

        }*/
        $data['agent']=$_SERVER ['HTTP_USER_AGENT'];
        $response['security']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }




    public function encryptionData($postData)
    {

        $status='';
        $message='';
        $data=array();
        $response=array();

        $username = validateObject($postData, username, "");
        $username = addslashes($username);


        $query = "SELECT config_value FROM ".TABLE_ADMIN_CONFIG. " WHERE config_key='globalPassword'";
        $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
        $secerectkey=mysqli_fetch_row($result);

        //$secerectkey="1234567891234567";
        $sec=new Security();
        echo $encrypted_username=$sec->encrypt($username,$secerectkey[0]); exit;
        //echo $encrypted_username=$sec->encrypt($username,"sandip");



        $data['encrypted']=$encrypted_username;
        //$newClear = $this->fnDecrypt($crypted, $aes256Key);


        $response['data']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

    public function decryptionData($postData)
    {
        $status='';
        $message='';
        $data=array();
        $response=array();


        $username = validateObject($postData, username, "");
        $username = addslashes($username);

        $query = "SELECT config_value FROM ".TABLE_ADMIN_CONFIG. " WHERE config_key='globalPassword'";
        $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);
        $secerectkey=mysqli_fetch_row($result);
        //$secerectkey="1234567891234567";
        $sec=new Security();
        echo $decrypted_username=$sec->decrypt($username,$secerectkey[0]);


        $data['decrpted']=$decrypted_username;

        $response['data']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

    /*
   *  Hashtag
   */

    public function hashTag($postData)
    {
        $status='';
        $message='';
        $data=array();
        $response=array();

        $hashtag_data = validateObject($postData, "hashtag_data", "");
        $hashtag_data = addslashes($hashtag_data);

        $resource_id = validateObject($postData, "resource_id", "");
        $resource_id = addslashes($resource_id);

        $resource_type = validateObject($postData, "resource_type", "");
        $resource_type = addslashes($resource_type);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($resource_type == "book") {
                $resource_name = "book_id";
                $table = TABLE_TAGS_BOOK;
            } elseif ($resource_type == "forum") {
                $resource_name = "forum_question_id";
                $table = TABLE_TAGS_FORUM_QUESTION;
            } elseif ($resource_type == "lecture") {
                $resource_name = "lecture_id";
                $table = TABLE_TAGS_LECTURE;
            } elseif ($resource_type == "question") {
                $resource_name = "question_id";
                $table = TABLE_TAGS_QUESTION;
            } elseif ($resource_type == "assignment") {
                $resource_name = "book_assignment_id	";
                $table = TABLE_TAG_BOOK_ASSIGNMENT;
            }

            $arrayForTags = explode(',', $hashtag_data);

            $arrayTagsID = array();
            $arrayTagsName = array();
            foreach ($arrayForTags as $tag) {

                $arraySeparateKeyValue = explode(':', $tag);
                $arrayTagsID[] = $arraySeparateKeyValue[1];
                $arrayTagsName[] = $arraySeparateKeyValue[0];
                $arrayTagsIDDelete[] = $arraySeparateKeyValue[2];
            }
            //  print_r($arrayTagsID); print_r($arrayTagsName);print_r($arrayTagsIDDelete); exit;
          //  $arrayTagsIDDelete[1] means add tag in table and $arrayTagsIDDelete[0] means remove tag from table

            if ($hashtag_data != null) {

                for ($i = 0; $i < count($arrayTagsName); $i++) {
                    if ($arrayTagsID[$i] == 0) {
                        if($arrayTagsIDDelete[$i]==1) {

                            $selectQueryFotTagName = "SELECT id FROM " . TABLE_TAGS . " WHERE  LOWER(`tag_name`) = LOWER('$arrayTagsName[$i]') AND is_delete=0";

                            //echo $selectQueryFotTagName ; exit;
                            $resultQueryFotTagName = mysqli_query($GLOBALS['con'], $selectQueryFotTagName) or $message = mysqli_error($GLOBALS['con']);
                            $getTagID = mysqli_fetch_row($resultQueryFotTagName);

                            if (mysqli_num_rows($resultQueryFotTagName) == 0) {
                                // while ($rowGetTags = mysqli_fetch_assoc($resultQueryFotTagName)) {

                                //$found=in_array($rowGetTags['tag_name'],$arrayTagsName,true);
                                //echo $rowGetTags['tag_name'];

                                $insertFields = "`tag_name`";
                                $insertValues = "'" . $arrayTagsName[$i] . "'";


                                $queryToInsertNewTag = "INSERT INTO " . TABLE_TAGS . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                                $resultToInsertNewTag = mysqli_query($GLOBALS['con'], $queryToInsertNewTag) or $message = mysqli_error($GLOBALS['con']);

                                $latest_tag_id = mysqli_insert_id($GLOBALS['con']);

                                $insertFields = "`tag_id`,`" . $resource_name . "`";
                                $insertValues = $latest_tag_id . "," . $resource_id;


                                $query = "INSERT INTO " . $table . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                                $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                                if ($result) {
                                    $status = SUCCESS;
                                    $message = "resource hash tagged";
                                } else {
                                    $status = FAILED;
                                    $message = "";
                                }
                                //}
                            } else {

                                $selectQuery = "SELECT * FROM " . $table . " WHERE " . $resource_name . " = " . $resource_id . " AND tag_id = " .$getTagID[0] ." AND is_delete=0";//$arrayTagsID[$i] . " AND is_delete=0";
                                $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

                                //echo $selectQuery; exit;
                                if (mysqli_num_rows($resultQuery) == 0) {
                                    $insertFields = "`tag_id`,`" . $resource_name . "`";
                                    $insertValues = $getTagID[0] . "," . $resource_id;

                                    $query = "INSERT INTO " . $table . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                                    $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                                    if ($result) {
                                        $status = SUCCESS;
                                        $message = "resource hash tagged";
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

                        if($arrayTagsIDDelete[$i]==0)
                        {

                            $selectQuery = "SELECT * FROM " . $table . " WHERE " . $resource_name . " = " . $resource_id . " AND tag_id = " . $arrayTagsID[$i] . " AND is_delete=0";
                            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);

                            if (mysqli_num_rows($resultQuery) > 0) {
                                $queryToDeleteRecord = "UPDATE " . $table . " SET is_delete=1 WHERE " . $resource_name . " = " . $resource_id . " AND tag_id = " . $arrayTagsID[$i] . " AND is_delete=0";
                                // echo $queryToDeleteRecord; exit;
                                $resultToDeleteRecord = mysqli_query($GLOBALS['con'], $queryToDeleteRecord) or $message = mysqli_error($GLOBALS['con']);


                                if ($resultToDeleteRecord) {
                                    $status = SUCCESS;
                                    $message = "resource hash tag is removed";
                                } else {
                                    $status = FAILED;
                                    $message = "failed to remove hash tag";
                                }
                            }
                        }
                        else
                        {

                            $selectQuery = "SELECT * FROM " . $table . " WHERE " . $resource_name . " = " . $resource_id . " AND tag_id = " . $arrayTagsID[$i] ;//. " AND is_delete=0";
                            $resultQuery = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);
                            //echo $selectQuery; exit;
                            if (mysqli_num_rows($resultQuery) == 0) {

                                $insertFields = "`tag_id`,`" . $resource_name . "`";
                                $insertValues = $arrayTagsID[$i] . "," . $resource_id;

                                $query = "INSERT INTO " . $table . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                                $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                                if ($result) {
                                    $status = SUCCESS;
                                    $message = "resource hash tagged";
                                } else {
                                    $status = FAILED;
                                    $message = "";
                                }
                            }
                            else
                            {
                                $queryToDeleteRecord = "UPDATE " . $table . " SET is_delete=0 WHERE " . $resource_name . " = " . $resource_id . " AND tag_id = " . $arrayTagsID[$i];
                                // echo $queryToDeleteRecord; exit;
                                $resultToDeleteRecord = mysqli_query($GLOBALS['con'], $queryToDeleteRecord) or $message = mysqli_error($GLOBALS['con']);


                                if ($resultToDeleteRecord) {
                                    $status = SUCCESS;
                                    $message = "again resource hash tag";
                                } else {
                                    $status = FAILED;
                                    $message = "again failed to resource hash tag";
                                }
                            }

                        }
                    }

                }

            } else {
                $status = FAILED;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else {

            $status = FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['data']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

    /*
     * Get All Hashtag
     */
    public function getAllHashtag($postData)
    {
        $data=array();
        $response=array();

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $tagQuery = "SELECT id as 'tag_id',tag_name as 'tag' FROM " . TABLE_TAGS . " WHERE is_delete=0 ORDER BY id";
            $tagResult = mysqli_query($GLOBALS['con'], $tagQuery) or $message = mysqli_error($GLOBALS['con']);

            if ($tagResult) {
                if (mysqli_num_rows($tagResult)) {
                    while ($rowGetTags = mysqli_fetch_assoc($tagResult)) {
                        $data[] = $rowGetTags;
                    }
                }
                $status = SUCCESS;
                $message = "";
            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else {

            $status = FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['tags']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

    public function getUserFavoriteQuestion($postData)
    {

        $data=array();
        $response=array();
        $post1=array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            //$selectToGetSubjectId = "SELECT id FROM " . TABLE_SUBJECTS . " WHERE is_delete=0";

            $selectToGetSubjectId="SELECT questions.*,subjects.subject_name FROM ".TABLE_USER_FAVORITE_QUESTION." user_favorite_question
            INNER JOIN ".TABLE_QUESTIONS." questions ON questions.id=user_favorite_question.question_id
            INNER JOIN " . TABLE_SUBJECTS . " subjects ON subjects.id=questions.subject_id
            WHERE user_favorite_question.user_id=".$user_id." and user_favorite_question.is_delete=0 GROUP BY questions.id,questions.subject_id ";

            $resultToGetSubjectId=mysqli_query($GLOBALS['con'], $selectToGetSubjectId) or $message = mysqli_error($GLOBALS['con']);
            $subject=array();
            $subjectQuestions=array();
            $post=array();
            if (mysqli_num_rows($resultToGetSubjectId) > 0) {

                while ($rowQuestion= mysqli_fetch_assoc($resultToGetSubjectId)) {

                    $questions=array();
                    $questions['question_id'] = $rowQuestion['id'];
                    $questions['question_text'] = $rowQuestion['question_text'];
                    $questions['question_format'] = $rowQuestion['question_format'];
                    $questions['question_hint'] = $rowQuestion['question_hint'];
                    $questions['subject_id'] = $rowQuestion['subject_id'];
                    $questions['subject_name'] = $rowQuestion['subject_name'];
                    $questions['solution'] = $rowQuestion['solution'];

                     $choice = array();
                      if ($rowQuestion['question_format'] == 'MCQ') {
                          $queryGetChoice = "SELECT `id`, `question_id`, `choice_text`, `is_right`, `image_link`, `audio_link`, `video_link` FROM " . TABLE_ANSWER_CHOICES . " WHERE `question_id`=" . $rowQuestion['id'] . " AND is_delete=0 ";
                          $resultGetChoice = mysqli_query($GLOBALS['con'], $queryGetChoice) or $message = mysqli_error($GLOBALS['con']);
                          // echo $resultGetChoice;
                          if (mysqli_num_rows($resultGetChoice)) {
                              while ($rowGetChoice = mysqli_fetch_assoc($resultGetChoice)) {
                                  $choice[] = $rowGetChoice;

                              }
                              $questions['answers'] = $choice;
                          }
                      } else {
                          $questions['answers'] = $choice;
                      }


                    //Group By Subjects
                    if(sizeof($subject)==0){
                        $subject=$questions['subject_name'];
                    }
                    else{
                        if(in_array($questions['subject_name'],$subject,true)){

                        }else{
                            $subject=$questions['subject_name'];
                        }
                    }

                    $subjectQuestions[$questions['subject_name']][]=$questions;
                }

                //$post[] = $subjectQuestions;
                $data[]=$subjectQuestions;
                $status = SUCCESS;
                $message = REQUEST_ACCEPTED;
            }
            else{
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else {

            $status = FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['user_favorite_questions']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

}

?>