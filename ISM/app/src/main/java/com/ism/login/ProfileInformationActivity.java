package com.ism.login;

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
import com.ism.adapter.CustomSpinnerAdapter;
import com.ism.object.MyTypeFace;

import java.io.IOException;

/**
 * Created by c162 on 07/10/15.
 */
public class ProfileInformationActivity extends Activity implements View.OnClickListener {

    private static final String TAG = ProfileInformationActivity.class.getSimpleName();

	private Spinner spGender, spSchoolGender, spAcademicYear, spCity, spClass, spCountry, spProgramCourse,  spState;
    String strArrGender[] = {"Gender","Female", "Male"};
    String strArrList[] = {"Select","Item1", "Item2"};
    private CustomSpinnerAdapter spinnerAdapter;
    private ArrayAdapter<String> adapter;
    private TextView txtUploadpic;
    private ImageView imgDp;
    private int PICK_IMAGE_REQUEST = 1;
    private TextView txtYouare;
    private TextView txtIfits;
    private TextView txtClickhere;
    private EditText etAge, etUserName, etNewPwd, etNameSchool, etHomeAddress, etFullName,
		                etEmailAddress, etDob, etDistrictOfSchool, etCurrentPwd, etContactNo,  etConfirmPwd;
    private LayoutInflater mInflator;
    private boolean selected;
    LinearLayout llImageUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_profile_info);
        intitView();
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


    private void intitView() {
        MyTypeFace myTypeFace = new MyTypeFace(this);
         txtIfits = (TextView) findViewById(R.id.txt_ifits_);
         txtClickhere = (TextView) findViewById(R.id.txt_clickhere);
         txtYouare = (TextView) findViewById(R.id.txt_youare_);
         spGender = (Spinner) findViewById(R.id.sp_gender);
         spSchoolGender = (Spinner) findViewById(R.id.sp_schoolgender);
         spAcademicYear = (Spinner) findViewById(R.id.sp_academicyear);
         spCity = (Spinner) findViewById(R.id.sp_city);
         spClass = (Spinner) findViewById(R.id.sp_class);
         spCountry = (Spinner) findViewById(R.id.sp_country);
         spState = (Spinner) findViewById(R.id.sp_state);
         spProgramCourse = (Spinner) findViewById(R.id.sp_programcourse);
         etAge = (EditText) findViewById(R.id.et_age);
         etContactNo = (EditText) findViewById(R.id.et_cno);
         etConfirmPwd = (EditText) findViewById(R.id.et_confirmpwd);
         etCurrentPwd = (EditText) findViewById(R.id.et_currentpwd);
         etDistrictOfSchool = (EditText) findViewById(R.id.et_districtofschool);
         etDob = (EditText) findViewById(R.id.et_dob);
         etEmailAddress = (EditText) findViewById(R.id.et_emailAdd);
         etFullName = (EditText) findViewById(R.id.et_fullname);
         etHomeAddress = (EditText) findViewById(R.id.et_homeAdd);
         etNameSchool = (EditText) findViewById(R.id.et_nameofschool);
         etNewPwd = (EditText) findViewById(R.id.et_newpwd);
        llImageUpload=(LinearLayout)findViewById(R.id.ll_imageProfile);
         etUserName = (EditText) findViewById(R.id.et_username);
         txtUploadpic = (TextView) findViewById(R.id.txt_uploadpic);
         imgDp = (ImageView) findViewById(R.id.img_dp_post_creator);
         txtYouare.setTypeface(myTypeFace.getRalewayThin());
         txtIfits.setTypeface(myTypeFace.getRalewayThin());
         txtClickhere.setTypeface(myTypeFace.getRalewayThin());
         txtUploadpic.setTypeface(myTypeFace.getRalewayRegular());
         etAge.setTypeface(myTypeFace.getRalewayRegular());
         etContactNo.setTypeface(myTypeFace.getRalewayRegular());
         etConfirmPwd.setTypeface(myTypeFace.getRalewayRegular());
         etCurrentPwd.setTypeface(myTypeFace.getRalewayRegular());
         etDistrictOfSchool.setTypeface(myTypeFace.getRalewayRegular());
         etDob.setTypeface(myTypeFace.getRalewayRegular());
         etEmailAddress.setTypeface(myTypeFace.getRalewayRegular());
         etFullName.setTypeface(myTypeFace.getRalewayRegular());
         etHomeAddress.setTypeface(myTypeFace.getRalewayRegular());
         etNameSchool.setTypeface(myTypeFace.getRalewayRegular());
         etNewPwd.setTypeface(myTypeFace.getRalewayRegular());
         etUserName.setTypeface(myTypeFace.getRalewayRegular());

        //  spGender.setTypeface(myTypeFace.getRalewayRegular());

         llImageUpload.setOnClickListener(this);
        spinnerAdapter = new CustomSpinnerAdapter(ProfileInformationActivity.this, strArrGender);
        selected = false;
        mInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, strArrGender);
//        ArrayAdapter.createFromResource(this,
//                R.array.list, R.layout.custom_spinner);
//        adapter.setDropDownViewResource(R.layout.row_spinner);
//         spGender.setAdapter(adapter);
        setUpSpinner(spAcademicYear, strArrList);
        setUpSpinner(spCity, strArrList);
        setUpSpinner(spClass, strArrList);
        setUpSpinner(spProgramCourse, strArrList);
        setUpSpinner(spState, strArrList);
        setUpSpinner(spSchoolGender, strArrList);
        setUpSpinner(spCountry, strArrList);
        setUpSpinner(spGender, strArrGender);

    }

    private void setUpSpinner(Spinner spinner,String[] strArrGender) {
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
                if (position==0)
                {
                    ((TextView) v).setTextColor(getResources().getColorStateList(
                            R.color.color_white));
                    ((TextView)v).setBackgroundColor(getResources().getColor(R.color.color_green));
                    ((TextView)v).setEnabled(false);

                    ((TextView)v).setOnClickListener(null);
                }

                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.row_spinner);
        spinner.setAdapter(adapter);
    }

	public void onClickSubmit(View view) {

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
            case R.id.ll_imageProfile: {
                openGallary();
            }
            break;
        }
    }


}
