package com.ism.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;

/**
 * Created by c162 on 07/10/15.
 */
public class LoginActivity extends Activity {

	private static final String TAG = LoginActivity.class.getSimpleName();

	private EditText etPwd, etUserid;

	private Global global;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		global = new Global(this);
		initView();

		//  getIsRemember();

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

	private void initView() {
		MyTypeFace myTypeFace = new MyTypeFace(this);
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etUserid = (EditText) findViewById(R.id.et_userid);

		etUserid.setTypeface(myTypeFace.getRalewayRegular());
		etPwd.setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_donothave)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_clickhere)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_forgotpwd)).setTypeface(myTypeFace.getRalewayRegular());

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	}

	public void onClickLogin(View view) {
		validatation();
		if (((CheckBox) findViewById(R.id.chk_rememberme)).isChecked()) {

		}
	}

	public void onClickForgotPassword(View view) {

	}

	public void onClickClickHere(View view) {

	}

	private void validatation() {
//        if (etUserid.getText().toString().length() == 0) {
//            etUserid.requestFocus();
//            etUserid.setError(Html.fromHtml("<font color='red'>Field should not be blank.</font>"));
//        } else if ( etPwd.getText().toString().length() == 0) {
//            etPwd.requestFocus();
//            etPwd.setError(Html.fromHtml("<font color='red'>Field should not be blank.</font>"));
//        } else {
//			isRemember();
			etUserid.setText("");
			etPwd.setText("");
			global.myIntent(this, ProfileInformationActivity.class);
//		}
	}

	private void isRemember() {
		/*if (chkRememberme.isChecked()) {
			editor = sharedPreferences.edit();
			editor.putString(AppConstant.USERID, etUserid.getText().toString());
			editor.putString(AppConstant.PASSWORD, etPwd.getText().toString());
			editor.commit();
		} else {
			editor = sharedPreferences.edit();
			editor.putString(AppConstant.USERID, "");
			editor.putString(AppConstant.PASSWORD, "");
			editor.commit();
		}*/
	}

}
