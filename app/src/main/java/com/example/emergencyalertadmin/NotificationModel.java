package com.example.emergencyalertadmin;

public class NotificationModel {
    String title;
    String body;
    String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public NotificationModel(String title, String body, String category) {
        this.title = title;
        this.body = body;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    }


