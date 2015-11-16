<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 09/03/15
 * Time: 12:20 PM
 */
include_once 'ConstantValues.php';
error_reporting(0);

class NotificationFunctions
{

    function __construct()
    {

    }

    public function call_service($service, $postData)
    {
        switch($service)
        {
            case "GetAllNotices":
            {
                return $this->getAllNotices($postData);//done
            }
                break;
                
             case "GetAllBadgeCount":
            {
                return $this->getAllBadgeCount($postData);//done
            }
                break;
                
            case "GetNotification":
            {
                return $this->getNotification($postData);//in progress
            }
                break;
                
           case "GetMessages":
            {
                return $this->getMessages($postData);//done
            }
                break;
                
            case "GetWalletData":
            {
                return $this->getWalletData($postData);//in progress
            }
                break;
                
            case "UpdateReadStatus":
            {
                return $this->updateReadStatus($postData);//in progress
            }
                break;
           
           case "ManageGeneralSettings":
           {
                return $this->manageGeneralSettings($postData);//in progress
           }
                break;
                
           case "GetAllPreferences":
           {
                return $this->getAllPreferences();//in progress
           }
                break;
                
        	case "GetUserPreferences":
        	{
                return $this->getUserPreferences($postData);//in progress
           }
                break;
        }
    }
    /*
    * getAllNotices
    */

    public function getAllNotices ($postData)
    {
        $data = array();
        $response = array();
		
		$role_id = validateObject($postData, 'role_id', "");
        $role_id = addslashes($role_id);
		
		if($role_id == NULL)
			$role_id="All";	
			
	 	$query="SELECT * FROM ".TABLE_NOTICEBOARD." WHERE notice_for = '$role_id' order by created_date";
        $result = mysql_query($query) or $message = mysql_error();
        
    	 if (mysql_num_rows($result)) {
      		  while ($val = mysql_fetch_assoc($result)){
           
         	 $post=array();
          	 $post['notice_id'] = $val['id'];
           	 $post['notice_title'] = $val['notice_title'];
           	 $post['notice'] = $val['notice'];
           	 $post['posted_by'] = $val['posted_by'];
             $post['posted_on'] = $val['created_date'];
             $data[]=$post;
            
            $status="success";
            $message="Request accepted";
        	}
       	}
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;
    }

 	/*
 	* getAllBadgeCount
    * This service will be used to fetch all the badge counts for the badges value. This service will return counters of messages received, studymate request received, notification received.
    */

  	public function getAllBadgeCount ($postData)
    {
        $data = array();
        $response = array();

       $user_id = validateObject($postData, 'user_id', "");
       $user_id = addslashes($user_id);
        
        //For message_count 
        
        $queryMessage="SELECT ifnull(count(*),'0') as 'message_count'FROM ".TABLE_MESSAGE_RECEIVER. " WHERE receiver_id = ". $user_id." and is_read=0";
        $resultMessage = mysql_query($queryMessage) or $message = mysql_error();
        $messgeCount=mysql_fetch_row($resultMessage);
        $data['message_count']=$messgeCount[0];
        
        //For request_count
        
       	$queryRequest="SELECT ifnull(count(*),'0') as 'request_count' FROM ".TABLE_STUDYMATES_REQUEST." WHERE request_to_mate_id = ". $user_id ." and is_seen = 0";
        $resultRequest = mysql_query($queryRequest) or $message = mysql_error();
        $requestCount=mysql_fetch_row($resultRequest);
        $data['request_count']= $requestCount[0];
    	
    	//For notification_count 
    	
    	$queryNotification="SELECT ifnull(count(*),'0') as 'notification_count' FROM ".TABLE_USER_NOTIFICATION." WHERE notification_to = ".$user_id." and is_read = 0";
    	$resultNotification= mysql_query($queryNotification);
    	$notificationCount=mysql_fetch_row($resultNotification);
    	$data['notification_count']=$notificationCount[0];
    	  
    	
        $response['data'][]=$data;
        $response['status']="success";
        $response['message']="";
        return $response;
    }
    
