package com.ism.author.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorBook {

    private String bookId;
    private String bookDescription;
    private String bookName;
    private String frontCoverImage;
    private String backCoverImage;
    private String ebookLink;


    @JsonProperty("book_id")
    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @JsonProperty("book_description")
    public String getBookDescription() {
        return this.bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    @JsonProperty("book_name")
    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


    @JsonProperty("back_cover_image")
    public String getBackCoverImage() {
        return backCoverImage;
    }

    public AuthorBook setBackCoverImage(String backCoverImage) {
        this.backCoverImage = backCoverImage;
        return this;
    }

    @JsonProperty("ebook_link")
    public String getEbookLink() {
        return ebookLink;
    }

    public AuthorBook setEbookLink(String ebookLink) {
        this.ebookLink = ebookLink;
        return this;
    }

    @JsonProperty("front_cover_image")
    public String getFrontCoverImage() {
        return frontCoverImage;
    }

    public AuthorBook setFrontCoverImage(String frontCoverImage) {
        this.frontCoverImage = frontCoverImage;
        return this;
    }


}
