package com.ism.author.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * these is for the create exam request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateExamRequest {


    private String exam_name;
    private String classroom_id;
    private String subject_id;
    private String exam_type;
    private String exam_category;
    private String exam_mode;
    private String passing_percent;
    private String exam_duration;
    private String exam_instruction;
    private String declare_results;
    private String negative_marking;
    private String random_question;
    private String submission_date;
    private String user_id;

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(String classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getExam_type() {
        return exam_type;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }

    public String getExam_category() {
        return exam_category;
    }

    public void setExam_category(String exam_category) {
        this.exam_category = exam_category;
    }

    public String getExam_mode() {
        return exam_mode;
    }

    public void setExam_mode(String exam_mode) {
        this.exam_mode = exam_mode;
    }

    public String getPassing_percent() {
        return passing_percent;
    }

    public void setPassing_percent(String passing_percent) {
        this.passing_percent = passing_percent;
    }

    public String getExam_duration() {
        return exam_duration;
    }

    public void setExam_duration(String exam_duration) {
        this.exam_duration = exam_duration;
    }

    public String getExam_instruction() {
        return exam_instruction;
    }

    public void setExam_instruction(String exam_instruction) {
        this.exam_instruction = exam_instruction;
    }

    public String getDeclare_results() {
        return declare_results;
    }

    public void setDeclare_results(String declare_results) {
        this.declare_results = declare_results;
    }

    public String getNegative_marking() {
        return negative_marking;
    }

    public void setNegative_marking(String negative_marking) {
        this.negative_marking = negative_marking;
    }

    public String getRandom_question() {
        return random_question;
    }

    public void setRandom_question(String random_question) {
        this.random_question = random_question;
    }

    public String getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(String submission_date) {
        this.submission_date = submission_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
