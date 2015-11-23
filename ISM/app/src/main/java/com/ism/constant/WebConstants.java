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
	public static final int GET_ALL_STUDY_MATES = 14;
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


	//general settings
	//public static final int BLOCK_USER = 52;

	public static final int MANAGE_GENERAL_SETTINGS = 51;
	public static final int GENERAL_SETTING_PREFERENCES = 52;
	public static final int GET_USER_PREFERENCES=53;



	/*
	edit profiles
	 */
	public static final int GET_BOOKS_FOR_USER = 54;
	public static final int GET_ABOUT_ME = 55;
	public static final int EDIT_ABOUT_ME = 56;
	public static final int GET_ROLEMODEL_FOR_USER = 57;
	public static final int GET_PASTTIME_FOR_USER = 58;
	public static final int GET_MOVIES_FOR_USER = 59;


	/**
	 *  URLs
	 */
	private static final String URL_HOST_161 = "http://192.168.1.161/ISM/WS_ISM/ISMServices.php?Service=";  // KRUNAL
	private static final String URL_HOST_162 = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";  // ARTI
	private static final String URL_HOST_147 = "http://192.168.1.147/WS_ISM/ISMServices.php?Service=";      // KINJAL

	public static final String URL_USERS_IMAGE_PATH = "http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/";
	public static final String URL_ACCEPT_TUTORIAL_GROUP = URL_HOST_161 + "AcceptTutorialGroup";
	public static final String URL_ALLOCATE_TUTORIAL_GROUP = URL_HOST_147 + "AllocateTutorialGroup";
	public static final String URL_GET_CITIES = URL_HOST_147 + "GetCities";
	public static final String URL_GET_STATES = URL_HOST_147 + "GetStates";
	public static final String URL_GET_COUNTRIES = URL_HOST_147 + "GetCountries";
	public static final String URL_LOGIN = URL_HOST_147 + "AuthenticateUser";
	public static final String URL_TAG_STUDY_MATES = URL_HOST_147 + "TagFriendInFeed";
	public static final String URL_GET_ALL_STUDY_MATES = URL_HOST_147 + "GetStudymates";
	public static final String URL_GET_ALL_COMMENTS = URL_HOST_147 + "GetAllComments";
	public static final String URL_ADD_COMMENT = URL_HOST_147 + "AddComment";
	public static final String URL_GET_ALL_FEEDS = URL_HOST_147 + "GetAllFeeds";
	public static final String URL_REGISTER_USER = URL_HOST_147 + "RegisterUser";
	public static final String URL_REQUEST_SCHOOL_INFO = URL_HOST_147 + "RequestForSchoolInfoUpdation";
	public static final String URL_REQUEST_CREDENTIALS = URL_HOST_147 + "RequestForCredentials";
	public static final String URL_FORGOT_PASSWORD = URL_HOST_147 + "ForgotPassword";
	public static final String URL_GET_ALL_NOTICES = URL_HOST_147 + "GetAllNotices";
	public static final String URL_USER_PREFERENCES = URL_HOST_147 + "GetUserPreferences";
	public static final String URL_GENERAL_SETTING_PREFERENCES = URL_HOST_147 + "GetAllPreferences";
	public static final String URL_MANAGE_GENERAL_SETTING = URL_HOST_147 + "ManageGeneralSettings";
	public static final String URL_GET_NOTIFICATION = URL_HOST_147 + "GetNotification";
	public static final String URL_GET_MESSAGES = URL_HOST_147 + "GetMessages";
	public static final String URL_GET_STUDYMATE_REQUEST = URL_HOST_147 + "GetStudymateRequest";
	public static final String URL_PRIVACY_SETTING = URL_HOST_147 + "GetAllNotices";
	public static final String URL_BLOCK_USER = URL_HOST_147 + "GetAllNotices";
	public static final String URL_NOTIFICATION = URL_HOST_147 + "GetAllNotices";
	public static final String URL_PROFILE_PIC = URL_HOST_162 + "UploadUserProfilePic";
	public static final String URL_GET_ALL_BADGES_COUNT = URL_HOST_147 + "GetAllBadgeCount";
	public static final String URL_RESPOND_TO_REQUEST = URL_HOST_147 + "AcceptRequestFromStudymate";
	public static final String URL_UPDATE_READ_STATUS = URL_HOST_147 + "UpdateReadStatus"; // studymate_request, messages, notification
	public static final String URL_GET_HIGH_SCORERS = URL_HOST_147 + "GetHighScorers";


	/*
	* edit profile
	* */
	public static final String URL_GET_ABOUT_ME =  URL_HOST_147 + "GetAboutMe";
	public static final String URL_EDIT_ABOUT_ME =  URL_HOST_147 + "EditAboutMe";
	public static final String URL_GET_BOOKS_FOR_USER =  URL_HOST_147 + "GetBooksForUser";
	public static final String URL_GET_MOVIES_FOR_USER =  URL_HOST_147 + "GetMoviesForUser";
	public static final String URL_GET_PASTTIME_FOR_USER =  URL_HOST_147 + "GetPastimeForUser";
	public static final String URL_GET_ROLEMODEL_FOR_USER =  URL_HOST_147 + "GetRoleModelForUser";




	/**
	 * Parameter values
	 */
	public static final String STUDYMATE_REQUEST = "studymate_request";
	public static final String MESSAGES = "messages";
	public static final String NOTIFICATION = "notification";
	public static final String ROLE_ALL = "all";
	public static final String ROLE_STUDENT = "student";

	/**
	 * Response values
	 */
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String DUPLICATE_ENTRY = "Duplicate entry";
}
