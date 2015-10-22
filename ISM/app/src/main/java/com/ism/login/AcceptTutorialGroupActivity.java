package com.ism.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ism.HostActivity;
import com.ism.R;
import com.ism.adapter.TutorialGroupAdapter;
import com.ism.object.MyTypeFace;

/**
 * Created by c162 on 08/10/15.
 */
public class AcceptTutorialGroupActivity extends Activity {
    private static final String TAG = AcceptTutorialGroupActivity.class.getSimpleName();
    private GridView grid;
    private TextView txtSchoolname, txtYearandcourse, txtYourtutorial, txtWelcometoism, txtUsername;

    private Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_accept_tutorial_group);
        intitView();
    }

    private void intitView() {
        MyTypeFace myTypeFace = new MyTypeFace(this);
        txtSchoolname = (TextView) findViewById(R.id.txt_schoolname);
        txtYearandcourse = (TextView) findViewById(R.id.txt_yearandcourse);
        txtYourtutorial = (TextView) findViewById(R.id.txt_yourtutorial_);
        txtWelcometoism = (TextView) findViewById(R.id.txt_welcometoism);
        txtUsername = (TextView) findViewById(R.id.txt_username);
        btnAccept = (Button) findViewById(R.id.btn_accept);
        txtUsername.setTypeface(myTypeFace.getRalewaySemiBold());
        txtWelcometoism.setTypeface(myTypeFace.getRalewaySemiBold());
        txtYourtutorial.setTypeface(myTypeFace.getRalewayBold());
        txtYearandcourse.setTypeface(myTypeFace.getRalewayRegular());
        txtSchoolname.setTypeface(myTypeFace.getRalewayRegular());

        grid = (GridView) findViewById(R.id.gv_groupMember);
        grid.setAdapter(new TutorialGroupAdapter(getApplicationContext()));

	    btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHostActivity = new Intent(AcceptTutorialGroupActivity.this, HostActivity.class);
                startActivity(intentHostActivity);
            }
        });

    }
}
