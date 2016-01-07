package com.ism.constant;

/**
 * Created by c161 on 05/11/15.
 */
public class WebConstants {

    /**
     * Api IDs
     */
    public static final int LOGIN = 1;
    public static final int FORGOT_PASSWORD = 2;
    public static final int REQUEST_CREDENTIALS = 3;
    public static final int GET_COUNTRIES = 4;
    public static final int GET_STATES = 5;
    public static final int GET_CITIES = 6;
    public static final int REGISTER_USER = 7;
    public static final int REQUEST_SCHOOL_INFO = 8;
    public static final int ALLOCATE_TUTORIAL_GROUP = 9;
    public static final int ACCEPT_TUTORIAL_GROUP = 10;
    public static final int GET_ALL_FEEDS = 11;
    public static final int ADD_COMMENT = 12;
    public static final int GET_ALL_COMMENTS = 13;
    public static final int GET_ALL_STUDYMATES = 14;
    public static final int TAG_STUDY_MATES = 15;
    public static final int GET_ALL_NOTICES = 16;
    public static final int GET_NOTIFICATION = 17;
    public static final int GET_MESSAGES = 18;
    public static final int GET_STUDYMATE_REQUEST = 19;
    public static final int GET_ALL_BADGES_COUNT = 20;
    public static final int RESPOND_TO_REQUEST = 21;
    public static final int UPDATE_READ_STATUS = 22;
    public static final int GET_HIGH_SCORERS = 23;
    public static final int UPLOAD_PROFILE_PIC = 24;
    public static final int GET_WALLET_SUMMARY = 25;
    public static final int GENERATE_VOUCHER = 26;
    public static final int GET_MY_FEEDS = 27;
    public static final int GET_ALL_STUDYMATES_WITH_DETAILS = 28;
    public static final int GET_ALL_RECOMMENDED_STUDYMATES = 29;
    public static final int GET_MY_ACTIVITY = 30;
    public static final int UPLOAD_FEED_MEDIA = 31;
    public static final int GET_ADMIN_CONFIG = 32;
    public static final int REFRESH_TOKEN = 33;
    public static final int SEND_REQUEST_STUDYMATE = 34;
    public static final int GET_GROUP_HISTORY = 35;
    public static final int GET_GROUP_PROFILE = 36;
    public static final int SUBMIT_QUESTION_FOR_FRIDAY = 37;
    public static final int GET_ALL_NOTES = 38;
    public static final int CHECK_FRIDAY_EXAM_STATUS = 39;
	public static final int GET_ALL_BOOKS = 40;
	public static final int GET_FRIDAY_EXAM_QUESTIONS = 41;
	public static final int SUBMIT_STUDENT_OBJECTIVE_RESPONSE = 42;

    //general settings

    public static final int MANAGE_GENERAL_SETTINGS = 71;
    public static final int GENERAL_SETTING_PREFERENCES = 72;
    public static final int GET_USER_PREFERENCES = 73;
    public static final int BLOCK_USER = 74;
    public static final int UNBLOCK_USER = 74;
    public static final int BLOCKED_USER = 75;

    //edit profiles

    public static final int GET_BOOKS_FOR_USER = 54;
    public static final int GET_ABOUT_ME = 55;
    public static final int EDIT_ABOUT_ME = 56;
    public static final int GET_ROLEMODEL_FOR_USER = 57;
    public static final int GET_PASTTIME_FOR_USER = 58;
    public static final int GET_MOVIES_FOR_USER = 59;
    public static final int MANAGE_FAVOURITES = 60;
    public static final int MANAGE_BOOK_LIBRARY = 61;
    public static final int POSTFEED = 62;
	public static final int LIKE_FEED =63 ;


	/**
	 * my author
	 */
//	public static final String HOST = "http://clientapp.narolainfotech.com/";                               // SERVER
	public static final int GET_AUTHOR_BOOK_ASSIGNMENT = 101;
	public static final int GET_ASSIGNMENT_BY_BOOK = 102;
	public static final int GET_BOOKS_BY_AUTHOR =103 ;
	public static final int GET_FOLLOW_USER = 104;
	public static final int GET_RECOMMENDED_AUTHORS = 105;

