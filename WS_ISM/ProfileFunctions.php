<?php
/**
 * Created by PhpStorm.
 * User: c162
 * Date: 09/18/15
 * Time: 12:20 PM
 */
class ProfileFunctions
{

    function __construct()
    {
 		
    }

    public function call_service($service, $postData)
    {
        switch($service)
        {

            case "AuthenticateUser":
            {
            	return $this->authenticateUser($_POST['username'],$_POST['password']);
            }
            break;
    	}
	}
    public function authenticateUser($userName,$password)
   	{
   	$post=array();
   		//$encryptedPassword=encryptPassword($passowrd);
   		$queryAuthUser="select username,password from ".TABLE_AUTO_GENERATED_CREDENTIAL." where username='".$userName."'";
   		$resultAuthUser=mysql_query($queryAuthUser) or $errorMsg=mysql_error();
   		if(mysql_num_rows($resultAuthUser))
   		{
   			$encryptedPassword='';
   			while ($val = mysql_fetch_assoc($resultAuthUser))
            {
                $username=$val['username'];
                $encryptedPassword=$val['password'];
            }
            if($encryptedPassword== $password)
            {
            	$status=1;
            	$errorMsg = USER_IS_AVAILABLE;
            }else
            {
            	$status=2;
            	$errorMsg = USER_IS_NOT_AVAILABLE;
            }
            
   		}
   		else
   		{
   			$status=2;
            $errorMsg = USER_IS_NOT_AVAILABLE;
    	}
    	// AND password='".$encryptedPassword."'
    	$post['username']=$username;
    	//$data['DB_password']=$encryptedPassword;
   		//$data['MyPassword with encryption']=$password;
   		$data['data']=$post;
		$data['message'] = $errorMsg;
      	$data['status'] = $status;
      
        return $data;	
   	}
}
?>