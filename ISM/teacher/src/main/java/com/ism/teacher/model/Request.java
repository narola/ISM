package com.ism.teacher.model;

/**
 * this is the common request class to set paarmeters in json.
 */
public class Request {


    private String exam_id;
    private String user_id;
    private int role;

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
