package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorBookAssignment {

    private String bookId;
    private ArrayList<Assignments> assignments;
    private String bookName;
    private String totalAssignments;


    @JsonProperty("book_id")
    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }


    @JsonProperty("assignments")
    public ArrayList<Assignments> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(ArrayList<Assignments> assignments) {
        this.assignments = assignments;
    }

    @JsonProperty("book_name")
    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @JsonProperty("total_assignments")
    public String getTotalAssignments() {
        return totalAssignments;
    }

    public AuthorBookAssignment setTotalAssignments(String totalAssignments) {
        this.totalAssignments = totalAssignments;
        return this;
    }

}
