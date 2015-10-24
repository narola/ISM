package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ism.R;
import com.ism.interfaces.FragmentListener;

import com.ism.teacher.model.GetAllFeedsTeacherRequest;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.adapters.AllFeedsAdapter;
import com.ism.teacher.helper.ConnectionDetector;
import com.ism.teacher.login.TeacherHomeActivity;
import com.ism.teacher.ws.WebserviceWrapper;

import java.util.ArrayList;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherHomeFragment.class.getSimpleName();

    private View rootview;
    // private ListView listPostFeed;
    private RecyclerView recyclerviewPost;
    private FragmentListener fragListener;
    ConnectionDetector connectionDetector;

    AllFeedsAdapter allFeedsAdapter;


    public static TeacherHomeFragment newInstance() {
        TeacherHomeFragment fragTeacherHome = new TeacherHomeFragment();
        return fragTeacherHome;
    }

    public TeacherHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectionDetector = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_post_home, container, false);

        recyclerviewPost = (RecyclerView) rootview.findViewById(R.id.recyclerview_post);
        if (connectionDetector.isConnectingToInternet()) {
            callAllFeedsApi();

        }

        Log.e(TAG, "called");
        return rootview;
    }

    private void callAllFeedsApi() {
        try {
            GetAllFeedsTeacherRequest getAllFeedsTeacherRequest = new GetAllFeedsTeacherRequest();
            getAllFeedsTeacherRequest.setUser_id("141");

            new WebserviceWrapper(getActivity(), getAllFeedsTeacherRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebserviceWrapper.GET_ALL_FEEDS);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_TEACHER_HOME);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_TEACHER_HOME);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {

        ResponseObject responseObj = (ResponseObject) object;
        if (responseObj.getStatus().equalsIgnoreCase("success")) {

            if (responseObj.getData().size() > 0) {
                allFeedsAdapter = new AllFeedsAdapter(getActivity(), responseObj.getData());
                recyclerviewPost.setAdapter(allFeedsAdapter);
                recyclerviewPost.setLayoutManager(new LinearLayoutManager(getActivity()));
//                allFeedsAdapter.notifyDataSetChanged();
            }

        } else {
            Toast.makeText(getActivity(), " Not Successful!!!", Toast.LENGTH_SHORT).show();
        }
    }

}