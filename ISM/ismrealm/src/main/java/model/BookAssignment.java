package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - details of assignment from books.
 * Relationship with {@link User}
 * Relationship with {@link Books}
 */
public class BookAssignment extends RealmObject {

    @PrimaryKey
    private  int bookAssignment;
    private String assignmentName;
    private String assignmentType;
    private User addedBy;
    private Books book;
    private Date createdDate;
    private Date modifiedDate;

    public int getBookAssignment() {
        return bookAssignment;
    }

    public void setBookAssignment(int bookAssignment) {
        this.bookAssignment = bookAssignment;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
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
