package com.ism.author.model;

/**
 * Created by c166 on 18/11/15.
 */
public class FragmentArgumentObject {

    private String examId;
    private String examType;
    private String examMode;
    private String studentId;
    private int position;
    private String profilePic;
    private int assignmentNo;
    private String assignmentName;
    private String studentName;

    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }


    public String getExamType() {
        return this.examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamMode() {
        return this.examMode;
    }

    public void setExamMode(String examMode) {
        this.examMode = examMode;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setAssignmentNo(int assignmentNo) {
        this.assignmentNo = assignmentNo;
    }

    public int getAssignmentNo() {
        return assignmentNo;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }
}
