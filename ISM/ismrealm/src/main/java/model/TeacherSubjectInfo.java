package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - subject information that each teacher has assigned with.
 * Relationship with {@link Classrooms}
 * Relationship with {@link User}
 * Relationship with {@link Subjects}
 * Relationship with {@link School}
 */
public class TeacherSubjectInfo extends RealmObject {

    @PrimaryKey
    private  int teacherSubjectInfoId;
    private String className;
    private User user;
    private Subjects subjects;
    private School school;
    private Classrooms classrooms;
    private Date createdDate;
    private Date modifiedDate;

    public int getTeacherSubjectInfoId() {
        return teacherSubjectInfoId;
    }

    public void setTeacherSubjectInfoId(int teacherSubjectInfoId) {
        this.teacherSubjectInfoId = teacherSubjectInfoId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Classrooms getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Classrooms classrooms) {
        this.classrooms = classrooms;
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
