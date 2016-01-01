package com.ism.teacher.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.commonsource.utility.AESHelper;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.PreferenceData;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.broadcastReceiver.NetworkStateListener;
import com.ism.teacher.broadcastReceiver.NetworkStatusReceiver;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.object.MyTypeFace;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.AdminConfig;
import com.ism.teacher.ws.model.Cities;
import com.ism.teacher.ws.model.Countries;
import com.ism.teacher.ws.model.States;
import com.realm.ismrealm.RealmAdaptor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import realmhelper.TeacherHelper;


/**
 * Created by c75 on 15/10/15.
 */
public class TeacherLoginActivity extends Activity implements WebserviceWrapper.WebserviceResponse, NetworkStateListener {

    private static final String TAG = TeacherLoginActivity.class.getSimpleName();


    EditText etTeacherUsername, etTeacherPassword;
    CheckBox chkRememberme;
    TextView txtForgotpwd, txtDonothave, txtClickhere;
    private InputValidator inputValidator;
    private ActionProcessButton btnLogin, progForgotPwd, progRequestCredentials, progCountry, progState, progCity;
    private Button btnForgotPwdSubmit, btnCredentialsSubmit;
    private Spinner spCountry, spState, spCity;
    private LinearLayout llLogin;
    private ImageView img_logo;


    //Array List

    private ArrayList<Countries> arrListCountries;
    private ArrayList<States> arrListStates;
    private ArrayList<Cities> arrListCities;
    private List<String> arrListDefault;

    private AlertDialog dialogCredentials;
    private ProgressGenerator progressGenerator;
    private AlertDialog dialogForgotPassword;
    private String strValidationMsg;


    /**
     * Service Security
     */
    private boolean isRememberMe;
    private boolean isRememberMeFirstLogin;
    private boolean isAdminConfigSet;
    private TeacherHelper teacherHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        teacherHelper = new TeacherHelper(getActivity());

