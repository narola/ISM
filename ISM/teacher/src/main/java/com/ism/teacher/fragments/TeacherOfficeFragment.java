package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHomeActivity;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.interfaces.FragmentListener;

/**
 * Created by c161 on --/10/15.
 */
public class TeacherOfficeFragment extends Fragment implements TeacherHomeActivity.HostListener, TeacherHomeActivity.AddTopicsListener {

    private static final String TAG = TeacherOfficeFragment.class.getName();

    private View view;

    private FragmentListener fragListener;

    private static final String ARG_FRAGMENT = "fragment";


    public static final int FRAGMENT_CLASSWALL = 0;
    public static final int FRAGMENT_NOTES = 1;
    public static final int FRAGMENT_QUIZ = 2;
    public static final int FRAGMENT_MARK_SCRIPT = 3;
    public static final int FRAGMENT_RESULTS = 4;
    public static final int FRAGMENT_PROGRESS_REPORT = 5;

    public static final int FRAGMENT_EXAM_WISE_STUDENTS=6;

    private int fragment;
    private static int current_fragment;


    TeacherQuizHomeFragment teacherQuizHomeFragment;
//    TeacherExamWiseAssignments teacherExamWiseAssignments;

    public static TeacherOfficeFragment newInstance(int fragment) {
        TeacherOfficeFragment fragClassroom = new TeacherOfficeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT, fragment);
        fragClassroom.setArguments(args);
        return fragClassroom;
    }

    public TeacherOfficeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragment = getArguments().getInt(ARG_FRAGMENT);
            if (fragListener != null) {
                fragListener.onFragmentAttached(fragment);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_office_home, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        loadFragment(FRAGMENT_CLASSWALL);

        teacherQuizHomeFragment=new TeacherQuizHomeFragment(this);
//        teacherExamWiseAssignments=new TeacherExamWiseAssignments(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            fragListener.onFragmentAttached(fragment);
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(fragment);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onControllerMenuItemClicked(int position) {
        loadFragment(position);
    }

    public void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_CLASSWALL:
                    current_fragment = FRAGMENT_CLASSWALL;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherClassWallFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_CLASSWALL).commit();
                    break;
                case FRAGMENT_NOTES:
                    current_fragment = FRAGMENT_NOTES;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherNoteHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_NOTES).commit();
                    break;
                case FRAGMENT_QUIZ:
                    current_fragment = FRAGMENT_QUIZ;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, teacherQuizHomeFragment).commit();
                    break;
                case FRAGMENT_MARK_SCRIPT:
                    current_fragment = FRAGMENT_MARK_SCRIPT;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherMarkScriptHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_MARKSCRIPT).commit();
                    break;
                case FRAGMENT_RESULTS:
                    current_fragment = FRAGMENT_RESULTS;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherResultsHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_RESULTS).commit();

                    break;
                case FRAGMENT_PROGRESS_REPORT:
                    current_fragment = FRAGMENT_PROGRESS_REPORT;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherProgressReportHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_PROGRESS_REPORT).commit();

                    break;
                /*case FRAGMENT_EXAM_WISE_STUDENTS:
                    current_fragment = FRAGMENT_EXAM_WISE_STUDENTS;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, teacherExamWiseAssignments).commit();

                    break;*/


//                case FRAGMENT_ADD_ASSIGNMENT:
//                    current_fragment = FRAGMENT_ADD_ASSIGNMENT;
//                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, AddAssignmentFragment.newInstance()).commit();
//                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "loadFragmentInMainContainer Exception : " + e.toString());
        }
    }

    public static int getCurrentChildFragment() {
        return current_fragment;
    }

    @Override
    public void addTopic(int position) {
        loadAddTopics(position);
    }

    private void loadAddTopics(int addTopic) {
        try {
            switch (addTopic) {
                case FRAGMENT_CLASSWALL:
                    //getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherClassWallFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_CLASSWALL).commit();
                    break;
                case FRAGMENT_NOTES:
                    //getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherNoteHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_NOTES).commit();
                    Utility.showToast("teacher notes test", getActivity());
                    break;
                case FRAGMENT_QUIZ:
                    Utility.showToast("teacher quiz", getActivity());
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, AssignmentAddNewFragment.newInstance()).commit();
                    break;
                case FRAGMENT_MARK_SCRIPT:
                    // getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherMarkScriptHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_MARKSCRIPT).commit();
                    break;
                case FRAGMENT_RESULTS:
//                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherResultsHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_RESULTS).commit();
                    break;
                case FRAGMENT_PROGRESS_REPORT:
//                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, TeacherProgressReportHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_PROGRESS_REPORT).commit();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "loadAddTopics Exception : " + e.toString());
        }
    }

}
