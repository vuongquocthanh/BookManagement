package com.example.thanh.android_project_mob204.sqlitedao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thanh.android_project_mob204.Constant;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Book;

import java.util.ArrayList;
import java.util.List;

public class DAOBook implements Constant {

    DatabaseHelper databaseHelper;

    public DAOBook(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void insertBook(Book book){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, book.getBookId());
        values.put(COLUMN_BOOK_NAME, book.getBookName());
        values.put(COLUMN_BOOK_DESCRIPTION, book.getBookDescription());
        values.put(COLUMN_BOOK_CATEGORY_ID, book.getBookCategoryId());
        values.put(COLUMN_BOOK_AUTHOR, book.getBookAuthor());
        values.put(COLUMN_BOOK_PUBLISHER, book.getBookPublisher());
        values.put(COLUMN_BOOK_PRICE, book.getBookPrice());
        values.put(COLUMN_BOOK_COUNT, book.getBookCount());
        values.put(COLUMN_BOOK_AVATAR, book.getBookAvatar());
        database.insert(TABLE_BOOK, null, values);
        database.close();
    }

    public Book getBook(String bookId){
        Book book = null;
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_BOOK, new String[]{COLUMN_BOOK_ID,
                COLUMN_BOOK_NAME,COLUMN_BOOK_DESCRIPTION, COLUMN_BOOK_CATEGORY_ID,
                COLUMN_BOOK_AUTHOR,COLUMN_BOOK_PUBLISHER,
                COLUMN_BOOK_PRICE,COLUMN_BOOK_COUNT,COLUMN_BOOK_AVATAR,},
                COLUMN_BOOK_ID+" =?",
                new String[]{bookId},
                null, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            String book_id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID));
            String book_name = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
            String book_description = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DESCRIPTION));
            String book_category_id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_CATEGORY_ID));
            String book_author = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR));
            String book_publisher = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PUBLISHER));
            String book_price = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PRICE));
            String book_count = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_COUNT));
            byte[] book_avatar = cursor.getBlob(cursor.getColumnIndex(COLUMN_BOOK_AVATAR));
            book = new Book(book_id, book_name,book_description, book_category_id, book_author, book_publisher, book_price, book_count, book_avatar);
        }
        return book;
    }

    public List<Book> getAllBook(){
        List<Book> listBook = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_BOOK;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Book book = null;
                String book_id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID));
                String book_name = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
                String book_description = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DESCRIPTION));
                String book_category_id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_CATEGORY_ID));
                String book_author = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR));
                String book_publisher = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PUBLISHER));
                String book_price = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PRICE));
                String book_count = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_COUNT));
                byte[] book_avatar = cursor.getBlob(cursor.getColumnIndex(COLUMN_BOOK_AVATAR));
                book = new Book(book_id, book_name,book_description, book_category_id, book_author, book_publisher, book_price, book_count, book_avatar);
                listBook.add(book);
            }while (cursor.moveToNext());
        }
        database.close();
        return listBook;
    }

    public void deleteBook(Book book){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_BOOK, COLUMN_BOOK_ID+" =?", new String[]{String.valueOf(book.getBookId())});
        database.close();
    }

    public int updateBook(Book book){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, book.getBookId());
        values.put(COLUMN_BOOK_NAME, book.getBookName());
        values.put(COLUMN_BOOK_DESCRIPTION, book.getBookDescription());
        values.put(COLUMN_BOOK_CATEGORY_ID, book.getBookCategoryId());
        values.put(COLUMN_BOOK_AUTHOR, book.getBookAuthor());
        values.put(COLUMN_BOOK_PUBLISHER, book.getBookPublisher());
        values.put(COLUMN_BOOK_PRICE, book.getBookPrice());
        values.put(COLUMN_BOOK_COUNT, book.getBookCount());
        values.put(COLUMN_BOOK_AVATAR, book.getBookAvatar());
        return database.update(TABLE_BOOK, values, COLUMN_BOOK_ID+" =?", new String[]{String.valueOf(book.getBookId())});
    }

    public List<Book> getBookTop10(String month){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<Book> listBook  = new ArrayList<>();
//        if(Integer.parseInt(month)<10){
//            month = "0"+month;
//        }

        String sql = "SELECT * FROM "+TABLE_BOOK
                +" INNER JOIN "+TABLE_INVOICE_DETAIL+" ON "
                +TABLE_BOOK+"."+COLUMN_BOOK_ID+" = "+TABLE_INVOICE_DETAIL+"."+BOOK_ID
                +" INNER JOIN "+TABLE_INVOICE+" ON "
                +TABLE_INVOICE+"."+COLUMN_INVOICE_ID+" = "+TABLE_INVOICE_DETAIL+"."+INVOICE_ID;

//        String sql = "SELECT * FROM "+TABLE_BOOK+" INNER JOIN "+TABLE_INVOICE_DETAIL
//                +" WHERE "+TABLE_BOOK+"."+COLUMN_BOOK_ID+" = "+TABLE_INVOICE_DETAIL+"."+BOOK_ID;
//        String sql = "SELECT * FROM "+TABLE_BOOK+""
//                +", SUM("+PURCHASE_NUMBER+") AS "+SOLUONG+" FROM "
//                +TABLE_INVOICE_DETAIL+" INNER JOIN "+TABLE_INVOICE+" ON "+TABLE_INVOICE+"."+COLUMN_INVOICE_ID+" = "
//                +TABLE_INVOICE_DETAIL+"."+INVOICE_ID+" WHERE STRFTIME('%M', "+TABLE_INVOICE+"."+COLUMN_INVOICE_DATE+") = "
//                +"'"+month+"'"+" GROUP BY "+COLUMN_BOOK_ID+" ORDER BY "+SOLUONG+" DESC LIMIT 10";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Book book = null;
                String book_id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID));
                String book_name = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
                String book_description = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DESCRIPTION));
                String book_category_id = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_CATEGORY_ID));
                String book_author = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR));
                String book_publisher = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PUBLISHER));
                String book_price = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PRICE));
                String book_count = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_COUNT));
                byte[] book_avatar = cursor.getBlob(cursor.getColumnIndex(COLUMN_BOOK_AVATAR));
                book = new Book(book_id, book_name,book_description, book_category_id, book_author, book_publisher, book_price, book_count, book_avatar);
                listBook.add(book);
            }while (cursor.moveToNext());
        }
        database.close();
        return listBook;
    }
}
