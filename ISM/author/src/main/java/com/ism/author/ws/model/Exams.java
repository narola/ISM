package com.ism.author.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Exams {

    private String totalQuestion;
    private String classroomName;
    private String examMode;
    private String examId;
    private String classroomId;
    private String averageScore;
    private String examName;
    private String subjectId;
    private String subjectName;
    private String duration;
    private String evaluationStatus;
    private String passPercentage;
    private String examType;
    private String examCategory;
    private String totalStudentAttempted;
    private String totalStudent;
    private String bookId;
    private String bookName;
    private String totalAssessed;
    private String totalUnAssessed;

    @JsonProperty("total_question")
    public String getTotalQuestion() {
        return this.totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    @JsonProperty("classroom_name")
    public String getClassroomName() {
        return this.classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    @JsonProperty("exam_mode")
    public String getExamMode() {
        return this.examMode;
    }

    public void setExamMode(String examMode) {
        this.examMode = examMode;
    }

    @JsonProperty("exam_id")
    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    @JsonProperty("classroom_id")
    public String getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    @JsonProperty("average_score")
    public String getAverageScore() {
        return this.averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    @JsonProperty("exam_name")
    public String getExamName() {
        return this.examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    @JsonProperty("subject_id")
    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @JsonProperty("subject_name")
    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("evaluation_status")
    public String getEvaluationStatus() {
        return this.evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    @JsonProperty("pass_percentage")
    public String getPassPercentage() {
        return this.passPercentage;
    }

    public void setPassPercentage(String passPercentage) {
        this.passPercentage = passPercentage;
    }

    @JsonProperty("exam_type")
    public String getExamType() {
        return this.examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    @JsonProperty("exam_category")
    public String getExamCategory() {
        return this.examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    @JsonProperty("total_student_attempted")
    public String getTotalStudentAttempted() {
        return totalStudentAttempted;
    }

    public void setTotalStudentAttempted(String totalStudentAttempted) {
        this.totalStudentAttempted = totalStudentAttempted;
    }

    @JsonProperty("total_student")
    public String getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(String totalStudent) {
        this.totalStudent = totalStudent;
    }

    @JsonProperty("book_name")
    public String getBookName() {
        return bookName;
    }

    public Exams setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    @JsonProperty("book_id")
    public String getBookId() {
        return bookId;
    }

    public Exams setBookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    @JsonProperty("total_unassessed")
    public String getTotalUnAssessed() {
        return totalUnAssessed;
    }

    public Exams setTotalUnAssessed(String totalUnAssessed) {
        this.totalUnAssessed = totalUnAssessed;
        return this;
    }

    @JsonProperty("total_assessed")
    public String getTotalAssessed() {
        return totalAssessed;
    }

    public Exams setTotalAssessed(String totalAssessed) {
        this.totalAssessed = totalAssessed;
        return this;
    }


}
