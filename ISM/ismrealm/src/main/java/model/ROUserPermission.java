package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' permission.
 * Relationship with {@link ROUserPermission}
 */
public class ROUserPermission extends RealmObject {

    @PrimaryKey
    private  int userPermissionId;
    private ROUser roUser;
    private ROPermissionAccess roPermissionAccess;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserPermissionId() {
        return userPermissionId;
    }

    public void setUserPermissionId(int userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROPermissionAccess getRoPermissionAccess() {
        return roPermissionAccess;
    }

    public void setRoPermissionAccess(ROPermissionAccess roPermissionAccess) {
        this.roPermissionAccess = roPermissionAccess;
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
