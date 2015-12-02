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
	public static final int GET_WALLET_SUMMARY = 25;
	public static final int GENERATE_VOUCHER = 26;
	public static final int GET_MY_FEEDS = 27;


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



	/**
	 *  URLs
	 */
	private static final String URL = "http://192.168.1.147/WS_ISM/ISMServices.php?Service=";  // KRUNAL
	private static final String URL_HOST_162 = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";  // ARTI

	public static final String HOST_202 = "http://192.168.1.202/pg/ISM/";      // pg
//	public static final String HOST_202 = "http://clientapp.narolaifotech.com/pg/ISM/";      // pg
//	private static final String URL = HOST_202 + "WS_ISM/ISMServices.php?Service=";

//	private static final String URL_IMAGE_PATH = "http://192.168.1.147/WS_ISM/Images";
	public static final String URL_HOST_202 = "http://192.168.1.202/pg/ISM/WS_ISM/";

	public static final String URL_USERS_IMAGE_PATH = "http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/";
	public static final String URL_ACCEPT_TUTORIAL_GROUP = URL + "AcceptTutorialGroup";
	public static final String URL_ALLOCATE_TUTORIAL_GROUP = URL + "AllocateTutorialGroup";
	public static final String URL_GET_CITIES = URL + "GetCities";
	public static final String URL_GET_STATES = URL + "GetStates";
	public static final String URL_GET_COUNTRIES = URL + "GetCountries";
	public static final String URL_LOGIN = URL + "AuthenticateUser";
	public static final String URL_TAG_STUDY_MATES = URL + "TagFriendInFeed";
	public static final String URL_GET_ALL_STUDY_MATES = URL + "GetStudymates";
	public static final String URL_GET_ALL_COMMENTS = URL + "GetAllComments";
	public static final String URL_ADD_COMMENT = URL + "AddComment";
	public static final String URL_GET_ALL_FEEDS = URL + "GetAllFeeds";
	public static final String URL_REGISTER_USER = URL + "RegisterUser";
	public static final String URL_REQUEST_SCHOOL_INFO = URL + "RequestForSchoolInfoUpdation";
	public static final String URL_REQUEST_CREDENTIALS = URL + "RequestForCredentials";
	public static final String URL_FORGOT_PASSWORD = URL + "ForgotPassword";
	public static final String URL_GET_ALL_NOTICES = URL + "GetAllNotices";
	public static final String URL_USER_PREFERENCES = URL + "GetUserPreferences";
	public static final String URL_GENERAL_SETTING_PREFERENCES = URL + "GetAllPreferences";
	public static final String URL_MANAGE_GENERAL_SETTING = URL + "ManageGeneralSettings";
	public static final String URL_GET_NOTIFICATION = URL + "GetNotification";
	public static final String URL_GET_MESSAGES = URL + "GetMessages";
	public static final String URL_GET_STUDYMATE_REQUEST = URL + "GetStudymateRequest";
	public static final String URL_BLOCK_USER = URL + "BlockUser";
	public static final String URL_GET_ALL_BADGES_COUNT = URL + "GetAllBadgeCount";
	public static final String URL_RESPOND_TO_REQUEST = URL + "AcceptRequestFromStudymate";
	public static final String URL_UPDATE_READ_STATUS = URL + "UpdateReadStatus"; // studymate_request, messages, notification
	public static final String URL_GET_HIGH_SCORERS = URL + "GetHighScorers";
	public static final String URL_GET_WALLET_SUMMARY = URL + "GetWalletSummary";
	public static final String URL_GENERATE_VOUCHER = URL + "GenerateVoucher";
	public static final String URL_GET_MY_FEEDS = URL + "GetMyFeeds";


	/*
	* edit profile
	* */
	public static final String URL_GET_ABOUT_ME =  URL + "GetAboutMe";
	public static final String URL_GET_BOOKS_FOR_USER =  URL + "GetBooksForUser";
	public static final String URL_EDIT_ABOUT_ME =  URL + "EditAboutMe";
	public static final String URL_GET_MOVIES_FOR_USER =  URL + "GetMoviesForUser";
	public static final String URL_GET_PASTTIME_FOR_USER =  URL + "GetPastimeForUser";
	public static final String URL_GET_ROLEMODEL_FOR_USER =  URL + "GetRoleModelForUser";
	public static final String URL_MANAGE_FAVOURITES =  URL + "ManageFavorite";
	public static final String URL_MANAGE_BOOK_LIBRARY =  URL + "ManageBookLibrary";
	public static final String URL_EDIT_PROFILE_PIC = " ";
	public static final String URL_GET_BLOCKED_USER =  URL + "GetBlockedUser";

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