    /*
 	* getNotification
    */
    public function getNotification ($postData)
    { 
    	$data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
       

	$query="SELECT userNotification.*,users.full_name as 't1',users1.full_name as 't2',users1.profile_pic as 'profile_pic' FROM ".TABLE_USER_NOTIFICATION." userNotification 

		INNER JOIN ".TABLE_USERS." users on users.id=userNotification.notification_to 

		INNER JOIN ".TABLE_USERS." users1 on users1.id=userNotification.notification_from 

		WHERE userNotification.notification_to = ".$user_id;

       $result = mysql_query($query) or $message = mysql_error();
        
    	 if (mysql_num_rows($result)) {
      		 while ($val = mysql_fetch_assoc($result)){
           
         	 $post=array();
         	 
          	 $post['notification_to_id'] = $val['notification_to'];
           	 $post['notification_to_name'] = $val['t1']; 
           	 $post['notification_from_id'] = $val['notification_from'];
           	 $post['notification_from_name']=$val['t2'];
           	 $post['notification_from_profile_pic']=$val['profile_pic'];       
             $post['notification_id'] = $val['notification_id'];
             $post['notification_text'] = $val['notification_text'];
             $post['navigate_to'] = $val['navigate_to'];
             $post['is_read'] = $val['is_read'];
             $post['notification_date'] = $val['created_date'];
             $data[]=$post;
            
            $status="success";
            $message="";
        	}
       	}
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;
    }
    
    
    /*
     * getMessages
     *
     */
     public function getMessages ($postData)
    { 
    	$data = array();
        $response = array();

        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
         
        $innerJoinQuery="messageReceiver.message_id,messages.message_text,messages.sender_id,users.username as 'sender_name',users.profile_pic as 'sender_profile_pic',messages.status,messages.created_date as 'sent_on' ";
	 	$queryMessage="SELECT ".$innerJoinQuery."FROM ".TABLE_MESSAGE_RECEIVER." messageReceiver INNER JOIN ".TABLE_MESSAGES." messages ON messageReceiver.message_id = messages.id 
	 	INNER JOIN " .TABLE_USERS." users on users.id=messages.sender_id
	 	WHERE messages.sender_id =".$user_id;
    
        $resultMessage = mysql_query($queryMessage) or $message = mysql_error();
        
    	 if (mysql_num_rows($resultMessage)) {
      		 while ($val = mysql_fetch_assoc($resultMessage)){
             $data[]=$val;
            
            $status="success";
            $message="Request accepted";
        	}
       	}
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        return $response;
    }

