package com.ism.author.fragment;

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

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.QuestionBankListAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
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

    Spinner spQuestionlistCourse, spQuestionlistSubject, spQuestionlistTopic;
    List<String> arrListExamType, arrListDefalt;
    List<Data> arrListSubject, arrListCourses, arrListTopic;
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
        spQuestionlistTopic = (Spinner) view.findViewById(R.id.sp_questionlist_topic);

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
        Adapters.setUpSpinner(getActivity(), spQuestionlistCourse, arrListExamType, Adapters.ADAPTER_SMALL);


        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.strtopic));
        Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, arrListDefalt, Adapters.ADAPTER_NORMAL);


        callApiGetQuestionBank();
        callApiGetSubjects();


        spQuestionlistSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrListSubject != null && position > 0) {

                    if (Utility.isOnline(getActivity())) {

                        callApiGetTopics(Integer.parseInt(arrListSubject.get(position - 1).getId()));
                    } else {
                        Utility.toastOffline(getActivity());
                    }
                } else {
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, arrListDefalt, Adapters.ADAPTER_NORMAL);
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
    }

    private void callApiGetSubjects() {

        if (Utility.isOnline(getActivity())) {
            try {
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
        if (Utility.isOnline(getActivity())) {
            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setSubjectId(String.valueOf(subject_id));
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETTOPICS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    private void callApiGetCourses() {

        if (Utility.isOnline(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETCOURSES);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    private void onResponseGetTopics(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    arrListTopic = new ArrayList<Data>();
                    arrListTopic.addAll(responseObj.getData());
                    List<String> topics = new ArrayList<String>();
                    topics.add(getString(R.string.select));
                    for (Data topic : arrListTopic) {
                        topics.add(topic.getTopicName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, topics, Adapters.ADAPTER_NORMAL);
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Adapters.setUpSpinner(getActivity(), spQuestionlistTopic, arrListDefalt, Adapters.ADAPTER_NORMAL);
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetTopics api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetTopics Exception : " + e.toString());
        }
    }


    private void callApiGetQuestionBank() {

        if (Utility.isOnline(getActivity())) {
            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setUserId("370");
                requestObject.setRole(AppConstant.AUTHOR_ROLE_ID + "");
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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


//    private void filterResults(int subject_id, String topicid) {
//
//        if (listOfQuestionBank.size() > 0) {
//            for (Data wp : listOfQuestionBank) {
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
//                ((AddQuestionContainerFragment) mFragment).previewQuestionFragment.
//                        addQuestionsToPreviewFragment(((AddQuestionContainerFragment) mFragment).getListOfPreviewQuestionsToAdd());
                getFragment().addQuestionsToPreviewFragment();
                getFragment().getListOfPreviewQuestionsToAdd().clear();
            } else {
                Utils.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());

            }
        } else if (v == tvQuestionlistAddNewQuestion) {

            getFragment().setQuestionData(null);
            getFragment().setIsSetQuestionData(false);
            getFragment().flipCard();

        }
    }

    public void updateViewAfterDeleteInPreviewQuestion(Data data) {
        int position = listOfQuestionBank.indexOf(data);
        listOfQuestionBank.get(position).setIsQuestionAddedInPreview(false);
        questionBankListAdapter.addAll(listOfQuestionBank);
        questionBankListAdapter.notifyDataSetChanged();
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
