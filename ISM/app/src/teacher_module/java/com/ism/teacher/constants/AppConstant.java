package com.ism.teacher.constants;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

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

    public static final String imageCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Images";
    public static final String videoCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Videos";
    public static final String audioCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Audios";

    /**
     * URLs
     */

    public static final String URL_ARTI_HOST = "http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=";
    public static final String URL_RAVI_HOST = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";



    private static final String URL_HOST = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";
    public static final String URL_LOGIN = URL_HOST + "AuthenticateUser";


    public static final String URL_GET_ALL_FEEDS = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=GetAllFeeds";
    public static final String URL_GET_ALL_COMMENTS = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=GetAllComments";
    public static final String URL_GET_STUDYMATES = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=GetStudymates";
    public static final String URL_ADD_COMMENT = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=AddComment";
    public static final String URL_TAG_FRIEND_IN_FEED = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=TagFriendInFeed";
    public static final String URL_LIKE_FEED = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=LikeFeed";

    public static final String URL_GETCLASSROOMS = URL_ARTI_HOST + "GetClassrooms";
    public static final String URL_GETSUBJECT = URL_ARTI_HOST + "GetSubject";
    public static final String URL_GETTOPICS = URL_ARTI_HOST + "GetTopics";
    public static final String URL_CREATEASSIGNMENT = URL_ARTI_HOST + "CreateAssignment";
    public static final String URL_GET_COURSES = URL_ARTI_HOST + "GetCourses";
    public static final String URL_CREATE_EXAM = URL_ARTI_HOST + "CreateExam";



    public static final String TEST_USER_ID = "370";
    public static final String TEST_GET_ALL_FEEDS = "141";
    public static final String TEST_GETSTUDYMATES = "167";
    public static final String TEST_FEEDID = "240";
    public static final String TEST_TAGGED_BY = "134";
    public static final String[] tagUserArray = new String[]{"141", "167"};


    /**
     * Managing Teacher office Fragment by using tags
     */
    public static final String FRAGMENT_TAG_TEACHER_CLASSWALL="teacher_classwall";
    public static final String FRAGMENT_TAG_TEACHER_NOTES="teacher_notes";
    public static final String FRAGMENT_TAG_TEACHER_QUIZ="teacher_quiz";
    public static final String FRAGMENT_TAG_TEACHER_MARKSCRIPT="teacher_markscript";
    public static final String FRAGMENT_TAG_TEACHER_RESULTS="teacher_results";
    public static final String FRAGMENT_TAG_TEACHER_PROGRESS_REPORT="teacher_progress_report";

}
