package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - score detail of student for exam.
 * Relationship with {@link User}
 * Relationship with {@link ExamSchedule}
 * Relationship with {@link Exam}
 */
public class StudentExamScore extends RealmObject {
    @PrimaryKey
    private  int studentExamScoreId;
    private String remarks;
   private User user;
    private ExamSchedule examSchedule;
    private Exam exam;
    private int attemptCount;
    private int correctAnswers;
    private int incorrectAnswers;
    private int firstCount;
    private int secondCount;
    private int thirdCount;
    private int forthCount;
    private int fifthCount;
    private int gradeObtained; //enum('A1', 'B2', 'B3', 'C4', 'C5', 'C6', 'D7', 'D8
    private int totalTimeSpent;
    private int publishResultStatus; //enum('published', 'unpublished')
    private int evaluationPrivacy; //enum('Yes', 'No')
    private int examStatus; //enum('unstarted', 'started', 'partial', 'finished'
    private float marksObtained;
    private float percentage;
    private Date examEndTime;
    private Date createdDate;
    private Date modifiedDate;

    public int getStudentExamScoreId() {
        return studentExamScoreId;
    }

    public void setStudentExamScoreId(int studentExamScoreId) {
        this.studentExamScoreId = studentExamScoreId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExamSchedule getExamSchedule() {
        return examSchedule;
    }

    public void setExamSchedule(ExamSchedule examSchedule) {
        this.examSchedule = examSchedule;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public int getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(int firstCount) {
        this.firstCount = firstCount;
    }

    public int getSecondCount() {
        return secondCount;
    }

    public void setSecondCount(int secondCount) {
        this.secondCount = secondCount;
    }

    public int getThirdCount() {
        return thirdCount;
    }

    public void setThirdCount(int thirdCount) {
        this.thirdCount = thirdCount;
    }

    public int getForthCount() {
        return forthCount;
    }

    public void setForthCount(int forthCount) {
        this.forthCount = forthCount;
    }

    public int getFifthCount() {
        return fifthCount;
    }

    public void setFifthCount(int fifthCount) {
        this.fifthCount = fifthCount;
    }

    public int getGradeObtained() {
        return gradeObtained;
    }

    public void setGradeObtained(int gradeObtained) {
        this.gradeObtained = gradeObtained;
    }

    public int getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(int totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    public int getPublishResultStatus() {
        return publishResultStatus;
    }

    public void setPublishResultStatus(int publishResultStatus) {
        this.publishResultStatus = publishResultStatus;
    }

    public int getEvaluationPrivacy() {
        return evaluationPrivacy;
    }

    public void setEvaluationPrivacy(int evaluationPrivacy) {
        this.evaluationPrivacy = evaluationPrivacy;
    }

    public int getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(int examStatus) {
        this.examStatus = examStatus;
    }

    public float getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(float marksObtained) {
        this.marksObtained = marksObtained;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public Date getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(Date examEndTime) {
        this.examEndTime = examEndTime;
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
}
