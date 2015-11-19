package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.RequestObject;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;
import com.ism.ws.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
    MyTypeFace myTypeFace;
    private static String TAG = AboutMeFragment.class.getSimpleName();
    private HostActivity activityHost;
    private String[] strArrayList;
    EditProfileFragment editProfileFragment;
    private TextView txtEdit;
    private EditText etCno, etDob;
    public static ImageView imgProfilePic, imgProfileEdit;
    ImageLoader imageLoader;
    private boolean editable = false;
    private Calendar calDob;
    private DatePickerDialog datePickerDob;
    private long lngMaxDob;
    private String strDob;
    private Date convertedDate;
    private String strDetailAboutMe=null;
    private String strAmbition=null;

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
        editProfileFragment = EditProfileFragment.newInstance();

        myTypeFace = new MyTypeFace(getActivity());

        txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
        txtEdit = (TextView) view.findViewById(R.id.txt_edit);
        txtSchool = (TextView) view.findViewById(R.id.txt_school);
        etCno = (EditText) view.findViewById(R.id.et_contactNo);
        etDob = (EditText) view.findViewById(R.id.et_dob);
        txtClass = (TextView) view.findViewById(R.id.txt_class);
        imgProfileEdit = (ImageView) view.findViewById(R.id.img_profile_edit);
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

        txtYourAmbition = (TextView) view.findViewById(R.id.txt_your_ambition);
        txtAboutMe = (TextView) view.findViewById(R.id.txt_about_me);
        txtClickAddAboutMe = (TextView) view.findViewById(R.id.txt_clickAddAboutMe);
        txtClickAddAmbitions = (TextView) view.findViewById(R.id.txt_clickAddAmbitions);


        //set typeface
        txtClass.setTypeface(myTypeFace.getRalewayRegular());
        txtSchool.setTypeface(myTypeFace.getRalewayRegular());
        txtUserName.setTypeface(myTypeFace.getRalewayRegular());
        txtEdit.setTypeface(myTypeFace.getRalewayRegular());
        etCno.setTypeface(myTypeFace.getRalewayRegular());
        etDob.setTypeface(myTypeFace.getRalewayRegular());

        txtSocial.setTypeface(myTypeFace.getRalewayRegular());
        txtTotalPost.setTypeface(myTypeFace.getRalewayBold());
        txtTotalStudymates.setTypeface(myTypeFace.getRalewayBold());
        txtTotalAuthorFollowed.setTypeface(myTypeFace.getRalewayBold());
        txtPost.setTypeface(myTypeFace.getRalewayRegular());
        txtAssignment.setTypeface(myTypeFace.getRalewayRegular());
        txtAuthorFollowed.setTypeface(myTypeFace.getRalewayRegular());
        txtAcademic.setTypeface(myTypeFace.getRalewayRegular());
        txtStudymates.setTypeface(myTypeFace.getRalewayRegular());
        txtIsmScore.setTypeface(myTypeFace.getRalewayRegular());
        txtTotalIsmScore.setTypeface(myTypeFace.getRalewayBold());
        txtIsmRank.setTypeface(myTypeFace.getRalewayRegular());
        txtTotalIsmRank.setTypeface(myTypeFace.getRalewayBold());
        txtTotalAssignment.setTypeface(myTypeFace.getRalewayBold());
        txtExam.setTypeface(myTypeFace.getRalewayRegular());
        txtTotalExam.setTypeface(myTypeFace.getRalewayBold());

        txtExcellence.setTypeface(myTypeFace.getRalewayRegular());
        txtFavQuestions.setTypeface(myTypeFace.getRalewayRegular());
        txtBadgesEarned.setTypeface(myTypeFace.getRalewayRegular());
        txtQueAsked.setTypeface(myTypeFace.getRalewayRegular());
        txtTotalBadgesEarned.setTypeface(myTypeFace.getRalewayBold());
        txtTotalQueAsked.setTypeface(myTypeFace.getRalewayBold());
        txtTotalFavQuestions.setTypeface(myTypeFace.getRalewayBold());

        txtYourAmbition.setTypeface(myTypeFace.getRalewayBold());
        txtAboutMe.setTypeface(myTypeFace.getRalewayBold());
        txtClickAddAboutMe.setTypeface(myTypeFace.getRalewayRegular());
        txtClickAddAmbitions.setTypeface(myTypeFace.getRalewayRegular());
        txtEdit.setText(R.string.strEdit);
        txtEdit.setOnClickListener(this);
        txtClickAddAboutMe.setOnClickListener(this);
        txtClickAddAmbitions.setOnClickListener(this);
        txtAboutMe.setOnClickListener(this);
        txtYourAmbition.setOnClickListener(this);
        imgProfileEdit.setOnClickListener(this);
        txtYourAmbition.setEnabled(false);
        txtAboutMe.setEnabled(true);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
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
            activityHost.showProgress();
            RequestObject requestObject = new RequestObject();
            requestObject.setUserId("1");
            new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_ABOUT_ME);

        } catch (Exception e) {
            Debug.i(TAG, "callApiGetAboutMe Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiEditAboutMe() {
        try {
            activityHost.showProgress();
            RequestObject requestObject = new RequestObject();
            requestObject.setUserId("1");
            requestObject.setUsername(txtUserName.getText().toString().trim());
            requestObject.setContactNumber(etCno.getText().toString().trim());
            requestObject.setBirthdate(strDob);
            requestObject.setProfileImage("");
            requestObject.setAmbitionInLife(strAmbition);
            requestObject.setAboutMeText(strDetailAboutMe);

//            requestObject.setAmbitionInLife("Businessman");
//            requestObject.setAboutMeText("I am a graduate from NIFT specializing in Apparel Production. I have a holistic experience of the Apparel Industry and has worked for domestic as well as the exports market. In the Indian retail industry I have worked with Lifestyle International Pvt. Ltd. on sourcing, vendor management and product development for private labels. I then moved to Madura Fashion & Lifestyle where I worked as a buyer. Product and Margin management, optimum allocation of merchandise, meeting sales targets along with competition, market and trend analysis were some of her responsibilities. I joined ISB to fast track my career and pursue opportunities in Category & Brand Management.I am President of the Retail Club. I  proud myself.");

            new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.EDIT_ABOUT_ME);

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

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // imgDp.setImageBitmap(bitmap);
//                imageURI = uri;
//                 sourceFile = new File(getPath(imageURI));
//                if (!sourceFile.isFile()) {
//                    Debug.e(TAG, "Source File Does not exist");
//                }
//                else{
//                    Debug.e(TAG, "Source File exist");
//                }
                imgProfilePic.setImageBitmap(bitmap);
                // hostListenerAboutMe.onSelectImage(bitmap);
                // strDpBase64 = Utility.getBase64ForImage(bitmap);
            } catch (IOException e) {
                // strDpBase64 = "";
                Debug.e(TAG, "onActivityResult convert Image Exception : " + e.toString());
            }
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
                case WebConstants.GET_STUDYMATE_REQUEST:
                    // onResponseGetStudymateRequest(object, error);
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
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    Log.e(TAG, "onResponseEditAboutMe success");
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
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
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    Log.e(TAG, "onResponseGetAboutMe success");
                    setUpData(responseObj.getData().get(0));

                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Log.e(TAG, "onResponseGetAboutMe Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetAboutMe api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAboutMe Exception : " + e.toString());
        }
    }

    private void setUpData(Data data) {
        txtUserName.setText(data.getUserName());
        txtSchool.setText(data.getSchoolName());
        txtClass.setText(data.getCourseName());
        etDob.setText(dateFormat(data.getBirthdate()));
        etCno.setText(data.getContactNumber());
        // imgProfilePic.setBackgroundColor(Color.BLACK);
        strDetailAboutMe = data.getAboutMeText();
        strAmbition = data.getAmbitionInLife();
        if (!strDetailAboutMe.equals(" ")) {
            // txtAboutMe.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.edit_profile),null);
            txtClickAddAboutMe.setText(data.getAboutMeText());
            txtClickAddAboutMe.setCompoundDrawables(null, null, null, null);
            txtAboutMe.setEnabled(true);
            Debug.i(TAG, "Details are available!");
        } else {
            Debug.i(TAG, "Details are not available!");
            txtAboutMe.setCompoundDrawables(null, null, null, null);
            txtAboutMe.setEnabled(false);

        }
        if (!strAmbition.equals(" ")) {
            // txtAboutMe.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.edit_profile),null);
            txtClickAddAmbitions.setText(data.getAmbitionInLife());
            txtClickAddAmbitions.setCompoundDrawables(null, null, null, null);
            txtYourAmbition.setEnabled(true);
            Debug.i(TAG, "Details are available!");
        } else {
            Debug.i(TAG, "Details are not available!");
            txtYourAmbition.setCompoundDrawables(null, null, null, null);
            txtYourAmbition.setEnabled(false);
        }
        txtTotalAssignment.setText(data.getTotalAssignment());
        txtTotalAuthorFollowed.setText(data.getTotalAauthorsFollowed());
        txtTotalBadgesEarned.setText(data.getTotalBadgesEarned());
        txtTotalExam.setText(data.getTotalExams());
        txtTotalFavQuestions.setText(data.getTotalFavoriteQuestions());
        txtTotalIsmRank.setText(data.getIsmRank());
        txtTotalIsmScore.setText(data.getIsmScore());
        txtTotalPost.setText(data.getTotalPost());
        txtTotalQueAsked.setText(data.getTotalQuestionAsked());
        txtTotalStudymates.setText(data.getTotalStudymates());
        imageLoader.displayImage(WebConstants.URL_USERS_IMAGE_PATH + data.getProfilePic(), imgProfilePic, ISMStudent.options);
    }


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
           // myPopup(ABOUT_ME);

        } else if (v == txtYourAmbition ) {
            Intent intent=new Intent(getActivity(),EditAboutMeDetailsActivity.class);
            intent.putExtra(USERNAME,txtUserName.getText().toString());
            intent.putExtra(BIRTHDATE, etDob.getText().toString());
            intent.putExtra(CONTACT_NUMBER,etCno.getText().toString());
            intent.putExtra(AMBITION,strAmbition);
            intent.putExtra(ABOUT_ME_DETAILS,strDetailAboutMe);
            intent.putExtra(EDIT_TYPE, YOUR_AMBITION);
            startActivity(intent);
           // myPopup(YOUR_AMBITION);

        } else if (v == txtAboutMe) {
           Intent intent=new Intent(getActivity(),EditAboutMeDetailsActivity.class);
            intent.putExtra(USERNAME,txtUserName.getText().toString());
            intent.putExtra(BIRTHDATE, etDob.getText().toString());
            intent.putExtra(CONTACT_NUMBER,etCno.getText().toString());
            intent.putExtra(AMBITION,strAmbition);
            intent.putExtra(ABOUT_ME_DETAILS,strDetailAboutMe);
            intent.putExtra(EDIT_TYPE,ABOUT_ME);
            startActivity(intent);

        }

    }

    View.OnTouchListener customPopUpTouchListenr = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            Log.d("POPUP", "Touch false");
            return false;
        }

    };

    public PopupWindow myPopup(final int type) {

        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.layout_add_about_yourself, null);

        final PopupWindow popupWindow = new PopupWindow(view,
                400,
                400, true);

        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // popupWindow.setTouchInterceptor(customPopUpTouchListenr);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        // TextView txtDetails = (TextView) view.findViewById(R.id.txt_about_me_details);
        final EditText etAboutMe = (EditText) view
                .findViewById(R.id.et_clickAddAboutMe);
        final TextView txtAboutMe = (TextView) view
                .findViewById(R.id.txt_about_me);
        TextView txtCancel = (TextView) view
                .findViewById(R.id.txt_cancel);
        if (type == ABOUT_ME) {
            txtAboutMe.setText(R.string.strAboutYou);
            etAboutMe.setText(strDetailAboutMe);
            etAboutMe.setHint("Write about your self");
        } else if (type == YOUR_AMBITION) {
            txtAboutMe.setText(R.string.strYourAmbitionInLife);
            etAboutMe.setText(strAmbition);
            etAboutMe.setHint("Write about your ambition in life");
        }

        etAboutMe.setText(strDetailAboutMe);
        txtCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (type == ABOUT_ME) {
                    strDetailAboutMe = etAboutMe.getText().toString().trim();
                } else if (type == YOUR_AMBITION) {
                    strAmbition = etAboutMe.getText().toString().trim();
                }
                popupWindow.dismiss();

            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);
        return popupWindow;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
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


//    @Override
//    public void onSelectImage(Bitmap bitmap) {
//        imgProfilePic.setImageBitmap(bitmap);
//
//    }
}
