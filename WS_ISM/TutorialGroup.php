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
		}
	}

	public function allocateTutorialGroup($postData)
	{
		$user_id = validateObject($postData, 'user_id', "");
		$user_id = addslashes($user_id);

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

			$get_students_query = "SELECT " . $selectFields . "
                                FROM " . TABLE_USERS . ", " . TABLE_STUDENT_PROFILE . " academic_info, " . TABLE_SCHOOLS . ",
                                    " . TABLE_CLASSROOMS . ", " . TABLE_COURSES . ", " . TABLE_USER_PROFILE_PICTURE . " pic,
                                    (select * from " . TABLE_STUDENT_ACADEMIC_INFO . " where user_id = " . $user_id . ") user
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
	                                and academic_info.academic_year = user.academic_year";
//									and users.id not in (SELECT user_id FROM " . TABLE_TUTORIAL_GROUP_MEMBER . ")

//		        $get_students_query .= " and academic_info.course_id = user.course_id";

			$res = mysql_query($get_students_query) or $message = mysql_error();

			if ($res) {
				if ((mysql_num_rows($res)) > 0) {

					$studentArrayA = array();
					$studentArrayB = array();
					$studentArrayC = array();
					$studentArrayD = array();
					$studentArrayE = array();

					while ($student = mysql_fetch_assoc($res)) {

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
						$get_group_name = "SELECT group_name FROM " . TABLE_GROUP_NAMES . " order by RAND() LIMIT 1";

						$res = mysql_query($get_group_name) or $message = mysql_error();

						if ($res) {
							if ((mysql_num_rows($res)) > 0) {
								while ($group = mysql_fetch_assoc($res)) {
									$groupName = $group['group_name'];
								}
							}
						}

						$groupName .= " " . $studentArray[0]['course_name'];

						$insertFields = "group_name, group_type, group_status, is_completed ";
						$valuesFields = " '" . $groupName . "', 'tutorial group', 1, 0 ";

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

		$data['status'] = ($status > 1) ? 'failed' : 'success';
		$data['message'] = $message;

		$result['tutorial_group_id'] = $tutorialGroupId;
		$result['tutorial_group_joining_status'] = $tutorialGroupJoiningStatus;
		$result['tutorial_group_complete'] = $allMembersAccepted;
		$result['tutorial_group_name'] = $tutorialGroupName;
		$result['tutorial_group_members'] = $tutorialGroupMembers;
		$dataArray = array();
		$dataArray[] = $result;

		$data['tutorial_group'] = $dataArray;

		return $data;

	}

	public function addGroupMember($tutorialGroupId, $userId) {
		$insertFields = "group_id, user_id, joining_status ";
		$valuesFields = " '" . $tutorialGroupId . "', '" . $userId . "', 0 ";
		$insertGroupQuery = "Insert into " . TABLE_TUTORIAL_GROUP_MEMBER . " (" . $insertFields . ") values(" . $valuesFields . ")";
		$res = mysql_query($insertGroupQuery) or $message = mysql_error();
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
			$statusText = "incomplete";
		} else {
			$statusText = ($status > 1) ? 'failed' : 'success';
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
									where members.user_id = " . $user_id . " and members.group_id = groups.id";

		$res = mysql_query($check_allocation_query) or $message = mysql_error();

		$result = '';

		if ($res) {

			$groupName = "";
			$studentArray = array();

			if ((mysql_num_rows($res)) > 0) {

				while ($student = mysql_fetch_assoc($res)) {
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
									        and users.id = pic.user_id";

				$res = mysql_query($get_group_members) or $message = mysql_error();

				if ($res) {
					if ((mysql_num_rows($res)) > 0) {
						while ($student = mysql_fetch_assoc($res)) {
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

}

?>