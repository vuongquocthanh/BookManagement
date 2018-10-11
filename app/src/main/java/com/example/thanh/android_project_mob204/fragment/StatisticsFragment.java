package com.example.thanh.android_project_mob204.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.StatisticsPagerAdapter;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Invoice;
import com.example.thanh.android_project_mob204.model.InvoiceDetail;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoice;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoiceDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    private Toolbar toolbarStatistics;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StatisticsPagerAdapter statisticsPagerAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutStatistics);
        tabLayout.setForegroundTintList(ActivityCompat.getColorStateList(getContext(), R.color.colorAccent));
        toolbarStatistics = view.findViewById(R.id.toolBarStatistics);
        toolbarStatistics.setTitle("Statistics");
        toolbarStatistics.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarStatistics);
        viewPager = view.findViewById(R.id.viewPagerStatistics);
        statisticsPagerAdapter = new StatisticsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(statisticsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
