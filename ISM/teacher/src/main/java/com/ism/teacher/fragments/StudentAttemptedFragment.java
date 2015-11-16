package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHomeActivity;
import com.ism.teacher.adapters.ObjectiveQuestionsAdapter;
import com.ism.teacher.adapters.StudentAttemptedAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.RequestObject;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.ws.WebserviceWrapper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by c162 on 04/11/15.
 */
public class StudentAttemptedFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {
    private static final String TAG = StudentAttemptedFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;
    private RecyclerView rvList;
    private ArrayList<Data> arrayList = new ArrayList<>();
    private StudentAttemptedAdapter studentAttemptedAdapter;
    private ObjectiveQuestionsAdapter objectiveQuestionsAdapter;
    public static ResponseObject responseObjQuestions;
    public static List<String> questionsID=new ArrayList<>();
    public static StudentAttemptedFragment newInstance() {
        StudentAttemptedFragment fragment = new StudentAttemptedFragment();
        return fragment;
    }

    public StudentAttemptedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_attempted, container, false);

        initGlobal();
        return view;

    }

    private void initGlobal() {

        rvList = (RecyclerView) view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        RequestObject requestObject = new RequestObject();
        requestObject.setExamId("9");
        requestObject.setUserId("340");
        requestObject.setRole(Integer.parseInt("3"));
        // Debug.i(TAG, "Request student attemted list : " ));
     //
     //   ((AuthorHostActivity) getActivity()).startProgress();
        new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                .execute(WebConstants.GET_ALL_EXAM_SUBMISSION);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_STUDENT_ATTEMPTED);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_STUDENT_ATTEMPTED);
                Debug.i(TAG, "detach");
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void loadFragment(int fragment) {
//        switch (fragment) {
//            case FRAGMENT_TRIAL:
//                currentFragment = fragment;
//                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL);
//                break;
//            case FRAGMENT_EXAM_OBJECTIVE_DETAILS:
//                currentFragment = fragment;
//                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_EXAM_OBJECTIVE_DETAILS);
//                break;
//        }
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
       // ((AuthorHostActivity) getActivity()).stopProgress();

//        Debug.i(TAG, "Response of student attempted  ::" + responseObject.getMessage());
//        Debug.i(TAG, "Response of student attempted  ::" + responseObject.getStatus());
//        Debug.i(TAG, "Response of student attempted  ::" + responseObject.getData().get(0).getExamID());
        try {
            if (API_METHOD == WebConstants.GET_ALL_EXAM_SUBMISSION) {
                ResponseObject resObjSubmisssion = (ResponseObject) object;
                if (resObjSubmisssion.getStatus().equals(ResponseObject.SUCCESS)) {
                    // ((AuthorHostActivity)getActivity()).stopProgress();
                    if (resObjSubmisssion.getData().size() != 0) {

                        Debug.i(TAG, "Arraylist of student attempted  ::" + resObjSubmisssion);

                        studentAttemptedAdapter = new StudentAttemptedAdapter(resObjSubmisssion, getActivity(), this);
                        rvList.setAdapter(studentAttemptedAdapter);
                        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        RequestObject requestObject = new RequestObject();
                        requestObject.setExamId("3");
                        //requestObject.setStudentId("202");

                       // ((AuthorHostActivity) getActivity()).startProgress();
                        callapigetexamquestions(requestObject);

                    }

                } else if (resObjSubmisssion.getStatus().equals(ResponseObject.FAILED)) {
                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
                }
            }
            else if (API_METHOD == WebConstants.GET_EXAM_QUESTIONS) {
                ResponseObject resObjQuestions = (ResponseObject) object;
                if (resObjQuestions.getStatus().equals(ResponseObject.SUCCESS)) {
                    if (resObjQuestions.getData().size() != 0) {
                        responseObjQuestions = resObjQuestions;
                        // Debug.i(TAG, "Arraylist of Questions  ::" + responseObject.getData().get(0).getEvaluations());

                        objectiveQuestionsAdapter = new ObjectiveQuestionsAdapter(responseObjQuestions, getActivity(), this, null);
                        ExamObjectiveDetailFragment.rvList.setAdapter(objectiveQuestionsAdapter);
                        ExamObjectiveDetailFragment.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        ExamObjectiveDetailFragment.txtBookNameValue.setText(responseObjQuestions.getData().get(0).getBookName());
                        ExamObjectiveDetailFragment.txtExamTypeName.setText(responseObjQuestions.getData().get(0).getExam_mode());
                        ExamObjectiveDetailFragment.txtClassName.setText(responseObjQuestions.getData().get(0).getClass_name());
//                        String examDate = responseObjQuestions.getData().get(0).getCreated_date();
                        ExamObjectiveDetailFragment.txtExamDateValue.setText(Utility.getFormattedDate("dd-MMM-yyyy",responseObjQuestions.getData().get(0).getCreated_date()));
                        ExamObjectiveDetailFragment.txtExamName.setText(responseObjQuestions.getData().get(0).getExamName());
                        questionsID=new ArrayList<>();
                        for(int i=0;i<responseObjQuestions.getData().get(0).getQuestions().size();i++){
                            questionsID.add(responseObjQuestions.getData().get(0).getQuestions().get(i).getQuestionId());
                            Debug.i(TAG,"Q.ID :" +questionsID.get(i));
                        }


                    }

                } else if (resObjQuestions.getStatus().equals(ResponseObject.FAILED)) {
                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            Debug.i(TAG, "Exceptions ::" + e.getLocalizedMessage());
        }

    }

    private void callapigetexamquestions(RequestObject requestObject) {

        if (Utility.isInternetConnected(getActivity())) {
            try {
               // ((AuthorHostActivity) getActivity()).startProgress();
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_QUESTIONS);
            } catch (Exception e) {
                Log.i(TAG, "callApi GET_EXAM_QUESTIONS ::" + e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }

    }
}
