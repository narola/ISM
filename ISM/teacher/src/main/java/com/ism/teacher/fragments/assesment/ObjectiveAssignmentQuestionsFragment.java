package com.ism.teacher.fragments.assesment;

import android.app.Activity;
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
import com.ism.teacher.adapters.AssignmentSubmitterAdapter;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.adapters.GetObjectiveAssignmentQuestionsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.fragments.createexam.CreateExamFragment;
import com.ism.teacher.fragments.office.TeacherOfficeFragment;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * At top this frag displays book name,class and assignment related details.
 * This fragment contains the list of objective questions list below.
 */
public class ObjectiveAssignmentQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = ObjectiveAssignmentQuestionsFragment.class.getSimpleName();
    private View view;

    private TextView tvObjectiveAssignmentSubject, tvObjectiveAssignmentClass, tvObjectiveAssignmentNo, tvObjectiveAssignmentTitle,
            tvObjectiveAssignmentDateTitle, tvObjectiveAssignmentDate, tvNoQuestions;
    private ImageView imgEditExam, imgCopyExam;

    private RecyclerView rvGetObjectiveAssignmentQuestionslist;
    private GetObjectiveAssignmentQuestionsAdapter getObjectiveAssignmentQuestionsAdapter;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();

    ResponseHandler responseObjGetAllExamQuestions = null;

    public static String ARG_ARR_LIST_QUESTIONS = "arrListQuestions";
    public static String ARG_EXAM_ISCOPY = "examIsCopy";
    public static String ARG_EXAM_TYPE = "examType";


    public static ObjectiveAssignmentQuestionsFragment newInstance() {
        ObjectiveAssignmentQuestionsFragment objectiveAssignmentQuestionsFragment = new ObjectiveAssignmentQuestionsFragment();
        return objectiveAssignmentQuestionsFragment;
    }

    public ObjectiveAssignmentQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TeacherHostActivity) getActivity()).hideAddOption();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exam_objective_details, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {

        tvNoQuestions = (TextView) view.findViewById(R.id.tv_no_questions);
        tvObjectiveAssignmentSubject = (TextView) view.findViewById(R.id.tv_objective_assignment_subject);
        tvObjectiveAssignmentClass = (TextView) view.findViewById(R.id.tv_objective_assignment_class);
        tvObjectiveAssignmentNo = (TextView) view.findViewById(R.id.tv_objective_assignment_no);
        tvObjectiveAssignmentTitle = (TextView) view.findViewById(R.id.tv_objective_assignment_title);
        tvObjectiveAssignmentDateTitle = (TextView) view.findViewById(R.id.tv_objective_assignment_date_title);
        tvObjectiveAssignmentDate = (TextView) view.findViewById(R.id.tv_objective_assignment_date);

        imgEditExam = (ImageView) view.findViewById(R.id.img_edit_exam);
        imgCopyExam = (ImageView) view.findViewById(R.id.img_copy_exam);

        tvObjectiveAssignmentSubject.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentClass.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentNo.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentTitle.setTypeface(Global.myTypeFace.getRalewayBold());
        tvObjectiveAssignmentDateTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentDate.setTypeface(Global.myTypeFace.getRalewayRegular());

        rvGetObjectiveAssignmentQuestionslist = (RecyclerView) view.findViewById(R.id.rv_getObjective_assignment_questionslist);
        getObjectiveAssignmentQuestionsAdapter = new GetObjectiveAssignmentQuestionsAdapter(getActivity());
        rvGetObjectiveAssignmentQuestionslist.setAdapter(getObjectiveAssignmentQuestionsAdapter);
        rvGetObjectiveAssignmentQuestionslist.setLayoutManager(new LinearLayoutManager(getActivity()));

        setAssignmentDetails();
        callApiGetExamQuestions();

        imgEditExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBundleArguments().putBoolean(CreateExamFragment.ARG_IS_CREATE_EXAM, false);
                getBundleArguments().putBoolean(ARG_EXAM_ISCOPY, false);
                setExamQuestions();
            }
        });

        imgCopyExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBundleArguments().putBoolean(CreateExamFragment.ARG_IS_CREATE_EXAM, false);
                getBundleArguments().putBoolean(ARG_EXAM_ISCOPY, true);
                setExamQuestions();
            }
        });

    }

    private void setExamQuestions() {

        if (responseObjGetAllExamQuestions != null) {
            getBundleArguments().putParcelableArrayList(ARG_ARR_LIST_QUESTIONS, arrListQuestions);
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TYPE, getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TYPE));

//            ((AuthorHostActivity) getActivity()).loadFragmentInRightContainer(
//                    (AuthorHostActivity.FRAGMENT_HIGHSCORE), null);

            TeacherOfficeFragment teacherOfficeFragment = (TeacherOfficeFragment) getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_TAG_TEACHER_OFFICE);
            teacherOfficeFragment.loadFragmentInTeacherOffice(TeacherOfficeFragment.FRAGMENT_CREATE_EXAM_CONTAINER);

        }

    }


    private void callApiGetExamQuestions() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
                request.setExamId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_ID));
//                request.setExamId("9");
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_QUESTIONS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
                ((TeacherHostActivity) getActivity()).hideProgress();
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
                request.setExamId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_ID));
                request.setStudentId(getBundleArguments().getString(AssignmentSubmitterAdapter.ARG_STUDENT_ID));

//                request.setExamId(WebConstants.EXAM_ID_9_OBJECTIVE);
//                request.setLocalStudentId(WebConstants.STUDENT_ID_202_OBJECCTIVE);
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

                    if (arrListQuestions.size() == 0) {
                        Utility.showView(tvNoQuestions);
                    }

                    if (getBundleArguments().getBoolean(AssignmentsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION)) {
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

    private void setAssignmentDetails() {

        if (getBundleArguments() != null) {
            Debug.e("objective test", getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE));
            tvObjectiveAssignmentSubject.setText(getActivity().getResources().getString(R.string.strsubject) + ": ");

            if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME) != null) {
                tvObjectiveAssignmentSubject.append(Utility.getSpannableString(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME), getResources().getColor(R.color.bg_assessment)));
            }
            tvObjectiveAssignmentClass.setText(getActivity().getResources().getString(R.string.strclass) + ": ");
            if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_NAME) != null) {
                tvObjectiveAssignmentClass.append(Utility.getSpannableString(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_NAME), getResources().getColor(R.color.bg_assessment)));
            }
        }
        tvObjectiveAssignmentNo.setText(getActivity().getResources().getString(R.string.strassignmentno) + ": 1");
        tvObjectiveAssignmentTitle.setText(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_NAME));
        tvObjectiveAssignmentDate.setText(getActivity().getResources().getString(R.string.strassignmentdatecolon) + " " +
                Utility.getFormattedDate("dd-MMM-yyyy", getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CREATED_DATE)));

    }

    public void loadStudentEvaluationData() {
        callAPiGetExamEvaluation();
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }
}


