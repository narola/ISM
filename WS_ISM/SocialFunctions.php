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
                return $this->getAllFeedsFunction($_POST['user_id']);
            }
                break;
        }
    }


    public function getAllFeedsFunction($user_id)
    {
        $final_followers = $this->getAllFollowtoFromFollowers($user_id);
        $final_mates = $this->getAllMatesFromStudyMates($user_id);
        $final_teachers = $this->getAllTeachersFromStudentTeacher($user_id);
        $final_groups= $this->getAllGroupsFromTutorialGroupMember($user_id);
        $final_users= $this->getAllUsersFromUsers($user_id);
        $final_feeds_tagged = $this->getAllFeedIdFromFeedsTag($user_id);

        $final_feeds['all_feeds'] = $this->getAllFeedsFromFeeds($final_followers, $final_mates, $final_teachers, $final_groups, $final_users, $final_feeds_tagged);

        //$data['total_feeds'] = $my_row_count;
        //return $data['all_feeds']=$final_feeds;
        return $final_feeds;
    }

    public function getAllFollowtoFromFollowers($user_id)
    {
        $query = "select follow_to from " . TABLE_FOLLOWERS . " where follower_id='" . $user_id . "'";

        $result = mysql_query($query) or
        $errorMsg = mysql_error();
        $my_row_count = mysql_num_rows($result);
        echo "followers:" . $my_row_count;
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
        echo "mates:" . $mates_row_count;
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
        echo "teachers:" . $teachers_row_count;
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
        echo "groups:" . $group_ids_count;
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
        echo "all_users:" . $users_count;
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

        echo "allfeeds_tagged:" . $feed_id_counts;

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
        //   $queryGetAllFeeds = "select * from " . TABLE_FEEDS . " where feed_by='" . $feed_by . "'";

//        print_r($final_followers);//56,57,59,54
//        print_r($final_mates);//138,139,140,138
//        print_r($final_teachers);//340-343
//        print_r($final_groups);//59
//        print_r($final_users);//2-5,108,109
//        print_r($final_feeds_tagged);//240,241,242,260,265,340


        $myvar = implode(",", $final_mates);

        $string1 = '';
        $string2 = '';
        $string3 = '';
        $string4 = '';
        $string5 = '';
        $string6 = '';

        foreach( $final_followers as $value){
            $string1 .= "'".$value."',";
        }
	    foreach( $final_mates as $value){
		    $string1 .= "'".$value."',";
        }
	    foreach( $final_teachers as $value){
		    $string1 .= "'".$value."',";
        }
	    foreach( $final_groups as $value){
	        $string1 .= "'".$value."',";
        }
	    foreach( $final_users as $value){
	        $string1 .= "'".$value."',";
        }
	    foreach( $final_feeds_tagged as $value){
	        $string1 .= "'".$value."',";
        }
	    $string1;


        //$queryGetAllFeeds = "select * from " . TABLE_FEEDS . " where feed_by IN ('138','140')";
        $queryGetAllFeeds = "select * from " . TABLE_FEEDS . " where feed_by IN (".$string1.")";
        $resultGetAllFeeds = mysql_query($queryGetAllFeeds) or $errorMsg = mysql_error();

        //for counting the number of rows for query result
        $feeds_count = mysql_num_rows($resultGetAllFeeds);
        $feeds_array = array();
        echo "allfeeds:" . $feeds_count;
        echo "\n\nfinal_query:".$queryGetAllFeeds;
        if ($feeds_count > 0) {
            while ($feeds = mysql_fetch_assoc($resultGetAllFeeds)) {

                $feeds_array[] = $feeds;
            }
        } else {
            $status = 2;
            $errorMsg = USER_IS_NOT_AVAILABLE;
        }

//        $data['message'] = $errorMsg;
//        $data['status'] = $status;
//        $data['total_feeds'] = $my_row_count;

//        return $data['allfeeds'] = $feeds_array;
        return $feeds_array;
    }


}

?>