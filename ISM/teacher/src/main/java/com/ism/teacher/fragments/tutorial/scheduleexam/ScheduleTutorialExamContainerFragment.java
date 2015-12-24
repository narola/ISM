package com.ism.teacher.fragments.tutorial.scheduleexam;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.fragments.tutorial.TutorialGroupFragment;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;


/**
 * This class will schedule exam for tutorial fragment.
 * This fragment class is container of questionlist,questionaddeditfragment,previewquestionfragment.
 */
public class ScheduleTutorialExamContainerFragment extends Fragment {
    private static final String TAG = ScheduleTutorialExamContainerFragment.class.getSimpleName();

    //Views

    private TextView tvExamName, tvGroupClass, tvTopic, tvSubjectName;

    FrameLayout fl_addquestionfragment_container_left, fl_addquestionfragment_container_right;

    public TutorialPreviewQuestionFragment tutorialPreviewQuestionFragment;
    public TutorialQuestionListFragment tutorialQuestionListFragment;
    public TutorialQuestionAddEditFragment tutorialQuestionAddEditFragment;
    private Boolean isFrontVisible = false;

    //Listeners
    private FragmentListener fragListener;

    public ScheduleTutorialExamContainerFragment() {

    }

    public static ScheduleTutorialExamContainerFragment newInstance() {
        ScheduleTutorialExamContainerFragment scheduleTutorialExamContainerFragment = new ScheduleTutorialExamContainerFragment();
        return scheduleTutorialExamContainerFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_SCHEDULE_EXAM);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_SCHEDULE_EXAM);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_exam_container, container, false);

        /**
         * initialize three frags
         */
        initGlobal(view);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, tutorialQuestionAddEditFragment)
                    .commit();

            showHideFragment(tutorialQuestionAddEditFragment);

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_addquestionfragment_container_left, tutorialQuestionListFragment)
                    .commit();
            isFrontVisible = true;
        }


        loadFragmentInRightContainer();
        return view;
    }

    private void initGlobal(View rootview) {

        tutorialQuestionListFragment = new TutorialQuestionListFragment(this);
        tutorialPreviewQuestionFragment = new TutorialPreviewQuestionFragment(this);
        tutorialQuestionAddEditFragment = new TutorialQuestionAddEditFragment(this);

        fl_addquestionfragment_container_left = (FrameLayout) rootview.findViewById(R.id.fl_addquestionfragment_container_left);
        fl_addquestionfragment_container_right = (FrameLayout) rootview.findViewById(R.id.fl_addquestionfragment_container_right);

        tvExamName = (TextView) rootview.findViewById(R.id.tv_exam_name);
        tvGroupClass = (TextView) rootview.findViewById(R.id.tv_group_class);
        tvTopic = (TextView) rootview.findViewById(R.id.tv_topic);
        tvSubjectName = (TextView) rootview.findViewById(R.id.tv_subject_name);

        tvExamName.setTypeface(Global.myTypeFace.getRalewayBold());
        tvGroupClass.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvTopic.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvSubjectName.setTypeface(Global.myTypeFace.getRalewayRegular());

        setDataFromArguments();
    }

    private void setDataFromArguments() {

        tvExamName.setText(getBundleArguments().getString(TutorialGroupFragment.ARG_TUTORIAL_EXAM_NAME));
        tvGroupClass.setText(getString(R.string.strclass) + " : " + getBundleArguments().getString(TutorialGroupFragment.ARG_TUTORIAL_GROUP_CLASS));
        tvTopic.setText(getString(R.string.strtopic) + " : " + getBundleArguments().getString(TutorialGroupFragment.ARG_TUTORIAL_TOPIC_NAME));
//        tvSubjectName.setText(getBundleArguments().getString(TutorialGroupFragment.ARG_TUTORIAL_SUBJECT_NAME));

    }

    public void flipCard() {
        showHideFragment(tutorialQuestionListFragment);
        showHideFragment(tutorialQuestionAddEditFragment);
        if (isFrontVisible) {
            isFrontVisible = false;
            tutorialQuestionAddEditFragment.setViewForAddEditQuestion();

        } else {
            isFrontVisible = true;
        }
    }

    private void loadFragmentInRightContainer() {
        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_addquestionfragment_container_right, tutorialPreviewQuestionFragment).commit();
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
        return tutorialPreviewQuestionFragment.arrListQuestions;
    }


    public void setListOfExamQuestions(ArrayList<Questions> arrListExamQuestions) {
        tutorialPreviewQuestionFragment.setExamQuestions(arrListExamQuestions);
    }


    /*this is to add question to preview fragment*/
    public void addQuestionsToPreviewFragment() {
        tutorialPreviewQuestionFragment.addQuestionsToPreviewFragment(getListOfPreviewQuestionsToAdd());
    }

    /*this is to update check box view in questionlist after delete it from preview questions*/
    public void updateQuestionListviewAfterDeleteQuestionInPreview(Questions question) {
        tutorialQuestionListFragment.updateViewAfterDeleteInPreviewQuestion(question.getQuestionId());
        tutorialPreviewQuestionFragment.arrListQuestions.remove(question);
        tutorialQuestionAddEditFragment.updateAddToPreviewCheckBoxStatus();
        tutorialPreviewQuestionFragment.updateQuestionInfoAfterDelete(Integer.parseInt(question.getQuestionScore()));
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
        tutorialQuestionListFragment.updateQuestionDataAfterEditQuestion(prevQuestionData, updatedQuestionData, isChecked);
        tutorialPreviewQuestionFragment.updateQuestionDataAfterEditQuestion(prevQuestionData, updatedQuestionData, isChecked);

    }

    /*this is to refresh list after successfull question add  */
    public void addQuestionDataAfterAddQuestion(Questions question, Boolean isChecked) {
        tutorialQuestionListFragment.addQuestionDataAfterAddQuestion(question);
        if (isChecked) {
            tutorialPreviewQuestionFragment.addQuestionDataAfterAddQuestion(question);
        }
    }

    public Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

}
