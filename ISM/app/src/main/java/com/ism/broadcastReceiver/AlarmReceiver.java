package com.ism.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.fragment.tutorialGroup.ExamFragment;
import com.ism.object.Global;
import com.ism.utility.Alarm;
import com.ism.utility.PreferenceData;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;

/**
 * Created by c161 on 01/01/16.
 */
public class AlarmReceiver extends BroadcastReceiver implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = AlarmReceiver.class.getSimpleName();

	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {

		this.context = context;

		switch (intent.getIntExtra(Alarm.REQUEST_CODE, -1)) {
			case Alarm.REQUEST_CODE_FRIDAY_EXAM_STATUS:
				callApiCheckFridayExamStatus();
				break;
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
						Alarm.cancelAlarm(context, Alarm.REQUEST_CODE_FRIDAY_EXAM_STATUS);
					} else {
						Toast.makeText(context, "exam not ready", Toast.LENGTH_SHORT).show();
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
