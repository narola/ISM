package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.adapters.AssignmentSubjectsAdapter;
import com.ism.teacher.interfaces.FragmentListener;

/**
 * Created by c161 on --/10/15.
 */
public class TeacherQuizHomeFragment extends Fragment {

    private static final String TAG = TeacherQuizHomeFragment.class.getSimpleName();

    private View view;

    private FragmentListener fragListener;

    private RecyclerView recyclerAssignmentSubjects;
    AssignmentSubjectsAdapter assignmentSubjectsAdapter;

    public static TeacherQuizHomeFragment newInstance() {
        TeacherQuizHomeFragment teacherQuizHomeFragment = new TeacherQuizHomeFragment();
        return teacherQuizHomeFragment;
    }

    public TeacherQuizHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment_home, container, false);

        recyclerAssignmentSubjects = (RecyclerView) view.findViewById(R.id.recycler_assignment_subjects);
        initGlobal();

        return view;
    }

    private void initGlobal() {
//        assignmentSubjectsAdapter = new AssignmentSubjectsAdapter();
        recyclerAssignmentSubjects.setHasFixedSize(true);
        recyclerAssignmentSubjects.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerAssignmentSubjects.setAdapter(new AssignmentSubjectsAdapter());
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
