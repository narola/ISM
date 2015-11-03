package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ism.R;
import com.ism.interfaces.FragmentListener;
import com.ism.teacher.Utility.Utils;
import com.ism.utility.Debug;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionListFragment extends Fragment {


    private static final String TAG = QuestionListFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    Fragment mFragment;

//    public QuestionListFragment newInstance(Fragment fragment) {
//        QuestionListFragment questionListFragment = new QuestionListFragment();
//        this.mFragment = fragment;
//        return questionListFragment;
//    }

    public QuestionListFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }

    Button flip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionlist, container, false);
        Utils.showToast("THE QUESTION LIST FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        flip = (Button) view.findViewById(R.id.flip);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((AddQuestionFragmentTeacher) mFragment).flipCard();


            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AddQuestionFragmentTeacher.FRAGMENT_QUESTIONLIST);
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
                fragListener.onFragmentDetached(AddQuestionFragmentTeacher.FRAGMENT_QUESTIONLIST);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
