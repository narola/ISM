package com.ism.fragment.userprofile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.adapter.Adapters;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;

/**
 * Created by c162 on 09/11/15.
 */
public class PrivacySettingFragment extends Fragment implements WebserviceWrapper.WebserviceResponse,View.OnClickListener {
    private View view;
    private TextView txtAlertsPersonalDetails,txtAlertsAcademicDetails,txtAcademicNotification,txtAcademicNotificationBD,txtAlertsExamScore;
    MyTypeFace myTypeFace;
    private View includeAcademic,includeAcademicBirthDate;
    private RadioGroup radioGroupAcademic,radioGroupAcademicBD;
    private RadioButton radioButtonYesAcademic,radioButtonNoAcademic,radioButtonYesAcademicBD,radioButtonNoAcademicBD;
    private Spinner spViewers;

    public static PrivacySettingFragment newInstance() {
        PrivacySettingFragment fragment = new PrivacySettingFragment();
        return fragment;
    }

    public PrivacySettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_privacy_setting, container, false);

        initGlobal();

        return view;
    }


    private void initGlobal() {
        myTypeFace=new MyTypeFace(getActivity());
        spViewers=(Spinner)view.findViewById(R.id.sp_viewers);
        txtAlertsPersonalDetails=(TextView)view.findViewById(R.id.txt_alerts_personal_details);
        txtAlertsAcademicDetails=(TextView)view.findViewById(R.id.txt_alerts_academic_details);
        includeAcademic=view.findViewById(R.id.include_contact_info);
        txtAcademicNotification=(TextView)includeAcademic.findViewById(R.id.txt_notification_name);
        radioGroupAcademic=(RadioGroup)includeAcademic.findViewById(R.id.radigroup_yes_no);
        radioButtonYesAcademic=(RadioButton)includeAcademic.findViewById(R.id.radiobutton_yes);
        radioButtonNoAcademic=(RadioButton)includeAcademic.findViewById(R.id.radiobutton_no);
        //txtAcademicNotification.setTextColor(getResources().getColor(R.color.color_blue));
        txtAcademicNotification.setText(R.string.strAllowsViewersToViewMyContactInfo);

        includeAcademicBirthDate=view.findViewById(R.id.include_birthdate_info);
        txtAcademicNotificationBD=(TextView)includeAcademicBirthDate.findViewById(R.id.txt_notification_name);
        radioGroupAcademicBD=(RadioGroup)includeAcademicBirthDate.findViewById(R.id.radigroup_yes_no);
        radioButtonYesAcademicBD=(RadioButton)includeAcademicBirthDate.findViewById(R.id.radiobutton_yes);
        radioButtonNoAcademicBD=(RadioButton)includeAcademicBirthDate.findViewById(R.id.radiobutton_no);
        //txtAcademicNotificationBD.setTextColor(getResources().getColor(R.color.color_blue));
        txtAcademicNotificationBD.setText(R.string.strAllowsViewersToViewMyBirthdate);

        txtAlertsExamScore=(TextView)view.findViewById(R.id.txt_alerts_exam_score);
        txtAlertsExamScore.setTextColor(getResources().getColor(R.color.color_blue));
        txtAlertsExamScore.setText(R.string.strAllowsViewersToViewMyExamScores);
        //set typeface
        txtAlertsAcademicDetails.setTypeface(myTypeFace.getRalewayRegular());
        txtAlertsPersonalDetails.setTypeface(myTypeFace.getRalewayRegular());
        txtAcademicNotification.setTypeface(myTypeFace.getRalewayRegular());
        txtAlertsExamScore.setTypeface(myTypeFace.getRalewayRegular());
        txtAcademicNotificationBD.setTypeface(myTypeFace.getRalewayRegular());

        radioButtonYesAcademic.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesAcademicBD.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoAcademic.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoAcademicBD.setTypeface(myTypeFace.getRalewayRegular());

        radioButtonNoAcademicBD.setOnClickListener(this);
        radioButtonNoAcademic.setOnClickListener(this);
        radioButtonYesAcademic.setOnClickListener(this);
        radioButtonYesAcademicBD.setOnClickListener(this);
        spViewers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedId = spViewers.getSelectedItemPosition();
                Toast.makeText(getActivity(), "Position : "+selectedId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Adapters adapters=new Adapters();
        String strArrayList[]={"Every one","Studymates"};
        Adapters.setUpSpinner(getActivity(), spViewers, getActivity().getResources().getStringArray(R.array.viewers_array)
                , myTypeFace.getRalewayRegular(), R.layout.list_item_simple_light);
//        adapters.setUpSpinner(getActivity(), spBlcok, strArrayList, myTypeFace.getRalewayRegular(), R.layout.simple_spinner);
//        spBlcok.setAdapter(new ArrayAdapter<String>(getActivity()));

    }


    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        ResponseObject responseObject=(ResponseObject)object;
        if (WebConstants.PRIVACY_SETTING==apiCode){
            if(responseObject.getStatus().equals(ResponseObject.SUCCESS)){

            }else if(responseObject.getStatus().equals(ResponseObject.FAILED)){

            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v==radioButtonNoAcademic){
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        }else if(v==radioButtonYesAcademic){
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        }else if (v==radioButtonNoAcademicBD){
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        }else if(v==radioButtonYesAcademicBD){
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        }
//        else if(v==spViewers){
//            int selectedId = spViewers.getSelectedItemPosition();
//                Toast.makeText(getActivity(), "Position : "+selectedId, Toast.LENGTH_SHORT).show();
//        }
    }
//    private void onClicks() {
//        radioButtonYesAcademic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedId = radioGroupAcademic.getCheckedRadioButtonId();
//                // find the radiobutton by returned id
//                if(radioButtonYesAcademic.getId()==selectedId){
//                    Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
//                }else if(radioButtonNoAcademic.getId()==selectedId){
//                    Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//        radioGroupAcademicBD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedId = radioGroupAcademicBD.getCheckedRadioButtonId();
//                // find the radiobutton by returned id
//                if(radioButtonYesAcademicBD.getId()==selectedId){
//                    Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
//                }else if(radioButtonNoAcademicBD.getId()==selectedId){
//                    Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
////        spViewers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
////            }
////        });
//        spViewers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int selectedId = spViewers.getSelectedItemPosition();
//                Toast.makeText(getActivity(), "Position : "+selectedId, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }

}
