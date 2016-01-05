package com.ism.teacher.fragments.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.interfaces.FragmentListener;


/**
 * This fragment is used to display activities related to the user.
 */

public class MyActivityFragment extends Fragment implements TeacherHostActivity.ProfileControllerPresenceListener {

    private static final String TAG = MyActivityFragment.class.getSimpleName();
    private View viewHighlighterTriangle;
    private TeacherHostActivity activityHost;


    private FragmentListener fragListener;

    public static MyActivityFragment newInstance() {
        MyActivityFragment fragBooks = new MyActivityFragment();
        return fragBooks;
    }

    public MyActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myactivity, container, false);

        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {

        viewHighlighterTriangle = (View) rootview.findViewById(R.id.view_highlighter_triangle);
        viewHighlighterTriangle.setVisibility(activityHost.getCurrentRightFragment() == TeacherHostActivity.FRAGMENT_PROFILE_CONTROLLER ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (TeacherHostActivity) activity;
            fragListener = (FragmentListener) activity;
            activityHost.setListenerProfileControllerPresence(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_MY_ACTIVITY);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_MY_ACTIVITY);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onProfileControllerAttached() {
        viewHighlighterTriangle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProfileControllerDetached() {
        viewHighlighterTriangle.setVisibility(View.GONE);
    }
}
