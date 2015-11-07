package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.interfaces.FragmentListener;

/**
 * Created by c75 on 31/10/15.
 */
public class AddQuestionFragmentTeacher extends Fragment {
    private static final String TAG = AddQuestionFragmentTeacher.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;

    private FragmentListener fragListener;

    FrameLayout fl_addquestionfragment_container_left, fl_addquestionfragment_container_right;
    public static final int FRAGMENT_QUESTIONLIST = 0, FRAGMENT_QUESTIONADDEDIT = 1, FRAGMENT_PREVIEWQUESTION = 2, FRAGMENT_ADD_NEW_QUESTION = 3;
    private boolean mShowingBack = false;
    private String exam_id = "";
    private static final String ARG_FRAGMENT = "fragment";

    public PreviewQuestionFragment previewQuestionFragment;
    public QuestionListFragment questionListFragment;
    public QuestionAddEditFragment questionAddEditFragment;


    public AddQuestionFragmentTeacher(String examid) {

        this.exam_id = examid;
    }


    public static AddQuestionFragmentTeacher newInstance(int fragment, String examid) {
        AddQuestionFragmentTeacher addQuestionFragmentTeacher = new AddQuestionFragmentTeacher(examid);
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT, fragment);
        addQuestionFragmentTeacher.setArguments(args);


        return addQuestionFragmentTeacher;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_question_teacher, container, false);
        initGlobal();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_addquestionfragment_container_left, questionListFragment)
                .addToBackStack(String.valueOf(FRAGMENT_QUESTIONLIST))
                .commit();
        loadFragmentInRightContainer();

        return view;
    }

    private void initGlobal() {

        previewQuestionFragment = new PreviewQuestionFragment(this);
        questionListFragment = new QuestionListFragment(this);
        questionAddEditFragment = new QuestionAddEditFragment(this);

        fl_addquestionfragment_container_left = (FrameLayout) view.findViewById(R.id.fl_addquestionfragment_container_left);
        fl_addquestionfragment_container_right = (FrameLayout) view.findViewById(R.id.fl_addquestionfragment_container_right);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void flipCard(Bundle dataForEdit) {
        if (dataForEdit != null) {

            questionAddEditFragment.setArguments(dataForEdit);
        }

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.fl_addquestionfragment_container_left, questionAddEditFragment)
                .addToBackStack(null)
                .commit();
    }


    private void loadFragmentInRightContainer() {

        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_addquestionfragment_container_right, previewQuestionFragment).commit();
        } catch (Exception e) {
            Debug.e(TAG, "loadFragment Exception : " + e.toString());

        }

    }

    public String getExam_id() {
        if (!exam_id.equalsIgnoreCase("")) {
            return exam_id;
        } else {
            return "";
        }
    }

}
