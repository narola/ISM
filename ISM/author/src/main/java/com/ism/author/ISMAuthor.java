package com.ism.author;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by c166 on 04/11/15.
 */
public class ISMAuthor extends Application {


    public static DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.ic_classmates_active)
                .showImageForEmptyUri(R.drawable.ic_classmates_active)
                .showImageOnFail(R.drawable.ic_classmates_active)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }
}
