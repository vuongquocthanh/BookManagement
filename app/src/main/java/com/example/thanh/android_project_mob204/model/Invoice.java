package com.example.thanh.android_project_mob204.model;

import java.text.SimpleDateFormat;

public class Invoice {
    private String invoice_ID;
    private long invoice_date;

    public Invoice(String invoice_ID, long invoice_date) {
        this.invoice_ID = invoice_ID;
        this.invoice_date = invoice_date;
    }

    public String getInvoice_ID() {
        return invoice_ID;
    }

    public void setInvoice_ID(String invoice_ID) {
        this.invoice_ID = invoice_ID;
    }

    public long getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(long invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String toStringInvoiceDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateConvert = simpleDateFormat.format(invoice_date);
        return dateConvert;
    }
}
