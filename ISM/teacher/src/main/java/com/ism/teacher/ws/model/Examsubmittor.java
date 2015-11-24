package com.ism.teacher.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Examsubmittor {

    private String remarks;
    private String evaluationScore;
    private String studentProfilePic;
    private String examStatus;
    private String submissionDate;
    private String studentName;
    private String studentId;


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


}
