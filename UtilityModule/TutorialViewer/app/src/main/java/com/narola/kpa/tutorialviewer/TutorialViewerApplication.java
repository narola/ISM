package com.narola.kpa.tutorialviewer;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Krunal Panchal on 01/10/15.
 */
public class TutorialViewerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());
    }
}
