package com.ism.author.fragment;

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
import com.ism.author.adapter.GetObjectiveAssignmentQuestionsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.FragmentArgument;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.ExamQuestions;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c166 on 10/11/15.
 */
public class GetObjectiveAssignmentQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GetObjectiveAssignmentQuestionsFragment.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;
    private FragmentListener fragListener;

    private TextView tvObjectiveAssignmentSubject, tvObjectiveAssignmentClass, tvObjectiveAssignmentNo, tvObjectiveAssignmentTitle,
            tvObjectiveAssignmentDateTitle, tvObjectiveAssignmentDate;
    private ImageView imgEditExam, imgCopyExam;

    private RecyclerView rvGetObjectiveAssignmentQuestionslist;
    private GetObjectiveAssignmentQuestionsAdapter getObjectiveAssignmentQuestionsAdapter;
    private ArrayList<Questions> listOfQuestions = new ArrayList<Questions>();
    private FragmentArgument fragmentArgument;


    public static GetObjectiveAssignmentQuestionsFragment newInstance(FragmentArgument fragmentArgument) {
        GetObjectiveAssignmentQuestionsFragment getObjectiveAssignmentQuestionsFragment = new GetObjectiveAssignmentQuestionsFragment();
        if (fragmentArgument != null) {
            getObjectiveAssignmentQuestionsFragment.fragmentArgument = fragmentArgument;
            getObjectiveAssignmentQuestionsFragment.fragmentArgument.setFragment(getObjectiveAssignmentQuestionsFragment);
        }
        return getObjectiveAssignmentQuestionsFragment;
    }


    public GetObjectiveAssignmentQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_getobjective_assignment_questions, container, false);
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


        if (fragmentArgument != null) {
            ((AuthorHostActivity) getActivity()).loadFragmentInRightContainer(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT, fragmentArgument);
        }


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


            fragmentArgument.getFragmentArgumentObject().setListOfQuestions(responseObjGetAllExamQuestions.getExamQuestions().get(0).getQuestions());
        }
        ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(
                (AuthorHostActivity.FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT), fragmentArgument);

        ((AuthorHostActivity) getActivity()).loadFragmentInRightContainer(
                (AuthorHostActivity.FRAGMENT_HIGHSCORE), fragmentArgument);

    }

    private void callApiGetExamQuestions() {
        if (Utility.isOnline(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).startProgress();
                Attribute request = new Attribute();
                request.setExamId("3");
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMQUESTIONS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    private void callAPiGetExamEvaluation() {
        if (Utility.isOnline(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).startProgress();
                Attribute request = new Attribute();
//                request.setExamId(fragmentArgument.getFragmentArgumentObject().getExamId());
//                request.setExamId(fragmentArgument.getFragmentArgumentObject().getStudentId());
                request.setExamId("3");
                request.setStudentId("202");
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS);
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
            ((AuthorHostActivity) getActivity()).stopProgress();
            if (object != null) {
                responseObjGetAllExamQuestions = (ResponseHandler) object;
                if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.SUCCESS)) {
                    listOfQuestions.addAll(responseObjGetAllExamQuestions.getExamQuestions().get(0).getQuestions());
                    getObjectiveAssignmentQuestionsAdapter.addAll(listOfQuestions);
                    getObjectiveAssignmentQuestionsAdapter.notifyDataSetChanged();
                    setAssignmentDetails(responseObjGetAllExamQuestions.getExamQuestions().get(0));

                    if (fragmentArgument != null) {

                        callAPiGetExamEvaluation();
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
            ((AuthorHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    getObjectiveAssignmentQuestionsAdapter.setEvaluationData(responseHandler.getExamEvaluation().get(0).getEvaluation());
                    getObjectiveAssignmentQuestionsAdapter.notifyDataSetChanged();


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
