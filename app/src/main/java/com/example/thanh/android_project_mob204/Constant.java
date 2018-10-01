package com.example.thanh.android_project_mob204;

public interface Constant {

//    TABLE USER
    String TABLE_USER = "USER";
    String COLUMN_USERNAME = "Username";
    String COLUMN_PASSWORD = "Password";
    String COLUMN_NAME = "Name";
    String COLUMN_PHONE_NUMBER = "Phone_number";
    String CREATE_TABLE_USER = "CREATE TABLE "
            +TABLE_USER+"("
            +COLUMN_USERNAME+" VARCHAR PRIMARY KEY,"
            +COLUMN_PASSWORD+" VARCHAR,"
            +COLUMN_NAME+" VARCHAR,"
            +COLUMN_PHONE_NUMBER+" VARCHAR)";
//    -----------------------------------------

//    TABLE CATEGORY
    String TABLE_CATEGORY = "CATEGORY";
    String COLUMN_CATEGORY_ID = "CategoryId";
    String COLUMN_CATEGORY_NAME = "CategoryName";
    String COLUMN_CATEGORY_AVATAR = "CategoryAvatar";
    String COLUMN_CATEGORY_DESCRIPTION = "CategoryDescription";
    String COLUMN_CATEGORY_POSITION = "CategoryPosition";
    String CREATE_TABLE_CATEGORY = "CREATE TABLE "
            +TABLE_CATEGORY+"("
            +COLUMN_CATEGORY_ID+" VARCHAR(5) PRIMARY KEY NOT NULL,"
            +COLUMN_CATEGORY_NAME+" VARCHAR(50),"
            +COLUMN_CATEGORY_AVATAR+" BLOB,"
            +COLUMN_CATEGORY_DESCRIPTION+" VARCHAR(255),"
            +COLUMN_CATEGORY_POSITION+" INT)";
//    -----------------------------------------

//    TABLE BOOK
    String TABLE_BOOK = "BOOK";
    String COLUMN_BOOK_ID = "BookId";
    String COLUMN_BOOK_NAME = "BookName";
    String COLUMN_BOOK_DESCRIPTION = "BookDescription";
    String COLUMN_BOOK_CATEGORY_ID = "BookCategoryId";
    String COLUMN_BOOK_AUTHOR = "BookAuthor";
    String COLUMN_BOOK_PUBLISHER = "BookPublisher";
    String COLUMN_BOOK_PRICE = "BookPrice";
    String COLUMN_BOOK_COUNT = "BookCount";
    String COLUMN_BOOK_AVATAR = "BookAvatar";
    String CREATE_TABLE_BOOK = "CREATE TABLE "+TABLE_BOOK
            +"("+COLUMN_BOOK_ID+" VARCHAR(15) PRIMARY KEY NOT NULL,"
            +COLUMN_BOOK_NAME+" VARCHAR(250),"
            +COLUMN_BOOK_DESCRIPTION+" VARCHAR(255),"
            +COLUMN_BOOK_CATEGORY_ID+" VARCHAR(5),"
            +COLUMN_BOOK_AUTHOR+" VARCHAR,"
            +COLUMN_BOOK_PUBLISHER+" VARCHAR,"
            +COLUMN_BOOK_PRICE+" DOUBLE,"
            +COLUMN_BOOK_COUNT+" NUMBER,"
            +COLUMN_BOOK_AVATAR+" BLOB)";
//    -------------------------------------------
}
