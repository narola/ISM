package com.ism.author.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String classId;
    private String schoolId;
    private String courseId;
    private String academicYear;
    private String roleId;
    private String districtName;
    private String credentialId;
    private String className;
    private String schoolType;
    private String courseName;
    private String schoolName;
    private String userId;
    private String profilePic;
    private String fullName;
    private String username;
    private String aboutAuthor;
    private String totalExams;
    private String totalBooks;
    private String totalQuestionAsked;
    private String totalFavoriteQuestions;
    private String birthdate;
    private String totalFollwers;
    private String contactNumber;
    private String totalPost;
    private String totalAssignment;
    private String totalFollowing;
    private String totalBadgesEarned;

    private String totalQuestionsAnswered;

    @JsonProperty("education")
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    private String education;

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("total_exams")
    public String getTotalExams() {
        return this.totalExams;
    }

    public void setTotalExams(String totalExams) {
        this.totalExams = totalExams;
    }

    @JsonProperty("total_books")
    public String getTotalBooks() {
        return this.totalBooks;
    }

    public void setTotalBooks(String totalBooks) {
        this.totalBooks = totalBooks;
    }


    @JsonProperty("total_Favorite_questions")
    public String getTotalFavoriteQuestions() {
        return this.totalFavoriteQuestions;
    }
    public void setTotalFavoriteQuestions(String totalFavoriteQuestions) {
        this.totalFavoriteQuestions = totalFavoriteQuestions;
    }

    @JsonProperty("birthdate")
    public String getBirthdate() {
        return this.birthdate;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @JsonProperty("about_author")
    public String getAboutAuthor() {
        return this.aboutAuthor;
    }
    public void setAboutAuthor(String aboutAuthor) {
        this.aboutAuthor = aboutAuthor;
    }

    @JsonProperty("total_follweres")
    public String getTotalFollowers() {
        return this.totalFollwers;
    }

    public void setTotalFollowers(String totalFollowers) {
        this.totalFollwers = totalFollowers;
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

    @JsonProperty("total_following")
    public String getTotalFollowing() {
        return this.totalFollowing;
    }
    public void setTotalFollowing(String totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    @JsonProperty("total_badges_earned")
    public String getTotalBadgesEarned() {
        return this.totalBadgesEarned;
    }
    public void setTotalBadgesEarned(String totalBadgesEarned) {
        this.totalBadgesEarned = totalBadgesEarned;
    }

    @JsonProperty("class_id")
    public String getClassId() {
        return this.classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
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
    public String getDistrictName() {
        return this.districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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

    @JsonProperty("user_id")
    public String getUserId() {
        return this.userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
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

    //
//    }
//        this.ismScore = ismScore;
//    public void setIsmScore(String ismScore) {
//
//    }
//        return this.ismScore;
//    public String getIsmScore() {
//    @JsonProperty("ism_score")

    public void setTotalQuestionsAnswered(String totalQuestionsAnswered) {
        this.totalQuestionsAnswered = totalQuestionsAnswered;
    }


    public String getTotalQuestionsAnswered() {
        return totalQuestionsAnswered;
    }
}
