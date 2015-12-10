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
import com.ism.adapter.PostFeedsAdapterTest;
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

import io.realm.RealmList;
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

    private PostFeedsAdapterTest adpPostFeeds;
    private HostActivity activityHost;

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

        recyclerPostFeeds.addItemDecoration(itemDecoration);

        if (Utility.isConnected(getActivity())) {
            callApiGetAllFeeds();
        } else {
            Utility.alertOffline(getActivity());
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
        StudentHelper studentHelper = new StudentHelper(getActivity());
        for (int i = 0; i < arrayList.size(); i++) {
            model.Feeds feedRealm = new model.Feeds();
            feedRealm.setFeedId(Integer.parseInt(arrayList.get(i).getFeedId()));
            feedRealm.setUserId(arrayList.get(i).getUserId());
            feedRealm.setFullName(arrayList.get(i).getFullName());
            feedRealm.setFeedText(arrayList.get(i).getFeedText());
            feedRealm.setProfilePic(arrayList.get(i).getProfilePic());
            feedRealm.setAudioLink(arrayList.get(i).getAudioLink());
            feedRealm.setVideoLink(arrayList.get(i).getVideoLink());
            feedRealm.setVideoThumbnail(arrayList.get(i).getVideoThumbnail());
            feedRealm.setTotalComment(Integer.parseInt(arrayList.get(i).getTotalComment()));
            feedRealm.setTotalLike(Integer.parseInt(arrayList.get(i).getTotalLike()));
            feedRealm.setCreatedDate(Utility.getDateFormateMySql(arrayList.get(i).getCreatedDate()));
            feedRealm.setModifiedDate(Utility.getDateFormateMySql(arrayList.get(i).getModifiedDate()));
            ArrayList<Comment> commentArrayList =arrayList.get(i).getComments() ;
            RealmList<FeedComment> feedComments = new RealmList<>();
            for (int j = 0; j < commentArrayList.size(); j++) {
                FeedComment feedComment = new FeedComment();
                feedComment.setFeedCommentId(Integer.parseInt(commentArrayList.get(j).getId()));
                feedComment.setComment(commentArrayList.get(j).getComment());
                User user = new User();
                user.setUserId(Integer.parseInt(commentArrayList.get(j).getCommentBy()));
                user.setFullName(commentArrayList.get(j).getFullName());
                user.setProfilePicture(commentArrayList.get(j).getProfilePic());
                feedComment.setCommentBy(user);
                feedComment.setCreatedDate(Utility.getDateFormateMySql(commentArrayList.get(j).getId()));
                feedComments.add(feedComment);
            }
            feedRealm.setComments(feedComments);
            feedRealm.setLike(arrayList.get(i).getLike());
            feedRealm.setPostedOn(Utility.getDateFormate(arrayList.get(i).getLike()));
            FeedImage feedImage = new FeedImage();
            ArrayList<FeedImages> arrayListImages = arrayList.get(i).getFeedImages();
            RealmList<FeedImage> feedImages = new RealmList<>();
            for (int j = 0; j < arrayListImages.size(); j++) {
                FeedImage feedImageData=new FeedImage();
                feedImageData.setFeedImageId(Integer.parseInt(arrayListImages.get(j).getId()));
                feedImageData.setImageLink(arrayListImages.get(j).getImageLink());
                feedImages.add(feedImageData);
//                feedImageData.set(user);
            }
            feedRealm.setFeedImages(feedImages);
            studentHelper.Feeds(feedRealm);
            adpPostFeeds = new PostFeedsAdapterTest(getActivity(), studentHelper.getFeeds(-1));
//            adpPostFeeds = new PostFeedsAdapterTest(getActivity(), arrayList);
            recyclerPostFeeds.setAdapter(adpPostFeeds);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityHost = (HostActivity) activity;
    }
}
