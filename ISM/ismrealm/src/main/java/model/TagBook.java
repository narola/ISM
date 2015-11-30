package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - tag info for books.
 * Relationship with {@link Questions}
 * Relationship with {@link Tags}
 */
public class TagBook extends RealmObject {

    @PrimaryKey
    private  int tagBookId;
    private Books book;
    private Tags tag;
    private Date createdDate;
    private Date modifiedDate;

    public int getTagBookId() {
        return tagBookId;
    }

    public void setTagBookId(int tagBookId) {
        this.tagBookId = tagBookId;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
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
