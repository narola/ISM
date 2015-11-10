package com.ism.fragment.userprofile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;

/**
 * Created by c162 on 09/11/15.
 */
public class SMSAlertsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {
    private View view;
    private MyTypeFace myTypeFace;
    private View includeEval, includeAssign, includeConference;
    private TextView txtConference, txtAssign, txtEvaluation, txtConfNotifi, txtAssignNotifi, txtEvalNotifi;
    private RadioGroup radioGroupConf, radioGroupAssign, radioGroupEval;
    private RadioButton radioButtonYesConf, radioButtonNoconf, radioButtonYesAssign, radioButtonNoAssign, radioButtonYesEval, radioButtonNoEval;

    public static SMSAlertsFragment newInstance() {
        SMSAlertsFragment fragment = new SMSAlertsFragment();
        return fragment;
    }

    public SMSAlertsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sms_alerts, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());
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
        txtConfNotifi.setTypeface(myTypeFace.getRalewayRegular());
        txtAssign.setTypeface(myTypeFace.getRalewayRegular());
        txtEvalNotifi.setTypeface(myTypeFace.getRalewayRegular());
        txtAssignNotifi.setTypeface(myTypeFace.getRalewayRegular());
        txtEvaluation.setTypeface(myTypeFace.getRalewayRegular());
        txtConference.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoAssign.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoconf.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoEval.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesAssign.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesEval.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesConf.setTypeface(myTypeFace.getRalewayRegular());

        radioButtonNoAssign.setOnClickListener(this);
        radioButtonNoconf.setOnClickListener(this);
        radioButtonNoEval.setOnClickListener(this);
        radioButtonYesAssign.setOnClickListener(this);
        radioButtonYesConf.setOnClickListener(this);
        radioButtonYesEval.setOnClickListener(this);


    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        ResponseObject responseObject = (ResponseObject) object;
        if (WebConstants.SMS_ALERTS == apiCode) {
            if (responseObject.getStatus().equals(ResponseObject.SUCCESS)) {

            } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {

            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v == radioButtonNoAssign) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonYesAssign) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonNoconf) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonYesConf) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonNoEval) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonYesEval) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        }
    }
}
