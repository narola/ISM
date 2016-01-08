package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of submission for given Assignment.
 * Relationship with {@link Assignments}
 * Relationship with {@link User}
 */
public class AssignmentSubmission extends RealmObject {

    @PrimaryKey
    private int assignmentSubmissionId;
    private User user;
    private Assignments assignment;
    private int assignmentStatus;
    private Date createdDate;
    private Date modifiedDate;

    public int getAssignmentSubmissionId() {
        return assignmentSubmissionId;
    }

    public void setAssignmentSubmissionId(int assignmentSubmissionId) {
        this.assignmentSubmissionId = assignmentSubmissionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Assignments getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignments assignment) {
        this.assignment = assignment;
    }

    public int getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(int assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
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

