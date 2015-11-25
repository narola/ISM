package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
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


    //test for opening evaluation for specific students after calling subjective questions

    Context context;
    public String examid_from_param = "";
    public String studentid_from_param = "";
    public String studentname_from_param = "";
    public boolean callEvaluationApiFlag = false;

    SubjectiveQuestionsFragment subjectiveQuestionsFragment;
    MyStudentsFragment myStudentsFragment;

    public ExamSubjectiveDetailFragment(Context context, String examid, String studentid, boolean flag,String studentname_from_param) {
        this.context = context;
        this.examid_from_param = examid;
        this.studentid_from_param = studentid;
        this.callEvaluationApiFlag = flag;
        this.studentname_from_param=studentname_from_param;
    }

    public ExamSubjectiveDetailFragment() {
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

        if (callEvaluationApiFlag) {

            // loadMyStudentsFragmentWithHighlightStudent();
            loadSubjectiveQuesionWithEvaluation(studentname_from_param);
            Log.i(TAG, "subjective ques with evaluation");
        } else {
            loadOnlySubjectiveQuestion();

        }

        loadMyStudentsFragment();

    }

    /**
     * Load simple container frag to show student and subjective question list.
     * Evaluation start after teacher click on specific student list
     */
    public void loadMyStudentsFragment() {
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_left, MyStudentsFragment.newInstance()).commit();
    }

    public void loadOnlySubjectiveQuestion() {

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_right, SubjectiveQuestionsFragment.newInstance()).commit();
    }


    /**
     * send studentid,examid and flag to call evaluation api after subjective question api response to bind evaluation response.
     */

    public void loadSubjectiveQuesionWithEvaluation(String studentname_from_param) {
        subjectiveQuestionsFragment = new SubjectiveQuestionsFragment(studentid_from_param, WebConstants.EXAM_ID_11_SUBJECTIVE, true,studentname_from_param);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_right, subjectiveQuestionsFragment).commit();

    }

    /**
     * send student id to highlight the specific student because this student's evaluation started
     */


    public void loadMyStudentsFragmentWithHighlightStudent() {
        myStudentsFragment = new MyStudentsFragment(studentid_from_param);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_left, myStudentsFragment).commit();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_EXAM_SUBJECTIVE_DETAILS);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_EXAM_SUBJECTIVE_DETAILS);
                // callLikeFeed();
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

}
