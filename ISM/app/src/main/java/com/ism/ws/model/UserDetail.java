package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c161 on 20/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetail {
	
    private String tutorialGroupId;
    private String tutorialGroupName;
    private String tutorialGroupJoiningStatus;
    private String tutorialGroupComplete;
    private String profilePic;
    private String fullName;
    private String userId;
    private String classId;
    private String courseName;
    private String schoolName;
    private String academicYear;
    private String roleId;
    private String schoolDistrict;
    private String credentialId;
    private String className;
    private String schoolType;
    private String schoolId;
    private String courseId;
    private ArrayList<TutorialGroupMember> tutorialGroupMembers;

    @JsonProperty("tutorial_group_id")
    public String getTutorialGroupId() {
        return this.tutorialGroupId;
    }

    public void setTutorialGroupId(String tutorialGroupId) {
        this.tutorialGroupId = tutorialGroupId;
    }

    @JsonProperty("tutorial_group_name")
    public String getTutorialGroupName() {
        return this.tutorialGroupName;
    }

    public void setTutorialGroupName(String tutorialGroupName) {
        this.tutorialGroupName = tutorialGroupName;
    }

    @JsonProperty("tutorial_group_joining_status")
    public String getTutorialGroupJoiningStatus() {
        return this.tutorialGroupJoiningStatus;
    }

    public void setTutorialGroupJoiningStatus(String tutorialGroupJoiningStatus) {
        this.tutorialGroupJoiningStatus = tutorialGroupJoiningStatus;
    }

    @JsonProperty("tutorial_group_complete")
    public String getTutorialGroupComplete() {
        return this.tutorialGroupComplete;
    }

    public void setTutorialGroupComplete(String tutorialGroupComplete) {
        this.tutorialGroupComplete = tutorialGroupComplete;
    }

    @JsonProperty("profile_pic")
    public String getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	@JsonProperty("class_id")
	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@JsonProperty("course_name")
	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@JsonProperty("school_name")
	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@JsonProperty("academic_year")
	public String getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	@JsonProperty("role_id")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@JsonProperty("district_name")
	public String getSchoolDistrict() {
		return this.schoolDistrict;
	}

	public void setSchoolDistrict(String schoolDistrict) {
		this.schoolDistrict = schoolDistrict;
	}

	@JsonProperty("credential_id")
	public String getCredentialId() {
		return this.credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	@JsonProperty("class_name")
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@JsonProperty("school_type")
	public String getSchoolType() {
		return this.schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	@JsonProperty("school_id")
	public String getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	@JsonProperty("course_id")
	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

    @JsonProperty("tutorial_group_members")
    public ArrayList<TutorialGroupMember> getTutorialGroupMembers() {
        return this.tutorialGroupMembers;
    }

    public void setTutorialGroupMembers(ArrayList<TutorialGroupMember> tutorialGroupMembers) {
        this.tutorialGroupMembers = tutorialGroupMembers;
    }

}
