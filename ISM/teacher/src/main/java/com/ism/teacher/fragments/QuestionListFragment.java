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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.adapters.QuestionBankListAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Courses;
import com.ism.teacher.ws.model.Questions;
import com.ism.teacher.ws.model.Subjects;
import com.ism.teacher.ws.model.Topics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This fragment displays the list of all questions of question bank.
 * We can filter these questions based on subjective,objective filter type
 * Filter questions based on subject type and do sorting on the latest list after filter.
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

    private Spinner spQuestionlistFiltertype, spQuestionlistSubject, spQuestionlistTopic;
    private List<String> arrListExamType, arrListDefalt;
    private List<Subjects> arrListSubject;
    private List<Courses> arrListCourses;
    private List<Topics> arrListTopic;
    private EditText etSearchQuestions;
    private TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview, tvNoQuestions;
    private RecyclerView rvQuestionlist;
    private RelativeLayout rlSortQuestionBank;

    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    public ArrayList<Questions> copylistOfQuestionBank = new ArrayList<Questions>();
    public ArrayList<Questions> latestlistOfQuestionBank = new ArrayList<Questions>();

    private ImageView imgSearchQuestions;


    public static final String MCQ_FORMAT = "MCQ";
    public static final String DESCRIPTIVE_FORMAT = "descriptive";
    public static final int SORT_UP = 1, SORT_DOWN = 2;

    public boolean isSorted = false;
    private QuestionBankListAdapter questionBankListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionlist_teacher, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {

        tvNoQuestions = (TextView) view.findViewById(R.id.tv_no_questions);
        tvNoQuestions.setTypeface(Global.myTypeFace.getRalewayBold());
        rlSortQuestionBank = (RelativeLayout) view.findViewById(R.id.rl_sort_question_bank);
        rlSortQuestionBank.setOnClickListener(this);

        imgSearchQuestions = (ImageView) view.findViewById(R.id.img_search_questions);

        spQuestionlistFiltertype = (Spinner) view.findViewById(R.id.sp_questionlist_filtertype);
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


        etSearchQuestions.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvQuestionlistTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvQuestionlistAddPreview.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setOnClickListener(this);
        tvQuestionlistAddPreview.setOnClickListener(this);


        arrListExamType = new ArrayList<String>();
//        arrListExamType.add(getString(R.string.strexamtype));
        arrListExamType = Arrays.asList(getResources().getStringArray(R.array.filtertype));
        Adapters.setUpSpinner(getActivity(), spQuestionlistFiltertype, arrListExamType, Adapters.ADAPTER_SMALL);


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
                    etSearchQuestions.setText("");
                    questionBankListAdapter.filter("");


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

                if (s.length() > 0) {
                    questionBankListAdapter.filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        spQuestionlistSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (arrListSubject != null && position > 0) {

                    /*this is to check that question are of this exam*/
                    if (arrListSubject.get(position - 1).getId().equals(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID))) {
                        questionBankListAdapter.canAddToPreview = true;
                    } else {
                        questionBankListAdapter.canAddToPreview = false;
                    }
                    if (arrListQuestions.size() > 0 && arrListSubject != null) {
                        filterResultsBasedOnSubjects(Integer.parseInt(arrListSubject.get(position - 1).getId()));
                    }
                    if (Utility.isConnected(getActivity())) {
                        callApiGetTopics(Integer.parseInt(arrListSubject.get(position - 1).getId()));
                    } else {
                        Utility.toastOffline(getActivity());
                    }


                } else {
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, arrListDefalt, Adapters.ADAPTER_SMALL);
//                    clearFilters();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spQuestionlistTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (arrListQuestions.size() > 0 && arrListTopic != null && position > 0) {

                    if (position == 1) {
                        filterResultsBasedOnSubjects(spQuestionlistSubject.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                                get(spQuestionlistSubject.getSelectedItemPosition() - 1).getId()) : 0);
                    } else {
                        filterResults(spQuestionlistSubject.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                                get(spQuestionlistSubject.getSelectedItemPosition() - 1).getId()) : 0, arrListTopic.get(position - 2).getId());
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spQuestionlistFiltertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Debug.e(TAG, "position is" + position);

                switch (position) {
                    case 1:
                        clearSubjectBasedFilters();
                        break;
                    case 2:
                        filterResultsForExamType(DESCRIPTIVE_FORMAT);
                        break;
                    case 3:
                        filterResultsForExamType(MCQ_FORMAT);
                        break;
                    case 4:
                        //filterResultsForFavorite();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        callApiGetQuestionBank();
        callApiGetSubjects();
    }

    private void clearSubjectBasedFilters() {
        latestlistOfQuestionBank.clear();
        latestlistOfQuestionBank.addAll(copylistOfQuestionBank);
        questionBankListAdapter.addAll(latestlistOfQuestionBank);
    }

    private void clearFilters() {
        questionBankListAdapter.addAll(arrListQuestions);
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
                ((TeacherHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_SUBJECT);
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
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setSubjectId(String.valueOf(subject_id));
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_TOPICS);
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
                ((TeacherHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(),new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller().execute(WebConstants.GET_COURSES);
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
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.USER_ID_370);
                attribute.setRole(WebConstants.TEACHER_ROLE_ID);

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_QUESTION_BANK);

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
                case WebConstants.GET_SUBJECT:
                    onResponseGetSubjects(object, error);
                    break;
                case WebConstants.GET_COURSES:
                    onResponseGetCourses(object, error);
                    break;
                case WebConstants.GET_TOPICS:
                    onResponseGetTopics(object, error);
                    break;
                case WebConstants.GET_QUESTION_BANK:
                    onResponseGetQuestionBank(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetSubjects(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
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

                    if (getBundleArguments() != null) {
                        Debug.e(TAG, "THE SUBJECT NAME IS" + getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME));
                        spQuestionlistSubject.setSelection(subjects.indexOf(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME)));
                    }


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

    private void onResponseGetCourses(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
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
                    Adapters.setUpSpinner(getActivity(), spQuestionlistFiltertype, courses, Adapters.ADAPTER_SMALL);

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


    private void onResponseGetTopics(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListTopic = new ArrayList<Topics>();
                    arrListTopic.addAll(responseHandler.getTopics());
                    List<String> topics = new ArrayList<String>();
                    topics.add(getString(R.string.select));
                    topics.add(getString(R.string.strall));
                    for (Topics topic : arrListTopic) {
                        topics.add(topic.getTopicName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, topics, Adapters.ADAPTER_SMALL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, arrListDefalt, Adapters.ADAPTER_SMALL);
                    Utility.showToast(responseHandler.getMessage(), getActivity());
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
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListQuestions.addAll(responseHandler.getQuestionBanks());
                    setQuestionData(arrListQuestions);

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
        filterResultsBasedOnSubjects(Integer.valueOf(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID)));
        if (getBundleArguments() != null) {
            if (getBundleArguments().containsKey(ObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS)) {
                ArrayList<Questions> arrListExamQuestions = getBundleArguments().
                        getParcelableArrayList(ObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS);
                updateQuestionStatusAfterSetDataOfExam(arrListExamQuestions);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_questionlist_add_preview:
                if (getFragment().getListOfPreviewQuestionsToAdd().size() > 0) {

                    Debug.e(TAG, "The size of preview questions is" + getFragment().getListOfPreviewQuestion().size());
                    getFragment().addQuestionsToPreviewFragment();
                    getFragment().getListOfPreviewQuestionsToAdd().clear();
                } else {
                    Utility.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());
                }
                break;

            case R.id.tv_questionlist_add_new_question:
                getFragment().setDataOnFragmentFlip(null, false, true);
                break;

            case R.id.rl_sort_question_bank:
                if (!isSorted) {
                    performSorting(SORT_DOWN);
                    isSorted = true;
                } else {
                    performSorting(SORT_UP);
                    isSorted = false;
                }
                break;
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
        filterResultsAfterAddEditDelete();
    }

    /*this method is to retain the filter status after question added,deleted and updated*/
    private void filterResultsAfterAddEditDelete() {
        if (spQuestionlistSubject.getSelectedItemPosition() > 0) {
            filterResultsBasedOnSubjects(Integer.valueOf(arrListSubject.get(spQuestionlistSubject.getSelectedItemPosition() - 1).getId()));

            int position = spQuestionlistFiltertype.getSelectedItemPosition();

            //  Debug.e(TAG+"============================","filter spinner item after we are coming from add/edit question"+spQuestionlistFiltertype.getSelectedItem().toString());
            if (position > 0) {
                switch (position) {
                    case 1:
                        clearSubjectBasedFilters();
                        break;
                    case 2:
                        filterResultsForExamType(DESCRIPTIVE_FORMAT);
                        break;

                    case 3:
                        filterResultsForExamType(MCQ_FORMAT);
                        break;
                    case 4:
                        //for favorites
                        break;
                }
            }

        }

    }

    /*this method is for edit question data after it successfully edited */
    public void updateQuestionDataAfterEditQuestion(Questions prevQuestionData, Questions updatedQuestionData, Boolean isChecked) {
        for (Questions questions : arrListQuestions) {
            if (questions.getQuestionId().equals(prevQuestionData.getQuestionId())) {
                if (isChecked) {
                    updatedQuestionData.setIsQuestionAddedInPreview(true);
                }
                arrListQuestions.set(arrListQuestions.indexOf(questions), updatedQuestionData);
                break;
            }
        }
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();
        isSorted = false;

        filterResultsAfterAddEditDelete();
    }


    public void addQuestionDataAfterAddQuestion(Questions question) {
        question.setIsQuestionAddedInPreview(true);
        arrListQuestions.add(0, question);
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();
        filterResultsAfterAddEditDelete();
    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }

    public void updateQuestionStatusAfterSetDataOfExam(ArrayList<Questions> arrListExamQuestions) {
        try {
            getFragment().setListOfExamQuestions(arrListExamQuestions);
            for (int i = 0; i < arrListExamQuestions.size(); i++) {
               // Debug.e(TAG, "THE EXAM QUESTION ID IS::::" + arrListExamQuestions.get(i).getQuestionId());
                for (int j = 0; j < arrListQuestions.size(); j++) {
                   // Debug.e(TAG, "THE QUESTION LIST QUESTION ID IS====" + arrListQuestions.get(j).getQuestionId());
                    if (arrListExamQuestions.get(i).getQuestionId().equals(arrListQuestions.get(j).getQuestionId())) {
                     //   Debug.e(TAG, "The position of exam question in question bank list is" + j);
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

    /**
     * Perform sorting always on latestlistOfQuestionBank to sort the latest list after filter.
     *
     * @param typeOfSort=SORT_UP or SORT_DOWN
     */

    private void performSorting(int typeOfSort) {

        //handling for sorting questions based on subjective and objective
        if (latestlistOfQuestionBank.size() > 0) {
            if (typeOfSort == SORT_UP) {
                Collections.sort(latestlistOfQuestionBank);
            } else {
                Collections.sort(latestlistOfQuestionBank, Collections.reverseOrder());
            }
            questionBankListAdapter.addAll(latestlistOfQuestionBank);
            questionBankListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * This method handles two types of filter.
     * 1.Based on subject id and topic id
     * <p/>
     * 1 A.first in only subjectid based filter ,we reach from edit exam(assignment exam and automatically we see the question bank
     * filtered based on the subjectid passed from param.
     * Now we copied the result into copylist .
     * and at end copied whole copy list into latestQuestionbank list to handle further filter based on that current list.
     * <p/>
     */

    private void filterResults(int subjectId, String topicId) {
        copylistOfQuestionBank.clear();
        latestlistOfQuestionBank.clear();

        if (arrListQuestions.size() > 0) {
            int count = 0;
            for (Questions wp : arrListQuestions) {
                //filter based on subject id and topic id (based on subjects)
                if (topicId != null && !topicId.equalsIgnoreCase("")) {
                    if (wp.getTopicId().equalsIgnoreCase(topicId) && wp.getSubjectId().equalsIgnoreCase(Integer.toString(subjectId))) {
                        //Debug.e(TAG + "filter success", "" + count++);
                        copylistOfQuestionBank.add(wp);
                    }
                }
            }
            latestlistOfQuestionBank.addAll(copylistOfQuestionBank);

            if (latestlistOfQuestionBank.size() > 0) {
                questionBankListAdapter.addAll(latestlistOfQuestionBank);
                hideEmptyText();
            } else {
                questionBankListAdapter.addAll(latestlistOfQuestionBank);
//                Toast.makeText(getActivity(), "No Questions Found to Filter", Toast.LENGTH_SHORT).show();
                showEmptyText();
            }

        }
    }

    /**
     * We already got subjectid based all questions in question bank(because of edit assignment).
     * If user selects any subject from spinner then it handles the filter.
     */

    private void filterResultsBasedOnSubjects(int subjectId) {
        copylistOfQuestionBank.clear();
        latestlistOfQuestionBank.clear();
        if (arrListQuestions.size() > 0) {
            int count = 0;
            for (Questions wp : arrListQuestions) {
                if (wp.getSubjectId().equalsIgnoreCase(Integer.toString(subjectId))) {
                    //    Debug.e(TAG + "filter success", "" + count++);
                    copylistOfQuestionBank.add(wp);
                }

            }

            latestlistOfQuestionBank.addAll(copylistOfQuestionBank);

            if (latestlistOfQuestionBank.size() > 0) {
                questionBankListAdapter.addAll(latestlistOfQuestionBank);
                hideEmptyText();
            } else {
                questionBankListAdapter.addAll(latestlistOfQuestionBank);
//                Toast.makeText(getActivity(), "No Questions Found to Filter", Toast.LENGTH_SHORT).show();
                showEmptyText();
            }
        }
    }

    private void filterResultsForExamType(String examtype) {
        // Debug.e(TAG, "examtype is:" + examtype);

        latestlistOfQuestionBank.clear();
        if (copylistOfQuestionBank.size() > 0) {
            for (Questions wp : copylistOfQuestionBank) {
                //filter based on only exam type
                if (wp.getQuestionFormat().equalsIgnoreCase(examtype)) {
                    latestlistOfQuestionBank.add(wp);
                }

            }
            if (latestlistOfQuestionBank.size() > 0) {
                //  Debug.e(TAG + "results after filter for exam type:" + examtype, "" + latestlistOfQuestionBank.size());
                questionBankListAdapter.addAll(latestlistOfQuestionBank);
                hideEmptyText();
            } else {
                questionBankListAdapter.addAll(latestlistOfQuestionBank);
//                Toast.makeText(getActivity(), "No Questions Found to Filter", Toast.LENGTH_SHORT).show();
                showEmptyText();
            }

        }

    }

    public void showEmptyText() {
        tvNoQuestions.setVisibility(View.VISIBLE);
        rvQuestionlist.setVisibility(View.INVISIBLE);
    }

    public void hideEmptyText() {
        tvNoQuestions.setVisibility(View.GONE);
        rvQuestionlist.setVisibility(View.VISIBLE);
    }
    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

}
