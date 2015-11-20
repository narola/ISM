package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.author.model.QuestionAnswersModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c166 on 04/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {


    private String schoolName;
    private String homeAddress;
    private int cityId;
    private String lastname;
    private String firstname;
    private int stateId;
    private int countryId;
    private String contactNumber;
    private String emailAddress;
    private String name;
    private String message;
    private String profileImage;
    private int courseId;
    private int schoolId;
    private String deviceToken;
    private String academicYear;
    private String profileImageName;
    private String birthdate;
    private String gender;
    private String deviceType;
    private String roleId;
    private String feedId;
    private String commentBy;
    private String comment;
    private String assignmentText;
    private String classroomId;
    private String userId;
    private String subjectId;
    private String submissionDate;
    private String emailId;
    private String topicId;
    private String examType;
    private String examMode;
    private String examStartTime;
    private String negativeMarkValue;
    private String examDuration;
    private String examInstruction;
    private String bookId;
    private String passingPercent;
    private String examName;
    private String declareResults;
    private String negativeMarking;
    private String randomQuestion;
    private String attemptCount;
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
    private String role;
    private String examId;
    private String[] taggedUserIds;
    private String taggedBy;
    private String[] likedId;
    private String[] unlikedId;
    private ArrayList<String> questionId;
    //    private String bankQuestionId;
    private String studentId;


    //these are for the add question

    private String questionFormat;
    private String questionAssetsLink;
    private String questionHint;
    private String questionImageLink;
    private String evaluationNotes;
    private String questionCreatorName;
    private ArrayList<QuestionAnswersModel> answers;
    private String solution;
    private String subjectName;
    private String questionText;
    private String questionCreatorId;

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
    public String getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }


    public String getUserId() {
        return this.userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }


    public String getSubmissionDate() {
        return this.submissionDate;
    }

    @JsonProperty("submission_date")
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }


    public String getTopicId() {
        return this.topicId;
    }

    @JsonProperty("topic_id")
    public void setTopicId(String topicId) {
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

    public String getBookId() {
        return this.bookId;
    }

    @JsonProperty("book_id")
    public void setBookId(String bookId) {
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

    public String getAttemptCount() {
        return this.attemptCount;
    }

    @JsonProperty("attempt_count")
    public void setAttemptCount(String attemptCount) {
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

    public String getRole() {
        return this.role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
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

    @JsonProperty("student_id")
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public ArrayList<String> getQuestionId() {
        return this.questionId;
    }

    @JsonProperty("question_id")
    public void setQuestionId(ArrayList<String> questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("email_id")
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


    public String getEmailId() {
        return this.emailId;
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


    public String getRoleId() {
        return this.roleId;
    }

    @JsonProperty("role_id")
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


    /*these are for add question*/

    public String getQuestionFormat() {
        return this.questionFormat;
    }

    @JsonProperty("question_format")
    public void setQuestionFormat(String questionFormat) {
        this.questionFormat = questionFormat;
    }

    public String getQuestionAssetsLink() {
        return this.questionAssetsLink;
    }

    @JsonProperty("question_assets_link")
    public void setQuestionAssetsLink(String questionAssetsLink) {
        this.questionAssetsLink = questionAssetsLink;
    }

    public String getQuestionHint() {
        return this.questionHint;
    }

    @JsonProperty("question_hint")
    public void setQuestionHint(String questionHint) {
        this.questionHint = questionHint;
    }

    public String getQuestionImageLink() {
        return this.questionImageLink;
    }

    @JsonProperty("question_image_link")
    public void setQuestionImageLink(String questionImageLink) {
        this.questionImageLink = questionImageLink;
    }

    public String getEvaluationNotes() {
        return this.evaluationNotes;
    }

    @JsonProperty("evaluation_notes")
    public void setEvaluationNotes(String evaluationNotes) {
        this.evaluationNotes = evaluationNotes;
    }

    public String getQuestionCreatorName() {
        return this.questionCreatorName;
    }

    @JsonProperty("question_creator_name")
    public void setQuestionCreatorName(String questionCreatorName) {
        this.questionCreatorName = questionCreatorName;
    }

    public ArrayList<QuestionAnswersModel> getAnswers() {
        return this.answers;
    }

    @JsonProperty("answers")
    public void setAnswers(ArrayList<QuestionAnswersModel> answers) {
        this.answers = answers;
    }

    public String getSolution() {
        return this.solution;
    }

    @JsonProperty("solution")
    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    @JsonProperty("subject_name")
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    @JsonProperty("question_text")
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionCreatorId() {
        return this.questionCreatorId;
    }

    @JsonProperty("question_creator_id")
    public void setQuestionCreatorId(String questionCreatorId) {
        this.questionCreatorId = questionCreatorId;
    }

//    public String getBankQuestionId() {
//        return this.bankQuestionId;
//    }
//
//    @JsonProperty("question_id")
//    public void setBankQuestionId(String bankQuestionId) {
//        this.bankQuestionId = questionCreatorId;
//    }


}
