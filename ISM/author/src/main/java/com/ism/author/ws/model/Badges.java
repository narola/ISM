package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c162 on 26/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Badges {
	
    private String requestCount;
    private String notificationCount;
    private String messageCount;

    @JsonProperty("request_count")
    public String getRequestCount() {
        return this.requestCount;
    }

    public void setRequestCount(String requestCount) {
        this.requestCount = requestCount;
    }

    @JsonProperty("notification_count")
    public String getNotificationCount() {
        return this.notificationCount;
    }

    public void setNotificationCount(String notificationCount) {
        this.notificationCount = notificationCount;
    }

    @JsonProperty("message_count")
    public String getMessageCount() {
        return this.messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

}
