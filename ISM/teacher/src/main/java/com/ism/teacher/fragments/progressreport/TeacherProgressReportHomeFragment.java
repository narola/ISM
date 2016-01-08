package com.ism.teacher.fragments.progressreport;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.ClassPerformance;

import java.util.ArrayList;
import java.util.List;

import realmhelper.TeacherHelper;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherProgressReportHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private static final String TAG = TeacherProgressReportHomeFragment.class.getSimpleName();

    //Views
    //Top layout views
    private Spinner spExamType;
    private TextView tvDateRange;
    private EditText etStartdate, etEnddate;
    private ImageView imgCalendarFrom, imgCalendarTo, imgGraphReport, imgListReport;
    private LinearLayout llReportFrom, llReportTo;
    //ArrayList
    private List<String> arrListDefault = new ArrayList<>();

    private TeacherHelper teacherHelper;


    public static TeacherProgressReportHomeFragment newInstance() {
        TeacherProgressReportHomeFragment teacherQuizHomeFragment = new TeacherProgressReportHomeFragment();
        return teacherQuizHomeFragment;
    }

    public TeacherProgressReportHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_report_average, container, false);
        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {

        teacherHelper = new TeacherHelper(getActivity());

        //initialize top views
        spExamType = (Spinner) rootview.findViewById(R.id.sp_exam_type);
        arrListDefault.add(Utility.getString(R.string.select, getActivity()));
        Adapters.setUpSpinner(getActivity(), spExamType, arrListDefault, Adapters.ADAPTER_NORMAL);
        tvDateRange = (TextView) rootview.findViewById(R.id.tv_date_range);
        etStartdate = (EditText) rootview.findViewById(R.id.et_startdate);
        etEnddate = (EditText) rootview.findViewById(R.id.et_enddate);
        imgCalendarFrom = (ImageView) rootview.findViewById(R.id.img_calendar_from);
        imgCalendarTo = (ImageView) rootview.findViewById(R.id.img_calendar_to);
        imgGraphReport = (ImageView) rootview.findViewById(R.id.img_graph_report);
        imgListReport = (ImageView) rootview.findViewById(R.id.img_list_report);
        llReportFrom = (LinearLayout) rootview.findViewById(R.id.ll_report_from);
        llReportTo = (LinearLayout) rootview.findViewById(R.id.ll_report_to);

        llReportFrom.setOnClickListener(this);
        llReportTo.setOnClickListener(this);
        imgGraphReport.setOnClickListener(this);
        imgListReport.setOnClickListener(this);

        applyFonts();
        callApiGetReportData();

    }

    private void applyFonts() {
        tvDateRange.setTypeface(Global.myTypeFace.getRalewayRegular());
        etStartdate.setTypeface(Global.myTypeFace.getRalewayRegular());
        etEnddate.setTypeface(Global.myTypeFace.getRalewayRegular());
    }

    private void callApiGetReportData() {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.USER_ID_580);
                attribute.setRoleId(WebConstants.TEACHER_ROLE_ID);
                attribute.setLastSyncDate("");

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_REPORT_DATA);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GET_REPORT_DATA:
                    onResponseGetReportData(object, error);
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG, e.getLocalizedMessage());

        }
    }

    private void onResponseGetReportData(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Debug.e(TAG, "onResponseGetReportData success");

                    if (responseHandler.getClassPerformance().size() > 0) {
//                        addClassPerformanceToTable(responseHandler.getClassPerformance());
                        addClassPerformanceToTable(responseHandler.getClassPerformance().get(0));
                    }

//                    setUpData();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseGetReportData Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetReportData api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetReportData Exception : " + e.toString());
        }
    }

    private void addClassPerformanceToTable(ClassPerformance classPerformance) {
        teacherHelper.addClassPerformance(Global.getRealmDataModel.getRealmClassPerformance(classPerformance));
    }

    private void addClassPerformanceToTable(ArrayList<ClassPerformance> arrListClassPerformance) {

        for (ClassPerformance classPerformance : arrListClassPerformance) {
            teacherHelper.addClassPerformance(Global.getRealmDataModel.getRealmClassPerformance(classPerformance));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_report_from:
                break;
            case R.id.ll_report_to:
                break;
            case R.id.img_graph_report:
                break;
            case R.id.img_list_report:
                break;
        }
    }
}
