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
import android.widget.Button;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.TutorialGroupAdapter;
import com.ism.teacher.interfaces.FragmentListener;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherTutorialGroupFragment extends Fragment {

    private static final String TAG = TeacherTutorialGroupFragment.class.getSimpleName();


    //Views
    private RecyclerView rvTutorialGroup;

    private TextView tvGroupName, tvCourseName, tvGroupScores, tvGroupRank, tvTopic;
    private Button btnScheduleExam;


    //Objects
    private FragmentListener fragListener;

    private TutorialGroupAdapter tutorialGroupAdapter;

    public static TeacherTutorialGroupFragment newInstance() {
        TeacherTutorialGroupFragment teacherTutorialGroupFragment = new TeacherTutorialGroupFragment();
        return teacherTutorialGroupFragment;
    }

    public TeacherTutorialGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tutorial_group_teacher, container, false);

        initGlobal(rootview);
        return rootview;
    }

    public void initGlobal(View rootview) {
        rvTutorialGroup = (RecyclerView) rootview.findViewById(R.id.rv_tutorial_group);

        tvGroupName = (TextView) rootview.findViewById(R.id.tv_group_name);
        tvCourseName = (TextView) rootview.findViewById(R.id.tv_course_name);
        tvGroupScores = (TextView) rootview.findViewById(R.id.tv_group_scores);
        tvGroupRank = (TextView) rootview.findViewById(R.id.tv_group_rank);
        tvTopic = (TextView) rootview.findViewById(R.id.tv_topic);
        btnScheduleExam = (Button) rootview.findViewById(R.id.btn_schedule_exam);

        rvTutorialGroup.setHasFixedSize(true);
        rvTutorialGroup.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        tutorialGroupAdapter = new TutorialGroupAdapter(getActivity());
        rvTutorialGroup.setAdapter(tutorialGroupAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_TEACHER_TUTORIAL_GROUP);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_TEACHER_TUTORIAL_GROUP);
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