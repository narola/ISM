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
import com.ism.teacher.adapters.AssignmentSubmitterAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Examsubmittor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c161 on --/10/15.
 */
public class GetAssignmentsSubmitterFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GetAssignmentsSubmitterFragment.class.getSimpleName();

    private View view;

    private RecyclerView rvAssignmentSubmittorList;
    AssignmentSubmitterAdapter assignmentSubmitterAdapter;

    Spinner spAssignmentSubject, spAssignmentClasswise, spAssignentSubmissionDate, spAssignentAssessed;
    ImageView imgToggleList;

    List<String> arrayListSubjects, arrayListClasses, arrayListSubmissionDate, arrayListAssessed;

    ArrayList<Examsubmittor> arrayListAssignments = new ArrayList<>();

    public GetAssignmentsSubmitterFragment() {
    }

    public static GetAssignmentsSubmitterFragment newInstance(Bundle bundleArguments) {
        GetAssignmentsSubmitterFragment getAssignmentsSubmitterFragment = new GetAssignmentsSubmitterFragment();
        getAssignmentsSubmitterFragment.setArguments(bundleArguments);
        return getAssignmentsSubmitterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_examwise_assignment, container, false);

        initGlobal(view);

        return view;
    }

    private void initGlobal(View view) {

        rvAssignmentSubmittorList = (RecyclerView) view.findViewById(R.id.recycler_examwise_assignments);

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

        assignmentSubmitterAdapter = new AssignmentSubmitterAdapter(getActivity(), getArguments());

        rvAssignmentSubmittorList.setHasFixedSize(true);
        rvAssignmentSubmittorList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAssignmentSubmittorList.setAdapter(assignmentSubmitterAdapter);

        callApiGetExamSubmission();
    }

    private void callApiGetExamSubmission() {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setExamId(WebConstants.EXAM_ID_9_OBJECTIVE);
                attribute.setUserId(WebConstants.USER_ID_340);
                attribute.setRole(AppConstant.TEACHER_ROLE_ID);

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_EXAM_SUBMISSION);
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
                case WebConstants.GET_ALL_EXAM_SUBMISSION:
                    onResponseGetAllExamSubmission(object);
                    break;
            }

        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }

    private void onResponseGetAllExamSubmission(Object object) {

        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

            arrayListAssignments.addAll(responseHandler.getExamSubmission().get(0).getExamsubmittor());
            assignmentSubmitterAdapter.addAll(arrayListAssignments);
        } else {

            Utility.showToast(getString(R.string.web_service_issue), getActivity());
        }
    }
}
