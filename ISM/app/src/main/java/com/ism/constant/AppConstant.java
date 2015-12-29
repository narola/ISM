package com.ism.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by c162 on 07/10/15.
 */
public class AppConstant {

    /**
     *  All fonts
     */
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
    public static final String SchadowBT = "SchadowBT.ttf";

    /**
     *  Directory paths
     */
    public static final String IMAGE_CAPTURE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Images";
    public static final String VIDEO_CAPTURE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Videos";
    public static final String AUDIO_CAPTURE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Audios";


    /*
    *  resource name for edit profile
    */
    public  static final String RESOURCE_BOOKS="books";
    public  static final String RESOURCE_ROLEMODEL="rolemodel";
    public  static final String RESOURCE_PASTTIMES="pastimes";
    public  static final String RESOURCE_MOVIES="movies";

    /**
     * Date pattern
     */

    public static final String DATE_YYYYMMDD="yyyy-MM-dd";
    public static final int REQUEST_CODE_PICK_FROM_GALLERY = 771;

    /**
     *
     */
    public static final String MEDIATYPE_IMAGE = "image";
    public static final String MEDIATYPE_VIDEO = "video";
    public static final String MEDIATYPE_AUDIO = "audio";

    public static final String FRAGMENT_JOTTER = "jotter";
    public static final String FRAGMENT_FAVOURITE = "favourites";
    public static final String FRAGMENT_BOOKS = "books";
    public static final String FRAGMENT_TIMETABLE = "timetable";
}
