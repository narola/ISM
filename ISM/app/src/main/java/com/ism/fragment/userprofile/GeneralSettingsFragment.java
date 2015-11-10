package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.interfaces.FragmentListener;
import com.ism.object.MyTypeFace;

/**
 * Created by c161 on 06/11/15.
 */
public class GeneralSettingsFragment extends Fragment {

    private static final String TAG = GeneralSettingsFragment.class.getSimpleName();

    private View view;
    private FrameLayout flFragmentContainerLeft;
    private FrameLayout flFragmentContainer;
    private static final int FRAGMENT_PRIVACY_SETTING = 0;
    private static final int FRAGMENT_SMS_ALERTS = 1;
    private static final int FRAGMENT_BLOCK_USER = 2;
    private static final int FRAGMENT_NOTIFICATION = 3;
    private HostActivity activityHost;
    private FragmentListener fragListener;
    private TextView txtPrivacySetting, txtSmsAlerts, txtBlockUsers, txtNotifications;
    MyTypeFace myTypeFace;
    private int currentFragment=-1;

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
        flFragmentContainerLeft = (FrameLayout) view.findViewById(R.id.fl_fragment_container_left);
        flFragmentContainer = (FrameLayout) view.findViewById(R.id.fl_fragment_container);
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
            }
            break;
            case FRAGMENT_NOTIFICATION: {
                txtNotifications.setActivated(false);
            }
            break;
            case FRAGMENT_PRIVACY_SETTING: {
                txtPrivacySetting.setActivated(false);
            }
            break;
            case FRAGMENT_SMS_ALERTS: {
                txtSmsAlerts.setActivated(false);
            }
            break;

        }
        if (fragment != currentFragment) {
            loadFragment(fragment);
            v.setActivated(true);
        }
    }

    private void loadFragment(int frag) {
        switch (frag) {
            case FRAGMENT_PRIVACY_SETTING: {
                currentFragment = frag;
                PrivacySettingFragment fragment = PrivacySettingFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_SMS_ALERTS: {
                currentFragment = frag;
                SMSAlertsFragment fragment = SMSAlertsFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_BLOCK_USER: {
                currentFragment = frag;
                BlockUserFragment fragment = BlockUserFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_NOTIFICATION: {
                currentFragment = frag;
                NotificationFragment fragment = NotificationFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
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


}