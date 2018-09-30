package com.example.thanh.android_project_mob204.model;

public class Invoice {
    private String avatarInvoice, title, date;

    public Invoice(String avatarInvoice, String title, String date) {
        this.avatarInvoice = avatarInvoice;
        this.title = title;
        this.date = date;
    }

    public String getAvatarInvoice() {
        return avatarInvoice;
    }

    public void setAvatarInvoice(String avatarInvoice) {
        this.avatarInvoice = avatarInvoice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
