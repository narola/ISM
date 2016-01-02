package com.ism.fragment.myAuthor;

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
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/**
 * Created by c162 on 01/01/16.
 */
public class FindMoreAuthorsFragment extends Fragment {

    private static final String TAG = FindMoreAuthorsFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private RecyclerView rvMyAuthorList;
    private TextView txtEmptyView;
    private MyAuthorAdapter myAuthorAdapter;
    private static int currentFragment=-1;
    private HostActivity activityHost;

    public static FindMoreAuthorsFragment newInstance() {
        FindMoreAuthorsFragment fragment = new FindMoreAuthorsFragment();
        return fragment;
    }

    public FindMoreAuthorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_authors, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
       // loadFragment(FRAGMENT_MY_AUTHORS);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            activityHost=(HostActivity)activity;
            if (fragListener != null) {
                //fragListener.onFragmentAttached(HostActivity.FRAGMENT_MY_AUTHOR);
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
              //  fragListener.onFragmentDetached(HostActivity.FRAGMENT_MY_AUTHOR);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void loadFragment(int fragment) {
        try {
            switch (fragment) {
//                case FRAGMENT_MY_AUTHORS:
//                    currentFragment=fragment;
//                    getChildFragmentManager().beginTransaction().replace(R.id.fl_my_authors, MyAuthorsFragment.newInstance()).commit();
//                    break;
//                case FRAGMENT_FIND_MORE_AUTHOR:
//                    currentFragment=fragment;
//                    getChildFragmentManager().beginTransaction().replace(R.id.fl_my_authors, FindMoreAuthorsFragment.newInstance()).commit();
//                    break;

            }
        } catch (Exception e) {
            Debug.i(TAG, "loadFragment Exceptions : " + e.getLocalizedMessage());
        }
    }

}
