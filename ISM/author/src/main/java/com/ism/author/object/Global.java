package com.ism.author.object;

import com.nostra13.universalimageloader.core.ImageLoader;

import io.realm.Realm;
import realmhelper.AuthorHelper;

/**
 * Created by c162 on 26/11/15.
 */
public class Global {
    public static String strUserId;
    public static String strFullName;
    public static String strProfilePic;

    public static int intApiCounter = 0;
    public static ImageLoader imageLoader;
    public static String role = "4";
    public static String checkSlotNo = "yes";

    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    public static MyTypeFace myTypeFace;
    public static AuthorHelper authorHelper;
}
