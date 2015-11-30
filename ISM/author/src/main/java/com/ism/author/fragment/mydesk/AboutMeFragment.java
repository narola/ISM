package com.ism.author.fragment.mydesk;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by c162 on 29/11/15.
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
    private TextView txtUserName,  txtTotalBooks, txtSocial, txtTotalPost, txtTotalStudymates, txtTotalAuthorFollowed, txtPost, txtAssignment, txtAuthorFollowed, txtAcademic, txtStudymates, txtIsmScore, txtTotalIsmScore, txtIsmRank, txtTotalIsmRank, txtTotalAssignment, txtExam, txtTotalExam, txtExcellence, txtFavQuestions, txtBadgesEarned, txtQueAsked, txtTotalBadgesEarned, txtTotalQueAsked, txtTotalFavQuestions, txtYourAmbition, txtAboutMe, txtEducation;
    private static String TAG = AboutMeFragment.class.getSimpleName();
    private AuthorHostActivity activityHost;
    MyDeskFragment myDeskFragment;
    public static ImageView imgProfilePic;
    private boolean editable = false;
    private Calendar calDob;
    private DatePickerDialog datePickerDob;
    private String strDob;
    private Date convertedDate;
    private TextView txtEducationName;
    private TextView txtBirthdate;

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
        myDeskFragment = MyDeskFragment.newInstance();

        txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
        txtTotalBooks = (TextView) view.findViewById(R.id.txt_total_books);
        imgProfilePic = (ImageView) view.findViewById(R.id.img_profile_pic);

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

        txtEducation = (TextView) view.findViewById(R.id.txt_education);
        txtEducationName = (TextView) view.findViewById(R.id.txt_education_name);
        txtBirthdate = (TextView) view.findViewById(R.id.txt_birthdate);


        //set typeface
        txtTotalBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtUserName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEducation.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEducationName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBirthdate.setTypeface(Global.myTypeFace.getRalewayRegular());

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

        txtEducation.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEducation.setOnClickListener(this);

        callApiGetAboutMe();

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
            if(Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_ABOUT_ME);
            }
            else{
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiGetAboutMe Exception : " + e.getLocalizedMessage());
        }
    }





    private void onResponseGetAboutMe(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseGetAboutMe success");
                    //setUpData(responseObj.getUser().get(0));

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

//    private void setUpData(User data) {
//        try {
//            txtUserName.setText(data.getUsername());
//            txtSchool.setText(data.getSchoolName());
//            txtTotalBooks.setText(data.getCourseName());
//            etDob.setText(dateFormat(data.getBirthdate()));
//            etCno.setText(data.getContactNumber());
//            // imgProfilePic.setBackgroundColor(Color.BLACK);
//            strDetailAboutMe = data.getAboutMeText();
//            strAmbition = data.getAmbitionInLife();
//            if (strDetailAboutMe.length() != 0) {
//                txtEducation.setText(data.getAboutMeText());
//                txtEducation.setCompoundDrawables(null, null, null, null);
//                imgEditAboutMe.setVisibility(View.VISIBLE);
//                Debug.i(TAG, "Details are available!");
//            } else {
//                txtEducation.setText(getResources().getString(R.string.strClickToWriteAboutYourSelf));
//                imgEditAboutMe.setVisibility(View.GONE);
//                Debug.i(TAG, "Details are not available!");
//            }
//            if (strAmbition != null) {
//                txtClickAddAmbitions.setText(data.getAmbitionInLife());
//                imgEditAmbition.setVisibility(View.VISIBLE);
//                txtClickAddAmbitions.setCompoundDrawables(null, null, null, null);
//                Debug.i(TAG, "Details are available!");
//            } else {
//                Debug.i(TAG, "Details are not available!");
//                txtClickAddAmbitions.setText(getResources().getString(R.string.strClickTOAddAmbitionInLife));
//                imgEditAmbition.setVisibility(View.VISIBLE);
//            }
//            txtTotalAssignment.setText(data.getTotalAssignment());
//            txtTotalAuthorFollowed.setText(data.getTotalAuthorsFollowed());
//            txtTotalBadgesEarned.setText(data.getTotalBadgesEarned());
//            txtTotalExam.setText(data.getTotalExams());
//            txtTotalFavQuestions.setText(data.getTotalFavoriteQuestions());
//            txtTotalIsmRank.setText(data.getIsmRank());
//            txtTotalIsmScore.setText(data.getIsmScore());
//            txtTotalPost.setText(data.getTotalPost());
//            txtTotalQueAsked.setText(data.getTotalQuestionAsked());
//            txtTotalStudymates.setText(data.getTotalStudymates());
//            imageLoader.displayImage(WebConstants.URL_USERS_IMAGE_PATH + data.getProfilePic(), imgProfilePic, ISMStudent.options);
//        } catch (Exception e) {
//            Debug.i(TAG,"SetupData :" +e.getLocalizedMessage());
//        }
//    }


    //used for changed date format in 14th May 2015
    private String dateFormat(String birthdate) {
        try {
            strDob = birthdate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
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
            Debug.i(TAG, "dateFormat ParseException : " + e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            Debug.i(TAG, "dateFormat Exceptions : " + e.getLocalizedMessage());
            return null;
        }


    }

    @Override
    public void onClick(View v) {
//        if (v == txtEdit && !editable) {
//            setEditable(etDob);
//            setEditable(etCno);
//            editable = true;
//            txtEdit.setText("Save");
//
//        }
    }

    View.OnTouchListener customPopUpTouchListenr = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            Log.d("POPUP", "Touch false");
            return false;
        }

    };



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            //   activityHost.setListenerHostAboutMe(this);
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
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GET_ABOUT_ME:
                    onResponseGetAboutMe(object, error);
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }
}
