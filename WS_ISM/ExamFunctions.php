<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 28/10/15
 * Time: 10:23 AM
 */


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

        $student_id = validateObject ($postData , 'student_id', "");
        $student_id = addslashes($student_id);

        $exam_id = validateObject ($postData , 'exam_id', "");
        $exam_id = addslashes($exam_id);

        $queryUser="SELECT * FROM ".TABLE_USERS." WHERE id=".$student_id;
        $resultUser=mysql_query($queryUser) or  $message=mysql_error();
//        echo $queryUser;
        if(mysql_num_rows($resultUser)){
            $row = mysql_fetch_assoc($resultUser);
            $post['student_name'] = $row['first_name'];
        }
        else{
            $post['student_name'] = "";
        }
        $queryExam="SELECT * FROM ".TABLE_EXAMS." WHERE id=".$exam_id;
        $resultExam=mysql_query($queryExam) or  $message=mysql_error();
        //echo $queryExam;
        if(mysql_num_rows($resultExam)) {

            $rowExam = mysql_fetch_assoc($resultExam);
            //$post['exam_id'] = $rowExam['id'];
           // $post['exam_score'] = $rowExam['marks_obtained'];
            if ($rowExam['exam_mode']=="subjective") {
//                {
//                    "exam_id":63,
//                    "student_id":370
//                }
                $query = "SELECT * FROM " . TABLE_EXAM_SCHEDULE . " exam_schedule INNER JOIN " . TABLE_USERS . " users on exam_schedule.exam_assessor=users.id WHERE exam_id=" . $exam_id;
                $result = mysql_query($query) or $message = mysql_error();
                //echo $query;
                $row = mysql_fetch_assoc($result);

                $post['evaluator_id'] = $row['exam_assessor'];
                $post['evaluator_name'] = $row['full_name'];
                $post['evaluator_profile_pic'] = $row['profile_pic'];


                $evaluation = array();
                $queryEvaluation = "SELECT * FROM " . TABLE_EXAM_EVALUATION . " WHERE exam_id=" . $exam_id;
                $resultEvaluation = mysql_query($queryEvaluation) or $message = mysql_error();

                if (mysql_num_rows($resultEvaluation)) {
                    $rowEvaluation = mysql_fetch_assoc($resultEvaluation);
                    if ($rowEvaluation['evaluation_status'] == "unassesed") {
                        $evaluation['evaluation_score'] = $rowEvaluation['evaluation_status'];
                    } else {
                        $queryStudentRes = "SELECT * FROM " . TABLE_STUDENT_SUBJECTIVE_EVALUATION . " WHERE `exam_id`=" . $exam_id . " and evaluation_by=".$row['exam_assessor']." and question_id in (SELECT `question_id` FROM `exam_question` WHERE `exam_id`=" . $exam_id . ")";
                        $resultStudentRes = mysql_query($queryStudentRes) or $message = mysql_error();
                        //echo $queryStudentRes;
                       // echo "\n".mysql_num_rows($resultStudentRes);
                        $evaluations=array();
                        if (mysql_num_rows($resultStudentRes)) {
                            while ($rowEvaluation = mysql_fetch_assoc($resultStudentRes)) {
                                $evaluation['question_id'] = $rowEvaluation['question_id'];
                                $evaluation['student_response'] = $rowEvaluation['student_response'];
                                $evaluation['evaluation_score'] = $rowEvaluation['evaluation_score'];
                                $evaluation['evaluation_notes'] = $rowEvaluation['evaluation_notes'];
                                $evaluations[]=$evaluation;
                            }

                        }
                        $post['evaluation']=$evaluations;

                    }

                }


            } else if($rowExam['exam_mode']=="objective"){
//                {
//                    "exam_id":9,
//                    "student_id":202
//                }
                $queryStudentRes = "SELECT * FROM " . TABLE_STUDENT_OBJECTIVE_RESPONSE . " WHERE `user_id`=" . $student_id . " and `exam_id`=" . $exam_id . " and question_id in (SELECT `question_id` FROM `exam_question` WHERE `exam_id`=" . $exam_id . ")";
                $resultStudentRes = mysql_query($queryStudentRes) or $message = mysql_error();
               // echo $queryStudentRes;
               // echo "\n".mysql_num_rows($resultStudentRes);
                $evaluations=array();
                if (mysql_num_rows($resultStudentRes)) {
                    while ($rowEvaluation = mysql_fetch_assoc($resultStudentRes)) {
                        $evaluation['question_id'] = $rowEvaluation['question_id'];
                        $evaluation['student_response'] = $rowEvaluation['choice_id'];
                        $evaluation['evaluation_score'] = $rowEvaluation['marks_obtained'];
                        $evaluation['is_right'] = $rowEvaluation['is_right'];
                        $evaluation['answer_status'] = $rowEvaluation['answer_status'];
                        $evaluations[]=$evaluation;
                    }

                }
                $post['evaluation']=$evaluations;
            }

            $data[]=$post;
          //  $message="";
            $status="success";
        }
        else{

            $status="failed";
            $message="";
        }


        $response['data']=$data;
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

        $post['exam_id']=$exam_id;
        $query="SELECT `student_id` FROM `student_teacher` WHERE `teacher_id`=".$user_id;
        $result=mysql_query($query) or  $message=mysql_error();
        //echo $query;
        if(mysql_num_rows($result)){
            while($row=mysql_fetch_assoc($result))
            {
                $evaluation=array();
                $student_id=$row['student_id'];

                $evaluation=array();
                $student_id=$row['student_id'];

                $queryStudentDetails="SELECT id,full_name,profile_pic FROM ".TABLE_USERS." WHERE id=".$student_id;
                $resultStudentDetails=mysql_query($queryStudentDetails) or  $message=mysql_error();
               // echo $queryStudentDetails;
                if(mysql_num_rows($resultStudentDetails))
                {
                    $rowDetails=mysql_fetch_assoc($resultStudentDetails);
                    $evaluation['student_id'] = $rowDetails['id'];
                    $evaluation['full_name'] = $rowDetails['full_name'];
                    $evaluation['profile_pic'] = $rowDetails['profile_pic'];


                    $queryDetails="SELECT * FROM ".TABLE_STUDENT_ACADEMIC_INFO." student INNER JOIN ".TABLE_CLASSROOMS. " classroom INNER JOIN ".TABLE_SCHOOLS." school on student.school_id=school.id and student.classroom_id=classroom.id WHERE user_id=".$student_id;
                    $resultDetails=mysql_query($queryDetails) or  $message=mysql_error();
                    $rowDetail = mysql_fetch_assoc($resultDetails);
                    $evaluation['school_name'] = $rowDetail['school_name'];
                    $evaluation['class_name'] = $rowDetail['class_name'];
                    $queryStudentDetails="SELECT * FROM ".TABLE_STUDENT_EXAM_SCORE." WHERE exam_id=".$exam_id;
                    $resultStudentDetails=mysql_query($queryStudentDetails) or  $message=mysql_error();
                    if(mysql_num_rows($resultStudentDetails)) {
                        $rowDetails = mysql_fetch_assoc($resultStudentDetails);
                        $evaluation['evaluation_score'] = $rowDetails['marks_obtained'];
                        $evaluation['exam_status'] = $rowDetails['exam_status'];
                        $evaluation['submission_date'] = $rowDetails['exam_endtime'];
                        $evaluation['remarks'] = $rowDetails['remarks'];
                        $evaluations[] = $evaluation;
                    }
                }
//                $queryStudentDetails="SELECT studentExamScore.user_id,users.full_name,users.profile_pic,studentExamScore.marks_obtained,studentExamScore.remarks FROM ".TABLE_STUDENT_EXAM_SCORE." studentExamScore INNER JOIN ".TABLE_USERS." users on studentExamScore.user_id=users.id WHERE studentExamScore.exam_id=".$exam_id." and studentExamScore.user_id=".$student_id;
//                $resultStudentDetails=mysql_query($queryStudentDetails) or  $message=mysql_error();
//                echo $queryStudentDetails;
//                if(mysql_num_rows($resultStudentDetails))
//                {
//                    $rowDetails=mysql_fetch_assoc($resultStudentDetails);
//                    $evaluation[] = $rowDetails;
//                    $post[]=$evaluation;
//                }
            }
           $post['evaluation']=$evaluations;
            $data[]=$post;
            //$message="";
            $status="success";
        }
        else{

            $status="failed";
           // $message=DEFAULT_NO_RECORDS;
        }


        $response['data']=$data;
       // $response['message']=$message;
        $response['status']=$status;
        $response['message']="data";
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

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

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

        $insertFields="`created_by`,`exam_name`, `book_id`,`classroom_id`, `subject_id`, `exam_type`, `exam_category`, `exam_mode`, `pass_percentage`, `duration`, `instructions`, `negative_marking`,`negative_mark_value`, `random_question`, `declare_results`,`attempt_count`";
        $insertValues=$user_id.",'".$exam_name."',".$book_id.",".$classroom_id.",".$subject_id.",'".$exam_type."','".$exam_category."','".$exam_mode."',".$passing_percent.",".$exam_duration.",'".$exam_instruction."','".$negative_marking."',".$negative_mark_value.",'".$random_question."','".$declare_results."','".$attempt_count."'";

        $query="INSERT INTO ".TABLE_EXAMS."(".$insertFields.") VALUES (".$insertValues.")";
        $result=mysql_query($query) or  $message=mysql_error();
       //echo $query;
       // echo $insertValues;
        if($result){
            $post['exam_id']=mysql_insert_id();
            $status="success";
            if($exam_start_date!=null and $exam_start_time!=null){
                $insertExamScheduleFields="`exam_id`, `schedule_by`, `exam_assessor`, `start_date`, `start_time`, `school_classroom_id`";
                $insertExamScheduleValues="".$post['exam_id'].",".$user_id.",".$user_id.",'".$exam_start_date."','".$exam_start_time."',".$classroom_id;
                $queryInsertExamSchedule="INSERT INTO `exam_schedule`(".$insertExamScheduleFields.") VALUES (".$insertExamScheduleValues.")";
                $resultExamSchedule=mysql_query($queryInsertExamSchedule) or  $message=mysql_error();
              //  echo $queryInsertExamSchedule;
                if($resultExamSchedule){
                    $status="success";
                  //  $message="Exam created and scheduled";
                }
                else{
                    $status="failed";
                   // $message="Exam is created but not scheduled";
                }
            }
            // $message="";
        }
        else{
            $post['exam_id']="";
            $status="failed";
           // $message="Exam is not created and scheduled";
        }

        $data[]=$post;
        $response['data']=$data;
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

        $question_id = validateObject ($postData , 'question_id', "");
        //$question_id = addslashes($question_id);


        foreach($question_id as $id){
            $insertFields="`exam_id`, `question_id`";
            $insertValues=$exam_id.",".$id;
            $query="INSERT INTO ".TABLE_EXAM_QUESTION."(".$insertFields.") VALUES (".$insertValues.")";
            $result=mysql_query($query) or  $message=mysql_error();
            // echo $query;
            if($result){
                $status="success";
                $message="";
            }
            else{
                $status="failed";
                // $message="";
            }
        }


        $response['data']=$data;
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

        if($role==3) {
            $getField = "subject_id";
            $rowParameter='subject_id';
            $table = TABLE_TEACHER_SUBJECT_INFO;
        }
        else if($role==4) {
            $getField = "book_id";
            $rowParameter='book_id';
            $table = TABLE_AUTHOR_BOOK;
            }

        $querySubjectId="SELECT ".$getField." FROM ".$table." WHERE `user_id`=".$user_id;
        $resultSubjectId=mysql_query($querySubjectId) or  $message=mysql_error();
        // echo $query;
        if(mysql_num_rows($resultSubjectId))
        {
            while($row=mysql_fetch_assoc($resultSubjectId)){
                //$post['subject_id']=$row;
                //$data[]=$row;
                $getFields="question.id,question.question_creator_id,question.question_format,question.question_hint,question.question_text,question.assets_link,question.question_image_link,question.evaluation_notes,question.solution,question.topic_id,question.subject_id,subject.subject_name,question.classroom_id,question.book_id";
                $queryQuestion="SELECT ".$getFields." FROM ".TABLE_QUESTIONS." question Inner join ".TABLE_USERS." users inner join ".TABLE_SUBJECTS." subject on question.subject_id=subject.id and question.question_creator_id=users.id  WHERE ".$getField."=".$row[$rowParameter];
                $resultQuestion=mysql_query($queryQuestion) or  $message=mysql_error();
                // echo $queryQuestion;
                if(mysql_num_rows($resultQuestion))
                {
                    while($rowQuestion=mysql_fetch_assoc($resultQuestion)) {
                        $post['question_id']=$rowQuestion['id'];
                        // $post['question_title']=$rowQuestion['id'];
                        $post['question_creator_id']=$rowQuestion['question_creator_id'];
                        $post['question_creator_name']=$rowQuestion['full_name'];
                        $post['question_format']=$rowQuestion['question_format'];
                        $post['question_hint']=$rowQuestion['question_hint'];
                        $post['question_text']=$rowQuestion['question_text'];
                        $post['question_assets_link']=$rowQuestion['assets_link'];
                        $post['question_image_link']=$rowQuestion['question_image_link'];
                        $post['evaluation_notes']=$rowQuestion['evaluation_notes'];
                        $post['solution']=$rowQuestion['solution'];
                        $post['topic_id']=$rowQuestion['topic_id'];
                        $post['subject_id']=$rowQuestion['subject_id'];
                        $post['subject_name']=$rowQuestion['subject_name'];
                        $post['classroom_id']=$rowQuestion['classroom_id'];
                        $post['book_id']=$rowQuestion['book_id'];
                        $choice=array();
                        if($rowQuestion['question_format']=='MCQ')
                        {
                            $queryGetChoice="SELECT `id`, `question_id`, `choice_text`, `is_right`, `image_link`, `audio_link`, `video_link` FROM ".TABLE_ANSWER_CHOICES." WHERE `question_id`=".$rowQuestion['id'];
                            $resultGetChoice=mysql_query($queryGetChoice) or $message=mysql_error();
                           // echo $resultGetChoice;
                            if(mysql_num_rows($resultGetChoice))
                            {
                                while($rowGetChoice=mysql_fetch_assoc($resultGetChoice)) {
                                    $choice[]=$rowGetChoice;

                                }
                                $post['answers']=$choice;
                            }
                        }
                        else{
                            $post['answers']=$choice;
                        }

                        $data[]=$post;
                    }
                    $status="success";
                    $message="";
                }
                else{
                    $status="failed";
                    $message=DEFAULT_NO_RECORDS;
                }
            }

        }
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }

        $response['data']=$data;
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


        $query="SELECT `id`, `course_name` FROM ".TABLE_COURSES;
        $result=mysql_query($query) or  $message=mysql_error();
        // echo $query;
        if(mysql_num_rows($result))
        {
            while($row=mysql_fetch_assoc($result)){
                //$post['subject_id']=$row;
                $data[]=$row;
            }
            $status="success";

        }
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }

        $response['data']=$data;
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


        $query="SELECT `id`, `subject_name`, `subject_image` FROM ".TABLE_SUBJECTS;
        $result=mysql_query($query) or  $message=mysql_error();
        // echo $query;
        if(mysql_num_rows($result))
        {
            while($row=mysql_fetch_assoc($result)){
                //$post['subject_id']=$row;
                $data[]=$row;

            }
            $status="success";

        }
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }

        $response['data']=$data;
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

        $query="SELECT `id`, `topic_name`, `topic_description` FROM ".TABLE_TOPICS ." where subject_id=".$subject_id;
        $result=mysql_query($query) or  $message=mysql_error();
        // echo $query;
        if(mysql_num_rows($result))
        {
            while($row=mysql_fetch_assoc($result)){
                //$post['subject_id']=$row;
                $data[]=$row;

            }
            $status="success";

        }
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }

        $response['data']=$data;
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


        $query="SELECT `id`, `course_id`, `class_name`, `class_nickname`FROM ".TABLE_CLASSROOMS;
        $result=mysql_query($query) or  $message=mysql_error();
        // echo $query;
        if(mysql_num_rows($result))
        {
            while($row=mysql_fetch_assoc($result)){
                //$post['subject_id']=$row;
                $data[]=$row;

            }
            $status="success";

        }
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }

        $response['data']=$data;
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
        $exam_id[]=null;
        if($role==3) {
            $getField = "subject_id";
            $rowParameter='subject_id';
            $table = TABLE_TEACHER_SUBJECT_INFO;

        }
        else if($role==4) {
            $getField = "book_id";
            $rowParameter='book_id';
            $table = TABLE_AUTHOR_BOOK;

        }

        $queryExam="SELECT * FROM ".TABLE_EXAMS." WHERE ".$getField." in (SELECT ".$getField." FROM ".$table." WHERE `user_id`=".$user_id.")";
        $resultExam=mysql_query($queryExam) or  $message=mysql_error();
        //echo $queryExam;
        if(mysql_num_rows($resultExam)>0){
            while($rowExam=mysql_fetch_assoc($resultExam)) {
                $exam_id[]=$rowExam['id'];
                $data[]=$this->getExamData($rowExam);
            }
        }
        $queryExam="SELECT * FROM ".TABLE_EXAMS." WHERE id in (select exam_id from ".TABLE_EXAM_SCHEDULE." where exam_assessor=".$user_id.")";
        $resultExam=mysql_query($queryExam) or  $message=mysql_error();
        //echo $queryExam."\n".mysql_num_rows($resultExam);
        if(mysql_num_rows($resultExam)){
            while($rowExam=mysql_fetch_assoc($resultExam)) {
                $flag=false;
                foreach ($exam_id as $id){
                    if($id==$rowExam['id']){
                        $flag=true;
                    }
                }
                if($flag==false){
                    $data[]=$this->getExamData($rowExam);
                }

            }
        }

        $status="success";
        $message="";
        if($data==null){
            $status="failed";
            $message=DEFAULT_NO_RECORDS;

        }
        $response['data']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    public function getExamData($rowExam){
        $post=array();
        $post['exam_id']=$rowExam['id'];
        // $post['question_title']=$rowExam['id'];
        $post['exam_name']=$rowExam['exam_name'];
        $post['classroom_id']=$rowExam['classroom_id'];
        $post['subject_id']=$rowExam['subject_id'];
        $post['book_id']=$rowExam['book_id'];
        $query="SELECT class_name FROM ".TABLE_CLASSROOMS." WHERE id=".$rowExam['classroom_id'];
//                        echo $query;
        $result=mysql_query($query) or  $message=mysql_error();
        if(mysql_num_rows($result))
        {
            while($rowName=mysql_fetch_assoc($result)) {
                $post['classroom_name']=$rowName['class_name'];
            }
        }
        // total student
        $query="SELECT * FROM ".TABLE_STUDENT_ACADEMIC_INFO." WHERE classroom_id=".$rowExam['classroom_id'];
//                        echo $query;
        $result=mysql_query($query) or  $message=mysql_error();
        $post['total_student']=mysql_num_rows($result);

        //subject name
        $query="SELECT subject_name FROM ".TABLE_SUBJECTS." WHERE id=".$rowExam['subject_id'];
//                        echo $query;
        $result=mysql_query($query) or  $message=mysql_error();
        if(mysql_num_rows($result))
        {
            while($rowName=mysql_fetch_assoc($result)) {
                $post['subject_name']=$rowName['subject_name'];
            }
        }
        $post['exam_type']=$rowExam['exam_type'];
        $post['exam_category']=$rowExam['exam_category'];
        $post['exam_mode']=$rowExam['exam_mode'];
        $post['pass_percentage']=$rowExam['pass_percentage'];
        $post['duration']=$rowExam['duration'];


        //total questions
        $query="SELECT `evaluation_status`, `total_questions`, `average_score`,`total_student_attempted` FROM `exam_evaluation` WHERE `exam_id`=".$rowExam['id'];
        $result=mysql_query($query) or  $message=mysql_error();
       // echo $query;
        if(mysql_num_rows($result))
        {
            while($rowName=mysql_fetch_assoc($result)) {
                $post['total_question']=$rowName['total_question'];
                $post['evaluation_status']=$rowName['evaluation_status'];
                $post['average_score']=$rowName['average_score'];
                $post['total_student_attempted']=$rowName['total_student_attempted'];
            }
        }
        else{
            $post['total_question']="";
            $post['evaluation_status']="";
            $post['average_score']="";
            $post['total_student_attempted']="0";
        }
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

        $query="SELECT * FROM ".TABLE_EXAMS." where id=".$exam_id;
        $result=mysql_query($query) or  $message=mysql_error();
        // echo $query;
        if(mysql_num_rows($result))
        {
            $row=mysql_fetch_assoc($result);
            $examData['id']=$row['id'];
            $examData['exam_name']=$row['exam_name'];
            $examData['instruction']=$row['instruction'];

            $queryExamQuestion="SELECT * FROM ".TABLE_EXAM_QUESTION." where exam_id=".$exam_id;
            $resultExamQuestion=mysql_query($queryExamQuestion) or  $message=mysql_error();

            if(mysql_num_rows($resultExamQuestion))
            {
                while($row=mysql_fetch_assoc($resultExamQuestion)){
                    //$post['subject_id']=$row;
                    //$data[]=$row;
                    $queryQuestion="SELECT * FROM ".TABLE_QUESTIONS." WHERE id=".$row['question_id'];
                    $resultQuestion=mysql_query($queryQuestion) or  $message=mysql_error();
                    // echo $query;
                    if(mysql_num_rows($resultQuestion))
                    {
                        while($rowQuestion=mysql_fetch_assoc($resultQuestion)) {
                            $questions['question_id']=$rowQuestion['id'];
                            // $post['question_title']=$rowQuestion['id'];
                            $questions['question_creator_id']=$rowQuestion['question_creator_id'];
                            $questions['question_format']=$rowQuestion['question_format'];
                            $questions['question_hint']=$rowQuestion['question_hint'];
                            $questions['question_text']=$rowQuestion['question_text'];
                            $questions['question_assets_link']=$rowQuestion['assets_link'];
                            $questions['question_image_link']=$rowQuestion['question_image_link'];
                            $questions['evaluation_notes']=$rowQuestion['evaluation_notes'];
                            $questions['solution']=$rowQuestion['solution'];
                            $questions['topic_id']=$rowQuestion['topic_id'];
                            $questions['subject_id']=$rowQuestion['subject_id'];
                            $questions['classroom_id']=$rowQuestion['classroom_id'];
                            $questions['book_id']=$rowQuestion['book_id'];
                            $choice=array();
                            if($rowQuestion['question_format']=='MCQ')
                            {
                                $queryGetChoice="SELECT `id`, `question_id`, `choice_text`, `is_right`, `image_link`, `audio_link`, `video_link` FROM ".TABLE_ANSWER_CHOICES." WHERE `question_id`=".$rowQuestion['id'];
                                $resultGetChoice=mysql_query($queryGetChoice) or $message=mysql_error();
                                // echo $resultGetChoice;
                                if(mysql_num_rows($resultGetChoice))
                                {
                                    while($rowGetChoice=mysql_fetch_assoc($resultGetChoice)) {
                                        $choice[]=$rowGetChoice;

                                    }
                                    $questions['answers']=$choice;
                                }
                            }
                            else{
                                $questions['answers']=$choice;
                            }

                            $post[]=$questions;
                        }
                        $status="success";
                        $message="";
                    }
                    else{
                        $status="failed";
                        $message="Questions are not found!";
                    }
                }

            }
            else{
                $status="failed";
                $message=DEFAULT_NO_RECORDS;
            }
        }
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }
        $examData['questions']=$post;
        $data[]=$examData;
        $response['data']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }
}


?>
