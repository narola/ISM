package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class for detail of courses for each school.
 * Relationship with {@link School},{@link Courses}
 */
public class SchoolCourse extends RealmObject {

    @PrimaryKey
    private  int schoolCourseId;
    private School school;
    private Courses course;
    private Date createdDate;
    private Date modifiedDate;

    public int getSchoolCourseId() {
        return schoolCourseId;
    }

    public void setSchoolCourseId(int schoolCourseId) {
        this.schoolCourseId = schoolCourseId;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
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
