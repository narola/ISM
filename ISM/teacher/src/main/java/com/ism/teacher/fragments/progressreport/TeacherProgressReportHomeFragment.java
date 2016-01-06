package com.ism.teacher.fragments.progressreport;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherProgressReportHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherProgressReportHomeFragment.class.getSimpleName();

//    //Views
//    private Spinner spExamType;
//    private RecyclerView rvStudentsReport;
//
//    //Adapter
//    private ProgressReportGraphAdapter progressReportGraphAdapter;
//    private MyGradesAdapter myGradesAdapter;
//
//    //ArrayList
//    private List<String> arrListDefault = new ArrayList<>();

    public static TeacherProgressReportHomeFragment newInstance() {
        TeacherProgressReportHomeFragment teacherQuizHomeFragment = new TeacherProgressReportHomeFragment();
        return teacherQuizHomeFragment;
    }

    public TeacherProgressReportHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_report_average, container, false);
        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {
//        rvStudentsReport = (RecyclerView) rootview.findViewById(R.id.rv_students_report);
//        spExamType = (Spinner) rootview.findViewById(R.id.sp_exam_type);
//        arrListDefault.add(Utility.getString(R.string.select, getActivity()));
//        Adapters.setUpSpinner(getActivity(), spExamType, arrListDefault, Adapters.ADAPTER_NORMAL);
//
//        rvStudentsReport.setHasFixedSize(true);
//        rvStudentsReport.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//        progressReportGraphAdapter = new ProgressReportGraphAdapter(getActivity());
//        rvStudentsReport.setAdapter(progressReportGraphAdapter);
//
//        callApiGetReportData();
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

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    private void onResponseGetReportData(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Debug.e(TAG, "onResponseGetReportData success");


                    //arrListmyStudents = responseHandler.getStudents();

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseGetNotification Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetNotification api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetNotification Exception : " + e.toString());
        }
    }
}
