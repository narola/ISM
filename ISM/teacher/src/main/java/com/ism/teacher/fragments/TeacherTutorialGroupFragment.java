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
import com.ism.teacher.activity.TeacherHostActivity;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherTutorialGroupFragment extends Fragment {

    private static final String TAG = TeacherTutorialGroupFragment.class.getSimpleName();

    private View rootview;


    private FragmentListener fragListener;

    public static TeacherTutorialGroupFragment newInstance() {
        TeacherTutorialGroupFragment fragChat = new TeacherTutorialGroupFragment();
        return fragChat;
    }

    public TeacherTutorialGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tutorial_group_teacher, container, false);

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
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_USER_PROFILE);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_USER_PROFILE);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }
}