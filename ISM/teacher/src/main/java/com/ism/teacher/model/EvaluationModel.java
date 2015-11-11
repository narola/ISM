package com.ism.teacher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c75 on 09/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class EvaluationModel {


    private String full_name;
    private String profile_pic;
    private String class_name;

    private String evaluation_score;
    private String remarks;
    private String school_name;
    private String student_id;
    private String submission_date;

    @JsonProperty("exam_status")
    public String getExam_status() {
        return exam_status;
    }

    public void setExam_status(String exam_status) {
        this.exam_status = exam_status;
    }

    private String exam_status;


    @JsonProperty("full_name")
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    @JsonProperty("profile_pic")
    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    @JsonProperty("class_name")
    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @JsonProperty("evaluation_score")
    public String getEvaluation_score() {
        return evaluation_score;
    }

    public void setEvaluation_score(String evaluation_score) {
        this.evaluation_score = evaluation_score;
    }

    @JsonProperty("remarks")

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonProperty("school_name")
    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    @JsonProperty("student_id")
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    @JsonProperty("submission_date")
    public String getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(String submission_date) {
        this.submission_date = submission_date;
    }

}
