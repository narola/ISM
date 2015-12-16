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
                return $this->getNotification($postData);//done
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
                return $this->updateReadStatus($postData);//done
            }
                break;
           
           case "ManageGeneralSettings":
           {
                return $this->manageGeneralSettings($postData);//done
           }
                break;
                
           case "GetAllPreferences":
           {
                return $this->getAllPreferences($postData);//done
           }
                break;
                
        	case "GetUserPreferences":
        	{
                return $this->getUserPreferences($postData);//done
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {


            if ($role_id == NULL)
                $role_id = "All";

            $query = "SELECT * FROM ".TABLE_NOTICEBOARD." WHERE notice_for = '$role_id' AND is_delete=0 order by created_date";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($result)) {
                while ($val = mysqli_fetch_assoc($result)) {

                    $post = array();
                    $post['notice_id'] = $val['id'];
                    $post['notice_title'] = $val['notice_title'];
                    $post['notice'] = $val['notice'];
                    $post['posted_by'] = $val['posted_by'];
                    $post['posted_on'] = $val['created_date'];
                    $data[] = $post;

                    $status = SUCCESS;
                    $message = "Request accepted";
                }
            } else {
                $status =  SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $response['notices']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            //For message_count

            //$queryMessage = "SELECT ifnull(count(*),'0') as 'message_count'FROM ".TABLE_MESSAGE_RECEIVER. " WHERE receiver_id = ". $user_id." and is_read=0 and is_delete=0 ";
            $queryMessage = "SELECT count(id) FROM ".TABLE_MESSAGE_RECEIVER. " WHERE receiver_id = ". $user_id." and is_read=0 and is_delete=0 ";
            $resultMessage = mysqli_query($GLOBALS['con'], $queryMessage) or $message = mysqli_error($GLOBALS['con']);
            $messageCount = mysqli_fetch_row($resultMessage);
            $data['message_count'] = $messageCount[0];

            //For request_count

            //$queryRequest = "SELECT ifnull(count(*),'0') as 'request_count' FROM ".TABLE_STUDYMATES." WHERE mate_of = ". $user_id ." and is_request_seen = 0 and status='pending' and is_delete=0 ";
            $queryRequest = "SELECT count(id) FROM ".TABLE_STUDYMATES." WHERE mate_of = ". $user_id ." and is_request_seen = 0 and status='pending' and is_delete=0 ";
            $resultRequest = mysqli_query($GLOBALS['con'], $queryRequest) or $message = mysqli_error($GLOBALS['con']);
            $requestCount = mysqli_fetch_row($resultRequest);
            $data['request_count'] = $requestCount[0];

            //For notification_count
            //ifnull(count(*),'0') as 'notification_count'
            $queryNotification = "SELECT count(id) FROM ".TABLE_USER_NOTIFICATION." WHERE notification_to = ".$user_id." and is_read = 0 and is_delete=0 ";
            $resultNotification = mysqli_query($GLOBALS['con'], $queryNotification);
            $notificationCount = mysqli_fetch_row($resultNotification);
            $data['notification_count'] = $notificationCount[0];

            $status= SUCCESS;
            $message = "";
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $response['badges'][]=$data;
        $response['status']=$status;
        $response['message']=$message;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT userNotification.*,users.full_name as 't1',users1.full_name as 't2',users1.profile_pic as 'profile_pic' FROM ".TABLE_USER_NOTIFICATION." userNotification

		INNER JOIN ".TABLE_USERS." users on users.id=userNotification.notification_to

		INNER JOIN ".TABLE_USERS." users1 on users1.id=userNotification.notification_from

		WHERE userNotification.notification_to = ".$user_id." AND userNotification.is_delete=0 ";

            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($result)) {
                while ($val = mysqli_fetch_assoc($result)) {

                    $post = array();

                    $post['record_id'] = $val['id'];
                    $post['notification_to_id'] = $val['notification_to'];
                    $post['notification_to_name'] = $val['t1'];
                    $post['notification_from_id'] = $val['notification_from'];
                    $post['notification_from_name'] = $val['t2'];
                    $post['notification_from_profile_pic'] = $val['profile_pic'];
                    $post['notification_text'] = $val['notification_text'];
                    $post['navigate_to'] = $val['navigate_to'];
                    $post['is_read'] = $val['is_read'];
                    $post['notification_date'] = $val['created_date'];
                    $data[] = $post;

                    $status =  SUCCESS;
                    $message = "";
                }
            } else {
                $status =  SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['notification']=$data;
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

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);


        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {


            $innerJoinQuery="messageReceiver.id as 'record_id',messageReceiver.message_id,messages.message_text,messages.sender_id,users.username as 'sender_name',
        users.profile_pic as 'sender_profile_pic',messages.status,messages.created_date as 'sent_on',messageReceiver.is_read ";

            $queryMessage="SELECT ".$innerJoinQuery." FROM " .TABLE_MESSAGE_RECEIVER. " messageReceiver INNER JOIN ".TABLE_MESSAGES." messages
	 	ON messageReceiver.message_id = messages.id
	 	INNER JOIN " .TABLE_USERS." users on users.id=messages.sender_id
	 	WHERE messageReceiver.receiver_id =".$user_id ." AND messageReceiver.is_delete=0 AND messages.is_delete=0";

            //WHERE messages.sender_id =".$user_id;
            $resultMessage = mysqli_query($GLOBALS['con'], $queryMessage) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultMessage)) {
                while ($val = mysqli_fetch_assoc($resultMessage)) {
                    $data[] = $val;

                    $status = "success";
                    $message = REQUEST_ACCEPTED;
                }
            } else {
                $status =  SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['messages']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
            $query = "SELECT * FROM ".TABLE_STUDYMATES."  WHERE mate_of = ".$user_id ." and mate_id= ". $studymate_id." and is_delete=0";

            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($result) > 0) {

                $queryToUpdateStatus = "UPDATE ".TABLE_STUDYMATES." SET status= 1  WHERE mate_of = ".$user_id ." and mate_id= ". $studymate_id." and is_delete=0";
                //echo $queryToUpdateStatus;
                $resultToUpdateStatus = mysqli_query($GLOBALS['con'], $queryToUpdateStatus) or $message = mysqli_error($GLOBALS['con']);

                $status =  SUCCESS;
                $message = REQUEST_ACCEPTED;

            } else {
                $status =  SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['wallet_info']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($read_category == "studymate_request") {
                $table = TABLE_STUDYMATES;
                $is_read = " is_request_seen = 1,status = 'pending'";
                $condition=" mate_of= ".$user_id;
            } else if ($read_category == "notification") {
                $table = TABLE_USER_NOTIFICATION;
                $is_read = " is_read = 1";
                $condition=" notification_to= ".$user_id;
            } else if ($read_category == "messages") {
                $table = TABLE_MESSAGE_RECEIVER;
                $is_read = " is_read = 1";
                $condition=" receiver_id= ".$user_id;
            }


            if ($record_id != null) {
                foreach ($record_id as $feed_id) {

                    $queryCheckFeed = "SELECT id FROM " . $table . " WHERE id =" . $feed_id." AND ".$condition;
                    //echo $queryCheckFeed."\n";
                    $resultCheckFeed = mysqli_query($GLOBALS['con'], $queryCheckFeed) or $message = mysqli_error($GLOBALS['con']);

                    if (mysqli_num_rows($resultCheckFeed) > 0) {
                        $val = mysqli_fetch_assoc($resultCheckFeed);
                        $queryUpdate = "UPDATE " .$table ." SET ".$is_read." WHERE id =".$feed_id." AND ".$condition;
                        $resultUpdate = mysqli_query($GLOBALS['con'], $queryUpdate) or $message = mysqli_error($GLOBALS['con']);
                        //echo $queryUpdate;

                        $status =  SUCCESS;
                        $message = REQUEST_ACCEPTED;
                    } else {
                        $status =  SUCCESS;
                        $message = DEFAULT_NO_RECORDS;
                    }
                }

            } else {
                $status =  SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['read_status']=$data;
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

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            if ($preferences != null) {

                if (is_array($preferences)) {

                    foreach ($preferences as $row) {

                        $selQuery = "SELECT * FROM ".TABLE_USER_ACCOUNT_PREFERENCE." WHERE preference_id=".$row->key_id ." AND user_id =".$row->user_id ." and is_delete=0";
                        $resultQuery = mysqli_query($GLOBALS['con'], $selQuery) or $message = mysqli_error($GLOBALS['con']);

                        if (mysqli_num_rows($resultQuery) > 0) {


                            $queryUpdate = "UPDATE " . TABLE_USER_ACCOUNT_PREFERENCE . " SET preference_value='" . $row->preference_value . "' WHERE user_id=" . $row->user_id . " AND preference_id = " . $row->key_id;
                            $resultQuery = mysqli_query($GLOBALS['con'], $queryUpdate) or $message = mysqli_error($GLOBALS['con']);
                            //echo $queryUpdate;

                            if ($resultQuery) {
                                $status =  SUCCESS;
                                $message = SUCCESSFULLY_UPDATED;
                            } else {
                                $status = FAILED;
                                $message = "";
                            }
                        } else {
                            $insertFields = "`preference_id`,`preference_value`,`user_id`";
                            $insertValues = "" . $row->key_id . ",'" . $row->preference_value . "'," . $row->user_id . "";

                            $queryInsert = "INSERT INTO " . TABLE_USER_ACCOUNT_PREFERENCE . "(" . $insertFields . ") VALUES (" . $insertValues . ")";
                            $resultInsert = mysqli_query($GLOBALS['con'], $queryInsert) or $message = mysqli_error($GLOBALS['con']);

                            if ($resultQuery) {
                                $status =  SUCCESS;
                                $message = SUCCESSFULLY_ADDED;
                            } else {
                                $status =FAILED;
                                $message = "";
                            }
                        }
                    }
                }
            } else {
                $status =  SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }

        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['general_settings']=$data;
        $response['status']=$status;
        $response['message']=$message;
        
        return $response;
	}
    
    /*
 	* getAllPreferences
    */
    
    public function getAllPreferences($postData)
    {
    	$data = array();
        $response = array();
        $post=array();

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $selQuery = "SELECT id,preference_key,display_value,default_value FROM ".TABLE_PREFERENCES ." WHERE parent_id=1 AND is_delete=0";

            $resultQuery = mysqli_query($GLOBALS['con'], $selQuery) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultQuery)) {
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    $data[] = $val;
                }
                $status = "success";
                // $post['Preferences']['Privacy_Setting']=$data;
                $post['Privacy_Setting'] = $data;
            }

            $data = array();
            $selQuery1 = "SELECT id,preference_key,display_value,default_value FROM ".TABLE_PREFERENCES ." WHERE parent_id=3 AND is_delete=0";

            $resultQuery1 = mysqli_query($GLOBALS['con'], $selQuery1) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultQuery1)) {
                while ($val = mysqli_fetch_assoc($resultQuery1)) {
                    $data[] = $val;
                }
                $status = "success";
                //$post['Preferences']['SMS_Alert']=$data;
                $post['SMS_Alert'] = $data;


            }

            $data = array();
            $selQuery3 = "SELECT id,preference_key,display_value,default_value FROM ".TABLE_PREFERENCES ." WHERE parent_id=4 AND is_delete=0";

            $resultQuery3 = mysqli_query($GLOBALS['con'], $selQuery3) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultQuery3)) {
                while ($val = mysqli_fetch_assoc($resultQuery3)) {
                    $data[] = $val;
                }
                $status = "success";
                //$post['Preferences']['Block_User']=$data;
                $post['Block_User'] = $data;

                $message = "";

            }

            $data = array();
            $selQuery4 = "SELECT id,preference_key,display_value,default_value FROM ".TABLE_PREFERENCES ." WHERE parent_id=5 AND is_delete=0";

            $resultQuery4 = mysqli_query($GLOBALS['con'], $selQuery4) or $message = mysqli_error($GLOBALS['con']);
            if (mysqli_num_rows($resultQuery4)) {
                while ($val = mysqli_fetch_assoc($resultQuery4)) {
                    $data[] = $val;
                }
                $status = "success";
                //$post['Preferences']['Notification']=$data;
                $post['Notification'] = $data;

            }
            $message=REQUEST_ACCEPTED;
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }

        $response['preference'][]=$post;
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
        $post=array();
        
        $user_id = validateObject($postData, 'user_id', "");
        $user_id = addslashes($user_id);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $selQuery = "SELECT preferences.id,preferences1.preference_key as 'parent_key',preferences.preference_key,preferences.display_value,userAccountPreferences.preference_value
      FROM ". TABLE_PREFERENCES." preferences
      INNER JOIN ".TABLE_USER_ACCOUNT_PREFERENCE." userAccountPreferences ON preferences.id = userAccountPreferences.preference_id
      INNER JOIN preferences preferences1 ON preferences1.id=preferences.parent_id
      WHERE userAccountPreferences.user_id =". $user_id." AND userAccountPreferences.is_delete=0";

            $resultQuery = mysqli_query($GLOBALS['con'], $selQuery) or $message = mysqli_error($GLOBALS['con']);

            if (mysqli_num_rows($resultQuery)) {
                $post = array();
                while ($val = mysqli_fetch_assoc($resultQuery)) {
                    $post[] = $val;
                }

                $status = SUCCESS;
                $message =REQUEST_ACCEPTED;
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
        $response['user_preference']=$post;
        $response['status']=$status;
        $response['message']=$message;
		return $response;
    }


    
}
?>