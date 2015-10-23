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
			case "AllocateTutorialGroup": {
				return $this->allocateTutorialGroup($_POST['user_id']);
			}
				break;

			case "AcceptTutorialGroup": {
				return $this->acceptTutorialGroup($_POST['user_id'], $_POST['group_id'], $_POST['joining_status']);
			}
				break;
		}
	}

	public function allocateTutorialGroup($user_id)
	{
		$selectFields = "users.id, users.username, courses.course_name, academic_info.academic_year, pic.profile_link, schools.school_name,
	                        schools.school_grade, classrooms.class_name";

		$get_students_query = "SELECT " . $selectFields . "
                                FROM " . TABLE_USERS . " users, " . TABLE_STUDENT_ACADEMIC_INFO . " academic_info, " . TABLE_SCHOOLS . " schools,
                                    " . TABLE_CLASSROOMS . " classrooms, " . TABLE_COURSES . " courses, " . TABLE_USER_PROFILE_PICTURE . " pic,
                                    (select * from " . TABLE_STUDENT_ACADEMIC_INFO . " where user_id = " . $user_id . ") user
                                WHERE users.role_id = 2
	                                and users.id in (select user_id from " . TABLE_STUDENT_ACADEMIC_INFO . " where school_id in
	                                    (select id from " . TABLE_SCHOOLS . " where school_grade != (select school_grade from
	                                    " . TABLE_SCHOOLS . " where id = (select school_id from " . TABLE_STUDENT_ACADEMIC_INFO . "
	                                     where user_id = user.user_id))))
	                                and users.id = academic_info.user_id
	                                and academic_info.school_id = schools.id
	                                and academic_info.classroom_id = classrooms.id
	                                and classrooms.course_id = courses.id
	                                and users.id = pic.user_id
	                                and academic_info.classroom_id = user.classroom_id
	                                and academic_info.academic_year = user.academic_year";

		$res = mysql_query($get_students_query) or $message = mysql_error();

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
					$get_group_name = "SELECT group_name FROM " . TABLE_GROUP_NAMES . " order by RAND() LIMIT 1";

					$res = mysql_query($get_group_name) or $message = mysql_error();

					if ($res) {
						if ((mysql_num_rows($res)) > 0) {
							while ($group = mysql_fetch_assoc($res)) {
								$group_name = $group['group_name'];
							}
						}
					}

					$groupId = $group_name . " " . $studentArray[0]['course_name'];

					$insertFields = "group_name, group_type, group_status, is_completed ";
					$valuesFields = " '" . $groupId . "', 'tutorial group', 1, 0 ";

					$insertGroupQuery = "Insert into " . TABLE_TUTORIAL_GROUPS . " (" . $insertFields . ") values(" . $valuesFields . ")";
					$res = mysql_query($insertGroupQuery) or $message = mysql_error();

					if ($res) {
						$tutorialGroupId = mysql_insert_id();

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
							$message = 'Group created';
						} else {
							$message = 'Failed to insert group member';
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

		$result['group_name'] = $groupId;
		$result['group_members'] = $studentArray;
		$data['data'] = $result;

		return $data;

	}

	public function addGroupMember($tutorialGroupId, $userId) {
		$insertFields = "group_id, user_id, joining_status ";
		$valuesFields = " '" . $tutorialGroupId . "', '" . $userId . "', 0 ";
		$insertGroupQuery = "Insert into " . TABLE_TUTORIAL_GROUP_MEMBER . " (" . $insertFields . ") values(" . $valuesFields . ")";
		$res = mysql_query($insertGroupQuery) or $message = mysql_error();
		return $res ? 1 : 2;
	}

	public function acceptTutorialGroup($user_id, $group_id, $joining_status)
	{
		$update_joining_status_query = "UPDATE " . TABLE_TUTORIAL_GROUP_MEMBER . " SET joining_status = " . $joining_status . ",
		                                modified_date = NOW() WHERE user_id = " . $user_id . " and group_id = " . $group_id;

		$res = mysql_query($update_joining_status_query) or $message = mysql_error();

		if ($res) {

			$fetch_group_members = "SELECT joining_status FROM tutorial_group_member WHERE group_id = " .$group_id;
			$res = mysql_query($fetch_group_members) or $message =  mysql_error();

			if ($res) {

				if ((mysql_num_rows($res)) == 5) {

					$complete = true;
					while ($member = mysql_fetch_assoc($res)) {
						if ($member['joining_status'] == 0) {
							$complete = false;
							break;
						}
					}

					if ($complete) {
						$update_group_completed = "UPDATE ".TABLE_TUTORIAL_GROUPS." SET is_completed = 1, modified_date = NOW() WHERE id = ".$group_id;

						$res = mysql_query($update_group_completed) or $message = mysql_error();

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
			$statusText = "in-complete";
		} else {
			$statusText = ($status > 1) ? 'failed' : 'success';
		}
		$data['status'] = $statusText;
		$data['message'] = $message;
		$data['data'] = "";

		return $data;

	}

}

?>