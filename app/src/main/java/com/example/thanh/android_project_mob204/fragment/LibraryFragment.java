package com.example.thanh.android_project_mob204.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.Library_Adapter_Main;
import com.example.thanh.android_project_mob204.model.Library;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {
    private Toolbar toolbarLibrary;
    private RecyclerView rvLibrary;
    private Library_Adapter_Main adapter;
    private List<Library> listLibrary;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        toolbarLibrary = view.findViewById(R.id.toolbarLibrary);
        toolbarLibrary.setTitle("Library");
        toolbarLibrary.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarLibrary);
        toolbarLibrary.setTitleMarginStart(30);
        rvLibrary = view.findViewById(R.id.rvLibrary);
        createData();
        adapter = new Library_Adapter_Main(listLibrary);
        rvLibrary.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        rvLibrary.setLayoutManager(manager);
        return view;
    }

    private void createData() {
        listLibrary = new ArrayList<>();
        listLibrary.add(new Library(R.drawable.title1,"ACTION","SubTitle1"));
        listLibrary.add(new Library(R.drawable.title2,"ROMANTIC","SubTitle2"));
        listLibrary.add(new Library(R.drawable.title3,"HISTORY","SubTitle3"));
        listLibrary.add(new Library(R.drawable.title4,"SCIENCE FICTION","SubTitle4"));
        listLibrary.add(new Library(R.drawable.title5,"HONOR","SubTitle5"));
        listLibrary.add(new Library(R.drawable.title6,"TECHNOLOGY","SubTitle6"));
    }
}
