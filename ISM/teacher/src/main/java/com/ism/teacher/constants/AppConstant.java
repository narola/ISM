package com.ism.teacher.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by c162 on 07/10/15.
 */
public class AppConstant {

    public static final String back_tag = "BACK==";

    //Pick from Gallery
    public static final int REQUEST_CODE_PICK_FROM_GALLERY = 1;
    public static final int REQUEST_CODE_ADD_POST = 2;

    //Constants for MediaType used in uploading
    public static final String MEDIATYPE_IMAGE = "image";
    public static final String MEDIATYPE_VIDEO = "video";
    public static final String MEDIATYPE_AUDIO = "audio";
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

    public static final String IMAGE_CAPTURE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Images";
    public static final String VIDEO_CAPTURE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Videos";
    public static final String AUDIO_CAPTURE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Audios";


    public static final String TEST_GET_ALL_FEEDS = "141";
    public static final String TEST_GETSTUDYMATES = "167";
    public static final String TEST_FEEDID = "240";
    public static final String TEST_TAGGED_BY = "134";
    public static final String[] tagUserArray = new String[]{"141", "167"};
    public static final String AUTHOR_ROLE_ID = "4";
    public static final String TEACHER_ROLE_ID = "3";

    /**
     * Managing Teacher office Fragment by using tags
     */

    public static final String FRAGMENT_TAG_TEACHER_OFFICE = "teacher_office";

    public static final String FRAGMENT_TAG_TEACHER_CLASSWALL = "teacher_classwall";
    public static final String FRAGMENT_TAG_TEACHER_NOTES = "teacher_notes";
    public static final String FRAGMENT_TAG_TEACHER_QUIZ = "teacher_quiz";
    public static final String FRAGMENT_TAG_TEACHER_MARKSCRIPT = "teacher_markscript";
    public static final String FRAGMENT_TAG_TEACHER_RESULTS = "teacher_results";
    public static final String FRAGMENT_TAG_TEACHER_PROGRESS_REPORT = "teacher_progress_report";
    public static final String FRAGMENT_TAG_ASSIGNMENT_SUBMITTER = "assignment_submitter";
    public static final String FRAGMENT_TAG_CREATE_EXAM_CONTAINER = "create_exam";
    public static final String FRAGMENT_TAG_VIEW_ASSIGNMENT_QUESTION = "view_assignment_question";
    public static final String FRAGMENT_TAG_SUBJECTIVE_QUESTIONS = "subjective_questions";
    public static final String FRAGMENT_TAG_NOTES_ADD_EDIT = "notesAddEdit";


    public static final int LIKE = 1;
    public static final int DISLIKE = 0;


    /**
     * Index of text menus on the top of teacher office
     */

    public static final int INDEX_CLASSWALL = 0;
    public static final int INDEX_NOTES = 1;
    public static final int INDEX_ALL_ASSIGNMENTS = 2;
    public static final int INDEX_MARKSCRIPT = 3;
    public static final int INDEX_RESULTS = 4;
    public static final int INDEX_PROGRESS_REPORT = 5;

}
