package com.example.thanh.android_project_mob204.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.AdapterBook;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Book;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;

import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment {
    private RecyclerView recyclerViewBookTrending;
    private DatabaseHelper databaseHelper;
    private DAOBook daoBook;
    private List<Book> listBook;
    private EditText edFindBookTrending;
    private AdapterBook adapterBook;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewTrending = inflater.inflate(R.layout.fragment_statistics_trending, container, false);
        recyclerViewBookTrending = viewTrending.findViewById(R.id.recyclerViewBookTrending);
        edFindBookTrending = viewTrending.findViewById(R.id.edFindTrending);
        String month = edFindBookTrending.getText().toString();
        databaseHelper = new DatabaseHelper(getContext());
        daoBook = new DAOBook(databaseHelper);

        listBook = new ArrayList<>();
        listBook.addAll(daoBook.getBookTop10(month));
        adapterBook = new AdapterBook(listBook);
        recyclerViewBookTrending.setAdapter(adapterBook);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewBookTrending.setLayoutManager(layoutManager);

        return viewTrending;
    }
}
