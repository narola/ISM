package com.ism.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.HostActivity;
import com.ism.R;
import com.ism.model.ForgotPasswordRequest;
import com.ism.model.LoginRequest;
import com.ism.model.ResponseObject;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.utility.PreferenceData;
import com.ism.ws.WebserviceWrapper;

/**
 * Created by c162 on 07/10/15.
 */
public class LoginActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = LoginActivity.class.getSimpleName();

	private EditText etPwd, etUserid;

	private InputValidator inputValidator;

	public static final String ID = "userId";
	public static final String SCHOOL_ID = "schoolId";
	public static final String SCHOOL_NAME = "schoolName";
	public static final String SCHOOL_DISTRICT = "schoolDistrict";
	public static final String CLASS_ID = "classId";
	public static final String CLASS_NAME = "className";
	public static final String COURSE_ID = "courseId";
	public static final String COURSE_NAME = "courseName";
	public static final String ACADEMIC_YEAR = "academicYear";
	public static final String ROLE_ID = "roleId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME, LoginActivity.this)) {
			launchHostActivity();
		} else {
			setContentView(R.layout.activity_login);
			initGlobal();
		}
	}

	private void initGlobal() {
		MyTypeFace myTypeFace = new MyTypeFace(this);
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etUserid = (EditText) findViewById(R.id.et_userid);

		etUserid.setText("prerna");
		etPwd.setText("narola21");

		etUserid.setTypeface(myTypeFace.getRalewayRegular());
		etPwd.setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_donothave)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_clickhere)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_forgotpwd)).setTypeface(myTypeFace.getRalewayRegular());

		inputValidator = new InputValidator(LoginActivity.this);
	}

	public void onClickLogin(View view) {
		if (isInputsValid()) {
			if (((CheckBox) findViewById(R.id.chk_rememberme)).isChecked()) {
				PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, LoginActivity.this, true);
			}
			callApiAuthenticateUser();
		}
	}

	public void onClickForgotPassword(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

		LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
		View dialogView = inflater.inflate(R.layout.dialog_forgot_password, null);
		final EditText etEmail = (EditText) dialogView.findViewById(R.id.et_email);

		builder.setView(dialogView)
				.setTitle("Password will be sent to your email id.")
				.setPositiveButton(R.string.strsubmit, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (inputValidator.validateAllConstraintsEmail(etEmail)) {
							callApiForgotPassword(etEmail.getText().toString().trim());
						}
					}
				}).setNegativeButton(R.string.strcancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		builder.setCancelable(false);
		builder.create().show();
	}

	public void onClickClickHere(View view) {

	}

	private boolean isInputsValid() {
		return inputValidator.validateStringPresence(etUserid) &
				(inputValidator.validateStringPresence(etPwd) && inputValidator.validatePasswordLength(etPwd));
	}

	private void callApiForgotPassword(String email) {
		try {
			ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
			forgotPasswordRequest.setEmailId(email);

			new WebserviceWrapper(LoginActivity.this, forgotPasswordRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.FORGOT_PASSWORD);

		} catch (Exception e) {
			Log.e(TAG, "callApiForgotPassword Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiAuthenticateUser() {
		try {
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setUsername(etUserid.getText().toString().trim());
			loginRequest.setPassword(etPwd.getText().toString().trim());
//			loginRequest.setUsername("0YGAJ8793B");
//			loginRequest.setPassword("narola21");

			new WebserviceWrapper(LoginActivity.this, loginRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.LOGIN);

		} catch (Exception e) {
			Log.e(TAG, "callApiAuthenticateUser Exception : " + e.getLocalizedMessage());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		switch (apiCode) {
			case WebserviceWrapper.LOGIN:
				onLoginResponse(object);
				break;
			case WebserviceWrapper.FORGOT_PASSWORD:
				onForgotPasswordResponse(object);
				break;
		}
	}

	private void onForgotPasswordResponse(Object object) {
		try {
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					Toast.makeText(LoginActivity.this, R.string.password_sent, Toast.LENGTH_LONG).show();
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Toast.makeText(LoginActivity.this, R.string.email_not_found, Toast.LENGTH_LONG).show();
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onForgotPasswordResponse Exception : " + e.toString());
		}
	}

	private void onLoginResponse(Object object) {
		ResponseObject responseObj = (ResponseObject) object;
		if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {

			if (responseObj.getData().get(0).getUserId() == null) {
				Log.e(TAG, "first time login userId : " + responseObj.getData().get(0).getUserId());

				Intent intentRegister = new Intent(LoginActivity.this, ProfileInformationActivity.class);
				intentRegister.putExtra(ID, responseObj.getData().get(0).getId());
				intentRegister.putExtra(SCHOOL_ID, responseObj.getData().get(0).getSchoolId());
				intentRegister.putExtra(SCHOOL_NAME, responseObj.getData().get(0).getSchoolName());
				intentRegister.putExtra(SCHOOL_DISTRICT, responseObj.getData().get(0).getSchoolDestrict());
				intentRegister.putExtra(CLASS_ID, responseObj.getData().get(0).getClassId());
				intentRegister.putExtra(CLASS_NAME, responseObj.getData().get(0).getClassName());
				intentRegister.putExtra(COURSE_ID, responseObj.getData().get(0).getCourseId());
				intentRegister.putExtra(COURSE_NAME, responseObj.getData().get(0).getCourseName());
				intentRegister.putExtra(ACADEMIC_YEAR, responseObj.getData().get(0).getAcademicYear());
				intentRegister.putExtra(ROLE_ID, responseObj.getData().get(0).getRoleId());
				startActivity(intentRegister);
				finish();

			} else {
				Log.e(TAG, "registered user login");
				PreferenceData.setStringPrefs(PreferenceData.USER_ID, LoginActivity.this, responseObj.getData().get(0).getUserId());
				PreferenceData.setStringPrefs(PreferenceData.USER_NAME, LoginActivity.this, responseObj.getData().get(0).getUserName());

				Intent intentRegister = new Intent(LoginActivity.this, ProfileInformationActivity.class);
				startActivity(intentRegister);
				finish();

//				launchHostActivity();
			}

		} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
			Toast.makeText(LoginActivity.this, "Username or Password is wrong!", Toast.LENGTH_LONG).show();
		}
	}

	private void launchHostActivity() {
		Intent intentRegister = new Intent(LoginActivity.this, HostActivity.class);
		startActivity(intentRegister);
		finish();
	}

}
