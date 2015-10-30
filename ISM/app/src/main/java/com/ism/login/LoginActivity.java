package com.ism.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.HostActivity;
import com.ism.R;
import com.ism.adapter.Adapters;
import com.ism.model.CredentialsRequest;
import com.ism.model.Data;
import com.ism.model.ForgotPasswordRequest;
import com.ism.model.GetCitiesRequest;
import com.ism.model.GetStatesRequest;
import com.ism.model.LoginRequest;
import com.ism.model.ResponseObject;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.WebserviceWrapper;

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

	private InputValidator inputValidator;
	private ArrayList<Data> arrListCountries;
	private ArrayList<Data> arrListStates;
	private ArrayList<Data> arrListCities;
	private List<String> arrListDefalt;
	private AlertDialog dialogCredentials;

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
	}

	public void onClickLogin(View view) {
		if (isInputsValid()) {
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
						if (Utility.isOnline(LoginActivity.this)) {
							if (inputValidator.validateAllConstraintsEmail(etEmail)) {
								callApiForgotPassword(etEmail.getText().toString().trim());
							}
						} else {
							Utility.toastOffline(LoginActivity.this);
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

		callApiGetCountries();

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

				}).setNegativeButton(R.string.strcancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setCancelable(false);
		dialogCredentials = builder.create();
		dialogCredentials.show();
		dialogCredentials.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Utility.isOnline(LoginActivity.this)) {
					if (isInputsValid()) {
						CredentialsRequest credentialsRequest = new CredentialsRequest();
						credentialsRequest.setFirstname(etFirstName.getText().toString().trim());
						credentialsRequest.setLastname(etLastName.getText().toString().trim());
						credentialsRequest.setEmailAddress(etEmail.getText().toString().trim());
						credentialsRequest.setHomeAddress(etHomeAddress.getText().toString().trim());
						credentialsRequest.setSchoolName(etSchoolName.getText().toString().trim());
						credentialsRequest.setContactNumber(etContactNo.getText().toString().trim());
						credentialsRequest.setCountryId(spCountry.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCountries.get(spCountry.getSelectedItemPosition() - 1).getId()) : 0);
						credentialsRequest.setStateId(spState.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListStates.get(spState.getSelectedItemPosition() - 1).getId()) : 0);
						credentialsRequest.setCityId(spCity.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCities.get(spCity.getSelectedItemPosition() - 1).getId()) : 0);
						callApiRequestCredentials(credentialsRequest);
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
			new WebserviceWrapper(LoginActivity.this, null).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_COUNTRIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetStates(int countryId) {
		try {
			GetStatesRequest statesRequest = new GetStatesRequest();
			statesRequest.setCountryId(countryId);

			new WebserviceWrapper(LoginActivity.this, statesRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_STATES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetStates Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetCities(int stateId) {
		try {
			GetCitiesRequest citiesRequest = new GetCitiesRequest();
			citiesRequest.setStateId(stateId);

			new WebserviceWrapper(LoginActivity.this, citiesRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_CITIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCities Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiRequestCredentials(CredentialsRequest credentialsRequest) {
		try {
			new WebserviceWrapper(LoginActivity.this, credentialsRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.REQUEST_CREDENTIALS);
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
		try {
			if (object != null) {
				switch (apiCode) {
					case WebserviceWrapper.LOGIN:
						onResponseLogin(object);
						break;
					case WebserviceWrapper.FORGOT_PASSWORD:
						onResponseForgotPassword(object);
						break;
					case WebserviceWrapper.GET_COUNTRIES:
						onResponseCountries(object);
						break;
					case WebserviceWrapper.GET_STATES:
						onResponseStates(object);
						break;
					case WebserviceWrapper.GET_CITIES:
						onResponseCities(object);
						break;
					case WebserviceWrapper.REQUEST_CREDENTIALS:
						onResponseCredentials(object);
						break;
				}
			} else if (error != null) {
				Log.e(TAG, "onResponse ApiCall Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseCities(Object object) {
		try {
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
		} catch (Exception e) {
			Log.e(TAG, "onResponseCities Exception : " + e.toString());
		}
	}

	private void onResponseStates(Object object) {
		try {
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
		} catch (Exception e) {
			Log.e(TAG, "onResponseStates Exception : " + e.toString());
		}
	}

	private void onResponseCountries(Object object) {
		try {
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
		} catch (Exception e) {
			Log.e(TAG, "onResponseCountries Exception : " + e.toString());
		}
	}

	private void onResponseCredentials(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				if (dialogCredentials != null) {
					dialogCredentials.dismiss();
				}
				Toast.makeText(LoginActivity.this, "Request for credentials sent to admin successfully.", Toast.LENGTH_LONG).show();
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Toast.makeText(LoginActivity.this, responseObj.getMessage(), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseCredentials Exception : " + e.toString());
		}
	}

	private void onResponseForgotPassword(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				Toast.makeText(LoginActivity.this, R.string.password_sent, Toast.LENGTH_LONG).show();
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Toast.makeText(LoginActivity.this, R.string.email_not_found, Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseForgotPassword Exception : " + e.toString());
		}
	}

	private void onResponseLogin(Object object) {
		ResponseObject responseObj = (ResponseObject) object;
		if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {

			if (responseObj.getData().get(0).getUserId() == null) {

				PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, LoginActivity.this, ((CheckBox) findViewById(R.id.chk_rememberme)).isChecked());
				PreferenceData.setStringPrefs(PreferenceData.CREDENTIAL_ID, LoginActivity.this, responseObj.getData().get(0).getCredentialId());
				PreferenceData.setStringPrefs(PreferenceData.PASSWORD, LoginActivity.this, etPwd.getText().toString().trim());
				PreferenceData.setStringPrefs(PreferenceData.SCHOOL_ID, LoginActivity.this, responseObj.getData().get(0).getSchoolId());
				PreferenceData.setStringPrefs(PreferenceData.SCHOOL_NAME, LoginActivity.this, responseObj.getData().get(0).getSchoolName());
				PreferenceData.setStringPrefs(PreferenceData.SCHOOL_DISTRICT, LoginActivity.this, responseObj.getData().get(0).getSchoolDestrict());
				PreferenceData.setStringPrefs(PreferenceData.SCHOOL_TYPE, LoginActivity.this, responseObj.getData().get(0).getSchoolType());
				PreferenceData.setStringPrefs(PreferenceData.CLASS_ID, LoginActivity.this, responseObj.getData().get(0).getClassId());
				PreferenceData.setStringPrefs(PreferenceData.CLASS_NAME, LoginActivity.this, responseObj.getData().get(0).getClassName());
				PreferenceData.setStringPrefs(PreferenceData.COURSE_ID, LoginActivity.this, responseObj.getData().get(0).getCourseId());
				PreferenceData.setStringPrefs(PreferenceData.COURSE_NAME, LoginActivity.this, responseObj.getData().get(0).getCourseName());
				PreferenceData.setStringPrefs(PreferenceData.ACADEMIC_YEAR, LoginActivity.this, responseObj.getData().get(0).getAcademicYear());
				PreferenceData.setStringPrefs(PreferenceData.ROLE_ID, LoginActivity.this, responseObj.getData().get(0).getRoleId());

				launchProfileInfoActivity();

			} else {

				PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, LoginActivity.this, ((CheckBox) findViewById(R.id.chk_rememberme)).isChecked());
				PreferenceData.setStringPrefs(PreferenceData.USER_ID, LoginActivity.this, Global.userId = responseObj.getData().get(0).getUserId());
				PreferenceData.setStringPrefs(PreferenceData.FULL_NAME, LoginActivity.this, Global.fullName = responseObj.getData().get(0).getFullName());
				PreferenceData.setStringPrefs(PreferenceData.PROFILE_PIC, LoginActivity.this, Global.profilePic = responseObj.getData().get(0).getProfilePic());

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
