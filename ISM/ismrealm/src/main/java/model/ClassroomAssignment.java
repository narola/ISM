package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of classroom assignment.
 * Relationship with {@link Assignments}
 * Relationship with {@link SchoolClassroom}
 */
public class ClassroomAssignment extends RealmObject {

    @PrimaryKey
    private  int classroomAssignmentId;
    private Date createdDate;
    private Date modifiedDate;
    private Assignments assignments;
    private SchoolClassroom schoolClassroom;

    public int getClassroomAssignmentId() {
        return classroomAssignmentId;
    }

    public void setClassroomAssignmentId(int classroomAssignmentId) {
        this.classroomAssignmentId = classroomAssignmentId;
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

    public Assignments getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignments assignments) {
        this.assignments = assignments;
    }

    public SchoolClassroom getSchoolClassroom() {
        return schoolClassroom;
    }

    public void setSchoolClassroom(SchoolClassroom schoolClassroom) {
        this.schoolClassroom = schoolClassroom;
    }
}
