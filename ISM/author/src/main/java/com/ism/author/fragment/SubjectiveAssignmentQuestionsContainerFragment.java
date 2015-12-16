package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.author.R;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.MyStudentListAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.model.QuestionPalette;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c166 on 16/11/15.
 */
public class SubjectiveAssignmentQuestionsContainerFragment extends Fragment {

    private static final String TAG = SubjectiveAssignmentQuestionsContainerFragment.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;
    private FragmentListener fragListener;
    public GetStudentsFragment getStudentsFragment;
    public SubjectiveQuestionsFragment subjectiveQuestionsFragment;
    public QuestionPaletteFragment questionPaletteFragment;
    private FrameLayout flGetsubjectiveAssignmentContainerLeft, flGetsubjectiveAssignmentContainerMiddle,
            flGetsubjectiveAssignmentContainerRight;


    public static SubjectiveAssignmentQuestionsContainerFragment newInstance() {
        SubjectiveAssignmentQuestionsContainerFragment subjectiveAssignmentQuestionsContainerFragment = new
                SubjectiveAssignmentQuestionsContainerFragment();
        return subjectiveAssignmentQuestionsContainerFragment;
    }

    public SubjectiveAssignmentQuestionsContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subjective_assignment_questions_container, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        getStudentsFragment = new GetStudentsFragment(this);
        subjectiveQuestionsFragment = new SubjectiveQuestionsFragment(this);
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
                .replace(R.id.fl_getsubjective_assignment_container_middle, subjectiveQuestionsFragment)
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
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    /*this is to load data for student evaluation*/
    public void loadStudentEvaluationData(String studentId) {
        subjectiveQuestionsFragment.loadStudentEvaluationData();
    }


    /*this is to refresh adapter on the click of next and previous button*/
//    public void setBundle() {
//        getStudentsFragment.setBundle();
//    }

    public void setBundleArgumentForStudent(int position) {
        getStudentsFragment.setBundleArgument(position);
    }

    /*this is to set the question status data in question palette*/
//    public void setQuestionStatusData(ArrayList<Evaluation> evaluations) {
//        questionPaletteFragment.setQuestionStatusData(evaluations);
//    }


    public void setQuestionStatusData(ArrayList<Questions> arrListQuestions, ArrayList<QuestionPalette> arrListQuestionPalette) {
        questionPaletteFragment.setQuestionStatusData(arrListQuestions, arrListQuestionPalette);
    }

    /*this is to scroll to specific question content on the click of question palette*/
    public void scrollToSpecificQuestion(int position) {
        subjectiveQuestionsFragment.scrollToSpecificQuestion(position);
    }


    /*this is for back navigation*/
    public void onBackClick() {

        getBundleArguments().remove(MyStudentListAdapter.ARG_ARR_LIST_STUDENTS);
        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER);
    }

    public Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }
}
