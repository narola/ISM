package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.adapters.SubjectiveQuestionAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.RequestObject;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.ws.WebserviceWrapper;

import java.util.ArrayList;

/**
 * Created by c75 on 10/11/15.
 */
public class SubjectiveQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = SubjectiveQuestionsFragment.class.getSimpleName();


    //Views
    View rootview;
    RecyclerView rvSubjectiveQuestionList;
    TextView txtExamName;

    //ArrayList
    ArrayList<Data> arrayListSubjectiveQuestions = new ArrayList<>();

    //Adapters
    SubjectiveQuestionAdapter subjectiveQuestionAdapter;

    public static SubjectiveQuestionsFragment newInstance() {
        SubjectiveQuestionsFragment myStudentsFragment = new SubjectiveQuestionsFragment();
        return myStudentsFragment;
    }

    public SubjectiveQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_subjective_questions, container, false);

        initGlobal(rootview);

        return rootview;
    }

    private void initGlobal(View rootview) {

        txtExamName = (TextView) rootview.findViewById(R.id.txt_exam_name);

        rvSubjectiveQuestionList = (RecyclerView) rootview.findViewById(R.id.rv_subjective_question_list);

        callGetSubjectionQuestionApi();
    }

    private void callGetSubjectionQuestionApi() {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setExamId("11");
            new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebConstants.GET_EXAM_QUESTIONS);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    @Override
    public void onResponse(int api_code, Object object, Exception error) {
        try {
            switch (api_code) {
                case WebConstants.GET_EXAM_QUESTIONS:
                    onResponseMySubjectiveQuestions(object);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }


    private void onResponseMySubjectiveQuestions(Object object) {
        ResponseObject responseObj = (ResponseObject) object;
        if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {

            txtExamName.setText(responseObj.getData().get(0).getExamName());
//            arrayListSubjectiveQuestions.addAll(responseObj.getData());
//            subjectiveQuestionAdapter.addAll(arrayListSubjectiveQuestions);
            subjectiveQuestionAdapter = new SubjectiveQuestionAdapter(responseObj,getActivity(), this);
            rvSubjectiveQuestionList.setAdapter(subjectiveQuestionAdapter);
            rvSubjectiveQuestionList.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }
}
