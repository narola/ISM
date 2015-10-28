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
                return $this->getStudymates($postData);
            }
                break;
            case "GetStudymatesWithDetails":
            {
                return $this->getStudymatesWithDetails($postData);
            }
                break;

            case "GetSuggestedStudymates":
            {
                return $this->getSuggestedStudymates($postData);
            }
                break;

            case "SendRequestToStudymate":
            {
                return $this->sendRequestToStudymate($postData);
            }
                break;

            case "AcceptRequestFromStudymate":
            {
                return $this->acceptRequestFromStudymate($postData);
            }
                break;

        }
    }

    public function uploadMedia($postData)
    {

        $data=array();

        return $data;

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
        $post=array();
        $response=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $query="SELECT * FROM `student_academic_info` WHERE user_id=370";
        $result=mysql_query($query) or $message=mysql_error();
        if(mysql_num_rows($result))
        {
            $val=mysql_fetch_assoc($result);
            $classroom_id=$val['classroom_id'];
            $course_id=$val['course_id'];
            $school_id=$val['school_id'];
            $querySchool="SELECT $user_id FROM `student_academic_info` WHERE user_id!=370 and school_id=".$school_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            if(mysql_num_rows($resultSchool)){
              //  while($row=m)
            }


        }
        $response['message'] ="Sent successfully";
        $response['data']=$post;
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

        $queryGetStudyMate="SELECT * from ".TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users on studymates.mate_id=users.id where mate_of=".$user_id." or mate_id=".$user_id;
        $resultGetStudyMate=mysql_query($queryGetStudyMate) or $message=mysql_error();
        if(mysql_num_rows($resultGetStudyMate))
        {

            while ($val = mysql_fetch_assoc($resultGetStudyMate))
            {
                $post=array();
                if($user_id!=$val['mate_of'])
                {
                    $post['user_id']=$val['mate_of'];
                }
                else if($user_id!=$val['mate_id'])
                {
                    $post['user_id']=$val['mate_id'];
                }
                //post['user_id']=$val['mate_id'];
                $post['full_name']=$val['username'];
                $post['profile_pic']=$val['profile_pic'];
                $data[]=$post;
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
        $response['data']=array();

        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);

        $queryInnerJoin=TABLE_STUDYMATES." studymates INNER JOIN ".TABLE_USERS." users INNER JOIN ".TABLE_STUDENT_ACADEMIC_INFO." studentAcademicInfo INNER JOIN ".TABLE_SCHOOLS." schools";
        $queryOn="studymates.mate_id=users.id=studentAcademicInfo.user_id and schools.id=studentAcademicInfo.school_id";

        $queryGetStudyMateAllDetail="SELECT * from ".$queryInnerJoin." on ".$queryOn."  where mate_of=".$user_id." or mate_id=".$user_id;
        //echo $queryGetStudyMateAllDetail;
        $resultGetStudyMateAllDetail=mysql_query($queryGetStudyMateAllDetail) or $message=mysql_error();
        if(mysql_num_rows($resultGetStudyMateAllDetail))
        {

            while ($val = mysql_fetch_assoc($resultGetStudyMateAllDetail))
            {
                $post=array();
                if($val['mate_id']!=$user_id)
                    $post['user_id+']=$val['mate_id'];
                else if( $val['mate_of']!=$user_id)
                $post['user_id']=$val['mate_id'];
                $post['full_name']=$val['username'];
                $post['profile_pic']=$val['profile_pic'];
                $post['is_online']=$val['is_online'];
                $post['school_name']=$val['school_name'];

                array_push($data,$post);
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

        array_push($response['data'],$data);
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
    }
}
?>