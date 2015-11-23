package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class SettingPreferences {

    private ArrayList<SMSAlert> sMSAlert;
    private ArrayList<PrivacySetting> privacySetting;
    private ArrayList<NotificationSetting> notificationSettings;



    @JsonProperty("SMS_Alert")
    public ArrayList<SMSAlert> getSMSAlert() {
        return this.sMSAlert;
    }

    public void setSMSAlert(ArrayList<SMSAlert> sMSAlert) {
        this.sMSAlert = sMSAlert;
    }

    @JsonProperty("Privacy_Setting")
    public ArrayList<PrivacySetting> getPrivacySetting() {
        return this.privacySetting;
    }

    public void setPrivacySetting(ArrayList<PrivacySetting> privacySetting) {
        this.privacySetting = privacySetting;
    }

    @JsonProperty("Notification")
    public ArrayList<NotificationSetting> getNotificationSettings() {
        return this.notificationSettings;
    }

    public void setNotificationSettings(ArrayList<NotificationSetting> notificationSettings) {
        this.notificationSettings = notificationSettings;
    }


}
