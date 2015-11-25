package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.QuestionBankListAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Courses;
import com.ism.teacher.ws.model.QuestionBank;
import com.ism.teacher.ws.model.Subjects;
import com.ism.teacher.ws.model.Topics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionListFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {


    private static final String TAG = QuestionListFragment.class.getSimpleName();
    Fragment mFragment;

    //Views
    private View view;
    Spinner spQuestionlistCourse, spQuestionlistSubject, spQuestionlistExamType;
    EditText etQuestionlistSearch;
    TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview;
    RecyclerView rvQuestionlist;

    //ArrayList and adapter
    QuestionBankListAdapter questionBankListAdapter;
    ArrayList<QuestionBank> listOfQuestionBank = new ArrayList<>();
    ArrayList<QuestionBank> copylistOfQuestionBank = new ArrayList<>();
    List<String> arrListExamType;

    List<Subjects> arrListSubject;
    List<Courses> arrListCourses;
    List<Topics> arrListTopic;

    //variables
    MyTypeFace myTypeFace;
    String subjectName = "";
    int subject_id;


    public QuestionListFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }

    public QuestionListFragment() {

    }

//    public static QuestionListFragment newInstance(Bundle bundleArgument) {
//        QuestionListFragment questionListFragment = new QuestionListFragment();
//        if (bundleArgument != null) {
//            Log.e(TAG, bundleArgument.getString(AppConstant.BUNDLE_EXAM_ID));
//
//        }
//        return questionListFragment;
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionlist_teacher, container, false);
        Utility.showToast("THE QUESTION LIST FRAGMENT CALLED", getActivity());
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

        etQuestionlistSearch.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddPreview.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setOnClickListener(this);
        tvQuestionlistAddPreview.setOnClickListener(this);


        arrListExamType = new ArrayList<String>();
