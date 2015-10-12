package com.ism.model;

/**
 * Created by c162 on 10/10/15.
 */
public class NoticeModel {
    String noticeName;
    String noticeDesc;

    public String getNoticeName() {
        return noticeName;
    }

    public NoticeModel(String noticeName, String noticeDesc) {
        this.noticeName = noticeName;
        this.noticeDesc = noticeDesc;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeDesc() {
        return noticeDesc;
    }

    public void setNoticeDesc(String noticeDesc) {
        this.noticeDesc = noticeDesc;
    }
}
