package com.example.thanh.android_project_mob204.sqlitedao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thanh.android_project_mob204.Constant;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Invoice;
import com.example.thanh.android_project_mob204.model.InvoiceDetail;

import java.util.ArrayList;
import java.util.List;

public class DAOInvoiceDetail implements Constant {
    public DatabaseHelper databaseHelper;

    public DAOInvoiceDetail(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long insertInvoiceDetail(String invoice_ID, String book_ID, int purchase_number){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INVOICE_ID, invoice_ID);
        values.put(BOOK_ID, book_ID);
        values.put(PURCHASE_NUMBER, purchase_number);

        long result = database.insert(TABLE_INVOICE_DETAIL, null, values);
        database.close();
        return result;
    }

    public long updateInvoiceDetail(InvoiceDetail invoiceDetail){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_ID, invoiceDetail.getBookID());
        values.put(PURCHASE_NUMBER, invoiceDetail.getPurchaseNumber());
        long result = database.update(TABLE_INVOICE_DETAIL, values,
                INVOICE_DETAIL_ID+" =?",
                new String[]{invoiceDetail.getInvoiceDetailId()+""});
        return result;
    }

    public void deleteInvoice(int invoiceDetailID){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_INVOICE_DETAIL, INVOICE_DETAIL_ID+" =?", new String[]{invoiceDetailID+""});
        database.close();
    }

    public void deleteAllInvoiceDetail(String invoiceID){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_INVOICE_DETAIL, INVOICE_ID+" =?", null);
        database.close();
    }

    public List<InvoiceDetail> getAllInvoice(){

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<InvoiceDetail> listInvoiceDetail = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_INVOICE_DETAIL, null);
        if (cursor!=null && cursor.moveToFirst()){
            do{
                InvoiceDetail invoiceDetail = null;
                int invoice_detai_id = cursor.getInt(cursor.getColumnIndex(INVOICE_DETAIL_ID));
                String invoice_id  = cursor.getString(cursor.getColumnIndex(INVOICE_ID));
                String book_id = cursor.getString(cursor.getColumnIndex(BOOK_ID));
                Integer purchase_number = cursor.getInt(cursor.getColumnIndex(PURCHASE_NUMBER));
                invoiceDetail = new InvoiceDetail(invoice_detai_id, invoice_id, book_id, purchase_number);
                listInvoiceDetail.add(invoiceDetail);
            }while (cursor.moveToNext());
        }
        database.close();
        return listInvoiceDetail;
    }

    public InvoiceDetail getInvoiceDetailById(long invoiceDetailID){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        InvoiceDetail invoiceDetail = null;
        Cursor cursor = database.query(TABLE_INVOICE_DETAIL,
                new String[]{INVOICE_DETAIL_ID, INVOICE_ID, BOOK_ID, PURCHASE_NUMBER},
                INVOICE_DETAIL_ID+" =?", new String[]{invoiceDetailID+""}, null, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            int invoice_detai_id = cursor.getInt(cursor.getColumnIndex(INVOICE_DETAIL_ID));
            String invoice_id  = cursor.getString(cursor.getColumnIndex(INVOICE_ID));
            String book_id = cursor.getString(cursor.getColumnIndex(BOOK_ID));
            Integer purchase_number = cursor.getInt(cursor.getColumnIndex(PURCHASE_NUMBER));
            invoiceDetail = new InvoiceDetail(invoice_detai_id, invoice_id, book_id, purchase_number);
        }
        database.close();
        return invoiceDetail;
    }

    public List<InvoiceDetail> getAllInvoiceDetailByInvoiceID(String invoiceID){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<InvoiceDetail> listInvoiceDetail = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_INVOICE_DETAIL+" WHERE "+INVOICE_ID+" = '"+invoiceID+"' ORDER BY "+INVOICE_ID;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor!=null && cursor.moveToFirst()){
            do{
                InvoiceDetail invoiceDetail = null;
                int invoice_detai_id = cursor.getInt(cursor.getColumnIndex(INVOICE_DETAIL_ID));
                String invoice_id  = cursor.getString(cursor.getColumnIndex(INVOICE_ID));
                String book_id = cursor.getString(cursor.getColumnIndex(BOOK_ID));
                Integer purchase_number = cursor.getInt(cursor.getColumnIndex(PURCHASE_NUMBER));
                invoiceDetail = new InvoiceDetail(invoice_detai_id, invoice_id, book_id, purchase_number);
                listInvoiceDetail.add(invoiceDetail);
            }while (cursor.moveToNext());
        }
        database.close();
        return listInvoiceDetail;

    }
}
