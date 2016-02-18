package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of survey audience.
 * Relationship with {@link RORole}
 * Relationship with {@link ROSurvey}
 */
public class ROSurveyAudience extends RealmObject {

    @PrimaryKey
    private  int surveyAudienceId;
    private ROSurvey roSurvey;
    private RORole roRole;
    private Date createdDate;
    private Date modifiedDate;

    public int getSurveyAudienceId() {
        return surveyAudienceId;
    }

    public void setSurveyAudienceId(int surveyAudienceId) {
        this.surveyAudienceId = surveyAudienceId;
    }

    public ROSurvey getRoSurvey() {
        return roSurvey;
    }

    public void setRoSurvey(ROSurvey roSurvey) {
        this.roSurvey = roSurvey;
    }

    public RORole getRoRole() {
        return roRole;
    }

    public void setRoRole(RORole roRole) {
        this.roRole = roRole;
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
