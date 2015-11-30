package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - evaluation for students' subjective answers.
 * Relationship with {@link User}
 * Relationship with {@link Questions}
 * Relationship with {@link Exam}
 */
public class StudentSubjectiveEvaluation extends RealmObject {
    @PrimaryKey
    private  int studentSubjectiveEvaluationId;
    private String studentResponse;
    private String evaluationNote;
    private User user;
    private User evaluationBy;
    private Questions question;
    private Exam exam;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getEvaluationBy() {
        return evaluationBy;
    }

    public void setEvaluationBy(User evaluationBy) {
        this.evaluationBy = evaluationBy;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
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
