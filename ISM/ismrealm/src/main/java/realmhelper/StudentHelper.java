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
		try {
			Number configId = realm.where(AdminConfig.class).max("configId");
			long newId = 0;
			if (configId != null) {
				newId = (long) configId + 1;
			}
			realm.beginTransaction();
			adminConfig.setConfigId((int)newId);
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

}
