package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - information about failed student.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROClassrooms}
 */
public class ROFailedStudent extends RealmObject{

    @PrimaryKey
    private  int failedStudentId;
    private String academicYear;
    private ROUser roUser;
    private ROClassrooms roClassrooms;
    private int failedCount;
    private Date createdDate;
    private Date modifiedDate;

    public int getFailedStudentId() {
        return failedStudentId;
    }

    public void setFailedStudentId(int failedStudentId) {
        this.failedStudentId = failedStudentId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROClassrooms getRoClassrooms() {
        return roClassrooms;
    }

    public void setRoClassrooms(ROClassrooms roClassrooms) {
        this.roClassrooms = roClassrooms;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
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
