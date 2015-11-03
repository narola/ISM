package com.ism.author.helper;


import android.app.Application;
import android.graphics.Bitmap;

import com.ism.author.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by c161 on 29/10/15.
 */
public class ImageLoaderInit extends Application {

	public static DisplayImageOptions options;

	@Override
	public void onCreate() {
		super.onCreate();
		Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());

		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.showImageOnLoading(R.drawable.img_loading)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();

	}
}
