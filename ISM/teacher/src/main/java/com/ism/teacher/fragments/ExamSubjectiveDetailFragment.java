package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHomeActivity;
import com.ism.teacher.interfaces.FragmentListener;


/**
 * Created by c162 on 06/11/15.
 */
public class ExamSubjectiveDetailFragment extends Fragment {
    private static final String TAG = ExamSubjectiveDetailFragment.class.getSimpleName();
    private View view;
    private FrameLayout fragment_container_left, fragment_container_right;
    private FragmentListener fragListener;

    public static final int FRAGMENT_MY_STUDENTS = 0;
    public static final int FRAGMENT_SUBJECTIVE_QUESTIONS = 1;

    public ExamSubjectiveDetailFragment() {
        //required
    }

    public static ExamSubjectiveDetailFragment newInstance() {
        ExamSubjectiveDetailFragment fragment = new ExamSubjectiveDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exam_subjective_details, container, false);

        initGlobal(view);

        Log.e(TAG, "subjective called");
        return view;
    }


    private void initGlobal(View view) {

        fragment_container_left = (FrameLayout) view.findViewById(R.id.fragment_container_left);
        fragment_container_right = (FrameLayout) view.findViewById(R.id.fragment_container_right);

        loadFragmentInLeftContainer();
        loadFragmentInRightContainer();
    }

    public void loadFragmentInLeftContainer() {
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_left, MyStudentsFragment.newInstance()).commit();

    }

    public void loadFragmentInRightContainer() {

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_right, SubjectiveQuestionsFragment.newInstance()).commit();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_EXAM_SUBJECTIVE_DETAILS);
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
                fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_EXAM_SUBJECTIVE_DETAILS);
                // callLikeFeed();
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

}
