package com.ism.ws.model;

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
public class Notice implements Parcelable {
	
    private String notice;
    private String postedBy;
    private String noticeTitle;
    private String postedOn;
    private String noticeId;
    private boolean isExpanded;

	public Notice() {
	}

	public Notice(Parcel parcelNotice) {
		this.notice = parcelNotice.readString();
		this.postedBy = parcelNotice.readString();
		this.noticeTitle = parcelNotice.readString();
		this.postedOn = parcelNotice.readString();
		this.noticeId = parcelNotice.readString();
		boolean[] arrayBoolean = new boolean[1];
		parcelNotice.readBooleanArray(arrayBoolean);
		this.isExpanded = arrayBoolean[0];
	}

	@JsonProperty("notice")
    public String getNotice() {
        return this.notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @JsonProperty("posted_by")
    public String getPostedBy() {
        return this.postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    @JsonProperty("notice_title")
    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    @JsonProperty("posted_on")
    public String getPostedOn() {
        return this.postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    @JsonProperty("notice_id")
    public String getNoticeId() {
        return this.noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setIsExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getNotice());
		dest.writeString(getPostedBy());
		dest.writeString(getNoticeTitle());
		dest.writeString(getPostedOn());
		dest.writeString(getNoticeId());
		dest.writeBooleanArray(new boolean[]{isExpanded()});
	}

	public static final Parcelable.Creator<Notice> CREATOR = new Parcelable.Creator<Notice>() {
		@Override
		public Notice createFromParcel(Parcel source) {
			return new Notice(source);
		}

		@Override
		public Notice[] newArray(int size) {
			return new Notice[size];
		}
	};

}