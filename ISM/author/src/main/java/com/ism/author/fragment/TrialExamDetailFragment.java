package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utils;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;

/**
 * Created by c162 on 04/11/15.
 */
public class TrialExamDetailFragment  extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = TrialExamDetailFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;
    private MyTypeFace myTypeFace;
    private InputValidator inputValidator;

    public static TrialExamDetailFragment newInstance() {
        TrialExamDetailFragment trialExamDetailFragment = new TrialExamDetailFragment();
        return trialExamDetailFragment;
    }

    public TrialExamDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trial_exam_details, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        inputValidator = new InputValidator(getActivity());




//        btnActivitySave.setOnClickListener(new View.OnClickListener() {
//                                               @Override
//                                               public void onClick(View v) {
//                                                   if (isInputsValid()) {
//                                                       callApiCreateAssignment();
//                                                   }
//
//
//                                               }
//                                           }
//        );
//
//        btnActivityCancel.setOnClickListener(new View.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     backToTrialScreen();
//                                                 }
//                                             }
//        );
//
//
//        etActivitySubmissionDate.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    showDatePickerDob();
//                }
//                return true;
//            }
//        });


               callApiGetClassRooms();


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_DETAILS);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_DETAILS);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void callApiGetClassRooms() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETCLASSROOMS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    private void callApiGetSubjects() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETSUBJECT);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    private void callApiGetTopics(int subject_id) {

        if (Utils.isInternetConnected(getActivity())) {
            try {
//                GetTopicsRequest getTopicsRequest = new GetTopicsRequest();
//                getTopicsRequest.setSubject_id(subject_id);
//                new WebserviceWrapper(getActivity(), getTopicsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
//                        .execute(WebserviceWrapper.GETTOPICS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    private void callApiCreateAssignment() {

        if (Utils.isInternetConnected(getActivity())) {

            try {


//                new WebserviceWrapper(getActivity(), createAssignmentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
//                        .execute(WebserviceWrapper.CREATEASSIGNMENT);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {
            if (apiMethodName == WebserviceWrapper.GETCLASSROOMS) {

                ResponseObject callGetClassRoomsResponseObject = (ResponseObject) object;
                if (callGetClassRoomsResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && callGetClassRoomsResponseObject != null) {


                } else {
                    Utils.showToast(callGetClassRoomsResponseObject.getMessage(), getActivity());
                }

            }


        } catch (Exception e) {
            Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }
    }

    private void backToTrialScreen() {

        ((AuthorHostActivity) getActivity()).onBackPressed();
    }


}


