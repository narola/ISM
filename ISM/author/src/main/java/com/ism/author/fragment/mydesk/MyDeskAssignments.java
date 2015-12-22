package com.ism.author.fragment.mydesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.userprofile.AssignmentsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;

/**
 * Created by c162 on 28/11/15.
 */
public class MyDeskAssignments extends Fragment implements WebserviceWrapper.WebserviceResponse{
    private static final String TAG = MyDeskAssignments.class.getSimpleName();
    private View view;
    private AuthorHostActivity activityHost;
    private GridView gridView;
    private AssignmentsAdapter arrayList;


    public MyDeskAssignments() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignments, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        gridView = (GridView) view.findViewById(R.id.gridview);// The number of Columns
        callApiForGetAllExam();

    }

    private void callApiForGetAllExam() {
        try {
            if (Utility.isConnected(getActivity())) {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setRole(Global.role);
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GETALLEXAM);
            } else {
                Utility.alertOffline(getActivity());
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiForGetAllExam Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            //   activityHost.setListenerHostAboutMe(this);
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    public static MyDeskAssignments newInstance() {
        return new MyDeskAssignments();
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        ((AuthorHostActivity) getActivity()).hideProgress();
        try {
            switch (apiCode) {
                case WebConstants.GETALLEXAM: {
                    onResponseGetAllExam(object, error);
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "onResponse Exceptions :" + e.toString() + "");
        }
    }

    private void onResponseGetAllExam(Object object, Exception error) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (object != null) {
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "onResponseGetAllExam status : " + WebConstants.SUCCESS);
                    if (responseHandler.getExams().size() != 0) {
                        arrayList = new AssignmentsAdapter(getActivity(), this);
                        gridView.setAdapter(arrayList);
                    }
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseGetAllExam status : " + WebConstants.FAILED);
                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseGetAllExam error : " + error.toString());
            }
        } catch (Exception e) {
            Debug.i(TAG, "onResponseGetAllExam Exception : " + e.getLocalizedMessage());
        }
    }

}
