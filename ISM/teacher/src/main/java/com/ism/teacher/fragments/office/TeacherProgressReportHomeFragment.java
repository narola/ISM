package com.ism.teacher.fragments.office;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherProgressReportHomeFragment extends Fragment {

    private static final String TAG = TeacherProgressReportHomeFragment.class.getSimpleName();

    //Views
    Spinner spExamType;
    private List<String> arrListDefault=new ArrayList<>();

    public static TeacherProgressReportHomeFragment newInstance() {
        TeacherProgressReportHomeFragment teacherQuizHomeFragment = new TeacherProgressReportHomeFragment();
        return teacherQuizHomeFragment;
    }

    public TeacherProgressReportHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_report_detail, container, false);

        initGlobal(view);

        return view;
    }

    private void initGlobal(View rootview) {

        spExamType =(Spinner)rootview.findViewById(R.id.sp_exam_type);
        arrListDefault.add(Utility.getString(R.string.select, getActivity()));
        Adapters.setUpSpinner(getActivity(), spExamType, arrListDefault, Adapters.ADAPTER_NORMAL);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

}
