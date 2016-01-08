package model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle feeds of user.
 * Relationship with {@link User}
 * Relationship with{@link FeedComment}
 * Relationship with{@link FeedImage}
 */

public class Feeds extends RealmObject {

    @PrimaryKey
    private int feedId;
    private User feedBy;
    private User user;
    private String feedText;
    private String videoLink;
    private String videoThumbnail;
    private String audioLink;
    private int totalLike;
    private int totalComment;
    private Date postedOn;
    private Date createdDate;
    private Date modifiedDate;
    private int isSync;
    private String selfLike;

    private RealmList<FeedComment> comments = new RealmList<FeedComment>();
    private RealmList<FeedImage> feedImages = new RealmList<FeedImage>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public User getFeedBy() {
        return feedBy;
    }

    public void setFeedBy(User feedBy) {
        this.feedBy = feedBy;
    }

    public String getFeedText() {
        return feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public Date getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
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

    public String getSelfLike() {
        return selfLike;
    }

    public void setSelfLike(String selfLike) {
        this.selfLike = selfLike;
    }

    public RealmList<FeedComment> getComments() {
        return comments;
    }

    public void setComments(RealmList<FeedComment> comments) {
        this.comments = comments;
    }

    public RealmList<FeedImage> getFeedImages() {
        return feedImages;
    }

    public void setFeedImages(RealmList<FeedImage> feedImages) {
        this.feedImages = feedImages;
    }

    public int isSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }


}
