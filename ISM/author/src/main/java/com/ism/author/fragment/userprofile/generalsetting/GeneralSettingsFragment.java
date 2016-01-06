package com.ism.author.fragment.userprofile.generalsetting;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.utility.PreferenceData;
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Preferences;
import com.ism.author.ws.model.UserPreferences;

import java.util.ArrayList;

/**
 * Created by c161 on 06/11/15.
 * updated by  c162
 */
public class GeneralSettingsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, AuthorHostActivity.ProfileControllerPresenceListener {

    private static final String TAG = GeneralSettingsFragment.class.getSimpleName();

    private View view, viewHighlighterTriangle;
    //    private FrameLayout flFragmentContainerLeft;
//    private FrameLayout flFragmentContainer;
    private static final int FRAGMENT_PRIVACY_SETTING = 0;
    private static final int FRAGMENT_SMS_ALERTS = 1;
    private static final int FRAGMENT_BLOCK_USER = 2;
    private static final int FRAGMENT_NOTIFICATION = 3;
    private AuthorHostActivity activityHost;
    private FragmentListener fragListener;
    private TextView txtPrivacySetting, txtSmsAlerts, txtBlockUsers, txtNotifications;
    MyTypeFace myTypeFace;
    private int currentFragment = -1;
    public static ArrayList<Preferences> preferencesList;
    private Attribute attribute;

    public static GeneralSettingsFragment newInstance() {
        GeneralSettingsFragment fragment = new GeneralSettingsFragment();
        return fragment;
    }

    public GeneralSettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_general_settings, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        preferencesList = new ArrayList<>();
        viewHighlighterTriangle = view.findViewById(R.id.view_highlighter_triangle);
        txtPrivacySetting = (TextView) view.findViewById(R.id.txt_privacy_setting);
        txtSmsAlerts = (TextView) view.findViewById(R.id.txt_sms_alerts);
        txtBlockUsers = (TextView) view.findViewById(R.id.txt_block_users);
        txtNotifications = (TextView) view.findViewById(R.id.txt_notifications);

        viewHighlighterTriangle.setVisibility(activityHost.getCurrentRightFragment() == AuthorHostActivity.FRAGMENT_PROFILE_CONTROLLER ? View.VISIBLE : View.GONE);

