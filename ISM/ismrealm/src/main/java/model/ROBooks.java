package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class with details of book.
 */
public class ROBooks extends RealmObject {

    @PrimaryKey
    private int bookId;
    private String bookName;
    private String bookDescription;
    private String ebookLink;
    private String imageLink;
    private String frontCoverImage;
    private String backCoverImage;
    private String publicationDate;
    private String price;
    private String publisherName;
    private String isbnNo;
    private Date createdDate;
    private Date modifiedDate;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getEbookLink() {
        return ebookLink;
    }

    public void setEbookLink(String ebookLink) {
        this.ebookLink = ebookLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
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

    public String getBackCoverImage() {
        return backCoverImage;
    }

    public void setBackCoverImage(String backCoverImage) {
        this.backCoverImage = backCoverImage;
    }

    public String getFrontCoverImage() {
        return frontCoverImage;
    }

    public void setFrontCoverImage(String frontCoverImage) {
        this.frontCoverImage = frontCoverImage;
    }
}
