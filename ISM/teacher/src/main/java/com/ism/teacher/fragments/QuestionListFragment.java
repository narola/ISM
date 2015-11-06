package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.QuestionBankListAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.RequestObject;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.ws.WebserviceWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c166 on 31/10/15.
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
    List<Data> arrListSubject, arrListCourses, arrListTopic;
    EditText etQuestionlistSearch;
    TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview;
    RecyclerView rvQuestionlist;
    QuestionBankListAdapter questionBankListAdapter;

    ArrayList<Data> listOfQuestionBank = new ArrayList<Data>();
    ArrayList<Data> copylistOfQuestionBank = new ArrayList<Data>();


    MyTypeFace myTypeFace;
    String subjectName = "";
    int subject_id;


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

                    subject_id = Integer.parseInt(arrListSubject.get(position - 1).getId());
                    subjectName = arrListSubject.get(position - 1).getSubject_name();
                    callApiGetTopics(subject_id);
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
//                    Toast.makeText(getActivity(), "Filter", Toast.LENGTH_SHORT).show();

                    Log.e("sub", "" + subject_id);
                    Log.e("subname", "" + subjectName);
                    Log.e("topics id", "" + arrListTopic.get(position - 1).getId());
                    Log.e("topics name", "" + arrListTopic.get(position - 1).getTopic_name());

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
            for (Data wp : listOfQuestionBank) {
                int count = 0;
                if (wp.getTopicId().equalsIgnoreCase(topicid) && wp.getSubject_id().equalsIgnoreCase(Integer.toString(subject_id))) {
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

        if (Utils.isInternetConnected(getActivity())) {
            try {
                RequestObject getTopicsRequest = new RequestObject();
                getTopicsRequest.setSubjectId(subject_id);
                new WebserviceWrapper(getActivity(), getTopicsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETTOPICS);
            } catch (Exception e) {
                //Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

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
            Utils.showToast(getString(R.string.no_internet), getActivity());
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
            Utils.showToast(getString(R.string.no_internet), getActivity());
        }

    }


    private void callApiGetQuestionBank() {


        if (Utils.isInternetConnected(getActivity())) {
            try {
                RequestObject request = new RequestObject();
                request.setUserId("370");
                request.setRole(AppConstant.AUTHOR_ROLE_ID);
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETQUESTIONBANK);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    public ResponseObject getQuestionBankResponseObject;

    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {
            switch (apiMethodName) {
                case WebConstants.GETSUBJECT:
                    onResponseGetSubject(object);
                    break;

                case WebConstants.GETCOURSES:
                    onResponseGetCourses(object);
                    break;

                case WebConstants.GETQUESTIONBANK:
                    onResponseGetQuestionBank(object);
                    break;
                case WebConstants.GETTOPICS:
                    onResponseGetTopics(object);
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    private void onResponseGetCourses(Object object) {
        ResponseObject callGetCoursesResponseObject = (ResponseObject) object;
        if (callGetCoursesResponseObject.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && callGetCoursesResponseObject != null) {

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
    }

    private void onResponseGetQuestionBank(Object object) {
        getQuestionBankResponseObject = (ResponseObject) object;
        if (getQuestionBankResponseObject.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && getQuestionBankResponseObject != null) {

            listOfQuestionBank.addAll(getQuestionBankResponseObject.getData());
//                    copylistOfQuestionBank.addAll(listOfQuestionBank);
            questionBankListAdapter.addAll(listOfQuestionBank);

            Log.e("list_size_after_api", "" + listOfQuestionBank.size());

        } else {
            Utils.showToast(getQuestionBankResponseObject.getMessage(), getActivity());
        }

    }

    private void onResponseGetTopics(Object object) {

        ResponseObject callGetTopicsResponseObject = (ResponseObject) object;
        arrListTopic = new ArrayList<Data>();
        arrListTopic.addAll(callGetTopicsResponseObject.getData());

        List<String> topics = new ArrayList<String>();
        topics.add(getString(R.string.strtopic));
        for (Data topic : arrListTopic) {
            topics.add(topic.getTopic_name());

        }
        Adapters.setUpSpinner(getActivity(), spQuestionlistExamType, topics, Adapters.ADAPTER_SMALL);
    }

    private void onResponseGetSubject(Object object) {
        ResponseObject callGetSubjectResponseObject = (ResponseObject) object;
        if (callGetSubjectResponseObject.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && callGetSubjectResponseObject != null) {

            arrListSubject = new ArrayList<Data>();
            arrListSubject.addAll(callGetSubjectResponseObject.getData());
            List<String> subjects = new ArrayList<String>();
            subjects.add(getString(R.string.strsubject));
            for (Data subject : arrListSubject) {
                subjects.add(subject.getSubject_name());

            }
            Adapters.setUpSpinner(getActivity(), spQuestionlistSubject, subjects, Adapters.ADAPTER_SMALL);

        } else {
            Utils.showToast(callGetSubjectResponseObject.getMessage(), getActivity());
        }
    }

    @Override
    public void onClick(View v) {

        if (v == tvQuestionlistAddPreview) {

            if (questionBankListAdapter.getListOfPreviewQuestionsToAdd().size() > 0) {

                ((AddQuestionFragmentTeacher) mFragment).previewQuestionFragment.addQuestionsToPreviewFragment(questionBankListAdapter.getListOfPreviewQuestionsToAdd());
                questionBankListAdapter.getListOfPreviewQuestionsToAdd().clear();

            } else {
                Utils.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());

            }


        } else if (v == tvQuestionlistAddNewQuestion) {
            ((AddQuestionFragmentTeacher) mFragment).flipCard();

        }

    }

    public void updateViewAfterDeleteInPreviewQuestion(Data data) {

        //Log.e("list_sizexzxZx", "" + getQuestionBankResponseObject.getData().size());
        // Log.e("list_size", "" + listOfQuestionBank.size());

        if (listOfQuestionBank.size() > 0) {
            int position = listOfQuestionBank.indexOf(data);
            listOfQuestionBank.get(position).setIsQuestionAddedInPreview(false);
            questionBankListAdapter.addAll(listOfQuestionBank);
            ((AddQuestionFragmentTeacher) mFragment).previewQuestionFragment.listOfPreviewQuestions.remove(data);
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


}
