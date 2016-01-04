package com.ism.teacher.fragments.userprofile.generalsetting;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.PreferenceData;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.object.Global;


/**
 * Created by c162 on 22/12/15.
 */
public class PrivacySettingFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView txtAlertsPersonalDetails, txtAlertsAcademicDetails, txtAcademicNotification, txtAcademicNotificationBD, txtAlertsExamScore;
    private View includeAcademic, includeAcademicBirthDate;
    private RadioGroup radioGroupAcademic, radioGroupAcademicBD;
    private RadioButton radioButtonYesAcademic, radioButtonNoAcademic, radioButtonYesAcademicBD, radioButtonNoAcademicBD;
    private Spinner spViewers;
    private static String TAG = PrivacySettingFragment.class.getSimpleName();
    private TeacherHostActivity activityHost;
    private String[] strArrayList;
    GeneralSettingsFragment generalSettingsFragment;

    public static PrivacySettingFragment newInstance() {
        PrivacySettingFragment fragment = new PrivacySettingFragment();
        return fragment;
    }

    public PrivacySettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_privacy_setting, container, false);

        initGlobal();

        return view;
    }


    private void initGlobal() {
        generalSettingsFragment = GeneralSettingsFragment.newInstance();

        spViewers = (Spinner) view.findViewById(R.id.sp_viewers);
        txtAlertsPersonalDetails = (TextView) view.findViewById(R.id.txt_alerts_personal_details);
        txtAlertsAcademicDetails = (TextView) view.findViewById(R.id.txt_alerts_academic_details);
        includeAcademic = view.findViewById(R.id.include_contact_info);
        txtAcademicNotification = (TextView) includeAcademic.findViewById(R.id.txt_notification_name);
        radioGroupAcademic = (RadioGroup) includeAcademic.findViewById(R.id.radigroup_yes_no);
        radioButtonYesAcademic = (RadioButton) includeAcademic.findViewById(R.id.radiobutton_yes);
        radioButtonNoAcademic = (RadioButton) includeAcademic.findViewById(R.id.radiobutton_no);
        //txtAcademicNotification.setTextColor(getResources().getColor(R.color.color_blue));
        txtAcademicNotification.setText(R.string.strAllowsViewersToViewMyContactInfo);

        includeAcademicBirthDate = view.findViewById(R.id.include_birthdate_info);
        txtAcademicNotificationBD = (TextView) includeAcademicBirthDate.findViewById(R.id.txt_notification_name);
        radioGroupAcademicBD = (RadioGroup) includeAcademicBirthDate.findViewById(R.id.radigroup_yes_no);
        radioButtonYesAcademicBD = (RadioButton) includeAcademicBirthDate.findViewById(R.id.radiobutton_yes);
        radioButtonNoAcademicBD = (RadioButton) includeAcademicBirthDate.findViewById(R.id.radiobutton_no);
        //txtAcademicNotificationBD.setTextColor(getResources().getColor(R.color.color_blue));
        txtAcademicNotificationBD.setText(R.string.strAllowsViewersToViewMyBirthdate);

        txtAlertsExamScore = (TextView) view.findViewById(R.id.txt_alerts_exam_score);
        txtAlertsExamScore.setTextColor(getResources().getColor(R.color.color_blue));
        txtAlertsExamScore.setText(R.string.strAllowsViewersToViewMyExamScores);
        //set typeface
        txtAlertsAcademicDetails.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAlertsPersonalDetails.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAcademicNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAlertsExamScore.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAcademicNotificationBD.setTypeface(Global.myTypeFace.getRalewayRegular());

        radioButtonYesAcademic.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesAcademicBD.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoAcademic.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoAcademicBD.setTypeface(Global.myTypeFace.getRalewayRegular());

        radioButtonNoAcademicBD.setOnClickListener(this);
        radioButtonNoAcademic.setOnClickListener(this);
        radioButtonYesAcademic.setOnClickListener(this);
        radioButtonYesAcademicBD.setOnClickListener(this);
        spViewers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedId = spViewers.getSelectedItemPosition();
                //Toast.makeText(getActivity(), "Position : " + selectedId, Toast.LENGTH_SHORT).show();
                String[] array = getResources().getStringArray(R.array.viewers_array);
                String key_value = getKeyId(PreferenceData.PS_VIWERS_VIEW_EXAMSCORE);
//                PreferenceData.setStringPrefs(key_value, getActivity(), array[position]);
                generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.PS_VIWERS_VIEW_EXAMSCORE, getActivity()), array[position], getActivity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        strArrayList = getActivity().getResources().getStringArray(R.array.viewers_array);

        Adapters.setUpSpinner(getActivity(), spViewers, getActivity().getResources().getStringArray(R.array.viewers_array)
                , Global.myTypeFace.getRalewayRegular(), R.layout.list_item_simple_light);
        setDefaultValues();

    }

    private void setDefaultValues() {

        String key_value;
        key_value = getKeyPereference(PreferenceData.PS_VIWERS_VIEW_EXAMSCORE);
        // Debug.i(TAG,"Value: "+key+"::"+key_value);
        if (key_value.equals("Studymates")) {
            spViewers.setSelection(2);

        } else {
            spViewers.setSelection(1);
        }
        key_value = getKeyPereference(PreferenceData.PS_VIWERS_VIEW_CONTACT);
        if (key_value.equals("No")) {
            radioGroupAcademic.check(R.id.radiobutton_no);

        } else {
            radioGroupAcademic.check(R.id.radiobutton_yes);
        }
        key_value = getKeyPereference(PreferenceData.PS_VIWERS_VIEW_BIRTHDATE);
        if (key_value.equals("No")) {
            radioGroupAcademicBD.check(R.id.radiobutton_no);

        } else {
            radioGroupAcademicBD.check(R.id.radiobutton_yes);
        }

    }

    public String getKeyPereference(String keyPref) {
        String key = PreferenceData.getStringPrefs(keyPref, getActivity(), "");
        String key_value = PreferenceData.getStringPrefs(key, getActivity(), "");
        return key_value;
    }

    public String getKeyId(String keyPref) {
        String key = PreferenceData.getStringPrefs(keyPref, getActivity(), "");
        return key;
    }


    @Override
    public void onClick(View v) {
        if (v == radioButtonNoAcademic) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.PS_VIWERS_VIEW_CONTACT, getActivity()), "No", getActivity());

        } else if (v == radioButtonYesAcademic) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.PS_VIWERS_VIEW_CONTACT, getActivity()), "Yes", getActivity());
        } else if (v == radioButtonNoAcademicBD) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.PS_VIWERS_VIEW_BIRTHDATE, getActivity()), "No", getActivity());
        } else if (v == radioButtonYesAcademicBD) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.PS_VIWERS_VIEW_BIRTHDATE, getActivity()), "Yes", getActivity());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (TeacherHostActivity) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            // generalSettingsFragment.callApiGetGeneralSettingPreferences();
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }


}
