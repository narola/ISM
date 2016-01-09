package model;



import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class- detail of schedule for each ROExam.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROExam}
 * Relationship with {@link ROSchoolClassroom}
 */
public class ROExamSchedule extends RealmObject {

    @PrimaryKey
    private  int examScheduleId;
    private ROUser scheduleBy;
    private ROUser examAssesor;
    private ROExam roExam;
    private ROSchoolClassroom roSchoolClassroom;
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

    public ROUser getScheduleBy() {
        return scheduleBy;
    }

    public void setScheduleBy(ROUser scheduleBy) {
        this.scheduleBy = scheduleBy;
    }

    public ROUser getExamAssesor() {
        return examAssesor;
    }

    public void setExamAssesor(ROUser examAssesor) {
        this.examAssesor = examAssesor;
    }

    public ROExam getRoExam() {
        return roExam;
    }

    public void setRoExam(ROExam roExam) {
        this.roExam = roExam;
    }

    public ROSchoolClassroom getRoSchoolClassroom() {
        return roSchoolClassroom;
    }

    public void setRoSchoolClassroom(ROSchoolClassroom roSchoolClassroom) {
        this.roSchoolClassroom = roSchoolClassroom;
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
