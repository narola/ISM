package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of studyMates.
 * Relationship with {@link User}
 */
public class StudyMates extends RealmObject {

    @PrimaryKey
    private  int studyMateId;
    private User mate;
    private User mateOf;
    private int status;
    private boolean isOnline;
    private Date createdDate;
    private Date modifiedDate;

    public int getStudyMateId() {
        return studyMateId;
    }

    public void setStudyMateId(int studyMateId) {
        this.studyMateId = studyMateId;
    }

    public User getMate() {
        return mate;
    }

    public void setMate(User mate) {
        this.mate = mate;
    }

    public User getMateOf() {
        return mateOf;
    }

    public void setMateOf(User mateOf) {
        this.mateOf = mateOf;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
