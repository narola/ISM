<?php

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
        }
    }


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

        $final_feeds = $this->getAllFeedsFromFeeds($final_followers, $final_mates, $final_teachers, $final_groups, $final_users, $final_feeds_tagged);

        $response['data']=$final_feeds['data'];
        $response['status']=$final_feeds['status'];
        $response['message']=$final_feeds['message'];

        $response['total_feeds'] = sizeof($response['data']);


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


    public function getAllFeedsFromFeeds($final_followers, $final_mates, $final_teachers, $final_groups, $final_users, $final_feeds_tagged)
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

        $final_string=rtrim($string_of_all_ids,",");
	   // //echo "test_latest:".$final_string;


        //SELECT f.`id`, f.`comment`, f.`comment_by`,u.`username`,p.`profile_link` FROM `feed_comment` f INNER JOIN `users` u INNER JOIN user_profile_picture p ON f.`comment_by`=u.id and p.user_id=u.id WHERE feed_id=13

        //$queryGetAllFeeds = "select * from " . TABLE_FEEDS . " where feed_by IN ('138','140')";
        $queryGetAllFeeds = "select * from " . TABLE_FEEDS ." ,users where feed_by IN (".$final_string.") Limit 20";
        $resultGetAllFeeds = mysql_query($queryGetAllFeeds) or $errorMsg = mysql_error();

        //for counting the number of rows for query result
        $feeds_count = mysql_num_rows($resultGetAllFeeds);
        $feeds_array = array();
        $allfeeds=array();
        //echo "allfeeds:" . $feeds_count;
        //echo "\n\nfinal_query:".$queryGetAllFeeds;


        /*
         * $queryOn="autoGenerateCredential.school_id=schools.id and autoGenerateCredential.course_id=courses.id";
            $queryData="SELECT * FROM ".TABLE_AUTO_GENERATED_CREDENTIAL." autoGenerateCredential INNER JOIN ".TABLE_SCHOOLS." schools INNER JOIN ".TABLE_COURSES." courses ON ".$queryOn." where username='".$userName."'";
         */
        if ($feeds_count > 0) {
            while ($feeds = mysql_fetch_assoc($resultGetAllFeeds)) {

                $feeds_array['feed_id'] = $feeds['id'];
                $feeds_array['user_id'] = $feeds['feed_by'];
                $feeds_array['video_link'] = $feeds['video_link'];
                $feeds_array['audio_link'] = $feeds['audio_link'];
                $feeds_array['posted_on'] = $feeds['posted_on'];
                $feeds_array['total_like'] = $feeds['total_like'];
                $feeds_array['total_comment'] = $feeds['total_comment'];
                $feeds_array['created_date'] = $feeds['created_date'];
                $feeds_array['modified_date'] = $feeds['modified_date'];
                //$feeds_array['user_id'] = $feeds['user_id'];
                $feeds_array['username'] = $feeds['username'];


                $feeds_array['comment']=array();
                if(sizeof($feeds_array)>0)
                {
                    $queryGetAllComments = "SELECT f.`id`, f.`comment`, f.`comment_by`,u.`username`,p.`profile_link` FROM `feed_comment` f INNER JOIN `users` u INNER JOIN user_profile_picture p ON f.`comment_by`=u.id and p.user_id=u.id WHERE f.feed_id=".$feeds['id'];
                    $resultGetAlComments = mysql_query($queryGetAllComments) or $errorMsg = mysql_error();
                        $allcomment=array();
                    //echo "\n".$queryGetAllComments;
                    //for counting the number of rows for query result
                    $comments_count = mysql_num_rows($resultGetAlComments);

                    while($comments=mysql_fetch_assoc($resultGetAlComments))
                    {
                        $allcomment[]=$comments;


                    }

                    $feeds_array['comment']=$allcomment;
                    //$data['comments']=$comments_array;


                }
                $allfeeds[]=$feeds_array;
            }
            //echo "\n\ncomments_query:".$queryGetAllComments;
            //echo "\n\ncomments_count:".$comments_count;
            $status = 1;
            $data['comments']=$comments_array;
            $errorMsg=USER_IS_AVAILABLE;
        } else {
            $status = 2;
            $errorMsg = USER_IS_NOT_AVAILABLE;
        }


        $data['status'] = ($status > 1) ? 'failed' : 'success';
        $data['message'] = $errorMsg;
        $data['data'] = $allfeeds;


        return $data;
       // return $feeds_array;
    }


}

?>