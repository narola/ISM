package com.ism.teacher.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectWiseScore {

    private String internalScore;
    private String subjectGrade;
    private String remarks;
    private String marksObtained;
    private String subjectRank;
    private String subjectName;
    private String subjectId;
    private String percentage;


    @JsonProperty("internal_score")
    public String getInternalScore() {
        return this.internalScore;
    }

    public void setInternalScore(String internalScore) {
        this.internalScore = internalScore;
    }

    @JsonProperty("subject_grade")
    public String getSubjectGrade() {
        return this.subjectGrade;
    }

    public void setSubjectGrade(String subjectGrade) {
        this.subjectGrade = subjectGrade;
    }

    @JsonProperty("remarks")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonProperty("marks_obtained")
    public String getMarksObtained() {
        return this.marksObtained;
    }

    public void setMarksObtained(String marksObtained) {
        this.marksObtained = marksObtained;
    }

    @JsonProperty("subject_rank")
    public String getSubjectRank() {
        return this.subjectRank;
    }

    public void setSubjectRank(String subjectRank) {
        this.subjectRank = subjectRank;
    }

    @JsonProperty("subject_name")
    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @JsonProperty("subject_id")
    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @JsonProperty("percentage")
    public String getPercentage() {
        return this.percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }


}
