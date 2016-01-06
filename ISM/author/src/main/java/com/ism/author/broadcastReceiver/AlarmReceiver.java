package com.ism.author.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ism.author.R;
import com.ism.author.constant.WebConstants;
import com.ism.author.utility.Debug;
import com.ism.author.utility.PreferenceData;
import com.ism.author.utility.Utility;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;

import java.util.ArrayList;

import io.realm.RealmResults;
import model.FeedLike;
import realmhelper.AuthorHelper;

/**
 * Created by c166 on 05/01/16.
 */
public class AlarmReceiver extends BroadcastReceiver implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    private Context mContext;
    private AuthorHelper authorHelper;
    private ArrayList<Integer> arrListLikeFeedId, arrListUnlikeFeedId;
    private String userId;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.mContext = context;
        this.arrListLikeFeedId = new ArrayList<Integer>();
        this.arrListUnlikeFeedId = new ArrayList<Integer>();
        this.authorHelper = new AuthorHelper(mContext);
        this.userId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, mContext);


        if (isDataAvailableForSync()) {

            Debug.e(TAG, "DATA IS AVAILABLE FOR SYNCING");

            callApi(WebConstants.LIKEFEED);
        } else {

            Debug.e(TAG, "DATA IS NOT AVAILABLE FOR SYNCING");

        }

    }


    private Boolean isDataAvailableForSync() {
        Boolean isDataAvailable = false;
        getLikeFeedData();
        getUnLikeFeedData();
        if (arrListLikeFeedId.size() > 0 || arrListUnlikeFeedId.size() > 0) {
            isDataAvailable = true;
        }
        return isDataAvailable;

    }


    private void getLikeFeedData() {
        RealmResults<FeedLike> realmResults = authorHelper.realm.where(model.FeedLike.class)
                .equalTo("isLiked", 1).equalTo("isSync", 0).findAll();
        if (realmResults.size() > 0) {
            for (model.FeedLike feedLike : realmResults) {
                arrListLikeFeedId.add(feedLike.getFeed().getFeedId());
            }
        }
    }

    private void getUnLikeFeedData() {

        RealmResults<model.FeedLike> realmResults = authorHelper.realm.where(model.FeedLike.class)
                .equalTo("isLiked", 0).equalTo("isSync", 0).findAll();

        if (realmResults.size() > 0) {
            for (model.FeedLike feedLike : realmResults) {
                arrListUnlikeFeedId.add(feedLike.getFeed().getFeedId());
            }
        }
    }


    public void callApi(int apiCode) {

        if (userId != null) {
            Debug.e(TAG, "API CALLED FOR SYNC");
            try {
                switch (apiCode) {
                    case WebConstants.LIKEFEED:
                        callApiLikeFeed();
                        break;
                }
            } catch (Exception e) {
                Debug.e(TAG, "onResponse Exception : " + e.toString());
            }

        }
    }


    private void callApiLikeFeed() {
        if (Utility.isConnected(mContext)) {

            try {
                Attribute attribute = new Attribute();
                attribute.setUserId(userId);
                attribute.setLikedId(arrListLikeFeedId);
                attribute.setUnlikedId(arrListUnlikeFeedId);

                new WebserviceWrapper(mContext, attribute, this).new WebserviceCaller()
                        .execute(WebConstants.LIKEFEED);
            } catch (Exception e) {
                Debug.i(TAG + mContext.getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        try {
            switch (apiCode) {
                case WebConstants.LIKEFEED:
                    onResponseLikeFeeds(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseLikeFeeds(Object object, Exception error) {
        try {
            if (object != null) {

                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Utility.showToast("DATA SYNCED SUCCESSFULLY", mContext);
//                    authorHelper.updateSyncStatusForFeedsAsychrounsly(arrListLikeFeedId, arrListUnlikeFeedId, userId);
                    authorHelper.updateSyncStatusForFeeds(arrListLikeFeedId, arrListUnlikeFeedId, userId);
                    arrListLikeFeedId.clear();
                    arrListUnlikeFeedId.clear();
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseLikeFeeds api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseLikeFeeds Exception : " + e.toString());
        }
    }


}
