package com.ism.teacher.constants;

/**
 * Created by c161 on 05/11/15.
 */
public class WebConstants {


    public static final String API_STATUS_SUCCESS = "success";
    public static final String API_STATUS_FAIL = "fail";

    public static final String FEED_MEDIA = "http://192.168.1.162/ISM/WS_ISM/Feeds/";
    public static final String USER_IMAGES = "http://192.168.1.162/ISM/WS_ISM/Images/";


    /**
     * Api IDs
     */
    //	Webservice flags
    public static final int LOGIN = 0;
    public static final int GET_ALL_FEEDS = 1;
    public static final int ADD_COMMENTS = 2;
    public static final int GET_ALL_COMMENTS = 3;
    public static final int GET_STUDYMATES = 4;
    public static final int TAG_FRIEND_IN_FEED = 5;
    public static final int LIKE_FEED = 6;
    public static final int GETCLASSROOMS = 7;
    public static final int GETSUBJECT = 8;
    public static final int GETTOPICS = 9;
    public static final int CREATEASSIGNMENT = 10;
    public static final int GETCOURSES = 11;
    public static final int CREATEEXAM = 12;
    public static final int GETQUESTIONBANK = 13;
    public static final int POSTFEED = 14;
    public static final int SET_QUESTIONS_FOR_EXAM = 15;
    public static final int FORGOT_PASSWORD = 16;
    public static final int REQUEST_CREDENTIALS = 17;


    public static final int GET_COUNTRIES = 18;
    public static final int GET_STATES = 19;
    public static final int GET_CITIES = 20;


    public static final int GET_ALL_ASSIGNMENTS = 21;
    public static final int GET_ALL_EXAM_SUBMISSION = 22;
    public static final int GETEXAMQUESTIONS = 23;
    public static final int GETEXAMEVALUATIONS = 24;


    /**
     * URLs
     */
    private static final String URL_HOST_75 = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";    // RAVI
    private static final String URL_HOST_161 = "http://192.168.1.161/ISM/WS_ISM/ISMServices.php?Service=";  // KRUNAL
    private static final String URL_HOST_162 = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";  // ARTI
    private static final String URL_HOST_147 = "http://192.168.1.147/WS_ISM/ISMServices.php?Service=";      // KINJAL

    public static final String URL_LOGIN = URL_HOST_162 + "AuthenticateUser";
    public static final String URL_GET_ALL_FEEDS = URL_HOST_162 + "GetAllFeeds";
    public static final String URL_GET_ALL_COMMENTS = URL_HOST_162 + "GetAllComments";
    public static final String URL_GET_STUDYMATES = URL_HOST_162 + "GetStudymates";
    public static final String URL_ADD_COMMENT = URL_HOST_162 + "AddComment";
    public static final String URL_TAG_FRIEND_IN_FEED = URL_HOST_162 + "TagFriendInFeed";
    public static final String URL_LIKE_FEED = URL_HOST_162 + "LikeFeed";
    public static final String URL_GETCLASSROOMS = URL_HOST_162 + "GetClassrooms";
    public static final String URL_GETSUBJECT = URL_HOST_162 + "GetSubject";
    public static final String URL_GETTOPICS = URL_HOST_162 + "GetTopics";
    public static final String URL_CREATEASSIGNMENT = URL_HOST_162 + "CreateAssignment";
    public static final String URL_GET_COURSES = URL_HOST_162 + "GetCourses";
    public static final String URL_CREATE_EXAM = URL_HOST_162 + "CreateExam";
    public static final String URL_GETQUESTIONBANK = URL_HOST_162 + "GetQuestionBank";
    public static final String URL_POSTFEED = URL_HOST_162 + "PostFeed";
    public static final String URL_SET_QUESTIONS_FOR_EXAM = URL_HOST_162 + "SetQuestionsForExam";
    public static final String URL_FORGOT_PASSWORD = URL_HOST_162 + "ForgotPassword";
    public static final String URL_REQUEST_CREDENTIALS = URL_HOST_162 + "RequestForCredentials";


    //used for assignment
    public static final String URL_GET_ALL_ASSIGNMENTS = URL_HOST_162 + "GetAllExams";
    public static final String URL_GET_ALL_EXAM_SUBMISSION = URL_HOST_162 + "GetExamSubmission";
    public static final String URL_GET_EXAM_QUESTIONS = URL_HOST_162 + "GetExamQuestions";
    public static final String URL_GET_EXAM_EVALUATIONS = URL_HOST_162 + "GetExamEvaluation";


    public static final String URL_GET_CITIES = URL_HOST_161 + "GetCities";
    public static final String URL_GET_STATES = URL_HOST_161 + "GetStates";
    public static final String URL_GET_COUNTRIES = URL_HOST_161 + "GetCountries";

}
