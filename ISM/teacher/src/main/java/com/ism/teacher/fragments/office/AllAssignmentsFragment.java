package com.ism.teacher.fragments.office;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Classrooms;
import com.ism.teacher.ws.model.Exams;
import com.ism.teacher.ws.model.Subjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class used to display the list of assignments.
 * We have the option to filter those results.
 */
public class AllAssignmentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AllAssignmentsFragment.class.getSimpleName();

    //Views
    private View view;
    private RecyclerView recyclerAssignmentSubjects;
    private EditText etAssignmentStartdate, etAssignmentEnddate;
    private Spinner spAssignmentSubject, spAssignmentClasswise, spAssignentAssessed;
    private ImageView imgToggleList;
    private TextView tvNoAssignments;

    //Array list

    private ArrayList<Exams> arrayListAssignments = new ArrayList<>();
    private ArrayList<Exams> copyListAssignments = new ArrayList<>();
    private ArrayList<Classrooms> arrListClassRooms = new ArrayList<>();
    private ArrayList<Subjects> arrListSubject = new ArrayList<>();
    private List<String> arrListAssessment = new ArrayList<>();

    //Objects
    public Fragment mFragment;
    AssignmentsAdapter assignmentsAdapter;

    private String examStartDate = "", examEndDate = "";

    public static AllAssignmentsFragment newInstance() {
        AllAssignmentsFragment allAssignmentsFragment = new AllAssignmentsFragment();
        return allAssignmentsFragment;
    }

    public AllAssignmentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment_home, container, false);

        initGlobal(view);
        return view;
    }


    private void initGlobal(View view) {
        assignmentsAdapter = new AssignmentsAdapter(getActivity(), this);
        tvNoAssignments = (TextView) view.findViewById(R.id.tv_no_assignments);
        recyclerAssignmentSubjects = (RecyclerView) view.findViewById(R.id.recycler_assignment_subjects);
        recyclerAssignmentSubjects.setHasFixedSize(true);
        recyclerAssignmentSubjects.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerAssignmentSubjects.setAdapter(assignmentsAdapter);

        spAssignmentSubject = (Spinner) view.findViewById(R.id.sp_assignment_subject);
        spAssignmentClasswise = (Spinner) view.findViewById(R.id.sp_assignment_classwise);
        spAssignentAssessed = (Spinner) view.findViewById(R.id.sp_assignent_assessed);
        imgToggleList = (ImageView) view.findViewById(R.id.img_toggle_list);

        etAssignmentStartdate = (EditText) view.findViewById(R.id.et_assignment_startdate);
        etAssignmentEnddate = (EditText) view.findViewById(R.id.et_assignment_enddate);

        arrListAssessment = Arrays.asList(getResources().getStringArray(R.array.assignment_status));

        Adapters.setUpSpinner(getActivity(), spAssignentAssessed, arrListAssessment, Adapters.ADAPTER_SMALL);

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

        /**
         * ItemselectedListener to handle filter based on particular spinner
         */

        AdapterView.OnItemSelectedListener spinnerListenerforFilters = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                filterAssignmentResults(adapterView, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        spAssignmentSubject.setOnItemSelectedListener(spinnerListenerforFilters);
        spAssignmentClasswise.setOnItemSelectedListener(spinnerListenerforFilters);
        spAssignentAssessed.setOnItemSelectedListener(spinnerListenerforFilters);

    }

    private void filterAssignmentResults(View view, int position) {

        if (view == spAssignmentSubject) {
            if (arrListSubject != null && position > 0) {
                if (position > 1) {

                    /**
                     * Position-2 because two static elements are added in the spinner in beginning
                     * so to fetch the first element from arraylist at index zero we are doing position-2
                     */
                    Debug.e(TAG, "subject is:" + arrListSubject.get(position - 2).getSubjectName());
                    filterSubjectIdWiseAssignments(arrListSubject.get(position - 2).getId());
                    spAssignentAssessed.setSelection(0);
                    spAssignmentClasswise.setSelection(0);

                } else {

                    clearFilters();
                    spAssignentAssessed.setSelection(0);
                    spAssignmentClasswise.setSelection(0);
                }
            }
        } else if (view == spAssignmentClasswise) {
            if (arrListClassRooms != null && position > 0) {
                if (position > 1) {
                    filterClassroomIdWiseAssignments(arrListClassRooms.get(position - 2).getId());
                    spAssignentAssessed.setSelection(0);
                    spAssignmentSubject.setSelection(0);

                } else {
                    clearFilters();
                    spAssignentAssessed.setSelection(0);
                    spAssignmentClasswise.setSelection(0);
                }
            }
        } else if (view == spAssignentAssessed) {
            if (position > 1) {
                filterAssessedNotAssessedAssignments(arrListAssessment.get(position));
                spAssignmentClasswise.setSelection(0);
                spAssignmentSubject.setSelection(0);

            } else {
                clearFilters();
                spAssignentAssessed.setSelection(0);
                spAssignmentClasswise.setSelection(0);
            }
        }

    }

    private void clearFilters() {
        assignmentsAdapter.addAll(arrayListAssignments);
        if (arrayListAssignments.size() > 0) {
            hideFilterText();
        } else {
            showFilterText();
        }
    }

    public void hideFilterText() {
        tvNoAssignments.setVisibility(View.GONE);
    }

    public void showFilterText() {
//        tvNoAssignments.setText(getString(R.string.no_assignments_found_for_your_filter_criteria));
        tvNoAssignments.setVisibility(View.VISIBLE);
    }

    private void filterSubjectIdWiseAssignments(String subjectId) {

        copyListAssignments.clear();

        if (arrayListAssignments.size() > 0) {
            for (Exams wp : arrayListAssignments) {
                if (wp.getSubjectId().equalsIgnoreCase(subjectId)) {

                    copyListAssignments.add(wp);
                }
            }
            if (copyListAssignments.size() > 0) {
                Debug.e(TAG + "results after filter:", "" + copyListAssignments.size());
                assignmentsAdapter.addAll(copyListAssignments);
                hideFilterText();
            } else {
                assignmentsAdapter.addAll(copyListAssignments);
                showFilterText();
//                Toast.makeText(getActivity(), "No Questions Found to Filter", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void filterClassroomIdWiseAssignments(String classroom_id) {

        copyListAssignments.clear();

        if (arrayListAssignments.size() > 0) {
            for (Exams wp : arrayListAssignments) {
                if (wp.getClassroomId().equalsIgnoreCase(classroom_id)) {

                    copyListAssignments.add(wp);
                }
            }
            if (copyListAssignments.size() > 0) {
                Debug.e(TAG + "results after filter:", "" + copyListAssignments.size());
                assignmentsAdapter.addAll(copyListAssignments);
                hideFilterText();
            } else {
                assignmentsAdapter.addAll(copyListAssignments);
                showFilterText();
//                Toast.makeText(getActivity(), "No Questions Found to Filter", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void filterAssessedNotAssessedAssignments(String evaluation_status) {

        copyListAssignments.clear();

        if (arrayListAssignments.size() > 0) {
            for (Exams wp : arrayListAssignments) {
                if (!wp.getEvaluationStatus().equals("") && wp.getEvaluationStatus().equalsIgnoreCase(evaluation_status)) {

                    copyListAssignments.add(wp);
                }
            }
            if (copyListAssignments.size() > 0) {
                Debug.e(TAG + "results after filter:", "" + copyListAssignments.size());
                assignmentsAdapter.addAll(copyListAssignments);
                hideFilterText();
            } else {
                assignmentsAdapter.addAll(copyListAssignments);
                showFilterText();
            }

        }
    }

    private void callApiGetClassrooms() {

        if (Utility.isConnected(getActivity())) {
            try {
                //   Utility.showSpinnerProgress(progAssignmentClass);
                Attribute attribute = new Attribute();
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_CLASSROOMS);

            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiGetSubjects() {

        if (Utility.isConnected(getActivity())) {
            try {
                // Utility.showSpinnerProgress(progAssignmentSubject);
                Attribute attribute = new Attribute();
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.USER_ID_370);
                attribute.setRole(AppConstant.TEACHER_ROLE_ID);
                attribute.setExamCategory("");
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

            switch (apicode) {
                case WebConstants.GET_ALL_ASSIGNMENTS:
                    if (getActivity() != null && isAdded()) {
                        ((TeacherHostActivity) getActivity()).hideProgress();
                        onResponseGetAllAssignments(object);
                    }
                    break;
                case WebConstants.GET_CLASSROOMS:
                    if (getActivity() != null && isAdded()) {
                        ((TeacherHostActivity) getActivity()).hideProgress();
                        onResponseGetClassrooms(object, error);

                    }
                    break;
                case WebConstants.GET_SUBJECT:
                    if (getActivity() != null && isAdded()) {
                        ((TeacherHostActivity) getActivity()).hideProgress();
                        onResponseGetSubjects(object, error);

                    }
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    private void onResponseGetClassrooms(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListClassRooms.addAll(responseHandler.getClassrooms());
                    List<String> classrooms = new ArrayList<String>();
                    classrooms.add(getString(R.string.strclass));
                    classrooms.add(getString(R.string.strall));
                    for (Classrooms classrooms1 : arrListClassRooms) {
                        classrooms.add(classrooms1.getClassName());

                    }
                    Adapters.setUpSpinner(getActivity(), spAssignmentClasswise, classrooms, Adapters.ADAPTER_SMALL);

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
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
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListSubject.addAll(responseHandler.getSubjects());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.strsubjectname));
                    subjects.add(getString(R.string.strall));
                    for (Subjects subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());

                    }
                    Adapters.setUpSpinner(getActivity(), spAssignmentSubject, subjects, Adapters.ADAPTER_SMALL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetSubjects api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetSubjects Exception : " + e.toString());
        }
    }

    private void onResponseGetAllAssignments(Object object) {

        if (object != null) {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                arrayListAssignments.addAll(responseHandler.getExams());
                assignmentsAdapter.addAll(arrayListAssignments);
                Debug.e("assignment size", "" + arrayListAssignments.size());

                if (arrayListAssignments.size() == 0) {
                    tvNoAssignments.setVisibility(View.VISIBLE);
                } else {
                    tvNoAssignments.setVisibility(View.GONE);

                }

            } else {

                Utility.showToast(responseHandler.getMessage(), getActivity());
            }
        } else {
            Debug.e(TAG, "onResponseGetAllAssignment Exception : " + "response object may be returning null");
        }

    }

    private TeacherOfficeFragment getFragment() {
        return (TeacherOfficeFragment) mFragment;
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }


}
