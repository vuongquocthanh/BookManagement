package com.example.thanh.android_project_mob204.model;

public class InvoiceDetail {
    private int invoiceDetailId;
    private String invoiceID, bookID;
    private int purchaseNumber;


    public InvoiceDetail(int invoiceDetailId, String invoiceID, String bookID, int purchaseNumber) {
        this.invoiceDetailId = invoiceDetailId;
        this.invoiceID = invoiceID;
        this.bookID = bookID;
        this.purchaseNumber = purchaseNumber;
    }

    public int getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(int invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}
