package com.ism.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptTutorialGroupRequest {
	
    private String userId;
    private String joiningStatus;
    private String groupId;

    public String getUserId() {
        return this.userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJoiningStatus() {
        return this.joiningStatus;
    }

    @JsonProperty("joining_status")
    public void setJoiningStatus(String joiningStatus) {
        this.joiningStatus = joiningStatus;
    }

    public String getGroupId() {
        return this.groupId;
    }

    @JsonProperty("group_id")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
