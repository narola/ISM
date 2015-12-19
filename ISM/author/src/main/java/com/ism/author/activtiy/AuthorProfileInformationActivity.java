package com.ism.author.activtiy;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.Adapters;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.MediaUploadAttribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Cities;
import com.ism.author.ws.model.Countries;
import com.ism.author.ws.model.States;
import com.ism.commonsource.view.ProcessButton;
import com.ism.commonsource.view.ProgressGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import realmhelper.AuthorHelper;


public class AuthorProfileInformationActivity extends Activity implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = AuthorProfileInformationActivity.class.getSimpleName();

    private Spinner spGender, spCity, spCountry, spState;
    private EditText etFirstName, etLastName,
            etEmailAddress, etContactNo, etDob, etAge, etHomeAddress, etUserName, etCurrentPwd, etNewPwd, etConfirmPwd, etEducation;
    private ImageView imgDp;
    private ProcessButton btnSubmit, progCountry, progState, progCity;

    private InputValidator inputValidator;
    private List<String> arrListGender;
    private List<String> arrListDefalt;
    private ArrayList<Countries> arrListCountries;
    private ArrayList<States> arrListStates;
    private ArrayList<Cities> arrListCities;
    private Calendar calDob;
    private DatePickerDialog datePickerDob;
    private ProgressGenerator progressGenerator;
    private AuthorHelper authorHelper;

    private String strUserId;
    private String strCurrentPassword;
    private String strRoleId;
    private String strDpBase64;
    private String strValidationMsg;
    private String strDob;
    private long lngMaxDob;
    private int PICK_IMAGE_REQUEST = 1;
    Uri selectedUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_profile_information);

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
        etEducation = (EditText) findViewById(R.id.et_education);
        imgDp = (ImageView) findViewById(R.id.img_dp_post_creator);
        btnSubmit = (ProcessButton) findViewById(R.id.btn_submit);
        progCountry = (ProcessButton) findViewById(R.id.prog_country);
        progState = (ProcessButton) findViewById(R.id.prog_state);
        progCity = (ProcessButton) findViewById(R.id.prog_city);

        authorHelper = new AuthorHelper(this);

        ((TextView) findViewById(R.id.txt_uploadpic)).setTypeface(Global.myTypeFace.getRalewayRegular());
        etAge.setTypeface(Global.myTypeFace.getRalewayRegular());
        etContactNo.setTypeface(Global.myTypeFace.getRalewayRegular());
        etConfirmPwd.setTypeface(Global.myTypeFace.getRalewayRegular());
        etCurrentPwd.setTypeface(Global.myTypeFace.getRalewayRegular());
        etDob.setTypeface(Global.myTypeFace.getRalewayRegular());
        etEmailAddress.setTypeface(Global.myTypeFace.getRalewayRegular());
        etFirstName.setTypeface(Global.myTypeFace.getRalewayRegular());
        etLastName.setTypeface(Global.myTypeFace.getRalewayRegular());
        etHomeAddress.setTypeface(Global.myTypeFace.getRalewayRegular());
        etNewPwd.setTypeface(Global.myTypeFace.getRalewayRegular());
        etUserName.setTypeface(Global.myTypeFace.getRalewayRegular());
        etEducation.setTypeface(Global.myTypeFace.getRalewayRegular());


        inputValidator = new InputValidator(getActivity());

        progressGenerator = new ProgressGenerator();

        strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_CREDENTIAL_ID, getActivity());
        strCurrentPassword = PreferenceData.getStringPrefs(PreferenceData.USER_PASSWORD, getActivity());
        strRoleId = PreferenceData.getStringPrefs(PreferenceData.USER_ROLE_ID, getActivity());


        arrListDefalt = new ArrayList<>();
        arrListDefalt.add(getString(R.string.select));
        Adapters.setUpSpinner(getActivity(), spCountry, arrListDefalt, Adapters.ADAPTER_NORMAL);
        Adapters.setUpSpinner(getActivity(), spState, arrListDefalt, Adapters.ADAPTER_NORMAL);
        Adapters.setUpSpinner(getActivity(), spCity, arrListDefalt, Adapters.ADAPTER_NORMAL);


        arrListGender = new ArrayList<String>();
        arrListGender.add(getString(R.string.gender));
        arrListGender.add(getString(R.string.male));
        arrListGender.add(getString(R.string.female));
        Adapters.setUpSpinner(getActivity(), spGender, arrListGender, Adapters.ADAPTER_NORMAL);


        if (Utility.isConnected(getActivity())) {
            callApiGetCountries();
        } else {
            Utility.alertOffline(getActivity());
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
                    if (Utility.isConnected(getActivity())) {
                        callApiGetStates(Integer.parseInt(arrListCountries.get(position - 1).getId()));
                    } else {
                        Utility.toastOffline(getActivity());
                    }
                } else {
                    Adapters.setUpSpinner(getActivity(), spState, arrListDefalt, Adapters.ADAPTER_NORMAL);
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
                    if (Utility.isConnected(getActivity())) {
                        callApiGetCities(Integer.parseInt(arrListStates.get(position - 1).getId()));
                    } else {
                        Utility.toastOffline(getActivity());
                    }
                } else {
                    Adapters.setUpSpinner(getActivity(), spCity, arrListDefalt, Adapters.ADAPTER_NORMAL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void showDatePickerDob() {
        try {
            if (calDob == null) {
                calDob = Calendar.getInstance();
                calDob.add(Calendar.YEAR, -3);
                lngMaxDob = calDob.getTimeInMillis();
            }
            datePickerDob = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
        if (Utility.isConnected(getActivity())) {

//			PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, ProfileInformationActivity.this,
//					PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, ProfileInformationActivity.this));
//			PreferenceData.remove(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, ProfileInformationActivity.this);
//			PreferenceData.setStringPrefs(PreferenceData.USER_ID, ProfileInformationActivity.this, "141");
//			PreferenceData.setStringPrefs(PreferenceData.USER_FULL_NAME, ProfileInformationActivity.this, "Krunal Panchal");
//			PreferenceData.setStringPrefs(PreferenceData.USER_PROFILE_PIC, ProfileInformationActivity.this, "user_374/logo_test.png");
//
//			Intent intentWelcome = new Intent(ProfileInformationActivity.this, WelComeActivity.class);
//			startActivity(intentWelcome);
//			finish();

            if (isInputsValid()) {
                callApiRegisterUser();
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    public void onClickCancel(View view) {
        finish();
    }

    public void onClickUploadImage(View v) {
        openGallary();
    }

    private void callApiRegisterUser() {
        try {
            btnSubmit.setProgress(1);
            btnSubmit.setEnabled(false);
            progressGenerator.start(btnSubmit);

	        /*
             {
		         "credential_id":1,
		         "email_address":"alpesh.narola@narolainfotech.com",
		         "password":"password",
		         "firstname":"Alpesh",
		         "birthdate":"1992-12-12",
		         "lastname":"Patel",
		         "city_id":1,
		         "state_id":1,
		         "country_id":2,
		         "device_token":"1111111",
		         "gender":"Male",
		         "username":"alpesh",
		         "home_address":"Surat",
		         "contact_number":"9898989898",
		         "school_id":10,
		         "course_id":12,
		         "classroom_id":10,
		         "academic_year":"2015-2016",
		         "role_id":2,
		         "device_type":"Android",
		         "school_classroom_id":10,
		         "secret_key":"allowaccesstoapp",
		         "access_key":"/6NOTTWw4VFCzhdHzJ/p5g=="
	         }
	         */

            Attribute attribute = new Attribute();
            attribute.setCredentialId(Integer.parseInt(PreferenceData.getStringPrefs(PreferenceData.USER_CREDENTIAL_ID, AuthorProfileInformationActivity.this)));
            attribute.setEmailAddress(etEmailAddress.getText().toString().trim());
            attribute.setPassword(etNewPwd.getText().toString().trim());
            attribute.setFirstname(etFirstName.getText().toString().trim());
            attribute.setLastname(etLastName.getText().toString().trim());
            attribute.setBirthdate(strDob);
            attribute.setCityId(spCity.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCities.get(spCity.getSelectedItemPosition() - 1).getId()) : 0);
            attribute.setStateId(spState.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListStates.get(spState.getSelectedItemPosition() - 1).getId()) : 0);
            attribute.setCountryId(spCountry.getSelectedItemPosition() > 0 ? arrListCountries.get(spCountry.getSelectedItemPosition() - 1).getId() : "0");
            attribute.setDeviceToken(Utility.getDeviceTokenId(getActivity()));
            attribute.setGender(arrListGender.get(spGender.getSelectedItemPosition()));
            attribute.setUsername(etUserName.getText().toString().trim());
            attribute.setHomeAddress(etHomeAddress.getText().toString().trim());
            attribute.setContactNumber(etContactNo.getText().toString().trim());
            attribute.setSchoolId(0);
            attribute.setCourseId(0);
            attribute.setClassroomId(String.valueOf(0));
            attribute.setAcademicYear("");
            attribute.setRoleId(strRoleId);
            attribute.setDeviceType(getString(R.string.android));
            attribute.setSchoolClassroomId(0);
            attribute.setEducation(etEducation.getText().toString());

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.REGISTERUSER);
        } catch (Exception e) {
            Log.e(TAG, "callApiRegisterUser Exception : " + e.getLocalizedMessage());
        }
    }


    private void callApiUploadProfilePic(String userId, String fileName) {

        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();
            try {
                Attribute attribute = new Attribute();

                MediaUploadAttribute userIdParam = new MediaUploadAttribute();
                userIdParam.setParamName("user_id");
                userIdParam.setParamValue(userId);
                attribute.getArrListParam().add(userIdParam);


                MediaUploadAttribute mediaFileParam = new MediaUploadAttribute();
                mediaFileParam.setParamName("media_images[]");
                mediaFileParam.setFileName(fileName);
                attribute.getArrListFile().add(mediaFileParam);


                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.UPLOADPROFILEIMAGES);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    private void callApiGetCountries() {
        try {
            progCountry.setProgress(1);
            progCountry.setVisibility(View.VISIBLE);
            progressGenerator.start(progCountry);
            new WebserviceWrapper(getActivity(), new Attribute(), this).new WebserviceCaller()
                    .execute(WebConstants.GETCOUNTRIES);
        } catch (Exception e) {
            Debug.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiGetStates(int countryId) {
        try {
            progState.setProgress(1);
            progState.setVisibility(View.VISIBLE);
            progressGenerator.start(progState);
            Attribute attribute = new Attribute();

            attribute.setCountryId(String.valueOf(countryId));

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GETSTATES);
        } catch (Exception e) {
            Debug.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiGetCities(int stateId) {
        try {
            progCity.setProgress(1);
            progCity.setVisibility(View.VISIBLE);
            progressGenerator.start(progCity);
            Attribute attribute = new Attribute();
            attribute.setStateId(stateId);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GETCITIES);
        } catch (Exception e) {
            Debug.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
        }
    }

    private boolean isInputsValid() {

        return inputValidator.validateStringPresence(etFirstName) &
                inputValidator.validateStringPresence(etLastName) &
                inputValidator.validateStringPresence(etEmailAddress) &
                inputValidator.validateStringPresence(etEducation) &
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
            Utility.alert(getActivity(), null, strValidationMsg);
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
            selectedUri = uri;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgDp.setImageBitmap(bitmap);
                strDpBase64 = Utility.getBase64ForImage(bitmap);
            } catch (IOException e) {
                strDpBase64 = "";
                Debug.e(TAG, "onActivityResult convert Image Exception : " + e.toString());
            }
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETCOUNTRIES:
                    onResponseCountries(object, error);
                    break;
                case WebConstants.GETSTATES:
                    onResponseStates(object, error);
                    break;
                case WebConstants.GETCITIES:
                    onResponseCities(object, error);
                    break;
                case WebConstants.REGISTERUSER:
                    onResponseRegisterUser(object, error);
                    break;
                case WebConstants.UPLOADPROFILEIMAGES:
                    onResponseUploadProfilePic(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }


    private void onResponseCities(Object object, Exception error) {
        try {
            progCity.setProgress(100);
            progCity.setVisibility(View.INVISIBLE);
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListCities = new ArrayList<Cities>();
                    arrListCities.addAll(responseHandler.getCities());
                    List<String> cities = new ArrayList<String>();
                    cities.add(getString(R.string.select));
                    for (Cities city : arrListCities) {
                        cities.add(city.getCityName());
                    }
                    Adapters.setUpSpinner(getActivity(), spCity, cities, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseCities Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCities api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCities Exception : " + e.toString());
        }
    }

    private void onResponseStates(Object object, Exception error) {
        try {
            progState.setProgress(100);
            progState.setVisibility(View.INVISIBLE);
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListStates = new ArrayList<States>();
                    arrListStates.addAll(responseHandler.getStates());
                    List<String> states = new ArrayList<String>();
                    states.add(getString(R.string.select));
                    for (States state : arrListStates) {
                        states.add(state.getStateName());
                    }
                    Adapters.setUpSpinner(getActivity(), spState, states, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseStates Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCountries api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseStates Exception : " + e.toString());
        }
    }


    private void onResponseCountries(Object object, Exception error) {
        try {
            progCountry.setProgress(100);
            progCountry.setVisibility(View.INVISIBLE);
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListCountries = new ArrayList<Countries>();
                    arrListCountries.addAll(responseHandler.getCountries());
                    List<String> countries = new ArrayList<String>();
                    countries.add(getString(R.string.select));
                    for (Countries country : arrListCountries) {
                        countries.add(country.getCountryName());
                    }
                    Adapters.setUpSpinner(getActivity(), spCountry, countries, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseCountries Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCountries api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCountries Exception : " + e.toString());
        }
    }

    private void onResponseRegisterUser(Object object, Exception error) {
        try {
            btnSubmit.setProgress(100);
            btnSubmit.setEnabled(true);
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, getActivity(),
                            PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, getActivity()));
                    PreferenceData.remove(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, getActivity());
                    PreferenceData.remove(PreferenceData.USER_PASSWORD, getActivity());
                    PreferenceData.setStringPrefs(PreferenceData.USER_ID, getActivity(), responseHandler.getUser().get(0).getUserId());
                    PreferenceData.setStringPrefs(PreferenceData.USER_FULL_NAME, getActivity(), responseHandler.getUser().get(0).getFullName());
                    PreferenceData.setStringPrefs(PreferenceData.USER_PROFILE_PIC, getActivity(), responseHandler.getUser().get(0).getProfilePic());


//                    callApiUploadProfilePic(responseHandler.getUser().get(0).getUserId(), Utility.getRealPathFromURI(selectedUri, getActivity()));

                    Intent intentWelcome = new Intent(getActivity(), AuthorHostActivity.class);
                    startActivity(intentWelcome);
                    finish();


                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    if (responseHandler.getMessage().contains(ResponseHandler.DUPLICATE_ENTRY)) {
                        if (responseHandler.getMessage().contains("email_id")) {
                            Utility.alert(getActivity(), getString(R.string.registration_failed), getString(R.string.msg_email_exists));
                        } else if (responseHandler.getMessage().contains("username")) {
                            Utility.alert(getActivity(), getString(R.string.registration_failed), getString(R.string.msg_username_exists));
                        }
                    }
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseRegisterUser api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseRegisterUser Exception : " + e.toString());
        }
    }

    private void onResponseUploadProfilePic(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Utils.showToast(getString(R.string.msg_success_imgupload_profilepic), getActivity());
                    PreferenceData.setStringPrefs(PreferenceData.USER_PROFILE_PIC, getActivity(), responseHandler.getUserImages().getProfileImages().get(0));

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseUploadMediaForQuestion api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseUploadMediaForQuestion Exception : " + e.toString());
        }
    }

    private Activity getActivity() {
        return AuthorProfileInformationActivity.this;
    }


}
