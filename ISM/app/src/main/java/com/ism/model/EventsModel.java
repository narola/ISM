package com.ism.model;

/**
 * Created by c162 on 12/10/15.
 */
public class EventsModel {
    String time;
    String date;
    String eventName;
    String eventDesc;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public EventsModel(String time, String date, String eventName, String eventDesc) {
        this.time = time;
        this.date = date;
        this.eventName = eventName;
        this.eventDesc = eventDesc;
    }
}
