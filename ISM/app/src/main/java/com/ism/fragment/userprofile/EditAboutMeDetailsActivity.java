package com.ism.fragment.userprofile;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ism.R;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;

/**
 * Created by c162 on 18/11/15.
 */
public class EditAboutMeDetailsActivity extends Activity implements View.OnClickListener, WebserviceWrapper.WebserviceResponse {
    public static final String TAG = EditAboutMeDetailsActivity.class.getSimpleName();
    private InputMethodManager inputMethod;
    private EditText etEnterHere;
    private TextView txtCancel;
    private TextView txtEdit;
    private String strBirthdate, strUserName, strCno, strAmbition, strAboutMe;
    private int editType;
    private ActionProcessButton progHost;
    private ProgressGenerator progressGenerator;
MyTypeFace myTypeFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_details);
        initLayout();

    }

    private void initLayout() {
        // txtPost = (TextView) findViewById(R.id.txt_post);
        myTypeFace=new MyTypeFace(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        etEnterHere = (EditText) findViewById(R.id.et_enter_here);
        txtCancel = (TextView) findViewById(R.id.txt_cancel);
        txtEdit = (TextView) findViewById(R.id.txt_edit);
        if (bundle.getInt(AboutMeFragment.EDIT_TYPE) == AboutMeFragment.ABOUT_ME) {
            editType = AboutMeFragment.ABOUT_ME;
            if (bundle.getString(AboutMeFragment.ABOUT_ME_DETAILS) != null)
                etEnterHere.setText(bundle.getString(AboutMeFragment.ABOUT_ME_DETAILS));
        } else if (bundle.getInt(AboutMeFragment.EDIT_TYPE) == AboutMeFragment.YOUR_AMBITION) {
            editType = AboutMeFragment.YOUR_AMBITION;
            if (bundle.getString(AboutMeFragment.AMBITION) != null)
                etEnterHere.setText(bundle.getString(AboutMeFragment.AMBITION));

        }
        progHost = (ActionProcessButton) findViewById(R.id.prog_host);
        progressGenerator = new ProgressGenerator();
        strBirthdate = bundle.getString(AboutMeFragment.BIRTHDATE);
        strUserName = bundle.getString(AboutMeFragment.USERNAME);
        strCno = bundle.getString(AboutMeFragment.CONTACT_NUMBER);
        strAmbition = bundle.getString(AboutMeFragment.AMBITION);
        strAboutMe = bundle.getString(AboutMeFragment.ABOUT_ME_DETAILS);
        txtCancel.setOnClickListener(this);
        txtEdit.setOnClickListener(this);

        etEnterHere.setTypeface(myTypeFace.getRalewayRegular());
        txtCancel.setTypeface(myTypeFace.getRalewayRegular());
        txtEdit.setTypeface(myTypeFace.getRalewayRegular());
        inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        showKeyboard();
        progHost.setVisibility(View.GONE);

    }

    public void showProgress() {
        try {
            progHost.setProgress(1);
            progHost.setVisibility(View.VISIBLE);
            progressGenerator.start(progHost);

        } catch (Exception e) {
            Log.e(TAG, "showProgress Exception : " + e.toString());
        }
    }

    public void hideProgress() {
        try {

            progHost.setProgress(100);
            progHost.setVisibility(View.GONE);

        } catch (Exception e) {
            Log.e(TAG, "hideProgress Exception : " + e.getLocalizedMessage());
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        inputMethod.showSoftInput(etEnterHere, InputMethodManager.SHOW_IMPLICIT);
    }

    private void callApiEditAboutMe() {
        try {
            if(Utility.isConnected(getApplicationContext())) {
                showProgress();
                Attribute requestObject = new Attribute();
                requestObject.setUserId("1");
                requestObject.setUsername(strUserName);
                requestObject.setContactNumber(strCno);
                requestObject.setBirthdate(strBirthdate);
                if (editType == AboutMeFragment.ABOUT_ME) {
                    strAboutMe = etEnterHere.getText().toString();
                } else if (editType == AboutMeFragment.YOUR_AMBITION) {
                    strAmbition = etEnterHere.getText().toString();
                }
                requestObject.setProfileImage("");
                requestObject.setAmbitionInLife(strAmbition);
                requestObject.setAboutMeText(strAboutMe);

//            requestObject.setAmbitionInLife("Businessman");
//            requestObject.setAboutMeText("I am a graduate from NIFT specializing in Apparel Production. I have a holistic experience of the Apparel Industry and has worked for domestic as well as the exports market. In the Indian retail industry I have worked with Lifestyle International Pvt. Ltd. on sourcing, vendor management and product development for private labels. I then moved to Madura Fashion & Lifestyle where I worked as a buyer. Product and Margin management, optimum allocation of merchandise, meeting sales targets along with competition, market and trend analysis were some of her responsibilities. I joined ISB to fast track my career and pursue opportunities in Category & Brand Management.I am President of the Retail Club. I  proud myself.");

                new WebserviceWrapper(getApplicationContext(), requestObject, this).new WebserviceCaller().execute(WebConstants.EDIT_ABOUT_ME);
            }
            else{
                Utility.toastOffline(getApplicationContext());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiEditAboutMe Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == txtCancel) {
            hideKeyboard();
            super.onBackPressed();

        } else if (v == txtEdit) {
            callApiEditAboutMe();

        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    private void onResponseEditAboutMe(Object object, Exception error) {
        try {
            hideProgress();
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseEditAboutMe success");
                    hideKeyboard();
                    super.onBackPressed();
                } else if (responseObj.getStatus().equals(WebConstants.FAILED)) {

                    Log.e(TAG, "onResponseEditAboutMe Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseEditAboutMe api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseEditAboutMe Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {

            switch (apiCode) {

                case WebConstants.EDIT_ABOUT_ME:
                    onResponseEditAboutMe(object, error);
                    break;

            }
        } catch (Exception e) {
            Debug.i(TAG, "OnResponse Exceptions : " + e.getLocalizedMessage());
        }

    }
}
