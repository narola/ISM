package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import model.ROAdminConfig;
import model.teachermodel.ROClassPerformance;

/**
 * Created by c166 on 16/12/15.
 */
public class TeacherHelper {

    private static final String TAG = TeacherHelper.class.getSimpleName();

    Realm realm;

    public TeacherHelper(Context context) {
        realm = RealmAdaptor.getInstance(context);
    }

    public String getGlobalPassword() {
        RealmResults<ROAdminConfig> ROAdminConfigs = realm.where(ROAdminConfig.class)
                .equalTo("configKey", "globalPassword")
                .findAll();
        if (ROAdminConfigs != null && ROAdminConfigs.size() > 0) {
            return ROAdminConfigs.get(0).getConfigValue();
        } else {
            return null;
        }
    }

    /**
     * Save admin config data
     *
     * @param ROAdminConfig
     */
    public void saveAdminConfig(ROAdminConfig ROAdminConfig) {
        try {
            Number configId = realm.where(ROAdminConfig.class).max("configId");
            long newId = 0;
            if (configId != null) {
                newId = (long) configId + 1;
            }
            realm.beginTransaction();
            ROAdminConfig.setConfigId((int) newId);
            realm.copyToRealmOrUpdate(ROAdminConfig);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "saveAdminConfig Exception : " + e.toString());
        }
    }

    public void destroy() {
        if (realm != null) {
            realm.close();
        }
    }

    /**
     *  This method is used to store the classPerformance response into table.
     *
     * @param realmClassPerformance is returned from GetRealmDataModel.getRealmClassPerformance
     */
    public void addClassPerformance(ROClassPerformance realmClassPerformance) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(realmClassPerformance);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "addFeedsData Exception : " + e.toString());
        }
    }

    /**
     * return allclassPerformances
     * @return
     */

    public RealmResults<ROClassPerformance> getAllClassPerformances() {
        RealmQuery<ROClassPerformance> query = realm.where(ROClassPerformance.class);
        return query.findAll();
    }
}
