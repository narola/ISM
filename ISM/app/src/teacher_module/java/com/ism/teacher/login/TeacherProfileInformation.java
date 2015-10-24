package com.ism.teacher.login;

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

import com.ism.R;
import com.ism.adapter.CustomSpinnerAdapter;
import com.ism.login.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;

/**
 * Created by c75 on 15/10/15.
 */
public class TeacherProfileInformation extends Activity implements View.OnClickListener {

    private static final String TAG = TeacherProfileInformation.class.getSimpleName();
    //Views
    private Spinner spGenderTeacher, spNameofSchool, spRegionSchool, spCityTeacher, spProgramCourse;
    private Button btnSubmit, btnCancel;

    private EditText etFullnameTeacher, etEmailAddressTeacher, etHomeAddressTeacher, etContactnoTeacher;
    private EditText etUsername, etCurrentPassword, etNewPassword, etConfirmPassword;
    private LinearLayout llUploadProfilePic;

    private TextView txtClickhere;
    String strArrGender[] = {"Gender", "Female", "Male"};
    String strArrSchool[] = {"Name of School", "School1", "School2"};
    String strArrRegion[] = {"Region of the School", "Region1", "Region2"};
    String strArrCity[] = {"City", "City1", "City2"};
    String strArrProgram[] = {"Program/Course", "Program/Course1", "Program/Course"};


    private CustomSpinnerAdapter spinnerAdapter;
    private boolean selected;
    private LayoutInflater mInflator;

    private InputValidator inputValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.layout_teacher_profile_info);

        initView();

    }

    private void initView() {

        inputValidator = new InputValidator(TeacherProfileInformation.this);
        txtClickhere = (TextView) findViewById(R.id.txt_clickhere);

        llUploadProfilePic = (LinearLayout) findViewById(R.id.ll_upload_profile_pic);

        etFullnameTeacher = (EditText) findViewById(R.id.et_fullname_teacher);
        etEmailAddressTeacher = (EditText) findViewById(R.id.et_emailAdd_teacher);
        etHomeAddressTeacher = (EditText) findViewById(R.id.et_homeAdd_teacher);
        etContactnoTeacher = (EditText) findViewById(R.id.et_contactno_teacher);
        etUsername = (EditText) findViewById(R.id.et_username);
        etCurrentPassword = (EditText) findViewById(R.id.et_currentpwd);
        etNewPassword = (EditText) findViewById(R.id.et_newpwd);
        etConfirmPassword = (EditText) findViewById(R.id.et_confirmpwd);

        spGenderTeacher = (Spinner) findViewById(R.id.sp_gender_teacher);
        spNameofSchool = (Spinner) findViewById(R.id.sp_name_of_school);
        spRegionSchool = (Spinner) findViewById(R.id.sp_region_school);
        spCityTeacher = (Spinner) findViewById(R.id.sp_city_teacher);
        spProgramCourse = (Spinner) findViewById(R.id.sp_program_course);

        spinnerAdapter = new CustomSpinnerAdapter(TeacherProfileInformation.this, strArrGender);
        selected = false;
        mInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setUpSpinner(spGenderTeacher, strArrGender);
        setUpSpinner(spNameofSchool, strArrSchool);
        setUpSpinner(spRegionSchool, strArrRegion);
        setUpSpinner(spCityTeacher, strArrCity);
        setUpSpinner(spProgramCourse, strArrProgram);


        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        txtClickhere.setOnClickListener(this);
        llUploadProfilePic.setOnClickListener(this);


    }


    private void setUpSpinner(Spinner spinner, String[] strArrGender) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.custom_spinner, strArrGender) {

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
                v.setBackgroundResource(R.color.color_white);

                ((TextView) v).setTextColor(getResources().getColorStateList(
                        R.color.color_dark_gray));
                ((TextView) v).setTypeface(new MyTypeFace(getApplicationContext()).getRalewayRegular());
                ((TextView) v).setCompoundDrawables(null, null, null, null);
                if (position == 0) {
                    ((TextView) v).setTextColor(getResources().getColorStateList(
                            R.color.color_white));
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

    @Override
    public void onClick(View view) {

        if (view == btnSubmit) {
//            if (isTextFieldsInputsValid()) {
//                Global.myIntent(TeacherProfileInformation.this, TeacherHomeActivity.class);
//            }
            Global.myIntent(TeacherProfileInformation.this, TeacherHomeActivity.class);

        } else if (view == llUploadProfilePic) {

        } else if (view == btnCancel) {

        } else if (view == txtClickhere) {
        }
    }

    private boolean isTextFieldsInputsValid() {
        return inputValidator.validateStringPresence(etFullnameTeacher)
                & (inputValidator.validateStringPresence(etEmailAddressTeacher) && inputValidator.validateEmail(etEmailAddressTeacher))
                & (inputValidator.validateStringPresence(etContactnoTeacher) && inputValidator.validatePhoneNumberLength(etContactnoTeacher))
                & inputValidator.validateStringPresence(etHomeAddressTeacher)
                & inputValidator.validateStringPresence(etUsername)
                & (inputValidator.validateStringPresence(etCurrentPassword) && inputValidator.validatePasswordLength(etHomeAddressTeacher))
                & (inputValidator.validateStringPresence(etNewPassword) && inputValidator.validatePasswordLength(etNewPassword))
                & (inputValidator.validateStringPresence(etConfirmPassword) && inputValidator.validatePasswordLength(etConfirmPassword) && inputValidator.validateConfirmPasswordMatch(etNewPassword, etConfirmPassword));
    }
}
