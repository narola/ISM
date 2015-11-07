package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utils;
import com.ism.author.interfaces.FlipCardListener;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.Data;


/**
 * these fragment class is container of questionlist,questionaddeditfragment,previewquestionfragment.
 */


public class AddQuestionDataFragment extends Fragment implements FragmentManager.OnBackStackChangedListener, FlipCardListener {

    private static final String TAG = AddQuestionDataFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    public static AddQuestionDataFragment newInstance() {
        AddQuestionDataFragment fragAddQuestions = new AddQuestionDataFragment();
        return fragAddQuestions;
    }

    public AddQuestionDataFragment() {
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

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionListFragment)
                    .commit();
        }

        loadFragmentInRightContainer();
        return view;
    }

    private void initGlobal() {

        previewQuestionFragment = new PreviewQuestionFragment(this);
        questionListFragment = new QuestionListFragment(this);


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

    private void flipCard(Boolean isSetData, Data data) {


        if (mShowingBack) {
            Utils.showToast("FRAGMENT BACKSTACK CALLED", getActivity());
//            getFragmentManager().popBackStack();

        }

        questionAddEditFragment = new QuestionAddEditFragment(this, isSetData, data);

        // Flip to the back.

        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.

        getFragmentManager()
                .beginTransaction()

                        // Replace the default fragment animations with animator resources representing
                        // rotations when switching to the back of the card, as well as animator
                        // resources representing rotations when flipping back to the front (e.g. when
                        // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                        // Replace any fragments currently in the container view with a fragment
                        // representing the next page (indicated by the just-incremented currentPage
                        // variable).
                .replace(R.id.fl_addquestionfragment_container_left, questionAddEditFragment)

                        // Add this transaction to the back stack, allowing users to press Back
                        // to get to the front of the card.
                .addToBackStack(null)

                        // Commit the transaction.
                .commit();

        if (isSetData) {
            questionAddEditFragment.setQuestionData(data);
        }
    }


    private void loadFragmentInRightContainer() {
        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_addquestionfragment_container_right, previewQuestionFragment).commit();
        } catch (Exception e) {
            Debug.e(TAG, "loadFragment Exception : " + e.toString());

        }

    }


    @Override
    public void onBackStackChanged() {


    }

    @Override
    public void onFlipCard(Boolean isSetData, Data data) {

        flipCard(isSetData, data);
    }


}
