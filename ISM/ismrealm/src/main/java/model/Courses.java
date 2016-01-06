package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle course detail.
 * Relationship with {@link CourseCategory}
 */
public class Courses extends RealmObject {
    @PrimaryKey
    private  int localCourseId;
    private  int serverCourseId;
    private String courseName;
    private String courseNickName;
    private String courseDetail;
    private String courseType;
    private int courseDuration;
    private int courseDegree;
    private boolean isSemester;
    private Date createdDate;
    private Date modifiedDate;
    private boolean isDelete;
    private  CourseCategory courseCategory;

    public int getLocalCourseId() {
        return localCourseId;
    }

    public void setLocalCourseId(int localCourseId) {
        this.localCourseId = localCourseId;
    }

    public int getServerCourseId() {
        return serverCourseId;
    }

    public void setServerCourseId(int serverCourseId) {
        this.serverCourseId = serverCourseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNickName() {
        return courseNickName;
    }

    public void setCourseNickName(String courseNickName) {
        this.courseNickName = courseNickName;
    }

    public String getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(String courseDetail) {
        this.courseDetail = courseDetail;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    public int getCourseDegree() {
        return courseDegree;
    }

    public void setCourseDegree(int courseDegree) {
        this.courseDegree = courseDegree;
    }

    public boolean isSemester() {
        return isSemester;
    }

    public void setIsSemester(boolean isSemester) {
        this.isSemester = isSemester;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public CourseCategory getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }
}
