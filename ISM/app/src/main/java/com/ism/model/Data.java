package com.ism.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

	private String id;
	private String credentialId;
	private String string;
	private String schoolName;
	private String courseId;
	private String courseName;
	private String roleId;
	private String schoolId;
	private String schoolDestrict;
	private String academicYear;
	private String userId;
	private String userName;
    private String classId;
    private String className;
    private String countryName;
    private String stateName;
    private String cityName;
    private String profilePic;
    private String schoolType;

	@Override
	public String toString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	@JsonProperty("credential_id")
	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	@JsonProperty("school_name")
    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @JsonProperty("course_id")
	public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @JsonProperty("course_name")
    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @JsonProperty("role_id")
    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @JsonProperty("school_id")
    public String getSchoolId() {
        return this.schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @JsonProperty("academic_year")
    public String getAcademicYear() {
        return this.academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

	@JsonProperty("user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("username")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("class_id")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@JsonProperty("class_name")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@JsonProperty("district_name")
	public String getSchoolDestrict() {
		return schoolDestrict;
	}

	public void setSchoolDestrict(String schoolDestrict) {
		this.schoolDestrict = schoolDestrict;
	}

	@JsonProperty("country_name")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		setString(this.countryName = countryName);
	}

	@JsonProperty("state_name")
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		setString(this.stateName = stateName);
	}

	@JsonProperty("city_name")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		setString(this.cityName = cityName);
	}

	@JsonProperty("profile_pic")
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@JsonProperty("school_type")
	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
}
