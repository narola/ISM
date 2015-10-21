package com.ism.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
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
    private static final String TAG = WelComeActivity.class.getSimpleName();
    private LinearLayout llMain;
    private TextView txtSelected, txtShareideas, txtAnd, txtDoAssign, txtCollaborate, txtTutorialGroup, txtNameyourTutorial, txtAndwill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_welcome);
        intitView();
    }

    private void intitView() {
        MyTypeFace myTypeFace = new MyTypeFace(this);
        //txtSelected = (TextView) findViewById(R.id.txt_selected_);
       // txtShareideas = (TextView) findViewById(R.id.txt_shareideas);
//        txtAnd = (TextView) findViewById(R.id.txt_and);
//        txtDoAssign = (TextView) findViewById(R.id.txt_doassign);
//        txtCollaborate = (TextView) findViewById(R.id.txt_collaborate);
        txtTutorialGroup = (TextView) findViewById(R.id.txt_tutorialgroup_);
        txtNameyourTutorial = (TextView) findViewById(R.id.txt_nameyourtutorial_);
        //txtAndwill = (TextView) findViewById(R.id.txt_andwill_);


        //txtSelected.setTypeface(myTypeFace.getRalewayRegular());
        //txtShareideas.setTypeface(myTypeFace.getRalewayRegular());
//        txtCollaborate.setTypeface(myTypeFace.getRalewayRegular());
//        txtAnd.setTypeface(myTypeFace.getRalewayRegular());
//        txtDoAssign.setTypeface(myTypeFace.getRalewayRegular());
        txtTutorialGroup.setTypeface(myTypeFace.getRalewayRegular());
        txtNameyourTutorial.setTypeface(myTypeFace.getRalewaySemiBold());
        //txtAndwill.setTypeface(myTypeFace.getRalewaySemiBold());
        String strTutorialGroup=getResources().getString(R.string.strtutorialgroupis5);
        txtTutorialGroup.setText(Html.fromHtml("Tutorial group is 5 members group of randomly <br> selected students, put together to <font color=\"#81D525\">share ideas,</font><br><font color=\"#CC89EA\">collaborate</font> and <font color=\"#F5A438\">do assignments.</font>"));
        llMain = (LinearLayout) findViewById(R.id.ll_main);
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.myIntent(getApplicationContext(), AcceptTutorialGroupActivity.class);
            }
        });
    }

}