	/**
	 *  URLs
	 */
//	private static final String URL_HOST_161 = "http://192.168.1.161/ISM/WS_ISM/ISMServices.php?Service=";  // KRUNAL
//	private static final String URL_HOST_162 = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";  // ARTI
//	private static final String URL_HOST_147 = HOST_147 + "WS_ISM/ISMServices.php?Service=";                // KINJAL
//	private static final String URL_IMAGE_PATH = "http://192.168.1.147/WS_ISM/Images"

	public static final String HOST = "http://192.168.1.202/";                      // SERVER
//	public static final String HOST = "http://clientapp.narolainfotech.com/";       // SERVER
//    public static final String HOST = "http://192.168.1.147/";                    // KINJAL

    private static final String DIR_PATH = "pg/ISM/WS_ISM/";                        //  SERVER
//    private static final String DIR_PATH = "WS_ISM/";                             //  KINJAL

	private static final String HOST_WS = HOST + DIR_PATH + "ISMServices.php?Service=";

	//	public static final String HOST_IMAGE_USER = HOST + DIR_PATH + "images/users_images/";
	public static final String HOST_IMAGE_USER = "http://192.168.1.202/pg/ISM/WS_ISM/images/users_images/";
	public static final String HOST_IMAGE_FEED = HOST + DIR_PATH + "images/feeds/";
	public static final String HOST_IMAGE_USER_OLD = "http://192.168.1.202/pg/ISM/WS_ISM/";
	public static final String URL_ACCEPT_TUTORIAL_GROUP = HOST_WS + "AcceptTutorialGroup";
	public static final String URL_ALLOCATE_TUTORIAL_GROUP = HOST_WS + "AllocateTutorialGroup";
	public static final String URL_GET_CITIES = HOST_WS + "GetCities";
	public static final String URL_GET_STATES = HOST_WS + "GetStates";
	public static final String URL_GET_COUNTRIES = HOST_WS + "GetCountries";
	public static final String URL_LOGIN = HOST_WS + "AuthenticateUser";
	public static final String URL_TAG_STUDY_MATES = HOST_WS + "TagFriendInFeed";
	public static final String URL_GET_ALL_STUDYMATES = HOST_WS + "GetStudymates";
	public static final String URL_GET_ALL_COMMENTS = HOST_WS + "GetAllComments";
	public static final String URL_ADD_COMMENT = HOST_WS + "AddComment";
	public static final String URL_GET_ALL_FEEDS = HOST_WS + "GetAllFeeds";
	public static final String URL_REGISTER_USER = HOST_WS + "RegisterUser";
	public static final String URL_REQUEST_SCHOOL_INFO = HOST_WS + "RequestForSchoolInfoUpdation";
	public static final String URL_REQUEST_CREDENTIALS = HOST_WS + "RequestForCredentials";
	public static final String URL_FORGOT_PASSWORD = HOST_WS + "ForgotPassword";
	public static final String URL_GET_ALL_NOTICES = HOST_WS + "GetAllNotices";
	public static final String URL_USER_PREFERENCES = HOST_WS + "GetUserPreferences";
	public static final String URL_GENERAL_SETTING_PREFERENCES = HOST_WS + "GetAllPreferences";
	public static final String URL_MANAGE_GENERAL_SETTING = HOST_WS + "ManageGeneralSettings";
	public static final String URL_GET_NOTIFICATION = HOST_WS + "GetNotification";
	public static final String URL_GET_MESSAGES = HOST_WS + "GetMessages";
	public static final String URL_GET_STUDYMATE_REQUEST = HOST_WS + "GetStudymateRequest";
	public static final String URL_BLOCK_USER = HOST_WS + "BlockUser";
	public static final String URL_GET_ALL_BADGES_COUNT = HOST_WS + "GetAllBadgeCount";
	public static final String URL_RESPOND_TO_REQUEST = HOST_WS + "AcceptRequestFromStudymate";
	public static final String URL_UPDATE_READ_STATUS = HOST_WS + "UpdateReadStatus"; // studymate_request, messages, notification
	public static final String URL_GET_HIGH_SCORERS = HOST_WS + "GetHighScorers";
	public static final String URL_GET_WALLET_SUMMARY = HOST_WS + "GetWalletSummary";
	public static final String URL_GENERATE_VOUCHER = HOST_WS + "GenerateVoucher";
	public static final String URL_GET_MY_FEEDS = HOST_WS + "GetMyFeeds";
	public static final String URL_GET_ALL_STUDYMATES_WITH_DETAILS = HOST_WS + "GetStudymatesWithDetails";
	public static final String URL_GET_ALL_RECOMMENDED_STUDYMATES = HOST_WS + "GetSuggestedStudymates";
	public static final String URL_POSTFEED = HOST_WS + "PostFeed";
	public static final String URL_GET_MY_ACTIVITY = HOST_WS + "GetMyActivity";
	public static final String URL_UPLOAD_FEED_MEDIA = HOST_WS + "UploadMedia";
	public static final String URL_REFRESH_TOKEN = HOST_WS + "RefreshToken";
	public static final String URL_GET_ADMIN_CONFIG = HOST_WS + "GetAdminConfig";
	public static final String URL_LIKE_FEED = HOST_WS + "LikeFeed";
	public static final String URL_GET_GROUP_HISTORY = HOST_WS + "GetGroupHistory";
	public static final String URL_GET_ALL_NOTES = HOST_WS + "GetAllNotes";
	public static final String URL_GET_GROUP_PROFILE = HOST_WS + "GetGroupProfile";
	public static final String URL_SEND_REQUEST_STUDYMATE = HOST_WS + "SendRequestToStudymate";
	public static final String URL_SUBMIT_QUESTION_FOR_FRIDAY = HOST_WS + "SubmitQuestionForFriday";
	public static final String URL_CHECK_FRIDAY_EXAM_STATUS = HOST_WS + "CheckFridayExamStatus";
	public static final String URL_GET_FRIDAY_EXAM_QUESTIONS = HOST_WS + "GetFridayExamQuestion";
	public static final String URL_SUBMIT_STUDENT_OBJECTIVE_RESPONSE = HOST_WS + "SubmitStudentObjectiveResponse";

