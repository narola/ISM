package com.ism.model;

/**
 * Created by c162 on 10/10/15.
 */
public class NoticeModel {
    String strNoticeName;
    String strNoticeDesc;

    public String getStrNoticeName() {
        return strNoticeName;
    }

    public NoticeModel(String strNoticeName, String strNoticeDesc) {
        this.strNoticeName = strNoticeName;
        this.strNoticeDesc = strNoticeDesc;
    }

    public void setStrNoticeName(String strNoticeName) {
        this.strNoticeName = strNoticeName;
    }

    public String getStrNoticeDesc() {
        return strNoticeDesc;
    }

    public void setStrNoticeDesc(String strNoticeDesc) {
        this.strNoticeDesc = strNoticeDesc;
    }
}
