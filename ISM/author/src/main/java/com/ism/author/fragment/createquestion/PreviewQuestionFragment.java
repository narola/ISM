package com.ism.author.fragment.createquestion;

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

import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.PreviewQuestionListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c166 on 31/10/15.
 */
//implements OnStartDragListener

public class PreviewQuestionFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = PreviewQuestionFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public PreviewQuestionFragment() {
    }

    @SuppressLint("ValidFragment")
    public PreviewQuestionFragment(Fragment fragment) {
        this.mFragment = fragment;
    }


    private TextView tvPreviewQuestionlistTitle, tvPreviewQuestionlistFreeze, tvPreviewTotalQuestionTitle, tvPreviewTotalQuestionNo,
            tvPreviewTotalScoreTitle, tvPreviewTotalScoreNo, tvNoDataMsg;
    private RecyclerView rvPreviewquestionlist;
    private PreviewQuestionListAdapter previewQuestionListAdapter;
    //    RecyclerListAdapter adapter;
    public ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();

    //this is for the movable recyclerview.
    private ItemTouchHelper mItemTouchHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preview_question, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {


        tvPreviewQuestionlistTitle = (TextView) view.findViewById(R.id.tv_previewquestion_title);
        tvPreviewQuestionlistTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvPreviewQuestionlistFreeze = (TextView) view.findViewById(R.id.tv_preview_questionlist_freeze);
        tvPreviewTotalQuestionTitle = (TextView) view.findViewById(R.id.tv_preview_total_question_title);
        tvPreviewTotalQuestionNo = (TextView) view.findViewById(R.id.tv_preview_total_question_no);
        tvPreviewTotalScoreTitle = (TextView) view.findViewById(R.id.tv_preview_total_score_title);
        tvPreviewTotalScoreNo = (TextView) view.findViewById(R.id.tv_preview_total_score_no);
        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);

        tvPreviewQuestionlistFreeze.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvPreviewTotalQuestionTitle.setTypeface(Global.myTypeFace.getRalewayBold());
        tvPreviewTotalQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());
        tvPreviewTotalScoreTitle.setTypeface(Global.myTypeFace.getRalewayBold());
        tvPreviewTotalScoreNo.setTypeface(Global.myTypeFace.getRalewayBold());

        tvPreviewTotalQuestionNo.setText("0");
        tvPreviewTotalScoreNo.setText("0");


        rvPreviewquestionlist = (RecyclerView) view.findViewById(R.id.rv_previewquestionlist);
        previewQuestionListAdapter = new PreviewQuestionListAdapter(getActivity(), mFragment);
        rvPreviewquestionlist.setAdapter(previewQuestionListAdapter);
        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*this is for movable items recyclerview */

//        adapter = new RecyclerListAdapter(getActivity(), this);
//        rvPreviewquestionlist.setHasFixedSize(true);
//        rvPreviewquestionlist.setAdapter(adapter);
//        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
//        callback.isItemViewSwipeEnabled();
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(rvPreviewquestionlist);

        tvPreviewQuestionlistFreeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiFreezeQuestions();
            }
        });

        setEmptyView(false);
    }

    public void setExamQuestions(ArrayList<Questions> arrListExamQuestions) {
        arrListQuestions.addAll(arrListExamQuestions);
        previewQuestionListAdapter.addAll(this.arrListQuestions);


        if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE)) {
            for (Questions question : arrListExamQuestions) {
                totalScore += Integer.parseInt(question.getQuestionScore());
                totalQuestions += 1;
            }
        } else {
            Debug.e(TAG, "THE QUESTION SCORE VALUE IS:::" + getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));

            totalScore = Integer.parseInt(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE)) * arrListExamQuestions.size();
            totalQuestions = arrListExamQuestions.size();

        }
        UpdateQuestionInfo();
    }

    int totalQuestions = 0;
    int totalScore = 0;

    public void UpdateQuestionInfo() {

        if (totalQuestions > 0) {
            setEmptyView(false);
        } else {
            setEmptyView(true);
        }

        tvPreviewTotalQuestionNo.setText(String.valueOf(totalQuestions));
        tvPreviewTotalScoreNo.setText(String.valueOf(totalScore));

    }

    private void callApiFreezeQuestions() {
        if (Utility.isConnected(getActivity())) {
            if (arrListQuestions.size() > 0) {
                try {
                    ((AuthorHostActivity) getActivity()).showProgress();
                    Attribute attribute = new Attribute();
                    attribute.setExamId(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                    attribute.setQuestionIdList(getArrListQuestionId());

                    new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                            .execute(WebConstants.SETQUESTIONSFOREXAM);
                } catch (Exception e) {
                    Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
                }
            } else {
                Utility.alert(getActivity(), null, getString(R.string.strnopreviewquestions));
            }
        } else {
            Utility.alertOffline(getActivity());
        }

    }

    private ArrayList<String> arrListQuestionId = new ArrayList<String>();

    private ArrayList<String> getArrListQuestionId() {
        for (int i = 0; i < arrListQuestions.size(); i++) {
            arrListQuestionId.add(arrListQuestions.get(i).getQuestionId());
        }
        return arrListQuestionId;

    }

    public void addQuestionsToPreviewFragment(ArrayList<Questions> arrListQuestionsToAdd) {

        if (arrListQuestionsToAdd.size() > 0) {
            for (int i = 0; i < arrListQuestionsToAdd.size(); i++) {
                arrListQuestions.add(arrListQuestionsToAdd.get(i));

                if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE)) {
                    totalScore += Integer.parseInt(arrListQuestions.get(i).getQuestionScore());
                } else {
                    totalScore += Integer.parseInt(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));
                }

                totalQuestions += 1;
            }
            Debug.e(TAG, "THE SIZE OF PREVIEW QUESTION LIST IS" + arrListQuestions.size());
            previewQuestionListAdapter.addAll(this.arrListQuestions);
            UpdateQuestionInfo();
        }

    }

    public void updateQuestionInfoAfterDelete(int questionScore) {
        totalQuestions -= 1;


        if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE)) {
            totalScore -= questionScore;
        } else {
            totalScore -= Integer.parseInt(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));
        }
        UpdateQuestionInfo();
    }

    //   @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.SETQUESTIONSFOREXAM:
                    onResponseSetQuestionsForExam(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseSetQuestionsForExam(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
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
        } else {

            if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE)) {
                totalScore -= Integer.parseInt(prevQuestionData.getQuestionScore());
                totalScore += Integer.parseInt(updatedQuestionData.getQuestionScore());
            } else {
                totalScore -= Integer.parseInt(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));
                totalScore += Integer.parseInt(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));
            }

            UpdateQuestionInfo();
        }
    }

    public void addQuestionDataAfterAddQuestion(Questions question) {

        arrListQuestions.add(question);
        previewQuestionListAdapter.addAll(arrListQuestions);
        previewQuestionListAdapter.notifyDataSetChanged();
        totalQuestions += 1;


        if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE)) {
            totalScore += Integer.parseInt(question.getQuestionScore());
        } else {
            totalScore += Integer.parseInt(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));
        }

        UpdateQuestionInfo();

    }


    private AddQuestionContainerFragment getBaseFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_preview_questions));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvPreviewquestionlist.setVisibility(isEnable ? View.GONE : View.VISIBLE);

    }
}
