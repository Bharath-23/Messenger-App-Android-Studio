package com.example.chat_application;

public class Message {
    private String username;
    private String message;
    private Boolean ismine;
    private String time;


    public Message(String username, String message, Boolean ismine,String time) {
        this.username = username;
        this.message = message;
        this.ismine = ismine;
        this.time = time;
    }

    public Message() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Boolean getIsmine() {
        return ismine;
    }

    public void setIsmine(Boolean ismine) {
        this.ismine = ismine;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
