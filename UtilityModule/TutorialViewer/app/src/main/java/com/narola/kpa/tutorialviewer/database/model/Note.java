package com.narola.kpa.tutorialviewer.database.model;

import android.util.Log;

import com.narola.kpa.tutorialviewer.object.Global;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Krunal Panchal on 01/10/15.
 */

@RealmClass
public class Note extends RealmObject {

    private static final String TAG = Note.class.getSimpleName();

    @PrimaryKey
    private int NoteID;
    private String TutorialID;
    private String TutorialName;
    private long TutorialTime;
    private long NoteCreationTime;
    private String NoteText;
    private String NoteThumbnailName;

    public int getNoteID() {
        return NoteID;
    }

    public void setNoteID(int noteID) {
        NoteID = noteID;
    }

    public String getTutorialID() {
        return TutorialID;
    }

    public void setTutorialID(String tutorialID) {
        TutorialID = tutorialID;
    }

    public String getTutorialName() {
        return TutorialName;
    }

    public void setTutorialName(String tutorialName) {
        TutorialName = tutorialName;
    }

    public long getTutorialTime() {
        return TutorialTime;
    }

    public void setTutorialTime(long tutorialTime) {
        TutorialTime = tutorialTime;
    }

    public long getNoteCreationTime() {
        return NoteCreationTime;
    }

    public void setNoteCreationTime(long noteCreationTime) {
        NoteCreationTime = noteCreationTime;
    }

    public String getNoteText() {
        return NoteText;
    }

    public void setNoteText(String noteText) {
        NoteText = noteText;
    }

    public String getNoteThumbnailName() {
        return NoteThumbnailName;
    }

    public void setNoteThumbnailName(String noteThumbnailName) {
        NoteThumbnailName = noteThumbnailName;
    }

    public static Note addNote(String tutorialID, String tutorialName, long tutorialTime, long noteCreationTime, String noteText, String noteThumbnailName) {
        try {
            int newNoteId = (int) Global.realm.where(Note.class).maximumInt("NoteID") + 1;

            Global.realm.beginTransaction();
            Note note = Global.realm.createObject(Note.class);
            note.setNoteID(newNoteId);
            note.setTutorialID(tutorialID);
            note.setTutorialName(tutorialName);
            note.setTutorialTime(tutorialTime);
            note.setNoteCreationTime(noteCreationTime);
            note.setNoteText(noteText);
            note.setNoteThumbnailName(noteThumbnailName);

            return note;
        } catch (Exception e) {
            Log.e(TAG, "addNote Exception : " + e.toString());
            return null;
        } finally {
            Global.realm.commitTransaction();
        }
    }

    public static RealmResults<Note> getAllNotes() {
        RealmResults<Note> notes = null;
        try {
            notes = Global.realm.allObjects(Note.class);
        } catch (Exception e) {
            Log.e(TAG, "getAllNotes Exception : " + e.toString());
        }
        return notes;
    }

    public static void deleteNote(Note note) {
        try {
            Global.realm.beginTransaction();

            note.removeFromRealm();

        } catch (Exception e) {
            Log.e(TAG, "deleteNote Exception : " + e.toString());
        } finally {
            Global.realm.commitTransaction();
        }
    }

    public static void updateNoteText(Note note, String noteText) {
        try {
            Global.realm.beginTransaction();

            note.setNoteText(noteText);

        } catch (Exception e) {
            Log.e(TAG, "updateNoteText Exception : " + e.toString());
        } finally {
            Global.realm.commitTransaction();
        }
    }

}
