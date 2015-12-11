<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 28/10/15
 * Time: 10:23 AM
 */

include_once 'SocialFunctions.php';
error_reporting(1);
class ExamFunctions
{

    function __construct()
    {

    }

    public function call_service($service, $postData)
    {
        switch ($service) {
            case "CreateExam":
            {
                return $this->createExam($postData);//done
            }
                break;

            case "SetQuestionsForExam":
            {
                return $this->setQuestionsForExam($postData);//done
            }
                break;
            case "GetQuestionBank":
            {
                return $this->getQuestionBank($postData);//done
            }
                break;

             case "GetCourses":
             {
                 return $this->getCourses($postData); //done
             }
                 break;

            case "GetSubject":
            {
                return $this->getSubject($postData); //done
            }
                break;

            case "GetTopics":
            {
                return $this->getTopics($postData); //done
            }
                break;

            case "GetClassrooms":
            {
                return $this->getClassrooms($postData); //done
            }
                break;

            case "GetAllExams":
            {
                return $this->getAllExams($postData); // done ---- book_id
            }
                break;
            case "GetExamQuestions":
            {
                return $this->getExamQuestions($postData); //done
            }
                break;
            case "GetExamSubmission":
            {
                return $this->getExamSubmission($postData); // remaining
            }
                break;

            case "GetExamEvaluation":
            {
                return $this->getExamEvaluation($postData); // done
            }
                break;
            
            case "CreateQuestion":
            {
                return $this->createQuestion($postData);//in progress
            }
                break;
                
            case "UploadMediaForQuestion":
            {
                return $this->uploadMediaForQuestion($postData);//in progress
            }
                break;
			
			case "GetAllResults":
            {
                return $this->getAllResults($postData);//in progress
            }
                break;
                
            case "GetStudentResultsByExam":
            {
                return $this->getStudentResultsByExam($postData);//in progress
            }
                break;
                
			case "GetHighScorers":
			{
                return $this->getHighScorers($postData);//in progress
            }
                break;

            case "TempCreateQuestion":
            {
                return $this->tempCreateQuestion($postData);//in progress
            }
                break;

            case "GetBooks":
            {
                return $this->getBooks($postData);//in progress
            }
                break;

            case "GetExamsByUser":
            {
                return $this->getExamsByUser($postData);//in progress
            }
                break;
        }
    }

    /*
    * getExamEvaluation
    */
     public function getExamEvaluation ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();
        $insert_question_palette=array();

        $student_id = validateObject ($postData , 'student_id', "");
        $student_id = addslashes($student_id);

        $exam_id = validateObject ($postData , 'exam_id', "");
        $exam_id = addslashes($exam_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

//        $query="SELECT * FROM `student_exam_score` WHERE `user_id`=".$student_id." and exam_id=".$exam_id;
//        $result=mysqli_query($GLOBALS['con'],$query) or  $message=mysqli_error($GLOBALS['con']);
            //echo $query;
            // echo $insertValues;


            $queryExam = "SELECT * FROM ".TABLE_EXAMS." WHERE id=".$exam_id ." AND is_delete=0";
            $resultExam = mysqli_query($GLOBALS['con'], $queryExam) or $message = mysqli_error($GLOBALS['con']);
            //echo $queryExam;
            if (mysqli_num_rows($resultExam)) {

                $rowExam = mysqli_fetch_assoc($resultExam);
                $post['exam_id'] = $rowExam['id'];
                $post['exam_score'] = $rowExam['marks_obtained'];

                //=========================Add Question Paallete========================
                if ($rowExam['exam_mode'] == "subjective") {
                    $table = TABLE_STUDENT_SUBJECTIVE_EVALUATION;
                } else {
                    $table = TABLE_STUDENT_OBJECTIVE_RESPONSE;
                }
                //user_id=".$student_id." AND
                $queryGetQuestionPalette = "SELECT question_id,answer_status FROM " . $table . " WHERE exam_id=" . $exam_id . " ORDER BY question_id ASC";
                $resultGetQuestionPalette = mysqli_query($GLOBALS['con'], $queryGetQuestionPalette) or $message = mysqli_error($GLOBALS['con']);

                $question_palette_array = array();
                if (mysqli_num_rows($resultGetQuestionPalette)) {

                    while ($rowQuestion = mysqli_fetch_assoc($resultGetQuestionPalette)) {
                        //$insert_question_palette[] = "{'" . $rowQuestion['question_id'] . "','" . $rowQuestion['answer_status'] . "'}";
                        $insert_question_palette['question_id']= $rowQuestion['question_id'];
                        $insert_question_palette['value']= $rowQuestion['answer_status'];
                        $post['question_palette'][] = $insert_question_palette;
                    }
                }
                //$post['question_palette'] = $insert_question_palette;
                //=========================Finish ti Add Question Paallete =================
                if ($rowExam['exam_mode'] == "subjective") {
//                {
//                    "exam_id":63,
//                    "student_id":370
//                }
                    $query = "SELECT * FROM " . TABLE_EXAM_SCHEDULE . " exam_schedule INNER JOIN " . TABLE_USERS . " users on exam_schedule.exam_assessor=users.id WHERE exam_id=" . $exam_id." AND exam_schedule.is_delete=0 AND users.is_delete=0";
                    $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                    //echo $query;
                    $row = mysqli_fetch_assoc($result);

                    $post['evaluator_id'] = $row['exam_assessor'];
                    $post['evaluator_name'] = $row['full_name'];
                    $post['evaluator_profile_pic'] = $row['profile_pic'];

                    $evaluation = array();
                    $queryEvaluation = "SELECT * FROM " . TABLE_EXAM_EVALUATION . " WHERE exam_id=" . $exam_id ." AND is_delete=0";
                    $resultEvaluation = mysqli_query($GLOBALS['con'], $queryEvaluation) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultEvaluation)) {
                        $rowEvaluation = mysqli_fetch_assoc($resultEvaluation);
                        if ($rowEvaluation['evaluation_status'] == "unassesed") {
                            $evaluation['evaluation_score'] = $rowEvaluation['evaluation_status'];
                        } else {
                            //user_id=".$student_id."
                            $queryStudentRes = "SELECT student_subjective_evaluation.*,questions.question_score FROM " . TABLE_STUDENT_SUBJECTIVE_EVALUATION . " JOIN questions on questions.id=student_subjective_evaluation.question_id WHERE student_subjective_evaluation.is_delete=0 and student_subjective_evaluation.`exam_id`=" . $exam_id . " and student_subjective_evaluation.`user_id`=" . $student_id . " and evaluation_by=".$row['exam_assessor']." and student_subjective_evaluation.question_id in (SELECT `question_id` FROM `exam_question` WHERE `exam_id`=" . $exam_id . ")";
                            $resultStudentRes = mysqli_query($GLOBALS['con'], $queryStudentRes) or $message = mysqli_error($GLOBALS['con']);
                            //echo $queryStudentRes;
                            // echo "\n".mysqli_num_rows($resultStudentRes);
                            $evaluations = array();
                            if (mysqli_num_rows($resultStudentRes)) {
                                while ($rowEvaluation = mysqli_fetch_assoc($resultStudentRes)) {
                                    $evaluation['question_id'] = $rowEvaluation['question_id'];
                                    $evaluation['student_response'] = $rowEvaluation['student_response'];
                                    $evaluation['evaluation_score'] = $rowEvaluation['evaluation_score'];
                                    $evaluation['evaluation_notes'] = $rowEvaluation['evaluation_notes'];
                                    $evaluation['question_score'] = $rowEvaluation['question_score'];
                                    $evaluations[] = $evaluation;
                                }
                            }
                            $post['evaluation'] = $evaluations;
                        }

                    }

                } else if ($rowExam['exam_mode'] == "objective") {
//                {
//                    "exam_id":9,
//                    "student_id":202
//                }
                    $queryStudentRes = "SELECT student_objective_response.*,questions.question_score FROM " . TABLE_STUDENT_OBJECTIVE_RESPONSE . " JOIN questions on questions.id=student_objective_response.question_id WHERE student_objective_response.is_delete=0 and student_objective_response.`user_id`=" . $student_id . " and student_objective_response.`exam_id`=" . $exam_id . " and student_objective_response.question_id in (SELECT `question_id` FROM `exam_question` WHERE `exam_id`=" . $exam_id . ")";
                    $resultStudentRes = mysqli_query($GLOBALS['con'], $queryStudentRes) or $message = mysqli_error($GLOBALS['con']);
                    //echo $queryStudentRes;
                    //echo "\n".mysqli_num_rows($resultStudentRes);
                    $evaluations = array();
                    if (mysqli_num_rows($resultStudentRes)) {
                        while ($rowEvaluation = mysqli_fetch_assoc($resultStudentRes)) {
                            $evaluation['question_id'] = $rowEvaluation['question_id'];
                            $evaluation['student_response'] = $rowEvaluation['choice_id'];
                            $evaluation['evaluation_score'] = $rowEvaluation['marks_obtained'];
                            $evaluation['is_right'] = $rowEvaluation['is_right'];
                            $evaluation['answer_status'] = $rowEvaluation['answer_status'];
                            $evaluation['question_score'] = $rowEvaluation['question_score'];
                            $evaluations[] = $evaluation;
                        }
                        $post['evaluation'] = $evaluations;
                    }
                }

                $data[] = $post;
                $message = "";
                $status = SUCCESS;
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

