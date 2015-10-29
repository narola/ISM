package com.ism.author.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.AuthorHostActivity;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.utility.Utility;

import java.io.IOException;


public class AuthorProfileInformationActivity extends Activity implements View.OnClickListener {


    private static final String TAG = AuthorProfileInformationActivity.class.getSimpleName();

    private Spinner spGender, spSchoolgender, spAcademicyear, spCity, spClass, spCountry, spProgramcourse, spState;

    String strArrGender[] = {"Gender", "Female", "Male"}, strArrList[] = {"Select", "Item1", "Item2"};
    private ArrayAdapter<String> adapter;

    private EditText etFullname, etEmailadd, etCno, etDob, etAge, etHomeadd, etNameofschool, etDistrictofschool, etUsername, etNewpwd, etCurrentpwd, etConfirmpwd;

    private Button btnSubmit;

    private TextView txtUploadpic, txtYouare, txtIfits, txtClickhere;

    private ImageView imgDp;

    private int PICK_IMAGE_REQUEST = 1;


    private LayoutInflater mInflator;
    private boolean selected;
    LinearLayout llImageUpload;
    private InputValidator inputValidator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_author_profile_information);

        initGlobal();
        onClicks();
    }

    private void onClicks() {
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initGlobal() {

        inputValidator = new InputValidator(AuthorProfileInformationActivity.this);
        MyTypeFace myTypeFace = new MyTypeFace(this);

        etFullname = (EditText) findViewById(R.id.et_fullname);
        etEmailadd = (EditText) findViewById(R.id.et_emailAdd);
        etCno = (EditText) findViewById(R.id.et_cno);
        etDob = (EditText) findViewById(R.id.et_dob);
        etAge = (EditText) findViewById(R.id.et_age);
        etHomeadd = (EditText) findViewById(R.id.et_homeAdd);
        etNameofschool = (EditText) findViewById(R.id.et_nameofschool);
        etDistrictofschool = (EditText) findViewById(R.id.et_districtofschool);
        etUsername = (EditText) findViewById(R.id.et_username);
        etCurrentpwd = (EditText) findViewById(R.id.et_currentpwd);
        etNewpwd = (EditText) findViewById(R.id.et_newpwd);
        etConfirmpwd = (EditText) findViewById(R.id.et_confirmpwd);

        txtUploadpic = (TextView) findViewById(R.id.txt_uploadpic);
        txtYouare = (TextView) findViewById(R.id.txt_youare_);
        txtIfits = (TextView) findViewById(R.id.txt_ifits_);
        txtClickhere = (TextView) findViewById(R.id.txt_clickhere);


        spGender = (Spinner) findViewById(R.id.sp_gender);
        spSchoolgender = (Spinner) findViewById(R.id.sp_schoolgender);
        spAcademicyear = (Spinner) findViewById(R.id.sp_academicyear);
        spCity = (Spinner) findViewById(R.id.sp_city);
        spClass = (Spinner) findViewById(R.id.sp_class);
        spCountry = (Spinner) findViewById(R.id.sp_country);
        spState = (Spinner) findViewById(R.id.sp_state);
        spProgramcourse = (Spinner) findViewById(R.id.sp_programcourse);


        llImageUpload = (LinearLayout) findViewById(R.id.ll_imageProfile);

        imgDp = (ImageView) findViewById(R.id.img_dp);
        btnSubmit = (Button) findViewById(R.id.btn_suumit);


        etFullname.setTypeface(myTypeFace.getRalewayRegular());
        etEmailadd.setTypeface(myTypeFace.getRalewayRegular());
        etCno.setTypeface(myTypeFace.getRalewayRegular());
        etDob.setTypeface(myTypeFace.getRalewayRegular());
        etAge.setTypeface(myTypeFace.getRalewayRegular());
        etHomeadd.setTypeface(myTypeFace.getRalewayRegular());
        etNameofschool.setTypeface(myTypeFace.getRalewayRegular());
        etDistrictofschool.setTypeface(myTypeFace.getRalewayRegular());
        etUsername.setTypeface(myTypeFace.getRalewayRegular());
        etCurrentpwd.setTypeface(myTypeFace.getRalewayRegular());
        etNewpwd.setTypeface(myTypeFace.getRalewayRegular());
        etConfirmpwd.setTypeface(myTypeFace.getRalewayRegular());


        txtUploadpic.setTypeface(myTypeFace.getRalewayRegular());
        txtYouare.setTypeface(myTypeFace.getRalewayThin());
        txtIfits.setTypeface(myTypeFace.getRalewayThin());
        txtClickhere.setTypeface(myTypeFace.getRalewayThin());


        llImageUpload.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        selected = false;
        mInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        setUpSpinner(spGender, strArrGender);
        setUpSpinner(spSchoolgender, strArrList);
        setUpSpinner(spAcademicyear, strArrList);
        setUpSpinner(spCity, strArrList);
        setUpSpinner(spClass, strArrList);
        setUpSpinner(spCountry, strArrList);
        setUpSpinner(spState, strArrList);
        setUpSpinner(spProgramcourse, strArrList);

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
                v.setBackgroundResource(R.color.color_white);

                ((TextView) v).setTextColor(getResources().getColorStateList(
                        R.color.color_dark_gray));
                ((TextView) v).setTypeface(new MyTypeFace(getApplicationContext()).getRalewayRegular());
                ((TextView) v).setCompoundDrawables(null, null, null, null);
                if (position == 0) {
                    ((TextView) v).setTextColor(getResources().getColorStateList(
                            R.color.color_white));
                    ((TextView) v).setBackgroundColor(getResources().getColor(R.color.color_orange));
                    ((TextView) v).setEnabled(false);

                    ((TextView) v).setOnClickListener(null);
                }

                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.row_spinner);
        spinner.setAdapter(adapter);
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
                // Log.d(TAG, String.valueOf(bitmap));ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imgDp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_suumit: {
                Utility.launchIntent(AuthorProfileInformationActivity.this, AuthorHostActivity.class);
            }
            break;
            case R.id.ll_imageProfile: {
                openGallary();
            }
            break;

        }
    }

    private boolean isInputsValid() {
        return inputValidator.validateStringPresence(etFullname) & (inputValidator.validateStringPresence(etEmailadd) && inputValidator.validateEmail(etEmailadd));

    }


}
