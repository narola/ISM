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
import com.ism.utility.Debug;

/**
 * Created by c162 on --/10/15.
 */
public class DeskFragment extends Fragment implements HostActivity.HostListener, HostActivity.HostListenerDesk {

    private static final String TAG = DeskFragment.class.getSimpleName();
    public static final int FRAGMENT_JOTTER = 0;
    public static final int FRAGMENT_FAVOURITES = 1;
    public static final int FRAGMENT_TIMETABLE = 2;
    public static final int FRAGMENT_BOOK = 3;

    public static final int FRAGMENT_ALL_BOOKS = 101;
    public static final int FRAGMENT_ALL_NOTES = 102;
    public static final int FRAGMENT_ALL_LINKS = 103;
    public static final int FRAGMENT_ALL_EVENTS = 104;
    public static final int FRAGMENT_ALL_ASSIGNMENTS = 105;
    public static final int FRAGMENT_ALL_EXAMS = 106;

//    public static final int FRAGMENT_ALL_FAVORITES = 0;
//    public static final int FRAGMENT_ALL_NOTES = 1;
//    public static final int FRAGMENT_ALL_BOOKS =2 ;
//    public static final int FRAGMENT_ALL_ASSIGNMENTS = 3;
//    public static final int FRAGMENT_ALL_EXAMS = 4;
//    public static final int FRAGMENT_ALL_LINKS = 5;
//    public static final int FRAGMENT_ALL_EVENTS =6 ;

    public static final String ARG_FRAGMENT = "deskfragment";
    public static final String ARG_SUBFRAGMENT = "deskSubfragment";
    private static int currentFragment ;
    private View view;

    private FragmentListener fragListener;
    AlertDismissListener alertDismissListener;
    private HostActivity activityHost;

    public void setAlertDismissListener(AlertDismissListener alertDismissListener) {
        this.alertDismissListener = alertDismissListener;
    }

//    public static DeskFragment newInstance(int fragment) {
//        currentFragment=fragment;
//        DeskFragment deskFragment = new DeskFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_FRAGMENT, fragment);
//        deskFragment.setArguments(args);
//        return deskFragment;
//    }

    public static DeskFragment newInstance() {
        DeskFragment deskFragment = new DeskFragment();
        return deskFragment;
    }

    public DeskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onBackMenuItemClick() {
        try {
//            getChildFragmentManager().popBackStack();

            switch (getCurrentChildFragment()) {
                case FRAGMENT_JOTTER:
//                    loadFragment(FRAGMENT_FAVOURITES);
                    break;
                case FRAGMENT_FAVOURITES:
                case FRAGMENT_TIMETABLE:
                case FRAGMENT_BOOK:
                    loadFragment(FRAGMENT_JOTTER);
                    break;
                case FRAGMENT_ALL_BOOKS:
                case FRAGMENT_ALL_ASSIGNMENTS:
                case FRAGMENT_ALL_EVENTS:
                case FRAGMENT_ALL_EXAMS:
                case FRAGMENT_ALL_LINKS:
                case FRAGMENT_ALL_NOTES:
                    // activityHost.handleMenus(1);
                    loadFragment(FRAGMENT_FAVOURITES);
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            Debug.i(TAG, "onBackMenuItemClick Exceptions : " + e.getLocalizedMessage());
        }
    }

    public interface AlertDismissListener {
        public void onDismiss(int alertDialog, String note);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            currentFragment = getArguments().getInt(ARG_FRAGMENT);
//            if (fragListener != null) {
//                fragListener.onFragmentAttached(currentFragment);
//            }
//        }
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
            Log.e(TAG, "onAttach Attached fragment ");
            activityHost = (HostActivity) activity;
            activityHost.setOnControllerTopBackHostListener(this);
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_DESK);
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
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_DESK);
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

    public void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case FRAGMENT_JOTTER:
                    currentFragment = fragment;
//                    getChildFragmentManager().beginTransaction().addToBackStack(null).add((R.id.fl_desk, JotterFragment.newInstance()).commit();
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, JotterFragment.newInstance()).commit();
                    break;
                case FRAGMENT_FAVOURITES:
                    currentFragment = fragment;
                    FavoriteFragment favoriteFragment=FavoriteFragment.newInstance();
                    activityHost.setListenerFavourites(favoriteFragment);
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, favoriteFragment).commit();
                    break;
                case FRAGMENT_TIMETABLE:
                    currentFragment = fragment;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, BooksFragment.newInstance()).commit();
                    break;
                case FRAGMENT_BOOK:
                    currentFragment = fragment;
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, BooksFragment.newInstance()).commit();
                    break;
//                case FRAGMENT_ALL_BOOKS:
//                    currentFragment=fragment;
//                    getChildFragmentManager().beginTransaction().replace(R.id.fl_desk, AllBooksFragment.newInstance()).commit();
//                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "loadFragment Exception : " + e.toString());
        }
    }

    public void setCurrentFragment(int currentFragment) {
        this.currentFragment = currentFragment;
    }

    public static int getCurrentChildFragment() {
        return currentFragment;
    }

}
