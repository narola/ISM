package model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import model.authormodel.ROExamSubmittor;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - evaluation related to exam.
 * Relationship with {@link ROExamSchedule}
 */
public class ROStudentExamEvaluation extends RealmObject {

    @PrimaryKey
    private int evaluationId;//This is the examID.
    private String examScore;
    private ROExam roExam;
    private ROExamSubmittor roExamSubmittor;
    private RealmList<ROQuestionPalette> roQuestionPalette = new RealmList<ROQuestionPalette>();
    private RealmList<ROEvaluation> roEvaluation = new RealmList<ROEvaluation>();


    private ROExamSchedule roExamSchedule;
    private int evaluationStatus;
    private int publishResultStatus;
    private int averageScore;
    private int totalStudentAttempted;
    private float totalQuestions;
    private Date createdDate;
    private Date modifiedDate;

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public ROExamSchedule getRoExamSchedule() {
        return roExamSchedule;
    }

    public void setRoExamSchedule(ROExamSchedule roExamSchedule) {
        this.roExamSchedule = roExamSchedule;
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


    public RealmList<ROEvaluation> getRoEvaluation() {
        return roEvaluation;
    }

    public void setRoEvaluation(RealmList<ROEvaluation> roEvaluation) {
        this.roEvaluation = roEvaluation;

    }

    public RealmList<ROQuestionPalette> getRoQuestionPalette() {
        return roQuestionPalette;
    }

    public void setRoQuestionPalette(RealmList<ROQuestionPalette> roQuestionPalette) {
        this.roQuestionPalette = roQuestionPalette;

    }

    public String getExamScore() {
        return examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }


    public ROExamSubmittor getRoExamSubmittor() {
        return roExamSubmittor;
    }

    public void setRoExamSubmittor(ROExamSubmittor roExamSubmittor) {
        this.roExamSubmittor = roExamSubmittor;
    }


    public ROExam getRoExam() {
        return roExam;
    }

    public void setRoExam(ROExam roExam) {
        this.roExam = roExam;
    }

}
