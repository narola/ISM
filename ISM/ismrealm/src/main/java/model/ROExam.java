package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of exams.
 * Relationship with {@link ROClassrooms}
 * Relationship with {@link ROUser}
 * Relationship with {@link ROSubjects}
 * Relationship with {@link ROBooks}
 */
public class ROExam extends RealmObject {

    @PrimaryKey
    private int examId;
    private String examName;
    private String instructions;
    private ROClassrooms roClassroom;
    private ROUser createdBy;
    private ROSubjects roSubjects;
    private ROAuthorBook roAuthorBook;
    private String examType;
    private String examCategory;
    private String examMode;
    private String passPercentage;
    private String duration;
    private String attemptCount;
    private String negativeMarkValue;
    private String correctAnswerScore;
    private boolean negativeMarking;
    private boolean useQuestionScore;
    private boolean randomQuestion;
    private boolean declareResults;
    private Date createdDate;
    private Date modifiedDate;
    private String totalStudent;
    private String totalStudentAttempted;
    private String examAssessor;
    private Date examStartDate;
    private String examStartTime;
    private String totalQuestion;
    private String evaluationStatus;
    private String averageScore;
    private String totalAssessed;
    private String totalUnassessed;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public ROClassrooms getRoClassroom() {
        return roClassroom;
    }

    public void setRoClassroom(ROClassrooms roClassroom) {
        this.roClassroom = roClassroom;
    }

    public ROUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ROUser createdBy) {
        this.createdBy = createdBy;
    }

    public ROSubjects getRoSubjects() {
        return roSubjects;
    }

    public void setRoSubjects(ROSubjects roSubjects) {
        this.roSubjects = roSubjects;
    }

    public ROAuthorBook getRoAuthorBook() {
        return roAuthorBook;
    }

    public void setRoAuthorBook(ROAuthorBook roAuthorBook) {
        this.roAuthorBook = roAuthorBook;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public String getExamMode() {
        return examMode;
    }

    public void setExamMode(String examMode) {
        this.examMode = examMode;
    }

    public String getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(String passPercentage) {
        this.passPercentage = passPercentage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(String attemptCount) {
        this.attemptCount = attemptCount;
    }

    public String getNegativeMarkValue() {
        return negativeMarkValue;
    }

    public void setNegativeMarkValue(String negativeMarkValue) {
        this.negativeMarkValue = negativeMarkValue;
    }

    public String getCorrectAnswerScore() {
        return correctAnswerScore;
    }

    public void setCorrectAnswerScore(String correctAnswerScore) {
        this.correctAnswerScore = correctAnswerScore;
    }

    public boolean isNegativeMarking() {
        return negativeMarking;
    }

    public void setNegativeMarking(boolean negativeMarking) {
        this.negativeMarking = negativeMarking;
    }

    public boolean isUseQuestionScore() {
        return useQuestionScore;
    }

    public void setUseQuestionScore(boolean useQuestionScore) {
        this.useQuestionScore = useQuestionScore;
    }

    public boolean isRandomQuestion() {
        return randomQuestion;
    }

    public void setRandomQuestion(boolean randomQuestion) {
        this.randomQuestion = randomQuestion;
    }

    public boolean isDeclareResults() {
        return declareResults;
    }

    public void setDeclareResults(boolean declareResults) {
        this.declareResults = declareResults;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(String totalStudent) {
        this.totalStudent = totalStudent;
    }

    public String getTotalStudentAttempted() {
        return totalStudentAttempted;
    }

    public void setTotalStudentAttempted(String totalStudentAttempted) {
        this.totalStudentAttempted = totalStudentAttempted;
    }

    public String getExamAssessor() {
        return examAssessor;
    }

    public void setExamAssessor(String examAssessor) {
        this.examAssessor = examAssessor;
    }

    public Date getExamStartDate() {
        return examStartDate;
    }

    public void setExamStartDate(Date examStartDate) {
        this.examStartDate = examStartDate;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
    }

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;

    }

    public String getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getTotalAssessed() {
        return totalAssessed;
    }

    public void setTotalAssessed(String totalAssessed) {
        this.totalAssessed = totalAssessed;
    }

    public String getTotalUnassessed() {
        return totalUnassessed;
    }

    public void setTotalUnassessed(String totalUnassessed) {
        this.totalUnassessed = totalUnassessed;
    }
}
