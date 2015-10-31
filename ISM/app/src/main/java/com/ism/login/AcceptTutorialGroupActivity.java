package com.ism.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.HostActivity;
import com.ism.ISMStudent;
import com.ism.R;
import com.ism.adapter.TutorialGroupAdapter;
import com.ism.helper.CircleImageView;
import com.ism.ws.RequestObject;
import com.ism.ws.ResponseObject;
import com.ism.object.MyTypeFace;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.WebserviceWrapper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by c161 on 30/10/15.
 */
public class AcceptTutorialGroupActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AcceptTutorialGroupActivity.class.getSimpleName();

	private GridView gridTutorialGroup;
	private TextView txtUserName, txtUserSchoolName, txtUserYearAndCourse;
	private CircleImageView imgUserDp;

	private ImageLoader imageLoader;

	private String strUserId, strGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_accept_tutorial_group);

        intitGlobal();

    }

    private void intitGlobal() {
        txtUserSchoolName = (TextView) findViewById(R.id.txt_schoolname);
        txtUserYearAndCourse = (TextView) findViewById(R.id.txt_yearandcourse);
        txtUserName = (TextView) findViewById(R.id.txt_username);
	    imgUserDp = (CircleImageView) findViewById(R.id.img_user_dp);

        MyTypeFace myTypeFace = new MyTypeFace(this);
	    ((TextView) findViewById(R.id.txt_welcometoism)).setTypeface(myTypeFace.getRalewaySemiBold());
	    ((TextView) findViewById(R.id.txt_yourtutorial_)).setTypeface(myTypeFace.getRalewayBold());
	    txtUserName.setTypeface(myTypeFace.getRalewaySemiBold());
	    txtUserYearAndCourse.setTypeface(myTypeFace.getRalewayRegular());
	    txtUserSchoolName.setTypeface(myTypeFace.getRalewayRegular());

	    imageLoader = ImageLoader.getInstance();
	    imageLoader.init(ImageLoaderConfiguration.createDefault(AcceptTutorialGroupActivity.this));

	    Global.profilePic = AppConstant.URL_IMAGE_PATH + PreferenceData.getStringPrefs(PreferenceData.USER_PROFILE_PIC, AcceptTutorialGroupActivity.this);

	    Global.profilePic = "http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png";

	    imageLoader.displayImage(Global.profilePic, imgUserDp, ISMStudent.options);
	    txtUserName.setText(PreferenceData.getStringPrefs(PreferenceData.USER_FULL_NAME, AcceptTutorialGroupActivity.this));
	    txtUserSchoolName.setText(PreferenceData.getStringPrefs(PreferenceData.USER_SCHOOL_NAME, AcceptTutorialGroupActivity.this));
	    txtUserYearAndCourse.setText(PreferenceData.getStringPrefs(PreferenceData.USER_CLASS_NAME, AcceptTutorialGroupActivity.this)
	                                + ", " + PreferenceData.getStringPrefs(PreferenceData.USER_COURSE_NAME, AcceptTutorialGroupActivity.this));

        gridTutorialGroup = (GridView) findViewById(R.id.gv_groupMember);
        gridTutorialGroup.setAdapter(new TutorialGroupAdapter(AcceptTutorialGroupActivity.this));

	    strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, AcceptTutorialGroupActivity.this);
	    strGroupId = PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, AcceptTutorialGroupActivity.this);

    }

	public void onClickAccept(View view) {
		if (Utility.isOnline(AcceptTutorialGroupActivity.this)) {
			callApiAcceptTutorialGroup();
		} else {
			Utility.toastOffline(AcceptTutorialGroupActivity.this);
		}
	}

	private void callApiAcceptTutorialGroup() {
		try {
			RequestObject requestObject = new RequestObject();
			requestObject.setUserId(strUserId);
			requestObject.setGroupId(strGroupId);
			requestObject.setJoiningStatus("1");

			new WebserviceWrapper(AcceptTutorialGroupActivity.this, requestObject, this).new WebserviceCaller()
					.execute(WebserviceWrapper.ACCEPT_TUTORIAL_GROUP);
		} catch (Exception e) {
			Log.e(TAG, "callApiAcceptTutorialGroup Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			if (object != null) {
				switch (apiCode) {
					case WebserviceWrapper.ACCEPT_TUTORIAL_GROUP:
						onResponseAcceptTutorialGroup(object);
						break;
				}
			} else if (error != null) {
				Log.e(TAG, "onResponse ApiCall Exception : " + error.toString() + "\nFor apiCode : " + apiCode);
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseAcceptTutorialGroup(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, AcceptTutorialGroupActivity.this, true);
				PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_COMPLETED, AcceptTutorialGroupActivity.this, true);
				launchHostActivity();
			} else if (responseObj.getStatus().equals("incomplete")) {
				Toast.makeText(AcceptTutorialGroupActivity.this, R.string.msg_waiting_for_other_members, Toast.LENGTH_LONG).show();
				PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, AcceptTutorialGroupActivity.this, true);
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Log.e(TAG, "onResponseAcceptTutorialGroup Failed : " + responseObj.getMessage());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseAcceptTutorialGroup Exception : " + e.toString());
		}
	}

	private void launchHostActivity() {
		Utility.launchIntent(AcceptTutorialGroupActivity.this, HostActivity.class);
		finish();
	}

}
