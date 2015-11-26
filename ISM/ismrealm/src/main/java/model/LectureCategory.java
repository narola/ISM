package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - information about type of category for lectures.
 * Relationship with {@link LectureCategory}
 */
public class LectureCategory extends RealmObject {

    @PrimaryKey
    private  int lectureCategoryId;
    private String categoryName;
    private LectureCategory lectureCategory;
    private Date createdDate;
    private Date modifiedDate;

    public int getLectureCategoryId() {
        return lectureCategoryId;
    }

    public void setLectureCategoryId(int lectureCategoryId) {
        this.lectureCategoryId = lectureCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LectureCategory getLectureCategory() {
        return lectureCategory;
    }

    public void setLectureCategory(LectureCategory lectureCategory) {
        this.lectureCategory = lectureCategory;
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
