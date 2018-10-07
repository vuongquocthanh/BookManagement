package com.example.thanh.android_project_mob204.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.activity.AddBookActivity;
import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.AdapterBook;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Book;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {
    public static ListView lvBook;
    public static List<Book> listBook;
    public static Toolbar toolbar;
    public static RecyclerView rvBook;
    public static DatabaseHelper databaseHelper;
    public static DAOBook daoBook;
    public static AdapterBook adapterBook;
    private EditText edEnterName;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        toolbar = view.findViewById(R.id.toolBar);
        edEnterName = view.findViewById(R.id.edEnterName);
        toolbar.setTitle("Book");
        toolbar.setTitleMarginStart(25);
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(30);
        search();
        listBook = new ArrayList<>();
        rvBook = view.findViewById(R.id.rvBook);
        databaseHelper = new DatabaseHelper(getContext());
        daoBook = new DAOBook(databaseHelper);
        listBook.addAll(daoBook.getAllBook());
        adapterBook = new AdapterBook(listBook);
        rvBook.setAdapter(adapterBook);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvBook.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        rvBook.addItemDecoration(dividerItemDecoration);
        return view;
    }

    public void search(){
        edEnterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equalsIgnoreCase("")){
//                    Reset ListView
                    databaseHelper = new DatabaseHelper(getContext());
                    daoBook = new DAOBook(databaseHelper);
                    listBook = new ArrayList<>();
                    listBook.addAll(daoBook.getAllBook());
                    adapterBook = new AdapterBook(listBook);
                    rvBook.setAdapter(adapterBook);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rvBook.setLayoutManager(layoutManager);
                }else{
                    searchItem(s.toString());
                    Log.e("size", String.valueOf(listBook.size()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void searchItem(String textToSearch){
        for(int i=0; i<listBook.size(); i++){
            if(!(listBook.get(i).getBookName().toLowerCase().contains(textToSearch.toLowerCase()))){
                listBook.remove(i);
            }
        }
        adapterBook.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_book, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addBook:
                Intent intent = new Intent(getContext(), AddBookActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
