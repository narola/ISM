package com.ism.author.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by c166 on 23/10/15.
 */
public class AppConstant {


    /*all fonts*/

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


    public static final String STATUS_UPLOADING = "uploading";
    public static final String MEDIATYPE_IMAGE = "image";
    public static final String MEDIATYPE_VIDEO = "video";
    public static final String MEDIATYPE_AUDIO = "audio";
    public static final String RESOURCE_TYPE_QUESTION = "question";
    public static final String RESOURCE_BOOKS = "books";
    public static final int LIKE = 1;
    public static final int DISLIKE = 0;



     /*these are the tags to handle backstack management for fragments*/

    public static final String FRAGMENT_MYDESK = "fragmentMyDesk";
    public static final String FRAGMENT_GOTRENDING = "fragmentGoTrending";
    public static final String FRAGMENT_TRIAL = "fragmentTrial";
    public static final String FRAGMENT_ASSESSMENT = "fragmentAssessment";
    public static final String FRAGMENT_CREATEEXAMCONTAINER = "fragmentCreateExamContainer";
    public static final String FRAGMENT_ASSIGNMENT_SUBMITTOR = "fragmentAssignmentSubmittor";
    public static final String FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS = "fragmentObjectiveAssignmentQuestions";
    public static final String FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER = "fragmentSubjectiveAssignmentQuestionsContainer";
    public static final String FRAGMENT_ADDQUESTION_CONTAINER = "fragmentAddQuestionContainer";
    public static final String FRAGMENT_PAST = "fragmentPast";
    public static final String FRAGMENT_ADD_ASSIGNMENT = "fragmentAddAssignment";
}
