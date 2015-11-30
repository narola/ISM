package model;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of permission accessability.
 * Relationship with {@link Permission}
 * Relationship with {@link SystemModule}
 */
public class PermissionAccess extends  RealmObject {

    @PrimaryKey
    private  int permissionAccessId;
    private String permissionName;
    private Permission permission;
    private SystemModule systemModule;
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

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public SystemModule getSystemModule() {
        return systemModule;
    }

    public void setSystemModule(SystemModule systemModule) {
        this.systemModule = systemModule;
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
