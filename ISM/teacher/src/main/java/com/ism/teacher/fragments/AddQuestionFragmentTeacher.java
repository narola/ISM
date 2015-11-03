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
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.model.Data;

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

    public PreviewQuestionFragment previewQuestionFragment;
    public QuestionListFragment questionListFragment;
    public QuestionAddEditFragment questionAddEditFragment;

    public AddQuestionFragmentTeacher() {

    }

    private int fragment;
    private static int current_fragment;
    private static final String ARG_FRAGMENT = "fragment";

    public static AddQuestionFragmentTeacher newInstance(int fragment) {
        AddQuestionFragmentTeacher addQuestionFragmentTeacher = new AddQuestionFragmentTeacher();
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
                .add(R.id.fl_addquestionfragment_container_left, new QuestionListFragment(this))
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

        //  loadFragment(FRAGMENT_QUESTIONLIST);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void flipCard() {

//        if (!mShowingBack) {
//            mShowingBack = true;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.fl_addquestionfragment_container_left, new AddNewQuestionFromAssignment(this))
                .addToBackStack(null)
                .commit();
        //loadFragment(FRAGMENT_QUESTIONADDEDIT);

//        } else {
//
//            getFragmentManager().popBackStack();
//            mShowingBack = false;

//        }


    }


    private void loadFragmentInRightContainer() {

        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_addquestionfragment_container_right, previewQuestionFragment).commit();
        } catch (Exception e) {
            Debug.e(TAG, "loadFragment Exception : " + e.toString());

        }

    }


    public void addQuestionToPreviewFragment() {
        previewQuestionFragment.addQuestionsToPreviewFragment();
    }
}
