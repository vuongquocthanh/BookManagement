package com.example.thanh.android_project_mob204.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;

import java.util.Calendar;

public class AddInvoiceActivity extends AppCompatActivity {
    private Toolbar toolbarAddInvoice;
    private EditText edInvoiceID, edInvoiceDate;
    private Button btInvoiceDate, btInvoiceAdd;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);
        toolbarAddInvoice = findViewById(R.id.toolbarAddInvoice);
        edInvoiceID = findViewById(R.id.edInvoiceID);
        edInvoiceDate =findViewById(R.id.edInvoiceDate);
        btInvoiceDate = findViewById(R.id.btSelectInvoiceDate);
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
                    Toast.makeText(AddInvoiceActivity.this, "Vui long dien day du thong tin!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AddInvoiceActivity.this, AddDetailInvoiceActivity.class);
                    intent.putExtra("invoiceID", invoiceID);
                    startActivity(intent);
                }
            }
        });

    }

    public void showDialogDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.Theme_AppCompat_DayNight_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edInvoiceDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
