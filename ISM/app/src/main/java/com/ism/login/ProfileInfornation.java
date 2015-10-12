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
    String TAG = "ProfileInfornation";
    private Spinner pinfo_sp_gender, pinfo_sp_schoolgender, pinfo_sp_academicyear, pinfo_sp_city, pinfo_sp_class, pinfo_sp_country, pinfo_sp_programcourse, pinfo_sp_state;
    String strArrGender[] = {"Gender","Female", "Male"};
    String strArrList[] = {"Select","Item1", "Item2"};
    private CustomSpinnerAdapter spinnerAdapter;
    private ArrayAdapter<String> adapter;
    private Button pinfo_btn_suumit;
    private TextView pinfo_txt_uploadpic;
    private ImageView pinfo_img_dp;
    private static int RESULT_LOAD_IMAGE = 1;
    private int PICK_IMAGE_REQUEST = 1;
    private TextView pinfo_txt_youare_;
    private TextView pinfo_txt_ifits_;
    private TextView pinfo_txt_clickhere;
    private EditText pinfo_et_age, pinfo_et_username, pinfo_et_newpwd, pinfo_et_nameofschool, pinfo_et_homeadd, pinfo_et_fullname,
            pinfo_et_emailadd, pinfo_et_dob, pinfo_et_districtofschool, pinfo_et_currentpwd, pinfo_et_cno, pinfo_et_confirmpwd;
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
        pinfo_sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        pinfo_txt_ifits_ = (TextView) findViewById(R.id.pinfo_txt_ifits_);
        pinfo_txt_clickhere = (TextView) findViewById(R.id.pinfo_txt_clickhere);
        pinfo_txt_youare_ = (TextView) findViewById(R.id.pinfo_txt_youare_);
        pinfo_sp_gender = (Spinner) findViewById(R.id.sp_gender);
        pinfo_sp_schoolgender = (Spinner) findViewById(R.id.pinfo_sp_schoolgender);
        pinfo_sp_academicyear = (Spinner) findViewById(R.id.pinfo_sp_academicyear);
        pinfo_sp_city = (Spinner) findViewById(R.id.sp_city);
        pinfo_sp_class = (Spinner) findViewById(R.id.pinfo_sp_class);
        pinfo_sp_country = (Spinner) findViewById(R.id.pinfo_sp_country);
        pinfo_sp_state = (Spinner) findViewById(R.id.pinfo_sp_state);
        pinfo_sp_programcourse = (Spinner) findViewById(R.id.pinfo_sp_programcourse);
        pinfo_et_age = (EditText) findViewById(R.id.et_age);
        pinfo_et_cno = (EditText) findViewById(R.id.et_cno);
        pinfo_et_confirmpwd = (EditText) findViewById(R.id.pinfo_et_confirmpwd);
        pinfo_et_currentpwd = (EditText) findViewById(R.id.pinfo_et_currentpwd);
        pinfo_et_districtofschool = (EditText) findViewById(R.id.pinfo_et_districtofschool);
        pinfo_et_dob = (EditText) findViewById(R.id.et_dob);
        pinfo_et_emailadd = (EditText) findViewById(R.id.et_emailAdd);
        pinfo_et_fullname = (EditText) findViewById(R.id.pinfo_et_fullname);
        pinfo_et_homeadd = (EditText) findViewById(R.id.et_homeAdd);
        pinfo_et_nameofschool = (EditText) findViewById(R.id.pinfo_et_nameofschool);
        pinfo_et_newpwd = (EditText) findViewById(R.id.pinfo_et_newpwd);
        pinfo_et_username = (EditText) findViewById(R.id.pinfo_et_username);
        pinfo_txt_uploadpic = (TextView) findViewById(R.id.txt_uploadpic);
        pinfo_img_dp = (ImageView) findViewById(R.id.img_dp);
        pinfo_btn_suumit = (Button) findViewById(R.id.pinfo_btn_suumit);
        pinfo_txt_youare_.setTypeface(myTypeFace.getRalewayThin());
        pinfo_txt_ifits_.setTypeface(myTypeFace.getRalewayThin());
        pinfo_txt_clickhere.setTypeface(myTypeFace.getRalewayThin());
        pinfo_txt_uploadpic.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_age.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_cno.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_confirmpwd.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_currentpwd.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_districtofschool.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_dob.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_emailadd.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_fullname.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_homeadd.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_nameofschool.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_newpwd.setTypeface(myTypeFace.getRalewayRegular());
        pinfo_et_username.setTypeface(myTypeFace.getRalewayRegular());

        // pinfo_sp_gender.setTypeface(myTypeFace.getRalewayRegular());

        pinfo_txt_uploadpic.setOnClickListener(this);
        pinfo_btn_suumit.setOnClickListener(this);
        spinnerAdapter = new CustomSpinnerAdapter(ProfileInfornation.this, strArrGender);
        selected = false;
        mInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, strArrGender);
//        ArrayAdapter.createFromResource(this,
//                R.array.list, R.layout.custom_spinner);
//        adapter.setDropDownViewResource(R.layout.raw_spinner);
//        pinfo_sp_gender.setAdapter(adapter);
        setUpSpinner(pinfo_sp_academicyear, strArrList);
        setUpSpinner(pinfo_sp_city, strArrList);
        setUpSpinner(pinfo_sp_class, strArrList);
        setUpSpinner(pinfo_sp_programcourse, strArrList);
        setUpSpinner(pinfo_sp_state, strArrList);
        setUpSpinner(pinfo_sp_schoolgender, strArrList);
        setUpSpinner(pinfo_sp_country, strArrList);
        setUpSpinner(pinfo_sp_gender, strArrGender);

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
        adapter.setDropDownViewResource(R.layout.raw_spinner);
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
                pinfo_img_dp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pinfo_btn_suumit: {
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
