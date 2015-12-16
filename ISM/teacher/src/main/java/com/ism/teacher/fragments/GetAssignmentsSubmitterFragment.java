package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.AssignmentSubmitterAdapter;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Examsubmittor;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class GetAssignmentsSubmitterFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GetAssignmentsSubmitterFragment.class.getSimpleName();

    //Views
    private RecyclerView rvAssignmentSubmittorList;
    ImageView imgToggleList;
    TextView tvNoSubmissions, tvSubmittorTitle;

    //List
    ArrayList<Examsubmittor> arrayListAssignments = new ArrayList<>();

    //Objects
    AssignmentSubmitterAdapter assignmentSubmitterAdapter;
    MyTypeFace myTypeFace;

    public GetAssignmentsSubmitterFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ((TeacherHostActivity) getActivity()).hideTxtAction();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }
    public static GetAssignmentsSubmitterFragment newInstance(Bundle bundleArguments) {
        GetAssignmentsSubmitterFragment getAssignmentsSubmitterFragment = new GetAssignmentsSubmitterFragment();
        getAssignmentsSubmitterFragment.setArguments(bundleArguments);

        Debug.e("test topic id", bundleArguments.getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID));
        return getAssignmentsSubmitterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_assignment_submittor, container, false);

        initGlobal(view);

        return view;
    }


    private void initGlobal(View view) {

        myTypeFace = new MyTypeFace(getActivity());

        tvNoSubmissions = (TextView) view.findViewById(R.id.tv_no_submissions);
        tvSubmittorTitle = (TextView) view.findViewById(R.id.tv_submittor_title);
        rvAssignmentSubmittorList = (RecyclerView) view.findViewById(R.id.recycler_examwise_assignments);
        imgToggleList = (ImageView) view.findViewById(R.id.img_toggle_list);

        tvSubmittorTitle.setTypeface(myTypeFace.getRalewayBold());
        tvSubmittorTitle.setText(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME));
        assignmentSubmitterAdapter = new AssignmentSubmitterAdapter(getActivity());

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
                attribute.setUserId(WebConstants.USER_ID_370);
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

        if(object!=null)
        {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                arrayListAssignments.addAll(responseHandler.getExamSubmission().get(0).getExamsubmittor());
                assignmentSubmitterAdapter.addAll(arrayListAssignments);

                if (arrayListAssignments.size() == 0) {
                    Utility.showView(tvNoSubmissions);
                } else {
                    Utility.hideView(tvNoSubmissions);
                }
            } else {

                Utility.showToast(getString(R.string.web_service_issue), getActivity());
            }
        }
        else
        {
            Debug.e(TAG, "onResponseGetAllSubmission Exception : " + "response object may be returning null");
        }

    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }
}
