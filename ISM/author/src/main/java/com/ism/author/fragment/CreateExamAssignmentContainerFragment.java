package com.ism.author.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.MyTypeFace;

/**
 * Created by c166 on 28/10/15.
 */
public class CreateExamAssignmentContainerFragment extends Fragment {


    private static final String TAG = CreateExamAssignmentContainerFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;
    private MyTypeFace myTypeFace;


    public static CreateExamAssignmentContainerFragment newInstance(Bundle bundleArgument) {
        CreateExamAssignmentContainerFragment createExamAssignmentContainerFragment = new CreateExamAssignmentContainerFragment();
        createExamAssignmentContainerFragment.setArguments(bundleArgument);
        return createExamAssignmentContainerFragment;
    }

    public CreateExamAssignmentContainerFragment() {
        // Required empty public constructor
    }


    public static final int FRAGMENT_TRIAL_ACTIVITY = 1;
    public static final int FRAGMENT_TRIAL_EXAM = 2;


    FrameLayout flTabActivity, flTabExam;
    TextView tvTabActivity, tvTabExam;
    ImageView imgSepTabActivity, imgSepTabExam;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_container_create_exam_assignment, container, false);
        initGlobal();
        return view;

    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        flTabActivity = (FrameLayout) view.findViewById(R.id.fl_tab_activity);
        flTabExam = (FrameLayout) view.findViewById(R.id.fl_tab_exam);

        tvTabActivity = (TextView) view.findViewById(R.id.tv_tab_activity);
        tvTabExam = (TextView) view.findViewById(R.id.tv_tab_exam);

        imgSepTabActivity = (ImageView) view.findViewById(R.id.img_sep_tab_activity);
        imgSepTabExam = (ImageView) view.findViewById(R.id.img_sep_tab_exam);

        tvTabActivity.setTypeface(myTypeFace.getRalewayRegular());
        tvTabExam.setTypeface(myTypeFace.getRalewayRegular());


        flTabActivity.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 initTab(0);


                                             }
                                         }
        );


        flTabExam.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             initTab(1);


                                         }
                                     }
        );


        if (getArguments() != null) {
            initTab(1);
        } else {
            initTab(0);
        }


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    CreateAssignmentFragment createAssignmentFragment;
    CreateExamFragment createExamFragment;

    //these is for the load fragment in right container.
    private void loadFragmentInContainer(int fragment) {

        try {
            switch (fragment) {
                case FRAGMENT_TRIAL_ACTIVITY:
                    createAssignmentFragment = CreateAssignmentFragment.newInstance(getArguments());
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, createAssignmentFragment).commit();
                    break;
                case FRAGMENT_TRIAL_EXAM:
                    createExamFragment = CreateExamFragment.newInstance(getArguments(), getActivity());
                    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, createExamFragment).commit();
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "loadFragment Exception : " + e.toString());

        }

    }

    private void initTab(int position) {

        if (position == 0) {
            tvTabActivity.setTextColor(getResources().getColor(R.color.color_black));
            imgSepTabActivity.setVisibility(View.VISIBLE);
            tvTabExam.setTextColor(getResources().getColor(R.color.color_text_hint));
            imgSepTabExam.setVisibility(View.INVISIBLE);
            loadFragmentInContainer(FRAGMENT_TRIAL_ACTIVITY);
        } else if (position == 1) {
            tvTabActivity.setTextColor(getResources().getColor(R.color.color_text_hint));
            imgSepTabActivity.setVisibility(View.INVISIBLE);
            tvTabExam.setTextColor(getResources().getColor(R.color.color_black));
            imgSepTabExam.setVisibility(View.VISIBLE);
            loadFragmentInContainer(FRAGMENT_TRIAL_EXAM);
        }

    }


}
