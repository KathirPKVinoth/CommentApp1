package com.example.commentapplication;

public class User {

    String Email, caption;

    public  User() {}

    public User(String email, String caption) {
        Email = email;
        this.caption = caption;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
