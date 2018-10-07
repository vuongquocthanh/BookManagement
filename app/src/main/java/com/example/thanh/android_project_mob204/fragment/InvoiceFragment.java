package com.example.thanh.android_project_mob204.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.activity.AddInvoiceActivity;
import com.example.thanh.android_project_mob204.adapter.AdapterInvoice;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Invoice;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceFragment extends Fragment {
    private Toolbar toolbarInvoice;
    public static RecyclerView recyclerViewInvoice;
    public static List<Invoice> listInvoice;
    public static FloatingActionButton floatingActionButton;
    public static AdapterInvoice adapterInvoice;
    public static DatabaseHelper databaseHelper;
    public static DAOInvoice daoInvoice;
    private EditText edFindInvoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        daoInvoice = new DAOInvoice(databaseHelper);
        edFindInvoice = view.findViewById(R.id.edFindInvoice);
        toolbarInvoice = view.findViewById(R.id.toolbarInvoice);
        toolbarInvoice.setTitle("Invoice");
        toolbarInvoice.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarInvoice);
        toolbarInvoice.setTitleMarginStart(30);
        searchInvoice();
        floatingActionButton = view.findViewById(R.id.fabAdd);
        recyclerViewInvoice = view.findViewById(R.id.recyclerviewInvoice);
        listInvoice = new ArrayList<>();
        listInvoice = daoInvoice.getAllInvoice();
        adapterInvoice = new AdapterInvoice(listInvoice);
        recyclerViewInvoice.setAdapter(adapterInvoice);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewInvoice.setLayoutManager(layoutManager);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddInvoiceActivity.class);
                startActivity(intent);
            }
        });
        Log.e("value", listInvoice.get(1).getInvoice_ID());

        return view;
    }

    public void searchInvoice(){
        edFindInvoice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equalsIgnoreCase("")){
                    initList();
                }else{
                    searchItem(charSequence.toString());

                    Log.e("size", listInvoice.size()+"");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void searchItem(String textToSearch){
        for (int i=0; i<listInvoice.size(); i++){
            if(!(listInvoice.get(i).getInvoice_ID().contains(textToSearch))){
                listInvoice.remove(i);
            }
        }
        adapterInvoice.notifyDataSetChanged();
    }

    public void initList() {
        listInvoice = new ArrayList<>();
        listInvoice = daoInvoice.getAllInvoice();
        adapterInvoice = new AdapterInvoice(listInvoice);
        recyclerViewInvoice.setAdapter(adapterInvoice);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewInvoice.setLayoutManager(layoutManager);
    }

}
