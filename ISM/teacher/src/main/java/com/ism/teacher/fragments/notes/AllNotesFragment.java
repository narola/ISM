package com.ism.teacher.fragments.notes;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.notes.AllNotesAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Classrooms;
import com.ism.teacher.ws.model.Notes;
import com.ism.teacher.ws.model.Subjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by c161 on --/10/15.
 */
public class AllNotesFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AllNotesFragment.class.getSimpleName();

    //Views
    RecyclerView rvNotes;
    Spinner spSubject, spClasswise, spAssessed;
    private TextView tvNoNotes;

    //Adapter
    AllNotesAdapter allNotesAdapter;

    //ArrayList
    private List<String> arrListDefault = new ArrayList<>();
    private List<String> arrListAssessment = new ArrayList<>();
    private ArrayList<Notes> arrListNotes = new ArrayList<>();
    private ArrayList<Classrooms> arrListClassRooms = new ArrayList<>();
    private ArrayList<Subjects> arrListSubject = new ArrayList<>();
    private ArrayList<Notes> copyListNotes = new ArrayList<>();


    public static AllNotesFragment newInstance() {
        AllNotesFragment allNotesFragment = new AllNotesFragment();
        return allNotesFragment;
    }

    public AllNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_notes, container, false);

        initGlobal(view);

        return view;
    }

    private void initGlobal(View rootview) {

        tvNoNotes = (TextView) rootview.findViewById(R.id.tv_no_notes);
        spSubject = (Spinner) rootview.findViewById(R.id.sp_subject);
        spClasswise = (Spinner) rootview.findViewById(R.id.sp_classwise);
        spAssessed = (Spinner) rootview.findViewById(R.id.sp_assessed);

        rvNotes = (RecyclerView) rootview.findViewById(R.id.rv_notes);
        rvNotes.setHasFixedSize(true);
        rvNotes.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        allNotesAdapter = new AllNotesAdapter(getActivity());
        rvNotes.setAdapter(allNotesAdapter);

        arrListDefault.add(getString(R.string.select));
        arrListAssessment = Arrays.asList(getResources().getStringArray(R.array.assignment_status));

        Adapters.setUpSpinner(getActivity(), spSubject, arrListDefault, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spClasswise, arrListDefault, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spAssessed, arrListAssessment, Adapters.ADAPTER_SMALL);

        callApiGetSubjects();
        callApiGetClassrooms();
        callApiGetAllLessonNotes();

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

        spSubject.setOnItemSelectedListener(spinnerListenerforFilters);
        spClasswise.setOnItemSelectedListener(spinnerListenerforFilters);

//        spAssessed.setOnItemSelectedListener(spinnerListenerforFilters);
    }

    private void filterAssignmentResults(View view, int position) {


        switch (view.getId()) {
            case R.id.sp_subject:
                if (arrListSubject != null && position > 0) {
                    if (position > 1) {

                        /**
                         * Position-2 because two static elements are added in the spinner in beginning
                         * so to fetch the first element from arraylist at index zero we are doing position-2
                         */
                        Debug.e(TAG, "subject is:" + arrListSubject.get(position - 2).getSubjectName());
                        filterSubjectIdWiseAssignments(arrListSubject.get(position - 2).getId());
                        spAssessed.setSelection(0);
                        spClasswise.setSelection(0);

                    } else {

                        clearFilters();
                        spAssessed.setSelection(0);
                        spClasswise.setSelection(0);
                    }
                }
                break;

            case R.id.sp_classwise:
                if (arrListClassRooms != null && position > 0) {
                    if (position > 1) {
                        filterClassroomWiseAssignments(arrListClassRooms.get(position - 2).getClassName());
                        spAssessed.setSelection(0);
                        spSubject.setSelection(0);

                    } else {
                        clearFilters();
                        spAssessed.setSelection(0);
                        spClasswise.setSelection(0);
                    }
                }
                break;
        }
    }

    private void filterSubjectIdWiseAssignments(String subjectId) {

        copyListNotes.clear();

        if (arrListNotes.size() > 0) {
            for (Notes wp : arrListNotes) {
                if (wp.getSubjectId().equalsIgnoreCase(subjectId)) {

                    copyListNotes.add(wp);
                }
            }
            if (copyListNotes.size() > 0) {
                Debug.e(TAG + "results after filter:", "" + copyListNotes.size());
                allNotesAdapter.addAll(copyListNotes);
                Utility.hideView(tvNoNotes);
            } else {
                allNotesAdapter.addAll(copyListNotes);
                Utility.showView(tvNoNotes);
            }


        }
    }

    private void filterClassroomWiseAssignments(String classroom_name) {

        copyListNotes.clear();
        if (arrListNotes.size() > 0) {
            for (Notes wp : arrListNotes) {
                if (wp.getClassName().equalsIgnoreCase(classroom_name)) {

                    copyListNotes.add(wp);
                }
            }
            if (copyListNotes.size() > 0) {
                Debug.e(TAG + "results after filter:", "" + copyListNotes.size());
                allNotesAdapter.addAll(copyListNotes);
                Utility.hideView(tvNoNotes);
            } else {
                allNotesAdapter.addAll(copyListNotes);
                Utility.showView(tvNoNotes);
            }


        }
    }

    private void clearFilters() {
        allNotesAdapter.addAll(arrListNotes);
        if (arrListNotes.size() > 0) {
            Utility.hideView(tvNoNotes);
        } else {
            Utility.showView(tvNoNotes);
        }
    }

    private void callApiGetClassrooms() {

        if (Utility.isConnected(getActivity())) {
            try {
                //   Utility.showSpinnerProgress(progAssignmentClass);
                Attribute attribute = new Attribute();
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
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
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_SUBJECT);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiGetAllLessonNotes() {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.USER_ID_370);
                attribute.setRoleId(WebConstants.TEACHER_ROLE_ID);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.ALL_LESSON_NOTES);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {

            switch (apiCode) {
                case WebConstants.ALL_LESSON_NOTES:
                    if (getActivity() != null && isAdded()) {
                        ((TeacherHostActivity) getActivity()).hideProgress();
                        onResponseGetAllLessonNotes(object, error);
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


    public void onResponseGetAllLessonNotes(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListNotes.addAll(responseHandler.getNotes());
                    allNotesAdapter.addAll(arrListNotes);
                    Debug.e("notes size", "" + arrListNotes.size());
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllLesson object may be null : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllLesson Notes Exception : " + e.toString());
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
                    Adapters.setUpSpinner(getActivity(), spClasswise, classrooms, Adapters.ADAPTER_SMALL);

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
                    Adapters.setUpSpinner(getActivity(), spSubject, subjects, Adapters.ADAPTER_SMALL);
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


}
