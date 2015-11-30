package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of survey response.
 * Relationship with {@link User}
 * Relationship with {@link SurveyAnswerChoices}
 * Relationship with {@link SurveyQuestion}
 */
public class SurveyResponse extends RealmObject {

    @PrimaryKey
    private  int surveyResponseId;
    private String answerText;
   private SurveyQuestion surveyQuestion;
    private User user;
    private SurveyAnswerChoices surveyAnswerChoice;
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

    public SurveyQuestion getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SurveyAnswerChoices getSurveyAnswerChoice() {
        return surveyAnswerChoice;
    }

    public void setSurveyAnswerChoice(SurveyAnswerChoices surveyAnswerChoice) {
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
