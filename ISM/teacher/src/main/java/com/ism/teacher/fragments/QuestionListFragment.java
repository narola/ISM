package com.ism.teacher.fragments;

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

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.adapters.QuestionBankListAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.model.Request;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.ws.WebserviceWrapper;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionListFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = QuestionListFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private static final String ARG_FRAGMENT = "fragment";
    Fragment mFragment;


    public QuestionListFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }

    public QuestionListFragment(){}
    public static QuestionListFragment newInstance(int fragment) {
        QuestionListFragment questionListFragment = new QuestionListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT, fragment);
        questionListFragment.setArguments(args);
        return questionListFragment;
    }



    Spinner spQuestionlistCourse, spQuestionlistSubject, spQuestionlistExamType;
    EditText etQuestionlistSearch;
    TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview;
    RecyclerView rvQuestionlist;
    QuestionBankListAdapter questionBankListAdapter;

    MyTypeFace myTypeFace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionlist_teacher, container, false);
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

        tvQuestionlistAddNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int fragment=AddQuestionFragmentTeacher.getCurrentChildFragment();
//
//                getFragmentManager().beginTransaction().
//                        setCustomAnimations(
//                                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
//                                R.animator.card_flip_left_in, R.animator.card_flip_left_out).replace(R.id.fl_addquestionfragment_container_left, AddNewQuestionFromAssignment.newInstance(fragment)).commit();

                ((AddQuestionFragmentTeacher) mFragment).flipCard();

            }
        });

        tvQuestionlistAddPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((AddQuestionFragmentTeacher) mFragment).addItemToPreviewFragment(getQuestionBankResponseObject.getData().get(0));


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
//                Debug.e(TAG + getString(R.string.str), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.no_internet), getActivity());
        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    ResponseObject getQuestionBankResponseObject;

    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {


        try {
            if (apiMethodName == WebserviceWrapper.GETQUESTIONBANK) {

                getQuestionBankResponseObject = (ResponseObject) object;
                if (getQuestionBankResponseObject.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && getQuestionBankResponseObject != null) {
                    questionBankListAdapter.addAll(getQuestionBankResponseObject.getData());
                    Utils.showToast(getQuestionBankResponseObject.getMessage() + getQuestionBankResponseObject.getData().size(), getActivity());
                } else {
                    Utils.showToast(getQuestionBankResponseObject.getMessage(), getActivity());
                }

            }
        } catch (Exception e) {

            //    Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }
}
