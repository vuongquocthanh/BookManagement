package com.example.thanh.android_project_mob204.sqlitedao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thanh.android_project_mob204.Constant;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Category;
import com.example.thanh.android_project_mob204.model.User;

import java.util.ArrayList;
import java.util.List;

public class DAOCategory implements Constant {
    DatabaseHelper databaseHelper;

    public DAOCategory(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void insertCategory(Category category){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, category.getCategoryID());
        values.put(COLUMN_CATEGORY_NAME, category.getCategoryName());
        values.put(COLUMN_CATEGORY_AVATAR, category.getCategoryAvatar());
        values.put(COLUMN_CATEGORY_DESCRIPTION, category.getCategoryDescription());
        values.put(COLUMN_CATEGORY_POSITION, category.getCategoryPosition());
        database.insert(TABLE_CATEGORY, null, values);
        database.close();
    }

    public Category getCategory(String categoryId){
        Category category = null;
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_CATEGORY, new String[]{COLUMN_CATEGORY_ID, COLUMN_CATEGORY_NAME, COLUMN_CATEGORY_AVATAR, COLUMN_CATEGORY_DESCRIPTION, COLUMN_CATEGORY_POSITION},
                COLUMN_CATEGORY_ID+" =?", new String[]{categoryId}, null, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            String category_id = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
            String category_name = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
            byte[] category_avatar = cursor.getBlob(cursor.getColumnIndex(COLUMN_CATEGORY_AVATAR));
            String category_description = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_DESCRIPTION));
            String category_position = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_POSITION));
            category = new Category(category_id, category_name, category_avatar, category_description, category_position);
        }
        return category;
    }

    public List<Category> getAllCategory(){
        List<Category> listCategory = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_CATEGORY;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Category category = null;
                String category_id = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                String category_name = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
                byte[] category_avatar = cursor.getBlob(cursor.getColumnIndex(COLUMN_CATEGORY_AVATAR));
                String category_description = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_DESCRIPTION));
                String category_position = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_POSITION));
                category = new Category(category_id, category_name, category_avatar, category_description, category_position);
                listCategory.add(category);
            }while(cursor.moveToNext());
        }
        database.close();
        return listCategory;
    }

    public void deleteCategory(Category category){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_CATEGORY, COLUMN_CATEGORY_ID+" =?", new String[]{String.valueOf(category.getCategoryID())});
        database.close();
    }

    public int updateCategory(Category category){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, category.getCategoryID());
        values.put(COLUMN_CATEGORY_NAME, category.getCategoryName());
        values.put(COLUMN_CATEGORY_AVATAR, category.getCategoryAvatar());
        values.put(COLUMN_CATEGORY_DESCRIPTION, category.getCategoryDescription());
        values.put(COLUMN_CATEGORY_POSITION, category.getCategoryPosition());
        return database.update(TABLE_CATEGORY, values, COLUMN_CATEGORY_ID+" =?", new String[]{String.valueOf(category.getCategoryID())});
    }
}
