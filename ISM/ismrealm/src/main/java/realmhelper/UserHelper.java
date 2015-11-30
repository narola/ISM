package realmhelper;



import android.content.Context;

import com.realm.ismrealm.RealmAdaptor;

import io.realm.Realm;
import model.User;

/**
 * Created by c85 on 24/11/15.
 */
public class UserHelper {

    Realm realm;
    User user;

    /**
     *
     *
     * @param user
     * @param context
     */
     public UserHelper(User user,Context context){

         realm = RealmAdaptor.getInstance(context);
         this.user = user;

     }

    /**
     * use to save user data in ISM database.
     */
    public void saveUser(){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

}
