package com.example.thanh.android_project_mob204;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.thanh.android_project_mob204.adapter.AdapterInvoiceDetail;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.InvoiceDetail;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoiceDetail;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private List<InvoiceDetail> listInvoiceDetail;
    private AdapterInvoiceDetail adapterInvoiceDetail;
    private DatabaseHelper databaseHelper;
    private DAOInvoiceDetail daoInvoiceDetail;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav);
        toolbar.setTitle("Invoice Detail");
        toolbar.setTitleTextColor(getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        databaseHelper = new DatabaseHelper(InvoiceDetailActivity.this);
        daoInvoiceDetail = new DAOInvoiceDetail(databaseHelper);
        Intent intent = getIntent();
        String invoiceID = intent.getStringExtra("invoiceID");
        recyclerview = findViewById(R.id.recyclerview);
        listInvoiceDetail = new ArrayList<>();
        listInvoiceDetail.addAll(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID));
        adapterInvoiceDetail = new AdapterInvoiceDetail(listInvoiceDetail);
        recyclerview.setAdapter(adapterInvoiceDetail);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
    }
}