        isAdminConfigSet = false;
        isRememberMe = PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME, getActivity());
        isRememberMeFirstLogin = PreferenceData.getBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, getActivity());

        NetworkStatusReceiver.setNetworkStateListener(this);

        if (Utility.isConnected(this)) {
            initializeData();
        } else {
            Utility.alertOffline(this);
        }

    }

    private void initializeData() {

        if (isRememberMe || isRememberMeFirstLogin) {
            WebConstants.ACCESS_KEY = PreferenceData.getStringPrefs(PreferenceData.ACCESS_KEY, this);
            WebConstants.SECRET_KEY = PreferenceData.getStringPrefs(PreferenceData.SECRET_KEY, this);
            callApiGetAdminConfig();
        } else {
            PreferenceData.clearWholePreference(this);
            PreferenceData.setStringPrefs(PreferenceData.ACCESS_KEY, this, WebConstants.NO_USERNAME);
            WebConstants.ACCESS_KEY = WebConstants.NO_USERNAME;
            WebConstants.SECRET_KEY = null;
            callApiRefreshToken();
        }
    }


    private void resumeApp() {
        if (isRememberMe) {
            launchHostActivity();
        } else if (isRememberMeFirstLogin) {
            launchProfileInfoActivity();
        } else {
            initGlobal();
        }
    }

    private void initGlobal() {
        MyTypeFace myTypeFace = new MyTypeFace(TeacherLoginActivity.this);
        inputValidator = new InputValidator(TeacherLoginActivity.this);
        btnLogin = (ActionProcessButton) findViewById(R.id.btn_login);

        txtForgotpwd = (TextView) findViewById(R.id.txt_forgotpwd);
        txtDonothave = (TextView) findViewById(R.id.txt_donothave_);
        txtClickhere = (TextView) findViewById(R.id.txt_clickhere);
        etTeacherUsername = (EditText) findViewById(R.id.et_teacher_username);
        etTeacherPassword = (EditText) findViewById(R.id.et_teacher_password);
        chkRememberme = (CheckBox) findViewById(R.id.chk_rememberme);

        llLogin = (LinearLayout) findViewById(R.id.ll_login);
        img_logo = (ImageView) findViewById(R.id.img_logo);

        showLoginLayout();

        etTeacherUsername.setTypeface(myTypeFace.getRalewayRegular());
        etTeacherPassword.setTypeface(myTypeFace.getRalewayRegular());
        txtForgotpwd.setTypeface(myTypeFace.getRalewayRegular());
        txtDonothave.setTypeface(myTypeFace.getRalewayRegular());
        txtClickhere.setTypeface(myTypeFace.getRalewayRegular());

        arrListDefault = new ArrayList<String>();
        arrListDefault.add(getString(R.string.select));

        progressGenerator = new ProgressGenerator();
        RealmAdaptor.getInstance(this);

    }

    private void showLoginLayout() {
        llLogin.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
        llLogin.setVisibility(View.VISIBLE);
        img_logo.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
    }

    public void onClickLogin(View view) {
        if (Utility.isConnected(getActivity())) {
            if (isInputsValid()) {
                btnLogin.setProgress(1);
                btnLogin.setEnabled(false);

                String globalPassword = teacherHelper.getGlobalPassword();
                if (globalPassword != null) {
                    WebConstants.ACCESS_KEY = AESHelper.encrypt(globalPassword, etTeacherUsername.getText().toString().trim());
                    PreferenceData.setStringPrefs(PreferenceData.ACCESS_KEY, getActivity(), WebConstants.ACCESS_KEY);
                    callApiRefreshToken();
                }
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    public void onClickForgotPassword(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TeacherLoginActivity.this);

        LayoutInflater inflater = LayoutInflater.from(TeacherLoginActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_forgot_password, null);
        final EditText etEmail = (EditText) dialogView.findViewById(R.id.et_email);
        progForgotPwd = (ActionProcessButton) dialogView.findViewById(R.id.prog_forgot_password);

        builder.setView(dialogView)
                .setTitle(R.string.title_forgot_password)
                .setPositiveButton(R.string.strsubmit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(R.string.strclose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setCancelable(false);
        dialogForgotPassword = builder.create();
        dialogForgotPassword.show();
        btnForgotPwdSubmit = dialogForgotPassword.getButton(DialogInterface.BUTTON_POSITIVE);
        btnForgotPwdSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isInternetConnected(TeacherLoginActivity.this)) {
                    if (inputValidator.validateAllConstraintsEmail(etEmail)) {
                        callApiForgotPassword(etEmail.getText().toString().trim());
                    }
                } else {
                    Utility.toastOffline(TeacherLoginActivity.this);
                }
            }
        });
    }

    public void onClickHere(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.dialog_request_credentials, null);
        final EditText etFirstName = (EditText) dialogView.findViewById(R.id.et_firstname);
        final EditText etLastName = (EditText) dialogView.findViewById(R.id.et_lastname);
        final EditText etEmail = (EditText) dialogView.findViewById(R.id.et_email);
        final EditText etHomeAddress = (EditText) dialogView.findViewById(R.id.et_home_address);
        final EditText etSchoolName = (EditText) dialogView.findViewById(R.id.et_school_name);
        final EditText etContactNo = (EditText) dialogView.findViewById(R.id.et_contactNo);
        spCountry = (Spinner) dialogView.findViewById(R.id.sp_country);
        spState = (Spinner) dialogView.findViewById(R.id.sp_state);
        spCity = (Spinner) dialogView.findViewById(R.id.sp_city);
        progRequestCredentials = (ActionProcessButton) dialogView.findViewById(R.id.prog_request_credentials);
        progCountry = (ActionProcessButton) dialogView.findViewById(R.id.prog_country);
        progState = (ActionProcessButton) dialogView.findViewById(R.id.prog_state);
        progCity = (ActionProcessButton) dialogView.findViewById(R.id.prog_city);

        if (Utility.isConnected(getActivity())) {
            callApiGetCountries();
        } else {
            Utility.alertOffline(getActivity());
        }

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrListCountries != null && position > 0) {
                    if (Utility.isConnected(getActivity())) {
                        callApiGetStates(arrListCountries.get(position - 1).getId());
                    } else {
                        Utility.alertOffline(getActivity());
                    }
                } else {
                    Adapters.setUpSpinner(getActivity(), spState, arrListDefault, Adapters.ADAPTER_NORMAL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrListStates != null && position > 0) {
                    if (Utility.isConnected(getActivity())) {
                        callApiGetCities(Integer.parseInt(arrListStates.get(position - 1).getId()));
                    } else {
                        Utility.alertOffline(getActivity());
                    }
                } else {
                    Adapters.setUpSpinner(getActivity(), spCity, arrListDefault, Adapters.ADAPTER_NORMAL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(dialogView)
                .setTitle("Request for Credentials")
                .setPositiveButton(R.string.strsubmit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                }).setNegativeButton(R.string.strclose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        dialogCredentials = builder.create();
        dialogCredentials.show();
        btnCredentialsSubmit = dialogCredentials.getButton(AlertDialog.BUTTON_POSITIVE);
        btnCredentialsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isConnected(getActivity())) {
                    if (isInputsValid()) {
                        Attribute attribute = new Attribute();
                        attribute.setFirstname(etFirstName.getText().toString().trim());
                        attribute.setLastname(etLastName.getText().toString().trim());
                        attribute.setEmailAddress(etEmail.getText().toString().trim());
                        attribute.setHomeAddress(etHomeAddress.getText().toString().trim());
                        attribute.setSchoolName(etSchoolName.getText().toString().trim());
                        attribute.setContactNumber(etContactNo.getText().toString().trim());
                        attribute.setCountryId(spCountry.getSelectedItemPosition() > 0 ? arrListCountries.get(spCountry.getSelectedItemPosition() - 1).getId() : "0");
                        attribute.setStateId(spState.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListStates.get(spState.getSelectedItemPosition() - 1).getId()) : 0);
                        attribute.setCityId(spCity.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCities.get(spCity.getSelectedItemPosition() - 1).getId()) : 0);

                        callApiRequestCredentials(attribute);
                    }
                } else {
                    Utility.alertOffline(getActivity());
                }
            }

            private boolean isInputsValid() {
                return inputValidator.validateStringPresence(etFirstName) &
                        inputValidator.validateStringPresence(etLastName) &
                        inputValidator.validateAllConstraintsEmail(etEmail) &
                        inputValidator.validateStringPresence(etHomeAddress) &
                        inputValidator.validateStringPresence(etSchoolName) &
                        inputValidator.validateStringPresence(etContactNo) &
                        checkOtherInputs();
            }
        });
    }


    private Context getActivity() {
        return TeacherLoginActivity.this;
    }

    private boolean isInputsValid() {
        return inputValidator.validateStringPresence(etTeacherUsername) &
                (inputValidator.validateStringPresence(etTeacherPassword) && inputValidator.validatePasswordLength(etTeacherPassword));
    }

    private boolean checkOtherInputs() {
        strValidationMsg = "";
        if (isCountrySet() & isStateSet() & isCitySet()) {
            return true;
        } else {
            Utility.alert(getActivity(), null, strValidationMsg);
            return false;
        }
    }

    private boolean isCitySet() {
        if (arrListCities != null && arrListCities.size() == 0 || spCity.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_city);
            return false;
        }
    }

    private boolean isStateSet() {
        if (arrListStates != null && arrListStates.size() == 0 || spState.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_state);
            return false;
        }
    }

    private boolean isCountrySet() {
        if (arrListCountries != null && spCountry.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_country);
            return false;
        }
    }


    private void callApiGetCountries() {
        try {
            progCountry.setVisibility(View.VISIBLE);
            progCountry.setProgress(1);
            progressGenerator.start(progCountry);
            new WebserviceWrapper(getActivity(), null, this).new WebserviceCaller()
                    .execute(WebConstants.GET_COUNTRIES);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiGetStates(String countryId) {
        try {
            progState.setVisibility(View.VISIBLE);
            progState.setProgress(1);
            progressGenerator.start(progState);
            Attribute attribute = new Attribute();
            attribute.setCountryId(countryId);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_STATES);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetStates Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiGetCities(int stateId) {
        try {
            progCity.setVisibility(View.VISIBLE);
            progCity.setProgress(1);
            progressGenerator.start(progCity);
            Attribute attribute = new Attribute();
            attribute.setStateId(stateId);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_CITIES);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetCities Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiRequestCredentials(Attribute attribute) {
        try {
            btnCredentialsSubmit.setEnabled(false);
            progRequestCredentials.setVisibility(View.VISIBLE);
            progRequestCredentials.setProgress(1);
            progressGenerator.start(progRequestCredentials);
            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.REQUEST_CREDENTIALS);
        } catch (Exception e) {
            Log.e(TAG, "callApiRequestCredentials Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiForgotPassword(String email) {
        try {
            btnForgotPwdSubmit.setEnabled(false);
            progForgotPwd.setVisibility(View.VISIBLE);
            progForgotPwd.setProgress(1);
            progressGenerator.start(progForgotPwd);
            Attribute attribute = new Attribute();
            attribute.setEmailId(email);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.FORGOT_PASSWORD);

        } catch (Exception e) {
            Debug.e(TAG, "callApiForgotPassword Exception : " + e.getLocalizedMessage());
        }
    }


    private void callApiAuthenticateUser() {
        try {
            Attribute attribute = new Attribute();
            attribute.setUsername(etTeacherUsername.getText().toString().trim());
            attribute.setPassword(etTeacherPassword.getText().toString().trim());

            new WebserviceWrapper(TeacherLoginActivity.this, attribute, this).new WebserviceCaller().execute(WebConstants.LOGIN);

        } catch (Exception e) {
            Log.e(TAG, "callApiAuthenticateUser Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiGetAdminConfig() {
        try {
            Attribute attribute = new Attribute();
            attribute.setRole("All");
            attribute.setLastSyncDate(PreferenceData.getStringPrefs(PreferenceData.SYNC_DATE_ADMIN_CONFIG, getActivity(), ""));

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_ADMIN_CONFIG);
        } catch (Exception e) {
            Log.e(TAG, "callApiAuthenticateUser Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiRefreshToken() {
        try {
            Attribute attribute = new Attribute(WebConstants.ACCESS_KEY);
            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.REFRESH_TOKEN);
        } catch (Exception e) {
            Log.e(TAG, "callApiRefreshToken Exception : " + e.toString());
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        try {
            switch (apiCode) {
                case WebConstants.GET_ADMIN_CONFIG:
                    onResponseGetAdminConfig(object, error);
                    break;
                case WebConstants.LOGIN:
                    onResponseLogin(object, error);
                    break;
                case WebConstants.FORGOT_PASSWORD:
                    onResponseForgotPassword(object, error);
                    break;
                case WebConstants.GET_COUNTRIES:
                    onResponseCountries(object, error);
                    break;
                case WebConstants.GET_STATES:
                    onResponseStates(object, error);
                    break;
                case WebConstants.GET_CITIES:
                    onResponseCities(object, error);
                    break;
                case WebConstants.REQUEST_CREDENTIALS:
                    onResponseCredentials(object, error);
                    break;
                case WebConstants.REFRESH_TOKEN:
                    onResponseRefreshToken(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAdminConfig(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    ArrayList<AdminConfig> arrListAdminConfig = responseHandler.getAdminConfig();
                    Log.e(TAG, "admin config size : " + arrListAdminConfig.size());

                    if (arrListAdminConfig != null && arrListAdminConfig.size() > 0) {
                        for (AdminConfig config : arrListAdminConfig) {
                            model.AdminConfig adminConfig = new model.AdminConfig();
                            adminConfig.setConfigKey(config.getConfigKey());
                            adminConfig.setConfigValue(config.getConfigValue());
                            teacherHelper.saveAdminConfig(adminConfig);
                        }
                        PreferenceData.setStringPrefs(PreferenceData.SYNC_DATE_ADMIN_CONFIG, getActivity(),
                                Utility.formatDateMySql(Calendar.getInstance().getTime()));
                        isAdminConfigSet = true;
                    }
                    resumeApp();

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Log.e(TAG, "onResponseGetAdminConfig Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetAdminConfig api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAdminConfig Exception : " + e.toString());
        }
    }

    private void onResponseRefreshToken(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    PreferenceData.setStringPrefs(PreferenceData.SECRET_KEY, this, responseHandler.getToken().get(0).getTokenName());
                    WebConstants.SECRET_KEY = responseHandler.getToken().get(0).getTokenName();
                    if (isAdminConfigSet) {
                        callApiAuthenticateUser();
                    } else {
                        callApiGetAdminConfig();
                    }
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Log.e(TAG, "onResponseRefreshToken failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseRefreshToken api Exception :" + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseRefreshToken Exception :" + e.toString());
        }
    }

    private void onResponseCountries(Object object, Exception error) {
        try {
            progCountry.setProgress(100);
            progCountry.setVisibility(View.INVISIBLE);
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListCountries = new ArrayList<Countries>();
                    arrListCountries.addAll(responseHandler.getCountries());
                    List<String> countries = new ArrayList<String>();
                    countries.add(getString(R.string.select));
                    for (Countries country : arrListCountries) {
                        countries.add(country.getCountryName());
                    }
                    Adapters.setUpSpinner(getActivity(), spCountry, countries, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseCountries Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCountries api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCountries Exception : " + e.toString());
        }
    }

    private void onResponseStates(Object object, Exception error) {
        try {
            progState.setProgress(100);
            progState.setVisibility(View.INVISIBLE);
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListStates = new ArrayList<States>();
                    arrListStates.addAll(responseHandler.getStates());
                    List<String> states = new ArrayList<String>();
                    states.add(getString(R.string.select));
                    for (States state : arrListStates) {
                        states.add(state.getStateName());
                    }
                    Adapters.setUpSpinner(getActivity(), spState, states, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseStates Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseStates api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseStates Exception : " + e.toString());
        }
    }

    private void onResponseCities(Object object, Exception error) {
        try {
            progCity.setProgress(100);
            progCity.setVisibility(View.INVISIBLE);
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListCities = new ArrayList<Cities>();
                    arrListCities.addAll(responseHandler.getCities());
                    List<String> cities = new ArrayList<String>();
                    cities.add(getString(R.string.select));
                    for (Cities city : arrListCities) {
                        cities.add(city.getCityName());
                    }
                    Adapters.setUpSpinner(getActivity(), spCity, cities, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseCities Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCities api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCities Exception : " + e.toString());
        }
    }

    private void onResponseCredentials(Object object, Exception error) {
        try {
            if (btnCredentialsSubmit != null) {
                btnCredentialsSubmit.setEnabled(true);
            }
            if (progRequestCredentials != null) {
                progRequestCredentials.setProgress(100);
                progRequestCredentials.setVisibility(View.INVISIBLE);
            }
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    if (dialogCredentials != null) {
                        dialogCredentials.dismiss();
                    }
                    Utility.showToast(getActivity().getString(R.string.msg_success_sent_credential), getActivity());
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCredentials api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCredentials Exception : " + e.toString());
        }
    }


    private void onResponseForgotPassword(Object object, Exception error) {
        try {
            if (btnForgotPwdSubmit != null) {
                btnForgotPwdSubmit.setEnabled(true);
            }
            if (progForgotPwd != null) {
                progForgotPwd.setProgress(100);
                progForgotPwd.setVisibility(View.INVISIBLE);
            }
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Utility.showToast(getActivity().getString(R.string.password_sent), getActivity());
                    dialogForgotPassword.dismiss();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(getActivity().getString(R.string.email_not_found), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseForgotPassword api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseForgotPassword Exception : " + e.toString());
        }
    }

    private void onResponseLogin(Object object, Exception error) {

        try {
            btnLogin.setProgress(100);
            btnLogin.setEnabled(true);
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getUser().get(0).getUserId() == null) {

                        PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME_FIRST_LOGIN, getActivity(), ((CheckBox) findViewById(R.id.chk_rememberme)).isChecked());
                        PreferenceData.setStringPrefs(PreferenceData.USER_CREDENTIAL_ID, getActivity(), responseHandler.getUser().get(0).getCredentialId());
                        Debug.e(TAG + "credential id", "" + responseHandler.getUser().get(0).getCredentialId());
                        PreferenceData.setStringPrefs(PreferenceData.USER_PASSWORD, getActivity(), etTeacherPassword.getText().toString().trim());
                        PreferenceData.setStringPrefs(PreferenceData.USER_ROLE_ID, getActivity(), responseHandler.getUser().get(0).getRoleId());

                        /**
                         * New Parameters need to be saved in preferences
                         */
                        PreferenceData.setStringPrefs(PreferenceData.USER_ACADEMIC_YEAR, getActivity(), responseHandler.getUser().get(0).getAcademicYear());
                        PreferenceData.setIntPrefs(PreferenceData.USER_COURSE_ID, getActivity(), Integer.parseInt(responseHandler.getUser().get(0).getCourseId()));
                        PreferenceData.setIntPrefs(PreferenceData.USER_SCHOOL_ID, getActivity(), Integer.parseInt(responseHandler.getUser().get(0).getSchoolId()));

                        PreferenceData.setStringPrefs(PreferenceData.USER_COURSE_NAME, getActivity(), responseHandler.getUser().get(0).getCourseName());
                        PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_NAME, getActivity(), responseHandler.getUser().get(0).getSchoolName());
//                        PreferenceData.setStringPrefs(PreferenceData.USER_SCHOOL_DISTRICT, getActivity(), responseHandler.getUser().get(0).getDistrictName());

                        launchProfileInfoActivity();

                    } else {

                        PreferenceData.setBooleanPrefs(PreferenceData.IS_REMEMBER_ME, getActivity(), ((CheckBox) findViewById(R.id.chk_rememberme)).isChecked());
                        PreferenceData.setStringPrefs(PreferenceData.USER_ID, getActivity(), responseHandler.getUser().get(0).getUserId());
                        PreferenceData.setStringPrefs(PreferenceData.USER_FULL_NAME, getActivity(), responseHandler.getUser().get(0).getFullName());
                        PreferenceData.setStringPrefs(PreferenceData.USER_PROFILE_PIC, getActivity(), responseHandler.getUser().get(0).getProfilePic());
                        PreferenceData.setStringPrefs(PreferenceData.USER_NAME, this, etTeacherUsername.getText().toString().trim());

                        launchHostActivity();
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(getString(R.string.msg_wrong_username_password), getActivity());
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseLogin api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseLogin Exception : " + e.toString());
        }

    }

    private void launchProfileInfoActivity() {
        Utility.launchIntent(TeacherLoginActivity.this, TeacherProfileInformation.class);
        finish();
    }

    private void launchHostActivity() {
        Utility.launchIntent(TeacherLoginActivity.this, TeacherHostActivity.class);
        finish();
    }


    @Override
    public void onNetworkStateChange() {
        if (Utility.isConnected(this)) {
            initializeData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        teacherHelper.destroy();
    }
}
