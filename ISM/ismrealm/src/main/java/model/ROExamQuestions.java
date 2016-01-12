package model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - students' answer for objective questions.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROExam}
 * Relationship with {@link ROQuestions}
 */
public class ROExamQuestions extends RealmObject{


    @PrimaryKey
    private int examId;//this is the exam id.
    private ROUser roUser;
    private ROExam roExam;
    private RealmList<ROQuestions> roQuestions = new RealmList<ROQuestions>();

    private String answerText;
    private int answerStatus;
    private int markObtained;
    private int responseDuration;
    private boolean isRight;
    private Date createdDate;
    private Date modifiedDate;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
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


    public RealmList<ROQuestions> getRoQuestions() {
        return roQuestions;
    }

    public void setRoQuestions(RealmList<ROQuestions> roQuestions) {
        this.roQuestions = roQuestions;
    }
}
