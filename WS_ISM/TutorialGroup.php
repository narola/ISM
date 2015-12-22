<?php

/**
 * User: c161
 * Date: 20/10/15
 */
include_once 'SendEmail.php';
include_once 'ExamFunctions.php';

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

            case "AllocateTeacherToGroup":
            {
                return $this->allocateTeacherToGroup($postData);
             }
                break;

            case "GetTutorialGroupExam":
            {
                return $this->getTutorialGroupExam($postData);
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


                        $selData="tutorial_group_topic_allocation.*,tutorial_topic.topic_name,topic_description,tutorial_topic.created_by,subjects.subject_name,tutorial_topic.topic_day";

                        $queryToFetchTopics="SELECT ".$selData." FROM ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." tutorial_group_topic_allocation
                         INNER JOIN ".TABLE_TUTORIAL_TOPIC." tutorial_topic ON tutorial_group_topic_allocation.tutorial_topic_id=tutorial_topic.id
                         INNER JOIN ".TABLE_SUBJECTS." subjects ON tutorial_topic.subject_id=subjects.id
                         WHERE tutorial_group_topic_allocation.group_id=".$group_id." AND tutorial_group_topic_allocation.week_no = ".$week_no ." AND tutorial_group_topic_allocation.week_day BETWEEN 1 AND 7 AND tutorial_group_topic_allocation.is_delete=0";
                        //AND (".$day_no." >= 1 AND ".$day_no." <= 7)

                        $resultToFetchTopics = mysqli_query($GLOBALS['con'], $queryToFetchTopics) or $message = mysqli_error($GLOBALS['con']);
                       // $post=array();

                        if (mysqli_num_rows($resultToFetchTopics) > 0) {
                            while ($topicGroups = mysqli_fetch_assoc($resultToFetchTopics)) {
                                $post['tutorial_topic_id']=$topicGroups['tutorial_topic_id'];
                                $post['tutorial_topic']=$topicGroups['topic_name'];
                                $post['topic_description']=$topicGroups['topic_description'];
                                $post['assigned_by']=$groups['group_name'];
                                $post['day_name']=$topicGroups['topic_day'];
                                $post['interface_type']=$topicGroups['interface_type'];
                                $post['assigned_time']=$topicGroups['created_date'];
                                $post['subject_name']=$topicGroups['subject_name'];

                                $queryForCurrentDay="SELECT * FROM ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." WHERE DATE_FORMAT(created_date,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d') AND group_id=".$group_id." AND tutorial_topic_id=".$topicGroups['tutorial_topic_id']." AND is_delete=0";
                                $resultForCurrentDay=mysqli_query($GLOBALS['con'], $queryForCurrentDay) or $message = mysqli_error($GLOBALS['con']);
                                if(mysqli_num_rows($resultForCurrentDay)>0)
                                {
                                    $post['is_current_day']="yes";
                                }
                                else{
                                    $post['is_current_day']="no";
                                }



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

            //$queryToGetGroups = "SELECT tutorial_groups.*,tutorial_group_topic_allocation.group_score,tutorial_group_topic_allocation.tutorial_topic_id FROM ". TABLE_TUTORIAL_GROUPS." tutorial_groups INNER JOIN ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." tutorial_group_topic_allocation ON tutorial_group_topic_allocation.group_id=tutorial_groups.id  WHERE tutorial_groups.id=".$group_id." AND tutorial_groups.is_delete=0";



            if($day_no == null && $week_no == null)
            {

//                       $time = time(); // or whenever
//                       $week_of_the_month = ceil(date('d', $time)/7);
                $condition=" AND tutorial_group_topic_allocation.is_delete=0 ORDER BY id DESC LIMIT 14";
            }
            elseif($day_no != null && $week_no==null)
            {
//                $day = time();
//                $dayOfWeek = date( "w", date('Y-m-d'));
//                $weekNum = date('W', $day) - date('W', strtotime(date('Y-m-01', $day))) + 1;

                $condition=" AND  tutorial_group_topic_allocation.week_no=".$week_no."  AND tutorial_group_topic_allocation.week_day=".$day_no." AND tutorial_group_topic_allocation.is_delete=0";

            }

            elseif($week_no != null && $day_no == null)
            {
                $condition=" AND week_no=".$week_no." AND tutorial_group_topic_allocation.is_delete=0";
            }
            elseif($day_no != null && $week_no != null)
            {
                //$condition=" AND  tutorial_group_topic_allocation.week_no=".$week_no."  AND tutorial_group_topic_allocation.week_day=".$day_no." AND tutorial_group_topic_allocation.is_delete=0";
                $condition=" AND  tutorial_group_topic_allocation.week_no=".$week_no." AND tutorial_group_topic_allocation.is_delete=0";
            }

//            else
//            {
//                $day = time();
//                $weekNum = date('W', $day) - date('W', strtotime(date('Y-m-01', $day))) + 1;
//                $condition=" AND week_no=".$weekNum." AND tutorial_group_topic_allocation.is_delete=0";
//            }

            $selData="DISTINCT tutorial_group_topic_allocation.*,tutorial_topic.topic_name,topic_description,tutorial_topic.created_by,subjects.subject_name,tutorial_topic.topic_day";

            $queryToFetchTopics="SELECT ".$selData." FROM ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." tutorial_group_topic_allocation
                         INNER JOIN ".TABLE_TUTORIAL_TOPIC." tutorial_topic ON tutorial_group_topic_allocation.tutorial_topic_id=tutorial_topic.id
                         INNER JOIN ".TABLE_SUBJECTS." subjects ON tutorial_topic.subject_id=subjects.id
                         WHERE tutorial_group_topic_allocation.group_id=".$group_id. $condition;//" AND tutorial_group_topic_allocation.week_no = ".$week_no ." AND tutorial_group_topic_allocation.is_delete=0";

            $resultToFetchTopics = mysqli_query($GLOBALS['con'], $queryToFetchTopics) or $message = mysqli_error($GLOBALS['con']);


            if (mysqli_num_rows($resultToFetchTopics) > 0) {

                while ($topicGroups = mysqli_fetch_assoc($resultToFetchTopics)) {
                    $topic_discussion['tutorial_topic_id']=$topicGroups['tutorial_topic_id'];
                    $topic_discussion['tutorial_topic']=$topicGroups['topic_name'];
                    $topic_discussion['topic_description']=$topicGroups['topic_description'];
                    $topic_discussion['assigned_by']=$topicGroups['created_by'];
                    $topic_discussion['day_name']=$topicGroups['topic_day'];
                    $topic_discussion['interface_type']=$topicGroups['interface_type'];
                    $topic_discussion['assigned_time']=$topicGroups['created_date'];
                    $topic_discussion['subject_name']=$topicGroups['subject_name'];


                    $queryForCurrentDay="SELECT * FROM ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." WHERE DATE_FORMAT(created_date,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d') AND group_id=".$group_id." AND tutorial_topic_id=".$topicGroups['tutorial_topic_id']." AND is_delete=0";
                    $resultForCurrentDay=mysqli_query($GLOBALS['con'], $queryForCurrentDay) or $message = mysqli_error($GLOBALS['con']);
                    if(mysqli_num_rows($resultForCurrentDay)>0)
                    {
                        $topic_discussion['is_current_day']="yes";
                    }
                    else{
                        $topic_discussion['is_current_day']="no";
                    }


                    $queryToGetGroupScore="SELECT sum(group_score)  FROM ".TABLE_TUTORIAL_GROUP_TOPIC_ALLOCATION." WHERE group_id=".$group_id;
                    $resultToGetGetGroupScore= mysqli_query($GLOBALS['con'], $queryToGetGroupScore) or $message = mysqli_error($GLOBALS['con']);
                    $rowToGetGroupScore=mysqli_fetch_row($resultToGetGetGroupScore);

                    $topic_discussion['group_score']=$rowToGetGroupScore[0];

                    $queryToGetTotalComment="SELECT sum(total_comments) as 'active_comments',sum(score) as 'active_comments_score' FROM ".TABLE_TUTORIAL_GROUP_MEMBER_SCORE." WHERE topic_id=".$topicGroups['tutorial_topic_id'];
                    $resultToGetTotalComment= mysqli_query($GLOBALS['con'], $queryToGetTotalComment) or $message = mysqli_error($GLOBALS['con']);
                    $rowToGetTotalComments=mysqli_fetch_row($resultToGetTotalComment);

                    $topic_discussion['total_active_comments']=$rowToGetTotalComments[0];
                    $topic_discussion['total_active_comments_score']=$rowToGetTotalComments[1];


                    $selData="tutorial_group_discussion.*,users.full_name,users.profile_pic";

                     $queryToFetchMembers="SELECT ".$selData." FROM ".TABLE_TUTORIAL_GROUP_DISCUSSION." tutorial_group_discussion
                         INNER JOIN ".TABLE_USERS." users ON tutorial_group_discussion.sender_id=users.id
                         WHERE tutorial_group_discussion.group_id=".$group_id. " AND tutorial_group_discussion.tutorial_topic_id=".$topicGroups['tutorial_topic_id']." AND tutorial_group_discussion.is_delete=0 ";//ORDER BY ". $user_id;
                    $resultToFetchMembers = mysqli_query($GLOBALS['con'], $queryToFetchMembers) or $message = mysqli_error($GLOBALS['con']);

                    $discussion_text=array();
                    if (mysqli_num_rows($resultToFetchMembers) > 0) {
                        while ($members = mysqli_fetch_assoc($resultToFetchMembers)) {
                            $groupMembers=array();

                            $groupMembers['tutorial_topic_id']=$members['tutorial_topic_id'];
                            $groupMembers['comment']=$members['message'];
                            $groupMembers['user_id']=$members['sender_id'];
                            $groupMembers['full_name']=$members['full_name'];
                            $groupMembers['profile_pic']=$members['profile_pic'];
                            $groupMembers['comment_timestamp']=$members['created_date'];
                            $groupMembers['message_type']=$members['message_type'];
                            $groupMembers['media_link']=$members['media_link'];

                            $discussion_text[]=$groupMembers;

                        }

                        $topic_discussion['discussion']=$discussion_text;
                        $status=SUCCESS;
                        $message="Group history retrieved";
                    }

                    else
                    {
                        $status=SUCCESS;
                        $message=DEFAULT_NO_RECORDS;

                    }
                    $data[]=$topic_discussion;
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

                         $queryToGetTotalComment="SELECT sum(total_comments) FROM ".TABLE_TUTORIAL_GROUP_MEMBER_SCORE." WHERE topic_id=".$groups['tutorial_topic_id'];
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


    public function allocateTeacherToGroup($postData)
    {
        $message = '';
        $status = '';
        $post = array();
        $data = array();
        $response = array();

        $group_id = validateObject($postData, 'group_id', "");
        $group_id = addslashes($group_id);

        $tutorial_topic_id = validateObject($postData, 'tutorial_topic_id', "");
        $tutorial_topic_id = addslashes($tutorial_topic_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security = new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key, $secret_key);

        if ($isSecure == yes) {
            $queryToGetSubjects = "SELECT * FROM " . TABLE_TUTORIAL_TOPIC . " WHERE topic_id=" . $tutorial_topic_id . " AND is_delete=0 group by subject_id";
            $resultToGetSubjects = mysqli_query($GLOBALS['con'], $queryToGetSubjects) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultToGetSubjects) > 0) {
                while ($subjects = mysqli_fetch_assoc($resultToGetSubjects)) {
                    $getSubjectsId[] = $subjects['subject_id'];
                    $queryToGetTeachers = "SELECT * FROM " . TABLE_TEACHER_SUBJECT_INFO . " WHERE subject_id=" . $subjects['subject_id'] . " AND is_delete=0 group by user_id";
                    $resultToGetTeachers = mysqli_query($GLOBALS['con'], $queryToGetTeachers) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultToGetTeachers) > 0) {
                        while ($teachers = mysqli_fetch_assoc($resultToGetTeachers)) {
                            $getTeacherId[] = $teachers['user_id'];
                            //print_r($getTeacherId);
                            $queryToChkRecordExist = "SELECT * FROM " . TABLE_TUTORIAL_TOPIC_EXAM . " WHERE tutorial_topic_id=" . $tutorial_topic_id . " AND tutorial_group_id=" . $group_id . " AND allocated_teacher_id=" . $teachers['user_id'] . " AND is_delete=0";
                            // echo $queryToChkRecordExist; exit;
                            $resultToChkRecordExist = mysqli_query($GLOBALS['con'], $queryToChkRecordExist) or $message = mysqli_error($GLOBALS['con']);

                            if (mysqli_num_rows($resultToChkRecordExist) > 0) {
                                $message = RECORD_ALREADY_EXIST;
                                $status = SUCCESS;
                            }
                            else
                            {
                                $exam_id = validateObject($postData, 'exam_id', "");
                                $exam_id = addslashes($exam_id);

                                $exam_name = validateObject($postData, 'exam_name', "");
                                $exam_name = addslashes($exam_name);

                                $classroom_id = validateObject($postData, 'classroom_id', "");
                                $classroom_id = addslashes($classroom_id);

                                $passing_percent = validateObject($postData, 'passing_percent', "");
                                $passing_percent = addslashes($passing_percent);

                                $exam_mode = validateObject($postData, 'exam_mode', "");
                                $exam_mode = addslashes($exam_mode);

                                $subject_id = validateObject($postData, 'subject_id', "");
                                $subject_id = addslashes($subject_id);

                                $book_id = validateObject($postData, 'book_id', "");
                                $book_id = addslashes($book_id);

                                $exam_type = validateObject($postData, 'exam_type', "");
                                $exam_type = addslashes($exam_type);

                                $exam_category = validateObject($postData, 'exam_category', "");
                                $exam_category = addslashes($exam_category);

                                $exam_duration = validateObject($postData, 'exam_duration', "");
                                $exam_duration = addslashes($exam_duration);

                                $exam_start_date = validateObject($postData, 'exam_start_date', "");
                                $exam_start_date = addslashes($exam_start_date);

                                $exam_start_time = validateObject($postData, 'exam_start_time', "");
                                $exam_start_time = addslashes($exam_start_time);

                                $exam_instruction = validateObject($postData, 'exam_instruction', "");
                                $exam_instruction = addslashes($exam_instruction);

                                $declare_results = validateObject($postData, 'declare_results', "");
                                $declare_results = addslashes($declare_results);

                                $attempt_count = validateObject($postData, 'attempt_count', "");
                                $attempt_count = addslashes($attempt_count);

                                $negative_marking = validateObject($postData, 'negative_marking', "");
                                $negative_marking = addslashes($negative_marking);

                                $negative_mark_value = validateObject($postData, 'negative_mark_value', "");
                                $negative_mark_value = addslashes($negative_mark_value);

                                $random_question = validateObject($postData, 'random_question', "");
                                $random_question = addslashes($random_question);

                                $use_question_score = validateObject($postData, 'use_question_score', "");
                                $use_question_score = addslashes($use_question_score);

                                $correct_answer_score = validateObject($postData, 'correct_answer_score', "");
                                $correct_answer_score = addslashes($correct_answer_score);

                                /*$examPostParamClass = new stdClass();
                                $examClass = new ExamFunctions();


                                $examPostParamClass->exam_id = $exam_id;
                                $examPostParamClass->exam_name = $exam_name;
                                $examPostParamClass->user_id = $teachers['user_id'];
                                $examPostParamClass->exam_assessor = $teachers['user_id'];
                                $examPostParamClass->classroom_id = $classroom_id;
                                $examPostParamClass->passing_percent = $passing_percent;
                                $examPostParamClass->exam_mode = $exam_mode;
                                $examPostParamClass->subject_id = $subject_id;
                                $examPostParamClass->attempt_count = $attempt_count;
                                $examPostParamClass->exam_type = $exam_type;
                                $examPostParamClass->exam_category = $exam_category;
                                $examPostParamClass->book_id = $book_id;
                                $examPostParamClass->exam_duration = $exam_duration;
                                $examPostParamClass->exam_start_date = $exam_start_date;
                                $examPostParamClass->exam_start_time = $exam_start_time;
                                $examPostParamClass->exam_instruction = $exam_instruction;
                                $examPostParamClass->declare_results = $declare_results;
                                $examPostParamClass->negative_marking = $negative_marking;
                                $examPostParamClass->negative_mark_value = $negative_mark_value;
                                $examPostParamClass->random_question = $random_question;
                                $examPostParamClass->use_question_score = $use_question_score;
                                $examPostParamClass->correct_answer_score = $correct_answer_score;
                                $examPostParamClass->topic_id = $tutorial_topic_id;*/


                                //==================================== Create Exam ====================================
                                //print_r($examPostParamClass); exit;
                                $insertExamFields = "`created_by`,`exam_name`, `book_id`,`classroom_id`, `subject_id`, `topic_id`, `exam_type`, `exam_category`, `exam_mode`, `pass_percentage`, `duration`, `instructions`, `negative_marking`,`negative_mark_value`,`use_question_score`,`correct_answer_score`, `random_question`, `declare_results`,`attempt_count`";
                                $insertExamValues = "".$teachers['user_id'] . ",'" . $exam_name . "'," . $book_id . "," . $classroom_id . "," . $subjects['subject_id'] . "," . $tutorial_topic_id . ",'" . $exam_type . "','" . $exam_category . "','" . $exam_mode . "'," . $passing_percent . "," . $exam_duration . ",'" . $exam_instruction . "','" . $negative_marking . "'," . $negative_mark_value . ",'".$use_question_score."',".$correct_answer_score.",'" . $random_question . "','" . $declare_results . "'," . $attempt_count;


                                $query = "INSERT INTO " . TABLE_EXAMS . "(" . $insertExamFields . ") VALUES (" . $insertExamValues . ")";
                                //echo $query; exit;
                                $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                                if ($result) {
                                    $getExamId = mysqli_insert_id($GLOBALS['con']);
                                    if ($exam_start_date != null and $exam_start_time != null) {
                                        $insertExamScheduleFields = "`exam_id`, `schedule_by`, `exam_assessor`, `start_date`, `start_time`, `school_classroom_id`";
                                        $insertExamScheduleValues = "" . $post['exam_id'] . "," . $teachers['user_id'] . "," . $teachers['user_id'] . ",'" . $exam_start_date . "','" . $exam_start_time . "'," . $classroom_id;
                                        $queryInsertExamSchedule = "INSERT INTO ". TABLE_EXAM_SCHEDULE."(" . $insertExamScheduleFields . ") VALUES (" . $insertExamScheduleValues . ")";
                                        $resultExamSchedule = mysqli_query($GLOBALS['con'], $queryInsertExamSchedule) or $message = mysqli_error($GLOBALS['con']);
                                        // echo $queryInsertExamSchedule; exit;
                                        if ($resultExamSchedule) {

                                            $status = SUCCESS;
                                            $message="Exam created and scheduled";
                                        } else {
                                            $status = SUCCESS;
                                            $message="Exam is created but not scheduled";
                                        }
                                    }
                                    else{
                                        $status = SUCCESS;
                                        $message="Exam created";
                                    }

                                } else {

                                    $status = FAILED;
                                    $message="Exam is not created and scheduled";
                                }



                                //====================================Exam Created====================================


                                if($getExamId) {

                                    $status = SUCCESS;
                                    $message = "exam is created";

                                    //$chkRecordExist="SELECT * FROM ".TABLE_TUTORIAL_TOPIC_EXAM." WHERE tutorial_topic_id=".$tutorial_topic_id." AND tutorial_group_id=".$group_id." AND exam_id=".$getExamId." AND ";

                                    $insertFields = "tutorial_topic_id,tutorial_group_id,exam_id,exam_type,allocated_teacher_id";
                                    $insertValues = "" . $tutorial_topic_id . ", " . $group_id . ", " . $getExamId . ", 'group'," . $teachers['user_id'];

                                    $query = "INSERT INTO " . TABLE_TUTORIAL_TOPIC_EXAM . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                                    //echo $query; exit;
                                    $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                                    if ($result) {

                                        $queryToGetTeacherProfile = "SELECT users.*,teacher_profile.education FROM " . TABLE_USERS . " users JOIN " . TABLE_TEACHER_PROFILE . " teacher_profile ON teacher_profile.user_id=users.id WHERE teacher_profile.user_id=" . $teachers['user_id'] . " AND  teacher_profile.is_delete=0 AND users.is_delete=0";
                                        $resultToGetTeacherProfile = mysqli_query($GLOBALS['con'], $queryToGetTeacherProfile) or $message = mysqli_error($GLOBALS['con']);

                                        if (mysqli_num_rows($resultToGetTeacherProfile) > 0) {
                                            while ($teacher = mysqli_fetch_assoc($resultToGetTeacherProfile)) {
                                                $profile = array();
                                                $profile['teacher_id'] = $teachers['user_id'];
                                                $profile['teacher_name'] = $teachers['full_name'];
                                                $profile['teacher_pic'] = $teachers['profile_pic'];
                                                $profile['education'] = $teachers['education'];
                                                $profile['exam_id'] = $getExamId;
                                                $profile['tutorial_topic_id'] = $tutorial_topic_id;

                                                $post[] = $profile;
                                            }
                                            $status = SUCCESS;
                                            $message = "Teacher is allocated and exam is also created";
                                        } else {
                                            $status = FAILED;
                                            $message = "Record not found ";
                                        }
                                        $data[]=$post;
                                    }
                                    else {

                                        $status = FAILED;
                                        $message = "failed to insert topic exam";
                                        $data[]=array();
                                    }
                                }
                                else
                                {
                                    $status = FAILED;
                                    $message = "exam id is not get";
                                    $data[]=array();
                                }

                            }
                        }
                    }

                }


            } else {
                $status = FAILED;
                $message = MALICIOUS_SOURCE;
            }
            $response['teacher'] = $data;
            $response['message'] = $message;
            $response['status'] = $status;

            return $response;

        }


    }


    public function getTutorialGroupExam ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $role = validateObject ($postData , 'role', "");
        $role = addslashes($role);

        $exam_id = validateObject ($postData , 'exam_id', "");
        $exam_id = addslashes($exam_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

           // $exam_ids[] = array();
            if ($role == 2) {
                if($exam_id==0)
                {
                    $queryExam = "SELECT exam_id FROM ".TABLE_TUTORIAL_TOPIC_EXAM." WHERE is_delete=0 AND tutorial_group_id IN( SELECT group_id FROM ".TABLE_TUTORIAL_GROUP_MEMBER." user_id= ".$user_id." WHERE is_delete=0)";
                    $resultExam = mysqli_query($GLOBALS['con'], $queryExam) or $message = mysqli_error($GLOBALS['con']);

                    if(mysqli_num_rows($resultExam)>0)
                    while($row=mysqli_fetch_assoc($resultExam))
                    {
                        $exam_ids[]=$row;
                    }
                }
                else{
                    $exam_ids[]['exam_id'] = $exam_id;
                }

            } else if ($role == 3) {
                if($exam_id==0)
                {
                    $queryExam = "SELECT exam_id FROM ".TABLE_TUTORIAL_TOPIC_EXAM." WHERE allocated_teacher_id= ".$user_id." AND is_delete=0";
                    $resultExam = mysqli_query($GLOBALS['con'], $queryExam) or $message = mysqli_error($GLOBALS['con']);

                    if(mysqli_num_rows($resultExam)>0)
                        while($row=mysqli_fetch_assoc($resultExam))
                        {
                            $exam_ids[]=$row;

                        }
                }
                else{
                    $exam_ids[]['exam_id'] = $exam_id;
                }
            }
            //print_r($exam_ids);


            foreach($exam_ids as $e1) {
               // echo $e1['exam_id'];

                $queryToGetExams = "SELECT * FROM " . TABLE_EXAMS . " WHERE id=" .$e1['exam_id'] . " AND is_delete=0";
                $resultToGetExams = mysqli_query($GLOBALS['con'], $queryToGetExams) or $message = mysqli_error($GLOBALS['con']);
                //echo $queryToGetExams;

                if (mysqli_num_rows($resultToGetExams) > 0) {
                    while ($rowExam = mysqli_fetch_assoc($resultToGetExams)) {
                        $examClass = new ExamFunctions();
                        $exams = array($examClass->getExamData($rowExam,1));

                    }
                    $status = SUCCESS;
                    $message = "Exams listed";
                }
                else
                {
                    $exams=array();
                    $status = SUCCESS;
                    $message = DEFAULT_NO_RECORDS;
                }
            }


            if ($exam_ids == null) {
                $exams=array();
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['group_exam']=$exams;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

}

?>