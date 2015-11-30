package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of classrooms for each school .
 * Relationship with {@link Classrooms},{@link School}
 */
public class SchoolClassroom extends RealmObject {

    @PrimaryKey
    private  int schoolClassroomId;
    private String classroomName;
    private Classrooms classroom;
    private School school;
    private Date createdDate;
    private Date modifiedDate;

    public int getSchoolClassroomId() {
        return schoolClassroomId;
    }

    public void setSchoolClassroomId(int schoolClassroomId) {
        this.schoolClassroomId = schoolClassroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Classrooms getClassroom() {
        return classroom;
    }

    public void setClassroom(Classrooms classroom) {
        this.classroom = classroom;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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
