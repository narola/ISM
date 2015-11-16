package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.adapters.MyStudentsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.RequestObject;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.ws.WebserviceWrapper;

import java.util.ArrayList;

/**
 * Created by c75 on 10/11/15.
 */
public class MyStudentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = MyStudentsFragment.class.getSimpleName();
    View rootview;
    RecyclerView rv_mystudentslist;
    MyStudentsAdapter myStudentsAdapter;
    ArrayList<Data> arrayListStudents = new ArrayList<>();


    public static MyStudentsFragment newInstance() {
        MyStudentsFragment myStudentsFragment = new MyStudentsFragment();
        return myStudentsFragment;
    }

    public MyStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_mystudents, container, false);

        initGlobal(rootview);


        return rootview;
    }

    private void initGlobal(View rootview) {
        rv_mystudentslist = (RecyclerView) rootview.findViewById(R.id.rv_mystudentslist);

        myStudentsAdapter = new MyStudentsAdapter(getActivity(),this);
        rv_mystudentslist.setAdapter(myStudentsAdapter);
        rv_mystudentslist.setLayoutManager(new LinearLayoutManager(getActivity()));

        callGetMyStudentsApi();
    }

    private void callGetMyStudentsApi() {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setUserId("319");
            new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebConstants.GET_MY_STUDENTS);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(int api_code, Object object, Exception error) {
        try {
            switch (api_code) {
                case WebConstants.GET_MY_STUDENTS:
                    onResponseMyStudents(object);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseMyStudents(Object object) {

        ResponseObject responseObj = (ResponseObject) object;
        if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {
            arrayListStudents.addAll(responseObj.getData());
            myStudentsAdapter.addAll(arrayListStudents);
        }
    }
}
