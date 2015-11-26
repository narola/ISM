package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' permission.
 * Relationship with {@link UserPermission}
 */
public class UserPermission extends RealmObject {

    @PrimaryKey
    private  int userPermissionId;
    private User user;
    private UserPermission userPermission;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserPermissionId() {
        return userPermissionId;
    }

    public void setUserPermissionId(int userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserPermission getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(UserPermission userPermission) {
        this.userPermission = userPermission;
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
