package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.QuestionBankListAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.model.Request;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;

/**
 * these fragment is for getting the questionbank.
 */
public class QuestionListFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = QuestionListFragment.class.getSimpleName();
    private View view;

    //    private FragmentListener fragListener;
    Fragment mFragment;


    public QuestionListFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }


    Spinner spQuestionlistCourse, spQuestionlistSubject, spQuestionlistExamType;
    EditText etQuestionlistSearch;
    TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview;
    RecyclerView rvQuestionlist;
    QuestionBankListAdapter questionBankListAdapter;

    MyTypeFace myTypeFace;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionlist, container, false);
        Utils.showToast("THE QUESTION LIST FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        spQuestionlistCourse = (Spinner) view.findViewById(R.id.sp_questionlist_course);
        spQuestionlistSubject = (Spinner) view.findViewById(R.id.sp_questionlist_subject);
        spQuestionlistExamType = (Spinner) view.findViewById(R.id.sp_questionlist_exam_type);

        etQuestionlistSearch = (EditText) view.findViewById(R.id.et_questionlist_search);

        tvQuestionlistTitle = (TextView) view.findViewById(R.id.tv_questionlist_title);
        tvQuestionlistAddNewQuestion = (TextView) view.findViewById(R.id.tv_questionlist_add_new_question);
        tvQuestionlistAddPreview = (TextView) view.findViewById(R.id.tv_questionlist_add_preview);

        rvQuestionlist = (RecyclerView) view.findViewById(R.id.rv_questionlist);
        questionBankListAdapter = new QuestionBankListAdapter(getActivity());
        rvQuestionlist.setAdapter(questionBankListAdapter);
        rvQuestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));


        etQuestionlistSearch.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddPreview.setTypeface(myTypeFace.getRalewayRegular());


        tvQuestionlistAddPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((AddQuestionFragment) mFragment).addItemToPreviewFragment(getQuestionBankResponseObject.getData().get(0));


            }
        });


        callApiGetQuestionBank();


    }


    private void callApiGetQuestionBank() {


        if (Utils.isInternetConnected(getActivity())) {
            try {
                Request request = new Request();
                request.setUser_id("370");
                request.setRole(AppConstant.AUTHOR_ROLE_ID);
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETQUESTIONBANK);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            fragListener = (FragmentListener) activity;
//            if (fragListener != null) {
//                fragListener.onFragmentAttached(AddQuestionFragment.FRAGMENT_QUESTIONLIST);
//            }
//        } catch (ClassCastException e) {
//            Debug.e(TAG, "onAttach Exception : " + e.toString());
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        try {
//            if (fragListener != null) {
//                fragListener.onFragmentDetached(AddQuestionFragment.FRAGMENT_QUESTIONLIST);
//            }
//        } catch (ClassCastException e) {
//            Debug.e(TAG, "onDetach Exception : " + e.toString());
//        }
//        fragListener = null;
    }

    ResponseObject getQuestionBankResponseObject;

    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {


        try {
            if (apiMethodName == WebserviceWrapper.GETQUESTIONBANK) {

                getQuestionBankResponseObject = (ResponseObject) object;
                if (getQuestionBankResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && getQuestionBankResponseObject != null) {
                    questionBankListAdapter.addAll(getQuestionBankResponseObject.getData());
                    Utils.showToast(getQuestionBankResponseObject.getMessage() + getQuestionBankResponseObject.getData().size(), getActivity());
                } else {
                    Utils.showToast(getQuestionBankResponseObject.getMessage(), getActivity());
                }

            }
        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }
}
