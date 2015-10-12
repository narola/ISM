package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.HostActivity;
import com.ism.R;
import com.ism.interfaces.FragmentListener;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private View mView;

    private FragmentListener mFragmentListener;

    public static HomeFragment newInstance() {
        HomeFragment fragmentHome = new HomeFragment();
        return fragmentHome;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initGlobal();

        return mView;
    }

    private void initGlobal() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mFragmentListener = (FragmentListener) activity;
            if (mFragmentListener != null) {
                mFragmentListener.onFragmentAttached(HostActivity.FRAGMENT_HOME);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (mFragmentListener != null) {
                mFragmentListener.onFragmentDetached(HostActivity.FRAGMENT_HOME);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        mFragmentListener = null;
    }

}
