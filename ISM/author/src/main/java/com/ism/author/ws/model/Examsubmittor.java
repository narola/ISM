package com.ism.author.ws.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Examsubmittor implements Parcelable {

    private String remarks;
    private String evaluationScore;
    private String studentProfilePic;
    private String examStatus;
    private String submissionDate;
    private String studentName;
    private String studentId;
    private boolean isFlagged;

    public Examsubmittor() {
    }

    public Examsubmittor(Parcel parcelExamSubmittor) {
        this.remarks = parcelExamSubmittor.readString();
        this.evaluationScore = parcelExamSubmittor.readString();
        this.studentProfilePic = parcelExamSubmittor.readString();
        this.examStatus = parcelExamSubmittor.readString();
        this.submissionDate = parcelExamSubmittor.readString();
        this.studentName = parcelExamSubmittor.readString();
        this.studentId = parcelExamSubmittor.readString();
    }

    @JsonProperty("remarks")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonProperty("evaluation_score")
    public String getEvaluationScore() {
        return this.evaluationScore;
    }

    public void setEvaluationScore(String evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    @JsonProperty("student_profile_pic")
    public String getStudentProfilePic() {
        return this.studentProfilePic;
    }

    public void setStudentProfilePic(String studentProfilePic) {
        this.studentProfilePic = studentProfilePic;
    }

    @JsonProperty("exam_status")
    public String getExamStatus() {
        return this.examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    @JsonProperty("submission_date")
    public String getSubmissionDate() {
        return this.submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    @JsonProperty("student_name")
    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @JsonProperty("student_id")
    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setIsFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getRemarks());
        dest.writeString(getEvaluationScore());
        dest.writeString(getStudentProfilePic());
        dest.writeString(getExamStatus());
        dest.writeString(getSubmissionDate());
        dest.writeString(getStudentName());
        dest.writeString(getStudentId());
    }


    public static final Parcelable.Creator<Examsubmittor> CREATOR = new Parcelable.Creator<Examsubmittor>() {
        @Override
        public Examsubmittor createFromParcel(Parcel source) {
            return new Examsubmittor(source);
        }

        @Override
        public Examsubmittor[] newArray(int size) {
            return new Examsubmittor[size];
        }
    };

}
