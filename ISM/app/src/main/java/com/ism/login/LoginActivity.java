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

public class LoginActivity extends Activity implements View.OnClickListener {

    TextView login_txt_forgotpwd;
    TextView login_txt_clickhere;
    Button login_btn_login;
    EditText login_et_pwd;
    EditText login_et_userid;
    CheckBox login_chk_rememberme;
    Global global;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView login_txt_donothave_;

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
        AppConstant.userId = sharedPreferences.getString(Global.USERID, null);

        AppConstant.password = sharedPreferences.getString(Global.PASSWORD, null);
        if (AppConstant.userId.length() == 0 && AppConstant.password.length() == 0) {
            login_et_userid.setText(AppConstant.userId);
            login_et_pwd.setText(AppConstant.password);

        }
    }

    private void initView() {
        MyTypeFace myTypeFace = new MyTypeFace(this);
        login_txt_forgotpwd = (TextView) findViewById(R.id.login_txt_forgotpwd);
        login_txt_clickhere = (TextView) findViewById(R.id.login_txt_clickhere);
        login_txt_donothave_ = (TextView) findViewById(R.id.login_txt_donothave_);
        login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_et_pwd = (EditText) findViewById(R.id.login_et_pwd);
        login_et_userid = (EditText) findViewById(R.id.login_et_userid);
        login_chk_rememberme = (CheckBox) findViewById(R.id.login_chk_rememberme);
        login_et_userid.setTypeface(myTypeFace.getRalewayRegular());
        login_et_pwd.setTypeface(myTypeFace.getRalewayRegular());
        login_txt_donothave_.setTypeface(myTypeFace.getRalewayRegular());
        login_txt_clickhere.setTypeface(myTypeFace.getRalewayRegular());
        login_txt_forgotpwd.setTypeface(myTypeFace.getRalewayRegular());

        login_btn_login.setOnClickListener(this);
        login_txt_clickhere.setOnClickListener(this);
        login_txt_forgotpwd.setOnClickListener(this);
        login_chk_rememberme.setOnClickListener(this);
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
            case R.id.login_btn_login: {
                validatation();

            }
            break;
            case R.id.login_chk_rememberme: {
                // isRemember();

            }
            break;
        }

    }

    private void validatation() {
//        if (login_et_userid.getText().toString().length() == 0) {
//            login_et_userid.requestFocus();
//            login_et_userid.setError(Html.fromHtml("<font color='red'>Field should not be blank.</font>"));
//
//        } else if (login_et_pwd.getText().toString().length() == 0) {
//            login_et_pwd.requestFocus();
//            login_et_pwd.setError(Html.fromHtml("<font color='red'>Field should not be blank.</font>"));
//
//        } else {
        //isRemember();
        login_et_userid.setText("");
        login_et_pwd.setText("");
        global.myIntent(this, ProfileInfornation.class);
        // }
    }

    private void isRemember() {
        if (login_chk_rememberme.isChecked()) {

            editor = sharedPreferences.edit();
            editor.putString(Global.USERID, login_et_userid.getText().toString());
            editor.putString(Global.PASSWORD, login_et_pwd.getText().toString());
            editor.commit();
        } else {
            editor = sharedPreferences.edit();
            editor.putString(Global.USERID, "");
            editor.putString(Global.PASSWORD, "");
            editor.commit();
        }

    }
}
