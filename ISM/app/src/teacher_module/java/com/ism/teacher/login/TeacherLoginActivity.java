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

import com.ism.R;
import com.ism.login.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;


/**
 * Created by c75 on 15/10/15.
 */
public class TeacherLoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = TeacherLoginActivity.class.getSimpleName();

    Button btnLogin;
    EditText etTeacherUserid, etTeacherPassword;
    CheckBox chkRememberme;
    TextView txtForgotpwd, txtDonothave, txtClickhere;
    private InputValidator inputValidator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_teacher_login);
        initView();

    }

    private void initView() {
        MyTypeFace myTypeFace = new MyTypeFace(TeacherLoginActivity.this);

        txtForgotpwd = (TextView) findViewById(R.id.txt_forgotpwd);
        txtDonothave = (TextView) findViewById(R.id.txt_donothave_);
        txtClickhere = (TextView) findViewById(R.id.txt_clickhere);

        btnLogin = (Button) findViewById(R.id.btn_login);
        etTeacherUserid = (EditText) findViewById(R.id.et_teacher_userid);
        etTeacherPassword = (EditText) findViewById(R.id.et_teacher_password);
        chkRememberme = (CheckBox) findViewById(R.id.chk_rememberme);
        inputValidator = new InputValidator(TeacherLoginActivity.this);

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
                Global.myIntent(this, com.ism.teacher.login.TeacherProfileInformation.class);
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
}
