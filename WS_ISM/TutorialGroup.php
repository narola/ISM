<?php

/**
 * User: c161
 * Date: 20/10/15
 */
include_once 'SendEmail.php';

class TutorialGroup
{

	function __construct()
	{

	}

	public function call_service($service, $postData)
	{
		switch ($service) {
			case "AllocateTutorialGroup": {
				return $this->allocateTutorialGroup($postData);
			}
				break;

			case "AcceptTutorialGroup": {
				return $this->acceptTutorialGroup($postData);
			}
				break;

			case "GetTutorialGroupOfUser": {
				return $this->getTutorialGroupOfUser($postData);
			}
				break;

            case "GetTopicForDay": {
                return $this->getTopicForDay($postData);
            }
                break;

            case "GetGroupHistory":{
                return $this->getGroupHistory($postData);
            }
                break;

            case "GetGroupProfile":{
                return $this->getGroupProfile($postData);
            }
                break;

            case "PingTutorialMate":{
                return $this->pingTutorialMate($postData);
            }
                break;



		}
	}

	public function allocateTutorialGroup($postData)
	{
		$user_id = validateObject($postData, 'user_id', "");
		$user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            $groupName = "";
            $studentArray = array();
            $tutorialGroupId = 0;
            $tutorialGroupJoiningStatus = '';
            $allMembersAccepted = '';
            $tutorialGroupName = "";
            $tutorialGroupMembers = array();

            $tutorialGroup = $this->getTutorialGroupOfUser($user_id);
//		$tutorialGroup['tutorial_group_found'] = false;
            if ($tutorialGroup['tutorial_group_found']) {
                $groupData = $tutorialGroup['tutorial_group'];
                $tutorialGroupId = $groupData['tutorial_group_id'];
                $tutorialGroupJoiningStatus = $groupData['tutorial_group_joining_status'];
                $allMembersAccepted = $groupData['tutorial_group_complete'];
                $tutorialGroupName = $groupData['tutorial_group_name'];
                $tutorialGroupMembers = $groupData['tutorial_group_members'];
                $status = 1;
                $message = 'Group created';
            } else {
//			$selectFields = "users.id, users.username, courses.course_name, academic_info.academic_year, pic.profile_link, schools.school_name, schools.school_grade, classrooms.class_name";
                $selectFields = "users.id, schools.school_grade, courses.course_name";

                $get_students_query ="SELECT " . $selectFields . "
                                FROM " . TABLE_USERS . ", " . TABLE_STUDENT_PROFILE . " academic_info, " . TABLE_SCHOOLS . ",
                                    " . TABLE_CLASSROOMS . ", " . TABLE_COURSES . ", " . TABLE_USER_PROFILE_PICTURE . " pic,
                                    (select * from " . TABLE_STUDENT_PROFILE . " where user_id = " . $user_id . ") user
                                WHERE users.role_id = 2
	                                and users.id in (select user_id from " . TABLE_STUDENT_PROFILE . " where school_id in
	                                    (select id from " . TABLE_SCHOOLS . " where school_grade != (select school_grade from
	                                    " . TABLE_SCHOOLS . " where id = (select school_id from " . TABLE_STUDENT_PROFILE . "
	                                     where user_id = user.user_id))))
	                                and users.id = academic_info.user_id
	                                and academic_info.school_id = schools.id
	                                and academic_info.classroom_id = classrooms.id
	                                and classrooms.course_id = courses.id
	                                and users.id = pic.user_id
	                                and academic_info.classroom_id = user.classroom_id
	                                and academic_info.academic_year = user.academic_year
	                                and users.is_delete=0
	                                and academic_info.is_delete=0
	                                and classrooms.is_delete=0";
//									and users.id not in (SELECT user_id FROM " . TABLE_TUTORIAL_GROUP_MEMBER . ")

//		        $get_students_query .= " and academic_info.course_id = user.course_id";

                $res = mysqli_query($GLOBALS['con'],$get_students_query) or $message = mysqli_error($GLOBALS['con']);

                if ($res) {
                    if ((mysqli_num_rows($res)) > 0) {

                        $studentArrayA = array();
                        $studentArrayB = array();
                        $studentArrayC = array();
                        $studentArrayD = array();
                        $studentArrayE = array();

                        while ($student = mysqli_fetch_assoc($res)) {

                            $studentObj = '';
                            $studentObj['user_id'] = $student['id'];
                            $studentObj['course_name'] = $student['course_name'];
                            $studentObj['school_grade'] = $student['school_grade'];

                            if (strcmp($studentObj['school_grade'], "A") == 0) {
                                $studentArrayA[] = $studentObj;
                            } elseif (strcmp($studentObj['school_grade'], "B") == 0) {
                                $studentArrayB[] = $studentObj;
                            } elseif (strcmp($studentObj['school_grade'], "C") == 0) {
                                $studentArrayC[] = $studentObj;
                            } elseif (strcmp($studentObj['school_grade'], "D") == 0) {
                                $studentArrayD[] = $studentObj;
                            } elseif (strcmp($studentObj['school_grade'], "E") == 0) {
                                $studentArrayE[] = $studentObj;
                            }

                        }

                        if (count($studentArrayA) > 0) {
                            $indexA = rand(0, count($studentArrayA) - 1);
                            $studentArray[] = $studentArrayA[$indexA];
                        }
                        if (count($studentArrayB) > 0) {
                            $indexB = rand(0, count($studentArrayB) - 1);
                            $studentArray[] = $studentArrayB[$indexB];
                        }
                        if (count($studentArrayC) > 0) {
                            $indexC = rand(0, count($studentArrayC) - 1);
                            $studentArray[] = $studentArrayC[$indexC];
                        }
                        if (count($studentArrayD) > 0) {
                            $indexD = rand(0, count($studentArrayD) - 1);
                            $studentArray[] = $studentArrayD[$indexD];
                        }
                        if (count($studentArrayE) > 0) {
                            $indexE = rand(0, count($studentArrayE) - 1);
                            $studentArray[] = $studentArrayE[$indexE];
                        }

                        if (count($studentArray) == 4) {
                            $get_group_name = "SELECT group_name FROM " . TABLE_GROUP_NAMES . " WHERE is_delete=0 order by RAND() LIMIT 1";

                            $res = mysqli_query($GLOBALS['con'],$get_group_name) or $message = mysqli_error($GLOBALS['con']);

                            if ($res) {
                                if ((mysqli_num_rows($res)) > 0) {
                                    while ($group = mysqli_fetch_assoc($res)) {
                                        $groupName = $group['group_name'];
                                    }
                                }
                            }

                            $groupName .= " " . $studentArray[0]['course_name'];

                            $insertFields = "group_name, group_type, group_status, is_completed ";
                            $valuesFields = " '" . $groupName . "', 'tutorial group', 1, 0 ";

                            $insertGroupQuery = "Insert into " . TABLE_TUTORIAL_GROUPS . " (" . $insertFields . ") values(" . $valuesFields . ")";
                            $res = mysqli_query($GLOBALS['con'],$insertGroupQuery) or $message = mysqli_error($GLOBALS['con']);

                            if ($res) {
                                $tutorialGroupId = mysqli_insert_id($GLOBALS['con']);

                                $status = $this->addGroupMember($tutorialGroupId, $user_id);
                                if ($status == 1) {
                                    foreach ($studentArray as $member) {
                                        $status = $this->addGroupMember($tutorialGroupId, $member['user_id']);
                                        if ($status == 2) {
                                            break;
                                        }
                                    }
                                }

                                if ($status == 1) {
                                    $tutorialGroup = $this->getTutorialGroupOfUser($user_id);
                                    if ($tutorialGroup['tutorial_group_found']) {
                                        $groupData = $tutorialGroup['tutorial_group'];
                                        $tutorialGroupId = $groupData['tutorial_group_id'];
                                        $tutorialGroupJoiningStatus = $groupData['tutorial_group_joining_status'];
                                        $allMembersAccepted = $groupData['tutorial_group_complete'];
                                        $tutorialGroupName = $groupData['tutorial_group_name'];
                                        $tutorialGroupMembers = $groupData['tutorial_group_members'];
                                    }

                                    $message = 'Group created';
                                } else {
                                    $message = 'Failed to insert group member';
                                }


                            } else {
                                $status = 2;
                            }

                        } else {
                            $status = 1;
                            $message = 'Your Tutorial group will be created as soon as other members join.';
                        }

                    } else {
                        $status = 1;
                        $message = 'Your Tutorial group will be created as soon as other members join.';
                    }

                } else {
                    $status = 2;
                }
            }


            $status = ($status > 1) ? 'failed' : 'success';
            $data['message'] = $message;


            $result['tutorial_group_id'] = $tutorialGroupId;
            $result['tutorial_group_joining_status'] = $tutorialGroupJoiningStatus;
            $result['tutorial_group_complete'] = $allMembersAccepted;
            $result['tutorial_group_name'] = $tutorialGroupName;
            $result['tutorial_group_members'] = $tutorialGroupMembers;
            $dataArray = array();
            $dataArray[] = $result;
        }
        else{
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
		$data['tutorial_group'] = $dataArray;
        $data['status'] = $status;
        $data['message'] = $message;
		return $data;

	}

