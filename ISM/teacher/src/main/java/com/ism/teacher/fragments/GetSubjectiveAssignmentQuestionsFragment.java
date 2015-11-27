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
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.ws.model.Evaluation;

import java.util.ArrayList;


/**
 * Created by c162 on 06/11/15.
 */
public class GetSubjectiveAssignmentQuestionsFragment extends Fragment {
    private static final String TAG = GetSubjectiveAssignmentQuestionsFragment.class.getSimpleName();
    private View view;
    private FrameLayout flGetsubjectiveAssignmentContainerLeft, flGetsubjectiveAssignmentContainerMiddle, flGetsubjectiveAssignmentContainerRight;
    private FragmentListener fragListener;

    public static final int FRAGMENT_MY_STUDENTS = 0;
    public static final int FRAGMENT_SUBJECTIVE_QUESTIONS = 1;


    //new code
    public GetStudentsFragment getStudentsFragment;
    public GetSubjectiveQuestionsFragment getSubjectiveQuestionsFragment;
    public QuestionPaletteFragment questionPaletteFragment;

    public GetSubjectiveAssignmentQuestionsFragment() {
    }


    public static GetSubjectiveAssignmentQuestionsFragment newInstance(Bundle bundleArguments) {
        GetSubjectiveAssignmentQuestionsFragment getSubjectiveAssignmentQuestionsFragment = new GetSubjectiveAssignmentQuestionsFragment();
        getSubjectiveAssignmentQuestionsFragment.setArguments(bundleArguments);
        return getSubjectiveAssignmentQuestionsFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TeacherHostActivity) getActivity()).hideRightContainerFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((TeacherHostActivity) getActivity()).showRightContainerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_getsubjective_assignment_questions, container, false);

        initGlobal(view);

        Log.e(TAG, "subjective called");
        return view;
    }


    private void initGlobal(View view) {

        getStudentsFragment = new GetStudentsFragment(this);
        getSubjectiveQuestionsFragment = new GetSubjectiveQuestionsFragment(this);
        questionPaletteFragment = new QuestionPaletteFragment(this);


        flGetsubjectiveAssignmentContainerLeft = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_left);
        flGetsubjectiveAssignmentContainerMiddle = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_middle);
        flGetsubjectiveAssignmentContainerRight = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_right);

        //new code starts

        getChildFragmentManager().beginTransaction().replace(R.id.fl_getsubjective_assignment_container_left, getStudentsFragment).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fl_getsubjective_assignment_container_middle, getSubjectiveQuestionsFragment).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fl_getsubjective_assignment_container_right, questionPaletteFragment).commit();


    }


    /*this is to load data for student evaluation*/
    public void loadStudentEvaluationData(String studentId) {
        getSubjectiveQuestionsFragment.loadStudentEvaluationData();
    }


    /*this is to refresh adapter on the click of next and previous button*/
    public void setBundleArgumentForStudent(int position) {
        getStudentsFragment.setBundleArgument(position);
    }

    /*this is to set the question status data in question palette*/
    public void setQuestionStatusData(ArrayList<Evaluation> evaluations) {
        //   questionPaletteFragment.setQuestionStatusData(evaluations);
    }

    /*this is to scroll to specific question content on the click of question palette*/
    public void scrollToSpecificQuestion(int position) {
        getSubjectiveQuestionsFragment.scrollToSpecificQuestion(position);
    }


}
