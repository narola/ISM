package com.ism.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.ws.model.AdminConfig;
import com.ism.ws.model.AllBooks;
import com.ism.ws.model.Assignment;
import com.ism.ws.model.AuthorBookAssignment;
import com.ism.ws.model.AuthorData;
import com.ism.ws.model.Badges;
import com.ism.ws.model.BlockedUsers;
import com.ism.ws.model.BookData;
import com.ism.ws.model.Books;
import com.ism.ws.model.City;
import com.ism.ws.model.Comment;
import com.ism.ws.model.Country;
import com.ism.ws.model.Feeds;
import com.ism.ws.model.FridayExam;
import com.ism.ws.model.FridayExamStatus;
import com.ism.ws.model.GroupDiscussionData;
import com.ism.ws.model.Message;
import com.ism.ws.model.Movies;
import com.ism.ws.model.Notes;
import com.ism.ws.model.Notice;
import com.ism.ws.model.Notification;
import com.ism.ws.model.Pastime;
import com.ism.ws.model.QuestionForFriday;
import com.ism.ws.model.RoleModel;
import com.ism.ws.model.SettingPreferences;
import com.ism.ws.model.State;
import com.ism.ws.model.StudymateRequest;
import com.ism.ws.model.Token;
import com.ism.ws.model.TutorialGroup;
import com.ism.ws.model.TutorialGroupProfile;
import com.ism.ws.model.User;
import com.ism.ws.model.UserActivitiy;
import com.ism.ws.model.UserPreferences;
import com.ism.ws.model.Wallet;

import java.util.ArrayList;

