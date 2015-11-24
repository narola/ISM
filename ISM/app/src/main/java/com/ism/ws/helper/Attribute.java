package com.ism.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by c161 on 30/10/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {

	//	AcceptTutorialGroupRequest
	private String userId;
	private String joiningStatus;
	private String groupId;

	//	CredentialsRequest
	private String schoolName;
	private String homeAddress;
	private int cityId;
	private String lastname;
	private String firstname;
	private int stateId;
	private String countryId;
	private String contactNumber;
	private String emailAddress;

//	ForgotPasswordRequest
	private String emailId;

//	LoginRequest
	private String username;
	private String password;

//	RegisterRequest
	private String profileImage;
	private int courseId;
	private int schoolId;
	private String deviceToken;
	private String academicYear;
	private String profileImageName;
	private String birthdate;
	private String gender;
	private String deviceType;
	private int classroomId;
	private String roleId;

//	SchoolInfoRequest
	private String name;
	private String message;

	private String feedId;
	private String commentBy;
	private String comment;
	private String taggedBy;
	private String keyId;
	private String settingValue;
	private String studymateId;
	private String readCategory;
	private ArrayList<String> recordIds;

	private String[] taggedUserIds;
	private ArrayList<Attribute> preferences;
	private File file;
	private String aboutMeText;
	private String ambitionInLife;

	private String fileName;

	public String getAmbitionInLife() {
		return ambitionInLife;
	}
	@JsonProperty("ambitionInLife")
	public void setAmbitionInLife(String ambitionInLife) {
		this.ambitionInLife = ambitionInLife;
	}

	public String getAboutMeText() {
		return aboutMeText;
	}
	@JsonProperty("aboutMeText")
	public void setAboutMeText(String aboutMeText) {
		this.aboutMeText = aboutMeText;
	}


	public String getKeyId() {
		return keyId;
	}
	@JsonProperty("key_id")
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getSettingValue() {
		return settingValue;
	}
	@JsonProperty("preference_value")
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}

	public String getUserId() {
		return this.userId;
	}

	@JsonProperty("user_id")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJoiningStatus() {
		return this.joiningStatus;
	}

	@JsonProperty("joining_status")
	public void setJoiningStatus(String joiningStatus) {
		this.joiningStatus = joiningStatus;
	}

	public String getGroupId() {
		return this.groupId;
	}

	@JsonProperty("group_id")
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	@JsonProperty("school_name")
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getHomeAddress() {
		return this.homeAddress;
	}

	@JsonProperty("home_address")
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public int getCityId() {
		return this.cityId;
	}

	@JsonProperty("city_id")
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getLastname() {
		return this.lastname;
	}

	@JsonProperty("lastname")
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return this.firstname;
	}

	@JsonProperty("firstname")
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getStateId() {
		return this.stateId;
	}

	@JsonProperty("state_id")
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getCountryId() {
		return this.countryId;
	}

	@JsonProperty("country_id")
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getContactNumber() {
		return this.contactNumber;
	}

	@JsonProperty("contact_number")
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	@JsonProperty("email_address")
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@JsonProperty("email_id")
	public String getEmailId() {
		return this.emailId;
	}

	@JsonProperty("username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileImage() {
		return this.profileImage;
	}

	@JsonProperty("profile_image")
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public int getCourseId() {
		return this.courseId;
	}

	@JsonProperty("course_id")
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSchoolId() {
		return this.schoolId;
	}

	@JsonProperty("school_id")
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getDeviceToken() {
		return this.deviceToken;
	}

	@JsonProperty("device_token")
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getAcademicYear() {
		return this.academicYear;
	}

	@JsonProperty("academic_year")
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getProfileImageName() {
		return this.profileImageName;
	}

	@JsonProperty("profile_image_name")
	public void setProfileImageName(String profileImageName) {
		this.profileImageName = profileImageName;
	}

	public String getBirthdate() {
		return this.birthdate;
	}

	@JsonProperty("birthdate")
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return this.gender;
	}

	@JsonProperty("gender")
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	@JsonProperty("device_type")
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public int getClassroomId() {
		return this.classroomId;
	}

	@JsonProperty("classroom_id")
	public void setClassroomId(int classroomId) {
		this.classroomId = classroomId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	@JsonProperty("role_id")
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return this.name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return this.message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	public String getFeedId() {
		return feedId;
	}

	@JsonProperty("feed_id")
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getCommentBy() {
		return commentBy;
	}

	@JsonProperty("comment_by")
	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}

	public String getComment() {
		return comment;
	}

	@JsonProperty("comment")
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String[] getTaggedUserIds() {
		return taggedUserIds;
	}

	@JsonProperty("tagged_user_id")
	public void setTaggedUserIds(String[] taggedUserIds) {
		this.taggedUserIds = taggedUserIds;
	}

	public String getTaggedBy() {
		return taggedBy;
	}

	@JsonProperty("tagged_by")
	public void setTaggedBy(String taggedBy) {
		this.taggedBy = taggedBy;
	}

	public ArrayList<Attribute> getPreferences() {
		return preferences;
	}

	@JsonProperty("preferences")
	public void setPreferences(ArrayList<Attribute> preferences) {
		this.preferences = preferences;
	}
	@JsonProperty("file")
	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}
	public String getStudymateId() {
		return studymateId;
	}

	@JsonProperty("studymate_id")
	public void setStudymateId(String studymateId) {
		this.studymateId = studymateId;
	}

	public String getReadCategory() {
		return readCategory;
	}

	@JsonProperty("read_category")
	public void setReadCategory(String readCategory) {
		this.readCategory = readCategory;
	}

	public ArrayList<String> getRecordIds() {
		return recordIds;
	}

	@JsonProperty("record_id")
	public void setRecordIds(ArrayList<String> recordIds) {
		this.recordIds = recordIds;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
