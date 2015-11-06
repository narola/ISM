package com.ism.teacher.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by c162 on 07/10/15.
 */
public class AppConstant {


    //All fonts

    public static final String RalewayBlack = "Raleway-Black.ttf";
    public static final String RalewayBlackItalic = "Raleway-BlackItalic.ttf";
    public static final String RalewayBold = "Raleway-Bold.ttf";
    public static final String RalewayBoldItalic = "Raleway-BoldItalic.ttf";
    public static final String RalewayExtraBold = "Raleway-ExtraBold.ttf";
    public static final String RalewayExtraBoldItalic = "Raleway-ExtraBoldItalic.ttf";
    public static final String RalewayExtraLight = "Raleway-ExtraLight.ttf";
    public static final String RalewayExtraLightItalic = "Raleway-ExtraLightItalic.ttf";
    public static final String RalewayItalic = "Raleway-Italic.ttf";
    public static final String RalewayLight = "Raleway-Light.ttf";
    public static final String RalewayLightItalic = "Raleway-LightItalic.ttf";
    public static final String RalewayMedium = "Raleway-Medium.ttf";
    public static final String RalewayMediumItalic = "Raleway-MediumItalic.ttf";
    public static final String RalewayRegular = "Raleway-Regular.ttf";
    public static final String RalewaySemiBold = "Raleway-SemiBold.ttf";
    public static final String RalewaySemiBoldItalic = "Raleway-SemiBoldItalic.ttf";
    public static final String RalewayThin = "Raleway-Thin.ttf";
    public static final String RalewayThinItalic = "Raleway-ThinItalic.ttf";

    public static final String USERID = "USERID";
    public static final String PASSWORD = "PASSWORD";
    public static final String API_STATUS_SUCCESS = "success";
    public static final String API_STATUS_FAIL = "fail";

    public static final String imageCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Images";
    public static final String videoCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Videos";
    public static final String audioCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Audios";

    /**
     * URLs
     */

    public static final String URL_ARTI_HOST = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";

    public static final String URL_RAVI_HOST = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";


    public static final String URL_LOGIN = URL_ARTI_HOST + "AuthenticateUser";
    public static final String URL_GET_ALL_FEEDS = URL_ARTI_HOST + "GetAllFeeds";
    public static final String URL_GET_ALL_COMMENTS = URL_ARTI_HOST + "GetAllComments";
    public static final String URL_GET_STUDYMATES = URL_ARTI_HOST + "GetStudymates";
    public static final String URL_ADD_COMMENT = URL_ARTI_HOST + "AddComment";
    public static final String URL_TAG_FRIEND_IN_FEED = URL_ARTI_HOST + "TagFriendInFeed";
    public static final String URL_LIKE_FEED = URL_ARTI_HOST + "LikeFeed";
    public static final String URL_GETCLASSROOMS = URL_ARTI_HOST + "GetClassrooms";
    public static final String URL_GETSUBJECT = URL_ARTI_HOST + "GetSubject";
    public static final String URL_GETTOPICS = URL_ARTI_HOST + "GetTopics";
    public static final String URL_CREATEASSIGNMENT = URL_ARTI_HOST + "CreateAssignment";
    public static final String URL_GET_COURSES = URL_ARTI_HOST + "GetCourses";
    public static final String URL_CREATE_EXAM = URL_ARTI_HOST + "CreateExam";
    public static final String URL_GETQUESTIONBANK = URL_ARTI_HOST + "GetQuestionBank";
    public static final String URL_POSTFEED = URL_ARTI_HOST + "PostFeed";
    public static final String URL_SET_QUESTIONS_FOR_EXAM= URL_ARTI_HOST + "SetQuestionsForExam";


   /* public static final String URL_LOGIN = URL_RAVI_HOST + "AuthenticateUser";
    public static final String URL_GET_ALL_FEEDS = URL_RAVI_HOST+ "GetAllFeeds";
    public static final String URL_GET_ALL_COMMENTS = URL_RAVI_HOST+"GetAllComments";
    public static final String URL_GET_STUDYMATES = URL_RAVI_HOST+"GetStudymates";
    public static final String URL_ADD_COMMENT =URL_RAVI_HOST+"AddComment";
    public static final String URL_TAG_FRIEND_IN_FEED = URL_RAVI_HOST+"TagFriendInFeed";
    public static final String URL_LIKE_FEED = URL_RAVI_HOST+"LikeFeed";
    public static final String URL_GETCLASSROOMS = URL_RAVI_HOST + "GetClassrooms";
    public static final String URL_GETSUBJECT = URL_RAVI_HOST + "GetSubject";
    public static final String URL_GETTOPICS = URL_RAVI_HOST + "GetTopics";
    public static final String URL_CREATEASSIGNMENT = URL_RAVI_HOST + "CreateAssignment";
    public static final String URL_GET_COURSES = URL_RAVI_HOST + "GetCourses";
    public static final String URL_CREATE_EXAM = URL_RAVI_HOST + "CreateExam";
    public static final String URL_GETQUESTIONBANK = URL_RAVI_HOST + "GetQuestionBank";
    public static final String URL_POSTFEED = URL_RAVI_HOST + "PostFeed";*/

    public static final String TEST_USER_ID = "370";
    public static final String TEST_GET_ALL_FEEDS = "141";
    public static final String TEST_GETSTUDYMATES = "167";
    public static final String TEST_FEEDID = "240";
    public static final String TEST_TAGGED_BY = "134";
    public static final String[] tagUserArray = new String[]{"141", "167"};
    public static final int AUTHOR_ROLE_ID = 4;

    /**
     * Managing Teacher office Fragment by using tags
     */
    public static final String FRAGMENT_TAG_TEACHER_CLASSWALL = "teacher_classwall";
    public static final String FRAGMENT_TAG_TEACHER_NOTES = "teacher_notes";
    public static final String FRAGMENT_TAG_TEACHER_QUIZ = "teacher_quiz";
    public static final String FRAGMENT_TAG_TEACHER_MARKSCRIPT = "teacher_markscript";
    public static final String FRAGMENT_TAG_TEACHER_RESULTS = "teacher_results";
    public static final String FRAGMENT_TAG_TEACHER_PROGRESS_REPORT = "teacher_progress_report";



}
