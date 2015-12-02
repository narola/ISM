package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.helper.MyTypeFace;


/**
 * This fragment contains two tabs
 * AssignmentActivity and AssignmentExam
 * And (from AssignmentExam) After assignment exam is created successfully(retrieve exam id and click on
 * SetQuestion which calls AddQuestionContainerFragment
 */


public class CreateExamAssignmentContainerFragment extends Fragment {


    private static final String TAG = CreateExamAssignmentContainerFragment.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;

    public static CreateExamAssignmentContainerFragment newInstance(Bundle bundleArgument) {
        CreateExamAssignmentContainerFragment createExamAssignmentContainerFragment = new CreateExamAssignmentContainerFragment();
        createExamAssignmentContainerFragment.setArguments(bundleArgument);
        return createExamAssignmentContainerFragment;
    }

    public CreateExamAssignmentContainerFragment() {
        // Required empty public constructor
    }


    public static final int FRAGMENT_ASSIGNMENT_ACTIVITY = 1;
    public static final int FRAGMENT_ASSIGNMENT_EXAM = 2;


    FrameLayout fl_tab_activity, fl_tab_exam;
    TextView tv_tab_activity, tv_tab_exam;
    ImageView img_sep_tab_activity, img_sep_tab_exam;

    //to hide the topbar_assignment
    public LinearLayout ll_topbar_assignment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_new_assignment, container, false);
        initGlobal();
        return view;

    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        //to hide the topbar_assignment
        ll_topbar_assignment = (LinearLayout) view.findViewById(R.id.ll_topbar_assignment);

        fl_tab_activity = (FrameLayout) view.findViewById(R.id.fl_tab_activity);
        fl_tab_exam = (FrameLayout) view.findViewById(R.id.fl_tab_exam);

        tv_tab_activity = (TextView) view.findViewById(R.id.tv_tab_activity);
        tv_tab_exam = (TextView) view.findViewById(R.id.tv_tab_exam);

        img_sep_tab_activity = (ImageView) view.findViewById(R.id.img_sep_tab_activity);
        img_sep_tab_exam = (ImageView) view.findViewById(R.id.img_sep_tab_exam);

        tv_tab_activity.setTypeface(myTypeFace.getRalewayRegular());
        tv_tab_exam.setTypeface(myTypeFace.getRalewayRegular());


        fl_tab_activity.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {

                                                   initTab(0);


                                               }
                                           }
        );


        fl_tab_exam.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               initTab(1);


                                           }
                                       }
        );

        /**
         * When click of edit question on top of View
         * then whole data is passed to call AssignmentExam(with args) which is in second tab.
         * And the data is set in the specific fields.
         * setExamDetails(); in AssignmentExam is used to set data using passed args.
         */

        if (getArguments() != null) {
            initTab(1);
            loadFragmentInContainer(FRAGMENT_ASSIGNMENT_EXAM);
        } else {
            initTab(0);
            loadFragmentInContainer(FRAGMENT_ASSIGNMENT_ACTIVITY);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    //these is for the load fragment in right container.
    private void loadFragmentInContainer(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_ASSIGNMENT_ACTIVITY:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_assignment_container, AssignmentActivityFragment.newInstance(getArguments())).commit();
                    break;
                case FRAGMENT_ASSIGNMENT_EXAM:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_assignment_container, AssignmentExamFragment.newInstance(this, getActivity(), getArguments())).commit();
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "loadFragmentInMainContainer Exception : " + e.toString());

        }

    }

    private void initTab(int position) {

        if (position == 0) {

            tv_tab_activity.setTextColor(getResources().getColor(R.color.color_black));
            img_sep_tab_activity.setVisibility(View.VISIBLE);
            tv_tab_exam.setTextColor(getResources().getColor(R.color.color_text_hint));
            img_sep_tab_exam.setVisibility(View.INVISIBLE);
            loadFragmentInContainer(FRAGMENT_ASSIGNMENT_ACTIVITY);

        } else if (position == 1) {

            tv_tab_activity.setTextColor(getResources().getColor(R.color.color_text_hint));
            img_sep_tab_activity.setVisibility(View.INVISIBLE);
            tv_tab_exam.setTextColor(getResources().getColor(R.color.color_black));
            img_sep_tab_exam.setVisibility(View.VISIBLE);
            loadFragmentInContainer(FRAGMENT_ASSIGNMENT_EXAM);

        }

    }

    /**
     * Hide top tab bar in AddQuestionContainerFragment because we are replacing AddQuestionContainerFragment in teacher office so the top
     * bar was visible.
     */
    public void hideTopBar() {
        ll_topbar_assignment.setVisibility(View.GONE);
    }


}
