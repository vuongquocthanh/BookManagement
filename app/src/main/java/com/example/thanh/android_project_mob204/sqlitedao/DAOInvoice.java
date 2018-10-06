package com.example.thanh.android_project_mob204.sqlitedao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;

import com.example.thanh.android_project_mob204.Constant;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class DAOInvoice implements Constant {
    public DatabaseHelper databaseHelper;

    public DAOInvoice(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long insertInvoice(Invoice invoice){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INVOICE_ID, invoice.getInvoice_ID());
        values.put(COLUMN_INVOICE_DATE, invoice.getInvoice_date());
        long result = database.insert(TABLE_INVOICE, null, values);
        database.close();
        return result;
    }

    public long updateInvoice(Invoice invoice){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INVOICE_ID, invoice.getInvoice_ID());
        values.put(COLUMN_INVOICE_DATE, invoice.getInvoice_date());
        long result = database.update(TABLE_INVOICE, values,
                COLUMN_INVOICE_ID+" =?",
                new String[]{invoice.getInvoice_ID()});
        return result;
    }

    public void deleteInvoice(String invoiceID){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_INVOICE, COLUMN_INVOICE_ID+" =?", new String[]{invoiceID});
        database.close();
    }

    public List<Invoice> getAllInvoice(){

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<Invoice> listInvoice = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_INVOICE, null);
        if (cursor!=null && cursor.moveToFirst()){
            do{
                Invoice invoice = null;
                String invoice_ID = cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_ID));
                long invoice_date  = cursor.getLong(cursor.getColumnIndex(COLUMN_INVOICE_DATE));
                invoice = new Invoice(invoice_ID, invoice_date);
                listInvoice.add(invoice);
            }while (cursor.moveToNext());
        }
        database.close();
        return listInvoice;
    }

    public Invoice getInvoiceById(String invoiceID){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Invoice invoice = null;
        Cursor cursor = database.query(TABLE_INVOICE,
                new String[]{COLUMN_INVOICE_ID, COLUMN_INVOICE_DATE},
                COLUMN_INVOICE_ID+" =?", new String[]{invoiceID}, null, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            String invoice_ID = cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_ID));
            long invoice_date  = cursor.getLong(cursor.getColumnIndex(COLUMN_INVOICE_DATE));
            invoice = new Invoice(invoice_ID, invoice_date);
        }
        database.close();
        return invoice;
    }
}
