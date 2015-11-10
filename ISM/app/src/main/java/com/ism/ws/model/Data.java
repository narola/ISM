package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.BitSet;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

	private String id;
	private String credentialId;
	private String schoolName;
	private String courseId;
	private String courseName;
	private String roleId;
	private String schoolId;
	private String schoolDestrict;
	private String academicYear;
	private String userId;
	private String userName;
    private String classId;
    private String className;
    private String countryName;
    private String stateName;
    private String cityName;
    private String profilePic;
    private String schoolType;
    private String fullName;
	private String tutorialGroupId;
	private String tutorialGroupJoiningStatus;
	private String tutorialGroupComplete;
	private String tutorialGroupName;
	private String like;
	private String modifiedDate;
	private String audioLink;
	private String postedOn;
	private String feedBy;
	private String totalLike;
	private String createdDate;
	private String feedId;
	private String videoLink;
	private String feedText;
	private String totalComment;
	private String comment;
	private String commentBy;
	private String profileLink;
	private String noticeId;
	private String noticeTitle;
	private String notice;
	private String postedBy;
	private String notificationDate;
	private String notificationFromId;
	private String navigateTo;
	private String notificationToId;
	private String notificationText;
	private String notificationToName;
	private String notificationId;
	private String notificationFromName;
	private String notificationFromProfilePic;
	private String isRead;
	private String status;
	private String senderName;
	private String messageId;
	private String messageText;
	private String senderProfilePic;
	private String sentOn;
	private String senderId;
	private String requestDate;
	private String requestFromName;
	private String isSeen;
	private String requestFromId;
	private String requesterProfile;
	private String requesterSchoolName;
	private String requesterCourseName;
	private String notificationCount;
	private String messageCount;
	private String requestCount;

	private ArrayList<TutorialGroupMember> tutorialGroupMembers;
	private ArrayList<Comment> comments;

	private boolean isFlagged;
	private ArrayList<Data> blockedList;

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isExpanded) {
		this.isFlagged = isExpanded;
	}

	@JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	@JsonProperty("credential_id")
	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	@JsonProperty("school_name")
    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @JsonProperty("course_id")
	public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @JsonProperty("course_name")
    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @JsonProperty("role_id")
    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @JsonProperty("school_id")
    public String getSchoolId() {
        return this.schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @JsonProperty("academic_year")
    public String getAcademicYear() {
        return this.academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

	@JsonProperty("user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("username")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("class_id")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@JsonProperty("class_name")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@JsonProperty("district_name")
	public String getSchoolDestrict() {
		return schoolDestrict;
	}

	public void setSchoolDestrict(String schoolDestrict) {
		this.schoolDestrict = schoolDestrict;
	}

	@JsonProperty("country_name")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@JsonProperty("state_name")
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@JsonProperty("city_name")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@JsonProperty("profile_pic")
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@JsonProperty("school_type")
	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	@JsonProperty("full_name")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@JsonProperty("tutorial_group_id")
	public String getTutorialGroupId() {
		return tutorialGroupId;
	}

	public void setTutorialGroupId(String tutorialGroupId) {
		this.tutorialGroupId = tutorialGroupId;
	}

	@JsonProperty("tutorial_group_joining_status")
	public String getTutorialGroupJoiningStatus() {
		return tutorialGroupJoiningStatus;
	}

	public void setTutorialGroupJoiningStatus(String tutorialGroupJoiningStatus) {
		this.tutorialGroupJoiningStatus = tutorialGroupJoiningStatus;
	}

	@JsonProperty("tutorial_group_complete")
	public String getTutorialGroupComplete() {
		return tutorialGroupComplete;
	}

	public void setTutorialGroupComplete(String tutorialGroupComplete) {
		this.tutorialGroupComplete = tutorialGroupComplete;
	}

	@JsonProperty("tutorial_group_name")
	public String getTutorialGroupName() {
		return tutorialGroupName;
	}

	public void setTutorialGroupName(String tutorialGroupName) {
		this.tutorialGroupName = tutorialGroupName;
	}

	@JsonProperty("tutorial_group_members")
	public ArrayList<TutorialGroupMember> getTutorialGroupMembers() {
		return this.tutorialGroupMembers;
	}

	public void setTutorialGroupMembers(ArrayList<TutorialGroupMember> tutorialGroupMembers) {
		this.tutorialGroupMembers = tutorialGroupMembers;
	}

	@JsonProperty("like")
	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	@JsonProperty("modified_date")
	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@JsonProperty("audio_link")
	public String getAudioLink() {
		return audioLink;
	}

	public void setAudioLink(String audioLink) {
		this.audioLink = audioLink;
	}

	@JsonProperty("posted_on")
	public String getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(String postedOn) {
		this.postedOn = postedOn;
	}

	@JsonProperty("feed_by")
	public String getFeedBy() {
		return feedBy;
	}

	public void setFeedBy(String feedBy) {
		this.feedBy = feedBy;
	}

	@JsonProperty("total_like")
	public String getTotalLike() {
		return totalLike;
	}

	public void setTotalLike(String totalLike) {
		this.totalLike = totalLike;
	}

	@JsonProperty("comment_list")
	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	@JsonProperty("created_date")
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@JsonProperty("feed_id")
	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	@JsonProperty("video_link")
	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	@JsonProperty("feed_text")
	public String getFeedText() {
		return feedText;
	}

	public void setFeedText(String feedText) {
		this.feedText = feedText;
	}

	@JsonProperty("total_comment")
	public String getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(String totalComment) {
		this.totalComment = totalComment;
	}

	@JsonProperty("comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonProperty("comment_by")
	public String getCommentBy() {
		return commentBy;
	}

	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}

	@JsonProperty("profile_link")
	public String getProfileLink() {
		return profileLink;
	}

	public void setProfileLink(String profileLink) {
		this.profileLink = profileLink;
	}

	@JsonProperty("notice_id")
	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	@JsonProperty("notice_title")
	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	@JsonProperty("notice")
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@JsonProperty("posted_by")
	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	@JsonProperty("notification_date")
	public String getNotificationDate() {
		return this.notificationDate;
	}

	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

	@JsonProperty("notification_from_id")
	public String getNotificationFromId() {
		return this.notificationFromId;
	}

	public void setNotificationFromId(String notificationFromId) {
		this.notificationFromId = notificationFromId;
	}

	@JsonProperty("navigate_to")
	public String getNavigateTo() {
		return this.navigateTo;
	}

	public void setNavigateTo(String navigateTo) {
		this.navigateTo = navigateTo;
	}

	@JsonProperty("notification_to_id")
	public String getNotificationToId() {
		return this.notificationToId;
	}

	public void setNotificationToId(String notificationToId) {
		this.notificationToId = notificationToId;
	}

	@JsonProperty("notification_text")
	public String getNotificationText() {
		return this.notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	@JsonProperty("notification_to_name")
	public String getNotificationToName() {
		return this.notificationToName;
	}

	public void setNotificationToName(String notificationToName) {
		this.notificationToName = notificationToName;
	}

	@JsonProperty("notification_id")
	public String getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	@JsonProperty("notification_from_name")
	public String getNotificationFromName() {
		return this.notificationFromName;
	}

	public void setNotificationFromName(String notificationFromName) {
		this.notificationFromName = notificationFromName;
	}

	@JsonProperty("notification_from_profile_pic")
	public String getNotificationFromProfilePic() {
		return notificationFromProfilePic;
	}

	public void setNotificationFromProfilePic(String notificationFromProfilePic) {
		this.notificationFromProfilePic = notificationFromProfilePic;
	}

	@JsonProperty("is_read")
	public String getIsRead() {
		return this.isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@JsonProperty("status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("sender_name")
	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@JsonProperty("message_id")
	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@JsonProperty("message_text")
	public String getMessageText() {
		return this.messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	@JsonProperty("sender_profile_pic")
	public String getSenderProfilePic() {
		return this.senderProfilePic;
	}

	public void setSenderProfilePic(String senderProfilePic) {
		this.senderProfilePic = senderProfilePic;
	}

	@JsonProperty("sent_on")
	public String getSentOn() {
		return this.sentOn;
	}

	public void setSentOn(String sentOn) {
		this.sentOn = sentOn;
	}

	@JsonProperty("sender_id")
	public String getSenderId() {
		return this.senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	@JsonProperty("request_date")
	public String getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	@JsonProperty("request_from_name")
	public String getRequestFromName() {
		return this.requestFromName;
	}

	public void setRequestFromName(String requestFromName) {
		this.requestFromName = requestFromName;
	}

	@JsonProperty("is_seen")
	public String getIsSeen() {
		return this.isSeen;
	}

	public void setIsSeen(String isSeen) {
		this.isSeen = isSeen;
	}

	@JsonProperty("request_from_id")
	public String getRequestFromId() {
		return this.requestFromId;
	}

	public void setRequestFromId(String requestFromId) {
		this.requestFromId = requestFromId;
	}

	@JsonProperty("requester_profile")
	public String getRequesterProfile() {
		return this.requesterProfile;
	}

	public void setRequesterProfile(String requesterProfile) {
		this.requesterProfile = requesterProfile;
	}

	@JsonProperty("requester_school_name")
	public String getRequesterSchoolName() {
		return this.requesterSchoolName;
	}

	public void setRequesterSchoolName(String requesterSchoolName) {
		this.requesterSchoolName = requesterSchoolName;
	}

	@JsonProperty("requester_course_name")
	public String getRequesterCourseName() {
		return this.requesterCourseName;
	}

	public void setRequesterCourseName(String requesterCourseName) {
		this.requesterCourseName = requesterCourseName;
	}

	public ArrayList<Data> getBlockedList() {
		return blockedList;
	}

	@JsonProperty("request_count")
	public String getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(String requestCount) {
		this.requestCount = requestCount;
	}

	@JsonProperty("message_count")
	public String getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(String messageCount) {
		this.messageCount = messageCount;
	}

	@JsonProperty("notification_count")
	public String getNotificationCount() {
		return notificationCount;
	}

	public void setNotificationCount(String notificationCount) {
		this.notificationCount = notificationCount;
	}
}
