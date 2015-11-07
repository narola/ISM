package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.author.R;

/**
 * Created by c162 on 06/11/15.
 */
public class TrialExamSujectiveDetailFragment extends Fragment {
    private View view;

    public TrialExamSujectiveDetailFragment() {
        //required
    }

    public static TrialExamSujectiveDetailFragment newInstance() {
        TrialExamSujectiveDetailFragment fragment=new TrialExamSujectiveDetailFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trial_exam_objective_details, container, false);

        initGlobal();
        onClicks();
        return view;
    }

    private void onClicks() {

    }

    private void initGlobal() {


    }
}
