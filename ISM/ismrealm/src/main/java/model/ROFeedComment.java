package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle comments on feed.
 * Relationship with {@link ROUser},{@link ROFeeds}
 */
public class ROFeedComment extends RealmObject {

    @PrimaryKey
    private int feedCommentId;
    private ROUser commentBy;
    private String comment;
    private ROFeeds feed;
    private Date createdDate;
    private Date modifiedDate;

    public int getFeedCommentId() {
        return feedCommentId;
    }

    public void setFeedCommentId(int feedCommentId) {
        this.feedCommentId = feedCommentId;
    }

    public ROUser getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(ROUser commentBy) {
        this.commentBy = commentBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
