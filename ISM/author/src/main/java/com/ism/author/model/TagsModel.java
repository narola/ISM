package com.ism.author.model;

import java.io.Serializable;

/**
 * Created by c166 on 05/11/15.
 */
public class TagsModel implements Serializable {

    private String tagName;
    private String tagId;

    public TagsModel(String tagName, String tagId) {
        this.tagName = tagName;
        this.tagId = tagId;

    }

    public String getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }


    @Override
    public String toString() {
        return tagName;
    }
}


