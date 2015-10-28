package com.ism.teacher.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.teacher.helper.ConnectionDetector;
import com.ism.teacher.model.LoginRequest;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.ws.WebserviceWrapper;
import com.ism.utility.InputValidator;
import com.ism.utility.Utility;


/**
 * Created by c75 on 15/10/15.
 */
public class TeacherLoginActivity extends Activity implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private static final String TAG = TeacherLoginActivity.class.getSimpleName();

    Button btnLogin;
    EditText etTeacherUserid, etTeacherPassword;
    CheckBox chkRememberme;
    TextView txtForgotpwd, txtDonothave, txtClickhere;
    private InputValidator inputValidator;
    private ConnectionDetector connectionDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_teacher_login);
        initView();

    }

    private void initView() {
        MyTypeFace myTypeFace = new MyTypeFace(TeacherLoginActivity.this);
        inputValidator = new InputValidator(TeacherLoginActivity.this);
        connectionDetector = new ConnectionDetector(TeacherLoginActivity.this);

        txtForgotpwd = (TextView) findViewById(R.id.txt_forgotpwd);
        txtDonothave = (TextView) findViewById(R.id.txt_donothave_);
        txtClickhere = (TextView) findViewById(R.id.txt_clickhere);

        btnLogin = (Button) findViewById(R.id.btn_login);
        etTeacherUserid = (EditText) findViewById(R.id.et_teacher_userid);
        etTeacherPassword = (EditText) findViewById(R.id.et_teacher_password);
        chkRememberme = (CheckBox) findViewById(R.id.chk_rememberme);


        etTeacherUserid.setTypeface(myTypeFace.getRalewayRegular());
        etTeacherPassword.setTypeface(myTypeFace.getRalewayRegular());

        txtForgotpwd.setTypeface(myTypeFace.getRalewayRegular());
        txtDonothave.setTypeface(myTypeFace.getRalewayRegular());
        txtClickhere.setTypeface(myTypeFace.getRalewayRegular());

        btnLogin.setOnClickListener(this);
    }

    private boolean isInputsValid() {
        return inputValidator.validateStringPresence(etTeacherUserid) &
                (inputValidator.validateStringPresence(etTeacherPassword) && inputValidator.validatePasswordLength(etTeacherPassword));
    }

    @Override
    public void onClick(View view) {

        if (view == btnLogin) {

            if (isInputsValid()) {
                Log.e(TAG, "inputs valid");

                if (connectionDetector.isConnectingToInternet()) {
                    authenticateUser();
                } else {
                    Toast.makeText(TeacherLoginActivity.this, "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }


                //Global.launchIntent(this, com.ism.teacher.login.TeacherProfileInformation.class);
            }
//            else if(!inputValidator.validateStringPresence(etTeacherUserid))
//            {
//                inputValidator.setError(etTeacherUserid,"Enter User Name");
//            }
//
//            else if(!inputValidator.validateStringPresence(etTeacherPassword))
//            {
//                inputValidator.setError(etTeacherPassword,"Enter Password");
//            }

        }

    }

    private void authenticateUser() {
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername("0YGAJ8793B");
            loginRequest.setPassword("narola21");

			/*new WebserviceWrapper(LoginActivity.this, loginRequest).new WebserviceCaller()
                    .execute("http://192.168.1.162/ISM/WS_ISM/ISMServices.php?Service=AuthenticateUser");*/

            new WebserviceWrapper(TeacherLoginActivity.this, loginRequest).new WebserviceCaller().execute(WebserviceWrapper.LOGIN);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        ResponseObject responseObj = (ResponseObject) object;
        Log.e(TAG, "onResponse");

        Log.e(TAG, ((ResponseObject) object).getMessage());
        if (responseObj.getStatus().equalsIgnoreCase("success")) {
            Toast.makeText(TeacherLoginActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
            Utility.launchIntent(this, com.ism.teacher.login.TeacherProfileInformation.class);
        } else {
            Toast.makeText(TeacherLoginActivity.this, "Please enter valid username and password", Toast.LENGTH_SHORT).show();
        }

    }
}
