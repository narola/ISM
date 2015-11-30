package com.ism.author.fragment;

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

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.QuestionBankListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Courses;
import com.ism.author.ws.model.Questions;
import com.ism.author.ws.model.Subjects;
import com.ism.author.ws.model.Topics;

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

    public QuestionListFragment() {
    }

    @SuppressLint("ValidFragment")
    public QuestionListFragment(Fragment fragment, Bundle bundleArguments) {
        this.mFragment = fragment;
        this.setArguments(bundleArguments);
    }

    private Spinner spQuestionlistCourse, spQuestionlistSubject, spQuestionlistTopic;
    private List<String> arrListExamType, arrListDefalt;
    private List<Subjects> arrListSubject;
    private List<Courses> arrListCourses;
    private List<Topics> arrListTopic;
    private EditText etSearchQuestions;
    private TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview;
    private RecyclerView rvQuestionlist;
    private QuestionBankListAdapter questionBankListAdapter;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    private MyTypeFace myTypeFace;
    private ImageView imgSearchQuestions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionlist, container, false);

//        Utils.showToast("THE QUESTION LIST FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        imgSearchQuestions = (ImageView) view.findViewById(R.id.img_search_questions);

        spQuestionlistCourse = (Spinner) view.findViewById(R.id.sp_questionlist_course);
        spQuestionlistSubject = (Spinner) view.findViewById(R.id.sp_questionlist_subject);
        spQuestionlistTopic = (Spinner) view.findViewById(R.id.sp_questionlist_topic);

        etSearchQuestions = (EditText) view.findViewById(R.id.et_search_questions);

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


        spQuestionlistSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrListSubject != null && position > 0) {

                    if (Utility.isConnected(getActivity())) {

                        callApiGetTopics(Integer.parseInt(arrListSubject.get(position - 1).getId()));
                    } else {
                        Utility.toastOffline(getActivity());
                    }
                } else {
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, arrListDefalt, Adapters.ADAPTER_SMALL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spQuestionlistTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
//                    Log.e("sub", "" + subject_id);
//                    Log.e("subname", "" + subjectName);
//                    Log.e("topics id", "" + arrListTopic.get(position - 1).getId());
//                    Log.e("topics name", "" + arrListTopic.get(position - 1).getTopic_name());
//
////                    filterResults(subject_id,arrListTopic.get(position - 1).getId());
//                    filterResults(subject_id, "30");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        callApiGetQuestionBank();

        callApiGetSubjects();
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

    private void callApiGetSubjects() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETSUBJECT);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    private void callApiGetTopics(int subject_id) {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setSubjectId(String.valueOf(subject_id));
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETTOPICS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callApiGetCourses() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller().execute(WebConstants.GETCOURSES);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callApiGetQuestionBank() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId("370");
                attribute.setRole("3");

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETQUESTIONBANK);

            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
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
                case WebConstants.GETTOPICS:
                    onResponseGetTopics(object, error);
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
            ((AuthorHostActivity) getActivity()).hideProgress();
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
                    Utils.showToast(responseHandler.getMessage(), getActivity());
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
            ((AuthorHostActivity) getActivity()).hideProgress();
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
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetCourses api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetCourses Exception : " + e.toString());
        }
    }


    private void onResponseGetTopics(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
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
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetTopics api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetTopics Exception : " + e.toString());
        }
    }


    private void onResponseGetQuestionBank(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListQuestions.addAll(responseHandler.getQuestionBank());
                    setQuestionData(arrListQuestions);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
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
        if (getArguments() != null) {
            if (getArguments().containsKey(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS)) {
                ArrayList<Questions> arrListExamQuestions = getArguments().
                        getParcelableArrayList(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS);
                Debug.e(TAG, "THE SIZE OF BANK QUESTION LIST IS" + arrListQuestions.size());
                Debug.e(TAG, "THE SIZE OF EXAM QUESTION LIST IS" + arrListExamQuestions.size());
                updateQuestionStatusAfterSetDataOfExam(arrListExamQuestions);
            }
        }


    }

