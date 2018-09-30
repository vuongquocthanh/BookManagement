package com.example.thanh.android_project_mob204.model;

public class Library {
    private int avatar;
    private String title, subTitle;

    public Library(int avatar, String title, String subTitle) {
        this.avatar = avatar;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
