package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle user tagged in feeds.
 * Relationship with {@link ROUser} ,{@link ROFeeds}
 */
public class ROFeedTaggedUser extends RealmObject {
    @PrimaryKey
    private int feedTaggedUserId;
    private ROUser roUser;
    private ROFeeds feed;
    private ROUser taggedBy;
    private Date createdDate;
    private Date modifiedDate;

    public int getFeedTaggedUserId() {
        return feedTaggedUserId;
    }

    public void setFeedTaggedUserId(int feedTaggedUserId) {
        this.feedTaggedUserId = feedTaggedUserId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROFeeds getFeed() {
        return feed;
    }

    public void setFeed(ROFeeds feed) {
        this.feed = feed;
    }

    public ROUser getTaggedBy() {
        return taggedBy;
    }

    public void setTaggedBy(ROUser taggedBy) {
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
