package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle images that is available in note.
 * Relationship with {@link Feeds}
 */
public class NoteImage extends RealmObject {

    @PrimaryKey
    private int noteImageId;
    private Notes notes;
    private String imageLink;
    private Date createdDate;
    private Date modifiedDate;

    public int getNoteImageId() {
        return noteImageId;
    }

    public void setNoteImageId(int noteImageId) {
        this.noteImageId = noteImageId;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
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
