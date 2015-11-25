package com.ism.teacher.fragments;

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
import com.ism.teacher.adapters.PreviewQuestionListAdapter;
import com.ism.teacher.constants.AppConstant;
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

    TextView tvQuestionlistTitle, tv_freeze_question;
    RecyclerView rvPreviewquestionlist;
    PreviewQuestionListAdapter previewQuestionListAdapter;

    MyTypeFace myTypeFace;
    Fragment mFragment;


    //ArrayList
    public ArrayList<Questions> listOfPreviewQuestions = new ArrayList<>();


    public PreviewQuestionFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }

    public PreviewQuestionFragment(){}
    Attribute attribute = new Attribute();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preview_question, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());

        tvQuestionlistTitle = (TextView) view.findViewById(R.id.tv_questionlist_title);
        tv_freeze_question = (TextView) view.findViewById(R.id.tv_freeze_question);

        tvQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());

        rvPreviewquestionlist = (RecyclerView) view.findViewById(R.id.rv_previewquestionlist);
        previewQuestionListAdapter = new PreviewQuestionListAdapter(getActivity(), mFragment);
        rvPreviewquestionlist.setAdapter(previewQuestionListAdapter);
        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));

/*        adapter = new RecyclerListAdapter(getActivity(), this);
//        rvPreviewquestionlist.setHasFixedSize(true);
        rvPreviewquestionlist.setAdapter(adapter);
        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        callback.isItemViewSwipeEnabled();
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvPreviewquestionlist);*/

        tv_freeze_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callApiFreezeQuestions();
            }
        });

    }

    private void callApiFreezeQuestions() {
        if (Utility.isOnline(getActivity())) {
            if (listOfPreviewQuestions.size() > 0) {
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

    private ArrayList<String> questionIdList = new ArrayList<String>();

    private ArrayList<String> getQuestionIdList() {

        for (int i = 0; i < listOfPreviewQuestions.size(); i++) {
            questionIdList.add(listOfPreviewQuestions.get(i).getQuestionId());
        }
        return questionIdList;

    }


    public void addQuestionsToPreviewFragment(ArrayList<Questions> data) {

        if (data.size() > 0) {

            listOfPreviewQuestions.addAll(data);
            previewQuestionListAdapter.addAll(listOfPreviewQuestions);

        }

    }


    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {
        try {
            if (apiMethodName == WebConstants.SET_QUESTIONS_FOR_EXAM) {

                ResponseHandler callGetFreezeQuesytionResponseHandler = (ResponseHandler) object;
                if (callGetFreezeQuesytionResponseHandler != null && callGetFreezeQuesytionResponseHandler.getStatus().equals(AppConstant.API_STATUS_SUCCESS)) {

                    Utility.showToast("Freeze question successful", getActivity());

                } else {
                    Utility.showToast("Freeze question not successful", getActivity());
                }

            }
        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }

    }

    public void updateQuestionDataAfterEditQuestion() {
        int position = getFragment().getPOSITION_FOR_EDITQUESTION();
        listOfPreviewQuestions.get(position).setQuestionText("test");
        previewQuestionListAdapter.addAll(listOfPreviewQuestions);
        previewQuestionListAdapter.notifyDataSetChanged();


    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }


}
