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
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.activtiy.PostFeedActivity;
import com.ism.author.adapter.PostFeedsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;

import java.util.ArrayList;

import io.realm.RealmResults;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initGlobal(view);
        return view;
    }

    private void initGlobal(View view) {

        arrListLikeFeedId = new ArrayList<String>();
        arrListUnlikeFeedId = new ArrayList<String>();

        llPost = (LinearLayout) view.findViewById(R.id.ll_post);
        rvPostFeeds = (RecyclerView) view.findViewById(R.id.rv_post_feeds);
        etWritePost = (EditText) view.findViewById(R.id.et_writePost);
        etWritePost.setEnabled(true);

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(View.GONE);
        tvNoDataMsg.setText(getString(R.string.no_post_feeds));

        onClickAttachFile = new OnClickListener() {
            @Override
            public void onClick(View view) {

                onAttachFileClick(view);
            }
        };
        etWritePost.setOnClickListener(onClickAttachFile);
        llPost.setOnClickListener(onClickAttachFile);


        postFeedsAdapter = new PostFeedsAdapter(getActivity());
        rvPostFeeds.setAdapter(postFeedsAdapter);
        rvPostFeeds.setLayoutManager(new LinearLayoutManager(getActivity()));


//        Global.authorHelper.clearTableData(FeedLike.class);
        if (isDataAvailableForSync()) {
            callApiLikeFeed();
        } else {
            callApiGetAllPostFeeds();
        }


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
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

//    private void onAttachFileClick(View view) {
//
//        if (view == llPost || view == etWritePost) {
//            Intent intent = new Intent(getActivity(), PostFeedActivity.class);
//            startActivity(intent);
//
//        }
//
//    }

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
            Utility.toastOffline(getActivity());
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETALLFEEDS:
                    onResponseGetAllFeeds(object, error);
                    break;
                case WebConstants.LIKEFEED:
                    onResponseLikeFeed(object, error);
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
                        postFeedsAdapter.addAll(responseHandler.getFeeds());
                        tvNoDataMsg.setVisibility(View.GONE);
                    } else {
                        tvNoDataMsg.setVisibility(View.VISIBLE);
                    }
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllFeeds api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllFeeds Exception : " + e.toString());
        }
    }


    private void getLikeFeedData() {
        RealmResults<model.FeedLike> realmResults = Global.authorHelper.realm.where(model.FeedLike.class)
                .equalTo("isLiked", 1).equalTo("isSync", 0).findAll();
        if (realmResults.size() > 0) {
            for (model.FeedLike feedLike : realmResults) {
                arrListLikeFeedId.add(feedLike.getFeedId());
            }
        }
    }

    private void getUnLikeFeedData() {
        RealmResults<model.FeedLike> realmResults = Global.authorHelper.realm.where(model.FeedLike.class)
                .equalTo("isLiked", 0).equalTo("isSync", 0).findAll();

        if (realmResults.size() > 0) {
            for (model.FeedLike feedLike : realmResults) {
                arrListUnlikeFeedId.add(feedLike.getFeedId());
            }
        }
    }

    private Boolean isDataAvailableForSync() {

        getLikeFeedData();
        getUnLikeFeedData();
        Boolean isDataAvailable = false;
        if (arrListLikeFeedId.size() > 0 || arrListUnlikeFeedId.size() > 0) {
            isDataAvailable = true;
        }
        return isDataAvailable;
    }

    private void callApiLikeFeed() {
        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();
            try {
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setLikedId(arrListLikeFeedId);
                attribute.setUnlikedId(arrListUnlikeFeedId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.LIKEFEED);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void onResponseLikeFeed(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Utils.showToast("DATA SYNCED SUCCESSFULLY", getActivity());
                    Global.authorHelper.updateSyncStatusForFeeds(arrListLikeFeedId, arrListUnlikeFeedId, Global.strUserId);
                    callApiGetAllPostFeeds();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseLikeFeeds api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseLikeFeeds Exception : " + e.toString());
        }
    }
}
