package com.ism.author.fragment.userprofile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.StudentAttemptedAssignmentAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Examsubmittor;

import java.util.ArrayList;

/**
 * Created by c166 on 19/11/15.
 */
public class StudentAttemptedAssignmentFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = StudentAttemptedAssignmentFragment.class.getSimpleName();
    private View view;

    public static StudentAttemptedAssignmentFragment newInstance() {
        StudentAttemptedAssignmentFragment studentAttemptedAssignmentFragment = new StudentAttemptedAssignmentFragment();
        return studentAttemptedAssignmentFragment;
    }

    public StudentAttemptedAssignmentFragment() {
        // Required empty public constructor
    }

    private MyTypeFace myTypeFace;
    private TextView tvTitleStudentattempted;
    private RecyclerView rvStudentattemptedList;
    private StudentAttemptedAssignmentAdapter studentAttemptedAssignmentAdapter;
    private ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<Examsubmittor>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_attempted_assignment, container, false);
        initGlobal();
        return view;
    }


    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        tvTitleStudentattempted = (TextView) view.findViewById(R.id.tv_title_studentattempted);
        rvStudentattemptedList = (RecyclerView) view.findViewById(R.id.rv_studentattempted_list);
        studentAttemptedAssignmentAdapter = new StudentAttemptedAssignmentAdapter(getActivity());
        rvStudentattemptedList.setAdapter(studentAttemptedAssignmentAdapter);
        rvStudentattemptedList.setLayoutManager(new LinearLayoutManager(getActivity()));

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
            Utility.toastOffline(getActivity());
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
                    arrListExamSubmittor.addAll(responseHandler.getExamSubmission().get(0).getExamsubmittor());
                    studentAttemptedAssignmentAdapter.addAll(arrListExamSubmittor);
                    studentAttemptedAssignmentAdapter.notifyDataSetChanged();

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExamSubmission api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExamSubmission Exception : " + e.toString());
        }
    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }
}
