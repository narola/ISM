package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle user tagged in feeds.
 * Relationship with {@link User} ,{@link Feeds}
 */
public class FeedTaggedUser extends RealmObject {
    @PrimaryKey
    private int feedTaggedUserId;
    private User user;
    private Feeds feed;
    private User taggedBy;
    private Date createdDate;
    private Date modifiedDate;

    public int getFeedTaggedUserId() {
        return feedTaggedUserId;
    }

    public void setFeedTaggedUserId(int feedTaggedUserId) {
        this.feedTaggedUserId = feedTaggedUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Feeds getFeed() {
        return feed;
    }

    public void setFeed(Feeds feed) {
        this.feed = feed;
    }

    public User getTaggedBy() {
        return taggedBy;
    }

    public void setTaggedBy(User taggedBy) {
        this.taggedBy = taggedBy;
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
