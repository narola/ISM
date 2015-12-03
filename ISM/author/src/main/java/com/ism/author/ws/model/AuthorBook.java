package com.ism.author.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorBook {

    private String bookId;
    private String bookImage;
    private String bookName;


    @JsonProperty("book_id")
    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @JsonProperty("book_image")
    public String getBookImage() {
        return this.bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    @JsonProperty("book_name")
    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


}
