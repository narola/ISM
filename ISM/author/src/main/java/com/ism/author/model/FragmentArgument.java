package com.ism.author.model;

import android.app.Fragment;

import java.util.ArrayList;

/**
 * Created by c161 on 10/11/15.
 */
public class FragmentArgument {

    private static final String TAG = FragmentArgument.class.getSimpleName();


    private Fragment fragment = null;
    private ArrayList<Data> arrayListData;
    private FragmentArgumentObject fragmentArgumentObject = new FragmentArgumentObject();

    public FragmentArgument() {
    }

    public FragmentArgument(ArrayList<Data> arrayListData) {
        this.arrayListData = arrayListData;
    }

    public ArrayList<Data> getArrayListData() {
        return arrayListData;
    }

    public void setArrayListData(ArrayList<Data> arrayListData) {
        this.arrayListData = arrayListData;
    }

    public FragmentArgumentObject getFragmentArgumentObject() {
        return this.fragmentArgumentObject;
    }

    public void setFragmentArgumentObject(FragmentArgumentObject fragmentArgumentObject) {
        this.fragmentArgumentObject = fragmentArgumentObject;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
