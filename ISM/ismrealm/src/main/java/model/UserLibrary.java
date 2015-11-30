package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of books for user/author.
 * Relationship with {@link User}
 * Relationship with {@link Books}
 */
public class UserLibrary extends RealmObject {

    @PrimaryKey
    private  int userLibraryId;
    private User user;
    private Books book;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserLibraryId() {
        return userLibraryId;
    }

    public void setUserLibraryId(int userLibraryId) {
        this.userLibraryId = userLibraryId;
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
