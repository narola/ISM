package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - message detail of reciever and other info related to {@link Messages}.
 * Relationship with {@link Messages}
 * Relationship with {@link User}
 */
public class MessageReceiver extends RealmObject {

    @PrimaryKey
    private  int messageReceiverId;
    private Messages message;
    private User receiver;
    private int groupStatus;
    private boolean isRead;
    private Date createdDate;
    private Date modifiedDate;

    public int getMessageReceiverId() {
        return messageReceiverId;
    }

    public void setMessageReceiverId(int messageReceiverId) {
        this.messageReceiverId = messageReceiverId;
    }

    public Messages getMessage() {
        return message;
    }

    public void setMessage(Messages message) {
        this.message = message;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
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
