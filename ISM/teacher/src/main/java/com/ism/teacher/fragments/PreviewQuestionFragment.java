package com.ism.teacher.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
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


    private TextView tvPreviewQuestionlistTitle, tvPreviewQuestionlistFreeze;
    private RecyclerView rvPreviewquestionlist;
    private PreviewQuestionListAdapter previewQuestionListAdapter;
    //    RecyclerListAdapter adapter;
    public ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    MyTypeFace myTypeFace;

    //this is for the movable recyclerview.
    private ItemTouchHelper mItemTouchHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preview_question, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());

        tvPreviewQuestionlistTitle = (TextView) view.findViewById(R.id.tv_questionlist_title);
        tvPreviewQuestionlistFreeze = (TextView) view.findViewById(R.id.tv_freeze_question);

        tvPreviewQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());

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
                    attribute.setExamId("61");
                    attribute.setQuestionId(getQuestionIdList());

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
    }

    private ArrayList<String> questionIdList = new ArrayList<String>();

    private ArrayList<String> getQuestionIdList() {

        for (int i = 0; i < arrListQuestions.size(); i++) {
            questionIdList.add(arrListQuestions.get(i).getQuestionId());
        }
        return questionIdList;

    }


    public void addQuestionsToPreviewFragment(ArrayList<Questions> arrListQuestionsToAdd) {

        if (arrListQuestionsToAdd.size() > 0) {

            arrListQuestions.addAll(arrListQuestionsToAdd);
            previewQuestionListAdapter.addAll(arrListQuestions);

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
        arrListQuestions.add(0, question);
        previewQuestionListAdapter.addAll(arrListQuestions);
        previewQuestionListAdapter.notifyDataSetChanged();
    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }


}
