package com.ism.author.constant;

/**
 * these is the common class for all the api related url and apiCode.
 */
public class WebConstants {


//    public static final String URL_KINJAL_HOST = "http://192.168.1.147/WS_ISM/ISMServices.php?Service=";


    public static final String FEED_MEDIA_KINJAL = "http://192.168.1.147/WS_ISM/Images/feeds/";

    public static final String FEED_MEDIA = "http://192.168.1.202/pg/ISM/WS_ISM/Images/feeds/";
    public static final String HOST_IMAGES = "http://192.168.1.202/pg/ISM/WS_ISM/";                                        // for books

    public static final String USER_IMAGES_TEST = "http://192.168.1.147/WS_ISM/Images/users_images/";

    public static final String HOST_202 = "http://192.168.1.202/pg/ISM/";      // pg
    public static final String URL_KINJAL_HOST = HOST_202 + "WS_ISM/ISMServices.php?Service=";

    public static final String URL_HOST_202 = HOST_202 + "WS_ISM/ISMServices.php?Service=";

    //	webservice constants for apiCode.

    public static final int LOGIN = 1;

    public static final int FORGOTPASSWORD = 2;

    public static final int REQUESTCREDENTIALS = 3;

    public static final int GETCOUNTRIES = 4;

    public static final int GETSTATES = 5;

    public static final int GETCITIES = 6;

    public static final int GETALLFEEDS = 7;

    public static final int GETALLCOMMENTS = 8;

    public static final int ADDCOMMENT = 9;

    public static final int GETSTUDYMATES = 10;

    public static final int TAGFRIENDINFEED = 11;

    public static final int LIKEFEED = 12;

    public static final int GETALLCLASSROOMS = 13;

    public static final int GETALLSUBJECT = 14;

    public static final int GETTOPICS = 15;

    public static final int CREATEASSIGNMENT = 16;

    public static final int POSTFEED = 17;

    public static final int GETALLCOURSES = 18;

    public static final int CREATEEXAM = 19;

    public static final int GETQUESTIONBANK = 20;

    public static final int GETASSIGNMENTBYBOOK = 21;

    public static final int GETEXAMSUBMISSION = 22;

    public static final int SETQUESTIONSFOREXAM = 23;

    public static final int REQUESTSCHOOLINFO = 24;

    public static final int REGISTERUSER = 25;

    public static final int GETEXAMEVALUATIONS = 26;

    public static final int GETEXAMQUESTIONS = 27;

    public static final int GETALLEXAMS = 28;

    public static final int GET_ALL_BADGES_COUNT = 29;

    public static final int CREATEQUESTION = 30;

    public static final int GETALLHASHTAG = 31;

    public static final int SETHASHTAG = 32;

    public static final int TEMPCREATEQUESTION = 33;

    public static final int GETBOOKSBYAUTHOR = 34;

    public static final int UPLOADMEDIAFORQUESTION = 35;

    public static final int UPLOAD_FEED_MEDIA = 37;

    public static final int UPLOADPROFILEIMAGES = 38;

    public static final int GETADMINCONFIG = 39;

    public static final int REFRESH_TOKEN = 40;


    public static final int GET_HIGH_SCORERS = 42;

    public static final int GETMYFOLLOWERS = 43;

    public static final int GET_AUTHOR_BOOK_ASSIGNMENT = 44;

    //user settings preferences

    public static final int MANAGE_GENERAL_SETTINGS = 71;

    public static final int BLOCK_USER = 74;

    public static final int UNBLOCK_USER = 74;

    public static final int BLOCKED_USER = 75;

    public static final int GENERAL_SETTING_PREFERENCES = 51;

    public static final int GET_USER_PREFERENCES = 52;

    public static final int GET_NOTIFICATION = 53;

    public static final int GET_MESSAGES = 54;

    public static final int GET_STUDYMATE_REQUEST = 55;

    public static final int RESPOND_TO_REQUEST = 56;

    public static final int UPDATE_READ_STATUS = 57;

    public static final int GET_MY_FEEDS = 58;

    public static final int GET_ABOUT_ME = 59;

    public static final int GET_BOOKS_FOR_USER = 60;

    public static final int MANAGE_FAVOURITES = 61;

