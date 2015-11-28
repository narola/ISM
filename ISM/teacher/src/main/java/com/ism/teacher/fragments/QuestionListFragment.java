package com.ism.teacher.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.QuestionBankListAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Courses;
import com.ism.teacher.ws.model.Questions;
import com.ism.teacher.ws.model.Subjects;
import com.ism.teacher.ws.model.Topics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionListFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {


    private static final String TAG = QuestionListFragment.class.getSimpleName();
    Fragment mFragment;

    //Views
    private View view;
    Spinner spQuestionlistCourse, spQuestionlistSubject, spQuestionlistTopic;
    private EditText etSearchQuestions;
    TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview;
    RecyclerView rvQuestionlist;
    private ImageView imgSearchQuestions;

    //ArrayList and adapter
    QuestionBankListAdapter questionBankListAdapter;
    ArrayList<Questions> arrListQuestions = new ArrayList<>();
    ArrayList<Questions> copylistOfQuestionBank = new ArrayList<>();
    List<String> arrListExamType, arrListDefalt;

    List<Subjects> arrListSubject;
    List<Courses> arrListCourses;
    List<Topics> arrListTopic;

    //variables
    MyTypeFace myTypeFace;
    String subjectName = "";
    int subject_id;


    @SuppressLint("ValidFragment")
    public QuestionListFragment(Fragment fragment, Bundle bundleArguments) {
        this.mFragment = fragment;
        this.setArguments(bundleArguments);
    }

    public QuestionListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            arrListQuestions = getArguments().getParcelableArrayList(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS);
            Debug.e(TAG, "THE SIZE OF ARRAYLIST IS" + arrListQuestions.size());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionlist_teacher, container, false);
        Utility.showToast("THE QUESTION LIST FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        imgSearchQuestions = (ImageView) view.findViewById(R.id.img_search_questions);
        etSearchQuestions = (EditText) view.findViewById(R.id.et_search_questions);

        spQuestionlistCourse = (Spinner) view.findViewById(R.id.sp_questionlist_course);
        spQuestionlistSubject = (Spinner) view.findViewById(R.id.sp_questionlist_subject);
        spQuestionlistTopic = (Spinner) view.findViewById(R.id.sp_questionlist_topic);

        tvQuestionlistTitle = (TextView) view.findViewById(R.id.tv_questionlist_title);
        tvQuestionlistAddNewQuestion = (TextView) view.findViewById(R.id.tv_questionlist_add_new_question);
        tvQuestionlistAddPreview = (TextView) view.findViewById(R.id.tv_questionlist_add_preview);

        rvQuestionlist = (RecyclerView) view.findViewById(R.id.rv_questionlist);

        questionBankListAdapter = new QuestionBankListAdapter(getActivity(), mFragment);
        rvQuestionlist.setAdapter(questionBankListAdapter);
        rvQuestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        etSearchQuestions.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddPreview.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setOnClickListener(this);
        tvQuestionlistAddPreview.setOnClickListener(this);


        arrListExamType = new ArrayList<String>();
        arrListExamType.add(getString(R.string.strexamtype));
        arrListExamType = Arrays.asList(getResources().getStringArray(R.array.examtype));
        Adapters.setUpSpinner(getActivity(), spQuestionlistCourse, arrListExamType, Adapters.ADAPTER_SMALL);

        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.strtopic));
        Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, arrListDefalt, Adapters.ADAPTER_SMALL);


        callApiGetCourses();
        callApiGetSubjects();

        callApiGetQuestionBank();

        imgSearchQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSearchQuestions.getVisibility() == View.VISIBLE) {
//                    startSlideAnimation(etSearchMystudents, 0, etSearchMystudents.getWidth(), 0, 0);
//                    startSlideAnimation(imgSearchMystudents, -imgSearchMystudents.getWidth(), 0, 0, 0);
                    etSearchQuestions.setVisibility(View.GONE);
                    questionBankListAdapter.filter("");
                    etSearchQuestions.setText("");

                } else {
                    startSlideAnimation(etSearchQuestions, etSearchQuestions.getWidth(), 0, 0, 0);
                    startSlideAnimation(imgSearchQuestions, etSearchQuestions.getWidth(), 0, 0, 0);
                    etSearchQuestions.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etSearchQuestions, getActivity());
                }
            }
        });

        etSearchQuestions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                questionBankListAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /*spQuestionlistTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spQuestionlistTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void startSlideAnimation(final View view, int fromX, int toX, int fromY, int toY) {
        TranslateAnimation slideOutAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideOutAnimation.setDuration(500);
        slideOutAnimation.setFillAfter(true);
        view.startAnimation(slideOutAnimation);
    }

    private void filterResults(int subject_id, String topicid) {

        if (arrListQuestions.size() > 0) {
            for (Questions wp : arrListQuestions) {
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
                attribute.setRole(AppConstant.TEACHER_ROLE_ID);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_QUESTION_BANK);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {
            switch (apiMethodName) {
                case WebConstants.GET_SUBJECT:
                    onResponseGetSubject(object, error);
                    break;

                case WebConstants.GET_COURSES:
                    onResponseGetCourses(object, error);
                    break;

                case WebConstants.GET_QUESTION_BANK:
                    onResponseGetQuestionBank(object, error);
                    break;
                case WebConstants.GET_TOPICS:
                    onResponseGetTopics(object, error);
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    private void onResponseGetCourses(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListCourses = new ArrayList<Courses>();
                    arrListCourses.addAll(responseHandler.getCourses());
                    List<String> courses = new ArrayList<String>();
                    courses.add(getString(R.string.strcourse));
                    for (Courses course : arrListCourses) {
                        courses.add(course.getCourseName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistCourse, courses, Adapters.ADAPTER_SMALL);

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
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
            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListQuestions.addAll(responseHandler.getQuestionBanks());
                    setQuestionData(responseHandler.getQuestionBanks());
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetCourses api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetCourses Exception : " + e.toString());
        }

    }

    private void setQuestionData(ArrayList<Questions> questions) {
        questionBankListAdapter.addAll(questions);

    }

    private void onResponseGetTopics(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListTopic = new ArrayList<Topics>();
                    arrListTopic.addAll(responseHandler.getTopics());
                    List<String> topics = new ArrayList<String>();
                    topics.add(getString(R.string.select));
                    for (Topics topic : arrListTopic) {
                        topics.add(topic.getTopicName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, topics, Adapters.ADAPTER_SMALL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, arrListDefalt, Adapters.ADAPTER_NORMAL);
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetTopics api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetTopics Exception : " + e.toString());
        }
    }

    private void onResponseGetSubject(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListSubject = new ArrayList<Subjects>();
                    arrListSubject.addAll(responseHandler.getSubjects());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.strsubject));
                    for (Subjects subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistSubject, subjects, Adapters.ADAPTER_SMALL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetSubjects api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetSubjects Exception : " + e.toString());
        }
    }

    @Override
    public void onClick(View v) {

        if (v == tvQuestionlistAddPreview) {
            if (getFragment().getListOfPreviewQuestionsToAdd().size() > 0) {
                Debug.e(TAG, "The size of preview question list is:::" + getFragment().getListOfPreviewQuestionsToAdd().size());
                getFragment().addQuestionsToPreviewFragment();
                getFragment().getListOfPreviewQuestionsToAdd().clear();
            } else {
                Utility.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());
            }
        } else if (v == tvQuestionlistAddNewQuestion) {
            getFragment().setDataOnFragmentFlip(null, false, true);
        }

    }

    public void updateViewAfterDeleteInPreviewQuestion(Questions data) {

        //Log.e("list_sizexzxZx", "" + getQuestionBankResponseHandler.getData().size());
        // Log.e("list_size", "" + arrListQuestions.size());

        if (arrListQuestions.size() > 0) {
            int position = arrListQuestions.indexOf(data);
            arrListQuestions.get(position).setIsQuestionAddedInPreview(false);
            questionBankListAdapter.addAll(arrListQuestions);
            ((AddQuestionContainerFragment) mFragment).previewQuestionFragment.arrListQuestions.remove(data);
        } else {
            Log.e("list_size", "" + arrListQuestions.size());
        }

    }


    public void updateQuestionDataAfterEditQuestion(Questions prevQuestionData, Questions updatedQuestionData) {
        int position = arrListQuestions.indexOf(prevQuestionData);
        if (position != -1) {
            arrListQuestions.set(position, updatedQuestionData);
            questionBankListAdapter.addAll(arrListQuestions);
            questionBankListAdapter.notifyDataSetChanged();
        }

    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }

    public void updateQuestionStatusAfterSetDataOfExam() {
    }

    public void addQuestionDataAfterAddQuestion(Questions question) {
        arrListQuestions.add(0, question);
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();
    }

    private boolean isExamTypeSet() {
        if (arrListExamType != null && arrListExamType.size() == 0 || spQuestionlistTopic.getSelectedItemPosition() > 0) {
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
}
