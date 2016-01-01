package com.ism.teacher.fragments.assesment;

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
import com.ism.teacher.fragments.GetStudentsFragment;
import com.ism.teacher.ws.model.QuestionPalette;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;


/**
 * This fragment is a container of three frags
 * GetStudents=retrieve list of students attempted
 * SubjectiveQuestionsFragment show list of all subjective questions
 * QuestionPaletteFragment shows list of all question evaluation status.
 */
public class SubjectiveQuestionsContainerFragment extends Fragment {
    private static final String TAG = SubjectiveQuestionsContainerFragment.class.getSimpleName();
    private View view;
    private FrameLayout flGetsubjectiveAssignmentContainerLeft, flGetsubjectiveAssignmentContainerMiddle, flGetsubjectiveAssignmentContainerRight;

    //new code
    public GetStudentsFragment getStudentsFragment;
    public SubjectiveQuestionsFragment subjectiveQuestionsFragment;
    public QuestionPaletteFragment questionPaletteFragment;

    public SubjectiveQuestionsContainerFragment() {
    }


    public static SubjectiveQuestionsContainerFragment newInstance(Bundle bundleArguments) {
        SubjectiveQuestionsContainerFragment subjectiveQuestionsContainerFragment = new SubjectiveQuestionsContainerFragment();
        subjectiveQuestionsContainerFragment.setArguments(bundleArguments);
        return subjectiveQuestionsContainerFragment;
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

        getStudentsFragment = GetStudentsFragment.newInstance(this);
        subjectiveQuestionsFragment = new SubjectiveQuestionsFragment(this);
        questionPaletteFragment = new QuestionPaletteFragment(this);


        flGetsubjectiveAssignmentContainerLeft = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_left);
        flGetsubjectiveAssignmentContainerMiddle = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_middle);
        flGetsubjectiveAssignmentContainerRight = (FrameLayout) view.findViewById(R.id.fl_getsubjective_assignment_container_right);

        //new code starts

        getChildFragmentManager().beginTransaction().replace(R.id.fl_getsubjective_assignment_container_left, getStudentsFragment).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fl_getsubjective_assignment_container_middle, subjectiveQuestionsFragment).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.fl_getsubjective_assignment_container_right, questionPaletteFragment).commit();

    }


    /*this is to load data for student evaluation*/
    public void loadStudentEvaluationData(String studentId) {
        subjectiveQuestionsFragment.loadStudentEvaluationData();
    }


    /*this is to refresh adapter on the click of next and previous button*/
    public void setBundleArgumentForStudent(int position) {
        getStudentsFragment.setBundleArgument(position);
    }

    /*this is to set the question status data in question palette*/
    public void setQuestionStatusData(ArrayList<Questions> arrListQuestions, ArrayList<QuestionPalette> arrListQuestionPalette) {
        questionPaletteFragment.setQuestionStatusData(arrListQuestions, arrListQuestionPalette);
    }

    /*this is to scroll to specific question content on the click of question palette*/
    public void scrollToSpecificQuestion(int position) {
        subjectiveQuestionsFragment.scrollToSpecificQuestion(position);
    }


    public void hideGetStudentsandPalleteFragment() {
        flGetsubjectiveAssignmentContainerLeft.setVisibility(View.GONE);
        flGetsubjectiveAssignmentContainerRight.setVisibility(View.GONE);
    }

    public void showGetStudentsandPalleteFragment() {
        flGetsubjectiveAssignmentContainerLeft.setVisibility(View.VISIBLE);
        flGetsubjectiveAssignmentContainerRight.setVisibility(View.VISIBLE);
    }

    /*
    this is to set title details in subjective questions fragment from bundle arguments.
     */
    public void setTitleDetails() {
        subjectiveQuestionsFragment.setTitleDetails();
    }


    public Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }
}
