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
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.R;
import com.ism.adapter.CustomSpinnerAdapter;
import com.ism.object.MyTypeFace;

import java.io.IOException;

/**
 * Created by c162 on 07/10/15.
 */
public class ProfileInfornation extends Activity implements View.OnClickListener {
    private static final String TAG = ProfileInfornation.class.getSimpleName();
    private Spinner spGender, spSchoolgender, spAcademicyear, spCity, spClass, spCountry, spProgramcourse,  spState;
    String strArrGender[] = {"Gender","Female", "Male"};
    String strArrList[] = {"Select","Item1", "Item2"};
    private CustomSpinnerAdapter spinnerAdapter;
    private ArrayAdapter<String> adapter;
    private Button btnSuumit;
    private TextView txtUploadpic;
    private ImageView imgDp;
    private static int RESULT_LOAD_IMAGE = 1;
    private int PICK_IMAGE_REQUEST = 1;
    private TextView txtYouare;
    private TextView txtIfits;
    private TextView txtClickhere;
    private EditText etAge, etUsername, etNewpwd, etNameofschool, etHomeadd, etFullname,
            etEmailadd, etDob, etDistrictofschool, etCurrentpwd, etCno,  etConfirmpwd;
    private LayoutInflater mInflator;
    private boolean selected;

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
         spSchoolgender = (Spinner) findViewById(R.id.sp_schoolgender);
         spAcademicyear = (Spinner) findViewById(R.id.sp_academicyear);
         spCity = (Spinner) findViewById(R.id.sp_city);
         spClass = (Spinner) findViewById(R.id.sp_class);
         spCountry = (Spinner) findViewById(R.id.sp_country);
         spState = (Spinner) findViewById(R.id.sp_state);
         spProgramcourse = (Spinner) findViewById(R.id.sp_programcourse);
         etAge = (EditText) findViewById(R.id.et_age);
         etCno = (EditText) findViewById(R.id.et_cno);
         etConfirmpwd = (EditText) findViewById(R.id.et_confirmpwd);
         etCurrentpwd = (EditText) findViewById(R.id.et_currentpwd);
         etDistrictofschool = (EditText) findViewById(R.id.et_districtofschool);
         etDob = (EditText) findViewById(R.id.et_dob);
         etEmailadd = (EditText) findViewById(R.id.et_emailAdd);
         etFullname = (EditText) findViewById(R.id.et_fullname);
         etHomeadd = (EditText) findViewById(R.id.et_homeAdd);
         etNameofschool = (EditText) findViewById(R.id.et_nameofschool);
         etNewpwd = (EditText) findViewById(R.id.et_newpwd);
         etUsername = (EditText) findViewById(R.id.et_username);
         txtUploadpic = (TextView) findViewById(R.id.txt_uploadpic);
         imgDp = (ImageView) findViewById(R.id.img_dp);
         btnSuumit = (Button) findViewById(R.id.btn_suumit);
         txtYouare.setTypeface(myTypeFace.getRalewayThin());
         txtIfits.setTypeface(myTypeFace.getRalewayThin());
         txtClickhere.setTypeface(myTypeFace.getRalewayThin());
         txtUploadpic.setTypeface(myTypeFace.getRalewayRegular());
         etAge.setTypeface(myTypeFace.getRalewayRegular());
         etCno.setTypeface(myTypeFace.getRalewayRegular());
         etConfirmpwd.setTypeface(myTypeFace.getRalewayRegular());
         etCurrentpwd.setTypeface(myTypeFace.getRalewayRegular());
         etDistrictofschool.setTypeface(myTypeFace.getRalewayRegular());
         etDob.setTypeface(myTypeFace.getRalewayRegular());
         etEmailadd.setTypeface(myTypeFace.getRalewayRegular());
         etFullname.setTypeface(myTypeFace.getRalewayRegular());
         etHomeadd.setTypeface(myTypeFace.getRalewayRegular());
         etNameofschool.setTypeface(myTypeFace.getRalewayRegular());
         etNewpwd.setTypeface(myTypeFace.getRalewayRegular());
         etUsername.setTypeface(myTypeFace.getRalewayRegular());

        //  spGender.setTypeface(myTypeFace.getRalewayRegular());

         txtUploadpic.setOnClickListener(this);
         btnSuumit.setOnClickListener(this);
        spinnerAdapter = new CustomSpinnerAdapter(ProfileInfornation.this, strArrGender);
        selected = false;
        mInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, strArrGender);
//        ArrayAdapter.createFromResource(this,
//                R.array.list, R.layout.custom_spinner);
//        adapter.setDropDownViewResource(R.layout.row_spinner);
//         spGender.setAdapter(adapter);
        setUpSpinner(spAcademicyear, strArrList);
        setUpSpinner(spCity, strArrList);
        setUpSpinner(spClass, strArrList);
        setUpSpinner(spProgramcourse, strArrList);
        setUpSpinner(spState, strArrList);
        setUpSpinner(spSchoolgender, strArrList);
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
                Global.myIntent(ProfileInfornation.this, WelComeActivity.class);
            }
            break;
            case R.id.txt_uploadpic: {
                openGallary();
            }
            break;

        }
    }


}
