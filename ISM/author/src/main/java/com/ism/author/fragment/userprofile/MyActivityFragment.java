package com.ism.author.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.interfaces.FragmentListener;

/**
 * Created by c162 on 26/10/15.
 */
public class MyActivityFragment extends Fragment {


    private static final String TAG = MyActivityFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;

    public static MyActivityFragment newInstance() {
        MyActivityFragment fragBooks = new MyActivityFragment();
        return fragBooks;
    }

    public MyActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_activity, container, false);

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
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_MY_ACTIVITY);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_MY_ACTIVITY);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
