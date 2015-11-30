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
    private  int feedLikeId;
    private User likeBy;
    private Feeds feed;
    private Date createdDate;
    private Date modifiedDate;

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
}
