package com.ism.teacher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c166 on 04/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestObject {


    //ry

    //	ForgotPasswordRequest
    private String emailId;



    //	CredentialsRequest
    private String schoolName;
    private String homeAddress;
    private int cityId;
    private String lastname;
    private String firstname;
    private int stateId;
    private int countryId;
    private String contactNumber;
    private String emailAddress;

    //=============ry=======

    private String feedId;
    private String commentBy;
    private String comment;
    private String assignmentText;
    private int classroomId;
    private String userId;
    private int subjectId;
    private String submissionDate;
    private int topicId;

    private String examType;
    private String examMode;
    private String examStartTime;
    private String negativeMarkValue;
    private String examDuration;
    private String examInstruction;
    private double bookId;
    private String passingPercent;
    private String examName;
    private String declareResults;
    private String negativeMarking;
    private String randomQuestion;
    private double attemptCount;
    private String examCategory;
    private String examStartDate;

    private String username;
    private String password;

    private String feedBy;
    private String feedText;
    private String videoLink;
    private String audioLink;
    private List<String> images;
    private String postedOn;
    private String videoThumbnail;


    private int role;
    private String examId;

    private String[] taggedUserIds;
    private String taggedBy;

    private String[] likedId;
    private String[] unlikedId;

    public String getFeedId() {
        return this.feedId;
    }

    @JsonProperty("feed_id")
    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }


    public String getCommentBy() {
        return this.commentBy;
    }

    @JsonProperty("comment_by")
    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }


    public String getComment() {
        return this.comment;
    }

    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getAssignmentText() {
        return this.assignmentText;
    }

    @JsonProperty("assignment_text")
    public void setAssignmentText(String assignmentText) {
        this.assignmentText = assignmentText;
    }

    @JsonProperty("classroom_id")
    public int getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }


    public String getUserId() {
        return this.userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSubjectId() {
        return this.subjectId;
    }

    @JsonProperty("subject_id")
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }


    public String getSubmissionDate() {
        return this.submissionDate;
    }

    @JsonProperty("submission_date")
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }


    public int getTopicId() {
        return this.topicId;
    }

    @JsonProperty("topic_id")
    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }


    public String getExamType() {
        return this.examType;
    }

    @JsonProperty("exam_type")
    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamMode() {
        return this.examMode;
    }

    @JsonProperty("exam_mode")
    public void setExamMode(String examMode) {
        this.examMode = examMode;
    }

    public String getExamStartTime() {
        return this.examStartTime;
    }

    @JsonProperty("exam_start_time")
    public void setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
    }

    public String getNegativeMarkValue() {
        return this.negativeMarkValue;
    }

    @JsonProperty("negative_mark_value")
    public void setNegativeMarkValue(String negativeMarkValue) {
        this.negativeMarkValue = negativeMarkValue;
    }


    public String getExamDuration() {
        return this.examDuration;
    }

    @JsonProperty("exam_duration")
    public void setExamDuration(String examDuration) {
        this.examDuration = examDuration;
    }

    public String getExamInstruction() {
        return this.examInstruction;
    }

    @JsonProperty("exam_instruction")
    public void setExamInstruction(String examInstruction) {
        this.examInstruction = examInstruction;
    }

    public double getBookId() {
        return this.bookId;
    }

    @JsonProperty("book_id")
    public void setBookId(double bookId) {
        this.bookId = bookId;
    }

    public String getPassingPercent() {
        return this.passingPercent;
    }

    @JsonProperty("passing_percent")
    public void setPassingPercent(String passingPercent) {
        this.passingPercent = passingPercent;
    }

    public String getExamName() {
        return this.examName;
    }

    @JsonProperty("exam_name")
    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDeclareResults() {
        return this.declareResults;
    }

    @JsonProperty("declare_results")
    public void setDeclareResults(String declareResults) {
        this.declareResults = declareResults;
    }

    public String getNegativeMarking() {
        return this.negativeMarking;
    }

    @JsonProperty("negative_marking")
    public void setNegativeMarking(String negativeMarking) {
        this.negativeMarking = negativeMarking;
    }

    public String getRandomQuestion() {
        return this.randomQuestion;
    }

    @JsonProperty("random_question")
    public void setRandomQuestion(String randomQuestion) {
        this.randomQuestion = randomQuestion;
    }

    public double getAttemptCount() {
        return this.attemptCount;
    }

    @JsonProperty("attempt_count")
    public void setAttemptCount(double attemptCount) {
        this.attemptCount = attemptCount;
    }

    public String getExamCategory() {
        return this.examCategory;
    }

    @JsonProperty("exam_category")
    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public String getExamStartDate() {
        return this.examStartDate;
    }

    @JsonProperty("exam_start_date")
    public void setExamStartDate(String examStartDate) {
        this.examStartDate = examStartDate;
    }


    public String getUsername() {
        return this.username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }


    public String getFeedBy() {
        return this.feedBy;
    }

    @JsonProperty("feed_by")
    public void setFeedBy(String feedBy) {
        this.feedBy = feedBy;
    }

    public String getFeedText() {
        return this.feedText;
    }

    @JsonProperty("feed_text")
    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    public String getVideoLink() {
        return this.videoLink;
    }

    @JsonProperty("video_link")
    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getAudioLink() {
        return this.audioLink;
    }

    @JsonProperty("audio_link")
    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }


    public List<String> getImages() {
        return this.images;
    }

    @JsonProperty("images")
    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPostedOn() {
        return this.postedOn;
    }

    @JsonProperty("posted_on")
    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    public String getVideoThumbnail() {
        return this.videoThumbnail;
    }

    @JsonProperty("video_thumbnail")
    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getExamId() {
        return this.examId;
    }

    @JsonProperty("exam_id")
    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getRole() {
        return this.role;
    }

    @JsonProperty("role")
    public void setRole(int role) {
        this.role = role;
    }

    public String[] getTaggedUserIds() {
        return taggedUserIds;
    }

    @JsonProperty("tagged_user_id")
    public void setTaggedUserIds(String[] taggedUserIds) {
        this.taggedUserIds = taggedUserIds;
    }

    public String getTaggedBy() {
        return this.taggedBy;
    }

    @JsonProperty("tagged_by")
    public void setTaggedBy(String taggedBy) {
        this.taggedBy = taggedBy;
    }


    public String[] getLikedId() {
        return likedId;
    }

    @JsonProperty("liked_id")
    public void setLikedId(String[] likedId) {
        this.likedId = likedId;
    }

    public String[] getUnlikedId() {
        return unlikedId;
    }

    @JsonProperty("unliked_id")
    public void setUnlikedId(String[] unlikedId) {
        this.unlikedId = unlikedId;
    }




    //ry


    /**
     * For request credentials ==============================
     */

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

    public int getCountryId() {
        return this.countryId;
    }

    @JsonProperty("country_id")
    public void setCountryId(int countryId) {
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

    /**
     * =======================For request credentials ==============================
     */


    /**
     * For freeze question ==============================
     */

    public ArrayList<String> list_question_ids;


    public ArrayList<String> getList_question_ids() {
        return list_question_ids;
    }

    @JsonProperty("question_id")
    public void setList_question_ids(ArrayList<String> list_question_ids) {
        this.list_question_ids = list_question_ids;
    }
    /**
     * ============For freeze question ==============================
     */

    @JsonProperty("email_id")
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }



}
