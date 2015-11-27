package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c75 on 31/10/15.
 */
public class AddQuestionContainerFragment extends Fragment {
    private static final String TAG = AddQuestionContainerFragment.class.getSimpleName();
    private View view;

    FrameLayout fl_addquestionfragment_container_left, fl_addquestionfragment_container_right;

    public static final int FRAGMENT_QUESTIONLIST = 0, FRAGMENT_PREVIEWQUESTION = 1;
    private String exam_id_received_from_bundle = "";

    public PreviewQuestionFragment previewQuestionFragment;
    public QuestionListFragment questionListFragment;
    public QuestionAddEditFragment questionAddEditFragment;

    private Boolean isFrontVisible = false;
    private Bundle bundleArgument;

    private FragmentListener fragListener;

    public AddQuestionContainerFragment() {

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

    /**
     * Uncomment if we want to replace question container in main(not from teacher office)
     */

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            fragListener = (FragmentListener) activity;
//            if (fragListener != null) {
//                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_ADDQUESTION_CONTAINER);
//            }
//        } catch (ClassCastException e) {
//            Debug.e(TAG, "onAttach Exception : " + e.toString());
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        try {
//            if (fragListener != null) {
//                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_ADDQUESTION_CONTAINER);
//            }
//        } catch (ClassCastException e) {
//            Debug.e(TAG, "onDetach Exception : " + e.toString());
//        }
//        fragListener = null;
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public static AddQuestionContainerFragment newInstance(Bundle bundleArgument) {
        AddQuestionContainerFragment addQuestionContainerFragment = new AddQuestionContainerFragment();
        if (bundleArgument != null) {
            Log.e(TAG, bundleArgument.getString(AppConstant.ARG_EXAM_ID));

        }
        return addQuestionContainerFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exam_id_received_from_bundle = getArguments().getString(AppConstant.ARG_EXAM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_question_teacher, container, false);

        /**
         * initialize three frags
         */

        initGlobal();

        if (savedInstanceState == null) {

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionAddEditFragment)
                    .commit();

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, questionListFragment)
                            //.add(R.id.fl_addquestionfragment_container_left, QuestionListFragment.newInstance(getArguments()))
                    .commit();

            showHideFragment(questionAddEditFragment);

            isFrontVisible = true;
        }


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
            Debug.e(TAG, "loadFragmentInMainContainer Exception : " + e.toString());

        }

    }

    public String getExam_id_received_from_bundle() {
        if (!exam_id_received_from_bundle.equalsIgnoreCase("")) {
            return exam_id_received_from_bundle;
        } else {
            return "";
        }
    }




    /*these is to set data in the add question fragment*/


    public Questions questionData;
    public Boolean isSetQuestionData = false;


    public Boolean getIsSetQuestionData() {
        return isSetQuestionData;
    }

    public void setIsSetQuestionData(Boolean isSetQuestionData) {
        this.isSetQuestionData = isSetQuestionData;
    }

    public Questions getQuestionData() {
        return questionData;
    }

    public void setQuestionData(Questions questionData) {
        this.questionData = questionData;
    }


    /*thsese are the listofpreview questions to add */

    public ArrayList<Questions> listOfPreviewQuestionsToAdd = new ArrayList<>();

    public ArrayList<Questions> getListOfPreviewQuestionsToAdd() {
        return listOfPreviewQuestionsToAdd;
    }


    /*get the list of preview question*/
    public ArrayList<Questions> getListOfPreviewQuestion() {
        return previewQuestionFragment.listOfPreviewQuestions;
    }

    /*this is to add question to preview fragment*/
    public void addQuestionsToPreviewFragment() {
        previewQuestionFragment.addQuestionsToPreviewFragment(getListOfPreviewQuestionsToAdd());

    }

    /*this is to update check box view in questionlist after delete it from preview questions*/
    public void updateQuestionListviewAfterRemoveInPreview(Questions data) {
        questionListFragment.updateViewAfterDeleteInPreviewQuestion(data);
        previewQuestionFragment.listOfPreviewQuestions.remove(data);

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

    public void setDataOnFragmentFlip(Questions data, Boolean isSetQuestionData, int FRAGMENT_TYPE, int POSITION_FOR_EDITQUESTION) {

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
