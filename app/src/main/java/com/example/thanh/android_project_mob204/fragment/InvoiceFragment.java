package com.example.thanh.android_project_mob204.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thanh.android_project_mob204.activity.AddInvoiceActivity;
import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.InvoiceAdapter;
import com.example.thanh.android_project_mob204.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceFragment extends Fragment {
    private Toolbar toolbarInvoice;
    private ListView lvInvoice;
    private InvoiceAdapter adapter;
    private List<Invoice> listInvoice;
    private FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        toolbarInvoice = view.findViewById(R.id.toolbarInvoice);
        toolbarInvoice.setTitle("Invoice");
        toolbarInvoice.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarInvoice);
        toolbarInvoice.setTitleMarginStart(30);
        floatingActionButton = view.findViewById(R.id.fabAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddInvoiceActivity.class);
                startActivity(intent);
            }
        });
        lvInvoice = view.findViewById(R.id.lvInvoice);
        createData();
        adapter = new InvoiceAdapter(getContext(), R.layout.item_invoice, listInvoice);
        lvInvoice.setAdapter(adapter);
        return view;
    }

    private void createData() {
        listInvoice = new ArrayList<>();
        listInvoice.add(new Invoice("", "Invoice 1", "Date1"));
        listInvoice.add(new Invoice("", "Invoice 2", "Date2"));
        listInvoice.add(new Invoice("", "Invoice 3", "Date3"));
        listInvoice.add(new Invoice("", "Invoice 4", "Date4"));
    }
}
