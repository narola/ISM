package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite pastime.
 * Relationship with {@link User}
 * Relationship with {@link RoleModel}
 */
public class UserFavoriteRoleModel extends RealmObject {
    @PrimaryKey
    private  int userFavoriteRoleModelId;
    private User user;
    private RoleModel roleModel;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteRoleModelId() {
        return userFavoriteRoleModelId;
    }

    public void setUserFavoriteRoleModelId(int userFavoriteRoleModelId) {
        this.userFavoriteRoleModelId = userFavoriteRoleModelId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
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
