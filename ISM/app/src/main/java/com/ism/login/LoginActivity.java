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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.utility.PreferenceData;

import org.json.JSONObject;

/**
 * Created by c162 on 07/10/15.
 */
public class LoginActivity extends Activity {

	private static final String TAG = LoginActivity.class.getSimpleName();

	private EditText etPwd, etUserid;

	private RequestQueue reqQueue;
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

		reqQueue = Volley.newRequestQueue(LoginActivity.this);
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

		}
		if (((CheckBox) findViewById(R.id.chk_rememberme)).isChecked()) {

		}
	}

	public void onClickForgotPassword(View view) {

	}

	public void onClickClickHere(View view) {

	}

	private boolean isInputsValid() {
		boolean valid = true;
		/*if (etUserid.getText().toString().length() == 0) {
			etUserid.requestFocus();
			etUserid.setError(Html.fromHtml("<font color='red'>Field should not be blank.</font>"));
		} else if ( etPwd.getText().toString().length() == 0) {
			etPwd.requestFocus();
			etPwd.setError(Html.fromHtml("<font color='red'>Field should not be blank.</font>"));
		} else {
			isRemember();
			etUserid.setText("");
			etPwd.setText("");
			global.myIntent(this, ProfileInformationActivity.class);
		}*/
//		return inputValidator.validateAllConstraintsEmail(et)
		return valid;
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

	private void authenticateUser() {
		JsonObjectRequest requestAuthentication = new JsonObjectRequest(Request.Method.POST, AppConstant.URL_LOGIN,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		reqQueue.add(requestAuthentication);
	}

}
