package com.ism.teacher.fragments.office;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.AssignmentSubmitterAdapter;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.adapters.MyStudentsAdapter;
import com.ism.teacher.adapters.notes.AllNotesAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.fragments.AssignmentsSubmitterFragment;
import com.ism.teacher.fragments.assesment.ObjectiveAssignmentQuestionsFragment;
import com.ism.teacher.fragments.assesment.SubjectiveQuestionsContainerFragment;
import com.ism.teacher.fragments.createexam.CreateExamAssignmentContainerFragment;
import com.ism.teacher.fragments.createexam.CreateExamFragment;
import com.ism.teacher.fragments.notes.AllNotesFragment;
import com.ism.teacher.fragments.notes.NotesAddEditFragment;
import com.ism.teacher.fragments.notes.NotesContainer;
import com.ism.teacher.fragments.progressreport.TeacherProgressReportHomeFragment;
import com.ism.teacher.fragments.results.AllResultsFragment;
import com.ism.teacher.interfaces.FragmentListener;

/**
 * This fragment is container which s used to handle all the transactions for office related topics.
 * The back navigation for classwall,notes,quiz,markscripts,progress and results are
 * controlled from onBackClick method which is called from TeacherHostActivity
 * ==================================================================================
 * <p/>
 * Case BackClick:
 * When back arrow is pressed it finds the current frag inside TeacherOfficeFragment container and handle back navigation.
 * ==================================================================================
 * <p/>
 * Case AddNotes,AddQuiz.... from particular active fragment:
 * For this AddTopicsListener is used which handles the addTopic to load into TeacherOfficeFragment.
 * ==================================================================================
 * Case setBackStackFragmentKey(String fragmentTag)
 * <p/>
 * This method set the key as current frag (from which new frag is called) so on back click it determines that from which frag new fragmewnt was called
 * and we have to return on that (original) key fragment.
 */


public class TeacherOfficeFragment extends Fragment implements TeacherHostActivity.HostListener, TeacherHostActivity.AddTopicsListener {

    private static final String TAG = TeacherOfficeFragment.class.getName();

    private View view;

    private FragmentListener fragListener;

    public static final int FRAGMENT_CLASSWALL = 1;
    public static final int FRAGMENT_NOTES = 2;
    public static final int FRAGMENT_QUIZ = 3;
    public static final int FRAGMENT_MARK_SCRIPT = 4;
    public static final int FRAGMENT_RESULTS = 5;
    public static final int FRAGMENT_PROGRESS_REPORT = 6;

    public static final int FRAGMENT_ASSIGNMENT_SUBMITTER = 7;
    public static final int FRAGMENT_CREATE_EXAM_CONTAINER = 8;

    //container to view objective/subjective questions only
    public static final int FRAGMENT_OBJECTIVE_QUESTIONS_VIEW = 9;
    public static final int FRAGMENT_SUBJECTIVE_QUESTIONS = 10;
    public static final int FRAGMENT_NOTES_CONTAINER = 11;

    public static int current_office_fragment;


    public static TeacherOfficeFragment newInstance() {
        TeacherOfficeFragment teacherOfficeFragment = new TeacherOfficeFragment();
        return teacherOfficeFragment;
    }

