package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Assignment {

    private String createdBy;
    private String bookId;
    private String assignmentName;
    private String assignmentId;
    private String authorName;
    private ArrayList<String> assignmentTags;
    private String assignmentText;
    private String bookName;


    @JsonProperty("created_by")
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("book_id")
    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @JsonProperty("assignment_name")
    public String getAssignmentName() {
        return this.assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    @JsonProperty("assignment_id")
    public String getAssignmentId() {
        return this.assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    @JsonProperty("author_name")
    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @JsonProperty("assignment_tags")
    public ArrayList<String> getAssignmentTags() {
        return this.assignmentTags;
    }

    public void setAssignmentTags(ArrayList<String> assignmentTags) {
        this.assignmentTags = assignmentTags;
    }

    @JsonProperty("assignment_text")
    public String getAssignmentText() {
        return this.assignmentText;
    }

    public void setAssignmentText(String assignmentText) {
        this.assignmentText = assignmentText;
    }

    @JsonProperty("book_name")
    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


}
