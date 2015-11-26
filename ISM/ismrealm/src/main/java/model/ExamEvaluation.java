package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - evaluation related to exam.
 * Relationship with {@link ExamSchedule}
 */
public class ExamEvaluation extends RealmObject {

    @PrimaryKey
    private  int examEvaluationId;
    private ExamSchedule examSchedule;
    private int evaluationStatus;
    private int publishResultStatus;
    private int averageScore;
    private int totalStudentAttempted;
    private float totalQuestions;
    private Date createdDate;
    private Date modifiedDate;

    public int getExamEvaluationId() {
        return examEvaluationId;
    }

    public void setExamEvaluationId(int examEvaluationId) {
        this.examEvaluationId = examEvaluationId;
    }

    public ExamSchedule getExamSchedule() {
        return examSchedule;
    }

    public void setExamSchedule(ExamSchedule examSchedule) {
        this.examSchedule = examSchedule;
    }

    public int getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(int evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public int getPublishResultStatus() {
        return publishResultStatus;
    }

    public void setPublishResultStatus(int publishResultStatus) {
        this.publishResultStatus = publishResultStatus;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public int getTotalStudentAttempted() {
        return totalStudentAttempted;
    }

    public void setTotalStudentAttempted(int totalStudentAttempted) {
        this.totalStudentAttempted = totalStudentAttempted;
    }

    public float getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(float totalQuestions) {
        this.totalQuestions = totalQuestions;
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
