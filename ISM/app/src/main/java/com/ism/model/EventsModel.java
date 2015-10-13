package com.ism.model;

/**
 * Created by c162 on 12/10/15.
 */
public class EventsModel {
    String strTime;
    String strDate;
    String strEventName;
    String strEventDesc;

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrEventName() {
        return strEventName;
    }

    public void setStrEventName(String strEventName) {
        this.strEventName = strEventName;
    }

    public String getStrEventDesc() {
        return strEventDesc;
    }

    public void setStrEventDesc(String strEventDesc) {
        this.strEventDesc = strEventDesc;
    }

    public EventsModel(String strTime, String strDate, String strEventName, String strEventDesc) {
        this.strTime = strTime;
        this.strDate = strDate;
        this.strEventName = strEventName;
        this.strEventDesc = strEventDesc;
    }
}
