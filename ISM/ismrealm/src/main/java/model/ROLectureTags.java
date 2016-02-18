package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class define tags with lectures.
 * Relationship with {@link ROTags}
 * Relationship with {@link ROLectureCategory}
 */
public class ROLectureTags extends RealmObject {

    @PrimaryKey
    private  int lectureTagsId;
   private ROTags tag;
    private ROLectures lecture;
    private Date createdDate;
    private Date modifiedDate;

    public int getLectureTagsId() {
        return lectureTagsId;
    }

    public void setLectureTagsId(int lectureTagsId) {
        this.lectureTagsId = lectureTagsId;
    }

    public ROTags getTag() {
        return tag;
    }

    public void setTag(ROTags tag) {
        this.tag = tag;
    }

    public ROLectures getLecture() {
        return lecture;
    }

    public void setLecture(ROLectures lecture) {
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
