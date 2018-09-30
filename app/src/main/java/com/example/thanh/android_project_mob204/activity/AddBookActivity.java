package com.example.thanh.android_project_mob204.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.thanh.android_project_mob204.R;

public class AddBookActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        toolbar = findViewById(R.id.toolBar);
        toolbar.setNavigationIcon(R.drawable.ic_nav);
        toolbar.setTitle("Add Book");
        toolbar.setTitleTextColor(getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(30);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
