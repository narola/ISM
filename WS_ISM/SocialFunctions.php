<?php
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

        $getFields="f.id, f.comment,f.created_date, f.comment_by,u.full_name,p.profile_link";
        $query="SELECT ".$getFields."  FROM feed_comment f INNER JOIN users u INNER JOIN user_profile_picture p ON f.comment_by=u.id and p.user_id=u.id WHERE f.feed_id=".$feed_id;
        $result=mysql_query($query) or $message=mysql_error();
        //echo $query;
        if(mysql_num_rows($result))
        {
            while($comments=mysql_fetch_assoc($result))
            {
                $data[]=$comments;
            }
            $message="";
        }
        else
        {
            $message=DEFAULT_NO_RECORDS;
        }
        $status="success";
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;

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
        if($unliked_id!=null){
            foreach($unliked_id as $feed_id) {
                $queryCheckFeed = "SELECT * FROM " . TABLE_FEED_LIKE . " where like_by =" . $user_id . " and feed_id=" . $feed_id;
                // echo $queryCheckFeed."\n_unliked";
                $resultCheckFeed = mysql_query($queryCheckFeed) or $message = mysql_error();
                // echo $queryCheckFeed . "\n";
                if (mysql_num_rows($resultCheckFeed)) {
                    $val = mysql_fetch_assoc($resultCheckFeed);
                    $feed_like_id = $val['id'];
                    $queryUpdate = "UPDATE `feed_like` SET `is_delete`=0 WHERE `id`=".$feed_like_id;
                    // echo $procedure;
                    $result = mysql_query($queryUpdate) or $errorMsg = mysql_error();
                }
            }
        }

        if($liked_id!=null) {
            foreach ($liked_id as $feed_id) {
                $queryCheckFeed = "SELECT * FROM " . TABLE_FEED_LIKE . " where like_by =" . $user_id . " and feed_id=" . $feed_id;
                //echo $queryCheckFeed."\n_liked";
                $resultCheckFeed = mysql_query($queryCheckFeed) or $message = mysql_error();
                if (mysql_num_rows($resultCheckFeed)) {
                    $val = mysql_fetch_assoc($resultCheckFeed);
                    $feed_like_id = $val['id'];
                    $queryUpdate = "UPDATE `feed_like` SET `is_delete`=1 WHERE `id`=".$feed_like_id;
                    // echo $procedure;
                    $result = mysql_query($queryUpdate) or $errorMsg = mysql_error();

                } else {
                    $insertFields = "`like_by`, `feed_id`,`is_delete`";
                    $insertValues = "" . $user_id . ", " . $feed_id . ",'1'";
                    $query = "INSERT INTO " . TABLE_FEED_LIKE . " (" . $insertFields . ") VALUES (" . $insertValues . ")";
                    $result = mysql_query($query) or $message = mysql_error();
                }
            }
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

        $user_id = validateObject ($postData , 'tagged_user_id', "");
        //$user_id = addslashes($user_id);

        $tagged_by = validateObject ($postData , 'tagged_by', "");
        $tagged_by = addslashes($tagged_by);



        foreach($user_id as $id){
            $insertFields="`user_id`, `feed_id`, `tagged_by`";
            $insertValues=$id.", ".$feed_id.", ".$tagged_by;
            $query="INSERT INTO " .TABLE_FEEDS_TAGGED_USER." (".$insertFields.") VALUES (".$insertValues.")";
            $result=mysql_query($query) or $message=mysql_error();
            // echo $query;
            if($result)
            {
                $status="success";
                $message="Tagged successfully";
            }
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

        $video_thumbnail = validateObject ($postData , 'video_thumbnail', "");
        $video_thumbnail = addslashes($video_thumbnail);

        if (!is_dir(FEEDS_MEDIA)) {
            mkdir(FEEDS_MEDIA, 0777, true);
        }
        $feed_media_dir = "user_" . $feed_by . "/";
        $dir = FEEDS_MEDIA . $feed_media_dir;
        if (!is_dir($dir)) {
            mkdir($dir, 0777);
        }
         $feed_thumb_link=null;
        if($video_thumbnail!=null){
            $feed_thumb = "Thumb-" . date("Ymd-his").".png";
            $feed_thumb_link = $feed_media_dir.$feed_thumb;
            if(!file_put_contents(FEEDS_MEDIA.$feed_thumb_link, base64_decode($video_thumbnail))){
                $feed_thumb_link="Failed";
            }

        }
        $insertFields="`feed_by`, `feed_text`, `video_link`, `audio_link`, `posted_on`,`video_thumbnail`";
        $insertValues=$feed_by.",'".$feed_text."','".$video_link."','".$audio_link."','".$posted_on."','".$feed_thumb_link."'";
        $queryPostFeed="INSERT INTO ".TABLE_FEEDS."(".$insertFields.") VALUES (".$insertValues.")";
        $result=mysql_query($queryPostFeed) or $message=mysql_error();
        if($result)
        {
            $feed_id=mysql_insert_id();
            $post['feed_id']=$feed_id;
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
            $data[]=$post;
            //$message="“Post successfully submitted";
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

        if (!is_dir(FEEDS_MEDIA)) {
            mkdir(FEEDS_MEDIA, 0777, true);
        }
        
        $data['feed_id']=$feed_id;
        $data['mediaType']=$mediaType;

        $data['feed_by']=$feed_by;
        $feed_media_dir = "user_" . $feed_by . "/";
        $dir = FEEDS_MEDIA . $feed_media_dir;
        if (!is_dir($dir)) {
            mkdir($dir, 0777);
        }
        if("video"==$mediaType)
        {
            if ($_FILES["mediaFile"]["error"] > 0) {
                $message = $_FILES["mediaFile"]["error"];

            } else {
                // Image 5 = Video 6 = Audio 7

                $mediaName = "VIDEO".$created_date."_test.mp4";
                $uploadDir = $dir;
                $uploadFile = FEEDS_MEDIA.$feed_media_dir . $mediaName;
                if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                    //store image data.
                    $link=$feed_media_dir . $mediaName;
                    $procedure_insert_set = "CALL UPDATE_VIDEO_LINK ('".$link."','".$feed_id."' )";
                    $result_procedure = mysql_query($procedure_insert_set) or $errorMsg = mysql_error();
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
                $message = $_FILES["audio_link"]["error"];
                $status=2;
            } else {
                $mediaName = "AUDIO".$created_date."_test.mp3";

                $uploadDir = $dir;
                $uploadFile = FEEDS_MEDIA .$feed_media_dir. $mediaName;
                if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {
                    //store image data.

                    $link=$feed_media_dir . $mediaName;
                    $procedure_insert_set = "CALL UPDATE_AUDIO_LINK ('".$link."','".$feed_id."' )";
                    $result_procedure = mysql_query($procedure_insert_set) or $errorMsg = mysql_error();
                    $status = "success";
                    $message = "Successfully uploaded!.";
                } else {
                    $status = "failed";
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

    /*
     *
     * GetAllFeeds
     */
    public function getAllFeedsFunction($postData)
    {
        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $final_followers = $this->getAllFollowtoFromFollowers($user_id);
        $final_mates = $this->getAllMatesFromStudyMates($user_id);
        $final_teachers = $this->getAllTeachersFromStudentTeacher($user_id);
        $final_groups= $this->getAllGroupsFromTutorialGroupMember($user_id);
        $final_users= $this->getAllUsersFromUsers($user_id);
        $final_feeds_tagged = $this->getAllFeedIdFromFeedsTag($user_id);

        $final_feeds = $this->getAllFeedsFromFeeds($user_id,$final_followers, $final_mates, $final_teachers, $final_groups, $final_users, $final_feeds_tagged);

        $response['data']=$final_feeds['data'];
        $response['status']=$final_feeds['status'];
        $response['message']=$final_feeds['message'];




        //$data['data']=$final_feeds;
        return $response;
    }

    public function getAllFollowtoFromFollowers($user_id)
    {
        $query = "select follow_to from " . TABLE_FOLLOWERS . " where follower_id='" . $user_id . "'";

        $result = mysql_query($query) or
        $errorMsg = mysql_error();
        $my_row_count = mysql_num_rows($result);
        //echo "followers:" . $my_row_count;
        $followers_array = array();
        if ($my_row_count > 0) {
            while ($followers = mysql_fetch_assoc($result)) {
                $followers_array[] = $followers['follow_to'];
            }
        }
        return $followers_array;

    }

    public function getAllMatesFromStudyMates($user_id)
    {
        $query_mates = "select mate_id from " . TABLE_STUDYMATES . " where mate_of='" . $user_id . "'";
        $result_mates = mysql_query($query_mates) or $errorMsg = mysql_error();

        $mates_row_count = mysql_num_rows($result_mates);
        $mates_array = array();
        //echo "mates:" . $mates_row_count;
        if ($mates_row_count > 0) {
            while ($mates = mysql_fetch_assoc($result_mates)) {
                $mates_array[] = $mates['mate_id'];
            }
        }
        // return $data['mates'] = $mates_array;
        return $mates_array;

    }

    public function getAllTeachersFromStudentTeacher($user_id)
    {
        $query_teachers = "select teacher_id from " . TABLE_STUDENT_TEACHER . " where student_id='" . $user_id . "'";
        $result_teachers = mysql_query($query_teachers) or $errorMsg = mysql_error();

        $teachers_row_count = mysql_num_rows($result_teachers);
        $teachers_array = array();
        //echo "teachers:" . $teachers_row_count;
        if ($teachers_row_count > 0) {
            while ($teachers = mysql_fetch_assoc($result_teachers)) {
                $teachers_array[] = $teachers['teacher_id'];

            }
        }
//        return $data['teachers'] = $teachers_array;
        return $teachers_array;

    }

    public function getAllGroupsFromTutorialGroupMember($user_id)
    {
        $query_group_id = "select group_id from " . TABLE_TUTORIAL_GROUP_MEMBER . " where user_id='" . $user_id . "'";

        $result_group_ids = mysql_query($query_group_id) or $errorMsg = mysql_error();

        $group_ids_count = mysql_num_rows($result_group_ids);
        //echo "groups:" . $group_ids_count;
        $group_id_array = array();

        if ($group_ids_count > 0) {
            while ($group_ids = mysql_fetch_assoc($result_group_ids)) {
                $group_id_array[] = $group_ids['group_id'];

            }
        }
//        return $data['groups'] = $group_id_array;
        return $group_id_array;

    }

    public function getAllUsersFromUsers($user_id)
    {
        $query_id_from_user_table = "select id from " . TABLE_USERS . " where role_id='1'";

        $result_id_from_user_table = mysql_query($query_id_from_user_table) or $errorMsg = mysql_error();
        $users_count = mysql_num_rows($result_id_from_user_table);
        $users_array = array();
        //echo "all_users:" . $users_count;
        if ($users_count > 0) {
            while ($users = mysql_fetch_assoc($result_id_from_user_table)) {
                $users_array[] = $users['id'];

            }
        }
//        return $data['all_users'] = $users_array;
        return $users_array;

    }

    public function getAllFeedIdFromFeedsTag($user_id)
    {
        $query_feed_id = "select feed_id from " . TABLE_FEEDS_TAGGED_USER . " where user_id='" . $user_id . "'";
        $result_feed_id = mysql_query($query_feed_id) or $errorMsg = mysql_error();
        $feed_id_counts = mysql_num_rows($result_feed_id);
        $feeds_id_array = array();

        //echo "allfeeds_tagged:" . $feed_id_counts;

        if ($feed_id_counts > 0) {
            while ($feeds_ids = mysql_fetch_assoc($result_feed_id)) {
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
        $queryGetAllFeeds = "select * from " . TABLE_FEEDS ." where feed_by IN (".$final_string.") ORDER BY id DESC Limit 20";
        $resultGetAllFeeds = mysql_query($queryGetAllFeeds) or $errorMsg = mysql_error();

        //for counting the number of rows for query result
        $feeds_count = mysql_num_rows($resultGetAllFeeds);
        $feeds_array = array();
        $allfeeds=array();
        $feedId[]=array();
        if ($feeds_count > 0) {
            while ($feeds = mysql_fetch_assoc($resultGetAllFeeds)) {
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
//                $resultUser=mysql_query($queryUser) or $errorMsg=mysql_error();
//
//                if(mysql_num_rows($resultUser)){
//                    $val = mysql_fetch_assoc($resultUser);
//                    $feeds_array['full_name'] = $val['full_name'];
//                    $feeds_array['profile_pic'] = $val['profile_pic'];
//
//                }
//                $queryLike="select * from ".TABLE_FEED_LIKE." where like_by=".$user_id." and feed_id= ". $feeds['id'];
//                $resultLike=mysql_query($queryLike) or $errorMsg=mysql_error();
//                $feeds_array['like'] ="0";
//                if(mysql_num_rows($resultLike)){
//                    $valLike = mysql_fetch_assoc($resultLike);
//                    $feeds_array['like'] = $valLike['is_delete'];
//                }
//                //$feeds_array['user_id'] = $feeds['user_id'];
//                $feeds_array['comment_list']=array();
//
//                if(sizeof($feeds_array)>0)
//                {
//                    $queryGetAllComments = "SELECT f.id, f.comment, f.comment_by,u.full_name,p.profile_link FROM feed_comment f INNER JOIN users u INNER JOIN user_profile_picture p ON f.comment_by=u.id and p.user_id=u.id WHERE f.feed_id=".$feeds['id']." Limit 2";
//                    $resultGetAlComments = mysql_query($queryGetAllComments) or $errorMsg = mysql_error();
//                        $allcomment=array();
//                    //echo "\n".$queryGetAllComments;
//                    //for counting the number of rows for query result
//                    if(mysql_num_rows($resultGetAlComments))
//                    {
//                        while($comments=mysql_fetch_assoc($resultGetAlComments))
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
        $queryGetAllFeeds = "select * from " . TABLE_FEEDS ." where feed_by =".$user_id." ORDER BY id DESC Limit 20";
        $resultGetAllFeeds = mysql_query($queryGetAllFeeds) or $errorMsg = mysql_error();
       // echo $queryGetAllFeeds;
        $feeds_count = mysql_num_rows($resultGetAllFeeds);

        if ($feeds_count > 0) {
            while ($feeds = mysql_fetch_assoc($resultGetAllFeeds)) {
                $feedID=$feeds['id'];
               // echo $feedID."\n";
//                    if(in_array($feedID,$feedId)){
//                        echo $feedID."***\n";
                $allfeeds[]=$this->getFeedInArray($feeds,$user_id);
//                    }
            }
        }
        $status = "success";

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


        $queryUser="select * from ".TABLE_USERS." where id=". $feeds['feed_by'];
        $resultUser=mysql_query($queryUser) or $errorMsg=mysql_error();

        if(mysql_num_rows($resultUser)){
            $val = mysql_fetch_assoc($resultUser);
            $feeds_array['full_name'] = $val['full_name'];
            $feeds_array['profile_pic'] = $val['profile_pic'];

        }
        $queryLike="select * from ".TABLE_FEED_LIKE." where like_by=".$user_id." and feed_id= ". $feeds['id'];
        $resultLike=mysql_query($queryLike) or $errorMsg=mysql_error();
        $feeds_array['like'] ="0";
        if(mysql_num_rows($resultLike)){
            $valLike = mysql_fetch_assoc($resultLike);
            $feeds_array['like'] = $valLike['is_delete'];
        }
        //$feeds_array['user_id'] = $feeds['user_id'];
        $feeds_array['comment_list']=array();

        if(sizeof($feeds_array)>0)
        {
            $queryGetAllComments = "SELECT f.id, f.comment, f.comment_by,f.created_date,u.full_name,p.profile_link FROM feed_comment f INNER JOIN users u INNER JOIN user_profile_picture p ON f.comment_by=u.id and p.user_id=u.id WHERE f.feed_id=".$feeds['id']." Limit 2";
            $resultGetAlComments = mysql_query($queryGetAllComments) or $errorMsg = mysql_error();
            $allcomment=array();
            //echo "\n".$queryGetAllComments;
            //for counting the number of rows for query result
            if(mysql_num_rows($resultGetAlComments))
            {
                while($comments=mysql_fetch_assoc($resultGetAlComments))
                {
                    $allcomment[]=$comments;
                }

                $feeds_array['comment_list']=$allcomment;
            }

            //$data['comments']=$comments_array;

        }
        $queryGetImages="SELECT `id`,`image_link` FROM `feed_image` WHERE `feed_id`=".$feeds['id'];
        $resultGetImages=mysql_query($queryGetImages) or $errorMsg=mysql_error();
        $allImages=array();
        //echo "\n".$queryGetAllComments;
        //for counting the number of rows for query result
        if(mysql_num_rows($resultGetImages))
        {
            while($images=mysql_fetch_assoc($resultGetImages))
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
		

        $queryGetAllFeeds = "select feed.*,user.id as 'UserId',user.full_name as 'Username',user.profile_pic as 'Profile_pic' from " . TABLE_FEEDS ." feed INNER JOIN ".TABLE_USERS." user ON feed.feed_by=user.id where feed.feed_by =".$user_id;
        $resultGetAllFeeds = mysql_query($queryGetAllFeeds) or $errorMsg = mysql_error();
        $feeds_count = mysql_num_rows($resultGetAllFeeds);

        if ($feeds_count > 0) {
            while ($feed = mysql_fetch_assoc($resultGetAllFeeds)) {
              
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
            $resultGetAlComments = mysql_query($queryGetAllComments) or $errorMsg = mysql_error();
            $allcomment=array();
            
            if(mysql_num_rows($resultGetAlComments))
            {
                while($comments=mysql_fetch_assoc($resultGetAlComments))
                {
                    $allcomment[]=$comments;
                } 
            }
			$feeds['Comments']=$allcomment;

        
        //Get Images
        $queryGetImages="SELECT `id`,`image_link` FROM `feed_image` WHERE `feed_id`=".$feed['id'];
        $resultGetImages=mysql_query($queryGetImages) or $errorMsg=mysql_error();
        $allImages=array();
        //for counting the number of rows for query result
        if(mysql_num_rows($resultGetImages))
        {
            while($images=mysql_fetch_assoc($resultGetImages))
            {
                $allImages[]=$images;
            }

        }
        $feeds['postImage']=$allImages;
			
                $data[]=$feeds;
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

}

?>