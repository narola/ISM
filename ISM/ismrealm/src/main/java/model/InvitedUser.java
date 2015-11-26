package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of invited user.
 * Relationship with {@link Role}
 * Relationship with {@link User}
 */
public class InvitedUser extends RealmObject {

    @PrimaryKey
    private  int invitedUserId;
    private String status;
    private String invitedEmail;
    private Role invitationRole;
    private User invitedBy;
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

    public Role getInvitationRole() {
        return invitationRole;
    }

    public void setInvitationRole(Role invitationRole) {
        this.invitationRole = invitationRole;
    }

    public User getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(User invitedBy) {
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
