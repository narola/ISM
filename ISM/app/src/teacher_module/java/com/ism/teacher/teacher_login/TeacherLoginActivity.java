package com.ism.teacher.teacher_login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ism.R;
import com.ism.login.Global;


/**
 * Created by c75 on 15/10/15.
 */
public class TeacherLoginActivity extends Activity implements View.OnClickListener {


    Global global;
    Button btnLogin;
    EditText etTeacherUserid, etTeacherPassword;
    CheckBox chkRememberme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_teacher_login);
        global = new Global(this);
        initView();

    }

    private void initView() {

        btnLogin =(Button)findViewById(R.id.btn_login);
        etTeacherUserid =(EditText)findViewById(R.id.et_teacher_userid);
        etTeacherPassword =(EditText)findViewById(R.id.et_teacher_password);
        chkRememberme =(CheckBox)findViewById(R.id.chk_rememberme);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view== btnLogin)
        {
            global.myIntent(this, com.ism.teacher.teacher_login.TeacherProfileInformation.class);
        }

    }
}
