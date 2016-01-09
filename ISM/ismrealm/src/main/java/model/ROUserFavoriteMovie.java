package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite books.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROMovies}
 */
public class ROUserFavoriteMovie extends RealmObject {

    @PrimaryKey
    private  int userFavoriteMovieId;
    private ROUser roUser;
    private ROMovies movie;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteMovieId() {
        return userFavoriteMovieId;
    }

    public void setUserFavoriteMovieId(int userFavoriteMovieId) {
        this.userFavoriteMovieId = userFavoriteMovieId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROMovies getMovie() {
        return movie;
    }

    public void setMovie(ROMovies movie) {
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
