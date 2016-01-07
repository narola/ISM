package com.ism.teacher.constants;

/**
 * Hold static constants required when dealing with web services
 */
public class WebConstants {

    /**
     * Parameter value Variables
     */
    public static String SECRET_KEY;
    public static String ACCESS_KEY;
    public static final String NO_USERNAME = "nousername";

    //Host urls
    //  public static final String URL_HOST = "http://192.168.1.147/WS_ISM/ISMServices.php?Service=";      // KINJAL
    public static final String URL_HOST = "http://192.168.1.202/pg/ISM/WS_ISM/ISMServices.php?Service=";      // PG


    public static final String Image_url = "http://192.168.1.147/WS_ISM/Images/questions_images/";
//    public static final String Image_url = "http://192.168.1.202/pg/ISM/WS_ISM/images/users_images/";


    public static final String URL_UPLOADPROFILEIMAGES = URL_HOST + "UploadProfileImages";

    public static final String FEED_MEDIA = "http://192.168.1.147/WS_ISM/Feeds/";
    //    public static final String USER_IMAGES = "http://192.168.1.147/WS_ISM/Images/users_images/";
    public static final String USER_IMAGES = "http://192.168.1.202/pg/ISM/WS_ISM/images/users_images/";


    //static parameters
    public static final String EXAM_ID_11_SUBJECTIVE = "11";
    public static final String EXAM_ID_3_OBJECTIVE = "3";
    public static final String EXAM_ID_9_OBJECTIVE = "9";
    public static final String STUDENT_ID_202_OBJECCTIVE = "202";
    public static final String USER_ID_370 = "370";
    public static final String USER_ID_319 = "319";
    public static final String USER_ID_580 = "580";
    public static final String TEST_GETSTUDYMATES = "167";


    public static final String TEACHER_ROLE_ID = "3";
    public static final String TEST_USER_ID_52 = "52";
    public static final String TEST_USER_NAME = "Admin";
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
    public static final int GET_EXAM_SUBMISSION = 22;
    public static final int GET_EXAM_QUESTIONS = 23;
    public static final int GET_EXAM_EVALUATIONS = 24;
    public static final int GET_MY_STUDENTS = 25;

    public static final int CREATEQUESTION = 26;
    public static final int GET_ALL_HASHTAG = 27;
    public static final int GET_ALL_BADGES_COUNT = 28;
    public static final int SET_HASHTAG = 29;
    public static final int TEMP_CREATE_QUESTION = 30;
    public static final int UPLOAD_FEED_MEDIA = 31;
    public static final int UPLOADMEDIAFORQUESTION = 32;
    public static final int REGISTER_USER = 33;
    public static final int UPLOADPROFILEIMAGES = 34;

    public static final int GET_ADMIN_CONFIG = 35;
    public static final int REFRESH_TOKEN = 36;
    public static final int GROUP_ALLOCATION = 37;
    public static final int ALL_LESSON_NOTES = 38;
    public static final int LESSON_NOTES_WITH_DETAILS = 39;

    //User Profile
    public static final int GET_NOTIFICATION = 40;
    public static final int UPDATE_READ_STATUS = 41;
    public static final int GET_MESSAGES = 42;
    public static final int RESPOND_TO_REQUEST = 43;
    public static final int GET_STUDYMATE_REQUEST = 44;
    public static final int GET_USER_PREFERENCES = 45;
    public static final int MANAGE_GENERAL_SETTINGS = 46;
    public static final int GENERAL_SETTING_PREFERENCES = 47;

    public static final int BLOCK_USER = 48;
    public static final int UNBLOCK_USER = 49;
    public static final int BLOCKED_USER = 50;
    public static final int GET_MY_FEEDS = 51;


    //Progress Report

    public static final int GET_REPORT_DATA = 52;


    public static final String URL_GET_ADMIN_CONFIG = URL_HOST + "GetAdminConfig";
    public static final String URL_REFRESH_TOKEN = URL_HOST + "RefreshToken";

