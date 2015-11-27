package com.ism.teacher.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamQuestions {

    private String id;
    private String createdDate;
    private String className;
    private String bookName;
    private String examName;
    private String instruction;
    private ArrayList<Questions> questions;

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("exam_name")
    public String getExamName() {
        return this.examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    @JsonProperty("instruction")
    public String getInstruction() {
        return this.instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @JsonProperty("questions")
    public ArrayList<Questions> getQuestions() {
        return this.questions;
    }

    public void setQuestions(ArrayList<Questions> questions) {
        this.questions = questions;
    }

    @JsonProperty("created_date")
    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("class_name")
    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty("book_name")
    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


}
