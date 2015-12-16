package com.ism.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.adapter.AcceptTutorialGroupAdapter;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.views.CircleImageView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
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
	private ActionProcessButton btnAccept;

	private ImageLoader imageLoader;
	private ProgressGenerator progressGenerator;

	private String strUserId, strGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_tutorial_group);

        intitGlobal();

    }

    private void intitGlobal() {
        txtUserSchoolName = (TextView) findViewById(R.id.txt_schoolname);
        txtUserYearAndCourse = (TextView) findViewById(R.id.txt_yearandcourse);
        txtUserName = (TextView) findViewById(R.id.txt_username);
	    imgUserDp = (CircleImageView) findViewById(R.id.img_user_dp);
	    btnAccept = (ActionProcessButton) findViewById(R.id.btn_accept);

        MyTypeFace myTypeFace = new MyTypeFace(this);
	    ((TextView) findViewById(R.id.txt_welcometoism)).setTypeface(myTypeFace.getRalewaySemiBold());
	    ((TextView) findViewById(R.id.txt_yourtutorial_)).setTypeface(myTypeFace.getRalewayBold());
	    txtUserName.setTypeface(myTypeFace.getRalewaySemiBold());
	    txtUserYearAndCourse.setTypeface(myTypeFace.getRalewayRegular());
	    txtUserSchoolName.setTypeface(myTypeFace.getRalewayRegular());

	    progressGenerator = new ProgressGenerator();

	    imageLoader = ImageLoader.getInstance();
	    imageLoader.init(ImageLoaderConfiguration.createDefault(AcceptTutorialGroupActivity.this));

	    Global.strProfilePic = WebConstants.HOST_IMAGE_USER + PreferenceData.getStringPrefs(PreferenceData.USER_PROFILE_PIC, AcceptTutorialGroupActivity.this);

//	    Global.strProfilePic = "http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png";

	    imageLoader.displayImage(Global.strProfilePic, imgUserDp, ISMStudent.options);
	    txtUserName.setText(PreferenceData.getStringPrefs(PreferenceData.USER_FULL_NAME, AcceptTutorialGroupActivity.this));
	    txtUserSchoolName.setText(PreferenceData.getStringPrefs(PreferenceData.USER_SCHOOL_NAME, AcceptTutorialGroupActivity.this));
	    txtUserYearAndCourse.setText(PreferenceData.getStringPrefs(PreferenceData.USER_CLASS_NAME, AcceptTutorialGroupActivity.this)
	                                + ", " + PreferenceData.getStringPrefs(PreferenceData.USER_COURSE_NAME, AcceptTutorialGroupActivity.this));

        gridTutorialGroup = (GridView) findViewById(R.id.gv_groupMember);
        gridTutorialGroup.setAdapter(new AcceptTutorialGroupAdapter(AcceptTutorialGroupActivity.this));

	    strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, AcceptTutorialGroupActivity.this);
	    strGroupId = PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, AcceptTutorialGroupActivity.this);
    }

	public void onClickAccept(View view) {
		if (Utility.isConnected(AcceptTutorialGroupActivity.this)) {
			callApiAcceptTutorialGroup();
		} else {
			Utility.alertOffline(AcceptTutorialGroupActivity.this);
		}
	}

	private void callApiAcceptTutorialGroup() {
		try {
			btnAccept.setProgress(1);
			btnAccept.setEnabled(false);
			progressGenerator.start(btnAccept);
			Attribute attribute = new Attribute();
			attribute.setUserId(strUserId);
			attribute.setGroupId(strGroupId);
			attribute.setJoiningStatus("1");

			new WebserviceWrapper(AcceptTutorialGroupActivity.this, attribute, this).new WebserviceCaller()
					.execute(WebConstants.ACCEPT_TUTORIAL_GROUP);
		} catch (Exception e) {
			Log.e(TAG, "callApiAcceptTutorialGroup Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.ACCEPT_TUTORIAL_GROUP:
					onResponseAcceptTutorialGroup(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseAcceptTutorialGroup(Object object, Exception error) {
		try {
			if (btnAccept != null) {
				btnAccept.setProgress(100);
				btnAccept.setEnabled(true);
			}
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, AcceptTutorialGroupActivity.this, true);
					PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_COMPLETED, AcceptTutorialGroupActivity.this, true);
					launchHostActivity();
				} else if (responseHandler.getStatus().equals("incomplete")) {
					Toast.makeText(AcceptTutorialGroupActivity.this, R.string.msg_waiting_for_other_members, Toast.LENGTH_LONG).show();
					PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, AcceptTutorialGroupActivity.this, true);
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseAcceptTutorialGroup Failed : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseAcceptTutorialGroup api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseAcceptTutorialGroup Exception : " + e.toString());
		}
	}

	private void launchHostActivity() {
		Utility.launchActivity(AcceptTutorialGroupActivity.this, HostActivity.class);
		finish();
	}

}
