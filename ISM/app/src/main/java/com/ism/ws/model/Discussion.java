package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 22/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Discussion {

	private String tutorialGroupDiscussionId;
	private String tutorialTopicId;
	private String messageType;
	private String mediaLink;
	private String userId;
	private String fullName;
	private String comment;
	private String profilePic;
	private String commentTimestamp;
	private String weekDay;
	private int topicPosition;
    private boolean showDetails;


	@JsonProperty("tutorial_topic_id")
	public String getTutorialTopicId() {
		return this.tutorialTopicId;
	}

	public void setTutorialTopicId(String tutorialTopicId) {
		this.tutorialTopicId = tutorialTopicId;
	}

	@JsonProperty("message_type")
	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@JsonProperty("media_link")
	public String getMediaLink() {
		return this.mediaLink;
	}

	public void setMediaLink(String mediaLink) {
		this.mediaLink = mediaLink;
	}

	@JsonProperty("user_id")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("full_name")
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@JsonProperty("comment")
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonProperty("profile_pic")
	public String getProfilePic() {
		return this.profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@JsonProperty("comment_timestamp")
	public String getCommentTimestamp() {
		return this.commentTimestamp;
	}

	public void setCommentTimestamp(String commentTimestamp) {
		this.commentTimestamp = commentTimestamp;
	}

	@JsonProperty("group_discussion_id")
	public String getTutorialGroupDiscussionId() {
		return tutorialGroupDiscussionId;
	}

	public void setTutorialGroupDiscussionId(String tutorialGroupDiscussionId) {
		this.tutorialGroupDiscussionId = tutorialGroupDiscussionId;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public int getTopicPosition() {
		return topicPosition;
	}

	public void setTopicPosition(int topicPosition) {
		this.topicPosition = topicPosition;
	}

	public boolean isShowDetails() {
		return showDetails;
	}

	public void setShowDetails(boolean showDetails) {
		this.showDetails = showDetails;
	}


}
