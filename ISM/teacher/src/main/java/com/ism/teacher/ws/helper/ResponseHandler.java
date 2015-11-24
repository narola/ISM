package com.ism.teacher.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.teacher.model.Data;
import com.ism.teacher.ws.model.Classrooms;
import com.ism.teacher.ws.model.CommentList;
import com.ism.teacher.ws.model.ExamQuestions;
import com.ism.teacher.ws.model.ExamSubmission;
import com.ism.teacher.ws.model.Exams;
import com.ism.teacher.ws.model.Feeds;
import com.ism.teacher.ws.model.LoginResponse;
import com.ism.teacher.ws.model.Studymates;
import com.ism.teacher.ws.model.Subjects;

import java.util.ArrayList;

/**
 * Created by c166 on 23/10/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHandler {

    private String message;
    private ArrayList<Data> data;
    private String status;

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String DUPLICATE_ENTRY = "Duplicate entry";
    private String TAG = ResponseHandler.class.getSimpleName();

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return this.data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    /*this is the new code for the response handler*/


    private ArrayList<Feeds> feeds;
    private ArrayList<CommentList> comments;
    private ArrayList<Studymates> studymates;
    private ArrayList<Exams> exams;
    private ArrayList<Subjects> subjects;
    private ArrayList<Classrooms> classrooms;
    private ArrayList<ExamQuestions> examQuestions;
    private ArrayList<ExamSubmission> examSubmission;


    private ArrayList<LoginResponse> user;

    @JsonProperty("user")
    public ArrayList<LoginResponse> getUser() {
        return user;
    }

    public void setUser(ArrayList<LoginResponse> user) {
        this.user = user;
    }


    @JsonProperty("feeds")
    public ArrayList<Feeds> getFeeds() {
        return this.feeds;
    }

    public void setFeeds(ArrayList<Feeds> feeds) {
        this.feeds = feeds;
    }

    @JsonProperty("comments")
    public ArrayList<CommentList> getComments() {
        return this.comments;
    }

    public void setComments(ArrayList<CommentList> comments) {
        this.comments = comments;
    }

    @JsonProperty("studymates")
    public ArrayList<Studymates> getStudymates() {
        return this.studymates;
    }

    public void setStudymates(ArrayList<Studymates> studymates) {
        this.studymates = studymates;
    }

    @JsonProperty("exams")
    public ArrayList<Exams> getExams() {
        return this.exams;
    }

    public void setExams(ArrayList<Exams> exams) {
        this.exams = exams;
    }

    @JsonProperty("subjects")
    public ArrayList<Subjects> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(ArrayList<Subjects> subjects) {
        this.subjects = subjects;
    }

    @JsonProperty("classrooms")
    public ArrayList<Classrooms> getClassrooms() {
        return this.classrooms;
    }

    public void setClassrooms(ArrayList<Classrooms> classrooms) {
        this.classrooms = classrooms;
    }

    @JsonProperty("exam_questions")
    public ArrayList<ExamQuestions> getExamQuestions() {
        return this.examQuestions;
    }

    public void setExamQuestions(ArrayList<ExamQuestions> examQuestions) {
        this.examQuestions = examQuestions;
    }

    @JsonProperty("exam_submission")
    public ArrayList<ExamSubmission> getExamSubmission() {
        return this.examSubmission;
    }

    public void setExamSubmission(ArrayList<ExamSubmission> examSubmission) {
        this.examSubmission = examSubmission;
    }


}

