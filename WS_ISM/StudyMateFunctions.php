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
        
        $user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);
        
        $studymate_id = validateObject ($postData , 'studymate_id', "");
        $studymate_id = addslashes($studymate_id);
		
		$query="select * from ".TABLE_STUDYMATES_REQUEST." where request_from_mate_id=".$user_id. " and request_to_mate_id=". $studymate_id;
		$result=mysql_query($query) or $message=mysql_error();
        if(mysql_num_rows($result)>0)
        {
        	$updateQuery="UPDATE ".TABLE_STUDYMATES_REQUEST." SET status=1 where request_from_mate_id=".$user_id. " and request_to_mate_id=". $studymate_id;
        	$updateResult=mysql_query($updateQuery) or $message=mysql_error();
        	
        	if($updateResult)
        	{
        	 	$message="Request accepted";
            	$status="success";
            }
        }
        else
        {
        	$message="";
            $status="failed";
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
		
		$user_id = validateObject ($postData , 'user_id', "");
        $user_id = addslashes($user_id);
        
        $studymate_id = validateObject ($postData , 'studymate_id', "");
        $studymate_id = addslashes($studymate_id);
		
		$query="SELECT * FROM ".TABLE_STUDYMATES_REQUEST." where request_from_mate_id=".$user_id. " and request_to_mate_id=". $studymate_id;
		$result=mysql_query($query) or $message=mysql_error();
		
        if(mysql_num_rows($result)==0)
        {
        	$inserFields="`request_from_mate_id`,`request_to_mate_id`.`status`";
        	$inserValues="".$user_id.",".$studymate_id.",";
        	
			$insertQuery="INSERT INTO ".TABLE_STUDYMATES_REQUEST."(".$inserFields.") VALUES( ".$inserValues.")";
			$insertResult=mysql_query($insertQuery) or $message=mysql_error();
			
			if($insertResult)
			{
				$message="Request sent.";
            	$status="success";
			}
			else
			{
				$message="";
           		$status="failed";
			}
		}
		else
		{
			$message="Request already sent.";
            $status="failed";
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

        $users[]=$user_id;
        $query="SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE user_id=".$user_id;
        $result=mysql_query($query) or $message=mysql_error();
        if(mysql_num_rows($result))
        {
            $val=mysql_fetch_assoc($result);
            $classroom_id=$val['classroom_id'];
            $course_id=$val['course_id'];
            $school_id=$val['school_id'];
            $querySchool="SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE school_id=".$school_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            if(mysql_num_rows($resultSchool)){
                while($row=mysql_fetch_assoc($resultSchool)){
                    $suggested_studymate_id=$row['user_id'];
                    if(!in_array($suggested_studymate_id,$users)){
                        $post['user_id']=$suggested_studymate_id;
                        $data[]=$post;

                    }
                }
            }
            $querySchool="SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE classroom_id=".$classroom_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
           // echo $querySchool;
            if(mysql_num_rows($resultSchool)){
                while($row=mysql_fetch_assoc($resultSchool)){
                    $suggested_studymate_id=$row['user_id'];
                    if(!in_array($suggested_studymate_id,$users)){
                        $post['user_id']=$suggested_studymate_id;
                        $data[]=$post;

                    }
                }
            }
            $querySchool="SELECT * FROM ".TABLE_STUDENT_PROFILE." WHERE course_id=".$course_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            //echo $querySchool;
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
            $querySchool="SELECT author_id FROM `user_favorite_author` WHERE user_id=".$user_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            $author_id=mysql_fetch_row($resultSchool);
            //echo "auth=".$fav_author_id = $author_id[0];
            
            //favorite book
            $querySchool="SELECT book_id FROM `user_favorite_book` WHERE user_id=".$user_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            $book_id=mysql_fetch_row($resultSchool);
            //echo "book=".$fav_book_id = $book_id[0];
            
            //favorite pastime
            $querySchool="SELECT pastime_id FROM `user_favorite_pastime` WHERE user_id=".$user_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
          	$pastime_id=mysql_fetch_row($resultSchool);
            //echo "past=".$fav_pastime_id = $pastime_id[0];
           
            //favorite movie
            $querySchool="SELECT movie_id FROM `user_favorite_movie` WHERE user_id=".$user_id;
            $resultSchool=mysql_query($querySchool) or $message=mysql_error();
            $movie_id=mysql_fetch_row($resultSchool);
            //echo "mov=".$fav_movie_id = $movie_id[0];
            
            //favorite author + favorite book
            if($fav_book_id != NULL && $fav_author_id){
            $querySchool = "SELECT * FROM `user_favorite_author` author INNER JOIN  `user_favorite_book` book on author.user_id=book.user_id, book.book_id=".$fav_book_id." , author.author_id=".$fav_author_id;
            $resultSchool = mysql_query($querySchool) or $message = mysql_error();
            //echo $querySchool;
            if (mysql_num_rows($resultSchool)>0) {

                while ($row = mysql_fetch_assoc($resultSchool)) {
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
             if($fav_movie_id != NULL && $fav_pastime_id!= NULL){
           $querySchool = "SELECT * FROM `user_favorite_movie` movie,user_favorite_pastime pastime WHERE movie.movie_id=" . $fav_movie_id." and pastime.pastime_id=".$fav_pastime_id;
            $resultSchool = mysql_query($querySchool) or $message = mysql_error();
            echo $querySchool;
            if (mysql_num_rows($resultSchool)) {

                while ($row = mysql_fetch_assoc($resultSchool)) {
                    $suggested_studymate_id2 = $row['user_id'];
                    if (!in_array($suggested_studymate_id, $users)) {
                        $post['user_id'] = $suggested_studymate_id2;
                        $data[] = $post;

                    }
                }
            }
		}
		
		//Final step
		 if($suggested_studymate_id1 != NULL && $suggested_studymate_id2 != NULL){
            $querySchool = "SELECT users.id,users,full_name.users.profile_pic,studymateRequest.status FROM ".TABLE_STUDYMATES_REQUEST." studymateRequest 
            JOIN ".TABLE_USERS."users ON users.id=studymateRequest.request_to_mate_id 
            WHERE studymateRequest.request_from_mate_id=" . $suggested_studymate_id1." AND studymateRequest.request_from_mate_id=" . $suggested_studymate_id2.
            " AND studymateRequest.request_to_mate_id=".$user_id;
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
		}

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
        	
		$queryGetStudymateRequest="SELECT users.full_name as 'request_from_name',users.profile_pic as 'requester_profile',studymatesReuest.is_seen,studymatesReuest.status,
		studymatesReuest.created_date,studymatesReuest.request_from_mate_id,studymatesReuest.id
		 FROM ".TABLE_STUDYMATES_REQUEST." studymatesReuest
		 
		 INNER JOIN ".TABLE_USERS." users on users.id = studymatesReuest.request_from_mate_id
		 
		 WHERE studymatesReuest.request_to_mate_id = ".$user_id ;
		
		
		
		$resultGetStudymateRequest=mysql_query($queryGetStudymateRequest) or $message=mysql_error();
		
        if(mysql_num_rows($resultGetStudymateRequest))
        {
            while ($val = mysql_fetch_assoc($resultGetStudymateRequest)){
                 
                 $post['record_id']=$val['id'];
                 $post['request_from_id']=$val['request_from_mate_id'];
                 $post['request_from_name']=$val['request_from_name'];
       			 $post['requester_profile']=$val['requester_profile'];
                
                	
                 //Get Requester Scholl name and Course name
                	$querySchollName="SELECT schools.school_name,courses.course_name FROM ". TABLE_STUDENT_ACADEMIC_INFO ." studentAcademicInfo 
					INNER JOIN ". TABLE_SCHOOLS ." schools ON schools.id= studentAcademicInfo.school_id 
					INNER JOIN ". TABLE_COURSES." courses ON courses.id= studentAcademicInfo.course_id
					WHERE studentAcademicInfo.user_id = ".$val['request_from_mate_id'];
					
                    $resultSchollName=mysql_query($querySchollName) or $message=mysql_error();
                    
                    if(mysql_num_rows($resultSchollName)) {
            		 while ($row = mysql_fetch_row($resultSchollName)) {
       				 	 $post['requester_school_name']=$row[0];
                 		 $post['requester_course_name']=$row[1];
           			 }
                    
           		 }
           		 
                 $post['request_date']=$val['created_date'];
                 $post['is_seen']=$val['is_seen'];
                 $post['status']=$val['status'];
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

        $response['studymate_request']=$data;
        $response['message'] = $message;
        $response['status'] = $status;

        return $response;
     }
}
?>