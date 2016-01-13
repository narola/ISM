package com.ism.author.fragment.assessment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.StudentAttemptedAssignmentAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.model.RealmDataModel;
import com.ism.author.object.Global;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.ExamSubmission;

import realmhelper.AuthorHelper;

/**
 * Created by c166 on 19/11/15.
 */
public class StudentAttemptedAssignmentFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = StudentAttemptedAssignmentFragment.class.getSimpleName();
    private View view;
    private AuthorHelper authorHelper;
    private RealmDataModel realmDataModel;

    public static StudentAttemptedAssignmentFragment newInstance() {

        StudentAttemptedAssignmentFragment studentAttemptedAssignmentFragment = new StudentAttemptedAssignmentFragment();
        return studentAttemptedAssignmentFragment;

    }

    public StudentAttemptedAssignmentFragment() {
        // Required empty public constructor
    }


    private TextView tvTitleStudentattempted, tvNoDataMsg;
    private RecyclerView rvStudentattemptedList;
    private StudentAttemptedAssignmentAdapter studentAttemptedAssignmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_attempted_assignment, container, false);
        initGlobal();
        return view;
    }


    private void initGlobal() {

        authorHelper = new AuthorHelper(getActivity());
        realmDataModel = new RealmDataModel();

        tvTitleStudentattempted = (TextView) view.findViewById(R.id.tv_title_studentattempted);
        tvTitleStudentattempted.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);

        rvStudentattemptedList = (RecyclerView) view.findViewById(R.id.rv_studentattempted_list);
        studentAttemptedAssignmentAdapter = new StudentAttemptedAssignmentAdapter(getActivity());
        rvStudentattemptedList.setAdapter(studentAttemptedAssignmentAdapter);
        rvStudentattemptedList.setLayoutManager(new LinearLayoutManager(getActivity()));

        setEmptyView(false);
        callApiGetExamSubmission();

    }


    private void callApiGetExamSubmission() {
        if (Utility.isConnected(getActivity())) {

            try {
                ((AuthorHostActivity) getActivity()).showProgress();

                Attribute request = new Attribute();
                request.setExamId(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                request.setUserId(Global.strUserId);
                request.setRole(Global.role);
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMSUBMISSION);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            setUpData();
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETEXAMSUBMISSION:
                    onResponseGetAllExamSubmission(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }
    }


    private void onResponseGetAllExamSubmission(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getExamSubmission().size() > 0) {
                        setEmptyView(false);
                        addExamSubmission(responseHandler.getExamSubmission().get(0));
                        setUpData();
                    } else {
                        setEmptyView(true);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExamSubmission api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExamSubmission Exception : " + e.toString());
        }
    }


    private void addExamSubmission(ExamSubmission examSubmission) {

        if (examSubmission.getExamsubmittor().size() > 0) {

//            authorHelper.addExamSubmission(realmDataModel.getROExamSubmission(examSubmission, authorHelper));
//            /**
//             * here we update the examsubmission data in exams table.
//             */
//            authorHelper.updateExamSubmissionData(authorHelper.getExamSubmission(Integer.valueOf(examSubmission.getExamId())));



            authorHelper.updateExamSubmissionData(realmDataModel.getROExamSubmission(examSubmission, authorHelper));
        }
    }

    private void setUpData() {

        if (authorHelper.getExamSubmission(Integer.valueOf(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID))) != null) {
            if (authorHelper.getExamSubmission(Integer.valueOf(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID))).getRoExamSubmittors().size() > 0) {
                setEmptyView(false);
                studentAttemptedAssignmentAdapter.addAll(authorHelper.getExamSubmission(Integer.valueOf(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID))).
                        getRoExamSubmittors());
            }
        } else {
            setEmptyView(true);
        }
    }

    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_exam_submittor));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvStudentattemptedList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }
}