	/*
	* edit profile
	* */
	public static final String URL_GET_ABOUT_ME =  HOST_WS + "GetAboutMe";
	public static final String URL_GET_BOOKS_FOR_USER =  HOST_WS + "GetBooksForUser";
	public static final String URL_EDIT_ABOUT_ME =  HOST_WS + "EditAboutMe";
	public static final String URL_GET_MOVIES_FOR_USER =  HOST_WS + "GetMoviesForUser";
	public static final String URL_GET_PASTTIME_FOR_USER =  HOST_WS + "GetPastimeForUser";
	public static final String URL_GET_ROLEMODEL_FOR_USER =  HOST_WS + "GetRoleModelForUser";
	public static final String URL_MANAGE_FAVOURITES =  HOST_WS + "ManageFavorite";
	public static final String URL_MANAGE_BOOK_LIBRARY =  HOST_WS + "ManageBookLibrary";
	public static final String URL_EDIT_PROFILE_PIC = " ";
	public static final String URL_GET_BLOCKED_USER =  HOST_WS + "GetBlockedUser";
	public static final String URL_GET_ALL_BOOKS = HOST_WS + "GetAllBooks";

	public static final String URL_UNBLOCK_USER =  HOST_WS + "UnBlockUser";
	/**
	 * my author
	 */

	public static final String URL_GET_AUTHOR_BOOK_ASSIGNMENT = HOST_WS + "GetAuthorBookAssignment";
	public static final String URL_GET_BOOKS_BY_AUTHOR = HOST_WS + "GetBooksByAuthors";
	public static final String URL_GET_ASSIGNMENT_BY_BOOK = HOST_WS + "GetAssignmentByBook";
	public static final String URL_GET_FOLLOW_USER = HOST_WS + "FollowUser";
	public static final String URL_GET_RECOMMENDED_AUTHORS = HOST_WS + "GetRecommendedAuthors";
	/**
	 * Parameter value Constants
	 */
	public static final String NO_USERNAME = "nousername";
	public static final String STUDYMATE_REQUEST = "studymate_request";
	public static final String MESSAGES = "messages";
	public static final String NOTIFICATION = "notification";
	public static final String ROLE_ALL = "all";
	public static final String ROLE_STUDENT = "student";
	public static final String MONDAY = "Mon";
	public static final String TUESDAY = "Tues";
	public static final String WEDNESDAY = "Wed";
	public static final String THURSDAY = "Thurs";
	public static final String FRIDAY = "Fri";
	public static final String SATURDAY = "Sat";

	/**
	 * Parameter value Variables
	 */
	public static String SECRET_KEY;
	public static String ACCESS_KEY;

	/**
	 * Response values
	 */
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String DUPLICATE_ENTRY = "Duplicate entry";

}
