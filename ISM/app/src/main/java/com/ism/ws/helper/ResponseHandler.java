package com.ism.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.ws.model.Badges;
import com.ism.ws.model.Books;
import com.ism.ws.model.City;
import com.ism.ws.model.Comment;
import com.ism.ws.model.Country;
import com.ism.ws.model.Feeds;
import com.ism.ws.model.Message;
import com.ism.ws.model.Movies;
import com.ism.ws.model.Notice;
import com.ism.ws.model.Notification;
import com.ism.ws.model.Pastime;
import com.ism.ws.model.RoleModel;
import com.ism.ws.model.SettingPreferences;
import com.ism.ws.model.State;
import com.ism.ws.model.StudymateRequest;
import com.ism.ws.model.TutorialGroup;
import com.ism.ws.model.User;
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
	private ArrayList<Wallet> wallet;

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

    @JsonProperty("badges")
    public ArrayList<Badges> getBadges() {
        return this.badges;
    }

    public void setBadges(ArrayList<Badges> badges) {
        this.badges = badges;
    }

    public void setPreference(ArrayList<SettingPreferences> preference) {
        this.preference = preference;
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

}
