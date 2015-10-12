package com.narola.kpa.tutorialviewer.object;

import android.os.Environment;

import java.io.File;

import io.realm.Realm;

/**
 * Created by Krunal Panchal on 01/10/15.
 */
public class Global {

    /**
     * Constants
     */
    public static final String imagePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "TutorialImages";

    /**
     * Constants
     */
    public static final Realm realm = Realm.getDefaultInstance();

}
