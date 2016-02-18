package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - subject information that each teacher has assigned with.
 * Relationship with {@link ROClassrooms}
 * Relationship with {@link ROUser}
 * Relationship with {@link ROSubjects}
 * Relationship with {@link ROSchool}
 */
public class ROTeacherSubjectInfo extends RealmObject {

    @PrimaryKey
    private  int teacherSubjectInfoId;
    private String className;
    private ROUser roUser;
    private ROSubjects roSubjects;
    private ROSchool roSchool;
    private ROClassrooms roClassrooms;
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

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROSubjects getRoSubjects() {
        return roSubjects;
    }

    public void setRoSubjects(ROSubjects roSubjects) {
        this.roSubjects = roSubjects;
    }

    public ROSchool getRoSchool() {
        return roSchool;
    }

    public void setRoSchool(ROSchool roSchool) {
        this.roSchool = roSchool;
    }

    public ROClassrooms getRoClassrooms() {
        return roClassrooms;
    }

    public void setRoClassrooms(ROClassrooms roClassrooms) {
        this.roClassrooms = roClassrooms;
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
