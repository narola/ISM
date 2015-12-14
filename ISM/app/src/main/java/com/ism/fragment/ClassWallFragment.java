package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.activity.PostFeedActivity;
import com.ism.adapter.PostFeedsAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Comment;
import com.ism.ws.model.FeedImages;
import com.ism.ws.model.Feeds;

import java.util.ArrayList;

import io.realm.RealmResults;
import model.FeedComment;
import model.FeedImage;
import model.User;
import realmhelper.StudentHelper;

public class ClassWallFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = ClassWallFragment.class.getSimpleName();
    private static final int REQUEST_CODE_ADD_NEW_POST = 100;

    private View view;
    private RecyclerView recyclerPostFeeds;
    private RelativeLayout rlNewPost;

    private PostFeedsAdapter adpPostFeeds;
    private HostActivity activityHost;
    private StudentHelper studentHelper;

    public static ClassWallFragment newInstance() {
        ClassWallFragment fragment = new ClassWallFragment();
        return fragment;
    }

    public ClassWallFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_class_wall, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        recyclerPostFeeds = (RecyclerView) view.findViewById(R.id.recycler_post);
        rlNewPost = (RelativeLayout) view.findViewById(R.id.rl_new_post);
        recyclerPostFeeds.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 10;
                outRect.right = 10;
                outRect.left = 10;
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = 10;
                }
            }
        };
        studentHelper = new StudentHelper(getActivity());
        recyclerPostFeeds.addItemDecoration(itemDecoration);

        if (Utility.isConnected(getActivity())) {
            callApiGetAllFeeds();
        } else {
            Utility.alertOffline(getActivity());
            setUpData(studentHelper.getFeeds(-1));
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            recyclerPostFeeds.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Debug.i(TAG, "Scroll : " + scrollX);
                }
            });
        }
        rlNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Start post activity
                Intent intent = new Intent(getActivity(), PostFeedActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NEW_POST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_NEW_POST) {
            callApiGetAllFeeds();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void callApiGetAllFeeds() {
        try {
            activityHost.showProgress();
            Attribute attribute = new Attribute();
	        attribute.setUserId(Global.strUserId);
	        new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_FEEDS);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetAllFeeds Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            switch (apiCode) {
                case WebConstants.GET_ALL_FEEDS:
                    onResponseGetAllFeeds(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAllFeeds(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    if (responseHandler.getFeeds().size() != 0) {
                        ParseAllData(responseHandler.getFeeds());
                        adpPostFeeds = new PostFeedsAdapter(getActivity(), studentHelper.getFeeds(-1));
                        Debug.i(TAG, "arrayList size : " + studentHelper.getFeeds(-1).size());
                        recyclerPostFeeds.setAdapter(adpPostFeeds);

                        //setUpData(studentHelper.getFeeds(-1));
                    }
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseGetAllFeeds Failed : " + responseHandler.getMessage());
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetAllFeeds apiCall Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAllFeeds Exception : " + e.toString());
        }
    }

    private void ParseAllData(ArrayList<Feeds> arrayList) {
        int i = 0;
        try {
            for (i = 0; i < arrayList.size(); i++) {
                model.Feeds feeds = new model.Feeds();
                feeds.setFeedId(Integer.parseInt(arrayList.get(i).getFeedId()));
                User feedBy = new User();
                feedBy.setUserId(Integer.parseInt(arrayList.get(i).getUserId()));
                feedBy.setFullName(arrayList.get(i).getFullName());
                feedBy.setProfilePicture(arrayList.get(i).getProfilePic());
                studentHelper.saveUser(feedBy);
                feeds.setFeedBy(feedBy);
                feeds.setFeedText(arrayList.get(i).getFeedText());
                //feeds.setProfilePic(arrayList.get(i).getProfilePic());
                feeds.setAudioLink(arrayList.get(i).getAudioLink());
                feeds.setVideoLink(arrayList.get(i).getVideoLink());
                feeds.setVideoThumbnail(arrayList.get(i).getVideoThumbnail());
                feeds.setTotalComment(Integer.parseInt(arrayList.get(i).getTotalComment()));
                feeds.setTotalLike(Integer.parseInt(arrayList.get(i).getTotalLike()));
                feeds.setCreatedDate(Utility.getDateFormateMySql(arrayList.get(i).getCreatedDate()));
                feeds.setModifiedDate(Utility.getDateFormateMySql(arrayList.get(i).getModifiedDate()));
                feeds.setLike(arrayList.get(i).getLike());
                feeds.setPostedOn(Utility.getDateFormate(arrayList.get(i).getLike()));
                studentHelper.saveFeeds(feeds);
                ArrayList<Comment> arrayListComment = arrayList.get(i).getComments();
                if (arrayListComment.size() > 0) {
                    for (int j = 0; j < arrayListComment.size(); j++) {
                        FeedComment feedComment = new FeedComment();
                        feedComment.setFeedCommentId(Integer.parseInt(arrayListComment.get(j).getId()));
                        feedComment.setComment(arrayListComment.get(j).getComment());
                        feedComment.setFeed(feeds);
                        User user = new User();
                        user.setUserId(Integer.parseInt(arrayListComment.get(j).getCommentBy()));
                        user.setFullName(arrayListComment.get(j).getFullName());
                        user.setProfilePicture(arrayListComment.get(j).getProfilePic());
                        studentHelper.saveUser(user);
                        feedComment.setCommentBy(user);
                        feedComment.setCreatedDate(Utility.getDateFormateMySql(arrayListComment.get(j).getCreatedDate()));
                        studentHelper.saveComments(feedComment);
                        feeds.getComments().add(feedComment);
                    }
                }
                ArrayList<FeedImages> arrayListImages = arrayList.get(i).getFeedImages();
                if (arrayListImages.size() > 0) {
                    for (int j = 0; j < arrayListImages.size(); j++) {
                        FeedImage feedImage = new FeedImage();
                        feedImage.setFeedImageId(Integer.parseInt(arrayListImages.get(j).getId()));
                        feedImage.setImageLink(arrayListImages.get(j).getImageLink());
                        feedImage.setFeed(feeds);
                        studentHelper.saveFeedImages(feedImage);
                        feeds.getFeedImages().add(feedImage);
                    }
                }
//                FeedLike feedLike = new FeedLike();
//                feedLike.setFeed(feeds);
//                feedLike.setLikeBy(studentHelper.getUser(Global.strUserId));
//                feedLike.setCreatedDate(Utility.getDateFormateMySql(arrayList.get(i).getCreatedDate()));
                //  studentHelper.saveFeedLikes(feedLike);
            }
        } catch (Exception e) {
            Log.e(TAG, i + "ParseAllData Exception : " + e.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityHost = (HostActivity) activity;
    }

    public void setUpData(RealmResults<model.Feeds> realmResultFeeds) {
        try {
//            ArrayList<saveFeeds> arrayList = new ArrayList<>();
//            for (int i = 0; i < realmResultFeeds.size(); i++) {
//                saveFeeds feeds = new saveFeeds();
//                feeds.setFeedId(String.valueOf(realmResultFeeds.get(i).getFeedId()));
//                feeds.setLike(realmResultFeeds.get(i).getLike());
//                feeds.setTotalComment(String.valueOf(realmResultFeeds.get(i).getTotalComment()));
//                feeds.setTotalLike(String.valueOf(realmResultFeeds.get(i).getTotalLike()));
//                feeds.setFullName((realmResultFeeds.get(i).getFeedBy() == null ? realmResultFeeds.get(i).getFeedBy() + "" : realmResultFeeds.get(i).getFeedBy().getFullName()));
//                feeds.setAudioLink(realmResultFeeds.get(i).getAudioLink());
//                feeds.setVideoThumbnail(realmResultFeeds.get(i).getVideoThumbnail());
//                feeds.setVideoLink(realmResultFeeds.get(i).getVideoLink());
//                ArrayList<Comment> arrayListComments = new ArrayList<>();
//                RealmList<FeedComment> realmListComments = realmResultFeeds.get(i).getComments();
//                for (int j = 0; j < realmListComments.size(); j++) {
//                    Comment comment = new Comment();
//                    comment.setComment(realmListComments.get(j).getComment());
//                    comment.setCommentBy(String.valueOf(realmListComments.get(j).getCommentBy().getUserId()));
//                    comment.setId(String.valueOf(realmListComments.get(j).getFeedCommentId()));
//                    comment.setProfilePic(String.valueOf(realmListComments.get(j).getCommentBy().getProfilePicture()));
//                    arrayListComments.add(comment);
//                }
//                feeds.setComments(arrayListComments);
//                feeds.setFeedText(realmResultFeeds.get(i).getFeedText());
//                feeds.setProfilePic((realmResultFeeds.get(i).getFeedBy() == null ? realmResultFeeds.get(i).getFeedBy() + "" : realmResultFeeds.get(i).getFeedBy().getProfilePicture()));
//                feeds.setUserId(String.valueOf((realmResultFeeds.get(i).getFeedBy() == null ? realmResultFeeds.get(i).getFeedBy() + "" : realmResultFeeds.get(i).getFeedBy().getUserId())));
//                arrayList.add(feeds);
//            }
            adpPostFeeds = new PostFeedsAdapter(getActivity(), realmResultFeeds);
            Debug.i(TAG, "arrayList size : " + realmResultFeeds.size());
            recyclerPostFeeds.setAdapter(adpPostFeeds);
        } catch (Exception e) {
            Debug.i(TAG, "setUpData Exception :" + e.getLocalizedMessage());
        }
    }
}
