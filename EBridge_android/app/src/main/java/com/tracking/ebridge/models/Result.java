package com.tracking.ebridge.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;
    @SerializedName("timetabledetails")
    private List<TimeTable> timeTableList;
    @SerializedName("questiondetails")
    private List<QuestionPaper> questionPapers;
    @SerializedName("notifications")
    private List<Notification> notifications;
    @SerializedName("doubts")
    private List<Doubt> doubts;

    public Result(int success, String message) {
        this.success = success;
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TimeTable> getTimeTableList() {
        return timeTableList;
    }

    public void setTimeTableList(List<TimeTable> timeTableList) {
        this.timeTableList = timeTableList;
    }

    public List<QuestionPaper> getQuestionPapers() {
        return questionPapers;
    }

    public void setQuestionPapers(List<QuestionPaper> questionPapers) {
        this.questionPapers = questionPapers;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Doubt> getDoubts() {
        return doubts;
    }

    public void setDoubts(List<Doubt> doubts) {
        this.doubts = doubts;
    }
}
