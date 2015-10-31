package com.ism;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by c161 on 29/10/15.
 */
public class ISMStudent extends Application {

	public static DisplayImageOptions options;

	@Override
	public void onCreate() {
		super.onCreate();
		Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());

		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.showImageOnLoading(R.drawable.img_audio_stop)
				.showImageForEmptyUri(R.drawable.img_audio_stop)
				.showImageOnFail(R.drawable.img_audio_stop)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();

	}
}
