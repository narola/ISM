package com.ism.teacher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data implements Serializable {

    //CityResponse
    private String cityName;
    private String stateName;
    private String countryName;


    //credential
    private String credentialId;
    private String schoolName;
    private String courseId;
    private String courseName;
    private String roleId;
    private String schoolId;
    private String schoolDistrict;
    private String academicYear;
    private String schoolType;
    private String classId;
    private String className;


    //Feeds Response
    private String course_name;

    private String feed_id;
    private String created_date;
    private String total_comment;
    private String total_like;
    private String modified_date;
    private String posted_on;
    private String user_id;
    private String audio_link;
    private String video_link;
    private String full_name;
    private String feed_text;
    private int like;
    private ArrayList<Comment> comment_list;

    private String video_thumbnail;
    private String audioLink;
    private ArrayList<PostFeedImagesModel> feed_images;


    //For StudymatesList

    private String profile_pic;


    //For AllComments

    private String id;
    private String commentBy;
    private String comment;
    private String profileLink;


    /*for the getAllClassrooms*/

    private String class_name;
    private String class_nickname;
    private String course_id;

    /*for the getSubjects*/

    private String subject_id;


    private String subject_image;
    private String subject_name;

     /* for the getTopics*/

    private String topic_name;
    private String topic_description;


    // for question bank
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


    //login response
    @JsonProperty("credential_id")
    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    @JsonProperty("school_name")
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @JsonProperty("course_id")

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @JsonProperty("course_name")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @JsonProperty("role_id")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @JsonProperty("school_id")
    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @JsonProperty("district_name")
    public String getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(String schoolDestrict) {
        this.schoolDistrict = schoolDestrict;
    }

    @JsonProperty("academic_year")
    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
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


    //request credentials

    @JsonProperty("city_name")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @JsonProperty("state_name")
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @JsonProperty("country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("class_name")
    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @JsonProperty("class_nickname")
    public String getClass_nickname() {
        return class_nickname;
    }

    public void setClass_nickname(String class_nickname) {
        this.class_nickname = class_nickname;
    }


    @JsonProperty("subject_image")
    public String getSubject_image() {
        return subject_image;
    }

    public void setSubject_image(String subject_image) {
        this.subject_image = subject_image;
    }

    @JsonProperty("subject_name")

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    @JsonProperty("topic_name")

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    @JsonProperty("topic_description")

    public String getTopic_description() {
        return topic_description;
    }

    public void setTopic_description(String topic_description) {
        this.topic_description = topic_description;
    }

    @JsonProperty("like")
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("commentBy")
    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @JsonProperty("profileLink")
    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    @JsonProperty("profile_pic")
    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }


    //For feeds
    @JsonProperty("video_thumbnail")
    public String getVideo_thumbnail() {
        return video_thumbnail;
    }

    public void setVideo_thumbnail(String video_thumbnail) {
        this.video_thumbnail = video_thumbnail;
    }

    @JsonProperty("audio_link")
    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    @JsonProperty("feed_images")
    public ArrayList<PostFeedImagesModel> getFeed_images() {
        return feed_images;
    }

    public void setFeed_images(ArrayList<PostFeedImagesModel> feed_images) {
        this.feed_images = feed_images;
    }


    @JsonProperty("feed_id")
    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    @JsonProperty("created_date")
    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    @JsonProperty("total_comment")
    public String getTotal_comment() {
        return total_comment;
    }

    public void setTotal_comment(String total_comment) {
        this.total_comment = total_comment;
    }

    @JsonProperty("total_like")
    public String getTotal_like() {
        return total_like;
    }

    public void setTotal_like(String total_like) {
        this.total_like = total_like;
    }

    @JsonProperty("modified_date")
    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    @JsonProperty("posted_on")
    public String getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(String posted_on) {
        this.posted_on = posted_on;
    }

    @JsonProperty("user_id")
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @JsonProperty("video_link")
    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    @JsonProperty("full_name")
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    @JsonProperty("feed_text")
    public String getFeed_text() {
        return feed_text;
    }

    public void setFeed_text(String feed_text) {
        this.feed_text = feed_text;
    }


    @JsonProperty("comment_list")
    public ArrayList<Comment> getCommentList() {
        return comment_list;
    }

    public void setCommentList(ArrayList<Comment> comment) {
        this.comment_list = comment;
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


    //handling for previewquestions

    private Boolean isQuestionAddedInPreview = false;

    public Boolean getIsQuestionAddedInPreview() {
        return isQuestionAddedInPreview;
    }

    public void setIsQuestionAddedInPreview(Boolean isQuestionAddedInPreview) {
        this.isQuestionAddedInPreview = isQuestionAddedInPreview;
    }

    private String exam_id;

    @JsonProperty("exam_id")
    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }


    @JsonProperty("subject_id")
    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }


}
