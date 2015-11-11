package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;

/**
 * Created by c75 on 10/11/15.
 */
public class MyStudentsFragment extends Fragment {

    View rootview;
    RecyclerView rv_mystudentslist;

    public static MyStudentsFragment newInstance() {
        MyStudentsFragment myStudentsFragment = new MyStudentsFragment();
        return myStudentsFragment;
    }

    public MyStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_mystudents, container, false);

        initGlobal(rootview);


        return rootview;
    }

    private void initGlobal(View rootview) {
        rv_mystudentslist=(RecyclerView)rootview.findViewById(R.id.rv_mystudentslist);
    }
}
