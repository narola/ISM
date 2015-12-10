package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import model.AdminConfig;
import model.FeedComment;
import model.FeedImage;
import model.FeedLike;
import model.Feeds;
import model.User;

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
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(adminConfig);
        realm.commitTransaction();
    }

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

    public void Feeds(Feeds feeds) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(feeds);
        realm.commitTransaction();
    }

    public RealmResults<Feeds> getFeeds(int feedId) {
        realm.beginTransaction();
        if (feedId != -1) {
           feedsRealmResults = realm.where(Feeds.class).equalTo("feedId", feedId).findAll();
        }else{
                feedsRealmResults = realm.where(Feeds.class).findAll();
        }
        realm.commitTransaction();
        return feedsRealmResults;
    }

    public RealmResults<FeedImage> getFeedImages() {
        realm.beginTransaction();
        RealmResults<FeedImage> feedsRealmResults = realm.where(FeedImage.class).equalTo("feedImageId", "700").findAll();
        realm.commitTransaction();
        return feedsRealmResults;
    }

    public void FeedImages(FeedImage feedImage) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(feedImage);
        realm.commitTransaction();
    }

    public User getUser(String user_id) {
        realm.beginTransaction();
        RealmResults<User> feedsRealmResults = realm.where(User.class).equalTo("userId", Integer.parseInt(user_id)).findAll();
        realm.commitTransaction();
        return feedsRealmResults.get(0);
    }

    public void FeedCommments(RealmList feeds) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(feeds);
        realm.commitTransaction();
    }

    public RealmResults<FeedComment> getFeedComments(String feedId) {
        realm.beginTransaction();
        RealmQuery<FeedComment> query = realm.where(FeedComment.class);
        RealmResults<FeedComment> feedsRealmResults = realm.where(FeedComment.class).equalTo("feed", feedId).findAll();
        realm.commitTransaction();
        return feedsRealmResults;
    }

    public void FeedLikes(FeedLike feedLike) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(feedLike);
        realm.commitTransaction();
    }

    public RealmResults<FeedLike> getFeedLikes(Date lastSynch, Date modified) {
        realm.beginTransaction();
        RealmResults<FeedLike> feedsRealmResults = realm.where(FeedLike.class).between("modifiedDate", lastSynch, modified).findAll();
        realm.commitTransaction();
        return feedsRealmResults;
    }

}
