package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle profile related to student.
 * Relationship with {@link Classrooms},{@link Courses},{@link School},{@link User}
 */
public class StudentProfile extends RealmObject {

    @PrimaryKey
    private  int localStudentId;
    private  int serverStudentId;
    private String academicYear;
    private String joiningYear;
    private String ambitionInLife;
    private String aboutMe;
    private String contactNumber;
    private User user;
    private School school;
    private Date birthDate;
    private Classrooms classroom;
    private Classrooms joiningClass;
    private User linkedAccount;
    private Courses course;
    private int totalScore;
    private int totalExam;
    private int totalBadges;
    private int totalStudyMate;
    private int totalAuthorFollowed;
    private int totalAssignment;
    private int totalFavoriteQuestion;
    private int totalQuestionAsked;
    private int totalPost;
    private int walletBalance;
    private int rank;
    private boolean failedEver;
    private Date createdDate;
    private Date modifiedDate;

    public int getLocalStudentId() {
        return localStudentId;
    }

    public void setLocalStudentId(int localStudentId) {
        this.localStudentId = localStudentId;
    }

    public int getServerStudentId() {
        return serverStudentId;
    }

    public void setServerStudentId(int serverStudentId) {
        this.serverStudentId = serverStudentId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getJoiningYear() {
        return joiningYear;
    }

    public void setJoiningYear(String joiningYear) {
        this.joiningYear = joiningYear;
    }

    public String getAmbitionInLife() {
        return ambitionInLife;
    }

    public void setAmbitionInLife(String ambitionInLife) {
        this.ambitionInLife = ambitionInLife;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Classrooms getClassroom() {
        return classroom;
    }

    public void setClassroom(Classrooms classroom) {
        this.classroom = classroom;
    }

    public Classrooms getJoiningClass() {
        return joiningClass;
    }

    public void setJoiningClass(Classrooms joiningClass) {
        this.joiningClass = joiningClass;
    }

    public User getLinkedAccount() {
        return linkedAccount;
    }

    public void setLinkedAccount(User linkedAccount) {
        this.linkedAccount = linkedAccount;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalExam() {
        return totalExam;
    }

    public void setTotalExam(int totalExam) {
        this.totalExam = totalExam;
    }

    public int getTotalBadges() {
        return totalBadges;
    }

    public void setTotalBadges(int totalBadges) {
        this.totalBadges = totalBadges;
    }

    public int getTotalStudyMate() {
        return totalStudyMate;
    }

    public void setTotalStudyMate(int totalStudyMate) {
        this.totalStudyMate = totalStudyMate;
    }

    public int getTotalAuthorFollowed() {
        return totalAuthorFollowed;
    }

    public void setTotalAuthorFollowed(int totalAuthorFollowed) {
        this.totalAuthorFollowed = totalAuthorFollowed;
    }

    public int getTotalAssignment() {
        return totalAssignment;
    }

    public void setTotalAssignment(int totalAssignment) {
        this.totalAssignment = totalAssignment;
    }

    public int getTotalFavoriteQuestion() {
        return totalFavoriteQuestion;
    }

    public void setTotalFavoriteQuestion(int totalFavoriteQuestion) {
        this.totalFavoriteQuestion = totalFavoriteQuestion;
    }

    public int getTotalQuestionAsked() {
        return totalQuestionAsked;
    }

    public void setTotalQuestionAsked(int totalQuestionAsked) {
        this.totalQuestionAsked = totalQuestionAsked;
    }

    public int getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(int totalPost) {
        this.totalPost = totalPost;
    }

    public int getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(int walletBalance) {
        this.walletBalance = walletBalance;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isFailedEver() {
        return failedEver;
    }

    public void setFailedEver(boolean failedEver) {
        this.failedEver = failedEver;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
