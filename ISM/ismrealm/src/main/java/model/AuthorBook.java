package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - books written by author.
 * Relationship with {@link User},{@link Books}
 */
public class AuthorBook extends RealmObject {

    @PrimaryKey
    private int authorBookId;
    private User user;
    private Books book;
    private Date createdDate;
    private Date modifiedDate;

    public int getAuthorBookId() {
        return authorBookId;
    }

    public void setAuthorBookId(int authorBookId) {
        this.authorBookId = authorBookId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
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
