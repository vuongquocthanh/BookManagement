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

}
