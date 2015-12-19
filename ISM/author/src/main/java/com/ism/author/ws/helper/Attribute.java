package com.ism.author.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.author.ws.model.AnswerChoices;
import com.ism.author.ws.model.Answers;
import com.ism.author.ws.model.Tags;

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
    private String taggedBy;
    private String studentId;
    private String questionFormat;
    private String questionAssetsLink;
    private String questionHint;
    private String questionImageLink;
    private String evaluationNotes;
    private String questionCreatorName;
    private String solution;
    private String subjectName;
    private String questionText;
    private String questionCreatorId;
    private String questionScore;
    private String examAssessor;
    private String useQuestionScore;
    private String correctAnswerScore;
    private ArrayList<Answers> answers;
    private ArrayList<AnswerChoices> answerChoices;
    private ArrayList<String> questionIdList;
    private ArrayList<Tags> tags;
    private String[] taggedUserIds;
    private String[] likedId;
    private String[] unlikedId;

    private String studymateId;
    private ArrayList<String> recordIds;
    private String readCategory;
    private String hashtagData;
    private String resourceId;
    private String resourceType;
    private ArrayList<String> addBookId;
    private ArrayList<String> removeBookId;
    private String resourceName;
    private ArrayList<String> unfavoriteResourceId;
    private ArrayList<String> favResourceId;
    private String authorId;
    private String checkSlot;
    private String answerText;

    public ArrayList<String> getUnfavoriteResourceId() {
        return this.unfavoriteResourceId;
    }


    @JsonProperty("unfavorite_resource_id")
    public void setUnfavoriteResourceId(ArrayList<String> unfavoriteResourceId) {
        this.unfavoriteResourceId = unfavoriteResourceId;
    }

    public ArrayList<String> getFavResourceId() {
        return this.favResourceId;
    }

    @JsonProperty("fav_resource_id")
    public void setFavResourceId(ArrayList<String> favResourceId) {
        this.favResourceId = favResourceId;
    }

    public String getQuestionid() {
        return questionid;
    }

    @JsonProperty("question_id")
    public Attribute setQuestionid(String questionid) {
        this.questionid = questionid;
        return this;
    }

    private String questionid;


    public String getResourceName() {
        return resourceName;
    }

    @JsonProperty("resource_name")
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

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

    public String getCheckSlot() {
        return this.checkSlot;
    }

    @JsonProperty("check_slot")
    public void setCheckSlot(String checkSlot) {
        this.checkSlot = checkSlot;
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

    public ArrayList<Answers> getAnswers() {
        return this.answers;
    }

    @JsonProperty("answers")
    public void setAnswers(ArrayList<Answers> answers) {
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

    //    public void setBankQuestionId(String bankQuestionId) {
//    @JsonProperty("question_id")
//
//    }
//        return this.bankQuestionId;
    public String getStudymateId() {
        return studymateId;
    }

    @JsonProperty("studymate_id")
    public void setStudymateId(String studymateId) {
        this.studymateId = studymateId;
    }


    //    public String getBankQuestionId() {
//        this.bankQuestionId = questionCreatorId;
//    }
    @JsonProperty("read_category")
    public void setReadCategory(String readCategory) {
        this.readCategory = readCategory;
    }

    public String getReadCategory() {
        return readCategory;
    }

    public ArrayList<String> getRecordIds() {
        return recordIds;
    }

    @JsonProperty("record_id")
    public void setRecordIds(ArrayList<String> recordIds) {
        this.recordIds = recordIds;
    }

    public String getQuestionScore() {
        return questionScore;
    }

    @JsonProperty("question_score")
    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    public ArrayList<AnswerChoices> getAnswerChoices() {
        return this.answerChoices;
    }

    @JsonProperty("answer_choices")
    public void setAnswerChoices(ArrayList<AnswerChoices> answerChoices) {
        this.answerChoices = answerChoices;
    }


    public ArrayList<Tags> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(ArrayList<Tags> tags) {
        this.tags = tags;
    }


    public String getHashtagData() {
        return this.hashtagData;
    }

    @JsonProperty("hashtag_data")
    public void setHashtagData(String hashtagData) {
        this.hashtagData = hashtagData;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    @JsonProperty("resource_id")
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    @JsonProperty("resource_type")
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setRemoveBookId(ArrayList<String> removeBookId) {
        this.removeBookId = removeBookId;
    }

    @JsonProperty("remove_book_id")
    public ArrayList<String> getRemoveBookId() {
        return removeBookId;
    }

    @JsonProperty("add_book_id")
    public ArrayList<String> getAddBookId() {
        return addBookId;
    }

    public void setAddBookId(ArrayList<String> addBookId) {

        this.addBookId = addBookId;
    }


    public ArrayList<String> getQuestionIdList() {
        return questionIdList;
    }

    @JsonProperty("question_id_list")
    public Attribute setQuestionIdList(ArrayList<String> questionIdList) {
        this.questionIdList = questionIdList;
        return this;
    }


    /*these are the upload media params*/
    private ArrayList<MediaUploadAttribute> arrListFile = new ArrayList<MediaUploadAttribute>();
    private ArrayList<MediaUploadAttribute> arrListParam = new ArrayList<MediaUploadAttribute>();
    private String mediaType;

    public ArrayList<MediaUploadAttribute> getArrListFile() {
        return arrListFile;
    }

    public Attribute setArrListFile(ArrayList<MediaUploadAttribute> arrListFile) {
        this.arrListFile = arrListFile;
        return this;
    }

    public ArrayList<MediaUploadAttribute> getArrListParam() {
        return arrListParam;
    }

    public Attribute setArrListParam(ArrayList<MediaUploadAttribute> arrListParam) {
        this.arrListParam = arrListParam;
        return this;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Attribute setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @JsonProperty("exam_assessor")
    public String getExamAssessor() {
        return examAssessor;
    }

    public Attribute setExamAssessor(String examAssessor) {
        this.examAssessor = examAssessor;
        return this;
    }

    @JsonProperty("use_question_score")
    public String getUseQuestionScore() {
        return useQuestionScore;
    }

    public Attribute setUseQuestionScore(String useQuestionScore) {
        this.useQuestionScore = useQuestionScore;
        return this;
    }

    @JsonProperty("correct_answer_score")
    public String getCorrectAnswerScore() {
        return correctAnswerScore;
    }

    public Attribute setCorrectAnswerScore(String correctAnswerScore) {
        this.correctAnswerScore = correctAnswerScore;
        return this;
    }

    @JsonProperty("author_id")
    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @JsonProperty("answer_text")
    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}

