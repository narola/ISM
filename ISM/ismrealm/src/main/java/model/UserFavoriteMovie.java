package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite books.
 * Relationship with {@link User}
 * Relationship with {@link Movies}
 */
public class UserFavoriteMovie extends RealmObject {

    @PrimaryKey
    private  int userFavoriteMovieId;
    private User user;
    private Movies movie;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteMovieId() {
        return userFavoriteMovieId;
    }

    public void setUserFavoriteMovieId(int userFavoriteMovieId) {
        this.userFavoriteMovieId = userFavoriteMovieId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
