package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of invited user.
 * Relationship with {@link RORole}
 * Relationship with {@link ROUser}
 */
public class ROInvitedUser extends RealmObject {

    @PrimaryKey
    private  int invitedUserId;
    private String status;
    private String invitedEmail;
    private RORole invitationRORole;
    private ROUser invitedBy;
    private Date createdDate;
    private Date modifiedDate;

    public int getInvitedUserId() {
        return invitedUserId;
    }

    public void setInvitedUserId(int invitedUserId) {
        this.invitedUserId = invitedUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvitedEmail() {
        return invitedEmail;
    }

    public void setInvitedEmail(String invitedEmail) {
        this.invitedEmail = invitedEmail;
    }

    public RORole getInvitationRORole() {
        return invitationRORole;
    }

    public void setInvitationRORole(RORole invitationRORole) {
        this.invitationRORole = invitationRORole;
    }

    public ROUser getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(ROUser invitedBy) {
        this.invitedBy = invitedBy;
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
