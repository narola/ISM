package com.ism.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.adapter.Adapters;
import com.ism.model.Data;
import com.ism.model.GetCitiesRequest;
import com.ism.model.GetStatesRequest;
import com.ism.model.RegisterRequest;
import com.ism.model.ResponseObject;
import com.ism.model.SchoolInfoRequest;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.WebserviceWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by c161 on 07/10/15.
 */
public class ProfileInformationActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = ProfileInformationActivity.class.getSimpleName();

	private Spinner spGender, spCity, spCountry, spState;
	private TextView txtSchoolGender, txtAcademicYear, txtNameSchool, txtDistrictOfSchool, txtProgramCourse, txtClass;
	private EditText etAge, etUserName, etNewPwd, etHomeAddress, etFirstName, etLastName,
			etEmailAddress, etCurrentPwd, etContactNo, etConfirmPwd, etDob;
	private ImageView imgDp;

	private MyTypeFace myTypeFace;
	private InputValidator inputValidator;
	private List<String> arrListGender;
	private List<String> arrListDefalt;
	private ArrayList<Data> arrListCountries;
	private ArrayList<Data> arrListStates;
	private ArrayList<Data> arrListCities;
	private Calendar calDob;
	private DatePickerDialog datePickerDob;
	private AlertDialog dialogSchoolInfo;

	private String strUserId;
	private String strCurrentPassword;
	private String strSchoolId;
	private String strSchoolName;
	private String strSchoolDistrict;
	private String strSchoolType;
	private String strClassId;
	private String strClassName;
	private String strCourseId;
	private String strCourseName;
	private String strAcademicYear;
	private String strRoleId;
	private String strDpBase64;
	private String strValidationMsg;
	private String strDob;
	private long lngMaxDob;
	private int PICK_IMAGE_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profile_info);

		intitGlobal();

	}

	private void intitGlobal() {
		spGender = (Spinner) findViewById(R.id.sp_gender);
		spCity = (Spinner) findViewById(R.id.sp_city);
		spCountry = (Spinner) findViewById(R.id.sp_country);
		spState = (Spinner) findViewById(R.id.sp_state);
		etAge = (EditText) findViewById(R.id.et_age);
		etContactNo = (EditText) findViewById(R.id.et_cno);
		etConfirmPwd = (EditText) findViewById(R.id.et_confirmpwd);
		etCurrentPwd = (EditText) findViewById(R.id.et_currentpwd);
		etDob = (EditText) findViewById(R.id.txt_dob);
		etEmailAddress = (EditText) findViewById(R.id.et_emailAdd);
		etFirstName = (EditText) findViewById(R.id.et_firstname);
		etLastName = (EditText) findViewById(R.id.et_lastname);
		etHomeAddress = (EditText) findViewById(R.id.et_homeAdd);
		etNewPwd = (EditText) findViewById(R.id.et_newpwd);
		etUserName = (EditText) findViewById(R.id.et_username);
		imgDp = (ImageView) findViewById(R.id.img_dp_post_creator);
		txtSchoolGender = (TextView) findViewById(R.id.txt_schoolgender);
		txtAcademicYear = (TextView) findViewById(R.id.txt_academicyear);
		txtNameSchool = (TextView) findViewById(R.id.txt_nameofschool);
		txtDistrictOfSchool = (TextView) findViewById(R.id.txt_districtofschool);
		txtProgramCourse = (TextView) findViewById(R.id.txt_programcourse);
		txtClass = (TextView) findViewById(R.id.txt_class);

		myTypeFace = new MyTypeFace(this);
		((TextView) findViewById(R.id.txt_youare_)).setTypeface(myTypeFace.getRalewayThin());
		((TextView) findViewById(R.id.txt_ifits_)).setTypeface(myTypeFace.getRalewayThin());
		((TextView) findViewById(R.id.txt_clickhere)).setTypeface(myTypeFace.getRalewayThin());
		((TextView) findViewById(R.id.txt_uploadpic)).setTypeface(myTypeFace.getRalewayRegular());
		etAge.setTypeface(myTypeFace.getRalewayRegular());
		etContactNo.setTypeface(myTypeFace.getRalewayRegular());
		etConfirmPwd.setTypeface(myTypeFace.getRalewayRegular());
		etCurrentPwd.setTypeface(myTypeFace.getRalewayRegular());
		etDob.setTypeface(myTypeFace.getRalewayRegular());
		etEmailAddress.setTypeface(myTypeFace.getRalewayRegular());
		etFirstName.setTypeface(myTypeFace.getRalewayRegular());
		etLastName.setTypeface(myTypeFace.getRalewayRegular());
		etHomeAddress.setTypeface(myTypeFace.getRalewayRegular());
		etNewPwd.setTypeface(myTypeFace.getRalewayRegular());
		etUserName.setTypeface(myTypeFace.getRalewayRegular());
		txtNameSchool.setTypeface(myTypeFace.getRalewayRegular());
		txtDistrictOfSchool.setTypeface(myTypeFace.getRalewayRegular());
		txtSchoolGender.setTypeface(myTypeFace.getRalewayRegular());
		txtAcademicYear.setTypeface(myTypeFace.getRalewayRegular());
		txtProgramCourse.setTypeface(myTypeFace.getRalewayRegular());
		txtClass.setTypeface(myTypeFace.getRalewayRegular());

		inputValidator = new InputValidator(ProfileInformationActivity.this);

		strUserId = PreferenceData.getStringPrefs(PreferenceData.CREDENTIAL_ID, ProfileInformationActivity.this);
		strCurrentPassword = PreferenceData.getStringPrefs(PreferenceData.PASSWORD, ProfileInformationActivity.this);
		strSchoolId = PreferenceData.getStringPrefs(PreferenceData.SCHOOL_ID, ProfileInformationActivity.this);
		strSchoolName = PreferenceData.getStringPrefs(PreferenceData.SCHOOL_NAME, ProfileInformationActivity.this);
		strSchoolDistrict = PreferenceData.getStringPrefs(PreferenceData.SCHOOL_DISTRICT, ProfileInformationActivity.this);
		strSchoolType = PreferenceData.getStringPrefs(PreferenceData.SCHOOL_TYPE, ProfileInformationActivity.this);
		strClassId = PreferenceData.getStringPrefs(PreferenceData.CLASS_ID, ProfileInformationActivity.this);
		strClassName = PreferenceData.getStringPrefs(PreferenceData.CLASS_NAME, ProfileInformationActivity.this);
		strCourseId = PreferenceData.getStringPrefs(PreferenceData.COURSE_ID, ProfileInformationActivity.this);
		strCourseName = PreferenceData.getStringPrefs(PreferenceData.COURSE_NAME, ProfileInformationActivity.this);
		strAcademicYear = PreferenceData.getStringPrefs(PreferenceData.ACADEMIC_YEAR, ProfileInformationActivity.this);
		strRoleId = PreferenceData.getStringPrefs(PreferenceData.ROLE_ID, ProfileInformationActivity.this);

		txtNameSchool.setText(strSchoolName);
		txtClass.setText(strClassName);
		txtAcademicYear.setText(strAcademicYear);
		txtDistrictOfSchool.setText(strSchoolDistrict);
		txtSchoolGender.setText(strSchoolType);
		txtProgramCourse.setText(strCourseName);

		if (Utility.isOnline(ProfileInformationActivity.this)) {
			callApiGetCountries();
		} else {
			Utility.toastOffline(ProfileInformationActivity.this);
		}

		etDob.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					showDatePickerDob();
				}
				return true;
			}
		});

		spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (arrListCountries != null && position > 0) {
					if (Utility.isOnline(ProfileInformationActivity.this)) {
						callApiGetStates(Integer.parseInt(arrListCountries.get(position - 1).getId()));
					} else {
						Utility.toastOffline(ProfileInformationActivity.this);
					}
				} else {
					Adapters.setUpSpinner(ProfileInformationActivity.this, spState, arrListDefalt);
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
					if (Utility.isOnline(ProfileInformationActivity.this)) {
						callApiGetCities(Integer.parseInt(arrListStates.get(position - 1).getId()));
					} else {
						Utility.toastOffline(ProfileInformationActivity.this);
					}
				} else {
					Adapters.setUpSpinner(ProfileInformationActivity.this, spCity, arrListDefalt);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		arrListGender = new ArrayList<String>();
		arrListGender.add(getString(R.string.gender));
		arrListGender.add(getString(R.string.male));
		arrListGender.add(getString(R.string.female));
		Adapters.setUpSpinner(ProfileInformationActivity.this, spGender, arrListGender);

		arrListDefalt = new ArrayList<String>();
		arrListDefalt.add(getString(R.string.select));
		Adapters.setUpSpinner(ProfileInformationActivity.this, spCountry, arrListDefalt);
		Adapters.setUpSpinner(ProfileInformationActivity.this, spState, arrListDefalt);
		Adapters.setUpSpinner(ProfileInformationActivity.this, spCity, arrListDefalt);

	}

	private void showDatePickerDob() {
		try {
			if (calDob == null) {
				calDob = Calendar.getInstance();
				calDob.add(Calendar.YEAR, -3);
				lngMaxDob = calDob.getTimeInMillis();
			}
			datePickerDob = new DatePickerDialog(ProfileInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					calDob.set(Calendar.YEAR, year);
					calDob.set(Calendar.MONTH, monthOfYear);
					calDob.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					strDob = Utility.formatDateApi(calDob.getTime());
					etDob.setText(Utility.formatDateDisplay(calDob.getTime()));
				}
			}, calDob.get(Calendar.YEAR), calDob.get(Calendar.MONTH), calDob.get(Calendar.DAY_OF_MONTH));
			datePickerDob.getDatePicker().setMaxDate(lngMaxDob);
			datePickerDob.show();
		} catch (Exception e) {
			Log.e(TAG, "showDatePickerDob Exception : " + e.toString());
		}
	}

	public void onClickSubmit(View view) {
		if (Utility.isOnline(ProfileInformationActivity.this)) {

			PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, ProfileInformationActivity.this,
					PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, ProfileInformationActivity.this));
			PreferenceData.remove(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, ProfileInformationActivity.this);
			PreferenceData.setStringPrefs(PreferenceData.USER_ID, ProfileInformationActivity.this, "141");
			PreferenceData.setStringPrefs(PreferenceData.FULL_NAME, ProfileInformationActivity.this, "Krunal Panchal");
			PreferenceData.setStringPrefs(PreferenceData.PROFILE_PIC, ProfileInformationActivity.this, "user_374/logo_test.png");

			Intent intentWelcome = new Intent(ProfileInformationActivity.this, WelComeActivity.class);
			startActivity(intentWelcome);
			finish();

			/*if (isInputsValid()) {
                callApiRegisterUser();
			}*/
		} else {
			Utility.toastOffline(ProfileInformationActivity.this);
		}
	}

	public void onClickCancel(View view) {
		finish();
	}

	public void onClickUploadImage(View v) {
		openGallary();
	}

	public void onClickHere(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ProfileInformationActivity.this);
		LayoutInflater inflater = LayoutInflater.from(ProfileInformationActivity.this);
		View dialogView = inflater.inflate(R.layout.dialog_request_schoolinfo, null);
		final EditText etName = (EditText) dialogView.findViewById(R.id.et_name);
		final EditText etEmail = (EditText) dialogView.findViewById(R.id.et_email);
		final EditText etMessage = (EditText) dialogView.findViewById(R.id.et_message);

		builder.setView(dialogView)
				.setTitle("Request for School information correction")
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
		dialogSchoolInfo = builder.create();
		dialogSchoolInfo.show();
		dialogSchoolInfo.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Utility.isOnline(ProfileInformationActivity.this)) {
					if (isInputsValidSchoolInfo()) {
						SchoolInfoRequest schoolInfoRequest = new SchoolInfoRequest();
						schoolInfoRequest.setName(etName.getText().toString().trim());
						schoolInfoRequest.setEmailAddress(etEmail.getText().toString().trim());
						schoolInfoRequest.setMessage(etMessage.getText().toString().trim());
						callApiRequestSchoolInfo(schoolInfoRequest);
					}
				} else {
					Utility.toastOffline(ProfileInformationActivity.this);
				}
			}

			private boolean isInputsValidSchoolInfo() {
				return inputValidator.validateStringPresence(etName) &
						inputValidator.validateAllConstraintsEmail(etEmail) &
						inputValidator.validateStringPresence(etMessage);
			}
		});
	}

	private void callApiRequestSchoolInfo(SchoolInfoRequest schoolInfoRequest) {
		try {
			new WebserviceWrapper(ProfileInformationActivity.this, schoolInfoRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.REQUEST_SCHOOL_INFO);
		} catch (Exception e) {
			Log.e(TAG, "callApiRequestSchoolInfo Exception : " + e.toString());
		}
	}

	private void callApiRegisterUser() {
		try {
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.setFirstname(etFirstName.getText().toString().trim());
			registerRequest.setLastname(etLastName.getText().toString().trim());
			registerRequest.setEmailAddress(etEmailAddress.getText().toString().trim());
			registerRequest.setContactNumber(etContactNo.getText().toString().trim());
			registerRequest.setGender(arrListGender.get(spGender.getSelectedItemPosition()));
			registerRequest.setBirthdate(strDob);
			registerRequest.setHomeAddress(etHomeAddress.getText().toString().trim());
			registerRequest.setCountryId(spCountry.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCountries.get(spCountry.getSelectedItemPosition() - 1).getId()) : 0);
			registerRequest.setStateId(spState.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListStates.get(spState.getSelectedItemPosition() - 1).getId()) : 0);
			registerRequest.setCityId(spCity.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCities.get(spCity.getSelectedItemPosition() - 1).getId()) : 0);
			registerRequest.setUsername(etUserName.getText().toString().trim());
			registerRequest.setPassword(etNewPwd.getText().toString().trim());
			registerRequest.setDeviceToken(Utility.getDeviceTokenId(ProfileInformationActivity.this));
			registerRequest.setSchoolId(Double.parseDouble(strSchoolId));
			registerRequest.setClassroomId(Double.parseDouble(strClassId));
			registerRequest.setCourseId(Double.parseDouble(strCourseId));
			registerRequest.setAcademicYear(strAcademicYear);
			registerRequest.setRoleId(Double.parseDouble(strRoleId));
			registerRequest.setDeviceType(getString(R.string.android));
			registerRequest.setProfileImageName("image_" + System.currentTimeMillis() + ".png");
			registerRequest.setProfileImage(strDpBase64);

			new WebserviceWrapper(ProfileInformationActivity.this, registerRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.REGISTER_USER);
		} catch (Exception e) {
			Log.e(TAG, "callApiRegisterUser Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetCountries() {
		try {
			new WebserviceWrapper(ProfileInformationActivity.this, null).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_COUNTRIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetStates(int countryId) {
		try {
			GetStatesRequest statesRequest = new GetStatesRequest();
			statesRequest.setCountryId(countryId);

			new WebserviceWrapper(ProfileInformationActivity.this, statesRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_STATES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetCities(int stateId) {
		try {
			GetCitiesRequest citiesRequest = new GetCitiesRequest();
			citiesRequest.setStateId(stateId);

			new WebserviceWrapper(ProfileInformationActivity.this, citiesRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_CITIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
		}
	}

	private boolean isInputsValid() {
		return inputValidator.validateStringPresence(etFirstName) &
				inputValidator.validateStringPresence(etLastName) &
				inputValidator.validateStringPresence(etEmailAddress) &
				inputValidator.validateStringPresence(etContactNo) &
				inputValidator.validateStringPresence(etAge) &
				inputValidator.validateStringPresence(etHomeAddress) &
				(inputValidator.validateStringPresence(etCurrentPwd) && inputValidator.validatePasswordLength(etCurrentPwd) && isCurrentPwdCorrect()) &
				(inputValidator.validateStringPresence(etNewPwd) && inputValidator.validatePasswordLength(etNewPwd)
						&& inputValidator.validateNewPassword(etCurrentPwd, etNewPwd)) &
				(inputValidator.validateStringPresence(etConfirmPwd) && inputValidator.validatePasswordLength(etConfirmPwd)
						&& inputValidator.validateConfirmPasswordMatch(etNewPwd, etConfirmPwd)) &
				inputValidator.validateStringPresence(etUserName) &&
				checkOtherInputs();
	}

	private boolean isCurrentPwdCorrect() {
		if (etCurrentPwd.getText().toString().equals(strCurrentPassword)) {
			etCurrentPwd.setError(null);
			return true;
		} else {
			etCurrentPwd.setError(getString(R.string.msg_wrong_current_pwd));
			return false;
		}
	}

	private boolean checkOtherInputs() {
		strValidationMsg = "";
		if (isDpSet() & isGenderSet() & isSetDob() & isCountrySet() & isStateSet() & isCitySet()) {
			return true;
		} else {
			Utility.alert(ProfileInformationActivity.this, null, strValidationMsg);
			return false;
		}
	}

	private boolean isSetDob() {
		if (strDob == null || strDob.length() == 0) {
			strValidationMsg += getString(R.string.msg_validaton_dob);
			return false;
		} else {
			return true;
		}
	}

	private boolean isGenderSet() {
		if (spGender.getSelectedItemPosition() > 0) {
			return true;
		} else {
			strValidationMsg += getString(R.string.msg_validation_gender);
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

	private boolean isDpSet() {
		if (strDpBase64 == null || strDpBase64.length() == 0) {
			strValidationMsg += getString(R.string.msg_validation_set_dp);
			return false;
		} else {
			return true;
		}
	}

	public void openGallary() {
		Intent intent = new Intent();

		// Show only images, no videos or anything else
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);

		// Always show the chooser (if there are multiple options available)
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
			Uri uri = data.getData();
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
				imgDp.setImageBitmap(bitmap);
				strDpBase64 = Utility.getBase64ForImage(bitmap);
			} catch (IOException e) {
				strDpBase64 = "";
				Log.e(TAG, "onActivityResult convert Image Exception : " + e.toString());
			}
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			if (object != null) {
				switch (apiCode) {
					case WebserviceWrapper.GET_COUNTRIES:
						onResponseCountries(object);
						break;
					case WebserviceWrapper.GET_STATES:
						onResponseStates(object);
						break;
					case WebserviceWrapper.GET_CITIES:
						onResponseCities(object);
						break;
					case WebserviceWrapper.REGISTER_USER:
						onResponseRegisterUser(object);
						break;
					case WebserviceWrapper.REQUEST_SCHOOL_INFO:
						onResponseRequestSchoolInfo(object);
						break;
				}
			} else if (error != null) {
				Log.e(TAG, "onResponse ApiCall Exception : " + error.toString() + "\nFor apiCode : " + apiCode);
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseRequestSchoolInfo(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				if (dialogSchoolInfo != null) {
					dialogSchoolInfo.dismiss();
				}
				Toast.makeText(ProfileInformationActivity.this, "Request for school information updation sent to admin successfully.", Toast.LENGTH_LONG).show();
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Toast.makeText(ProfileInformationActivity.this, responseObj.getMessage(), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseRequestSchoolInfo Exception : " + e.toString());
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
				Adapters.setUpSpinner(ProfileInformationActivity.this, spCity, cities);
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
				Adapters.setUpSpinner(ProfileInformationActivity.this, spState, states);
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
				Adapters.setUpSpinner(ProfileInformationActivity.this, spCountry, countries);
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Log.e(TAG, "onResponseCountries Failed");
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseCountries Exception : " + e.toString());
		}
	}

	private void onResponseRegisterUser(Object object) {
		try {
			Toast.makeText(ProfileInformationActivity.this, "Register response", Toast.LENGTH_SHORT).show();
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, ProfileInformationActivity.this,
							PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, ProfileInformationActivity.this));
					PreferenceData.remove(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, ProfileInformationActivity.this);
					PreferenceData.setStringPrefs(PreferenceData.USER_ID, ProfileInformationActivity.this, responseObj.getData().get(0).getUserId());
					PreferenceData.setStringPrefs(PreferenceData.FULL_NAME, ProfileInformationActivity.this, responseObj.getData().get(0).getFullName());
					PreferenceData.setStringPrefs(PreferenceData.PROFILE_PIC, ProfileInformationActivity.this, responseObj.getData().get(0).getProfilePic());

					Intent intentWelcome = new Intent(ProfileInformationActivity.this, WelComeActivity.class);
					startActivity(intentWelcome);
					finish();
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					if (responseObj.getMessage().contains(ResponseObject.DUPLICATE_ENTRY)) {
						if (responseObj.getMessage().contains("email_id")) {
							Utility.alert(ProfileInformationActivity.this, getString(R.string.registration_failed), getString(R.string.msg_email_exists));
						} else if (responseObj.getMessage().contains("username")) {
							Utility.alert(ProfileInformationActivity.this, getString(R.string.registration_failed), getString(R.string.msg_username_exists));
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseRegisterUser Exception : " + e.toString());
		}
	}

}
