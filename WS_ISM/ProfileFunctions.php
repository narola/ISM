<?php
/**
 * Created by PhpStorm.
 * User: c119
 * Date: 09/03/15
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
            case "AddProfile":
            {
                return $this->addProfile($postData);
            }
                break;
            case "AuthenticateUser":
            {
            	return $this->authenticateUser($_POST['username'],$_POST['password']);
            }
            break;
    	}
	}
    public function addProfile ($requestData)
    {
        $status = 2;
        $errorMsg ='';

        $profile_fname = validateObject ($requestData , 'profile_fname', "");
        $profile_fname = addslashes($profile_fname);

        $profile_lname = validateObject ($requestData , 'profile_lname', "");
        $profile_lname = addslashes($profile_lname);

        $profile_status = validateObject ($requestData , 'profile_status', "1");
        $profile_added_by_user = validateObject ($requestData , 'profile_added_by_user', "0");
        $profile_image = validateObject ($requestData , 'profile_image', "");
        $profile_image_name = validateObject ($requestData , 'profile_image_name', "");
        $profile_age = validateObject ($requestData , 'profile_age', "0");

//        $profile_dob = validateObject ($requestData , 'profile_dob', getDefaultDate());

        $profile_dob = validateObject ($requestData , 'profile_dob', "");


        //Image Saving
        $profile_image_upload_dir = PROFILE_IMAGES . $profile_image_name;
        $profile_image_link = $profile_image_upload_dir;
        file_put_contents('..' . $profile_image_upload_dir, base64_decode($profile_image));

        $insertFields;
        $valuesFields;

        if (strlen($profile_dob) > 0)
        {
            $insertFields = "group_id, profile_fname , profile_lname, profile_status, profile_image, profile_age, profile_dob ";
            $valuesFields = " '1' ,'".$profile_fname."','".$profile_lname."','$profile_status','".$profile_image_link."','".$profile_age."', '$profile_dob' " ;
        }
        else
        {
            $insertFields = "group_id, profile_fname , profile_lname, profile_status, profile_image, profile_age ";
            $valuesFields = " '1' ,'".$profile_fname."','".$profile_lname."','$profile_status','".$profile_image_link."','".$profile_age."' " ;
        }

        $query = "Insert into ".TABLE_PROFILES ." (".$insertFields.") values(".$valuesFields.")";
        $res = mysql_query($query) or $errorMsg =  mysql_error();

        if($res)
        {
			$status = 1;
        }
        else
        {
            $status = 2;
        }

        if ($status > 1)
        {
            $data['status'] = ($status > 1) ? 'failed' : 'success';
            $data['message'] = $errorMsg;
            return $data;
        }
        else
        {
            return $new_profile_with_data;
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