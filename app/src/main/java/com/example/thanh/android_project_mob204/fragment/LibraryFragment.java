package com.example.thanh.android_project_mob204.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.activity.AddBookActivity;
import com.example.thanh.android_project_mob204.activity.AddCategoryActivity;
import com.example.thanh.android_project_mob204.adapter.AdapterCategory;
import com.example.thanh.android_project_mob204.adapter.Library_Adapter_Main;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Category;
import com.example.thanh.android_project_mob204.model.Library;
import com.example.thanh.android_project_mob204.sqlitedao.DAOCategory;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {
    private Toolbar toolbarLibrary;
    public static RecyclerView rvLibrary;
    public static AdapterCategory adapter;
    public static List<Category> listLibrary;
    public static DatabaseHelper databaseHelper;
    public static DAOCategory daoCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        toolbarLibrary = view.findViewById(R.id.toolbarLibrary);
        rvLibrary = view.findViewById(R.id.rvLibrary);
        toolbarLibrary.setTitle("Library");
        toolbarLibrary.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarLibrary);
        databaseHelper = new DatabaseHelper(getContext());
        daoCategory = new DAOCategory(databaseHelper);
        listLibrary = new ArrayList<>();
        listLibrary.addAll(daoCategory.getAllCategory());
        adapter = new AdapterCategory(listLibrary);
        rvLibrary.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvLibrary.setLayoutManager(layoutManager);

//        createData();
//        adapter = new Library_Adapter_Main(listLibrary);
//        rvLibrary.setAdapter(adapter);
//        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 3);
//        rvLibrary.setLayoutManager(manager);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addCategory:
                Intent intent = new Intent(getContext(), AddCategoryActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void createData() {
//        listLibrary = new ArrayList<>();
//        listLibrary.add(new Library(R.drawable.title1,"ACTION","SubTitle1"));
//        listLibrary.add(new Library(R.drawable.title2,"ROMANTIC","SubTitle2"));
//        listLibrary.add(new Library(R.drawable.title3,"HISTORY","SubTitle3"));
//        listLibrary.add(new Library(R.drawable.title4,"SCIENCE FICTION","SubTitle4"));
//        listLibrary.add(new Library(R.drawable.title5,"HONOR","SubTitle5"));
//        listLibrary.add(new Library(R.drawable.title6,"TECHNOLOGY","SubTitle6"));
//    }
}
