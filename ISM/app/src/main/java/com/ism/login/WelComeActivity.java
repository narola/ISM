package com.ism.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;

/**
 * Created by c162 on 08/10/15.
 */
public class WelComeActivity extends Activity {
    private LinearLayout wc_ll_main;
    private TextView wc_txt_selected_, wc_txt_shareideas, wc_txt_and, wc_txt_doassign, wc_txt_collaborate, wc_txt_tutorialgroup_, wc_txt_nameyourtutorial_, wc_txt_andwill_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_welcome);
        intitView();
    }

    private void intitView() {
        MyTypeFace myTypeFace = new MyTypeFace(this);
        wc_txt_selected_ = (TextView) findViewById(R.id.wc_txt_selected_);
        wc_txt_shareideas = (TextView) findViewById(R.id.wc_txt_shareideas);
        wc_txt_and = (TextView) findViewById(R.id.wc_txt_and);
        wc_txt_doassign = (TextView) findViewById(R.id.wc_txt_doassign);
        wc_txt_collaborate = (TextView) findViewById(R.id.wc_txt_collaborate);
        wc_txt_tutorialgroup_ = (TextView) findViewById(R.id.wc_txt_tutorialgroup_);
        wc_txt_nameyourtutorial_ = (TextView) findViewById(R.id.txt_nameyourtutorial_);
        wc_txt_andwill_ = (TextView) findViewById(R.id.txt_andwill_);


        wc_txt_selected_.setTypeface(myTypeFace.getRalewayRegular());
        wc_txt_shareideas.setTypeface(myTypeFace.getRalewayRegular());
        wc_txt_collaborate.setTypeface(myTypeFace.getRalewayRegular());
        wc_txt_and.setTypeface(myTypeFace.getRalewayRegular());
        wc_txt_doassign.setTypeface(myTypeFace.getRalewayRegular());
        wc_txt_tutorialgroup_.setTypeface(myTypeFace.getRalewayRegular());
        wc_txt_nameyourtutorial_.setTypeface(myTypeFace.getRalewaySemiBold());
        wc_txt_andwill_.setTypeface(myTypeFace.getRalewaySemiBold());
        wc_ll_main = (LinearLayout) findViewById(R.id.ll_main);
        wc_ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.myIntent(getApplicationContext(), TutorialGroup.class);
            }
        });
    }

}
