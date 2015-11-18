package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.SubjectiveQuestionAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.model.RequestObject;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.WebserviceWrapper;

/**
 * Created by c75 on 10/11/15.
 */
public class SubjectiveQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = SubjectiveQuestionsFragment.class.getSimpleName();


    //Views
    View rootview;
    public static RecyclerView rvSubjectiveQuestionList;
    TextView txtExamName;

    public static ResponseObject responseObjQuestions;

    //Adapters
    SubjectiveQuestionAdapter subjectiveQuestionAdapter;


    //Student details part
    public static RelativeLayout rlStudentDetails;
    public static CircleImageView img_student_pic;
    public static TextView txt_student_name, txt_student_rollno;
    public static ImageView img_online;


    //test
    String studentid_from_param = "";
    String examid_from_param = "";
    boolean callEvaluationApiFlag = false;
    public ResponseObject responseObjectEval;

    public static SubjectiveQuestionsFragment newInstance() {
        SubjectiveQuestionsFragment myStudentsFragment = new SubjectiveQuestionsFragment();
        return myStudentsFragment;
    }

    public SubjectiveQuestionsFragment() {
        // Required empty public constructor
    }

    public SubjectiveQuestionsFragment(String studentid_from_param, String examid_from_param, boolean callEvaluationApiFlag) {
        this.studentid_from_param = studentid_from_param;
        this.examid_from_param = examid_from_param;
        this.callEvaluationApiFlag = callEvaluationApiFlag;

        Log.i("ExamSubjectiveDetailFragment to Subjec", "call evaluation api=" + callEvaluationApiFlag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_subjective_questions, container, false);

        initGlobal(rootview);

        return rootview;
    }

    private void initGlobal(View rootview) {

        //static
        rlStudentDetails = (RelativeLayout) rootview.findViewById(R.id.rl_student_details);
        img_student_pic = (CircleImageView) rootview.findViewById(R.id.img_student_pic);
        img_online = (ImageView) rootview.findViewById(R.id.img_student_pic);
        txt_student_name = (TextView) rootview.findViewById(R.id.txt_student_name);
        txt_student_rollno = (TextView) rootview.findViewById(R.id.txt_student_rollno);

        txtExamName = (TextView) rootview.findViewById(R.id.txt_exam_name);
        rvSubjectiveQuestionList = (RecyclerView) rootview.findViewById(R.id.rv_subjective_question_list);

        callGetSubjectionQuestionApi();
    }

    private void callGetSubjectionQuestionApi() {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setExamId("11");
            new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebConstants.GET_EXAM_QUESTIONS);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    private void callAPIStudentEvaluations(String student_id, String exam_id) {
        Log.i("ExamSubjectiveDetailFragment to Subjective", "called evaluation api========");
        try {
            if (Utility.isInternetConnected(getActivity())) {
                ((TeacherHostActivity) getActivity()).startProgress();
                RequestObject requestObject = new RequestObject();
                requestObject.setStudentId(student_id);
                requestObject.setExamId(exam_id);

                Log.e("subjective exam evaluation ", "student_id:" + student_id + "examid" + exam_id);

                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_EVALUATIONS);
            } else {
                Utility.showToast(getActivity().getString(R.string.strnetissue), getActivity());
            }

        } catch (Exception e) {
            Debug.i(TAG, "callAPIStudentEvaluations Exceptions: " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(int api_code, Object object, Exception error) {
        try {
            switch (api_code) {
                case WebConstants.GET_EXAM_QUESTIONS:
                    onResponseMySubjectiveQuestions(object);
                    break;
                case WebConstants.GET_EXAM_EVALUATIONS:
                    onResponseGetEvaluation(object);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }


    private void onResponseMySubjectiveQuestions(Object object) {
        ResponseObject responseObj = (ResponseObject) object;

        responseObjQuestions = (ResponseObject) object;

        if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {

            txtExamName.setText(responseObj.getData().get(0).getExamName());
//            arrayListSubjectiveQuestions.addAll(responseObj.getData());
//            subjectiveQuestionAdapter.addAll(arrayListSubjectiveQuestions);
            subjectiveQuestionAdapter = new SubjectiveQuestionAdapter(responseObj, getActivity(), this, null);
            rvSubjectiveQuestionList.setAdapter(subjectiveQuestionAdapter);
            rvSubjectiveQuestionList.setLayoutManager(new LinearLayoutManager(getActivity()));

            if (callEvaluationApiFlag) {
                callAPIStudentEvaluations(studentid_from_param, examid_from_param);
            }
        }
    }

    private void onResponseGetEvaluation(Object object) {

        ResponseObject responseObject = (ResponseObject) object;
        if (responseObject.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {
            if (responseObject.getData().get(0).getArrayListEvaluation().size() != 0) {
                responseObjectEval = responseObject;

                if (responseObjectEval.getStatus().equalsIgnoreCase(WebConstants.API_STATUS_SUCCESS)) {
                    SubjectiveQuestionsFragment.rlStudentDetails.setVisibility(View.VISIBLE);
                    SubjectiveQuestionsFragment.txt_student_name.setText(responseObject.getData().get(0).getStudentName());
                    // imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListStudents.get(position).getProfile_pic(), holder.imgStudentPic, ISMTeacher.options);
                }

                SubjectiveQuestionAdapter subjectiveQuestionAdapter = new SubjectiveQuestionAdapter(SubjectiveQuestionsFragment.responseObjQuestions, getActivity(), this, responseObjectEval);
                SubjectiveQuestionsFragment.rvSubjectiveQuestionList.setAdapter(subjectiveQuestionAdapter);
                subjectiveQuestionAdapter.notifyDataSetChanged();
            }
        } else {
            Debug.i(TAG, "Response :" + WebConstants.GET_EXAM_EVALUATIONS + " :" + responseObject.getStatus());
        }
    }


}
