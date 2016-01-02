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
import com.ism.adapter.myAuthor.MyAuthorAdapter;
import com.ism.fragment.MyAuthorFragment;
import com.ism.object.Global;
import com.ism.utility.Debug;

/**
 * Created by c162 on 01/1/16.
 */
public class MyAuthorsFragment extends Fragment {

    private static final String TAG = MyAuthorsFragment.class.getSimpleName();
    private static final int FRAGMENT_MY_AUTHORS = 0;

    private View view;

    private RecyclerView rvMyAuthorList;
    private TextView txtEmptyView;
    private MyAuthorAdapter myAuthorAdapter;
    private TextView txtFindMoreAuthors;
    private HostActivity activityHost;
    public MyAuthorFragment myAuthorFragment;
    private RelativeLayout rrFindMore;
    private int currentFragment;

    public static MyAuthorsFragment newInstance() {
        MyAuthorsFragment fragReportCard = new MyAuthorsFragment();
        return fragReportCard;
    }

    public MyAuthorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_authors, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        rvMyAuthorList = (RecyclerView) view.findViewById(R.id.recyclerview);
        rvMyAuthorList.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        txtEmptyView = (TextView) view.findViewById(R.id.txt_empty_view);
        txtEmptyView.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtFindMoreAuthors = (TextView) view.findViewById(R.id.txt_find_more_author);
        txtFindMoreAuthors.setTypeface(Global.myTypeFace.getRalewayRegular());

        myAuthorAdapter = new MyAuthorAdapter(this, getActivity());
        rvMyAuthorList.setAdapter(myAuthorAdapter);

        myAuthorFragment = MyAuthorFragment.newInstance();

        rrFindMore = (RelativeLayout) view.findViewById(R.id.rr_find_more);


        onClicks();
    }

    private void onClicks() {
        try {
            txtFindMoreAuthors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityHost.showControllerTopBackButton();
                    rrFindMore.setVisibility(View.GONE);
                }
            });

//            rvMyAuthorList.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    myAuthorFragment.loadFragment(MyAuthorFragment.FRAGMENT_AUTHOR);
//                }
//            });
        } catch (Exception e) {
            Debug.i(TAG, "onCLicks Exceptions : " + e.getLocalizedMessage());
        }
    }

    public void loadFragment(int fragment) {
        try {
            switch (fragment) {

                case MyAuthorFragment.FRAGMENT_AUTHOR:
                    currentFragment = fragment;
                    AuthorOfficeFragment authorOfficeFragment = AuthorOfficeFragment.newInstance();
//                    activityHost.sh
                    getFragmentManager().beginTransaction().replace(R.id.fl_my_authors, authorOfficeFragment).commit();
                    break;

            }
        } catch (Exception e) {
            Debug.i(TAG, "loadFragment Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // fragListener = (FragmentListener) activity;
            activityHost = (HostActivity) activity;

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    //    public void loadFragment(int fragment) {
//        try {
//            switch (fragment) {
//                case FRAGMENT_MY_AUTHORS:
//                    getChildFragmentManager().beginTransaction().replace(R.id.fl_my_authors,M)
//                break;
//            }
//        } catch (Exception e) {
//            Debug.i(TAG, "loadFragment Exceptions : " + e.getLocalizedMessage());
//        }
//    }
//    public void showView() {
//        rrFindMore.setVisibility(View.VISIBLE);
//    }
}
