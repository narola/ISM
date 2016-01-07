package com.ism.teacher.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentsScore {
	
    private String headMistressComment;
    private ArrayList<SubjectWiseScore> subjectWiseScore;
    private String classMistressComment;
    private String studentPic;
    private String grade;
    private String rank;
    private String studentScore;
    private String studentName;
    private String studentId;
    private String percentage;


    @JsonProperty("head_mistress_comment")
    public String getHeadMistressComment() {
        return this.headMistressComment;
    }

    public void setHeadMistressComment(String headMistressComment) {
        this.headMistressComment = headMistressComment;
    }

    @JsonProperty("subject_wise_score")
    public ArrayList<SubjectWiseScore> getSubjectWiseScore() {
        return this.subjectWiseScore;
    }

    public void setSubjectWiseScore(ArrayList<SubjectWiseScore> subjectWiseScore) {
        this.subjectWiseScore = subjectWiseScore;
    }

    @JsonProperty("class_mistress_comment")
    public String getClassMistressComment() {
        return this.classMistressComment;
    }

    public void setClassMistressComment(String classMistressComment) {
        this.classMistressComment = classMistressComment;
    }


    @JsonProperty("student_pic")
    public String getStudentPic() {
        return this.studentPic;
    }

    public void setStudentPic(String studentPic) {
        this.studentPic = studentPic;
    }

    @JsonProperty("grade")
    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @JsonProperty("rank")
    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @JsonProperty("students_score")
    public String getStudentScore() {
        return this.studentScore;
    }

    public void setStudentScore(String studentScore) {
        this.studentScore = studentScore;
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

    @JsonProperty("percentage")
    public String getPercentage() {
        return this.percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    
}
