package com.ism.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ism.broadcastReceiver.AlarmReceiver;

/**
 * Created by c161 on 01/01/16.
 */
public class Alarm {

	private static final String TAG = Alarm.class.getSimpleName();

	public static final String REQUEST_CODE = "requestCode";
	public static final int REQUEST_CODE_FRIDAY_EXAM_STATUS = 0;

	public static final long SECOND = 1000;
	public static final long MINUTE = SECOND * 60;

	public static void setAlarm(Context context, int requestCode, long triggerAtTimeMillis, long repeatAtMillis) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intentAlarm = new Intent(context, AlarmReceiver.class);
		intentAlarm.putExtra(REQUEST_CODE, requestCode);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTimeMillis, repeatAtMillis, pendingIntent);
	}

	public static void cancelAlarm(Context context, int requestCode) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intentAlarm = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(pendingIntent);
	}

}
