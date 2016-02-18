package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c162 on 29/12/15.
 * {@link RealmObject} class - detail of jottings or notes of user.
 */
public class Notes extends RealmObject {

    @PrimaryKey
    private  int localNoteId;
    private  int serverNoteId;
    private String noteName;
    private String noteText;
    private String video;
    private String audio;
    private NoteImage noteImage ;
    private User user;
    private  int isSync;
    private String noteSubject;
    private Date createdDate;
    private Date modifiedDate;

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }

    public int getLocalNoteId() {
        return localNoteId;
    }

    public void setLocalNoteId(int localNoteId) {
        this.localNoteId = localNoteId;
    }

    public int getServerNoteId() {
        return serverNoteId;
    }

    public void setServerNoteId(int serverNoteId) {
        this.serverNoteId = serverNoteId;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteSubject() {
        return noteSubject;
    }

    public void setNoteSubject(String noteSubject) {
        this.noteSubject = noteSubject;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public NoteImage getNoteImage() {
        return noteImage;
    }

    public void setNoteImage(NoteImage noteImage) {
        this.noteImage = noteImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
