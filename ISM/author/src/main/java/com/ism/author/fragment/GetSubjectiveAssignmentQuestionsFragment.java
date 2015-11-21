package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.object.MyTypeFace;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.Data;
import com.ism.author.model.FragmentArgument;

import java.util.ArrayList;

/**
 * Created by c166 on 16/11/15.
 */
public class GetSubjectiveAssignmentQuestionsFragment extends Fragment {

    private static final String TAG = GetSubjectiveAssignmentQuestionsFragment.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;
    private FragmentListener fragListener;
    public GetStudentsFragment getStudentsFragment;
    public GetSubjectiveQuestionsFragment getSubjectiveQuestionsFragment;
    public QuestionPaletteFragment questionPaletteFragment;
    private FrameLayout flGetsubjectiveAssignmentContainerLeft, flGetsubjectiveAssignmentContainerMiddle,
            flGetsubjectiveAssignmentContainerRight;
    private FragmentArgument fragmentArgument;

    public static GetSubjectiveAssignmentQuestionsFragment newInstance(FragmentArgument fragmentArgument) {
        GetSubjectiveAssignmentQuestionsFragment getSubjectiveAssignmentQuestionsFragment = new GetSubjectiveAssignmentQuestionsFragment();
        getSubjectiveAssignmentQuestionsFragment.fragmentArgument = fragmentArgument;

        return getSubjectiveAssignmentQuestionsFragment;
    }

    public GetSubjectiveAssignmentQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_getsubjective_assignment_questions, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        getStudentsFragment = new GetStudentsFragment(this);
        getSubjectiveQuestionsFragment = new GetSubjectiveQuestionsFragment(this);
        questionPaletteFragment = new QuestionPaletteFragment(this);

        flGetsubjectiveAssignmentContainerLeft = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_left);
        flGetsubjectiveAssignmentContainerMiddle = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_middle);
        flGetsubjectiveAssignmentContainerRight = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_right);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_getsubjective_assignment_container_left, getStudentsFragment)
                .commit();


        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_getsubjective_assignment_container_middle, getSubjectiveQuestionsFragment)
                .commit();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_getsubjective_assignment_container_right, questionPaletteFragment)
                .commit();

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public FragmentArgument getFragmnetArgument() {
        return fragmentArgument;

    }

    public void loadStudentEvaluationData(String studentId) {
        getFragmnetArgument().getFragmentArgumentObject().setStudentId(studentId);
        getSubjectiveQuestionsFragment.loadStudentEvaluationData();
    }


    public void refreshAdapterForStudentNavigation() {
        getStudentsFragment.refreshAdapterForStudentNavigation();
    }

    public void setQuestionStatusData(ArrayList<Data> data) {

        questionPaletteFragment.setQuestionStatusData(data);

    }

    public void scrollToSpecificQuestion(int position) {

        getSubjectiveQuestionsFragment.scrollToSpecificQuestion(position);

    }
}
