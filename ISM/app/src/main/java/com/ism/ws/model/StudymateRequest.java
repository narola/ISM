package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 19/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudymateRequest {
	
    private String requestDate;
    private String status;
    private String requestFromName;
    private String recordId;
    private String isSeen;
    private String requesterProfile;
    private String requestFromId;

    @JsonProperty("request_date")
    public String getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("request_from_name")
    public String getRequestFromName() {
        return this.requestFromName;
    }

    public void setRequestFromName(String requestFromName) {
        this.requestFromName = requestFromName;
    }

    @JsonProperty("record_id")
    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @JsonProperty("is_seen")
    public String getIsSeen() {
        return this.isSeen;
    }

    public void setIsSeen(String isSeen) {
        this.isSeen = isSeen;
    }

    @JsonProperty("requester_profile")
    public String getRequesterProfile() {
        return this.requesterProfile;
    }

    public void setRequesterProfile(String requesterProfile) {
        this.requesterProfile = requesterProfile;
    }

    @JsonProperty("request_from_id")
    public String getRequestFromId() {
        return this.requestFromId;
    }

    public void setRequestFromId(String requestFromId) {
        this.requestFromId = requestFromId;
    }

}
