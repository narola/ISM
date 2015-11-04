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

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.QuestionBankListAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.Request;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * these fragment is for getting the questionbank.
 */
public class QuestionListFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {


    private static final String TAG = QuestionListFragment.class.getSimpleName();
    private View view;

    //    private FragmentListener fragListener;
    Fragment mFragment;


    public QuestionListFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }


    Spinner spQuestionlistCourse, spQuestionlistSubject, spQuestionlistExamType;
    List<String> arrListExamType;
    List<Data> arrListSubject, arrListCourses;
    EditText etQuestionlistSearch;
    TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview;
    RecyclerView rvQuestionlist;
    QuestionBankListAdapter questionBankListAdapter;

    ArrayList<Data> listOfQuestionBank = new ArrayList<Data>();


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
        questionBankListAdapter = new QuestionBankListAdapter(getActivity(), mFragment);
        rvQuestionlist.setAdapter(questionBankListAdapter);
        rvQuestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));


        etQuestionlistSearch.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddPreview.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setOnClickListener(this);
        tvQuestionlistAddPreview.setOnClickListener(this);


        arrListExamType = new ArrayList<String>();
        arrListExamType.add(getString(R.string.strexamtype));
        arrListExamType = Arrays.asList(getResources().getStringArray(R.array.examtype));
        Adapters.setUpSpinner(getActivity(), spQuestionlistExamType, arrListExamType, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spQuestionlistCourse, arrListExamType, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spQuestionlistSubject, arrListExamType, Adapters.ADAPTER_SMALL);


        callApiGetQuestionBank();


    }

    private void callApiGetSubjects() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETSUBJECT);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    private void callApiGetCourses() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETCOURSES);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

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


            if (apiMethodName == WebserviceWrapper.GETSUBJECT) {

                ResponseObject callGetSubjectResponseObject = (ResponseObject) object;
                if (callGetSubjectResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && callGetSubjectResponseObject != null) {

                    arrListSubject = new ArrayList<Data>();
                    arrListSubject.addAll(callGetSubjectResponseObject.getData());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.strsubject));
                    for (Data subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistSubject, subjects, Adapters.ADAPTER_SMALL);

                } else {
                    Utils.showToast(callGetSubjectResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.GETCOURSES) {

                ResponseObject callGetCoursesResponseObject = (ResponseObject) object;
                if (callGetCoursesResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && callGetCoursesResponseObject != null) {

                    arrListCourses = new ArrayList<Data>();
                    arrListCourses.addAll(callGetCoursesResponseObject.getData());
                    List<String> courses = new ArrayList<String>();
                    courses.add(getString(R.string.strcourse));
                    for (Data course : arrListCourses) {
                        courses.add(course.getCourseName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistCourse, courses, Adapters.ADAPTER_SMALL);
                } else {

                    Utils.showToast(callGetCoursesResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.GETQUESTIONBANK) {

                getQuestionBankResponseObject = (ResponseObject) object;
                if (getQuestionBankResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && getQuestionBankResponseObject != null) {

                    listOfQuestionBank.addAll(getQuestionBankResponseObject.getData());
                    questionBankListAdapter.addAll(listOfQuestionBank);
                } else {
                    Utils.showToast(getQuestionBankResponseObject.getMessage(), getActivity());
                }

            }
        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    @Override
    public void onClick(View v) {

        if (v == tvQuestionlistAddPreview) {

            if (questionBankListAdapter.getListOfPreviewQuestionsToAdd().size() > 0) {

                ((AddQuestionFragment) mFragment).previewQuestionFragment.addQuestionsToPreviewFragment(questionBankListAdapter.getListOfPreviewQuestionsToAdd());
                questionBankListAdapter.getListOfPreviewQuestionsToAdd().clear();

            } else {
                Utils.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());

            }


        } else if (v == tvQuestionlistAddNewQuestion) {
            ((AddQuestionFragment) mFragment).flipCard();

        }

    }

    public void updateViewAfterDeleteInPreviewQuestion(Data data) {

        int position = listOfQuestionBank.indexOf(data);
        listOfQuestionBank.get(position).setIsQuestionAddedInPreview(false);
        questionBankListAdapter.addAll(listOfQuestionBank);
        ((AddQuestionFragment) mFragment).previewQuestionFragment.listOfPreviewQuestions.remove(data);


    }
}
