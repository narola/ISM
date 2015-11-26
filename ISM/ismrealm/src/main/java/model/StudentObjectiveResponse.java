package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - students' answer for objective questions.
 * Relationship with {@link User}
 * Relationship with {@link Exam}
 * Relationship with {@link Questions}
 * Relationship with {@link AnswerChoices}
 */
public class StudentObjectiveResponse extends RealmObject {
    @PrimaryKey
    private  int studentObjectiveResponseId;
    private String answerText;
   private User user;
    private Exam exam;
    private Questions question;
    private AnswerChoices answerChoice;
    private int answerStatus;
    private int markObtained;
    private int responseDuration;
    private boolean isRight;
    private Date createdDate;
    private Date modifiedDate;

    public int getStudentObjectiveResponseId() {
        return studentObjectiveResponseId;
    }

    public void setStudentObjectiveResponseId(int studentObjectiveResponseId) {
        this.studentObjectiveResponseId = studentObjectiveResponseId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }

    public AnswerChoices getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(AnswerChoices answerChoice) {
        this.answerChoice = answerChoice;
    }

    public int getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(int answerStatus) {
        this.answerStatus = answerStatus;
    }

    public int getMarkObtained() {
        return markObtained;
    }

    public void setMarkObtained(int markObtained) {
        this.markObtained = markObtained;
    }

    public int getResponseDuration() {
        return responseDuration;
    }

    public void setResponseDuration(int responseDuration) {
        this.responseDuration = responseDuration;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
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
