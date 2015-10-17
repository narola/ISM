package com.ism.teacher.teacher_login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ism.R;
import com.ism.login.Global;
import com.ism.login.ProfileInfornation;


/**
 * Created by c75 on 15/10/15.
 */
public class TeacherLoginActivity extends Activity implements View.OnClickListener {


    Global global;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_teacher_login);
        global = new Global(this);
        initView();

    }

    private void initView() {

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view==btn_login)
        {
            global.myIntent(this, TeacherProfileInformation.class);
        }

    }
}