        $response['exam_evaluation']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }
    /*
     * getExamSubmission
     */
    public function getExamSubmission ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();
        $evaluations=array();
        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $exam_id = validateObject ($postData , 'exam_id', "");
        $exam_id = addslashes($exam_id);

        $role = validateObject ($postData , 'role', "");
        $role = addslashes($role);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $post['exam_id'] = $exam_id;
            $query = $query="SELECT * FROM ".TABLE_STUDENT_EXAM_SCORE." WHERE `exam_id`=".$exam_id ." and is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            //echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $evaluation = array();
                    $student_id = $row['user_id'];

                    $queryStudentDetails = $queryStudentDetails="SELECT user.id,user.full_name,user.profile_pic,student_profile.roll_no FROM ".TABLE_USERS." user JOIN .".TABLE_STUDENT_PROFILE." student_profile
                ON student_profile.user_id=user.id WHERE user.id=".$student_id ." and user.is_delete=0 and student_profile.is_delete=0";
                    $resultStudentDetails = mysqli_query($GLOBALS['con'], $queryStudentDetails) or $message = mysqli_error($GLOBALS['con']);
                    // echo $queryStudentDetails;
                    if (mysqli_num_rows($resultStudentDetails)) {
                        $rowDetails = mysqli_fetch_assoc($resultStudentDetails);
                        $evaluation['student_id'] = $rowDetails['id'];
                        $evaluation['student_name'] = $rowDetails['full_name'];
                        $evaluation['student_profile_pic'] = $rowDetails['profile_pic'];
                        if($role==2)
                        {
                            $evaluation['student_roll_no'] = $rowDetails['student_roll_no'];
                        }

                    }
                    $evaluation['evaluation_score'] = $row['marks_obtained'];
                    $evaluation['exam_status'] = $row['exam_status'];
                    $evaluation['submission_date'] = $row['exam_endtime'];
                    $evaluation['remarks'] = $row['remarks'];
                    $evaluations[] = $evaluation;
