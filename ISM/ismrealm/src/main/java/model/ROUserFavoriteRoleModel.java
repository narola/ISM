package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite pastime.
 * Relationship with {@link ROUser}
 * Relationship with {@link RORoleModel}
 */
public class ROUserFavoriteRoleModel extends RealmObject {
    @PrimaryKey
    private  int userFavoriteRoleModelId;
    private ROUser roUser;
    private RORoleModel roRoleModel;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteRoleModelId() {
        return userFavoriteRoleModelId;
    }

    public void setUserFavoriteRoleModelId(int userFavoriteRoleModelId) {
        this.userFavoriteRoleModelId = userFavoriteRoleModelId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public RORoleModel getRoRoleModel() {
        return roRoleModel;
    }

    public void setRoRoleModel(RORoleModel roRoleModel) {
        this.roRoleModel = roRoleModel;
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
