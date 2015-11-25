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
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;

/**
 * Created by c75 on 10/11/15.
 */
public class SubjectiveQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = SubjectiveQuestionsFragment.class.getSimpleName();


    //Views
    View rootview;
    public static RecyclerView rvSubjectiveQuestionList;
    TextView txtExamName;

    public static ResponseHandler responseObjQuestions;

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
    String studentname_from_param = "";
    boolean callEvaluationApiFlag = false;
    public ResponseHandler responseObjectEval;

    public static SubjectiveQuestionsFragment newInstance() {
        SubjectiveQuestionsFragment myStudentsFragment = new SubjectiveQuestionsFragment();
        return myStudentsFragment;
    }

    public SubjectiveQuestionsFragment() {
        // Required empty public constructor
    }

    public SubjectiveQuestionsFragment(String studentid_from_param, String examid_from_param, boolean callEvaluationApiFlag, String studentname_from_param) {
        this.studentid_from_param = studentid_from_param;
        this.examid_from_param = examid_from_param;
        this.callEvaluationApiFlag = callEvaluationApiFlag;
        this.studentname_from_param = studentname_from_param;

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

        callGetSubjectiveQuestionApi();
    }

    private void callGetSubjectiveQuestionApi() {
        try {
            Attribute attribute = new Attribute();
            attribute.setExamId(WebConstants.EXAM_ID_11_SUBJECTIVE);
            new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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
                Attribute attribute = new Attribute();
                attribute.setStudentId(student_id);
                attribute.setExamId(exam_id);

                Log.e("subjective exam evaluation ", "student_id:" + student_id + "examid" + exam_id);

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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
        ResponseHandler responseHandler = (ResponseHandler) object;

        responseObjQuestions = (ResponseHandler) object;

        if (responseHandler.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {

            txtExamName.setText(responseHandler.getExamQuestions().get(0).getExamName());
//            arrayListSubjectiveQuestions.addAll(responseHandler.getData());
//            subjectiveQuestionAdapter.addAll(arrayListSubjectiveQuestions);
            subjectiveQuestionAdapter = new SubjectiveQuestionAdapter(responseHandler, getActivity(), this, null);
            rvSubjectiveQuestionList.setAdapter(subjectiveQuestionAdapter);
            rvSubjectiveQuestionList.setLayoutManager(new LinearLayoutManager(getActivity()));

            if (callEvaluationApiFlag) {
                callAPIStudentEvaluations(studentid_from_param, examid_from_param);
            }
        }
    }

    private void onResponseGetEvaluation(Object object) {

        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {
            if (responseHandler.getExamEvaluation().get(0).getEvaluation().size() != 0) {
                responseObjectEval = responseHandler;

                if (responseObjectEval.getStatus().equalsIgnoreCase(WebConstants.API_STATUS_SUCCESS)) {
                    rlStudentDetails.setVisibility(View.VISIBLE);
                    txt_student_name.setText(studentname_from_param);
                    // imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListStudents.get(position).getProfile_pic(), holder.imgStudentPic, ISMTeacher.options);
                }

                SubjectiveQuestionAdapter subjectiveQuestionAdapter = new SubjectiveQuestionAdapter(SubjectiveQuestionsFragment.responseObjQuestions, getActivity(), this, responseObjectEval);
                SubjectiveQuestionsFragment.rvSubjectiveQuestionList.setAdapter(subjectiveQuestionAdapter);
                subjectiveQuestionAdapter.notifyDataSetChanged();
            }
        } else {
            Debug.i(TAG, "Response :" + WebConstants.GET_EXAM_EVALUATIONS + " :" + responseHandler.getStatus());
        }
    }


}
