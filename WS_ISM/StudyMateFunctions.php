<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 23/10/15
 * Time: 9:56 AM
 */
include_once 'ConstantValues.php';
error_reporting(0);

class StudyMateFunctions
{

    function __construct()
    {

    }

    public function call_service($service, $postData)
    {
        switch($service)
        {
            case "GetStudymates":
            {
                return $this->getStudymates($postData);//done
            }
                break;
            case "GetStudymatesWithDetails":
            {
                return $this->getStudymatesWithDetails($postData);//done
            }
                break;

            case "GetSuggestedStudymates":
            {
                return $this->getSuggestedStudymates($postData);//in progress
            }
                break;

            case "SendRequestToStudymate":
            {
                return $this->sendRequestToStudymate($postData);// remaining
            }
                break;

            case "AcceptRequestFromStudymate":
            {
                return $this->acceptRequestFromStudymate($postData);//remaining
            }
                break;
			
			case "GetStudymateRequest":
            {
                return $this->getStudymateRequest($postData);//remaining
            }
                break;
        }
    }



    public function acceptRequestFromStudymate ($postData)
    {
        $data=array();
        $response=array();

        $mate_of = validateObject ($postData , 'mate_of', "");
        $mate_of = addslashes($mate_of);

        $mate_id = validateObject ($postData , 'mate_id', "");
        $mate_id = addslashes($mate_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

           $query = "select * from ".TABLE_STUDYMATES." where ((mate_id=".$mate_id. " and mate_of=". $mate_of." ) or (mate_id=".$mate_of. " and mate_of=". $mate_id." )) and ( status='pending' or status='request') and is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($result) > 0) {
                $updateQuery = "UPDATE " . TABLE_STUDYMATES . " SET status='friend' where ((mate_id=".$mate_id. " and mate_of=". $mate_of." ) or (mate_id=".$mate_of. " and mate_of=". $mate_id." ))";
                $updateResult = mysqli_query($GLOBALS['con'], $updateQuery) or $message = mysqli_error($GLOBALS['con']);

                if ($updateResult) {
                    $message = REQUEST_ACCEPTED;
                    $status = "success";
                }
            } else {
                $message = DEFAULT_NO_RECORDS;
                $status = "failed";
            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['acceptRequest']=$data;
		$response['message'] = $message;
        $response['status'] = $status;
        return $response;
    }

    public function sendRequestToStudymate ($postData)
    {
        $message='';
        $data=array();
        $response=array();
		
		$mate_of = validateObject ($postData , 'mate_of', "");
        $mate_of = addslashes($mate_of);
        
        $mate_id = validateObject ($postData , 'mate_id', "");
        $mate_id = addslashes($mate_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT * FROM ".TABLE_STUDYMATES ." where mate_of=".$mate_of. " and mate_id=". $mate_id." and is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($result) == 0) {
                $insertFields = "`mate_of`,`mate_id`,`status`,`is_request_seen`";
                $insertValues = "" . $mate_of . "," . $mate_id . ",'request', 0";

                $insertQuery = "INSERT INTO " . TABLE_STUDYMATES . "(" . $insertFields . ") VALUES( " . $insertValues . ")";
                $insertResult = mysqli_query($GLOBALS['con'], $insertQuery) or $message = mysqli_error($GLOBALS['con']);

                if ($insertResult) {
                    $message = "Request sent.";
                    $status = "success";
                } else {
                    $message = "";
                    $status = "failed";
                }
            } else {
                $message = "Request already sent.";
                $status = "failed";
            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
		$response['sendRequest']=$data;
		$response['message'] = $message;
        $response['status'] = $status;
		
        return $response;
    }

    /*
     * getSuggestedStudymates
     * This webservice will be used to fetch all the studymates that will be displayed in sidebar to initiate the chat.
     * in progress
     */
    public function getSuggestedStudymates ($postData)
    {
        $message ='';
        $status='';
        $post=array();
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

            $users[] = $user_id;
            $query = "SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE user_id=".$user_id ." AND is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($result)) {
                $val = mysqli_fetch_assoc($result);
                $classroom_id = $val['classroom_id'];
                $course_id = $val['course_id'];
                $school_id = $val['school_id'];
                $querySchool = "SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE school_id=".$school_id." AND is_delete=0";
                $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                if (mysqli_num_rows($resultSchool)) {
                    while ($row = mysqli_fetch_assoc($resultSchool)) {
                        $suggested_studymate_id = $row['user_id'];
                        if (!in_array($suggested_studymate_id, $users)) {
                            $post['user_id'] = $suggested_studymate_id;
                            $data[] = $post;

                        }
                    }
                }
                $querySchool = "SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE classroom_id=".$classroom_id." AND is_delete=0";
                $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                // echo $querySchool;
                if (mysqli_num_rows($resultSchool)) {
                    while ($row = mysqli_fetch_assoc($resultSchool)) {
                        $suggested_studymate_id = $row['user_id'];
                        if (!in_array($suggested_studymate_id, $users)) {
                            $post['user_id'] = $suggested_studymate_id;
                            $data[] = $post;

                        }
                    }
                }
                $querySchool = "SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE course_id=".$course_id." AND is_delete=0";
                $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                //echo $querySchool;
                if (mysqli_num_rows($resultSchool)) {
                    while ($row = mysqli_fetch_assoc($resultSchool)) {
                        $suggested_studymate_id = $row['user_id'];
                        if (!in_array($suggested_studymate_id, $users)) {
                            $post['user_id'] = $suggested_studymate_id;
                            $data[] = $post;
                        }
                    }
                }

                //favorite author
                $querySchool = "SELECT author_id FROM `user_favorite_author` WHERE user_id=" . $user_id." AND is_delete=0";
                $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                $fav_author_id = mysqli_fetch_row($resultSchool);
                //echo "auth=".$fav_author_id = $author_id[0];

                //favorite book
                $querySchool = "SELECT book_id FROM `user_favorite_book` WHERE user_id=" . $user_id." AND is_delete=0";
                $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                $fav_book_id = mysqli_fetch_row($resultSchool);
                //echo "book=".$fav_book_id = $book_id[0];

                //favorite pastime
                $querySchool = "SELECT pastime_id FROM `user_favorite_pastime` WHERE user_id=" . $user_id." AND is_delete=0";
                $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                $fav_pastime_id = mysqli_fetch_row($resultSchool);
                //echo "past=".$fav_pastime_id = $pastime_id[0];

                //favorite movie
                $querySchool = "SELECT movie_id FROM `user_favorite_movie` WHERE user_id=" . $user_id." AND is_delete=0";
                $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                $fav_movie_id = mysqli_fetch_row($resultSchool);
                //echo "mov=".$fav_movie_id = $movie_id[0];

                //favorite author + favorite book
                if ($fav_book_id != NULL && $fav_author_id) {
                    $querySchool = "SELECT * FROM `user_favorite_author` author
INNER JOIN  `user_favorite_book` book on author.user_id=book.user_id, book.book_id=".$fav_book_id." , author.author_id=".$fav_author_id;
                    $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                    //echo $querySchool;
                    if (mysqli_num_rows($resultSchool) > 0) {

                        while ($row = mysqli_fetch_assoc($resultSchool)) {
                            $suggested_studymate_id1 = $row['user_id'];
                            if (!in_array($suggested_studymate_id, $users)) {
                                $post['user_id'] = $suggested_studymate_id1;
                                $data[] = $post;
                            }
                        }
                    }
                }

                //  movie INNER JOIN user_favorite_pastime passtime on passtime.user.id=movie.user_id

                //favorite author + favorite book
                if ($fav_movie_id != NULL && $fav_pastime_id != NULL) {
                    $querySchool = "SELECT * FROM `user_favorite_movie` movie,user_favorite_pastime pastime WHERE movie.movie_id=" . $fav_movie_id." and pastime.pastime_id=".$fav_pastime_id;
                    $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                    echo $querySchool;
                    if (mysqli_num_rows($resultSchool)) {

                        while ($row = mysqli_fetch_assoc($resultSchool)) {
                            $suggested_studymate_id2 = $row['user_id'];
                            if (!in_array($suggested_studymate_id, $users)) {
                                $post['user_id'] = $suggested_studymate_id2;
                                $data[] = $post;

                            }
                        }
                    }
                }

                //Final step
                if ($suggested_studymate_id1 != NULL && $suggested_studymate_id2 != NULL) {
                    $querySchool =  "SELECT users.id,users,full_name.users.profile_pic,studymateRequest.status FROM ".TABLE_STUDYMATES_REQUEST." studymateRequest
            JOIN ".TABLE_USERS."users ON users.id=studymateRequest.request_to_mate_id
            WHERE studymateRequest.request_from_mate_id=" . $suggested_studymate_id1." AND studymateRequest.request_from_mate_id=" . $suggested_studymate_id2.
                        " AND studymateRequest.request_to_mate_id=".$user_id." AND studymateRequest.is_delete=0 AND user.is_delete=0";
                    $resultSchool = mysqli_query($GLOBALS['con'], $querySchool) or $message = mysqli_error($GLOBALS['con']);
                    echo $querySchool;
                    if (mysqli_num_rows($resultSchool)) {

                        while ($row = mysqli_fetch_assoc($resultSchool)) {
                            $suggested_studymate_id = $row['user_id'];
                            if (!in_array($suggested_studymate_id, $users)) {
                                $post['user_id'] = $suggested_studymate_id;
                                $data[] = $post;

                            }
                        }
                    }
                }

            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['status'] =$status;
        $response['message'] =$message;
        $response['suggested_studymates']=$data;
        return $response;

    }

    /*
     * getStudymates
     *
     */
    public function getStudymates($postData)
    {
        $response=array();
        $data=array();


        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $users[] = null;
            $queryGetStudyMate = "SELECT * from ".TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users on studymates.mate_id=users.id where studymates.is_delete=0 and (mate_of=".$user_id." or mate_id=".$user_id.")";
            $resultGetStudyMate = mysqli_query($GLOBALS['con'], $queryGetStudyMate) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultGetStudyMate)) {

                while ($val = mysqli_fetch_assoc($resultGetStudyMate)) {
                    $post = array();
                    $studymate_id = null;
                    if ($user_id != $val['mate_of']) {
                        $studymate_id = $val['mate_of'];
                    } else if ($user_id != $val['mate_id']) {
                        $studymate_id = $val['mate_id'];
                    }
                    //post['user_id']=$val['mate_id'];
                    if (in_array($studymate_id, $users)) {

                    } else {
                        $post['user_id'] = $studymate_id;
                        $users[] = $studymate_id;
                        $post['full_name'] = $val['username'];
                        $post['profile_pic'] = $val['profile_pic'];
                        $data[] = $post;
                    }

                }
                $status = "success";
                $message = "";

            } else {
                $status = "failed";
                $message = DEFAULT_NO_RECORDS;
                $data = "";
            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['studymates']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }

    /*
     * getStudymatesWithDetails
     *
     */
    public function getStudymatesWithDetails($postData)
    {

        $response=array();
        $data=array();
        //$response['data']=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $queryInnerJoin = TABLE_STUDYMATES . " studymates INNER JOIN " . TABLE_USERS . " users INNER JOIN " . TABLE_STUDENT_PROFILE . " studentAcademicInfo INNER JOIN " . TABLE_SCHOOLS . " schools";
            $queryOn = "studymates.mate_id=users.id=studentAcademicInfo.user_id and schools.id=studentAcademicInfo.school_id";

            $queryGetStudyMateAllDetail = "SELECT * from ".$queryInnerJoin." on ".$queryOn."  where  (mate_of=".$user_id." or mate_id=".$user_id.") and studymates.is_delete=0";
            //echo $queryGetStudyMateAllDetail;
            $resultGetStudyMateAllDetail = mysqli_query($GLOBALS['con'], $queryGetStudyMateAllDetail) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultGetStudyMateAllDetail)) {
                $users[] = null;
                while ($val = mysqli_fetch_assoc($resultGetStudyMateAllDetail)) {
                    $post = array();
                    $studymate_id = null;
                    if ($val['mate_id'] != $user_id)
                        $studymate_id = $val['mate_id'];
                    else if ($val['mate_of'] != $user_id)
                        $studymate_id = $val['mate_of'];
                    if (in_array($studymate_id, $users)) {

                    } else {
                        $post['user_id'] = $studymate_id;
                        $users[] = $studymate_id;
                        $post['full_name'] = $val['username'];
                        $post['profile_pic'] = $val['profile_pic'];
                        $post['is_online'] = $val['is_online'];
                        $post['school_name'] = $val['school_name'];
                        array_push($data, $post);
                    }

                }
                $status = "success";
                $message = "";

            } else {
                $status = "failed";
                $message = DEFAULT_NO_RECORDS;
                $data = "";
            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['studymate_details']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
    
    
    
     /*
     * GetStudymateRequest
     *
     */
    public function getStudymateRequest ($postData)
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

            $queryGetStudyMateRequest = "SELECT users.full_name as 'request_from_name',users.profile_pic as 'requester_profile',studymate.is_request_seen,studymate.status,studymate.created_date,studymate.mate_id,studymate.id
		 FROM ".TABLE_STUDYMATES." studymate INNER JOIN ".TABLE_USERS." users on users.id = studymate.mate_id
		 WHERE studymate.mate_of = ".$user_id ." AND ( studymate.status='pending' or studymate.status='request') AND studymate.is_delete=0 AND users.is_delete=0";


            $resultGetStudyMateRequest = mysqli_query($GLOBALS['con'], $queryGetStudyMateRequest) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultGetStudyMateRequest)) {
                while ($val = mysqli_fetch_assoc($resultGetStudyMateRequest)) {

                    $post['record_id'] = $val['id'];
                    $post['request_from_id'] = $val['mate_id'];
                    $post['request_from_name'] = $val['request_from_name'];
                    $post['requester_profile'] = $val['requester_profile'];


                    //Get Requester Scholl name and Course name
                    $querySchoolName = "SELECT schools.school_name,courses.course_name FROM ". TABLE_STUDENT_PROFILE ." studentAcademicInfo
					INNER JOIN ". TABLE_SCHOOLS ." schools ON schools.id= studentAcademicInfo.school_id
					INNER JOIN ". TABLE_COURSES." courses ON courses.id= studentAcademicInfo.course_id
					WHERE studentAcademicInfo.user_id = ".$val['mate_id']." AND studentAcademicInfo.is_delete=0 AND schools.is_delete=0 AND courses.is_delete=0";

                    $resultSchollName = mysqli_query($GLOBALS['con'], $querySchoolName) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultSchollName)) {
                        while ($row = mysqli_fetch_row($resultSchollName)) {
                            $post['requester_school_name'] = $row[0];
                            $post['requester_course_name'] = $row[1];
                        }

                    }

                    $post['request_date'] = $val['created_date'];
                    $post['is_seen'] = $val['is_request_seen'];
                    $post['status'] = $val['status'];
                    $data[] = $post;
                }

                $status = "success";
                $message = "";

            } else {
                $status = "failed";
                $message = DEFAULT_NO_RECORDS;
                $data = "";
            }
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }
        $response['studymate_request']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
     }
}
?>