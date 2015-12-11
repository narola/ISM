package com.ism.teacher.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.adapters.PreviewQuestionListAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c166 on 31/10/15.
 */
public class PreviewQuestionFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = PreviewQuestionFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public PreviewQuestionFragment() {
    }

    @SuppressLint("ValidFragment")
    public PreviewQuestionFragment(Fragment fragment, Bundle bundleArguments) {
        this.mFragment = fragment;
        this.setArguments(bundleArguments);
    }


    public TextView tvPreviewQuestionlistTitle, tvPreviewQuestionlistFreeze, tvNoQuestions;
    public RecyclerView rvPreviewquestionlist;
    private PreviewQuestionListAdapter previewQuestionListAdapter;
    public ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    MyTypeFace myTypeFace;

    //this is for the movable recyclerview.

    public TextView tv_total_questions, tv_total_score;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preview_question, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());

        tv_total_questions = (TextView) view.findViewById(R.id.tv_total_questions);
        tv_total_score = (TextView) view.findViewById(R.id.tv_total_score);
        tv_total_questions.setTypeface(myTypeFace.getRalewayRegular());
        tv_total_score.setTypeface(myTypeFace.getRalewayRegular());

        tvNoQuestions = (TextView) view.findViewById(R.id.tv_no_questions);
        tvPreviewQuestionlistTitle = (TextView) view.findViewById(R.id.tv_questionlist_title);
        tvPreviewQuestionlistFreeze = (TextView) view.findViewById(R.id.tv_freeze_question);

        tvPreviewQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvNoQuestions.setTypeface(myTypeFace.getRalewayBold());

        rvPreviewquestionlist = (RecyclerView) view.findViewById(R.id.rv_previewquestionlist);
        previewQuestionListAdapter = new PreviewQuestionListAdapter(getActivity(), mFragment);
        rvPreviewquestionlist.setAdapter(previewQuestionListAdapter);
        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        tvPreviewQuestionlistFreeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callApiFreezeQuestions();
            }
        });

    }

    private void callApiFreezeQuestions() {
        if (Utility.isConnected(getActivity())) {
            if (arrListQuestions.size() > 0) {
                try {
                    Attribute attribute = new Attribute();
                    attribute.setExamId(getArguments().getString(AssignmentsAdapter.ARG_EXAM_ID));
                    attribute.setQuestionIdList(getArrListQuestionId());

                    new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                            .execute(WebConstants.SET_QUESTIONS_FOR_EXAM);
                } catch (Exception e) {
                    Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
                }

            } else {
                Utility.showToast(getString(R.string.strnopreviewquestions), getActivity());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    public void setExamQuestions(ArrayList<Questions> arrListExamQuestions) {
        arrListQuestions.addAll(arrListExamQuestions);
        previewQuestionListAdapter.addAll(this.arrListQuestions);
        tv_total_questions.setText(getString(R.string.strtotalquestions)+ arrListQuestions.size());

    }

    private ArrayList<String> arrListQuestionId = new ArrayList<String>();

    private ArrayList<String> getArrListQuestionId() {

        for (int i = 0; i < arrListQuestions.size(); i++) {
            arrListQuestionId.add(arrListQuestions.get(i).getQuestionId());
        }

        return arrListQuestionId;

    }


    public void addQuestionsToPreviewFragment(ArrayList<Questions> arrListQuestionsToAdd) {
        Debug.e(TAG, "inside addQuetopreview data");
        if (arrListQuestionsToAdd.size() > 0) {

            for (int i = 0; i < arrListQuestionsToAdd.size(); i++) {
                arrListQuestions.add(arrListQuestionsToAdd.get(i));
            }
            Debug.e(TAG, "THE SIZE OF PREVIEW QUESTION LIST IS" + arrListQuestions.size());
            previewQuestionListAdapter.addAll(this.arrListQuestions);
            tv_total_questions.setText(getString(R.string.strtotalquestions)+ arrListQuestions.size());
            getFragment().hideText();
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        try {
            switch (apiCode) {
                case WebConstants.SET_QUESTIONS_FOR_EXAM:
                    onResponseSetQuestionsForExam(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }


    }

    private void onResponseSetQuestionsForExam(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Utility.showToast(getActivity().getString(R.string.str_success_setexamquestions), getActivity());
                } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseSetQuestionForExam api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseSetQuestionForExam Exception : " + e.toString());
        }

    }

    public void updateQuestionDataAfterEditQuestion(Questions prevQuestionData, Questions updatedQuestionData, Boolean isChecked) {

        Boolean isQuestionPresent = false;


        for (Questions questions : arrListQuestions) {
            if (questions.getQuestionId().equals(prevQuestionData.getQuestionId())) {
                arrListQuestions.set(arrListQuestions.indexOf(questions), updatedQuestionData);
                isQuestionPresent = true;
                break;
            }
        }
        previewQuestionListAdapter.addAll(arrListQuestions);
        previewQuestionListAdapter.notifyDataSetChanged();

        if (!isQuestionPresent && isChecked) {
            addQuestionDataAfterAddQuestion(updatedQuestionData);
        }

//        int position = arrListQuestions.indexOf(prevQuestionData);
//        if (position != -1) {
//            arrListQuestions.set(position, updatedQuestionData);
//            previewQuestionListAdapter.addAll(arrListQuestions);
//            previewQuestionListAdapter.notifyDataSetChanged();
//        } else {
//            if (isChecked) {
//                addQuestionDataAfterAddQuestion(updatedQuestionData);
//            }
//        }
    }

    public void addQuestionDataAfterAddQuestion(Questions question) {
//        arrListQuestions.add(0, question);
        arrListQuestions.add(question);
        previewQuestionListAdapter.addAll(arrListQuestions);
        previewQuestionListAdapter.notifyDataSetChanged();

        Debug.e(TAG, "inside addQue data");
    }


    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }

}
