package com.example.thanh.android_project_mob204.model;

public class Book {
    private String bookId, bookName,bookDescription, bookCategoryId, bookAuthor, bookPublisher, bookPrice, bookCount;
    private byte[] bookAvatar;

    public Book(String bookId, String bookName, String bookDescription, String bookCategoryId, String bookAuthor, String bookPublisher, String bookPrice, String bookCount, byte[] bookAvatar) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookDescription = bookDescription;
        this.bookCategoryId = bookCategoryId;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookPrice = bookPrice;
        this.bookCount = bookCount;
        this.bookAvatar = bookAvatar;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public String getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(String bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookCount() {
        return bookCount;
    }

    public void setBookCount(String bookCount) {
        this.bookCount = bookCount;
    }

    public byte[] getBookAvatar() {
        return bookAvatar;
    }

    public void setBookAvatar(byte[] bookAvatar) {
        this.bookAvatar = bookAvatar;
    }
}
