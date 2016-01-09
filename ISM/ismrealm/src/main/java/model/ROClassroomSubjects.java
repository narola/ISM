package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of classroom subjects.
 * Relationship with {@link ROSubjects}
 * Relationship with {@link ROClassrooms}
 */
public class ROClassroomSubjects extends RealmObject {
    @PrimaryKey
    private  int classroomSubjectsId;
    private Date createdDate;
    private Date modifiedDate;
    private ROSubjects roSubjects;
    private ROClassrooms roClassrooms;

    public int getClassroomSubjectsId() {
        return classroomSubjectsId;
    }

    public void setClassroomSubjectsId(int classroomSubjectsId) {
        this.classroomSubjectsId = classroomSubjectsId;
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

    public ROSubjects getRoSubjects() {
        return roSubjects;
    }

    public void setRoSubjects(ROSubjects roSubjects) {
        this.roSubjects = roSubjects;
    }

    public ROClassrooms getRoClassrooms() {
        return roClassrooms;
    }

    public void setRoClassrooms(ROClassrooms roClassrooms) {
        this.roClassrooms = roClassrooms;
    }
}
