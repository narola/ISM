package com.ism.teacher.activity;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.commonsource.view.ProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.PreferenceData;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.MediaUploadAttribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by c75 on 15/10/15.
 */
public class TeacherProfileInformation extends Activity implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherProfileInformation.class.getSimpleName();

    //Views
    private Spinner spGender;
    private Button btnCancel;
    private EditText etFirstnameTeacher, etLastnameTeacher, etEmailAddressTeacher, etHomeAddressTeacher,
            etContactNumberTeacher, etSpecializaton, etEducation,
            etDobTeacher;
    private EditText etUsername, etCurrentPassword, etNewPassword, etConfirmPassword;
    private TextView tvClickhere, tvUploadpic, tvPersonalInfo, tvWorkprofileInfo, tvYouare, tvIfits,
            tvManageCredential, tvNameofschool, tvProgramcourse;
    private ImageView imgTeacherProfilePic;


    //Objects

    private InputValidator inputValidator;
    private MyTypeFace myTypeFace;
    ProgressGenerator progressGenerator;

    private Calendar calDob;
    private DatePickerDialog datePickerDob;
    private long lngMaxDob;
    private String strDob;
    //Array List
    private List<String> arrListGender;
    private List<String> arrListDefalt;
    private int PICK_IMAGE_REQUEST = 1;
    Uri selectedUri = null;

    private String strValidationMsg, strDpBase64, strUserCredentialId, strCurrentPassword, strRoleId,
            strSchoolName, strCourseName, strAcademicYear;

    private int schoolIdfromPreferences, courseIdfromPreference;
    private ProcessButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teacher_profile_info);
        initView();
    }

    private void initView() {

        inputValidator = new InputValidator(TeacherProfileInformation.this);

        imgTeacherProfilePic = (ImageView) findViewById(R.id.img_teacher_profile_pic);
        tvClickhere = (TextView) findViewById(R.id.txt_clickhere);
        tvUploadpic = (TextView) findViewById(R.id.txt_uploadpic);
        tvPersonalInfo = (TextView) findViewById(R.id.tv_personal_info);

        tvWorkprofileInfo = (TextView) findViewById(R.id.tv_workprofile_info);
        tvYouare = (TextView) findViewById(R.id.txt_youare_);
        tvIfits = (TextView) findViewById(R.id.txt_ifits_);
        tvManageCredential = (TextView) findViewById(R.id.tv_manage_credential);

        tvNameofschool = (TextView) findViewById(R.id.txt_nameofschool);
        tvProgramcourse = (TextView) findViewById(R.id.txt_programcourse);

        etDobTeacher = (EditText) findViewById(R.id.et_dob_teacher);
        etFirstnameTeacher = (EditText) findViewById(R.id.et_firstname_teacher);
        etLastnameTeacher = (EditText) findViewById(R.id.et_lastname_teacher);

        etEmailAddressTeacher = (EditText) findViewById(R.id.et_emailAdd_teacher);
        etHomeAddressTeacher = (EditText) findViewById(R.id.et_homeAdd_teacher);
        etContactNumberTeacher = (EditText) findViewById(R.id.et_contactno_teacher);
        etUsername = (EditText) findViewById(R.id.et_username);
        etCurrentPassword = (EditText) findViewById(R.id.et_currentpwd);
        etNewPassword = (EditText) findViewById(R.id.et_newpwd);
        etConfirmPassword = (EditText) findViewById(R.id.et_confirmpwd);
        etEducation = (EditText) findViewById(R.id.et_education);
        etSpecializaton = (EditText) findViewById(R.id.et_specializaton);
        btnSubmit = (ProcessButton) findViewById(R.id.btn_submit);

        spGender = (Spinner) findViewById(R.id.sp_gender_teacher);

        arrListGender = new ArrayList<>();
        arrListGender.add(getString(R.string.strgender));
        arrListGender.add(getString(R.string.male));
        arrListGender.add(getString(R.string.female));
        Adapters.setUpSpinner(TeacherProfileInformation.this, spGender, arrListGender, Adapters.ADAPTER_NORMAL);

        arrListDefalt = new ArrayList<>();
        arrListDefalt.add(getString(R.string.select));

        myTypeFace = new MyTypeFace(this);
        progressGenerator = new ProgressGenerator();

        retrieveFromPrefernces();

        applyFontsOnViews();

        etDobTeacher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDatePickerDob();
                }
                return true;
            }
        });

    }

    private void retrieveFromPrefernces() {
        strUserCredentialId = PreferenceData.getStringPrefs(PreferenceData.USER_CREDENTIAL_ID, getActivity());
        strCurrentPassword = PreferenceData.getStringPrefs(PreferenceData.USER_PASSWORD, getActivity());
        strRoleId = PreferenceData.getStringPrefs(PreferenceData.USER_ROLE_ID, getActivity());
        /**
         * New params
         */
        schoolIdfromPreferences = PreferenceData.getIntPrefs(PreferenceData.USER_SCHOOL_ID, getActivity());
        courseIdfromPreference = PreferenceData.getIntPrefs(PreferenceData.USER_COURSE_ID, getActivity());

        strSchoolName = PreferenceData.getStringPrefs(PreferenceData.USER_SCHOOL_NAME, getActivity());
        strCourseName = PreferenceData.getStringPrefs(PreferenceData.USER_COURSE_NAME, getActivity());
        strAcademicYear = PreferenceData.getStringPrefs(PreferenceData.USER_ACADEMIC_YEAR, getActivity());

        tvNameofschool.setText(strSchoolName);
        tvProgramcourse.setText(strCourseName);

    }

    public void onClickUploadImage(View v) {
        openGallery();
    }

    public void onClickHere(View v) {
    }

    public void onClickCancel(View view) {
        finish();
    }

    public void onClickSubmit(View view) {
        if (Utility.isConnected(TeacherProfileInformation.this)) {

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
            } else {
                Debug.e(TAG, "validation are not true for all fileds");
            }
        } else {
            Utility.alertOffline(TeacherProfileInformation.this);
        }
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
            attribute.setCredentialId(Integer.parseInt(strUserCredentialId));
            Debug.e(TAG+"credentail id in attrib",strUserCredentialId);
            attribute.setEmailAddress(etEmailAddressTeacher.getText().toString().trim());
            attribute.setPassword(etNewPassword.getText().toString().trim());
            attribute.setFirstname(etFirstnameTeacher.getText().toString().trim());
            attribute.setLastname(etLastnameTeacher.getText().toString().trim());
            attribute.setBirthdate(strDob);

            attribute.setCityId(0);
            attribute.setStateId(0);
            attribute.setCountryId(String.valueOf(0));

            attribute.setDeviceToken(Utility.getDeviceTokenId(getActivity()));
            attribute.setGender(arrListGender.get(spGender.getSelectedItemPosition()));
            attribute.setUsername(etUsername.getText().toString().trim());
            attribute.setHomeAddress(etHomeAddressTeacher.getText().toString().trim());
            attribute.setContactNumber(etContactNumberTeacher.getText().toString().trim());

            attribute.setSchoolId(schoolIdfromPreferences);
            attribute.setCourseId(courseIdfromPreference);
            attribute.setClassroomId(String.valueOf(0));
            attribute.setAcademicYear(strAcademicYear);
            attribute.setRoleId(strRoleId);
            attribute.setDeviceType(getString(R.string.android));
            attribute.setSchoolClassroomId(0);
            attribute.setEducation(etEducation.getText().toString());
            attribute.setSpecialization(etSpecializaton.getText().toString());

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.REGISTERUSER);
        } catch (Exception e) {
            Log.e(TAG, "callApiRegisterUser Exception : " + e.getLocalizedMessage());
        }
    }

    public void openGallery() {
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
                imgTeacherProfilePic.setImageBitmap(bitmap);
                strDpBase64 = Utility.getBase64ForImage(bitmap);
            } catch (IOException e) {
                strDpBase64 = "";
                Debug.e(TAG, "onActivityResult convert Image Exception : " + e.toString());
            }
        }
    }

    private void callApiUploadProfilePic(String userId, String fileName) {

        if (Utility.isConnected(getActivity())) {
//            ((AuthorHostActivity) getActivity()).showProgress();
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

    private void showDatePickerDob() {
        try {
            if (calDob == null) {
                calDob = Calendar.getInstance();
                calDob.add(Calendar.YEAR, -3);
                lngMaxDob = calDob.getTimeInMillis();
            }
            datePickerDob = new DatePickerDialog(TeacherProfileInformation.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calDob.set(Calendar.YEAR, year);
                    calDob.set(Calendar.MONTH, monthOfYear);
                    calDob.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    strDob = Utility.formatDateApi(calDob.getTime());
                    etDobTeacher.setText(Utility.formatDateDisplay(calDob.getTime()));
                }
            }, calDob.get(Calendar.YEAR), calDob.get(Calendar.MONTH), calDob.get(Calendar.DAY_OF_MONTH));
            datePickerDob.getDatePicker().setMaxDate(lngMaxDob);
            datePickerDob.show();
        } catch (Exception e) {
            Log.e(TAG, "showDatePickerDob Exception : " + e.toString());
        }
    }

    private boolean isInputsValid() {
        return inputValidator.validateStringPresence(etFirstnameTeacher)
                & inputValidator.validateStringPresence(etLastnameTeacher)
                & inputValidator.validateStringPresence(etEducation)
                & inputValidator.validateStringPresence(etSpecializaton)
                & (inputValidator.validateStringPresence(etEmailAddressTeacher) && inputValidator.validateEmail(etEmailAddressTeacher))
                & (inputValidator.validateStringPresence(etContactNumberTeacher) && inputValidator.validatePhoneNumberLength(etContactNumberTeacher))
                & inputValidator.validateStringPresence(etHomeAddressTeacher)
                & inputValidator.validateStringPresence(etUsername)

                & (inputValidator.validateStringPresence(etCurrentPassword) && inputValidator.validatePasswordLength(etCurrentPassword) && isCurrentPwdCorrect())
                & (inputValidator.validateStringPresence(etNewPassword) && inputValidator.validatePasswordLength(etNewPassword) && inputValidator.validateNewPassword(etCurrentPassword, etNewPassword))
                & (inputValidator.validateStringPresence(etConfirmPassword) && inputValidator.validatePasswordLength(etConfirmPassword)
                && inputValidator.validateConfirmPasswordMatch(etNewPassword, etConfirmPassword)) &&
                checkOtherInputs();
    }

    private boolean isCurrentPwdCorrect() {
        if (etCurrentPassword.getText().toString().equals(strCurrentPassword)) {
            etCurrentPassword.setError(null);
            return true;
        } else {
            etCurrentPassword.setError(getString(R.string.msg_wrong_current_pwd));
            return false;
        }
    }

    private boolean checkOtherInputs() {
        strValidationMsg = "";
//        if (isDpSet() & isGenderSet() & isSetDob()) {
        if (isGenderSet() & isSetDob()) {
            return true;

        } else {
            Utility.alert(TeacherProfileInformation.this, null, strValidationMsg);
            Debug.e(TAG, "false");
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

    private boolean isSetDob() {
        if (strDob == null || strDob.length() == 0) {
            strValidationMsg += getString(R.string.msg_validaton_dob);
            Debug.e(TAG, "dob validation false");
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
            Debug.e(TAG, "gender validation false");
            return false;
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
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
                    Intent intentWelcome = new Intent(getActivity(), TeacherHostActivity.class);
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
//            ((TEa) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Utility.showToast(getString(R.string.msg_success_imgupload_profilepic), getActivity());
                    PreferenceData.setStringPrefs(PreferenceData.USER_PROFILE_PIC, getActivity(), responseHandler.getUserImages().getProfileImages().get(0));

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseUploadMediaForQuestion api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseUploadMediaForQuestion Exception : " + e.toString());
        }
    }

    private void applyFontsOnViews() {

        tvPersonalInfo.setTypeface(myTypeFace.getRalewayRegular());
        tvUploadpic.setTypeface(myTypeFace.getRalewayRegular());
        tvWorkprofileInfo.setTypeface(myTypeFace.getRalewayRegular());
        tvYouare.setTypeface(myTypeFace.getRalewayRegular());
        tvClickhere.setTypeface(myTypeFace.getRalewayRegular());
        tvIfits.setTypeface(myTypeFace.getRalewayRegular());
        tvManageCredential.setTypeface(myTypeFace.getRalewayRegular());

        etDobTeacher.setTypeface(myTypeFace.getRalewayRegular());
        etFirstnameTeacher.setTypeface(myTypeFace.getRalewayRegular());
        etLastnameTeacher.setTypeface(myTypeFace.getRalewayRegular());
        etEmailAddressTeacher.setTypeface(myTypeFace.getRalewayRegular());
        etHomeAddressTeacher.setTypeface(myTypeFace.getRalewayRegular());
        etContactNumberTeacher.setTypeface(myTypeFace.getRalewayRegular());
        etSpecializaton.setTypeface(myTypeFace.getRalewayRegular());
        etEducation.setTypeface(myTypeFace.getRalewayRegular());
        etUsername.setTypeface(myTypeFace.getRalewayRegular());
        etCurrentPassword.setTypeface(myTypeFace.getRalewayRegular());
        etConfirmPassword.setTypeface(myTypeFace.getRalewayRegular());
        etNewPassword.setTypeface(myTypeFace.getRalewayRegular());
    }

    private Activity getActivity() {
        return TeacherProfileInformation.this;
    }
}
