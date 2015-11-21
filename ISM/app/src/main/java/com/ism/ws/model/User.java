package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String totalExams;
    private String courseName;
    private String totalAuthorsFollowed;
    private String totalQuestionAsked;
    private String totalFavoriteQuestions;
    private String userId;
    private String ambitionInLife;
    private String birthdate;
    private String aboutMeText;
    private String ismScore;
    private String profilePic;
    private String schoolName;
    private String username;
    private String ismRank;
    private String contactNumber;
    private String totalPost;
    private String totalAssignment;
    private String totalStudymates;
    private String totalBadgesEarned;
    private String fullName;
    private String tutorialGroupId;
    private String tutorialGroupName;
    private String tutorialGroupJoiningStatus;
    private String tutorialGroupComplete;
	private String classId;private String academicYear;
	private String roleId;
	private String schoolDistrict;
	private String credentialId;
	private String className;
	private String schoolType;
	private String schoolId;
	private String courseId;
	private ArrayList<TutorialGroupMember> tutorialGroupMembers;

    @JsonProperty("total_exams")
    public String getTotalExams() {
        return this.totalExams;
    }

    public void setTotalExams(String totalExams) {
        this.totalExams = totalExams;
    }
    @JsonProperty("course_name")
    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    @JsonProperty("total_authors_followed")
    public String getTotalAuthorsFollowed() {
        return this.totalAuthorsFollowed;
    }

    public void setTotalAuthorsFollowed(String totalAuthorsFollowed) {
        this.totalAuthorsFollowed = totalAuthorsFollowed;
    }
    @JsonProperty("total_question_asked")
    public String getTotalQuestionAsked() {
        return this.totalQuestionAsked;
    }

    public void setTotalQuestionAsked(String totalQuestionAsked) {
        this.totalQuestionAsked = totalQuestionAsked;
    }
    @JsonProperty("total_Favorite_questions")
    public String getTotalFavoriteQuestions() {
        return this.totalFavoriteQuestions;
    }

    public void setTotalFavoriteQuestions(String totalFavoriteQuestions) {
        this.totalFavoriteQuestions = totalFavoriteQuestions;
    }
    @JsonProperty("user_id")
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @JsonProperty("ambitionInLife")
    public String getAmbitionInLife() {
        return this.ambitionInLife;
    }

    public void setAmbitionInLife(String ambitionInLife) {
        this.ambitionInLife = ambitionInLife;
    }
    @JsonProperty("birthdate")
    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    @JsonProperty("aboutMeText")
    public String getAboutMeText() {
        return this.aboutMeText;
    }

    public void setAboutMeText(String aboutMeText) {
        this.aboutMeText = aboutMeText;
    }
    @JsonProperty("ism_score")
    public String getIsmScore() {
        return this.ismScore;
    }

    public void setIsmScore(String ismScore) {
        this.ismScore = ismScore;
    }
    @JsonProperty("profile_pic")
    public String getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
    @JsonProperty("school_name")
    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    @JsonProperty("username")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @JsonProperty("ism_rank")
    public String getIsmRank() {
        return this.ismRank;
    }

    public void setIsmRank(String ismRank) {
        this.ismRank = ismRank;
    }
    @JsonProperty("contact_number")
    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    @JsonProperty("total_post")
    public String getTotalPost() {
        return this.totalPost;
    }

    public void setTotalPost(String totalPost) {
        this.totalPost = totalPost;
    }
    @JsonProperty("total_assignment")
    public String getTotalAssignment() {
        return this.totalAssignment;
    }

    public void setTotalAssignment(String totalAssignment) {
        this.totalAssignment = totalAssignment;
    }
    @JsonProperty("total_studymates")
    public String getTotalStudymates() {
        return this.totalStudymates;
    }

    public void setTotalStudymates(String totalStudymates) {
        this.totalStudymates = totalStudymates;
    }
    @JsonProperty("total_badges_earned")
    public String getTotalBadgesEarned() {
        return this.totalBadgesEarned;
    }

    public void setTotalBadgesEarned(String totalBadgesEarned) {
        this.totalBadgesEarned = totalBadgesEarned;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

	@JsonProperty("class_id")
	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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
