package com.ism.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;

/**
 * Created by c162 on 07/10/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private TextView txtForgotpwd;
    private TextView txtClickhere;
    private Button btnLogin;
    private EditText etPwd;
    private EditText etUserid;
    private CheckBox chkRememberme;
    private Global global;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView txtDonothave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        global = new Global(this);
        initView();

        //  getIsRemember();

    }

    private void getIsRemember() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Global.userId = sharedPreferences.getString(AppConstant.USERID, null);

        Global.password = sharedPreferences.getString(AppConstant.PASSWORD, null);
        if (Global.userId.length() == 0 && Global.password.length() == 0) {
             etUserid.setText(Global.userId);
             etPwd.setText(Global.password);

        }
    }

    private void initView() {
        MyTypeFace myTypeFace = new MyTypeFace(this);
         txtForgotpwd = (TextView) findViewById(R.id.txt_forgotpwd);
         txtClickhere = (TextView) findViewById(R.id.txt_clickhere);
         txtDonothave = (TextView) findViewById(R.id.txt_donothave_);
         btnLogin = (Button) findViewById(R.id.btn_login);
         etPwd = (EditText) findViewById(R.id.et_pwd);
         etUserid = (EditText) findViewById(R.id.et_userid);
         chkRememberme = (CheckBox) findViewById(R.id.chk_rememberme);
         etUserid.setTypeface(myTypeFace.getRalewayRegular());
         etPwd.setTypeface(myTypeFace.getRalewayRegular());
         txtDonothave.setTypeface(myTypeFace.getRalewayRegular());
         txtClickhere.setTypeface(myTypeFace.getRalewayRegular());
         txtForgotpwd.setTypeface(myTypeFace.getRalewayRegular());

         btnLogin.setOnClickListener(this);
         txtClickhere.setOnClickListener(this);
         txtForgotpwd.setOnClickListener(this);
         chkRememberme.setOnClickListener(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                validatation();

            }
            break;
            case R.id.chk_rememberme: {
                // isRemember();

            }
            break;
        }

    }

    private void validatation() {
//        if ( etUserid.getText().toString().length() == 0) {
//             etUserid.requestFocus();
//             etUserid.setError(Html.fromHtml("<font color='red'>Field should not be blank.</font>"));
//
//        } else if ( etPwd.getText().toString().length() == 0) {
//             etPwd.requestFocus();
//             etPwd.setError(Html.fromHtml("<font color='red'>Field should not be blank.</font>"));
//
//        } else {
        //isRemember();
         etUserid.setText("");
         etPwd.setText("");
        global.myIntent(this, ProfileInformationActivity.class);
        // }
    }

    private void isRemember() {
        if ( chkRememberme.isChecked()) {

            editor = sharedPreferences.edit();
            editor.putString(AppConstant.USERID, etUserid.getText().toString());
            editor.putString(AppConstant.PASSWORD, etPwd.getText().toString());
            editor.commit();
        } else {
            editor = sharedPreferences.edit();
            editor.putString(AppConstant.USERID, "");
            editor.putString(AppConstant.PASSWORD, "");
            editor.commit();
        }

    }
}
