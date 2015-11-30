package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of lectures.
 * Relationship with {@link Classrooms}
 * Relationship with {@link Subjects}
 * Relationship with {@link TeacherProfile}
 * Relationship with {@link Topics}
 * Relationship with {@link LectureCategory}
 */
public class Lectures extends RealmObject {
    @PrimaryKey
    private  int lecturesId;
    private String lectureTitle;
    private String description;
    private String videoLink;
    private String audioLink;
    private String chatTranscript;
    private String difficultyLevel;
    private String notes;
    private Classrooms classroom;
    private Subjects subject;
    private TeacherProfile lectureBy;
    private Topics topic;
    private LectureCategory lectureCategory;
    private int yearId;
    private int viewCount;
    private Date createdDate;
    private Date modifiedDate;

    public int getLecturesId() {
        return lecturesId;
    }

    public void setLecturesId(int lecturesId) {
        this.lecturesId = lecturesId;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public void setLectureTitle(String lectureTitle) {
        this.lectureTitle = lectureTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public String getChatTranscript() {
        return chatTranscript;
    }

    public void setChatTranscript(String chatTranscript) {
        this.chatTranscript = chatTranscript;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Classrooms getClassroom() {
        return classroom;
    }

    public void setClassroom(Classrooms classroom) {
        this.classroom = classroom;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public TeacherProfile getLectureBy() {
        return lectureBy;
    }

    public void setLectureBy(TeacherProfile lectureBy) {
        this.lectureBy = lectureBy;
    }

    public Topics getTopic() {
        return topic;
    }

    public void setTopic(Topics topic) {
        this.topic = topic;
    }

    public LectureCategory getLectureCategory() {
        return lectureCategory;
    }

    public void setLectureCategory(LectureCategory lectureCategory) {
        this.lectureCategory = lectureCategory;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
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
