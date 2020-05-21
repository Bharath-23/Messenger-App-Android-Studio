package com.example.chat_application;

class Upload {
    private String imageurl;

    public Upload(String imageurl) {

        this.imageurl = imageurl;
    }

    public Upload(){

    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
