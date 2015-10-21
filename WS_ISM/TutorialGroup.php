<?php

/**
 * User: c161
 * Date: 20/10/15
 */
class TutorialGroup
{

    function __construct()
    {

    }

    public function call_service($service, $postData)
    {
        switch ($service) {
            case "allocateTutorialGroup": {
                return $this->generateTutorialGroup($_POST['user_id']);
            }
                break;
        }
    }

    public function generateTutorialGroup($user_id)
    {
        $get_students_query = "SELECT usr.id, usr.username, crs.course_name, inf.academic_year, pic.profile_link, sch.school_name,
                                sch.school_grade, cls.class_name

                                FROM " . TABLE_USERS . " usr, " . TABLE_STUDENT_ACADEMIC_INFO . " inf, " . TABLE_SCHOOLS . " sch,
                                    " . TABLE_CLASSROOMS . " cls, " . TABLE_COURSES . " crs, " . TABLE_USER_PROFILE_PICTURE . " pic,
                                    (select * from " . TABLE_STUDENT_ACADEMIC_INFO . " where user_id = " . $user_id . ") user

                                WHERE usr.role_id = 2

                                and usr.id in (select user_id from " . TABLE_STUDENT_ACADEMIC_INFO . " where school_id in
                                    (select id from " . TABLE_SCHOOLS . " where school_grade != (select school_grade from
                                    " . TABLE_SCHOOLS . " where id = (select school_id from " . TABLE_STUDENT_ACADEMIC_INFO . "
                                     where user_id = user.user_id))))

                                and usr.id = inf.user_id
                                and inf.school_id = sch.id
                                and inf.classroom_id = cls.id
                                and cls.course_id = crs.id
                                and usr.id = pic.user_id
                                and inf.classroom_id = user.classroom_id
                                and inf.academic_year = user.academic_year";

        $res = mysql_query($get_students_query) or $message = "Find user error : ".mysql_error();

        if ($res) {
            if ((mysql_num_rows($res)) > 0) {

                $studentArray = array();
                $studentArrayA = array();
                $studentArrayB = array();
                $studentArrayC = array();
                $studentArrayD = array();
                $studentArrayE = array();

                while ($student = mysql_fetch_assoc($res)) {

                    $studentObj = '';
                    $studentObj['user_id'] = $student['id'];
                    $studentObj['user_name'] = $student['username'];
                    $studentObj['course_name'] = $student['course_name'];
                    $studentObj['academic_year'] = $student['academic_year'];
                    $studentObj['profile_pic'] = $student['profile_link'];
                    $studentObj['school_name'] = $student['school_name'];
                    $studentObj['school_grade'] = $student['school_grade'];
                    $studentObj['classroom_name'] = $student['class_name'];

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
	                $get_group_name = "SELECT group_name FROM ".TABLE_GROUP_NAMES." order by RAND() LIMIT 1";

	                $res = mysql_query($get_group_name) or $message = mysql_error();

	                if ($res) {
		                if ((mysql_num_rows($res)) > 0) {
			                while ($group = mysql_fetch_assoc($res)) {
				                $group_name = $group['group_name'];
			                }
		                }
	                }

	                $groupId = $group_name." ".$studentArray[0]['course_name'];

		            $insertFields = "group_name, group_type, group_status, is_completed ";
		            $valuesFields = " '".$groupId."', 'tutorial group', 1, 0 " ;

	                $insertGroupQuery = "Insert into ".TABLE_TUTORIAL_GROUPS." (".$insertFields.") values(".$valuesFields.")";
	                $res = mysql_query($insertGroupQuery) or $message = "Group insert error : ".mysql_error();

	                if($res)
	                {
		                $tutorialGroupId = mysql_insert_id();

		                $insertFields = "group_id, user_id, joining_status ";

		                foreach ($studentArray as $member) {
			                $valuesFields = " '".$tutorialGroupId."', '".$member['user_id']."', 0 " ;
			                $insertGroupQuery = "Insert into ".TABLE_TUTORIAL_GROUP_MEMBER." (".$insertFields.") values(".$valuesFields.")";
			                $res = mysql_query($insertGroupQuery) or $message = "GroupMember insert error : ".mysql_error();
			                if ($res) {
				                $status = 1;
			                } else {
				                $status = 2;
				                break;
			                }
		                }

		                if ($status == 1) {
			                $message = 'Group created';
		                }

	                } else {
		                $status = 2;
	                }

                } else {
	                $status = 1;
	                $message = 'Your Tutorial group will be created as soon as all members join.';
                }

            } else {
                $status = 1;
                $message = 'Your Tutorial group will be created as soon as all members join.';
            }

        } else {
            $status = 2;
        }

        $data['status'] = ($status > 1) ? 'failed' : 'success';
        $data['message'] = $message;

	    $result['group_id'] = $groupId;
	    $result['group_members'] = $studentArray;
        $data['data'] = $result;

        return $data;

    }

}

?>