    public static final int MANAGE_BOOK_LIBRARY = 62;

    public static final int GET_TRENDING_QUESTIONS = 63;

    public static final int GET_PAST_TRENDING_QUESTION = 64;

    public static final int SUBMIT_TRENDING_ANSWER = 65;

    public static final int GET_ALL_BOOKS = 66;

    public static final int GET_TRENDING_QUESTION_DETAIL = 67;

    /*webservice url*/

    public static final String URL_GETADMINCONFIG = URL_KINJAL_HOST + "GetAdminConfig";

    public static final String URL_REFRESHTOKEN = URL_KINJAL_HOST + "RefreshToken";

    public static final String URL_LOGIN = URL_KINJAL_HOST + "AuthenticateUser";

    public static final String URL_FORGOTPASSWORD = URL_KINJAL_HOST + "ForgotPassword";

    public static final String URL_REQUESTCREDENTIALS = URL_KINJAL_HOST + "RequestForCredentials";

    public static final String URL_GETCOUNTRIES = URL_KINJAL_HOST + "GetCountries";

    public static final String URL_GETSTATES = URL_KINJAL_HOST + "GetStates";

    public static final String URL_GETCITIES = URL_KINJAL_HOST + "GetCities";

    public static final String URL_REQUESTSCHOOLINFO = URL_KINJAL_HOST + "RequestForSchoolInfoUpdation";

    public static final String URL_REGISTERUSER = URL_KINJAL_HOST + "RegisterUser";

    public static final String URL_GETALLFEEDS = URL_KINJAL_HOST + "GetAllFeeds";

    public static final String URL_ADDCOMMENT = URL_KINJAL_HOST + "AddComment";

    public static final String URL_GETSTUDYMATES = URL_KINJAL_HOST + "GetStudymates";

    public static final String URL_TAGFRIENDINFEED = URL_KINJAL_HOST + "TagFriendInFeed";

    public static final String URL_GETALLCOMMENTS = URL_KINJAL_HOST + "GetAllComments";

    public static final String URL_LIKEFEED = URL_KINJAL_HOST + "LikeFeed";

    public static final String URL_GETALLCLASSROOMS = URL_KINJAL_HOST + "GetAllClassrooms";

    public static final String URL_GETALLSUBJECT = URL_KINJAL_HOST + "GetAllSubjects";

    public static final String URL_GETTOPICS = URL_KINJAL_HOST + "GetTopics";

    public static final String URL_CREATEASSIGNMENT = URL_KINJAL_HOST + "CreateAssignment";

    public static final String URL_POSTFEED = URL_KINJAL_HOST + "PostFeed";

    public static final String URL_GETALLCOURSES = URL_KINJAL_HOST + "GetAllCourses";

    public static final String URL_GETBOOKSBYAUTHOR = URL_KINJAL_HOST + "GetBooksByAuthors";

    public static final String URL_GETTRENDINGQUESTIONS = URL_KINJAL_HOST + "GetTrendingQuestions";

    public static final String URL_GET_PAST_TRENDING_QUESTION = URL_KINJAL_HOST + "GetPastTrendingQuestion";

    public static final String URL_UPLOADMEDIAFORQUESTION = URL_KINJAL_HOST + "UploadMediaForQuestion";

    public static final String URL_UPLOADPROFILEIMAGES = URL_KINJAL_HOST + "UploadProfileImages";

    public static final String URL_UPLOAD_FEED_MEDIA = URL_KINJAL_HOST + "UploadMedia";

    public static final String URL_CREATEEXAM = URL_KINJAL_HOST + "CreateExam";

    public static final String URL_GETQUESTIONBANK = URL_KINJAL_HOST + "GetQuestionBank";

    public static final String URL_GET_ASSIGNMENT_BY_BOOK = URL_KINJAL_HOST + "GetAssignmentByBook";

    public static final String URL_HASHTAG = URL_KINJAL_HOST + "Hashtag";

    public static final String URL_GETEXAMSUBMISSION = URL_KINJAL_HOST + "GetExamSubmission";

    public static final String URL_GETEXAMEVALUATIONS = URL_KINJAL_HOST + "GetExamEvaluation";

    public static final String URL_GETEXAMQUESTIONS = URL_KINJAL_HOST + "GetExamQuestions";

