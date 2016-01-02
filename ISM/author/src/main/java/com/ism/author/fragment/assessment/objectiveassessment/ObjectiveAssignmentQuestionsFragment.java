package com.ism.author.fragment.assessment.objectiveassessment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.AssignmentSubmittorAdapter;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.ObjectiveAssignmentQuestionsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.createexam.CreateExamFragment;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c166 on 10/11/15.
 */

/*these fragment will be use for objective evaluation ,view total questions for subjective and objective and view student data for both subjective and objective*/
public class ObjectiveAssignmentQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = ObjectiveAssignmentQuestionsFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    private TextView tvObjectiveAssignmentSubject, tvObjectiveAssignmentClass, tvObjectiveAssignmentNo, tvObjectiveAssignmentTitle,
            tvObjectiveAssignmentDateTitle, tvObjectiveAssignmentDate, tvNoDataMsg;
    private ImageView imgEditExam, imgCopyExam;

    private RecyclerView rvGetObjectiveAssignmentQuestionslist;
    private ObjectiveAssignmentQuestionsAdapter objectiveAssignmentQuestionsAdapter;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    public static String ARG_ARR_LIST_QUESTIONS = "arrListQuestions";
    public static String ARG_EXAM_TYPE = "examType";
    public static String ARG_EXAM_ISCOPY = "examIsCopy";


    public static ObjectiveAssignmentQuestionsFragment newInstance() {
        ObjectiveAssignmentQuestionsFragment objectiveAssignmentQuestionsFragment = new ObjectiveAssignmentQuestionsFragment();
        return objectiveAssignmentQuestionsFragment;
    }


    public ObjectiveAssignmentQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_objective_assignment_questions, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {


        tvObjectiveAssignmentSubject = (TextView) view.findViewById(R.id.tv_objective_assignment_subject);
        tvObjectiveAssignmentClass = (TextView) view.findViewById(R.id.tv_objective_assignment_class);
        tvObjectiveAssignmentNo = (TextView) view.findViewById(R.id.tv_objective_assignment_no);
        tvObjectiveAssignmentTitle = (TextView) view.findViewById(R.id.tv_objective_assignment_title);
        tvObjectiveAssignmentDateTitle = (TextView) view.findViewById(R.id.tv_objective_assignment_date_title);
        tvObjectiveAssignmentDate = (TextView) view.findViewById(R.id.tv_objective_assignment_date);

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        imgEditExam = (ImageView) view.findViewById(R.id.img_edit_exam);
        imgCopyExam = (ImageView) view.findViewById(R.id.img_copy_exam);

        tvObjectiveAssignmentSubject.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentClass.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentNo.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentTitle.setTypeface(Global.myTypeFace.getRalewayBold());
        tvObjectiveAssignmentDateTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvObjectiveAssignmentDate.setTypeface(Global.myTypeFace.getRalewayRegular());


        rvGetObjectiveAssignmentQuestionslist = (RecyclerView) view.findViewById(R.id.rv_getObjective_assignment_questionslist);
        objectiveAssignmentQuestionsAdapter = new ObjectiveAssignmentQuestionsAdapter(getActivity());
        rvGetObjectiveAssignmentQuestionslist.setAdapter(objectiveAssignmentQuestionsAdapter);
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

        setEmptyView(false);

    }


    private void setExamQuestions() {

        if (responseObjGetAllExamQuestions != null) {
            getBundleArguments().putParcelableArrayList(ARG_ARR_LIST_QUESTIONS, arrListQuestions);
            getBundleArguments().putString(ARG_EXAM_TYPE, getString(R.string.strobjective));


            ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(
                    (AuthorHostActivity.FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT));

            ((AuthorHostActivity) getActivity()).loadFragmentInRightContainer(
                    (AuthorHostActivity.FRAGMENT_HIGHSCORE));

        }

    }

    private void callApiGetExamQuestions() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setExamId(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMQUESTIONS);
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
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setExamId(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                attribute.setStudentId(getBundleArguments().getString(AssignmentSubmittorAdapter.ARG_STUDENT_ID));
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMEVALUATIONS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETEXAMQUESTIONS:
                    onResponseGetAllExamQuestions(object, error);
                    break;
                case WebConstants.GETEXAMEVALUATIONS:
                    onResponseGetExamEvaluation(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }

    }

    ResponseHandler responseObjGetAllExamQuestions = null;

    private void onResponseGetAllExamQuestions(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                responseObjGetAllExamQuestions = (ResponseHandler) object;
                if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseObjGetAllExamQuestions.getExamQuestions().get(0).getQuestions().size() > 0) {
                        arrListQuestions.addAll(responseObjGetAllExamQuestions.getExamQuestions().get(0).getQuestions());
                        objectiveAssignmentQuestionsAdapter.addAll(arrListQuestions);
                        objectiveAssignmentQuestionsAdapter.notifyDataSetChanged();
                        if (getBundleArguments().getBoolean(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION)) {
                            callAPiGetExamEvaluation();
                        }

                        setEmptyView(false);

                    } else {

                        setEmptyView(true);
                    }

                } else if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseObjGetAllExamQuestions.getMessage(), getActivity());
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
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    rvGetObjectiveAssignmentQuestionslist.scrollToPosition(0);

                    if (responseHandler.getExamEvaluation().get(0).getEvaluation() != null) {
                        if (responseHandler.getExamEvaluation().get(0).getEvaluation().size() > 0) {
                            objectiveAssignmentQuestionsAdapter.setEvaluationData(responseHandler.getExamEvaluation().get(0).getEvaluation());
                        }

                    } else {
                        objectiveAssignmentQuestionsAdapter.setEvaluationData(null);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
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
            tvObjectiveAssignmentSubject.setText(getResources().getString(R.string.strbookname) + ": ");
            if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_NAME) != null) {
                tvObjectiveAssignmentSubject.append(Utility.getSpannableString(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_NAME), getResources().getColor(R.color.bg_assessment)));
            }
            tvObjectiveAssignmentClass.setText(getResources().getString(R.string.strclass) + ": ");
            if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME) != null) {
                tvObjectiveAssignmentClass.append(Utility.getSpannableString(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME), getResources().getColor(R.color.bg_assessment)));
            }
            tvObjectiveAssignmentTitle.setText(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_NAME));

            tvObjectiveAssignmentDate.setText(Utils.getDateInApiFormat(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CREATED_DATE)));

        }
    }

    public void loadStudentEvaluationData() {
        callAPiGetExamEvaluation();
    }


    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }

    public void onBackClick() {

        getBundleArguments().remove(ARG_ARR_LIST_QUESTIONS);
        getBundleArguments().remove(ARG_EXAM_TYPE);
        getBundleArguments().remove(ARG_EXAM_ISCOPY);

        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_exam_questions));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvGetObjectiveAssignmentQuestionslist.setVisibility(isEnable ? View.INVISIBLE : View.VISIBLE);
    }
}
