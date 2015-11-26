<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 28/10/15
 * Time: 10:23 AM
 */

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


//        $query="SELECT * FROM `student_exam_score` WHERE `user_id`=".$student_id." and exam_id=".$exam_id;
//        $result=mysql_query($query) or  $message=mysql_error();
        //echo $query;
        // echo $insertValues;


		    

        $queryExam="SELECT * FROM ".TABLE_EXAMS." WHERE id=".$exam_id;
        $resultExam=mysql_query($queryExam) or  $message=mysql_error();
        //echo $queryExam;
        if(mysql_num_rows($resultExam)) {

            $rowExam = mysql_fetch_assoc($resultExam);
            $post['exam_id'] = $rowExam['id'];
            $post['exam_score'] = $rowExam['marks_obtained'];
           
           //=========================Add Question Paallete======================== 
           if($rowExam['exam_mode']=="subjective")
           {
           		$table=TABLE_STUDENT_SUBJECTIVE_EVALUATION;
           }
           else
           {
          	 	$table=TABLE_STUDENT_OBJECTIVE_RESPONSE;
           }
           //user_id=".$student_id." AND
            $queryGetQuestionPalette="SELECT question_id,answer_status FROM ".$table." WHERE exam_id=".$exam_id ." ORDER BY question_id ASC";
            $resultGetQuestionPalette=mysql_query($queryGetQuestionPalette) or  $message=mysql_error();
    		
    		$question_palette_array=array();
            if(mysql_num_rows($resultGetQuestionPalette)) {

           	 	while($rowQuestion = mysql_fetch_assoc($resultGetQuestionPalette))
           	 	{  	
  	              $insert_question_palette[] = "{'".$rowQuestion['question_id']."','".$rowQuestion['answer_status']."'}";	   
           	 	}
           	 }
           $post['question_palette']=$insert_question_palette;
             //=========================Finish ti Add Question Paallete =================
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
                    //user_id=".$student_id."
                        $queryStudentRes = "SELECT * FROM " . TABLE_STUDENT_SUBJECTIVE_EVALUATION . " WHERE `exam_id`=" . $exam_id . " and `user_id`=" . $student_id . " and evaluation_by=".$row['exam_assessor']." and question_id in (SELECT `question_id` FROM `exam_question` WHERE `exam_id`=" . $exam_id . ")";
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
                //echo $queryStudentRes;
                //echo "\n".mysql_num_rows($resultStudentRes);
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
                    $post['evaluation']=$evaluations;
                }
            }

            $data[]=$post;
            $message="";
            $status="success";
        }
        else{

            $status="failed";
            $message="";
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

        $post['exam_id']=$exam_id;
        $query="SELECT * FROM ".TABLE_STUDENT_EXAM_SCORE." WHERE `exam_id`=".$exam_id;
        $result=mysql_query($query) or  $message=mysql_error();
        //echo $query;
        if(mysql_num_rows($result)){
            while($row=mysql_fetch_assoc($result))
            {
                $evaluation=array();
                $student_id=$row['user_id'];

                $queryStudentDetails="SELECT id,full_name,profile_pic FROM ".TABLE_USERS."  WHERE id=".$student_id;
                $resultStudentDetails=mysql_query($queryStudentDetails) or  $message=mysql_error();
                // echo $queryStudentDetails;
                if(mysql_num_rows($resultStudentDetails))
                {
                    $rowDetails=mysql_fetch_assoc($resultStudentDetails);
                    $evaluation['student_id'] = $rowDetails['id'];
                    $evaluation['student_name'] = $rowDetails['full_name'];
                    $evaluation['student_profile_pic'] = $rowDetails['profile_pic'];

                }
                $evaluation['evaluation_score'] = $row['marks_obtained'];
                $evaluation['exam_status'] = $row['exam_status'];
                $evaluation['submission_date'] = $row['exam_endtime'];
                $evaluation['remarks'] = $row['remarks'];
                $evaluations[] = $evaluation;
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
           $post['examsubmittor']=$evaluations;
            $data[]=$post;
            //$message="";
            $status="success";
        }
        else{

            $status="failed";
            //$message="";
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

        $question_id = validateObject ($postData , 'question_id', "");
        $question_id = addslashes($question_id);

        $insertFields="`exam_id`, `question_id`";
        $insertValues=$exam_id.",".$question_id;

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
               $queryQuestion="SELECT question.*,users.full_name,subject.subject_name FROM ".TABLE_QUESTIONS." question
                Inner join ".TABLE_USERS." users
                inner join ".TABLE_SUBJECTS." subject on question.subject_id=subject.id and question.question_creator_id=users.id
                WHERE ".$getField."=".$row[$rowParameter];
                $resultQuestion=mysql_query($queryQuestion) or  $message=mysql_error();
                //echo $query;
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

                        $tags=array();
                        $tagQuery="SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_QUESTION." tag_question JOIN ".TABLE_TAGS." tags ON tags.id=tag_question.tag_id WHERE tag_question.question_id=".$rowQuestion['id'];
                        $tagResult=mysql_query($tagQuery) or  $message=mysql_error();
                        if(mysql_num_rows($tagResult))
                        {

                            while($rowGetTags=mysql_fetch_assoc($tagResult)) {
                                $tags[]=$rowGetTags;

                            }
                            $post['tags']=$tags;
                        }
                        else{
                            $post['tags']=$tags;
                        }



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
                    //$status="failed ";
                   // $message=DEFAULT_NO_RECORDS;
                }
            }

        }
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
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
        $response['exams']=$data;
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
        $query="SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE classroom_id=".$rowExam['classroom_id'];
        //    echo $query;
        $result=mysql_query($query) or  $message=mysql_error();

       // $post['total_student']=0;// need to change
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
        $query="SELECT `evaluation_status`, `total_questions`, `average_score` FROM `exam_evaluation` WHERE `exam_id`=".$rowExam['id'];
        $result=mysql_query($query) or  $message=mysql_error();
       // echo $query;
        if(mysql_num_rows($result))
        {
            while($rowName=mysql_fetch_assoc($result)) {
                $post['total_question']=$rowName['total_question'];
                $post['evaluation_status']=$rowName['evaluation_status'];
                $post['average_score']=$rowName['average_score'];
            }
        }
        else{
            $post['total_question']="";
            $post['evaluation_status']="";
            $post['average_score']="";
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

        $query="SELECT exams.*,classrooms.class_name,books.book_name FROM ".TABLE_EXAMS." exams 
        LEFT JOIN ".TABLE_CLASSROOMS." classrooms ON classrooms.id=exams.classroom_id 
        LEFT JOIN ".TABLE_BOOKS." books ON books.id=exams.book_id
        where exams.id=".$exam_id;
        
        $result=mysql_query($query) or  $message=mysql_error();
        // echo $query;
        if(mysql_num_rows($result))
        {
            $row=mysql_fetch_assoc($result);
            
            $examData['id']=$row['id'];
            $examData['exam_name']=$row['exam_name'];
            $examData['instruction']=$row['instruction'];
            $examData['book_name']=$row['book_name'];
            $examData['class_name']=$row['class_name'];
            $examData['created_date']=$row['created_date'];

            $queryExamQuestion="SELECT * FROM ".TABLE_EXAM_QUESTION." where exam_id=".$exam_id;
            $resultExamQuestion=mysql_query($queryExamQuestion) or  $message=mysql_error();

            if(mysql_num_rows($resultExamQuestion))
            {
                while($row=mysql_fetch_assoc($resultExamQuestion)){
                    //$post['subject_id']=$row;
                    //$data[]=$row;
                    $queryQuestion="SELECT question.*,users.full_name,subject.subject_name FROM ".TABLE_QUESTIONS." question
                Inner join ".TABLE_USERS." users
                inner join ".TABLE_SUBJECTS." subject on question.subject_id=subject.id and question.question_creator_id=users.id
                WHERE question.id=".$row['question_id'];
                   // $queryQuestion="SELECT * FROM ".TABLE_QUESTIONS." WHERE id=".$row['question_id'];
                    $resultQuestion=mysql_query($queryQuestion) or  $message=mysql_error();
                    // echo $query;
                    if(mysql_num_rows($resultQuestion))
                    {
                        while($rowQuestion=mysql_fetch_assoc($resultQuestion)) {
                            $questions['question_id']=$rowQuestion['id'];
                            // $post['question_title']=$rowQuestion['id'];
                            $questions['question_creator_id']=$rowQuestion['question_creator_id'];
                            $questions['question_creator_name']=$rowQuestion['full_name'];
                            $questions['question_format']=$rowQuestion['question_format'];
                            $questions['question_hint']=$rowQuestion['question_hint'];
                            $questions['question_text']=$rowQuestion['question_text'];
                            $questions['question_assets_link']=$rowQuestion['assets_link'];
                            $questions['question_image_link']=$rowQuestion['question_image_link'];
                            $questions['evaluation_notes']=$rowQuestion['evaluation_notes'];
                            $questions['solution']=$rowQuestion['solution'];
                            $questions['topic_id']=$rowQuestion['topic_id'];
                            $questions['subject_id']=$rowQuestion['subject_id'];
                            $questions['subject_name']=$rowQuestion['subject_name'];
                            $questions['classroom_id']=$rowQuestion['classroom_id'];
                            $questions['book_id']=$rowQuestion['book_id'];


                            $tags=array();
                            $tagQuery="SELECT tags.id as 'tag_id',tags.tag_name FROM ".TABLE_TAGS_QUESTION." tag_question JOIN ".TABLE_TAGS." tags ON tags.id=tag_question.tag_id WHERE tag_question.question_id=".$rowQuestion['id'];
                            $tagResult=mysql_query($tagQuery) or  $message=mysql_error();
                            if(mysql_num_rows($tagResult))
                            {

                                while($rowGetTags=mysql_fetch_assoc($tagResult)) {
                                    $tags[]=$rowGetTags;

                                }
                                $questions['tags']=$tags;
                            }
                            else{
                                $questions['tags']=$tags;
                            }

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

        $answer_choices = validateObject ($postData , 'answer_choices', "");
      
      //Check for question_format
      	if($question_format=="MCQ" || $question_format=="subjective" || $question_format=="text")
      	{
		
        $insertFields="`question_text`,`question_score`, `question_format`, `question_creator_id`, `evaluation_notes`, `solution`, `topic_id`, `subject_id`, `classroom_id`,`book_id`";
        $insertValues="'".$question_text."',".$question_score.",'".$question_format."',".$user_id. ",'".$evaluation_notes."','".$solution ."',".$topic_id.",".$subject_id.",".$classroom_id.",".$book_id;

        $query="INSERT INTO ".TABLE_QUESTIONS."(".$insertFields.") VALUES (".$insertValues.")";
        $result=mysql_query($query) or  $message=mysql_error();
       
    	
       if($result)
       {
      	 $post['question_id']=mysql_insert_id();
       
        if($question_format == "MCQ")
      	{ 	    	
   			if(is_array($answer_choices)){
  
   			 foreach($answer_choices as $row){
  		 				
  		  
  		           $insertChoiceFeilds="`question_id`,`choice_text`,`is_right`";
  	               $insertChoiceValues = "".$post['question_id'].",'".$row->choice_text."',".(int) $row->is_right;
  		 		   
  		 		   $insertQuestionQuery="INSERT INTO " .TABLE_ANSWER_CHOICES."(".$insertChoiceFeilds.") VALUES( ".$insertChoiceValues.")";
  		 		   $resultQuestionQuery =mysql_query($insertQuestionQuery) or $message=mysql_error();
       			 	
 	 			}  
  			}
		 }
		 
		   $status="success";
		}
		else
		{
			$post['question_id']="";
            $status="failed";
		}
		
		
		}
		else
		{
            $status="failed";
            $message="question format is not correct";
            $data=array();
		}
		 
        $data[]=$post;
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
        $mediaName = '';
        $created_date = date("Ymd-His");
        
        //Create Random String.
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        
        //Generate random string with minimum 5 and maximum of 10 characters
        $str = substr(str_shuffle($chars), 0, 8);
        
        
         $question_id=$_POST['question_id'];
         $mediaType=$_POST['mediaType'];
      
      
        $question_media_dir = "question_" . $question_id . "/";
        if (!is_dir(QUESTION_IMAGE.$question_media_dir)) {
          	 mkdir(QUESTION_IMAGE.$question_media_dir, 0777);
       	}
        
        if("video"==$mediaType)
        {
        	$mediaName = "VIDEO".$created_date."_test.mp4";
        	$procedure="UPDATE_QUESTION_VIDEO_LINK";
        }
        else if("image"==$mediaType)
        {
        	$mediaName = "IMAGE".$created_date."_test.jpg";
        	$procedure="UPDATE_QUESTION_IMAGE";
        }
        
        if ($_FILES["mediaFile"]["error"] > 0) {
                $message = $_FILES["mediaFile"]["error"];

        } 
        else {
       
        //$thisdir = getcwd(); 
       	//$uploadFolder =  $thisdir."/".QUESTION_IMAGE.$question_media_dir;
        
        	$uploadFile =  QUESTION_IMAGE.$question_media_dir . $mediaName;
                
        	if (move_uploaded_file($_FILES['mediaFile']['tmp_name'],$uploadFile)) {
                
                    //store image data.
                     $link=$question_media_dir . $mediaName;
                
                    $procedure_update_set = "CALL ".$procedure." ('".$link."','".$question_id."')";
                    $result_procedure = mysql_query($procedure_update_set) or $message = mysql_error();
                    
                    if($result_procedure)
                    {
                    	 $status = "success";
                   		 $message = "Successfully uploaded!.";
                    }
                    else
                    {
                    	$status = "failed";
                   		$message = "failed to uploaded.";
                    }
                   
                    
               }
			else {
                    $status = "failed";
                    $message = "Failed to upload media file on server.";
            }
               
        } 
        $data['question_id']=$question_id;
        $data['mediaType']=$mediaType;
    	$data['status']=$status;
        $data['image_link']=$link;
        $data['message']=$message;
        return $data;
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
		
			
	 	$query="SELECT exams.*,examEvaluation.evaluation_status as 'Status',examEvaluation.total_questions as 'total_questions',examEvaluation.average_score as 'score',examEvaluation.total_student_attempted as 'total_submission' FROM ".TABLE_EXAMS. " exams 
	 	INNER JOIN ".TABLE_EXAM_SCHEDULE." exam_schedule on exams.id=exam_schedule.exam_id 
	 	INNER JOIN ". TABLE_EXAM_EVALUATION." examEvaluation on exams.id = examEvaluation.exam_id 
	 	WHERE exam_schedule.exam_assessor =".$user_id ." ORDER BY exams.created_date DESC";
        $result = mysql_query($query) or $message = mysql_error();
        
    	 if (mysql_num_rows($result)) {
      		  while ($val = mysql_fetch_assoc($result)){
            $post=array();
            
          			 //Get Classromm name
                	$queryForClassName="SELECT class_name FROM ".TABLE_CLASSROOMS." classrooms INNER JOIN ".TABLE_EXAMS." exams ON classrooms.id=exams.classroom_id WHERE exams.classroom_id= ".$val['classroom_id'];
                	$resultForClassName = mysql_query($queryForClassName) or $message=mysql_error();
                    
                	if(mysql_num_rows($resultForClassName)) {
            		 while ($row = mysql_fetch_row($resultForClassName)) {
            			 $post['classroom_name']=$row[0];
       					 
           			 }
                	}
                	
                	 //Get Subject name
                	$queryForSubjectName="SELECT subjects.subject_name FROM ".TABLE_SUBJECTS ." subjects JOIN ".TABLE_EXAMS." exams ON subjects.id=exams.subject_id WHERE exams.subject_id=".$val['subject_id'];
                	$resultForSubjectName = mysql_query($queryForSubjectName) or $message=mysql_error();
                    
                	if(mysql_num_rows($resultForSubjectName)) {
            		 while ($row = mysql_fetch_row($resultForSubjectName)) {
            			 $post['subject_name']=$row[0]; 
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
           
             $data[]=$post;
            
            $status="success";
            $message="";
        	}
       	}
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
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
        
		
	 	$query="SELECT exam_score.*,s.username as 'username',s.user_profile_pic as 'profile_pic' FROM ".TABLE_STUDENT_EXAM_SCORE." exam_score
	 	INNER JOIN (SELECT id,full_name as 'username',profile_pic as 'user_profile_pic' FROM ".TABLE_USERS." users WHERE id=".$user_id.") s ON s.id=".$user_id."   
	 	WHERE exam_score.exam_id=".$exam_id." AND exam_score.user_id=".$user_id;
	 	
	 	
        $result = mysql_query($query) or $message = mysql_error();
        
    	 if (mysql_num_rows($result)) {
      		  while ($val = mysql_fetch_assoc($result)){
            	$post=array();
            
          			
          			 //Get Classromm name
               		 $queryForClassName="SELECT class_name from ".TABLE_CLASSROOMS." WHERE id IN ( SELECT classroom_id FROM ".TABLE_EXAMS." e JOIN ".TABLE_CLASSROOMS." c ON e.classroom_id=c.id WHERE e.id=".$exam_id.") ";
                	
                	$resultForClassName = mysql_query($queryForClassName) or $message=mysql_error();
                    
                	if(mysql_num_rows($resultForClassName)) {
            		 while ($row = mysql_fetch_row($resultForClassName)) {
            			$post['classroom_name']=$row[0];
       					 
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
           	 
             $data[]=$post;
            
            $status="success";
            $message="";
        	}
       	}
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
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
        
        
        if($role_id=="student")
        {
        
      /*  $selectQuery=" SELECT se.user_id,u.username,u.profile_pic as 'profile_pic',se.marks_obtained as 'exam_score',t.school_name,sub.subject_name FROM `student_academic_info` sai 
		  INNER JOIN classroom_subject cs ON cs.`classroom_id`= sai.`classroom_id` 
		  INNER JOIN exams e ON e.classroom_id=sai.classroom_id
		  INNER JOIN student_exam_score se ON se.exam_id=e.id
		  INNER JOIN (select s.school_name as 'school_name',s.id from schools s JOIN student_academic_info sa on sa.school_id=s.id) t ON t.id=sai.school_id
          INNER JOIN users u ON u.id=se.user_id
          INNER JOIN subjects sub ON sub.id=e.subject_id
          WHERE sai.user_id = ".$user_id." and e.exam_category='ISM_Mock' group by se.user_id";*/
        
        $selectQuery="select user_id,u.full_name,u.profile_pic,mx as 'exam_score',sch.school_name,sub.subject_name from 
        (select max(marks_obtained) as mx,ses.exam_id,ses.user_id,ss2.school_id,ss2.subject_id from student_exam_score ses
		INNER JOIN 
		(select * from (select e.id,e.exam_category,ss1.school_id,ss1.subject_id from exams e
		INNER JOIN 
		(select subject_id,ss.school_id from classroom_subject cs
		INNER JOIN  
		(select classroom_id,school_id from ".TABLE_STUDENT_PROFILE."
		where user_id=".$user_id.") ss on ss.classroom_id = cs.classroom_id) ss1 ON ss1.subject_id = e.subject_id)sx
		where sx.exam_category='ISM_Mock')ss2 ON ss2.id = ses.exam_id
		group by ses.id order by exam_id,ses.marks_obtained desc,user_id)ss3 
		JOIN schools sch ON sch.id=ss3.school_id 
		JOIN subjects sub ON sub.id=ss3.subject_id
		JOIN users u ON u.id=ss3.user_id
		group by exam_id";
		
    	$result = mysql_query($selectQuery) or $message = mysql_error();    
    	 if (mysql_num_rows($result)) {
      		  while ($val = mysql_fetch_assoc($result)){
      		  
      		  $data[]=$val;
            	$status="success";
            	}
            }
            else
            {
            $status="failed";
            }
        
        }
        $response['high_scorers']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
    }
    
    public function tempCreateQuestion($postData)
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

        $answer_choices = validateObject ($postData , 'answer_choices', "");
      
      //Check for question_format
      	if($question_format=="MCQ" || $question_format=="subjective" || $question_format=="text")
      	{
		
        $insertFields="`question_text`,`question_score`, `question_format`, `question_creator_id`, `evaluation_notes`, `solution`, `topic_id`, `subject_id`, `classroom_id`,`book_id`";
        $insertValues="'".$question_text."',".$question_score.",'".$question_format."',".$user_id. ",'".$evaluation_notes."','".$solution ."',".$topic_id.",".$subject_id.",".$classroom_id.",".$book_id;

        $query="INSERT INTO ".TABLE_QUESTIONS."(".$insertFields.") VALUES (".$insertValues.")";
        $result=mysql_query($query) or  $message=mysql_error();
       
    	
       if($result)
       {
      	 $post['question_id']=mysql_insert_id();
       
        if($question_format == "MCQ")
      	{ 	    	
   			if(is_array($answer_choices)){
  
   			 foreach($answer_choices as $row){

  		           $insertChoiceFeilds="`question_id`,`choice_text`,`is_right`";
  	               $insertChoiceValues = "".$post['question_id'].",'".$row->choice_text."',".(int) $row->is_right;
  		 		   
  		 		   $insertQuestionQuery="INSERT INTO " .TABLE_ANSWER_CHOICES."(".$insertChoiceFeilds.") VALUES( ".$insertChoiceValues.")";
  		 		   $resultQuestionQuery =mysql_query($insertQuestionQuery) or $message=mysql_error();
       			 	
 	 			}  
  			}
		 }
		 
		   $status="success";
		}
		else
		{
			$post['question_id']="";
            $status="failed";
		}
		
		
		}
		else
		{
            $status="failed";
            $message="question format is not correct";
            $data=array();
		}
		 
        $data[]=$post;
        $response['question']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }
}


?>
