package com.ism.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.ws.model.RequestObject;
import com.ism.ws.model.ResponseObject;
import com.ism.ws.model.TutorialGroupMember;
import com.ism.object.MyTypeFace;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.WebserviceWrapper;

/**
 * Created by c161 on 08/10/15.
 */
public class WelComeActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = WelComeActivity.class.getSimpleName();

    private TextView txtTutorialGroup, txtWelcomeMsg;
	private ActionProcessButton progWelcome;

	private ProgressGenerator progressGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        intitGlobal();

    }

    private void intitGlobal() {
	    txtTutorialGroup = (TextView) findViewById(R.id.txt_tutorialgroup_);
	    txtWelcomeMsg = (TextView) findViewById(R.id.txt_welcome_msg);
	    progWelcome = (ActionProcessButton) findViewById(R.id.prog_welcome);

	    MyTypeFace myTypeFace = new MyTypeFace(this);
	    txtTutorialGroup.setTypeface(myTypeFace.getRalewayRegular());
	    txtWelcomeMsg.setTypeface(myTypeFace.getRalewaySemiBold());

	    txtWelcomeMsg.setText(PreferenceData.getStringPrefs(PreferenceData.USER_FULL_NAME, WelComeActivity.this)
			    + ", " + getString(R.string.welcome_msg));
        txtTutorialGroup.setText(Html.fromHtml(getString(R.string.tutorial_group_description)));

	    progressGenerator = new ProgressGenerator();

	    Global.strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, WelComeActivity.this);

	    if (Utility.isOnline(WelComeActivity.this)) {
		    callApiAllocateTutorialGroup();
	    } else {
		    Utility.toastOffline(WelComeActivity.this);
	    }
    }

	private void callApiAllocateTutorialGroup() {
		try {
			progWelcome.setProgress(1);
			progWelcome.setVisibility(View.VISIBLE);
			progressGenerator.start(progWelcome);
			RequestObject requestObject = new RequestObject();
			requestObject.setUserId(Global.strUserId);

			new WebserviceWrapper(WelComeActivity.this, requestObject, this).new WebserviceCaller()
					.execute(WebConstants.ALLOCATE_TUTORIAL_GROUP);
		} catch (Exception e) {
			Log.e(TAG, "callApiAllocateTutorialGroup Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.ALLOCATE_TUTORIAL_GROUP:
					onResponseAllocateTutorialGroup(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseAllocateTutorialGroup(Object object, Exception error) {
		try {
			if (progWelcome != null) {
				progWelcome.setProgress(100);
				progWelcome.setVisibility(View.INVISIBLE);
			}
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					if (responseObj.getMessage().equals("Group created")) {

						PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ALLOCATED, WelComeActivity.this, true);
						PreferenceData.setStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, WelComeActivity.this, responseObj.getData().get(0).getTutorialGroupId());
						PreferenceData.setStringPrefs(PreferenceData.TUTORIAL_GROUP_NAME, WelComeActivity.this, responseObj.getData().get(0).getTutorialGroupName());

						for (TutorialGroupMember member : responseObj.getData().get(0).getTutorialGroupMembers()) {
							if (member.getUserId().equals(Global.strUserId)) {
								PreferenceData.setStringPrefs(PreferenceData.USER_COURSE_NAME, WelComeActivity.this, member.getCourseName());
								PreferenceData.setStringPrefs(PreferenceData.USER_ACADEMIC_YEAR, WelComeActivity.this, member.getAcademicYear());
								PreferenceData.setStringPrefs(PreferenceData.USER_PROFILE_PIC, WelComeActivity.this, member.getProfilePic());
								PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_NAME, WelComeActivity.this, member.getSchoolName());
								PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_GRADE, WelComeActivity.this, member.getSchoolGrade());
								PreferenceData.setStringPrefs(PreferenceData.USER_CLASS_NAME, WelComeActivity.this, member.getClassName());
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
			} else if (error != null) {
				Log.e(TAG, "onResponseAllocateTutorialGroup api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseAllocateTutorialGroup Exception : " + e.toString());
		}
	}

	private void launchAcceptTutorialGroupActivity() {
		Utility.launchActivity(WelComeActivity.this, AcceptTutorialGroupActivity.class);
		finish();
	}

}