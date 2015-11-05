package com.ism.model;

/**
 * Created by c161 on 04/11/15.
 */
public class AllNotice {

    String strNoticeName;
    String strNoticeDesc;

	public AllNotice(String strNoticeName, String strNoticeDesc) {
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
