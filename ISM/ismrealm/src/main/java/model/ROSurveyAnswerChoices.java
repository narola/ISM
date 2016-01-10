package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of survey answer choices.
 * Relationship with {@link ROSurveyQuestion}
 */
public class ROSurveyAnswerChoices extends RealmObject {

    @PrimaryKey
    private  int surveyAnswerChoiceId;
    private String choiceText;
    private ROSurveyQuestion roSurveyQuestion;
    private Date createdDate;
    private Date modifiedDate;

    public int getSurveyAnswerChoiceId() {
        return surveyAnswerChoiceId;
    }

    public void setSurveyAnswerChoiceId(int surveyAnswerChoiceId) {
        this.surveyAnswerChoiceId = surveyAnswerChoiceId;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public ROSurveyQuestion getRoSurveyQuestion() {
        return roSurveyQuestion;
    }

    public void setRoSurveyQuestion(ROSurveyQuestion roSurveyQuestion) {
        this.roSurveyQuestion = roSurveyQuestion;
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
