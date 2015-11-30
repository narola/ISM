package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of notificatios.
 * Relationship with {@link User}
 */

public class UserNotification extends RealmObject {

    @PrimaryKey
    private  int userNotificationId;
    private String notificationText;
    private User notificationTo;
    private User notificationFrom;
    private int navigateTo;
    private boolean isRead;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserNotificationId() {
        return userNotificationId;
    }

    public void setUserNotificationId(int userNotificationId) {
        this.userNotificationId = userNotificationId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public User getNotificationTo() {
        return notificationTo;
    }

    public void setNotificationTo(User notificationTo) {
        this.notificationTo = notificationTo;
    }

    public User getNotificationFrom() {
        return notificationFrom;
    }

    public void setNotificationFrom(User notificationFrom) {
        this.notificationFrom = notificationFrom;
    }

    public int getNavigateTo() {
        return navigateTo;
    }

    public void setNavigateTo(int navigateTo) {
        this.navigateTo = navigateTo;
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
