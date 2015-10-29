package com.ism.author.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.R;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/**
 * Created by c166 on 28/10/15.
 */
public class TrialActivityFragment extends Fragment {


    private static final String TAG = TrialActivityFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    public static TrialActivityFragment newInstance() {
        TrialActivityFragment trialActivityFragment = new TrialActivityFragment();
        return trialActivityFragment;
    }

    public TrialActivityFragment() {
        // Required empty public constructor
    }


    TextView tvActivityTitle, tvActivityAssignmentname, tvActivityCoursename, tvActivityClass, tvActivitySubject, tvActivitySubmissiondate,
            tv_activity_submissiondate_select;
    EditText etActivityAssignmentname, etActivityCoursename;
    Button btnActivitySave, btnActivityCancel;
    Spinner spActivityClass, spActivitySubject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trial_activity, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        tvActivityTitle = (TextView) view.findViewById(R.id.tv_activity_title);
        tvActivityAssignmentname = (TextView) view.findViewById(R.id.tv_activity_assignmentname);
        tvActivityCoursename = (TextView) view.findViewById(R.id.tv_activity_coursename);
        tvActivityClass = (TextView) view.findViewById(R.id.tv_activity_class);
        tvActivitySubject = (TextView) view.findViewById(R.id.tv_activity_subject);
        tvActivitySubmissiondate = (TextView) view.findViewById(R.id.tv_activity_submissiondate);
        tv_activity_submissiondate_select = (TextView) view.findViewById(R.id.tv_activity_submissiondate_select);

        etActivityAssignmentname = (EditText) view.findViewById(R.id.et_activity_assignmentname);
        etActivityCoursename = (EditText) view.findViewById(R.id.et_activity_coursename);

        btnActivitySave = (Button) view.findViewById(R.id.btn_activity_save);
        btnActivityCancel = (Button) view.findViewById(R.id.btn_activity_cancel);

        spActivityClass = (Spinner) view.findViewById(R.id.sp_activity_class);
        spActivitySubject = (Spinner) view.findViewById(R.id.sp_activity_subject);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TrialFragment.FRAGMENT_TRIAL_ACTIVITY);
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
                fragListener.onFragmentDetached(TrialFragment.FRAGMENT_TRIAL_ACTIVITY);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
