package com.ism.constant;

/**
 * Created by c161 on 05/11/15.
 */
public class WebConstants {

	/**
	 *  Api IDs
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


	//general settings

	public static final int MANAGE_GENERAL_SETTINGS = 71;
	public static final int GENERAL_SETTING_PREFERENCES = 72;
	public static final int GET_USER_PREFERENCES=73;
	public static final int BLOCK_USER = 74;
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

	/**
	 *  URLs
	 */
//	private static final String URL_HOST_161 = "http://192.168.1.161/ISM/WS_ISM/ISMServices.php?Service=";  // KRUNAL
//	private static final String URL_HOST_162 = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";  // ARTI
//	private static final String URL_HOST_147 = HOST_147 + "WS_ISM/ISMServices.php?Service=";                // KINJAL
//	private static final String URL_IMAGE_PATH = "http://192.168.1.147/WS_ISM/Images"

//	public static final String HOST = "http://192.168.1.202/";                                              // SERVER
//	public static final String HOST = "http://clientapp.narolainfotech.com/";                               // SERVER
	public static final String HOST = "http://192.168.1.147/";                                              // KINJAL

//	private static final String DIR_PATH = "pg/ISM/WS_ISM/";    //  SERVER
	private static final String DIR_PATH = "WS_ISM/";           //  KINJAL
	private static final String HOST_WS = HOST + DIR_PATH + "ISMServices.php?Service=";
	//	public static final String HOST_IMAGE_USER = HOST + DIR_PATH + "images/users_Images/";
	public static final String HOST_IMAGE_USER = "http://192.168.1.202/pg/ISM/WS_ISM/images/users_Images/";

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

	/**
	 * Parameter value Constants
	 */
	public static final String STUDYMATE_REQUEST = "studymate_request";
	public static final String MESSAGES = "messages";
	public static final String NOTIFICATION = "notification";
	public static final String ROLE_ALL = "all";
	public static final String ROLE_STUDENT = "student";

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
