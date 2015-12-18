package com.ism.teacher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.commonsource.view.ProgressGenerator;
import com.ism.teacher.R;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.CustomSpinnerAdapter;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.helper.WebserviceWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c75 on 15/10/15.
 */
public class TeacherProfileInformation extends Activity implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherProfileInformation.class.getSimpleName();

    //Views
    private Spinner spGender, spNameofSchool, spRegionSchool, spCity, spProgramCourse;
    private Button btnSubmit, btnCancel;
    private EditText etFullnameTeacher, etEmailAddressTeacher, etHomeAddressTeacher, etContactNumberTeacher, etSpecializaton, etEducation,
            etDobTeacher;
    private EditText etUsername, etCurrentPassword, etNewPassword, etConfirmPassword;
    private LinearLayout llUploadProfilePic;
    private TextView tvClickhere, tvUploadpic, tvPersonalInfo, tvWorkprofileInfo, tvYouare, tvIfits, tvManageCredential;

    //arrays
    String strArrGender[] = {"Gender", "Female", "Male"};
    String strArrSchool[] = {"Name of School", "School1", "School2"};
    String strArrRegion[] = {"Region of the School", "Region1", "Region2"};
    String strArrCity[] = {"City", "City1", "City2"};
    String strArrProgram[] = {"Program/Course", "Program/Course1", "Program/Course"};

    //Objects

    private CustomSpinnerAdapter spinnerAdapter;
    private boolean selected;
    private LayoutInflater inflater;
    private InputValidator inputValidator;
    private MyTypeFace myTypeFace;
    ProgressGenerator progressGenerator;

    //Array List
    private List<String> arrListGender;
    private List<String> arrListDefalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.layout_teacher_profile_info);

        initView();

    }

    private void initView() {

        inputValidator = new InputValidator(TeacherProfileInformation.this);
        tvClickhere = (TextView) findViewById(R.id.txt_clickhere);
        tvUploadpic = (TextView) findViewById(R.id.txt_uploadpic);
        tvPersonalInfo = (TextView) findViewById(R.id.tv_personal_info);

        tvWorkprofileInfo = (TextView) findViewById(R.id.tv_workprofile_info);
        tvYouare = (TextView) findViewById(R.id.txt_youare_);
        tvIfits = (TextView) findViewById(R.id.txt_ifits_);
        tvManageCredential = (TextView) findViewById(R.id.tv_manage_credential);

        llUploadProfilePic = (LinearLayout) findViewById(R.id.ll_upload_profile_pic);

        etDobTeacher = (EditText) findViewById(R.id.et_dob_teacher);
        etFullnameTeacher = (EditText) findViewById(R.id.et_firstname_teacher);
        etEmailAddressTeacher = (EditText) findViewById(R.id.et_emailAdd_teacher);
        etHomeAddressTeacher = (EditText) findViewById(R.id.et_homeAdd_teacher);
        etContactNumberTeacher = (EditText) findViewById(R.id.et_contactno_teacher);
        etUsername = (EditText) findViewById(R.id.et_username);
        etCurrentPassword = (EditText) findViewById(R.id.et_currentpwd);
        etNewPassword = (EditText) findViewById(R.id.et_newpwd);
        etConfirmPassword = (EditText) findViewById(R.id.et_confirmpwd);

        etEducation = (EditText) findViewById(R.id.et_education);
        etSpecializaton = (EditText) findViewById(R.id.et_specializaton);

        spGender = (Spinner) findViewById(R.id.sp_gender_teacher);
        spNameofSchool = (Spinner) findViewById(R.id.sp_name_of_school);
        spRegionSchool = (Spinner) findViewById(R.id.sp_region_school);
        spCity = (Spinner) findViewById(R.id.sp_city_teacher);
        spProgramCourse = (Spinner) findViewById(R.id.sp_program_course);

        spinnerAdapter = new CustomSpinnerAdapter(TeacherProfileInformation.this, strArrGender);
        selected = false;
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        arrListDefalt = new ArrayList<>();
        arrListDefalt.add(getString(R.string.select));

//        Adapters.setUpSpinner(TeacherProfileInformation.this, spCountry, arrListDefalt, myTypeFace.getRalewayRegular());
//        Adapters.setUpSpinner(TeacherProfileInformation.this, spState, arrListDefalt, myTypeFace.getRalewayRegular());
        Adapters.setUpSpinner(TeacherProfileInformation.this, spCity, arrListDefalt, myTypeFace.getRalewayRegular());

        arrListGender = new ArrayList<>();
        arrListGender.add(getString(R.string.strgender));
        arrListGender.add(getString(R.string.male));
        arrListGender.add(getString(R.string.female));

        Adapters.setUpSpinner(TeacherProfileInformation.this, spGender, arrListGender, myTypeFace.getRalewayRegular());


//        setUpSpinner(spNameofSchool, strArrSchool);
//        setUpSpinner(spRegionSchool, strArrRegion);
//        setUpSpinner(spProgramCourse, strArrProgram);

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        myTypeFace = new MyTypeFace(this);
        progressGenerator = new ProgressGenerator();

        applyFontsOnViews();

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
        etFullnameTeacher.setTypeface(myTypeFace.getRalewayRegular());
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


    private void setUpSpinner(Spinner spinner, String[] strArrGender) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.simple_spinner, strArrGender) {

            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(new MyTypeFace(getApplicationContext()).getRalewayRegular());
                if (position == 0) {
                    ((TextView) v).setTextColor(
                            getResources().getColor(R.color.color_text_hint));
                    //  ((TextView)v).setGravity(Gravity.RIGHT);
                    //.getColorStateList(R.color.white));
                    return v;
                } else {
                    ((TextView) v).setTextColor(
                            getResources().getColor(R.color.color_black));
                    return v;
                }
            }

            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,
                        parent);
                v.setBackgroundResource(R.color.white);

                ((TextView) v).setTextColor(getResources().getColorStateList(
                        R.color.color_dark_gray));
                ((TextView) v).setTypeface(new MyTypeFace(getApplicationContext()).getRalewayRegular());
                ((TextView) v).setCompoundDrawables(null, null, null, null);
                if (position == 0) {
                    ((TextView) v).setTextColor(getResources().getColorStateList(
                            R.color.white));
                    ((TextView) v).setBackgroundColor(getResources().getColor(R.color.color_blue));
                    ((TextView) v).setEnabled(false);

                    ((TextView) v).setOnClickListener(null);
                }

                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.row_spinner);
        spinner.setAdapter(adapter);
    }

