package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.adapters.GetObjectiveAssignmentQuestionsAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.ExamQuestions;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * At top this frag displays book name,class and assignment related details.
 * This fragment contains the list of objective questions list below.
 */
public class GetObjectiveAssignmentQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = GetObjectiveAssignmentQuestionsFragment.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;

    private TextView tvObjectiveAssignmentSubject, tvObjectiveAssignmentClass, tvObjectiveAssignmentNo, tvObjectiveAssignmentTitle,
            tvObjectiveAssignmentDateTitle, tvObjectiveAssignmentDate;
    private ImageView imgEditExam, imgCopyExam;

    private RecyclerView rvGetObjectiveAssignmentQuestionslist;
    private GetObjectiveAssignmentQuestionsAdapter getObjectiveAssignmentQuestionsAdapter;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();

    ResponseHandler responseObjGetAllExamQuestions = null;

    public static String ARG_ARR_LIST_QUESTIONS = "arrListQuestions";
    public static String ARG_EXAM_TYPE = "examType";


    public static GetObjectiveAssignmentQuestionsFragment newInstance(Bundle bundleArguments) {
        GetObjectiveAssignmentQuestionsFragment getObjectiveAssignmentQuestionsFragment = new GetObjectiveAssignmentQuestionsFragment();
        getObjectiveAssignmentQuestionsFragment.setArguments(bundleArguments);
        return getObjectiveAssignmentQuestionsFragment;
    }

    public GetObjectiveAssignmentQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exam_objective_details, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        tvObjectiveAssignmentSubject = (TextView) view.findViewById(R.id.tv_objective_assignment_subject);
        tvObjectiveAssignmentClass = (TextView) view.findViewById(R.id.tv_objective_assignment_class);
        tvObjectiveAssignmentNo = (TextView) view.findViewById(R.id.tv_objective_assignment_no);
        tvObjectiveAssignmentTitle = (TextView) view.findViewById(R.id.tv_objective_assignment_title);
        tvObjectiveAssignmentDateTitle = (TextView) view.findViewById(R.id.tv_objective_assignment_date_title);
        tvObjectiveAssignmentDate = (TextView) view.findViewById(R.id.tv_objective_assignment_date);

        imgEditExam = (ImageView) view.findViewById(R.id.img_edit_exam);
        imgCopyExam = (ImageView) view.findViewById(R.id.img_copy_exam);

        tvObjectiveAssignmentSubject.setTypeface(myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentClass.setTypeface(myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentNo.setTypeface(myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentTitle.setTypeface(myTypeFace.getRalewayBold());
        tvObjectiveAssignmentDateTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentDate.setTypeface(myTypeFace.getRalewayRegular());

        rvGetObjectiveAssignmentQuestionslist = (RecyclerView) view.findViewById(R.id.rv_getObjective_assignment_questionslist);
        getObjectiveAssignmentQuestionsAdapter = new GetObjectiveAssignmentQuestionsAdapter(getActivity());
        rvGetObjectiveAssignmentQuestionslist.setAdapter(getObjectiveAssignmentQuestionsAdapter);
        rvGetObjectiveAssignmentQuestionslist.setLayoutManager(new LinearLayoutManager(getActivity()));

        callApiGetExamQuestions();

        imgEditExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExamQuestions();
            }
        });

        imgCopyExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExamQuestions();
            }
        });


    }

    private void setExamQuestions() {

        if (responseObjGetAllExamQuestions != null) {
            getArguments().putParcelableArrayList(ARG_ARR_LIST_QUESTIONS, arrListQuestions);
            getArguments().putString(ARG_EXAM_TYPE, getString(R.string.strobjective));

//            ((AuthorHostActivity) getActivity()).loadFragmentInRightContainer(
//                    (AuthorHostActivity.FRAGMENT_HIGHSCORE), null);
            getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home,
                    CreateExamAssignmentContainerFragment.newInstance(getArguments())).commit();

        }

    }


    private void callApiGetExamQuestions() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
                request.setExamId(getArguments().getString(AssignmentsAdapter.ARG_EXAM_ID));
//                request.setExamId("9");
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_QUESTIONS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    /*if bundle arguments are not null then we will call get exam evaluation for student nd set data according to it*/
    private void callAPiGetExamEvaluation() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
//                request.setExamId(getArguments().getString(ExamsAdapter.ARG_EXAM_ID));
//                request.setStudentId(getArguments().getString(AssignmentSubmittorAdapter.ARG_STUDENT_ID));
                request.setExamId(WebConstants.EXAM_ID_9_OBJECTIVE);
                request.setStudentId(WebConstants.STUDENT_ID_202_OBJECCTIVE);
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_EVALUATIONS);
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
                case WebConstants.GET_EXAM_QUESTIONS:
                    onResponseGetAllExamQuestions(object, error);
                    break;
                case WebConstants.GET_EXAM_EVALUATIONS:
                    onResponseGetExamEvaluation(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }
    }

    private void onResponseGetAllExamQuestions(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                responseObjGetAllExamQuestions = (ResponseHandler) object;
                if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListQuestions.addAll(responseObjGetAllExamQuestions.getExamQuestions().get(0).getQuestions());
                    getObjectiveAssignmentQuestionsAdapter.addAll(arrListQuestions);
                    getObjectiveAssignmentQuestionsAdapter.notifyDataSetChanged();
                    setAssignmentDetails(responseObjGetAllExamQuestions.getExamQuestions().get(0));

                    if (getArguments().getBoolean(AssignmentsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION)) {
                        callAPiGetExamEvaluation();
                    }

                } else if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseObjGetAllExamQuestions.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExamQuestions api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExamQuestions Exception : " + e.toString());
        }
    }

    private void onResponseGetExamEvaluation(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    rvGetObjectiveAssignmentQuestionslist.scrollToPosition(0);
                    getObjectiveAssignmentQuestionsAdapter.setEvaluationData(responseHandler.getExamEvaluation().get(0).getEvaluation());

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetExamEvaluation api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetExamEvaluation Exception : " + e.toString());
        }
    }

    private void setAssignmentDetails(ExamQuestions examQuestions) {

        tvObjectiveAssignmentSubject.setText(getResources().getString(R.string.strbookname) + ": ");
        if (examQuestions.getBookName() != null) {
            tvObjectiveAssignmentSubject.append(Utility.getSpannableString(examQuestions.getBookName(), getResources().getColor(R.color.bg_assessment)));
        }
        tvObjectiveAssignmentClass.setText(getResources().getString(R.string.strclass) + ": ");
        if (examQuestions.getClassName() != null) {
            tvObjectiveAssignmentClass.append(Utility.getSpannableString(examQuestions.getClassName(), getResources().getColor(R.color.bg_assessment)));
        }
        tvObjectiveAssignmentNo.setText(getResources().getString(R.string.strassignmentno) + ": 1");
        tvObjectiveAssignmentTitle.setText(examQuestions.getExamName());
        tvObjectiveAssignmentDate.setText(getActivity().getResources().getString(R.string.strassignmentdatecolon) + " " +
                Utility.getFormattedDate("dd-MMM-yyyy", examQuestions.getCreatedDate()));

    }

    public void loadStudentEvaluationData() {
        callAPiGetExamEvaluation();
    }
}


