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
import com.ism.teacher.adapters.ExamWiseAssignmentAdapter;
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
public class TeacherExamWiseAssignments extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherExamWiseAssignments.class.getSimpleName();

    private View view;

    private RecyclerView recyclerExamwiseAssignments;
    ExamWiseAssignmentAdapter examWiseAssignmentAdapter;

    Spinner spAssignmentSubject, spAssignmentClasswise, spAssignentSubmissionDate, spAssignentAssessed;
    ImageView imgToggleList;

    List<String> arrayListSubjects, arrayListClasses, arrayListSubmissionDate, arrayListAssessed;

    ArrayList<Data> arrayListAssignments = new ArrayList<>();
    Fragment mFragment;
    String examid = "",exam_mode="";

    /*public static TeacherExamWiseAssignments newInstance() {
        TeacherExamWiseAssignments teacherExamWiseAssignments = new TeacherExamWiseAssignments();
        return teacherExamWiseAssignments;
    }*/

    public TeacherExamWiseAssignments(Fragment fragment, String examid,String exammode) {
        // Required empty public constructor
        this.mFragment = fragment;
        this.examid = examid;
        this.exam_mode=exammode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_examwise_assignment, container, false);

        initGlobal(view);

        return view;
    }

    private void initGlobal(View view) {

        recyclerExamwiseAssignments = (RecyclerView) view.findViewById(R.id.recycler_examwise_assignments);

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


        callApiGetExamSubmission();
    }

    private void callApiGetExamSubmission() {
        if (Utility.isInternetConnected(getActivity())) {
            try {
                Attribute attribute=new Attribute();
                attribute.setExamId(WebConstants.EXAM_ID_9_OBJECTIVE);
                attribute.setUserId(WebConstants.USER_ID_340);
                attribute.setRole(AppConstant.TEACHER_ROLE_ID + "");

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

        ResponseHandler callGetAllExamSubmission = (ResponseHandler) object;
        if (callGetAllExamSubmission.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {

            examWiseAssignmentAdapter = new ExamWiseAssignmentAdapter(callGetAllExamSubmission.getData().get(0).getExam_id(),getActivity(), this,exam_mode);
            recyclerExamwiseAssignments.setHasFixedSize(true);
            recyclerExamwiseAssignments.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            recyclerExamwiseAssignments.setAdapter(examWiseAssignmentAdapter);


            arrayListAssignments.addAll(callGetAllExamSubmission.getData().get(0).getArrayListEvaluation());
            examWiseAssignmentAdapter.addAll(arrayListAssignments);
        } else {

            Utility.showToast(getString(R.string.web_service_issue), getActivity());
        }
    }
}
