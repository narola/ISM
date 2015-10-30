package com.ism.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.HostActivity;
import com.ism.R;
import com.ism.adapter.TutorialGroupAdapter;
import com.ism.model.AcceptTutorialGroupRequest;
import com.ism.model.ResponseObject;
import com.ism.object.MyTypeFace;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.WebserviceWrapper;

/**
 * Created by c162 on 08/10/15.
 */
public class AcceptTutorialGroupActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AcceptTutorialGroupActivity.class.getSimpleName();

	private String strUserId;
	private String strGroupId;
	private GridView grid;
    private TextView txtSchoolname, txtYearandcourse, txtYourtutorial, txtWelcometoism, txtUsername;

    private Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_accept_tutorial_group);
        intitView();
    }

    private void intitView() {
        MyTypeFace myTypeFace = new MyTypeFace(this);
        txtSchoolname = (TextView) findViewById(R.id.txt_schoolname);
        txtYearandcourse = (TextView) findViewById(R.id.txt_yearandcourse);
        txtYourtutorial = (TextView) findViewById(R.id.txt_yourtutorial_);
        txtWelcometoism = (TextView) findViewById(R.id.txt_welcometoism);
        txtUsername = (TextView) findViewById(R.id.txt_username);
        btnAccept = (Button) findViewById(R.id.btn_accept);
        txtUsername.setTypeface(myTypeFace.getRalewaySemiBold());
        txtWelcometoism.setTypeface(myTypeFace.getRalewaySemiBold());
        txtYourtutorial.setTypeface(myTypeFace.getRalewayBold());
        txtYearandcourse.setTypeface(myTypeFace.getRalewayRegular());
        txtSchoolname.setTypeface(myTypeFace.getRalewayRegular());

        grid = (GridView) findViewById(R.id.gv_groupMember);
        grid.setAdapter(new TutorialGroupAdapter(getApplicationContext()));

	    strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, AcceptTutorialGroupActivity.this);
	    strGroupId = PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, AcceptTutorialGroupActivity.this);

	    btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            callApiAcceptTutorialGroup();
                PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, AcceptTutorialGroupActivity.this, true);
                Intent intentHostActivity = new Intent(AcceptTutorialGroupActivity.this, HostActivity.class);
                startActivity(intentHostActivity);
                finish();
            }
        });

    }

	private void callApiAcceptTutorialGroup() {
		try {
			AcceptTutorialGroupRequest acceptTutorialGroupRequest = new AcceptTutorialGroupRequest();
			acceptTutorialGroupRequest.setUserId(strUserId);
			acceptTutorialGroupRequest.setGroupId(strGroupId);
			acceptTutorialGroupRequest.setJoiningStatus("1");

			new WebserviceWrapper(AcceptTutorialGroupActivity.this, acceptTutorialGroupRequest).new WebserviceCaller()
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
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				if (responseObj.getMessage().contains("Group is not yet complete")) {
					PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, AcceptTutorialGroupActivity.this, true);
					Toast.makeText(AcceptTutorialGroupActivity.this, R.string.msg_waiting_for_other_members, Toast.LENGTH_LONG).show();
				}
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
