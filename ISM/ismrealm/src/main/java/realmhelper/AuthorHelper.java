package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

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

    Realm realm;

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
     * Clear admin config data
     */
    public void clearAdminConfigData() {

        try {
            RealmQuery<AdminConfig> query = realm.where(AdminConfig.class);
            Log.e(TAG, "The no of records in config table is" + query.findAll().size());

            realm.beginTransaction();
            realm.clear(AdminConfig.class);
            realm.commitTransaction();

        } catch (Exception e) {
            Log.e(TAG, "clearAdminConfigData Exception : " + e.toString());
        }
    }

    /**
     * Method to insert feedLike data and its sync value.
     *
     * @param feedLike
     */
    public void insertLikeFeedData(FeedLike feedLike) {

        try {
            Number feedLikeId = realm.where(FeedLike.class).max("feedLikeId");
            long newId = 0;
            if (feedLikeId != null) {
                newId = (long) feedLikeId + 1;
            }
            realm.beginTransaction();
            feedLike.setFeedLikeId((int) newId);
            realm.copyToRealmOrUpdate(feedLike);
            realm.commitTransaction();


            Log.e(TAG, "The no of records in likeFeed table is" + realm.where(FeedLike.class).findAll().size());
        } catch (Exception e) {
            Log.e(TAG, "insertLikeFeedData Exception : " + e.toString());
        }
    }

    public void destroy() {

    }

    /**
     *
     */
}
