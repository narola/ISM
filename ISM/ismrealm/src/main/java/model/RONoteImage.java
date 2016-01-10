package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle images that is available in note.
 * Relationship with {@link ROFeeds}
 */
public class RONoteImage extends RealmObject {

    @PrimaryKey
    private int noteImageId;
    private RONotes roNotes;
    private String imageLink;
    private Date createdDate;
    private Date modifiedDate;

    public int getNoteImageId() {
        return noteImageId;
    }

    public void setNoteImageId(int noteImageId) {
        this.noteImageId = noteImageId;
    }

    public RONotes getRoNotes() {
        return roNotes;
    }

    public void setRoNotes(RONotes roNotes) {
        this.roNotes = roNotes;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
