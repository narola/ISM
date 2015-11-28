package com.ism.author.object;

import com.nostra13.universalimageloader.core.ImageLoader;

import io.realm.Realm;

/**
 * Created by c162 on 26/11/15.
 */
public class Global {
    public static String strUserId;
    public static String strFullName;
    public static String strProfilePic;

    public static int intApiCounter = 0;
    public static ImageLoader imageLoader;

    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    public static MyTypeFace myTypeFace;
}
