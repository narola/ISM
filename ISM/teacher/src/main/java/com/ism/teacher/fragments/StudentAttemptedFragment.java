package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.ObjectiveQuestionsAdapter;
import com.ism.teacher.adapters.StudentAttemptedAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.model.Data;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;

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
    public static ResponseHandler responseObjQuestions;
    public static List<String> questionsID = new ArrayList<>();


    //Test
    Context context;
    public String examid_from_param = "";
    public String studentid_from_param = "";
    public boolean callEvaluationApiFlag = false;
    ResponseHandler responseObjectEval;

    public static StudentAttemptedFragment newInstance() {
        StudentAttemptedFragment fragment = new StudentAttemptedFragment();
        return fragment;
    }

    public StudentAttemptedFragment() {
        // Required empty public constructor
    }


    public StudentAttemptedFragment(Context context, String examid, String studentid, boolean callEvaluationApiFlag) {
        this.context = context;
        this.examid_from_param = examid;
        this.studentid_from_param = studentid;
        this.callEvaluationApiFlag = callEvaluationApiFlag;
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
        Attribute attribute = new Attribute();
        attribute.setExamId("9");
        attribute.setUserId("340");
        attribute.setRole("3");

        callapigetExamSubmission(attribute);
    }

    /**
     * Web Service call for examsubmisison(student attempted)
     */

    private void callapigetExamSubmission(Attribute attribute) {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                // ((AuthorHostActivity) getActivity()).startProgress();
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_EXAM_SUBMISSION);
            } catch (Exception e) {
                Log.i(TAG, "callApi GET_EXAM_QUESTIONS ::" + e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    /**
     * Web Service call for exam question called after student attempted response
     */


    private void callapigetexamquestions(Attribute attribute) {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                // ((AuthorHostActivity) getActivity()).startProgress();
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_QUESTIONS);
            } catch (Exception e) {
                Log.i(TAG, "callApi GET_EXAM_QUESTIONS ::" + e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    /**
     * Web Service call for exam evaluation( called if flag is true for evaluation)
     * otherwise evaluation will be called from adapter of student attempted
     */

    private void callAPIStudentEvaluations(String studentId, String examId) {
        try {
            if (Utility.isOnline(context)) {
                ((TeacherHostActivity) context).startProgress();
                Attribute attribute = new Attribute();
                attribute.setStudentId(studentId);
                attribute.setExamId(examId);
                new WebserviceWrapper(context, attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_EVALUATIONS);
            } else {
                Utility.toastOffline(context);
            }

        } catch (Exception e) {
            Debug.i(TAG, "callAPIStudentEvaluations Exceptions: " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        ((TeacherHostActivity) getActivity()).stopProgress();
        try {

            switch (API_METHOD) {
                case WebConstants.GET_ALL_EXAM_SUBMISSION:
                    onResponseGetAllStudentAttempted(object);
                    break;
                case WebConstants.GET_EXAM_QUESTIONS:
                    onResponseGetExamQuestions(object);
                    break;
                case WebConstants.GET_EXAM_EVALUATIONS:
                    onResponseGetEvaluation(object);
                    break;
            }

        } catch (Exception e) {
            Debug.i(TAG, "Exceptions ::" + e.getLocalizedMessage());
        }

    }


    private void onResponseGetAllStudentAttempted(Object object) {
        ResponseHandler resObjSubmisssion = (ResponseHandler) object;
        if (resObjSubmisssion.getStatus().equals(ResponseHandler.SUCCESS)) {
            // ((AuthorHostActivity)getActivity()).stopProgress();
            if (resObjSubmisssion.getData().size() != 0) {

                Debug.i(TAG, "Arraylist of student attempted  ::" + resObjSubmisssion);

                studentAttemptedAdapter = new StudentAttemptedAdapter(resObjSubmisssion, getActivity(), this);
                rvList.setAdapter(studentAttemptedAdapter);
                rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
                Attribute attribute = new Attribute();
                attribute.setExamId("3");
                //requestObject.setStudentId("202");

                // ((AuthorHostActivity) getActivity()).startProgress();
                callapigetexamquestions(attribute);

            }

        } else if (resObjSubmisssion.getStatus().equals(ResponseHandler.FAILED)) {
            Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
        }

    }

    private void onResponseGetExamQuestions(Object object) {
        ResponseHandler resObjQuestions = (ResponseHandler) object;
        if (resObjQuestions.getStatus().equals(ResponseHandler.SUCCESS)) {
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
                ExamObjectiveDetailFragment.txtExamDateValue.setText(Utility.getFormattedDate("dd-MMM-yyyy", responseObjQuestions.getData().get(0).getCreated_date()));
                ExamObjectiveDetailFragment.txtExamName.setText(responseObjQuestions.getData().get(0).getExamName());
                questionsID = new ArrayList<>();
                for (int i = 0; i < responseObjQuestions.getData().get(0).getQuestions().size(); i++) {
                    questionsID.add(responseObjQuestions.getData().get(0).getQuestions().get(i).getQuestionId());
                    Debug.i(TAG, "Q.ID :" + questionsID.get(i));
                }

                // call evaluation api only if flag is true from examassignadapter-->Host->Objectivedetail->StudentAttempted
                if (callEvaluationApiFlag) {
                    Log.i(TAG, "student_id:" + studentid_from_param + "===exam_id" + examid_from_param);
                    //      callAPIStudentEvaluations(studentid_from_param,examid_from_param);

                    //static
                    callAPIStudentEvaluations(WebConstants.STUDENT_ID_202_OBJECCTIVE, WebConstants.EXAM_ID_9_OBJECTIVE);
                }


            }

        } else if (resObjQuestions.getStatus().equals(ResponseHandler.FAILED)) {
            Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
        }
    }

    private void onResponseGetEvaluation(Object object) {
        ResponseHandler responseObject = (ResponseHandler) object;
        if (responseObject.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {
            if (responseObject.getData().get(0).getArrayListEvaluation().size() != 0) {
                responseObjectEval = responseObject;
                ObjectiveQuestionsAdapter objectiveQuestionsAdapter = new ObjectiveQuestionsAdapter(StudentAttemptedFragment.responseObjQuestions, context, this, responseObjectEval);
                ExamObjectiveDetailFragment.rvList.setAdapter(objectiveQuestionsAdapter);
                objectiveQuestionsAdapter.notifyDataSetChanged();
            }
        } else {
            Debug.i(TAG, "Response :" + WebConstants.GET_EXAM_EVALUATIONS + " :" + responseObject.getStatus());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_STUDENT_ATTEMPTED);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_STUDENT_ATTEMPTED);
                Debug.i(TAG, "detach");
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


}
