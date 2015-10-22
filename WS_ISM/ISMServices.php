<?php
/**
 * Created by PhpStorm.
 * User: c119
 * Date: 03/03/15
 * Time: 4:10 PM
 */

include_once 'config.php';
include_once 'paths.php';
include_once 'ConstantValues.php';
include_once 'HelperFunctions.php';
include_once 'table_vars.php';

$post_body = file_get_contents('php://input');
$post_body = iconv('UTF-8', 'UTF-8//IGNORE', utf8_encode($post_body));
$reqData[] = json_decode($post_body);
$postData = $reqData[0];

$debug = 0;
$logger -> Log($debug, 'POST DATA :', $postData);
$status = "";
$logger -> Log($debug, 'Service :', $_REQUEST['Service']);

switch ($_REQUEST['Service'])
{
    /*********************  Profile Functions ******************************/
    case "RequestForCredentials":
    case "AuthenticateUser":
    case "RegisterUser":
    case "CheckUsernameAvailability":
    case "ForgotPassword":
    {
  	 include_once 'ProfileFunctions.php';
     $profile = new ProfileFunctions();
     $data = $profile -> call_service($_REQUEST['Service'], $postData);
    }
        break;
    case "GetAllFeeds":
    {
         include_once 'SocialFunctions.php';
       	 $profile = new SocialFunctions();
    	 $data = $profile -> call_service($_REQUEST['Service'], $postData);
    }    
        
 break;

	/*********************  TutorialGroup Functions ******************************/
       case "AllocateTutorialGroup":
    {
		include_once 'TutorialGroup.php';
		$profile = new TutorialGroup();
		$data = $profile -> call_service($_REQUEST['Service'], $postData);
    }
        break;

	case "AcceptTutorialGroup":
	{
		include_once 'TutorialGroup.php';
		$profile = new TutorialGroup();
		$data = $profile -> call_service($_REQUEST['Service'], $postData);
	}
		break;

    /*********************  Invalid Option to serve ******************************/
    default:
    {
        $data['data'] = 'No Service Found';
        $data['message'] = $_REQUEST['Service'];
    }
        break;
}

header('Content-type: application/json');

echo json_encode($data);
mysql_close($con);

?>