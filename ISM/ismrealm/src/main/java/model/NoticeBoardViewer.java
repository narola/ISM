package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of notice viewer.
 * Relationship with {@link NoticeBoard}
 * Relationship with {@link Role}
 * Relationship with {@link Classrooms}
 */
public class NoticeBoardViewer extends RealmObject {

    @PrimaryKey
    private  int noticeBoardViewerId;
    private NoticeBoard noticeBoard;
    private Role role;
    private Classrooms classrooms;
    private Date createdDate;
    private Date modifiedDate;

    public int getNoticeBoardViewerId() {
        return noticeBoardViewerId;
    }

    public void setNoticeBoardViewerId(int noticeBoardViewerId) {
        this.noticeBoardViewerId = noticeBoardViewerId;
    }

    public NoticeBoard getNoticeBoard() {
        return noticeBoard;
    }

    public void setNoticeBoard(NoticeBoard noticeBoard) {
        this.noticeBoard = noticeBoard;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Classrooms getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Classrooms classrooms) {
        this.classrooms = classrooms;
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
