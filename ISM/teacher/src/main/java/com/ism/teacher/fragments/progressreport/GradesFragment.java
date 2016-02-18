package com.ism.teacher.fragments.progressreport;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.adapters.progressreport.MyGradesAdapter;
import com.ism.teacher.interfaces.FragmentListener;

/**
 * Created by c75 on 06/01/16.
 */


public class GradesFragment extends Fragment {

    private static final String TAG = GradesFragment.class.getSimpleName();
    private RecyclerView rv_grades;
    private FragmentListener fragListener;
    private MyGradesAdapter myGradesAdapter;

    public static GradesFragment newInstance() {
        GradesFragment gradesFragment = new GradesFragment();
        return gradesFragment;
    }

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
             //   fragListener.onFragmentAttached(TeacherHostActivity.);
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
               // fragListener.onFragmentDetached(TeacherHostActivity.);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {

        rv_grades=(RecyclerView)rootview.findViewById(R.id.rv_grades);
    }


}
