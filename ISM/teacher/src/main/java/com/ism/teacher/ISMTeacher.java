package com.ism.teacher;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by c161 on 29/10/15.
 */
public class ISMTeacher extends Application {

	public static DisplayImageOptions options;

	@Override
	public void onCreate() {
		super.onCreate();
		Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());

		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.showImageOnLoading(R.drawable.ic_study_mates_active_teacher)
				.showImageForEmptyUri(R.drawable.ic_study_mates_active_teacher)
				.showImageOnFail(R.drawable.ic_study_mates_active_teacher)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();

	}
}
