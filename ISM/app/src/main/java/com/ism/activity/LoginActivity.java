package com.ism.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.adapter.Adapters;
import com.ism.broadcastReceiver.NetworkStatusReceiver;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.WebConstants;
import com.ism.interfaces.NetworkStateListener;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.InputValidator;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.AdminConfig;
import com.ism.ws.model.City;
import com.ism.ws.model.Country;
import com.ism.ws.model.State;
import com.ism.ws.model.User;
import com.realm.ismrealm.RealmAdaptor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import realmhelper.StudentHelper;

/**
 * Created by c161 on 07/10/15.
 */
public class LoginActivity extends Activity implements WebserviceWrapper.WebserviceResponse, NetworkStateListener {

	private static final String TAG = LoginActivity.class.getSimpleName();

	private EditText etUserName, etPwd;
	private Spinner spCountry;
	private Spinner spState;
	private Spinner spCity;
	private LinearLayout llLogin;
	private ActionProcessButton btnLogin, progForgotPwd, progRequestCredentials, progCountry, progState, progCity;
	private Button btnForgotPwdSubmit, btnCredentialsSubmit;

	private InputValidator inputValidator;
	private ArrayList<Country> arrListCountries;
	private ArrayList<State> arrListStates;
	private ArrayList<City> arrListCities;
	private List<String> arrListDefalt;
	private AlertDialog dialogCredentials;
	private ProgressGenerator progressGenerator;
	private AlertDialog dialogForgotPassword;
	private MyTypeFace myTypeFace;
	private StudentHelper studentHelper;

	private String strValidationMsg;
	private boolean isRememberMe;
	private boolean isRememberMeFirstLogin;
	private boolean isAdminConfigSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		studentHelper = new StudentHelper(LoginActivity.this);

