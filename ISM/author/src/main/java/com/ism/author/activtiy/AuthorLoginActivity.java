package com.ism.author.activtiy;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.author.R;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utility;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.LoginRequest;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
/**
 * this is the class for user login.
 */
public class AuthorLoginActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AuthorLoginActivity.class.getSimpleName();
    EditText etPwd, etUserid;
    CheckBox chk_rememberme;


    private InputValidator inputValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_login);

        initGlobal();

    }


    private void initGlobal() {

        MyTypeFace myTypeFace = new MyTypeFace(this);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etUserid = (EditText) findViewById(R.id.et_userid);

        etUserid.setTypeface(myTypeFace.getRalewayRegular());
        etPwd.setTypeface(myTypeFace.getRalewayRegular());
        ((TextView) findViewById(R.id.txt_donothave)).setTypeface(myTypeFace.getRalewayRegular());
        ((TextView) findViewById(R.id.txt_clickhere)).setTypeface(myTypeFace.getRalewayRegular());
        ((TextView) findViewById(R.id.txt_forgotpwd)).setTypeface(myTypeFace.getRalewayRegular());

        chk_rememberme = (CheckBox) findViewById(R.id.chk_rememberme);

        inputValidator = new InputValidator(AuthorLoginActivity.this);

//        setIsRememberMe();


    }


    private void getIsRemember() {

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        AppConstant.userId = sharedPreferences.getString(AppConstant.USERID, null);
//
//        Global.password = sharedPreferences.getString(AppConstant.PASSWORD, null);
//        if (Global.userId.length() == 0 && Global.password.length() == 0) {
//            etUserid.setText(Global.userId);
//            etPwd.setText(Global.password);
//
//        }
    }


    public void onClickLogin(View view) {
//        if (isInputsValid()) {
//            Debug.e(TAG, "inputs valid");
//
//
//            if (chk_rememberme.isChecked()) {
//
//                PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, AuthorLoginActivity.this, true);
//                PreferenceData.setStringPrefs(PreferenceData.USER_FULL_NAME, AuthorLoginActivity.this, etUserid.getText().toString().trim());
////                PreferenceData.setStringPrefs(PreferenceData.USER_PASSWORD, AuthorLoginActivity.this, etPwd.getText().toString().trim());
//
//            } else {
//                PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, AuthorLoginActivity.this, false);
//                PreferenceData.setStringPrefs(PreferenceData.USER_FULL_NAME, AuthorLoginActivity.this, "");
////                PreferenceData.setStringPrefs(PreferenceData.USER_PASSWORD, AuthorLoginActivity.this, "");
//            }
//            authenticateUser();
//
//        }

        Utility.launchIntent(AuthorLoginActivity.this, AuthorProfileInformationActivity.class);
    }

    public void onClickForgotPassword(View view) {

    }

    public void onClickClickHere(View view) {

    }

    public void onClickIsRememberMe(View view) {


    }

    private boolean isInputsValid() {
        return inputValidator.validateStringPresence(etUserid) &
                (inputValidator.validateStringPresence(etPwd) && inputValidator.validatePasswordLength(etPwd));
    }

    private void authenticateUser() {
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername("0YGAJ8793B");
            loginRequest.setPassword("narola21");

            new WebserviceWrapper(AuthorLoginActivity.this, loginRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebserviceWrapper.LOGIN);

        } catch (Exception e) {
            Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }
    }


    private void setIsRememberMe() {

        if (PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME, AuthorLoginActivity.this)) {

            chk_rememberme.setChecked(true);
           // etUserid.setText(PreferenceData.getStringPrefs(PreferenceData.USER_FULL_NAME, AuthorLoginActivity.this));
//            etPwd.setText(PreferenceData.getStringPrefs(PreferenceData.USER_PASSWORD, AuthorLoginActivity.this));

        }

    }


    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {

            ResponseObject responseObj = (ResponseObject) object;

            if (responseObj.getStatus().equals(WebConstants.STATUS_SUCCESS) && responseObj != null) {

                if (apiMethodName == WebserviceWrapper.LOGIN) {

//                    PreferenceData.setBooleanPrefs(PreferenceData.IS_LOGGED_IN, AuthorLoginActivity.this, true);
                    Utility.launchIntent(AuthorLoginActivity.this, AuthorProfileInformationActivity.class);

                    Toast.makeText(AuthorLoginActivity.this, getString(R.string.strloginsuccess),
                            Toast.LENGTH_LONG).show();
                }

            } else {

                Toast.makeText(AuthorLoginActivity.this, responseObj.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

            Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }


    }
}
