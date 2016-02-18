package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - number of student assign to each teacher.
 * Relationship with {@link ROTeacherProfile}
 * Relationship with {@link ROStudentProfile}
 */
public class ROStudentTeacher extends RealmObject {

    @PrimaryKey
    private  int studentTeacherId;
    private ROStudentProfile student;
    private ROTeacherProfile teacher;
    private boolean isOnline;
    private Date createdDate;
    private Date modifiedDate;

    public int getStudentTeacherId() {
        return studentTeacherId;
    }

    public void setStudentTeacherId(int studentTeacherId) {
        this.studentTeacherId = studentTeacherId;
    }

    public ROStudentProfile getStudent() {
        return student;
    }

    public void setStudent(ROStudentProfile student) {
        this.student = student;
    }

    public ROTeacherProfile getTeacher() {
        return teacher;
    }

    public void setTeacher(ROTeacherProfile teacher) {
        this.teacher = teacher;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
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
