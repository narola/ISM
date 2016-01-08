package com.realm.ismrealm;

import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.internal.Table;

/**
 * this class is use to realm migration with different versions.
 */
public class Migration implements RealmMigration {

    int newVersion;

    public Migration(int newVersion) {
        this.newVersion = newVersion;
    }


    @Override
    public long execute(Realm realm, long version) {

        long updateVersion = version;
        if (version == 0) {

            // yet to migrate

        }

        return updateVersion;
    }

    private long getIndexForProperty(Table table, String name) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getColumnName(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }


}