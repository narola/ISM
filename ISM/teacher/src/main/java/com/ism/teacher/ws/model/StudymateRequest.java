package com.ism.teacher.ws.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 19/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudymateRequest implements Parcelable {
	
    private String requestDate;
    private String status;
    private String requestFromName;
    private String recordId;
    private String isSeen;
    private String requesterProfile;
    private String requestFromId;

	public StudymateRequest() {
	}

	public StudymateRequest(Parcel parcelStudymateRequest) {
		this.requestDate = parcelStudymateRequest.readString();
		this.status = parcelStudymateRequest.readString();
		this.requestFromName = parcelStudymateRequest.readString();
		this.recordId = parcelStudymateRequest.readString();
		this.isSeen = parcelStudymateRequest.readString();
		this.requesterProfile = parcelStudymateRequest.readString();
		this.requestFromId = parcelStudymateRequest.readString();
	}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getRequestDate());
        dest.writeString(getStatus());
        dest.writeString(getRequestFromName());
        dest.writeString(getRecordId());
        dest.writeString(getIsSeen());
        dest.writeString(getRequesterProfile());
        dest.writeString(getRequestFromId());
    }

	public static final Creator<StudymateRequest> CREATOR = new Creator<StudymateRequest>() {

		@Override
		public StudymateRequest createFromParcel(Parcel source) {
			return new StudymateRequest(source);
		}

		@Override
		public StudymateRequest[] newArray(int size) {
			return new StudymateRequest[size];
		}
	};

}
