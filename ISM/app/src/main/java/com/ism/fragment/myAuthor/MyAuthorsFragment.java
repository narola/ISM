package com.ism.fragment.myAuthor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Utility;

/**
 * Created by c162 on 01/1/16.
 */
public class MyAuthorsFragment extends Fragment implements HostActivity.HostListenerMyAllAuthors {

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
    private AuthorOfficeFragment authorOfficeFragment;
    private FragmentListener fragListener;

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
        myAuthorFragment = MyAuthorFragment.newInstance();
        rvMyAuthorList = (RecyclerView) view.findViewById(R.id.recyclerview);
        rvMyAuthorList.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        txtEmptyView = (TextView) view.findViewById(R.id.txt_empty_view);
        txtEmptyView.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmptyView.setText(Html.fromHtml(activityHost.getString(R.string.strYourHaveNotJoinedAnyAuthorClickFindMoreAuthorAndAddAuthor)));

        txtFindMoreAuthors = (TextView) view.findViewById(R.id.txt_find_more_author);
        txtFindMoreAuthors.setTypeface(Global.myTypeFace.getRalewayRegular());

        myAuthorAdapter = new MyAuthorAdapter(this, getActivity());
        //rvMyAuthorList.setAdapter(myAuthorAdapter);

        Utility.showView(txtEmptyView);

        myAuthorFragment = MyAuthorFragment.newInstance();

        rrFindMore = (RelativeLayout) view.findViewById(R.id.rr_find_more);


        onClicks();
    }

    private void onClicks() {
        try {
            txtFindMoreAuthors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onCLicks Exceptions : " + e.getLocalizedMessage());
        }
    }

    public void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE:
                    currentFragment = fragment;
                    AuthorOfficeFragment authorOfficeFragment = AuthorOfficeFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.fl_my_authors, authorOfficeFragment).commit();
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            Log.e(TAG, "onAttach");
            fragListener = (FragmentListener) activity;
            activityHost.setListenerMyAllAuthors(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_MY_AUTHORS);
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
            fragListener.onFragmentDetached(MyAuthorFragment.FRAGMENT_MY_AUTHORS);
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    @Override
    public void onTopControllerBackClick(int fragment) {
        loadFragment(fragment);
    }

}
