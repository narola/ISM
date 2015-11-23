package com.realm.ismrealm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by c85 on 19/11/15.
 * This class is use to create Realm instance with database and migration functionality.
 * it is working as a base class for Realm.
 */
public class RealmAdaptor {
    /**
    * get realm instance
    * @param context
    * @return
            */
    public static Realm getInstance(Context context){

        RealmConfiguration configuration = new RealmConfiguration.Builder(context)
                .name("ISM.realm")
                .schemaVersion(0)
                .migration(new Migration(0))
                .build();

        return Realm.getInstance(configuration);
    }
}
