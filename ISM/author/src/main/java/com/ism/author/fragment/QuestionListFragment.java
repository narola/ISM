package com.ism.author.fragment;

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
import com.ism.author.interfaces.FlipCardListener;
import com.ism.author.model.Data;
import com.ism.author.model.RequestObject;
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
    Fragment mFragment;

    public QuestionListFragment(Fragment fragment) {
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
                        .execute(WebConstants.GETSUBJECT);
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
                        .execute(WebConstants.GETCOURSES);
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
                RequestObject requestObject = new RequestObject();
                requestObject.setUserId("370");
                requestObject.setRole(AppConstant.AUTHOR_ROLE_ID);
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETQUESTIONBANK);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETSUBJECT:
                    onResponseGetSubjects(object, error);
                    break;
                case WebConstants.GETCOURSES:
                    onResponseGetCourses(object, error);
                    break;
                case WebConstants.GETQUESTIONBANK:
                    onResponseGetQuestionBank(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }


    private void onResponseGetSubjects(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    arrListSubject = new ArrayList<Data>();
                    arrListSubject.addAll(responseObj.getData());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.strsubject));
                    for (Data subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistSubject, subjects, Adapters.ADAPTER_SMALL);
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetSubjects api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetSubjects Exception : " + e.toString());
        }
    }

    private void onResponseGetCourses(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {

                    arrListCourses = new ArrayList<Data>();
                    arrListCourses.addAll(responseObj.getData());
                    List<String> courses = new ArrayList<String>();
                    courses.add(getString(R.string.strcourse));
                    for (Data course : arrListCourses) {
                        courses.add(course.getCourseName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistCourse, courses, Adapters.ADAPTER_SMALL);

                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetCourses api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetCourses Exception : " + e.toString());
        }
    }


    private void onResponseGetQuestionBank(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    listOfQuestionBank.addAll(responseObj.getData());
                    questionBankListAdapter.addAll(listOfQuestionBank);
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetCourses api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetCourses Exception : " + e.toString());
        }
    }

    FlipCardListener flipCardListener;

    @Override
    public void onClick(View v) {

        if (v == tvQuestionlistAddPreview) {
            if (questionBankListAdapter.getListOfPreviewQuestionsToAdd().size() > 0) {
                ((AddQuestionDataFragment) mFragment).previewQuestionFragment.addQuestionsToPreviewFragment(questionBankListAdapter.getListOfPreviewQuestionsToAdd());
                questionBankListAdapter.getListOfPreviewQuestionsToAdd().clear();
            } else {
                Utils.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());

            }
        } else if (v == tvQuestionlistAddNewQuestion) {

            flipCardListener = (FlipCardListener) mFragment;
            flipCardListener.onFlipCard(false, null);


        }

    }

    public void updateViewAfterDeleteInPreviewQuestion(Data data) {
        int position = listOfQuestionBank.indexOf(data);
        listOfQuestionBank.get(position).setIsQuestionAddedInPreview(false);
        questionBankListAdapter.addAll(listOfQuestionBank);
        ((AddQuestionDataFragment) mFragment).previewQuestionFragment.listOfPreviewQuestions.remove(data);
    }


}
