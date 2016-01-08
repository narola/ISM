package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import model.AdminConfig;
import model.teachermodel.ClassPerformanceRealmModel;

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
    public void addClassPerformance(ClassPerformanceRealmModel realmClassPerformance) {
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

    public RealmResults<ClassPerformanceRealmModel> getAllClassPerformances() {
        RealmQuery<ClassPerformanceRealmModel> query = realm.where(ClassPerformanceRealmModel.class);
        return query.findAll();
    }
}
