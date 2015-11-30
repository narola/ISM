package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of rolemodels.
 */
public class RoleModel extends RealmObject {

    @PrimaryKey
    private  int roleModelId;
    private String modelName;
    private String birthdate;
    private String organization;
    private String quotes;
    private String achievements;
    private String description;
    private String activities;
    private String education;
    private String modelImage;
    private Date createdDate;
    private Date modifiedDate;


    public int getRoleModelId() {
        return roleModelId;
    }

    public void setRoleModelId(int roleModelId) {
        this.roleModelId = roleModelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
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
