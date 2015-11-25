package com.ism.teacher.ws.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tags implements Parcelable {

    private String tagName;
    private String tagId;

    public Tags() {
    }

    public Tags(Parcel parcelTags) {
        this.tagName = parcelTags.readString();
        this.tagId = parcelTags.readString();
    }

    @JsonProperty("tag_name")
    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonProperty("tag_id")
    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getTagName());
        dest.writeString(getTagId());
    }


    public static final Parcelable.Creator<Tags> CREATOR = new Parcelable.Creator<Tags>() {
        @Override
        public Tags createFromParcel(Parcel source) {
            return new Tags(source);
        }

        @Override
        public Tags[] newArray(int size) {
            return new Tags[size];
        }
    };
}
