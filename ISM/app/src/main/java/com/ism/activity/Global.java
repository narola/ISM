package com.ism.activity;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by c162 on 07/10/15.
 */
public class Global {

    static Context context;

    public Global(Context context) {
        this.context = context;
    }

    public static String userId;
    public static String fullName;
    public static String profilePic;
    public static String password;
    public static String tutorialGroupId;

	public Realm getRealmInstance() {
		return Realm.getDefaultInstance();
	}

}
