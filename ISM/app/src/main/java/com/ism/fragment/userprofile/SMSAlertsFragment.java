package com.ism.fragment.userProfile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.PreferenceData;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;

/**
 * Created by c162 on 09/11/15.
 */
public class SMSAlertsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {
    private View view;
    private View includeEval, includeAssign, includeConference;
    private TextView txtConference, txtAssign, txtEvaluation, txtConfNotifi, txtAssignNotifi, txtEvalNotifi;
    private RadioGroup radioGroupConf, radioGroupAssign, radioGroupEval;
    private RadioButton radioButtonYesConf, radioButtonNoconf, radioButtonYesAssign, radioButtonNoAssign, radioButtonYesEval, radioButtonNoEval;
    private static String TAG = SMSAlertsFragment.class.getSimpleName();
    private HostActivity activityHost;
    private com.ism.fragment.userProfile.GeneralSettingsFragment generalSettingsFragment;

    public static SMSAlertsFragment newInstance() {
        SMSAlertsFragment fragment = new SMSAlertsFragment();
        return fragment;
    }

    public SMSAlertsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sms_alerts, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        generalSettingsFragment = com.ism.fragment.userProfile.GeneralSettingsFragment.newInstance();
        txtAssign = (TextView) view.findViewById(R.id.txt_alerts_assignment);
        txtEvaluation = (TextView) view.findViewById(R.id.txt_alerts_evaluations);
        txtConference = (TextView) view.findViewById(R.id.txt_alerts_conference);
        includeConference = view.findViewById(R.id.include_conference);
        txtConfNotifi = (TextView) includeConference.findViewById(R.id.txt_notification_name);
        radioGroupConf = (RadioGroup) includeConference.findViewById(R.id.radigroup_yes_no);
        radioButtonYesConf = (RadioButton) includeConference.findViewById(R.id.radiobutton_yes);
        radioButtonNoconf = (RadioButton) includeConference.findViewById(R.id.radiobutton_no);
        // txtConfNotifi.setTextColor(getResources().getColor(R.color.color_blue));
        txtConfNotifi.setText(R.string.strSmsAlertForConf);

        includeAssign = view.findViewById(R.id.include_assignments);
        txtAssignNotifi = (TextView) includeAssign.findViewById(R.id.txt_notification_name);
        radioGroupAssign = (RadioGroup) includeAssign.findViewById(R.id.radigroup_yes_no);
        radioButtonYesAssign = (RadioButton) includeAssign.findViewById(R.id.radiobutton_yes);
        radioButtonNoAssign = (RadioButton) includeAssign.findViewById(R.id.radiobutton_no);
        //txtAssignNotifi.setTextColor(getResources().getColor(R.color.color_blue));
        txtAssignNotifi.setText(R.string.strSmsAlertsForAssignment);

        includeEval = view.findViewById(R.id.include_evaluations);
        txtEvalNotifi = (TextView) includeEval.findViewById(R.id.txt_notification_name);
        radioGroupEval = (RadioGroup) includeEval.findViewById(R.id.radigroup_yes_no);
        radioButtonYesEval = (RadioButton) includeEval.findViewById(R.id.radiobutton_yes);
        radioButtonNoEval = (RadioButton) includeEval.findViewById(R.id.radiobutton_no);
        //txtEvalNotifi.setTextColor(getResources().getColor(R.color.color_blue));
        txtEvalNotifi.setText(R.string.strSmsAlertForExamEvaluations);

        //set typeface
        txtConfNotifi.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAssign.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtEvalNotifi.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAssignNotifi.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEvaluation.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtConference.setTypeface(Global.myTypeFace.getRalewayMedium());
        radioButtonNoAssign.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoconf.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoEval.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesAssign.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesEval.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesConf.setTypeface(Global.myTypeFace.getRalewayRegular());

        radioButtonNoAssign.setOnClickListener(this);
        radioButtonNoconf.setOnClickListener(this);
        radioButtonNoEval.setOnClickListener(this);
        radioButtonYesAssign.setOnClickListener(this);
        radioButtonYesConf.setOnClickListener(this);
        radioButtonYesEval.setOnClickListener(this);
        setDefaultValues();

    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (WebConstants.MANAGE_GENERAL_SETTINGS == apiCode) {
            if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

            } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {

            }
        }
    }

    public String getKeyID(String keyPref) {
        String key = PreferenceData.getStringPrefs(keyPref, getActivity(), "");
        return key;
    }

    @Override
    public void onClick(View v) {
        String key_value, strPref = null, value = null;
        if (v == radioButtonNoAssign) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.SA_ALERT_NEW_ASSIGNMENT, getActivity()), getActivity().getResources().getString(R.string.strNo), getActivity());
        } else if (v == radioButtonYesAssign) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.SA_ALERT_NEW_ASSIGNMENT, getActivity()), getActivity().getResources().getString(R.string.strYes), getActivity());
        } else if (v == radioButtonNoconf) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.SA_ALERT_CONFERENCE_SCHECDULE, getActivity()),getActivity().getResources().getString(R.string.strNo), getActivity());
        } else if (v == radioButtonYesConf) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.SA_ALERT_CONFERENCE_SCHECDULE, getActivity()), getActivity().getResources().getString(R.string.strYes), getActivity());
        } else if (v == radioButtonNoEval) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.SA_ALERT_EVALUATION_READY, getActivity()),getActivity().getResources().getString(R.string.strNo), getActivity());
        } else if (v == radioButtonYesEval) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.SA_ALERT_EVALUATION_READY, getActivity()), getActivity().getResources().getString(R.string.strYes), getActivity());
        }
    }

    public String getKeyPereference(String keyPref) {
        String key = PreferenceData.getStringPrefs(keyPref, getActivity(), "");
        String key_value = PreferenceData.getStringPrefs(key, getActivity(), "");
        return key_value;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
          //  generalSettingsFragment.callApiGetGeneralSettingPreferences();
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    private void setDefaultValues() {
        String key_value;
        key_value = getKeyPereference(PreferenceData.SA_ALERT_EVALUATION_READY);
        // Log.e(TAG,"Value: "+key+"::"+key_value);
        if (key_value.equals(getActivity().getResources().getString(R.string.strNo))) {
            radioGroupEval.check(R.id.radiobutton_no);

        } else {
            radioGroupEval.check(R.id.radiobutton_yes);
        }
        key_value = getKeyPereference(PreferenceData.SA_ALERT_CONFERENCE_SCHECDULE);
        if (key_value.equals(getActivity().getResources().getString(R.string.strNo))) {
            radioGroupConf.check(R.id.radiobutton_no);

        } else {
            radioGroupConf.check(R.id.radiobutton_yes);
        }
        key_value = getKeyPereference(PreferenceData.SA_ALERT_NEW_ASSIGNMENT);
        if (key_value.equals(getActivity().getResources().getString(R.string.strNo))) {
            radioGroupAssign.check(R.id.radiobutton_no);

        } else {
            radioGroupAssign.check(R.id.radiobutton_yes);
        }

    }

}