//        arrListExamType.add(getString(R.string.strexamtype));
//        arrListExamType = Arrays.asList(getResources().getStringArray(R.array.examtype));

        //tets for filter

        arrListExamType.add(getString(R.string.strtopic));

        Adapters.setUpSpinner(getActivity(), spQuestionlistExamType, arrListExamType, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spQuestionlistCourse, arrListExamType, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spQuestionlistSubject, arrListExamType, Adapters.ADAPTER_SMALL);

        callApiGetCourses();
        callApiGetSubjects();

        callApiGetQuestionBank();


        /*spQuestionlistExamType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isExamTypeSet()) {
                    if (isSubjectSet()) {
//                        Toast.makeText(getActivity(),"Filter",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Please select subject", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please select exam type ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        spQuestionlistSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {

                    callApiGetTopics(Integer.parseInt(arrListSubject.get(position - 1).getId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spQuestionlistExamType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    Log.e("sub", "" + subject_id);
                    Log.e("subname", "" + subjectName);
                    Log.e("topics id", "" + arrListTopic.get(position - 1).getId());
                    Log.e("topics name", "" + arrListTopic.get(position - 1).getTopicName());

//                    filterResults(subject_id,arrListTopic.get(position - 1).getId());
                    filterResults(subject_id, "30");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void filterResults(int subject_id, String topicid) {

        if (listOfQuestionBank.size() > 0) {
            for (QuestionBank wp : listOfQuestionBank) {
                int count = 0;
                if (wp.getTopicId().equalsIgnoreCase(topicid) && wp.getSubjectId().equalsIgnoreCase(Integer.toString(subject_id))) {
                    Log.e("filter success", "" + count++);
                    copylistOfQuestionBank.add(wp);
                }

            }

            if (copylistOfQuestionBank.size() > 0) {
                questionBankListAdapter.addAll(copylistOfQuestionBank);

            } else {
                Toast.makeText(getActivity(), "No Questions Found to Filter", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void callApiGetTopics(int subject_id) {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setSubjectId(String.valueOf(subject_id));
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_TOPICS);
            } catch (Exception e) {
                //Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }

    private void callApiGetSubjects() {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_SUBJECT);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.no_internet), getActivity());
        }

    }

    private void callApiGetCourses() {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_COURSES);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.no_internet), getActivity());
        }

    }


    private void callApiGetQuestionBank() {


        if (Utility.isInternetConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setUserId(AppConstant.TEST_USER_ID);
                attribute.setRole(AppConstant.TEACHER_ROLE_ID + "");
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_QUESTION_BANK);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    public ResponseHandler getQuestionBankResponseHandler;

    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {
            switch (apiMethodName) {
                case WebConstants.GET_SUBJECT:
                    onResponseGetSubject(object);
                    break;

                case WebConstants.GET_COURSES:
                    onResponseGetCourses(object);
                    break;

                case WebConstants.GET_QUESTION_BANK:
                    onResponseGetQuestionBank(object);
                    break;
                case WebConstants.GET_TOPICS:
                    onResponseGetTopics(object);
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    private void onResponseGetCourses(Object object) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && responseHandler != null) {

            arrListCourses = new ArrayList<>();
            arrListCourses.addAll(responseHandler.getCourses());
            List<String> courses = new ArrayList<String>();
            courses.add(getString(R.string.strcourse));
            for (Courses course : arrListCourses) {
                courses.add(course.getCourseName());

            }
            Adapters.setUpSpinner(getActivity(), spQuestionlistCourse, courses, Adapters.ADAPTER_SMALL);
        } else {

            Utility.showToast(responseHandler.getMessage(), getActivity());
        }
    }

    private void onResponseGetQuestionBank(Object object) {
        getQuestionBankResponseHandler = (ResponseHandler) object;
        if (getQuestionBankResponseHandler.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && getQuestionBankResponseHandler != null) {

            listOfQuestionBank.addAll(getQuestionBankResponseHandler.getQuestionBanks());
//                    copylistOfQuestionBank.addAll(listOfQuestionBank);
            questionBankListAdapter.addAll(listOfQuestionBank);

            Log.e("list_size_after_question bank", "" + listOfQuestionBank.size());

        } else {
            Utility.showToast(getQuestionBankResponseHandler.getMessage(), getActivity());
        }

    }

    private void onResponseGetTopics(Object object) {

        ResponseHandler responseHandler = (ResponseHandler) object;
        arrListTopic = new ArrayList<>();
        arrListTopic.addAll(responseHandler.getTopics());

        List<String> topics = new ArrayList<String>();
        topics.add(getString(R.string.strtopic));
        for (Topics topic : arrListTopic) {
            topics.add(topic.getTopicName());

        }
        Adapters.setUpSpinner(getActivity(), spQuestionlistExamType, topics, Adapters.ADAPTER_SMALL);
    }

    private void onResponseGetSubject(Object object) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && responseHandler != null) {

            arrListSubject = new ArrayList<>();
            arrListSubject.addAll(responseHandler.getSubjects());
            List<String> subjects = new ArrayList<String>();
            subjects.add(getString(R.string.strsubject));
            for (Subjects subject : arrListSubject) {
                subjects.add(subject.getSubjectName());

            }
            Adapters.setUpSpinner(getActivity(), spQuestionlistSubject, subjects, Adapters.ADAPTER_SMALL);

        } else {
            Utility.showToast(responseHandler.getMessage(), getActivity());
        }
    }

    @Override
    public void onClick(View v) {

        if (v == tvQuestionlistAddPreview) {

            if (((AddQuestionContainerFragment) mFragment).getListOfPreviewQuestionsToAdd().size() > 0) {
//                ((AddQuestionContainerFragment) mFragment).previewQuestionFragment.
//                        addQuestionsToPreviewFragment(((AddQuestionContainerFragment) mFragment).getListOfPreviewQuestionsToAdd());
                ((AddQuestionContainerFragment) mFragment).addQuestionsToPreviewFragment();
                ((AddQuestionContainerFragment) mFragment).getListOfPreviewQuestionsToAdd().clear();
            } else {
                Utility.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());

            }


        } else if (v == tvQuestionlistAddNewQuestion) {
            ((AddQuestionContainerFragment) mFragment).setQuestionData(null);
            ((AddQuestionContainerFragment) mFragment).setIsSetQuestionData(false);
            ((AddQuestionContainerFragment) mFragment).flipCard();

        }

    }

    public void updateViewAfterDeleteInPreviewQuestion(QuestionBank data) {

        //Log.e("list_sizexzxZx", "" + getQuestionBankResponseHandler.getData().size());
        // Log.e("list_size", "" + listOfQuestionBank.size());

        if (listOfQuestionBank.size() > 0) {
            int position = listOfQuestionBank.indexOf(data);
            listOfQuestionBank.get(position).setIsQuestionAddedInPreview(false);
            questionBankListAdapter.addAll(listOfQuestionBank);
            ((AddQuestionContainerFragment) mFragment).previewQuestionFragment.listOfPreviewQuestions.remove(data);
        } else {
            Log.e("list_size", "" + listOfQuestionBank.size());
        }

    }


    private boolean isExamTypeSet() {
        if (arrListExamType != null && arrListExamType.size() == 0 || spQuestionlistExamType.getSelectedItemPosition() > 0) {
            return true;
        } else {
            return false;
        }

    }


    private boolean isSubjectSet() {
        if (arrListSubject != null && arrListSubject.size() == 0 || spQuestionlistSubject.getSelectedItemPosition() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void updateQuestionDataAfterEditQuestion() {
        int position = getFragment().getPOSITION_FOR_EDITQUESTION();
        listOfQuestionBank.get(position).setQuestionText("test");
        questionBankListAdapter.addAll(listOfQuestionBank);
        questionBankListAdapter.notifyDataSetChanged();


    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }

}
