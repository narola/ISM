package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.interfaces.FragmentListener;

/**
 * Created by c161 on --/10/15.
 */
public class TeacherMarkScriptHomeFragment extends Fragment {

    private static final String TAG = TeacherMarkScriptHomeFragment.class.getSimpleName();

    private View view;

    private FragmentListener fragListener;

    public static TeacherMarkScriptHomeFragment newInstance() {
        TeacherMarkScriptHomeFragment teacherQuizHomeFragment = new TeacherMarkScriptHomeFragment();
        return teacherQuizHomeFragment;
    }

    public TeacherMarkScriptHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_markscript_home, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                // fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_TEACHER_OFFICE);
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
                // fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_TEACHER_OFFICE);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

}
