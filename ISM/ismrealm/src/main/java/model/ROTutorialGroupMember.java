package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 *  {@link RealmObject} class to handle detail of group member information.
 *  Relationship with {@link ROTutorialGroup},{@link ROUser}
 */
public class ROTutorialGroupMember extends RealmObject {
    @PrimaryKey
    private  int groupMemberId;
    private ROTutorialGroup roTutorialGroup;
    private ROUser roUser;
    private int joiningStatus;
    private boolean isCompleted;
    private Date createdDate;
    private Date modifiedDate;
    private Date isDelete;


    public int getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(int groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public ROTutorialGroup getRoTutorialGroup() {
        return roTutorialGroup;
    }

    public void setRoTutorialGroup(ROTutorialGroup roTutorialGroup) {
        this.roTutorialGroup = roTutorialGroup;
    }

    public int getJoiningStatus() {
        return joiningStatus;
    }

    public void setJoiningStatus(int joiningStatus) {
        this.joiningStatus = joiningStatus;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
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

    public Date getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Date isDelete) {
        this.isDelete = isDelete;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }
}
