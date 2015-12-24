package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.User;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by c162 on 09/11/15.
 */
public class AboutMeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {
    public static final int ABOUT_ME = 100;
    public static final int YOUR_AMBITION = 101;
    public static final int PICK_IMAGE_REQUEST = 200;
    public static final String USERNAME = "username";
    public static final String BIRTHDATE = "birthdate";
    public static final String CONTACT_NUMBER = "contact_number";
    public static final String ABOUT_ME_DETAILS = "about_me_details";
    public static final String AMBITION = "ambition";
    public static final String EDIT_TYPE = "type";

    // public static final int RESULT_OK = 300;
    private View view;
    private TextView txtUserName, txtSchool, txtClass, txtSocial, txtTotalPost, txtTotalStudymates, txtTotalAuthorFollowed, txtPost, txtAssignment, txtAuthorFollowed, txtAcademic, txtStudymates, txtIsmScore, txtTotalIsmScore, txtIsmRank, txtTotalIsmRank, txtTotalAssignment, txtExam, txtTotalExam, txtExcellence, txtFavQuestions, txtBadgesEarned, txtQueAsked, txtTotalBadgesEarned, txtTotalQueAsked, txtTotalFavQuestions, txtYourAmbition, txtAboutMe, txtClickAddAboutMe, txtClickAddAmbitions;
    private static String TAG = AboutMeFragment.class.getSimpleName();
    private HostActivity activityHost;

    com.ism.fragment.userprofile.EditProfileFragment editProfileFragment;
    private TextView txtEdit;
    private EditText etCno, etDob;
    public static ImageView imgProfilePic, imgProfileEdit;
    private boolean editable = false;
    private Calendar calDob;
    private DatePickerDialog datePickerDob;
    private long lngMaxDob;
    private String strDob;
    private Date convertedDate;
    public static String strDetailAboutMe = "";
    public static String strAmbition = "";
    private ImageView imgEditAmbition, imgEditAboutMe;

    public static AboutMeFragment newInstance() {
        AboutMeFragment fragment = new AboutMeFragment();
        return fragment;
    }

