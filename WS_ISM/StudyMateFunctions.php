<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 23/10/15
 * Time: 9:56 AM
 */
include_once 'ConstantValues.php';
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

        }
    }



    public function acceptRequestFromStudymate ($postData)
    {
        $data=array();
        $response=array();
        $email_id = validateObject ($postData , 'email_id', "");
        $email_id = addslashes($email_id);

        $response['data']=$data;

        return $response;
    }

    public function sendRequestToStudymate ($postData)
    {
        $message='';
        $data=array();
        $response=array();

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

        $users[]=$user_id;
        $query="SELECT * FROM `student_academic_info` WHERE user_id=".$user_id;
        $result=mysql_query($query) or $message=mysql_error();
        if(mysql_num_rows($result))
        {
            $val=mysql_fetch_assoc($result);
            $classroom_id=$val['classroom_id'];
            $course_id=$val['course_id'];
            $school_id=$val['school_id'];
            $querySchool="SELECT * FROM `student_academic_info` WHERE school_id=".$school_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            echo $querySchool;
            if(mysql_num_rows($resultSchool)){
                while($row=mysql_fetch_assoc($resultSchool)){
                    $suggested_studymate_id=$row['user_id'];
                    if(!in_array($suggested_studymate_id,$users)){
                        $post['user_id']=$suggested_studymate_id;
                        $data[]=$post;

                    }
                }
            }
            $querySchool="SELECT * FROM `student_academic_info` WHERE classroom_id=".$classroom_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            echo $querySchool;
            if(mysql_num_rows($resultSchool)){
                while($row=mysql_fetch_assoc($resultSchool)){
                    $suggested_studymate_id=$row['user_id'];
                    if(!in_array($suggested_studymate_id,$users)){
                        $post['user_id']=$suggested_studymate_id;
                        $data[]=$post;

                    }
                }
            }
            $querySchool="SELECT * FROM `student_academic_info` WHERE course_id=".$course_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            echo $querySchool;
            if(mysql_num_rows($resultSchool)){
                while($row=mysql_fetch_assoc($resultSchool)){
                    $suggested_studymate_id=$row['user_id'];
                    if(!in_array($suggested_studymate_id,$users)){
                        $post['user_id']=$suggested_studymate_id;
                        $data[]=$post;

                    }
                }
            }

            //favorite author
            $querySchool="SELECT * FROM `user_favorite_author` WHERE user_id=".$user_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            echo $querySchool;
            if(mysql_num_rows($resultSchool)) {
                $fav_author_id = $row['author_id'];
            }
            //favorite book
            $querySchool="SELECT * FROM `user_favorite_book` WHERE user_id=".$user_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            echo $querySchool;
            if(mysql_num_rows($resultSchool)) {
                $fav_book_id = $row['book_id'];
            }
            //favorite pastime
            $querySchool="SELECT * FROM `user_favorite_pastime` WHERE user_id=".$user_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            echo $querySchool;
            if(mysql_num_rows($resultSchool)) {
                $fav_pastime_id = $row['pastime_id'];
            }
            //favorite movie
            $querySchool="SELECT * FROM `user_favorite_movie` WHERE user_id=".$user_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            echo $querySchool;
            if(mysql_num_rows($resultSchool)) {
                $fav_movie_id = $row['movie_id'];
            }
            //favorite author + favorite book
            $querySchool = "SELECT * FROM `user_favorite_author` author INNER JOIN  user_favorite_book book on author.user_id=book.user_id, book.book_id=".$fav_book_id." , author.author_id=".$fav_author_id;
            $resultSchool = mysql_query($querySchool) or $message = mysql_error();
            echo $querySchool;
            if (mysql_num_rows($resultSchool)) {

                while ($row = mysql_fetch_assoc($resultSchool)) {
                    $suggested_studymate_id = $row['user_id'];
                    if (!in_array($suggested_studymate_id, $users)) {
                        $post['user_id'] = $suggested_studymate_id;
                        $data[] = $post;

                    }
                }
            }
//            $querySchool = "SELECT * FROM `user_favorite_book` WHERE book_id=" . $fav_book_id;
//            $resultSchool = mysql_query($querySchool) or $message = mysql_error();
//            echo $querySchool;
//            if (mysql_num_rows($resultSchool)) {
//
//                while ($row = mysql_fetch_assoc($resultSchool)) {
//                    $suggested_studymate_id = $row['user_id'];
//                    if (!in_array($suggested_studymate_id, $users)) {
//                        $post['user_id'] = $suggested_studymate_id;
//                        $data[] = $post;
//
//                    }
//                }
//            }

          //  movie INNER JOIN user_favorite_pastime passtime on passtime.user.id=movie.user_id

            //favorite author + favorite book
//            $querySchool = "SELECT * FROM `user_favorite_movie` movie,user_favorite_pastime pastime WHERE movie.movie_id=" . $fav_movie_id." and pastime.pastime_id=".$fav_pastime_id;
//            $resultSchool = mysql_query($querySchool) or $message = mysql_error();
//            echo $querySchool;
//            if (mysql_num_rows($resultSchool)) {
//
//                while ($row = mysql_fetch_assoc($resultSchool)) {
//                    $suggested_studymate_id = $row['user_id'];
//                    if (!in_array($suggested_studymate_id, $users)) {
//                        $post['user_id'] = $suggested_studymate_id;
//                        $data[] = $post;
//
//                    }
//                }
//            }

//            $querySchool = "SELECT * FROM `user_favorite_pastime` WHERE pastime_id=" . $fav_pastime_id;
//            $resultSchool = mysql_query($querySchool) or $message = mysql_error();
//            echo $querySchool;
//            if (mysql_num_rows($resultSchool)) {
//
//                while ($row = mysql_fetch_assoc($resultSchool)) {
//                    $suggested_studymate_id = $row['user_id'];
//                    if (!in_array($suggested_studymate_id, $users)) {
//                        $post['user_id'] = $suggested_studymate_id;
//                        $data[] = $post;
//
//                    }
//                }
//            }


        }
        $response['status'] =$status;
        $response['message'] =$message;
        $response['data']=$data;
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
        $users[]=null;
        $queryGetStudyMate="SELECT * from ".TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users on studymates.mate_id=users.id where mate_of=".$user_id." or mate_id=".$user_id;
        $resultGetStudyMate=mysql_query($queryGetStudyMate) or $message=mysql_error();
        if(mysql_num_rows($resultGetStudyMate))
        {

            while ($val = mysql_fetch_assoc($resultGetStudyMate))
            {
                $post=array();
                $studymate_id=null;
                if($user_id!=$val['mate_of'])
                {
                    $studymate_id=$val['mate_of'];
                }
                else if($user_id!=$val['mate_id'])
                {
                    $studymate_id=$val['mate_id'];
                }
                //post['user_id']=$val['mate_id'];
                if(in_array($studymate_id,$users)){

                }
                else{
                    $post['user_id']=$studymate_id;
                    $users[]=$studymate_id;
                    $post['full_name']=$val['username'];
                    $post['profile_pic']=$val['profile_pic'];
                    $data[]=$post;
                }

            }
            $status="success";
            $message="";

        }
        else
        {
            $status="failed";
            $message = DEFAULT_NO_RECORDS;
            $data="";
        }

        $response['data']=$data;
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

        $queryInnerJoin=TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users INNER JOIN ".TABLE_STUDENT_ACADEMIC_INFO." studentAcademicInfo INNER JOIN ".TABLE_SCHOOLS." schools";
        $queryOn="studymates.mate_id=users.id=studentAcademicInfo.user_id and schools.id=studentAcademicInfo.school_id";

        $queryGetStudyMateAllDetail="SELECT * from ".$queryInnerJoin." on ".$queryOn."  where mate_of=".$user_id." or mate_id=".$user_id;
        //echo $queryGetStudyMateAllDetail;
        $resultGetStudyMateAllDetail=mysql_query($queryGetStudyMateAllDetail) or $message=mysql_error();
        if(mysql_num_rows($resultGetStudyMateAllDetail))
        {
            $users[]=null;
            while ($val = mysql_fetch_assoc($resultGetStudyMateAllDetail))
            {
                $post=array();
                $studymate_id=null;
                if($val['mate_id']!=$user_id)
                    $studymate_id=$val['mate_id'];
                else if( $val['mate_of']!=$user_id)
                    $studymate_id=$val['mate_of'];
                if(in_array($studymate_id,$users)){

                }
                else{
                    $post['user_id']=$studymate_id;
                    $users[]=$studymate_id;
                    $post['full_name']=$val['username'];
                    $post['profile_pic']=$val['profile_pic'];
                    $post['is_online']=$val['is_online'];
                    $post['school_name']=$val['school_name'];
                    array_push($data,$post);
                }

            }
            $status="success";
            $message="";

        }
        else
        {
            $status="failed";
            $message = DEFAULT_NO_RECORDS;
            $data="";
        }

        $response['data']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
}
?>