package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ism.R;
import com.ism.interfaces.FragmentListener;
import com.ism.teacher.helper.ConnectionDetector;
import com.ism.teacher.login.TeacherHomeActivity;

/**
 * Created by c161 on --/10/15.
 */
public class TeacherHomeFragment extends Fragment {

    private static final String TAG = TeacherHomeFragment.class.getSimpleName();

    private View rootview;
    private ListView listPostFeed;
    private FragmentListener fragListener;
    ConnectionDetector connectionDetector;

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

        listPostFeed = (ListView) rootview.findViewById(R.id.list_post_feed);
        if (connectionDetector.isConnectingToInternet()) {
            callAllFeedsApi();
        }

        Log.e(TAG, "called");
        return rootview;

    }

    private void callAllFeedsApi() {

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

}