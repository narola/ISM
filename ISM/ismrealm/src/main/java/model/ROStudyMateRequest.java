package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class information about requests from studymates.
 * Relationship with {@link ROUser}
 */
public class ROStudyMateRequest extends RealmObject {

    @PrimaryKey
    private  int studyMateRequestId;
    private ROUser requestFrom;
    private ROUser requestTo;
    private int status;
    private boolean isSeen;
    private Date createdDate;
    private Date modifiedDate;

    public int getStudyMateRequestId() {
        return studyMateRequestId;
    }

    public void setStudyMateRequestId(int studyMateRequestId) {
        this.studyMateRequestId = studyMateRequestId;
    }

    public ROUser getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(ROUser requestFrom) {
        this.requestFrom = requestFrom;
    }

    public ROUser getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(ROUser requestTo) {
        this.requestTo = requestTo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean isSeen) {
        this.isSeen = isSeen;
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