    public static final String URL_SETQUESTIONSFOREXAM = URL_KINJAL_HOST + "SetQuestionsForExam";

    public static final String URL_GETALLEXAMS = URL_KINJAL_HOST + "GetAllExams";

    public static final String URL_USER_PREFERENCES = URL_KINJAL_HOST + "GetUserPreferences";

    public static final String URL_GENERAL_SETTING_PREFERENCES = URL_KINJAL_HOST + "GetAllPreferences";

    public static final String URL_MANAGE_GENERAL_SETTING = URL_KINJAL_HOST + "ManageGeneralSettings";

    public static final String URL_GET_NOTIFICATION = URL_KINJAL_HOST + "GetNotification";

    public static final String URL_GET_MESSAGES = URL_KINJAL_HOST + "GetMessages";

    public static final String URL_GET_STUDYMATE_REQUEST = URL_KINJAL_HOST + "GetStudymateRequest";

    public static final String URL_BLOCK_USER = URL_KINJAL_HOST + "BlockUser";

    public static final String URL_CREATEQUESTION = URL_KINJAL_HOST + "CreateQuestion";

    public static final String URL_TEMPCREATEQUESTION = URL_KINJAL_HOST + "TempCreateQuestion";

    public static final String URL_GETALLHASHTAG = URL_KINJAL_HOST + "GetAllHashtag";

    public static final String URL_RESPOND_TO_REQUEST = URL_KINJAL_HOST + "AcceptRequestFromStudymate";

    public static final String URL_UPDATE_READ_STATUS = URL_KINJAL_HOST + "UpdateReadStatus"; // studymate_request, messages, notification

    public static final String URL_GET_HIGH_SCORERS = URL_KINJAL_HOST + "GetHighScorers";

    public static final String URL_GET_ALL_BADGES_COUNT = URL_KINJAL_HOST + "GetAllBadgeCount";

    public static final String URL_GETMYFOLLOWERS = URL_KINJAL_HOST + "GetMyFollowers";

    public static final String URL_GET_AUTHOR_BOOK_ASSIGNMENT = URL_KINJAL_HOST + "GetAuthorBookAssignment";

    public static final String URL_GET_TRENDING_QUESTION_DETAIL = URL_KINJAL_HOST + "GetTrendingQuestionDetail";

    /*status for apisuccess ans apifailed*/

    public static final String URL_SUBMIT_TRENDING_ANSWER = URL_KINJAL_HOST + "SubmitTrendingAnswer";

    public static final String URL_GET_MY_FEEDS = URL_KINJAL_HOST + "GetMyFeeds";

    public static final String URL_GET_BLOCKED_USER = URL_KINJAL_HOST + "GetBlockedUser";

    public static final String URL_GET_ABOUT_ME = URL_KINJAL_HOST + "GetAboutMe";

    public static final String URL_GET_BOOKS_FOR_USER = URL_KINJAL_HOST + "GetBooksForUser";

    public static final String URL_MANAGE_FAVOURITES = URL_KINJAL_HOST + "ManageFavorite";

    public static final String URL_MANAGE_BOOK_LIBRARY = URL_KINJAL_HOST + "ManageBookLibrary";

    public static final String URL_GET_ALL_BOOKS = URL_KINJAL_HOST + "GetAllBooks";


    /*status for apisuccess ans apifailed*/

    public static final String SUCCESS = "success";

    public static final String FAILED = "failed";

    public static final String TEST_GETSTUDYMATES = "499";

    /**
     * Parameter values
     */

    public static final String STUDYMATE_REQUEST = "studymate_request";

    public static final String MESSAGES = "messages";

    public static final String NOTIFICATION = "notification";

    /*Image Url Constants*/

    public static final String USER_IMAGES = "http://192.168.1.202/pg/ISM/WS_ISM/images/users_images/";
    public static final String QUESTION_IMAGES = "http://192.168.1.147/WS_ISM/Images/questions_images/";
    public static final String BOOKS_IMAGES = "http://192.168.1.147/WS_ISM/";

    /**
     * Parameter value Variables
     */

    public static String SECRET_KEY;

    public static String ACCESS_KEY;

    public static final String NO_USERNAME = "nousername";
}