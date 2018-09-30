package com.example.thanh.android_project_mob204.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thanh.android_project_mob204.activity.AddBookActivity;
import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.BookAdapter;
import com.example.thanh.android_project_mob204.model.Book;

import java.util.ArrayList;
import java.util.List;

import static com.example.thanh.android_project_mob204.R.color.colorAccent;

public class BookFragment extends Fragment {
    private ListView lvBook;
    private List<Book> listBook;
    private BookAdapter adapter;
    private Toolbar toolbar;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        lvBook = view.findViewById(R.id.lvBook);
        toolbar = view.findViewById(R.id.toolBar);
        toolbar.setTitle("Book");
        toolbar.setTitleMarginStart(25);
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(30);
        createData();
        adapter = new BookAdapter(getContext(), R.layout.item_listview_book, listBook);
        lvBook.setAdapter(adapter);
//        Bundle bundle = getArguments();
//        String usernameLogin = null;
//        if(bundle!=null){
//            usernameLogin = bundle.getString("username");
//        }
//        Log.e("username", usernameLogin);
        return view;
    }

    private void createData() {
        listBook = new ArrayList<>();
        listBook.add(new Book("The Awkward", R.drawable.book1, "Born là một bí ẩn trong văn học mạng, người ta chỉ biết đến cô qua khối lượng tác phẩm khá lớn đã được in thành sách."));
        listBook.add(new Book("The End", R.drawable.book2, "Born là một bí ẩn trong văn học mạng, người ta chỉ biết đến cô qua khối lượng tác phẩm khá lớn đã được in thành sách."));
        listBook.add(new Book("The 1-Our", R.drawable.book3, "Born là một bí ẩn trong văn học mạng, người ta chỉ biết đến cô qua khối lượng tác phẩm khá lớn đã được in thành sách."));
        listBook.add(new Book("Improve Your Social Skill", R.drawable.book4, "Born là một bí ẩn trong văn học mạng, người ta chỉ biết đến cô qua khối lượng tác phẩm khá lớn đã được in thành sách."));
        listBook.add(new Book("The Dress and The Girl", R.drawable.book6, "Born là một bí ẩn trong văn học mạng, người ta chỉ biết đến cô qua khối lượng tác phẩm khá lớn đã được in thành sách."));
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
            case R.id.add:
                Intent intent = new Intent(getContext(), AddBookActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