/**
 * Created by c161 on 19/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHandler {

    private String message;
    private String status;
    private ArrayList<State> states;
    private ArrayList<Country> countries;
    private ArrayList<City> cities;
    private ArrayList<User> user;
    private ArrayList<User> highScorers;
    private ArrayList<User> studymates;
    private ArrayList<User> recommendedStudymates;
    private ArrayList<TutorialGroup> tutorialGroup;
    private ArrayList<Comment> comments;
    private ArrayList<Notice> notices;
    private ArrayList<Notification> notification;
    private ArrayList<Message> messages;
    private ArrayList<StudymateRequest> studymateRequest;
    private ArrayList<SettingPreferences> preference;
    private ArrayList<UserPreferences> userPreference;
    private ArrayList<Books> books;
    private ArrayList<Badges> badges;
    private ArrayList<RoleModel> roleModel;
    private ArrayList<Movies> movies;
    private ArrayList<Pastime> pastime;
    private ArrayList<Feeds> feeds;
    private ArrayList<Feeds> feed;
    private ArrayList<Feeds> feedImages;
    private ArrayList<Wallet> wallet;
    private ArrayList<BlockedUsers> blockedUsers;
    private ArrayList<UserActivitiy> userActivities;
    private ArrayList<AdminConfig> adminConfig;
    private ArrayList<Token> token;
    private ArrayList<Comment> comment;
    private ArrayList<GroupDiscussionData> groupDiscussionData;
    private ArrayList<TutorialGroupProfile> tutorialGroupProfile;
    private ArrayList<Notes> notes;
    private ArrayList<FridayExamStatus> fridayExamStatus;
    private ArrayList<QuestionForFriday> questionForFriday;
    private ArrayList<AllBooks> allBooks;
    private ArrayList<AuthorBookAssignment> authorBookAssignment;
    private ArrayList<Assignment> assignment;
    private ArrayList<FridayExam> fridayExam;
    private ArrayList<BookData> authorBook;
    private ArrayList<AuthorData> author;

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

    @JsonProperty("states")
    public ArrayList<State> getStates() {
        return this.states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    @JsonProperty("countries")
    public ArrayList<Country> getCountries() {
        return this.countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    @JsonProperty("cities")
    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    @JsonProperty("user")
    public ArrayList<User> getUser() {
        return this.user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }

    @JsonProperty("tutorial_group")
    public ArrayList<TutorialGroup> getTutorialGroup() {
        return this.tutorialGroup;
    }

    public void setTutorialGroup(ArrayList<TutorialGroup> tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    @JsonProperty("books")
    public ArrayList<Books> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Books> books) {
        this.books = books;
    }

    @JsonProperty("preference")
    public ArrayList<SettingPreferences> getPreference() {
        return preference;
    }

    public void setPreference(ArrayList<SettingPreferences> preference) {
        this.preference = preference;
    }

    @JsonProperty("badges")
    public ArrayList<Badges> getBadges() {
        return this.badges;
    }

    public void setBadges(ArrayList<Badges> badges) {
        this.badges = badges;
    }

    @JsonProperty("user_preference")
    public ArrayList<UserPreferences> getUserPreference() {
        return this.userPreference;
    }

    public void setUserPreference(ArrayList<UserPreferences> userPreference) {
        this.userPreference = userPreference;
    }

    @JsonProperty("role_model")
    public ArrayList<RoleModel> getRoleModel() {
        return this.roleModel;
    }

    public void setRoleModel(ArrayList<RoleModel> roleModel) {
        this.roleModel = roleModel;
    }

    @JsonProperty("movies")
    public ArrayList<Movies> getMovies() {
        return this.movies;
    }

    public void setMovies(ArrayList<Movies> movies) {
        this.movies = movies;
    }

    @JsonProperty("pastime")
    public ArrayList<Pastime> getPastime() {
        return this.pastime;
    }

    public void setPastime(ArrayList<Pastime> pastime) {
        this.pastime = pastime;
    }

    @JsonProperty("feeds")
    public ArrayList<Feeds> getFeeds() {
        return this.feeds;
    }

    public void setFeeds(ArrayList<Feeds> feeds) {
        this.feeds = feeds;
    }

    @JsonProperty("comments")
    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @JsonProperty("comment")
    public ArrayList<Comment> getComment() {
        return this.comment;
    }

    public void setComment(ArrayList<Comment> comment) {
        this.comment = comment;
    }

    @JsonProperty("studymates")
    public ArrayList<User> getStudymates() {
        return this.studymates;
    }

    public void setStudymates(ArrayList<User> studymates) {
        this.studymates = studymates;
    }

    @JsonProperty("notices")
    public ArrayList<Notice> getNotices() {
        return this.notices;
    }

    public void setNotices(ArrayList<Notice> notices) {
        this.notices = notices;
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

    @JsonProperty("high_scorers")
    public ArrayList<User> getHighScorers() {
        return highScorers;
    }

    public void setHighScorers(ArrayList<User> highScorers) {
        this.highScorers = highScorers;
    }

    @JsonProperty("wallet_summary")
    public ArrayList<Wallet> getWallet() {
        return this.wallet;
    }

    public void setWallet(ArrayList<Wallet> wallet) {
        this.wallet = wallet;
    }

    @JsonProperty("blocked_users")
    public ArrayList<BlockedUsers> getBlockedUsers() {
        return this.blockedUsers;
    }

    public void setBlockedUsers(ArrayList<BlockedUsers> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    @JsonProperty("suggested_studymates")
    public ArrayList<User> getRecommendedStudymates() {
        return recommendedStudymates;
    }

    public void setRecommendedStudymates(ArrayList<User> recommendedStudymates) {
        this.recommendedStudymates = recommendedStudymates;
    }

    @JsonProperty("user_activities")
    public ArrayList<UserActivitiy> getUserActivities() {
        return this.userActivities;
    }

    public void setUserActivities(ArrayList<UserActivitiy> userActivities) {
        this.userActivities = userActivities;
    }

    @JsonProperty("admin_config")
    public ArrayList<AdminConfig> getAdminConfig() {
        return this.adminConfig;
    }

    public void setAdminConfig(ArrayList<AdminConfig> adminConfig) {
        this.adminConfig = adminConfig;
    }

    @JsonProperty("feed")
    public ArrayList<Feeds> getFeed() {
        return feed;
    }

    public void setFeed(ArrayList<Feeds> feed) {
        this.feed = feed;
    }

    @JsonProperty("feed_images")
    public ArrayList<Feeds> getFeedImages() {
        return feedImages;
    }

    public void setFeedImages(ArrayList<Feeds> feedImages) {
        this.feedImages = feedImages;
    }

    @JsonProperty("token")
    public ArrayList<Token> getToken() {
        return this.token;
    }

    public void setToken(ArrayList<Token> token) {
        this.token = token;
    }

    @JsonProperty("group_history")
    public ArrayList<GroupDiscussionData> getGroupDiscussionData() {
        return this.groupDiscussionData;
    }

    public void setGroupDiscussionData(ArrayList<GroupDiscussionData> groupDiscussionData) {
        this.groupDiscussionData = groupDiscussionData;
    }

    @JsonProperty("tutorial_group_profile")
    public ArrayList<TutorialGroupProfile> getTutorialGroupProfile() {
        return this.tutorialGroupProfile;
    }

    public void setTutorialGroupProfile(ArrayList<TutorialGroupProfile> tutorialGroupProfile) {
        this.tutorialGroupProfile = tutorialGroupProfile;
    }

    @JsonProperty("notes")
    public ArrayList<Notes> getNotes() {
        return this.notes;
    }

    public void setNotes(ArrayList<Notes> notes) {
        this.notes = notes;
    }

    @JsonProperty("friday_exam_status")
    public ArrayList<FridayExamStatus> getFridayExamStatus() {
        return this.fridayExamStatus;
    }

    public void setFridayExamStatus(ArrayList<FridayExamStatus> fridayExamStatus) {
        this.fridayExamStatus = fridayExamStatus;
    }

    @JsonProperty("question_for_friday")
    public ArrayList<QuestionForFriday> getQuestionForFriday() {
        return this.questionForFriday;
    }

    public void setQuestionForFriday(ArrayList<QuestionForFriday> questionForFriday) {
        this.questionForFriday = questionForFriday;
    }

    @JsonProperty("all_books")
    public ArrayList<AllBooks> getAllBooks() {
        return allBooks;
    }

    public ResponseHandler setAllBooks(ArrayList<AllBooks> allBooks) {
        this.allBooks = allBooks;
        return this;
    }

    @JsonProperty("book_assignments")
    public ArrayList<AuthorBookAssignment> getAuthorBookAssignment() {
        return this.authorBookAssignment;
    }

    public void setAuthorBookAssignment(ArrayList<AuthorBookAssignment> authorBookAssignment) {
        this.authorBookAssignment = authorBookAssignment;
    }

    public void setAssignment(ArrayList<Assignment> assignment) {
        this.assignment = assignment;
    }

    @JsonProperty("assignment")
    public ArrayList<Assignment> getAssignment() {
        return this.assignment;
    }

    @JsonProperty("exam_questions")
    public ArrayList<FridayExam> getFridayExam() {
        return this.fridayExam;
    }

    public void setFridayExam(ArrayList<FridayExam> fridayExam) {
        this.fridayExam = fridayExam;
    }

    @JsonProperty("author_book")
    public ArrayList<BookData> getAuthorBooks() {
        return this.authorBook;
    }

    public void setAuthorBooks(ArrayList<BookData> authorBook) {
        this.authorBook = authorBook;
    }

    @JsonProperty("author")
    public ArrayList<AuthorData> getAuthor() {
        return this.author;
    }

    public void setAuthor(ArrayList<AuthorData> author) {
        this.author = author;
    }
}
