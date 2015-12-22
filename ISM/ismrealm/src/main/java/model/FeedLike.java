package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle feed likes by users.
 * Relationship with {@link Feeds},{@link User}
 */
public class FeedLike extends RealmObject {

    @PrimaryKey
    private int feedLikeId;
    private User likeBy;
    private Feeds feed;
    private Date createdDate;
    private Date modifiedDate;
    private int isLiked;
    private int isSync;


    /**
     * new added by c166
     */
    private String feedId;
    private String userId;

    public int getFeedLikeId() {
        return feedLikeId;
    }

    public void setFeedLikeId(int feedLikeId) {
        this.feedLikeId = feedLikeId;
    }

    public User getLikeBy() {
        return likeBy;
    }

    public void setLikeBy(User likeBy) {
        this.likeBy = likeBy;
    }

    public Feeds getFeed() {
        return feed;
    }

    public void setFeed(Feeds feed) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

}
