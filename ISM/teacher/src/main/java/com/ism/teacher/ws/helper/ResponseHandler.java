package com.ism.teacher.ws.helper;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.teacher.ws.model.AdminConfig;
import com.ism.teacher.ws.model.Badges;
import com.ism.teacher.ws.model.BlockedUsers;
import com.ism.teacher.ws.model.Cities;
import com.ism.teacher.ws.model.Classrooms;
import com.ism.teacher.ws.model.CommentList;
import com.ism.teacher.ws.model.Countries;
import com.ism.teacher.ws.model.Courses;
import com.ism.teacher.ws.model.CreateExam;
import com.ism.teacher.ws.model.ExamEvaluation;
import com.ism.teacher.ws.model.ExamQuestions;
import com.ism.teacher.ws.model.ExamSubmission;
import com.ism.teacher.ws.model.Exams;
import com.ism.teacher.ws.model.Feed;
import com.ism.teacher.ws.model.Feeds;
import com.ism.teacher.ws.model.FileUploadResponse;
import com.ism.teacher.ws.model.Group;
import com.ism.teacher.ws.model.HashTags;
import com.ism.teacher.ws.model.LessonNotes;
import com.ism.teacher.ws.model.Message;
import com.ism.teacher.ws.model.Notes;
import com.ism.teacher.ws.model.Notification;
import com.ism.teacher.ws.model.Question;
import com.ism.teacher.ws.model.Questions;
import com.ism.teacher.ws.model.SettingPreferences;
import com.ism.teacher.ws.model.States;
import com.ism.teacher.ws.model.Students;
import com.ism.teacher.ws.model.StudymateRequest;
import com.ism.teacher.ws.model.Studymates;
import com.ism.teacher.ws.model.Subjects;
import com.ism.teacher.ws.model.Token;
import com.ism.teacher.ws.model.Topics;
import com.ism.teacher.ws.model.User;
import com.ism.teacher.ws.model.UserImages;
import com.ism.teacher.ws.model.UserPreferences;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHandler {

    private String TAG = ResponseHandler.class.getSimpleName();
    private String message;
    private String status;

    private String mediaType;
    private String imageLink;

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String DUPLICATE_ENTRY = "Duplicate entry";

    private ArrayList<Feeds> feedImages;
    private ArrayList<BlockedUsers> blockedUsers;
    private ArrayList<SettingPreferences> preference;
    private ArrayList<UserPreferences> userPreference;
    private ArrayList<Students> students;
    private ArrayList<Feeds> feeds;
    private ArrayList<CommentList> comments;
    private ArrayList<Studymates> studymates;
    private ArrayList<Exams> exams;
    private ArrayList<Subjects> subjects;
    private ArrayList<Classrooms> classrooms;
    private ArrayList<ExamQuestions> examQuestions;
    private ArrayList<ExamSubmission> examSubmission;
    private ArrayList<ExamEvaluation> examEvaluation;
    private ArrayList<Topics> topics;
    private ArrayList<CreateExam> createExam;
    private ArrayList<Countries> countries;
    private ArrayList<States> states;
    private ArrayList<Cities> cities;
    private ArrayList<Feed> feed;
    private ArrayList<Courses> courses;
    private ArrayList<Questions> questions;
    private ArrayList<User> user;
    private ArrayList<Questions> questionBanks;
    private ArrayList<HashTags> tags;
    private ArrayList<Question> question;
    private UserImages userImages;
    private ArrayList<Token> token;
    private ArrayList<AdminConfig> adminConfig;
    private ArrayList<Group> group;
    private ArrayList<Badges> badges;
    private ArrayList<Notification> notification;
    private ArrayList<StudymateRequest> studymateRequest;
    private ArrayList<Message> messages;


    @JsonProperty("students")
    public ArrayList<Students> getStudents() {
        return students;
    }

    public ResponseHandler setStudents(ArrayList<Students> students) {
        this.students = students;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @JsonProperty("blocked_users")
    public ArrayList<BlockedUsers> getBlockedUsers() {
        return this.blockedUsers;
    }

    public void setBlockedUsers(ArrayList<BlockedUsers> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    @JsonProperty("user_preference")
    public ArrayList<UserPreferences> getUserPreference() {
        return this.userPreference;
    }

    public void setUserPreference(ArrayList<UserPreferences> userPreference) {
        this.userPreference = userPreference;
    }

    @JsonProperty("preference")
    public ArrayList<SettingPreferences> getPreference() {
        return preference;
    }

    public void setPreference(ArrayList<SettingPreferences> preference) {
        this.preference = preference;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * User Profile
     */

    @JsonProperty("badges")
    public ArrayList<Badges> getBadges() {
        return this.badges;
    }

    public void setBadges(ArrayList<Badges> badges) {
        this.badges = badges;
    }

    @JsonProperty("notification")
    public ArrayList<Notification> getNotification() {
        return this.notification;
    }

    public void setNotification(ArrayList<Notification> notification) {
        this.notification = notification;
    }

    @JsonProperty("messages")
    public ArrayList<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @JsonProperty("studymate_request")
    public ArrayList<StudymateRequest> getStudymateRequest() {
        return this.studymateRequest;
    }

    public void setStudymateRequest(ArrayList<StudymateRequest> studymateRequest) {
        this.studymateRequest = studymateRequest;
    }


    /**
     * For Notes
     */

    private ArrayList<Notes> notes;
    private ArrayList<LessonNotes> lessonNotes;

    @JsonProperty("lesson_notes")
    public ArrayList<LessonNotes> getLessonNotes() {
        return lessonNotes;
    }

    public ResponseHandler setLessonNotes(ArrayList<LessonNotes> lessonNotes) {
        this.lessonNotes = lessonNotes;
        return this;
    }

    @JsonProperty("notes")
    public ArrayList<Notes> getNotes() {
        return notes;
    }

    public ResponseHandler setNotes(ArrayList<Notes> notes) {
        this.notes = notes;
        return this;
    }

    @JsonProperty("group")
    public ArrayList<Group> getGroup() {
        return group;
    }

    public ResponseHandler setGroup(ArrayList<Group> group) {
        this.group = group;
        return this;
    }

    @JsonProperty("question_bank")
    public ArrayList<Questions> getQuestionBanks() {
        return questionBanks;
    }

    public ResponseHandler setQuestionBanks(ArrayList<Questions> questionBanks) {
        this.questionBanks = questionBanks;
        return this;
    }

    @JsonProperty("user")
    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }


    @JsonProperty("feeds")
    public ArrayList<Feeds> getFeeds() {
        return this.feeds;
    }

    public void setFeeds(ArrayList<Feeds> feeds) {
        this.feeds = feeds;
    }

    @JsonProperty("comments")
    public ArrayList<CommentList> getComments() {
        return this.comments;
    }

    public void setComments(ArrayList<CommentList> comments) {
        this.comments = comments;
    }

    @JsonProperty("studymates")
    public ArrayList<Studymates> getStudymates() {
        return this.studymates;
    }

    public void setStudymates(ArrayList<Studymates> studymates) {
        this.studymates = studymates;
    }

    @JsonProperty("exams")
    public ArrayList<Exams> getExams() {
        return this.exams;
    }

    public void setExams(ArrayList<Exams> exams) {
        this.exams = exams;
    }

    @JsonProperty("subjects")
    public ArrayList<Subjects> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(ArrayList<Subjects> subjects) {
        this.subjects = subjects;
    }

    @JsonProperty("classrooms")
    public ArrayList<Classrooms> getClassrooms() {
        return this.classrooms;
    }

    public void setClassrooms(ArrayList<Classrooms> classrooms) {
        this.classrooms = classrooms;
    }

    @JsonProperty("exam_questions")
    public ArrayList<ExamQuestions> getExamQuestions() {
        return this.examQuestions;
    }

    public void setExamQuestions(ArrayList<ExamQuestions> examQuestions) {
        this.examQuestions = examQuestions;
    }

    @JsonProperty("exam_submission")
    public ArrayList<ExamSubmission> getExamSubmission() {
        return this.examSubmission;
    }

    public void setExamSubmission(ArrayList<ExamSubmission> examSubmission) {
        this.examSubmission = examSubmission;
    }

    @JsonProperty("exam_evaluation")
    public ArrayList<ExamEvaluation> getExamEvaluation() {
        return this.examEvaluation;
    }

    public void setExamEvaluation(ArrayList<ExamEvaluation> examEvaluation) {
        this.examEvaluation = examEvaluation;
    }

    @JsonProperty("topics")
    public ArrayList<Topics> getTopics() {
        return this.topics;
    }

    public void setTopics(ArrayList<Topics> topics) {
        this.topics = topics;
    }

    @JsonProperty("exam")
    public ArrayList<CreateExam> getCreateExam() {
        return this.createExam;
    }

    public void setCreateExam(ArrayList<CreateExam> createExam) {
        this.createExam = createExam;
    }


    @JsonProperty("countries")
    public ArrayList<Countries> getCountries() {
        return this.countries;
    }

    public void setCountries(ArrayList<Countries> countries) {
        this.countries = countries;
    }

    @JsonProperty("states")
    public ArrayList<States> getStates() {
        return this.states;
    }

    public void setStates(ArrayList<States> states) {
        this.states = states;
    }

    @JsonProperty("cities")
    public ArrayList<Cities> getCities() {
        return this.cities;
    }

    public void setCities(ArrayList<Cities> cities) {
        this.cities = cities;
    }

    @JsonProperty("feed")
    public ArrayList<Feed> getFeed() {
        return this.feed;
    }

    public void setFeed(ArrayList<Feed> feed) {
        this.feed = feed;
    }

    @JsonProperty("courses")
    public ArrayList<Courses> getCourses() {
        return this.courses;
    }

    public void setCourses(ArrayList<Courses> courses) {
        this.courses = courses;
    }

    @JsonProperty("questions")
    public ArrayList<Questions> getQuestions() {
        return this.questions;
    }

    public void setQuestions(ArrayList<Questions> questions) {
        this.questions = questions;
    }


    @JsonProperty("tags")
    public ArrayList<HashTags> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<HashTags> tags) {
        this.tags = tags;
    }


    @JsonProperty("question")
    public ArrayList<Question> getQuestion() {
        return this.question;
    }

    public void setQuestion(ArrayList<Question> question) {
        this.question = question;
    }


    FileUploadResponse fileUploadResponse;

    @JsonProperty("upload_question")
    public FileUploadResponse getFileUploadResponse() {
        return fileUploadResponse;
    }

    public ResponseHandler setFileUploadResponse(FileUploadResponse fileUploadResponse) {
        this.fileUploadResponse = fileUploadResponse;
        return this;
    }

    @JsonProperty("feed_images")
    public ArrayList<Feeds> getFeedImages() {
        return feedImages;
    }

    public void setFeedImages(ArrayList<Feeds> feedImages) {
        this.feedImages = feedImages;
    }

    @JsonProperty("user_images")
    public UserImages getUserImages() {
        return this.userImages;
    }

    public void setUserImages(UserImages userImages) {
        this.userImages = userImages;
    }


    @JsonProperty("token")
    public ArrayList<Token> getToken() {
        return this.token;
    }

    public void setToken(ArrayList<Token> token) {
        this.token = token;
    }

    @JsonProperty("admin_config")
    public ArrayList<AdminConfig> getAdminConfig() {
        return this.adminConfig;
    }

    public void setAdminConfig(ArrayList<AdminConfig> adminConfig) {
        this.adminConfig = adminConfig;
    }

}

