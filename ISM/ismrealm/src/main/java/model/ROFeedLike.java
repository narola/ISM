package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle feed likes by users.
 * Relationship with {@link ROFeeds},{@link ROUser}
 */
public class ROFeedLike extends RealmObject {

    @PrimaryKey
    private int feedLikeId;
    private ROUser likeBy;
    private ROFeeds feed;
    private Date createdDate;
    private Date modifiedDate;
    private int isLiked;
    private int isSync;


    public int getFeedLikeId() {
        return feedLikeId;
    }

    public void setFeedLikeId(int feedLikeId) {
        this.feedLikeId = feedLikeId;
    }

    public ROUser getLikeBy() {
        return likeBy;
    }

    public void setLikeBy(ROUser likeBy) {
        this.likeBy = likeBy;
    }

    public ROFeeds getFeed() {
        return feed;
    }

    public void setFeed(ROFeeds feed) {
        this.feed = feed;
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

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public int isSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }

}
