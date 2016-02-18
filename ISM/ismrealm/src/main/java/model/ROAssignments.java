package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of assignment.
 * Relationship with {@link ROClassrooms}
 * Relationship with {@link ROUser}
 * Relationship with {@link ROSubjects}
 * Relationship with {@link ROTopics}
 */
public class ROAssignments extends RealmObject {

    @PrimaryKey
    private int assignmentsId;
    private String description;
    private ROUser assignmentBy;
    private ROClassrooms roClassrooms;
    private ROSubjects roSubjects;
    private ROTopics roTopics;
    private Date submissionDate;
    private Date createdDate;
    private Date modifiedDate;

    public int getAssignmentsId() {
        return assignmentsId;
    }

    public void setAssignmentsId(int assignmentsId) {
        this.assignmentsId = assignmentsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ROUser getAssignmentBy() {
        return assignmentBy;
    }

    public void setAssignmentBy(ROUser assignmentBy) {
        this.assignmentBy = assignmentBy;
    }

    public ROClassrooms getRoClassrooms() {
        return roClassrooms;
    }

    public void setRoClassrooms(ROClassrooms roClassrooms) {
        this.roClassrooms = roClassrooms;
    }

    public ROSubjects getRoSubjects() {
        return roSubjects;
    }

    public void setRoSubjects(ROSubjects roSubjects) {
        this.roSubjects = roSubjects;
    }

    public ROTopics getRoTopics() {
        return roTopics;
    }

    public void setRoTopics(ROTopics roTopics) {
        this.roTopics = roTopics;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
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
