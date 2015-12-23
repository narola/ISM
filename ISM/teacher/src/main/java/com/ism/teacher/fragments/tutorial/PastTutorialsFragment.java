package com.ism.teacher.fragments.tutorial;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.tutorial.PastTutorialAdapter;
import com.ism.teacher.interfaces.FragmentListener;


/**
 * Created by c161 on --/10/15.
 */
public class PastTutorialsFragment extends Fragment {

    private static final String TAG = PastTutorialsFragment.class.getSimpleName();


    //Views
    private RecyclerView rvPastTutorials;

    //Objects
    private FragmentListener fragListener;

    private PastTutorialAdapter pastTutorialAdapter;

    public static PastTutorialsFragment newInstance() {
        PastTutorialsFragment pastTutorialsFragment = new PastTutorialsFragment();
        return pastTutorialsFragment;
    }

    public PastTutorialsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_past_tutorials, container, false);

        initGlobal(rootview);
        return rootview;
    }

    public void initGlobal(View rootview) {
        rvPastTutorials = (RecyclerView) rootview.findViewById(R.id.rv_past_tutorials);

        rvPastTutorials.setHasFixedSize(true);
        rvPastTutorials.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        pastTutorialAdapter = new PastTutorialAdapter(getActivity());
        rvPastTutorials.setAdapter(pastTutorialAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_PAST_TUTORIALS);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_PAST_TUTORIALS);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

}