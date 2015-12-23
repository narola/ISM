package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import model.AdminConfig;
import model.AuthorProfile;
import model.FeedLike;
import model.Preferences;

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
     * This method for save the preferences data
     * @param preferences
     */
    public void saveAllPreferences(Preferences preferences){
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(preferences);
            realm.commitTransaction();
            Log.e(TAG, "Records availbale in preferences table :" + realm.where(Preferences.class).findAll().size());
        }
        catch (Exception e){
            Log.i(TAG," saveAllPreferences Exceptions : "+e.getLocalizedMessage());
        }
    }

//    /**
//     * update the general settings
//     * @param preferences
//     */
//    public void updateUserPereferenes(Preferences preferences){
//        try{
//            Preferences getpreferences=realm.where(Preferences.class).equalTo("preferencesId",preferences.getPreferencesId()).findFirst();
//            realm.beginTransaction();
//            if(getpreferences!=null){
//                getpreferences.setIsSync(getpreferences.getIsSync()==0?1:0);
//                getpreferences.setDefaultValue(preferences.getDefaultValue());
//                Log.i(TAG,"Update id : " +preferences.getPreferencesId() + " and isSync : "+ getpreferences.getIsSync() + " and value is : " + preferences.getDefaultValue());
//            }
//            //else
//            //realm.copyToRealmOrUpdate(preferences);
//            realm.commitTransaction();
//            Log.e(TAG, "Records availbale in preferences table :" + realm.where(Preferences.class).findAll().size());
//        }
//        catch (Exception e){
//            Log.i(TAG," updateUserPereferenes Exceptions : "+e.getLocalizedMessage());
//        }
//    }


    /**
     *
     * @param authorProfile
     */
    public void saveAuthorProfile(AuthorProfile authorProfile){
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(authorProfile);
            realm.commitTransaction();
            Log.e(TAG, "Records availbale in preferences table :" + realm.where(AuthorProfile.class).findAll().size());
        }
        catch (Exception e){
            Log.i(TAG," saveAllPreferences Exceptions : "+e.getLocalizedMessage());
        }
    }
}