    public static final String URL_REGISTER_USER = URL_HOST + "RegisterUser";
    public static final String URL_LOGIN = URL_HOST + "AuthenticateUser";
    public static final String URL_GET_ALL_FEEDS = URL_HOST + "GetAllFeeds";
    public static final String URL_GET_ALL_COMMENTS = URL_HOST + "GetAllComments";
    public static final String URL_GET_STUDYMATES = URL_HOST + "GetStudymates";
    public static final String URL_ADD_COMMENT = URL_HOST + "AddComment";
    public static final String URL_TAG_FRIEND_IN_FEED = URL_HOST + "TagFriendInFeed";
    public static final String URL_LIKE_FEED = URL_HOST + "LikeFeed";
    public static final String URL_GETCLASSROOMS = URL_HOST + "GetAllClassrooms";
    public static final String URL_GETSUBJECT = URL_HOST + "GetAllSubjects";
    public static final String URL_GETTOPICS = URL_HOST + "GetAllTopics";
    public static final String URL_CREATEASSIGNMENT = URL_HOST + "CreateAssignment";
    public static final String URL_GET_COURSES = URL_HOST + "GetAllCourses";
    public static final String URL_CREATE_EXAM = URL_HOST + "CreateExam";
    public static final String URL_GET_QUESTION_BANK = URL_HOST + "GetQuestionBank";
    public static final String URL_POSTFEED = URL_HOST + "PostFeed";
    //SetQuestionForexam api
    public static final String URL_SET_QUESTIONS_FOR_EXAM = URL_HOST + "SetQuestionsForExam";
    public static final String URL_FORGOT_PASSWORD = URL_HOST + "ForgotPassword";
    public static final String URL_UPLOAD_FEED_MEDIA = URL_HOST + "UploadMedia";
    public static final String URL_REQUEST_CREDENTIALS = URL_HOST + "RequestForCredentials";


    //used for assignment
    public static final String URL_GET_ALL_ASSIGNMENTS = URL_HOST + "GetAllExams";
    public static final String URL_GET_EXAM_QUESTIONS = URL_HOST + "GetExamQuestions";
    public static final String URL_GET_EXAM_EVALUATIONS = URL_HOST + "GetExamEvaluation";
    public static final String URL_GET_MY_STUDENTS = URL_HOST + "GetMyStudents";


    public static final String URL_GET_EXAM_SUBMISSION = URL_HOST + "GetExamSubmission";

    public static final String URL_GET_CITIES = URL_HOST + "GetCities";
    public static final String URL_GET_STATES = URL_HOST + "GetStates";
    public static final String URL_GET_COUNTRIES = URL_HOST + "GetCountries";

    //Add Question Functionality

    public static final String URL_GET_ALL_HASHTAG = URL_HOST + "GetAllHashtag";
    public static final String URL_CREATEQUESTION = URL_HOST + "CreateQuestion";


    //Question Add/Edit Functionality
    public static final String URL_HASHTAG = URL_HOST + "Hashtag";
    public static final String URL_TEMP_CREATE_QUESTION = URL_HOST + "TempCreateQuestion";

    //Upload Media url

    public static final String URL_UPLOADMEDIAFORQUESTION = URL_HOST + "UploadMediaForQuestion";
    //Group Allocation url for sunday
    public static final String URL_GROUP_ALLOCATION = URL_HOST + "CheckGroupAllocation";

    //fetch all lesson notes
    public static final String URL_ALL_LESSON_NOTES = URL_HOST + "GetAllLessonNotes";

    //lesson notes with detail for subject id
    public static final String URL_LESSON_NOTES_WITH_DETAILS = URL_HOST + "GetLessonNotesWithDetails";


    public static final String STUDYMATE_REQUEST = "studymate_request";

    public static final String MESSAGES = "messages";

    public static final String NOTIFICATION = "notification";

    /**
     * User profile urls
     */
    public static final String URL_GET_NOTIFICATION = URL_HOST + "GetNotification";
    public static final String URL_UPDATE_READ_STATUS = URL_HOST + "UpdateReadStatus"; // studymate_request, messages, notification
    public static final String URL_GET_MESSAGES = URL_HOST + "GetMessages";
    public static final String URL_RESPOND_TO_REQUEST = URL_HOST + "AcceptRequestFromStudymate";
    public static final String URL_GET_STUDYMATE_REQUEST = URL_HOST + "GetStudymateRequest";
    public static final String URL_GENERAL_SETTING_PREFERENCES = URL_HOST + "GetAllPreferences";
    public static final String URL_MANAGE_GENERAL_SETTING = URL_HOST + "ManageGeneralSettings";
    public static final String URL_GET_USER_PREFERENCES = URL_HOST + "GetUserPreferences";
    public static final String URL_BLOCK_USER = URL_HOST + "BlockUser";
    public static final String URL_GET_BLOCKED_USER = URL_HOST + "GetBlockedUser";
    public static final String URL_GET_MY_FEEDS = URL_HOST + "GetMyFeeds";


    /**
     * Progress Report
     */

    public static final String URL_GET_REPORT_DATA = URL_HOST + "GetReportData";

}
