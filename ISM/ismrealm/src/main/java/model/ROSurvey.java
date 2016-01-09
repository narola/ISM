package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of survey.
 * Relationship with {@link ROUser}
 */
public class ROSurvey extends RealmObject {

    @PrimaryKey
    private  int surveyId;
    private String surveyTitle;
    private String surveyDecription;
    private ROUser createdBy;
   private Date createdDate;
    private Date modifiedDate;

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyDecription() {
        return surveyDecription;
    }

    public void setSurveyDecription(String surveyDecription) {
        this.surveyDecription = surveyDecription;
    }

    public ROUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ROUser createdBy) {
        this.createdBy = createdBy;
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
