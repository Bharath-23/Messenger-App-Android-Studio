package com.example.chat_application;

import android.content.Context;

import java.util.List;

public class Notification {
    private String username;
    private String notification;
    private String date;
    private String time;
    private String image;



    public Notification(String username,String notification,String date,String time,String image) {
        this.username = username;
        this.notification = notification;
        this.date = date;
        this.time = time;
        this.image = image;

    }

    public Notification() {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
