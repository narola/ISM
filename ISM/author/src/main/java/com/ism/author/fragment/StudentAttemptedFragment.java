package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.StudentAttemptedAdapter;
import com.ism.author.adapter.TrialExamDetailsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;

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
    private StudentAttemptedAdapter studentAttemptedAdapter;
    private TrialExamDetailsAdapter trialExamDetailsAdapter;
    public static ResponseHandler responseObjQuestions;
    public static List<String> arrListQuestionIds = new ArrayList<>();

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
        Attribute attribute = new Attribute();
        attribute.setExamId("9");
        attribute.setUserId("340");
        attribute.setRole("3");
        // Debug.i(TAG, "Request student attemted list : " ));
        ((AuthorHostActivity) getActivity()).showProgress();
        new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                .execute(WebConstants.GETEXAMSUBMISSION);


    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            fragListener = (FragmentListener) activity;
//            if (fragListener != null) {
//                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED);
//            }
//        } catch (ClassCastException e) {
//            Debug.e(TAG, "onAttach Exception : " + e.toString());
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        try {
//            if (fragListener != null) {
//                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED);
//                Debug.i(TAG, "detach");
//            }
//        } catch (ClassCastException e) {
//            Debug.e(TAG, "onDetach Exception : " + e.toString());
//        }
//        fragListener = null;
//    }


    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        ((AuthorHostActivity) getActivity()).hideProgress();

        try {
            if (API_METHOD == WebConstants.GETEXAMSUBMISSION) {
                ResponseHandler reponseHandler = (ResponseHandler) object;
                if (reponseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    if (reponseHandler.getExamSubmission().size() != 0) {

                        Debug.i(TAG, "Arraylist of student attempted  ::" + reponseHandler);

                        studentAttemptedAdapter = new StudentAttemptedAdapter(reponseHandler, getActivity(), this);
                        rvList.setAdapter(studentAttemptedAdapter);
                        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        Attribute attribute = new Attribute();
                        attribute.setExamId("3");
                        //attribute.setStudentId("202");
                        ((AuthorHostActivity) getActivity()).showProgress();
                        callapigetexamquestions(attribute);
                    }

                } else if (reponseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
                }
            } else if (API_METHOD == WebConstants.GETEXAMQUESTIONS) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    if (responseHandler.getExamQuestions().size() != 0) {
                        responseObjQuestions = responseHandler;
                        trialExamDetailsAdapter = new TrialExamDetailsAdapter(responseObjQuestions, getActivity(), this, null);
                        TrialExamObjectiveDetailFragment.rvList.setAdapter(trialExamDetailsAdapter);
                        TrialExamObjectiveDetailFragment.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        TrialExamObjectiveDetailFragment.txtBookNameValue.setText(responseObjQuestions.getExamQuestions().get(0).getBookName());
//                        TrialExamObjectiveDetailFragment.txtExamTypeName.setText(responseObjQuestions.getExamQuestions().get(0).getEXa());
                        TrialExamObjectiveDetailFragment.txtClassName.setText(responseObjQuestions.getExamQuestions().get(0).getClassName());
                        String examDate[] = responseObjQuestions.getExamQuestions().get(0).getCreatedDate().split(" ");
                        TrialExamObjectiveDetailFragment.txtExamDateValue.setText(examDate[0]);
                        TrialExamObjectiveDetailFragment.txtExamName.setText(responseObjQuestions.getExamQuestions().get(0).getExamName());
                        arrListQuestionIds = new ArrayList<>();
                        for (int i = 0; i < responseObjQuestions.getExamQuestions().get(0).getQuestions().size(); i++) {
                            arrListQuestionIds.add(responseObjQuestions.getExamQuestions().get(0).getQuestions().get(i).getQuestionId());
                            Debug.i(TAG, "Q.ID :" + arrListQuestionIds.get(i));
                        }

                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            Debug.i(TAG, "Exceptions ::" + e.getLocalizedMessage());
        }

    }

    private void callapigetexamquestions(Attribute attribute) {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMQUESTIONS);
            } catch (Exception e) {
                Log.i(TAG, "callApi GETEXAMQUESTIONS ::" + e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }
}
