package com.ism.author.ws.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 23/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Parcelable {
	
    private String status;
    private String senderName;
    private String recordId;
    private String messageId;
    private String messageText;
    private String senderProfilePic;
    private String sentOn;
    private String senderId;
    private String isRead;

	public Message() {
	}

	public Message(Parcel parcelMessage) {
		this.status = parcelMessage.readString();
		this.senderName = parcelMessage.readString();
		this.recordId = parcelMessage.readString();
		this.messageId = parcelMessage.readString();
		this.messageText = parcelMessage.readString();
		this.senderProfilePic = parcelMessage.readString();
		this.sentOn = parcelMessage.readString();
		this.senderId = parcelMessage.readString();
		this.isRead = parcelMessage.readString();
	}

	@JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("sender_name")
    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @JsonProperty("record_id")
    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @JsonProperty("message_id")
    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @JsonProperty("message_text")
    public String getMessageText() {
        return this.messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @JsonProperty("sender_profile_pic")
    public String getSenderProfilePic() {
        return this.senderProfilePic;
    }

    public void setSenderProfilePic(String senderProfilePic) {
        this.senderProfilePic = senderProfilePic;
    }

    @JsonProperty("sent_on")
    public String getSentOn() {
        return this.sentOn;
    }

    public void setSentOn(String sentOn) {
        this.sentOn = sentOn;
    }

    @JsonProperty("sender_id")
    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @JsonProperty("is_read")
    public String getIsRead() {
        return this.isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
	    dest.writeString(getStatus());
	    dest.writeString(getSenderName());
	    dest.writeString(getRecordId());
	    dest.writeString(getMessageId());
	    dest.writeString(getMessageText());
	    dest.writeString(getSenderProfilePic());
	    dest.writeString(getSentOn());
	    dest.writeString(getSenderId());
	    dest.writeString(getIsRead());
    }

	public static final Creator<Message> CREATOR = new Creator<Message>() {
		@Override
		public Message createFromParcel(Parcel source) {
			return new Message(source);
		}

		@Override
		public Message[] newArray(int size) {
			return new Message[size];
		}
	};

}
