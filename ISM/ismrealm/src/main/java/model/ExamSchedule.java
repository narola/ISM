package model;



import java.sql.Time;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class- detail of schedule for each exam.
 * Relationship with {@link User}
 * Relationship with {@link Exam}
 * Relationship with {@link SchoolClassroom}
 */
public class ExamSchedule extends RealmObject {

    @PrimaryKey
    private  int examScheduleId;
    private User scheduleBy;
    private User examAssesor;
    private Exam exam;
    private SchoolClassroom schoolClassroom;
    private boolean isCompleted;
    private Date startDate;
    private String startTime;
    private Date createdDate;
    private Date modifiedDate;

    public int getExamScheduleId() {
        return examScheduleId;
    }

    public void setExamScheduleId(int examScheduleId) {
        this.examScheduleId = examScheduleId;
    }

    public User getScheduleBy() {
        return scheduleBy;
    }

    public void setScheduleBy(User scheduleBy) {
        this.scheduleBy = scheduleBy;
    }

    public User getExamAssesor() {
        return examAssesor;
    }

    public void setExamAssesor(User examAssesor) {
        this.examAssesor = examAssesor;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public SchoolClassroom getSchoolClassroom() {
        return schoolClassroom;
    }

    public void setSchoolClassroom(SchoolClassroom schoolClassroom) {
        this.schoolClassroom = schoolClassroom;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
