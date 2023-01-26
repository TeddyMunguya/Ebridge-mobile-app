package com.tracking.ebridge.models;

import com.google.gson.annotations.SerializedName;

public class QuestionPaper {
    @SerializedName("id")
    private int id;
    @SerializedName("subject")
    private String subject;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