	/*
	*getWalletData
	*/
	public function getWalletData($postData)
	{
		$data = array();
        $response = array();
		
		$user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
		
		$studymate_id = validateObject($postData, 'studymate_id', "");
        $studymate_id = addslashes($studymate_id);
	
        
        $query="SELECT * FROM ".TABLE_STUDYMATES_REQUEST."  WHERE request_from_mate_id = ".$user_id ." and request_to_mate_id= ". $studymate_id;
    
        $result=mysql_query($query) or $message=mysql_error();
        
        if (mysql_num_rows($result)>0) {
      		
      		$queryToUpdateStatus="UPDATE ".TABLE_STUDYMATES_REQUEST." SET status= 1 WHERE request_from_mate_id = ".$user_id ." and request_to_mate_id= ". $studymate_id;
      		//echo $queryToUpdateStatus;
        	$resultToUpdateStatus=mysql_query($queryToUpdateStatus) or $message=mysql_error();
        	
            $status="success";
            $message="Request accepted";
        	
       	}
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS;
        }
        
        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
	}
	
	/*
 	* updateReadStatus
    */
	public function updateReadStatus ($postData)
	{
		$data = array();
        $response = array();

	    
        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
        
        $record_id = validateObject($postData, 'record_id', "");
       // $record_id = addslashes($record_id); 
        
        $read_category = validateObject($postData, 'read_category', "");
        $read_category = addslashes($read_category);
        
        
        if($read_category == "studymate_request"){
        	$table= TABLE_STUDYMATES_REQUEST;
        	$is_read="is_seen";
        }
        else if($read_category == "notification"){
        	$table= TABLE_USER_NOTIFICATION;
        	$is_read="is_read";
        }
        else if($read_category == "messages"){
        	$table= TABLE_MESSAGE_RECEIVER;	
        	$is_read="is_read";
        }
       
        
        if($record_id!=null)
        {
            foreach($record_id as $feed_id) {
            
                $queryCheckFeed = "SELECT id FROM " . $table . " WHERE id =" . $feed_id;
                //echo $queryCheckFeed."\n";
                $resultCheckFeed = mysql_query($queryCheckFeed) or $message = mysql_error();
                
                if (mysql_num_rows($resultCheckFeed) > 0) {
                    $val = mysql_fetch_assoc($resultCheckFeed);
                  	 $queryUpdate="UPDATE " .$table ." SET ".$is_read." = 1 WHERE id = ".$feed_id;
            		$resultUpdate = mysql_query($queryUpdate) or $errorMsg = mysql_error();
            		//echo $queryUpdate;
            		
                     $status = "success";
       				 $message = "Request accepted";
                }
                else
                {
                	 $status="failed";
            		 $message=DEFAULT_NO_RECORDS;
                }
            }
            
        }
        else
        {
        	$status="failed";
            $message=DEFAULT_NO_RECORDS;
        }
        

        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
	}
	
	/*
 	* manageGeneralSettings
    */
	
	public function manageGeneralSettings($postData)
	{
		$data = array();
        $response = array();

		$preferences = validateObject ($postData , 'preferences', "");
		       
       if($preferences !=null){
       
        if(is_array($preferences)){
  
   			 foreach($preferences as $row){
   			 
   			 $selQuery="SELECT * FROM ".TABLE_USER_ACCOUNT_PREFERENCE." WHERE preference_id=".$row->key_id ." AND user_id =".$row->user_id;
       		$resultQuery=mysql_query($selQuery) or $errorMsg = mysql_error();
   			 
   			 if(mysql_num_rows($resultQuery) > 0) {
       		 
  		 				
  		 	 $queryUpdate="UPDATE " .TABLE_USER_ACCOUNT_PREFERENCE ." SET preference_value='". $row->preference_value ."' WHERE user_id=".$row->user_id." AND preference_id = ".$row->key_id;
        	 $resultQuery=mysql_query($queryUpdate) or $errorMsg = mysql_error();
        	//echo $queryUpdate;
        	
        	if( $resultQuery)
        	{
        		$status="success";
        		$message="Updated succesfully";
    		 }
    		 else
    		 {
    		 	$status="failed";
    		 	$message="";
    		 }
        }
       	else
       	 {
        	$insertFields="`preference_id`,`preference_value`,`user_id`";
        	$insertValues="".$row->key_id.",'". $row->preference_value."',".$row->user_id."";
        	
           	$queryInsert="INSERT INTO ".TABLE_USER_ACCOUNT_PREFERENCE."(".$insertFields.") VALUES (".$insertValues.")";
            $resultInsert=mysql_query($queryInsert) or  $message=mysql_error();
           
           if( $resultQuery)
        	{
        		$status="success";
        		$message="Added succesfully";
        	}
        	else
    		 {
    		 	$status="failed";
    		 	$message="";
    		 }
       	 }		 
   		}
   	  }
     }
        else
        {
        	$status="failed";
            $message=DEFAULT_NO_RECORDS;
        }
        

        $response['data']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
	}
    
    /*
 	* getAllPreferences
    */
    
    public function getAllPreferences()
    {
    	$data = array();
        $response = array();
        $post=array();
       
        $selQuery="SELECT id,preference_key,display_value,default_value FROM ".TABLE_PREFERENCES ." WHERE parent_id=1";
       
        $resultQuery=mysql_query($selQuery) or $errorMsg = mysql_error();
        if (mysql_num_rows($resultQuery)) {
      		 while ($val = mysql_fetch_assoc($resultQuery)){
             $data[]=$val;
        	}
        	$status="success";
          // $post['Preferences']['Privacy_Setting']=$data;
          $post['Privacy_Setting']=$data;
       }
       
       $data = array();
        $selQuery1="SELECT id,preference_key,display_value,default_value FROM ".TABLE_PREFERENCES ." WHERE parent_id=3";
       
        $resultQuery1=mysql_query($selQuery1) or $errorMsg = mysql_error();
        if (mysql_num_rows($resultQuery1)) {
      		 while ($val = mysql_fetch_assoc($resultQuery1)){
             $data[]=$val;
        	}
        	$status="success";
        	//$post['Preferences']['SMS_Alert']=$data;
        	 $post['SMS_Alert']=$data;
       	
       
       }
       
       $data = array();
        $selQuery3="SELECT id,preference_key,display_value,default_value FROM ".TABLE_PREFERENCES ." WHERE parent_id=4";
       
        $resultQuery3=mysql_query($selQuery3) or $errorMsg = mysql_error();
        if (mysql_num_rows($resultQuery3)) {
      		 while ($val = mysql_fetch_assoc($resultQuery3)){
             $data[]=$val;
        	}
        	$status="success";
        	//$post['Preferences']['Block_User']=$data;
        	 $post['Block_User']=$data;
       	
       		$message="";
       
       }
       
       $data = array();
       $selQuery4="SELECT id,preference_key,display_value,default_value FROM ".TABLE_PREFERENCES ." WHERE parent_id=5";
       
        $resultQuery4=mysql_query($selQuery4) or $errorMsg = mysql_error();
        if (mysql_num_rows($resultQuery4)) {
      		 while ($val = mysql_fetch_assoc($resultQuery4)){
             $data[]=$val;
        	}
        	$status="success";
            //$post['Preferences']['Notification']=$data;
            $post['Notification']=$data;
       		$message="";
       }
       
        
        $response['data'][]=$post;
        $response['status']=$status;
        $response['message']=$message;
		return $response;
    }
    
    
    /*
 	* getUserPreferences
    */
    public function getUserPreferences($postData)
    {
    	$data = array();
        $response = array();
        
        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);
       
      
      $selQuery="SELECT preferences.id,preferences1.preference_key as 'parent_key',preferences.preference_key,preferences.display_value,userAccountPreferences.preference_value 
      FROM ". TABLE_PREFERENCES." preferences  
      INNER JOIN ".TABLE_USER_ACCOUNT_PREFERENCE." userAccountPreferences ON preferences.id = userAccountPreferences.preference_id 
      INNER JOIN preferences preferences1 ON preferences1.id=preferences.parent_id
      WHERE userAccountPreferences.user_id =". $user_id;
       
       $resultQuery=mysql_query($selQuery) or $errorMsg = mysql_error();
        
        if (mysql_num_rows($resultQuery)) {
       	 $post = array();
      		 while ($val = mysql_fetch_assoc($resultQuery)){
              $post[]=$val;
        	}
        	
        	$status="success";
            $message="";
       	}
        else{
            $status="failed";
            $message=DEFAULT_NO_RECORDS . $errorMsg;
            
        }
        
        $response['data']=$post;
        $response['status']=$status;
        $response['message']=$message;
		return $response;
    }
    
}
?>