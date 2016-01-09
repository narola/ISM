package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of lectures.
 * Relationship with {@link ROClassrooms}
 * Relationship with {@link ROSubjects}
 * Relationship with {@link ROTeacherProfile}
 * Relationship with {@link ROTopics}
 * Relationship with {@link ROLectureCategory}
 */
public class ROLectures extends RealmObject {
    @PrimaryKey
    private  int lecturesId;
    private String lectureTitle;
    private String description;
    private String videoLink;
    private String audioLink;
    private String chatTranscript;
    private String difficultyLevel;
    private String notes;
    private ROClassrooms roClassrooms;
    private ROSubjects subject;
    private ROTeacherProfile lectureBy;
    private ROTopics topic;
    private ROLectureCategory roLectureCategory;
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

    public ROClassrooms getRoClassrooms() {
        return roClassrooms;
    }

    public void setRoClassrooms(ROClassrooms roClassrooms) {
        this.roClassrooms = roClassrooms;
    }

    public ROSubjects getSubject() {
        return subject;
    }

    public void setSubject(ROSubjects subject) {
        this.subject = subject;
    }

    public ROTeacherProfile getLectureBy() {
        return lectureBy;
    }

    public void setLectureBy(ROTeacherProfile lectureBy) {
        this.lectureBy = lectureBy;
    }

    public ROTopics getTopic() {
        return topic;
    }

    public void setTopic(ROTopics topic) {
        this.topic = topic;
    }

    public ROLectureCategory getRoLectureCategory() {
        return roLectureCategory;
    }

    public void setRoLectureCategory(ROLectureCategory roLectureCategory) {
        this.roLectureCategory = roLectureCategory;
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
