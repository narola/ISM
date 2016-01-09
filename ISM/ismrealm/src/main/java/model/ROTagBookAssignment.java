package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - tag info for book assignment.
 * Relationship with {@link ROBookAssignment}
 * Relationship with {@link ROTags}
 */
public class ROTagBookAssignment extends RealmObject {

    @PrimaryKey
    private  int tagBookAssignmentId;
    private ROBookAssignment roBookAssignment;
    private ROTags tag;
    private Date createdDate;
    private Date modifiedDate;

    public int getTagBookAssignmentId() {
        return tagBookAssignmentId;
    }

    public void setTagBookAssignmentId(int tagBookAssignmentId) {
        this.tagBookAssignmentId = tagBookAssignmentId;
    }

    public ROBookAssignment getRoBookAssignment() {
        return roBookAssignment;
    }

    public void setRoBookAssignment(ROBookAssignment roBookAssignment) {
        this.roBookAssignment = roBookAssignment;
    }

    public ROTags getTag() {
        return tag;
    }

    public void setTag(ROTags tag) {
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
