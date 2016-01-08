package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
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
import com.ism.realm.RealmHandler;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Feeds;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ClassWallFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = ClassWallFragment.class.getSimpleName();
    private static final int REQUEST_CODE_ADD_NEW_POST = 100;

    private View view;
    private RecyclerView recyclerPostFeeds;
    private RelativeLayout rlNewPost;

    private PostFeedsAdapter adpPostFeeds;
    private HostActivity activityHost;
    private RealmHandler dataToRealm;
    private Handler mHandler = new Handler();

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
        dataToRealm=new RealmHandler(getActivity());

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
            setUpData(dataToRealm.getFeeds(-1, Integer.parseInt(Global.strUserId)));
        }
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            recyclerPostFeeds.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    Log.e(TAG, "Scroll : " + scrollX);
//                }
//            });
//        }


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
        callApiLikeFeed();


    }

    private void callApiLikeFeed() {
        try {
            if (Utility.isConnected(getActivity())) {
                //activityHost.showProgress();
                Attribute attribute = new Attribute();
                ArrayList<String> likedId = new ArrayList<>();
                ArrayList<String> unlikedId = new ArrayList<>();
                RealmResults<model.Feeds> realmResSyncFeedLikes = dataToRealm.getUpdatedFeedLikes(false);
                Log.e(TAG, "realmResSyncFeedLikes size : " + realmResSyncFeedLikes.size());
//                RealmResults<model.Feeds> realmResSyncFeedLikes = studentHelper.managedFeedLikeStatus(Utility.getDateFormateMySql("2015-12-16 9:41:42"), Utility.getDateMySql());
                if (realmResSyncFeedLikes.size() > 0) {
                    for (int i = 0; i < realmResSyncFeedLikes.size(); i++) {
                        if (realmResSyncFeedLikes.get(i).getSelfLike().equals("1")) {
                            likedId.add(String.valueOf(realmResSyncFeedLikes.get(i).getFeedId()));
                        } else if (realmResSyncFeedLikes.get(i).getSelfLike().equals("0")) {
                            attribute.getUnlikedId().add(String.valueOf(realmResSyncFeedLikes.get(i).getFeedId()));
                            unlikedId.add(String.valueOf(realmResSyncFeedLikes.get(i).getFeedId()));
                        }
                    }
                    attribute.setLikedId(likedId);
                    attribute.setUnlikedId(unlikedId);
                    attribute.setUserId(Global.strUserId);
                    new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                            .execute(WebConstants.LIKE_FEED);
                    Log.e(TAG, "callApiLikeFeed : record found to sync ");
                } else {
                    Log.e(TAG, "callApiLikeFeed : zero record found to sync ");
                }
            } else {
                Utility.alertOffline(getActivity());
            }

        } catch (Exception e) {
            Log.e(TAG, "callApiLikeFeed Exception : " + e.toString());
        }
    }

//    public void callApiFeedLikeTask() {
//        final Handler handler = new Handler();
//        Timer timer = new Timer();
//        TimerTask doAsynchronousTask = new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    public void run() {
//                        try {
//                            callApiLikeFeed();
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                        }
//                    }
//                });
//            }
//        };
//        timer.schedule(doAsynchronousTask, 0, 120000); //execute in every 50000 ms
//    }

    public void callApiGetAllFeeds() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_FEEDS);
            } else {
                Utility.alertOffline(getActivity());
                setUpData(dataToRealm.getFeeds(-1, Integer.parseInt(Global.strUserId)));
            }

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
                case WebConstants.LIKE_FEED:
                    onResponseLikeFeed(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseLikeFeed(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseGetAllFeeds Success : " + responseHandler.getMessage());
                    // studentHelper.managedFeedLikeStatus(true);
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

    private void onResponseGetAllFeeds(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    if (responseHandler.getFeeds().size() != 0) {
                        ParseAllData(responseHandler.getFeeds());
                        adpPostFeeds = new PostFeedsAdapter(getActivity(), dataToRealm.getFeeds(-1,Integer.parseInt(Global.strUserId)));
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
        try {
            dataToRealm.saveFeeds(arrayList);
        } catch (Exception e) {
            Log.e(TAG, "ParseAllData Exception : " + e.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityHost = (HostActivity) activity;
    }

    public void setUpData(RealmResults<model.Feeds> realmResultFeeds) {
        try {
            adpPostFeeds = new PostFeedsAdapter(getActivity(), realmResultFeeds);
            Log.e(TAG, "arrayList size : " + realmResultFeeds.size());
            recyclerPostFeeds.setAdapter(adpPostFeeds);
        } catch (Exception e) {
            Log.e(TAG, "setUpData Exception :" + e.getLocalizedMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataToRealm.removeRealm();
    }
}
