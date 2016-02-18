package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of notices.
 * Relationship with {@link ROUser}
 */
public class RONoticeBoard extends RealmObject {
    @PrimaryKey
    private  int noticeBoardId;
    private String noticeTitle;
    private String notice;
    private ROUser postedBy;
    private int noticeFor;
    private int status;
    private boolean isTemplate;
    private Date createdDate;
    private Date modifiedDate;

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getNoticeBoardId() {
        return noticeBoardId;
    }

    public void setNoticeBoardId(int noticeBoardId) {
        this.noticeBoardId = noticeBoardId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public ROUser getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(ROUser postedBy) {
        this.postedBy = postedBy;
    }

    public int getNoticeFor() {
        return noticeFor;
    }

    public void setNoticeFor(int noticeFor) {
        this.noticeFor = noticeFor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(boolean isTemplate) {
        this.isTemplate = isTemplate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
