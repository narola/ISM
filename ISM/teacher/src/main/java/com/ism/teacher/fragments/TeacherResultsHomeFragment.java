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


/**
 * Created by c161 on --/10/15.
 */
public class TeacherResultsHomeFragment extends Fragment {

    private static final String TAG = TeacherResultsHomeFragment.class.getSimpleName();

    private View view;

    private FragmentListener fragListener;

    public static TeacherResultsHomeFragment newInstance() {
        TeacherResultsHomeFragment teacherQuizHomeFragment = new TeacherResultsHomeFragment();
        return teacherQuizHomeFragment;
    }

    public TeacherResultsHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_results_home, container, false);

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
                // fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_TEACHER_OFFICE);
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
                // fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_TEACHER_OFFICE);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

}
