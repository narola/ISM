package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of favorite author for users.
 * Relationship with {@link ROAuthorProfile}
 * Relationship with {@link ROUser}
 */
public class ROUserFavoriteAuthor extends RealmObject {
    @PrimaryKey
    private  int userFavoriteAuthorId;
    private ROUser roUser;
    private ROAuthorProfile author;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteAuthorId() {
        return userFavoriteAuthorId;
    }

    public void setUserFavoriteAuthorId(int userFavoriteAuthorId) {
        this.userFavoriteAuthorId = userFavoriteAuthorId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROAuthorProfile getAuthor() {
        return author;
    }

    public void setAuthor(ROAuthorProfile author) {
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