	public function addGroupMember($tutorialGroupId, $userId) {
		$insertFields = "group_id, user_id, joining_status ";
		$valuesFields = " '" . $tutorialGroupId . "', '" . $userId . "', 0 ";
		$insertGroupQuery = "Insert into " . TABLE_TUTORIAL_GROUP_MEMBER . " (" . $insertFields . ") values(" . $valuesFields . ")";
		$res = mysqli_query($GLOBALS['con'],$insertGroupQuery) or $message = mysqli_error($GLOBALS['con']);
		return $res ? 1 : 2;
	}

	public function acceptTutorialGroup($postData)
	{
		$user_id = validateObject($postData, 'user_id', "");
		$group_id = validateObject($postData, 'group_id', "");
		$joining_status = validateObject($postData, 'joining_status', "");

		$user_id = addslashes($user_id);
		$group_id = addslashes($group_id);
		$joining_status = addslashes($joining_status);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $update_joining_status_query ="UPDATE " . TABLE_TUTORIAL_GROUP_MEMBER . " SET joining_status = " . $joining_status . ",
		                                modified_date = NOW() WHERE user_id = " . $user_id . " and group_id = " . $group_id ." and is_delete=0";

            $res = mysqli_query($GLOBALS['con'],$update_joining_status_query) or $message = mysqli_error($GLOBALS['con']);

            if ($res) {

                $fetch_group_members = "SELECT joining_status FROM tutorial_group_member WHERE group_id = " .$group_id." AND is_delete=0";
                $res = mysqli_query($GLOBALS['con'],$fetch_group_members) or $message = mysqli_error($GLOBALS['con']);

                if ($res) {

                    if ((mysqli_num_rows($res)) == 5) {

                        $complete = true;
                        while ($member = mysqli_fetch_assoc($res)) {
                            if ($member['joining_status'] == 0) {
                                $complete = false;
                                break;
                            }
                        }

                        if ($complete) {
                            $update_group_completed = "UPDATE ".TABLE_TUTORIAL_GROUPS." SET is_completed = 1, modified_date = NOW() WHERE id = ".$group_id." AND is_delete=0";

                            $res = mysqli_query($GLOBALS['con'],$update_group_completed) or $message = mysqli_error($GLOBALS['con']);

                            if ($res) {
                                $status = 1;
                                $message = "Group is complete";
                            } else {
                                $status = 2;
                            }

                        } else {
                            $status = 3;
                            $message = "Group is not yet complete. Waiting for other members to join.";
                        }

                    } else {
                        $status = 2;
                        $message = "Insufficient tutorial group members.";
                    }
                } else {
                    $status = 2;
                }
            } else {
                $status = 2;
            }

            if ($status == 3) {
                $statusText = "incomplete";
            } else {
                $statusText = ($status > 1) ? 'failed' : 'success';
            }
        }
        else
        {
            $statusText="failed";
            $message = MALICIOUS_SOURCE;
        }
		$data['status'] = $statusText;
		$data['message'] = $message;
		$data['accept_tutorial_group'] = array();

