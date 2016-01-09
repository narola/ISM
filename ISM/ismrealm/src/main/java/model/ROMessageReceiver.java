package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - message detail of reciever and other info related to {@link ROMessages}.
 * Relationship with {@link ROMessages}
 * Relationship with {@link ROUser}
 */
public class ROMessageReceiver extends RealmObject {

    @PrimaryKey
    private  int messageReceiverId;
    private ROMessages message;
    private ROUser receiver;
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

    public ROMessages getMessage() {
        return message;
    }

    public void setMessage(ROMessages message) {
        this.message = message;
    }

    public ROUser getReceiver() {
        return receiver;
    }

    public void setReceiver(ROUser receiver) {
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
