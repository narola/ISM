package model;

import java.security.acl.Group;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 *  {@link RealmObject} class to handle detail of group member information.
 *  Relationship with {@link TutorialGroup},{@link User}
 */
public class TutorialGroupMember extends RealmObject {
    @PrimaryKey
    private  int groupMemberId;
    private TutorialGroup tutorialGroup;
    private User user;
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

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }

    public void setTutorialGroup(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
