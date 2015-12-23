package com.ism.teacher.fragments.results;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.results.AllResultsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by c161 on --/10/15.
 */
public class AllResultsFragment extends Fragment {

    private static final String TAG = AllResultsFragment.class.getSimpleName();

    //Views
    private RecyclerView rvResults;
    Spinner spSubject, spClasswise, spAssessed;
    //Adapter
    AllResultsAdapter allResultsAdapter;

    //ArrayList
    private List<String> arrListDefault = new ArrayList<>();
    private List<String> arrListAssessment = new ArrayList<>();


    public static AllResultsFragment newInstance() {
        AllResultsFragment allResultsFragment = new AllResultsFragment();
        return allResultsFragment;
    }

    public AllResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_results, container, false);

        initGlobal(view);

        return view;
    }

    private void initGlobal(View rootview) {

        spSubject = (Spinner) rootview.findViewById(R.id.sp_subject);
        spClasswise = (Spinner) rootview.findViewById(R.id.sp_classwise);
        spAssessed = (Spinner) rootview.findViewById(R.id.sp_assessed);
        rvResults = (RecyclerView) rootview.findViewById(R.id.rv_results);
        rvResults.setHasFixedSize(true);
        rvResults.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        allResultsAdapter = new AllResultsAdapter(getActivity());
        rvResults.setAdapter(allResultsAdapter);

        arrListDefault.add(getString(R.string.select));
        arrListAssessment = Arrays.asList(getResources().getStringArray(R.array.assignment_status));

        Adapters.setUpSpinner(getActivity(), spSubject, arrListDefault, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spClasswise, arrListDefault, Adapters.ADAPTER_SMALL);
        Adapters.setUpSpinner(getActivity(), spAssessed, arrListAssessment, Adapters.ADAPTER_SMALL);
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

}
