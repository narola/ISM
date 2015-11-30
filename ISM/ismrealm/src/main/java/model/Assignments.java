package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of assignment.
 * Relationship with {@link Classrooms}
 * Relationship with {@link User}
 * Relationship with {@link Subjects}
 * Relationship with {@link Topics}
 */
public class Assignments extends RealmObject {

    @PrimaryKey
    private  int assignmentsId;
    private String description;
    private User assignmentBy;
    private Classrooms classroom;
    private Subjects subject;
    private Topics topics;
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

    public User getAssignmentBy() {
        return assignmentBy;
    }

    public void setAssignmentBy(User assignmentBy) {
        this.assignmentBy = assignmentBy;
    }

    public Classrooms getClassroom() {
        return classroom;
    }

    public void setClassroom(Classrooms classroom) {
        this.classroom = classroom;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public Topics getTopics() {
        return topics;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
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
