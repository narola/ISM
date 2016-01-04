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
import com.ism.fragment.myAuthor.MyAuthorsFragment;
import com.ism.fragment.myAuthor.authorDesk.AuthorDeskFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/**
 * Created by c162 on 01/1/16.
 */
public class MyAuthorFragment extends Fragment implements HostActivity.HostListenerMyAuthor{

    private static final String TAG = MyAuthorFragment.class.getSimpleName();
    public static final int FRAGMENT_MY_AUTHORS = 0;
    public static final int FRAGMENT_FIND_MORE_AUTHOR = 1;
    public static final int FRAGMENT_AUTHOR = 2;

    private View view;

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
        loadFragment(FRAGMENT_MY_AUTHORS);
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
                case FRAGMENT_MY_AUTHORS:
                    currentFragment = fragment;
                    myAuthorsFragment = MyAuthorsFragment.newInstance();
                    activityHost.hideControllerTopBackButton();
                    getChildFragmentManager().beginTransaction().addToBackStack(AppConstant.FRAGMENT_MY_AUTHORS).replace(R.id.fl_my_authors, myAuthorsFragment).commit();
                    break;

                case HostActivity.FRAGMENT_AUTHOR_DESK:
                    currentFragment = fragment;
                    AuthorDeskFragment authorDeskFragment = AuthorDeskFragment.newInstance();
                    getChildFragmentManager().beginTransaction().replace(R.id.fl_my_authors, authorDeskFragment).commit();
                    //activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_DESK);
                    break;

                case HostActivity.FRAGMENT_GOTRENDING:
                    currentFragment = fragment;
                    // ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_GOTRENDING);
                    break;

                case HostActivity.FRAGMENT_TRIAL:
                    currentFragment = fragment;
                    //  ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL);
                    break;

                case HostActivity.FRAGMENT_MYTHIRTY:
                    currentFragment = fragment;
                    //  ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL);
                    break;

                case HostActivity.FRAGMENT_AUTHOR_ASSESSMENT:
                    currentFragment = fragment;
                    // ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ASSESSMENT);
                    break;


            }
        } catch (Exception e) {
            Debug.i(TAG, "loadFragment Exceptions : " + e.getLocalizedMessage());
        }
    }

    public void setCurrentFragment(int currentFragment) {
        this.currentFragment = currentFragment;
    }

    public static int getCurrentChildFragment() {
        return currentFragment;
    }

    @Override
    public void onControllerTopBackClick() {
        switch (currentFragment) {
            case FRAGMENT_MY_AUTHORS:
                loadFragment(FRAGMENT_MY_AUTHORS);
//                activityHost.hideControllerTopBackButton();
//                myAuthorsFragment.showView();
                break;
        }
    }

//    @Override
//    public void onTabItemClick(int position) {
//        Log.e(TAG, "onTabItemClick : " + position);
//        loadFragment(position);
//    }
}
