package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of badges for user/author.
 * Relationship with {@link User}
 * Relationship with {@link Badges}
 */
public class UserBadge extends RealmObject{
    @PrimaryKey
    private  int userBadgeId;
    private User user;
    private Badges badge;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserBadgeId() {
        return userBadgeId;
    }

    public void setUserBadgeId(int userBadgeId) {
        this.userBadgeId = userBadgeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Badges getBadge() {
        return badge;
    }

    public void setBadge(Badges badge) {
        this.badge = badge;
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
