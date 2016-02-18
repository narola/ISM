package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of notice viewer.
 * Relationship with {@link RONoticeBoard}
 * Relationship with {@link RORole}
 * Relationship with {@link ROClassrooms}
 */
public class RONoticeBoardViewer extends RealmObject {

    @PrimaryKey
    private  int noticeBoardViewerId;
    private RONoticeBoard roNoticeBoard;
    private RORole roRole;
    private ROClassrooms roClassrooms;
    private Date createdDate;
    private Date modifiedDate;

    public int getNoticeBoardViewerId() {
        return noticeBoardViewerId;
    }

    public void setNoticeBoardViewerId(int noticeBoardViewerId) {
        this.noticeBoardViewerId = noticeBoardViewerId;
    }

    public RONoticeBoard getRoNoticeBoard() {
        return roNoticeBoard;
    }

    public void setRoNoticeBoard(RONoticeBoard roNoticeBoard) {
        this.roNoticeBoard = roNoticeBoard;
    }

    public RORole getRoRole() {
        return roRole;
    }

    public void setRoRole(RORole roRole) {
        this.roRole = roRole;
    }

    public ROClassrooms getRoClassrooms() {
        return roClassrooms;
    }

    public void setRoClassrooms(ROClassrooms roClassrooms) {
        this.roClassrooms = roClassrooms;
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
