package com.ism.author.fragment;

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
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.PreviewQuestionListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.MyTypeFace;
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

        tvPreviewQuestionlistTitle = (TextView) view.findViewById(R.id.tv_previewquestion_title);
        tvPreviewQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvPreviewQuestionlistFreeze = (TextView) view.findViewById(R.id.tv_preview_questionlist_freeze);
        tvPreviewQuestionlistFreeze.setTypeface(myTypeFace.getRalewayRegular());

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



        /*this condition will not work this is for testing*/
        /*check for the arraylist presence*/

//        if (getArguments() != null) {
//
//            if (getArguments().containsKey(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS)) {
//
//                arrListQuestions = getArguments().getParcelableArrayList(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS);
//                previewQuestionListAdapter.addAll(this.arrListQuestions);
//
//                Debug.e(TAG, "THE SIZE OF PREVIEW QUESTION LIST IS" + arrListQuestions.size());
//            }
//        }

    }

    public void setExamQuestions(ArrayList<Questions> arrListExamQuestions) {
        arrListQuestions.addAll(arrListExamQuestions);
        previewQuestionListAdapter.addAll(this.arrListQuestions);
    }


    private void callApiFreezeQuestions() {
        if (Utility.isConnected(getActivity())) {
            if (arrListQuestions.size() > 0) {
                try {
                    ((AuthorHostActivity) getActivity()).showProgress();
                    Attribute attribute = new Attribute();
                    attribute.setExamId(getArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                    attribute.setQuestionIdList(getArrListQuestionId());

                    new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                            .execute(WebConstants.SETQUESTIONSFOREXAM);
                } catch (Exception e) {
                    Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
                }
            } else {
                Utility.alert(getActivity(), null, getString(R.string.strnopreviewquestions));
//                Utils.showToast(getString(R.string.strnopreviewquestions), getActivity());
            }
        } else {
            Utility.toastOffline(getActivity());
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
            }
            Debug.e(TAG, "THE SIZE OF PREVIEW QUESTION LIST IS" + arrListQuestions.size());
            previewQuestionListAdapter.addAll(this.arrListQuestions);
        }
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
                    Utils.showToast(getActivity().getString(R.string.str_success_setexamquestions), getActivity());
//                    Utility.alert(getActivity(), null, getActivity().getString(R.string.str_success_setexamquestions));
                } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
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
    }


    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }

}
