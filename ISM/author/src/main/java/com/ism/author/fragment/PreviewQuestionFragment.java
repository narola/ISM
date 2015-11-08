package com.ism.author.fragment;

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
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.PreviewQuestionListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.RequestObject;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;

import java.util.ArrayList;

/**
 * Created by c166 on 31/10/15.
 */
//implements OnStartDragListener

public class PreviewQuestionFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = PreviewQuestionFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public PreviewQuestionFragment(Fragment fragment) {
        this.mFragment = fragment;
    }


    private TextView tvPreviewQuestionlistTitle, tvPreviewQuestionlistFreeze;
    private RecyclerView rvPreviewquestionlist;
    private PreviewQuestionListAdapter previewQuestionListAdapter;
    //    RecyclerListAdapter adapter;
    public ArrayList<Data> listOfPreviewQuestions = new ArrayList<Data>();
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

    }


    private void callApiFreezeQuestions() {

        if (Utils.isInternetConnected(getActivity())) {
            if (listOfPreviewQuestions.size() > 0) {
                try {
                    RequestObject requestObject = new RequestObject();
                    requestObject.setExamId("61");
                    requestObject.setQuestionId(getQuestionIdList());

                    new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                            .execute(WebConstants.SETQUESTIONSFOREXAM);
                } catch (Exception e) {
                    Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
                }

            } else {
                Utils.showToast(getString(R.string.strnopreviewquestions), getActivity());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    private ArrayList<String> questionIdList = new ArrayList<String>();

    private ArrayList<String> getQuestionIdList() {

        for (int i = 0; i < listOfPreviewQuestions.size(); i++) {
            questionIdList.add(listOfPreviewQuestions.get(i).getQuestionId());
        }
        return questionIdList;

    }

    public void addQuestionsToPreviewFragment(ArrayList<Data> data) {
        if (data.size() > 0) {
            listOfPreviewQuestions.addAll(data);
            previewQuestionListAdapter.addAll(listOfPreviewQuestions);

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
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    Utils.showToast(getActivity().getString(R.string.str_success_setexamquestions), getActivity());
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseSetQuestionForExam api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseSetQuestionForExam Exception : " + e.toString());
        }

    }
}
