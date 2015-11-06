package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.activity.TeacherHomeActivity;


/**
 * Created by c161 on --/10/15.
 */
public class UserProfileFragment extends Fragment {

    private static final String TAG = UserProfileFragment.class.getSimpleName();

    private View rootview;


    private FragmentListener fragListener;

    public static UserProfileFragment newInstance() {
        UserProfileFragment fragChat = new UserProfileFragment();
        return fragChat;
    }

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        //initGlobal(rootview);

        Log.e(TAG,"called");
        return rootview;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_USER_PROFILE);
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
                fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_USER_PROFILE);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

}