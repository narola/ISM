package com.ism.teacher.teacher_login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.ism.R;
import com.ism.login.Global;

/**
 * Created by c75 on 15/10/15.
 */
public class TeacherProfileInformation extends Activity implements View.OnClickListener {


    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.layout_teacher_profile_info);
        global = new Global(this);
        //initView();

    }

    @Override
    public void onClick(View view) {

    }
}
