package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of classroom assignment.
 * Relationship with {@link ROAssignments}
 * Relationship with {@link ROSchoolClassroom}
 */
public class ROClassroomAssignment extends RealmObject {

    @PrimaryKey
    private  int classroomAssignmentId;
    private Date createdDate;
    private Date modifiedDate;
    private ROAssignments roAssignments;
    private ROSchoolClassroom roSchoolClassroom;

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

    public ROAssignments getRoAssignments() {
        return roAssignments;
    }

    public void setRoAssignments(ROAssignments roAssignments) {
        this.roAssignments = roAssignments;
    }

    public ROSchoolClassroom getRoSchoolClassroom() {
        return roSchoolClassroom;
    }

    public void setRoSchoolClassroom(ROSchoolClassroom roSchoolClassroom) {
        this.roSchoolClassroom = roSchoolClassroom;
    }
}
