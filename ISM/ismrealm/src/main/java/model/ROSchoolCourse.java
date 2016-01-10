package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class for detail of courses for each school.
 * Relationship with {@link ROSchool},{@link ROCourses}
 */
public class ROSchoolCourse extends RealmObject {

    @PrimaryKey
    private  int schoolCourseId;
    private ROSchool roSchool;
    private ROCourses course;
    private Date createdDate;
    private Date modifiedDate;

    public int getSchoolCourseId() {
        return schoolCourseId;
    }

    public void setSchoolCourseId(int schoolCourseId) {
        this.schoolCourseId = schoolCourseId;
    }

    public ROSchool getRoSchool() {
        return roSchool;
    }

    public void setRoSchool(ROSchool roSchool) {
        this.roSchool = roSchool;
    }

    public ROCourses getCourse() {
        return course;
    }

    public void setCourse(ROCourses course) {
        this.course = course;
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
