package com.example.thanh.android_project_mob204.sqlitedao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thanh.android_project_mob204.Constant;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements Constant {
    private DatabaseHelper databaseHelper;

    public UserDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void insertUser(User user){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE_NUMBER, user.getPhonenumber());
        long id = database.insert(TABLE_USER, null, values);
        database.close();
    }

    public User getUser(String username){
        User user = null;
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_USER, new String[]{COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_NAME, COLUMN_PHONE_NUMBER},
                COLUMN_USERNAME+" =?", new String[]{username}, null, null, null);

//        Kiểm tra xem cursor có khác null và chứa giá trị không
        if(cursor!=null && cursor.moveToFirst()){
            String user_name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            String pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
            user = new User(user_name, pass, name, phone);
        }
        return user;
    }

    public List<User> getAllUser(){
        List<User> listUser = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_USER;
//        Cursor là đối tượng để chứa kết quả truy vấn
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                User user = null;
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                user = new User(username, password, name, phone);
                listUser.add(user);
            }while(cursor.moveToNext());
        }
        database.close();
        return listUser;
    }

    public void deleteUser(User user){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_USER, COLUMN_USERNAME+" =?", new String[]{String.valueOf(user.getUsername())});
        database.close();
    }

    public int updateUser(User user){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE_NUMBER, user.getPhonenumber());
        return database.update(TABLE_USER, values, COLUMN_USERNAME+" =?", new String[]{String.valueOf(user.getUsername())});
    }

//    public Cursor getUserListByKeyWord(String search){
//        SQLiteDatabase database = databaseHelper.getWritableDatabase();
//        String selectQuery = "SELECT rowid as "+COLUMN_USERNAME+", "
//                +COLUMN_PASSWORD + ", "
//                +COLUMN_NAME + ", "
//                +COLUMN_PHONE_NUMBER
//                +" FROM "+TABLE_USER
//                +" WHERE "+COLUMN_NAME+" LIKE '%" + search + "%'";
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        if(cursor==null){
//            return null;
//        }else if(!cursor.moveToFirst()){
//            cursor.close();
//            return null;
//        }
//        return cursor;
//    }
}
