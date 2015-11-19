package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.ws.model.RequestObject;
import com.ism.ws.model.ResponseObject;
import com.ism.ws.WebserviceWrapper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by c162 on 09/11/15.
 */
public class AboutMeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {
    private View view;
    private TextView txtUserName, txtSchool, txtClass;
    MyTypeFace myTypeFace;
    private static String TAG = AboutMeFragment.class.getSimpleName();
    private HostActivity activityHost;
    private String[] strArrayList;
    EditProfileFragment editProfileFragment;
    private TextView txtEdit;
    private EditText etCno, etDob;
    private ImageView imgProfilePic, imgProfileEdit;
    ImageLoader imageLoader;
    private boolean editable = false;

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

        //set typeface
        txtClass.setTypeface(myTypeFace.getRalewayRegular());
        txtSchool.setTypeface(myTypeFace.getRalewayRegular());
        txtUserName.setTypeface(myTypeFace.getRalewayRegular());
        txtEdit.setTypeface(myTypeFace.getRalewayRegular());
        etCno.setTypeface(myTypeFace.getRalewayRegular());
        etDob.setTypeface(myTypeFace.getRalewayRegular());

        txtEdit.setOnClickListener(this);
        imgProfileEdit.setOnClickListener(this);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        setEditableFalse(etDob);
        setEditableFalse(etCno);
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
            RequestObject requestObject = new RequestObject();
            requestObject.setUserId("1");
            new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_ABOUT_ME);

        } catch (Exception e) {
            Debug.i(TAG, "callApiGetAboutMe Exception : " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(Object object, Exception error, int apiCode) {

        try {
            switch (apiCode) {
                case WebConstants.GET_ABOUT_ME:
                    onResponseGetAboutMe(object, error);
                    break;
                case WebConstants.GET_MESSAGES:
                    // onResponseGetMessages(object, error);
                    break;
                case WebConstants.GET_STUDYMATE_REQUEST:
                    // onResponseGetStudymateRequest(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseGetAboutMe(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    Log.e(TAG, "onResponseGetAboutMe success");
                    txtUserName.setText(responseObj.getData().get(0).getUserName());
                    txtSchool.setText(responseObj.getData().get(0).getSchoolName());
                    txtClass.setText(responseObj.getData().get(0).getCourseName());
                    etDob.setText(dateFormat(responseObj.getData().get(0).getBirthdate()));
                    etCno.setText(responseObj.getData().get(0).getContactNumber());
                    imgProfilePic.setBackgroundColor(Color.BLACK);
                    //imageLoader.displayImage(WebConstants.URL_USERS_IMAGE_PATH + responseObj.getData().get(0).getProfilePic(), imgProfilePic, ISMStudent.options);
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

    private String dateFormat(String birthdate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(birthdate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
            //  generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.PS_VIWERS_VIEW_CONTACT, getActivity()), "No",getActivity());

        } else if (v == txtEdit && editable) {
            setEditableFalse(etDob);
            setEditableFalse(etCno);
            editable = false;
            txtEdit.setText("Edit");
            //  generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.PS_VIWERS_VIEW_CONTACT, getActivity()), "No",getActivity());

        } else if (v == imgProfileEdit) {
            // generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.PS_VIWERS_VIEW_CONTACT, getActivity()), "Yes",getActivity());
        }
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

}
