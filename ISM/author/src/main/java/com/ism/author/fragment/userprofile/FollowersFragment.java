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
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.userprofile.FollowersAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Followers;

import java.util.ArrayList;

/**
 * Created by c162 on 26/10/15.
 */
public class FollowersFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = FollowersFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private RecyclerView rvFollowersList;
    private TextView txtEmpty;
    private FollowersAdapter followersAdapter;
    private TextView tvNoDataMsg;
    private ArrayList<Followers> arrListMyFollowers;

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

        rvFollowersList = (RecyclerView) view.findViewById(R.id.rv_followers_list);
        followersAdapter = new FollowersAdapter(getActivity());
        rvFollowersList.setAdapter(followersAdapter);
        rvFollowersList.setLayoutManager(new LinearLayoutManager(getActivity()));

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(View.GONE);
        tvNoDataMsg.setText(getString(R.string.no_books_added));


        callApiGetMyFollowers();

    }

    private void callApiGetMyFollowers() {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETMYFOLLOWERS);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, getActivity()), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {

                case WebConstants.GETMYFOLLOWERS:
                    onResponseGetMyFollowers(object, error);
                    break;

            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetMyFollowers(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {


                    if (responseHandler.getFollowers().size() > 0) {
                        arrListMyFollowers = new ArrayList<Followers>();
                        arrListMyFollowers.addAll(responseHandler.getFollowers());
                        followersAdapter.addAll(arrListMyFollowers);
                        followersAdapter.notifyDataSetChanged();
                        tvNoDataMsg.setVisibility(View.GONE);
                    } else {
                        tvNoDataMsg.setVisibility(View.VISIBLE);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetMyFollowersapi Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetMyFollowers Exception : " + e.toString());
        }
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
