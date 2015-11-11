package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.AssignmentSubjectsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.RequestObject;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.ws.WebserviceWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c161 on --/10/15.
 */
public class TeacherQuizHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherQuizHomeFragment.class.getSimpleName();

    private View view;

    private RecyclerView recyclerAssignmentSubjects;
    AssignmentSubjectsAdapter assignmentSubjectsAdapter;

    Spinner spAssignmentSubject, spAssignmentClasswise, spAssignentSubmissionDate, spAssignentAssessed;
    ImageView imgToggleList;

    List<String> arrayListSubjects, arrayListClasses, arrayListSubmissionDate, arrayListAssessed;

    ArrayList<Data> arrayListAssignments = new ArrayList<>();

    Fragment mFragment;

    /*public static TeacherQuizHomeFragment newInstance() {
        TeacherQuizHomeFragment teacherQuizHomeFragment = new TeacherQuizHomeFragment(this);
        return teacherQuizHomeFragment;
    }*/

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
        spAssignentSubmissionDate = (Spinner) view.findViewById(R.id.sp_assignent_submission_date);
        spAssignentAssessed = (Spinner) view.findViewById(R.id.sp_assignent_assessed);
        imgToggleList = (ImageView) view.findViewById(R.id.img_toggle_list);

        arrayListSubjects = new ArrayList<String>();
        arrayListClasses = new ArrayList<String>();
        arrayListSubmissionDate = new ArrayList<String>();


        arrayListSubjects.add(getString(R.string.strsubject));
        arrayListClasses.add(getString(R.string.classwise));
        arrayListSubmissionDate.add(getString(R.string.submission_date));

        arrayListAssessed = Arrays.asList(getResources().getStringArray(R.array.assignment_status));

        Adapters.setUpSpinner(getActivity(), spAssignmentSubject, arrayListSubjects, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spAssignmentClasswise, arrayListClasses, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spAssignentSubmissionDate, arrayListSubmissionDate, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spAssignentAssessed, arrayListAssessed, Adapters.ADAPTER_SMALL);


        callApiGetAllAssignments();
    }

    private void callApiGetAllAssignments() {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                RequestObject request = new RequestObject();
                request.setUserId("370");
                request.setRole(AppConstant.TEACHER_ROLE_ID);
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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
                    onResponseGetAllAssignments(object);
                    break;

            }

        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    private void onResponseGetAllAssignments(Object object) {

        ResponseObject callGetAllAssignments = (ResponseObject) object;
        if (callGetAllAssignments.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {

            arrayListAssignments.addAll(callGetAllAssignments.getData());
            assignmentSubjectsAdapter.addAll(arrayListAssignments);
        } else {

            Utility.showToast(getString(R.string.web_service_issue), getActivity());
        }
    }




}
