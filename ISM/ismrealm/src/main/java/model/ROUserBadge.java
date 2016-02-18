package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of badges for user/author.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROBadges}
 */
public class ROUserBadge extends RealmObject{
    @PrimaryKey
    private  int userBadgeId;
    private ROUser roUser;
    private ROBadges badge;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserBadgeId() {
        return userBadgeId;
    }

    public void setUserBadgeId(int userBadgeId) {
        this.userBadgeId = userBadgeId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROBadges getBadge() {
        return badge;
    }

    public void setBadge(ROBadges badge) {
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
