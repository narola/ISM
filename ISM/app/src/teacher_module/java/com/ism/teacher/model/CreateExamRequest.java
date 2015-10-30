package com.ism.teacher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * these is for the create exam request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateExamRequest {


    private String exam_name;
    private int classroom_id;
    private int subject_id;
    private int attempt_count;
    private String exam_type;
    private String exam_category;
    private String exam_mode;
    private String passing_percent;
    private String exam_duration;
    private String exam_instruction;
    private String declare_results;
    private String negative_marking;
    private String random_question;
    private String exam_start_date;
    private String exam_start_time;
    private String user_id;
    private String negative_mark_value;

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getAttempt_count() {
        return attempt_count;
    }

    public void setAttempt_count(int attempt_count) {
        this.attempt_count = attempt_count;
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

    public String getExam_start_date() {
        return exam_start_date;
    }

    public void setExam_start_date(String exam_start_date) {
        this.exam_start_date = exam_start_date;
    }

    public String getExam_start_time() {
        return exam_start_time;
    }

    public void setExam_start_time(String exam_start_time) {
        this.exam_start_time = exam_start_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNegative_mark_value() {
        return negative_mark_value;
    }

    public void setNegative_mark_value(String negative_mark_value) {
        this.negative_mark_value = negative_mark_value;
    }


}
