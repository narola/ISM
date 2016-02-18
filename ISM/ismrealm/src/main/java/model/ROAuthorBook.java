package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - books written by author.
 * Relationship with {@link ROUser},{@link ROBooks}
 */
public class ROAuthorBook extends RealmObject {

    @PrimaryKey
    private int authorBookId;
    private ROUser roUser;
    private ROBooks roBook;
    private Date createdDate;
    private Date modifiedDate;

    public int getAuthorBookId() {
        return authorBookId;
    }

    public void setAuthorBookId(int authorBookId) {
        this.authorBookId = authorBookId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROBooks getRoBook() {
        return roBook;
    }

    public void setRoBook(ROBooks roBook) {
        this.roBook = roBook;
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
