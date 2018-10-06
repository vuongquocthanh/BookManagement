package com.example.thanh.android_project_mob204.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.AdapterInvoiceDetail;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Invoice;
import com.example.thanh.android_project_mob204.model.InvoiceDetail;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoiceDetail;

import java.util.ArrayList;
import java.util.List;

public class AddDetailInvoiceActivity extends AppCompatActivity {
    private Toolbar toolbarInvoiceDetail;
    private EditText edInvoiceID, edInvoiceBookID, edInvoiceCount;
    private RecyclerView recyclerviewDetaiInvoice;
    private TextView tvAmount;
    private DatabaseHelper databaseHelper;
    private DAOInvoiceDetail daoInvoiceDetail;
    private DAOBook daoBook;
    private List<InvoiceDetail> listInvoiceDetail;
    private AdapterInvoiceDetail adapterInvoiceDetail;
    private Button btInvoiceDetailAdd, btPay;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__detail__invoice);

        databaseHelper = new DatabaseHelper(AddDetailInvoiceActivity.this);
        daoInvoiceDetail = new DAOInvoiceDetail(databaseHelper);
        daoBook = new DAOBook(databaseHelper);
        listInvoiceDetail = new ArrayList<>();


        recyclerviewDetaiInvoice = findViewById(R.id.recyclerviewInvoiceDetail);
        tvAmount = findViewById(R.id.tvAmount);
        edInvoiceID = findViewById(R.id.edInvoiceID);
        edInvoiceBookID = findViewById(R.id.edInvoiceBookID);
        edInvoiceCount = findViewById(R.id.edInvoiceCount);
        btInvoiceDetailAdd = findViewById(R.id.btInvoiceDetailAdd);
        btPay = findViewById(R.id.btPay);

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
        final Intent intent = getIntent();
        final String invoiceID = intent.getStringExtra("invoiceID");
        edInvoiceID.setText(invoiceID);
//
//        listInvoiceDetail = new ArrayList<>();
//        listInvoiceDetail = daoInvoiceDetail.getAllInvoice();
//        adapterInvoiceDetail = new AdapterInvoiceDetail(listInvoiceDetail);
//        recyclerviewDetaiInvoice.setAdapter(adapterInvoiceDetail);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AddDetailInvoiceActivity.this);
//        recyclerviewDetaiInvoice.setLayoutManager(layoutManager);

        btInvoiceDetailAdd.setOnClickListener(new View.OnClickListener() {

            String invoice_ID = edInvoiceID.getText().toString();
            @Override
            public void onClick(View view) {
                boolean check = true;
                for (int i = 0; i < daoBook.getAllBook().size(); i++) {
                    if (daoBook.getAllBook().get(i).getBookId().equalsIgnoreCase(edInvoiceBookID.getText().toString())) {
                        check = true;
                        break;
                    } else {
                        check = false;
                    }
                }
                if (check == true) {
                    Log.e("listByInvoiceID", daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoice_ID).size()+"");
                    boolean check1 = false;
                    int index = 0;
                    for (int j = 0; j < daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoice_ID).size(); j++) {
                        if (daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoice_ID).get(j).getBookID().equalsIgnoreCase(edInvoiceBookID.getText().toString())) {
                            index = j;
                            check1 = true;
                            break;
                        } else {
                            check1 = false;
                        }
                    }
                    Log.e("check1", check1+"");
                    if (check1 == true) {
                        Log.e("index", index+"");
//                        Update
                        InvoiceDetail invoiceDetail1 = listInvoiceDetail.get(index);
                        invoiceDetail1.setPurchaseNumber(Integer.parseInt(edInvoiceCount.getText().toString())+listInvoiceDetail.get(index).getPurchaseNumber());
                        listInvoiceDetail.set(index, invoiceDetail1);
                        daoInvoiceDetail.updateInvoiceDetail(invoiceDetail1);
                        adapterInvoiceDetail.notifyDataSetChanged();
                    } else {
                        long id = daoInvoiceDetail.insertInvoiceDetail(invoiceID, edInvoiceBookID.getText().toString(), Integer.parseInt(edInvoiceCount.getText().toString()));
                        InvoiceDetail invoiceDetail = daoInvoiceDetail.getInvoiceDetailById(id);
                        if (invoiceDetail != null) {
                            listInvoiceDetail.add(invoiceDetail);
                        }
                        adapterInvoiceDetail = new AdapterInvoiceDetail(listInvoiceDetail);
                        recyclerviewDetaiInvoice.setAdapter(adapterInvoiceDetail);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AddDetailInvoiceActivity.this);
                        recyclerviewDetaiInvoice.setLayoutManager(layoutManager);
                        adapterInvoiceDetail.notifyDataSetChanged();
                        Log.e("size", daoInvoiceDetail.getAllInvoice().size() + "");
                        Log.e("size2", daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID) + "");
                    }

                } else {
                    edInvoiceBookID.setError("Book ID not found!!!");
                }
            }
        });
//        btPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                double amount = 0;
//                int purchase = 0;
//                for (int i = 0; i < daoInvoiceDetail.getAllInvoice().size(); i++) {
//                    purchase += daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID).get(i).getPurchaseNumber();
//                }
//                String price = daoBook.getBook(edInvoiceBookID.getText().toString()).getBookPrice();
//                Log.e("purchase", purchase+"");
//                Log.e("price", price);
//                amount = purchase*Double.parseDouble(price);
//                tvAmount.setText(amount+"");
//            }
//        });

        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount = 0;
                int purchase = 0;
                Log.e("size", daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID).size() + "");
//                for(int i=0; i<daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID).size(); i++){
//                    purchase = daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID).get(i).getPurchaseNumber();
//                    String price = daoBook.getBook(daoInvoiceDetail.get).getBookPrice();
//                    amount += purchase*Double.parseDouble(price);
//                }
                for (int i = 0; i < daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID).size(); i++) {
                    Log.e("bookid", daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID).get(i).getBookID());
                    amount += daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID).get(i).getPurchaseNumber() * (Double.parseDouble(daoBook.getBook(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceID).get(i).getBookID()).getBookPrice()));

                }
                tvAmount.setText(amount + "");
            }
        });
    }
}
