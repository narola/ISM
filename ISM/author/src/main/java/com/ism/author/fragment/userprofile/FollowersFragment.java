package com.ism.author.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.userprofile.FollowersAdapter;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;

/**
 * Created by c162 on 26/10/15.
 */
public class FollowersFragment extends Fragment {


    private static final String TAG = FollowersFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private RecyclerView rvList;
    private TextView txtEmpty;
    private FollowersAdapter followersAdapter;

    public static FollowersFragment newInstance() {
        FollowersFragment fragBooks = new FollowersFragment();
        return fragBooks;
    }

    public FollowersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_followers, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {


        rvList=(RecyclerView)view.findViewById(R.id.rv_list);
        followersAdapter = new FollowersAdapter(getActivity(),null);
        rvList.setAdapter(followersAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        txtEmpty=(TextView)view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_FOLLOWERS);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_FOLLOWERS);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
