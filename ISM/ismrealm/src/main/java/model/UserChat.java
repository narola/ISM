package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle chat of users.
 * Relationship with {@link User}
 */
public class UserChat extends RealmObject {

    @PrimaryKey
    private  int userChatId;
    private User sender;
    private User receiver;
    private String message;
    private String mediaLink;
    private String mediaType;
    private int groupStatus;
    private boolean receivedStatus;
    private Date createdDate;

    public int getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(int userChatId) {
        this.userChatId = userChatId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public int getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }

    public boolean isReceivedStatus() {
        return receivedStatus;
    }

    public void setReceivedStatus(boolean receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
