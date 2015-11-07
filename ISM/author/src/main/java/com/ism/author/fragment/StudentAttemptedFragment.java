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
import android.widget.Toast;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.StudentAttemptedAdapter;
import com.ism.author.adapter.TrialExamDetailsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.Data;
import com.ism.author.model.RequestObject;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;

import java.util.ArrayList;

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
    private TrialExamDetailsAdapter trialExamDetailsAdapter;
    public static ResponseObject responseObjQuestions;

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
        requestObject.setRole("3");
        // Debug.i(TAG, "Request student attemted list : " ));
        ((AuthorHostActivity) getActivity()).startProgress();
        new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                .execute(WebConstants.GETEXAMSUBMISSION);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED);
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
//            case FRAGMENT_TRIAL_EXAM_DETAILS:
//                currentFragment = fragment;
//                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_DETAILS);
//                break;
//        }
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        ((AuthorHostActivity) getActivity()).stopProgress();
        ResponseObject responseObject = (ResponseObject) object;
//        Debug.i(TAG, "Response of student attempted  ::" + responseObject.getMessage());
//        Debug.i(TAG, "Response of student attempted  ::" + responseObject.getStatus());
//        Debug.i(TAG, "Response of student attempted  ::" + responseObject.getData().get(0).getExamID());
        try {
            if (API_METHOD == WebConstants.GETEXAMSUBMISSION) {
                if (responseObject.getStatus().equals(ResponseObject.SUCCESS)) {
                    // ((AuthorHostActivity)getActivity()).stopProgress();
                    if (responseObject.getData().size() != 0) {

                        //  Debug.i(TAG, "Arraylist of student attempted  ::" + respon);

                        studentAttemptedAdapter = new StudentAttemptedAdapter(responseObject, getActivity(), this);
                        rvList.setAdapter(studentAttemptedAdapter);
                        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        RequestObject requestObject = new RequestObject();
                        requestObject.setExamId("9");
                        requestObject.setStudentId("202");
                        ((AuthorHostActivity) getActivity()).startProgress();
                        callApiGetExamEvaluations(requestObject);

                    }

                } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {
                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
                }
            }else if (API_METHOD == WebConstants.GETEXAMQUESTIONS) {
                if (responseObject.getStatus().equals(ResponseObject.SUCCESS)) {
                    ((AuthorHostActivity)getActivity()).stopProgress();
                    if (responseObject.getData().size() != 0) {
                        responseObjQuestions=responseObject;
                       // Debug.i(TAG, "Arraylist of Questions  ::" + responseObject.getData().get(0).getEvaluations());

                        trialExamDetailsAdapter = new TrialExamDetailsAdapter(responseObjQuestions, getActivity(), this,null);
                        TrialExamDetailFragment.rvList.setAdapter(trialExamDetailsAdapter);
                        TrialExamDetailFragment.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));


                    }

                } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {
                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
                }
            }

        }
        catch (Exception e){
            Debug.i(TAG, "Exceptions ::" + e.getLocalizedMessage());
        }

    }

    private void callApiGetExamEvaluations(RequestObject requestObject) {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).startProgress();
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMQUESTIONS);
            } catch (Exception e) {
                Log.i(TAG ,"callApiGetExamEvaluations ::"+ e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }
}