//    private void filterResults(int subject_id, String topicid) {
//
//        if (arrListQuestions.size() > 0) {
//            for (Data wp : arrListQuestions) {
//                int count = 0;
//                if (wp.getTopicId().equalsIgnoreCase(topicid) && wp.getSubject_id().equalsIgnoreCase(Integer.toString(subject_id))) {
//                    Log.e("filter success", "" + count++);
//                    copylistOfQuestionBank.add(wp);
//                }
//
//            }
//
//            if (copylistOfQuestionBank.size() > 0) {
//                questionBankListAdapter.addAll(copylistOfQuestionBank);
//
//            } else {
//                Toast.makeText(getActivity(), "No Questions Found to Filter", Toast.LENGTH_SHORT).show();
//            }
//
//
//        }
//    }

    @Override
    public void onClick(View v) {
        if (v == tvQuestionlistAddPreview) {

            if (getFragment().getListOfPreviewQuestionsToAdd().size() > 0) {

                Debug.e(TAG, "The size of preview questions is" + getFragment().getListOfPreviewQuestion().size());
                getFragment().addQuestionsToPreviewFragment();
                getFragment().getListOfPreviewQuestionsToAdd().clear();
            } else {
                Utils.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());
            }
        } else if (v == tvQuestionlistAddNewQuestion) {
            getFragment().setDataOnFragmentFlip(null, false, true);
        }
    }

    public void updateViewAfterDeleteInPreviewQuestion(String questionId) {

        for (Questions question : arrListQuestions) {
            if (questionId.equals(question.getQuestionId())) {
                arrListQuestions.get(arrListQuestions.indexOf(question)).setIsQuestionAddedInPreview(false);
                break;
            }
        }
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();
//        int position = arrListQuestions.indexOf(question);
//        Debug.e(TAG, "The question position is:::" + position);
//        if (position != -1) {
//            arrListQuestions.get(position).setIsQuestionAddedInPreview(false);
//        }
//        questionBankListAdapter.addAll(arrListQuestions);
//        questionBankListAdapter.notifyDataSetChanged();


    }

    public void updateQuestionDataAfterEditQuestion(Questions prevQuestionData, Questions updatedQuestionData) {
//        int position = arrListQuestions.indexOf(prevQuestionData);
//        if (position != -1) {
//            arrListQuestions.set(position, updatedQuestionData);
//            questionBankListAdapter.addAll(arrListQuestions);
//            questionBankListAdapter.notifyDataSetChanged();
//        }

        for (Questions questions : arrListQuestions) {
            if (questions.getQuestionId().equals(prevQuestionData.getQuestionId())) {
                updatedQuestionData.setIsQuestionAddedInPreview(true);
                arrListQuestions.set(arrListQuestions.indexOf(questions), updatedQuestionData);
                break;
            }
        }
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();


    }

    public void addQuestionDataAfterAddQuestion(Questions question) {
        question.setIsQuestionAddedInPreview(true);
        arrListQuestions.add(0, question);
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();
    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }

    public void updateQuestionStatusAfterSetDataOfExam(ArrayList<Questions> arrListExamQuestions) {

        try {
            Debug.e(TAG, "The size of arraylist is:::" + arrListExamQuestions.size());
            getFragment().setListOfExamQuestions(arrListExamQuestions);
            for (int i = 0; i < arrListExamQuestions.size(); i++) {
                Debug.e(TAG, "THE EXAM QUESTION ID IS::::" + arrListExamQuestions.get(i).getQuestionId());
                for (int j = 0; j < arrListQuestions.size(); j++) {
                    Debug.e(TAG, "THE QUESTION LIST QUESTION ID IS====" + arrListQuestions.get(j).getQuestionId());

                    if (arrListExamQuestions.get(i).getQuestionId().equals(arrListQuestions.get(j).getQuestionId())) {
                        Debug.e(TAG, "The position is" + j);
                        arrListQuestions.get(j).setIsQuestionAddedInPreview(true);
                        break;
                    }
                }
            }
            questionBankListAdapter.addAll(arrListQuestions);
            questionBankListAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> listOfQuestionId = new ArrayList<String>();


}
