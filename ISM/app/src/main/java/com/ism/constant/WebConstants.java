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

	/**
	 *  URLs
	 */
	private static final String URL_HOST_75 = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";    // RAVI
	private static final String URL_HOST_161 = "http://192.168.1.161/ISM/WS_ISM/ISMServices.php?Service=";  // KRUNAL
	private static final String URL_HOST_162 = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";  // ARTI
	private static final String URL_HOST_147 = "http://192.168.1.147/WS_ISM/ISMServices.php?Service=";      // KINJAL

	public static final String URL_IMAGE_PATH = "http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/";
	public static final String URL_ACCEPT_TUTORIAL_GROUP = URL_HOST_161 + "AcceptTutorialGroup";
	public static final String URL_ALLOCATE_TUTORIAL_GROUP = URL_HOST_161 + "AllocateTutorialGroup";
	public static final String URL_GET_CITIES = URL_HOST_161 + "GetCities";
	public static final String URL_GET_STATES = URL_HOST_161 + "GetStates";
	public static final String URL_GET_COUNTRIES = URL_HOST_161 + "GetCountries";
	public static final String URL_LOGIN = URL_HOST_161 + "AuthenticateUser";
	public static final String URL_TAG_STUDY_MATES = URL_HOST_162 + "TagFriendInFeed";
	public static final String URL_GET_ALL_STUDY_MATES = URL_HOST_162 + "GetStudymates";
	public static final String URL_GET_ALL_COMMENTS = URL_HOST_162 + "GetAllComments";
	public static final String URL_ADD_COMMENT = URL_HOST_162 + "AddComment";
	public static final String URL_GET_ALL_FEEDS = URL_HOST_162 + "GetAllFeeds";
	public static final String URL_REGISTER_USER = URL_HOST_162 + "RegisterUser";
	public static final String URL_REQUEST_SCHOOL_INFO = URL_HOST_162 + "RequestForSchoolInfoUpdation";
	public static final String URL_REQUEST_CREDENTIALS = URL_HOST_162 + "RequestForCredentials";
	public static final String URL_FORGOT_PASSWORD = URL_HOST_162 + "ForgotPassword";
	public static final String URL_GET_ALL_NOTICES = URL_HOST_147 + "GetAllNotices";

}
