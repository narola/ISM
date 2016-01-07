package com.ism.teacher.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassPerformance {
	
    private ArrayList<StudentsScore> studentsScore;
    private String examScore;
    private String examName;
    private String internalMarks;

    @JsonProperty("students_score")
    public ArrayList<StudentsScore> getStudentsScore() {
        return this.studentsScore;
    }

    public void setStudentsScore(ArrayList<StudentsScore> studentsScore) {
        this.studentsScore = studentsScore;
    }

    @JsonProperty("exam_score")
    public String getExamScore() {
        return this.examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }

    @JsonProperty("exam_name")
    public String getExamName() {
        return this.examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    @JsonProperty("internal_marks")
    public String getInternalMarks() {
        return this.internalMarks;
    }

    public void setInternalMarks(String internalMarks) {
        this.internalMarks = internalMarks;
    }

    
}
