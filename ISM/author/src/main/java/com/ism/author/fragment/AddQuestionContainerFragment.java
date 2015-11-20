package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.Data;
import com.ism.author.model.FragmentArgument;

import java.util.ArrayList;


/**
 * these fragment class is container of questionlist,questionaddeditfragment,previewquestionfragment.
 */
//implements FragmentManager.OnBackStackChangedListener

public class AddQuestionContainerFragment extends Fragment {

    private static final String TAG = AddQuestionContainerFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    public static AddQuestionContainerFragment newInstance(FragmentArgument fragmentArgument) {
        AddQuestionContainerFragment addQuestionContainerFragment = new AddQuestionContainerFragment();
        addQuestionContainerFragment.fragmentArgument = fragmentArgument;
        return addQuestionContainerFragment;
    }

    public AddQuestionContainerFragment() {
        // Required empty public constructor
    }

    FrameLayout flAddquestionfragmentContainerLeft, flAddquestionfragmentContainerRight;
    public static final int FRAGMENT_QUESTIONLIST = 0, FRAGMENT_QUESTIONADDEDIT = 1, FRAGMENT_PREVIEWQUESTION = 2;
    public QuestionListFragment questionListFragment;
    public PreviewQuestionFragment previewQuestionFragment;
    public QuestionAddEditFragment questionAddEditFragment;
    private Boolean isFrontVisible = false;
    private FragmentArgument fragmentArgument;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_question_container, container, false);
        initGlobal();
        if (savedInstanceState == null) {

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionListFragment)
                    .commit();

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionAddEditFragment)
                    .commit();


            showHideFragment(questionAddEditFragment);

            isFrontVisible = true;

        }

        loadFragmentInRightContainer();
        return view;
    }

    private void initGlobal() {

        questionListFragment = new QuestionListFragment(this, fragmentArgument);
        previewQuestionFragment = new PreviewQuestionFragment(this);
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
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ADDQUESTION_CONTAINER);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ADDQUESTION_CONTAINER);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void flipCard() {

        showHideFragment(questionListFragment);
        showHideFragment(questionAddEditFragment);
        if (isFrontVisible) {
            isFrontVisible = false;
            questionAddEditFragment.setViewForAddEditQuestion();

        } else {
            isFrontVisible = true;
        }
    }

    private void loadFragmentInRightContainer() {
        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_addquestionfragment_container_right, previewQuestionFragment).commit();
        } catch (Exception e) {
            Debug.e(TAG, "loadFragment Exception : " + e.toString());
        }

    }


    public void showHideFragment(final Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (isFrontVisible) {
            ft.setCustomAnimations(
                    R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                    R.animator.card_flip_left_in, R.animator.card_flip_left_out);
        } else {
            ft.setCustomAnimations(
                    R.animator.card_flip_left_in, R.animator.card_flip_left_out,
                    R.animator.card_flip_right_in, R.animator.card_flip_right_out);
        }

        if (fragment.isHidden()) {
            ft.show(fragment);
            Debug.e("hidden", "Show");
        } else {
            ft.hide(fragment);
            Debug.e("Shown", "Hide");
        }

        ft.commit();
    }






    /*thsese are the listofpreview questions to add */

    public ArrayList<Data> listOfPreviewQuestionsToAdd = new ArrayList<Data>();

    public ArrayList<Data> getListOfPreviewQuestionsToAdd() {
        return listOfPreviewQuestionsToAdd;
    }

    /*get the list of preview question*/
    public ArrayList<Data> getListOfPreviewQuestion() {
        return previewQuestionFragment.listOfPreviewQuestions;
    }

    /*this is to add question to preview fragment*/
    public void addQuestionsToPreviewFragment() {
        previewQuestionFragment.addQuestionsToPreviewFragment(getListOfPreviewQuestionsToAdd());

    }

    /*this is to update check box view in questionlist after delete it from preview questions*/
    public void updateQuestionListviewAfterRemoveInPreview(Data data) {
        questionListFragment.updateViewAfterDeleteInPreviewQuestion(data);
        previewQuestionFragment.listOfPreviewQuestions.remove(data);

    }


    /*these is to set data in the add question fragment*/
    public Data questionData;
    public Boolean isSetQuestionData = false;


    public Boolean getIsSetQuestionData() {
        return isSetQuestionData;
    }

    public void setIsSetQuestionData(Boolean isSetQuestionData) {
        this.isSetQuestionData = isSetQuestionData;
    }

    public Data getQuestionData() {
        return questionData;
    }

    public void setQuestionData(Data questionData) {
        this.questionData = questionData;
    }

    private int FRAGMENT_TYPE;

    public void setFragmentTypeForQuestionEdit(int FRAGMENTTYPE) {
        this.FRAGMENT_TYPE = FRAGMENTTYPE;
    }

    public int getFRAGMENT_TYPE() {
        return FRAGMENT_TYPE;
    }


    private int POSITION_FOR_EDITQUESTION;

    public int getPOSITION_FOR_EDITQUESTION() {
        return POSITION_FOR_EDITQUESTION;
    }

    public void setPositionForEditQuestion(int POSITION_FOR_EDITQUESTION) {
        this.POSITION_FOR_EDITQUESTION = POSITION_FOR_EDITQUESTION;
    }

    public void setDataOnFragmentFlip(Data data, Boolean isSetQuestionData, int FRAGMENT_TYPE, int POSITION_FOR_EDITQUESTION) {

        setQuestionData(data);
        setIsSetQuestionData(isSetQuestionData);
        setFragmentTypeForQuestionEdit(FRAGMENT_TYPE);
        setPositionForEditQuestion(POSITION_FOR_EDITQUESTION);
        flipCard();

    }

    public void setQuestionDataAfterEditQuestion() {

        if (getFRAGMENT_TYPE() == FRAGMENT_QUESTIONLIST) {

            questionListFragment.updateQuestionDataAfterEditQuestion();

        } else if (getFRAGMENT_TYPE() == FRAGMENT_PREVIEWQUESTION) {

            previewQuestionFragment.updateQuestionDataAfterEditQuestion();

        }

    }

}
