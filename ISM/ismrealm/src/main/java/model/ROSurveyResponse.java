package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of survey response.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROSurveyAnswerChoices}
 * Relationship with {@link ROSurveyQuestion}
 */
public class ROSurveyResponse extends RealmObject {

    @PrimaryKey
    private int surveyResponseId;
    private String answerText;
    private ROSurveyQuestion roSurveyQuestion;
    private ROUser roUser;
    private ROSurveyAnswerChoices surveyAnswerChoice;
    private Date createdDate;
    private Date modifiedDate;

    public int getSurveyResponseId() {
        return surveyResponseId;
    }

    public void setSurveyResponseId(int surveyResponseId) {
        this.surveyResponseId = surveyResponseId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public ROSurveyQuestion getRoSurveyQuestion() {
        return roSurveyQuestion;
    }

    public void setRoSurveyQuestion(ROSurveyQuestion roSurveyQuestion) {
        this.roSurveyQuestion = roSurveyQuestion;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROSurveyAnswerChoices getSurveyAnswerChoice() {
        return surveyAnswerChoice;
    }

    public void setSurveyAnswerChoice(ROSurveyAnswerChoices surveyAnswerChoice) {
        this.surveyAnswerChoice = surveyAnswerChoice;
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
