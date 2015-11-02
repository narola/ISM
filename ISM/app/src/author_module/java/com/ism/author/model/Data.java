package com.ism.author.model;

/**
 * these is the common class for handling all the api response data.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


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

    /*these are for the getAllClassrooms*/

    private String className;
    private String classNickname;

    /*these are for the getSubjects*/

    private String subjectImage;
    private String subjectName;

     /*these are for the getTopics*/

    private String topicName;
    private String subjectId;
    private String topicDescription;


    /*these are for the postfeed*/

    private String feed_by;

    private List<String> images;


    /*these are for the get questionbank*/

    private String questionFormat;
    private String questionAssetsLink;
    private String questionHint;
    private String questionImageLink;
    private String evaluationNotes;
    private String topicId;
    private ArrayList<AnswersModel> answers;
    private String classroomId;
    private String bookId;
    private String solution;
    private String questionId;
    private String questionText;
    private String questionCreatorId;
    private String questionCreatorName;


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


      /*these are for the getAllClassrooms*/

    @JsonProperty("class_name")
    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty("class_nickname")
    public String getClassNickname() {
        return this.classNickname;
    }

    public void setClassNickname(String classNickname) {
        this.classNickname = classNickname;
    }

      /*these are for the getSubjects*/

    @JsonProperty("subject_image")
    public String getSubjectImage() {
        return this.subjectImage;
    }

    public void setSubjectImage(String subjectImage) {
        this.subjectImage = subjectImage;
    }

    @JsonProperty("subject_name")
    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


    /*these are for the getTopics*/

    @JsonProperty("topic_name")
    public String getTopicName() {
        return this.topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @JsonProperty("subject_id")
    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @JsonProperty("topic_description")
    public String getTopicDescription() {
        return this.topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    /*these are for the postfeed*/
    @JsonProperty("feed_by")
    public String getFeed_by() {
        return feed_by;
    }

    public void setFeed_by(String feed_by) {
        this.feed_by = feed_by;
    }

    @JsonProperty("images")
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    /*these are for the get questionbank*/

    @JsonProperty("question_format")
    public String getQuestionFormat() {
        return this.questionFormat;
    }

    public void setQuestionFormat(String questionFormat) {
        this.questionFormat = questionFormat;
    }

    @JsonProperty("question_assets_link")
    public String getQuestionAssetsLink() {
        return this.questionAssetsLink;
    }

    public void setQuestionAssetsLink(String questionAssetsLink) {
        this.questionAssetsLink = questionAssetsLink;
    }

    @JsonProperty("question_hint")
    public String getQuestionHint() {
        return this.questionHint;
    }

    public void setQuestionHint(String questionHint) {
        this.questionHint = questionHint;
    }

    @JsonProperty("question_image_link")
    public String getQuestionImageLink() {
        return this.questionImageLink;
    }

    public void setQuestionImageLink(String questionImageLink) {
        this.questionImageLink = questionImageLink;
    }

    @JsonProperty("evaluation_notes")
    public String getEvaluationNotes() {
        return this.evaluationNotes;
    }

    public void setEvaluationNotes(String evaluationNotes) {
        this.evaluationNotes = evaluationNotes;
    }

    @JsonProperty("topic_id")
    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @JsonProperty("answers")
    public ArrayList<AnswersModel> getAnswers() {
        return this.answers;
    }

    public void setAnswers(ArrayList<AnswersModel> answers) {
        this.answers = answers;
    }

    @JsonProperty("classroom_id")
    public String getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    @JsonProperty("book_id")
    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @JsonProperty("solution")
    public String getSolution() {
        return this.solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @JsonProperty("question_id")
    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("question_text")
    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @JsonProperty("question_creator_id")
    public String getQuestionCreatorId() {
        return this.questionCreatorId;
    }

    public void setQuestionCreatorId(String questionCreatorId) {
        this.questionCreatorId = questionCreatorId;
    }

    @JsonProperty("question_creator_name")
    public String getQuestionCreatorName() {
        return this.questionCreatorName;
    }

    public void setQuestionCreatorName(String questionCreatorName) {
        this.questionCreatorName = questionCreatorName;
    }


}