		isAdminConfigSet = false;
		isRememberMe = PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME, LoginActivity.this);
		isRememberMeFirstLogin = PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, LoginActivity.this);

		NetworkStatusReceiver.setNetworkStateListener(this);

		if (Utility.isConnected(this)) {
			initializeData();
		} else {
			Utility.alertOffline(this);
		}

	}

	private void initializeData() {
		if (isRememberMe || isRememberMeFirstLogin) {
			WebConstants.ACCESS_KEY = PreferenceData.getStringPrefs(PreferenceData.ACCESS_KEY, this);
			WebConstants.SECRET_KEY = PreferenceData.getStringPrefs(PreferenceData.SECRET_KEY, this);
			//callApiGetAdminConfig();
			resumeApp();
		} else {
			PreferenceData.clearWholePreference(this);
			PreferenceData.setStringPrefs(PreferenceData.ACCESS_KEY, this, WebConstants.NO_USERNAME);
			WebConstants.ACCESS_KEY = WebConstants.NO_USERNAME;
			WebConstants.SECRET_KEY = null;
			callApiRefreshToken();
		}
	}

	private void resumeApp() {
		if (isRememberMe) {
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
		} else if (isRememberMeFirstLogin) {
			launchProfileInfoActivity();
		} else {
			initGlobal();
		}
	}

	private void initGlobal() {
		myTypeFace = new MyTypeFace(this);
		btnLogin = (ActionProcessButton) findViewById(R.id.btn_login);
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etUserName = (EditText) findViewById(R.id.et_userid);
		llLogin = (LinearLayout) findViewById(R.id.ll_login);

		showLoginLayout();

//		etUserName.setText("0YGAJ8793B");
//		etUserName.setText("prerna");
//		etPwd.setText("narola21");

		etUserName.setTypeface(myTypeFace.getRalewayRegular());
		etPwd.setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_donothave)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_clickhere)).setTypeface(myTypeFace.getRalewayRegular());
		((TextView) findViewById(R.id.txt_forgotpwd)).setTypeface(myTypeFace.getRalewayRegular());

		inputValidator = new InputValidator(LoginActivity.this);

		arrListDefalt = new ArrayList<>();
		arrListDefalt.add(getString(R.string.select));

        progressGenerator = new ProgressGenerator();

        RealmAdaptor.getInstance(this);
    }

	private void showLoginLayout() {
		llLogin.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
		llLogin.setVisibility(View.VISIBLE);
		findViewById(R.id.img_logo).startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
	}

	public void onClickLogin(View view) {
		if (Utility.isConnected(LoginActivity.this)) {
			if (isInputsValid()) {
				btnLogin.setProgress(1);
				btnLogin.setEnabled(false);
				progressGenerator.start(btnLogin);

//				String globalPassword = studentHelper.getGlobalPassword();
//				if (globalPassword != null) {
//					WebConstants.ACCESS_KEY = AESHelper.encrypt(globalPassword, etUserName.getText().toString().trim());
//					PreferenceData.setStringPrefs(PreferenceData.ACCESS_KEY, LoginActivity.this, WebConstants.ACCESS_KEY);
//					callApiRefreshToken();
//				}
				callApiAuthenticateUser();
			}
		} else {
			Utility.alertOffline(LoginActivity.this);
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
				if (Utility.isConnected(LoginActivity.this)) {
					if (inputValidator.validateAllConstraintsEmail(etEmail)) {
						callApiForgotPassword(etEmail.getText().toString().trim());
					}
				} else {
					Utility.alertOffline(LoginActivity.this);
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

		if (Utility.isConnected(LoginActivity.this)) {
			callApiGetCountries();
		} else {
			Utility.alertOffline(LoginActivity.this);
		}

		spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (arrListCountries != null && position > 0) {
					if (Utility.isConnected(LoginActivity.this)) {
						callApiGetStates(arrListCountries.get(position - 1).getId());
					} else {
						Utility.alertOffline(LoginActivity.this);
					}
				} else {
					Adapters.setUpSpinner(LoginActivity.this, spState, arrListDefalt, myTypeFace.getRalewayRegular());
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
					if (Utility.isConnected(LoginActivity.this)) {
						callApiGetCities(Integer.parseInt(arrListStates.get(position - 1).getId()));
					} else {
						Utility.alertOffline(LoginActivity.this);
					}
				} else {
					Adapters.setUpSpinner(LoginActivity.this, spCity, arrListDefalt, myTypeFace.getRalewayRegular());
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
				if (Utility.isConnected(LoginActivity.this)) {
					if (isInputsValid()) {
						Attribute attribute = new Attribute();
						attribute.setFirstname(etFirstName.getText().toString().trim());
						attribute.setLastname(etLastName.getText().toString().trim());
						attribute.setEmailAddress(etEmail.getText().toString().trim());
						attribute.setHomeAddress(etHomeAddress.getText().toString().trim());
						attribute.setSchoolName(etSchoolName.getText().toString().trim());
						attribute.setContactNumber(etContactNo.getText().toString().trim());
						attribute.setCountryId(spCountry.getSelectedItemPosition() > 0 ? arrListCountries.get(spCountry.getSelectedItemPosition() - 1).getId() : "0");
						attribute.setStateId(spState.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListStates.get(spState.getSelectedItemPosition() - 1).getId()) : 0);
						attribute.setCityId(spCity.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCities.get(spCity.getSelectedItemPosition() - 1).getId()) : 0);

						callApiRequestCredentials(attribute);
					}
				} else {
					Utility.alertOffline(LoginActivity.this);
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

	private void callApiGetStates(String countryId) {
		try {
			progState.setVisibility(View.VISIBLE);
			progState.setProgress(1);
			progressGenerator.start(progState);
			Attribute attribute = new Attribute();
			attribute.setCountryId(countryId);

			new WebserviceWrapper(LoginActivity.this, attribute, this).new WebserviceCaller()
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
			Attribute attribute = new Attribute();
			attribute.setStateId(stateId);

			new WebserviceWrapper(LoginActivity.this, attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_CITIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCities Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiRequestCredentials(Attribute attribute) {
		try {
			btnCredentialsSubmit.setEnabled(false);
			progRequestCredentials.setVisibility(View.VISIBLE);
			progRequestCredentials.setProgress(1);
			progressGenerator.start(progRequestCredentials);
			new WebserviceWrapper(LoginActivity.this, attribute, this).new WebserviceCaller()
					.execute(WebConstants.REQUEST_CREDENTIALS);
		} catch (Exception e) {
			Log.e(TAG, "callApiRequestCredentials Exception : " + e.getLocalizedMessage());
		}
	}

	private boolean isInputsValid() {
		return inputValidator.validateStringPresence(etUserName) &
				(inputValidator.validateStringPresence(etPwd) && inputValidator.validatePasswordLength(etPwd));
	}

	private void callApiForgotPassword(String email) {
		try {
			btnForgotPwdSubmit.setEnabled(false);
			progForgotPwd.setVisibility(View.VISIBLE);
			progForgotPwd.setProgress(1);
			progressGenerator.start(progForgotPwd);
			Attribute attribute = new Attribute();
			attribute.setEmailId(email);

			new WebserviceWrapper(LoginActivity.this, attribute, this).new WebserviceCaller()
					.execute(WebConstants.FORGOT_PASSWORD);

		} catch (Exception e) {
			Log.e(TAG, "callApiForgotPassword Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiAuthenticateUser() {
		try {
			Attribute attribute = new Attribute();
			attribute.setUsername(etUserName.getText().toString().trim());
			attribute.setPassword(etPwd.getText().toString().trim());
//			requestObject.setUsername("0YGAJ8793B");
//			requestObject.setPassword("narola21");

			new WebserviceWrapper(LoginActivity.this, attribute, this).new WebserviceCaller()
					.execute(WebConstants.LOGIN);

		} catch (Exception e) {
			Log.e(TAG, "callApiAuthenticateUser Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetAdminConfig() {
		try {
			Attribute attribute = new Attribute();
			attribute.setRole("All");
			attribute.setLastSyncDate(PreferenceData.getStringPrefs(PreferenceData.SYNC_DATE_ADMIN_CONFIG, LoginActivity.this, ""));

			new WebserviceWrapper(LoginActivity.this, attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_ADMIN_CONFIG);
		} catch (Exception e) {
			Log.e(TAG, "callApiAuthenticateUser Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiRefreshToken() {
		try {
			Attribute attribute = new Attribute();
			attribute.setUsername(WebConstants.ACCESS_KEY);

			new WebserviceWrapper(getApplicationContext(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.REFRESH_TOKEN);
		} catch (Exception e) {
			Log.e(TAG, "callApiRefreshToken Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_ADMIN_CONFIG:
					onResponseGetAdminConfig(object, error);
					break;
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
				case WebConstants.REFRESH_TOKEN:
					onResponseRefreshToken(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetAdminConfig(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					ArrayList<AdminConfig> arrListAdminConfig = responseHandler.getAdminConfig();
					Log.e(TAG, "admin config size : " + arrListAdminConfig.size());

					if (arrListAdminConfig != null && arrListAdminConfig.size() > 0) {
						for (AdminConfig config : arrListAdminConfig) {
							model.AdminConfig adminConfig = new model.AdminConfig();
							adminConfig.setConfigKey(config.getConfigKey());
							adminConfig.setConfigValue(config.getConfigValue());
							studentHelper.saveAdminConfig(adminConfig);
						}
						PreferenceData.setStringPrefs(PreferenceData.SYNC_DATE_ADMIN_CONFIG, LoginActivity.this,
								Utility.formatDateMySql(Calendar.getInstance().getTime()));
						isAdminConfigSet = true;
					}
					resumeApp();

				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetAdminConfig Failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetAdminConfig api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetAdminConfig Exception : " + e.toString());
		}
	}

	private void onResponseRefreshToken(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					PreferenceData.setStringPrefs(PreferenceData.SECRET_KEY, this, responseHandler.getToken().get(0).getTokenName());
					WebConstants.SECRET_KEY = responseHandler.getToken().get(0).getTokenName();
					if (isAdminConfigSet) {
						callApiAuthenticateUser();
					} else {
						callApiGetAdminConfig();
					}
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseRefreshToken failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseRefreshToken api Exception :" + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseRefreshToken Exception :" + e.toString());
		}
	}

	private void onResponseCities(Object object, Exception error) {
		try {
			progCity.setProgress(100);
			progCity.setVisibility(View.INVISIBLE);
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					arrListCities = new ArrayList<>();
					arrListCities.addAll(responseHandler.getCities());
					List<String> cities = new ArrayList<>();
					cities.add(getString(R.string.select));
					for (City city : arrListCities) {
						cities.add(city.getCityName());
					}
					Adapters.setUpSpinner(LoginActivity.this, spCity, cities, myTypeFace.getRalewayRegular());
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
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
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					arrListStates = new ArrayList<>();
					arrListStates.addAll(responseHandler.getStates());
					List<String> states = new ArrayList<>();
					states.add(getString(R.string.select));
					for (State state : arrListStates) {
						states.add(state.getStateName());
					}
					Adapters.setUpSpinner(LoginActivity.this, spState, states, myTypeFace.getRalewayRegular());
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
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
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					arrListCountries = new ArrayList<>();
					arrListCountries.addAll(responseHandler.getCountries());
					List<String> countries = new ArrayList<>();
					countries.add(getString(R.string.select));
					for (Country country : arrListCountries) {
						countries.add(country.getCountryName());
					}
					Adapters.setUpSpinner(LoginActivity.this, spCountry, countries, myTypeFace.getRalewayRegular());
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
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
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					if (dialogCredentials != null) {
						dialogCredentials.dismiss();
					}
					Toast.makeText(LoginActivity.this, "Request for credentials sent to admin successfully.", Toast.LENGTH_LONG).show();
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Toast.makeText(LoginActivity.this, responseHandler.getMessage(), Toast.LENGTH_LONG).show();
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
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					Toast.makeText(LoginActivity.this, R.string.password_sent, Toast.LENGTH_LONG).show();
					dialogForgotPassword.dismiss();
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
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
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

					if (responseHandler.getUser().get(0).getUserId() == null) {

						PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, LoginActivity.this, ((CheckBox) findViewById(R.id.chk_rememberme)).isChecked());
						PreferenceData.setStringPrefs(PreferenceData.USER_CREDENTIAL_ID, LoginActivity.this, responseHandler.getUser().get(0).getCredentialId());
						PreferenceData.setStringPrefs(PreferenceData.USER_PASSWORD, LoginActivity.this, etPwd.getText().toString().trim());
						PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_ID, LoginActivity.this, responseHandler.getUser().get(0).getSchoolId());
						PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_NAME, LoginActivity.this, responseHandler.getUser().get(0).getSchoolName());
						PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_DISTRICT, LoginActivity.this, responseHandler.getUser().get(0).getSchoolDistrict());
						PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_TYPE, LoginActivity.this, responseHandler.getUser().get(0).getSchoolType());
						PreferenceData.setStringPrefs(PreferenceData.USER_CLASS_ID, LoginActivity.this, responseHandler.getUser().get(0).getClassId());
						PreferenceData.setStringPrefs(PreferenceData.USER_CLASS_NAME, LoginActivity.this, responseHandler.getUser().get(0).getClassName());
						PreferenceData.setStringPrefs(PreferenceData.USER_COURSE_ID, LoginActivity.this, responseHandler.getUser().get(0).getCourseId());
						PreferenceData.setStringPrefs(PreferenceData.USER_COURSE_NAME, LoginActivity.this, responseHandler.getUser().get(0).getCourseName());
						PreferenceData.setStringPrefs(PreferenceData.USER_ACADEMIC_YEAR, LoginActivity.this, responseHandler.getUser().get(0).getAcademicYear());
						PreferenceData.setStringPrefs(PreferenceData.USER_ROLE_ID, LoginActivity.this, responseHandler.getUser().get(0).getRoleId());

						launchProfileInfoActivity();

					} else {

						PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, LoginActivity.this, ((CheckBox) findViewById(R.id.chk_rememberme)).isChecked());
						PreferenceData.setStringPrefs(PreferenceData.USER_ID, LoginActivity.this, responseHandler.getUser().get(0).getUserId());
						PreferenceData.setStringPrefs(PreferenceData.USER_FULL_NAME, LoginActivity.this, responseHandler.getUser().get(0).getFullName());
						PreferenceData.setStringPrefs(PreferenceData.USER_PROFILE_PIC, LoginActivity.this, responseHandler.getUser().get(0).getProfilePic());
						PreferenceData.setStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, LoginActivity.this, responseHandler.getUser().get(0).getTutorialGroupId());
						PreferenceData.setStringPrefs(PreferenceData.TUTORIAL_GROUP_NAME, LoginActivity.this, responseHandler.getUser().get(0).getTutorialGroupName());
						PreferenceData.setStringPrefs(PreferenceData.USER_NAME, this, etUserName.getText().toString().trim());

//						if (responseHandler.getUser().get(0).getTutorialGroupId() == null) {
//							PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ALLOCATED, LoginActivity.this, false);
//							launchWelcomeActivity();
//						} else {
//							PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ALLOCATED, LoginActivity.this, true);
//
//							if (responseHandler.getUser().get(0).getTutorialGroupJoiningStatus().equals("1")) {
						PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, LoginActivity.this, true);
//
//								if (responseHandler.getUser().get(0).getTutorialGroupComplete().equals("1")) {
									PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_COMPLETED, LoginActivity.this, true);
									launchHostActivity();
//								} else {
//									launchAcceptTutorialGroupActivity();
//								}
//							} else {
//								PreferenceData.setBooleanPrefs(PreferenceData.IS_TUTORIAL_GROUP_ACCEPTED, LoginActivity.this, false);
//								launchAcceptTutorialGroupActivity();
//							}
//						}

					}

				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Toast.makeText(LoginActivity.this, "Username or Password is wrong!", Toast.LENGTH_LONG).show();
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseLogin api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseLogin Exception : " + e.toString());
		}
	}

    private void ParseAllData(ArrayList<User> user) {
        try {
            model.User userData = new model.User();
            userData.setUserId(Integer.parseInt(user.get(0).getUserId()));
            userData.setFullName(user.get(0).getFullName());
            userData.setProfilePicture(user.get(0).getProfilePic());
//			userData.setClassName(user.get(0).getClassName());
//			userData.setFirstName(user.get(0).getCourseName());
//			userData.setFirstName(user.get(0).getCourseName());
//			userData.setFirstName(user.get(0).getCourseName());
//			userData.setFirstName(user.get(0).getCourseName());
//			userData.setFirstName(user.get(0).getCourseName());
            StudentHelper studentHelper = new StudentHelper(this);
            studentHelper.saveUser(userData);

        } catch (Exception e) {
            Debug.i(TAG, "ParseAllData Exception : " + e.getLocalizedMessage());
        }
    }

    private void launchProfileInfoActivity() {
        Utility.launchActivity(LoginActivity.this, ProfileInformationActivity.class);
        finish();
    }

    private void launchHostActivity() {
        Utility.launchActivity(LoginActivity.this, HostActivity.class);
        finish();
    }

    private void launchWelcomeActivity() {
        Utility.launchActivity(LoginActivity.this, WelComeActivity.class);
        finish();
    }

    private void launchAcceptTutorialGroupActivity() {
        Utility.launchActivity(LoginActivity.this, AcceptTutorialGroupActivity.class);
        finish();
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		studentHelper.destroy();
	}

	@Override
	public void onNetworkStateChange() {
		if (Utility.isConnected(this)) {
			initializeData();
		}
	}

}
