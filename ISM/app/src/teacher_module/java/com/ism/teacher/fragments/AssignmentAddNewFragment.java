package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.interfaces.FragmentListener;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.R;

/**
 * Created by c166 on 28/10/15.
 */
public class AssignmentAddNewFragment extends Fragment {


    private static final String TAG = AssignmentAddNewFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;
    private MyTypeFace myTypeFace;


    public static AssignmentAddNewFragment newInstance() {
        AssignmentAddNewFragment assignmentAddNewFragment = new AssignmentAddNewFragment();
        return assignmentAddNewFragment;
    }

    public AssignmentAddNewFragment() {
        // Required empty public constructor
    }


    public static final int FRAGMENT_ASSIGNMENT_ACTIVITY = 1;
    public static final int FRAGMENT_ASSIGNMENT_EXAM = 2;


    FrameLayout fl_tab_activity, fl_tab_exam;
    TextView tv_tab_activity, tv_tab_exam;
    ImageView img_sep_tab_activity, img_sep_tab_exam;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_new_assignment, container, false);
        initGlobal();
        return view;

    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        loadFragmentInContainer(FRAGMENT_ASSIGNMENT_ACTIVITY);

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
                                                   loadFragmentInContainer(FRAGMENT_ASSIGNMENT_ACTIVITY);

                                               }
                                           }
        );


        fl_tab_exam.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               initTab(1);
                                               loadFragmentInContainer(FRAGMENT_ASSIGNMENT_EXAM);

                                           }
                                       }
        );


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
//                fragListener.onFragmentAttached();
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
//                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ADDNEWTRIAL);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    //these is for the load fragment in right container.
    private void loadFragmentInContainer(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_ASSIGNMENT_ACTIVITY:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_assignment_container, AssignmentActivityFragment.newInstance()).commit();
                    break;
                case FRAGMENT_ASSIGNMENT_EXAM:
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_assignment_container, TeacherChatFragment.newInstance()).commit();
                    break;

            }

        } catch (Exception e) {
            Debug.e(TAG, "loadFragment Exception : " + e.toString());

        }

    }

    private void initTab(int position) {


        if (position == 0) {

            tv_tab_activity.setTextColor(getResources().getColor(R.color.color_black));
            img_sep_tab_activity.setVisibility(View.VISIBLE);
            tv_tab_exam.setTextColor(getResources().getColor(R.color.color_text_hint));
            img_sep_tab_exam.setVisibility(View.INVISIBLE);

        } else if (position == 1) {


            tv_tab_activity.setTextColor(getResources().getColor(R.color.color_text_hint));
            img_sep_tab_activity.setVisibility(View.INVISIBLE);
            tv_tab_exam.setTextColor(getResources().getColor(R.color.color_black));
            img_sep_tab_exam.setVisibility(View.VISIBLE);

        }

    }


}
