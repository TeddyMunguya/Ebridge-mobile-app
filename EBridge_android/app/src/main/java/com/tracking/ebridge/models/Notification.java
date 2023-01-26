package com.tracking.ebridge.models;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("id")
    private int id;
    @SerializedName("subject")
    private String subject;
    @SerializedName("description")
    private String description;
    @SerializedName("username")
    private String username;
    @SerializedName("time")
    private String time;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
