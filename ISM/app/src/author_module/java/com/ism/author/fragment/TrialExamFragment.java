package com.ism.author.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/**
 * Created by c166 on 28/10/15.
 */
public class TrialExamFragment extends Fragment {


    private static final String TAG = TrialExamFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;

    public static TrialExamFragment newInstance() {
        TrialExamFragment trialExamFragment = new TrialExamFragment();
        return trialExamFragment;
    }

    public TrialExamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trial_exam, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TrialFragment.FRAGMENT_TRIAL_EXAM);
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
                fragListener.onFragmentDetached(TrialFragment.FRAGMENT_TRIAL_EXAM);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