		return $data;

	}

	/**
	 * function to get tutorialGroup of user by passing user_id as parameter
	 * @param $user_id
	 * @return string
	 */
	public function getTutorialGroupOfUser($user_id)
	{

            $selectFields = "members.group_id, members.joining_status, groups.group_name, groups.is_completed ";

            $check_allocation_query = "SELECT " . $selectFields . "
									FROM " . TABLE_TUTORIAL_GROUP_MEMBER . " members, " . TABLE_TUTORIAL_GROUPS . " groups
									where members.user_id = " . $user_id . " and members.group_id = groups.id and members.is_delete=0 and groups.is_delete=0";

            $res = mysqli_query($GLOBALS['con'],$check_allocation_query) or $message = mysqli_error($GLOBALS['con']);

            $result = '';

            if ($res) {

                $groupName = "";
                $studentArray = array();

                if ((mysqli_num_rows($res)) > 0) {

                    while ($student = mysqli_fetch_assoc($res)) {
                        $groupId = $student['group_id'];
                        $joiningStatus = $student['joining_status'];
                        $allMembersAccepted = $student['is_completed'];
                        $groupName = $student['group_name'];
                    }

                    $selectFields = "users.id, users.username, courses.course_name, academic_info.academic_year, pic.profile_link, schools.school_name,
	                        schools.school_grade, classrooms.class_name";

                    $get_group_members = "SELECT " . $selectFields . "

									    FROM " . TABLE_USERS . ", " . TABLE_STUDENT_PROFILE . " academic_info, " . TABLE_SCHOOLS . ",
                                            " . TABLE_CLASSROOMS . ", " . TABLE_COURSES . ", " . TABLE_USER_PROFILE_PICTURE . " pic

									    WHERE users.id in (select user_id from tutorial_group_member where group_id = " . $groupId . ")
									        and users.id = academic_info.user_id
									        and academic_info.school_id = schools.id
									        and academic_info.classroom_id = classrooms.id
									        and classrooms.course_id = courses.id
									        and users.id = pic.user_id
									        and users.is_delete=0
	                                        and academic_info.is_delete=0
	                                        and classrooms.is_delete=0
	                                        and schools.is_delete=0
	                                        and pic.is_delete=0";

                    $res = mysqli_query($GLOBALS['con'],$get_group_members) or $message = mysqli_error($GLOBALS['con']);

                    if ($res) {
                        if ((mysqli_num_rows($res)) > 0) {
                            while ($student = mysqli_fetch_assoc($res)) {
                                $studentObj = '';
                                $studentObj['user_id'] = $student['id'];
                                $studentObj['user_name'] = $student['username'];
                                $studentObj['course_name'] = $student['course_name'];
                                $studentObj['academic_year'] = $student['academic_year'];
                                $studentObj['profile_pic'] = $student['profile_link'];
                                $studentObj['school_name'] = $student['school_name'];
                                $studentObj['school_grade'] = $student['school_grade'];
                                $studentObj['class_name'] = $student['class_name'];

                                $studentArray[] = $studentObj;
                            }
                        }
                    }

                    $tutorialGroup['tutorial_group_id'] = $groupId;
                    $tutorialGroup['tutorial_group_joining_status'] = $joiningStatus;
                    $tutorialGroup['tutorial_group_complete'] = $allMembersAccepted;
                    $tutorialGroup['tutorial_group_name'] = $groupName;
                    $tutorialGroup['tutorial_group_members'] = $studentArray;

                    $result['tutorial_group_found'] = true;
                    $result['tutorial_group'] = $tutorialGroup;
                } else {
                    $result['tutorial_group_found'] = false;
                }
            }

		return $result;

	}


    public function getTopicForDay($postData)
    {
        $message ='';
        $status='';
        $post=array();
        $data=array();
        $response=array();

        $group_id = validateObject($postData, 'group_id', "");
        $group_id = addslashes($group_id);

        $day_no = validateObject($postData, 'day_no', "");
        $day_no = addslashes($day_no);

        $week_no = validateObject($postData, 'week_no', "");
        $week_no = addslashes($week_no);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            $queryToGetGroups = "SELECT * FROM ". TABLE_TUTORIAL_GROUPS." WHERE id=".$group_id." AND is_delete=0";
            $resultToGetGroups = mysqli_query($GLOBALS['con'], $queryToGetGroups) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultToGetGroups) > 0) {

                while ($groups = mysqli_fetch_assoc($resultToGetGroups)) {
                    if($groups['group_cycle']=='finished')
                    {
                        //CALL GENERATE_GROUP_CYCLE;
                    }
                    if($groups['group_cycle']=='ongoing'){

                        $selData="tutorial_group_topic_allocation.*,tutorial_topic.topic_name,topic_description,tutorial_topic.created_by,subjects.subject_name";

                          $queryToFetchTopics="SELECT ".$selData." FROM ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." tutorial_group_topic_allocation
                         INNER JOIN ".TABLE_TUTORIAL_TOPIC." tutorial_topic ON tutorial_group_topic_allocation.tutorial_topic_id=tutorial_topic.id
                         INNER JOIN ".TABLE_SUBJECTS." subjects ON tutorial_topic.subject_id=subjects.id
                         WHERE group_id=".$group_id." AND week_no = ".$week_no ." AND (".$day_no." >= 1 AND ".$day_no." <= 7) AND tutorial_group_topic_allocation.is_delete=0";

                        //(".$day_no." >= 1 AND ".$day_no." <= 7)
                        $resultToFetchTopics = mysqli_query($GLOBALS['con'], $queryToFetchTopics) or $message = mysqli_error($GLOBALS['con']);
                       // $post=array();
                        if (mysqli_num_rows($resultToFetchTopics) > 0) {
                            while ($topicGroups = mysqli_fetch_assoc($resultToFetchTopics)) {
                                $post['tutorial_topic']=$topicGroups['topic_name'];
                                $post['topic_description']=$topicGroups['topic_description'];
                                $post['assigned_by']=$topicGroups['created_by'];
                                $post['day_name']=$topicGroups['topic_day'];
                                $post['interface_type']=$topicGroups['interface_type'];
                                $post['created_date']=$topicGroups['assigned_time'];
                                $post['subject_name']=$topicGroups['subject_name'];
                                $data[]=$post;
                            }

                            $status=SUCCESS;
                            $message="Topic details retrieved";
                        }
                        else
                        {
                            $status=SUCCESS;
                            $message=DEFAULT_NO_RECORDS;

                        }
                    }
                    else
                    {
                        $status=SUCCESS;
                        $message=DEFAULT_NO_RECORDS;
                    }

                }
            }
            else
            {
                $status=SUCCESS;
                $message=DEFAULT_NO_RECORDS;

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['tutorial_topic']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;

    }

    public function getGroupHistory($postData)
    {
        $message ='';
        $status='';
        $post=array();
        $data=array();
        $response=array();
        //echo jddayofweek ( cal_to_jd(CAL_GREGORIAN, date("m"),date("d"), date("Y")) , 1 ); exit; //monday

        $group_id = validateObject($postData, 'group_id', "");
        $group_id = addslashes($group_id);

        $day_no = validateObject($postData, 'day_no', "");
        $day_no = addslashes($day_no);

        $week_no = validateObject($postData, 'week_no', "");
        $week_no = addslashes($week_no);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            //$queryToGetGroups = "SELECT tutorial_groups.*,tutorial_group_topic_allocation.group_score,tutorial_group_topic_allocation.tutorial_topic_id FROM ". TABLE_TUTORIAL_GROUPS." tutorial_groups INNER JOIN ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." tutorial_group_topic_allocation ON tutorial_group_topic_allocation.group_id=tutorial_groups.id  WHERE tutorial_groups.id=".$group_id." AND tutorial_groups.is_delete=0";
            $selData="DISTINCT tutorial_group_topic_allocation.*,tutorial_topic.topic_name,topic_description,tutorial_topic.created_by,subjects.subject_name";


            if($day_no == null && $week_no == null)
            {
                $day = time(); // or whatever unix timestamp, etc
                $weekNum = date('W', $day) - date('W', strtotime(date('Y-m-01', $day))) + 1;
                $week_no=$weekNum;

//                        $time = time(); // or whenever
//                        $week_of_the_month = ceil(date('d', $time)/7);
                $condition=" AND tutorial_group_topic_allocation.week_no=".$week_no." AND tutorial_group_topic_allocation.is_delete=0";
            }
            elseif($day_no!=null)
            {
                jddayofweek ( cal_to_jd(CAL_GREGORIAN, date("m"),date("d"), date("Y")) , 1 ); exit; //monday
                $weekNum = date('W', $day) - date('W', strtotime(date('Y-m-01', $day))) + 1;
                $condition=" AND tutorial_group_topic_allocation.week_day='".jddayofweek."' AND  tutorial_group_topic_allocation.week_no=".$weekNum." AND tutorial_group_topic_allocation.is_delete=0";

            }
            elseif($week_no != null)
            {
                $condition=" AND week_no=".$week_no." AND tutorial_group_topic_allocation.is_delete=0";
            }

            $queryToFetchTopics="SELECT ".$selData." FROM ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." tutorial_group_topic_allocation
                         INNER JOIN ".TABLE_TUTORIAL_TOPIC." tutorial_topic ON tutorial_group_topic_allocation.tutorial_topic_id=tutorial_topic.id
                         INNER JOIN ".TABLE_SUBJECTS." subjects ON tutorial_topic.subject_id=subjects.id
                         WHERE tutorial_group_topic_allocation.group_id=".$group_id. $condition;//" AND tutorial_group_topic_allocation.week_no = ".$week_no ." AND tutorial_group_topic_allocation.is_delete=0";

            $resultToFetchTopics = mysqli_query($GLOBALS['con'], $queryToFetchTopics) or $message = mysqli_error($GLOBALS['con']);


            if (mysqli_num_rows($resultToFetchTopics) > 0) {

                while ($topicGroups = mysqli_fetch_assoc($resultToFetchTopics)) {


                    $post['tutorial_topic']=$topicGroups['tutorial_topic'];
                    $post['topic_description']=$topicGroups['topic_description'];
                    $post['assigned_by']=$topicGroups['created_by'];
                    $post['day_name']=$topicGroups['topic_day'];
                    $post['interface_type']=$topicGroups['interface_type'];
                    $post['created_date']=$topicGroups['assigned_time'];
                    $post['subject_name']=$topicGroups['subject_name'];



                    $queryToGetTotalComment="SELECT total_comments FROM ".TABLE_TUTORIAL_GROUP_MEMBER_SCORE." WHERE topic_id=".$topicGroups['tutorial_topic_id'];
                    $resultToGetTotalComment= mysqli_query($GLOBALS['con'], $queryToGetTotalComment) or $message = mysqli_error($GLOBALS['con']);
                    $rowToGetTotalComments=mysqli_fetch_row($resultToGetTotalComment);

                    $post['total_active_comments']=$rowToGetTotalComments[0];
                    $post['group_score']=$topicGroups['group_score'];



                   if($day_no == null && $week_no == null)
                    {
                        $day = time(); // or whatever unix timestamp, etc
                        $weekNum = date('W', $day) - date('W', strtotime(date('Y-m-01', $day))) + 1;
                        $week_no=$weekNum;

//                        $time = time(); // or whenever
//                        $week_of_the_month = ceil(date('d', $time)/7);
                        $condition=" AND week_no=".$week_no;
                    }
                    elseif($day_no!=null)
                    {
                        jddayofweek ( cal_to_jd(CAL_GREGORIAN, date("m"),date("d"), date("Y")) , 1 ); exit; //monday
                        $weekNum = date('W', $day) - date('W', strtotime(date('Y-m-01', $day))) + 1;
                        $condition=" AND week_day='".jddayofweek."' AND AND week_no=".$weekNum;

                    }
                    elseif($week_no != null)
                    {
                         $condition=" AND week_no=".$week_no;
                    }

                    $selData="tutorial_group_discussion.*,users.full_name,users.profile_pic";

                     $queryToFetchMembers="SELECT ".$selData." FROM ".TABLE_TUTORIAL_GROUP_DISCUSSION." tutorial_group_discussion
                         INNER JOIN ".TABLE_USERS." users ON tutorial_group_discussion.sender_id=users.id
                         WHERE tutorial_group_discussion.group_id=".$group_id. $condition." AND tutorial_group_discussion.is_delete=0 ";//ORDER BY ". $user_id;
                    $resultToFetchMembers = mysqli_query($GLOBALS['con'], $queryToFetchMembers) or $message = mysqli_error($GLOBALS['con']);


                    if (mysqli_num_rows($resultToFetchMembers) > 0) {
                        while ($members = mysqli_fetch_assoc($resultToFetchMembers)) {

                            $groupMembers=array();
                            $groupMembers['comment']=$members['message'];
                            $groupMembers['user_id']=$members['sender_id'];
                            $groupMembers['full_name']=$members['full_name'];
                            $groupMembers['profile_pic']=$members['profile_pic'];
                            $groupMembers['comment_timestamp']=$members['created_date'];
                            $groupMembers['message_type']=$members['message_type'];
                            $groupMembers['media_link']=$members['media_link'];
                            $post['discussion'][]=$groupMembers;
                        }

                        $status=SUCCESS;
                        $message="Group history retrieved";
                    }
                    else
                    {
                        $status=SUCCESS;
                        $message=DEFAULT_NO_RECORDS;

                    }

                    $data[]=$post;
                }
            }
            else
            {
                $status=SUCCESS;
                $message=DEFAULT_NO_RECORDS;

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['group_history']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;

    }


    public function getGroupProfile($postData)
    {
        $message ='';
        $status='';
        $post=array();
        $data=array();
        $response=array();


        $group_id = validateObject($postData, 'group_id', "");
        $group_id = addslashes($group_id);

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            //$queryToGetGroups = "SELECT tutorial_groups.*,tutorial_group_topic_allocation.group_score,tutorial_group_topic_allocation.tutorial_topic_id FROM ". TABLE_TUTORIAL_GROUPS." tutorial_groups INNER JOIN ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." tutorial_group_topic_allocation ON tutorial_group_topic_allocation.group_id=tutorial_groups.id  WHERE tutorial_groups.id=".$group_id." AND tutorial_groups.is_delete=0";
            $queryToGetGroups = "SELECT tutorial_groups.* FROM ". TABLE_TUTORIAL_GROUPS." tutorial_groups  WHERE tutorial_groups.id=".$group_id." AND tutorial_groups.is_delete=0";
            $resultToGetGroups = mysqli_query($GLOBALS['con'], $queryToGetGroups) or $message = mysqli_error($GLOBALS['con']);
            $groupMembers=array();
            if (mysqli_num_rows($resultToGetGroups) > 0) {

                while ($groups = mysqli_fetch_assoc($resultToGetGroups)) {
                        $post['group_name']=$groups['group_name'];
                        $post['group_profile_pic']=$groups['group_profile_pic'];
                        $post['group_name']=$groups['group_name'];

                         $queryToGetTotalComment="SELECT total_comments FROM ".TABLE_TUTORIAL_GROUP_MEMBER_SCORE." WHERE topic_id=".$groups['tutorial_topic_id'];
                         $resultToGetTotalComment= mysqli_query($GLOBALS['con'], $queryToGetTotalComment) or $message = mysqli_error($GLOBALS['con']);
                         $rowToGetTotalComments=mysqli_fetch_row($resultToGetTotalComment);

                        $post['total_active_comments']=$rowToGetTotalComments[0];
                        $post['group_score']=$groups['group_score'];
                        $post['group_rank']=$groups['group_rank'];

                        $selData="tutorial_group_member.*,users.full_name,users.profile_pic,school.school_name";
                        $queryToFetchMembers="SELECT ".$selData." FROM ".TABLE_TUTORIAL_GROUP_MEMBER." tutorial_group_member
                         INNER JOIN ".TABLE_USERS." users ON tutorial_group_member.user_id=users.id
                         INNER JOIN ". TABLE_STUDENT_PROFILE." studentProfile ON users.id=studentProfile.user_id
                         LEFT JOIN ".TABLE_SCHOOLS." school ON school.id=studentProfile.school_id
                         WHERE tutorial_group_member.group_id=".$group_id." AND tutorial_group_member.is_delete=0 ORDER BY (tutorial_group_member.user_id=".$user_id.") DESC";
                        $resultToFetchMembers = mysqli_query($GLOBALS['con'], $queryToFetchMembers) or $message = mysqli_error($GLOBALS['con']);


                        if (mysqli_num_rows($resultToFetchMembers) > 0) {
                            while ($members = mysqli_fetch_assoc($resultToFetchMembers)) {
                                $groupMembers['user_id']=$members['user_id'];
                                $groupMembers['full_name']=$members['full_name'];
                                $groupMembers['profile_pic']=$members['profile_pic'];
                                $groupMembers['user_is_online']=$members['is_online'];
                                $groupMembers['school_name']=$members['school_name'];
                                $groupMembers['last_seen']=$members['last_seen'];
                                $post['group_members'][]=$groupMembers;

                            }

                            $status=SUCCESS;
                            $message="Topic details retrieved";
                        }
                        else
                        {
                            $status=SUCCESS;
                            $message=DEFAULT_NO_RECORDS;

                        }

                $data[]=$post;
                }
            }
            else
            {
                $status=SUCCESS;
                $message=DEFAULT_NO_RECORDS;

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['tutorial_group_profile']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;

    }

    public  function pingTutorialMate($postData)
    {
        $message ='';
        $post=array();
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

            $query = "SELECT * FROM ".TABLE_USERS." WHERE `id`=" . $user_id ." AND is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($result))
            {
                $userData=mysqli_fetch_row($result);
                $email_address=$userData[7];
                $contact_number=$userData[8];

                $sendEmail = new SendEmail();
                $message = "Hello ISM,\nI am very much interested to be part of ISM system.Please check my details below and let me know how can I become the part of this system.\nContact number: " . $contact_number .
                    "\nEmail address: " . $email_address ."\n\nI am waiting for your call.
		\nThanks.";
                $status = SUCCESS;
                $sendEmail->sendemail($email_address, $message, "Ping to Tutorial Mate", "ism.educare@gmail.com");
                //  $response['status'] =$status;
                $message="ping sent to the user";


                //For send sms(Remaining)


            }
            else
            {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['status'] =$status;
        $response['message'] =$message;
        $response['ping_tutorial_mate']=$post;
        return $response;

    }




}

?>