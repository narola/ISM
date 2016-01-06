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
import com.ism.author.ws.model.CommentList;
import com.ism.author.ws.model.FeedImages;
import com.ism.author.ws.model.Feeds;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;

import java.util.ArrayList;

import io.realm.RealmResults;
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

                onAttachFileClick(view);
            }
        };
        etWritePost.setOnClickListener(onClickAttachFile);
        llPost.setOnClickListener(onClickAttachFile);


        postFeedsAdapter = new PostFeedsAdapter(getActivity(), authorHelper);
        rvPostFeeds.setAdapter(postFeedsAdapter);
        rvPostFeeds.setLayoutManager(new LinearLayoutManager(getActivity()));


//        if (isDataAvailableForSync()) {
//            callApiLikeFeed();
//        } else {
//            callApiGetAllPostFeeds();
//        }

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
            postFeedsAdapter.addAll(authorHelper.getAllPostFeeds());

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
                        addFeeds(responseHandler);
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

    private void addFeeds(ResponseHandler responseHandler) {

        if (responseHandler.getFeeds().size() > 0) {
            for (Feeds feed : responseHandler.getFeeds()) {
                authorHelper.addFeeds(getRealmFeedObject(feed));
            }

        }

    }


    private model.Feeds getRealmFeedObject(Feeds feed) {


        model.Feeds postFeed = new model.Feeds();
        postFeed.setFeedId(feed.getFeedId());

        model.User user = new model.User();
        user.setUserId(feed.getFeedBy());
        user.setProfilePicture(feed.getProfilePic());
        user.setFullName(feed.getFullName());

        postFeed.setFeedBy(user);
        postFeed.setFeedText(feed.getFeedText());
        postFeed.setVideoLink(feed.getVideoLink());
        postFeed.setAudioLink(feed.getAudioLink());
        postFeed.setVideoThumbnail(feed.getVideoThumbnail());
        postFeed.setPostedOn(Utility.getRealmDateFormat(feed.getPostedOn()));
        postFeed.setTotalLike(feed.getTotalLike());
        postFeed.setTotalComment(feed.getTotalComment());
        postFeed.setCreatedDate(Utility.getRealmDateFormat(feed.getCreatedDate()));
        postFeed.setModifiedDate(Utility.getRealmDateFormat(feed.getModifiedDate()));
        postFeed.setSelfLike(feed.getSelfLike());
        postFeed.setIsSync(1);

        for (CommentList comment : feed.getCommentList()) {
            model.FeedComment feedComment = new model.FeedComment();
            feedComment.setFeedCommentId(comment.getId());
            feedComment.setComment(comment.getComment());
            feedComment.setCreatedDate(Utility.getRealmDateFormat(comment.getCreatedDate()));

            model.User commentBy = new model.User();
            commentBy.setUserId(comment.getCommentBy());
            commentBy.setProfilePicture(comment.getProfilePic());
            commentBy.setFullName(comment.getFullName());

            feedComment.setCommentBy(commentBy);
            feedComment.setFeed(postFeed);
            postFeed.getComments().add(feedComment);
        }


        for (FeedImages image : feed.getFeedImages()) {

            model.FeedImage feedImage = new model.FeedImage();
            feedImage.setFeedImageId(image.getId());
            feedImage.setImageLink(image.getImageLink());
            feedImage.setFeed(postFeed);

            postFeed.getFeedImages().add(feedImage);
        }


        return postFeed;
    }


    private void getLikeFeedData() {
        RealmResults<model.FeedLike> realmResults = authorHelper.realm.where(model.FeedLike.class)
                .equalTo("isLiked", 1).equalTo("isSync", 0).findAll();
        if (realmResults.size() > 0) {
            for (model.FeedLike feedLike : realmResults) {
                arrListLikeFeedId.add(String.valueOf(feedLike.getFeedId()));
            }
        }
    }

    private void getUnLikeFeedData() {
        RealmResults<model.FeedLike> realmResults = authorHelper.realm.where(model.FeedLike.class)
                .equalTo("isLiked", 0).equalTo("isSync", 0).findAll();

        if (realmResults.size() > 0) {
            for (model.FeedLike feedLike : realmResults) {
                arrListUnlikeFeedId.add(String.valueOf(feedLike.getFeedId()));
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
                    Utility.showToast("DATA SYNCED SUCCESSFULLY", getActivity());
                    authorHelper.updateSyncStatusForFeeds(arrListLikeFeedId, arrListUnlikeFeedId, Global.strUserId);
                    callApiGetAllPostFeeds();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseLikeFeeds api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseLikeFeeds Exception : " + e.toString());
        }
    }


    private void setEmptyView(boolean isEnable) {
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setText(getString(R.string.no_post_feeds));
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvPostFeeds.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }
}
