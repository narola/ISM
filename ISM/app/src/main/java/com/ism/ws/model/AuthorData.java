package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorData {
	
    private String authorId;
    private String termsLink;
    private String aboutAuthor;
    private String authorName;
    private String totalFollowers;
    private String education;
    private String totalBooks;
    private String authorPic;

    @JsonProperty("author_id")
    public String getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @JsonProperty("terms_link")
    public String getTermsLink() {
        return this.termsLink;
    }

    public void setTermsLink(String termsLink) {
        this.termsLink = termsLink;
    }
    @JsonProperty("about_author")
    public String getAboutAuthor() {
        return this.aboutAuthor;
    }

    public void setAboutAuthor(String aboutAuthor) {
        this.aboutAuthor = aboutAuthor;
    }
    @JsonProperty("author_name")
    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    @JsonProperty("total_followers")
    public String getTotalFollowers() {
        return this.totalFollowers;
    }

    public void setTotalFollowers(String totalFollowers) {
        this.totalFollowers = totalFollowers;
    }
    @JsonProperty("education")
    public String getEducation() {
        return this.education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @JsonProperty("total_books")
    public String getTotalBooks() {
        return this.totalBooks;
    }

    public void setTotalBooks(String totalBooks) {
        this.totalBooks = totalBooks;
    }

    @JsonProperty("author_pic")
    public String getAuthorPic() {
        return this.authorPic;
    }

    public void setAuthorPic(String authorPic) {
        this.authorPic = authorPic;
    }


    
}
