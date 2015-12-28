package com.ism.teacher.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonNotes {

    private String lectureId;
    private String lectureName;
    private ArrayList<Notes> notes;

    @JsonProperty("lecture_id")
    public String getLectureId() {
        return lectureId;
    }

    public LessonNotes setLectureId(String lectureId) {
        this.lectureId = lectureId;
        return this;
    }

    @JsonProperty("lecture_name")
    public String getLectureName() {
        return lectureName;
    }

    public LessonNotes setLectureName(String lectureName) {
        this.lectureName = lectureName;
        return this;
    }

    @JsonProperty("notes")
    public ArrayList<Notes> getNotes() {
        return notes;
    }

    public LessonNotes setNotes(ArrayList<Notes> notes) {
        this.notes = notes;
        return this;
    }
}
