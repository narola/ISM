package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.MyAuthorAdapter;
import com.ism.constant.AppConstant;
import com.ism.fragment.myAuthor.FindMoreAuthorsFragment;
import com.ism.fragment.myAuthor.MyAuthorsFragment;
import com.ism.interfaces.FragmentListener;

/**
 * Created by c162 on 01/1/16.
 */
public class MyAuthorFragment extends Fragment implements HostActivity.HostListenerMyAuthor{

    private static final String TAG = MyAuthorFragment.class.getSimpleName();

    private View view;

    //My Author
    public static final int FRAGMENT_AUTHOR_OFFICE = 31;
    public static final int FRAGMENT_AUTHOR_DESK = 32;
    public static final int FRAGMENT_GOTRENDING = 33;
    public static final int FRAGMENT_TRIAL = 34;
    public static final int FRAGMENT_MYTHIRTY = 35;
    public static final int FRAGMENT_AUTHOR_ASSESSMENT = 36;
    public static final int FRAGMENT_MY_AUTHORS = 37;
    public static final int FRAGMENT_FIND_MORE_AUTHORS = 38;
    public static final int FRAGMENT_TERM_AND_CONDITION = 39;

    private FragmentListener fragListener;
    private RecyclerView rvMyAuthorList;
    private TextView txtEmptyView;
    private MyAuthorAdapter myAuthorAdapter;
    private static int currentFragment = -1;
    private HostActivity activityHost;
    private MyAuthorsFragment myAuthorsFragment;

    public static MyAuthorFragment newInstance() {
        MyAuthorFragment fragReportCard = new MyAuthorFragment();
        return fragReportCard;
    }

    public MyAuthorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_author, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        loadFragment(MyAuthorFragment.FRAGMENT_MY_AUTHORS);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            activityHost = (HostActivity) activity;
            activityHost.setListenerHostMyAuthor(this);
            // activityHost.on
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_MY_AUTHOR);
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
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_MY_AUTHOR);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case MyAuthorFragment.FRAGMENT_MY_AUTHORS:
                    currentFragment = fragment;
                    myAuthorsFragment = MyAuthorsFragment.newInstance();
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_my_authors, myAuthorsFragment, AppConstant.FRAGMENT_MY_AUTHORS).commit();
                    break;
                case MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS:
                    currentFragment = fragment;
                    FindMoreAuthorsFragment findMoreAuthorsFragment = FindMoreAuthorsFragment.newInstance();
                    Log.e(TAG,"getFragmentmanager " +getFragmentManager());
                    Log.e(TAG,"R.id.fl_my_authors " +R.id.fl_my_authors);
                    getFragmentManager().beginTransaction().replace(R.id.fl_my_authors, findMoreAuthorsFragment).commit();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "loadFragment Exceptions : " + e.getLocalizedMessage());
        }
    }

    public void setCurrentFragment(int currentFragment) {
        this.currentFragment = currentFragment;
    }

    public static int getCurrentChildFragment() {
        return currentFragment;
    }
    public void onTopControllerBackClick(int position) {
        loadFragment(currentFragment);
    }

    @Override
    public void onControllerTopBackClick(int position) {
        loadFragment(position);
    }
}
