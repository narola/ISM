package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;


/**
 * these fragment class is container of questionlist,questionaddeditfragment,previewquestionfragment.
 */
//implements FragmentManager.OnBackStackChangedListener

public class AddQuestionContainerFragment extends Fragment {

    private static final String TAG = AddQuestionContainerFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    public static AddQuestionContainerFragment newInstance(Bundle bundleArgument) {
        AddQuestionContainerFragment addQuestionContainerFragment = new AddQuestionContainerFragment();
        addQuestionContainerFragment.setArguments(bundleArgument);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_question_container, container, false);
        initGlobal();
        if (savedInstanceState == null) {

//            getFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.fl_addquestionfragment_container_left, questionListFragment)
//                    .commit();
//
//            getFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.fl_addquestionfragment_container_left, questionAddEditFragment)
//                    .commit();
//
//
//            showHideFragment(questionAddEditFragment);
//
//            isFrontVisible = true;

            /*new code*/


            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionAddEditFragment)
                    .commit();

            showHideFragment(questionAddEditFragment);

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionListFragment)
                    .commit();
            isFrontVisible = true;

        }

        loadFragmentInRightContainer();
        return view;
    }

    private void initGlobal() {

        questionListFragment = new QuestionListFragment(this, getArguments());
        previewQuestionFragment = new PreviewQuestionFragment(this, getArguments());
        questionAddEditFragment = new QuestionAddEditFragment(this, getArguments());

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


    /*from here methods start to communicate between three fragments*/


    /*thsese are the listof questions to add it in preview */

    public ArrayList<Questions> listOfPreviewQuestionsToAdd = new ArrayList<Questions>();

    public ArrayList<Questions> getListOfPreviewQuestionsToAdd() {
        return listOfPreviewQuestionsToAdd;
    }

    /*get the list of preview question*/
    public ArrayList<Questions> getListOfPreviewQuestion() {
        return previewQuestionFragment.arrListQuestions;
    }


    public void setListOfExamQuestions(ArrayList<Questions> arrListExamQuestions) {
        previewQuestionFragment.setExamQuestions(arrListExamQuestions);
    }


    /*this is to add question to preview fragment*/
    public void addQuestionsToPreviewFragment() {
        previewQuestionFragment.addQuestionsToPreviewFragment(getListOfPreviewQuestionsToAdd());
    }

    /*this is to update check box view in questionlist after delete it from preview questions*/
    public void updateQuestionListviewAfterRemoveInPreview(Questions question) {
        questionListFragment.updateViewAfterDeleteInPreviewQuestion(question.getQuestionId());
        previewQuestionFragment.arrListQuestions.remove(question);
    }


    /*these is to set data in the add question fragment*/
    private Questions questionData;
    private Boolean isSetQuestionData = false;
    private Boolean isCopy = false;/*this variable will use for both copy and add new question because copy is same like
    addnewquestion using other question data*/


    public Boolean getIsCopy() {
        return isCopy;
    }

    public void setIsCopy(Boolean isCopy) {
        this.isCopy = isCopy;
    }

    public Questions getQuestionData() {
        return questionData;
    }

    public void setQuestionData(Questions questionData) {
        this.questionData = questionData;
    }

    public Boolean getIsSetQuestionData() {
        return isSetQuestionData;
    }

    public void setIsSetQuestionData(Boolean isSetQuestionData) {
        this.isSetQuestionData = isSetQuestionData;
    }

    public void setDataOnFragmentFlip(Questions questions, Boolean isSetQuestionData, Boolean isCopy) {
        setQuestionData(questions);
        setIsSetQuestionData(isSetQuestionData);
        setIsCopy(isCopy);
        flipCard();
    }

    /*this is to edit question data after edit.*/
    public void setQuestionDataAfterEditQuestion(Questions prevQuestionData, Questions updatedQuestionData, Boolean isChecked) {
        questionListFragment.updateQuestionDataAfterEditQuestion(prevQuestionData, updatedQuestionData);
        previewQuestionFragment.updateQuestionDataAfterEditQuestion(prevQuestionData, updatedQuestionData, isChecked);
    }

    /*this is to refresh list after successfull question add  */
    public void addQuestionDataAfterAddQuestion(Questions question, Boolean isChecked) {
        questionListFragment.addQuestionDataAfterAddQuestion(question);
        if (isChecked) {
            previewQuestionFragment.addQuestionDataAfterAddQuestion(question);
        }
    }

}
