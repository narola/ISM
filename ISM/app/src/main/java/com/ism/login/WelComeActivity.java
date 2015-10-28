package com.ism.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.utility.Utility;

/**
 * Created by c162 on 08/10/15.
 */
public class WelComeActivity extends Activity {

    private static final String TAG = WelComeActivity.class.getSimpleName();

    private TextView txtTutorialGroup, txtWelcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        intitGlobal();

    }

    private void intitGlobal() {
	    txtTutorialGroup = (TextView) findViewById(R.id.txt_tutorialgroup_);
	    txtWelcomeMsg = (TextView) findViewById(R.id.txt_welcome_msg);

	    MyTypeFace myTypeFace = new MyTypeFace(this);
	    txtTutorialGroup.setTypeface(myTypeFace.getRalewayRegular());
	    txtWelcomeMsg.setTypeface(myTypeFace.getRalewaySemiBold());

	    txtWelcomeMsg.setText(Global.fullName + ", " + getString(R.string.welcome_msg));
        txtTutorialGroup.setText(Html.fromHtml(getString(R.string.tutorial_group_description)));

        txtWelcomeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            Utility.launchIntent(getApplicationContext(), AcceptTutorialGroupActivity.class);
	            finish();
            }
        });
    }

}