//                $queryStudentDetails="SELECT studentExamScore.user_id,users.full_name,users.profile_pic,studentExamScore.marks_obtained,studentExamScore.remarks FROM ".TABLE_STUDENT_EXAM_SCORE." studentExamScore INNER JOIN ".TABLE_USERS." users on studentExamScore.user_id=users.id WHERE studentExamScore.exam_id=".$exam_id." and studentExamScore.user_id=".$student_id;
//                $resultStudentDetails=mysqli_query($GLOBALS['con'],$queryStudentDetails) or  $message=mysqli_error($GLOBALS['con']);
//                echo $queryStudentDetails;
//                if(mysqli_num_rows($resultStudentDetails))
//                {
//                    $rowDetails=mysqli_fetch_assoc($resultStudentDetails);
//                    $evaluation[] = $rowDetails;
//                    $post[]=$evaluation;
//                }
                }
                $post['examsubmittor'] = $evaluations;
                $data[] = $post;
                //$message="";
                $status = SUCCESS;
            } else {

                $status = SUCCESS;
                $message=DEFAULT_NO_RECORDS;
            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['exam_submission']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * create exam
     */
    public function createExam ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();

        $exam_id = validateObject ($postData , 'exam_id', "");
        $exam_id = addslashes($exam_id);

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $exam_assessor = validateObject ($postData , 'exam_assessor', "");
        $exam_assessor = addslashes($exam_assessor);

        $exam_name = validateObject ($postData , 'exam_name', "");
        $exam_name = addslashes($exam_name);

//        $course_id = validateObject ($postData , 'course_id', "");
//        $course_id = addslashes($course_id);

        $classroom_id = validateObject ($postData , 'classroom_id', "");
        $classroom_id = addslashes($classroom_id);

        $passing_percent = validateObject ($postData , 'passing_percent', "");
        $passing_percent = addslashes($passing_percent);

        $subject_id = validateObject ($postData , 'subject_id', "");
        $subject_id = addslashes($subject_id);

        $topic_id = validateObject ($postData , 'topic_id', "");
        $topic_id = addslashes($topic_id);

        $exam_mode = validateObject ($postData , 'exam_mode', "");
        $exam_mode = addslashes($exam_mode);

        $book_id = validateObject ($postData , 'book_id', "");
        $book_id = addslashes($book_id);

        $exam_type = validateObject ($postData , 'exam_type', "");
        $exam_type = addslashes($exam_type);

        $exam_category = validateObject ($postData , 'exam_category', "");
        $exam_category = addslashes($exam_category);

        $exam_duration = validateObject ($postData , 'exam_duration', "");
        $exam_duration= addslashes($exam_duration);

        $exam_start_date = validateObject ($postData , 'exam_start_date', "");
        $exam_start_date = addslashes($exam_start_date);

        $exam_start_time = validateObject ($postData , 'exam_start_time', "");
        $exam_start_time = addslashes($exam_start_time);

        $exam_instruction = validateObject ($postData , 'exam_instruction', "");
        $exam_instruction = addslashes($exam_instruction);

        $declare_results = validateObject ($postData , 'declare_results', "");
        $declare_results = addslashes($declare_results);

        $attempt_count = validateObject ($postData , 'attempt_count', "");
        $attempt_count = addslashes($attempt_count);

        $negative_marking = validateObject ($postData , 'negative_marking', "");
        $negative_marking = addslashes($negative_marking);

        $negative_mark_value = validateObject ($postData , 'negative_mark_value', "");
        $negative_mark_value = addslashes($negative_mark_value);

        $random_question = validateObject ($postData , 'random_question', "");
        $random_question = addslashes($random_question);

        $use_question_score = validateObject ($postData , 'use_question_score', "");
        $use_question_score = addslashes($use_question_score);

        $correct_answer_score = validateObject ($postData , 'correct_answer_score', "");
        $correct_answer_score = addslashes($correct_answer_score);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($exam_id == 0) {

                $insertFields = "`created_by`,`exam_name`, `book_id`,`classroom_id`, `subject_id`, `topic_id`, `exam_type`, `exam_category`, `exam_mode`, `pass_percentage`, `duration`, `instructions`, `negative_marking`,`negative_mark_value`,`use_question_score`,`correct_answer_score`, `random_question`, `declare_results`,`attempt_count`";
                $insertValues = "".$user_id . ",'" . $exam_name . "'," . $book_id . "," . $classroom_id . "," . $subject_id . "," . $topic_id . ",'" . $exam_type . "','" . $exam_category . "','" . $exam_mode . "'," . $passing_percent . "," . $exam_duration . ",'" . $exam_instruction . "','" . $negative_marking . "'," . $negative_mark_value . ",'".$use_question_score."',".$correct_answer_score.",'" . $random_question . "','" . $declare_results . "'," . $attempt_count;

                $query = "INSERT INTO " . TABLE_EXAMS . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                echo $query; exit;
                $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                if ($result) {
                    $post['exam_id'] = mysqli_insert_id($GLOBALS['con']);
                    if ($exam_start_date != null and $exam_start_time != null) {
                        $insertExamScheduleFields = "`exam_id`, `schedule_by`, `exam_assessor`, `start_date`, `start_time`, `school_classroom_id`";
                        $insertExamScheduleValues = "" . $post['exam_id'] . "," . $user_id . "," . $exam_assessor . ",'" . $exam_start_date . "','" . $exam_start_time . "'," . $classroom_id;
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
                        $post['exam_id'] = mysqli_insert_id($GLOBALS['con']);
                        $status = SUCCESS;
                        $message="Exam created";
                    }

                } else {
                    $post['exam_id'] = "";
                    $status = FAILED;
                     $message="Exam is not created and scheduled";
                }
            }
            else
            {

                $queryToChkIfExist="SELECT * FROM ".TABLE_EXAMS." WHERE id=".$exam_id." AND is_delete=0";
                $resultToChkIfExist = mysqli_query($GLOBALS['con'], $queryToChkIfExist) or $message = mysqli_error($GLOBALS['con']);

                if(mysqli_num_rows($resultToChkIfExist)>0) {
                    $updateFields = "`created_by`=" . $user_id . " ,`exam_name`='" . $exam_name . "', `book_id`=" . $book_id . ",`classroom_id`=" . $classroom_id . ", `subject_id`=" . $subject_id . ", `topic_id`=" . $topic_id . ", `exam_type`='" . $exam_type . "', `exam_category`='" . $exam_category . "', `exam_mode`='" . $exam_mode . "', `pass_percentage`=" . $passing_percent . ", `duration`=" . $exam_duration . ", `instructions`='" . $exam_instruction . "', `negative_marking`='" . $negative_marking . "',`negative_mark_value`=" . $negative_mark_value . ", `use_question_score`='".$use_question_score."',`correct_answer_score`=".$correct_answer_score.", `random_question`='" . $random_question . "', `declare_results`='" . $declare_results . "',`attempt_count`=" . $attempt_count;
                    //echo $updateFields; exit;
                    $queryUpdate = "UPDATE " . TABLE_EXAMS . " SET " . $updateFields . " WHERE id=" . $exam_id;
                    $resultUpdate = mysqli_query($GLOBALS['con'], $queryUpdate) or $message = mysqli_error($GLOBALS['con']);

                    if ($resultUpdate) {

                        if ($exam_start_date != null and $exam_start_time != null) {

                                $queryToChkIfExist="SELECT * FROM ".TABLE_EXAM_SCHEDULE." WHERE exam_id=".$exam_id." AND is_delete=0";
                                $resultToChkIfExist = mysqli_query($GLOBALS['con'], $queryToChkIfExist) or $message = mysqli_error($GLOBALS['con']);
                                if(mysqli_num_rows($resultToChkIfExist)>0) {

                                $updateExamScheduleFields = " `schedule_by`=".$user_id.", `exam_assessor`=".$exam_assessor.", `start_date`='".$exam_start_date."', `start_time`='".$exam_start_time."', `school_classroom_id`=".$classroom_id;
                                $queryUpdateExamSchedule = "UPDATE ". TABLE_EXAM_SCHEDULE." SET ".$updateExamScheduleFields." WHERE exam_id= ".$exam_id;
                                $resultExamSchedule = mysqli_query($GLOBALS['con'], $queryUpdateExamSchedule) or $message = mysqli_error($GLOBALS['con']);
                                //  echo $queryInsertExamSchedule;
                                if ($resultExamSchedule) {
                                    $post['exam_id'] = $exam_id;
                                    $status = SUCCESS;
                                    $message = "Exam updated and scheduled";
                                } else {
                                    $status = SUCCESS;
                                    $message = "Exam is updated but not scheduled";
                                }
                            }
                            else{
                                $status = SUCCESS;
                                $message = DEFAULT_NO_RECORDS;
                            }
                        }
                        else{
                            $post['exam_id'] = $exam_id;
                            $status = SUCCESS;
                            $message = "Exam updated";

                        }
                    } else {
                        $post['exam_id'] = $exam_id;
                        $status = FAILED;
                        $message = "Exam is not updated and scheduled";
                    }
                }
                else{
                    $status = SUCCESS;
                    $message = DEFAULT_NO_RECORDS;
                }

            }

            $data[] = $post;

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['exam']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * setQuestionsForExam
     */
    public function setQuestionsForExam ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $exam_id = validateObject ($postData , 'exam_id', "");
        $exam_id = addslashes($exam_id);

        $question_id_list = validateObject ($postData , 'question_id_list', "");
        //$question_id_list = addslashes($question_id_list);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($question_id_list != null) {

                foreach ($question_id_list as $ques_id) {

                    $queryCheckFeed = "SELECT * FROM " . TABLE_EXAM_QUESTION . " WHERE question_id =" . $ques_id . " and exam_id=" . $exam_id . " and is_delete=0";
                    //echo $queryCheckFeed."\n";
                    $resultCheckFeed = mysqli_query($GLOBALS['con'],$queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultCheckFeed) == 0) {

                        $insertFields = "`exam_id`, `question_id`";
                        $insertValues = $exam_id . "," . $ques_id;

                        $query = "INSERT INTO " . TABLE_EXAM_QUESTION . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                        // echo $query;
                        if ($result) {
                            $status = SUCCESS;
                            $message = "";
                        } else {
                            $status = FAILED;
                            // $message="";
                        }
                    } else {
                        $status = SUCCESS;
                        $message = RECORD_ALREADY_EXIST;
                    }
                }
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
        $response['question']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
    * getQuestionBank
    */
    public function getQuestionBank ($postData)
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($role == 3) {
                $getField = "subject_id";
                $rowParameter = 'subject_id';
                $table = TABLE_TEACHER_SUBJECT_INFO;
            } else if ($role == 4) {
                $getField = "book_id";
                $rowParameter = 'book_id';
                $table = TABLE_AUTHOR_BOOK;
            }

            $querySubjectId = "SELECT ".$getField." FROM ".$table." WHERE `user_id`=".$user_id ." and is_delete=0";
            $resultSubjectId = mysqli_query($GLOBALS['con'], $querySubjectId) or $message = mysqli_error($GLOBALS['con']);
           // echo $querySubjectId; exit;
            if (mysqli_num_rows($resultSubjectId)) {
                while ($row = mysqli_fetch_assoc($resultSubjectId)) {
                    //$post['subject_id']=$row;
                    //$data[]=$row;
                    if ($role == 3) {
                        $queryQuestion = "SELECT question.*,users.full_name,subject.subject_name FROM " . TABLE_QUESTIONS . " question
                Inner join " . TABLE_USERS . " users on question.question_creator_id=users.id
                inner join " . TABLE_SUBJECTS . " subject on question.subject_id=subject.id
                WHERE " . $getField . "=" . $row[$rowParameter] . " AND question.is_delete=0 AND users.is_delete=0 AND subject.is_delete=0";

                    }
                    elseif($role == 4) {
                        $queryQuestion = "SELECT question.*,users.full_name,book.book_name FROM " . TABLE_QUESTIONS . " question
                Inner join " . TABLE_USERS . " users on question.question_creator_id=users.id
                Inner join " . TABLE_BOOKS . " book on question.book_id=book.id
                WHERE " . $getField . "=" . $row[$rowParameter] . " AND question.is_delete=0 AND users.is_delete=0 AND book.is_delete=0";
                    }
                    $resultQuestion = mysqli_query($GLOBALS['con'], $queryQuestion) or $message = mysqli_error($GLOBALS['con']);
                   // echo $queryQuestion; exit;
                    if (mysqli_num_rows($resultQuestion)) {
                        while ($rowQuestion = mysqli_fetch_assoc($resultQuestion)) {
                            $post['question_id'] = $rowQuestion['id'];
                            // $post['question_title']=$rowQuestion['id'];
                            $post['question_creator_id'] = $rowQuestion['question_creator_id'];
                            $post['question_creator_name'] = $rowQuestion['full_name'];
                            $post['question_format'] = $rowQuestion['question_format'];
                            $post['question_hint'] = $rowQuestion['question_hint'];
                            $post['question_text'] = $rowQuestion['question_text'];
                            $post['question_score'] = $rowQuestion['question_score'];
                            $post['question_assets_link'] = $rowQuestion['assets_link'];
                            $post['question_image_link'] = $rowQuestion['question_image_link'];
                            $post['evaluation_notes'] = $rowQuestion['evaluation_notes'];
                            $post['solution'] = $rowQuestion['solution'];
                            $post['topic_id'] = $rowQuestion['topic_id'];

                            $post['classroom_id'] = $rowQuestion['classroom_id'];

                            if($role==3)
                            {
                                $post['subject_id'] = $rowQuestion['subject_id'];
                                $post['subject_name'] = $rowQuestion['subject_name'];
                            }
                            if($role==4)
                            {
                                $post['book_id'] = $rowQuestion['book_id'];
                                $post['book_name'] = $rowQuestion['book_name'];
                            }






                            $tags = array();
                            $tagQuery = "SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_QUESTION." tag_question JOIN ".TABLE_TAGS." tags ON tags.id=tag_question.tag_id WHERE tag_question.question_id=".$rowQuestion['id'] ." and tags.is_delete=0 and tag_question.is_delete=0";
                            $tagResult = mysqli_query($GLOBALS['con'], $tagQuery) or $message = mysqli_error($GLOBALS['con']);
                            if (mysqli_num_rows($tagResult)) {

                                while ($rowGetTags = mysqli_fetch_assoc($tagResult)) {
                                    $tags[] = $rowGetTags;

                                }
                                $post['tags'] = $tags;
                            } else {
                                $post['tags'] = $tags;
                            }


                            $choice = array();
                            if ($rowQuestion['question_format'] == 'MCQ') {
                                $queryGetChoice = "SELECT `id`, `question_id`, `choice_text`, `is_right`, `image_link`, `audio_link`, `video_link` FROM ".TABLE_ANSWER_CHOICES." WHERE `question_id`=".$rowQuestion['id'] ." and is_delete=0";
                                $resultGetChoice = mysqli_query($GLOBALS['con'], $queryGetChoice) or $message = mysqli_error($GLOBALS['con']);
                                // echo $resultGetChoice;
                                if (mysqli_num_rows($resultGetChoice)) {
                                    while ($rowGetChoice = mysqli_fetch_assoc($resultGetChoice)) {
                                        $choice[] = $rowGetChoice;

                                    }
                                    $post['answers'] = $choice;
                                }
                            } else {
                                $post['answers'] = $choice;
                            }

                            $data[] = $post;
                        }
                        $status = SUCCESS;
                        $message = "";
                    } /*else {
                        $status = FAILED;
                        $message = DEFAULT_NO_RECORDS."jjj";
                    }*/
                }

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $response['question_bank']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
   * getCourses
   */
    public function getCourses ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `course_name` FROM ".TABLE_COURSES ." WHERE is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    //$post['subject_id']=$row;
                    $data[] = $row;
                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['courses']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * getCourses
    */
    public function getSubject ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `subject_name`, `subject_image` FROM ".TABLE_SUBJECTS ." WHERE is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    //$post['subject_id']=$row;
                    $data[] = $row;

                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['subjects']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * getTopic
    */
    public function getTopics ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $subject_id = validateObject ($postData , 'subject_id', "");
        $subject_id = addslashes($subject_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `topic_name`, `topic_description` FROM ".TABLE_TOPICS ." where subject_id=".$subject_id ." and is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    //$post['subject_id']=$row;
                    $data[] = $row;

                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['topics']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * getClassrooms
    */
    public function getClassrooms ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `course_id`, `class_name`, `class_nickname` FROM ".TABLE_CLASSROOMS ." WHERE is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    //$post['subject_id']=$row;
                    $data[] = $row;
                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['classrooms']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * getAllExams
    */
    public function getAllExams ($postData)
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

        $exam_category = validateObject ($postData , 'exam_category', "");
        $exam_category = addslashes($exam_category);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $exam_id[] = null;
            if ($role == 3) {
                $getField = "subject_id";
                $rowParameter = 'subject_id';
                $table = TABLE_TEACHER_SUBJECT_INFO;

            } else if ($role == 4) {
                $getField = "book_id";
                $rowParameter = 'book_id';
                $table = TABLE_AUTHOR_BOOK;
            }


            if($exam_category!="")
            {
                $condition=" AND exam_category='".$exam_category."' ";
            }


            $queryExam = "SELECT * FROM ".TABLE_EXAMS." WHERE is_delete=0 ".$condition." AND ".$getField." in (SELECT ".$getField." FROM ".$table." WHERE `user_id`=".$user_id.")";
            $resultExam = mysqli_query($GLOBALS['con'], $queryExam) or $message = mysqli_error($GLOBALS['con']);
            //echo $queryExam; exit;
            if (mysqli_num_rows($resultExam) > 0) {
                while ($rowExam = mysqli_fetch_assoc($resultExam)) {
                    $exam_id[] = $rowExam['id'];
                    $data[] = $this->getExamData($rowExam);
                }
            }
            $queryExam = "SELECT * FROM ".TABLE_EXAMS." WHERE is_delete=0 ".$condition."  AND id in (select exam_id from ".TABLE_EXAM_SCHEDULE." where exam_assessor=".$user_id.")";
            $resultExam = mysqli_query($GLOBALS['con'], $queryExam) or $message = mysqli_error($GLOBALS['con']);
           // echo $queryExam."\n".mysqli_num_rows($resultExam);
            if (mysqli_num_rows($resultExam)) {
                while ($rowExam = mysqli_fetch_assoc($resultExam)) {
                    $flag = false;
                    foreach ($exam_id as $id) {
                        if ($id == $rowExam['id']) {
                            $flag = true;
                        }
                    }
                    if ($flag == false) {
                        $data[] = $this->getExamData($rowExam);
                    }

                }
            }

            $status = SUCCESS;
            $message = "";
            if ($data == null) {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['exams']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    public function getExamData($rowExam)
    {
        $post = array();
        $post['exam_id'] = $rowExam['id'];
        // $post['question_title']=$rowExam['id'];
        $post['exam_name'] = $rowExam['exam_name'];
        $post['classroom_id'] = $rowExam['classroom_id'];

        $query = "SELECT class_name FROM " . TABLE_CLASSROOMS . " WHERE id=" . $rowExam['classroom_id'] . " AND is_delete=0 ";
//                        echo $query;
        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
        if (mysqli_num_rows($result)) {
            while ($rowName = mysqli_fetch_assoc($result)) {
                $post['classroom_name'] = $rowName['class_name'];
            }
        }
        // total student
        //    echo $query;
        $query = "SELECT * FROM " . TABLE_STUDENT_PROFILE . " WHERE classroom_id=" . $rowExam['classroom_id'] . " AND is_delete=0 ";
        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

        // $post['total_student']=0;// need to change
        $post['total_student'] = mysqli_num_rows($result);



            $post['subject_id'] = $rowExam['subject_id'];
            //subject name
            $query = "SELECT subject_name FROM " . TABLE_SUBJECTS . " WHERE id=" . $rowExam['subject_id'] . " AND is_delete=0 ";
//                        echo $query;
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($result)) {
                while ($rowName = mysqli_fetch_assoc($result)) {
                    $post['subject_name'] = $rowName['subject_name'];
                }
            }


            $post['book_id'] = $rowExam['book_id'];
            //book name
            $query = "SELECT book_name FROM " . TABLE_BOOKS . " WHERE id=" . $rowExam['book_id'] . " AND is_delete=0 ";
//                        echo $query;
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($result)) {
                while ($rowName = mysqli_fetch_assoc($result)) {

                    $post['book_name'] = $rowName['book_name'];
                }
            }

        //get Topic Ids

        $post['topic_id'] = $rowExam['topic_id'];

//        $query = "SELECT topic_name FROM " . TABLE_TOPICS . " WHERE id=" . $rowExam['topic_id'] . " AND is_delete=0 ";
//////                        echo $query;
//        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
//        if (mysqli_num_rows($result)) {
//            while ($rowName = mysqli_fetch_assoc($result)) {
//
//                $post['topic_name'] = $rowName['topic_name'];
//            }
//       }

        $post['exam_type']=$rowExam['exam_type'];
        $post['exam_category']=$rowExam['exam_category'];
        $post['exam_mode']=$rowExam['exam_mode'];
        $post['pass_percentage']=$rowExam['pass_percentage'];
        $post['duration']=$rowExam['duration'];


        //total questions
        $query="SELECT `evaluation_status`, `total_questions`, `average_score`, `total_student_attempted`, `total_assessed` FROM `exam_evaluation` WHERE `exam_id`=".$rowExam['id'] ." AND is_delete=0 ";
        $result=mysqli_query($GLOBALS['con'],$query) or  $message=mysqli_error($GLOBALS['con']);
       // echo $query;
        if(mysqli_num_rows($result))
        {
            while($rowName=mysqli_fetch_assoc($result)) {

                if($rowName['total_question'] == NULL)
                {
                    $post['total_question']=0;
                }
                else {
                    $post['total_question'] = $rowName['total_question'];
                }
                $post['evaluation_status']=$rowName['evaluation_status'];
                $post['average_score']=$rowName['average_score'];
                $post['total_assessed']=$rowName['total_assessed'];
                $post['total_unassessed']= $rowName['total_student_attempted'] - $rowName['total_assessed'];
            }
        }
        else{
            $post['total_question']="";
            $post['evaluation_status']="";
            $post['average_score']="";
            $post['total_assessed']="";
            $post['total_unassessed']= "";
        }


        $post['exam_created_date'] = $rowExam['created_date'];

        return $post;
    }

    /*
    * getExamQuestions
   */
    public function getExamQuestions ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $examData=array();
        $post=array();
        $questions=array();
        $response=array();

        $exam_id = validateObject ($postData , 'exam_id', "");
        $exam_id = addslashes($exam_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT exams.*,classrooms.class_name,books.book_name FROM ".TABLE_EXAMS." exams
        LEFT JOIN ".TABLE_CLASSROOMS." classrooms ON classrooms.id=exams.classroom_id
        LEFT JOIN ".TABLE_BOOKS." books ON books.id=exams.book_id
        where exams.id=".$exam_id  ." and exams.is_delete=0 ";

            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                $row = mysqli_fetch_assoc($result);

                $examData['id'] = $row['id'];
                $examData['exam_name'] = $row['exam_name'];
                $examData['instruction'] = $row['instruction'];
                $examData['book_name'] = $row['book_name'];
                $examData['class_name'] = $row['class_name'];
                $examData['created_date'] = $row['created_date'];

                $queryExamQuestion = "SELECT * FROM ".TABLE_EXAM_QUESTION." where exam_id=".$exam_id ." and is_delete=0 ";
                $resultExamQuestion = mysqli_query($GLOBALS['con'], $queryExamQuestion) or $message = mysqli_error($GLOBALS['con']);

                if (mysqli_num_rows($resultExamQuestion)) {
                    while ($row = mysqli_fetch_assoc($resultExamQuestion)) {
                        //$post['subject_id']=$row;
                        //$data[]=$row;

                        //left join ".TABLE_SUBJECTS." subject on question.subject_id=subject.id  ,subject.subject_name
                        $queryQuestion = "SELECT DISTINCT question.*,users.full_name,subject.subject_name FROM ".TABLE_QUESTIONS." question
                inner join ".TABLE_USERS." users on users.id=question.question_creator_id
                left join ".TABLE_SUBJECTS." subject on subject.id=question.subject_id
                WHERE question.id=".$row['question_id'] ." AND question.is_delete=0 ";
                        $resultQuestion = mysqli_query($GLOBALS['con'], $queryQuestion) or $message = mysqli_error($GLOBALS['con']);
                        //echo $queryQuestion ; exit;
                        if (mysqli_num_rows($resultQuestion)) {
                            while ($rowQuestion = mysqli_fetch_assoc($resultQuestion)) {
                                $questions['question_id']=$rowQuestion['id'];
                                // $post['question_title']=$rowQuestion['id'];
                                $questions['question_creator_id']=$rowQuestion['question_creator_id'];
                                $questions['question_creator_name']=$rowQuestion['full_name'];
                                $questions['question_format']=$rowQuestion['question_format'];
                                $questions['question_hint']=$rowQuestion['question_hint'];
                                $questions['question_text']=$rowQuestion['question_text'];
                                $questions['question_assets_link']=$rowQuestion['assets_link'];
                                $questions['question_image_link']=$rowQuestion['question_image_link'];
                                $questions['question_score']=$rowQuestion['question_score'];
                                $questions['evaluation_notes']=$rowQuestion['evaluation_notes'];
                                $questions['solution']=$rowQuestion['solution'];
                                $questions['topic_id']=$rowQuestion['topic_id'];
                                $questions['subject_id']=$rowQuestion['subject_id'];
                                $questions['subject_name']=$rowQuestion['subject_name'];
                                $questions['classroom_id']=$rowQuestion['classroom_id'];
                                $questions['book_id']=$rowQuestion['book_id'];




                                $choice = array();
                                if ($rowQuestion['question_format'] == 'MCQ') {
                                   $queryGetChoice = "SELECT `id`, `question_id`, `choice_text`, `is_right`, `image_link`, `audio_link`, `video_link` FROM ".TABLE_ANSWER_CHOICES." WHERE `question_id`=".$rowQuestion['id'] ." AND is_delete=0 ";
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

                                $post[] = $questions;



                                $tags=array();
                                $tagQuery="SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_QUESTION." tag_question JOIN ".TABLE_TAGS." tags ON tags.id=tag_question.tag_id WHERE tag_question.question_id=".$rowQuestion['id'] ." and tags.is_delete=0 and tag_question.is_delete=0";
                                $tagResult=mysqli_query($GLOBALS['con'],$tagQuery) or  $message=mysqli_error($GLOBALS['con']);
                                if(mysqli_num_rows($tagResult))
                                {

                                    while($rowGetTags=mysqli_fetch_assoc($tagResult)) {
                                        $tags[]=$rowGetTags;

                                    }
                                    $questions['tags']=$tags;
                                }
                                else{
                                    $questions['tags']=$tags;
                                }
                            }
                            $status = SUCCESS;
                            $message = "";
                        } else {
                            $status = SUCCESS;
                            $message = DEFAULT_NO_RECORDS;
                        }
                    }

                } else {
                    $status = SUCCESS;
                    $message = DEFAULT_NO_RECORDS;
                }
            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $examData['questions'] = $post;
        $data[] = $examData;
        $response['exam_questions']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }
    
    
    /*
	*createQuestion
	*/
	public function createQuestion ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $question_text = validateObject ($postData , 'question_text', "");
        $question_text = addslashes($question_text);

        $subject_id = validateObject ($postData , 'subject_id', "");
        $subject_id = addslashes($subject_id);

        $question_score = validateObject ($postData , 'question_score', "");
        $question_score = addslashes($question_score);

        $question_format = validateObject ($postData , 'question_format', "");
        $question_format = addslashes($question_format);

       /* $question_image = validateObject ($postData , 'question_image', "");
        $question_image = addslashes($question_image);

        $question_video = validateObject ($postData , 'question_video', "");
        $question_video = addslashes($question_video);*/

        $evaluation_notes = validateObject ($postData , 'evaluation_notes', "");
        $evaluation_notes = addslashes($evaluation_notes);

        $solution = validateObject ($postData , 'solution', "");
        $solution = addslashes($solution);

        $topic_id = validateObject ($postData , 'topic_id', "");
        $topic_id= addslashes($topic_id);

        $classroom_id = validateObject ($postData , 'classroom_id', "");
        $classroom_id = addslashes($classroom_id);

        $book_id = validateObject ($postData , 'book_id', "");
        $book_id = addslashes($book_id);

        $question_id = validateObject($postData, 'question_id', "");
        $question_id = addslashes($question_id);

        $answer_choices = validateObject ($postData , 'answer_choices', "");

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {


            //Check for question_format
            if ($question_format == "MCQ" || $question_format == "descriptive" || $question_format == "Fill ups") {
                if ($question_id != 0) {

                    $queryToChkRecordExist = "SELECT * FROM " . TABLE_QUESTIONS . " WHERE id=" . $question_id . " AND is_delete=0";
                    $resultToChkRecordExist = mysqli_query($GLOBALS['con'],$queryToChkRecordExist) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultToChkRecordExist) > 0) {
                        $updateFields = "`question_text` ='" . $question_text . "',`question_score`='" . $question_score . "', `question_format`='" . $question_format . "', `question_creator_id`=" . $user_id . ", `evaluation_notes`='" . $evaluation_notes . "', `solution`='" . $solution . "', `topic_id`=" . $topic_id . ", `subject_id`=" . $subject_id . ", `classroom_id`=" . $classroom_id . ",`book_id`=" . $book_id;

                        $query = "UPDATE " . TABLE_QUESTIONS . " SET " . $updateFields . " WHERE id=" . $question_id;
                        $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);

                        if ($result) {
                            $status = SUCCESS;
                            $message = SUCCESSFULLY_UPDATED;
                            $post['question_id'] = $question_id;
                        } else {
                            $status = FAILED;
                            $message = "";
                            $post['question_id'] = "";
                        }
                    }
                } else {

                    $insertFields = "`question_text`,`question_score`, `question_format`, `question_creator_id`, `evaluation_notes`, `solution`, `topic_id`, `subject_id`, `classroom_id`,`book_id`";
                    $insertValues = "'" . $question_text . "'," . $question_score . ",'" . $question_format . "'," . $user_id . ",'" . $evaluation_notes . "','" . $solution . "'," . $topic_id . "," . $subject_id . "," . $classroom_id . "," . $book_id;

                    $query = "INSERT INTO " . TABLE_QUESTIONS . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                    $result = mysqli_query($GLOBALS['con'],$query) or $message = mysqli_error($GLOBALS['con']);

                    $questionID = mysqli_insert_id($GLOBALS['con']);


                    if ($result) {
                        $status = SUCCESS;
                        $message = SUCCESSFULLY_ADDED;
                        $post['question_id'] = $questionID;
                    } else {
                        $status = FAILED;
                        $message = "";
                        $post['question_id'] = "";
                    }
                }

                if ($result) {
                    if ($question_format == "MCQ") {
                        if (is_array($answer_choices)) {

                            foreach ($answer_choices as $row) {

                                if ($question_id != 0) {

                                    $queryToChkRecordExist = "SELECT * FROM " . TABLE_ANSWER_CHOICES . " WHERE question_id=" . $question_id . " AND is_delete=0";
                                    $resultToChkRecordExist = mysqli_query($GLOBALS['con'],$queryToChkRecordExist) or $message = mysqli_error($GLOBALS['con']);

                                    if (mysqli_num_rows($resultToChkRecordExist) > 0) {
                                        $updateFields = "`choice_text` ='" . $row->choice_text . "', `is_right`=" . (int)$row->is_right;

                                        $updateQuestionQuery = "UPDATE " . TABLE_ANSWER_CHOICES . " SET " . $updateFields . " WHERE question_id=" . $question_id;
                                        $resultQuestionQuery = mysqli_query($GLOBALS['con'],$updateQuestionQuery) or $message = mysqli_error($GLOBALS['con']);

                                        if ($resultQuestionQuery) {
                                            $status = SUCCESS;
                                            $message = SUCCESSFULLY_UPDATED;
                                        } else {
                                            $status = FAILED;
                                            $message = "";
                                        }
                                    }

                                } else {

                                    $insertChoiceFields = "`question_id`,`choice_text`,`is_right`";
                                    $insertChoiceValues = "" . $questionID . ",'" . $row->choice_text . "'," . (int)$row->is_right;

                                    $insertQuestionQuery = "INSERT INTO " . TABLE_ANSWER_CHOICES . "(" . $insertChoiceFields . ") VALUES( " . $insertChoiceValues . ")";
                                    $resultQuestionQuery = mysqli_query($GLOBALS['con'],$insertQuestionQuery) or $message = mysqli_error($GLOBALS['con']);


                                    if ($resultQuestionQuery) {
                                        $status = SUCCESS;
                                        $message = SUCCESSFULLY_ADDED;
                                    } else {
                                        $status = FAILED;
                                        $message = "";
                                    }
                                }
                            }
                        }
                    }
                }

            } else {
                $status = FAILED;
                $message = "question format is not correct";
                $data = array();
            }

            $data[] = $post;

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['question']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }
    
    
    /*
    * uploadMediaForQuestion
    */
    public function uploadMediaForQuestion($postData)
    {
        $status='';
        $mediaName = '';
        $created_date = date("Ymd-His");
        $reponse=array();
        //Create Random String.
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        
        //Generate random string with minimum 5 and maximum of 10 characters
        $str = substr(str_shuffle($chars), 0, 8);
        
        
         $question_id=$_POST['question_id'];
         $mediaType=$_POST['mediaType'];
         $secret_key = $_POST['secret_key'];
         $access_key = $_POST['access_key'];

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $question_media_dir = "question_" . $question_id . "/";
            if (!is_dir(QUESTION_IMAGE . $question_media_dir)) {
                mkdir(QUESTION_IMAGE . $question_media_dir, 0777);
            }

            if ("video" == $mediaType) {
                $mediaName = "_testVIDEO" . $created_date . ".mp4";
                $procedure = "UPDATE_QUESTION_VIDEO_LINK";
            } else if ("image" == $mediaType) {
                $mediaName = "_testIMAGE" . $created_date . ".png";
                $procedure = "UPDATE_QUESTION_IMAGE";
            }

            if ($_FILES["mediaFile"]["error"] > 0) {
                $message = $_FILES["mediaFile"]["error"];

            } else {

                //$thisdir = getcwd();
                //$uploadFolder =  $thisdir."/".QUESTION_IMAGE.$question_media_dir;

                $uploadFile = QUESTION_IMAGE . $question_media_dir . $mediaName;

                if (move_uploaded_file($_FILES['mediaFile']['tmp_name'], $uploadFile)) {

                    //store image data.
                    $link = $question_media_dir . $mediaName;

                    $procedure_update_set = "CALL " . $procedure . " ('" . $link . "','" . $question_id . "')";
                    $result_procedure = mysqli_query($GLOBALS['con'], $procedure_update_set) or $message = mysqli_error($GLOBALS['con']);

                    if ($result_procedure) {
                        $status = SUCCESS;
                        $message = "Successfully uploaded!.";
                    } else {
                        $status = FAILED;
                        $message = "failed to uploaded.";
                    }


                } else {
                    $status = FAILED;
                    $message = FAILED_TO_UPLOAD_MEDIA;
                }

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $data['question_id']=$question_id;
        $data['mediaType']=$mediaType;
        $data['image_link']=$link;

        $response['upload_question']=$data;
        $response['status']=$status;;
        $response['message']=$message;
        return $response;
    }
    
     /*
    * GetAllResults
    */
    public function getAllResults ($postData)
    {
    	$data = array();
        $response = array();
		
		$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query ="SELECT exams.*,examEvaluation.evaluation_status as 'Status',examEvaluation.total_questions as 'total_questions',examEvaluation.average_score as 'score',examEvaluation.total_student_attempted as 'total_submission' FROM ".TABLE_EXAMS. " exams
	 	INNER JOIN ".TABLE_EXAM_SCHEDULE." exam_schedule on exams.id=exam_schedule.exam_id
	 	INNER JOIN ". TABLE_EXAM_EVALUATION." examEvaluation on exams.id = examEvaluation.exam_id
	 	WHERE exam_schedule.exam_assessor =".$user_id ." AND exam_schedule.is_delete=0  ORDER BY exams.created_date DESC";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($result)) {
                while ($val = mysqli_fetch_assoc($result)) {
                    $post = array();

                    //Get Classromm name
                    $queryForClassName = "SELECT class_name FROM ".TABLE_CLASSROOMS." classrooms INNER JOIN ".TABLE_EXAMS." exams ON classrooms.id=exams.classroom_id WHERE exams.classroom_id= ".$val['classroom_id'] ." AND exams.is_delete=0";
                    $resultForClassName = mysqli_query($GLOBALS['con'], $queryForClassName) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultForClassName)) {
                        while ($row = mysqli_fetch_row($resultForClassName)) {
                            $post['classroom_name'] = $row[0];

                        }
                    }

                    //Get Subject name
                    $queryForSubjectName = "SELECT subjects.subject_name FROM ".TABLE_SUBJECTS ." subjects JOIN ".TABLE_EXAMS." exams ON subjects.id=exams.subject_id WHERE exams.subject_id=".$val['subject_id'] ." AND exams.is_delete=0";
                    $resultForSubjectName = mysqli_query($GLOBALS['con'], $queryForSubjectName) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultForSubjectName)) {
                        while ($row = mysqli_fetch_row($resultForSubjectName)) {
                            $post['subject_name'] = $row[0];
                        }
                    }

                    $post['exam_id'] = $val['id'];
                    $post['exam_name'] = $val['exam_name'];
                    $post['exam_type'] = $val['exam_type'];
                    $post['exam_category'] = $val['exam_category'];
                    $post['exam_mode'] = $val['exam_mode'];
                    $post['subject_id'] = $val['subject_id'];
                    $post['classroom_id'] = $val['classroom_id'];
                    $post['evaluation_status'] = $val['Status'];
                    $post['average_score'] = $val['score'];
                    $post['total_submission'] = $val['total_submission'];
                    $post['average_score'] = $val['score'];
                    $post['total_questions'] = $val['total_questions'];

                    $data[] = $post;

                    $status = SUCCESS;
                    $message = "";
                }
            } else {
                $status = FAILED;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=SUCCESS;
            $message = MALICIOUS_SOURCE;
        }
        $response['result']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;
    }
    
    /*
    * getStudentResultsByExam
    */
	public function getStudentResultsByExam($postData)
    {
    	$data = array();
        $response = array();
		
		$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $exam_id = validateObject($postData, 'exam_id', "");
        $exam_id = addslashes($exam_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query ="SELECT exam_score.*,s.username as 'username',s.user_profile_pic as 'profile_pic' FROM ".TABLE_STUDENT_EXAM_SCORE." exam_score
	 	INNER JOIN (SELECT id,full_name as 'username',profile_pic as 'user_profile_pic' FROM ".TABLE_USERS." users WHERE id=".$user_id.") s ON s.id=".$user_id."
	 	WHERE exam_score.exam_id=".$exam_id." AND exam_score.user_id=".$user_id ."AND exam_score.is_delete=0";


            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($result)) {
                while ($val = mysqli_fetch_assoc($result)) {
                    $post = array();


                    //Get Classromm name
                    $queryForClassName ="SELECT class_name from ".TABLE_CLASSROOMS." WHERE id IN ( SELECT classroom_id FROM ".TABLE_EXAMS." e JOIN ".TABLE_CLASSROOMS." c ON e.classroom_id=c.id WHERE e.id=".$exam_id.") ";

                    $resultForClassName = mysqli_query($GLOBALS['con'], $queryForClassName) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultForClassName)) {
                        while ($row = mysqli_fetch_row($resultForClassName)) {
                            $post['classroom_name'] = $row[0];

                        }
                    }


                    $post['exam_score_id'] = $val['id'];
                    $post['exam_id'] = $val['exam_id'];
                    $post['user_id'] = $val['user_id'];
                    $post['username'] = $val['username'];
                    $post['user_profile_pic'] = $val['profile_pic'];
                    $post['grade'] = $val['grade_obtained'];
                    $post['remark'] = $val['remarks'];
                    $post['score_obtained'] = $val['marks_obtained'];
                    $post['percentage'] = $val['percentage'];
                    $post['incorrect_count'] = $val['incorrect_answers'];
                    $post['1s_count'] = $val['1s_count'];
                    $post['2s_count'] = $val['2s_count'];
                    $post['3s_count'] = $val['3s_count'];
                    $post['4s_count'] = $val['4s_count'];
                    $post['5s_count'] = $val['5s_count'];

                    $data[] = $post;

                    $status = SUCCESS;
                    $message = "";
                }
            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['student_result']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
    }
    
    public function getHighScorers($postData)
    {
    	$message ='';
        $status='';
    	$data = array();
        $response = array();
		
		$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $role_id = validateObject($postData, 'role_id', "");
        $role_id = addslashes($role_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($role_id == "student") {

                /*  $selectQuery=" SELECT se.user_id,u.username,u.profile_pic as 'profile_pic',se.marks_obtained as 'exam_score',t.school_name,sub.subject_name FROM `student_academic_info` sai
                    INNER JOIN classroom_subject cs ON cs.`classroom_id`= sai.`classroom_id`
                    INNER JOIN exams e ON e.classroom_id=sai.classroom_id
                    INNER JOIN student_exam_score se ON se.exam_id=e.id
                    INNER JOIN (select s.school_name as 'school_name',s.id from schools s JOIN student_academic_info sa on sa.school_id=s.id) t ON t.id=sai.school_id
                    INNER JOIN users u ON u.id=se.user_id
                    INNER JOIN subjects sub ON sub.id=e.subject_id
                    WHERE sai.user_id = ".$user_id." and e.exam_category='ISM_Mock' group by se.user_id";*/

                $selectQuery = "select user_id,u.full_name,u.profile_pic,mx as 'exam_score',sch.school_name,sub.subject_name from
        (select max(marks_obtained) as mx,ses.exam_id,ses.user_id,ss2.school_id,ss2.subject_id from student_exam_score ses
		INNER JOIN 
		(select * from (select e.id,e.exam_category,ss1.school_id,ss1.subject_id from exams e
		INNER JOIN 
		(select subject_id,ss.school_id from classroom_subject cs
		INNER JOIN  
		(select classroom_id,school_id from " . TABLE_STUDENT_PROFILE . "
		where user_id=" . $user_id . ") ss on ss.classroom_id = cs.classroom_id) ss1 ON ss1.subject_id = e.subject_id)sx
		where sx.exam_category='ISM_Mock')ss2 ON ss2.id = ses.exam_id
		group by ses.id order by exam_id,ses.marks_obtained desc,user_id)ss3 
		JOIN schools sch ON sch.id=ss3.school_id 
		JOIN subjects sub ON sub.id=ss3.subject_id
		JOIN users u ON u.id=ss3.user_id
		group by exam_id";

                $result = mysqli_query($GLOBALS['con'], $selectQuery) or $message = mysqli_error($GLOBALS['con']);
                if (mysqli_num_rows($result)) {
                    while ($val = mysqli_fetch_assoc($result)) {

                        $data[] = $val;
                        $status = SUCCESS;
                    }
                } else {
                    $status = SUCCESS;
                    $message=DEFAULT_NO_RECORDS;
                }

            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['high_scorers']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
    }

    public function tempCreateQuestion ($postData)
    {
        $message = '';
        $status = '';
        $data = array();
        $post = array();
        $response = array();
        $answer_ids=array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $question_text = validateObject($postData, 'question_text', "");
        $question_text = addslashes($question_text);

        $subject_id = validateObject($postData, 'subject_id', "");
        $subject_id = addslashes($subject_id);

        $question_score = validateObject($postData, 'question_score', "");
        $question_score = addslashes($question_score);

        $question_format = validateObject($postData, 'question_format', "");
        $question_format = addslashes($question_format);

        /* $question_image = validateObject ($postData , 'question_image', "");
         $question_image = addslashes($question_image);

         $question_video = validateObject ($postData , 'question_video', "");
         $question_video = addslashes($question_video);*/

        $evaluation_notes = validateObject($postData, 'evaluation_notes', "");
        $evaluation_notes = addslashes($evaluation_notes);

        $solution = validateObject($postData, 'solution', "");
        $solution = addslashes($solution);

        $topic_id = validateObject($postData, 'topic_id', "");
        $topic_id = addslashes($topic_id);

        $classroom_id = validateObject($postData, 'classroom_id', "");
        $classroom_id = addslashes($classroom_id);

        $book_id = validateObject($postData, 'book_id', "");
        $book_id = addslashes($book_id);

        $question_id = validateObject($postData, 'question_id', "");
        $question_id = addslashes($question_id);

        $answer_choices = validateObject($postData, 'answer_choices', "");

        $hashtag_data = validateObject($postData, "hashtag_data", "");
        $hashtag_data = addslashes($hashtag_data);


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            //Check for question_format
            if ($question_format == "MCQ" || $question_format == "descriptive" || $question_format == "Fill ups") {
                if ($question_id != 0) {

                    $questionId = $question_id;
                    $queryToChkRecordExist = "SELECT * FROM " . TABLE_QUESTIONS . " WHERE id=" . $question_id . " AND is_delete=0";
                    $resultToChkRecordExist = mysqli_query($GLOBALS['con'], $queryToChkRecordExist) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultToChkRecordExist) > 0) {
                        $updateFields = "`question_text` ='" . $question_text . "',`question_score`='" . $question_score . "', `question_format`='" . $question_format . "', `question_creator_id`=" . $user_id . ", `evaluation_notes`='" . $evaluation_notes . "', `solution`='" . $solution . "', `topic_id`=" . $topic_id . ", `subject_id`=" . $subject_id . ", `classroom_id`=" . $classroom_id . ",`book_id`=" . $book_id;

                        $query = "UPDATE " . TABLE_QUESTIONS . " SET " . $updateFields . " WHERE id=" . $question_id;
                        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                        if ($result) {
                            $status = SUCCESS;
                            $message = SUCCESSFULLY_UPDATED;
                            $post['question_id'] = $question_id;
                        } else {
                            $status = FAILED;
                            $message = "";
                            $post['question_id'] = "";
                        }
                    }

                } else {

                    $insertFields = "`question_text`,`question_score`, `question_format`, `question_creator_id`, `evaluation_notes`, `solution`, `topic_id`, `subject_id`, `classroom_id`,`book_id`";
                    $insertValues = "'" . $question_text . "'," . $question_score . ",'" . $question_format . "'," . $user_id . ",'" . $evaluation_notes . "','" . $solution . "'," . $topic_id . "," . $subject_id . "," . $classroom_id . "," . $book_id;

                    $query = "INSERT INTO " . TABLE_QUESTIONS . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                    $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                    $questionID = mysqli_insert_id($GLOBALS['con']);

                    $questionId = $questionID;

                    if ($result) {
                        $status = SUCCESS;
                        $message = SUCCESSFULLY_ADDED;
                        $post['question_id'] = $questionID;
                    } else {
                        $status = FAILED;
                        $message = "";
                        $post['question_id'] = "";
                    }
                }

                if ($result) {

                    $getSocialFunctionClass = new SocialFunctions();

                    $postParam = new stdClass();

                    $resource_type = "question";
                    $resource_id = $questionId;

                    $postParam->hashtag_data = $hashtag_data;
                    $postParam->resource_id = $resource_id;
                    $postParam->resource_type = $resource_type;
                    //$hashTag= $getSocialFunctionClass->hashTag($postParam);


                    $hashTag = $getSocialFunctionClass->call_service("Hashtag", $postParam);


                    $post['hasgtag'] = $hashTag['message'];


                    if ($question_format == "MCQ") {
                        if (is_array($answer_choices)) {

                            foreach ($answer_choices as $row) {

                                if ($question_id != 0) {

                                    $queryToChkRecordExist = "SELECT * FROM " . TABLE_ANSWER_CHOICES . " WHERE question_id=" . $questionId . " AND id= " . $row->id . " AND is_delete=0";
                                    $resultToChkRecordExist = mysqli_query($GLOBALS['con'], $queryToChkRecordExist) or $message = mysqli_error($GLOBALS['con']);

                                    if (mysqli_num_rows($resultToChkRecordExist) > 0) {
                                        $updateFields = "`choice_text` ='" . $row->choice_text . "', `is_right`=" . (int)$row->is_right;

                                        $updateQuestionQuery = "UPDATE " . TABLE_ANSWER_CHOICES . " SET " . $updateFields . " WHERE question_id=" . $questionId . " AND id= " . $row->id;
                                        $resultQuestionQuery = mysqli_query($GLOBALS['con'], $updateQuestionQuery) or $message = mysqli_error($GLOBALS['con']);

                                        if ($resultQuestionQuery) {
                                            $status = SUCCESS;
                                            $message = SUCCESSFULLY_UPDATED;
                                        } else {
                                            $status = FAILED;
                                            $message = "";
                                        }
                                    } else {

                                        $insertChoiceFields = "`question_id`,`choice_text`,`is_right`";
                                        $insertChoiceValues = "" . $questionId . ",'" . $row->choice_text . "'," . (int)$row->is_right;

                                        $insertQuestionQuery = "INSERT INTO " . TABLE_ANSWER_CHOICES . "(" . $insertChoiceFields . ") VALUES( " . $insertChoiceValues . ")";
                                        $resultQuestionQuery = mysqli_query($GLOBALS['con'], $insertQuestionQuery) or $message = mysqli_error($GLOBALS['con']);


                                        if ($resultQuestionQuery) {
                                            $answer_ids[] = mysqli_insert_id($GLOBALS['con']);
                                            $status = SUCCESS;
                                            $message = SUCCESSFULLY_ADDED;
                                        } else {
                                            $status = FAILED;
                                            $message = "";
                                        }
                                    }

                                } else {

                                    $queryToChkRecordExist = "SELECT * FROM " . TABLE_ANSWER_CHOICES . " WHERE question_id=" . $question_id . " AND is_delete=0";
                                    $resultToChkRecordExist = mysqli_query($GLOBALS['con'], $queryToChkRecordExist) or $message = mysqli_error($GLOBALS['con']);

                                    if (mysqli_num_rows($resultToChkRecordExist) == 0) {

                                        $insertChoiceFields = "`question_id`,`choice_text`,`is_right`";
                                        $insertChoiceValues = "" . $questionID . ",'" . $row->choice_text . "'," . (int)$row->is_right;

                                        $insertQuestionQuery = "INSERT INTO " . TABLE_ANSWER_CHOICES . "(" . $insertChoiceFields . ") VALUES( " . $insertChoiceValues . ")";
                                        $resultQuestionQuery = mysqli_query($GLOBALS['con'], $insertQuestionQuery) or $message = mysqli_error($GLOBALS['con']);


                                        if ($resultQuestionQuery) {
                                            $answer_ids[] = mysqli_insert_id($GLOBALS['con']);
                                            $status = SUCCESS;
                                            $message = SUCCESSFULLY_ADDED;
                                        } else {
                                            $status = FAILED;
                                            $message = "";
                                        }
                                    }
                                }
                            }
                            $post['answer_choice_ids'] = $answer_ids;
                        }
                    }


                }

            } else {
                $status = FAILED;
                $message = "question format is not correct";
                $data = array();
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $data[]=$post;
        $response['question']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }


    /*
     * getCourses
    */
    public function getBooks ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `book_name`, `book_description`,`ebook_link`,`image_link` FROM ".TABLE_BOOKS ." WHERE is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            //echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    //$post['subject_id']=$row;
                    $data[] = $row;

                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['books']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    public function getExamsByUser($postData)
    {
        $message = '';
        $status = '';
        $data = array();
        $post = array();
        $exams = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security = new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key, $secret_key);

        if ($isSecure == yes) {

            $queryExam = "SELECT exams.*,student_exam_score.marks_obtained,student_exam_score.percentage,student_exam_score.exam_endtime,classroom.class_name,books.book_name,subject.subject_name FROM " . TABLE_EXAMS . " exams INNER JOIN " . TABLE_STUDENT_EXAM_SCORE . " student_exam_score ON exams.id=student_exam_score.exam_id
            LEFT JOIN ".TABLE_CLASSROOMS." classroom ON classroom.id=exams.classroom_id
            LEFT JOIN ".TABLE_BOOKS." books ON books.id=exams.book_id
            LEFT JOIN ".TABLE_SUBJECTS." subject ON subject.id=exams.subject_id
            WHERE student_exam_score.user_id=" . $user_id . " and student_exam_score.is_delete=0 and exams.is_delete=0 ORDER BY exams.id ASC";
            $resultExam = mysqli_query($GLOBALS['con'], $queryExam) or $message = mysqli_error($GLOBALS['con']);
           //echo $queryExam."\n".mysqli_num_rows($resultExam);

            if (mysqli_num_rows($resultExam) > 0) {
                while ($rowExam = mysqli_fetch_assoc($resultExam)) {

                    $post=array();
                   // $post[] = $this->getExamData($rowExam);
                    $post['exam_id']=$rowExam['id'];
                    $post['exam_type']=$rowExam['exam_type'];
                    $post['exam_category']=$rowExam['exam_category'];
                    $post['exam_mode']=$rowExam['exam_mode'];
                    $post['pass_percentage']=$rowExam['pass_percentage'];
                    $post['duration']=$rowExam['duration'];
                    $post['exam_type']=$rowExam['exam_type'];
                    $post['subject_id']=$rowExam['subject_id'];
                    $post['subject_name']=$rowExam['subject_name'];
                    $post['books_id']=$rowExam['books_id'];
                    $post['book_name']=$rowExam['book_name'];
                    $post['classroom_id']=$rowExam['classroom_id'];
                    $post['class_name']=$rowExam['class_name'];
                    $post['score_obtained'] = $rowExam['marks_obtained'];
                    $post['percentage'] = $rowExam['percentage'];
                    //total questions
                    $query="SELECT `evaluation_status`, `total_questions`, `average_score`, `total_student_attempted`, `total_assessed` FROM `exam_evaluation` WHERE `exam_id`=".$rowExam['id'] ." AND is_delete=0 ";
                    $result=mysqli_query($GLOBALS['con'],$query) or  $message=mysqli_error($GLOBALS['con']);
                    // echo $query;
                    if(mysqli_num_rows($result))
                    {
                        while($rowName=mysqli_fetch_assoc($result)) {
                            $post['total_question']=$rowName['total_question'];
                            $post['evaluation_status']=$rowName['evaluation_status'];
                            $post['exam_average_score']=$rowName['average_score'];
                            $post['total_assessed']=$rowName['total_assessed'];
                            $post['total_unassessed']= $rowName['total_student_attempted'] - $rowName['total_assessed'];
                        }
                    }
                    else{
                        $post['total_question']="";
                        $post['evaluation_status']="";
                        $post['exam_average_score']="";
                        $post['total_assessed']="";
                        $post['total_unassessed']= "";
                    }



                    //For get all exam questions
                    $queryExamQuestion = "SELECT * FROM ".TABLE_EXAM_QUESTION." where exam_id=".$rowExam['id'] ." and is_delete=0 ORDER BY question_id ASC";
                    $resultExamQuestion = mysqli_query($GLOBALS['con'], $queryExamQuestion) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultExamQuestion)) {
                        while ($row = mysqli_fetch_assoc($resultExamQuestion)) {
                            //$post['subject_id']=$row;
                            //$data[]=$row;

                            //left join ".TABLE_SUBJECTS." subject on question.subject_id=subject.id  ,subject.subject_name
                            $queryQuestion = "SELECT DISTINCT question.*,users.full_name FROM " . TABLE_QUESTIONS . " question
                inner join " . TABLE_USERS . " users on users.id=question.question_creator_id
                WHERE question.id=" . $row['question_id'] . " AND question.is_delete=0 ORDER BY question.id ASC";
                            $resultQuestion = mysqli_query($GLOBALS['con'], $queryQuestion) or $message = mysqli_error($GLOBALS['con']);
                            //echo $queryQuestion ; exit;

                            $questions=array();
                            if (mysqli_num_rows($resultQuestion)) {
                                while ($rowQuestion = mysqli_fetch_assoc($resultQuestion)) {


                                    $questions['question_id'] = $rowQuestion['id'];
                                    // $post['question_title']=$rowQuestion['id'];
                                    $questions['question_creator_id'] = $rowQuestion['question_creator_id'];
                                    $questions['question_creator_name'] = $rowQuestion['full_name'];
                                    $questions['question_format'] = $rowQuestion['question_format'];
                                    $questions['question_hint'] = $rowQuestion['question_hint'];
                                    $questions['question_text'] = $rowQuestion['question_text'];
                                    $questions['question_assets_link'] = $rowQuestion['assets_link'];
                                    $questions['question_image_link'] = $rowQuestion['question_image_link'];
                                    $questions['evaluation_notes'] = $rowQuestion['evaluation_notes'];
                                    $questions['solution'] = $rowQuestion['solution'];
                                    $questions['topic_id'] = $rowQuestion['topic_id'];



                                    if ($rowExam['exam_mode'] == "subjective") {
//
                                        $query = "SELECT * FROM " . TABLE_EXAM_SCHEDULE . " exam_schedule INNER JOIN " . TABLE_USERS . " users on exam_schedule.exam_assessor=users.id WHERE exam_id=" . $rowExam['id'] . " AND exam_schedule.is_delete=0 AND users.is_delete=0";
                                        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

                                        $row = mysqli_fetch_assoc($result);

                                        $post['evaluator_id'] = $row['exam_assessor'];
                                        $post['evaluator_name'] = $row['full_name'];
                                        $post['evaluator_profile_pic'] = $row['profile_pic'];

                                        $evaluation = array();
                                        $queryEvaluation = "SELECT * FROM " . TABLE_EXAM_EVALUATION . " WHERE exam_id=" . $rowExam['id'] . " AND is_delete=0";
                                        $resultEvaluation = mysqli_query($GLOBALS['con'], $queryEvaluation) or $message = mysqli_error($GLOBALS['con']);

                                        if (mysqli_num_rows($resultEvaluation)) {
                                            $rowEvaluation = mysqli_fetch_assoc($resultEvaluation);
                                            if ($rowEvaluation['evaluation_status'] == "unassesed") {
                                                $evaluation['evaluation_score'] = $rowEvaluation['evaluation_status'];
                                            } else {
                                                //user_id=".$student_id."
                                                $queryStudentRes = "SELECT student_subjective_evaluation.*,questions.question_score FROM " . TABLE_STUDENT_SUBJECTIVE_EVALUATION . " JOIN questions on questions.id=student_subjective_evaluation.question_id WHERE student_subjective_evaluation.is_delete=0 and student_subjective_evaluation.`exam_id`=" . $rowExam['id'] . " and student_subjective_evaluation.`user_id`=" . $user_id . " and evaluation_by=" . $row['exam_assessor'] . " and student_subjective_evaluation.question_id in (SELECT `question_id` FROM `exam_question` WHERE `exam_id`=" . $rowExam['id'] . ")";
                                                $resultStudentRes = mysqli_query($GLOBALS['con'], $queryStudentRes) or $message = mysqli_error($GLOBALS['con']);
                                                //echo $queryStudentRes;
                                                // echo "\n".mysqli_num_rows($resultStudentRes);
                                                //$evaluations = array();
                                                if (mysqli_num_rows($resultStudentRes)) {
                                                    while ($rowEvaluation = mysqli_fetch_assoc($resultStudentRes)) {
                                                        $evaluation['question_id'] = $rowEvaluation['question_id'];
                                                        $evaluation['student_response'] = $rowEvaluation['student_response'];
                                                        $evaluation['evaluation_score'] = $rowEvaluation['evaluation_score'];
                                                        $evaluation['evaluation_notes'] = $rowEvaluation['evaluation_notes'];
                                                        $evaluation['question_score'] = $rowEvaluation['question_score'];
                                                        $evaluations[] = $evaluation;

                                                    }
                                                    $questions['evaluations'] = $evaluations;
                                                } else {
                                                    $questions['evaluations'] = array();
                                                }

                                            }

                                        }

                                    } else if ($rowExam['exam_mode'] == "objective") {
//                {
//                    "exam_id":9,
//                    "student_id":202
//                }
                                        $queryStudentRes = "SELECT student_objective_response.*,questions.question_score FROM " . TABLE_STUDENT_OBJECTIVE_RESPONSE . " JOIN questions on questions.id=student_objective_response.question_id WHERE student_objective_response.is_delete=0 and student_objective_response.`user_id`=" . $user_id . " and student_objective_response.`exam_id`=" . $rowExam['id'] . " and student_objective_response.question_id=" . $questions['question_id'];
                                        $resultStudentRes = mysqli_query($GLOBALS['con'], $queryStudentRes) or $message = mysqli_error($GLOBALS['con']);
                                        //echo $queryStudentRes;
                                        //echo "\n".mysqli_num_rows($resultStudentRes);
                                        $evaluations = array();
                                        if (mysqli_num_rows($resultStudentRes)) {
                                            while ($rowEvaluation = mysqli_fetch_assoc($resultStudentRes)) {
                                                $evaluation['question_id'] = $rowEvaluation['question_id'];
                                                $evaluation['student_response'] = $rowEvaluation['choice_id'];
                                                $evaluation['evaluation_score'] = $rowEvaluation['marks_obtained'];
                                                $evaluation['is_right'] = $rowEvaluation['is_right'];
                                                $evaluation['answer_status'] = $rowEvaluation['answer_status'];
                                                $evaluation['question_score'] = $rowEvaluation['question_score'];
                                                $evaluations[] = $evaluation;

                                            }
                                            $questions['evaluations'] = $evaluations;
                                        } else {
                                            $questions['evaluations'] = array();
                                        }

                                    }

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


                                    $tags = array();
                                    $tagQuery = "SELECT tags.id as 'tag_id',tags.tag_name FROM " . TABLE_TAGS_QUESTION . " tag_question JOIN " . TABLE_TAGS . " tags ON tags.id=tag_question.tag_id WHERE tag_question.question_id=" . $rowQuestion['id'] . " and tags.is_delete=0 and tag_question.is_delete=0";
                                    $tagResult = mysqli_query($GLOBALS['con'], $tagQuery) or $message = mysqli_error($GLOBALS['con']);
                                    if (mysqli_num_rows($tagResult)) {

                                        while ($rowGetTags = mysqli_fetch_assoc($tagResult)) {
                                            $tags[] = $rowGetTags;

                                        }
                                        $questions['tags'] = $tags;
                                    } else {
                                        $questions['tags'] = $tags;
                                    }



                                }

                            }
                            $post['exam_questions'][] = $questions;
                        }


                    }


                    $post['exam_created_date'] = $rowExam['created_date'];
                    $post['exam_submission_date'] = $rowExam['exam_endtime'];


                    //Total Students
                    $query = "SELECT * FROM " . TABLE_STUDENT_PROFILE . " WHERE classroom_id=" . $rowExam['classroom_id'] . " AND is_delete=0 ";
                    $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                    // $post['total_student']=0;// need to change
                    $post['total_student'] = mysqli_num_rows($result);



                    $data[]=$post;

                }


                $status = SUCCESS;
                $message = REQUEST_ACCEPTED;
            }
            else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['user_exams']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }


}


?>
