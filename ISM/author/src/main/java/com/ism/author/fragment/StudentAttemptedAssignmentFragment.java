package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.StudentAttemptedAssignmentAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.FragmentArgument;
import com.ism.author.model.RequestObject;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;

import java.util.ArrayList;

/**
 * Created by c166 on 19/11/15.
 */
public class StudentAttemptedAssignmentFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = StudentAttemptedAssignmentFragment.class.getSimpleName();
    private View view;
    private FragmentArgument fragmentArgument;


    public static StudentAttemptedAssignmentFragment newInstance(FragmentArgument fragmentArgument) {
        StudentAttemptedAssignmentFragment studentAttemptedAssignmentFragment = new StudentAttemptedAssignmentFragment();
        studentAttemptedAssignmentFragment.fragmentArgument = fragmentArgument;
        return studentAttemptedAssignmentFragment;
    }

    public StudentAttemptedAssignmentFragment() {
        // Required empty public constructor
    }

    private MyTypeFace myTypeFace;
    private TextView tvTitleStudentattempted;
    private RecyclerView rvStudentattemptedList;
    private StudentAttemptedAssignmentAdapter studentAttemptedAssignmentAdapter;
    private ArrayList<Data> listOfStudents = new ArrayList<Data>();

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
        studentAttemptedAssignmentAdapter = new StudentAttemptedAssignmentAdapter(getActivity(), fragmentArgument);
        rvStudentattemptedList.setAdapter(studentAttemptedAssignmentAdapter);
        rvStudentattemptedList.setLayoutManager(new LinearLayoutManager(getActivity()));

        callApiGetExamSubmission();

    }


    private void callApiGetExamSubmission() {
        if (Utility.isOnline(getActivity())) {

            try {
                ((AuthorHostActivity) getActivity()).startProgress();
                RequestObject request = new RequestObject();
//                request.setExamId(fragmentArgument.getRequestObject().getExamId());
                request.setExamId("9");
                request.setUserId("340");
                request.setRole(String.valueOf(AppConstant.AUTHOR_ROLE_ID));

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
            ((AuthorHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    listOfStudents.addAll(responseObj.getData().get(0).getEvaluations());
                    studentAttemptedAssignmentAdapter.addAll(listOfStudents);
                    studentAttemptedAssignmentAdapter.notifyDataSetChanged();

                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExamSubmission api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExamSubmission Exception : " + e.toString());
        }
    }

}
