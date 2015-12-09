package com.ism.author.ws.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Answers implements Parcelable {

    private String imageLink;
    private String id;
    private String choiceText;
    private String isRight;
    private String questionId;
    private String audioLink;
    private String videoLink;

    public Answers() {
    }


    public Answers(Parcel parcelAnswers) {
        this.imageLink = parcelAnswers.readString();
        this.id = parcelAnswers.readString();
        this.choiceText = parcelAnswers.readString();
        this.isRight = parcelAnswers.readString();
        this.questionId = parcelAnswers.readString();
        this.audioLink = parcelAnswers.readString();
        this.videoLink = parcelAnswers.readString();
    }

    @JsonProperty("image_link")
    public String getImageLink() {
        return this.imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("choice_text")
    public String getChoiceText() {
        return this.choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    @JsonProperty("is_right")
    public String getIsRight() {
        return this.isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    @JsonProperty("question_id")
    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("audio_link")
    public String getAudioLink() {
        return this.audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    @JsonProperty("video_link")
    public String getVideoLink() {
        return this.videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getImageLink());
        dest.writeString(getId());
        dest.writeString(getChoiceText());
        dest.writeString(getIsRight());
        dest.writeString(getQuestionId());
        dest.writeString(getAudioLink());
        dest.writeString(getVideoLink());
    }


    public static final Parcelable.Creator<Answers> CREATOR = new Parcelable.Creator<Answers>() {
        @Override
        public Answers createFromParcel(Parcel source) {
            return new Answers(source);
        }

        @Override
        public Answers[] newArray(int size) {
            return new Answers[size];
        }
    };
}