//    @Override
//    public void onClick(View view) {
//
//        if (view == btnSubmit) {
////            if (isTextFieldsInputsValid()) {
////                Global.launchIntent(TeacherProfileInformation.this, TeacherHostActivity.class);
////            }
//            Utility.launchIntent(TeacherProfileInformation.this, TeacherHostActivity.class);
//
//        } else if (view == llUploadProfilePic) {
//
//
//        } else if (view == btnCancel) {
//
//        } else if (view == tvClickhere) {
//        }
//    }

    private boolean isTextFieldsInputsValid() {
        return inputValidator.validateStringPresence(etFullnameTeacher)
                & inputValidator.validateStringPresence(etEducation)
                & inputValidator.validateStringPresence(etSpecializaton)
                & (inputValidator.validateStringPresence(etEmailAddressTeacher) && inputValidator.validateEmail(etEmailAddressTeacher))
                & (inputValidator.validateStringPresence(etContactNumberTeacher) && inputValidator.validatePhoneNumberLength(etContactNumberTeacher))
                & inputValidator.validateStringPresence(etHomeAddressTeacher)
                & inputValidator.validateStringPresence(etUsername)
                & (inputValidator.validateStringPresence(etCurrentPassword) && inputValidator.validatePasswordLength(etHomeAddressTeacher))
                & (inputValidator.validateStringPresence(etNewPassword) && inputValidator.validatePasswordLength(etNewPassword))
                & (inputValidator.validateStringPresence(etConfirmPassword) && inputValidator.validatePasswordLength(etConfirmPassword) && inputValidator.validateConfirmPasswordMatch(etNewPassword, etConfirmPassword));
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {

    }
}
