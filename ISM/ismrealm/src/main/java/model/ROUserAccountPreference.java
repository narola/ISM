package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - user preference info.
 * Relationship with {@link ROPreferences}
 * Relationship with {@link ROUser}
 */
public class ROUserAccountPreference extends RealmObject{

    @PrimaryKey
    private  int userAccountPreferenceId;
    private String preferenceValue;
    private ROPreferences preference;
    private ROUser ROUserId;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserAccountPreferenceId() {
        return userAccountPreferenceId;
    }

    public void setUserAccountPreferenceId(int userAccountPreferenceId) {
        this.userAccountPreferenceId = userAccountPreferenceId;
    }

    public String getPreferenceValue() {
        return preferenceValue;
    }

    public void setPreferenceValue(String preferenceValue) {
        this.preferenceValue = preferenceValue;
    }

    public ROPreferences getPreference() {
        return preference;
    }

    public void setPreference(ROPreferences preference) {
        this.preference = preference;
    }

    public ROUser getROUserId() {
        return ROUserId;
    }

    public void setROUserId(ROUser ROUserId) {
        this.ROUserId = ROUserId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
