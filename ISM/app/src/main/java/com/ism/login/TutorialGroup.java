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
public class TutorialGroup extends Activity {
    private GridView grid;
    private TextView tg_txt_schoolname,tg_txt_yearandcourse,tg_txt_yourtutorial_,tg_txt_welcometoism,tg_txt_username;
    private Button tg_btn_accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_tutorial_group);
        intitView();
    }

    private void intitView() {
        MyTypeFace myTypeFace = new MyTypeFace(this);
        tg_txt_schoolname = (TextView) findViewById(R.id.txt_schoolname);
        tg_txt_yearandcourse = (TextView) findViewById(R.id.txt_yearandcourse);
        tg_txt_yourtutorial_ = (TextView) findViewById(R.id.txt_yourtutorial_);
        tg_txt_welcometoism = (TextView) findViewById(R.id.txt_welcometoism);
        tg_txt_username = (TextView) findViewById(R.id.txt_username);
        tg_btn_accept = (Button) findViewById(R.id.btn_accept);
        tg_txt_username.setTypeface(myTypeFace.getRalewaySemiBold());
        tg_txt_welcometoism.setTypeface(myTypeFace.getRalewaySemiBold());
        tg_txt_yourtutorial_.setTypeface(myTypeFace.getRalewayBold());
        tg_txt_yearandcourse.setTypeface(myTypeFace.getRalewayRegular());
        tg_txt_schoolname.setTypeface(myTypeFace.getRalewayRegular());

        grid = (GridView) findViewById(R.id.gv_groupMember);
        grid.setAdapter(new TutorialGroupAdapter(getApplicationContext()));

	    tg_btn_accept.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Intent intentHostActivity = new Intent(TutorialGroup.this, HostActivity.class);
			    startActivity(intentHostActivity);
		    }
	    });

    }
}
