package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * Created by c75 on 31/10/15.
 */
public class AddQuestionContainerFragment extends Fragment {
    private static final String TAG = AddQuestionContainerFragment.class.getSimpleName();
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

    private Boolean isFrontVisible = false;


    public AddQuestionContainerFragment(String examid) {

        this.exam_id = examid;
    }


    public static AddQuestionContainerFragment newInstance(int fragment, String examid) {
        AddQuestionContainerFragment addQuestionContainerFragment = new AddQuestionContainerFragment(examid);
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT, fragment);
        addQuestionContainerFragment.setArguments(args);


        return addQuestionContainerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_question_teacher, container, false);
        initGlobal();

        if (savedInstanceState == null) {


            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionAddEditFragment)
                    .commit();

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionListFragment)
                    .commit();

            showHideFragment(questionAddEditFragment);

            isFrontVisible = true;


        }


        loadFragmentInRightContainer();
        return view;
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

    public void flipCard() {
        Utility.showToast("FLIPCARD CALLED", getActivity());
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

    public String getExam_id() {
        if (!exam_id.equalsIgnoreCase("")) {
            return exam_id;
        } else {
            return "";
        }
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

}
