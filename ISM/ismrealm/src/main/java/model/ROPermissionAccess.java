package model;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of permission accessability.
 * Relationship with {@link ROPermission}
 * Relationship with {@link ROSystemModule}
 */
public class ROPermissionAccess extends  RealmObject {

    @PrimaryKey
    private  int permissionAccessId;
    private String permissionName;
    private ROPermission roPermission;
    private ROSystemModule roSystemModule;
    private Date createdDate;
    private Date modifiedDate;

    public int getPermissionAccessId() {
        return permissionAccessId;
    }

    public void setPermissionAccessId(int permissionAccessId) {
        this.permissionAccessId = permissionAccessId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public ROPermission getRoPermission() {
        return roPermission;
    }

    public void setRoPermission(ROPermission roPermission) {
        this.roPermission = roPermission;
    }

    public ROSystemModule getRoSystemModule() {
        return roSystemModule;
    }

    public void setRoSystemModule(ROSystemModule roSystemModule) {
        this.roSystemModule = roSystemModule;
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
