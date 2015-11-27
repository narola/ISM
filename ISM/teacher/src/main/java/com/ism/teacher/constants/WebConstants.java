package com.ism.teacher.constants;

/**
 * Created by c161 on 05/11/15.
 */
public class WebConstants {


    public static final String API_STATUS_SUCCESS = "success";
    public static final String API_STATUS_FAIL = "fail";

    public static final String FEED_MEDIA = "http://192.168.1.162/ISM/WS_ISM/Feeds/";
    public static final String USER_IMAGES = "http://192.168.1.162/ISM/WS_ISM/Images/";

    public static final String EXAM_ID_11_SUBJECTIVE = "11";
    public static final String EXAM_ID_3_OBJECTIVE = "3";
    public static final String EXAM_ID_9_OBJECTIVE = "9";
    public static final String STUDENT_ID_202_OBJECCTIVE = "202";
    public static final String USER_ID_370 = "370";
    public static final String USER_ID_340 = "340";
    public static final String TEST_GETSTUDYMATES = "167";


    public static final String TEACHER_ROLE_ID = "3";
    public static final String STUDENT_ID_1 = "1";

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
    public static final int GET_CLASSROOMS = 7;
    public static final int GET_SUBJECT = 8;
    public static final int GET_TOPICS = 9;
    public static final int CREATE_ASSIGNMENT = 10;
    public static final int GET_COURSES = 11;
    public static final int CREATE_EXAM = 12;
    public static final int GET_QUESTION_BANK = 13;
    public static final int POSTFEED = 14;
    public static final int SET_QUESTIONS_FOR_EXAM = 15;
    public static final int FORGOT_PASSWORD = 16;
    public static final int REQUEST_CREDENTIALS = 17;


    public static final int GET_COUNTRIES = 18;
    public static final int GET_STATES = 19;
    public static final int GET_CITIES = 20;


    public static final int GET_ALL_ASSIGNMENTS = 21;
    public static final int GET_ALL_EXAM_SUBMISSION = 22;
    public static final int GET_EXAM_QUESTIONS = 23;
    public static final int GET_EXAM_EVALUATIONS = 24;
    public static final int GET_MY_STUDENTS = 25;


    /**
     * URLs
     */
    private static final String URL_HOST_75 = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";    // RAVI
    private static final String URL_HOST_161 = "http://192.168.1.161/ISM/WS_ISM/ISMServices.php?Service=";  // KRUNAL
    private static final String URL_HOST_162 = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";  // ARTI
    private static final String URL_KINJAL_HOST = "http://192.168.1.147/WS_ISM/ISMServices.php?Service=";      // KINJAL

    public static final String URL_LOGIN = URL_KINJAL_HOST + "AuthenticateUser";
    public static final String URL_GET_ALL_FEEDS = URL_KINJAL_HOST + "GetAllFeeds";
    public static final String URL_GET_ALL_COMMENTS = URL_KINJAL_HOST + "GetAllComments";
    public static final String URL_GET_STUDYMATES = URL_KINJAL_HOST + "GetStudymates";
    public static final String URL_ADD_COMMENT = URL_KINJAL_HOST + "AddComment";
    public static final String URL_TAG_FRIEND_IN_FEED = URL_KINJAL_HOST + "TagFriendInFeed";
    public static final String URL_LIKE_FEED = URL_KINJAL_HOST + "LikeFeed";
    public static final String URL_GETCLASSROOMS = URL_KINJAL_HOST + "GetClassrooms";
    public static final String URL_GETSUBJECT = URL_KINJAL_HOST + "GetSubject";
    public static final String URL_GETTOPICS = URL_KINJAL_HOST + "GetTopics";
    public static final String URL_CREATEASSIGNMENT = URL_KINJAL_HOST + "CreateAssignment";
    public static final String URL_GET_COURSES = URL_KINJAL_HOST + "GetCourses";
    public static final String URL_CREATE_EXAM = URL_KINJAL_HOST + "CreateExam";
    public static final String URL_GET_QUESTION_BANK = URL_KINJAL_HOST + "GetQuestionBank";
    public static final String URL_POSTFEED = URL_KINJAL_HOST + "PostFeed";
    public static final String URL_SET_QUESTIONS_FOR_EXAM = URL_KINJAL_HOST + "SetQuestionsForExam";
    public static final String URL_FORGOT_PASSWORD = URL_KINJAL_HOST + "ForgotPassword";
    public static final String URL_REQUEST_CREDENTIALS = URL_KINJAL_HOST + "RequestForCredentials";


    //used for assignment
    public static final String URL_GET_ALL_ASSIGNMENTS = URL_KINJAL_HOST + "GetAllExams";
    public static final String URL_GET_ALL_EXAM_SUBMISSION = URL_HOST_162 + "GetExamSubmission";
    public static final String URL_GET_EXAM_QUESTIONS = URL_KINJAL_HOST + "GetExamQuestions";
    public static final String URL_GET_EXAM_EVALUATIONS = URL_KINJAL_HOST + "GetExamEvaluation";
    public static final String URL_GET_MY_STUDENTS = URL_KINJAL_HOST + "GetMyStudents";


    public static final String URL_GET_CITIES = URL_KINJAL_HOST + "GetCities";
    public static final String URL_GET_STATES = URL_KINJAL_HOST + "GetStates";
    public static final String URL_GET_COUNTRIES = URL_KINJAL_HOST + "GetCountries";

}
