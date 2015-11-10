package com.ism.author.constant;

/**
 * these is the common class for all the api related url.
 */
public class WebConstants {

    private static final String URL_HOST = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";
    public static final String URL_ARTI_HOST = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";
    public static final String URL_RAVI_HOST = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";

    public static final String FEED_MEDIA = "http://192.168.1.162/ISM/WS_ISM/Feeds/";
    public static final String USER_IMAGES = "http://192.168.1.162/ISM/WS_ISM/Images/";


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
    public static final int GETCLASSROOMS = 13;
    public static final int GETSUBJECT = 14;
    public static final int GETTOPICS = 15;
    public static final int CREATEASSIGNMENT = 16;
    public static final int POSTFEED = 17;
    public static final int GETCOURSES = 18;
    public static final int CREATEEXAM = 19;
    public static final int GETQUESTIONBANK = 20;
    public static final int GETALLEXAM = 21;
    public static final int GETEXAMSUBMISSION = 22;
    public static final int SETQUESTIONSFOREXAM = 23;
    public static final int REQUESTSCHOOLINFO = 24;
    public static final int REGISTERUSER = 25;
    public static final int GETEXAMEVALUATIONS = 26;
    public static final int GETEXAMQUESTIONS = 27;
    public static final int GET_ALL_ASSIGNMENTS = 28;


    //	webservice url
    public static final String URL_LOGIN = URL_ARTI_HOST + "AuthenticateUser";
    public static final String URL_FORGOT_PASSWORD = URL_ARTI_HOST + "ForgotPassword";
    public static final String URL_REQUEST_CREDENTIALS = URL_ARTI_HOST + "RequestForCredentials";
    public static final String URL_GET_COUNTRIES = URL_ARTI_HOST + "GetCountries";
    public static final String URL_GET_STATES = URL_ARTI_HOST + "GetStates";
    public static final String URL_GET_CITIES = URL_ARTI_HOST + "GetCities";
    public static final String URL_REQUEST_SCHOOL_INFO = URL_ARTI_HOST + "RequestForSchoolInfoUpdation";
    public static final String URL_REGISTER_USER = URL_ARTI_HOST + "RegisterUser";
    public static final String URL_GETALLFEEDS = URL_ARTI_HOST + "GetAllFeeds";
    public static final String URL_ADDCOMMENT = URL_ARTI_HOST + "AddComment";
    public static final String URL_GETSTUDYMATES = URL_ARTI_HOST + "GetStudymates";
    public static final String URL_TAGFRIENDINFEED = URL_ARTI_HOST + "TagFriendInFeed";
    public static final String URL_GETALLCOMMENTS = URL_ARTI_HOST + "GetAllComments";
    public static final String URL_LIKEFEED = URL_ARTI_HOST + "LikeFeed";
    public static final String URL_GETCLASSROOMS = URL_ARTI_HOST + "GetClassrooms";
    public static final String URL_GETSUBJECT = URL_ARTI_HOST + "GetSubject";
    public static final String URL_GETTOPICS = URL_ARTI_HOST + "GetTopics";
    public static final String URL_CREATEASSIGNMENT = URL_ARTI_HOST + "CreateAssignment";
    public static final String URL_POSTFEED = URL_ARTI_HOST + "PostFeed";
    public static final String URL_GET_COURSES = URL_ARTI_HOST + "GetCourses";
    public static final String URL_CREATE_EXAM = URL_ARTI_HOST + "CreateExam";
    public static final String URL_GETQUESTIONBANK = URL_ARTI_HOST + "GetQuestionBank";
    public static final String URL_GET_ALL_EXAM = URL_ARTI_HOST + "GetAllExams";
    public static final String URL_GET_EXAM_SUBMISSION = URL_ARTI_HOST + "GetExamSubmission";
    public static final String URL_GET_EXAM_EVALUATIONS = URL_ARTI_HOST + "GetExamEvaluation";
    public static final String URL_GET_EXAM_QUESTIONS = URL_ARTI_HOST + "GetExamQuestions";
    public static final String URL_SET_QUESTIONS_FOR_EXAM = URL_ARTI_HOST + "SetQuestionsForExam";
    public static final String URL_GET_ALL_ASSIGNMENTS = URL_ARTI_HOST + "GetAllExams";

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILED = "failed";

    //these are the temparory questionData for testing

    public static final String TEST_USER_ID = "370";
    public static final String TEST_GET_ALL_FEEDS = "141";
    public static final String TEST_GETSTUDYMATES = "167";
    public static final String TEST_FEEDID = "240";
    public static final String TEST_TAGGED_BY = "134";
    public static final String[] tagUserArray = new String[]{"141", "167"};
    public static final String TEST_LIKEUSERID = "141";


}

