package com.ism.author.model;

import com.ism.author.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c166 on 18/11/15.
 */
public class FragmentArgumentObject {

    private String examId;
    private String examType;
    private String examMode;
    private String examName;
    private String classroomId;
    private String studentId;
    private int position;
    private String profilePic;
    private int assignmentNo;
    private String assignmentName;
    private String studentName;
    private String subjectId;
    private String subjectName;
    private String passPercentage;
    private String duration;
    private String examCategory;
    private ArrayList<Questions> listOfQuestions;


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

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamName() {
        return examName;
    }

    public String getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getPassPercentage() {
        return this.passPercentage;
    }

    public void setPassPercentage(String passPercentage) {
        this.passPercentage = passPercentage;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArrayList<Questions> getArrayListOfQuestions() {
        return this.listOfQuestions;
    }

    public void setListOfQuestions(ArrayList<Questions> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }


    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getExamCategory() {
        return this.examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }


}
