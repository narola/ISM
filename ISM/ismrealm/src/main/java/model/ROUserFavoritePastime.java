package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite pastime.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROPastime}
 */
public class ROUserFavoritePastime extends RealmObject {
    @PrimaryKey
    private  int userFavoritePastimeId;
    private ROUser roUser;
    private ROPastime ROPastime;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoritePastimeId() {
        return userFavoritePastimeId;
    }

    public void setUserFavoritePastimeId(int userFavoritePastimeId) {
        this.userFavoritePastimeId = userFavoritePastimeId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROPastime getROPastime() {
        return ROPastime;
    }

    public void setROPastime(ROPastime ROPastime) {
        this.ROPastime = ROPastime;
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
