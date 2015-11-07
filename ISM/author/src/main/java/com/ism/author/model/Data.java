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


    private String schoolName;
    private String credentialId;
    private String courseId;
    private String courseName;
    private String roleId;
    private String schoolId;
    private String academicYear;
    private String schoolDestrict;
    private String schoolType;
    private String classId;
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
    private String countryName;
    private String stateName;
    private String cityName;
    private ArrayList<PostFeedCommentsModel> commentList;
    private String fullName;
    private String id;
    private String commentBy;
    private String comment;
    private String profileLink;
    private String className;
    private String classNickname;
    private String subjectImage;
    private String subjectName;
    private String topicName;
    private String subjectId;
    private String topicDescription;
    private String feed_by;
    private String video_thumbnail;
    private List<String> images;
    private ArrayList<PostFeedImagesModel> feed_images;
    private String questionFormat;
    private String questionAssetsLink;
    private String questionHint;
    private String questionImageLink;
    private String evaluationNotes;
    private String topicId;
    private ArrayList<QuestionAnswersModel> answers;
    private String classroomId;
    private String bookId;
    private String solution;
    private String questionId;
    private String questionText;
    private String questionCreatorId;
    private String questionCreatorName;
    private String total_student_attempted;
    private String total_student;
    private String exam_type;
    private String average_score;
    private String studentName;
    private String EvaluationsScore;
    private ArrayList<Data> evaluations;

    private ArrayList<Data> questions;
    private String examID;
    private String studentId;

    private boolean isFlagged;
    private String studentResponse;
    private String examDate;
    private String examName;
    private String bookName;
    private String examMode;

    /*these methods are for login response*/

    @JsonProperty("feed_images")
    public ArrayList<PostFeedImagesModel> getFeed_images() {
        return feed_images;
    }

    public void setFeed_images(ArrayList<PostFeedImagesModel> feed_images) {
        this.feed_images = feed_images;
    }

    public String getVideo_thumbnail() {
        return video_thumbnail;
    }

    public void setVideo_thumbnail(String video_thumbnail) {
        this.video_thumbnail = video_thumbnail;
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
    public ArrayList<PostFeedCommentsModel> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<PostFeedCommentsModel> commentList) {
        this.commentList = commentList;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


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
    public ArrayList<QuestionAnswersModel> getAnswers() {
        return this.answers;
    }

    public void setAnswers(ArrayList<QuestionAnswersModel> answers) {
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

    private Boolean isQuestionAddedInPreview = false;

    public Boolean getIsQuestionAddedInPreview() {
        return isQuestionAddedInPreview;
    }

    public void setIsQuestionAddedInPreview(Boolean isQuestionAddedInPreview) {
        this.isQuestionAddedInPreview = isQuestionAddedInPreview;
    }

    @JsonProperty("average_score")
    public String getAverage_score() {
        return average_score;
    }

    public void setAverage_score(String average_score) {
        this.average_score = average_score;
    }

    @JsonProperty("exam_type")
    public String getExam_type() {
        return exam_type;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }

    @JsonProperty("total_student_attempted")
    public String getTotal_student_attempted() {
        return total_student_attempted;
    }

    public void setTotal_student_attempted(String total_student_attempted) {
        this.total_student_attempted = total_student_attempted;
    }

    @JsonProperty("total_student")
    public String getTotal_student() {
        return total_student;
    }

    public void setTotal_student(String total_student) {
        this.total_student = total_student;
    }

    @JsonProperty("student_name")
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @JsonProperty("evoluation_score")
    public String getEvaluationsScore() {
        return EvaluationsScore;
    }

    public void setEvaluationsScore(String evaluationsScore) {
        EvaluationsScore = evaluationsScore;
    }

    @JsonProperty("evaluation")
    public ArrayList<Data> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(ArrayList<Data> evaluations) {
        this.evaluations = evaluations;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    @JsonProperty("exam_id")
    public String getExamID() {
        return examID;
    }

    @JsonProperty("questions")
    public ArrayList<Data> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Data> questions) {
        this.questions = questions;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setIsFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    @JsonProperty("student_id")
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentResponse(String studentResponse) {
        this.studentResponse = studentResponse;
    }

    @JsonProperty("student_response")
    public String getStudentResponse() {
        return studentResponse;
    }

    @JsonProperty("district_name")
    public String getSchoolDestrict() {
        return schoolDestrict;
    }

    public void setSchoolDestrict(String schoolDestrict) {
        this.schoolDestrict = schoolDestrict;
    }

    @JsonProperty("school_type")
    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }


    @JsonProperty("class_id")
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @JsonProperty("country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("state_name")
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @JsonProperty("city_name")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @JsonProperty("book_name")
    public String getBookName() {
        return bookName;
    }

//    @JsonProperty("created_date")
//    public String getExamDate() {
//        return examDate;
//    }
//
//    public void setExamDate(String examDate) {
//        this.examDate = examDate;
//    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @JsonProperty("exam_name")
    public String getExamName() {
        return examName;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public void setExamMode(String examMode) {
        this.examMode = examMode;
    }

    @JsonProperty("exam_mode")
    public String getExamMode() {
        return examMode;
    }
}
