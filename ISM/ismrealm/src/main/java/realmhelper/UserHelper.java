package realmhelper;


import android.content.Context;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import model.ROUser;

/**
 * Created by c85 on 24/11/15.
 */
public class UserHelper {

    Realm realm;
    ROUser ROUser;

    /**
     * @param ROUser
     * @param context
     */
    public UserHelper(ROUser ROUser, Context context) {

        realm = RealmAdaptor.getInstance(context);
        this.ROUser = ROUser;

    }

    /**
     * use to save user data in ISM database.
     */
    public void saveUser() {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ROUser);
        realm.commitTransaction();
    }

}
