package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.AssignmentAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.MyTypeFace;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.ws.model.Attribute;
import com.ism.author.model.Data;
import com.ism.author.ws.model.ResponseHandler;
import com.ism.author.ws.WebserviceWrapper;
import com.ism.commonsource.view.ActionProcessButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c166 on 09/11/15.
 */
public class AssessmentFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AssessmentFragment.class.getSimpleName();
    private View view;
    private RecyclerView rvAssignmentList;
    private AssignmentAdapter assignmentAdapter;
    private ArrayList<Data> listOfAssignment = new ArrayList<Data>();
    private MyTypeFace myTypeFace;
    private FragmentListener fragListener;
    private Spinner spAssignmentSubject, spAssignmentClass, spAssignmentAssessed;
    private ActionProcessButton progAssignmentSubject, progAssignmentClass, progAssignmentAssessed;
    private ImageView imgToggleList;
    private ArrayList<Data> arrListClassRooms, arrListSubject;
    private List<String> arrListAssessment;

    private TextView txtSubmissionDate;
    private EditText etAssignmentStartdate, etAssignmentEnddate;
    private String examStartDate = "", examEndDate = "";


    public static AssessmentFragment newInstance() {
        AssessmentFragment assessmentFragment = new AssessmentFragment();
        return assessmentFragment;
    }

    public AssessmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assessment, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());

        rvAssignmentList = (RecyclerView) view.findViewById(R.id.rv_assignment_list);
        assignmentAdapter = new AssignmentAdapter(getActivity());

        rvAssignmentList.setHasFixedSize(true);
        rvAssignmentList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAssignmentList.setAdapter(assignmentAdapter);

        spAssignmentSubject = (Spinner) view.findViewById(R.id.sp_assignment_subject);
        spAssignmentClass = (Spinner) view.findViewById(R.id.sp_assignment_class);
        spAssignmentAssessed = (Spinner) view.findViewById(R.id.sp_assignment_assessed);

        progAssignmentSubject = (ActionProcessButton) view.findViewById(R.id.prog_assignment_subject);
        progAssignmentClass = (ActionProcessButton) view.findViewById(R.id.prog_assignment_class);
        progAssignmentAssessed = (ActionProcessButton) view.findViewById(R.id.prog_assignment_assessed);

        arrListAssessment = new ArrayList<String>();
        arrListAssessment = Arrays.asList(getResources().getStringArray(R.array.assessment_type));
        Adapters.setUpSpinner(getActivity(), spAssignmentAssessed, arrListAssessment, Adapters.ADAPTER_SMALL);

        txtSubmissionDate = (TextView) view.findViewById(R.id.txt_submission_date);
        etAssignmentStartdate = (EditText) view.findViewById(R.id.et_assignment_startdate);
        etAssignmentEnddate = (EditText) view.findViewById(R.id.et_assignment_enddate);

        txtSubmissionDate.setTypeface(myTypeFace.getRalewayRegular());
        etAssignmentStartdate.setTypeface(myTypeFace.getRalewayRegular());
        etAssignmentEnddate.setTypeface(myTypeFace.getRalewayRegular());

        etAssignmentStartdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examStartDate = Utils.showDatePickerDob(getActivity(), etAssignmentStartdate);
                }
                return true;
            }
        });

        etAssignmentEnddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examEndDate = Utils.showDatePickerDob(getActivity(), etAssignmentEnddate);
                }
                return true;
            }
        });

        callApiGetSubjects();
        callApiGetClassrooms();
        callApiGetAllAssignments();

    }

    private void callApiGetAllAssignments() {
        if (Utility.isOnline(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).startProgress();
                Attribute request = new Attribute();
                request.setUserId("370");
                request.setRole(String.valueOf(AppConstant.AUTHOR_ROLE_ID));
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLASSIGNMENTS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    private void callApiGetClassrooms() {

        if (Utility.isOnline(getActivity())) {
            try {
                Utility.showSpinnerProgress(progAssignmentClass);
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETCLASSROOMS);

            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiGetSubjects() {

        if (Utility.isOnline(getActivity())) {
            try {
                Utility.showSpinnerProgress(progAssignmentSubject);
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETSUBJECT);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETALLASSIGNMENTS:
                    onResponseGetAllAssignments(object, error);
                    break;

                case WebConstants.GETCLASSROOMS:
                    onResponseGetClassrooms(object, error);
                    break;

                case WebConstants.GETSUBJECT:
                    onResponseGetSubjects(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAllAssignments(Object object, Exception error) {

        try {
            ((AuthorHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(ResponseHandler.SUCCESS)) {
                    listOfAssignment.addAll(responseObj.getData());
                    assignmentAdapter.addAll(listOfAssignment);
                    assignmentAdapter.notifyDataSetChanged();
                } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllAssignments api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllAssignments Exception : " + e.toString());
        }
    }

    private void onResponseGetClassrooms(Object object, Exception error) {
        try {
            Utility.hideSpinnerProgress(progAssignmentClass);
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListClassRooms = new ArrayList<Data>();
                    arrListClassRooms.addAll(responseObj.getData());
                    List<String> classrooms = new ArrayList<String>();
                    classrooms.add(getString(R.string.strclass));
                    for (Data course : arrListClassRooms) {
                        classrooms.add(course.getClassName());

                    }
                    Adapters.setUpSpinner(getActivity(), spAssignmentClass, classrooms, Adapters.ADAPTER_SMALL);

                } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetClassrooms api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetClassrooms Exception : " + e.toString());
        }
    }

    private void onResponseGetSubjects(Object object, Exception error) {
        try {
            Utility.hideSpinnerProgress(progAssignmentSubject);
            if (object != null) {
                ResponseHandler responseObj = (ResponseHandler) object;
                if (responseObj.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListSubject = new ArrayList<Data>();
                    arrListSubject.addAll(responseObj.getData());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.strsubjectname));
                    for (Data subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());

                    }
                    Adapters.setUpSpinner(getActivity(), spAssignmentSubject, subjects, Adapters.ADAPTER_SMALL);
                } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetSubjects api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetSubjects Exception : " + e.toString());
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ASSESSMENT);
                Debug.i(TAG, "attach");
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ASSESSMENT);
                Debug.i(TAG, "detach");
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


}
