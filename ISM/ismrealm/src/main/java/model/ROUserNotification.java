package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of notificatios.
 * Relationship with {@link ROUser}
 */

public class ROUserNotification extends RealmObject {

    @PrimaryKey
    private  int userNotificationId;
    private String notificationText;
    private ROUser notificationTo;
    private ROUser notificationFrom;
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

    public ROUser getNotificationTo() {
        return notificationTo;
    }

    public void setNotificationTo(ROUser notificationTo) {
        this.notificationTo = notificationTo;
    }

    public ROUser getNotificationFrom() {
        return notificationFrom;
    }

    public void setNotificationFrom(ROUser notificationFrom) {
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
