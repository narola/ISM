package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.PreferenceData;
import com.ism.ws.helper.Attribute;
import com.ism.ws.model.ResponseObject;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on 06/11/15.
 */
public class GeneralSettingsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GeneralSettingsFragment.class.getSimpleName();

    private View view;
//    private FrameLayout flFragmentContainerLeft;
//    private FrameLayout flFragmentContainer;
    private static final int FRAGMENT_PRIVACY_SETTING = 0;
    private static final int FRAGMENT_SMS_ALERTS = 1;
    private static final int FRAGMENT_BLOCK_USER = 2;
    private static final int FRAGMENT_NOTIFICATION = 3;
    private HostActivity activityHost;
    private FragmentListener fragListener;
    private TextView txtPrivacySetting, txtSmsAlerts, txtBlockUsers, txtNotifications;
    MyTypeFace myTypeFace;
    private int currentFragment = -1;
    private ArrayList<Data> arrayList;
    public static ArrayList<Attribute> preferencesList;
    private Attribute attributeSetting;

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
        //flFragmentContainerLeft = (FrameLayout) view.findViewById(R.id.fl_fragment_container_left);
        //flFragmentContainer = (FrameLayout) view.findViewById(R.id.fl_fragment_container);
        txtPrivacySetting = (TextView) view.findViewById(R.id.txt_privacy_setting);
        txtSmsAlerts = (TextView) view.findViewById(R.id.txt_sms_alerts);
        txtBlockUsers = (TextView) view.findViewById(R.id.txt_block_users);
        txtNotifications = (TextView) view.findViewById(R.id.txt_notifications);

        myTypeFace = new MyTypeFace(getActivity());
        txtBlockUsers.setTypeface(myTypeFace.getRalewayRegular());
        txtNotifications.setTypeface(myTypeFace.getRalewayRegular());
        txtSmsAlerts.setTypeface(myTypeFace.getRalewayRegular());
        txtPrivacySetting.setTypeface(myTypeFace.getRalewayRegular());
        onClicks();
        setBackGroundLeftController(txtPrivacySetting, FRAGMENT_PRIVACY_SETTING);
//        callApiForGETUSerPreference();

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
                PrivacySettingFragment fragment = PrivacySettingFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_SMS_ALERTS: {
                currentFragment = frag;
                SMSAlertsFragment fragment = SMSAlertsFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_BLOCK_USER: {
                currentFragment = frag;
                BlockUserFragment fragment = BlockUserFragment.newInstance();
                callApiGetGeneralSettingPreferences(preferencesList);
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_NOTIFICATION: {
                currentFragment = frag;
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
            activityHost = (HostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_GENERAL_SETTINGS);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_GENERAL_SETTINGS);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        ResponseObject responseObject = (ResponseObject) object;
        if(activityHost!=null)
        activityHost.hideProgress();
        try {
            if (WebConstants.GENERAL_SETTINGS == apiCode) {

                if (responseObject.getStatus().toString().equals(ResponseObject.SUCCESS)) {
//                for (int i = 0; i < requestObjectSetting.getPreferences().size(); i++) {
//                    PreferenceData.setStringPrefs(requestObjectSetting.getPreferences().get(i).getKeyId(), getActivity(), requestObjectSetting.getPreferences().get(i).getSettingValue());
//                    preferencesList = new ArrayList<>();

                } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {
                }

            }
//            else if (WebConstants.GET_USER_PREFERENCES == apiCode) {
//
//                if (responseObject.getStatus().toString().equals(ResponseObject.SUCCESS)) {
//                    if (responseObject.getData().size() > 0) {
//                        for (int j = 0; j < responseObject.getData().size(); j++) {
//                            setPreferenceList(responseObject.getData().get(j).getId(), responseObject.getData().get(j).getDefaultValue(), getActivity());
//                        }
//                    } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {
//
//                    }
//                }
//            }
        } catch (Exception e) {
            Debug.i(TAG, "Response Exception :" + e.getLocalizedMessage());
        }

    }


    private void callApiGetGeneralSettingPreferences(ArrayList<Attribute> reqObj) {
        try {

            activityHost.showProgress();
            if (reqObj != null) {
                attributeSetting = new Attribute();
                attributeSetting.setPreferences(reqObj);
                Debug.i(TAG, "Onject" + attributeSetting);
                new WebserviceWrapper(getActivity(), attributeSetting, this).new WebserviceCaller().execute(WebConstants.GENERAL_SETTINGS);

            } else {
                Debug.i(TAG, "General setting Pereference list size :" + reqObj.size());
            }

        } catch (Exception e) {

            Debug.i(TAG, "General setting Pereference :" + e.getLocalizedMessage());

        }
    }

    public void setPreferenceList(String key, String value, Context context) {
        Attribute attribute = new Attribute();
        attribute.setUserId("1");
        attribute.setKeyId(key);
        attribute.setSettingValue(value);
        Debug.i(TAG,"setPreferenceList" +"key:"+key+"value:"+value);
        PreferenceData.setStringPrefs(key, context, value);
        preferencesList.add(attribute);
    }

    private String getKeyPereference(String keyPref) {
        String key = PreferenceData.getStringPrefs(keyPref, getActivity(), "");
        String key_value = PreferenceData.getStringPrefs(key, getActivity(), "");
        return key_value;
    }

}