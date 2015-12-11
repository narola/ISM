package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import model.AdminConfig;
import model.FeedComment;
import model.FeedImage;
import model.FeedLike;
import model.Feeds;
import model.User;

/**
 * Created by c161 on 08/12/15.
 */
public class StudentHelper {

    private static final String TAG = StudentHelper.class.getSimpleName();

    Realm realm;
    private RealmResults<Feeds> feedsRealmResults;

    public StudentHelper(Context context) {
        realm = RealmAdaptor.getInstance(context);
    }

    /**
     * use to save user data in ISM database.
     */
    public void saveUser(User user) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
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

	/*User user = new User();
    user.setFirstName("krunal");
//  UserHelper user1 = new UserHelper(user,getActivity());
	user.saveUser();
	user  =null;
	System.gc();*/

    public void destroy() {

    }

    public String getGlobalPassword() {
        RealmResults<AdminConfig> adminConfigs = realm.where(AdminConfig.class)
                .equalTo("configKey", "globalPassword")
                .findAll();
        Log.e(TAG, "getGlobalPassword size : " + adminConfigs.size());
        if (adminConfigs != null && adminConfigs.size() > 0) {
            return adminConfigs.get(0).getConfigValue();
        } else {
            return null;
        }
    }


    public void saveFeeds(Feeds feeds) {

        // remaining : to update feed with local id
        Number localId = realm.where(Feeds.class).max("localId");
        long newId = 0;
        if (localId != null) {
            newId = (long) localId + 1;
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(feeds);
        realm.commitTransaction();
    }

    /**
     * if feedId is -1 then its return the all feeds
     * else return the single record of @param feedId
     * @param feedId
     * @return
     */
    public RealmResults<Feeds> getFeeds(int feedId) {
        realm.beginTransaction();
        if (feedId != -1) {
            feedsRealmResults = realm.where(Feeds.class).equalTo("feedId", feedId).findAll();
        } else {
            feedsRealmResults = realm.where(Feeds.class).findAll();
        }
        realm.commitTransaction();
        return feedsRealmResults;
    }

//    public RealmResults<FeedImage> getFeedImages() {
//        realm.beginTransaction();
//        RealmResults<FeedImage> feedsRealmResults = realm.where(FeedImage.class).equalTo("feedImageId", "700").findAll();
//        realm.commitTransaction();
//        return feedsRealmResults;
//    }

    /**
     * get the User
     * @param user_id
     * @return
     */
    public User getUser(String user_id) {
        realm.beginTransaction();
        RealmResults<User> feedsRealmResults = realm.where(User.class).equalTo("userId", Integer.parseInt(user_id)).findAll();
        realm.commitTransaction();
        return feedsRealmResults.get(0);
    }

    /**
     * store feed comment records
     * @param feedComment
     */
    public void FeedCommments(FeedComment feedComment) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(feedComment);
        realm.commitTransaction();
    }

    /**
     * return the all the comments of @param feedId
     * @param feedId
     * @return
     */
    public RealmResults<FeedComment> getFeedComments(int feedId) {
        realm.beginTransaction();
        RealmResults<FeedComment> feedsRealmResults = realm.where(FeedComment.class).equalTo("feed.feedId",feedId).findAll();
        realm.commitTransaction();
        return feedsRealmResults;
    }

    /**
     * store feedlike records
     * @param feedLike
     */
    public void saveFeedLikes(FeedLike feedLike) {
        realm.beginTransaction();
        Number feedLikeId = realm.where(FeedLike.class).max("feedLikeId");
        long newId = 0;
        if (feedLikeId != null) {
            newId = (long) feedLikeId + 1;
        }
        feedLike.setFeedLikeId((int) newId);
        realm.copyToRealmOrUpdate(feedLike);
        realm.commitTransaction();
    }

    public void updateFeedLikes(Feeds feeds,int like) {
        Feeds toUpdateFeeds=realm.where(Feeds.class).equalTo("feedId",feeds.getFeedId()).findFirst();
        realm.beginTransaction();
        toUpdateFeeds.setLike(String.valueOf(like));
        toUpdateFeeds.setTotalLike(feeds.getTotalLike());
        realm.commitTransaction();
    }

    public RealmResults<FeedLike> getFeedLikes(Date lastSynch, Date modified) {
        realm.beginTransaction();
        RealmResults<FeedLike> feedsRealmResults = realm.where(FeedLike.class).between("modifiedDate", lastSynch, modified).findAll();
        realm.commitTransaction();
        return feedsRealmResults;
    }

    /**
     * store feed related comments
     * @param feedComment
     */
    public void saveComments(FeedComment feedComment) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(feedComment);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.i(TAG, "saveComments Exceptions : " + e.getLocalizedMessage());
        }
    }

    /**
     * store feed images
     * @param feedImage
     */
    public void saveFeedImages(FeedImage feedImage) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(feedImage);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.i(TAG, "saveFeedImages Exceptions : " + e.getLocalizedMessage());
        }
    }
}
