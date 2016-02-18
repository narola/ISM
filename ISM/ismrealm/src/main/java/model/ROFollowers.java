package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle followers of users.
 * Relationship with {@link ROUser}
 */
public class ROFollowers extends RealmObject{

    @PrimaryKey
    private  int followerId;
    private ROUser follower;
    private ROUser followerTo;
    private int followStatus;
    private Date createdDate;
    private Date modifiedDate;

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public ROUser getFollower() {
        return follower;
    }

    public void setFollower(ROUser follower) {
        this.follower = follower;
    }

    public ROUser getFollowerTo() {
        return followerTo;
    }

    public void setFollowerTo(ROUser followerTo) {
        this.followerTo = followerTo;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
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
