package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of classroom subjects.
 * Relationship with {@link Subjects}
 * Relationship with {@link Classrooms}
 */
public class ClassroomSubjects extends RealmObject {
    @PrimaryKey
    private  int classroomSubjectsId;
    private Date createdDate;
    private Date modifiedDate;
    private Subjects subjects;
    private Classrooms classrooms;

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

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public Classrooms getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Classrooms classrooms) {
        this.classrooms = classrooms;
    }
}
