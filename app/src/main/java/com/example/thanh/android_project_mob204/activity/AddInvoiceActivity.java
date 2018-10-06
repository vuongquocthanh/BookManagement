package com.example.thanh.android_project_mob204.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.AdapterInvoice;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.fragment.InvoiceFragment;
import com.example.thanh.android_project_mob204.model.Invoice;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddInvoiceActivity extends AppCompatActivity {
    private Toolbar toolbarAddInvoice;
    private EditText edInvoiceID, edInvoiceDate;
    private Button btInvoiceAdd;
    private DatabaseHelper databaseHelper;
    private DAOInvoice daoInvoice;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);
        databaseHelper = new DatabaseHelper(this);
        daoInvoice = new DAOInvoice(databaseHelper);
        toolbarAddInvoice = findViewById(R.id.toolbarAddInvoice);
        edInvoiceID = findViewById(R.id.edInvoiceID);
        edInvoiceDate =findViewById(R.id.edInvoiceDate);
        btInvoiceAdd = findViewById(R.id.btInvoiceAdd);
        toolbarAddInvoice.setNavigationIcon(R.drawable.ic_nav);
        toolbarAddInvoice.setTitle("Add Invoice");
        toolbarAddInvoice.setTitleTextColor(getColor(R.color.colorAccent));
        setSupportActionBar(toolbarAddInvoice);
        toolbarAddInvoice.setTitleMarginStart(30);
        toolbarAddInvoice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btInvoiceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String invoiceID = edInvoiceID.getText().toString().trim();
                String date = edInvoiceDate.getText().toString().trim();
                if(invoiceID.equalsIgnoreCase("") || date.equalsIgnoreCase("")){
                    Toast.makeText(AddInvoiceActivity.this, "Please do not empty!", Toast.LENGTH_SHORT).show();
                }else{
                    long mili = 0;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                    try {
                        Date d = simpleDateFormat.parse(edInvoiceDate.getText().toString().trim());
                        mili = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    daoInvoice.insertInvoice(new Invoice(edInvoiceID.getText().toString().trim(), mili));
                    insertDataToList();
                    Intent intent = new Intent(AddInvoiceActivity.this, AddDetailInvoiceActivity.class);
                    intent.putExtra("invoiceID", invoiceID);
                    startActivity(intent);
                }
                finish();
            }
        });

    }

    public void insertDataToList(){
        InvoiceFragment.listInvoice = new ArrayList<>();
        InvoiceFragment.listInvoice = daoInvoice.getAllInvoice();
        InvoiceFragment.adapterInvoice = new AdapterInvoice(InvoiceFragment.listInvoice);
        InvoiceFragment.recyclerViewInvoice.setAdapter(InvoiceFragment.adapterInvoice);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AddInvoiceActivity.this);
        InvoiceFragment.recyclerViewInvoice.setLayoutManager(layoutManager);
    }

    public void showDialogDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.Theme_AppCompat_DayNight_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                long datePicked = 0;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                datePicked = calendar.getTimeInMillis();
//                String datePicked_ = new Date(datePicked).toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                String strDate = simpleDateFormat.format(datePicked);
                edInvoiceDate.setText(strDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
