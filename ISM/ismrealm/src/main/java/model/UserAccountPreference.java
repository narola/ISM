package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - user preference info.
 * Relationship with {@link Preferences}
 * Relationship with {@link User}
 */
public class UserAccountPreference extends RealmObject{

    @PrimaryKey
    private  int userAccountPreferenceId;
    private String preferenceValue;
    private Preferences preference;
    private User userId;
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

    public Preferences getPreference() {
        return preference;
    }

    public void setPreference(Preferences preference) {
        this.preference = preference;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
