package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.AssignmentAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.RequestObject;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;

import java.util.ArrayList;

/**
 * Created by c166 on 09/11/15.
 */
public class AssessmentFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AssessmentFragment.class.getSimpleName();
    private View view;
    RecyclerView rvAssignmentList;
    AssignmentAdapter assignmentAdapter;
    ArrayList<Data> listOfAssignment = new ArrayList<Data>();
    MyTypeFace myTypeFace;

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

        callApiGetAllAssignments();

    }

    private void callApiGetAllAssignments() {
        if (Utility.isOnline(getActivity())) {
            try {
                RequestObject request = new RequestObject();
                request.setUserId("370");
                request.setRole(String.valueOf(AppConstant.AUTHOR_ROLE_ID));
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_ASSIGNMENTS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GET_ALL_ASSIGNMENTS:
                    onResponseGetAllAssignments(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAllAssignments(Object object, Exception error) {

        try {
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    listOfAssignment.addAll(responseObj.getData());
                    assignmentAdapter.addAll(listOfAssignment);
                    assignmentAdapter.notifyDataSetChanged();
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllAssignments api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllAssignments Exception : " + e.toString());
        }
    }

}
