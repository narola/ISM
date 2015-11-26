package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite pastime.
 * Relationship with {@link User}
 * Relationship with {@link Pastime}
 */
public class UserFavoritePastime extends RealmObject {
    @PrimaryKey
    private  int userFavoritePastimeId;
    private User user;
    private Pastime pastime;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoritePastimeId() {
        return userFavoritePastimeId;
    }

    public void setUserFavoritePastimeId(int userFavoritePastimeId) {
        this.userFavoritePastimeId = userFavoritePastimeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pastime getPast() {
        return pastime;
    }

    public void setPast(Pastime past) {
        this.pastime = past;
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
