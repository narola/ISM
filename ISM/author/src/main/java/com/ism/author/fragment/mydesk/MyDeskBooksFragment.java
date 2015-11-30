package com.ism.author.fragment.mydesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.author.R;
import com.ism.author.Utility.Debug;

/**
 * Created by c162 on 28/10/15.
 */
public class MyDeskBooksFragment extends Fragment {


    private static final String TAG = MyDeskBooksFragment.class.getSimpleName();
    private View view;


    public static MyDeskBooksFragment newInstance() {
        MyDeskBooksFragment fragBooks = new MyDeskBooksFragment();
        return fragBooks;
    }

    public MyDeskBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_books, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           // fragListener = (FragmentListener) activity;
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {

        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
    }
}
