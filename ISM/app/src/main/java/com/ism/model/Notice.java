package com.ism.model;

/**
 * Created by c162 on 10/10/15.
 */
public class Notice {

    String strNoticeName;
    String strNoticeDesc;

	public Notice(String strNoticeName, String strNoticeDesc) {
		this.strNoticeName = strNoticeName;
		this.strNoticeDesc = strNoticeDesc;
	}

    public String getStrNoticeName() {
        return strNoticeName;
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
