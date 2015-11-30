package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class define tags with lectures.
 * Relationship with {@link Tags}
 * Relationship with {@link LectureCategory}
 */
public class LectureTags extends RealmObject {

    @PrimaryKey
    private  int lectureTagsId;
   private Tags tag;
    private Lectures lecture;
    private Date createdDate;
    private Date modifiedDate;

    public int getLectureTagsId() {
        return lectureTagsId;
    }

    public void setLectureTagsId(int lectureTagsId) {
        this.lectureTagsId = lectureTagsId;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public Lectures getLecture() {
        return lecture;
    }

    public void setLecture(Lectures lecture) {
        this.lecture = lecture;
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
