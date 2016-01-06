package com.ism.author.fragment.mydesk;

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
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.User;
import com.ism.author.utility.Debug;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.AuthorProfile;
import realmhelper.AuthorHelper;

/**
 * Created by c162 on 29/11/15.
 */
public class AboutMeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private View view;
    private TextView txtUserName, txtTotalBooks, txtSocial, txtTotalPost, txtTotalFollowing, txtPost, txtAssignment, txtAcademic, txtTotalAssignment,
            txtExam, txtTotalExam, txtExcellence, txtFavQuestions, txtBadgesEarned, txtTotalBadgesEarned, txtTotalFavQuestions, txtEducation;
    private static String TAG = AboutMeFragment.class.getSimpleName();
    MyDeskFragment myDeskFragment;
    public static ImageView imgProfilePic;
    private TextView txtEducationName;
    private TextView txtBirthdate;
    private TextView txtTotalFollowers;
    private TextView txtFollowing, txtFollowers;
    private TextView txtBooks;
    private TextView txtTotalQueAnswered, txtQuestionAnswered;
    private TextView txtAboutAuthorDetails, txtAboutAuhtor;
    private AuthorHelper authorHelper;

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
        authorHelper = new AuthorHelper(getActivity());
        myDeskFragment = MyDeskFragment.newInstance();

        txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
        txtTotalBooks = (TextView) view.findViewById(R.id.txt_total_books);
        imgProfilePic = (ImageView) view.findViewById(R.id.img_profile_pic);
        authorHelper = new AuthorHelper(getActivity());
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
        txtUserName.setTypeface(Global.myTypeFace.getRalewayBold());
        txtEducation.setTypeface(Global.myTypeFace.getRalewayBold());
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
        txtFollowing.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAssignment.setTypeface(Global.myTypeFace.getRalewayRegular());
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
        AuthorProfile authorProfile=getAuthorData();
        if(authorProfile!=null){
            setUpDBData(authorProfile);
            callApiGetAboutMe();
        }
        else {
            callApiGetAboutMe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"OnDestroy");
        authorHelper.realm.close();
    }

    private void callApiGetAboutMe() {
        try {
            if (com.ism.author.utility.Utility.isConnected(getActivity())) {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setRoleId(Global.role);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_ABOUT_ME);
            } else {
                com.ism.author.utility.Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            com.ism.author.utility.Debug.i(TAG, "callApiGetAboutMe Exception : " + e.getLocalizedMessage());
        }
    }


    private void onResponseGetAboutMe(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseGetAboutMe success");
                    // setUpData(responseObj.getUser().get(0));
                    saveAuthorProfile(responseObj.getUser().get(0));
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

    private void saveAuthorProfile(User user) {
        try {
            AuthorProfile authorProfile = new AuthorProfile();
            model.User userData = authorHelper.getUser(Integer.parseInt(Global.strUserId));
            if (userData != null) {
                authorProfile.setUser(userData);
            } else {
                userData = new model.User();
                userData.setUserId(Integer.parseInt(user.getUserId()));
//                newuser.setUsername(user.getUsername());
                userData.setFullName(user.getUsername());
                userData.setProfilePicture(user.getProfilePic());
                authorHelper.saveUser(userData);
                authorProfile.setUser(userData);
            }
            authorProfile.setServerAuthorId(Integer.parseInt(user.getUserId()));
            authorProfile.setAboutAuthor(user.getAboutAuthor());
            //authorProfile.setContactNumber(user.getContactNumber()); // this field is never used in author module
            authorProfile.setBirthDate(getDateFormate(user.getBirthdate()));
            authorProfile.setEducation(user.getEducation());
            authorProfile.setTotalAssignment(Integer.parseInt(user.getTotalAssignment() == null ? "0" : user.getTotalAssignment()));
            authorProfile.setTotalBadges(Integer.parseInt(user.getTotalBadgesEarned() == null ? "0" : user.getTotalBadgesEarned()));
            authorProfile.setTotalPost(Integer.parseInt(user.getTotalPost() == null ? "0" : user.getTotalPost()));
            authorProfile.setTotalQuestionAnswered(Integer.parseInt(user.getTotalQuestionsAnswered() == null ? "0" : user.getTotalQuestionsAnswered()));
            authorProfile.setTotalFavouritesQuestions(Integer.parseInt(user.getTotalFavoriteQuestions() == null ? "0" : user.getTotalFavoriteQuestions()));
            authorProfile.setTotalFollowers(Integer.parseInt(user.getTotalFollowers() == null ? "0" : user.getTotalFollowers()));
            authorProfile.setTotalFollpwing(Integer.parseInt(user.getTotalFollowing() == null ? "0" : user.getTotalFollowing()));
            authorProfile.setTotalExamCreated(Integer.parseInt(user.getTotalExams() == null ? "0" : user.getTotalExams()));
            authorProfile.setTotalBooks(Integer.parseInt(user.getTotalBooks() == null ? "0" : user.getTotalBooks()));
            authorHelper.saveAuthorProfile(authorProfile);
            setUpDBData(getAuthorData());

        } catch (Exception e) {
            com.ism.author.utility.Debug.i(TAG, "saveAuthorProfile Exceptions: " + e.getLocalizedMessage());
        }
    }

    private AuthorProfile getAuthorData() {
        return authorHelper.getAuthorprofile(Integer.parseInt(Global.strUserId));
    }

    public static Date getDateFormate(String birthdate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(birthdate);
            System.out.println(date);
            System.out.println(formatter.format(date));
            Log.i(TAG, "getDateFormate : " + date);
            return date;
        } catch (ParseException e) {
            Log.i(TAG, "getDateFormate ParseException : " + e.getLocalizedMessage());
        }
        return null;
    }

    public static String getDateFormate(Date date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // Date date = formatter.parse(birthdate);
            System.out.println(date);
            System.out.println(formatter.format(date));
            Log.i(TAG, "getDateFormate : " + date);
            return formatter.format(date);
        } catch (Exception e) {
            Log.i(TAG, "getDateFormate ParseException : " + e.getLocalizedMessage());
        }
        return null;
    }

    private void setUpDBData(model.AuthorProfile data) {
        try {
            txtUserName.setText(data.getUser().getFullName().toUpperCase());
            txtEducationName.setText(data.getEducation());
            txtBirthdate.setText(com.ism.commonsource.utility.Utility.DateFormat(getDateFormate(data.getBirthDate())));

            if (data.getTotalAssignment() == 0)
                txtTotalAssignment.setText("0");
            else
                txtTotalAssignment.setText(data.getTotalAssignment());

            if (data.getAboutAuthor() == null)
                txtAboutAuthorDetails.setText("No inforamation available!");
            else
                txtAboutAuthorDetails.setText(data.getAboutAuthor());

            if (data.getTotalBadges() == 0)
                txtTotalBadgesEarned.setText("0");
            else
                txtTotalBadgesEarned.setText(data.getTotalBadges());

            if (data.getTotalExamCreated() == 0)
                txtTotalExam.setText("0");
            else
                txtTotalExam.setText(data.getTotalExamCreated());


            if (data.getTotalPost() == 0)
                txtTotalPost.setText("0");
            else
                txtTotalPost.setText(data.getTotalPost());

            if (data.getTotalQuestionAnswered() == 0)
                txtTotalQueAnswered.setText("0");
            else
                txtTotalQueAnswered.setText(data.getTotalQuestionAnswered());

            if (data.getTotalBooks() == 0)
                txtTotalBooks.setText("0");
            else
                txtTotalBooks.setText(data.getTotalBooks());

            if (data.getTotalFollowers() == 0)
                txtTotalFollowers.setText("0");
            else
                txtTotalFollowers.setText(data.getTotalFollowers());

            if (data.getTotalFavouritesQuestions() == 0)
                txtTotalFavQuestions.setText("0");
            else
                txtTotalFavQuestions.setText(data.getTotalFavouritesQuestions());

            if (data.getTotalFollpwing() == 0)
                txtTotalFollowing.setText("0");
            else
                txtTotalFollowing.setText(data.getTotalFollpwing());

            txtAboutAuhtor.setText("ABOUT " + data.getUser().getFullName().toUpperCase());
//            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + data.getProfilePic(), imgProfilePic, ISMAuthor.options);
            Global.imageLoader.displayImage(Global.strProfilePic, imgProfilePic, ISMAuthor.options);
        } catch (Exception e) {
            com.ism.author.utility.Debug.i(TAG, "SetupData :" + e.getLocalizedMessage());
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

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach");
        authorHelper.realm.close();
    }
}
