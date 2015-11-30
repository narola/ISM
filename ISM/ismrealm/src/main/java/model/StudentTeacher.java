package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - number of student assign to each teacher.
 * Relationship with {@link TeacherProfile}
 * Relationship with {@link StudentProfile}
 */
public class StudentTeacher extends RealmObject {

    @PrimaryKey
    private  int studentTeacherId;
    private StudentProfile student;
    private TeacherProfile teacher;
    private boolean isOnline;
    private Date createdDate;
    private Date modifiedDate;

    public int getStudentTeacherId() {
        return studentTeacherId;
    }

    public void setStudentTeacherId(int studentTeacherId) {
        this.studentTeacherId = studentTeacherId;
    }

    public StudentProfile getStudent() {
        return student;
    }

    public void setStudent(StudentProfile student) {
        this.student = student;
    }

    public TeacherProfile getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherProfile teacher) {
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
