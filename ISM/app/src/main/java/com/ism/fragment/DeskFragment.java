package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.fragment.desk.BooksFragment;
import com.ism.fragment.desk.FavoriteFragment;
import com.ism.fragment.desk.JotterFragment;
import com.ism.interfaces.FragmentListener;

/**
 * Created by c162 on --/10/15.
 */
public class DeskFragment extends Fragment implements HostActivity.HostListener {

    private static final String TAG = DeskFragment.class.getSimpleName();
    private static final int FRAGMENT_JOTTER = 0;
    private static final int FRAGMENT_FAVOURITES = 1;
    private static final int FRAGMENT_TIMETABLE = 2;
    private static final int FRAGMENT_BOOK = 3;
    private static final String ARG_FRAGMENT = "deskfragment";
    private static int fragment;
    private View view;

    private FragmentListener fragListener;
    AlertDismissListener alertDismissListener;

    public interface AlertDismissListener{
        public void onDismiss(int alertDialog,String note);
    }

    public void setAlertDismissListener(AlertDismissListener alertDismissListener) {
        this.alertDismissListener = alertDismissListener;
    }

    public static DeskFragment newInstance(int fragment) {
        DeskFragment deskFragment = new DeskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT, fragment);
        deskFragment.setArguments(args);
        return deskFragment;
    }

    public DeskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragment = getArguments().getInt(ARG_FRAGMENT);
            if (fragListener != null) {
                fragListener.onFragmentAttached(fragment);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desk, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        loadFragment(FRAGMENT_JOTTER);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                //fragListener.onFragmentAttached(fragment);
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
                fragListener.onFragmentDetached(fragment);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    //    @Override
//    public void Scientific(String symbol) {
//        //etNotes.setText(etNotes.getText().toString() + symbol);
//        // etNotes.setSelection(etNotes.getText().toString().length());
//    }
//length

    @Override
    public void onControllerMenuItemClicked(int position) {
        loadFragment(position);
    }

    private void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_JOTTER:
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, JotterFragment.newInstance()).commit();
                    break;
                case FRAGMENT_FAVOURITES:
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, FavoriteFragment.newInstance()).commit();
                    break;
                case FRAGMENT_TIMETABLE:
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, BooksFragment.newInstance()).commit();
                    break;
                case FRAGMENT_BOOK:
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, BooksFragment.newInstance()).commit();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "loadFragment Exception : " + e.toString());
        }
    }

    public static int getCurrentChildFragment() {
        return fragment;
    }
}
