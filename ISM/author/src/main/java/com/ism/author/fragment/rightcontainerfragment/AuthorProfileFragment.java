package com.ism.author.fragment.rightcontainerfragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.interfaces.FragmentListener;


/**
 * Created by c166 on 21/10/15.
 */
public class AuthorProfileFragment extends Fragment {


    private static final String TAG = AuthorProfileFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;

    public static AuthorProfileFragment newInstance() {
        AuthorProfileFragment fragAuthorProfile = new AuthorProfileFragment();
        return fragAuthorProfile;
    }

    public AuthorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_profile, container, false);

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
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_AUTHORPROFILE);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_AUTHORPROFILE);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    public void loadFragmentIntoMainContainer(View view) {

        try {
            switch (view.getId()) {
                case R.id.txt_myfeeds:
                    loadFragment(AuthorHostActivity.FRAGMENT_MYFEEDS);
                    break;

                case R.id.txt_mybooks:
                    loadFragment(AuthorHostActivity.FRAGMENT_BOOKS);
                    break;

                case R.id.txt_myactivity:
                    loadFragment(AuthorHostActivity.FRAGMENT_MYACTIVITY);
                    break;
            }

        } catch (Exception e) {

        }

    }


    private void loadFragment(int fragment) {


        AuthorHostActivity authorHostActivity = (AuthorHostActivity) getActivity();

        if (authorHostActivity != null) {

            authorHostActivity.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MYFEEDS, null);
        }

    }


}

