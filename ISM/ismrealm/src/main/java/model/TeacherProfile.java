package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of teachers.
 * Relationship with {@link User}
 */
public class TeacherProfile extends RealmObject {

    @PrimaryKey
    private  int teacherId;
    private User user;
    private int totalAssignment;
    private int totalSubmissionReceived;
    private int totalPost;
    private int totalBadges;
    private Date createdDate;
    private Date modifiedDate;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalAssignment() {
        return totalAssignment;
    }

    public void setTotalAssignment(int totalAssignment) {
        this.totalAssignment = totalAssignment;
    }

    public int getTotalSubmissionReceived() {
        return totalSubmissionReceived;
    }

    public void setTotalSubmissionReceived(int totalSubmissionReceived) {
        this.totalSubmissionReceived = totalSubmissionReceived;
    }

    public int getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(int totalPost) {
        this.totalPost = totalPost;
    }

    public int getTotalBadges() {
        return totalBadges;
    }

    public void setTotalBadges(int totalBadges) {
        this.totalBadges = totalBadges;
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