        myTypeFace = new MyTypeFace(getActivity());
        txtBlockUsers.setTypeface(myTypeFace.getRalewayRegular());
        txtNotifications.setTypeface(myTypeFace.getRalewayRegular());
        txtSmsAlerts.setTypeface(myTypeFace.getRalewayRegular());
        txtPrivacySetting.setTypeface(myTypeFace.getRalewayRegular());
        onClicks();
        setBackGroundLeftController(txtPrivacySetting, FRAGMENT_PRIVACY_SETTING);
//        callApiForGETUSerPreference();
        callApiForGetUserPreference();

    }


    private void onClicks() {
        txtBlockUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackGroundLeftController(v, FRAGMENT_BLOCK_USER);
            }
        });
        txtPrivacySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackGroundLeftController(v, FRAGMENT_PRIVACY_SETTING);
            }
        });
        txtSmsAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackGroundLeftController(v, FRAGMENT_SMS_ALERTS);
            }
        });
        txtNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackGroundLeftController(v, FRAGMENT_NOTIFICATION);
            }
        });
    }

    private void setBackGroundLeftController(View v, int fragment) {

        switch (currentFragment) {
            case FRAGMENT_BLOCK_USER: {
                txtBlockUsers.setActivated(false);
                txtBlockUsers.setEnabled(true);
            }
            break;
            case FRAGMENT_NOTIFICATION: {
                txtNotifications.setActivated(false);
                txtNotifications.setEnabled(true);
            }
            break;
            case FRAGMENT_PRIVACY_SETTING: {
                txtPrivacySetting.setActivated(false);
                txtPrivacySetting.setEnabled(true);
            }
            break;
            case FRAGMENT_SMS_ALERTS: {
                txtSmsAlerts.setActivated(false);
                txtSmsAlerts.setEnabled(true);
            }
            break;

        }
        if (fragment != currentFragment) {
            loadFragment(fragment);
            v.setActivated(true);
            v.setEnabled(false);
        }
    }

    private void loadFragment(int frag) {
        switch (frag) {
            case FRAGMENT_PRIVACY_SETTING: {
                currentFragment = frag;
                // callApiGetGeneralSettingPreferences(preferencesList);
                PrivacySettingFragment fragment = PrivacySettingFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_SMS_ALERTS: {
                currentFragment = frag;
                //callApiGetGeneralSettingPreferences(preferencesList);
                SMSAlertsFragment fragment = SMSAlertsFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_BLOCK_USER: {
                currentFragment = frag;
                BlockUserFragment fragment = BlockUserFragment.newInstance();
                //callApiGetGeneralSettingPreferences(preferencesList);
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_NOTIFICATION: {
                currentFragment = frag;
                // callApiGetGeneralSettingPreferences(preferencesList);
                NotificationFragment fragment = NotificationFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            fragListener = (FragmentListener) activity;
            activityHost.setListenerProfileControllerPresence(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_GENERAL_SETTING);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_GENERAL_SETTING);
                //callApiGetGeneralSettingPreferences();
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void onResponseGetUserPreference(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().toString().equals(WebConstants.SUCCESS)) {
                    if (responseHandler.getUserPreference() != null) {
                        ArrayList<UserPreferences> arrayListUserPreferences = new ArrayList<>();
                        arrayListUserPreferences = responseHandler.getUserPreference();
                        for (int j = 0; j < arrayListUserPreferences.size(); j++) {
                            Debug.i(TAG, "j :" + j);
                            //setPreferenceList(arrayListUserPreferences.get(j).getId(), arrayListUserPreferences.get(j).getPreferenceValue(), getActivity());
                            PreferenceData.setStringPrefs(arrayListUserPreferences.get(j).getId(), getActivity(), arrayListUserPreferences.get(j).getPreferenceValue());
                        }
                    }
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "Failed to load user setting preferences");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetUserPreference api Exceptiion : " + error.toString());
            }
        } catch (Exception e) {
            Debug.i(TAG, "onResponseGetUserPreference :" + e.getLocalizedMessage());
        }
    }

    private void callApiForGetUserPreference() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute requestObject = new Attribute();
                requestObject.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller().execute(WebConstants.GET_USER_PREFERENCES);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "General setting Pereference :" + e.getLocalizedMessage());
        }
    }


    private void onResponseManageGeneralSetting(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().toString().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "Updated successfully");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "Failed to load manage general setting");
                }
            } else {
                Debug.i(TAG, "onResponseManageGeneralSetting : object is null");
            }
        } catch (Exception e) {
            Debug.e(TAG, "General setting Pereference :" + e.getLocalizedMessage());
        }
    }

    public void callApiGetGeneralSettingPreferences() {
        try {
            if (Utility.isConnected(getActivity())) {
                if (preferencesList != null) {
                    activityHost.showProgress();
                    attribute = new Attribute();
                    attribute.setPreferences(preferencesList);
                    new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.MANAGE_GENERAL_SETTINGS);

                } else {
                    Debug.i(TAG, "General setting Pereference list size :" + preferencesList.size());
                }
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.e(TAG, "General setting Pereference :" + e.getLocalizedMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        callApiGetGeneralSettingPreferences();
    }

    //used for store the preferences value of general setting in arraylist
    public void setPreferenceList(String key, String value, Context context) {

        Preferences requestObject = new Preferences();
        requestObject.setUserId(Global.strUserId);
        requestObject.setKeyId(key);
        requestObject.setPreferenceValue(value);
        Debug.i(TAG, "setPreferenceList " + "key:" + key + "value:" + value);
        PreferenceData.setStringPrefs(key, context, value);
        preferencesList.add(requestObject);

//        model.Preferences preferences=new model.Preferences();
//        preferences.setPreferencesId(Integer.parseInt(key));
//        preferences.setDefaultValue(value);
//        Global.authorHelper.updateUserPereferenes(preferences);
    }

    @Override
    public void onProfileControllerAttached() {
        viewHighlighterTriangle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProfileControllerDetached() {
        viewHighlighterTriangle.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.MANAGE_GENERAL_SETTINGS:
                    onResponseManageGeneralSetting(object, error);
                    break;
                case WebConstants.GET_USER_PREFERENCES:
                    onResponseGetUserPreference(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.i(TAG, "Response Exception :" + e.getLocalizedMessage());
        }
    }

//    private String getKeyPereference(String keyPref) {
//        String key = PreferenceData.getStringPrefs(keyPref, getActivity(), "");
//        String key_value = PreferenceData.getStringPrefs(key, getActivity(), "");
//        return key_value;
//    }

}