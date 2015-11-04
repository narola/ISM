package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.R;
import com.ism.author.AuthorHostActivity;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/**
 * these fragment class is container of questionlist,questionaddeditfragment,previewquestionfragment.
 */
public class AddQuestionFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {

    private static final String TAG = AddQuestionFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    public static AddQuestionFragment newInstance() {
        AddQuestionFragment fragAddQuestions = new AddQuestionFragment();
        return fragAddQuestions;
    }

    public AddQuestionFragment() {
        // Required empty public constructor
    }

    FrameLayout flAddquestionfragmentContainerLeft, flAddquestionfragmentContainerRight;
    public static final int FRAGMENT_QUESTIONLIST = 0, FRAGMENT_QUESTIONADDEDIT = 1, FRAGMENT_PREVIEWQUESTION = 2;
    private boolean mShowingBack = false;

    public PreviewQuestionFragment previewQuestionFragment;
    public QuestionListFragment questionListFragment;
    public QuestionAddEditFragment questionAddEditFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_question, container, false);
        initGlobal();


        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_addquestionfragment_container_left, questionListFragment)
                .addToBackStack(String.valueOf(FRAGMENT_QUESTIONLIST))
                .commit();

        loadFragmentInRightContainer();

        return view;
    }

    private void initGlobal() {

        previewQuestionFragment = new PreviewQuestionFragment(this);
        questionListFragment = new QuestionListFragment(this);
        questionAddEditFragment = new QuestionAddEditFragment(this);

        flAddquestionfragmentContainerLeft = (FrameLayout) view.findViewById(R.id.fl_addquestionfragment_container_left);
        flAddquestionfragmentContainerRight = (FrameLayout) view.findViewById(R.id.fl_addquestionfragment_container_right);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ADDQUESTION);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ADDQUESTION);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
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
                    .add(R.id.fl_addquestionfragment_container_left, questionAddEditFragment)
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

    @Override
    public void onBackStackChanged() {


    }


    private void loadFragmentInRightContainer() {

        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_addquestionfragment_container_right, previewQuestionFragment).commit();

        } catch (Exception e) {
            Debug.e(TAG, "loadFragment Exception : " + e.toString());

        }

    }


}
