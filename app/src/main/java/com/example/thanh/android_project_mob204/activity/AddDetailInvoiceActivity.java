package com.example.thanh.android_project_mob204.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.thanh.android_project_mob204.R;

public class AddDetailInvoiceActivity extends AppCompatActivity {
    private Toolbar toolbarInvoiceDetail;
    private EditText edInvoiceDetailID, edInvoiceBookID, edInvoiceCount;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__detail__invoice);

        toolbarInvoiceDetail = findViewById(R.id.toolbarInvoiceDetail);
        toolbarInvoiceDetail.setNavigationIcon(R.drawable.ic_nav);
        toolbarInvoiceDetail.setTitle("Invoice Detail");
        toolbarInvoiceDetail.setTitleTextColor(getColor(R.color.colorAccent));
        setSupportActionBar(toolbarInvoiceDetail);
        toolbarInvoiceDetail.setTitleMarginStart(30);
        toolbarInvoiceDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edInvoiceDetailID = findViewById(R.id.edDetailInvoiceID);
        edInvoiceBookID = findViewById(R.id.edInvoiceBookID);
        edInvoiceCount = findViewById(R.id.edInvoiceCount);
        Intent intent = getIntent();
        String invoiceID = intent.getStringExtra("invoiceID");
        edInvoiceDetailID.setText(invoiceID);
    }
}
