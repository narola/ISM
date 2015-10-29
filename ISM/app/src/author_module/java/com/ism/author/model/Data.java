package com.ism.author.model;

/**
 * these is the common class for handling all the api response data.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    /*these parameters are of login response*/
    private String schoolName;
    private String courseId;
    private String courseName;
    private String roleId;
    private String schoolId;
    private String academicYear;


    /*these paarmeters are of getALlFeeds response*/
    private String feedId;
    private String createdDate;
    private String totalComment;
    private String totalLike;
    private String modifiedDate;
    private String postedOn;
    private String userId;
    private String feedText;
    private String audioLink;
    private String videoLink;
    private String profilePic;
    private double like;
    private ArrayList<GetAllFeedsComment> commentList;

    /*these parameters are of GetStudyMates Response*/

    private String fullName;

    /*these are for the getAllComments*/

    private String id;
    private String commentBy;
    private String comment;
    private String profileLink;


    /*these methods are for login response*/
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


    /*these methods are for getAllfeeds response*/


    public double getLike() {
        return this.like;
    }

    public void setLike(double like) {
        this.like = like;
    }

    @JsonProperty("feed_id")
    public String getFeedId() {
        return this.feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    @JsonProperty("created_date")
    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("total_comment")
    public String getTotalComment() {
        return this.totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }

    @JsonProperty("total_like")
    public String getTotalLike() {
        return this.totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

    @JsonProperty("modified_date")
    public String getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @JsonProperty("posted_on")
    public String getPostedOn() {
        return this.postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("audio_link")
    public String getAudioLink() {
        return this.audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    @JsonProperty("video_link")
    public String getVideoLink() {
        return this.videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }


    @JsonProperty("feed_text")
    public String getFeedText() {
        return feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    @JsonProperty("profile_pic")
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @JsonProperty("comment_list")
    public ArrayList<GetAllFeedsComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<GetAllFeedsComment> commentList) {
        this.commentList = commentList;
    }



    /*these methods are for GetStudyMates response*/

    @JsonProperty("full_name")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    /*these methods are for the getCommentsList*/


    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("comment_by")
    public String getCommentBy() {
        return this.commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    @JsonProperty("comment")
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty("profile_link")
    public String getProfileLink() {
        return this.profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }


}
