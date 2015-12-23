package com.ism.teacher.fragments.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.adapters.userprofile.MyStudentsFromProfileAdapter;

/**
 * Created by c75 on 22/12/15.
 */
public class MyStudentsFromProfileFragment extends Fragment {

    private static final String TAG = MyStudentsFromProfileFragment.class.getSimpleName();

    RecyclerView rvMystudents;
    MyStudentsFromProfileAdapter myStudentsFromProfileAdapter;

    public static MyStudentsFromProfileFragment newInstance() {
        MyStudentsFromProfileFragment myStudentsFromProfileFragment = new MyStudentsFromProfileFragment();
        return myStudentsFromProfileFragment;
    }

    public MyStudentsFromProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_mystudents_from_teacher_profile, container, false);

        initGlobal(rootview);
        return rootview;
    }

    private void initGlobal(View rootview) {
        rvMystudents = (RecyclerView) rootview.findViewById(R.id.rv_mystudents);
        rvMystudents.setHasFixedSize(true);
        rvMystudents.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        myStudentsFromProfileAdapter = new MyStudentsFromProfileAdapter(getActivity());
        rvMystudents.setAdapter(myStudentsFromProfileAdapter);
    }

}
