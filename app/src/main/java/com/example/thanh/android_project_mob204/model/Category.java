package com.example.thanh.android_project_mob204.model;

public class Category {
    private String categoryID;
    private String categoryName;
    private byte[] categoryAvatar;
    private String categoryDescription;
    private String categoryPosition;

    public Category(String categoryID, String categoryName, byte[] categoryAvatar, String categoryDescription, String categoryPosition) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryAvatar = categoryAvatar;
        this.categoryDescription = categoryDescription;
        this.categoryPosition = categoryPosition;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public byte[] getCategoryAvatar() {
        return categoryAvatar;
    }

    public void setCategoryAvatar(byte[] categoryAvatar) {
        this.categoryAvatar = categoryAvatar;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryPosition() {
        return categoryPosition;
    }

    public void setCategoryPosition(String categoryPosition) {
        this.categoryPosition = categoryPosition;
    }
}
