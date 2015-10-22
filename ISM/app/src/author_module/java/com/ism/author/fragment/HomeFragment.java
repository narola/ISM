package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.author.AuthorHostActivity;
import com.ism.author.Utility.Debug;
import com.ism.interfaces.FragmentListener;


public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;

    public static HomeFragment newInstance() {
        HomeFragment fragHome = new HomeFragment();
        return fragHome;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_HOME);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_HOME);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
