package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite books.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROBooks}
 */
public class ROUserFavoriteBook extends RealmObject {

    @PrimaryKey
    private  int userFavoriteBookId;
    private ROUser roUser;
    private ROBooks book;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteBookId() {
        return userFavoriteBookId;
    }

    public void setUserFavoriteBookId(int userFavoriteBookId) {
        this.userFavoriteBookId = userFavoriteBookId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROBooks getBook() {
        return book;
    }

    public void setBook(ROBooks book) {
        this.book = book;
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
