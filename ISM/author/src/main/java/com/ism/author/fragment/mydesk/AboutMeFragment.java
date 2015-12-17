package com.ism.author.fragment.mydesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by c162 on 29/11/15.
 */
public class AboutMeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private View view;
    private TextView txtUserName, txtTotalBooks, txtSocial, txtTotalPost, txtTotalFollowing, txtPost, txtAssignment, txtAcademic, txtTotalAssignment, txtExam, txtTotalExam, txtExcellence, txtFavQuestions, txtBadgesEarned, txtQueAsked, txtTotalBadgesEarned, txtTotalQueAsked, txtTotalFavQuestions, txtYourAmbition, txtAboutMe, txtEducation;
    private static String TAG = AboutMeFragment.class.getSimpleName();
    private AuthorHostActivity activityHost;
    MyDeskFragment myDeskFragment;
    public static ImageView imgProfilePic;
    private Date convertedDate;
    private TextView txtEducationName;
    private TextView txtBirthdate;
    private TextView txtTotalFollowers;
    private TextView txtFollowing, txtFollowers;
    private TextView txtBooks;
    private TextView txtTotalQueAnswered, txtQuestionAnswered;
    private TextView txtAboutAuthorDetails, txtAboutAuhtor;

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
        txtAboutAuthorDetails = (TextView) view.findViewById(R.id.txt_About_author_details);
        txtAboutAuhtor = (TextView) view.findViewById(R.id.txt_About_author);
        txtTotalPost = (TextView) view.findViewById(R.id.txt_total_post);
        txtTotalFollowing = (TextView) view.findViewById(R.id.txt_total_following);
        txtTotalFollowers = (TextView) view.findViewById(R.id.txt_total_followers);
        txtPost = (TextView) view.findViewById(R.id.txt_post);
        txtFollowers = (TextView) view.findViewById(R.id.txt_followers);
        txtFollowing = (TextView) view.findViewById(R.id.txt_following);
        txtAcademic = (TextView) view.findViewById(R.id.txt_academic);
        txtExam = (TextView) view.findViewById(R.id.txt_exam);
        txtTotalExam = (TextView) view.findViewById(R.id.txt_total_exam);
        txtTotalAssignment = (TextView) view.findViewById(R.id.txt_total_assignment);
        txtAssignment = (TextView) view.findViewById(R.id.txt_assignment);
        txtTotalBooks = (TextView) view.findViewById(R.id.txt_total_books);
        txtBooks = (TextView) view.findViewById(R.id.txt_books);
        txtFavQuestions = (TextView) view.findViewById(R.id.txt_fav_questions);
        txtTotalFavQuestions = (TextView) view.findViewById(R.id.txt_total_fav_questions);

        txtExcellence = (TextView) view.findViewById(R.id.txt_excellence);
        txtQuestionAnswered = (TextView) view.findViewById(R.id.txt_que_answered);
        txtBadgesEarned = (TextView) view.findViewById(R.id.txt_badges_earned);
        txtTotalBadgesEarned = (TextView) view.findViewById(R.id.txt_total_badges_earned);
        txtTotalQueAnswered = (TextView) view.findViewById(R.id.txt_total_que_answered);
        txtEducation = (TextView) view.findViewById(R.id.txt_education);
        txtEducationName = (TextView) view.findViewById(R.id.txt_education_name);
        txtBirthdate = (TextView) view.findViewById(R.id.txt_birthdate);


        //set typeface
        txtTotalBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtUserName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEducation.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEducationName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBirthdate.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtAboutAuhtor.setTypeface(Global.myTypeFace.getRalewayBold());
        txtAboutAuthorDetails.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSocial.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalPost.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalFollowing.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalFollowers.setTypeface(Global.myTypeFace.getRalewayBold());
        txtPost.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAcademic.setTypeface(Global.myTypeFace.getRalewayRegular());
        //txtAssignment.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFollowing.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAssignment.setTypeface(Global.myTypeFace.getRalewayRegular());
        //txtTotalAssignment.setTypeface(Global.myTypeFace.getRalewayBold());
        txtBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalBooks.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalAssignment.setTypeface(Global.myTypeFace.getRalewayBold());
        txtExam.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalExam.setTypeface(Global.myTypeFace.getRalewayBold());

        txtExcellence.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFavQuestions.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBadgesEarned.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalQueAnswered.setTypeface(Global.myTypeFace.getRalewayBold());
        txtTotalBadgesEarned.setTypeface(Global.myTypeFace.getRalewayBold());
        txtQuestionAnswered.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalFavQuestions.setTypeface(Global.myTypeFace.getRalewayBold());
        txtEducation.setTypeface(Global.myTypeFace.getRalewayRegular());
        callApiGetAboutMe();

    }

    private void callApiGetAboutMe() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setRoleId(Global.role);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_ABOUT_ME);
            } else {
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


    //used for changed date format in 14th May 2015
    private String dateFormat(String birthdate) {
        try {
            String strDob = birthdate;
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

    private void setUpData(User data) {
        try {
            txtUserName.setText(data.getUsername());
            txtEducationName.setText(data.getEducation());
            txtBirthdate.setText(dateFormat(data.getBirthdate()));
            txtTotalAssignment.setText(data.getTotalAssignment());
            txtAboutAuthorDetails.setText(data.getAboutAuthor());
            txtTotalExam.setText(data.getTotalExams());
            txtTotalBadgesEarned.setText(data.getTotalBadgesEarned());
            txtTotalQueAnswered.setText(data.getTotalQuestionsAnswered());
            txtTotalFavQuestions.setText(data.getTotalFavoriteQuestions());
            txtTotalFollowers.setText(data.getTotalFollowers());
            txtTotalFollowing.setText(data.getTotalFollowing());
            txtTotalPost.setText(data.getTotalPost());
            txtTotalBooks.setText(data.getTotalBooks());
            txtAboutAuhtor.setText("ABOUT " + data.getUsername().toUpperCase());
//            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + data.getProfilePic(), imgProfilePic, ISMAuthor.options);
            Global.imageLoader.displayImage(Global.strProfilePic, imgProfilePic, ISMAuthor.options);
        } catch (Exception e) {
            Debug.i(TAG, "SetupData :" + e.getLocalizedMessage());
        }
    }


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
