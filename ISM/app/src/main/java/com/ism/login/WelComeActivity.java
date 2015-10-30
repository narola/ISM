package com.ism.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.adapter.Adapters;
import com.ism.model.AllocateTutorialGroupRequest;
import com.ism.model.Data;
import com.ism.model.ResponseObject;
import com.ism.model.TutorialGroupMember;
import com.ism.object.MyTypeFace;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.WebserviceWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c161 on 08/10/15.
 */
public class WelComeActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = WelComeActivity.class.getSimpleName();

    private TextView txtTutorialGroup, txtWelcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        intitGlobal();

    }

    private void intitGlobal() {
	    txtTutorialGroup = (TextView) findViewById(R.id.txt_tutorialgroup_);
	    txtWelcomeMsg = (TextView) findViewById(R.id.txt_welcome_msg);

	    MyTypeFace myTypeFace = new MyTypeFace(this);
	    txtTutorialGroup.setTypeface(myTypeFace.getRalewayRegular());
	    txtWelcomeMsg.setTypeface(myTypeFace.getRalewaySemiBold());

	    txtWelcomeMsg.setText(PreferenceData.getStringPrefs(PreferenceData.FULL_NAME, WelComeActivity.this) + ", " + getString(R.string.welcome_msg));
        txtTutorialGroup.setText(Html.fromHtml(getString(R.string.tutorial_group_description)));

	    Global.userId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, WelComeActivity.this);

	    if (Utility.isOnline(WelComeActivity.this)) {
		    callApiAllocateTutorialGroup();
	    } else {
		    Utility.toastOffline(WelComeActivity.this);
	    }
    }

	private void callApiAllocateTutorialGroup() {
		try {
			AllocateTutorialGroupRequest allocateTutorialGroupRequest = new AllocateTutorialGroupRequest();
			allocateTutorialGroupRequest.setUserId(Global.userId);

			new WebserviceWrapper(WelComeActivity.this, allocateTutorialGroupRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.ALLOCATE_TUTORIAL_GROUP);
		} catch (Exception e) {
			Log.e(TAG, "callApiAllocateTutorialGroup Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			if (object != null) {
				switch (apiCode) {
					case WebserviceWrapper.ALLOCATE_TUTORIAL_GROUP:
						onResponseAllocateTutorialGroup(object);
						break;
				}
			} else if (error != null) {
				Log.e(TAG, "onResponse ApiCall Exception : " + error.toString() + "\nFor apiCode : " + apiCode);
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseAllocateTutorialGroup(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				if (responseObj.getMessage().equals("Group created")) {

					PreferenceData.setStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, WelComeActivity.this, responseObj.getData().get(0).getTutorialGroupId());
					PreferenceData.setStringPrefs(PreferenceData.TUTORIAL_GROUP_NAME, WelComeActivity.this, responseObj.getData().get(0).getTutorialGroupName());

					for (TutorialGroupMember member : responseObj.getData().get(0).getTutorialGroupMembers()) {
						if (member.getUserId().equals(Global.userId)) {
							PreferenceData.setStringPrefs(PreferenceData.COURSE_NAME, WelComeActivity.this, member.getCourseName());
							PreferenceData.setStringPrefs(PreferenceData.ACADEMIC_YEAR, WelComeActivity.this, member.getAcademicYear());
							PreferenceData.setStringPrefs(PreferenceData.PROFILE_PIC, WelComeActivity.this, member.getProfilePic());
							PreferenceData.setStringPrefs(PreferenceData.SCHOOL_NAME, WelComeActivity.this, member.getSchoolName());
							PreferenceData.setStringPrefs(PreferenceData.SCHOOL_GRADE, WelComeActivity.this, member.getSchoolGrade());
							PreferenceData.setStringPrefs(PreferenceData.CLASS_NAME, WelComeActivity.this, member.getClassName());
						}
					}

//					Save tutorial group from :
//					responseObj.getData().get(0).getTutorialGroupId();
//					responseObj.getData().get(0).getTutorialGroupJoiningStatus();
//					responseObj.getData().get(0).getTutorialGroupName();
//					responseObj.getData().get(0).getTutorialGroupMembers();

					launchAcceptTutorialGroupActivity();
				} else {
					Toast.makeText(WelComeActivity.this, R.string.msg_tutorial_group_pending, Toast.LENGTH_LONG).show();
				}
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Log.e(TAG, "onResponseAllocateTutorialGroup Failed");
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseAllocateTutorialGroup Exception : " + e.toString());
		}
	}

	private void launchAcceptTutorialGroupActivity() {
		Utility.launchIntent(WelComeActivity.this, AcceptTutorialGroupActivity.class);
		finish();
	}

}