    public AboutMeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_me, container, false);

        initGlobal();

        return view;
    }


    private void initGlobal() {
        editProfileFragment = com.ism.fragment.userprofile.EditProfileFragment.newInstance();

        txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
        txtEdit = (TextView) view.findViewById(R.id.txt_edit);
        txtSchool = (TextView) view.findViewById(R.id.txt_school);
        etCno = (EditText) view.findViewById(R.id.et_contactNo);
        etDob = (EditText) view.findViewById(R.id.et_dob);
        txtClass = (TextView) view.findViewById(R.id.txt_class);
        imgProfileEdit = (ImageView) view.findViewById(R.id.img_profile_edit);
        imgProfilePic = (ImageView) view.findViewById(R.id.img_profile_pic);
        imgEditAboutMe = (ImageView) view.findViewById(R.id.img_edit_about_me);
        imgEditAmbition = (ImageView) view.findViewById(R.id.img_edit_your_ambition);

        txtSocial = (TextView) view.findViewById(R.id.txt_social);
        txtTotalPost = (TextView) view.findViewById(R.id.txt_total_post);
        txtTotalStudymates = (TextView) view.findViewById(R.id.txt_total_Studymates);
        txtTotalAuthorFollowed = (TextView) view.findViewById(R.id.txt_total_author_followed);
        txtPost = (TextView) view.findViewById(R.id.txt_post);
        txtAssignment = (TextView) view.findViewById(R.id.txt_assignment);
        txtAuthorFollowed = (TextView) view.findViewById(R.id.txt_author_followed);
        txtAcademic = (TextView) view.findViewById(R.id.txt_academic);
        txtStudymates = (TextView) view.findViewById(R.id.txt_studymates);
        txtIsmScore = (TextView) view.findViewById(R.id.txt_ism_score);
        txtTotalIsmScore = (TextView) view.findViewById(R.id.txt_total_ism_score);
        txtIsmRank = (TextView) view.findViewById(R.id.txt_ism_rank);
        txtTotalIsmRank = (TextView) view.findViewById(R.id.txt_total_ism_rank);
        txtTotalAssignment = (TextView) view.findViewById(R.id.txt_total_assignment);
        txtExam = (TextView) view.findViewById(R.id.txt_exam);
        txtTotalExam = (TextView) view.findViewById(R.id.txt_total_exam);

        txtExcellence = (TextView) view.findViewById(R.id.txt_excellence);
        txtFavQuestions = (TextView) view.findViewById(R.id.txt_fav_questions);
        txtBadgesEarned = (TextView) view.findViewById(R.id.txt_badges_earned);
        txtQueAsked = (TextView) view.findViewById(R.id.txt_que_asked);
        txtTotalBadgesEarned = (TextView) view.findViewById(R.id.txt_total_badges_earned);
        txtTotalQueAsked = (TextView) view.findViewById(R.id.txt_total_que_asked);
        txtTotalFavQuestions = (TextView) view.findViewById(R.id.txt_total_fav_questions);
        txtYourAmbition = (TextView) view.findViewById(R.id.txt_your_ambition);
        txtAboutMe = (TextView) view.findViewById(R.id.txt_about_me);
        txtClickAddAboutMe = (TextView) view.findViewById(R.id.txt_clickAddAboutMe);
        txtClickAddAmbitions = (TextView) view.findViewById(R.id.txt_clickAddAmbitions);

        //set typeface
        txtClass.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSchool.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtUserName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEdit.setTypeface(Global.myTypeFace.getRalewayRegular());
        etCno.setTypeface(Global.myTypeFace.getRalewayRegular());
        etDob.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtSocial.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalPost.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalStudymates.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalAuthorFollowed.setTypeface(Global.myTypeFace.getRalewayBold());
        txtPost.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAssignment.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAuthorFollowed.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAcademic.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtStudymates.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtIsmScore.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalIsmScore.setTypeface(Global.myTypeFace.getRalewayBold());
        txtIsmRank.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalIsmRank.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalAssignment.setTypeface(Global.myTypeFace.getRalewayBold());
        txtExam.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalExam.setTypeface(Global.myTypeFace.getRalewayBold());

        txtExcellence.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFavQuestions.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBadgesEarned.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtQueAsked.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalBadgesEarned.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalQueAsked.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalFavQuestions.setTypeface(Global.myTypeFace.getRalewayBold());

        txtYourAmbition.setTypeface(Global.myTypeFace.getRalewayBold());
        txtAboutMe.setTypeface(Global.myTypeFace.getRalewayBold());
        txtClickAddAboutMe.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtClickAddAmbitions.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEdit.setText(R.string.strEdit);
        txtEdit.setOnClickListener(this);
        txtClickAddAboutMe.setOnClickListener(this);
        txtClickAddAmbitions.setOnClickListener(this);
        imgEditAboutMe.setOnClickListener(this);
        imgEditAmbition.setOnClickListener(this);
        imgProfileEdit.setOnClickListener(this);

        setEditableFalse(etDob);
        setEditableFalse(etCno);
        callApiGetAboutMe();
        etDob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDatePickerDob();
                }
                return true;
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
                    etDob.setText(dateFormat(strDob));
                }
            }, calDob.get(Calendar.YEAR), calDob.get(Calendar.MONTH), calDob.get(Calendar.DAY_OF_MONTH));
            datePickerDob.getDatePicker().setMaxDate(lngMaxDob);
            datePickerDob.show();
        } catch (Exception e) {
            Log.e(TAG, "showDatePickerDob Exception : " + e.toString());
        }
    }

    private void setEditable(EditText editText) {
        editText.setEnabled(true);
        editText.setClickable(true);
        editText.requestFocus();
    }

    private void setEditableFalse(EditText editText) {
        editText.setEnabled(false);
        editText.setClickable(false);
        editText.setTextColor(Color.BLACK);
        editText.clearFocus();
    }

    private void callApiGetAboutMe() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setRoleId(Global.roleID);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_ABOUT_ME);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetAboutMe Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiEditAboutMe() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setUsername(txtUserName.getText().toString().trim());
                attribute.setContactNumber(etCno.getText().toString().trim());
                attribute.setBirthdate(strDob);
                attribute.setProfileImage("");
                attribute.setAmbitionInLife(strAmbition);
                attribute.setAboutMeText(strDetailAboutMe);

