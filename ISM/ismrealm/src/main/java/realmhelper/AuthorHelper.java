package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import model.AdminConfig;
import model.FeedLike;

/**
 * Created by c166 on 16/12/15.
 */
public class AuthorHelper {

    private static final String TAG = AuthorHelper.class.getSimpleName();

    public Realm realm;

    public AuthorHelper(Context context) {
        realm = RealmAdaptor.getInstance(context);
    }


    /**
     * Method to get the config data
     *
     * @return config data
     */

    public String getGlobalPassword() {
        RealmResults<AdminConfig> adminConfigs = realm.where(AdminConfig.class)
                .equalTo("configKey", "globalPassword")
                .findAll();

        if (adminConfigs != null && adminConfigs.size() > 0) {
            return adminConfigs.get(0).getConfigValue();
        } else {
            return null;
        }
    }

    /**
     * Save admin config data
     *
     * @param adminConfig
     */
    public void saveAdminConfig(AdminConfig adminConfig) {
        try {
            Number configId = realm.where(AdminConfig.class).max("configId");
            long newId = 0;
            if (configId != null) {
                newId = (long) configId + 1;
            }
            realm.beginTransaction();

            adminConfig.setConfigId((int) newId);
            realm.copyToRealmOrUpdate(adminConfig);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveAdminConfig Exception : " + e.toString());
        }
    }


    /**
     * Method to clear table data
     *
     * @param className
     */
    public void clearTableData(Class className) {
        try {
            RealmQuery<AdminConfig> realmQuery = realm.where(className);
            realm.beginTransaction();
            realm.clear(className);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "clearTableData Exception : " + e.toString());
        }
    }

    /**
     * Method to get the total records of table.
     *
     * @param className
     */
    public void getTotalRecordsInTable(Class className) {
        try {
            RealmQuery<AdminConfig> realmQuery = realm.where(className);

            Log.e(TAG, "The Total No Of records in " + className.getSimpleName() + " table are::::" + realmQuery.findAll().size());

        } catch (Exception e) {
            Log.e(TAG, "clearTableData Exception : " + e.toString());
        }
    }


    /**
     * Method to insert feedLike data and its sync value.
     *
     * @param feedLike
     */
    public void insertUpdateLikeFeedData(FeedLike feedLike) {
        try {
            FeedLike isRecordExist = realm.where(FeedLike.class)
                    .equalTo("feedId", feedLike.getFeedId()).equalTo("userId", feedLike.getUserId()).findFirst();

            if (isRecordExist == null) {
                Number feedLikeId = realm.where(FeedLike.class).max("feedLikeId");
                long newId = 0;
                if (feedLikeId != null) {
                    newId = (long) feedLikeId + 1;
                }
                feedLike.setFeedLikeId((int) newId);
            }

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(feedLike);
            realm.commitTransaction();


            Log.e(TAG, "The no of records in likeFeed table is" + realm.where(FeedLike.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, "insertUpdateLikeFeedData Exception : " + e.toString());
        }
    }


    /**
     * Method to update sync status after successfully update data at server side.
     *
     * @param arrListLikeFeedId
     * @param arrListUnlikeFeedId
     * @param strUserId
     */
    public void updateSyncStatusForFeeds(ArrayList<String> arrListLikeFeedId, ArrayList<String> arrListUnlikeFeedId, String strUserId) {

        if (arrListLikeFeedId.size() > 0) {
            for (String feedId : arrListLikeFeedId) {
                model.FeedLike feedLike = realm.where(model.FeedLike.class)
                        .equalTo("feedId", feedId).equalTo("userId", strUserId).findFirst();
                realm.beginTransaction();
                feedLike.setIsSync(true);
                realm.copyToRealmOrUpdate(feedLike);
                realm.commitTransaction();
            }
        }
        if (arrListUnlikeFeedId.size() > 0) {
            for (String feedId : arrListUnlikeFeedId) {
                model.FeedLike feedLike = realm.where(model.FeedLike.class)
                        .equalTo("feedId", feedId).equalTo("userId", strUserId).findFirst();
                realm.beginTransaction();
                feedLike.setIsSync(true);
                realm.copyToRealmOrUpdate(feedLike);
                realm.commitTransaction();
            }
        }
    }


    public void updateSyncStatusForFeedsAsychrounsly(final ArrayList<String> arrListLikeFeedId, final ArrayList<String> arrListUnlikeFeedId, final String strUserId) {

        realm.executeTransaction(new Realm.Transaction() {


            @Override
            public void execute(Realm bgRealm) {
                if (arrListLikeFeedId.size() > 0) {
                    for (String feedId : arrListLikeFeedId) {
                        model.FeedLike feedLike = realm.where(model.FeedLike.class)
                                .equalTo("feedId", feedId).equalTo("userId", strUserId).findFirst();
                        realm.beginTransaction();
                        feedLike.setIsSync(true);
                        realm.copyToRealmOrUpdate(feedLike);
                        realm.commitTransaction();
                    }
                }
                if (arrListUnlikeFeedId.size() > 0) {
                    for (String feedId : arrListUnlikeFeedId) {
                        model.FeedLike feedLike = realm.where(model.FeedLike.class)
                                .equalTo("feedId", feedId).equalTo("userId", strUserId).findFirst();
                        realm.beginTransaction();
                        feedLike.setIsSync(true);
                        realm.copyToRealmOrUpdate(feedLike);
                        realm.commitTransaction();
                    }
                }
            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onSuccess() {

                Log.e(TAG, "Sync Successfully");
            }

            @Override
            public void onError(Exception e) {

                Log.e(TAG, "UpdateSyncStatusForFeeds Exception : " + e.toString());
            }
        });
    }

    public void destroy() {

    }


}
