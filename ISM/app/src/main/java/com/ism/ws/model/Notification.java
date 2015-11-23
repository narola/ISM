package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 23/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
	
    private String notificationDate;
    private String notificationFromId;
    private String recordId;
    private String navigateTo;
    private String notificationToId;
    private String notificationText;
    private String notificationFromProfilePic;
    private String notificationToName;
    private String notificationFromName;
    private String isRead;

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

    @JsonProperty("record_id")
    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    @JsonProperty("notification_from_profile_pic")
    public String getNotificationFromProfilePic() {
        return this.notificationFromProfilePic;
    }

    public void setNotificationFromProfilePic(String notificationFromProfilePic) {
        this.notificationFromProfilePic = notificationFromProfilePic;
    }

    @JsonProperty("notification_to_name")
    public String getNotificationToName() {
        return this.notificationToName;
    }

    public void setNotificationToName(String notificationToName) {
        this.notificationToName = notificationToName;
    }

    @JsonProperty("notification_from_name")
    public String getNotificationFromName() {
        return this.notificationFromName;
    }

    public void setNotificationFromName(String notificationFromName) {
        this.notificationFromName = notificationFromName;
    }

    @JsonProperty("is_read")
    public String getIsRead() {
        return this.isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

}