//            requestObject.setAmbitionInLife("Businessman");
//            requestObject.setAboutMeText("I am a graduate from NIFT specializing in Apparel Production. I have a holistic experience of the Apparel Industry and has worked for domestic as well as the exports market. In the Indian retail industry I have worked with Lifestyle International Pvt. Ltd. on sourcing, vendor management and product development for private labels. I then moved to Madura Fashion & Lifestyle where I worked as a buyer. Product and Margin management, optimum allocation of merchandise, meeting sales targets along with competition, market and trend analysis were some of her responsibilities. I joined ISB to fast track my career and pursue opportunities in Category & Brand Management.I am President of the Retail Club. I  proud myself.");

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.EDIT_ABOUT_ME);
            } else {
                Utility.alertOffline(getActivity());
            }

        } catch (Exception e) {
            Debug.i(TAG, "callApiEditAboutMe Exception : " + e.getLocalizedMessage());
        }
    }

    public void openGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // imgDp.setImageBitmap(bitmap);
//                imageURI = uri;
//                 sourceFile = new File(getPathImage(imageURI));
//                if (!sourceFile.isFile()) {
//                    Debug.e(TAG, "Source File Does not exist");
//                }
//                else{
//                    Debug.e(TAG, "Source File exist");
//                }
                Log.e(TAG, "uri :" + uri + " Uri path :" + uri.getPath());
                imgProfilePic.setImageBitmap(bitmap);
                File file = new File(Utility.getImagePath(uri, getActivity()));// fileName = file;
                Log.e(TAG, "uri :" + uri + " file :" + file);
                if (!file.isFile()) {
                    Debug.e(TAG, "Source File Does not exist");
                } else {
                    new com.ism.fragment.userprofile.EditProfileImageAsync(file).execute();
                }
                // hostListenerAboutMe.onSelectImage(bitmap);
                // strDpBase64 = Utility.getBase64ForImage(bitmap);

            } else if (requestCode == AboutMeFragment.ABOUT_ME) {
                onAboutMe();
            } else if (requestCode == AboutMeFragment.YOUR_AMBITION) {
                onAmbition();
            }
        } catch (IOException e) {
            // strDpBase64 = "";
            Debug.e(TAG, "onActivityResult convert Image Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        try {
            switch (apiCode) {
                case WebConstants.GET_ABOUT_ME:
                    onResponseGetAboutMe(object, error);
                    break;
                case WebConstants.EDIT_ABOUT_ME:
                    onResponseEditAboutMe(object, error);
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseEditAboutMe(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseEditAboutMe success");
                } else if (responseObj.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseEditAboutMe Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseEditAboutMe api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseEditAboutMe Exception : " + e.toString());
        }
    }


    private void onResponseGetAboutMe(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseGetAboutMe success");
                    setUpData(responseObj.getUser().get(0));

                } else if (responseObj.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseGetAboutMe Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetAboutMe api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAboutMe Exception : " + e.toString());
        }
    }

    private void setUpData(User data) {
        try {
            txtUserName.setText(data.getUsername());
            txtSchool.setText(data.getSchoolName());
            txtClass.setText(data.getCourseName());
            etDob.setText(dateFormat(data.getBirthdate()));
            etCno.setText(data.getContactNumber());
          //  Global.strProfilePic=WebConstants.HOST_IMAGE_USER + data.getProfilePic();
            Debug.i(TAG, "WebConstants.HOST_IMAGE_USER_OLD + data.getProfilePic() :" + WebConstants.HOST_IMAGE_USER + data.getProfilePic());
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + data.getProfilePic(), imgProfilePic, ISMStudent.options);
            if(data.getTotalAssignment()==null)
            txtTotalAssignment.setText("0");
            else
            txtTotalAssignment.setText(data.getTotalAssignment());
            if(data.getTotalAuthorsFollowed()==null)
            txtTotalAuthorFollowed.setText("0");
            else
            txtTotalAuthorFollowed.setText(data.getTotalAuthorsFollowed());
            if(data.getTotalBadgesEarned()==null)
            txtTotalBadgesEarned.setText("0");
            else
                txtTotalBadgesEarned.setText(data.getTotalBadgesEarned());
            if(data.getTotalExams()==null)
            txtTotalExam.setText("0");
            else
                txtTotalExam.setText(data.getTotalExams());
            if(data.getTotalFavoriteQuestions()==null)
            txtTotalFavQuestions.setText("0");
            else
                txtTotalFavQuestions.setText(data.getTotalFavoriteQuestions());
            if(data.getIsmRank()==null)
            txtTotalIsmRank.setText("0");
            else
                txtTotalIsmRank.setText(data.getIsmRank());
            if(data.getIsmScore()==null)
            txtTotalIsmScore.setText("0");
            else
            txtTotalIsmScore.setText(data.getIsmScore());
            if(data.getTotalPost()==null)
            txtTotalPost.setText("0");
            else
            txtTotalPost.setText(data.getTotalPost());
            if(data.getTotalQuestionAsked()==null)
            txtTotalQueAsked.setText("0");
            else
                txtTotalQueAsked.setText(data.getTotalQuestionAsked());
            if(data.getTotalStudymates()==null)
            txtTotalStudymates.setText("0");
            else
                txtTotalStudymates.setText(data.getTotalStudymates());
//            imageLoader.displayImage(Global.strProfilePic, imgProfilePic, ISMStudent.options);
            strDetailAboutMe = data.getAboutMeText();
            strAmbition = data.getAmbitionInLife();
            if (strDetailAboutMe!=null && strDetailAboutMe.length() != 0 ) {
                txtClickAddAboutMe.setText(data.getAboutMeText());
                txtClickAddAboutMe.setCompoundDrawables(null, null, null, null);
                imgEditAboutMe.setVisibility(View.VISIBLE);
                Debug.i(TAG, "Details are available!");
            } else {
                txtClickAddAboutMe.setText(getResources().getString(R.string.strClickToWriteAboutYourSelf));
                imgEditAboutMe.setVisibility(View.GONE);
                Debug.i(TAG, "Details are not available!");
            }
            if (strAmbition!=null && strAmbition .length()!=0) {
                txtClickAddAmbitions.setText(data.getAmbitionInLife());
                imgEditAmbition.setVisibility(View.VISIBLE);
                txtClickAddAmbitions.setCompoundDrawables(null, null, null, null);
                Debug.i(TAG, "Details are available!");
            } else {
                Debug.i(TAG, "Details are not available!");
                txtClickAddAmbitions.setText(getResources().getString(R.string.strClickTOAddAmbitionInLife));
                imgEditAmbition.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Debug.i(TAG, "SetupData :" + e.getLocalizedMessage());
        }
    }

    //used for changed date format in 14th May 2015
    private String dateFormat(String birthdate) {
        try {
            strDob = birthdate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            convertedDate = new Date();
            convertedDate = dateFormat.parse(birthdate);
            System.out.println(convertedDate);
            SimpleDateFormat format = new SimpleDateFormat("dd");
            String date = format.format(convertedDate);

            Debug.i(TAG, "Date :" + date);

            if (date.endsWith("1") && !date.endsWith("11"))
                format = new SimpleDateFormat("dd'st' MMMM yyyy");
            else if (date.endsWith("2") && !date.endsWith("12"))
                format = new SimpleDateFormat("dd'nd' MMMM yyyy");
            else if (date.endsWith("3") && !date.endsWith("13"))
                format = new SimpleDateFormat("dd'rd' MMMM yyyy");
            else
                format = new SimpleDateFormat("dd'th' MMMM yyyy");
            Debug.i(TAG, "Date Formated:" + format.format(convertedDate));
            return format.format(convertedDate);
        } catch (ParseException e) {
            Debug.i(TAG, "DateFormat ParseException : " + e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            Debug.i(TAG, "DateFormat Exceptions : " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == txtEdit && !editable) {
            setEditable(etDob);
            setEditable(etCno);
            editable = true;
            txtEdit.setText("Save");

        } else if (v == txtEdit && editable) {
            setEditableFalse(etDob);
            setEditableFalse(etCno);
            editable = false;
            txtEdit.setText("Edit");
            callApiEditAboutMe();

        } else if (v == imgProfileEdit) {
            openGallary();
            //callApiEditAboutMe();
        } else if (v == txtClickAddAboutMe) {
            if (strDetailAboutMe .length()==0) {
                editDetails(ABOUT_ME);
            }

        } else if (v == txtClickAddAmbitions) {
            if (strAmbition.length()==0) {
                editDetails(YOUR_AMBITION);
            }

        } else if (v == imgEditAmbition) {
            editDetails(YOUR_AMBITION);
            // myPopup(YOUR_AMBITION);

        } else if (v == imgEditAboutMe) {
            editDetails(ABOUT_ME);

        }

    }

    private void editDetails(int type) {
        Intent intent = new Intent(getActivity(), com.ism.fragment.userprofile.EditAboutMeDetailsActivity.class);
        intent.putExtra(USERNAME, txtUserName.getText().toString());
        intent.putExtra(BIRTHDATE, strDob);
        intent.putExtra(CONTACT_NUMBER, etCno.getText().toString());
        intent.putExtra(AMBITION, strAmbition);
        intent.putExtra(ABOUT_ME_DETAILS, strDetailAboutMe);
        intent.putExtra(EDIT_TYPE, type);
        startActivityForResult(intent, type);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // callApiGetAboutMe();
    }

    public void onAmbition() {

        if (strAmbition != null && strAmbition.length()!=0) {
            txtClickAddAmbitions.setText(strAmbition);
            imgEditAmbition.setVisibility(View.VISIBLE);
            txtClickAddAmbitions.setCompoundDrawables(null, null, null, null);
            Debug.i(TAG, "Details are available!");
        } else {
            Debug.i(TAG, "Details are not available!");
            txtClickAddAmbitions.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.aroow_gray), null);
            txtClickAddAmbitions.setText(getResources().getString(R.string.strClickTOAddAmbitionInLife));
            imgEditAmbition.setVisibility(View.GONE);
        }
    }

    public void onAboutMe() {
        if (strDetailAboutMe != null && strDetailAboutMe.length() != 0 ) {
            txtClickAddAboutMe.setText(strDetailAboutMe);
            txtClickAddAboutMe.setCompoundDrawables(null, null, null, null);
            imgEditAboutMe.setVisibility(View.VISIBLE);
            Debug.i(TAG, "Details are available!");
        } else {
            txtClickAddAboutMe.setCompoundDrawables(null, null,getResources().getDrawable(R.drawable.aroow_gray), null);
            txtClickAddAboutMe.setText(getResources().getString(R.string.strClickToWriteAboutYourSelf));
            imgEditAboutMe.setVisibility(View.GONE);
            Debug.i(TAG, "Details are not available!");
        }
    }
}
