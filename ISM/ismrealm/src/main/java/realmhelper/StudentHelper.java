package realmhelper;

import android.content.Context;
import android.util.Log;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import io.realm.RealmResults;
import model.AdminConfig;
import model.User;

/**
 * Created by c161 on 08/12/15.
 */
public class StudentHelper {

	private static final String TAG = StudentHelper.class.getSimpleName();

	Realm realm;

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
}
