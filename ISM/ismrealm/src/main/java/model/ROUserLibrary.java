package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of books for user/author.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROBooks}
 */
public class ROUserLibrary extends RealmObject {

    @PrimaryKey
    private  int userLibraryId;
    private ROUser roUser;
    private ROBooks roBooks;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserLibraryId() {
        return userLibraryId;
    }

    public void setUserLibraryId(int userLibraryId) {
        this.userLibraryId = userLibraryId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROBooks getRoBooks() {
        return roBooks;
    }

    public void setRoBooks(ROBooks roBooks) {
        this.roBooks = roBooks;
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
