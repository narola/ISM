package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.activtiy.PostFeedActivity;
import com.ism.author.adapter.PostFeedsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Feeds;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;

import java.util.ArrayList;

import realmhelper.AuthorHelper;

/*
* This is the homefragment containg the newsfeed.
*/
public class HomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private ActionProcessButton progress_bar;
    private ProgressGenerator progressGenerator;

    public static HomeFragment newInstance() {
        HomeFragment fragHome = new HomeFragment();
        return fragHome;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    private LinearLayout llPost;
    private EditText etWritePost;
    private RecyclerView rvPostFeeds;
    private PostFeedsAdapter postFeedsAdapter;
    private OnClickListener onClickAttachFile;
    private TextView tvNoDataMsg;

    private ArrayList<String> arrListLikeFeedId, arrListUnlikeFeedId;
    private AuthorHelper authorHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initGlobal(view);
        return view;
    }

    private void initGlobal(View view) {


        authorHelper = new AuthorHelper(getActivity());

        arrListLikeFeedId = new ArrayList<String>();
        arrListUnlikeFeedId = new ArrayList<String>();

        llPost = (LinearLayout) view.findViewById(R.id.ll_post);
        rvPostFeeds = (RecyclerView) view.findViewById(R.id.rv_post_feeds);
        etWritePost = (EditText) view.findViewById(R.id.et_writePost);
        etWritePost.setEnabled(true);

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);

        setEmptyView(false);

        onClickAttachFile = new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utility.isConnected(getActivity())) {
                    onAttachFileClick(view);
                } else {
                    Utility.alertOffline(getActivity());
                }
            }
        };
        etWritePost.setOnClickListener(onClickAttachFile);
        llPost.setOnClickListener(onClickAttachFile);


        postFeedsAdapter = new PostFeedsAdapter(getActivity(), authorHelper);
        rvPostFeeds.setAdapter(postFeedsAdapter);
        rvPostFeeds.setLayoutManager(new LinearLayoutManager(getActivity()));

        callApiGetAllPostFeeds();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_HOME);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_HOME);
                authorHelper.realm.close();
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void onAttachFileClick(View view) {
        if (view == llPost || view == etWritePost) {
            Intent intent = new Intent(getActivity(), PostFeedActivity.class);
            startActivityForResult(intent, AppConstant.REQUEST_CODE_ADD_POST);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstant.REQUEST_CODE_ADD_POST) {
            callApiGetAllPostFeeds();
        }

    }


    private void callApiGetAllPostFeeds() {

        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();

            try {
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GETALLFEEDS);

            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            setUpData();
        }
    }

    private void setUpData() {

        if (authorHelper.getAllPostFeeds().size() > 0) {
            setEmptyView(false);
            postFeedsAdapter.addAll();
        } else {
            setEmptyView(true);
        }


    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETALLFEEDS:
                    onResponseGetAllFeeds(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAllFeeds(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getFeeds().size() > 0) {
                        addFeeds(responseHandler.getFeeds());
                    }

                    setUpData();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllFeeds api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllFeeds Exception : " + e.toString());
        }
    }

    private void addFeeds(ArrayList<Feeds> arrayListFeeds) {

        if (arrayListFeeds.size() > 0) {
            for (Feeds feed : arrayListFeeds) {
                authorHelper.addFeeds(Global.getRealmDataModel.getRealmFeed(feed));
            }
        }

    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setText(getString(R.string.no_post_feeds));
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvPostFeeds.setVisibility(isEnable ? View.GONE : View.VISIBLE);

    }
}
