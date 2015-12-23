package com.ism.author.fragment.mydesk;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.MyDeskAssignmentsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Assignment;

import java.util.ArrayList;

/**
 * Created by c162 on 28/11/15.
 */
public class MyDeskAssignmentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = MyDeskAssignmentsFragment.class.getSimpleName();
    private View view;
    private RecyclerView rvAssignmentsList;
    private MyDeskAssignmentsAdapter assignmentAdapter;
    private Fragment mFragment;
    private ArrayList<Assignment> arrListAssignments = new ArrayList<Assignment>();
    private TextView tvNoDataMsg;


    public static MyDeskAssignmentsFragment newInstance(Fragment fragment) {
        MyDeskAssignmentsFragment myDeskAssignmentsFragment = new MyDeskAssignmentsFragment();
        myDeskAssignmentsFragment.mFragment = fragment;
        return myDeskAssignmentsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignments, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());

        rvAssignmentsList = (RecyclerView) view.findViewById(R.id.rv_assignments_list);
        assignmentAdapter = new MyDeskAssignmentsAdapter(mFragment, getActivity());
        rvAssignmentsList.setHasFixedSize(true);
        rvAssignmentsList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAssignmentsList.setAdapter(assignmentAdapter);


        callApiGetAllExams();


    }


    private void callApiGetAllExams() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
//                attribute.setUserId(Global.strUserId);
//                attribute.setRole(Global.role);
                attribute.setBookId("5");

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLASSIGNMENTS);
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
                case WebConstants.GETALLASSIGNMENTS:
                    onResponseGetAllAssignments(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAllAssignments(Object object, Exception error) {

        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getAssignment().size() > 0) {
                        arrListAssignments.addAll(responseHandler.getAssignment());
                        assignmentAdapter.addAll(arrListAssignments);
                        assignmentAdapter.notifyDataSetChanged();

                        tvNoDataMsg.setVisibility(View.GONE);
                    } else {
                        tvNoDataMsg.setVisibility(View.VISIBLE);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllAssignments api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllAssignments Exception : " + e.toString());
        }
    }

}
