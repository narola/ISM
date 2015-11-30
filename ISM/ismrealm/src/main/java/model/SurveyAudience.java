package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of survey audience.
 * Relationship with {@link Role}
 * Relationship with {@link Survey}
 */
public class SurveyAudience extends RealmObject {

    @PrimaryKey
    private  int surveyAudienceId;
    private Survey survey;
    private Role role;
    private Date createdDate;
    private Date modifiedDate;

    public int getSurveyAudienceId() {
        return surveyAudienceId;
    }

    public void setSurveyAudienceId(int surveyAudienceId) {
        this.surveyAudienceId = surveyAudienceId;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
