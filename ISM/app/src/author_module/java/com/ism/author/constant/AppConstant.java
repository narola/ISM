package com.ism.author.constant;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

import java.io.File;

/**
 * Created by c166 on 23/10/15.
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

    public static final String imageCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Images";
    public static final String videoCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Videos";
    public static final String audioCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Audios";



    /**
     * URLs
     */
    private static final String URL_HOST = "http://192.168.1.75/ISM/WS_ISM/ISMServices.php?Service=";
    public static final String URL_LOGIN = URL_HOST + "AuthenticateUser";
    public static final String Status_Uploading="uploading";

    /**
     * Hide Keyboard
     *
     * @param mContext
     */
    public static void hideKeyboard(Context mContext) {

        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity) mContext).getWindow()
                .getCurrentFocus().getWindowToken(), 0);
    }


}
