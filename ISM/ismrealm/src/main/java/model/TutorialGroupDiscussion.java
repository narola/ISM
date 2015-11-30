package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail about discussion happen in tutorial group in weekly exam.
 * Relationship with {@link TutorialGroup},{@link User},{@link TutorialTopic}
 */
public class TutorialGroupDiscussion extends RealmObject {

    @PrimaryKey
    private  int tutorialGroupDiscussionId;
    private String message;
    private String messageType;
    private String messageStatus;
    private String mediaLink;
    private String mediaType;
    private TutorialGroup tutorialGroup;
    private TutorialTopic topic;
    private User sender;
    private int commentScore;
    private boolean inActiveHours;
    private Date createdDate;
    private Date modifiedDate;

    public int getTutorialGroupDiscussionId() {
        return tutorialGroupDiscussionId;
    }

    public void setTutorialGroupDiscussionId(int tutorialGroupDiscussionId) {
        this.tutorialGroupDiscussionId = tutorialGroupDiscussionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }

    public void setTutorialGroup(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    public TutorialTopic getTopic() {
        return topic;
    }

    public void setTopic(TutorialTopic topic) {
        this.topic = topic;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(int commentScore) {
        this.commentScore = commentScore;
    }

    public boolean isInActiveHours() {
        return inActiveHours;
    }

    public void setInActiveHours(boolean inActiveHours) {
        this.inActiveHours = inActiveHours;
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
