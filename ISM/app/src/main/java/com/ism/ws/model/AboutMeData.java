package com.ism.ws.model;

import org.json.JSONObject;

public class AboutMeData {
	
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

    public AboutMeData (JSONObject json) {

        this.totalExams = json.optString("total_exams");
        this.courseName = json.optString("course_name");
        this.totalAuthorsFollowed = json.optString("total_authors_followed");
        this.totalQuestionAsked = json.optString("total_question_asked");
        this.totalFavoriteQuestions = json.optString("total_Favorite_questions");
        this.userId = json.optString("user_id");
        this.ambitionInLife = json.optString("ambitionInLife");
        this.birthdate = json.optString("birthdate");
        this.aboutMeText = json.optString("aboutMeText");
        this.ismScore = json.optString("ism_score");
        this.profilePic = json.optString("profile_pic");
        this.schoolName = json.optString("school_name");
        this.username = json.optString("username");
        this.ismRank = json.optString("ism_rank");
        this.contactNumber = json.optString("contact_number");
        this.totalPost = json.optString("total_post");
        this.totalAssignment = json.optString("total_assignment");
        this.totalStudymates = json.optString("total_studymates");
        this.totalBadgesEarned = json.optString("total_badges_earned");

    }

    
    public String getTotalExams() {
        return this.totalExams;
    }

    public void setTotalExams(String totalExams) {
        this.totalExams = totalExams;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTotalAuthorsFollowed() {
        return this.totalAuthorsFollowed;
    }

    public void setTotalAuthorsFollowed(String totalAuthorsFollowed) {
        this.totalAuthorsFollowed = totalAuthorsFollowed;
    }

    public String getTotalQuestionAsked() {
        return this.totalQuestionAsked;
    }

    public void setTotalQuestionAsked(String totalQuestionAsked) {
        this.totalQuestionAsked = totalQuestionAsked;
    }

    public String getTotalFavoriteQuestions() {
        return this.totalFavoriteQuestions;
    }

    public void setTotalFavoriteQuestions(String totalFavoriteQuestions) {
        this.totalFavoriteQuestions = totalFavoriteQuestions;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmbitionInLife() {
        return this.ambitionInLife;
    }

    public void setAmbitionInLife(String ambitionInLife) {
        this.ambitionInLife = ambitionInLife;
    }

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAboutMeText() {
        return this.aboutMeText;
    }

    public void setAboutMeText(String aboutMeText) {
        this.aboutMeText = aboutMeText;
    }

    public String getIsmScore() {
        return this.ismScore;
    }

    public void setIsmScore(String ismScore) {
        this.ismScore = ismScore;
    }

    public String getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsmRank() {
        return this.ismRank;
    }

    public void setIsmRank(String ismRank) {
        this.ismRank = ismRank;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getTotalPost() {
        return this.totalPost;
    }

    public void setTotalPost(String totalPost) {
        this.totalPost = totalPost;
    }

    public String getTotalAssignment() {
        return this.totalAssignment;
    }

    public void setTotalAssignment(String totalAssignment) {
        this.totalAssignment = totalAssignment;
    }

    public String getTotalStudymates() {
        return this.totalStudymates;
    }

    public void setTotalStudymates(String totalStudymates) {
        this.totalStudymates = totalStudymates;
    }

    public String getTotalBadgesEarned() {
        return this.totalBadgesEarned;
    }

    public void setTotalBadgesEarned(String totalBadgesEarned) {
        this.totalBadgesEarned = totalBadgesEarned;
    }


    
}
