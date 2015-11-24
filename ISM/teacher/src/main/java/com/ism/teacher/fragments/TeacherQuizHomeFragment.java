package com.ism.teacher.fragments;

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

import com.ism.commonsource.view.ActionProcessButton;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.AssignmentSubjectsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.model.Data;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c161 on --/10/15.
 */
public class TeacherQuizHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherQuizHomeFragment.class.getSimpleName();

    //Views
    private View view;
    private RecyclerView recyclerAssignmentSubjects;
    EditText etAssignmentStartdate, etAssignmentEnddate;
    Spinner spAssignmentSubject, spAssignmentClasswise, spAssignentAssessed;
    ImageView imgToggleList;
    private ActionProcessButton progAssignmentSubject, progAssignmentClass, progAssignmentAssessed;

    List<String> arrayListSubjects, arrayListClasses, arrayListSubmissionDate, arrayListAssessed;
    ArrayList<Data> arrayListAssignments = new ArrayList<>();
    private ArrayList<Data> arrListClassRooms, arrListSubject;

    Fragment mFragment;
    AssignmentSubjectsAdapter assignmentSubjectsAdapter;
    private String examStartDate = "", examEndDate = "";

    public TeacherQuizHomeFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment_home, container, false);

        initGlobal(view);

        return view;
    }

    private void initGlobal(View view) {
        assignmentSubjectsAdapter = new AssignmentSubjectsAdapter(getActivity(), this);
        recyclerAssignmentSubjects = (RecyclerView) view.findViewById(R.id.recycler_assignment_subjects);
        recyclerAssignmentSubjects.setHasFixedSize(true);
        recyclerAssignmentSubjects.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerAssignmentSubjects.setAdapter(assignmentSubjectsAdapter);

        spAssignmentSubject = (Spinner) view.findViewById(R.id.sp_assignment_subject);
        spAssignmentClasswise = (Spinner) view.findViewById(R.id.sp_assignment_classwise);
        spAssignentAssessed = (Spinner) view.findViewById(R.id.sp_assignent_assessed);
        imgToggleList = (ImageView) view.findViewById(R.id.img_toggle_list);


        progAssignmentSubject = (ActionProcessButton) view.findViewById(R.id.prog_assignment_subject);
        progAssignmentClass = (ActionProcessButton) view.findViewById(R.id.prog_assignment_class);
        progAssignmentAssessed = (ActionProcessButton) view.findViewById(R.id.prog_assignment_assessed);

        etAssignmentStartdate = (EditText) view.findViewById(R.id.et_assignment_startdate);
        etAssignmentEnddate = (EditText) view.findViewById(R.id.et_assignment_enddate);

        arrayListSubjects = new ArrayList<String>();
        arrayListClasses = new ArrayList<String>();
        arrayListSubmissionDate = new ArrayList<String>();


        arrayListSubjects.add(getString(R.string.strsubject));
        arrayListClasses.add(getString(R.string.classwise));
        arrayListSubmissionDate.add(getString(R.string.submission_date));

        arrayListAssessed = Arrays.asList(getResources().getStringArray(R.array.assignment_status));

        Adapters.setUpSpinner(getActivity(), spAssignmentSubject, arrayListSubjects, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spAssignmentClasswise, arrayListClasses, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spAssignentAssessed, arrayListAssessed, Adapters.ADAPTER_SMALL);

        etAssignmentStartdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examStartDate = Utility.showDatePickerDob(getActivity(), etAssignmentStartdate);
                }
                return true;
            }
        });

        etAssignmentEnddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examEndDate = Utility.showDatePickerDob(getActivity(), etAssignmentEnddate);
                }
                return true;
            }
        });

        callApiGetSubjects();
        callApiGetClassrooms();
        callApiGetAllAssignments();
    }

    private void callApiGetClassrooms() {

        if (Utility.isOnline(getActivity())) {
            try {
                Utility.showSpinnerProgress(progAssignmentClass);
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_CLASSROOMS);

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
                        .execute(WebConstants.GET_SUBJECT);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiGetAllAssignments() {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).startProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.USER_ID_370);
                attribute.setRole(AppConstant.TEACHER_ROLE_ID + "");
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_ASSIGNMENTS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    @Override
    public void onResponse(int apicode, Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            switch (apicode) {
                case WebConstants.GET_ALL_ASSIGNMENTS:
                    onResponseGetAllAssignments(object);
                    break;
                case WebConstants.GET_CLASSROOMS:
                    onResponseGetClassrooms(object, error);
                    break;
                case WebConstants.GET_SUBJECT:
                    onResponseGetSubjects(object, error);
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

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
                        classrooms.add(course.getClass_name());

                    }
                    Adapters.setUpSpinner(getActivity(), spAssignmentClasswise, classrooms, Adapters.ADAPTER_SMALL);

                } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseObj.getMessage(), getActivity());
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
                        subjects.add(subject.getSubject_name());

                    }
                    Adapters.setUpSpinner(getActivity(), spAssignmentSubject, subjects, Adapters.ADAPTER_SMALL);
                } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetSubjects api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetSubjects Exception : " + e.toString());
        }
    }

    private void onResponseGetAllAssignments(Object object) {

        ResponseHandler callGetAllAssignments = (ResponseHandler) object;
        if (callGetAllAssignments.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {

            arrayListAssignments.addAll(callGetAllAssignments.getData());
            assignmentSubjectsAdapter.addAll(arrayListAssignments);
        } else {

            Utility.showToast(getString(R.string.web_service_issue), getActivity());
        }
    }


}
