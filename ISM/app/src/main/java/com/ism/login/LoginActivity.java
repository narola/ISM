package com.ism.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.utility.PreferenceData;

/**
 * Created by c162 on 07/10/15.
 */
public class LoginActivity extends Activity {

	private static final String TAG = LoginActivity.class.getSimpleName();

	private EditText etPwd, etUserid;

	private InputValidator inputValidator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initGlobal();

	}

	private void initGlobal() {
		MyTypeFace myTypeFace = new MyTypeFace(this);
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etUserid = (EditText) findViewById(R.id.et_userid);

		etUserid.setTypeface(myTypeFace.getRalewayRegular());
		etPwd.setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_donothave)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_clickhere)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_forgotpwd)).setTypeface(myTypeFace.getRalewayRegular());

		inputValidator = new InputValidator(LoginActivity.this);
	}

	private void getIsRemember() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Global.userId = sharedPreferences.getString(AppConstant.USERID, null);

		Global.password = sharedPreferences.getString(AppConstant.PASSWORD, null);
		if (Global.userId.length() == 0 && Global.password.length() == 0) {
			etUserid.setText(Global.userId);
			etPwd.setText(Global.password);
		}
	}

	public void onClickLogin(View view) {
		if (isInputsValid()) {
			Log.e(TAG, "inputs valid");
			if (((CheckBox) findViewById(R.id.chk_rememberme)).isChecked()) {
				PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, LoginActivity.this, true);
				PreferenceData.setStringPrefs(PreferenceData.USER_NAME, LoginActivity.this, etUserid.getText().toString().trim());
				PreferenceData.setBooleanPrefs(PreferenceData.IS_LOGGED_IN, LoginActivity.this, true);
			}
		}
	}

	public void onClickForgotPassword(View view) {

	}

	public void onClickClickHere(View view) {

	}

	private boolean isInputsValid() {
		return inputValidator.validateStringPresence(etUserid) &
				(inputValidator.validateStringPresence(etPwd) && inputValidator.validatePasswordLength(etPwd));
	}

	private void authenticateUser() {

	}

}
