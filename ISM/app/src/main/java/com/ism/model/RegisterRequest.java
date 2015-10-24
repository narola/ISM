package com.ism.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest {
	
    private String profileImage;
    private String lastname;
    private double stateId;
    private double cityId;
    private String firstname;
    private double courseId;
    private double schoolId;
    private String contactNumber;
    private String deviceToken;
    private double countryId;
    private String homeAddress;
    private String academicYear;
    private String profileImageName;
    private String birthdate;
    private String gender;
    private String emailAddress;
    private String password;
    private String deviceType;
    private double classroomId;
    private double roleId;
    private String username;

    public String getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public double getStateId() {
        return this.stateId;
    }

    @JsonProperty("state_id")
    public void setStateId(double stateId) {
        this.stateId = stateId;
    }

    public double getCityId() {
        return this.cityId;
    }

    @JsonProperty("city_id")
    public void setCityId(double cityId) {
        this.cityId = cityId;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public double getCourseId() {
        return this.courseId;
    }

    public void setCourseId(double courseId) {
        this.courseId = courseId;
    }

    public double getSchoolId() {
        return this.schoolId;
    }

    public void setSchoolId(double schoolId) {
        this.schoolId = schoolId;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    @JsonProperty("device_token")
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public double getCountryId() {
        return this.countryId;
    }

    @JsonProperty("country_id")
    public void setCountryId(double countryId) {
        this.countryId = countryId;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getAcademicYear() {
        return this.academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getProfileImageName() {
        return this.profileImageName;
    }

    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public double getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(double classroomId) {
        this.classroomId = classroomId;
    }

    public double getRoleId() {
        return this.roleId;
    }

    public void setRoleId(double roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
