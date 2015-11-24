package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.MyStudentsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;

/**
 * Created by c75 on 10/11/15.
 */
public class MyStudentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = MyStudentsFragment.class.getSimpleName();

    //views
    View rootview;
    RecyclerView rv_mystudentslist;
    MyStudentsAdapter myStudentsAdapter;
    EditText etSearchStudents;

    //test
    String student_id_to_highlight="";

    public static MyStudentsFragment newInstance() {
        MyStudentsFragment myStudentsFragment = new MyStudentsFragment();
        return myStudentsFragment;
    }

    public MyStudentsFragment() {
        // Required empty public constructor
    }

    public MyStudentsFragment(String student_id_to_highlight) {
       this.student_id_to_highlight=student_id_to_highlight;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_mystudents, container, false);

        initGlobal(rootview);

        return rootview;
    }

    private void initGlobal(View rootview) {
        rv_mystudentslist = (RecyclerView) rootview.findViewById(R.id.rv_mystudentslist);
        etSearchStudents = (EditText) rootview.findViewById(R.id.et_search_students);


        callGetExamSubmissionApi();
    }

    private void callGetExamSubmissionApi() {
        try {
            Attribute attribute=new Attribute();
            attribute.setExamId("9");
            attribute.setUserId("340");
            attribute.setRole("3");

            ((TeacherHostActivity) getActivity()).startProgress();
            new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_EXAM_SUBMISSION);


        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(int api_code, Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            switch (api_code) {
                case WebConstants.GET_ALL_EXAM_SUBMISSION:
                    onResponseMyStudents(object);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseMyStudents(Object object) {

        ResponseHandler resObjStudentAttempted = (ResponseHandler) object;
        if (resObjStudentAttempted.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {

            myStudentsAdapter = new MyStudentsAdapter(student_id_to_highlight,resObjStudentAttempted, getActivity(), this);
            rv_mystudentslist.setAdapter(myStudentsAdapter);
            rv_mystudentslist.setLayoutManager(new LinearLayoutManager(getActivity()));


        }
    }
}
