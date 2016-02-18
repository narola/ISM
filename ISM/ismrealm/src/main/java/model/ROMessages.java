package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - contain message information.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROMessages}
 */
public class ROMessages extends RealmObject {

    @PrimaryKey
    private  int messageId;
    private String message;
    private String messageTitle;
    private String status;
    private ROUser sender;
    private ROMessages replyFor;
    private Date createdDate;
    private Date modifiedDate;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ROUser getSender() {
        return sender;
    }

    public void setSender(ROUser sender) {
        this.sender = sender;
    }

    public ROMessages getReplyFor() {
        return replyFor;
    }

    public void setReplyFor(ROMessages replyFor) {
        this.replyFor = replyFor;
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
