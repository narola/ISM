package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of favorite author for users.
 * Relationship with {@link AuthorProfile}
 * Relationship with {@link User}
 */
public class UserFavoriteAuthor extends RealmObject {
    @PrimaryKey
    private  int userFavoriteAuthorId;
    private User user;
    private AuthorProfile author;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteAuthorId() {
        return userFavoriteAuthorId;
    }

    public void setUserFavoriteAuthorId(int userFavoriteAuthorId) {
        this.userFavoriteAuthorId = userFavoriteAuthorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthorProfile getAuthor() {
        return author;
    }

    public void setAuthor(AuthorProfile author) {
        this.author = author;
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
