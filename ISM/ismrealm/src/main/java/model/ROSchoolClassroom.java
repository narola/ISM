package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of classrooms for each school .
 * Relationship with {@link ROClassrooms},{@link ROSchool}
 */
public class ROSchoolClassroom extends RealmObject {

    @PrimaryKey
    private  int schoolClassroomId;
    private String classroomName;
    private ROClassrooms classroom;
    private ROSchool roSchool;
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

    public ROClassrooms getClassroom() {
        return classroom;
    }

    public void setClassroom(ROClassrooms classroom) {
        this.classroom = classroom;
    }

    public ROSchool getRoSchool() {
        return roSchool;
    }

    public void setRoSchool(ROSchool roSchool) {
        this.roSchool = roSchool;
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
