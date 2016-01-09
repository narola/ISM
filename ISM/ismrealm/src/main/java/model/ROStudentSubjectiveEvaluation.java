package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - evaluation for students' subjective answers.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROQuestions}
 * Relationship with {@link ROExam}
 */
public class ROStudentSubjectiveEvaluation extends RealmObject {
    @PrimaryKey
    private int studentSubjectiveEvaluationId;
    private String studentResponse;
    private String evaluationNote;
    private ROUser roUser;
    private ROUser evaluationBy;
    private ROQuestions question;
    private ROExam roExam;
    private int answerStatus;
    private int evaluationScore;
    private Date createdDate;
    private Date modifiedDate;

    public int getStudentSubjectiveEvaluationId() {
        return studentSubjectiveEvaluationId;
    }

    public void setStudentSubjectiveEvaluationId(int studentSubjectiveEvaluationId) {
        this.studentSubjectiveEvaluationId = studentSubjectiveEvaluationId;
    }

    public String getStudentResponse() {
        return studentResponse;
    }

    public void setStudentResponse(String studentResponse) {
        this.studentResponse = studentResponse;
    }

    public String getEvaluationNote() {
        return evaluationNote;
    }

    public void setEvaluationNote(String evaluationNote) {
        this.evaluationNote = evaluationNote;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROUser getEvaluationBy() {
        return evaluationBy;
    }

    public void setEvaluationBy(ROUser evaluationBy) {
        this.evaluationBy = evaluationBy;
    }

    public ROQuestions getQuestion() {
        return question;
    }

    public void setQuestion(ROQuestions question) {
        this.question = question;
    }

    public ROExam getRoExam() {
        return roExam;
    }

    public void setRoExam(ROExam roExam) {
        this.roExam = roExam;
    }

    public int getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(int answerStatus) {
        this.answerStatus = answerStatus;
    }

    public int getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(int evaluationScore) {
        this.evaluationScore = evaluationScore;
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
