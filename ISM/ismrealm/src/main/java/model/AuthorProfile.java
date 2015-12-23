package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - handle information about authors.
 * Relationship with {@link User}
 */
public class AuthorProfile extends RealmObject {

    @PrimaryKey
    private  int authorId;
    private String education;
    private String aboutAuthor;
    private User user;
    private int totalQuestionAnswered;
    private int totalAssignment;
    private int totalFavouritesQuestions;
    private int totalExamCreated;
    private int totalFollowers;
    private int totalFollpwing;
    private int totalBooks;
    private int totalSubmissionReceived;
    private int totalPost;
    private int totalBadges;
    private Date createdDate;
    private Date modifiedDate;

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAboutAuthor() {
        return aboutAuthor;
    }

    public void setAboutAuthor(String aboutAuthor) {
        this.aboutAuthor = aboutAuthor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalQuestionAnswered() {
        return totalQuestionAnswered;
    }

    public void setTotalQuestionAnswered(int totalQuestionAnswered) {
        this.totalQuestionAnswered = totalQuestionAnswered;
    }

    public int getTotalFollpwing() {
        return totalFollpwing;
    }

    public void setTotalFollpwing(int totalFollpwing) {
        this.totalFollpwing = totalFollpwing;
    }

    public int getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(int totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public int getTotalExamCreated() {
        return totalExamCreated;
    }

    public void setTotalExamCreated(int totalExamCreated) {
        this.totalExamCreated = totalExamCreated;
    }

    public int getTotalFavouritesQuestions() {
        return totalFavouritesQuestions;
    }

    public void setTotalFavouritesQuestions(int totalFavouritesQuestions) {
        this.totalFavouritesQuestions = totalFavouritesQuestions;
    }

    public int getTotalAssignment() {
        return totalAssignment;
    }

    public void setTotalAssignment(int totalAssignment) {
        this.totalAssignment = totalAssignment;
    }

    public int getTotalSubmissionReceived() {
        return totalSubmissionReceived;
    }

    public void setTotalSubmissionReceived(int totalSubmissionReceived) {
        this.totalSubmissionReceived = totalSubmissionReceived;
    }

    public int getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(int totalPost) {
        this.totalPost = totalPost;
    }

    public int getTotalBadges() {
        return totalBadges;
    }

    public void setTotalBadges(int totalBadges) {
        this.totalBadges = totalBadges;
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
}
