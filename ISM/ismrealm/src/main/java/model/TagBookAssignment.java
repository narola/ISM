package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - tag info for book assignment.
 * Relationship with {@link BookAssignment}
 * Relationship with {@link Tags}
 */
public class TagBookAssignment extends RealmObject {

    @PrimaryKey
    private  int tagBookAssignmentId;
    private BookAssignment bookAssignment;
    private Tags tag;
    private Date createdDate;
    private Date modifiedDate;

    public int getTagBookAssignmentId() {
        return tagBookAssignmentId;
    }

    public void setTagBookAssignmentId(int tagBookAssignmentId) {
        this.tagBookAssignmentId = tagBookAssignmentId;
    }

    public BookAssignment getBookAssignment() {
        return bookAssignment;
    }

    public void setBookAssignment(BookAssignment bookAssignment) {
        this.bookAssignment = bookAssignment;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
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
