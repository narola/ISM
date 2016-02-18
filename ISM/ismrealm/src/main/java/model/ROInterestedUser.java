package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of interested user.
 * Relationship with {@link RORole}
 * Relationship with {@link ROCity}
 * Relationship with {@link ROState}
 * Relationship with {@link RODistrict}
 * Relationship with {@link ROCountry}
 */
public class ROInterestedUser extends RealmObject {

    @PrimaryKey
    private  int interestedUserId;
    private String fullName;
    private String emailId;
    private String course;
    private String message;
    private RORole roRole;
    private ROCity roCity;
    private ROState roState;
    private RODistrict roDistrict;
    private ROCountry roCountry;
    private int contactNumber;
    private Date createdDate;
    private Date modifiedDate;

    public int getInterestedUserId() {
        return interestedUserId;
    }

    public void setInterestedUserId(int interestedUserId) {
        this.interestedUserId = interestedUserId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RORole getRoRole() {
        return roRole;
    }

    public void setRoRole(RORole roRole) {
        this.roRole = roRole;
    }

    public ROCity getRoCity() {
        return roCity;
    }

    public void setRoCity(ROCity roCity) {
        this.roCity = roCity;
    }

    public ROState getRoState() {
        return roState;
    }

    public void setRoState(ROState roState) {
        this.roState = roState;
    }

    public RODistrict getRoDistrict() {
        return roDistrict;
    }

    public void setRoDistrict(RODistrict roDistrict) {
        this.roDistrict = roDistrict;
    }

    public ROCountry getRoCountry() {
        return roCountry;
    }

    public void setRoCountry(ROCountry roCountry) {
        this.roCountry = roCountry;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
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
