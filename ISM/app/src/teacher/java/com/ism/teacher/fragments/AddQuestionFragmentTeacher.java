package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.interfaces.FragmentListener;
import com.ism.object.MyTypeFace;
import com.ism.R;
import com.ism.teacher.login.TeacherHomeActivity;
import com.ism.utility.Debug;

/**
 * Created by c75 on 31/10/15.
 */
public class AddQuestionFragmentTeacher extends Fragment {
    private static final String TAG = AddQuestionFragmentTeacher.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;

    private FragmentListener fragListener;

    FrameLayout fl_addquestionfragment_container_left, fl_addquestionfragment_container_right;
    public static final int FRAGMENT_QUESTIONLIST = 0, FRAGMENT_QUESTIONADDEDIT = 1, FRAGMENT_PREVIEWQUESTION = 2;
    private boolean mShowingBack = false;


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

        return view;
    }

    private void initGlobal() {

        fl_addquestionfragment_container_left = (FrameLayout) view.findViewById(R.id.fl_addquestionfragment_container_left);
        fl_addquestionfragment_container_right = (FrameLayout) view.findViewById(R.id.fl_addquestionfragment_container_right);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_ADDQUESTION);
            }
        } catch (ClassCastException e) {
            //Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
//                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ADDQUESTION);
                fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_ADDQUESTION);
            }
        } catch (ClassCastException e) {
//            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void flipCard() {
        if (!mShowingBack) {
            mShowingBack = true;
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .add(R.id.fl_addquestionfragment_container_left, new QuestionAddEditFragment(this))
                    .addToBackStack(null)
                    .commit();

        } else {

            getFragmentManager().popBackStack();
            mShowingBack = false;

//            FragmentManager fm = getFragmentManager();
//            fm.beginTransaction()
//                    .setCustomAnimations(
//                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
//                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
//                    .show(getFragmentManager().findFragmentByTag(String.valueOf(FRAGMENT_QUESTIONLIST)))
//                    .commit();

        }


    }


    private void loadFragmentInRightContainer() {

        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_addquestionfragment_container_right, PreviewQuestionFragment.newInstance()).commit();
        } catch (Exception e) {
            Debug.e(TAG, "loadFragment Exception : " + e.toString());

        }

    }

}
