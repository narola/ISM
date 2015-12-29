package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c162 on 27/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notes {
	
    private String userProfilePic;
    private String topicName;
    private String audioLink;
    private String topicId;
    private String createdDate;
    private String noteByUser;
    private String noteText;
    private String imageLink;
    private String noteId;
    private String videoThumbnail;
    private String videoLink;
    private String noteById;
    private String noteTitle;

    @JsonProperty("user_profile_pic")
    public String getUserProfilePic() {
        return this.userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    @JsonProperty("topic_name")
    public String getTopicName() {
        return this.topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @JsonProperty("audio_link")
    public String getAudioLink() {
        return this.audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    @JsonProperty("topic_id")
    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @JsonProperty("created_date")
    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("note_by_user")
    public String getNoteByUser() {
        return this.noteByUser;
    }

    public void setNoteByUser(String noteByUser) {
        this.noteByUser = noteByUser;
    }

    @JsonProperty("note_text")
    public String getNoteText() {
        return this.noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @JsonProperty("image_link")
    public String getImageLink() {
        return this.imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @JsonProperty("note_id")
    public String getNoteId() {
        return this.noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
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

    @JsonProperty("note_by_id")
    public String getNoteById() {
        return this.noteById;
    }

    public void setNoteById(String noteById) {
        this.noteById = noteById;
    }

    @JsonProperty("note_title")
    public String getNoteTitle() {
        return this.noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }


    
}
