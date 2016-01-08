package com.ism.broadcastReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.utility.Alarm;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;

import java.util.Calendar;

/**
 * Created by c161 on 01/01/16.
 */
public class AlarmReceiver extends BroadcastReceiver implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = AlarmReceiver.class.getSimpleName();

	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {

		this.context = context;

		if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

			if (PreferenceData.getStringPrefs(PreferenceData.FRIDAY_EXAM_QUESTION_SET_DATE, context, "").equals(Utility.getDate())
					&& !PreferenceData.getBooleanPrefs(PreferenceData.IS_FRIDAY_EXAM_READY, context)) {
//			if (!PreferenceData.getBooleanPrefs(PreferenceData.IS_FRIDAY_EXAM_READY, context)) {

				/**
				 * Set alarm to check Exam status every 5mins.
				 */
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MINUTE, 5);
//				calendar.add(Calendar.SECOND, 10);

				Alarm.setAlarm(context, Alarm.REQUEST_CODE_FRIDAY_EXAM_STATUS, calendar.getTimeInMillis(), Alarm.MINUTE * 5);
//				Alarm.setAlarm(context, Alarm.REQUEST_CODE_FRIDAY_EXAM_STATUS, calendar.getTimeInMillis(), Alarm.SECOND * 10);
			}
		} else {
			switch (intent.getIntExtra(Alarm.REQUEST_CODE, -1)) {
				case Alarm.REQUEST_CODE_FRIDAY_EXAM_STATUS:
					callApiCheckFridayExamStatus();
					break;
			}
		}
	}

	private void callApiCheckFridayExamStatus() {
		try {
			Attribute attribute = new Attribute();
			attribute.setGroupId(PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, context));
			attribute.setTutorialTopicId(PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_TOPIC_ID, context));
			new WebserviceWrapper(context, attribute, this).new WebserviceCaller().execute(WebConstants.CHECK_FRIDAY_EXAM_STATUS);
		} catch (Exception e) {
			Log.e(TAG, "callApiCheckFridayExamStatus Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.CHECK_FRIDAY_EXAM_STATUS:
					onResponseCheckFridayExamStatus(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseCheckFridayExamStatus(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					if (responseHandler.getFridayExamStatus() == null && responseHandler.getFridayExamStatus().size() > 0
							&& responseHandler.getFridayExamStatus().get(0).getIsReady().equals("yes")) {
						PreferenceData.setBooleanPrefs(PreferenceData.IS_FRIDAY_EXAM_READY, context, true);
						Alarm.cancelAlarm(context, Alarm.REQUEST_CODE_FRIDAY_EXAM_STATUS);

						NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
						NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
								.setSmallIcon(R.drawable.ic_chat)
								.setContentTitle(context.getString(R.string.app_name))
								.setContentText(context.getString(R.string.msg_tutorial_exam_ready))
								.setAutoCancel(true);

						Intent intentFridayExam = new Intent(context, HostActivity.class);
						intentFridayExam.setAction(AppConstant.ACTION_FRIDAY_EXAM);
						PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentFridayExam, PendingIntent.FLAG_UPDATE_CURRENT);
						notificationBuilder.setContentIntent(pendingIntent);
						notificationManager.notify(AppConstant.ID_NOTIFICATION_FRIDAY_EXAM, notificationBuilder.build());
					} else {
						PreferenceData.setBooleanPrefs(PreferenceData.IS_FRIDAY_EXAM_READY, context, false);
					}
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseCheckFridayExamStatus failed : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseCheckFridayExamStatus api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseCheckFridayExamStatus Exception : " + e.toString());
		}
	}

}
