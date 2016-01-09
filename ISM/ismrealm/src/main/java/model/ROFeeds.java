package model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle feeds of user.
 * Relationship with {@link ROUser}
 * Relationship with{@link ROFeedComment}
 * Relationship with{@link ROFeedImage}
 */

public class ROFeeds extends RealmObject {

    @PrimaryKey
    private int feedId;
    private ROUser feedBy;
    private ROUser roUser;
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

    private RealmList<ROFeedComment> comments = new RealmList<ROFeedComment>();
    private RealmList<ROFeedImage> ROFeedImages = new RealmList<ROFeedImage>();

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public ROUser getFeedBy() {
        return feedBy;
    }

    public void setFeedBy(ROUser feedBy) {
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

    public RealmList<ROFeedComment> getComments() {
        return comments;
    }

    public void setComments(RealmList<ROFeedComment> comments) {
        this.comments = comments;
    }

    public RealmList<ROFeedImage> getROFeedImages() {
        return ROFeedImages;
    }

    public void setROFeedImages(RealmList<ROFeedImage> ROFeedImages) {
        this.ROFeedImages = ROFeedImages;
    }

    public int isSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }


}
