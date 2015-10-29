package com.ism;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by c161 on 29/10/15.
 */
public class ISMStudent extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());
	}
}
