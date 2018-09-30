package com.example.thanh.android_project_mob204.model;

public class Book {
    private String title;
    private int avatar;
    private String content;

    public Book(String title, int avatar, String content) {
        this.title = title;
        this.avatar = avatar;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
