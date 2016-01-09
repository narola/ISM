package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle images that is available in feed.
 * Relationship with {@link ROFeeds}
 */
public class ROFeedImage extends RealmObject {

    @PrimaryKey
    private  int feedImageId;
    private ROFeeds feed;
    private String imageLink;
    private Date createdDate;
    private Date modifiedDate;

    public int getFeedImageId() {
        return feedImageId;
    }

    public void setFeedImageId(int feedImageId) {
        this.feedImageId = feedImageId;
    }

    public ROFeeds getFeed() {
        return feed;
    }

    public void setFeed(ROFeeds feed) {
        this.feed = feed;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
