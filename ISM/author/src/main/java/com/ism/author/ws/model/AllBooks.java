package com.ism.author.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllBooks {

    private String bookName;
    private String id;
    private String frontCoverImage;
    private String ebookLink;
    private String bookDescription;
    private String backCoverImage;


    @JsonProperty("book_name")
    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @JsonProperty("front_cover_image")
    public String getFrontCoverImage() {
        return this.frontCoverImage;
    }

    public void setFrontCoverImage(String frontCoverImage) {
        this.frontCoverImage = frontCoverImage;
    }

    @JsonProperty("ebook_link")
    public String getEbookLink() {
        return this.ebookLink;
    }

    public void setEbookLink(String ebookLink) {
        this.ebookLink = ebookLink;
    }

    @JsonProperty("book_description")
    public String getBookDescription() {
        return this.bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    @JsonProperty("back_cover_image")
    public String getBackCoverImage() {
        return this.backCoverImage;
    }

    public void setBackCoverImage(String backCoverImage) {
        this.backCoverImage = backCoverImage;
    }


}