    public TeacherOfficeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_office_home, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        loadFragmentInTeacherOffice(FRAGMENT_CLASSWALL);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_TEACHER_OFFICE);
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Office Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_TEACHER_OFFICE);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Office Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onControllerMenuItemClicked(int fragmentIndex) {
        loadFragmentInTeacherOffice(fragmentIndex);
    }

    public void loadFragmentInTeacherOffice(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_CLASSWALL:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_TEACHER_CLASSWALL);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_TEACHER_CLASSWALL);
                    getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home,
                            TeacherClassWallFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_CLASSWALL)
                            .commit();
                    ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                    break;

                case FRAGMENT_NOTES:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_TEACHER_NOTES);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_TEACHER_NOTES);

                    getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home,
                            AllNotesFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_NOTES).commit();
                    ((TeacherHostActivity) getActivity()).hideAddOption();

                    break;

                case FRAGMENT_QUIZ:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_TEACHER_QUIZ);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_TEACHER_QUIZ);
                    getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, AllAssignmentsFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_QUIZ).commit();
                    ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                    break;

                case FRAGMENT_MARK_SCRIPT:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_TEACHER_MARKSCRIPT);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_TEACHER_MARKSCRIPT);
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home,
                            TeacherMarkScriptHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_MARKSCRIPT).commit();
                    ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                    break;

                case FRAGMENT_RESULTS:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_TEACHER_RESULTS);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_TEACHER_RESULTS);
                    getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home,
                            AllResultsFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_RESULTS).commit();
                    ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                    break;

                case FRAGMENT_PROGRESS_REPORT:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_TEACHER_PROGRESS_REPORT);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_TEACHER_PROGRESS_REPORT);

                    getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home,
                            TeacherProgressReportHomeFragment.newInstance(), AppConstant.FRAGMENT_TAG_TEACHER_PROGRESS_REPORT).commit();
                    ((TeacherHostActivity) getActivity()).hideRightContainerFragment();
                    break;


                case FRAGMENT_ASSIGNMENT_SUBMITTER:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_ASSIGNMENT_SUBMITTER);

                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_ASSIGNMENT_SUBMITTER);
                    getFragmentManager().beginTransaction().
                            replace(R.id.fl_teacher_office_home,
                                    AssignmentsSubmitterFragment.newInstance(), AppConstant.FRAGMENT_TAG_ASSIGNMENT_SUBMITTER).commit();
                    break;

                case FRAGMENT_OBJECTIVE_QUESTIONS_VIEW:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_VIEW_ASSIGNMENT_QUESTION);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_VIEW_ASSIGNMENT_QUESTION);

                    getFragmentManager().beginTransaction().
                            replace(R.id.fl_teacher_office_home,
                                    ObjectiveAssignmentQuestionsFragment.newInstance(), AppConstant.FRAGMENT_TAG_VIEW_ASSIGNMENT_QUESTION).commit();
                    break;

                case FRAGMENT_SUBJECTIVE_QUESTIONS:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_SUBJECTIVE_QUESTIONS);

                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_SUBJECTIVE_QUESTIONS);

                    getFragmentManager().beginTransaction().
                            replace(R.id.fl_teacher_office_home,
                                    SubjectiveQuestionsContainerFragment.newInstance(getBundleArguments()), AppConstant.FRAGMENT_TAG_SUBJECTIVE_QUESTIONS).commit();
                    break;

                /**
                 * CreateExamAssignmentContainerFragment called from multiple places
                 */

                case FRAGMENT_CREATE_EXAM_CONTAINER:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_CREATE_EXAM_CONTAINER);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_CREATE_EXAM_CONTAINER);
                    getFragmentManager().beginTransaction().
                            replace(R.id.fl_teacher_office_home,
                                    CreateExamAssignmentContainerFragment.newInstance(), AppConstant.FRAGMENT_TAG_CREATE_EXAM_CONTAINER).commit();
                    break;

                case FRAGMENT_NOTES_CONTAINER:
                    Debug.e(AppConstant.back_tag + "child added=>>>>>>>>>>>>>>>>>>>", AppConstant.FRAGMENT_TAG_NOTES_CONTAINER);
                    setBackStackFragmentKey(AppConstant.FRAGMENT_TAG_NOTES_CONTAINER);
                    getFragmentManager().beginTransaction().
                            replace(R.id.fl_teacher_office_home,
                                    NotesContainer.newInstance(), AppConstant.FRAGMENT_TAG_NOTES_CONTAINER).commit();
                    ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                    ((TeacherHostActivity) getActivity()).showAddOption();
                    break;


            }

            current_office_fragment = fragment;

        } catch (Exception e) {
            Log.e(TAG, "loadFragmentInOfficeContainer Exception : " + e.toString());
        }
    }

    public void onBackClick() {
        switch (current_office_fragment) {

            case TeacherOfficeFragment.FRAGMENT_CLASSWALL:
                loadFragmentInTeacherOffice(TeacherOfficeFragment.FRAGMENT_CLASSWALL);
                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showAllMainMenus();

                break;
            case TeacherOfficeFragment.FRAGMENT_QUIZ:
                removeAllAssignmentArguments();
                handleBackClick(AppConstant.FRAGMENT_TAG_TEACHER_QUIZ);

                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showAllMainMenus();
                break;

            case TeacherOfficeFragment.FRAGMENT_ASSIGNMENT_SUBMITTER:

                removeSubmitterArguments();
                handleBackClick(AppConstant.FRAGMENT_TAG_ASSIGNMENT_SUBMITTER);

                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showSpinnerWithSubMenu(AppConstant.INDEX_ALL_ASSIGNMENTS);
                ((TeacherHostActivity) getActivity()).showAddOption();
                break;

            case TeacherOfficeFragment.FRAGMENT_NOTES:
                handleBackClick(AppConstant.FRAGMENT_TAG_TEACHER_NOTES);

                getBundleArguments().remove(AllNotesAdapter.ARG_NOTES_SUBJECT_ID);
                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showAllMainMenus();
                break;

            case TeacherOfficeFragment.FRAGMENT_MARK_SCRIPT:
                handleBackClick(AppConstant.FRAGMENT_TAG_TEACHER_MARKSCRIPT);

                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showAllMainMenus();
                break;

            case TeacherOfficeFragment.FRAGMENT_RESULTS:

                handleBackClick(AppConstant.FRAGMENT_TAG_TEACHER_RESULTS);

                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showAllMainMenus();
                break;
            case TeacherOfficeFragment.FRAGMENT_PROGRESS_REPORT:

                handleBackClick(AppConstant.FRAGMENT_TAG_TEACHER_PROGRESS_REPORT);

                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showAllMainMenus();
                break;


            case TeacherOfficeFragment.FRAGMENT_OBJECTIVE_QUESTIONS_VIEW:

                removeObjectiveQuestionArguments();
                handleBackClick(AppConstant.FRAGMENT_TAG_VIEW_ASSIGNMENT_QUESTION);

                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showSpinnerWithSubMenu(AppConstant.INDEX_ALL_ASSIGNMENTS);
                break;

            case TeacherOfficeFragment.FRAGMENT_SUBJECTIVE_QUESTIONS:
                getBundleArguments().remove(MyStudentsAdapter.ARG_ARR_LIST_STUDENTS);
                handleBackClick(AppConstant.FRAGMENT_TAG_SUBJECTIVE_QUESTIONS);

                ((TeacherHostActivity) getActivity()).showRightContainerFragment();
                ((TeacherHostActivity) getActivity()).showSpinnerWithSubMenu(AppConstant.INDEX_ALL_ASSIGNMENTS);
                break;

            case TeacherOfficeFragment.FRAGMENT_CREATE_EXAM_CONTAINER:
                getBundleArguments().remove(CreateExamFragment.ARG_IS_CREATE_EXAM);

                handleBackClick(AppConstant.FRAGMENT_TAG_CREATE_EXAM_CONTAINER);
                ((TeacherHostActivity) getActivity()).showSpinnerWithSubMenu(AppConstant.INDEX_ALL_ASSIGNMENTS);
                break;
//
//            case TeacherOfficeFragment.FRAGMENT_NOTES_ADD_EDIT:
//                getBundleArguments().remove(NotesAddEditFragment.ARG_IS_CREATE_NOTE);
//                handleBackClick(AppConstant.FRAGMENT_TAG_NOTES_ADD_EDIT);
//                ((TeacherHostActivity) getActivity()).showSpinnerWithSubMenu(AppConstant.INDEX_ALL_ASSIGNMENTS);
//                break;

            case TeacherOfficeFragment.FRAGMENT_NOTES_CONTAINER:
                getBundleArguments().remove(NotesAddEditFragment.ARG_IS_CREATE_NOTE);
                getBundleArguments().remove(AllNotesAdapter.ARG_NOTES_SUBJECT_ID);
                getBundleArguments().remove(NotesContainer.ARG_NOTES_LECTURE_ID);
                handleBackClick(AppConstant.FRAGMENT_TAG_NOTES_CONTAINER);
                ((TeacherHostActivity) getActivity()).showSpinnerWithSubMenu(AppConstant.INDEX_NOTES);
                break;

        }
    }

    private void removeSubmitterArguments() {
        getBundleArguments().remove(AssignmentSubmitterAdapter.ARG_STUDENT_ID);
        getBundleArguments().remove(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION);
        getBundleArguments().remove(AssignmentSubmitterAdapter.ARG_STUDENT_PROFILE_PIC);
        getBundleArguments().remove(AssignmentSubmitterAdapter.ARG_STUDENT_NAME);
    }

    private void removeObjectiveQuestionArguments() {
        getBundleArguments().remove(ObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS);
        //  getBundleArguments().remove(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_TYPE);
        getBundleArguments().remove(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY);

    }

    private void removeAllAssignmentArguments() {

        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_ID);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_NAME);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_CLASSROOM_ID);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_CLASSROOM_NAME);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_TOPIC_ID);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_BOOK_ID);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_CATEGORY);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_TYPE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_MODE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_DURATION);
        getBundleArguments().remove(AssignmentsAdapter.ARG_ASSIGNMENT_NO);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_PASS_PERCENTAGE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_CREATED_DATE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_ATTEMPT_COUNT);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_INSTRUCTIONS);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_IS_RANDOM_QUESTION);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_IS_DECLARE_RESULTS);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_ASSESSOR);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_START_DATE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_EXAM_START_TIME);
        getBundleArguments().remove(AssignmentsAdapter.ARG_FRAGMENT_TYPE);
        getBundleArguments().remove(AssignmentsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION);

    }

    public static int getCurrentChildFragment() {
        return current_office_fragment;
    }

    @Override
    public void addTopic(int position) {
        loadAddTopics(position);
    }

    public void loadAddTopics(int addTopicFromMenu) {
        try {
            switch (addTopicFromMenu) {
                case FRAGMENT_CLASSWALL:
                    break;
                case FRAGMENT_NOTES:
                    Utility.showToast("teacher notes test", getActivity());
                    break;
                case FRAGMENT_QUIZ:
                    getBundleArguments().putBoolean(CreateExamFragment.ARG_IS_CREATE_EXAM, true);
                    loadFragmentInTeacherOffice(TeacherOfficeFragment.FRAGMENT_CREATE_EXAM_CONTAINER);
                    break;

                case FRAGMENT_MARK_SCRIPT:
                    break;
                case FRAGMENT_RESULTS:
                    break;
                case FRAGMENT_PROGRESS_REPORT:
                    break;

//                case FRAGMENT_NOTES_ADD_EDIT:
//                    getBundleArguments().putBoolean(NotesAddEditFragment.ARG_IS_CREATE_NOTE, true);
//                    loadFragmentInTeacherOffice(TeacherOfficeFragment.FRAGMENT_NOTES_ADD_EDIT);
//                    break;
                case FRAGMENT_NOTES_CONTAINER:
                    getBundleArguments().putBoolean(NotesAddEditFragment.ARG_IS_CREATE_NOTE, true);
                    loadFragmentInTeacherOffice(TeacherOfficeFragment.FRAGMENT_NOTES_CONTAINER);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "loadAddTopics Exception : " + e.toString());
        }
    }


    public void setBackStackFragmentKey(String fragmentName) {

        if (!getBundleArguments().containsKey(fragmentName)) {
            getBundleArguments().putInt(fragmentName, current_office_fragment);
        }
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

    public void handleBackClick(String fragmentName) {
        loadFragmentInTeacherOffice(getBundleArguments().getInt(fragmentName));
        getBundleArguments().remove(fragmentName);
    }
}
