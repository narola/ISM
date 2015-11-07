package com.ism.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.adapter.Adapters;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.RequestObject;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;
import com.ism.ws.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c161 on 07/10/15.
 */
public class LoginActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = LoginActivity.class.getSimpleName();

	private EditText etPwd, etUserid;
	private Spinner spCountry;
	private Spinner spState;
	private Spinner spCity;
	private ActionProcessButton btnLogin, progForgotPwd, progRequestCredentials, progCountry, progState, progCity;
	private Button btnForgotPwdSubmit, btnCredentialsSubmit;

	private InputValidator inputValidator;
	private ArrayList<Data> arrListCountries;
	private ArrayList<Data> arrListStates;
	private ArrayList<Data> arrListCities;
	private List<String> arrListDefalt;
	private AlertDialog dialogCredentials;
	private ProgressGenerator progressGenerator;
	private AlertDialog dialogForgotPassword;

	private String strValidationMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME, LoginActivity.this)) {
			if (PreferenceData.getBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, LoginActivity.this)) {
				if (PreferenceData.getBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_COMPLETED, LoginActivity.this)) {
					launchHostActivity();
				} else {
					launchAcceptTutorialGroupActivity();
				}
			} else if (PreferenceData.getBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ALLOCATED, LoginActivity.this)) {
				launchAcceptTutorialGroupActivity();
			} else {
				launchWelcomeActivity();
			}
		} else if (PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, LoginActivity.this)) {
			launchProfileInfoActivity();
		} else {
			setContentView(R.layout.activity_login);
			initGlobal();
		}
	}

	private void initGlobal() {
		MyTypeFace myTypeFace = new MyTypeFace(this);
		btnLogin = (ActionProcessButton) findViewById(R.id.btn_login);
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etUserid = (EditText) findViewById(R.id.et_userid);

		etUserid.setText("0YGAJ8793B");
//		etUserid.setText("prerna");
		etPwd.setText("narola21");

		etUserid.setTypeface(myTypeFace.getRalewayRegular());
		etPwd.setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_donothave)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_clickhere)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_forgotpwd)).setTypeface(myTypeFace.getRalewayRegular());

		inputValidator = new InputValidator(LoginActivity.this);

		arrListDefalt = new ArrayList<String>();
		arrListDefalt.add(getString(R.string.select));

		progressGenerator = new ProgressGenerator();
	}

	public void onClickLogin(View view) {
		if (Utility.isOnline(LoginActivity.this)) {

			/*if (strValidationMsg == null || strValidationMsg.equals("")) {
				strValidationMsg = "1";
				btnLogin.setProgress(1);
				progressGenerator.start(btnLogin);
			} else {
				strValidationMsg = "";
				btnLogin.setProgress(100);
			}*/

			if (isInputsValid()) {
				callApiAuthenticateUser();
			}
		} else {
			Utility.toastOffline(LoginActivity.this);
		}
	}

	public void onClickForgotPassword(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

		LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
		View dialogView = inflater.inflate(R.layout.dialog_forgot_password, null);
		final EditText etEmail = (EditText) dialogView.findViewById(R.id.et_email);
		progForgotPwd = (ActionProcessButton) dialogView.findViewById(R.id.prog_forgot_password);

		builder.setView(dialogView)
				.setTitle(R.string.title_forgot_password)
				.setPositiveButton(R.string.strsubmit, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).setNegativeButton(R.string.strclose, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.setCancelable(false);
		dialogForgotPassword = builder.create();
		dialogForgotPassword.show();
		btnForgotPwdSubmit = dialogForgotPassword.getButton(DialogInterface.BUTTON_POSITIVE);
		btnForgotPwdSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Utility.isOnline(LoginActivity.this)) {
					if (inputValidator.validateAllConstraintsEmail(etEmail)) {
						callApiForgotPassword(etEmail.getText().toString().trim());
					}
				} else {
					Utility.toastOffline(LoginActivity.this);
				}
			}
		});
	}

	public void onClickHere(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

		LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
		View dialogView = inflater.inflate(R.layout.dialog_request_credentials, null);
		final EditText etFirstName = (EditText) dialogView.findViewById(R.id.et_firstname);
		final EditText etLastName = (EditText) dialogView.findViewById(R.id.et_lastname);
		final EditText etEmail = (EditText) dialogView.findViewById(R.id.et_email);
		final EditText etHomeAddress = (EditText) dialogView.findViewById(R.id.et_home_address);
		final EditText etSchoolName = (EditText) dialogView.findViewById(R.id.et_school_name);
		final EditText etContactNo = (EditText) dialogView.findViewById(R.id.et_contactNo);
		spCountry = (Spinner) dialogView.findViewById(R.id.sp_country);
		spState = (Spinner) dialogView.findViewById(R.id.sp_state);
		spCity = (Spinner) dialogView.findViewById(R.id.sp_city);
		progRequestCredentials = (ActionProcessButton) dialogView.findViewById(R.id.prog_request_credentials);
		progCountry = (ActionProcessButton) dialogView.findViewById(R.id.prog_country);
		progState = (ActionProcessButton) dialogView.findViewById(R.id.prog_state);
		progCity = (ActionProcessButton) dialogView.findViewById(R.id.prog_city);

		if (Utility.isOnline(LoginActivity.this)) {
			callApiGetCountries();
		} else {
			Utility.toastOffline(LoginActivity.this);
		}

		spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (arrListCountries != null && position > 0) {
					if (Utility.isOnline(LoginActivity.this)) {
						callApiGetStates(Integer.parseInt(arrListCountries.get(position - 1).getId()));
					} else {
						Utility.toastOffline(LoginActivity.this);
					}
				} else {
					Adapters.setUpSpinner(LoginActivity.this, spState, arrListDefalt);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (arrListStates != null && position > 0) {
					if (Utility.isOnline(LoginActivity.this)) {
						callApiGetCities(Integer.parseInt(arrListStates.get(position - 1).getId()));
					} else {
						Utility.toastOffline(LoginActivity.this);
					}
				} else {
					Adapters.setUpSpinner(LoginActivity.this, spCity, arrListDefalt);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		builder.setView(dialogView)
				.setTitle("Request for Credentials")
				.setPositiveButton(R.string.strsubmit, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}

				}).setNegativeButton(R.string.strclose, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		builder.setCancelable(false);
		dialogCredentials = builder.create();
		dialogCredentials.show();
		btnCredentialsSubmit = dialogCredentials.getButton(AlertDialog.BUTTON_POSITIVE);
		btnCredentialsSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Utility.isOnline(LoginActivity.this)) {
					if (isInputsValid()) {
						RequestObject requestObject = new RequestObject();
						requestObject.setFirstname(etFirstName.getText().toString().trim());
						requestObject.setLastname(etLastName.getText().toString().trim());
						requestObject.setEmailAddress(etEmail.getText().toString().trim());
						requestObject.setHomeAddress(etHomeAddress.getText().toString().trim());
						requestObject.setSchoolName(etSchoolName.getText().toString().trim());
						requestObject.setContactNumber(etContactNo.getText().toString().trim());
						requestObject.setCountryId(spCountry.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCountries.get(spCountry.getSelectedItemPosition() - 1).getId()) : 0);
						requestObject.setStateId(spState.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListStates.get(spState.getSelectedItemPosition() - 1).getId()) : 0);
						requestObject.setCityId(spCity.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCities.get(spCity.getSelectedItemPosition() - 1).getId()) : 0);

						callApiRequestCredentials(requestObject);
					}
				} else {
					Utility.toastOffline(LoginActivity.this);
				}
			}

			private boolean isInputsValid() {
				return inputValidator.validateStringPresence(etFirstName) &
						inputValidator.validateStringPresence(etLastName) &
						inputValidator.validateAllConstraintsEmail(etEmail) &
						inputValidator.validateStringPresence(etHomeAddress) &
						inputValidator.validateStringPresence(etSchoolName) &
						inputValidator.validateStringPresence(etContactNo) &
						checkOtherInputs();
			}
		});
	}

	private boolean checkOtherInputs() {
		strValidationMsg = "";
		if (isCountrySet() & isStateSet() & isCitySet()) {
			return true;
		} else {
			Utility.alert(LoginActivity.this, null, strValidationMsg);
			return false;
		}
	}

	private boolean isCitySet() {
		if (arrListCities != null && arrListCities.size() == 0 || spCity.getSelectedItemPosition() > 0) {
			return true;
		} else {
			strValidationMsg += getString(R.string.msg_validation_city);
			return false;
		}
	}

	private boolean isStateSet() {
		if (arrListStates != null && arrListStates.size() == 0 || spState.getSelectedItemPosition() > 0) {
			return true;
		} else {
			strValidationMsg += getString(R.string.msg_validation_state);
			return false;
		}
	}

	private boolean isCountrySet() {
		if (arrListCountries != null && spCountry.getSelectedItemPosition() > 0) {
			return true;
		} else {
			strValidationMsg += getString(R.string.msg_validation_country);
			return false;
		}
	}

	private void callApiGetCountries() {
		try {
			progCountry.setVisibility(View.VISIBLE);
			progCountry.setProgress(1);
			progressGenerator.start(progCountry);
			new WebserviceWrapper(LoginActivity.this, null, this).new WebserviceCaller()
					.execute(WebConstants.GET_COUNTRIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetStates(int countryId) {
		try {
			progState.setVisibility(View.VISIBLE);
			progState.setProgress(1);
			progressGenerator.start(progState);
			RequestObject requestObject = new RequestObject();
			requestObject.setCountryId(countryId);

			new WebserviceWrapper(LoginActivity.this, requestObject, this).new WebserviceCaller()
					.execute(WebConstants.GET_STATES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetStates Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetCities(int stateId) {
		try {
			progCity.setVisibility(View.VISIBLE);
			progCity.setProgress(1);
			progressGenerator.start(progCity);
			RequestObject requestObject = new RequestObject();
			requestObject.setStateId(stateId);

			new WebserviceWrapper(LoginActivity.this, requestObject, this).new WebserviceCaller()
					.execute(WebConstants.GET_CITIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCities Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiRequestCredentials(RequestObject requestObject) {
		try {
			btnCredentialsSubmit.setEnabled(false);
			progRequestCredentials.setVisibility(View.VISIBLE);
			progRequestCredentials.setProgress(1);
			progressGenerator.start(progRequestCredentials);
			new WebserviceWrapper(LoginActivity.this, requestObject, this).new WebserviceCaller()
					.execute(WebConstants.REQUEST_CREDENTIALS);
		} catch (Exception e) {
			Log.e(TAG, "callApiRequestCredentials Exception : " + e.getLocalizedMessage());
		}
	}

	private boolean isInputsValid() {
		return inputValidator.validateStringPresence(etUserid) &
				(inputValidator.validateStringPresence(etPwd) && inputValidator.validatePasswordLength(etPwd));
	}

	private void callApiForgotPassword(String email) {
		try {
			btnForgotPwdSubmit.setEnabled(false);
			progForgotPwd.setVisibility(View.VISIBLE);
			progForgotPwd.setProgress(1);
			progressGenerator.start(progForgotPwd);
			RequestObject requestObject = new RequestObject();
			requestObject.setEmailId(email);

			new WebserviceWrapper(LoginActivity.this, requestObject, this).new WebserviceCaller()
					.execute(WebConstants.FORGOT_PASSWORD);

		} catch (Exception e) {
			Log.e(TAG, "callApiForgotPassword Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiAuthenticateUser() {
		try {
			btnLogin.setProgress(1);
			btnLogin.setEnabled(false);
			progressGenerator.start(btnLogin);

			btnLogin.setEnabled(false);
			RequestObject requestObject = new RequestObject();
			requestObject.setUsername(etUserid.getText().toString().trim());
			requestObject.setPassword(etPwd.getText().toString().trim());
//			requestObject.setUsername("0YGAJ8793B");
//			requestObject.setPassword("narola21");

			new WebserviceWrapper(LoginActivity.this, requestObject, this).new WebserviceCaller()
					.execute(WebConstants.LOGIN);

		} catch (Exception e) {
			Log.e(TAG, "callApiAuthenticateUser Exception : " + e.getLocalizedMessage());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.LOGIN:
					onResponseLogin(object, error);
					break;
				case WebConstants.FORGOT_PASSWORD:
					onResponseForgotPassword(object, error);
					break;
				case WebConstants.GET_COUNTRIES:
					onResponseCountries(object, error);
					break;
				case WebConstants.GET_STATES:
					onResponseStates(object, error);
					break;
				case WebConstants.GET_CITIES:
					onResponseCities(object, error);
					break;
				case WebConstants.REQUEST_CREDENTIALS:
					onResponseCredentials(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseCities(Object object, Exception error) {
		try {
			progCity.setProgress(100);
			progCity.setVisibility(View.INVISIBLE);
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					arrListCities = new ArrayList<Data>();
					arrListCities.addAll(responseObj.getData());
					List<String> cities = new ArrayList<String>();
					cities.add(getString(R.string.select));
					for (Data city : arrListCities) {
						cities.add(city.getCityName());
					}
					Adapters.setUpSpinner(LoginActivity.this, spCity, cities);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onResponseCities Failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseCities api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseCities Exception : " + e.toString());
		}
	}

	private void onResponseStates(Object object, Exception error) {
		try {
			progState.setProgress(100);
			progState.setVisibility(View.INVISIBLE);
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					arrListStates = new ArrayList<Data>();
					arrListStates.addAll(responseObj.getData());
					List<String> states = new ArrayList<String>();
					states.add(getString(R.string.select));
					for (Data state : arrListStates) {
						states.add(state.getStateName());
					}
					Adapters.setUpSpinner(LoginActivity.this, spState, states);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onResponseStates Failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseStates api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseStates Exception : " + e.toString());
		}
	}

	private void onResponseCountries(Object object, Exception error) {
		try {
			progCountry.setProgress(100);
			progCountry.setVisibility(View.INVISIBLE);
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					arrListCountries = new ArrayList<Data>();
					arrListCountries.addAll(responseObj.getData());
					List<String> countries = new ArrayList<String>();
					countries.add(getString(R.string.select));
					for (Data country : arrListCountries) {
						countries.add(country.getCountryName());
					}
					Adapters.setUpSpinner(LoginActivity.this, spCountry, countries);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onResponseCountries Failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseCountries api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseCountries Exception : " + e.toString());
		}
	}

	private void onResponseCredentials(Object object, Exception error) {
		try {
			if (btnCredentialsSubmit != null) {
				btnCredentialsSubmit.setEnabled(true);
			}
			if (progRequestCredentials != null) {
				progRequestCredentials.setProgress(100);
				progRequestCredentials.setVisibility(View.INVISIBLE);
			}
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					if (dialogCredentials != null) {
						dialogCredentials.dismiss();
					}
					Toast.makeText(LoginActivity.this, "Request for credentials sent to admin successfully.", Toast.LENGTH_LONG).show();
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Toast.makeText(LoginActivity.this, responseObj.getMessage(), Toast.LENGTH_LONG).show();
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseCredentials api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseCredentials Exception : " + e.toString());
		}
	}

	private void onResponseForgotPassword(Object object, Exception error) {
		try {
			if (btnForgotPwdSubmit != null) {
				btnForgotPwdSubmit.setEnabled(true);
			}
			if (progForgotPwd != null) {
				progForgotPwd.setProgress(100);
				progForgotPwd.setVisibility(View.INVISIBLE);
			}
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					Toast.makeText(LoginActivity.this, R.string.password_sent, Toast.LENGTH_LONG).show();
					dialogForgotPassword.dismiss();
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Toast.makeText(LoginActivity.this, R.string.email_not_found, Toast.LENGTH_LONG).show();
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseForgotPassword api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseForgotPassword Exception : " + e.toString());
		}
	}

	private void onResponseLogin(Object object, Exception error) {
		try {
			btnLogin.setProgress(100);
			btnLogin.setEnabled(true);
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {

					if (responseObj.getData().get(0).getUserId() == null) {

						PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, LoginActivity.this, ((CheckBox) findViewById(R.id.chk_rememberme)).isChecked());
						PreferenceData.setStringPrefs(PreferenceData.USER_CREDENTIAL_ID, LoginActivity.this, responseObj.getData().get(0).getCredentialId());
						PreferenceData.setStringPrefs(PreferenceData.USER_PASSWORD, LoginActivity.this, etPwd.getText().toString().trim());
						PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_ID, LoginActivity.this, responseObj.getData().get(0).getSchoolId());
						PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_NAME, LoginActivity.this, responseObj.getData().get(0).getSchoolName());
						PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_DISTRICT, LoginActivity.this, responseObj.getData().get(0).getSchoolDestrict());
						PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_TYPE, LoginActivity.this, responseObj.getData().get(0).getSchoolType());
						PreferenceData.setStringPrefs(PreferenceData.USER_CLASS_ID, LoginActivity.this, responseObj.getData().get(0).getClassId());
						PreferenceData.setStringPrefs(PreferenceData.USER_CLASS_NAME, LoginActivity.this, responseObj.getData().get(0).getClassName());
						PreferenceData.setStringPrefs(PreferenceData.USER_COURSE_ID, LoginActivity.this, responseObj.getData().get(0).getCourseId());
						PreferenceData.setStringPrefs(PreferenceData.USER_COURSE_NAME, LoginActivity.this, responseObj.getData().get(0).getCourseName());
						PreferenceData.setStringPrefs(PreferenceData.USER_ACADEMIC_YEAR, LoginActivity.this, responseObj.getData().get(0).getAcademicYear());
						PreferenceData.setStringPrefs(PreferenceData.USER_ROLE_ID, LoginActivity.this, responseObj.getData().get(0).getRoleId());

						launchProfileInfoActivity();

					} else {

						PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, LoginActivity.this, ((CheckBox) findViewById(R.id.chk_rememberme)).isChecked());
						PreferenceData.setStringPrefs(PreferenceData.USER_ID, LoginActivity.this, responseObj.getData().get(0).getUserId());
						PreferenceData.setStringPrefs(PreferenceData.USER_FULL_NAME, LoginActivity.this, responseObj.getData().get(0).getFullName());
						PreferenceData.setStringPrefs(PreferenceData.USER_PROFILE_PIC, LoginActivity.this, responseObj.getData().get(0).getProfilePic());
						PreferenceData.setStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, LoginActivity.this, responseObj.getData().get(0).getTutorialGroupId());
						PreferenceData.setStringPrefs(PreferenceData.TUTORIAL_GROUP_NAME, LoginActivity.this, responseObj.getData().get(0).getTutorialGroupName());

						if (responseObj.getData().get(0).getTutorialGroupId() == null) {
							PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ALLOCATED, LoginActivity.this, false);
							launchWelcomeActivity();
						} else {
							PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ALLOCATED, LoginActivity.this, true);

							if (responseObj.getData().get(0).getTutorialGroupJoiningStatus().equals("1")) {
								PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, LoginActivity.this, true);

								if (responseObj.getData().get(0).getTutorialGroupComplete().equals("1")) {
									PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_COMPLETED, LoginActivity.this, true);
									launchHostActivity();
								} else {
									launchAcceptTutorialGroupActivity();
								}
							} else {
								PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, LoginActivity.this, false);
								launchAcceptTutorialGroupActivity();
							}
						}

					}

				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Toast.makeText(LoginActivity.this, "Username or Password is wrong!", Toast.LENGTH_LONG).show();
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseLogin api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseLogin Exception : " + e.toString());
		}
	}

	private void launchProfileInfoActivity() {
		Utility.launchIntent(LoginActivity.this, ProfileInformationActivity.class);
		finish();
	}

	private void launchHostActivity() {
		Utility.launchIntent(LoginActivity.this, HostActivity.class);
		finish();
	}

	private void launchWelcomeActivity() {
		Utility.launchIntent(LoginActivity.this, WelComeActivity.class);
		finish();
	}

	private void launchAcceptTutorialGroupActivity() {
		Utility.launchIntent(LoginActivity.this, AcceptTutorialGroupActivity.class);
		finish();
	}

}
