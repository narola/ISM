package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c161 on 21/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feeds {
	
    private String like;
    private String fullName;
    private String modifiedDate;
    private String audioLink;
	private String postedOn;
	private String feedBy;
	private String totalLike;
	private String userId;
	private String createdDate;
	private String feedId;
	private String profilePic;
	private String videoThumbnail;
	private String videoLink;
	private String feedText;
	private String totalComment;
	private ArrayList<Comment> comments;
	private ArrayList<FeedImages> feedImages;

	@JsonProperty("like")
    public String getLike() {
        return this.like;
    }

    public void setLike(String like) {
        this.like = like;
    }

	@JsonProperty("full_name")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

	@JsonProperty("modified_date")
    public String getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

	@JsonProperty("feed_images")
    public ArrayList<FeedImages> getFeedImages() {
        return this.feedImages;
    }

    public void setFeedImages(ArrayList<FeedImages> feedImages) {
        this.feedImages = feedImages;
    }

	@JsonProperty("audio_link")
    public String getAudioLink() {
        return this.audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

	@JsonProperty("posted_on")
    public String getPostedOn() {
        return this.postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

	@JsonProperty("feed_by")
    public String getFeedBy() {
        return this.feedBy;
    }

    public void setFeedBy(String feedBy) {
        this.feedBy = feedBy;
    }

	@JsonProperty("total_like")
    public String getTotalLike() {
        return this.totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

	@JsonProperty("comment_list")
    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

	@JsonProperty("user_id")
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	@JsonProperty("created_date")
    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

	@JsonProperty("feed_id")
    public String getFeedId() {
        return this.feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

	@JsonProperty("profile_pic")
    public String getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

	@JsonProperty("video_thumbnail")
    public String getVideoThumbnail() {
        return this.videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

	@JsonProperty("video_link")
    public String getVideoLink() {
        return this.videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

	@JsonProperty("feed_text")
    public String getFeedText() {
        return this.feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

	@JsonProperty("total_comment")
    public String getTotalComment() {
        return this.totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }

}
