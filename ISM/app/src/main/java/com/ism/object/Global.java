package com.ism.object;

import com.nostra13.universalimageloader.core.ImageLoader;

import io.realm.Realm;

/**
 * Created by c161 on 05/11/15.
 */
public class Global {

    public static String strUserId;
    public static String strFullName;
    public static String strProfilePic;
    public static String strTutorialGroupId;
    public static String strTutorialGroupName;

    public static int intApiCounter = 0;
    public static ImageLoader imageLoader;
    public static String roleID = "2";

    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    public static MyTypeFace myTypeFace;

}
