package com.ism.fragment.myAuthor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.FindMoreAuthorAdapter;
import com.ism.fragment.MyAuthorFragment;
import com.ism.fragment.myAuthor.authorDesk.AcceptTermAndConditionsFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;

/**
 * Created by c162 on 04/1/16.
 */
public class FindMoreAuthorsFragment extends Fragment  {

    private static final String TAG = FindMoreAuthorsFragment.class.getSimpleName();
    private static final int FRAGMENT_MY_AUTHORS = 0;

    private View view;

    private RecyclerView rvMyAuthorList;
    private TextView txtEmptyView;
    private FindMoreAuthorAdapter findMoreAuthorAdapter;
    private HostActivity activityHost;
    private RelativeLayout rrFindMore;
    private int currentFragment;
    private FragmentListener fragListener;

    public static FindMoreAuthorsFragment newInstance() {
        FindMoreAuthorsFragment fragReportCard = new FindMoreAuthorsFragment();
        return fragReportCard;
    }

    public FindMoreAuthorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_author, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        rvMyAuthorList = (RecyclerView) view.findViewById(R.id.recyclerview);
        rvMyAuthorList.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        txtEmptyView = (TextView) view.findViewById(R.id.txt_empty_view);
        txtEmptyView.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmptyView.setText(activityHost.getString(R.string.strNoMoreAuthorsAvailable));

        findMoreAuthorAdapter = new FindMoreAuthorAdapter(this, getActivity());
        rvMyAuthorList.setAdapter(findMoreAuthorAdapter);

        //Utility.showView(txtEmptyView);

        onClicks();
    }

    private void onClicks() {
        try {


        } catch (Exception e) {
            Log.e(TAG, "onCLicks Exceptions : " + e.getLocalizedMessage());
        }
    }
    public void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case MyAuthorFragment.FRAGMENT_TERM_AND_CONDITION:
                    currentFragment = fragment;
                    AcceptTermAndConditionsFragment acceptTermAndConditionsFragment = AcceptTermAndConditionsFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.fl_my_authors, acceptTermAndConditionsFragment).commit();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "loadFragment Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            Log.e(TAG, "onAttach ");
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS);
            }

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Log.e(TAG, "onDetach");
            fragListener.onFragmentDetached(MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS);
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

}
