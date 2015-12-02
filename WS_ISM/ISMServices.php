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
include_once 'SecurityFunctions.php';

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
    case "GetStudentAcademicInfo":
    case "RequestForSchoolInfoUpdation":
    case "GetWalletSummary":
    case "GenerateVoucher":
    case "GetAboutMe":
    case "EditAboutMe":
    case "GetBooksForUser":
    case "GetMyActivity":
    case "BlockUser":
    case "GetPastimeForUser":
    case "GetRoleModelForUser":
    case "GetMoviesForUser":
    case "ManageFavorite":
    case "FollowUser":
    case "GetStudentProfile":
    case "ManageBookLibrary":
    case "GetBlockedUser":
    case "GetBooksForAuthor":
    {
        include_once 'ProfileFunctions.php';
        $profile = new ProfileFunctions();
        $data = $profile -> call_service($_REQUEST['Service'], $postData);
    }
        break;

    /*************************** StudyMate Functions ******************************/

    case "GetStudentAcademicInfo":
    case "GetStudymatesWithDetails":
    case "GetSuggestedStudymates":
    case "SendRequestToStudymate":
    case "AcceptRequestFromStudymate":
    case "GetStudymates":
    case "GetStudymateRequest":
    {
        include_once 'StudyMateFunctions.php';
        $studyMate = new StudyMateFunctions();
        $data = $studyMate -> call_service($_REQUEST['Service'], $postData);
    }
        break;

    /*************************** Teacher Functions ******************************/

    case "PostForClasswall":
    case "GetAllClasswallPost":
    case "GetMyStudents":
    case "GetAllSubjectsByClass":
    case "GetAllNotes":
    case "SubmitNotes":
    case "UploadMediaNotes":
    case "CreateAssignment":
    {
        include_once 'TeacherFunctions.php';
        $teacher = new TeacherFunctions();
        $data = $teacher -> call_service($_REQUEST['Service'], $postData);
    }
        break;

    /*************************** Exam Functions ******************************/
    case "CreateExam":
    case "SetQuestionsForExam":
    case "GetQuestionBank":
    case "GetCourses":
    case "GetSubject":
    case "GetTopics":
    case "GetClassrooms":
    case "GetAllExams":
    case "GetExamQuestions":
    case "GetExamSubmission":
    case "GetExamEvaluation":
    case "CreateQuestion":
    case "UploadMediaForQuestion":
    case "GetAllResults":
    case "GetStudentResultsByExam":
    case "GetHighScorers":
    case "TempCreateQuestion":
    case "GetBooks":
    {
        include_once 'ExamFunctions.php';
        $exam = new ExamFunctions();
        $data = $exam -> call_service($_REQUEST['Service'], $postData);
    }
        break;
    /*********************  Social Functions ******************************/
    case "GetAllFeeds":
    case "PostFeed":
    case "TagFriendInFeed":
    case "UploadMedia":
    case "AddComment":
    case "LikeFeed":
    case "GetAllComments":
    case "GetMyFeeds":
    case "AddQuestionToFavorite":
    case "TempGetMyFeeds":
    case "GetSecurirty":
    case "GetConfigData":
    case "EncryptionData":
    case "DecryptionData":
    case "Hashtag":
    case "GetAllHashtag":
    {
        include_once 'SocialFunctions.php';
        $profile = new SocialFunctions();
        $data = $profile -> call_service($_REQUEST['Service'], $postData);
    }

        break;

    /*********************  TutorialGroup Functions  ******************************/

    case "AllocateTutorialGroup":
    case "AcceptTutorialGroup":
    {
        include_once 'TutorialGroup.php';
        $profile = new TutorialGroup();
        $data = $profile -> call_service($_REQUEST['Service'], $postData);
    }
        break;

    /*********************  Data Functions  ***************************************/

    case "GetCountries":
    case "GetStates":
    case "GetCities":
    {
        include_once 'Data.php';
        $profile = new TutorialGroup();
        $data = $profile -> call_service($_REQUEST['Service'], $postData);
    }
        break;

    /*********************  Notification Functions  ***************************************/

    case "GetAllNotices":
    case "GetAllBadgeCount":
    case "GetNotification":
    case "GetMessages":
    case "GetWalletData":
    case "UpdateReadStatus":
    case "ManageGeneralSettings":
    case "GetAllPreferences":
    case "GetUserPreferences":
    {
        include_once 'NotificationFunctions.php';
        $notification = new NotificationFunctions();
        $data = $notification -> call_service($_REQUEST['Service'], $postData);
    }
        break;


    /*********************  Invalid Option to serve  ******************************/
    default:
    {
        $data['data'] = 'No Service Found';
        $data['message'] = $_REQUEST['Service'];
    }
        break;
}

header('Content-type: application/json');

echo json_encode($data);
mysqli_close($con);

?>