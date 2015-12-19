package com.ism.author.ws.helper;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.author.ws.model.AdminConfig;
import com.ism.author.ws.model.AuthorBook;
import com.ism.author.ws.model.Badges;
import com.ism.author.ws.model.Books;
import com.ism.author.ws.model.Cities;
import com.ism.author.ws.model.Classrooms;
import com.ism.author.ws.model.CommentList;
import com.ism.author.ws.model.Countries;
import com.ism.author.ws.model.Courses;
import com.ism.author.ws.model.CreateExam;
import com.ism.author.ws.model.ExamEvaluation;
import com.ism.author.ws.model.ExamQuestions;
import com.ism.author.ws.model.ExamSubmission;
import com.ism.author.ws.model.Exams;
import com.ism.author.ws.model.Feed;
import com.ism.author.ws.model.Feeds;
import com.ism.author.ws.model.FileUploadResponse;
import com.ism.author.ws.model.HashTags;
import com.ism.author.ws.model.Message;
import com.ism.author.ws.model.Notification;
import com.ism.author.ws.model.Question;
import com.ism.author.ws.model.Questions;
import com.ism.author.ws.model.States;
import com.ism.author.ws.model.StudymateRequest;
import com.ism.author.ws.model.Studymates;
import com.ism.author.ws.model.Subjects;
import com.ism.author.ws.model.Token;
import com.ism.author.ws.model.Topics;
import com.ism.author.ws.model.TrendingQuestion;
import com.ism.author.ws.model.User;
import com.ism.author.ws.model.UserImages;

import java.util.ArrayList;


/**
 * Created by c166 on 23/10/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHandler {
    private String TAG = ResponseHandler.class.getSimpleName();

    private String message;
    private String status;

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String DUPLICATE_ENTRY = "Duplicate entry";

    private ArrayList<Badges> badges;
    private ArrayList<Notification> notification;
    private ArrayList<StudymateRequest> studymateRequest;
    private ArrayList<Message> messages;
    private ArrayList<Books> books;
    private ArrayList<Feeds> feedImages;
    private ArrayList<TrendingQuestion> trendingQuestions;
    private ArrayList<User> highScorers;

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*this is the new code for the response handler*/

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
    private ArrayList<User> user;
    private ArrayList<Feed> feed;
    private ArrayList<Courses> courses;
    private ArrayList<Questions> questions;
    private ArrayList<Questions> questionBank;
    private ArrayList<Question> question;
    private ArrayList<HashTags> tags;
    private ArrayList<AuthorBook> authorBook;
    private ArrayList<Token> token;
    private ArrayList<AdminConfig> adminConfig;
    private UserImages userImages;
    FileUploadResponse fileUploadResponse;

    @JsonProperty("high_scorers")
    public ArrayList<User> getHighScorers() {
        return highScorers;
    }

    public void setHighScorers(ArrayList<User> highScorers) {
        this.highScorers = highScorers;
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

    @JsonProperty("user")
    public ArrayList<User> getUser() {
        return this.user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
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


    @JsonProperty("question_bank")
    public ArrayList<Questions> getQuestionBank() {
        return this.questionBank;
    }

    public void setQuestionBank(ArrayList<Questions> questionBank) {
        this.questionBank = questionBank;
    }

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

    @JsonProperty("question")
    public ArrayList<Question> getQuestion() {
        return this.question;
    }

    public void setQuestion(ArrayList<Question> question) {
        this.question = question;
    }

    @JsonProperty("tags")
    public ArrayList<HashTags> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<HashTags> tags) {
        this.tags = tags;
    }

    @JsonProperty("books")
    public ArrayList<Books> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Books> books) {
        this.books = books;
    }

    @JsonProperty("author_book")
    public ArrayList<AuthorBook> getAuthorBook() {
        return this.authorBook;
    }

    public void setAuthorBook(ArrayList<AuthorBook> authorBook) {
        this.authorBook = authorBook;
    }

    @JsonProperty("trending_question")
    public ArrayList<TrendingQuestion> getTrendingQuestions() {
        return this.trendingQuestions;
    }

    public void setTrendingQuestions(ArrayList<TrendingQuestion> trendingQuestions) {
        this.trendingQuestions = trendingQuestions;
    }

    @JsonProperty("feed_images")
    public ArrayList<Feeds> getFeedImages() {
        return feedImages;
    }

    public void setFeedImages(ArrayList<Feeds> feedImages) {
        this.feedImages = feedImages;
    }

    @JsonProperty("upload_question")
    public FileUploadResponse getFileUploadResponse() {
        return fileUploadResponse;
    }

    public ResponseHandler setFileUploadResponse(FileUploadResponse fileUploadResponse) {
        this.fileUploadResponse = fileUploadResponse;
        return this;
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

    @JsonProperty("user_images")
    public UserImages getUserImages() {
        return this.userImages;
    }

    public void setUserImages(UserImages userImages) {
        this.userImages = userImages;
    }


}

