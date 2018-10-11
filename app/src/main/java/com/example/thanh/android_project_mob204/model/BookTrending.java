package com.example.thanh.android_project_mob204.model;

public class BookTrending {
    private String bookID, bookName, bookDescription;
    private byte[] bookAvatar;
    private int countTrending;

    public BookTrending(String bookID, String bookName, String bookDescription, byte[] bookAvatar, int countTrending) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookDescription = bookDescription;
        this.bookAvatar = bookAvatar;
        this.countTrending = countTrending;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public byte[] getBookAvatar() {
        return bookAvatar;
    }

    public void setBookAvatar(byte[] bookAvatar) {
        this.bookAvatar = bookAvatar;
    }

    public int getCountTrending() {
        return countTrending;
    }

    public void setCountTrending(int countTrending) {
        this.countTrending = countTrending;
    }
}
