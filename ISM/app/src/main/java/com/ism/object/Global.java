package com.ism.object;

import io.realm.Realm;

/**
 * Created by c161 on 05/11/15.
 */
public class Global {

    public static String strUserId;
    public static String strFullName;
    public static String strProfilePic;

	public static int intApiCounter = 0;

	public Realm getRealmInstance() {
		return Realm.getDefaultInstance();
	}
	public static MyTypeFace myTypeFace;

}
