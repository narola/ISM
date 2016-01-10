package com.ism.author.realm;

import android.content.Context;

import realmhelper.AuthorHelper;

/**
 * Created by c166 on 08/01/16.
 */
public class RealmHandler {


    private static final String TAG = RealmHandler.class.getSimpleName();
    private AuthorHelper authorHelper;

    public RealmHandler(Context context) {
        authorHelper = new AuthorHelper(context);
    }


    public void removeRealm() {
        authorHelper.realm.close();
    }
}
